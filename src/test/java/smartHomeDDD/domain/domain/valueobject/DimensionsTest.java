package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Dimensions;
import smartHomeDDD.domain.valueobject.Height;
import smartHomeDDD.domain.valueobject.Length;
import smartHomeDDD.domain.valueobject.Width;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Dimensions class in the smartHomeDDD domain.
 * The tests cover the following functionalities:
 * - Construction of a Dimensions object with valid length, width, and height values.
 * - Exception handling when the Dimensions constructor is given a negative or zero value for height, width, or length.
 * - Verification of the equals method in different scenarios: comparing the same object, a null object, a different object with the same dimensions, and a different object with different dimensions.
 * - Verification of the toString method to ensure it returns the correct format and the actual dimensions of the object.
 * - Verification of the getLength, getWidth, and getHeight methods to ensure they return the correct values.
 * - Verification of the hashCode method to ensure it returns the same hash code for two Dimensions objects with the same dimensions.
 */
class DimensionsTest {

    /**
     * Test to ensure that the Dimensions constructor creates a Dimensions object when given valid length, width, and height values.
     */
    @Test
    void validDimensions_shouldCreateDimensions() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);

        // Act & Assert
        assertDoesNotThrow(() -> {
            new Dimensions(length, width, height);
        });
    }

    /**
     * Parameterized test to ensure that the Dimensions constructor throws an exception when the height is negative or zero.
     * @param value The negative or zero height value.
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.1, -10.0})
    void negativeOrZeroHeight_shouldThrowException(double value) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Length length = new Length(10.0);
            Width width = new Width(5.0);
            Height height = new Height(value);
            new Dimensions(length, width, height);
        });
        assertEquals("Height must be greater than 0", exception.getMessage());
    }

    /**
     * Parameterized test to ensure that the Dimensions constructor throws an exception when the width is negative or zero.
     * @param value The negative or zero width value.
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.1, -10.0})
    void negativeOrZeroWidth_shouldThrowException(double value) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Length length = new Length(10.0);
            Width width = new Width(value);
            Height height = new Height(5.0);
            new Dimensions(length, width, height);
        });
        assertEquals("Width must be greater than 0", exception.getMessage());
    }

    /**
     * Parameterized test to ensure that the Dimensions constructor throws an exception when the length is negative or zero.
     * @param value The negative or zero length value.
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.1, -10.0})
    void negativeOrZeroLength_shouldThrowException(double value) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Length length = new Length(value);
            Width width = new Width(5.0);
            Height height = new Height(3.0);
            new Dimensions(length, width, height);
        });
        assertEquals("Length must be greater than 0", exception.getMessage());
    }

    /**
     * Test to ensure that the equals method of the Dimensions class returns true when the same Dimensions object is compared to itself.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions = new Dimensions(length, width, height);

        // Act
        boolean result = dimensions.equals(dimensions);

        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the equals method of the Dimensions class returns false when a null object is compared to a Dimensions object.
     * This test creates a Dimensions object and checks if it is equal to null.
     * If the equals method is implemented correctly, the test will pass when isEqual is false.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions = new Dimensions(length, width, height);

        // Act
        boolean isEqual = dimensions.equals(null);

        // Assert
        assertFalse(isEqual);
    }

    /**
     * Test to ensure that the equals method of the Dimensions class returns true when two different Dimensions objects with the same dimensions are compared.
     */
    @Test
    void differentObjectSameDimensions_shouldReturnTrue() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions1 = new Dimensions(length, width, height);
        Dimensions dimensions2 = new Dimensions(length, width, height);

        // Act & Assert
        assertEquals(dimensions1, dimensions2);
    }

    /**
     * Test to ensure that the toString method returns the dimensions of the object.
     */
    @Test
    void toString_shouldReturnDimensions() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions = new Dimensions(length, width, height);
        String expected = "10.0, 5.0, 3.0";

        // Act
        String actual = dimensions.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to ensure that the toString method of the Dimensions class returns the correct format.
     * This test creates a Dimensions object and checks if the toString method returns the expected string.
     * If the toString method is implemented correctly, the test will pass.
     */
    @Test
    void toString_shouldReturnCorrectFormat() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions = new Dimensions(length, width, height);
        String expected = "10.0, 5.0, 3.0";

        // Act
        String actual = dimensions.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to ensure that the getLength method of the Dimensions class returns the correct length.
     * This test creates a Dimensions object and checks if the getLength method returns the expected length.
     * If the getLength method is implemented correctly, the test will pass.
     */
    @Test
    void getLength_shouldReturnCorrectLength() {
        // Arrange
        Length expected = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions = new Dimensions(expected, width, height);

        // Act
        Length actual = dimensions.getLength();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to ensure that the getWidth method of the Dimensions class returns the correct width.
     * This test creates a Dimensions object and checks if the getWidth method returns the expected width.
     * If the getWidth method is implemented correctly, the test will pass.
     */
    @Test
    void getWidth_shouldReturnCorrectWidth() {
        // Arrange
        Length length = new Length(10.0);
        Width expected = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions = new Dimensions(length, expected, height);

        // Act
        Width actual = dimensions.getWidth();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to ensure that the getHeight method of the Dimensions class returns the correct height.
     * This test creates a Dimensions object and checks if the getHeight method returns the expected height.
     * If the getHeight method is implemented correctly, the test will pass.
     */
    @Test
    void getHeight_shouldReturnCorrectHeight() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height expected = new Height(3.0);
        Dimensions dimensions = new Dimensions(length, width, expected);

        // Act
        Height actual = dimensions.getHeight();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to ensure that the hashCode method of the Dimensions class works correctly.
     * This test creates two Dimensions objects with the same length, width, and height and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass.
     */
    @Test
    void sameDimensions_shouldReturnSameHashCode() {
        // Arrange
        Length length = new Length(10.0);
        Width width = new Width(5.0);
        Height height = new Height(3.0);
        Dimensions dimensions1 = new Dimensions(length, width, height);
        Dimensions dimensions2 = new Dimensions(length, width, height);

        // Act
        int hashCode1 = dimensions1.hashCode();
        int hashCode2 = dimensions2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }


}
