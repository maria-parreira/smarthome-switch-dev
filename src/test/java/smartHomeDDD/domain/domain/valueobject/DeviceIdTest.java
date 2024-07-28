package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.DeviceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the DeviceId value object. It lists the following scenarios:
 * - Successful creation of a valid DeviceId.
 * - Exception thrown when an invalid DeviceId is provided.
 * - Exception thrown when a null DeviceId is provided.
 * - Equals method returns false when comparing with a null object.
 * - Equals method returns true when comparing with the same object.
 * - Equals method returns true when comparing with different objects but with the same IDs.
 * - Equals method returns false when comparing with different objects and different IDs.
 * - toString method returns the ID string.
 */
class DeviceIdTest {

    /**
     * Tests the creation of a valid DeviceId.
     */
    @Test
    void shouldCreateAValidDeviceId() {
        // Arrange
        String sId = "device1";

        // Act+Assert
        new DeviceId(sId);
    }

    /**
     * Tests that an exception is thrown when an invalid DeviceId is provided.
     *
     * @param id the invalid DeviceId string
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_DeviceId(String id) {
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceId(id);
        });

        String expectedMessage = "DeviceId cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests that an exception is thrown when a null DeviceId is provided.
     */
    @Test
    void shouldThrowException_DeviceIdNullId() {
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceId(null);
        });

        String expectedMessage = "DeviceId cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests the equals() method when comparing with a null object.
     */
    @Test
    void shouldReturnFalse_Equals_Null() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");

        // Act
        boolean isEquals = deviceId.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests the equals() method when comparing with the same object.
     */
    @Test
    void shouldReturnTrue_Equals_SameObject() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");

        // Act
        boolean isEquals = deviceId.equals(deviceId);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals() method when comparing with different objects but with the same IDs.
     */
    @Test
    void shouldReturnTrue_Equals_SameIds() {
        // Arrange
        DeviceId deviceId1 = new DeviceId("device1");
        DeviceId deviceId2 = new DeviceId("device1");

        // Act
        boolean isEquals = deviceId1.equals(deviceId2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals() method when comparing with different objects and different IDs.
     */
    @Test
    void shouldReturnFalse_Equals_DifferentIds() {
        // Arrange
        DeviceId deviceId1 = new DeviceId("device1");
        DeviceId deviceId2 = new DeviceId("device2");

        // Act
        boolean isEquals = deviceId1.equals(deviceId2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if the toString() method returns the ID string.
     */
    @Test
    void toString_ShouldReturnTheIdString() {
        // Arrange
        DeviceId deviceId1 = new DeviceId("device1");

        // Act
        String actual = deviceId1.toString();

        // Assert
        assertEquals("device1", actual);
    }
}
