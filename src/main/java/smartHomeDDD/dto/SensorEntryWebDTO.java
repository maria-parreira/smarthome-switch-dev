package smartHomeDDD.dto;

/**
 * The SensorEntryWebDTO class is a Data Transfer Object (DTO) that represents a sensor in the system.
 * It contains the sensor's ID, model ID and the ID of the device that the sensor is associated with.
 */
public class SensorEntryWebDTO {

    /**
     * The unique identifier of the sensor model.
     */
    private final String _sensorModelID;

    /**
     * The unique identifier of the device that the sensor is associated with.
     */
    private final String _deviceId;

    /**
     * Constructor Method for the creation of an {@link SensorEntryWebDTO} object.
     * @param deviceID The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The unique identifier of the sensor model.
     */
    public SensorEntryWebDTO(String deviceID,String sensorModelID) {
        this._sensorModelID = sensorModelID;
        this._deviceId = deviceID;
    }

    /**
     * Returns the unique identifier of the device that the sensor is associated with.
     *
     * @return the unique identifier of the device
     */
    public String getDeviceID() {
        return _deviceId;
    }

    /**
     * Returns the unique identifier of the sensor model.
     *
     * @return the unique identifier of the sensor model
     */
    public String getSensorModelID() {
        return _sensorModelID;
    }
}


