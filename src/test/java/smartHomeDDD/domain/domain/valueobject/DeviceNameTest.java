package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.DeviceName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the DeviceName class.
 * This class tests the conditions for the following scenarios:
 * - create a valid instance of DeviceName
 * - throw an exception when a null name is provided
 * - throw an exception when an empty or blank name is provided
 * - return true when the equals method is given the same object as parameter
 * - return false when the equals method is given a null object as parameter
 * - return true when the equals method is given a DeviceName object with the same name as parameter
 * - return false when the equals method is given a DeviceName object with different name as parameter
 * - return the name string
 * - return the same hash code for two DeviceName objects with the same name
 */

class DeviceNameTest {

    /**
     * Test to ensure that the DeviceName constructor creates a DeviceName object when given a valid name.
     */

    @Test
    void validDeviceName_shouldCreateDeviceName() {
        String name = "DeviceName";
        new DeviceName(name);
    }

    /**
     * Test to ensure that the DeviceName constructor throws an exception when given a null name.
     */

    @Test
    void nullDeviceName_shouldThrowException() {
        // arrange
        String expectedMessage = "Device Name cannot be null or empty.";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceName(null);
        });
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the DeviceName constructor throws an exception when given an empty or blank name.
     */

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r", "\n"})
    void invalidDeviceName_shouldThrowException(String name) {
        // arrange
        String expectedMessage = "Device Name cannot be null or empty.";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceName(name);
        });
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the equals method returns true when given the same object.
     */

    @Test
    void sameObject_shouldReturnTrue() {
        // arrange
        DeviceName deviceName = new DeviceName("DeviceName");

        // act
        boolean isEquals = deviceName.equals(deviceName);

        // assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns false when given a null object.
     */

    @Test
    void nullObject_shouldReturnFalse() {
        // arrange
        DeviceName deviceName = new DeviceName("DeviceName");

        // act
        boolean isEquals = deviceName.equals(null);

        // assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the equals method returns true when given a different object with the same name.
     */

    @Test
    void differentObjectSameName_shouldReturnTrue() {
        // arrange
        DeviceName deviceName1 = new DeviceName("DeviceName");
        DeviceName deviceName2 = new DeviceName("DeviceName");

        // act
        boolean isEquals = deviceName1.equals(deviceName2);

        // assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns false when given a different object with a different name.
     */

    @Test
    void differentObjectDifferentName_shouldReturnFalse() {
        // arrange
        DeviceName deviceName1 = new DeviceName("DeviceName1");
        DeviceName deviceName2 = new DeviceName("DeviceName2");

        // act
        boolean isEquals = deviceName1.equals(deviceName2);

        // assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the toString method returns the name of the device.
     */

    @Test
    void toString_shouldReturnName() {
        // arrange
        String expectedName = "DeviceName";
        DeviceName deviceName = new DeviceName(expectedName);

        // act
        String actualName = deviceName.toString();

        // assert
        assertEquals(actualName, expectedName);
    }

    /**
     * Test to ensure that the hashCode method of the DeviceName class works correctly.
     * This test creates two DeviceName objects with the same name and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass.
     */
    @Test
    void sameName_shouldReturnSameHashCode() {
        // Arrange
        String name = "DeviceName";
        DeviceName deviceName1 = new DeviceName(name);
        DeviceName deviceName2 = new DeviceName(name);

        // Act
        int hashCode1 = deviceName1.hashCode();
        int hashCode2 = deviceName2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}
