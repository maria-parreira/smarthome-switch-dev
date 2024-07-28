package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceActuatorModel;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The ActuatorModelWebController class provides web services related to ActuatorModel objects.
 */
@RestController
@RequestMapping("/api/v1/actuator-models")
public class ActuatorModelWebController {

    /**
     * The service for actuator models.
     */
    private final ServiceActuatorModel serviceActuatorModel;

    /**
     * Constructor for the ActuatorModelWebController class.
     * @param serviceActuatorModel The service for actuator models.
     */
    public ActuatorModelWebController(ServiceActuatorModel serviceActuatorModel) {
        this.serviceActuatorModel = serviceActuatorModel;
    }

    /**
     * Retrieves all actuator models associated with a given actuator type.
     * @param ID The unique identifier of the actuator type.
     * @return A list of actuator models associated with the actuator type.
     */
    @GetMapping("")
    public ResponseEntity<List<ActuatorModelExitWebDTO>> getActuatorModelsByType(@RequestParam("actuatorTypeID") String ID) {
        ActuatorTypeID actuatorTypeID = ActuatorTypeMapper.DTOToActuatorTypeID(ID);
        List<ActuatorModel> actuatorModels = serviceActuatorModel.getModelsByActuatorType(actuatorTypeID);

        List<ActuatorModelExitWebDTO> actuatorModelExitWebDTOs = new ArrayList<>();

        for (ActuatorModel model : actuatorModels) {
            ActuatorModelExitWebDTO webDTO = ActuatorModelMapper.domainToExitWebDTO(model);
            Link selfLink = linkTo(ActuatorModelWebController.class).slash(model.identity()).withSelfRel();
            webDTO.add(selfLink);
            actuatorModelExitWebDTOs.add(webDTO);
        }

        return new ResponseEntity<>(actuatorModelExitWebDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves an actuator model by its unique identifier.
     * @param id The unique identifier of the actuator model.
     * @return The actuator model associated with the unique identifier.
     */
    @GetMapping("/{actuatorModelID}")
    public ResponseEntity<?> getActuatorModelByID(@PathVariable(value = "actuatorModelID") String id) {
        try {
            ActuatorModelID actuatorModelID = ActuatorModelMapper.convertToActuatorModelID(id);
            ActuatorModel actuatorModel = serviceActuatorModel.getActuatorModelByID(actuatorModelID);
            ActuatorModelExitWebDTO actuatorModelWebDTO = ActuatorModelMapper.domainToExitWebDTO(actuatorModel);

            Link selfLink = linkTo(ActuatorModelWebController.class).slash(actuatorModelWebDTO.getActuatorModelID()).withSelfRel();
            actuatorModelWebDTO.add(selfLink);

            return new ResponseEntity<>(actuatorModelWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}