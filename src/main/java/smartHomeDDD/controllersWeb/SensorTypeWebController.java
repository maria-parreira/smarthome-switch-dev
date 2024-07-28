package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceSensorType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The SensorTypeWebController class provides web services related to SensorType objects. It contains methods to:
 * - Create a new sensor type.
 * - Get a list of all existing sensor types.
 */
@RestController
@RequestMapping(path = "/api/v1/sensor-types")
public class SensorTypeWebController {

    /**
     * The service that will be used to interact with the domain.
     */
    private final ServiceSensorType serviceSensorType;

    /**
     * Constructor of the class.
     * @param service The service that will be used to interact with the domain.
     */
    public SensorTypeWebController(ServiceSensorType service) {
        this.serviceSensorType = service;
    }

    /**
     * Handles the POST request to create a new SensorType.
     * This method receives a SensorTypeEntryWebDTO object as a request body, which contains the necessary information to create a new SensorType.
     * @param sensorTypeWebDTO The SensorTypeEntryWebDTO object that contains the information to create a new SensorType.
     * @return A ResponseEntity that contains the SensorTypeExitWebDTO of the newly created SensorType and the appropriate HTTP status code.
     * @throws DataIntegrityViolationException if there is a conflict during the creation of the SensorType (e.g., duplicate SensorTypeID).
     */
    @PostMapping("")
    public ResponseEntity<?> defineSensorType(@RequestBody SensorTypeEntryWebDTO sensorTypeWebDTO) {
        try {
            Description description = SensorTypeMapper.createDescription(sensorTypeWebDTO.getDescription());
            Unit unit = SensorTypeMapper.createUnit(sensorTypeWebDTO.getUnit());
            SensorTypeID sensorTypeID = SensorTypeMapper.createSensorTypeID(sensorTypeWebDTO.getSensorTypeID());
            SensorType sensorType = this.serviceSensorType.createSensorType(sensorTypeID, description, unit);

            SensorTypeExitWebDTO sensorTypeDTOSaved = SensorTypeMapper.domainToExitWebDTO(sensorType);
            Link selfLink = linkTo(SensorTypeWebController.class).slash(sensorTypeDTOSaved.getSensorTypeID()).withSelfRel();
            sensorTypeDTOSaved.add(selfLink);

            return new ResponseEntity<>(sensorTypeDTOSaved, HttpStatus.CREATED);
        } catch (
                DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Gets a list of all existing sensor types.
     * @return A list of all existing sensor types.
     */
    @GetMapping("")
    public ResponseEntity<?> getSensorTypes() {
        try {
        Iterable<SensorType> sensorTypeList = serviceSensorType.getSensorTypes();
        List<SensorTypeExitWebDTO> listSensorTypesDTO = new ArrayList<>();
        for (SensorType sensorType : sensorTypeList) {
            SensorTypeExitWebDTO sensorTypeWebDTO = SensorTypeMapper.domainToExitWebDTO(sensorType);

            Link selfLink = linkTo(SensorTypeWebController.class)
                    .slash(sensorTypeWebDTO.getSensorTypeID())
                    .withSelfRel();
            sensorTypeWebDTO.add(selfLink);

            listSensorTypesDTO.add(sensorTypeWebDTO);
        }
        return new ResponseEntity<>(listSensorTypesDTO, HttpStatus.OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles the GET request to retrieve a SensorType by its ID.
     * This method receives a sensor type ID as a path variable, which is used to retrieve the corresponding SensorType.
     * @param id The ID of the SensorType to retrieve.
     * @return A ResponseEntity that contains the SensorTypeExitWebDTO of the retrieved SensorType and the appropriate HTTP status code.
     * @throws EntityNotFoundException if the SensorType with the given ID is not found.
     */
    @GetMapping("/{sensorTypeID}")
    public ResponseEntity<?> getSensorTypeByID(@PathVariable(value = "sensorTypeID") String id) {
        try {
            SensorTypeID sensorTypeID = SensorTypeMapper.createSensorTypeID(id);
            SensorType sensorType = serviceSensorType.getSensorTypeById(sensorTypeID);
            SensorTypeWebDTO sensorTypeWebDTO = SensorTypeMapper.domainToWebDTO(sensorType);

            Link selfLink = linkTo(SensorTypeWebController.class).slash(sensorTypeWebDTO.getSensorTypeID()).withSelfRel();
            sensorTypeWebDTO.add(selfLink);

            return new ResponseEntity<>(sensorTypeWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}