package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.FloorNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the FloorNumber class. The tests cover the following aspects:
 * - Construction of a FloorNumber object with a valid floor number.
 * - Exception handling when a null value is passed to the FloorNumber constructor.
 * - Verification of the equals method in different scenarios:
 *   - Comparing a FloorNumber object to itself.
 *   - Comparing a FloorNumber object to null.
 *   - Comparing two different FloorNumber objects with the same floor number.
 *   - Comparing two different FloorNumber objects with different floor numbers.
 * - Verification of the toString method to ensure it returns the correct floor number value.
 */

class FloorNumberTest {


    /**
     * Test to ensure that the FloorNumber constructor creates a FloorNumber object when given a valid floor number value.
     */
    @Test
    void validFloorNumber_shouldCreateFloorNumber() {
        // Arrange
        Integer value = 1;

        // Act & Assert
        assertDoesNotThrow(() -> {
            new FloorNumber(value);
        });
    }

    /**
     * Test to ensure that the FloorNumber constructor throws an exception when given a null value.
     */
    @Test
    void nullFloorNumber_shouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new FloorNumber(null);
        });
    }


    /**
     * Test to ensure that the equals method returns true when given the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Integer floorNumberValue = 1;
        FloorNumber floorNumber = new FloorNumber(floorNumberValue);

        // Act
        boolean result = floorNumber.equals(floorNumber);

        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the FloorNumber constructor throws an exception when given a null value.
     * The test first creates a FloorNumber object with a valid value and sets up the expected exception message.
     * Then, it attempts to create a new FloorNumber object with a null value, expecting an exception to be thrown.
     * The actual exception message is then compared with the expected message.
     */
    @Test
    void constructorWithNullArgument_shouldThrowException() {
        // Arrange
        FloorNumber floorNumber = new FloorNumber(1);
        String expectedMessage = "Invalid floor number, it must not be null";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new FloorNumber(null);
        });
        String actualMessage = exception.getMessage();


        // Assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the equals method returns true when given a different object with the same floor number value.
     */
    @Test
    void differentObjectSameFloorNumber_shouldReturnTrue() {
        // Arrange
        FloorNumber floorNumber1 = new FloorNumber(1);
        FloorNumber floorNumber2 = new FloorNumber(1);

        // Act & Assert
        assertEquals(floorNumber1, floorNumber2);
    }

    /**
     * Test to ensure that the equals method returns false when given a different object with a different floor number value.
     */
    @Test
    void differentObjectDifferentFloorNumber_shouldReturnFalse() {
        // Arrange
        FloorNumber floorNumber1 = new FloorNumber(1);
        FloorNumber floorNumber2 = new FloorNumber(2);

        // Act & Assert
        assertNotEquals(floorNumber1, floorNumber2);
    }

    /**
     * Test to ensure that the toString method returns the floor number value of the object.
     */
    @Test
    void toString_shouldReturnFloorNumberValue() {
        // Arrange
        Integer value = 1;
        FloorNumber floorNumber = new FloorNumber(value);

        // Act
        String actual = floorNumber.toString();

        // Assert
        assertEquals(String.valueOf(value), actual);
    }

    /**
     * Test to ensure that the getFloorNumber method of the FloorNumber class returns the correct floor number.
     * This test creates a FloorNumber object and checks if the getFloorNumber method returns the expected floor number.
     * If the getFloorNumber method is implemented correctly, the test will pass.
     */
    @Test
    void getFloorNumber_shouldReturnCorrectFloorNumber() {
        // Arrange
        Integer expected = 1;
        FloorNumber floorNumber = new FloorNumber(expected);

        // Act
        Integer actual = floorNumber.getFloorNumber();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test to ensure that the hashCode method of the FloorNumber class works correctly.
     * This test creates two FloorNumber objects with the same floor number and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass.
     */
    @Test
    void sameFloorNumber_shouldReturnSameHashCode() {
        // Arrange
        Integer value = 1;
        FloorNumber floorNumber1 = new FloorNumber(value);
        FloorNumber floorNumber2 = new FloorNumber(value);

        // Act
        int hashCode1 = floorNumber1.hashCode();
        int hashCode2 = floorNumber2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    /**
     * Test to ensure that the equals method of the FloorNumber class returns false when the object compared is null.
     * This test creates a FloorNumber object and checks if it is equal to null.
     * If the equals method is implemented correctly, the test will pass when isEqual is false.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        FloorNumber floorNumber = new FloorNumber(1);

        // Act
        boolean isEqual = floorNumber.equals(null);

        // Assert
        assertFalse(isEqual);
    }
}
