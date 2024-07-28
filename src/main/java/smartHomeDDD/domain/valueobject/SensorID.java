package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * The SensorID class represents the unique identifier of a sensor in the Smart Home domain.
 * It implements the DomainId interface.
 */
public class SensorID implements DomainId {

    /**
     * The unique identifier for the sensor.
     */
    private final String _sensorID;

    /**
     * Constructs a SensorID object with the specified sensor ID value.
     *
     * @param sensorID The sensor ID value.
     * @throws IllegalArgumentException If the sensorID is null or empty.
     */

    public SensorID(String sensorID) throws IllegalArgumentException {
        if (isSensorIDInvalid(sensorID)) {
            throw new IllegalArgumentException("Sensor ID cannot be null or empty");
        }
        this._sensorID = sensorID;
    }


    /**
     * Checks if the provided sensor ID is invalid.
     *
     * @param sensorID The sensor ID to validate.
     * @return true if the sensor ID is invalid, false otherwise.
     */

    private boolean isSensorIDInvalid(String sensorID) {
        return sensorID == null || sensorID.isBlank() || sensorID.isEmpty();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two SensorID objects are considered equal if they have the same sensorID value.
     *
     * @param object The reference object with which to compare.
     * @return true if this object is the same as the obj argument, false otherwise.
     */

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof SensorID otherSensorID) {
            return this._sensorID.equals(otherSensorID._sensorID);
        }
        return false;
    }

    /**
     * Returns the string representation of the SensorID object.
     *
     * @return The string representation of the SensorID object.
     */

    @Override
    public String toString() {
        return _sensorID;
    }

    /**
     * Returns the hash code of this SensorID object.
     *
     * @return The hash code of this SensorID object.
     */
    @Override
    public int hashCode() {
        return _sensorID.hashCode();
    }
}
