package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * Value object representing the unique identifier of an actuator.
 */
public class ActuatorID implements DomainId {
    /**
     * The unique identifier of the actuator.
     */
    private final String actuatorID;

    /**
     * Constructs an ActuatorID object with the specified actuator ID value.
     * @param actuatorID The actuator ID value.
     * @throws IllegalArgumentException If the actuatorID is null or empty.
     */
    public ActuatorID(String actuatorID) throws IllegalArgumentException {
        if (isActuatorIDValid(actuatorID))
            this.actuatorID = actuatorID;
        else
            throw new IllegalArgumentException("actuator ID cannot be null or empty");

    }

    /**
     * Checks if the provided actuator ID is valid.
     * @param actuatorID The actuator ID to validate.
     * @return true if the actuator ID is valid, false otherwise.
     */
    private boolean isActuatorIDValid(String actuatorID) {
        if (actuatorID != null && !actuatorID.isBlank() && !actuatorID.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two ActuatorID objects are considered equal if they have the same actuatorID value.
     * @param object The reference object with which to compare.
     * @return true if this object is the same as the obj argument, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof ActuatorID actuatorID1) {

            return this.actuatorID.equals(actuatorID1.actuatorID);
        }
        return false;
    }

    /**
     * Returns the string representation of the ActuatorID object.
     * @return The actuator ID value as a string.
     */
    @Override
    public String toString() {
        return actuatorID;
    }

    /**
     * Returns the hash code of the actuator ID.
     *
     * @return The hash code of the actuator reading ID.
     */
    @Override
    public int hashCode() {
        return actuatorID.hashCode();
    }
}
