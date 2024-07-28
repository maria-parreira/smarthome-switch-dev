package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;



/**
 * A class representing a unit value.
 * Implements the ValueObject interface.
 */
public class Unit implements ValueObject {

    /**
     * The unit value.
     *
     */
    private final String _unit;

    /**
     * Constructs a new Unit object with the specified unit value.
     * Throws an IllegalArgumentException if the unit value is null or empty.
     * @param unit The unit value to set.
     * @throws IllegalArgumentException If the unit value is null or empty.
     */
    public Unit(String unit) {
        if (isUnitValid(unit))
            this._unit = unit;
        else
            throw new IllegalArgumentException("Unit cannot be null or empty");

    }

    /**
     * Checks if the provided unit value is valid.
     * @param unit The unit value to validate.
     * @return true if the unit value is not null, not empty, and not blank; false otherwise.
     */
    private boolean isUnitValid(String unit) {
        return unit != null && !unit.isBlank() && !unit.isEmpty();
    }


    /**
     * Checks if this Unit object is equal to another object.
     * Two Unit objects are considered equal if they have the same unit value.
     * @param object The object to compare with this Unit.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof Unit unit1) {

            return this._unit.equals(unit1._unit);
        }
        return false;
    }

    /**
     * Returns a string representation of the Unit object.
     * @return The unit value as a string.
     */
    @Override
    public String toString() {
        return _unit;
    }

    /**
     * Returns the hash code value for this Unit object.
     * @return The hash code value for this Unit object.
     */
    @Override
    public int hashCode() {
        return _unit.hashCode();
    }
}