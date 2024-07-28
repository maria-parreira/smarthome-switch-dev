package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents the floor number of a room, and it is a Value Object.
 */

public class FloorNumber implements ValueObject {
    /**
     * The floor number of the room.
     */
    private final Integer m_floorNumber;
    /**
     * Constructor for the FloorNumber class.
     *
     * @param floorNumber The floor number to be assigned to the room. Must not be null.
     * @throws IllegalArgumentException If the provided floor number is null.
     */
    public FloorNumber(Integer floorNumber) {
        if (floorNumber != null) {
            this.m_floorNumber = floorNumber;
        } else
            throw new IllegalArgumentException("Invalid floor number, it must not be null");
    }

    /**
     * Method to check if floor numbers are the same.
     * @param object The object to compare.
     * @return true if the floor number objects are the same, otherwise it returns false.
     */
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof FloorNumber floorNumber) {

            return this.m_floorNumber.equals(floorNumber.m_floorNumber);
        }
        return false;
    }

    /**
     * Method to get the floor number of the room.
     * @return The floor number of the room as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(this.m_floorNumber);
    }
    public Integer getFloorNumber() {
        return m_floorNumber;
    }

    /**
     * Method to get the hash code of the floor number.
     * @return The hash code of the floor number.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(this.m_floorNumber);
    }
}

