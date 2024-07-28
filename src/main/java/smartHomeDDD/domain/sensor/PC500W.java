package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.PC500WValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The PC500W class represents a sensor that measures power consumption.
 * It implements SensorValueLocalTime, and AggregateRoot interfaces.
 * It contains information about the sensor's value, device ID, sensor type, sensor model, sensor ID, and readings.
 */
public class PC500W implements SensorValueLocalTime, AggregateRoot<SensorID> {

    /**
     * The value of the PC500W sensor.
     */
    private PC500WValue _pc500WValue;
    /**
     * The ID of the device where the sensor is installed.
     */
    private final DeviceId _deviceID;

    /**
     * The model of the sensor.
     */
    private final SensorModelID _sensorModelID;

    /**
     * The ID of the sensor.
     */
    private final SensorID _sensorID;
    /**
     * A map storing the readings of the sensor at different times.
     * The key is the time of the reading, and the value is the reading at that time.
     */
    private final Map<LocalTime, Double> _readings;

    /**
     * Constructs a new PC500W object with the given device ID, sensor type, sensor model, and sensor ID.
     *
     * @param deviceId      the device ID of the sensor
     * @param sensorModelID the model ID of the sensor
     * @param sensorID      the ID of the sensor
     */
    public PC500W(DeviceId deviceId, SensorModelID sensorModelID, SensorID sensorID) {
        this._deviceID = deviceId;
        this._sensorModelID = sensorModelID;
        this._sensorID = sensorID;
        this._readings = setData();
    }

    /**
     * Returns the sensor ID.
     *
     * @return the sensor ID
     */
    @Override
    public SensorID identity() {
        return _sensorID;
    }

    /**
     * Returns the value of the sensor at the given time.
     * If there is no reading at the given time, it returns a default value.
     *
     * @param dateTime the time to get the sensor value
     * @return the sensor value at the given time
     */
    public Value getValue(LocalTime dateTime) {
        Double reading = _readings.get(dateTime);
        if (reading == null) {
            this._pc500WValue = new PC500WValue(0.0); // default value
        } else {
            this._pc500WValue = new PC500WValue(reading);
        }
        return this._pc500WValue;
    }

    /**
     * Checks if the provided object is the same as this PC500W object.
     * The method first checks if the provided object is an instance of PC500W.
     * If it is, the method then checks if the SensorModelID and DeviceID of the provided object are the same as those of this PC500W object.
     * If they are, the method returns true. Otherwise, it returns false.
     *
     * @param object the object to be compared with this PC500W object
     * @return true if the provided object is a PC500W object and its SensorModelID and DeviceID are the same as those of this PC500W object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof PC500W pc500w) {
            if (!equals(object)) {
                return false;
            }
            return Objects.equals(this._sensorID, pc500w._sensorID) &&
                    Objects.equals(this._deviceID, pc500w._deviceID) &&
                    Objects.equals(this._sensorModelID, pc500w._sensorModelID);
        }
        return false;
    }

    /**
     * Compares the current object with the object passed as a parameter.
     * Returns true if the objects' IDs are the same, false otherwise.
     *
     * @param object The object to compare with.
     */
    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object instanceof PC500W pc500w) {
            return this._sensorID.equals(pc500w._sensorID);
        }
        return false;
    }

    /**
     * Sets the initial data for the sensor readings.
     *
     * @return a map of times and sensor readings
     */
    public Map<LocalTime, Double> setData()
    {
        Map<LocalTime, Double> readings = new HashMap<>();
        readings.put(LocalTime.of(3, 0), 100.0);
        readings.put(LocalTime.of(15, 15), 200.0);
        readings.put(LocalTime.of(12, 30), 250.5);
        readings.put(LocalTime.of(18, 2), 300.0);
        return readings;
    }

    /**
     * Getter method for the device's ID, associated with this sensor.
     *
     * @return the unique identifier of this sensor.
     */

    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Getter method for the sensor model's ID, associated with this sensor.
     *
     * @return the unique identifier of this sensor.
     */
    @Override
    public SensorModelID getSensorModelID() {
        return _sensorModelID;
    }

    /**
     * Returns the hash code of the sensor.
     *
     * @return The hash code of the sensor.
     */
    @Override
    public int hashCode() {
        return Objects.hash(_sensorID);
    }
}