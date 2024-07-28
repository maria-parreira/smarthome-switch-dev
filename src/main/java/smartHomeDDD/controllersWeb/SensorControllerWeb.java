package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.services.ServiceSensor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST Controller to handle operations related to sensors.
 */
@RestController
@RequestMapping(path = "/api/v1/sensors")
public class SensorControllerWeb {

    /**
     * ServiceSensor instance to handle sensor-related operations.
     */
    private final ServiceSensor serviceSensor;

    /**
     * Constructor to initialize SensorControllerWeb with a ServiceSensor instance.
     *
     * @param serviceSensor An instance of ServiceSensor to handle sensor-related operations.
     */
    public SensorControllerWeb(ServiceSensor serviceSensor) {
        this.serviceSensor = serviceSensor;
    }

    /**
     * Endpoint to add a new sensor to a device.
     *
     * @param sensorWebDTO The DTO (Data Transfer Object) containing sensor information.
     * @return ResponseEntity containing the added sensor information and HTTP status.
     * @throws DataIntegrityViolationException if the sensor already exists along with a CONFLICT status.
     * @throws EntityNotFoundException if the device does not exist along with a BAD_REQUEST status.
     */
    @PostMapping("")
    public ResponseEntity<?> addSensorToDevice(@RequestBody SensorEntryWebDTO sensorWebDTO) {
        try {
            DeviceId deviceID = DeviceMapper.DTOToDeviceId(sensorWebDTO.getDeviceID());
            SensorModelID sensorModelID = SensorModelMapper.DTOToSensorModelId(sensorWebDTO.getSensorModelID());
            Sensor sensorSaved = serviceSensor.createNewSensor(deviceID, sensorModelID);
            SensorExitWebDTO sensorSavedDTO = SensorMapper.convertSensorToExitWebDTO(sensorSaved);

            // Creating self link for the added sensor
            Link selfLink = linkTo(SensorControllerWeb.class).slash(sensorSavedDTO.getSensorID()).withSelfRel();
            sensorSavedDTO.add(selfLink);
            return new ResponseEntity<>(sensorSavedDTO, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to retrieve a sensor by ID.
     * @param id The ID of the sensor to retrieve.
     * @return ResponseEntity containing the SensorWebDTO and HTTP status.
     * @throws EntityNotFoundException if the sensor does not exist along with a NOT_FOUND status.
     */
    @GetMapping("/{sensorID}")
    public ResponseEntity<?> getSensorByID(@PathVariable(value = "sensorID") String id) {
        try {
            SensorID sensorID = SensorMapper.convertToSensorId(id);
            Sensor sensor = serviceSensor.getSensor(sensorID);
            SensorExitWebDTO sensorWebDTO = SensorMapper.convertSensorToExitWebDTO(sensor);

            Link selfLink = linkTo(SensorControllerWeb.class).slash(sensorWebDTO.getSensorID()).withSelfRel();
            sensorWebDTO.add(selfLink);

            return new ResponseEntity<>(sensorWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
