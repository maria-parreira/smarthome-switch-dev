package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.SensorID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SensorIDTest class provides unit tests for the SensorID class.
 * It encompasses the following scenarios:
 * - Creating a valid sensor ID
 * - Creating a sensor ID with invalid input
 * - Creating a sensor ID with a null ID
 * - Creating a sensor ID with an empty ID
 * - Checking the equality of a sensor ID with null
 * - Checking the equality of a sensor ID with itself
 * - Checking the equality of two sensor IDs with the same ID
 * - Checking the equality of two sensor IDs with different IDs
 * - Getting the string representation of a sensor ID
 */

class SensorIDTest {

    /**
     * Test case for creating a valid sensor ID.
     * The test verifies that the SensorID constructor returns a non-null object when provided with a valid ID.
     */
    @Test
    void validSensorID_ShouldCreateAValidSensorID() throws IllegalArgumentException {
        // Arrange
        String id = "sensor1";

        // Act
        SensorID sensorID = new SensorID(id);

        // Assert
        assertNotNull(sensorID);
    }

    /**
     * Test case for creating a sensor ID with invalid input.
     * The test verifies that the SensorID constructor throws an IllegalArgumentException when provided with an empty or whitespace-only ID.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidSensorID_ShouldThrowException(String id) throws IllegalArgumentException{

        // Arrange
        String expectedMessage = "Sensor ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorID(id);
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for creating a sensor ID with a null ID.
     * The test verifies that the SensorID constructor throws an IllegalArgumentException when provided with a null ID.
     */
    @Test
    void sensorIDWithNullId_ShouldThrowException() throws IllegalArgumentException{

        // Arrange
        String expectedMessage = "Sensor ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorID(null);
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for creating a sensor ID with an empty ID.
     * The test verifies that the SensorID constructor throws an IllegalArgumentException when provided with an empty ID.
     */
    @Test
    void sensorIDWithEmptyId_ShouldThrowException() throws IllegalArgumentException {

        // Arrange
        String expectedMessage = "Sensor ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorID("");
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for checking the equality of a sensor ID with null.
     * The test verifies that the equals method of the SensorID class returns false when the sensor ID is compared with null.
     */
    @Test
    void nullObject_ShouldReturnFalseEquals() {
        // Arrange
        SensorID sensorID = new SensorID("sensor1");

        // Act
        boolean isEquals = sensorID.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for checking the equality of a sensor ID with itself.
     * The test verifies that the equals method of the SensorID class returns true when the sensor ID is compared with itself.
     */
    @Test
    void withSameObject_ShouldReturnTrueEquals() {
        // Arrange
        SensorID sensorID = new SensorID("sensor1");

        // Act
        boolean isEquals = sensorID.equals(sensorID);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test case for checking the equality of two sensor IDs with the same ID.
     * The test verifies that the equals method of the SensorID class returns true when two sensor IDs with the same ID are compared.
     */
    @Test
    void objectWithSameIds_ShouldReturnTrueEquals() {
        // Arrange
        SensorID sensorID1 = new SensorID("sensor1");
        SensorID sensorID2 = new SensorID("sensor1");

        // Act
        boolean isEquals = sensorID1.equals(sensorID2);

        // Assert
        assertTrue(isEquals);
    }


    /**
     * Test case for checking the equality of two sensor IDs with different IDs.
     * The test verifies that the equals method of the SensorID class returns false when two sensor IDs with different IDs are compared.
     */
    @Test
    void objectWithDifferentIds_ShouldReturnFalseEquals() {
        // Arrange
        SensorID sensorID1 = new SensorID("sensor1");
        SensorID sensorID2 = new SensorID("sensor2");

        // Act
        boolean isEquals = sensorID1.equals(sensorID2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for getting the string representation of a sensor ID.
     * The test verifies that the toString method of the SensorID class returns the correct ID string.
     */
    @Test
    void toString_ShouldReturnTheIdString() {
        // Arrange
        SensorID sensorID = new SensorID("sensor1");

        // Act
        String actual = sensorID.toString();

        // Assert
        assertEquals("sensor1", actual);
    }

    @Test
    void hashCode_ShouldReturnTheHashCode() {
        // Arrange
        SensorID sensorID = new SensorID("sensor1");
        // Act
        int actual = sensorID.hashCode();
        // Assert
        assertEquals("sensor1".hashCode(), actual);
    }
}

