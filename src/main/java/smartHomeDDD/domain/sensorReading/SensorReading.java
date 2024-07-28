package smartHomeDDD.domain.sensorReading;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;

import java.sql.Timestamp;

/**
 * The SensorReading class represents a reading of a sensor in a given Device Object.
 * It contains a value of a reading, the IDs of the associated device and sensors, and the timestamp of the reading.
 */
public class SensorReading implements AggregateRoot<SensorReadingID>
{
    /**
     * The unique identifier of the SensorReading.
     */
    private final SensorReadingID _sensorReadingID;
    /**
     * The value of the SensorReading.
     */
    private final Reading _reading;
    /**
     * The unique identifier of the device which the SensorReading belongs to.
     */
    private final DeviceId _deviceID;
    /**
     * The unique identifier of the Sensor which the reading belongs to.
     */
    private final SensorID _sensorID;
    /**
     * The timestamp of the SensorReading.
     */
    private final Timestamp _timeStamp;

    /**
     * Constructor for the SensorReading class.
     *
     * @param reading   The value of the sensor reading.
     * @param deviceID  The unique identifier of the device which the log belongs to.
     * @param sensorID  The unique identifier of the Sensor which the reading belongs to.
     * @param timeStamp The timestamp of the sensor reading.
     */
    public SensorReading(SensorReadingID sensorReadingID, Reading reading, DeviceId deviceID, SensorID sensorID, Timestamp timeStamp)
    {
        this._sensorReadingID = sensorReadingID;
        this._reading = reading;
        this._deviceID = deviceID;
        this._sensorID = sensorID;
        this._timeStamp = timeStamp;
    }
    /**
     * Returns the value of the SensorReading.
     *
     * @return the value of the SensorReading.
     */
    public Reading getReading()
    {
        return _reading;
    }

    /**
     * Returns the ID of the device the log belongs to.
     *
     * @return the ID of the device the log belongs to.
     */
    public DeviceId getDeviceID()
    {
        return _deviceID;
    }

    /**
     * Returns the ID of the sensor the log belongs to.
     *
     * @return the ID of the sensor the log belongs to.
     */
    public SensorID getSensorID()
    {
        return _sensorID;
    }

    /**
     * Returns the timestamp of the log.
     *
     * @return the timestamp of the log.
     */
    public Timestamp getTimeStamp()
    {
        return _timeStamp;
    }

    /**
     * Returns the unique identifier of the log.
     *
     * @return the unique identifier of the log.
     */
    @Override
    public SensorReadingID identity()
    {
        return _sensorReadingID;
    }

    /**
     * Compares the ID of the Log object with the ID of the object passed as a parameter.
     *
     * @param object the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;
        if (object instanceof SensorReading sensorReading) {
            if (this._sensorReadingID.equals(sensorReading._sensorReadingID))
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
    public boolean sameAs(Object object)
    {
        if (object instanceof SensorReading sensorReading) {

            if (
                    this._sensorReadingID.equals(sensorReading.identity()) &&
                            this._reading.equals(sensorReading._reading) &&
                            this._deviceID.equals(sensorReading._deviceID) &&
                            this._sensorID.equals(sensorReading._sensorID) &&
                            this._timeStamp.equals(sensorReading._timeStamp))
                return true;
        }

        return false;
    }

    /**
     * Returns the hash code of the sensor reading ID.
     *
     * @return The hash code of the sensor reading ID.
     */
    @Override
    public int hashCode() {
        return _sensorReadingID.hashCode();
    }
}
