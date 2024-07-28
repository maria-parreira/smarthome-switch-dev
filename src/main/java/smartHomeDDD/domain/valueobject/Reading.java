package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents a Reading value object, the reading value that is obtained from a sensor.
 */
public class Reading implements ValueObject {

    /**
     * The reading value.
     */

    private final String _reading;

    /**
     * Constructor for the Reading class.
     *
     * @param reading The reading value to be assigned. Must not be null or empty.
     * @throws IllegalArgumentException If the provided reading is null, empty, or doesn't meet certain other input criteria.
     */
    public Reading(String reading) {
        if (reading == null || reading.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid reading");
        }
        this._reading = reading;
    }

    /**
     * Compares two objects via their reading.
     * @param object  the object to compare.
     * @return true if the objects are the same, otherwise it returns false.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Reading reading) {
            return this._reading.equals(reading._reading);
        }
        return false;
    }

    /**
     * Method to get the reading value.
     *
     * @return The reading value as a string.
     */
    @Override
    public  String toString() {
        return _reading;
    }

    /**
     * Returns the hash code of this Reading.
     *
     * @return The hash code of this Reading.
     */
    @Override
    public int hashCode() {
        return _reading.hashCode();
    }
}