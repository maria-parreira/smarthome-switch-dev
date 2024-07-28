package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * SensorReadingExitWebDTO is a DTO (Data Transfer Object) class that represents a SensorReading object in a format
 * that can be used by the web layer.
 */
public class SensorReadingExitWebDTO extends RepresentationModel<SensorReadingExitWebDTO> {
    /**
     * The identity of the sensorReading.
     */
    private final String _sensorReadingID;

    /**
     * The reading of the sensorReading.
     */
    private final String _reading;

    /**
     * The deviceID of the sensorReading.
     */
    private final String _deviceID;

    /**
     * The sensorID of the sensorReading.
     */
    private final String _sensorID;

    /**
     * The timeStamp of the sensorReading.
     */
    private final String _timeStamp;

    /**
     * Constructor for the SensorReadingExitWebDTO class.
     * @param sensorReadingID The identity of the sensorReading.
     * @param reading The reading of the sensorReading.
     * @param deviceID The deviceID of the sensorReading.
     * @param sensorID The sensorID of the sensorReading.
     * @param timeStamp The timeStamp of the sensorReading.
     */
    public SensorReadingExitWebDTO(String sensorReadingID, String reading, String deviceID, String sensorID, String timeStamp) {
        this._sensorReadingID = sensorReadingID;
        this._reading = reading;
        this._deviceID = deviceID;
        this._sensorID = sensorID;
        this._timeStamp = timeStamp;
    }

    /**
     * Gets the unique identifier of the Sensor Reading.
     * @return The sensorReadingID.
     */
    public String getSensorReadingID() {
        return _sensorReadingID;
    }

    /**
     * Gets the value of the Sensor Reading.
     * @return The reading.
     */
    public String getReading() {
        return _reading;
    }

    /**
     * Gets the deviceID associated with the Sensor Reading.
     * @return The deviceID.
     */
    public String getDeviceID() {
        return _deviceID;
    }

    /**
     * Gets the sensorID associated with the Sensor Reading.
     * @return The sensorID.
     */
    public String getSensorID() {
        return _sensorID;
    }

    /**
     * Gets the timeStamp of the Sensor Reading.
     * @return The timeStamp.
     */
    public String getTimeStamp() {
        return _timeStamp;
    }
}
