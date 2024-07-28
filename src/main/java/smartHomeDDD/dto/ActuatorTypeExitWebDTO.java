package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The ActuatorTypeWebDTO class represents the DTO that will be used to interact with the web.
 */
public class ActuatorTypeExitWebDTO extends RepresentationModel<ActuatorTypeExitWebDTO> {

    /**
     * The actuator type ID.
     */
    private final String actuatorTypeID;

    /**
     * The description of the actuator type.
     */
    private final String description;

    /**
     * The unit of the actuator type.
     */
    private final String unit;

    /**
     * Constructor of the class.
     * @param actuatorTypeID The actuator type ID in string format.
     * @param description The description of the actuator type.
     * @param unit The unit of the actuator type.
     */
    public ActuatorTypeExitWebDTO(String actuatorTypeID, String description, String unit) {
        this.actuatorTypeID = actuatorTypeID;
        this.description = description;
        this.unit = unit;
    }

    /**
     * Retrieves the actuator type ID.
     */
    public String getActuatorTypeID() {
        return actuatorTypeID;
    }

    /**
     * Retrieves the description of the actuator type.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Retrieves the unit of the actuator type.
     */
    public String getUnit() {
        return unit;
    }

}

