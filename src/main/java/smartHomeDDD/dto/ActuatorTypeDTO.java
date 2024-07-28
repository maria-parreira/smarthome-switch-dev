package smartHomeDDD.dto;

public class ActuatorTypeDTO {

    /**
     * The description of the actuator type.
     */
    private final String description;

    /**
     * The unique identifier of the actuator type.
     */
    private final String actuatorTypeID;

    /**
     * Constructs a new ActuatorTypeDTO with the specified actuator type information.
     *
     * @param actuatorTypeID the unique identifier of the actuator type.
     * @param description    the description of the actuator type.
     */
    public ActuatorTypeDTO(String actuatorTypeID, String description) {
        this.actuatorTypeID = actuatorTypeID;
        this.description = description;
    }


    /**
     * Returns the description of the actuator type.
     *
     * @return the description of the actuator type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the unique identifier of the actuator type.
     *
     * @return the unique identifier of the actuator type.
     */
    public String getID() {
        return actuatorTypeID;
    }

    /**
     * Checks if this ActuatorTypeDTO is equal to another object.
     * Two ActuatorTypeDTO objects are considered equal if they have the same actuatorTypeID and description.
     *
     * @param obj The object to compare with this ActuatorTypeDTO.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ActuatorTypeDTO actuatorTypeDTO) {
            return this.actuatorTypeID.equals(actuatorTypeDTO.actuatorTypeID) &&
                    this.description.equals(actuatorTypeDTO.description);


        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return actuatorTypeID.hashCode();
    }
}
