package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.ActivationStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the ActivationStatus value object. It lists the following scenarios:
 * - Successful creation of a valid ActivationStatus.
 * - Non-equality of an ActivationStatus object with a null object.
 * - Equality of an ActivationStatus object with the same object.
 * - Equality of two ActivationStatus objects with the same status.
 * - Non-equality of two ActivationStatus objects with different statuses.
 * - The toString() method returns the status of the ActivationStatus object.
 */

class ActivationStatusTest {

    /**
     * Tests the creation of a valid ActivationStatus.
     */
    @Test
    void shouldCreateAValidActivationStatus() {
        // Arrange
        boolean bIsActive = true;

        // Act+Assert
        new ActivationStatus(bIsActive);
    }

    /**
     * Tests the equals() method when comparing with a null object.
     */
    @Test
    void shouldReturnFalse_Equals_Null() {
        // Arrange
        ActivationStatus activationStatus = new ActivationStatus(true);

        // Act
        boolean isEquals = activationStatus.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests the equals() method when comparing with the same object.
     */
    @Test
    void shouldReturnTrue_Equals_SameObject() {
        // Arrange
        ActivationStatus activationStatus = new ActivationStatus(true);

        // Act
        boolean isEquals = activationStatus.equals(activationStatus);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals() method when comparing with different objects but with the same status.
     */
    @Test
    void shouldReturnTrue_Equals_SameStatus() {
        // Arrange
        ActivationStatus activationStatus1 = new ActivationStatus(true);
        ActivationStatus activationStatus2 = new ActivationStatus(true);

        // Act
        boolean isEquals = activationStatus1.equals(activationStatus2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals() method when comparing with different objects and different status.
     */
    @Test
    void shouldReturnFalse_Equals_DifferentStatus() {
        // Arrange
        ActivationStatus activationStatus1 = new ActivationStatus(true);
        ActivationStatus activationStatus2 = new ActivationStatus(false);

        // Act
        boolean isEquals = activationStatus1.equals(activationStatus2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if the toString() method returns the status of the ActivationStatus object.
     */
    @Test
    void toString_ShouldReturnTheIdString() {
        // Arrange
        ActivationStatus activationStatus1 = new ActivationStatus(true);

        // Act
        String actual = activationStatus1.toString();

        // Assert
        assertEquals("true", actual);
    }

    @Test
    void equalObjectsHaveShouldHaveSameHashCode(){
        //Arrange
        ActivationStatus activationStatus1 = new ActivationStatus(true);
        ActivationStatus activationStatus2 = new ActivationStatus(true);

        //Act
        int hashCode1 = activationStatus1.hashCode();
        int hashCode2 = activationStatus2.hashCode();

        //Assert
        assertEquals(hashCode1, hashCode2);
    }

}
