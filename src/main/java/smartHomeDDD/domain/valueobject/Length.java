package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents the length of a room, and it is a Value Object.
 */
public class Length implements ValueObject {

    /**
     * The length of the room.
     */
    private final double m_length;

    /**
     * Constructor of the class Length.
     *
     * @param length The length of the room.
     */
    public Length(double length) {
        if (length > 0) {
            this.m_length = length;
        } else {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
    }

    /**
     * Method to check if lengths are the same.
     * @param object The object to compare.
     * @return true if the lengths are the same, otherwise it returns false.
     */
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Length length) {
            return this.m_length == length.m_length;
        }
        return false;
    }

    /**
     * Method to get the length of the room.
     * @return The length of the room as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(this.m_length);
    }
    public double toDouble() {
        return m_length;
    }

    /**
     * Returns the hash code of the length.
     * @return The hash code of the length.
     */
    @Override
    public int hashCode() {
        return Double.hashCode(m_length);
    }
}
