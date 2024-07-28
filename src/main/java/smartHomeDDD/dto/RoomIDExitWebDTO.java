package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * This class represents a Room Data Transfer Object for the web.
 */
public class RoomIDExitWebDTO extends RepresentationModel<RoomIDExitWebDTO> {

    /**
     * The id of the room.
     */
    private final String roomId;

    /**
     * Constructs a new Room Web DTO with the specified room information.
     */
    public RoomIDExitWebDTO(String roomId) {
        this.roomId = roomId;

    }

    /**
     * Gets the id of the room.
     *
     * @return the id of the room.
     */
    public String getRoomId() {
        return roomId;
    }


}
