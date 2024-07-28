package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * Value object representing the activation status of a device in the Smart Home domain.
 */
public class ActivationStatus implements ValueObject{
    private final boolean _isActive;

    /**
     * Constructs an ActivationStatus object with the specified activation status.
     *
     * @param isActive the activation status to assign to this ActivationStatus
     */
    public ActivationStatus (boolean isActive) {
            this._isActive = isActive;
    }

    /**
     * Compares this ActivationStatus with the specified object for equality.
     *
     * @param object the object to compare with
     * @return true if the specified object is equal to this ActivationStatus, false otherwise
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof ActivationStatus otherStatus) {
            return this._isActive == otherStatus._isActive; // Compare the _isActive field of both objects
        }
        return false;
    }

    /**
     * Returns the string representation of this ActivationStatus.
     *
     * @return "true" if the device is active, "false" otherwise
     */
    @Override
    public String toString() {
        return String.valueOf(_isActive);
    }

    /**
     * Returns the hash code of this ActivationStatus object.
     *
     * @return the hash code of this ActivationStatus object
     */
    @Override
    public int hashCode() {
        return Boolean.hashCode(_isActive);
    }
}
