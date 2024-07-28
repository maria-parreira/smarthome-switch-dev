package smartHomeDDD.domain.domain.room;

import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The following tests were made in order to evaluate the performance of the class ImplFactoryRoom when creating a Room object:
 * Test to ensure that the ImplFactoryRoom class creates a Room object when given valid arguments.
 * Test to ensure that the createRoomId creates a roomId object from a String.
 * Test to ensure that the createFloorNumber successfully creates a FloorNumber object from an integer.
 * Test to ensure that the createDimensions successfully creates a Dimensions object with the correct values.
 */
class ImplFactoryRoomTest {

    /**
     * Test to ensure that the ImplFactoryRoom class creates a Room object when given valid arguments.
     */
    @Test
    void createRoom_ShouldCreateRoom() {
        // Arrange
        ImplFactoryRoom implFactoryRoom = new ImplFactoryRoom();
        HouseId houseId = mock(HouseId.class);
        RoomID roomId = mock(RoomID.class);
        FloorNumber floorNumber = mock(FloorNumber.class);
        Dimensions dimensions = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        // Act
        try (MockedConstruction<Room> roomDouble = Mockito.mockConstruction(Room.class,
                (mock, context) -> {
                    HouseId id = (HouseId) context.arguments().get(0);
                    RoomID room = (RoomID) context.arguments().get(1);
                    FloorNumber floor = (FloorNumber) context.arguments().get(2);
                    Dimensions dim = (Dimensions) context.arguments().get(3);
                    RoomName name  = (RoomName) context.arguments().get(5);

                    when(mock.getHouseId()).thenReturn(id);
                    when(mock.identity()).thenReturn(room);
                    when(mock.getFloorNumber()).thenReturn(floor);
                    when(mock.getDimensions()).thenReturn(dim);
                    when(mock.getRoomName()).thenReturn(name);
                })) {

            Room roomCreated = implFactoryRoom.createRoom(houseId, roomId, floorNumber, dimensions, isInside, roomName);

            // Assert
            List<Room> mockedRooms = roomDouble.constructed();
            Room mockedRoom = mockedRooms.get(0);

            assertEquals(1, mockedRooms.size());
            assertEquals(houseId, mockedRoom.getHouseId());
            assertEquals(roomId, mockedRoom.identity());
            assertEquals(floorNumber, mockedRoom.getFloorNumber());
            assertEquals(dimensions, mockedRoom.getDimensions());
            assertEquals(roomName, mockedRoom.getRoomName());
            assertEquals(roomCreated, mockedRoom);
        }
    }

}

