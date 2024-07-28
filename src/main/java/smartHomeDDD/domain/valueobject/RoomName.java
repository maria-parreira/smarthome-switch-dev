package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * The RoomName class represents the name of a room in the smartHomeDDD domain.
 * This class is a value object in the domain-driven design context.
 * A RoomName instance is immutable once created and ensures that the room name is not null or empty.
 * This class implements the ValueObject interface, which means it has a method to check the equality with other objects.
 * The class also overrides the toString method to return the room name as a string.
 */

public class RoomName implements ValueObject {
    /**
     * The name of the room.
     */

    private final String _name;

    /**
     * Constructs a new RoomName object.
     * @param name the name of the room, in String format.
     * @throws IllegalArgumentException if the provided name is null or empty.
     */

    public RoomName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Room Name cannot be null or empty.");
        else
            this._name = name;
    }

    /**
     * Compares this RoomName object to another object.
     * @param object the object to compare this RoomName object to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof RoomName roomName) {

            return this._name.equals(roomName._name);
        }
        return false;
    }

    /**
     * @return the string representation of this RoomName object.
     */
    @Override
    public String toString() {
        return _name;
    }

    /**
     * @return the hash code of this RoomName object.
     */
    @Override
    public int hashCode() {
        return _name.hashCode();
    }
}
