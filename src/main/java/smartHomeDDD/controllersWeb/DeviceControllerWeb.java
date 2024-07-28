package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The DeviceControllerWeb class provides web services related to Device objects.
 */
@RestController
@RequestMapping("/api/v1/devices")
public class DeviceControllerWeb {

    /**
     * The service for devices.
     */
    private final ServiceDevice serviceDevice;

    /**
     * The service for devices by type.
     */
    private final ServiceDeviceByType serviceDeviceByType;

    /**
     * The service for sensors.
     */
    private final ServiceSensor serviceSensor;

    /**
     * The service for sensor readings.
     */
    private final ServiceSensorReading serviceSensorReading;

    /**
     * The service for actuators.
     */
    private final ServiceActuator serviceActuator;

    /**
     * Constructor for the DeviceControllerWeb class.
     *
     * @param deviceService The service for devices.
     * @throws IllegalArgumentException If the device service is null.
     */
    public DeviceControllerWeb(ServiceDevice deviceService, ServiceDeviceByType serviceDeviceByType, ServiceSensor serviceSensor, ServiceSensorReading serviceSensorReading, ServiceActuator serviceActuator) {
        this.serviceDevice = deviceService;
        this.serviceDeviceByType = serviceDeviceByType;
        this.serviceSensor = serviceSensor;
        this.serviceSensorReading = serviceSensorReading;
        this.serviceActuator = serviceActuator;
    }

