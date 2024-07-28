package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * Value object representing the unique identifier of a device in the Smart Home domain.
 */
public class DeviceId implements DomainId {
    /**
     * The string identifier of the device.
     */
    private final String _id;

    /**
     * Constructs a new DeviceId with the provided string identifier.
     * @param stringId The string identifier of the device.
     * @throws IllegalArgumentException If the provided string identifier is null or empty.
     */
    public DeviceId (String stringId) {
        if (stringId != null && !stringId.isBlank() && !stringId.isEmpty())
            this._id = stringId;
        else
            throw new IllegalArgumentException("DeviceId cannot be null or empty");
    }

    /**
     * Compares this DeviceId with the specified object for equality.
     * @param object the object to compare with
     * @return true if the specified object is equal to this DeviceId, false otherwise
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof DeviceId deviceId) {

            return this._id.equals(deviceId._id);
        }
        return false;
    }

    /**
     * Returns the hash code value for this object. The default implementation
     * of this method returns the hash code of the _id object associated with this object.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode(){
        return this._id.hashCode();
    }

    /**
     * Returns the string representation of this DeviceId.
     *
     * @return the string identifier of this DeviceId
     */
    @Override
    public String toString() {
        return _id;
    }
}
