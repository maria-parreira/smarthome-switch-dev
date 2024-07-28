package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceSensorModel;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The SensorModelWebController class provides web services related to SensorModel objects.
 */
@RestController
@RequestMapping(path = "/api/v1/sensor-models")
public class SensorModelWebController {

    /**
     * The service that will be used to interact with the domain.
     */
    private final ServiceSensorModel _serviceSensorModel;

    /**
     * Constructor of the class.
     * @param service The service that will be used to interact with the domain.
     */
    public SensorModelWebController(ServiceSensorModel service) {
        this._serviceSensorModel = service;
    }

    /**
     * This method is responsible for fetching all sensor models by a specific sensor type.
     * It receives a sensor type ID as a request parameter, which is used to fetch the sensor models from the service layer.
     * The method maps the domain objects to SensorModelWebDTO objects, which are then returned in the response.
     * Each SensorModelWebDTO object also includes a self link for HATEOAS compliance.
     * If the operation is successful, it returns a list of SensorModelWebDTO objects with an OK (200) HTTP status.
     *
     * @param id The unique identifier for the sensor type.
     * @return A list of SensorModelWebDTO objects.
     */
    @GetMapping("")
    public ResponseEntity<List<SensorModelExitWebDTO>> getSensorModelsByType(@RequestParam("sensorTypeID") String id) {
        SensorTypeID sensorTypeID = SensorTypeMapper.createSensorTypeID(id);

        List<SensorModel> sensorModels = _serviceSensorModel.getModelsBySensorType(sensorTypeID);

        List<SensorModelExitWebDTO> sensorModelWebDTOs = new ArrayList<>();

        for (SensorModel model : sensorModels) {
            SensorModelExitWebDTO webDTO = SensorModelMapper.domainToExitWebDTO(model);
            Link selfLink = linkTo(SensorModelWebController.class).slash(model.identity()).withSelfRel();
            webDTO.add(selfLink);
            sensorModelWebDTOs.add(webDTO);
        }

        return new ResponseEntity<>(sensorModelWebDTOs, HttpStatus.OK);
    }

    /**
     * This method is responsible for fetching a sensor model by its unique identifier.
     * @param id The unique identifier of the sensor model.
     * @return The sensor model associated with the unique identifier.
     */
    @GetMapping("/{sensorModelID}")
    public ResponseEntity<?> getSensorModelByID(@PathVariable(value = "sensorModelID") String id) {
        try {
            SensorModelID sensorModelID = SensorModelMapper.DTOToSensorModelId(id);
            SensorModel sensorModel = _serviceSensorModel.getSensorModelByID(sensorModelID);
            SensorModelExitWebDTO sensorModelWebDTO = SensorModelMapper.domainToExitWebDTO(sensorModel);

            Link selfLink = linkTo(SensorModelWebController.class).slash(sensorModelWebDTO.getSensorModelID()).withSelfRel();
            sensorModelWebDTO.add(selfLink);

            return new ResponseEntity<>(sensorModelWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
