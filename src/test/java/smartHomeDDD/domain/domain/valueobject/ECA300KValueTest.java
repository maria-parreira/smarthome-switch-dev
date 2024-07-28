package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.ECA300KValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class contains a set of test cases designed to verify the behavior and functionality of the ECA300KValue class.
 * Each test case explores different scenarios, including edge cases, to ensure the reliability and correctness of the ECA300KValue implementation.
 * <p>
 * It encompasses the following scenarios:
 * - Creating a ECA300KValue with valid measurements.
 * - Creating a ECA300KValue with invalid measurements.
 */
class ECA300KValueTest {


    /**
     * Test case for creating a EEC300KValue with a negative value.
     * The test verifies that the EEC300KValue constructor throws an IllegalArgumentException when provided with a negative value.
     */
    @Test
    void negativeValue_ShouldThrowException() {
        // arrange
        double expectedValue = -1.0;
        String expectedMessage = "The measurement value is not valid: -1.0";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ECA300KValue(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case for getting the string representation of a EEC300KValue.
     * The test verifies that the toString method of the EEC300KValue class returns the correct string representation of the value.
     */
    @Test
    void positiveValue_ShouldReturnCorrectString_ElectricEnergyConsumption() {
        // arrange
        double nValue = 10.0;
        String expected = "Electrical Energy Consumption: 10.0";

        // act
        ECA300KValue value = new ECA300KValue(nValue);

        // assert
        assertEquals(expected, value.toString());
    }

    /**
     * Test case for getting the string representation of a EEC300KValue with an incorrect value.
     * The test verifies that the toString method of the EEC300KValue class does not return the incorrect string representation of the value.
     */
    @Test
    void incorrectValueToString_ShouldReturnDifferentString() {
        // arrange
        double nValue = 40.0;
        String incorrect = "50.0";

        // act
        ECA300KValue value = new ECA300KValue(nValue);

        // assert
        assertFalse(value.toString().contains(incorrect));
    }
}
