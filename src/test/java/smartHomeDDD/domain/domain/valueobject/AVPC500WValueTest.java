package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.AVPC500WValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class contains a set of test cases designed to verify the behavior and functionality of the AVPC500WValue class.
 * Each test case explores different scenarios, including edge cases, to ensure the reliability and correctness of the AVPC500WValue implementation.
 * It encompasses the following scenarios:
 * - Creating a AVPC500WValue with valid measurements.
 * - Creating a AVPC500WValue with invalid measurements.
 *
 */

class AVPC500WValueTest {

    /**
     * Test case for creating a APC500WValue with a negative value.
     * The test verifies that the AVPC500WValue constructor throws an IllegalArgumentException when provided with a negative value.
     */
    @Test
    void negativeValue_ShouldThrowException() {
        // arrange
        double expectedValue = -1.0;
        String expectedMessage = "The measurement value is not valid: -1.0";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AVPC500WValue(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case for getting the string representation of a APC500WValue.
     * The test verifies that the toString method of the APC500WValue class returns the correct string representation of the value.
     */
    @Test
    void toString_ShouldReturnCorrectString_AverageEnergyConsumption() {
        // arrange
        double nValue = 100.0;
        String expected = "Average Energy Consumption: 100.0";

        // act
        AVPC500WValue apc500WValue = new AVPC500WValue(nValue);

        // assert
        assertEquals(expected, apc500WValue.toString());
    }

    /**
     * Test case for getting the string representation of a APC500WValue with an incorrect value.
     * The test verifies that the toString method of the APC500WValue class does not return the incorrect string representation of the value.
     */
    @Test
    void incorrectValueToString_ShouldReturnFalse(){
        // arrange
        double nValue = 40.0;
        String incorrect = "50.0";

        // act
        AVPC500WValue apc500WValue = new AVPC500WValue(nValue);

        // assert
        assertFalse(apc500WValue.toString().contains(incorrect));
    }
}
