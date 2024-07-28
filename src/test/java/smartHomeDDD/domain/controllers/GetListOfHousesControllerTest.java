package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetListOfHousesController;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.house.ImplFactoryHouse;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.HouseDTO;
import smartHomeDDD.persistence.mem.RepositoryHouseMem;
import smartHomeDDD.services.ServiceHouse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible for testing the GetListOfHousesController.
 * It verifies the correct behavior of the controller methods under different scenarios.
 * The scenarios include:
 * - When the service is null
 * - When the repository is empty
 * - When the repository has houses
 * - When saving a null house
 * - When saving an existing house
 * - When updating a non-existing house
 * - When retrieving a house by its identity
 */

class GetListOfHousesControllerTest {
    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void nullServiceHouse_ShouldThrowIllegalArgumentException() {
        // Arrange
        String expectedMessage = "Service cannot be null.";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GetListOfHousesController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    /**
     * Test case to verify that the list of houses is returned when the repository has houses.
     */

    @Test
    void repositoryHouseWithHouses_ShouldReturnListHousesSaved() {
        // Arrange

        ImplFactoryHouse factoryHouse = new ImplFactoryHouse();
        HouseId houseId1 = new HouseId("House01");
        Address address = new Address("Rua das Flores");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location1 = new Location(address, zipCode, gpsCoordinates);

        HouseId houseId2 = new HouseId("House02");
        Address address2 = new Address("Rua dos Arbustos");
        ZipCode zipCode2 = new ZipCode("Portugal", "1245-321");
        Latitude latitude2 = new Latitude(50);
        Longitude longitude2 = new Longitude(80);
        GPSCoordinates gpsCoordinates2 = new GPSCoordinates(latitude2, longitude2);
        Location location2 = new Location(address2, zipCode2, gpsCoordinates2);

        House myHouse1 = factoryHouse.createHouse(houseId1, location1);
        House myHouse2 = factoryHouse.createHouse(houseId2, location2);

        IRepositoryHouse repositoryHouse = new RepositoryHouseMem();
        repositoryHouse.save(myHouse1);
        repositoryHouse.save(myHouse2);

        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());

        GetListOfHousesController getHousesController = new GetListOfHousesController(serviceHouse);

        // Act
        List<HouseDTO> result = getHousesController.getHouses();

        // Assert
        assertEquals(2, result.size());
    }


}