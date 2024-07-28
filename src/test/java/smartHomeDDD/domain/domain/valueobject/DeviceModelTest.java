package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.DeviceModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the DeviceModel class.
 * This class provides tests for the following scenarios:
 * - create a valid instance of DeviceModel
 * - throw an exception when a null model is provided
 * - throw an exception when an empty or blank model is provided
 * - return true when the equals method is given the same object as parameter
 * - return true when the equals method is given a DeviceModel object with the same model as parameter
 * - return false when the equals method is given a null object as parameter
 * - return false when the equals method is given a DeviceModel object with different model as parameter
 * - return the model string
 * - return the same hash code for two DeviceModel objects with the same model
 */
class DeviceModelTest {

    /**
     * Test to ensure that the DeviceModel constructor creates a DeviceModel object when given a valid model.
     */

    @Test
    void validDeviceModel_shouldCreateDeviceModel() {
        String model = "DeviceModel";
        new DeviceModel(model);
    }

    /**
     * Test to ensure that the DeviceModel constructor throws an exception when given a null model.
     */

    @Test
    void nullDeviceModel_shouldThrowException() {
        // Arrange
        String expectedMessage = "Device Model cannot be null or empty.";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceModel(null);
        });
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the DeviceModel constructor throws an exception when given an empty or blank model.
     */

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n", "\t", "\r"})
    void invalidDeviceModel_shouldThrowException(String model) {
        // Arrange
        String expectedMessage = "Device Model cannot be null or empty.";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceModel(model);
        });
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the DeviceModel equals method returns true when given the same object.
     */

    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        DeviceModel deviceModel = new DeviceModel("DeviceModel");

        // Act
        boolean isEquals = deviceModel.equals(deviceModel);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the DeviceModel equals method returns true when given two DeviceModel objects with the same model.
     */

    @Test
    void differentObjectSameModel_shouldReturnTrue() {
        // Arrange
        DeviceModel deviceModel1 = new DeviceModel("DeviceModel");
        DeviceModel deviceModel2 = new DeviceModel("DeviceModel");

        // Act
        boolean isEquals = deviceModel1.equals(deviceModel2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the DeviceModel equals method returns false when given a null object.
     */

    @Test
    void nullModel_shouldReturnFalse() {
        // Arrange
        DeviceModel deviceModel1 = new DeviceModel("DeviceModel");

        // Act
        boolean isEquals = deviceModel1.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the DeviceModel equals method returns false when given two DeviceModel objects with different models.
     */

    @Test
    void differentObjectDifferentModel_shouldReturnFalse() {
        // Arrange
        DeviceModel deviceModel1 = new DeviceModel("DeviceModel1");
        DeviceModel deviceModel2 = new DeviceModel("DeviceModel2");

        // Act
        boolean isEquals = deviceModel1.equals(deviceModel2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the DeviceModel toString method returns the model of the DeviceModel object.
     */

    @Test
    void toStringShouldReturnTheModelString() {
        // Arrange
        String model = "DeviceModel";
        DeviceModel deviceModel = new DeviceModel(model);

        // Act
        String actualModel = deviceModel.toString();

        // Assert
        assertEquals(actualModel, model);
    }

    /**
     * Test to ensure that the hashCode method of the DeviceModel class works correctly.
     * This test creates two DeviceModel objects with the same model and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass.
     */
    @Test
    void sameModel_shouldReturnSameHashCode() {
        // Arrange
        String model = "DeviceModel";
        DeviceModel deviceModel1 = new DeviceModel(model);
        DeviceModel deviceModel2 = new DeviceModel(model);

        // Act
        int hashCode1 = deviceModel1.hashCode();
        int hashCode2 = deviceModel2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

}