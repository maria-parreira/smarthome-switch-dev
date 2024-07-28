package smartHomeDDD.domain.domain.valueobject;


import smartHomeDDD.domain.valueobject.SUNRISE407Value;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the SUNRISE407Value class.
 * It tests scenarios such as:
 * - Valid instantiation of Object SUNRISE407Value;
 * - Invalid (null) value;
 * - valid transposition to String
 * - invalid transposition to String due to different string
 */

class SUNRISE407ValueTest {
    /**
     * This test checks if a SUNRISE407Value object can be successfully instantiated with a valid LocalTime value.
     * The test passes if the instantiated object is not null.
     */
    @Test
    void validValue_shouldReturnNonNullValue() {
        // Arrange
        LocalTime instant = LocalTime.of(7, 0, 28);
        // Act
        assertNotNull(new SUNRISE407Value(instant));
    }

    /**
     * Test case for the SUNRISE407Value constructor with a null value.
     * The test verifies that an InstantiationException is thrown when provided with a null value.
     */
    @Test
    void nullValue_ShouldThrowException() {
        // Arrange
        String expectedMessage = "Invalid value";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new SUNRISE407Value(null);
        });

        // Assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * This test checks if the toString method of the SUNRISE407Value class returns the correct string representation of the object.
     * The test passes if the returned string matches the expected string.
     */
    @Test
    void validValueToString_shouldReturnValidString() {
        // Arrange
        LocalTime instant = LocalTime.of(7, 0, 28);
        String expected = "07:00:28";
        SUNRISE407Value myValue = new SUNRISE407Value(instant);
        // Act
        assertEquals(expected, myValue.toString());
    }

    /**
     * This test checks if the toString method of the SUNRISE407Value class returns a string that does not match an incorrect expected string.
     * The test passes if the returned string does not match the incorrect expected string.
     */
    @Test
    void incorrectValueToString_shouldReturnDifferentString() {
        // Arrange
        LocalTime instant = LocalTime.of(7, 0, 28);
        String expected = "07:00:00";
        SUNRISE407Value myValue = new SUNRISE407Value(instant);
        // Act
        assertNotEquals(expected, myValue.toString());
    }

}