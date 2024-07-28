package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.ActuatorID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorIDTest {

    /**
     * Test to verify that a valid ActuatorID can be created.
     */
    @Test
    void shouldCreateAValidActuatorID() {
        // Arrange
        String id = "actuator1";

        // Act
        ActuatorID actuatorID = new ActuatorID(id);

        // Assert
        assertNotNull(actuatorID);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when attempting to create an ActuatorID with a null or empty ID.
     *
     * @param id The invalid ID value.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_NullOrEmptyActuatorID(String id) throws IllegalArgumentException {
        //Arrange
        String expectedMessage = "actuator ID cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorID(id);
        });
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that the equals method returns false when comparing an ActuatorID with null.
     */
    @Test
    void shouldReturnFalse_Equals_Null() {
        // Arrange
        ActuatorID actuatorID = new ActuatorID("actuator1");

        // Act
        boolean isEquals = actuatorID.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to verify that the equals method returns true when comparing an ActuatorID with itself.
     */
    @Test
    void shouldReturnTrue_Equals_SameObject() {
        // Arrange
        ActuatorID actuatorID = new ActuatorID("actuator1");

        // Act
        boolean isEquals = actuatorID.equals(actuatorID);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to verify that the toString method returns the correct ID string representation.
     */
    @Test
    void toString_ShouldReturnTheIdString() {
        // Arrange
        String expected = "actuator1";
        ActuatorID actuatorID = new ActuatorID(expected);

        // Act
        String actual = actuatorID.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to verify that the equals method returns true when comparing two ActuatorID objects with the same ID.
     */
    @Test
    void differentObjectSameModel_shouldReturnTrue() {
        // Arrange
        String expected = "actuator1";
        ActuatorID actuatorID = new ActuatorID(expected);
        ActuatorID actuatorID1 = new ActuatorID(expected);

        // Act
        boolean isEquals = actuatorID.equals(actuatorID1);

        // Assert
        assertTrue(isEquals);
    }

}
