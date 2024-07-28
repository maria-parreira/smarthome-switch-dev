package smartHomeDDD.domain.sensorType;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;

/**
 * The sensorType class represents a specific type of a sensor in the Smart Home domain.
 * It implements the AggregateRoot interface with SensorTypeID as the type of its identity.
 */

public class SensorType implements AggregateRoot<SensorTypeID> {
    /**
     * The unit associated with the sensor type.
     */
    private final Unit unit;
    /**
     * The description of the sensor type.
     */
    private final Description description;
    /**
     * The unique identifier for the sensor type.
     */
    private final SensorTypeID sensorTypeID;

    /**
     * Constructs an sensorType object with the specified unit, description, and identifier.
     * Throws IllegalArgumentException if any of the parameters are null.
     *
     * @param unit         The unit associated with the sensor type.
     * @param description  The description of the sensor type.
     * @param sensorTypeID The unique identifier for the sensor type.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    public SensorType(Unit unit, Description description, SensorTypeID sensorTypeID)
    {
        if (unit == null) {
            throw new IllegalArgumentException("Parameter 'unit' cannot be null.");
        }
        if (description == null) {
            throw new IllegalArgumentException("Parameter 'description' cannot be null.");
        }
        if (sensorTypeID == null) {
            throw new IllegalArgumentException("Parameter 'id' cannot be null.");
        }

        this.unit = unit;
        this.description = description;
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Checks if the provided object is the same as this sensorType object.
     * The method first checks if the provided object is an instance of sensorType.
     * If it is, the method then checks if the sensorTypeID, unit, and description of the provided object are the same as those of this sensorType object.
     * If they are, the method returns true. Otherwise, it returns false.
     *
     * @param object the object to be compared with this sensorType object
     * @return true if the provided object is a sensorType object and its sensorTypeID, unit, and description are the same as those of this sensorType object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof SensorType sensorType) {
            return this.sensorTypeID.equals(sensorType.sensorTypeID) &&
                    this.unit.equals(sensorType.unit) &&
                    this.description.equals(sensorType.description);
        }

        return false;
    }

    /**
     * Checks if this sensorType is equal to another object.
     * Two sensorType objects are considered equal if they have the same sensorTypeID.
     *
     * @param object The object to compare with this sensorType.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof SensorType sensorType) {

            return this.sensorTypeID.equals(sensorType.sensorTypeID);
        }
        return false;
    }


    /**
     * Gets the unit associated with the sensor type.
     *
     * @return The unit associated with the sensor type.
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Gets the description of the sensor type.
     *
     * @return The description of the sensor type.
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Gets the identity of the sensor type, which is the sensorTypeID.
     *
     * @return The identity of the sensor type.
     */
    @Override
    public SensorTypeID identity() {
        return sensorTypeID;
    }

    /**
     * Returns the hash code of the sensor type's sensorTypeID.
     *
     * @return The hash code of the sensor type's sensorTypeID.
     */
    @Override
    public int hashCode() {
        return sensorTypeID.hashCode();
    }
}
