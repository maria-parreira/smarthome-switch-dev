package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Length;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Length class. The tests cover the following scenarios:
 * - Creating a Length object with a valid length value
 * - Attempting to create a Length object with a negative length value
 * - Attempting to create a Length object with a length value of 0
 * - Attempting to create a Length object with an invalid length value (0, -0, NaN, Infinity)
 * - Checking the equality of a Length object with itself
 * - Checking the equality of a Length object with a null object
 * - Checking the equality of two Length objects with the same length value
 * - Checking the equality of two Length objects with different length values
 * - Retrieving the string representation of a Length object
 * - Checking the equality of two Length objects with the same length value
 * - Retrieving the double value of a Length object
 * - Checking the hash code of two Length objects with the same length value
 * - Checking the hash code of two Length objects with different length values
 */
class LengthTest {

    /**
     * Test to ensure that the Length constructor creates a Length object when given a valid length value.
     */
    @Test
    void validLength_shouldCreateLength() {
        // Arrange
        double value = 10.0;

        // Act & Assert
        assertDoesNotThrow(() -> {
            new Length(value);
        });
    }

    /**
     * Test to ensure that the Length constructor throws an exception when given a negative length value.
     */
    @Test
    void negativeLength_shouldThrowException() {
        // Arrange
        String expectedMessage = "Length must be greater than 0";
        double value = -0.1;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Length(value));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test to ensure that the Length constructor throws an exception when given a value of 0.
     */
    @Test
    void zeroLength_shouldThrowException() {
        // Arrange
        String expectedMessage = "Length must be greater than 0";
        double value = 0.0;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Length(value));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test to ensure that the Length constructor throws an exception when given an invalid length value (0, -0, NaN, Infinity).
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.0, Double.NaN, Double.NEGATIVE_INFINITY})
    void invalidLength_shouldThrowException(double value) {
        // Arrange
        String expectedMessage = "Length must be greater than 0";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Length(value));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test to ensure that the equals method returns true when given the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Length length = new Length(10.0);

        // Act
        boolean result = length.equals(length);

        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the equals method returns false when given a null object.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        Length length = new Length(10.0);

        // Act & Assert
        assertNotEquals(null, length);
    }

    /**
     * Test to ensure that the equals method returns true when given a different object with the same length value.
     */
    @Test
    void differentObjectSameLength_shouldReturnTrue() {
        // Arrange
        Length length1 = new Length(10.0);
        Length length2 = new Length(10.0);

        // Act & Assert
        assertEquals(length1, length2);
    }

    /**
     * Test to ensure that the equals method returns false when given a different object with a different length value.
     */
    @Test
    void differentObjectDifferentLength_shouldReturnFalse() {
        // Arrange
        Length length1 = new Length(10.0);
        Length length2 = new Length(20.0);

        // Act & Assert
        assertNotEquals(length1, length2);
    }

    /**
     * Test to ensure that the toString method returns the length value of the object.
     */
    @Test
    void toString_shouldReturnLengthValue() {
        // Arrange
        double value = 10.0;
        Length length = new Length(value);

        // Act
        String actual = length.toString();

        // Assert
        assertEquals(String.valueOf(value), actual);
    }
    /**
     * Test to ensure that the equals method of the Length class returns true when comparing two Length objects with the same length value.
     * This test creates two Length objects with the same length value and checks if they are equal.
     * If the equals method is implemented correctly, the test will pass when isEqual is true.
     */
    @Test
    void equals_SameLengthValue_ShouldReturnTrue() {
        // Arrange
        Length length1 = new Length(10.0);
        Length length2 = new Length(10.0);

        // Act
        boolean isEqual = length1.equals(length2);

        // Assert
        assertTrue(isEqual);
    }

    /**
     * Test to ensure that the toDouble method of the Length class returns the correct length value.
     * This test creates a Length object and checks if the returned length value from the toDouble method is equal to the expected length value.
     * If the toDouble method is implemented correctly, the test will pass when the actual length value is equal to the expected length value.
     */
    @Test
    void toDouble_ShouldReturnCorrectLengthValue() {
        // Arrange
        double expectedLength = 10.0;
        Length length = new Length(expectedLength);

        // Act
        double actualLength = length.toDouble();

        // Assert
        assertEquals(expectedLength, actualLength);
    }

    /**
     * Test to ensure that the hashCode method of the Length class returns the same hash code for two Length objects with the same length value.
     * This test creates two Length objects with the same length value and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass when the hash codes of the two Length objects are equal.
     */
    @Test
    void testHashCode_sameLengthValue_shouldReturnSameHashCode() {
        // Arrange
        Length length1 = new Length(10.0);
        Length length2 = new Length(10.0);

        // Act
        int hashCode1 = length1.hashCode();
        int hashCode2 = length2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}


