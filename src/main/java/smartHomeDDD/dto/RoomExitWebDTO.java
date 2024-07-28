package smartHomeDDD.dto;
import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a DTO (Data Transfer Object) for a room exit in a web environment.
 * This DTO encapsulates information about a room including its dimensions, location, and name.
 */
public class RoomExitWebDTO extends RepresentationModel<RoomExitWebDTO> {

    /** The unique identifier of the room. */
    private final String roomId;

    /** The length of the room. */
    private final double length;

    /** The width of the room. */
    private final double width;

    /** The height of the room. */
    private final double height;

    /** The floor number where the room is situated. */
    private final Integer floorNumber;

    /** The identifier of the house to which the room belongs. */
    private final String houseId;

    /** Indicates whether the room is situated inside the house. */
    private final boolean isInside;

    /** The name of the room. */
    private final String roomName;

    /**
     * Constructs a new RoomExitWebDTO object with the specified parameters.
     * @param roomId      The unique identifier of the room.
     * @param length      The length of the room.
     * @param width       The width of the room.
     * @param height      The height of the room.
     * @param floorNumber The floor number where the room is situated.
     * @param houseId     The identifier of the house to which the room belongs.
     * @param isInside    Indicates whether the room is situated inside the house.
     * @param roomName    The name of the room.
     */
    public RoomExitWebDTO(String roomId, double length, double width, double height, Integer floorNumber, String houseId, boolean isInside, String roomName) {
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
     * Retrieves the name of the room.
     * @return The name of the room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Checks whether the room is situated inside the house.
     * @return True if the room is situated inside the house, false otherwise.
     */
    public boolean isInside() {
        return isInside;
    }

    /**
     * Retrieves the identifier of the house to which the room belongs.
     * @return The identifier of the house to which the room belongs.
     */
    public String getHouseId() {
        return houseId;
    }

    /**
     * Retrieves the floor number where the room is situated.
     * @return The floor number where the room is situated.
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Retrieves the height of the room.
     * @return The height of the room.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Retrieves the width of the room.
     * @return The width of the room.
     */
    public double getWidth() {
        return width;
    }


    /**
     * Retrieves the length of the room.
     * @return The length of the room.
     */
    public double getLength() {
        return length;
    }

    /**
     * Retrieves the unique identifier of the room.
     * @return The unique identifier of the room.
     */
    public String getRoomId() {
        return roomId;
    }














}
