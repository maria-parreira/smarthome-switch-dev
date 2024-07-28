package smartHomeDDD.dto;

/**
 * Data Transfer Object (DTO) for the SensorReading domain object.
 * This class is used to transfer data between different parts of the application.
 */
public class SensorReadingDTO
{
    /**
     * The unique identifier of the sensorReading.
     */
    private final String _sensorReadingID;
    /**
     * The value of the sensorReading.
     */
    private final String _value;
    /**
     * The unique identifier of the device.
     */
    private final String _deviceID;
    /**
     * The unique identifier of the sensor.
     */
    private final String _sensorID;
    /**
     * The timestamp of the sensorReading.
     */
    private final String _timeStamp;

    /**
     * Constructor for the SensorReadingDTO class
     * @param sensorReadingID     The unique identifier of the sensorReading.
     * @param value     The value of the sensorReading.
     * @param deviceID  The unique identifier of the device.
     * @param sensorID  The unique identifier of the sensor.
     * @param timeStamp The timestamp of the sensorReading.
     */

    public SensorReadingDTO(String sensorReadingID, String value, String deviceID, String sensorID, String timeStamp)
    {
        this._sensorReadingID = sensorReadingID;
        this._value = value;
        this._deviceID = deviceID;
        this._sensorID = sensorID;
        this._timeStamp = timeStamp;
    }

    /**
     * Retrieves the unique identifier of the sensorReading.
     */
    public String getSensorReadingID()
    {
        return _sensorReadingID;
    }

    /**
     * Retrieves the value of the sensorReading in String format.
     */
    public String getValue()
    {
        return _value;
    }

    /**
     * Retrieves the unique identifier of the device.
     */
    public String getDeviceID()
    {
        return _deviceID;
    }

    /**
     * Retrieves the unique identifier of the sensor.
     */
    public String getSensorID()
    {
        return _sensorID;
    }

    /**
     * Retrieves the timestamp of the sensorReading in String format.
     */
    public String getTimeStamp()
    {
        return _timeStamp;
    }
}
