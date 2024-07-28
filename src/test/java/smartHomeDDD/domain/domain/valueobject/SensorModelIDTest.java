package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SensorModelIDTest class provides unit tests for the SensorModelID class.
 * It encompasses the following scenarios:
 * - Creating a valid sensor model ID
 * - Creating a sensor model ID with invalid input
 * - Checking the equality of a sensor model ID with null
 * - Checking the equality of a sensor model ID with itself
 * - Getting the string representation of a sensor model ID
 */
class SensorModelIDTest {

    /**
     * Test case for creating a valid sensor model ID.
     * The test verifies that the SensorModelID constructor returns a non-null object when provided with a valid ID.
     */
    @Test
    void validSensorModelID_shouldCreateAValidActuatorModelID() {
        // Arrange
        String Id = "SUNSET567"; //Name of model present in config file.
        // Act / Assert
        SensorModelID sensorModelID = new SensorModelID(Id);
        assertNotNull(sensorModelID);
    }

    /**
     * Test case for creating a sensor model ID with invalid input.
     * The test verifies that the SensorModelID constructor throws an Exception when provided with an empty or whitespace-only ID.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidID_shouldThrowException(String id) {
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorModelID(id);
        });
        String expectedMessage = "The ID of the sensor model cannot be null or empty";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for checking the equality of a sensor model ID with null.
     * The test verifies that the equals method of the SensorModelID class returns false when the sensor model ID is compared with null.
     */
    @Test
    void nonNullActuatorModelID_shouldReturnFalse() {
        // Arrange
        SensorModelID sensorModelID = new SensorModelID("PC500W");
        // Act
        boolean isEquals = sensorModelID.equals(null);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for checking the equality of a sensor model ID with itself.
     * The test verifies that the equals method of the SensorModelID class returns true when the sensor model ID is compared with itself.
     */
    @Test
    void sensorModelIDEqualToItself_shouldReturnEqualObject() {
        // Arrange
        SensorModelID sensorModelID = new SensorModelID("PC500W");
        // Act
        boolean isEquals = sensorModelID.equals(sensorModelID);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test case for getting the string representation of a sensor model ID.
     * The test verifies that the toString method of the SensorModelID class returns the correct ID string.
     */
    @Test
    void validSensorModelIDtoString_ShouldReturnModelIdString() {
        // Arrange
        String expected = "SUNSET567";
        SensorModelID sensorModelID = new SensorModelID(expected);
        // Act
        String actual = sensorModelID.toString();
        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test case for creating a sensor model ID with an invalid model name.
     * The test verifies that the SensorModelID constructor throws an Exception when provided with a model name that does not exist in the list of models.
     */
    @Test
    void invalidModelName_shouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorModelID("InvalidModelName");
        });
        String expectedMessage = "Sensor Model name does not exist in list";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getHashCode_shouldReturnHashCode() {
        // Arrange
        SensorModelID sensorModelID = new SensorModelID("PC500W");
        // Act
        int hashCode = sensorModelID.hashCode();
        // Assert
        assertEquals("PC500W".hashCode(),hashCode);
    }
}