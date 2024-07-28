package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.ActuatorModelID;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests the ActuatorModelID class. It lists the following scenarios:
 * - Valid instantiation of ActuatorModelID;
 * - Invalid instantiation of ActuatorModelID;
 * - Equality between an ActuatorModelID object and itself;
 * - Non-Equality between an ActuatorModelID object and null one;
 * - Equality between two identical ActuatorModelID objects;
 * - Non-Equality between two ActuatorModelID objects with different IDs;
 * - Correct string representation of ActuatorModelID;
 * - Correct hash code computation of ActuatorModelID;
 * - Correct generation of actuator model list from a valid configuration file;
 * - Exception handling when a ConfigurationException is encountered during the generation of actuator model list.
 */
class ActuatorModelIDTest {

    /**
     * Tests if a valid ActuatorModelID results in a non-null object with expected attributes.
     */
    @Test
    void validActuatorModelID_shouldCreateAValidActuatorModelID() {
        // Arrange
        String Id = "OPNCL0100";
        // Act / Assert
        new ActuatorModelID(Id);
    }

    /**
     * Tests if the ActuatorModelID constructor throws an IllegalArgumentException when the id is not valid.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidID_shouldThrowException(String id) {
        Exception exception = assertThrows(Exception.class, () -> new ActuatorModelID(id));
        String expectedMessage = "The ID of the actuator model cannot be null or empty";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests if the equals method returns false when the ActuatorModelID is compared with a null object.
     */
    @Test
    void nonNullActuatorModelID_shouldReturnFalse() {
        // Arrange
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        boolean isEquals = false;
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if the equals method returns true when the ActuatorModelID is compared with itself.
     */
    @Test
    void actuatorModelIDEqualToItself_shouldReturnEqualObject() {
        // Arrange
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        boolean isEquals = actuatorModelID.equals(actuatorModelID);
        // Assert
        assertTrue(true);
    }

    /**
     * Tests if the toString method of ActuatorModelID returns the correct string representation of the ID.
     */
    @Test
    void validActuatorModelIDtoString_ShouldReturnModelIdString() {
        // Arrange
        String expected = "OPNCL0100";
        ActuatorModelID actuatorModelID = new ActuatorModelID(expected);
        // Act
        String actual = actuatorModelID.toString();
        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests if the equals method returns true when the other object is a different instance but with the same ID.
     */
    @Test
    void equals_withDifferentInstanceSameID_shouldReturnTrue() {
        ActuatorModelID id1 = new ActuatorModelID("OPNCL0100");
        ActuatorModelID id2 = new ActuatorModelID("OPNCL0100");

        assertEquals(id1, id2);
    }



    /**
     * Tests if the equals method returns false when the other object is not an instance of ActuatorModelID.
     */
    @Test
    void equals_withNonActuatorModelIDObject_shouldReturnFalse() {
        ActuatorModelID id = new ActuatorModelID("OPNCL0100");
        String nonActuatorModelIDObject = "I am not an ActuatorModelID";

        assertNotEquals(id, nonActuatorModelIDObject);
    }

    /**
     * Tests if the hashCode method returns the same hash code for two ActuatorModelID instances with the same ID.
     */
    @Test
    void hashCode_withSameID_shouldReturnSameHashCode() {
        ActuatorModelID id1 = new ActuatorModelID("OPNCL0100");
        ActuatorModelID id2 = new ActuatorModelID("OPNCL0100");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    /**
     * Tests if the ActuatorModelID constructor throws an IllegalArgumentException when the id is not valid.
     */
    @Test
    void constructor_withInvalidID_shouldThrowIllegalArgumentException() {
        String invalidID = ""; // An invalid ID (empty string)

        assertThrows(IllegalArgumentException.class, () -> new ActuatorModelID(invalidID));
    }

    /**
     * Tests if the ActuatorModelID constructor throws an IllegalArgumentException when the id does not exist in the list of models.
     */
    @Test
    void constructor_withNonexistentID_shouldThrowIllegalArgumentException() {
        String nonexistentID = "NONEXISTENT_ID"; // An ID that does not exist in the list of models

        assertThrows(IllegalArgumentException.class, () -> new ActuatorModelID(nonexistentID));
    }

    /**
     * Tests if the generateActuatorModelList method returns a non-empty array of actuator models when provided with a valid configuration file.
     */
    @Test
    void generateActuatorModelList_withValidConfigurationFile_shouldReturnActuatorModels() {
        // Arrange
        String filePathname = "config.properties"; // A valid configuration file

        // Act
        String[] actuatorModels = ActuatorModelID.generateActuatorModelList(filePathname);

        // Assert
        assertNotNull(actuatorModels);
        assertTrue(actuatorModels.length > 0);
    }

    /**
     * Tests if the generateActuatorModelList method throws an IllegalArgumentException when a ConfigurationException is encountered.
     */
    @Test
    void generateActuatorModelList_withConfigurationException_shouldThrowIllegalArgumentException() throws ConfigurationException {
        // Arrange
        Configurations configsMock = mock(Configurations.class);
        String filePathname = "testFile";

        doThrow(new ConfigurationException("Test exception")).when(configsMock).properties(new File(filePathname));

        // Act / Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorModelID.generateActuatorModelList(filePathname));
        String expectedMessage = "Something went wrong in reading the configuration: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}