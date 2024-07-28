package smartHomeDDD.dto;


/**
 * This class represents a Room Data Transfer Object for general purposes.
 */
public class RoomDTO {
    /**
     * The id of the room.
     */
    private final String roomId;
    /**
     * The length of the room.
     */
    private final double length;
    /**
     * The width of the room.
     */
    private final double width;
    /**
     * The height of the room.
     */
    private final double height;
    /**
     * The floor number of the room.
     */
    private final Integer floorNumber;
    /**
     * The id of the house where the room is located.
     */
    private final String houseId;
    /**
     * Boolean value that indicates whether the room is inside the house.
     */
    private final boolean isInside;

    /**
     * The name of the room.
     */
    private final String roomName;

    /**
     * Constructor of the class RoomDTO.
     *
     * @param roomId      The id of the room.
     * @param length      The length of the room.
     * @param width       The width of the room.
     * @param height      The height of the room.
     * @param floorNumber The floor number of the room.
     * @param houseId     The id of the house where the room is located.
     * @param isInside    Boolean value that indicates whether the room is inside the house.
     * @param roomName The name of the room.
     */
    public RoomDTO(String roomId, double length, double width, double height, Integer floorNumber, String houseId, boolean isInside, String roomName)
    {
        this.roomId = roomId;
        this.length = length;
        this.width = width;
        this.height = height;
        this.floorNumber = floorNumber;
        this.houseId = houseId;
        this.isInside = isInside;
        this.roomName = roomName;
    }

    /**
     * Method to get the id of the room.
     * @return The id of the room.
     */
    public String getRoomId()
    {
        return roomId;
    }

    /**
     * Method to get the length of the room.
     * @return The length of the room.
     */
    public double getLength()
    {
        return length;
    }

    /**
     * Method to get the width of the room.
     * @return The width of the room.
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * Method to get the height of the room.
     * @return The height of the room.
     */
    public double getHeight()
    {
        return height;
    }

    /**
     * Method to get the floor number of the room.
     * @return The floor number of the room.
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Method to get the id of the house where the room is located.
     * @return The id of the house where the room is located.
     */
    public String getHouseId() {
        return houseId;
    }

    /**
     * Returns whether the room is inside the house or not.
     * @return True if the room is inside the house, false otherwise.
     */
    public boolean isInside()
    {
        return isInside;
    }

    /**
     * Method to get the name of the room.
     * @return The name of the room.
     */
    public String getRoomName() { return roomName; }
}
