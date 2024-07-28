package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ONF01SValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.util.Map;
import java.util.Objects;

/**
 * The ONF01S class represents an ON/OFF status sensor in a smart home system.
 * It implements the SensorValue and AggregateRoot interfaces.
 * It contains information about the sensor's value, ID, device ID, sensor model ID, and a Map,
 * simulating a database with readings from the sensor.
 * Its ONF01SValue object represents the current reading from the sensor.
 */
public class ONF01S implements SensorValue, AggregateRoot<SensorID> {

    /**
     * The Value of the sensor. This is a Value Object.
     */
    private ONF01SValue _sensorValue;

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
     * The simulated data readings from the sensor. They are represented as a map where the key corresponds to the
     * reading number and the value corresponds to the reading value (ON/OFF).
     */
    private final Map<Integer, String> _sensorData;

    /**
     * Constructs a new ONF01S sensor with the given device ID, sensor type ID, sensor model ID, and sensor ID.
     *
     * @param deviceID      The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The model of the sensor.
     * @param sensorID      The unique identifier of the sensor.
     */
    public ONF01S(DeviceId deviceID, SensorModelID sensorModelID, SensorID sensorID) {

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
     * Compares the current object with another object of the same class. The result is true if and only if the argument
     * is not null and is a ONF01S object that has the same values for sensor value, sensor ID, device ID, sensor model ID
     * and sensor data.
     *
     * @param object The object to compare this object to.
     * @return True if the objects are the same, otherwise return false.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof ONF01S sensor) {
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
    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object instanceof ONF01S onf01s) {
            return this._sensorID.equals(onf01s._sensorID);
        }
        return false;
    }

    /**
     * Sets the data readings from the sensor. They are represented as a map which simulates a database.
     * The key corresponds to the reading number and the value corresponds to the reading value.
     *
     * @return A map containing the readings from the sensor.
     */
    private Map<Integer, String> setData() {
        return Map.of(1, "ON",
                      2, "OFF",
                      3, "OFF",
                      4, "ON");
    }

    /**
     * Sets the value of the sensor to the reading with the given number.
     *
     * @param readingNumber The number of the reading to set the value to.
     */
    public void setValue(Integer readingNumber) {
        this._sensorValue = new ONF01SValue(this._sensorData.get(readingNumber));
    }

    /**
     * Retrieves the current value of the ONF01S sensor.
     *
     * @return The current value of the sensor as a Value object.
     */
    @Override
    public Value getValue() {
        return _sensorValue;
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
