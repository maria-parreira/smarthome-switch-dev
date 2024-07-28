package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.WS8600Value;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The WS8600 class represents a sensor gives the wind speed (km/h) and direction (radians).
 * It implements the SensorValue and AggregateRoot interfaces.
 * It contains information about the sensor's value, ID, device ID, sensor model ID, and a Map,
 * simulating a database with readings from the sensor.
 * Its WS8600Value object represents the current reading, with added cardinal direction, from the sensor.
 */
public class WS8600 implements SensorValue, AggregateRoot<SensorID> {

    /**
     * The Value of the sensor. This is a Value Object.
     */
    private WS8600Value _sensorValue;

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
     * The simulated data readings from the sensor. They are represented as a map
     * where the key corresponds to thE reading number the associated List<Double>
     * contains sensor readings containing wind speed and direction.
     */
    private final Map<Integer, List<Double>> _sensorData;

    /**
     * Constructs a new WS8600 sensor with the given device ID, sensor type ID,
     * sensor model ID, and sensor ID.
     *
     * @param deviceID      The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The model of the sensor.
     * @param sensorID      The unique identifier of the sensor.
     */
    public WS8600(DeviceId deviceID, SensorModelID sensorModelID, SensorID sensorID) {

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
     * Compares the current object with another object of the same class. The result is true if and only if the argument is not null and is a WS8600 object that has the same values for sensor value, sensor ID, device ID, sensor model ID
     * and sensor data.
     *
     * @param object The object to compare this object to.
     * @return True if the objects are the same, otherwise return false.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof WS8600 sensor) {
            return Objects.equals(this._sensorID, sensor._sensorID) &&
                    Objects.equals(this._deviceID, sensor._deviceID) &&
                    Objects.equals(this._sensorModelID, sensor._sensorModelID) &&
                    equals(object);
        }
        return false;
    }

    /**
     * Compares the current object with the object passed as a parameter.
     * Returns true if the objects' IDs are the same, false otherwise.
     *
     * @param object The object to compare with.
     */
    public boolean equals(Object object){
        if (this == object)
            return true;

        if (object instanceof WS8600 ws8600) {
            return this._sensorID.equals(ws8600._sensorID);
        }
        return false;
    }

    /**
     * Sets the simulated data for the WS8600 sensor. The data structure is a Map, where each key represents a unique
     * Integer and is associated with a List of Double values representing sensor readings at the moment.
     * The Map is populated with sample data to initialize the sensor readings.
     *
     * @return A Map<Integer, List<Double>> containing initial sensor readings.
     * The key is identifier Integer, and the associated List<Double> contains readings with wind speed and direction.
     * Example:
     * {
     *     1: [10.5, 0.0],    // key 1 with wind speed 10.5 km/h and direction 0.0 radians
     *     2: [-20.5, 4.714]  // key 2 with wind speed -20.5 km/h and direction 4.714 radians
     * }
     */
    public Map <Integer, List<Double>> setData() {
        return Map.of(1, List.of(10.5,0.0),
                      2, List.of(20.5,4.714));
    }

    /**
     * Sets the value of the sensor to the reading with the given number.
     *
     * @param readingNumber The number of the reading to set the value to.
     */
    public void setValue(Integer readingNumber) {
        this._sensorValue = new WS8600Value(this._sensorData.get(readingNumber).get(0),
                                            this._sensorData.get(readingNumber).get(1));
    }

    /**
     * Retrieves the current value of the WS8600 sensor.
     *
     * @return The current value of the sensor as a Value object.
     */
    @Override
    public Value getValue() {
        return this._sensorValue;
    }

    /**
     * Getter method for the device's ID, associated with this sensor.
     *
     * @return the ID of the device which the sensor belongs to.
     */
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Getter method for the sensor model's ID, associated with this sensor.
     *
     * @return the sensor model ID.
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
