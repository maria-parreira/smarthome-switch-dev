package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the Location class, which encapsulates a geographical location
 * characterized by an address, a zip code, and GPS coordinates. The tests cover the following aspects:
 * - Construction of a Location object with valid address, zip code, and GPS coordinates.
 * - Validation that the constructor prevents creation of a Location object with null address, zip code, or GPS coordinates.
 * - Verification of the equals method, ensuring it correctly identifies equal and unequal Location objects.
 * - Validation of the equals method's handling of null and different type objects.
 * - Verification of the consistency of the equals method when comparing different Location instances with identical properties.
 * - Verification of the toString method, ensuring it returns a string representation of the Location object as expected.
 */
class LocationTest {


    /**
     * Tests if a valid location can be created with valid address and zip code.
     */
    @Test
    void validLocation_shouldCreateLocation() {
        // Arrange
        Address addressDouble = mock(Address.class);
        ZipCode zipCodeDouble = mock(ZipCode.class);
        GPSCoordinates gpsCoordinatesDouble = mock(GPSCoordinates.class);

        // Act
        Location location = new Location(addressDouble,zipCodeDouble,gpsCoordinatesDouble);

        // Assert
        assertNotNull(location);
    }

    /**
     * Tests if the equals method returns true when comparing the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Address addressDouble = mock(Address.class);
        ZipCode zipCodeDouble = mock(ZipCode.class);
        GPSCoordinates gpsCoordinatesDouble = mock(GPSCoordinates.class);
        Location location1 = new Location(addressDouble, zipCodeDouble,gpsCoordinatesDouble);
        Location location2 = new Location(addressDouble, zipCodeDouble,gpsCoordinatesDouble);

        // Act & Assert
        assertEquals(location1, location2);
    }

    /**
     * Tests if the equals method returns false when comparing with a null object.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        Address addressDouble = mock(Address.class);
        ZipCode zipCodeDouble = mock(ZipCode.class);
        GPSCoordinates gpsCoordinatesDouble = mock(GPSCoordinates.class);
        Location location = new Location(addressDouble, zipCodeDouble,gpsCoordinatesDouble);

        // Act & Assert
        assertNotEquals(null, location);
    }

    /**
     * Tests if the equals method returns false when comparing two objects with different properties.
     */
    @Test
    void differentObjectDifferentLocation_shouldReturnFalse(){
        // Arrange
        Address addressDouble1 = mock(Address.class);
        ZipCode zipCodeDouble1 = mock(ZipCode.class);
        GPSCoordinates gpsCoordinatesDouble1 = mock(GPSCoordinates.class);
        Location location1 = new Location(addressDouble1, zipCodeDouble1,gpsCoordinatesDouble1);

        Address addressDouble = mock(Address.class);
        ZipCode zipCodeDouble = mock(ZipCode.class);
        GPSCoordinates gpsCoordinatesDouble = mock(GPSCoordinates.class);
        Location location = new Location(addressDouble, zipCodeDouble,gpsCoordinatesDouble);

        // Act & Assert
        assertNotEquals(location1, location);
    }

    /**
     * Tests if the toString method returns the correct representation of the location.
     */
    @Test
    void toString_shouldReturnLocation() {
        // Arrange
        Address addressDouble = mock(Address.class);
        when(addressDouble.toString()).thenReturn("street");

        ZipCode zipCodeDouble = mock(ZipCode.class);
        when(zipCodeDouble.toString()).thenReturn("123-456");

        GPSCoordinates gpsCoordinatesDouble = mock(GPSCoordinates.class);
        when(gpsCoordinatesDouble.toString()).thenReturn("55 130");

        String expected = "street 123-456 55 130";

        // Act
        Location location = new Location(addressDouble, zipCodeDouble,gpsCoordinatesDouble);
        String actual = location.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test case to verify the toString() method of the Location class.
     * It ensures that the toString() method returns the concatenated string
     * representation of the address, zip code, and GPS coordinates of the location.
     */

    @Test
    void toString_shouldReturnAddressZipCodeGPS() {
        // Arrange
        Address addressDouble = mock(Address.class);
        when(addressDouble.toString()).thenReturn("street");

        ZipCode zipCodeDouble = mock(ZipCode.class);
        when(zipCodeDouble.toString()).thenReturn("123-456");

        GPSCoordinates gpsCoordinatesDouble = mock(GPSCoordinates.class);
        when(gpsCoordinatesDouble.toString()).thenReturn("55 130");

        String expected = "street 123-456 55 130";

        // Act
        Location location = new Location(addressDouble, zipCodeDouble,gpsCoordinatesDouble);
        String actual = location.toString();

        // Assert
        assertEquals(expected, actual);
        assertEquals("street",location.getAddress().toString());
        assertEquals("123-456",location.getZipCode().toString());
        assertEquals("55 130",location.getGPSCoordinates().toString());
    }

    /**
     * Test to ensure that the equals method of the Location class returns true when comparing two Location objects with the same properties.
     * This test creates two Location objects with the same properties and checks if they are equal.
     * If the equals method is implemented correctly, the test will pass when the two Location objects are equal.
     */
    @Test
    void equals_SameProperties_ShouldReturnTrue() {
        // Arrange
        Address address = new Address("123 Street");
        ZipCode zipCode = new ZipCode("Portugal", "4400-000");
        Latitude latitude = new Latitude(45.0);
        Longitude longitude = new Longitude(90.0);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        Location location1 = new Location(address, zipCode, gpsCoordinates);
        Location location2 = new Location(address, zipCode, gpsCoordinates);

        // Act
        boolean isEqual = location1.equals(location2);

        // Assert
        assertTrue(isEqual);
    }

    /**
     * Test to ensure that the equals method of the Location class returns false when comparing two Location objects with different properties.
     * This test creates two Location objects with different properties and checks if they are equal.
     * If the equals method is implemented correctly, the test will pass when the two Location objects are not equal.
     */
    @Test
    void equals_DifferentProperties_ShouldReturnFalse() {
        // Arrange
        Address address1 = new Address("123 Street");
        ZipCode zipCode1 = new ZipCode("Portugal", "4400-000");
        Latitude latitude = new Latitude(45.0);
        Longitude longitude = new Longitude(90.0);
        GPSCoordinates gpsCoordinates1 = new GPSCoordinates(latitude, longitude);

        Address address2 = new Address("456 Avenue");
        ZipCode zipCode2 = new ZipCode("Portugal", "4500-000");
        Latitude latitude2 = new Latitude(90.0);
        Longitude longitude2 = new Longitude(180.0);
        GPSCoordinates gpsCoordinates2 = new GPSCoordinates(latitude2, longitude2);

        Location location1 = new Location(address1, zipCode1, gpsCoordinates1);
        Location location2 = new Location(address2, zipCode2, gpsCoordinates2);

        // Act
        boolean isEqual = location1.equals(location2);

        // Assert
        assertFalse(isEqual);
    }


}

