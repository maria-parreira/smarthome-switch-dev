package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Height;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The following tests verify the functionality of the Height Class:
 * validHeight_shouldCreateHeight() - Test to ensure that the Height constructor creates a Height object when given a valid height value.
 * negativeHeight_shouldThrowException() - Test to ensure that the Height constructor throws an exception when given a negative height value.
 * zeroHeight_shouldThrowException() - Test to ensure that the Height constructor throws an exception when given a value of 0.
 * invalidHeight_shouldThrowException() - Test to ensure that the Height constructor throws an exception when given an invalid height value (0, -0, NaN, Infinity).
 * sameObject_shouldReturnTrue() - Test to ensure that the equals method returns true when given the same object.
 * nullObject_shouldReturnFalse() - Test to ensure that the equals method returns false when given a null object.
 * differentObjectSameHeight_shouldReturnTrue() - Test to ensure that the equals method returns true when given a different object with the same height value.
 * differentObjectDifferentHeight_shouldReturnFalse() - Test to ensure that the equals method returns false when given a different object with a different height value.
 * toString_shouldReturnHeightValue() - Test to ensure that the toString method returns the height value of the object.
 */
class HeightTest {

    /**
     * Test to ensure that the Height constructor creates a Height object when given a valid height value.
     */
    @Test
    void validHeight_shouldCreateHeight() {
        // Arrange
        double value = 10.0;

        // Act & Assert
        assertDoesNotThrow(() -> {
            new Height(value);
        });
    }

    /**
     * Test to ensure that the Height constructor throws an exception when given a negative height value.
     */
    @Test
    void negativeHeight_shouldThrowException() {
        // Arrange
        String expectedMessage = "Height must be greater than 0";
        double value = -0.1;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Height(value));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test to ensure that the Height constructor throws an exception when given a value of 0.
     */
    @Test
    void zeroHeight_shouldThrowException() {
        // Arrange
        String expectedMessage = "Height must be greater than 0";
        double value = 0.0;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Height(value));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test to ensure that the Height constructor throws an exception when given an invalid height value (0, -0, NaN, Infinity).
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.0, Double.NaN, Double.NEGATIVE_INFINITY})
    void invalidHeight_shouldThrowException(double value) {
        // Arrange
        String expectedMessage = "Height must be greater than 0";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Height(value));
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test to ensure that the equals method returns true when given the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Height height = new Height(10.0);

        // Act
        boolean isEqual = height.equals(height);

        // Assert
        assertTrue(true);
    }

    /**
     * Test to ensure that the equals method returns false when given a null object.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        Height height = new Height(10.0);

        // Act & Assert
        assertNotEquals(null, height);
    }

    /**
     * Test to ensure that the equals method returns true when given a different object with the same height value.
     */
    @Test
    void differentObjectSameHeight_shouldReturnTrue() {
        // Arrange
        Height height1 = new Height(10.0);
        Height height2 = new Height(10.0);

        // Act & Assert
        assertEquals(height1, height2);
    }

    /**
     * Test to ensure that the equals method returns false when given a different object with a different height value.
     */
    @Test
    void differentObjectDifferentHeight_shouldReturnFalse() {
        // Arrange
        Height height1 = new Height(10.0);
        Height height2 = new Height(20.0);

        // Act & Assert
        assertNotEquals(height1, height2);
    }

    /**
     * Test to ensure that the toString method returns the height value of the object.
     */
    @Test
    void toString_shouldReturnHeightValue() {
        // Arrange
        double value = 10.0;
        Height height = new Height(value);

        // Act
        String actual = height.toString();

        // Assert
        assertEquals(String.valueOf(value), actual);
    }

    @Test
    void equals_SameHeightValue_ShouldReturnTrue() {
        // Arrange
        Height height1 = new Height(10.0);
        Height height2 = new Height(10.0);

        // Act
        boolean isEqual = height1.equals(height2);

        // Assert
        assertTrue(isEqual);
    }

    @Test
    void equals_DifferentObjectType_ShouldReturnFalse() {
        Height height1 = new Height(1.0);
        Object obj = new Object();

        assertNotEquals(height1, obj);
    }

    @Test
    void toDouble_ShouldReturnCorrectHeightValue() {
        // Arrange
        double expectedHeight = 10.0;
        Height height = new Height(expectedHeight);

        // Act
        double actualHeight = height.toDouble();

        // Assert
        assertEquals(expectedHeight, actualHeight);
    }
}

