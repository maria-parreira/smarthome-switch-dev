package smartHomeDDD.dto;

/**
 * Data Transfer Object (DTO) for the actuator Model domain object.
 * This class is used to transfer data between different parts of the application.
 */
public class ActuatorModelDTO {


    /**
     * The unique identifier of the actuator model.
     */
    private final String _actuatorModelID;
    /**
     * The unique identifier of the actuator type.
     */
    private final String _actuatorTypeID;

    /**
     * Constructor Method for the creation of an ActuatorModelDTO object.
     *
     * @param actuatorModelID The unique identifier of the actuator model.
     * @param actuatorTypeID  The unique identifier of the actuator type.
     */

    public ActuatorModelDTO(String actuatorModelID, String actuatorTypeID) {
        this._actuatorModelID = actuatorModelID;
        this._actuatorTypeID = actuatorTypeID;
    }


    /**
     * Compares the current ActuatorModelDTO object with another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ActuatorModelDTO actuatorTypeDTO) {
            return this._actuatorModelID.equals(actuatorTypeDTO._actuatorModelID) &&
                    this._actuatorTypeID.equals(actuatorTypeDTO._actuatorTypeID);
        }
        return false;
    }

    /**
     * Returns the hash code value of the ActuatorModelDTO object.
     * @return The hash code value of the ActuatorModelDTO object.
     */
    @Override
    public int hashCode() {
        return _actuatorModelID.hashCode() + _actuatorTypeID.hashCode();
    }
}
