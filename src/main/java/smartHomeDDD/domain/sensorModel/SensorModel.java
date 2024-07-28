package smartHomeDDD.domain.sensorModel;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;

/**
 * The sensorModel class represents a specific model of a sensor in the Smart Home domain.
 * It implements the AggregateRoot interface with SensorModelID as the type of its identity.
 */

public class SensorModel implements AggregateRoot<SensorModelID> {

    /**
     * The unique identifier for the sensor model, derived from an existing list of models.
     */
    private final SensorModelID _sensorModelID;
    /**
     * The unique identifier of the type of sensor associated with this model.
     */
    private final SensorTypeID _sensorTypeID;


    /**
     * Constructs an sensorModel object with the specified model name and ID.
     * Throws an IllegalArgumentException if any of the specified parameters are null.
     *
     * @param sensorModelID The unique identifier for the sensor model.
     * @param sensorTypeID  The unique identifier of the type of sensor associated with this model.
     * @throws IllegalArgumentException exception thrown if any of the parameters are null.
     */
    public SensorModel(SensorModelID sensorModelID, SensorTypeID sensorTypeID)
    {
        if (sensorModelID == null) {
            throw new IllegalArgumentException("Parameter 'sensorModelID' cannot be null.");
        }
        if (sensorTypeID == null) {
            throw new IllegalArgumentException("Parameter 'sensorTypeID' cannot be null.");
        }
        this._sensorModelID = sensorModelID;
        this._sensorTypeID = sensorTypeID;
    }


    /**
     * Returns the unique identifier for the sensor model.
     *
     * @return The unique identifier for the sensor model.
     */
    @Override
    public SensorModelID identity() {
        return this._sensorModelID;
    }

    /**
     * Determines whether this sensor model is the same as the specified object.
     * The two objects are considered the same if they are both sensorModel objects and their model names and IDs are equal.
     *
     * @param object The object to compare with this sensor model.
     * @return true if this sensor model is the same as the specified object; false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof SensorModel model) {
            if (!equals(object)) {
                return false;
            }
            return this._sensorModelID.equals(model._sensorModelID) &&
                    this._sensorTypeID.equals(model._sensorTypeID);
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof SensorModel model) {
            return this._sensorModelID.equals(model._sensorModelID);
        }
        return false;
    }

    /**
     * Returns the unique identifier of the type of sensor associated with this model.
     *
     * @return the unique identifier of the type of sensor associated with this model.
     */
    public SensorTypeID getSensorTypeID() {
        return this._sensorTypeID;
    }

    /**
     * Returns the hash code of the sensor model ID.
     *
     * @return The hash code of the sensor model ID.
     */
    @Override
    public int hashCode() {
        return _sensorModelID.hashCode();
    }
}
