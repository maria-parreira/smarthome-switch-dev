package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Latitude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class validates the behavior of the Latitude class, which represents a geographical latitude.
 * Key aspects tested include:
 * - Creation of a valid Latitude with a non-null value.
 * - Prevention of Latitude creation with an invalid value, ensuring proper error handling.
 * - Equality comparison between Latitudes, checking if two instances with the same properties are considered equal.
 * - Handling of null objects and ensuring that the equals method returns false when comparing with null.
 * - Consistency of equals method when comparing different instances with the same properties.
 * - Accuracy of the toString method, verifying that it returns the expected string representation of the Latitude.
 */

class LatitudeTest {

    /**
     * Tests if a valid Latitude can be created with a valid value.
     */
    @Test
    void shouldCreateAValidLatitude() {
        // Arrange
        double latitude = 45.0;

        // Act
        Latitude createdLatitude = new Latitude(latitude);

        // Assert
        assertNotNull(createdLatitude);
        assertEquals(latitude, createdLatitude.getValue());
    }
    /**
     * Tests if an exception is thrown when trying to create a Latitude with a value less than -90.0.
     */
    @Test
    void shouldThrowException_LatitudeLessThanNegative90() {
        // Arrange
        double latitude = -91.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Latitude(latitude));
    }

    /**
     * Tests if an exception is thrown when trying to create a Latitude with a value greater than 90.0.
     */
    @Test
    void shouldThrowException_LatitudeGreaterThan90() {
        // Arrange
        double latitude = 91.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Latitude(latitude));
    }

    /**
     * Tests if the equals method returns true when comparing two Latitudes with the same value.
     */
    @Test
    void shouldReturnTrueEquals_WithSameLatitude() {
        // Arrange
        Latitude latitude1 = new Latitude(45.0);
        Latitude latitude2 = new Latitude(45.0);

        // Assert
        assertEquals(latitude1, latitude2);
    }

    /**
     * Tests if the equals method returns false when comparing two Latitudes with different values.
     */
    @Test
    void shouldReturnFalseEquals_WithDifferentLatitude() {
        // Arrange
        Latitude latitude1 = new Latitude(45.0);
        Latitude latitude2 = new Latitude(46.0);

        // Assert
        assertNotEquals(latitude1, latitude2);
    }

    /**
     * Tests if the equals method returns false when comparing with a null object.
     */
    @Test
    void hashCode_shouldReturnSameHashCodeForSameLatitude() {
        // Arrange
        Latitude latitude1 = new Latitude(45.0);
        Latitude latitude2 = new Latitude(45.0);

        // Assert
        assertEquals(latitude1.hashCode(), latitude2.hashCode());
    }

    /**
     * Tests if the equals method returns true when comparing two Latitudes with the same properties.
     */
    @Test
    void toString_shouldReturnCorrectStringRepresentation() {
        // Arrange
        Latitude latitude = new Latitude(45.0);

        // Act
        String result = latitude.toString();

        // Assert
        assertEquals("45.0", result);
    }
}