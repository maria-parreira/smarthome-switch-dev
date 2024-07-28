package smartHomeDDD.domain.actuatorType;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;

/**
 * This class represents the ActuatorType, comprised of Unit, Description and ActuatorTypeID.
 * It implements the Aggregate root interface.
 */
public class ActuatorType implements AggregateRoot<ActuatorTypeID> {
    /**
     * The corresponding Unit of the ActuatorType.
     */
    private final Unit _unit;
    /**
     * The Description of the ActuatorType, congruent with the existing ActuatorTypes
     */
    private final Description _description;
    /**
     * The Unique Identifier of the ActuatorType.
     */
    private  final ActuatorTypeID _actuatorTypeID;

    /**
     * Constructs an ActuatorType object with the specified unit, description, and identifier.
     * Throws IllegalArgumentException if any of the parameters are null.
     * @param unit           The unit associated with the actuator type.
     * @param description    The description of the actuator type.
     * @param actuatorTypeID The unique identifier for the actuator type.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    public ActuatorType(Unit unit, Description description, ActuatorTypeID actuatorTypeID) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (actuatorTypeID == null) {
            throw new IllegalArgumentException("ActuatorTypeID cannot be null");
        }
        this._unit = unit;
        this._description = description;
        this._actuatorTypeID = actuatorTypeID;
    }

    /**
     * Checks if this ActuatorType is equal to another object.
     * Two ActuatorType objects are considered equal if they have the same actuatorTypeID, unit and description.
     *
     * @param object The object to compare with this ActuatorType.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof ActuatorType actuatorType) {

            return this._actuatorTypeID.equals(actuatorType._actuatorTypeID) &&
                    this._unit.equals(actuatorType._unit) &&
                    this._description.equals(actuatorType._description);
        }

        return false;
    }

    /**
     * Checks if this ActuatorType is equal to another object.
     * Two ActuatorType objects are considered equal if they have the same actuatorTypeID.
     *
     * @param object The object to compare with this ActuatorType.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof ActuatorType actuatorType) {

            return this._actuatorTypeID.equals(actuatorType._actuatorTypeID);
        }
        return false;
    }

    /**
     * Gets the unit associated with the actuator type.
     *
     * @return The unit associated with the actuator type.
     */
    public Unit getUnit() {
        return _unit;
    }

    /**
     * Gets the description of the actuator type.
     *
     * @return The description of the actuator type.
     */
    public Description getDescription() {
        return _description;
    }

    /**
     * Gets the identity of the actuator type, which is the actuatorTypeID.
     *
     * @return The identity of the actuator type.
     */
    @Override
    public ActuatorTypeID identity() {
        return _actuatorTypeID;
    }

    /**
     * Returns the hash code of the actuator type ID.
     *
     * @return The hash code of the actuator type ID.
     */
    @Override
    public int hashCode() {
        return _actuatorTypeID.hashCode();
    }
}
