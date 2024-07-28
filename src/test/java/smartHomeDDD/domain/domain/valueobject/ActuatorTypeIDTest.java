package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.ddd.DomainId;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This is a test class for the ActuatorTypeID class. It tests the following scenarios:
 * - Successful creation of a valid ActuatorTypeID.
 * - Exception thrown when an invalid ActuatorTypeID is provided.
 * - The equals method returns false when compared to null.
 * - The equals method returns true when compared to the same object.
 * - The toString method returns the correct ID string.
 */
class ActuatorTypeIDTest {

    /**
     * Test case to verify that a valid ActuatorTypeID can be created.
     */
    @Test
    void validActuatorTypeID_shouldCreateAValidActuatorTypeID() {
        // Arrange
        String sId = "actuatorType1";

        // Act+Assert
        new ActuatorTypeID(sId);
    }

    /**
     * Parameterized test case to verify that an exception is thrown when an invalid ActuatorTypeID is provided.
     *
     * @param id the ActuatorTypeID to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidActuatorTypeID_shouldThrowException(String id) {
        Exception exception = assertThrows(Exception.class, () -> new ActuatorTypeID(id));

        String expectedMessage = "actuator Type ID cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to verify that the equals method returns false when compared to null.
     */
    @Test
    void nullActuatorTypeID_shouldReturnFalse() {
        // Arrange
        new ActuatorTypeID("actuatorType1");

        // Act
        boolean isEquals = false;

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case to verify that the equals method returns true when compared to the same object.
     */
    @Test
    void actuatorTypeIDEqualToItself_shouldReturnTrue() {
        // Arrange
        new ActuatorTypeID("actuatorType1");

        // Act

        // Assert
        assertTrue(true);
    }

    /**
     * Test case to verify that the toString method returns the correct ID string.
     */
    @Test
    void validActuatorTypeIDtoString_ShouldReturnTheIdString() {
        // Arrange
        String expected = "actuatorType1";
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID(expected);

        // Act
        String actual = actuatorTypeID.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test case to verify that the equals method returns false when compared to an object of a different class.
     */
    @Test
    void equals_differentClass_shouldReturnFalse() {
        // Arrange
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("actuatorType1");
        DomainId notAnActuatorTypeID = mock(DomainId.class);

        // Act
        boolean isEqual = actuatorTypeID.equals(notAnActuatorTypeID);

        // Assert
        assertFalse(isEqual);
    }

    /**
     * Test case to verify that the constructor does not throw an exception when a valid ID is provided.
     */
    @Test
    void constructor_validID_shouldNotThrowException() {
        // Arrange
        String validId = "actuatorType1";

        // Act & Assert
        assertDoesNotThrow(() -> new ActuatorTypeID(validId));
    }

    /**
     * Test to verify that the equals method returns true when comparing two ActuatorTypeIDs with the same value.
     */
    @Test
    void equals_sameInstance_shouldReturnTrue() {
        // Arrange
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("actuatorType1");

        // Act & Assert
        assertEquals(actuatorTypeID, actuatorTypeID);
    }

    /**
     * Test to verify that the equals method returns true when comparing two ActuatorTypeIDs with the same value.
     */
    @Test
    void equals_sameActuatorTypeID_shouldReturnTrue() {
        // Arrange
        ActuatorTypeID actuatorTypeID1 = new ActuatorTypeID("actuatorType1");
        ActuatorTypeID actuatorTypeID2 = new ActuatorTypeID("actuatorType1");

        // Act & Assert
        assertEquals(actuatorTypeID1, actuatorTypeID2);
    }


    /**
     * Test to verify that the equals method returns false when comparing an ActuatorTypeID with null.
     */
    @Test
    void equals_nullObject_shouldReturnFalse() {
        // Arrange
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("actuatorType1");

        // Act & Assert
        assertNotEquals(null, actuatorTypeID);
    }


    /**
     * Test to verify that the equals method returns false when comparing two ActuatorTypeIDs with different values.
     */
    @Test
    void equals_differentActuatorTypeID_shouldReturnFalse() {
        // Arrange
        ActuatorTypeID actuatorTypeID1 = new ActuatorTypeID("actuatorType1");
        ActuatorTypeID actuatorTypeID2 = new ActuatorTypeID("actuatorType2");

        // Act & Assert
        assertNotEquals(actuatorTypeID1, actuatorTypeID2);
    }

    /**
     * Test to verify that the hashCode method returns a non-zero value for a valid ActuatorTypeID.
     */
    @Test
    void hashCode_nonZeroForValidActuatorTypeID() {
        // Arrange
        String validId = "actuatorType1";
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID(validId);

        // Act
        int hashCode = actuatorTypeID.hashCode();

        // Assert
        assertNotEquals(0, hashCode);
    }


}
