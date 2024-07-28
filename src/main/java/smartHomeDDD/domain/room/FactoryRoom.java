package smartHomeDDD.domain.room;

import smartHomeDDD.domain.valueobject.*;

/**
 * Factory for creating Room instances.
 */
public interface FactoryRoom {
    Room createRoom(HouseId houseId, RoomID roomId, FloorNumber floorNumber, Dimensions dimensions, boolean isInside, RoomName roomName);

}