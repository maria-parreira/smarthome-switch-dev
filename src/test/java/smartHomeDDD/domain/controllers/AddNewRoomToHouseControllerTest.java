package smartHomeDDD.domain.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.controllers.AddNewRoomToHouseController;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.house.ImplFactoryHouse;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.RoomDTO;
import smartHomeDDD.persistence.mem.RepositoryHouseMem;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is responsible for testing the functionality US02AddNewRoomToHouseController class.
 * It verifies if the controller is able to add a new room to the house and save it successfully in the repository as well as
 * if exceptions are thrown when invalid data is provided.
 * The following tests were performed:
 * An exception is thrown when the repository is null.
 * An exception is thrown when the factory is null.
 * Room is added to the house and saved in the repository.
 * Two rooms are added to the house and saved in the repository.
 * The room is not created as the houseId is invalid and an exception is thrown.
 * The room is not created as the height is invalid and an exception is thrown.
 * The room is not created as the length is invalid and an exception is thrown.
 * The room is not created as the width is invalid and an exception is thrown.
 */

class AddNewRoomToHouseControllerTest {

    private static House getHouse() {
        Latitude latitude = new Latitude(38.74777);
        Longitude longitude = new Longitude(-9.23098);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        ZipCode zipCode = new ZipCode("Portugal", "4935-054");
        Address address = new Address("address");
        Location location = new Location(address, zipCode, gpsCoordinates);
        HouseId houseId = new HouseId("houseId1");
        FactoryHouse factoryHouse = new ImplFactoryHouse();

        return factoryHouse.createHouse(houseId, location);
    }

    /**
     * Test case to verify when that an exception is thrown when the room repository is null.
     */
    @Test
    void nullService_shouldThrowException() {
        // Arrange
        String expectedMessage = "Service cannot be null";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new AddNewRoomToHouseController(null));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify that a valid room is added to the house and saved in the repository.
     */
    @Test
    void validRoom_shouldAddRoomToHouse() {
        // Arrange
        String roomId = "roomId1";
        double length = 5.0;
        double width = 4.0;
        double height = 3.0;
        Integer floorNumber = 1;
        String houseId = "houseId1";
        boolean isInside = true;
        String roomName = "Living Room";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);

        repositoryHouse.save(getHouse());

        when(mockId.generateID()).thenReturn(roomId);

        int expectedSize = 1;

