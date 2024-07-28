package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The ActuatorTypeWebDTO class represents the DTO that will be used to interact with the web.
 */
public class ActuatorTypeIDExitWebDTO extends RepresentationModel<ActuatorTypeIDExitWebDTO> {

    /**
     * The actuator type ID.
     */
    private final String actuatorTypeID;

    /**
     * Constructor of the class.
     * @param actuatorTypeID The actuator type ID in string format.
     */
    public ActuatorTypeIDExitWebDTO(String actuatorTypeID) {
        this.actuatorTypeID = actuatorTypeID;
    }

    /**
     * Retrieves the actuator type ID.
     */
    public String getActuatorTypeID() {
        return actuatorTypeID;
    }

}
