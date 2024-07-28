package smartHomeDDD.domain.room;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.*;

/**
 * This class represents a room in the smart home system.
 */
public class Room implements AggregateRoot<RoomID> {
    /**
     * The unique identifier for the room.
     */
    private final HouseId _houseId;
    /**
     * The unique identifier for the house in which the room is located.
     */
    private final RoomID _roomID;
    /**
     * The floor number where the room is located.
     */
    private final FloorNumber m_floorNumber;
    /**
     * The dimensions of the room.
     */
    private final Dimensions m_dimensions;
    /**
     * boolean value that indicates whether the room is inside the house.
     */
    private final boolean isInside;

    /**
     * The name of the room.
     */
    private final RoomName roomName;

    /**
     * Constructor of the Room class.
     *
     * @param houseId     The unique identifier for the house in which the room is located.
     * @param roomId      The unique identifier for the room.
     * @param floorNumber The floor number where the room is located.
     * @param dimensions  The dimensions of the room.
     * @param isInside    boolean value that indicates whether the room is inside the house.
     * @param roomName    The name of the room.
     * @throws IllegalArgumentException If any of the parameters are null.
     */

    public Room(HouseId houseId, RoomID roomId, FloorNumber floorNumber, Dimensions dimensions, boolean isInside, RoomName roomName) {
        this._roomID = roomId;
        this._houseId = houseId;
        this.m_floorNumber = floorNumber;
        this.m_dimensions = dimensions;
        this.isInside = isInside;
        this.roomName = roomName;
    }

    /**
     * Method to get the unique identifier for the room.
     *
     * @return The unique identifier for the room.
     */
    @Override
    public RoomID identity() {
        return _roomID;
    }

    /**
     * Method to get the house in which the room is.
     */
    public HouseId getHouseId() {
        return _houseId;
    }

    /**
     * Method to get the floor number of the room.
     */
    public FloorNumber getFloorNumber() {
        return m_floorNumber;
    }

    /**
     * Method to get the dimensions of the room.
     */
    public Dimensions getDimensions() {
        return m_dimensions;
    }

    /**
     * Retrieves the state of the room regarding whether it's inside or outside.
     */
    public boolean isInside() {
        return isInside;
    }

    /**
     * Retrieves the name of the room.
     */
    public RoomName getRoomName() {return roomName; }

    /**
     * This method compares the roomId of the room with the roomId of the object passed as a parameter.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof Room oRoom) {

            return this._roomID.equals(oRoom._roomID);
        }
        return false;
    }

    /**
     * This method compares the houseId, floorNumber and dimensions of the room with the houseId, floorNumber and dimensions of the object passed as a parameter.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof Room oRoom) {
            return this._houseId.equals(oRoom._houseId)
                    && this.m_floorNumber.equals(oRoom.m_floorNumber)
                    && this.m_dimensions.equals(oRoom.m_dimensions)
                    && this.isInside == (oRoom.isInside)
                    && this._roomID.equals(oRoom._roomID)
                    && this.roomName.equals(oRoom.roomName);
        }

        return false;
    }

    /**
     * Returns the hash code of the room ID.
     *
     * @return The hash code of the room ID.
     */
    @Override
    public int hashCode() {
        return _roomID.hashCode();
    }
}
