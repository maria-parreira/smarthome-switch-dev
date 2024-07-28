package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents the dimensions of a room, and it is a Value Object.
 * This value object is composed of three other value objects: Length, Width, and Height.
 */
public class Dimensions implements ValueObject {

    /**
     * The length of the room.
     */
    private final Length m_length;

    /**
     * The width of the room.
     */
    private final Width m_width;

    /**
     * The height of the room.
     */
    private final Height m_height;

    /**
     * Constructor of the class Dimensions.
     * @param length The length of the room.
     * @param width  The width of the room.
     * @param height The height of the room.
     */
    public Dimensions(Length length, Width width, Height height) {
        this.m_length = length;
        this.m_width = width;
        this.m_height = height;
    }

    /**
     * Method to check if dimensions are the same.
     * @param object The object to compare.
     *               <p>
     *               It compares the dimensions length, width, and height of the room with the dimensions of the object passed as a parameter.
     * @return true if the dimensions are the same, otherwise it returns false.
     */
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Dimensions dimensions) {
            return this.m_length.equals(dimensions.m_length) &&
                    this.m_width.equals(dimensions.m_width) &&
                    this.m_height.equals(dimensions.m_height);
        }
        return false;
    }

    /**
     * Method to get the dimensions of the room.
     * @return The dimensions of the room as a string.
     */
    @Override
    public String toString() {
        return m_length.toString() + ", " + m_width.toString() + ", " + m_height.toString();
    }
    public Length getLength() {
        return m_length;
    }
    public Width getWidth() {
        return m_width;
    }
    public Height getHeight() {
        return m_height;
    }

    /**
     * Method to get the hash code of the dimensions.
     * @return The hash code of the dimensions.
     */
    @Override
    public int hashCode() {
        return m_length.hashCode() + m_width.hashCode() + m_height.hashCode();
    }
}
