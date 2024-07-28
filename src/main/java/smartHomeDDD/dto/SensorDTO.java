package smartHomeDDD.dto;
/**
 * The SensorDTO class is a Data Transfer Object (DTO) that represents a sensor in the system.
 * It contains the sensor's ID, model ID and the ID of the device that the sensor is associated with.
 */
public class SensorDTO {

    /**
     * The unique identifier of the sensor.
     */
    private final String _sensorId;

    /**
     * The unique identifier of the sensor model.
     */
    private final String _sensorModelID;

    /**
     * The unique identifier of the device that the sensor is associated with.
     */
    private final String _deviceId;

    /**
     * Constructor Method for the creation of an SensorDTO object, without the model name.
     * @param sensorID The unique identifier of the sensor.
     * @param deviceID The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The unique identifier of the sensor model.
     */
    public SensorDTO(String sensorID, String deviceID,String sensorModelID) {
        this._sensorId = sensorID;
        this._sensorModelID = sensorModelID;
        this._deviceId = deviceID;
    }

    /**
     * Returns the unique identifier of the sensor.
     *
     * @return the unique identifier of the sensor
     */
    public String getSensorID() {
        return _sensorId;
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
