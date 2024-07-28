package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceActuator;
import smartHomeDDD.services.ServiceSensor;
import smartHomeDDD.services.ServiceSensorReading;


import java.sql.Timestamp;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST Controller to handle operations related to actuators.
 */
@RestController
@RequestMapping(path = "/api/v1/actuators")
public class ActuatorControllerWeb {

    /**
     * ServiceActuator instance used to perform operations related to actuators.
     */
    private final ServiceActuator _serviceActuator;

    /**
     * ServiceSensor instance used to perform operations related to sensors.
     */
    private final ServiceSensor _serviceSensor;

    /**
     * ServiceSensorReading instance used to perform operations related to sensor readings.
     */
    private final ServiceSensorReading _serviceSensorReading;

    /**
     * Constructs a new ActuatorControllerWeb with the provided ServiceActuator, ServiceSensor, and ServiceSensorReading.
     * @param serviceActuator The service to handle operations related to actuators.
     * @param serviceSensor The service to handle operations related to sensors.
     * @param serviceSensorReading The service to handle operations related to sensor readings.
     */
    public ActuatorControllerWeb(ServiceActuator serviceActuator, ServiceSensor serviceSensor, ServiceSensorReading serviceSensorReading) {
        _serviceActuator = serviceActuator;
        _serviceSensor = serviceSensor;
        _serviceSensorReading = serviceSensorReading;
    }

    /**
     * Handles the POST request to add a new actuator to a device.
     * This method receives an ActuatorEntryWebDTO object as a request body, which contains the necessary information to create a new Actuator.
     * @param actuatorEntryWebDTO The ActuatorEntryWebDTO object that contains the information to create a new Actuator.
     * @return A ResponseEntity that contains the ActuatorExitWebDTO of the newly created Actuator and the appropriate HTTP status code.
     * @throws DataIntegrityViolationException if there is a conflict during the creation of the Actuator (e.g., duplicate ActuatorID).
     * @throws EntityNotFoundException if the device for the new actuator is not found.
     */
    @PostMapping("")
    public ResponseEntity<?> addActuatorToDevice(@RequestBody ActuatorEntryWebDTO actuatorEntryWebDTO) {
        try {
            DeviceId deviceID = DeviceMapper.DTOToDeviceId(actuatorEntryWebDTO.getDeviceID());
            ActuatorModelID actuatorModelID = ActuatorModelMapper.convertToActuatorModelID(actuatorEntryWebDTO.getActuatorModelID());

            Actuator actuatorSaved = _serviceActuator.addNewActuator(deviceID, actuatorModelID);
            ActuatorExitWebDTO actuatorDTOSaved = ActuatorMapper.convertToActuatorExitWebDTO(actuatorSaved);

            Link selfLink = linkTo(ActuatorControllerWeb.class).slash(actuatorDTOSaved.getActuatorID()).withSelfRel();
            actuatorDTOSaved.add(selfLink);

            return new ResponseEntity<>(actuatorDTOSaved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT); // ActuatorID already in use.
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Cant find the device.
        }
    }

    /**
     * Handles the PATCH request to update the value of a roller blind.
     * This method receives an actuator ID and an OPNCLEntryDTO object as input,
     * which contains the necessary information to update the roller blind value.
     * @param actuatorID The ID of the actuator.
     * @param entryDTO The OPNCLEntryDTO object that contains the information to close a roller blind.
     * @return A ResponseEntity that contains the ActuatorExitWebDTO of the updated Actuator and the appropriate HTTP status code.
     * @throws EntityNotFoundException if the actuator or sensor is not found.
     */
    @PatchMapping("/{actuatorID}")
    public ResponseEntity<?> updateRollerBlindValue(@PathVariable String actuatorID, @RequestBody OPNCLEntryDTO entryDTO) {
        try {
            ActuatorID actuatorId = ActuatorMapper.convertToActuatorID(entryDTO.getActuatorID());
            SensorID sensorID = SensorMapper.convertToSensorId(entryDTO.getSensorID());
            String inputValue = entryDTO.getInputValue();

            // Get the actuator and sensor, and check if they exist in the database.
            Actuator chosenActuator = _serviceActuator.getActuator(actuatorId);
            Sensor chosenSensor = _serviceSensor.getSensor(sensorID);

            _serviceActuator.areActuadorAndSensorInSameDevice(chosenActuator, chosenSensor);

            // Value creation
            OPNCL0100Value actuatorValue = (OPNCL0100Value) ActuatorMapper.convertToValue(inputValue, chosenActuator.getActuatorModelID().toString());

            // Closes the roller blind if all arguments are valid.
            Actuator actuatorSaved = _serviceActuator.updateRollerBlind(chosenActuator, actuatorValue);

            //New the sensor reading
            Reading reading = SensorReadingMapper.DTOToReading(actuatorValue.toString());
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            _serviceSensorReading.addSensorReading( reading, actuatorSaved.getDeviceID(), sensorID, timeStamp);

            //Create exit DTO
            ActuatorExitWebDTO actuatorDTOSaved = ActuatorMapper.convertToActuatorExitWebDTO(actuatorSaved);

            Link selfLink = linkTo(ActuatorControllerWeb.class).slash(actuatorDTOSaved.getActuatorID()).withSelfRel();
            actuatorDTOSaved.add(selfLink);

            return new ResponseEntity<>(actuatorDTOSaved, HttpStatus.OK); // Success.

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Cant find the actuator or sensor.
        } catch (InstantiationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // Config file related.
        } catch (Exception e) {
            // Sensor and actuator are not in the same device or incorrect models.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles the GET request to retrieve an Actuator by its ID.
     * This method receives an actuator ID as a path variable, which is used to retrieve the corresponding Actuator.
     * @param id The ID of the Actuator to retrieve.
     * @return A ResponseEntity that contains the ActuatorExitWebDTO of the retrieved Actuator and the appropriate HTTP status code.
     * @throws EntityNotFoundException if the Actuator with the given ID is not found.
     */
    @GetMapping("/{actuatorID}")
    public ResponseEntity<?> getActuatorByID(@PathVariable(value = "actuatorID") String id) {
        try {
            ActuatorID actuatorID = ActuatorMapper.convertToActuatorID(id);
            Actuator actuator = _serviceActuator.getActuator(actuatorID);
            ActuatorExitWebDTO actuatorWebDTO = ActuatorMapper.convertToActuatorExitWebDTO(actuator);

            Link selfLink = linkTo(ActuatorControllerWeb.class).slash(actuatorWebDTO.getActuatorID()).withSelfRel();
            actuatorWebDTO.add(selfLink);

            return new ResponseEntity<>(actuatorWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
