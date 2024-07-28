package smartHomeDDD.dto;

/**
 * A data transfer object (DTO) representing a sensor model.
 */
public class SensorModelDTO {

    /**
     * The ID of the sensor model
     */
    private final String _sensorModelID;

    /**
     * The ID of the sensor type
     */
    private final String _sensorTypeID;


    /**
    * Constructs a new SensorModelDTO with the given sensor model ID and sensor type ID.
    * @param sensorModelID The ID of the sensor model.
    * @param sensorTypeID The ID of the sensor type.
    */
    public SensorModelDTO(String sensorModelID,String sensorTypeID) {
        this._sensorModelID = sensorModelID;
        this._sensorTypeID = sensorTypeID;
    }

    /**
     * Retrieves the sensor model ID.
     * @return The sensor model ID.
     */
    public String getSensorModelID() {
            return _sensorModelID;
        }


    /**
     * Retrieves the sensor type ID.
     * @return The sensor type ID.
     */
    public String getID() {
            return _sensorTypeID;
        }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SensorModelDTO sensorTypeDTO) {
            return this._sensorModelID.equals(sensorTypeDTO._sensorModelID) &&
                    this._sensorTypeID.equals(sensorTypeDTO._sensorTypeID);
        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return _sensorModelID.hashCode() + _sensorTypeID.hashCode();
    }
}
