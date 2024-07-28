package smartHomeDDD.domain.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import smartHomeDDD.domain.valueobject.SensorReadingID;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The SensorReadingIDTest class provides unit tests for the SensorReadingID class.
 * It encompasses the following scenarios:
 * - Creating a valid sensor reading ID
 * - Creating a sensor reading ID with invalid input
 * - Creating a sensor reading ID with a null ID
 * - Creating a sensor reading ID with an empty ID
 * - Checking the equality of a sensor reading ID with null
 * - Checking the equality of a sensor reading ID with itself
 * - Checking the equality of two sensor reading IDs with the same ID
 * - Checking the equality of two sensor reading IDs with different IDs
 * - Getting the string representation of a sensor reading ID
 */

public class SensorReadingIDTest {

    /**
     * Test case for creating a valid sensor reading ID.
     * The test verifies that the SensorReadingID constructor returns a non-null object when provided with a valid ID.
     */
    @Test
    void validSensorReadingID_ShouldCreateAValidSensorReadingID() throws IllegalArgumentException {
        // Arrange
        String id = "sensorReading1";
        // Act
        SensorReadingID sensorReadingID = new SensorReadingID(id);
        // Assert
        assertNotNull(sensorReadingID);
    }

    /**
     * Test case for creating a sensor reading ID with invalid input.
     * The test verifies that the SensorReadingID constructor throws an IllegalArgumentException when provided with an empty or whitespace-only ID.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidReadingSensorID_ShouldThrowException(String id) throws IllegalArgumentException {

        // Arrange
        String expectedMessage = "Log ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorReadingID(id);
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for creating a sensor readingID with a null ID.
     * The test verifies that the SensorReadingID constructor throws an IllegalArgumentException when provided with a null ID.
     */
    @Test
    void sensorReadingIDWithNullId_ShouldThrowException() throws IllegalArgumentException {
        // Arrange
        String expectedMessage = "Log ID cannot be null or empty";
        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorReadingID(null);
        });
        String actualMessage = exception.getMessage();
        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for checking the equality of a sensor reading ID with null.
     * The test verifies that the equals method of the SensorReadingID class returns false when the sensor ID is compared with null.
     */
    @Test
    void nullObject_ShouldReturnFalseEquals() {
        // Arrange
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReading1");

        // Act
        boolean isEquals = sensorReadingID.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for checking the equality of a sensor reading ID with itself.
     * The test verifies that the equals method of the SensorID class returns true when the sensor reading ID is compared with itself.
     */
    @Test
    void withSameObject_ShouldReturnTrueEquals() {
        // Arrange
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReading1");
        // Act
        boolean isEquals = sensorReadingID.equals(sensorReadingID);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test case for checking the equality of two sensor reading IDs with the same ID.
     * The test verifies that the equals method of the SensorReadingID class returns true when two sensor reading IDs with the same ID are compared.
     */
    @Test
    void objectWithSameIds_ShouldReturnTrueEquals() {
        // Arrange
        SensorReadingID sensorReadingID1 = new SensorReadingID("sensorReading1");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sensorReading1");
        // Act
        boolean isEquals = sensorReadingID1.equals(sensorReadingID2);
        // Assert
        assertTrue(isEquals);
    }


    /**
     * Test case for checking the equality of two sensor reading IDs with different IDs.
     * The test verifies that the equals method of the SensorReadingID class returns false when two sensor reading IDs with different IDs are compared.
     */
    @Test
    void objectWithDifferentIds_ShouldReturnFalseEquals() {
        // Arrange
        SensorReadingID sensorReadingID1 = new SensorReadingID("sensorReading1");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sensorReading2");
        // Act
        boolean isEquals = sensorReadingID1.equals(sensorReadingID2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for getting the string representation of a sensor reading ID.
     * The test verifies that the toString method of the SensorReadingID class returns the correct ID string.
     */
    @Test
    void toString_ShouldReturnTheIdString() {
        // Arrange
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReading1");

        // Act
        String actual = sensorReadingID.toString();

        // Assert
        assertEquals("sensorReading1", actual);
    }

    @Test
    void hashCode_ShouldReturnTheHashCode() {
        // Arrange
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReading1");
        // Act
        int actual = sensorReadingID.hashCode();
        // Assert
        assertEquals("sensorReading1".hashCode(), actual);
    }
}

