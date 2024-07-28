package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * This class represents the ID of an actuator type.
 * It implements the DomainID interface.
 */
public class ActuatorTypeID implements DomainId {


    /**
     * The string value of the actuator type ID.
     */
    private final String _actuatorTypeID;

    /**
     * Constructs a new ActuatorTypeID with the specified ID.
     * @param actuatorTypeID the ID of the actuator type.
     * @throws IllegalArgumentException if the provided ID is null or empty.
     */
    public ActuatorTypeID(String actuatorTypeID)  {
        if (isActuatorTypeIDValid(actuatorTypeID))
            this._actuatorTypeID = actuatorTypeID;
        else
            throw new IllegalArgumentException("actuator Type ID cannot be null or empty");

    }

    /**
     * Checks if the provided actuator type ID is valid.
     * @param actuatorTypeID the actuator type ID to validate.
     * @return true if the actuator type ID is valid, false otherwise.
     */
    private boolean isActuatorTypeIDValid(String actuatorTypeID) {
        return actuatorTypeID != null && !actuatorTypeID.isBlank() && !actuatorTypeID.isEmpty();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param object the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof ActuatorTypeID actuatorTypeID1) {

            return this._actuatorTypeID.equals(actuatorTypeID1._actuatorTypeID);
        }
        return false;
    }

    /**
     * Returns the string representation of the actuator type ID.
     * @return the string representation of the actuator type ID.
     */
    @Override
    public String toString() {
        return _actuatorTypeID;
    }

    /**
     * Returns the hash code value of the actuator type ID.
     * @return the hash code value of the actuator type ID.
     */
    @Override
    public int hashCode() {
        return _actuatorTypeID.hashCode();
    }
}
