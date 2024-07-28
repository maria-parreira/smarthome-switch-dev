package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.PC500WValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The PC500WValueTest class provides unit tests for the PC500WValue class.
 * It encompasses the following scenarios:
 * - Creating a PC500WValue with a negative value.
 * - Creating a PC500WValue with a zero value.
 * - Creating a PC500WValue with a positive value.
 * - Getting the string representation of a PC500WValue.
 * - Getting the string representation of a PC500WValue with an incorrect value.
 * - Getting the value of a PC500WValue.
 * - Comparing the same object with the equals method.
 * - Comparing different objects with the same value with the equals method.
 * - Comparing different objects with different values with the equals method.
 * - Comparing an object with null with the equals method.
 * - Getting the hash code of the same PC500WValue object.
 */
class PC500WValueTest {


    /**
     * Test case for creating a PC500WValue with a negative value.
     * The test verifies that the PC500WValue constructor throws an IllegalArgumentException when provided with a negative value.
     */
    @Test
    void negativeValue_ShouldThrowException() {
        // arrange
        double expectedValue = -1.0;
        String expectedMessage = "Invalid value. Value cannot be negative.";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PC500WValue(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case for creating a PC500WValue with a zero value.
     * The test verifies that the PC500WValue constructor returns a non-null object when provided with a zero value.
     */
    @Test
    void zeroValue_ShouldInstantiate() {
        // arrange
        double expectedValue = 0.0;

        // act
        PC500WValue pc500WValue = new PC500WValue(expectedValue);

        // assert
        assertEquals(expectedValue, Double.parseDouble(pc500WValue.toString()), 0.01);
    }

    /**
     * Test case for creating a PC500WValue with a positive value.
     * The test verifies that the PC500WValue constructor sets the correct value when provided with a positive value.
     */
    @Test
    void positiveValue_ShouldSetCorrectValue() {
        // arrange
        double expectedValue = 1.0;

        // act
        PC500WValue pc500WValue = new PC500WValue(expectedValue);

        // assert
        assertEquals(expectedValue, Double.parseDouble(pc500WValue.toString()), 0.01);
    }

    /**
     * Test case for getting the string representation of a PC500WValue.
     * The test verifies that the toString method of the PC500WValue class returns the correct string representation of the value.
     */
    @Test
    void toString_ShouldReturnCorrectString() {
        // arrange
        double nValue = 10.0;
        String expected = "10.0";

        // act
        PC500WValue pc500WValue = new PC500WValue(nValue);

        // assert
        assertEquals(expected, pc500WValue.toString());
    }

    /**
     * Test case for getting the string representation of a PC500WValue with an incorrect value.
     * The test verifies that the toString method of the PC500WValue class does not return the incorrect string representation of the value.
     */
    @Test
    void incorrectValueToString_ShouldReturnDifferentString() {
        // arrange
        double nValue = 40.0;
        String incorrect = "50.0";

        // act
        PC500WValue pc500WValue = new PC500WValue(nValue);

        // assert
        assertNotEquals(incorrect, pc500WValue.toString());
    }

    /**
     * Test case for the getValue method in the PC500WValue class.
     * The test verifies that the getValue method returns the correct value that was set during the instantiation of the PC500WValue object.
     * The test uses an expected value of 1.0 for the instantiation of the PC500WValue object and asserts that the returned value from the getValue method equals the expected value.
     *
     */
    @Test
    void getValue_ShouldReturnCorrectValue() {
        // Arrange
        double expectedValue = 1.0;
        PC500WValue pc500WValue = new PC500WValue(expectedValue);

        // Act
        double actualValue = pc500WValue.get_Value();

        // Assert
        assertEquals(expectedValue, actualValue, 0.01);
    }


    /**
     * Test case for the equals method in the PC500WValue class when comparing the same object.
     * The test verifies that the equals method returns true when the same object is compared.
     *
     * @throws IllegalArgumentException if an error occurs during the instantiation of the PC500WValue object.
     */

    @Test
    void equals_SameObject_ShouldReturnTrue() throws IllegalArgumentException {
        // Arrange
        PC500WValue value1 = new PC500WValue(100.0);

        // Act & Assert
        assertEquals(value1, value1);
    }

    /**
     * Test case for the equals method in the PC500WValue class when comparing different objects with the same value.
     * The test verifies that the equals method returns true when different objects with the same value are compared.
     *
     * @throws IllegalArgumentException if an error occurs during the instantiation of the PC500WValue object.
     */
    @Test
    void equals_DifferentObjectSameValue_ShouldReturnTrue() throws IllegalArgumentException {
        // Arrange
        PC500WValue value1 = new PC500WValue(100.0);
        PC500WValue value2 = new PC500WValue(100.0);

        // Act & Assert
        assertEquals(value1, value2);
    }

    /**
     * Test case for the equals method in the PC500WValue class when comparing different objects with different values.
     * The test verifies that the equals method returns false when different objects with different values are compared.
     *
     * @throws IllegalArgumentException if an error occurs during the instantiation of the PC500WValue object.
     */
    @Test
    void equals_DifferentObjectDifferentValue_ShouldReturnFalse() throws IllegalArgumentException {
        // Arrange
        PC500WValue value1 = new PC500WValue(100.0);
        PC500WValue value2 = new PC500WValue(200.0);

        // Act & Assert
        assertNotEquals(value1, value2);
    }

    /**
     * Test case for the equals method in the PC500WValue class when comparing an object with null.
     * The test verifies that the equals method returns false when an object is compared with null.
     *
     * @throws IllegalArgumentException if an error occurs during the instantiation of the PC500WValue object.
     */
    @Test
    void equals_NullObject_ShouldReturnFalse() throws IllegalArgumentException {
        // Arrange
        PC500WValue value1 = new PC500WValue(100.0);

        // Act & Assert
        assertNotEquals(null, value1);
    }

    /**
     * Test to ensure that the hashCode method of the PC500WValue class returns the same hash code for two PC500WValue objects with the same power consumption value.
     * This test creates two PC500WValue objects with the same power consumption value and checks if their hash codes are equal.
     * If the hashCode method is implemented correctly, the test will pass when the hash codes of the two PC500WValue objects are equal.
     */
    @Test
    void hashCode_samePowerConsumptionValue_ShouldReturnSameHashCode() {
        // Arrange
        double powerConsumptionValue = 100.0;

        PC500WValue pc500WValue1 = new PC500WValue(powerConsumptionValue);
        PC500WValue pc500WValue2 = new PC500WValue(powerConsumptionValue);

        // Act
        int hashCode1 = pc500WValue1.hashCode();
        int hashCode2 = pc500WValue2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

}

