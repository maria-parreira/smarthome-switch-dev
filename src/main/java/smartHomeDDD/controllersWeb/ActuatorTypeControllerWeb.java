package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceActuatorType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The ActuatorTypeControllerWeb class provides web services related to ActuatorType objects.
 */
@RestController
@RequestMapping(path = "/api/v1/actuator-types")
public class ActuatorTypeControllerWeb {

    /**
     * The service that will be used to interact with the domain.
     */

    private final ServiceActuatorType serviceActuatorType;

    /**
     * Constructor of the class.
     *
     * @param serviceActuatorType The service that will be used to interact with the domain.
     */
    public ActuatorTypeControllerWeb(ServiceActuatorType serviceActuatorType) {
        this.serviceActuatorType = serviceActuatorType;
    }

    /**
     * Gets a list of actuator types.
     * @return The list of existing actuator types.
     */
    @GetMapping(path = "")
    public ResponseEntity<List<ActuatorTypeIDExitWebDTO>> getActuatorTypes() {
        Iterable<ActuatorType> actuatorTypeList = serviceActuatorType.getActuatorTypes();
        List<ActuatorTypeIDExitWebDTO> listActuatorTypesDTO = new ArrayList<>();
        for (ActuatorType actuatorType : actuatorTypeList) {
            ActuatorTypeIDExitWebDTO actuatorTypeWebDTO = ActuatorTypeMapper.domainToIDExitWebDTO(actuatorType);

            Link selfLink = linkTo(ActuatorTypeControllerWeb.class)
                    .slash(actuatorTypeWebDTO.getActuatorTypeID())
                    .withSelfRel();
            actuatorTypeWebDTO.add(selfLink);

            listActuatorTypesDTO.add(actuatorTypeWebDTO);
        }
        return new ResponseEntity<>(listActuatorTypesDTO, HttpStatus.OK);
    }

    /**
     * Gets an actuator type by its unique identifier.
     *
     * @param id The unique identifier of the actuator type.
     * @return The actuator type associated with the unique identifier.
     */
    @GetMapping("/{actuatorTypeID}")
    public ResponseEntity<?> getActuatorTypeByID(@PathVariable(value = "actuatorTypeID") String id) {
        try {
            ActuatorTypeID actuatorModelID = ActuatorTypeMapper.DTOToActuatorTypeID(id);
            ActuatorType actuatorType = serviceActuatorType.getActuatorTypeById(actuatorModelID);
            ActuatorTypeExitWebDTO actuatorTypeWebDTO = ActuatorTypeMapper.domainToExitWebDTO(actuatorType);

            Link selfLink = linkTo(ActuatorTypeControllerWeb.class).slash(actuatorTypeWebDTO.getActuatorTypeID()).withSelfRel();
            actuatorTypeWebDTO.add(selfLink);

            return new ResponseEntity<>(actuatorTypeWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
