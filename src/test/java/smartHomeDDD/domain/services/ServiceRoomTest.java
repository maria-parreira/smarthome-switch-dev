package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceRoom;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is responsible for unit testing the ServiceRoom class. It verifies the following use cases:
 * - Successful creation of a ServiceRoom object
 * - Handling of null parameters in the constructor
 * - Addition of a room to a house
 * - Handling of a scenario where adding a room to a non-existent house is attempted
 * - Retrieval of a list of rooms in a house
 * - Handling of a scenario where retrieving rooms from a non-existent house is attempted
 * - Retrieval of a room using its unique ID
 * - Handling of a scenario where retrieval of a room is attempted with a non-existent ID
 * - Retrieval of the entrance room of a house
 * - Handling of a scenario where retrieval of the entrance room of a house is attempted but the room is not found
 */
class ServiceRoomTest {

    /**
     * This method tests the successful creation of a ServiceRoom object.
     */
    @Test
    void testServiceRoomConstructor_shouldConstructServiceObject() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();

        // Act
        ServiceRoom result = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);

        // Assert
        assertNotNull(result);
    }

    /**
     * This method tests the handling of a null FactoryRoom in the ServiceRoom constructor. It should throw an
     * IllegalArgumentException.
     */
    @Test
    void testServiceRoomConstructorWithNullFactoryRoom_shouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);


        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceRoom(null, repoRoom, repoHouse,
                generateRandomId));
    }

    /**
     * This method tests the handling of a null IRepositoryRoom in the ServiceRoom constructor. It should throw
     * an IllegalArgumentException.
     */
    @Test
    void testServiceRoomConstructorWithNullRepoRoom_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceRoom(factoryRoom, null,
                repoHouse, generateRandomId));
    }

    /**
     * This method tests the handling of a null IRepositoryHouse in the ServiceRoom constructor. It should throw an
     * IllegalArgumentException.
     */
    @Test
    void testServiceRoomConstructorWithNullRepoHouse_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceRoom(factoryRoom, repoRoom, null,
                generateRandomId));
    }

    /**
     * This method tests the handling of a null GenerateRandomId in the ServiceRoom constructor. It should throw an
     * IllegalArgumentException.
     */
    @Test
    void testServiceRoomConstructorWithNullGenerateRandomId_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceRoom(factoryRoom, repoRoom, repoHouse,
                null));
    }

    /**
     * This method tests the addition of a room to a house. It should return the newly added room.
     */
    @Test
    void testAddRoomToHouse() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);
        HouseId houseId = new HouseId("1");
        FloorNumber floorNumber = new FloorNumber(1);
        Dimensions dimensions = new Dimensions(new Length(10), new Width(10), new Height(10));
        RoomName roomName = new RoomName("Living Room");
        when(repoHouse.containsOfIdentity(houseId)).thenReturn(true);
        RoomID roomId = new RoomID("r1");
        Room room = factoryRoom.createRoom(houseId, roomId, floorNumber, dimensions, true, roomName);
        when(repoRoom.save(room)).thenReturn(room);
        when(generateRandomId.generateID()).thenReturn("r1");

        // Act
        Room result = serviceRoom.addRoomToHouse(houseId, floorNumber, dimensions, true, roomName);

        // Assert
        assertEquals(room, result);
    }

    /**
     * This method tests the handling of a scenario where adding a room to a non-existent house is attempted. It should
     * throw an EntityNotFoundException.
     */
    @Test
    void testAddRoomToHouseWithNoHouse_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);
        HouseId houseId = new HouseId("1");
        FloorNumber floorNumber = new FloorNumber(1);
        Dimensions dimensions = new Dimensions(new Length(10), new Width(10), new Height(10));
        RoomName roomName = new RoomName("Living Room");
        when(repoHouse.containsOfIdentity(houseId)).thenReturn(false);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceRoom.addRoomToHouse(houseId, floorNumber, dimensions,
                true, roomName));
    }

    /**
     * This method tests the retrieval of a list of rooms in a house. It should return a list of rooms.
     */
    @Test
    void testGetRoomsByHouseID() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);
        HouseId houseId = new HouseId("1");
        RoomID roomId1 = new RoomID("r1");
        RoomID roomId2 = new RoomID("r2");
        Room room1 = factoryRoom.createRoom(houseId, roomId1, new FloorNumber(1), new Dimensions(new Length(10),
                new Width(10), new Height(10)), true, new RoomName("Living Room"));
        Room room2 = factoryRoom.createRoom(houseId, roomId2, new FloorNumber(1), new Dimensions(new Length(10),
                new Width(10), new Height(10)), true, new RoomName("Kitchen"));
        when(repoHouse.containsOfIdentity(houseId)).thenReturn(true);
        when(repoRoom.getRoomsByHouseID(houseId)).thenReturn(List.of(room1, room2));


        // Act
        List<Room> result = serviceRoom.getRoomsByHouseID(houseId);
        List<Room> rooms = List.of(room1, room2);

        // Assert
        assertEquals(rooms, result);
    }

    /**
     * This method tests the handling of a scenario where retrieving rooms from a non-existent house is attempted.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testGetRoomsByHouseIDWithNoHouse_shouldThrowEntityNotFoundException() {

        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);
        HouseId houseId = new HouseId("1");
        when(repoHouse.containsOfIdentity(houseId)).thenReturn(false);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceRoom.getRoomsByHouseID(houseId));
    }

    /**
     * This method tests the retrieval of a room by its ID. It should return the room.
     */
    @Test
    void testGetRoomById() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);
        RoomID roomId = new RoomID("1");
        HouseId houseId = new HouseId("1");
        Room room = factoryRoom.createRoom(houseId, roomId, new FloorNumber(1), new Dimensions(new Length(10),
                new Width(10), new Height(10)), true, new RoomName("Living Room"));
        when(repoRoom.ofIdentity(roomId)).thenReturn(Optional.of(room));

        // Act
        Room result = serviceRoom.getRoomById(roomId);

        // Assert
        assertEquals(room, result);
    }

    /**
     * This method tests the handling of a scenario where retrieval of a room is attempted with a non-existent ID.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testGetRoomByIdWithNoRoom_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repoRoom, repoHouse, generateRandomId);
        RoomID roomId = new RoomID("1");
        when(repoRoom.ofIdentity(roomId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceRoom.getRoomById(roomId));
    }

}
