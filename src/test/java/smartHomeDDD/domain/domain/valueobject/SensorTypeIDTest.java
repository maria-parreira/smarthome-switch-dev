package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.SensorTypeID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SensorTypeIDTest class provides unit tests for the SensorTypeID class.
 * It encompasses the following scenarios:
 * - Creating a valid sensor type ID
 * - Creating a sensor type ID with invalid input
 * - Creating a sensor type ID with a null ID
 * - Creating a sensor type ID with an empty ID
 * - Checking the equality of a sensor type ID with null
 * - Checking the equality of a sensor type ID with itself
 * - Checking the equality of two sensor type IDs with the same ID
 * - Checking the equality of two sensor type IDs with different IDs
 * - Getting the string representation of a sensor type ID
 */
class SensorTypeIDTest {

    /**
     * Test case for creating a valid sensor type ID.
     * The test verifies that the SensorTypeID constructor returns a non-null object when provided with a valid ID.
     */
    @Test
    void validSensorTypeID_ShouldCreateAValidSensorID() throws IllegalArgumentException {
        // Arrange
        String id = "sensorType1";

        // Act
        SensorTypeID sensorTypeID = new SensorTypeID(id);

        // Assert
        assertNotNull(sensorTypeID);
    }

    /**
     * Test case for creating a sensor type ID with invalid input.
     * The test verifies that the SensorTypeID constructor throws an Exception when provided with an empty or whitespace-only ID.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidSensorTypeID_ShouldThrowException(String id) throws IllegalArgumentException{

        // Arrange
        String expectedMessage = "sensorType ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorTypeID(id);
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for creating a sensor type ID with a null ID.
     * The test verifies that the SensorTypeID constructor throws an Exception when provided with a null ID.
     */
    @Test
    void sensorTypeIDWithNullId_ShouldThrowException() throws IllegalArgumentException{

        // Arrange
        String expectedMessage = "sensorType ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorTypeID(null);
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Test case for creating a sensor type ID with an empty ID.
     * The test verifies that the SensorTypeID constructor throws an Exception when provided with an empty ID.
     */
    @Test
    void sensorTypeIDWithEmptyId_ShouldThrowException() throws IllegalArgumentException {

        // Arrange
        String expectedMessage = "sensorType ID cannot be null or empty";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorTypeID("");
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Test case for checking the equality of a sensor type ID with null.
     * The test verifies that the equals method of the SensorTypeID class returns false when the sensor type ID is compared with null.
     */
    @Test
    void nullObject_ShouldReturnFalseEquals() {
        // Arrange
        SensorTypeID sensorTypeID = new SensorTypeID("sensorType1");

        // Act
        boolean isEquals = sensorTypeID.equals(null);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Test case for checking the equality of a sensor type ID with itself.
     * The test verifies that the equals method of the SensorTypeID class returns true when the sensor type ID is compared with itself.
     */
    @Test
    void withSameObject_ShouldReturnTrueEquals() {
        // Arrange
        SensorTypeID sensorTypeID = new SensorTypeID("sensorType1");

        // Act
        boolean isEquals = sensorTypeID.equals(sensorTypeID);

        // Assert
        assertTrue(isEquals);
    }
    /**
     * Test case for checking the equality of two sensor type IDs with the same ID.
     * The test verifies that the equals method of the SensorTypeID class returns true when two sensor type IDs with the same ID are compared.
     */
    @Test
    void objectWithSameIds_ShouldReturnTrueEquals() {
        // Arrange
        SensorTypeID sensorTypeID1 = new SensorTypeID("sensorType1");
        SensorTypeID sensorTypeID2 = new SensorTypeID("sensorType1");

        // Act
        boolean isEquals = sensorTypeID1.equals(sensorTypeID2);

        // Assert
        assertTrue(isEquals);
    }
    /**
     * Test case for checking the equality of two sensor type IDs with different IDs.
     * The test verifies that the equals method of the SensorTypeID class returns false when two sensor type IDs with different IDs are compared.
     */
    @Test
    void objectWithDifferentIds_ShouldReturnFalseEquals() {
        // Arrange
        SensorTypeID sensorTypeID1 = new SensorTypeID("sensorType1");
        SensorTypeID sensorTypeID2 = new SensorTypeID("sensorType2");

        // Act
        boolean isEquals = sensorTypeID1.equals(sensorTypeID2);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Test case for getting the string representation of a sensor type ID.
     * The test verifies that the toString method of the SensorTypeID class returns the correct ID string.
     */
    @Test
    void toString_ShouldReturnTheIdString() {
        // Arrange
        SensorTypeID sensorTypeID = new SensorTypeID("sensorType1");

        // Act
        String actual = sensorTypeID.toString();

        // Assert
        assertEquals("sensorType1", actual);
    }
}
