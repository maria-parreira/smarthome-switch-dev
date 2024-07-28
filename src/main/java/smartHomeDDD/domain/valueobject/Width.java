package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents the width of a room, and it is a Value Object.
 */
public class Width implements ValueObject {

    /**
     * The width of the room.
     */
    private final double m_width;

    /**
     * Constructor of the class Width.
     *
     * @param width The width of the room.
     */
    public Width(double width) {
        if (width > 0) {
            this.m_width = width;
        } else {
            throw new IllegalArgumentException("Width must be greater than 0");
        }
    }

    /**
     * Method to check if widths are the same.
     * @param object The object to compare.
     * @return true if the widths are the same, otherwise it returns false.
     */
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Width width) {
            return this.m_width == width.m_width;
        }
        return false;

    }

    /**
     * Method to get the width of the room.
     * @return The width of the room as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(this.m_width);
    }
    public double toDouble() {
        return m_width;
    }

    /**
     * Method to get the hash code of the Width object.
     * @return The hash code of the Width object.
     */
    @Override
    public int hashCode() {
        return Double.hashCode(m_width);
    }
}
