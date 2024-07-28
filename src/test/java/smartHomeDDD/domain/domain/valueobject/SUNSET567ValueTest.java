package smartHomeDDD.domain.domain.valueobject;


import smartHomeDDD.domain.valueobject.SUNSET567Value;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the SUNSET567Value sensor class.
 * It tests scenarios such as:
 * - valid instantiation of SUNSET567Value object,
 * - invalid (null) value
 * - valid transposition to String
 * - invalid transposition to String due to different string.
 */

class SUNSET567ValueTest {
    /**
     * Test case for valid SUNSET567Value instantiation.
     * The test verifies that a non-null SUNSET567Value object is returned when a valid LocalTime is provided.
     */

    @Test
    void validValue_shouldReturnNonNullValue() {
        // Arrange
        LocalTime instant = LocalTime.of(18, 0, 28);
        // Act
        assertNotNull(new SUNSET567Value(instant));
    }

    /**
     * Test case for invalid SUNSET567Value instantiation.
     * The test verifies that an InstantiationException is thrown when a null LocalTime is provided.
     *
     * @throws IllegalArgumentException if the provided LocalTime is invalid.
     */
    @Test
    void nullValue_ShouldThrowException() throws IllegalArgumentException {
        // Arrange
        String expectedMessage = "Invalid value";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new SUNSET567Value(null));

        // Assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for valid SUNSET567Value toString method.
     * The test verifies that the correct string representation of the SUNSET567Value object is returned.
     *
     * @throws IllegalArgumentException if the provided LocalTime is invalid
     */
    @Test
    void validValueToString_shouldReturnValidString() throws IllegalArgumentException {
        // Arrange
        LocalTime instant = LocalTime.of(18, 0, 28);
        String expected = "18:00:28";
        SUNSET567Value myValue = new SUNSET567Value(instant);
        // Act
        assertEquals(expected, myValue.toString());
    }

    /**
     * Test case for invalid SUNSET567Value toString method.
     * The test verifies that the returned string representation of the SUNSET567Value object is not equal to the expected string.
     *
     * @throws IllegalArgumentException if the provided LocalTime is invalid
     */
    @Test
    void incorrectValueToString_shouldReturnDifferentString() throws IllegalArgumentException {
        // Arrange
        LocalTime instant = LocalTime.of(18, 0, 0);
        String expected = "19:30:30";
        SUNSET567Value myValue = new SUNSET567Value(instant);
        // Act
        assertNotEquals(expected, myValue.toString());
    }

}