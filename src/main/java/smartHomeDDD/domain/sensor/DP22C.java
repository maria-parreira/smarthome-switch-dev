
package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.*;

import java.util.Map;
import java.util.Objects;

/**
 * Represents an DP22C sensor. This sensor measures the dew point.
 * Implements the SensorValue and AggregateRoot interfaces.
 */
public class DP22C implements SensorValue, AggregateRoot<SensorID> {

    /**
     * The Value of the sensor. This is a Value Object.
     */
    private DP22CValue _sensorValue;

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
     * The data readings from the sensor. They are represented as a map where
     * the key corresponds to the reading number and the value corresponds to
     * the reading value.
     */
    private final Map<Integer, Double> _sensorData;


    public DP22C(DeviceId deviceID,
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
     * Compares this DP22C sensor with the specified object for equality.
     * The result is true if and only if the argument is not null and is a DP22C
     * object that has the same values for sensor value, sensor ID, device ID,
     * sensor type ID, sensor model ID, and sensor data.
     *
     * @param object the object to compare this DP22C sensor against.
     * @return true if the given object represents a DP22C sensor equivalent to this sensor, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof DP22C sensor) {
            return Objects.equals(this._sensorID, sensor._sensorID) &&
                    Objects.equals(this._deviceID, sensor._deviceID) &&
                    Objects.equals(this._sensorModelID, sensor._sensorModelID)&&
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
    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object instanceof DP22C dp22c) {
            return this._sensorID.equals(dp22c._sensorID);
        }
        return false;
    }

    /**
     * Sets the data readings from the sensor. They are represented as a map which simulates a database.
     * The key corresponds to the reading number and the value corresponds to the reading value.
     *
     * @return A map containing the readings from the sensor.
     */
    private Map<Integer, Double> setData() {
        return Map.of(1, 30.0, 2, -12.2, 3, 300.1, 4, -275.0);
    }

    /**
     * Sets the value of the sensor to the reading with the given number.
     * If the reading number does not exist in the data map, a new DP22CValue object is created with a null value.
     *
     * @param keyReading The number of the reading to set the value to.
     * @throws IllegalArgumentException If the value cannot be created.
     */
    public void setValue(Integer keyReading)  {
        this._sensorValue = new DP22CValue(this._sensorData.get(keyReading));
    }

    /**
     * Retrieves the current value of the DP22C sensor.
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
