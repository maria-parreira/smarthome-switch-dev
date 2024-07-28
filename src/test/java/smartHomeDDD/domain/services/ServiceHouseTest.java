package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.house.ImplFactoryHouse;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.services.ServiceHouse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is responsible for unit testing the ServiceHouse class. It verifies the following use cases:
 * - Successful creation of a ServiceHouse object
 * - Handling of null parameters in the constructor
 * - Configuration of the location of a house
 * - Handling of a scenario where location configuration is attempted for a non-existent house
 * - Retrieval of a list of houses
 * - Retrieval of a house using its unique ID
 * - Handling of a scenario where retrieval of a house is attempted with a non-existent ID
 * - Addition of a new house
 */
class ServiceHouseTest {

    /**
     * This method tests the successful creation of a ServiceHouse object.
     */
    @Test
    void testServiceHouseConstructor_shouldConstructServiceObject() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();

        // Act
        ServiceHouse result = new ServiceHouse(repoHouse, factoryHouse);

        // Assert
        assertNotNull(result);
    }

    /**
     * This method tests the handling of a null repository in the ServiceHouse constructor. It should throw an IllegalArgumentException.
     */
    @Test
    void testServiceHouseConstructorWithNullRepoHouse_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryHouse factoryHouse = new ImplFactoryHouse();

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceHouse(null, factoryHouse));
    }

    /**
     * This method tests the handling of a null factory in the ServiceHouse constructor. It should throw an IllegalArgumentException.
     */
    @Test
    void testServiceHouseConstructorWithNullFactoryHouse_shouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceHouse(repoHouse, null));
    }

    /**
     * This method tests the configuration of the location of a house. It should return the updated house.
     */
    @Test
    void testConfigureLocation() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        ServiceHouse serviceHouse = new ServiceHouse(repoHouse, factoryHouse);
        HouseId id = new HouseId("1");
        Location newLocation = new Location(new Address("New Address"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55), new Longitude(60)));
        House house = new House(id, newLocation);
        when(repoHouse.ofIdentity(id)).thenReturn(Optional.of(house));
        when(repoHouse.update(house)).thenReturn(house);

        // Act
        House result = serviceHouse.configureLocation(newLocation, id);

        // Assert
        assertEquals(house, result);
    }

    /**
     * This method tests the handling of a scenario where location configuration is attempted for a non-existent house. It should throw an EntityNotFoundException.
     */
    @Test
    void testConfigureLocationWithNoHouse_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        ServiceHouse serviceHouse = new ServiceHouse(repoHouse, factoryHouse);
        HouseId id = new HouseId("1");
        Location newLocation = new Location(new Address("New Address"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55), new Longitude(60)));
        when(repoHouse.ofIdentity(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceHouse.configureLocation(newLocation, id));
    }

    /**
     * This method tests the retrieval of a list of houses. It should return a list of houses.
     */
    @Test
    void testListOfHouses() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        ServiceHouse serviceHouse = new ServiceHouse(repoHouse, factoryHouse);
        House house1 = new House(new HouseId("1"), new Location(new Address("Address 1"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55), new Longitude(60))));
        House house2 = new House(new HouseId("2"), new Location(new Address("Address 2"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55), new Longitude(60))));
        when(repoHouse.findAll()).thenReturn(List.of(house1, house2));

        // Act
        List<House> result = serviceHouse.listOfHouses();
        List<House> houses = List.of(house1, house2);

        // Assert
        assertEquals(houses, result);
    }

    /**
     * This method tests the retrieval of a house by its ID. It should return the house.
     */
    @Test
    void testGetHouseById() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        ServiceHouse serviceHouse = new ServiceHouse(repoHouse, factoryHouse);
        HouseId id = new HouseId("1");
        House house = new House(id, new Location(new Address("New Address"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55), new Longitude(60))));
        when(repoHouse.ofIdentity(id)).thenReturn(Optional.of(house));

        // Act
        House result = serviceHouse.getHouseById(id);

        // Assert
        assertEquals(house, result);
    }

    /**
     * This method tests the handling of a scenario where retrieval of a house is attempted with a non-existent ID. It should throw an EntityNotFoundException.
     */
    @Test
    void testGetHouseByIdWithNoHouse_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        ServiceHouse serviceHouse = new ServiceHouse(repoHouse, factoryHouse);
        HouseId id = new HouseId("1");
        when(repoHouse.ofIdentity(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceHouse.getHouseById(id));
    }

    /**
     * This method tests the addition of a new house. It should return the newly added house.
     */
    @Test
    void testAddHouse() {
        // Arrange
        IRepositoryHouse repoHouse = mock(IRepositoryHouse.class);
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        ServiceHouse serviceHouse = new ServiceHouse(repoHouse, factoryHouse);
        HouseId id = new HouseId("1");
        Location location = new Location(new Address("New Address"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55), new Longitude(60)));
        House house = factoryHouse.createHouse(id, location);
        when(repoHouse.save(house)).thenReturn(house);

        // Act
        House result = serviceHouse.addHouse(id, location);

        // Assert
        assertEquals(house, result);
    }
}