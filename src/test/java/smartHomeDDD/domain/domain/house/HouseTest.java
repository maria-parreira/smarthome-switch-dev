package smartHomeDDD.domain.domain.house;


import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for the House class, which represents a residential property identified by a unique house ID
 * and a location. Key aspects tested include:
 * - Creation of a house instance with a valid house ID and location.
 * - Creation of a house instance with a valid house ID or location.
 * - Retrieval of the house ID through the identity() method, ensuring correct return value.
 * - Comparison of houses using the sameAs() method:
 * - Verifying that houses with different IDs are not considered equal.
 * - Ensuring consistent behavior when comparing houses with varying ID and location combinations.
 * - Verifying that different object types are not considered equal to a house.
 * - Ensuring configuration and retrieval of house location.
 * - Verifying that the hashCode method returns the same value for two equal houses.
 */
class HouseTest {


    /**
     * Test to ensure that a valid House instance can be created with a non-null house ID and location.
     */
    @Test
    void validArguments_shouldInstantiateHouse() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);

        //Act
        House house = new House(houseIdDouble,locationDouble);

        // Act+Assert
        assertNotNull(house);
    }


    /**
     * Test to verify that the identity() method returns the correct house ID of the house instance.
     */
    @Test
    void shouldReturnHouseId_WhenGettingIdentity(){
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);
        House house = new House(houseIdDouble,locationDouble);

        // Act
        HouseId houseId = house.identity();

        // Assert
        assertEquals(houseIdDouble, houseId);
    }


    /**
     * Test to ensure that two houses with the same ID and location are considered equal by the sameAs() method.
     */
    @Test
    void SameAs_shouldReturnTrue_WhenComparingTwoEqualHouses(){
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);
        House house = new House(houseIdDouble,locationDouble);
        House house1 = new House(houseIdDouble,locationDouble);

        // Act
        boolean isSame = house.sameAs(house1);

        // Assert
        assertTrue(isSame);
    }

    /**
     * Test to ensure that two houses with different IDs or locations are not considered equal by the sameAs() method.
     */
    @Test
    void SameAs_shouldReturnFalse_WhenComparingTwoDiffHouses(){
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);
        House house = new House(houseIdDouble,locationDouble);

        HouseId houseIdDouble1 = mock(HouseId.class);
        Location locationDouble1 = mock(Location.class);
        House house1 = new House(houseIdDouble1,locationDouble1);

        // Act
        boolean isSame = house.sameAs(house1);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that houses with different IDs but equal locations are not considered equal by the sameAs() method.
     */
    @Test
    void shouldReturnFalse_WhenComparingHouses_DiffId_SameLocation_SameAs(){
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);
        House house = new House(houseIdDouble,locationDouble);

        HouseId houseIdDouble1 = mock(HouseId.class);
        House house1 = new House(houseIdDouble1,locationDouble);

        // Act
        boolean isSame = house.sameAs(house1);

        // Assert
        assertFalse(isSame);
    }

    /**
     * This test verifies that the location of a house can be configured correctly.
     * It creates a house object with a specified location, updates the location,
     * and checks whether the new location is properly set.
     *
     */
    @Test
    void shouldConfigureLocation_shouldReturnHouseWithNewLocation(){
        //Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);
        House house = new House(houseIdDouble,locationDouble); //create house with location

        Location locationDoubleNew = mock(Location.class); //create new location

        //Act
        Location location = house.getHouseLocation();
        house.configureLocation(locationDoubleNew); //add new location to house
        Location locationNew = house.getHouseLocation();

        //Assert
        assertEquals(locationDouble,location); //house with initial location
        assertEquals(locationDoubleNew,locationNew); //house with new location after configure
    }

    /**
     * This test verifies that the hashCode method returns the same value for two equal houses.
     */
    @Test
    void hashCode_SameHouse_ShouldReturnSameHashCode(){
        //Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);
        House house = new House(houseIdDouble,locationDouble); //create house with location

        House house1 = new House(houseIdDouble,locationDouble); //create house with location

        //Act
        int hashCode = house.hashCode();
        int hashCode1 = house1.hashCode();

        //Assert
        assertEquals(hashCode,hashCode1);
    }

    @Test
    void testEquals() {
        // Arrange
        HouseId houseId1 = mock(HouseId.class);
        HouseId houseId2 = mock(HouseId.class);
        Location location = mock(Location.class);

        House house1 = new House(houseId1, location);
        House house2 = new House(houseId1, location);
        House house3 = new House(houseId2, location);

        // Act & Assert
        // Scenario 1: Comparing different instances with the same houseId
        assertEquals(house1, house2);

        // Scenario 2: Comparing instances with different houseId
        assertNotEquals(house1, house3);

        // Scenario 3: Comparing with a non-House object
        assertNotEquals(house1, new Object());
    }

    @Test
    void testHashCode() {
        // Arrange
        HouseId houseId = mock(HouseId.class);
        Location location = mock(Location.class);

        House house = new House(houseId, location);

        // Act
        int hashCode = house.hashCode();

        // Assert
        assertNotEquals(0, hashCode);
    }
}
