package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;

import smartHomeDDD.domain.valueobject.CAP200Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.util.Map;
import java.util.Objects;

/**
 * The CAP200 class represents the CAP200 sensor. It measures the capacity in percentage.
 * It is an Aggregate Root that represents the sensor CAP200 in the smart home system.
 */
public class CAP200 implements SensorValue, AggregateRoot<SensorID> {

    /**
     * The value of the sensor. This is a Value Object that represents
     * the measurement of the sensor.
     */
    private CAP200Value _sensorValue;

    /**
     * The unique identifier of the sensor.
     */
    private final SensorID _sensorID;

    /**
     * The unique identifier of the device which the sensor belongs to.
     */
    private final DeviceId _deviceID;

    /**
     * The model of the actuator.
     */
    private final SensorModelID _sensorModelID;

    /**
     * The data readings from the sensor. They are represented as a map
     * where the key corresponds to the reading number and the value corresponds
     * to the reading value.
     */
    private final Map<Integer, Integer> _sensorData;

    /**
     * Constructs a new instance of CAP200 with the specified device ID, sensor model ID
     * and sensor ID.
     *
     * @param deviceID      The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The model of the sensor.
     * @param sensorID      The unique identifier of the sensor.
     */
    public CAP200(DeviceId deviceID,
                  SensorModelID sensorModelID,
                  SensorID sensorID) {

        this._sensorID = sensorID;
        this._deviceID = deviceID;
        this._sensorModelID = sensorModelID;
        this._sensorData = setData();
    }

    /**
     * Getter method for the sensor's ID.
     *
     * @return the unique identifier of this sensor.
     */
    @Override
    public SensorID identity() {
        return _sensorID;
    }

    /**
     * Compares the current object with another object of the same class.
     * The result is true if and only if the argument is not null and is
     * a CAP200 object that has the same values for sensor value, sensor ID,
     * device ID, sensor model ID and sensor data.
     *
     * @param object the object to compare this object to.
     * @return true if the objects are the same, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof CAP200 sensor) {
            return Objects.equals(this._sensorID, sensor._sensorID) &&
                    Objects.equals(this._deviceID, sensor._deviceID) &&
                    Objects.equals(this._sensorModelID, sensor._sensorModelID) &&
                    equals(object);
        }
        return false;
    }

    /**
     * Compares the current object with another object of the same class.
     * The result is true if and only if the argument is not null and is
     * a CAP200 object that has the same values for sensor ID.
     *
     * @param object the object to compare this object to.
     * @return true if the objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof CAP200 cap200) {
            return this._sensorID.equals(cap200._sensorID);
        }
        return false;
    }

    /**
     * Sets the data readings from the sensor. They are represented as a map
     * which simulates a database. The key corresponds to the reading number
     * and the value corresponds to the reading value.
     *
     * @return A map containing the readings from the sensor.
     */
    private Map<Integer, Integer> setData() {
        return Map.of(1, 1, 2, 0, 3, 100);
    }

    /**
     * Sets the value of the sensor to the reading with the given number.
     * If the reading number does not exist in the data map, a new CAP200Value
     * object is created with a null value.
     *
     * @param keyReading The number of the reading to set the value to.
     * @throws IllegalArgumentException If the value cannot be created.
     */
    public void setValue(Integer keyReading) {
        this._sensorValue = new CAP200Value(this._sensorData.get(keyReading));
    }

    /**
     * Retrieves the current value of the CAP200 sensor.
     *
     * @return The current value of the sensor as a Value object.
     */
    @Override
    public Value getValue() {
        return this._sensorValue;
    }

    /**
     * Returns the ID of the device that the sensor belongs to.
     *
     * @return The ID of the device.
     */
    @Override
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Retrieves the model ID of the sensor's model.
     *
     * @return The model ID of the sensor.
     */
    @Override
    public SensorModelID getSensorModelID() {
        return _sensorModelID;
    }

    /**
     * Returns the hash code of the sensor ID.
     *
     * @return The hash code of the sensor ID.
     */
    @Override
    public int hashCode() {
        return _sensorID.hashCode();
    }
}
