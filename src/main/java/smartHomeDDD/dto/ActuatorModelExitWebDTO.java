package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The ActuatorModelWebDTO class represents a Data Transfer Object (DTO) of type Web for an actuator model.
 */
public class ActuatorModelExitWebDTO extends RepresentationModel<ActuatorModelExitWebDTO> {

    /** The unique identifier of the actuator model. */
    private final String actuatorModelID;

    /**
     * Constructs a new ActuatorModel Web DTO with the specified actuator model information.
     * @param actuatorModelID the unique identifier of the actuator model.
     * @param actuatorTypeID the unique identifier of the actuator type associated with the model.
     */
    public ActuatorModelExitWebDTO(String actuatorModelID, String actuatorTypeID) {
        this.actuatorModelID = actuatorModelID;
    }

    /**
     * Returns the unique identifier of the actuator model.
     * @return the unique identifier of the actuator model.
     */
    public String getActuatorModelID() {
        return actuatorModelID;
    }

}

