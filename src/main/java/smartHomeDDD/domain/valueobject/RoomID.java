package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * This class represents the RoomID, and it is a Value Object.
 */
public class RoomID implements DomainId {

    /**
     * The id of the room.
     */
    private final String _id;

    /**
     * Constructor of the class RoomID
     *
     * @param sId The id of the room.
     * @throws IllegalArgumentException If the room id is null or empty.
     */
    public RoomID(String sId)
    {
        if (sId != null && !sId.isBlank() && !sId.isEmpty())
            this._id = sId;
        else
            throw new IllegalArgumentException("RoomId cannot be null or empty");
    }

    /**
     * Compares two objects via their ID.
     *
     * @param object the object to compare.
     * @return true if the objects are the same, otherwise it returns false.
     */
    @Override
    public boolean equals(Object object)
    {

        if (this == object)
            return true;

        if (object instanceof RoomID roomId) {

            return this._id.equals(roomId._id);
        }
        return false;
    }

    /**
     * Method to get the id of the room.
     *
     * @return The id of the room as a string.
     */
    @Override
    public String toString()
    {
        return _id;
    }

    /**
     * Returns the hash code of this RoomID.
     *
     * @return The hash code of this RoomID.
     */
    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
