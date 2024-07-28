package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetRoomsController;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.house.ImplFactoryHouse;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.RoomDTO;
import smartHomeDDD.persistence.mem.RepositoryHouseMem;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceRoom;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Test class for US03GetRoomsController.
 * It contains the following tests:
 * - nullRepositoryRoom_ShouldThrowIllegalArgumentException
 * - houseWithNoRooms_ShouldGiveEmptyList
 * - houseWithRooms_ShouldGiveList
 * - nonExistingHouse_ShouldThrowIllegalArgumentException
 */

class GetRoomsControllerTest {

    private static House getHouse() {
        Latitude latitude = new Latitude(38.74777);
        Longitude longitude = new Longitude(-9.23098);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        ZipCode zipCode = new ZipCode("Portugal", "4935-054");
        Address address = new Address("adress");
        Location location = new Location(address, zipCode, gpsCoordinates);
        HouseId houseId = new HouseId("1");
        FactoryHouse factoryHouse = new ImplFactoryHouse();

        return factoryHouse.createHouse(houseId, location);
    }

    /**
     * Tests the constructor of US03GetRoomsController when the repositoryRoom is null.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void nullServiceRoom_ShouldThrowException() {
        //Arrange
        ServiceRoom serviceRoom = null;
        String expectedMessage = "Service cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GetRoomsController(serviceRoom));
        String actualMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Tests the listRoomsInHouse() method when the house has no rooms.
     * It verifies that the method returns an empty list in this scenario.
     */
    @Test
    void houseWithNoRooms_ShouldGiveEmptyList() {
        // Arrange
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();

        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);

        GetRoomsController GetRoomsController = new GetRoomsController(serviceRoom);

        // Create a house and save it in the repository to be able to get the rooms
        House house = getHouse();
        repositoryHouse.save(house);

        // String to be used as the house ID
        String strHouseId = "1";

        // Act
        List<RoomDTO> result = GetRoomsController.getRooms(strHouseId);

        // Assert
        assertEquals(0, result.size());

    }

    /**
     * Tests the listRoomsInHouse() method when the house has rooms.
     * It verifies that the method returns a list of rooms in this scenario.
     */
    @Test
    void houseWithRooms_ShouldGiveList() {
        // Arrange
        HouseId houseId = new HouseId("1");

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        Room room1 = factoryRoom.createRoom(houseId, new RoomID("room1"), new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, new RoomName("Living Room"));
        Room room2 = factoryRoom.createRoom(houseId, new RoomID("room2"), new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, new RoomName("Kitchen"));

        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        repositoryRoom.save(room1);
        repositoryRoom.save(room2);

        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();

        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceRoom serviceRoom = new ServiceRoom(factoryRoom, repositoryRoom, repositoryHouse, mockId);
        GetRoomsController GetRoomsController = new GetRoomsController(serviceRoom);

        // Create a house and save it in the repository to be able to get the rooms
        House house = getHouse();
        repositoryHouse.save(house);

        // String to be used as the house ID
        String strHouseId = "1";

        // Act
        List<RoomDTO> result = GetRoomsController.getRooms(strHouseId);

        // Assert
        assertEquals(2, result.size());

    }

}