        RoomDTO roomDTO = new RoomDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);
        // Act
        RoomDTO addedRoomDTO = myController.addRoomToHouse(roomDTO);

        // Assert
        assertEquals(roomId, addedRoomDTO.getRoomId());
        assertEquals(length, addedRoomDTO.getLength()); // Adjust the delta for double comparison
        assertEquals(width, addedRoomDTO.getWidth());
        assertEquals(height, addedRoomDTO.getHeight());
        assertEquals(floorNumber, addedRoomDTO.getFloorNumber());
        assertEquals(houseId, addedRoomDTO.getHouseId());
        assertEquals(expectedSize, repositoryRoom.findAll().spliterator().getExactSizeIfKnown());
    }

    /**
     * Test case to verify if two valid rooms are added to the house and saved in the repository.
     */
    @Test
    void validRooms_shouldAddRoomsToHouse() {
        // Arrange
        String roomId1 = "roomId1";
        double length1 = 5.0;
        double width1 = 4.0;
        double height1 = 3.0;
        int floorNumber1 = 1;
        String houseId1 = "houseId1";
        boolean isInside = true;
        String roomName1 = "Living Room";

        String roomId2 = "roomId2";
        double length2 = 6.0;
        double width2 = 5.0;
        double height2 = 4.0;
        Integer floorNumber2 = 2;
        String roomName2 = "Bedroom";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);

        int expectedSize = 2;

        RoomDTO roomDTO1 = new RoomDTO(roomId1, length1, width1, height1, floorNumber1, houseId1, isInside, roomName1);
        RoomDTO roomDTO2 = new RoomDTO(roomId2, length2, width2, height2, floorNumber2, houseId1, isInside, roomName2);

        repositoryHouse.save(getHouse());

        when(mockId.generateID()).thenReturn(roomId1).thenReturn(roomId2);

        // Act
        RoomDTO addedRoomDTO1 = myController.addRoomToHouse(roomDTO1);
        RoomDTO addedRoomDTO2 = myController.addRoomToHouse(roomDTO2);

        // Assert
        assertEquals(roomId1, addedRoomDTO1.getRoomId());
        assertEquals(length1, addedRoomDTO1.getLength()); // Adjust the delta for double comparison
        assertEquals(width1, addedRoomDTO1.getWidth());
        assertEquals(height1, addedRoomDTO1.getHeight());
        assertEquals(floorNumber1, addedRoomDTO1.getFloorNumber());
        assertEquals(houseId1, addedRoomDTO1.getHouseId());

        assertEquals(roomId2, addedRoomDTO2.getRoomId());
        assertEquals(length2, addedRoomDTO2.getLength()); // Adjust the delta for double comparison
        assertEquals(width2, addedRoomDTO2.getWidth());
        assertEquals(height2, addedRoomDTO2.getHeight());
        assertEquals(floorNumber2, addedRoomDTO2.getFloorNumber());
        assertEquals(houseId1, addedRoomDTO2.getHouseId());

        assertEquals(expectedSize, repositoryRoom.findAll().spliterator().getExactSizeIfKnown());
    }

    /**
     * Test case to verify that an exception is thrown when the houseId is invalid.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_invalidHouseId(String houseId) {
        // Arrange
        String roomId = "roomId1";
        double length = 5.0;
        double width = 4.0;
        double height = 3.0;
        Integer floorNumber = 1;
        boolean isInside = true;
        String roomName = "Living Room";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);


        RoomDTO roomDTO = new RoomDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);

        String expectedMessage = "HouseId can't be empty";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.addRoomToHouse(roomDTO));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify that an exception is thrown when the height is invalid.
     */
    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -10.5})
    void shouldThrowException_invalidHeight(double height) {
        // Arrange
        String houseId = "houseId1";
        String roomId = "roomId1";
        Integer floorNumber = 1;
        double length = 5.0;
        double width = 4.0;
        boolean isInside = true;
        String roomName = "Living Room";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);

        RoomDTO roomDTO = new RoomDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);

        String expectedMessage = "Height must be greater than 0";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.addRoomToHouse(roomDTO));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify that an exception is thrown when the length is invalid.
     */
    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -10.5})
    void shouldThrowException_invalidLength(double length) {
        // Arrange
        String houseId = "houseId1";
        String roomId = "roomId1";
        int floorNumber = 1;
        double width = 4.0;
        double height = 3.0;
        boolean isInside = true;
        String roomName = "Living Room";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);

        RoomDTO roomDTO = new RoomDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);

        String expectedMessage = "Length must be greater than 0";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.addRoomToHouse(roomDTO));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify that an exception is thrown when the width is invalid.
     */
    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -10.5})
    void shouldThrowException_invalidWidth(double width) {
        // Arrange
        String houseId = "houseId1";
        String roomId = "roomId1";
        int floorNumber = 1;
        double length = 5.0;
        double height = 3.0;
        boolean isInside = true;
        String roomName = "Living Room";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);

        RoomDTO roomDTO = new RoomDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);

        String expectedMessage = "Width must be greater than 0";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.addRoomToHouse(roomDTO));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify that an exception is thrown when the room already exists.
     */
    @Test
    void existentRoom_ShouldThrowException() {
        // Arrange
        String roomId = "roomId1";
        double length = 5.0;
        double width = 4.0;
        double height = 3.0;
        int floorNumber = 1;
        String houseId = "houseId1";
        boolean isInside = true;
        String roomName = "Living Room";

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        AddNewRoomToHouseController myController = new AddNewRoomToHouseController(serviceRoom);

        repositoryHouse.save(getHouse());

        when(mockId.generateID()).thenReturn(roomId);

        RoomDTO roomDTO = new RoomDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);
        myController.addRoomToHouse(roomDTO);
        String expectedMessage = "Room already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> myController.addRoomToHouse(roomDTO));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}
