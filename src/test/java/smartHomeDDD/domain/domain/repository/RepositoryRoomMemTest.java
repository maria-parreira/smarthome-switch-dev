package smartHomeDDD.domain.domain.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.RoomID;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
    /**
     * This following tests verify the functionality of the RepositoryRoom class:
     * - Saving a room to an initially empty repository and verifying that the repository contains the saved room.
     * - Verifying that an empty repository doesn't contain any rooms.
     * - Verifying that the save method throws an IllegalArgumentException when trying to save a null room.
     * - Ensuring that, when the repository is empty, the findAll() method returns an empty iterable.
     * - Verifying if the findAll() method returns a non-empty iterable when the repository contains rooms.
     * - Verifying that, when the repository is empty, the getRoomsByHouseID method returns an empty list.
     * - Verifying that the getRoomsByHouseID method returns a list of rooms when there are rooms in the repository.
     * - Verifying that the save method throws an IllegalArgumentException when trying to save a room with the same identity as a room already in the repository.
     * - Verifying that the getOutsideRooms method returns an empty list when there are no outside rooms in the repository.
     * - Verifying that the getOutsideRooms method returns a list of outside rooms when there are outside rooms in the repository.
     */
class RepositoryRoomMemTest {

    /**
     * Verifies if the initially empty RoomRepository contains a Room after saving it in the repository.
     */
    @Test
    void saveRoomToEmptyRepository_ShouldContainRoom() {

        // Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        RoomID RoomId = mock(RoomID.class);
        Room Room = mock(Room.class);
        when(Room.identity()).thenReturn(RoomId);

        // Act
        Room savedRoom = repository.save(Room);
        RoomId = savedRoom.identity();
        Optional<Room> retrievedRoomOptional = repository.ofIdentity(RoomId);

        // Assert
        assertTrue(repository.containsOfIdentity(RoomId));
        assertTrue(retrievedRoomOptional.isPresent());
    }

    /**
     * Verifies that an empty repository doesn't contain any Rooms.
     */
    @Test
    void emptyRepository_ShouldNotContainRoom() {

        // Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        RoomID RoomId = mock(RoomID.class);
        Room Room = mock(Room.class);
        when(Room.identity()).thenReturn(RoomId);

        // Act
        Optional<Room> retrievedRoomOptional = repository.ofIdentity(RoomId);

        // Assert
        assertFalse(repository.containsOfIdentity(RoomId));
        assertFalse(retrievedRoomOptional.isPresent());
    }

    /**
     * Verifies that the save method throws an IllegalArgumentException when trying to save a null Room.
     */
    @Test
    void saveNullRoom_ShouldThrowException() {
        // Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        String expectedMessage = "Room cannot be null";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.save(null));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Ensures that, when the repository is empty, the findAll() method returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {

        // Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();

        // Act
        Iterable<Room> allRooms = repository.findAll();

        // Assert
        assertFalse(allRooms.iterator().hasNext());
    }

    /**
     * Verifies if the findAll() method returns a non-empty iterable when the repository contains Rooms.
     */
    @Test
    void findAll_NonEmptyRepository_ShouldReturnNonEmptyIterable() {

        // Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        RoomID RoomId1 = mock(RoomID.class);
        RoomID RoomId2 = mock(RoomID.class);
        Room Room1 = mock(Room.class);
        when(Room1.identity()).thenReturn(RoomId1);
        Room Room2 = mock(Room.class);
        when(Room2.identity()).thenReturn(RoomId2);
        repository.save(Room1);
        repository.save(Room2);

        // Act
        Iterable<Room> allRooms = repository.findAll();

        // Assert
        assertNotNull(allRooms);
        assertTrue(allRooms.iterator().hasNext());
    }
    /**
     * Ensures that, when the repository is empty, the findAll() method returns an empty iterable.
     */
    @Test
    void houseWithNoRooms_shouldReturnEmptyList() {
        //Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        HouseId houseId = mock(HouseId.class);
        //Act
        List<Room> roomsInHouse = repository.getRoomsByHouseID(houseId);

        //Assert
        assertTrue(roomsInHouse.isEmpty());
    }
    @Test
    void houseWithSeveralRooms_shouldReturnList() {
        //Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        HouseId houseId = mock(HouseId.class);
        when(houseId.toString()).thenReturn("1");
        Room room1 = mock(Room.class);
        when(room1.getHouseId()).thenReturn(houseId);
        when(room1.identity()).thenReturn(mock(RoomID.class));

        Room room2 = mock(Room.class);
        when(room2.getHouseId()).thenReturn(houseId);
        when(room2.identity()).thenReturn(mock(RoomID.class));

        Room room3 = mock(Room.class);
        when(room3.getHouseId()).thenReturn(mock(HouseId.class));
        when(room3.identity()).thenReturn(mock(RoomID.class));

        repository.save(room1);
        repository.save(room2);
        repository.save(room3);
        //Act
        List<Room> roomsInHouse = repository.getRoomsByHouseID(houseId);
        //Assert
        assertEquals(2, roomsInHouse.size());
    }
    /**
     * Verifies that, when saving a Room with the same identity as a Room already in the repository, the save method returns null.
     */
    @Test
    void saveDuplicateRoom_ShouldThrowException() {
        // Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        Room room = mock(Room.class);
        RoomID roomId = mock(RoomID.class);
        when(room.identity()).thenReturn(roomId);
        repository.save(room);
        String expectedMessage = "Room already exists";
        // Act+Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(room));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Verifies that the getOutsideRooms method returns an empty list when there are no outside rooms in the repository.
     */

    @Test
    void ListOfOutsideRooms_shouldReturnEmptyList() {
        //Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        Room room1 = mock(Room.class);
        when(room1.identity()).thenReturn(mock(RoomID.class));
        when(room1.isInside()).thenReturn(true);

        Room room2 = mock(Room.class);
        when(room2.identity()).thenReturn(mock(RoomID.class));
        when(room2.isInside()).thenReturn(true);

        Room room3 = mock(Room.class);
        when(room3.identity()).thenReturn(mock(RoomID.class));
        when(room3.isInside()).thenReturn(true);

        repository.save(room1);
        repository.save(room2);
        repository.save(room3);

        Iterable<Room> allRooms = repository.findAll();

        //Act
        List<Room> outsideRooms = repository.getOutsideRooms(allRooms);

        //Assert
        assertTrue(outsideRooms.isEmpty());
    }

    /**
     * Verifies that the getOutsideRooms method returns a list of outside rooms when there are outside rooms in the repository.
     */
    @Test
    void ListOfOutsideRooms_shouldReturnListOfOutsideRooms() {
        //Arrange
        IRepositoryRoom repository = new RepositoryRoomMem();
        Room room1 = mock(Room.class);
        when(room1.identity()).thenReturn(mock(RoomID.class));
        when(room1.isInside()).thenReturn(false);

        Room room2 = mock(Room.class);
        when(room2.identity()).thenReturn(mock(RoomID.class));
        when(room2.isInside()).thenReturn(true);

        Room room3 = mock(Room.class);
        when(room3.identity()).thenReturn(mock(RoomID.class));
        when(room3.isInside()).thenReturn(false);

        repository.save(room1);
        repository.save(room2);
        repository.save(room3);

        Iterable<Room> allRooms = repository.findAll();

        //Act
        List<Room> outsideRooms = repository.getOutsideRooms(allRooms);

        //Assert
        assertFalse(outsideRooms.isEmpty());
    }

}


