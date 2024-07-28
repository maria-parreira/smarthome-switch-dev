package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.TSY01Value;

import java.util.Map;
import java.util.Objects;

/**
 * This class represents a TSY01 sensor.
 * This sensor measures humidity.
 * It implements the SensorValue interface and the AggregateRoot interface.
 */
public class TSY01 implements SensorValue, AggregateRoot<SensorID> {

    /**
     * The unique identifier of the sensor.
     */
    private final SensorID _sensorID;

    /**
     *  The unique identifier of the device which the sensor belongs to.
     */
    private final DeviceId _deviceID;

    /**
     * The unique identifier of the sensor model.
     */
    private final SensorModelID _sensorModelID;

    /**
     * The value of the sensor.
     */
    private TSY01Value _value;

    /**
     * The data of the sensor.
     */
    private final Map<Integer, Integer> _data;

    /**
     * Constructor for the TSY01 sensor.
     *
     * @param sensorID      The unique identifier of the sensor.
     * @param deviceID      The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The model of the sensor.
     */
    public TSY01(DeviceId deviceID, SensorModelID sensorModelID, SensorID sensorID) {

        this._sensorID = sensorID;
        this._deviceID = deviceID;
        this._sensorModelID = sensorModelID;
        this._data = setData();
    }

    /**
     * Returns the unique identifier of the sensor.
     */
    @Override
    public SensorID identity() {
        return _sensorID;
    }

    /**
     * Compares the current object with the object passed as a parameter.
     * Returns true if the objects are the same, false otherwise.
     *
     * @param object The object to compare with.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof TSY01 tsy01) {
            return Objects.equals(this._sensorID, tsy01._sensorID) &&
                    Objects.equals(this._deviceID, tsy01._deviceID) &&
                    Objects.equals(this._sensorModelID, tsy01._sensorModelID)&&
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
        if (object instanceof TSY01 tsy01) {
            return this._sensorID.equals(tsy01._sensorID);
        }
        return false;
    }

    /**
     * Returns the unique identifier of the device which the sensor belongs to.
     */
    @Override
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Returns the unique identifier of the sensor model.
     */
    @Override
    public SensorModelID getSensorModelID() {
        return _sensorModelID;
    }

    /**
     * Returns the value of the sensor.
     */
    @Override
    public Value getValue() {
        return _value;
    }

    /**
     * Connects the value of the sensor to a specific reading.
     *
     * @param readingID The unique identifier of the reading.
     */
    public void setValue(Integer readingID) {
        this._value = new TSY01Value(this._data.get(readingID));
    }

    /**
     * Sets the data readings from the sensor.
     * They are represented as a map which simulates a database.
     * The key corresponds to the reading number and the value
     * corresponds to the reading value.
     * @return A map containing the readings from the sensor.
     */
    public Map <Integer, Integer> setData()
    {
        return Map.of(1,  15, 2, 85, 3, 65);
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
