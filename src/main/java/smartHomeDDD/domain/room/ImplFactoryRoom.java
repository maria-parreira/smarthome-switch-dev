package smartHomeDDD.domain.room;


import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.*;


/**
 * This class is a factory that implements the FactoryRoom interface and creates Room objects as well as Room related Value Objects
 * such as RoomID, FloorNumber, and Dimensions.
 */
@Component
public class ImplFactoryRoom implements FactoryRoom {


    /**
     * This method creates a Room object. It takes in a HouseId, RoomID, FloorNumber, and Dimensions objects as parameters
     * and returns a Room object.
     * @param houseId The ID of the house that the room belongs to.
     * @param roomId The ID of the room.
     * @param floorNumber The floor number of the room.
     * @param dimensions The dimensions of the room.
     * @param isInside boolean value that indicates if the room is inside the house.
     * @return Room object
     */
    @Override
    public Room createRoom(HouseId houseId, RoomID roomId, FloorNumber floorNumber, Dimensions dimensions, boolean isInside, RoomName roomName)
    {
        return new Room(houseId, roomId, floorNumber, dimensions, isInside, roomName);
    }

}