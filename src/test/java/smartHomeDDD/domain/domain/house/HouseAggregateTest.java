package smartHomeDDD.domain.domain.house;

import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.house.ImplFactoryHouse;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HouseAggregateTest is a class containing unit tests for the HouseAggregate class.
 * It ensures the correct behavior of House instantiation, including validation of
 * house ID and location, as well as the accuracy of methods like identity().
 * These tests cover various scenarios including valid arguments, invalid house IDs,
 * and invalid locations, ensuring robustness and correctness in HouseAggregate behavior.
 */
class HouseAggregateTest {

    /**
     * Test to ensure that a valid House instance can be created with a non-null house ID and location.
     */
    @Test
    void validHouse_shouldInstantiateHouse() {
        // Arrange
        ImplFactoryHouse implFactoryHouse = new ImplFactoryHouse();
        HouseId houseId = new HouseId("House01");
        Address address = new Address("Rua das Flores");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location = new Location(address, zipCode, gpsCoordinates);
        // Act
        House myHouse = implFactoryHouse.createHouse(houseId, location);
        //Assert
        assertNotNull(myHouse);
        assertEquals(location.toString(), myHouse.getHouseLocation().toString());
    }


    /**
     * Test to verify that the identity() method returns the correct house ID of the house instance.
     */
    @Test
    void shouldReturnHouseId_WhenGettingIdentity() {
        // Arrange
        ImplFactoryHouse factoryHouse = new ImplFactoryHouse();
        HouseId houseIdExpected = new HouseId("House01");
        Address address = new Address("Rua das Flores");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location = new Location(address, zipCode, gpsCoordinates);



        // Act
        House myHouse = factoryHouse.createHouse(houseIdExpected, location);
        String houseIdActual = myHouse.identity().toString();
        // Assert
        assertEquals(houseIdExpected.toString(), houseIdActual);
    }

    /**
     * Test to ensure that the sameAs method returns true when given the same object.
     */
    @Test
    void comparingTheSameHouse_shouldReturnTrue() {
        // Arrange
        ImplFactoryHouse implFactoryHouse = new ImplFactoryHouse();
        HouseId houseIdExpected = new HouseId("House01");
        Address address = new Address("Rua das Flores");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location = new Location(address, zipCode, gpsCoordinates);
        House myHouse1 = implFactoryHouse.createHouse(houseIdExpected, location);
        // Act
        boolean sameObject = myHouse1.sameAs(myHouse1);
        // Assert
        assertTrue(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given the different object.
     */
    @Test
    void nonObjectHouse_shouldReturnFalse() {
        // Arrange
        ImplFactoryHouse implFactoryHouse = new ImplFactoryHouse();
        HouseId houseIdExpected = new HouseId("House01");
        Address address = new Address("Rua das Flores");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location = new Location(address, zipCode, gpsCoordinates);
        House myHouse1 = implFactoryHouse.createHouse(houseIdExpected, location);
        // Act
        Object obj = new Object();
        boolean sameObject = myHouse1.sameAs(obj);
        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns true when given two equal houses, with different ids.
     */
    @Test
    void comparingTwoIdenticalHousesWithDifferentIds_shouldReturnFalse() {
        // Arrange
        ImplFactoryHouse implFactoryHouse = new ImplFactoryHouse();
        HouseId houseIdExpected = new HouseId("House01");
        HouseId houseIdExpected2 = new HouseId("House02");
        Address address = new Address("Rua das Flores");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location = new Location(address, zipCode, gpsCoordinates);
        House myHouse1 = implFactoryHouse.createHouse(houseIdExpected, location);
        House myHouse2 = implFactoryHouse.createHouse(houseIdExpected2, location);
        // Act
        boolean sameObject = myHouse1.sameAs(myHouse2);
        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two houses with different locations.
     */
    @Test
    void comparingTwoHousesWithDifferentLocation_shouldReturnFalse() {
        // Arrange
        ImplFactoryHouse implFactoryHouse = new ImplFactoryHouse();
        HouseId houseIdExpected = new HouseId("House01");
        Address address = new Address("Rua das Flores");
        Address address2 = new Address("Rua das Gramíneas");
        ZipCode zipCode = new ZipCode("Portugal", "1234-123");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(90);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);
        Location location1 = new Location(address, zipCode, gpsCoordinates);
        Location location2 = new Location(address2, zipCode, gpsCoordinates);
        House myHouse1 = implFactoryHouse.createHouse(houseIdExpected, location1);
        House myHouse2 = implFactoryHouse.createHouse(houseIdExpected, location2);
        // Act
        boolean sameObject = myHouse1.sameAs(myHouse2);
        // Assert
        assertFalse(sameObject);
    }


}
