package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Longitude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This test class validates the behavior of the Longitude class, which represents a geographical longitude.
 * Key aspects tested include:
 * - Creation of a valid Longitude with a non-null value.
 * - Prevention of Longitude creation with an invalid value, ensuring proper error handling.
 * - Equality comparison between Longitudes, checking if two instances with the same properties are considered equal.
 * - Handling of null objects and ensuring that the equals method returns false when comparing with null.
 * - Consistency of equals method when comparing different instances with the same properties.
 * - Accuracy of the toString method, verifying that it returns the expected string representation of the Longitude.
 */
class LongitudeTest {

    /**
     * Tests if a valid Longitude can be created with a valid value.
     */
    @Test
    void shouldCreateAValidLongitude() {
        // Arrange
        double longitude = 45.0;

        // Act
        Longitude createdLongitude = new Longitude(longitude);

        // Assert
        assertNotNull(createdLongitude);
        assertEquals(longitude, createdLongitude.getValue());
    }

    /**
     * Tests if an exception is thrown when trying to create a Longitude with a value less than -180.0.
     */
    @Test
    void shouldThrowException_LongitudeLessThanNegative180() {
        // Arrange
        double longitude = -181.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Longitude(longitude));
    }

    /**
     * Tests if an exception is thrown when trying to create a Longitude with a value greater than 180.0.
     */
    @Test
    void shouldThrowException_LongitudeGreaterThan180() {
        // Arrange
        double longitude = 181.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Longitude(longitude));
    }

    /**
     * Tests if the equals method returns true when comparing two Longitudes with the same value.
     */
    @Test
    void shouldReturnTrueEquals_WithSameLongitude() {
        // Arrange
        Longitude longitude1 = new Longitude(45.0);
        Longitude longitude2 = new Longitude(45.0);

        // Assert
        assertEquals(longitude1, longitude2);
    }

    /**
     * Tests if the equals method returns false when comparing two Longitudes with different values.
     */
    @Test
    void shouldReturnFalseEquals_WithDifferentLongitude() {
        // Arrange
        Longitude longitude1 = new Longitude(45.0);
        Longitude longitude2 = new Longitude(46.0);

        // Assert
        assertNotEquals(longitude1, longitude2);
    }

    /**
     * Tests if the equals method returns false when comparing with a null object.
     */
    @Test
    void hashCode_shouldReturnSameHashCodeForSameLongitude() {
        // Arrange
        Longitude longitude1 = new Longitude(45.0);
        Longitude longitude2 = new Longitude(45.0);

        // Assert
        assertEquals(longitude1.hashCode(), longitude2.hashCode());
    }

    /**
     * Tests if the equals method returns false when comparing with a null object.
     */
    @Test
    void toString_shouldReturnCorrectStringRepresentation() {
        // Arrange
        Longitude longitude = new Longitude(45.0);

        // Act
        String result = longitude.toString();

        // Assert
        assertEquals("45.0", result);
    }
}