    /**
     * This method is responsible for adding a new device to the system.
     * It receives a DeviceEntryWebDTO object as a request body, which contains the necessary information to create a new device.
     * The method maps the DTO to domain objects, then calls the service layer to add the new device.
     * If the device is successfully added, it returns a DeviceWebDTO object with a CREATED (201) HTTP status.
     * If a DataIntegrityViolationException is thrown, it means there was a conflict with the existing data (e.g., duplicate device ID), and a CONFLICT (409) status is returned.
     * If an EntityNotFoundException is thrown, it means some referenced entity was not found (e.g., the room for the device does not exist), and a BAD_REQUEST (400) status is returned.
     *
     * @param deviceEntryWebDTO The device to add.
     * @return The added device.
     * @throws DataIntegrityViolationException if there is a conflict with the existing data.
     * @throws EntityNotFoundException         if a referenced entity was not found.
     */
    @PostMapping("")
    public ResponseEntity<?> addDevice(@RequestBody DeviceEntryWebDTO deviceEntryWebDTO) {
        try {
            DeviceName deviceName = DeviceMapper.DTOToDeviceName(deviceEntryWebDTO.getDeviceName());
            DeviceModel deviceModel = DeviceMapper.DTOToDeviceModel(deviceEntryWebDTO.getDeviceModel());
            ActivationStatus activationStatus = DeviceMapper.DTOToActivationStatus(deviceEntryWebDTO.getActivationStatus());
            RoomID roomID = RoomMapper.DTOToRoomId(deviceEntryWebDTO.getRoomId());

            Device addedDevice = serviceDevice.addNewDevice(deviceName, deviceModel, activationStatus, roomID);

            DeviceExitWebDTO addedDeviceDTO = DeviceMapper.domainToExitWebDTO(addedDevice);

            Link selfLink = linkTo(DeviceControllerWeb.class).slash(addedDeviceDTO.getDeviceId()).withSelfRel();
            addedDeviceDTO.add(selfLink);

            return new ResponseEntity<>(addedDeviceDTO, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is responsible for retrieving a specific device by its ID.
     * It receives a device ID as a path variable, which is used to fetch the device from the service layer.
     * The method maps the domain object to a DeviceWebDTO object, which is then returned in the response.
     * If the device is successfully retrieved, it returns a DeviceWebDTO object with an OK (200) HTTP status.
     * If an EntityNotFoundException is thrown, it means the device with the provided ID was not found, and a NOT_FOUND (404) status is returned.
     *
     * @param id The unique identifier for the device.
     * @return The device with the given ID.
     * @throws EntityNotFoundException if the device with the provided ID was not found.
     */
    @GetMapping("/{deviceID}")
    public ResponseEntity<?> getDevice(@PathVariable(value = "deviceID") String id) {
        try {
            DeviceId deviceId = DeviceMapper.DTOToDeviceId(id);

            Device device = serviceDevice.getDeviceByID(deviceId);

            DeviceExitWebDTO deviceDTO = DeviceMapper.domainToExitWebDTO(device);

            Link selfLink = linkTo(DeviceControllerWeb.class).slash(deviceDTO.getDeviceId()).withSelfRel();
            Link actuatorsLink = linkTo(methodOn(DeviceControllerWeb.class).getActuatorsByDevice(deviceDTO.getDeviceId())).withRel("actuators");
            Link sensorsLink = linkTo(methodOn(DeviceControllerWeb.class).getSensorsByDevice(deviceDTO.getDeviceId())).withRel("sensors");
            Link sensorReadingsLink = linkTo(methodOn(DeviceControllerWeb.class).getSensorReadingsByDevice(deviceDTO.getDeviceId())).withRel("sensor-readings");

            deviceDTO.add(selfLink);
            deviceDTO.add(actuatorsLink);
            deviceDTO.add(sensorsLink);
            deviceDTO.add(sensorReadingsLink);

            return new ResponseEntity<>(deviceDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for retrieving all sensors associated with a specific device.
     * It receives a device ID as a path variable, which is used to fetch the sensors from the service layer.
     * The method maps the domain objects to SensorWebDTO objects, which are then returned in the response.
     * If the sensors are successfully retrieved, it returns a list of SensorWebDTO objects with an OK (200) HTTP status.
     * If an EntityNotFoundException is thrown, it means the device with the provided ID was not found, and a NOT_FOUND (404) status is returned.
     *
     * @param id The unique identifier for the device.
     * @return A list of sensors associated with the given device ID.
     * @throws EntityNotFoundException if the device with the provided ID was not found.
     */
    @GetMapping("/{deviceID}/sensors")
    public ResponseEntity<?> getSensorsByDevice(@PathVariable(value = "deviceID") String id) {
        try {
            DeviceId deviceId = DeviceMapper.DTOToDeviceId(id);

            List<Sensor> sensors = serviceSensor.getSensorsByDeviceID(deviceId);

            List<SensorIDExitWebDTO> listSensorsDTO = new ArrayList<>();

            for (Sensor sensor : sensors) {
                SensorIDExitWebDTO sensorDTO = SensorMapper.convertSensorIDToExitWebDTO(sensor);
                Link selfLink = linkTo(SensorControllerWeb.class).slash(sensorDTO.getSensorId()).withSelfRel();
                sensorDTO.add(selfLink);
                listSensorsDTO.add(sensorDTO);
            }

            return new ResponseEntity<>(listSensorsDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * This method is responsible for retrieving all actuators associated with a specific device, identified by its ID.
     * If the actuators are successfully retrieved, it returns a list of ActuatorWebDTO objects with an OK (200) HTTP status.
     * If an EntityNotFoundException is thrown, and the device with the provided ID was not found, and a NOT_FOUND (404) status is returned.
     *
     * @param id The unique identifier for the device.
     * @return A list of actuators associated with the given device ID.
     * @throws EntityNotFoundException if the device with the provided ID was not found.
     */
    @GetMapping("/{deviceID}/actuators")
    public ResponseEntity<?> getActuatorsByDevice(@PathVariable(value = "deviceID") String id) {
        try {
            DeviceId deviceId = DeviceMapper.DTOToDeviceId(id);

            List<Actuator> actuators = serviceActuator.getActuatorsByDeviceID(deviceId);

            List<ActuatorIDExitWebDTO> listActuatorsDTO = new ArrayList<>();

            for (Actuator actuator : actuators) {
                ActuatorIDExitWebDTO actuatorDTO = ActuatorMapper.convertToIDActuatorExitWebDTO(actuator.identity());
                Link selfLink = linkTo(ActuatorControllerWeb.class).slash(actuatorDTO.getActuatorId()).withSelfRel();
                actuatorDTO.add(selfLink);
                listActuatorsDTO.add(actuatorDTO);
            }

            return new ResponseEntity<>(listActuatorsDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * This method is responsible for retrieving all sensor readings associated with a specific device.
     * It receives a device ID as a path variable, which is used to fetch the sensor readings from the service layer.
     * The method maps the domain objects to SensorReadingWebDTO objects, which are then returned in the response.
     * If the sensor readings are successfully retrieved, it returns a list of SensorReadingWebDTO objects with an OK (200) HTTP status.
     * If an EntityNotFoundException is thrown, it means the device with the provided ID was not found, and a NOT_FOUND (404) status is returned.
     *
     * @param id The unique identifier for the device.
     * @return A list of sensor readings associated with the given device ID.
     * @throws EntityNotFoundException if the device with the provided ID was not found.
     */
    @GetMapping("/{deviceID}/sensor-readings")
    public ResponseEntity<?> getSensorReadingsByDevice(@PathVariable(value = "deviceID") String id) {
        try {
            DeviceId deviceId = DeviceMapper.DTOToDeviceId(id);

            List<SensorReading> sensorReadings = serviceSensorReading.getSensorReadingsByDeviceID(deviceId);

            List<SensorReadingIDExitWebDTO> listSensorReadingsDTO = new ArrayList<>();

            for (SensorReading sensorReading : sensorReadings) {
                SensorReadingIDExitWebDTO sensorReadingDTO = SensorReadingMapper.sensorReadingIDToExitWebDTO(sensorReading);
                Link selfLink = linkTo(SensorReadingWebController.class).slash(sensorReadingDTO.getSensorReadingID()).withSelfRel();
                sensorReadingDTO.add(selfLink);
                listSensorReadingsDTO.add(sensorReadingDTO);
            }

            return new ResponseEntity<>(listSensorReadingsDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * This method is responsible for retrieving devices based on the provided query parameter.
     * If the 'getBy' parameter is not provided, it returns all devices.
     * If the 'getBy' parameter is set to 'active', it returns all active devices.
     * If the 'getBy' parameter is set to 'functionality', it returns devices sorted by their functionality.
     *
     * @param getBy The query parameter to determine the type of devices to return.
     * @return A ResponseEntity containing a list of devices and an HTTP status.
     */
    @GetMapping("")
    public ResponseEntity<?> getDevices(@RequestParam(required = false) String getBy) {
        if (getBy != null && getBy.equals("active")) {

            return getActiveDevices();
        }
        if (getBy != null && getBy.equals("functionality")) {

            return getDevicesByType();
        } else {

            return getAllDevices();

        }

    }

    /**
     * This method is responsible for deactivating a device in the system.
     * It receives a device ID as a request parameter, which is used to deactivate the device in the service layer.
     * The method maps the domain object to a DeviceWebDTO object, which is then returned in the response.
     * If the device is successfully deactivated, it returns a DeviceWebDTO object with an OK (200) HTTP status.
     * If an EntityNotFoundException is thrown, it means the device with the provided ID was not found, and a NOT_FOUND (404) status is returned.
     *
     * @param id The unique identifier for the device.
     * @return The deactivated device.
     * @throws EntityNotFoundException if the device with the provided ID was not found.
     */
    @PatchMapping("")
    public ResponseEntity<?> deactivateDevice(@RequestParam(value = "deviceID") String id) {

        try {
            DeviceId deviceId = DeviceMapper.DTOToDeviceId(id);

            Device updatedDevice = serviceDevice.deactivateDevice(deviceId);

            DeviceExitWebDTO updatedDeviceDTO = DeviceMapper.domainToExitWebDTO(updatedDevice);

            Link selfLink = linkTo(DeviceControllerWeb.class).slash(updatedDeviceDTO.getDeviceId()).withSelfRel();
            updatedDeviceDTO.add(selfLink);

            return new ResponseEntity<>(updatedDeviceDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method retrieves all active devices from the service layer, converts them to DeviceWebDTO objects,
     * adds a self link to each DTO, and returns them in a ResponseEntity.
     *
     * @return ResponseEntity containing a list of DeviceWebDTO objects representing all active devices, and an HTTP status of OK.
     */
    private ResponseEntity<List<DeviceIDExitWebDTO>> getActiveDevices() {
        List<Device> devices = serviceDevice.listOfActiveDevices();
        List<DeviceIDExitWebDTO> listDeviceWebDTOs = DeviceMapper.domainListToWebDTO(devices);

        for (DeviceIDExitWebDTO device : listDeviceWebDTOs) {
            Link selfLink = linkTo(DeviceControllerWeb.class).slash(device.getId()).withSelfRel();
            device.add(selfLink);
        }

        return new ResponseEntity<>(listDeviceWebDTOs, HttpStatus.OK);
    }

    /**
     * This method retrieves devices grouped by their type from the service layer, converts them to DeviceWebDTO objects,
     * adds a self link to each DTO, and returns them in a ResponseEntity.
     * @return ResponseEntity containing a map where the key is the device type and the value is a list of DeviceWebDTO objects representing all devices of that type, and an HTTP status of OK.
     */
    private ResponseEntity<Map<String, List<DeviceIDExitWebDTO>>> getDevicesByType() {
        Map<String, List<Device>> devices = serviceDeviceByType.getDevicesByType();
        Map<String, List<DeviceIDExitWebDTO>> devicesDTO = DeviceMapper.domainMapToExitWebDTO(devices);

        for (Map.Entry<String, List<DeviceIDExitWebDTO>> entry : devicesDTO.entrySet()) {
            List<DeviceIDExitWebDTO> devicesList = entry.getValue();
            for (DeviceIDExitWebDTO device : devicesList) {
                Link selfLink = linkTo(DeviceControllerWeb.class).slash(device.getId()).withSelfRel();
                device.add(selfLink);
            }
        }
        return new ResponseEntity<>(devicesDTO, HttpStatus.OK);
    }

    /**
     * This method retrieves all devices from the service layer, converts them to DeviceWebDTO objects,
     * adds a self link to each DTO, and returns them in a ResponseEntity.
     * @return ResponseEntity containing a list of DeviceWebDTO objects representing all devices, and an HTTP status of OK.
     */
    private ResponseEntity<List<DeviceIDExitWebDTO>> getAllDevices() {
        Iterable<Device> devices = serviceDevice.getAllDevices();
        List<DeviceIDExitWebDTO> listDeviceWebDTOs = new ArrayList<>();
        for (Device device : devices) {
            DeviceIDExitWebDTO deviceWebDTO = DeviceMapper.domainToIDExitWebDTO(device);
            Link selfLink = linkTo(DeviceControllerWeb.class).slash(deviceWebDTO.getId()).withSelfRel();
            deviceWebDTO.add(selfLink);
            listDeviceWebDTOs.add(deviceWebDTO);
        }
        return new ResponseEntity<>(listDeviceWebDTOs, HttpStatus.OK);
    }

}


