package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SR001Value;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.util.Map;
import java.util.Objects;

/**
 * The SR001 class represents a sensor that measures solar irradiance.
 * It implements the SensorValue interface and the AggregateRoot interface. The SR001 sensor has a value, a unique identifier,
 * a device ID, a sensor model ID and a map of sensor data.
 * This sensor measures the solar irradiance in watts per square meter.
 */
public class SR001 implements SensorValue, AggregateRoot<SensorID> {

    /**
     * The value of the sensor. This is a Value Object that represents
     * the measurement of the sensor.
     */
    private SR001Value _sensorValue;

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
     * where the key corresponds to the reading number and the value
     * corresponds to the reading value.
     */
    private final Map<Integer, Double> _data;

    /**
     * Constructor for the SR001 sensor.
     *
     * @param sensorID      The unique identifier of the sensor.
     * @param deviceID      The unique identifier of the device
     *                      which the sensor belongs to.
     * @param sensorModelID The model of the sensor.
     */
    public SR001(DeviceId deviceID,
                 SensorModelID sensorModelID,
                 SensorID sensorID) {

        this._sensorID = sensorID;
        this._deviceID = deviceID;
        this._sensorModelID = sensorModelID;
        this._data = setData();
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
     *
     * @param object The object to compare this object to.
     * @return True if the objects are the same, otherwise return false.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof SR001 sensor) {
            return Objects.equals(this._sensorID, sensor._sensorID) &&
                    Objects.equals(this._deviceID, sensor._deviceID) &&
                    Objects.equals(this._sensorModelID, sensor._sensorModelID)&&
                    equals(object);
        }
        return false;
    }

    /**
     * Compares the current object with another object of the same class.
     * The result is true if and only if the argument is not null and is
     * a SR001 object that has the same values for sensor ID.
     *
     * @param object The object to compare this object to.
     * @return True if the objects have the same sensor ID, otherwise return false.
     */
    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object instanceof SR001 sr001) {
            return this._sensorID.equals(sr001._sensorID);
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
    private Map<Integer, Double> setData() {
        return Map.of(1, 0.0, 2, 12345.6, 3, 250.19);
    }

    /**
     * Sets the value of the sensor to the reading with the given number.
     * If the reading number does not exist in the data map, a new SR001Value
     * object is created with a null value.
     *
     * @param keyReading The number of the reading to set the value to.
     * @throws IllegalArgumentException If the value cannot be created.
     */
    public void setValue(Integer keyReading) throws IllegalArgumentException {
        this._sensorValue = new SR001Value(this._data.get(keyReading));
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
     * Returns the ID of the device that the sensor belongs to.
     *
     * @return The ID of the device.
     */
    @Override
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Retrieves the current value of the SR001 sensor.
     *
     * @return The current value of the sensor as a Value object.
     */
    @Override
    public Value getValue() {
        return this._sensorValue;
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
