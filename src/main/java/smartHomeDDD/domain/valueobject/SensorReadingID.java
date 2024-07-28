package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * The LogID class represents the unique identifier of a log object.
 */
public class SensorReadingID implements DomainId
{
    /**
     * The unique identifier of the log.
     */
    private final String _sensorReadingID;

    /**
     * Constructor for the LogID class.
     *
     * @param sensorReadingID The unique identifier of the log.
     */
    public SensorReadingID(String sensorReadingID)
    {
        if (!isSensorReadingIDValid(sensorReadingID)) {
            throw new IllegalArgumentException("Log ID cannot be null or empty");
        }
        this._sensorReadingID = sensorReadingID;
    }

    /**
     * Checks if the log ID is valid.
     *
     * @param sensorReadingID the log ID to validate.
     * @return true if the log ID is valid, false otherwise.
     */
    private boolean isSensorReadingIDValid(String sensorReadingID)
    {
        if (sensorReadingID != null && !sensorReadingID.isBlank() && !sensorReadingID.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SensorReadingID sensorReadingID)) {
            return false;
        }
        return this._sensorReadingID.equals(sensorReadingID._sensorReadingID);
    }

    /**
     * Returns the value of the log in String format.
     *
     * @return the value of the log in String format.
     */
    @Override
    public String toString()
    {
        return _sensorReadingID;
    }

    /**
     * Returns the hash code of this LogID.
     *
     * @return The hash code of this LogID.
     */
    @Override
    public int hashCode() {
        return _sensorReadingID.hashCode();
    }
}
