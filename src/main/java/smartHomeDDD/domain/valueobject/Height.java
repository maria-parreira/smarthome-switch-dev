package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents the height of a room, and it is a Value Object.
 */

public class Height implements ValueObject {
    /**
     * The height of the room.
     */
    private final double m_height;
    /**
     * Constructor of the class Height.
     *
     * @param height The height of the room.
     */
    public Height(double height) {
        if (height > 0) {
            this.m_height = height;
        } else {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
    }

    /**
     * Method to check if heights are the same.
     * @param object The object to compare.
     * @return true if the heights are the same, otherwise it returns false.
     */
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Height height) {
            return this.m_height == height.m_height;
        }
        return false;

    }

    /**
     * Method to get the height of the room.
     * @return The height of the room as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(this.m_height);
    }
    public double toDouble() {
        return m_height;
    }

    /**
     * Method to get the hash code of the height.
     *
     * @return The hash code of the height.
     */
    @Override
    public int hashCode() {
        return Double.hashCode(this.m_height);
    }
}
