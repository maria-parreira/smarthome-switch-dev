package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Width;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests that verify the functionality of the Width class:
 * - Tests to ensure that the Width constructor creates a Width object when given a valid width value.
 * - Tests to ensure that the Width constructor throws an exception when given a negative width value, a value of 0, or an invalid width value (0, -0, NaN, Infinity).
 * - Tests to ensure that the equals method behaves correctly when given the same object, a null object, a different object with the same width value, and a different object with a different width value.
 * - Test to ensure that the toString method returns the width value of the object as a string.
 * - Test to ensure that the toDouble method of the Width class returns the correct width value.
 * - Tests to ensure that the hashCode method of the Width class returns the same hash code for two Width objects with the same width value.
 */
class WidthTest {

    /**
     * Test to ensure that the Width constructor creates a Width object when given a valid width value.
     */
    @Test
    void validWidth_shouldCreateWidth() {
        // Arrange
        double value = 10.0;

        // Act
        new Width(value);

        // No explicit assert needed as an exception will be thrown if the constructor fails.
    }

    /**
     * Test to ensure that the Width constructor throws an exception when given a negative width value.
     */
    @Test
    void negativeWidth_shouldThrowException() {
        // Arrange
        String expectedMessage = "Width must be greater than 0";
        double value = -0.1;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Width(value);
        });
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the Width constructor throws an exception when given a value of 0.
     */
    @Test
    void zeroWidth_shouldThrowException() {
        // Arrange
        String expectedMessage = "Width must be greater than 0";
        double value = 0.0;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Width(value);
        });
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the Width constructor throws an exception when given an invalid width value (0, -0, NaN, Infinity).
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.0, Double.NaN, Double.NEGATIVE_INFINITY})
    void invalidWidth_shouldThrowException(double value) {
        // Arrange
        String expectedMessage = "Width must be greater than 0";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Width(value);
        });
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the equals method returns true when given the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Width width = new Width(10.0);

        // Act
        boolean result = width.equals(width);

        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the equals method returns false when given a null object.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        Width width = new Width(10.0);

        // Act & Assert
        assertNotEquals(null, width);
    }

    /**
     * Test to ensure that the equals method returns true when given a different object with the same width value.
     */
    @Test
    void differentObjectSameWidth_shouldReturnTrue() {
        // Arrange
        Width width1 = new Width(10.0);
        Width width2 = new Width(10.0);

        // Act & Assert
        assertEquals(width1, width2);
    }

    /**
     * Test to ensure that the equals method returns false when given a different object with a different width value.
     */
    @Test
    void differentObjectDifferentWidth_shouldReturnFalse() {
        // Arrange
        Width width1 = new Width(10.0);
        Width width2 = new Width(20.0);

        // Act & Assert
        assertNotEquals(width1, width2);
    }

    /**
     * Test to ensure that the toString method returns the width value of the object.
     */
    @Test
    void toString_shouldReturnWidthValue() {
        // Arrange
        double value = 10.0;
        Width width = new Width(value);

        // Act
        String actual = width.toString();

        // Assert
        assertEquals(String.valueOf(value), actual);
    }

    /**
     * Test to ensure that the toDouble method of the Width class returns the correct width value.
     * This test creates a Width object and checks if the returned width value from the toDouble method is equal to the expected width value.
     * If the toDouble method is implemented correctly, the test will pass when the actual width value is equal to the expected width value.
     */
    @Test
    void toDouble_shouldReturnWidthValue() {
        // Arrange
        double expectedWidth = 10.0;
        Width width = new Width(expectedWidth);

        // Act
        double actualWidth = width.toDouble();

        // Assert
        assertEquals(expectedWidth, actualWidth, 0.0001);
    }

    /**
     * Test to ensure that the hashCode method of the Width class returns the same hash code for two Width objects with the same width value.
     * This test creates two Width objects with the same width value and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass when the hash codes of the two Width objects are equal.
     */
    @Test
    public void testHashCode_sameWidthValue_shouldReturnSameHashCode() {
        // Arrange
        Width width1 = new Width(10.0);
        Width width2 = new Width(10.0);

        // Act
        int hashCode1 = width1.hashCode();
        int hashCode2 = width2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

}
