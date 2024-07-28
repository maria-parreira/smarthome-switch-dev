package smartHomeDDD.domain.valueobject;
import smartHomeDDD.ddd.DomainId;

/**
 * The SensorTypeID class represents the unique identifier of a sensor type in the Smart Home domain.
 * It implements the DomainId interface.
 */
public class SensorTypeID implements DomainId{
    /**
     * The unique identifier for the sensor type.
     */
    private final String _sensorTypeID;

    /**
     * Constructs a SensorTypeID object with the specified sensor type ID value.
     * Throws an IllegalArgumentException if the sensor type ID value is null or empty.
     *
     * @param sensorTypeID The sensor type ID value.
     * @throws IllegalArgumentException If the sensor type ID value is null or empty.
     */
    public SensorTypeID(String sensorTypeID) throws IllegalArgumentException {
        if (isSensorTypeIDValid(sensorTypeID))
            this._sensorTypeID = sensorTypeID;
        else
            throw new IllegalArgumentException("sensorType ID cannot be null or empty");
    }

    /**
     * Checks if the provided sensor type ID value is valid.
     *
     * @param sensorTypeID The sensor type ID value to validate.
     * @return true if the sensor type ID value is not null, not empty, and not blank; false otherwise.
     */
    private boolean isSensorTypeIDValid(String sensorTypeID) {
        if (sensorTypeID != null && !sensorTypeID.isBlank() && !sensorTypeID.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Checks if this SensorTypeID object is equal to another object.
     * Two SensorTypeID objects are considered equal if they have the same sensor type ID value.
     *
     * @param object The object to compare with this SensorTypeID.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof SensorTypeID otherSensorTypeID) {

            return this._sensorTypeID.equals(otherSensorTypeID._sensorTypeID);
        }
        return false;
    }

    /**
     * Returns a string representation of the SensorTypeID object.
     *
     * @return The sensor type ID value as a string.
     */
    @Override
    public String toString() {
        return _sensorTypeID;
    }

    /**
     * Returns the hash code of this SensorTypeID.
     *
     * @return The hash code of this SensorTypeID.
     */
    @Override
    public int hashCode() {
        return _sensorTypeID.hashCode();
    }
}
