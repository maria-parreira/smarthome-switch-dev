package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.SPV300Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SPV300Value.
 * It tests the following scenarios:
 * - instantiating a SIV280Value with a valid value
 * - instantiating a SIV280Value with a value below the minimum value
 * - instantiating a SIV280Value with a value above the maximum value
 * - instantiating a SIV280Value with the minimum value
 * - instantiating a SIV280Value with the maximum value
 */

class SPV300ValueTest {

    /**
     * Tests the constructor of the SPV300Value class.
     * It checks whether the constructor correctly sets the value when it is within the range.
     * @throws IllegalArgumentException if the value is not within the valid range
     */
    @Test
    void validSPV300Value_shouldCreateNewInstance() {
        // arrange
        double valueToRound = 51.55141;
        double expectedValue = 51.551;

        // act
        SPV300Value spv300Value = new SPV300Value(valueToRound);

        // assert
        assertEquals(expectedValue, spv300Value.getValue());

    }

    /**
     * Tests the constructor of the SPV300Value class.
     * It checks whether the constructor correctly throws an InstantiationException when the value is lower than the minimum value.
     * @throws IllegalArgumentException if the value is lower than the minimum value
     */
    @Test
    void invalidMinSPV300Value_shouldThrowException(){
        // arrange
        double expectedValue = -1;
        String expectedMessage = "Value is out of range";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SPV300Value(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);

    }

    /**
     * Tests the constructor of the SPV300Value class.
     * It checks whether the constructor correctly throws an InstantiationException when the value is greater than the maximum value.
     * @throws IllegalArgumentException if the value is greater than the maximum value
     */
    @Test
    void invalidMaxSPV300Value_shouldThrowException(){
        // arrange
        double expectedValue = 101;
        String expectedMessage = "Value is out of range";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SPV300Value(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);

    }

    /**
     * Tests the constructor of the SPV300Value class.
     * It checks whether the constructor correctly sets the value when it is the minimum value.
     */
    @Test
    void validMinSPV300Value_shouldCreateNewInstance() {
        // arrange
        int expectedValue = 0;

        // act
        SPV300Value spv300Value = new SPV300Value(expectedValue);

        // assert
        assertEquals(expectedValue, spv300Value.getValue());
    }

    /**
     * Tests the constructor of the SPV300Value class.
     * It checks whether the constructor correctly sets the value when it is the maximum value.
     */
    @Test
    void validMaxSPV300Value_shouldCreateNewInstance() {
        // arrange
        double expectedValue = 100.0;
        String expectedStrValue = String.valueOf(expectedValue);

        // act
        SPV300Value spv300Value = new SPV300Value(expectedValue);

        // assert
        assertEquals(expectedStrValue, spv300Value.toString());
    }

    /**
     * Test case to verify that the equals method returns true when the same instance is compared.
     */
    @Test
    void equals_sameInstance_shouldReturnTrue() {
        // Arrange
        SPV300Value spv300Value = new SPV300Value(22);

        // Act & Assert
        assertEquals(spv300Value, spv300Value);
    }

    /**
     * Test case to verify that the equals method returns true when two different instances have the same _nValue.
     */
    @Test
    void equals_same_nValue_shouldReturnTrue() {
        // Arrange
        SPV300Value spv300Value1 = new SPV300Value(22);
        SPV300Value spv300Value2 = new SPV300Value(22);

        // Act & Assert
        assertEquals(spv300Value1, spv300Value2);
    }

    /**
     * Test case to verify that the equals method returns false when the other object is null.
     */
    @Test
    void equals_nullObject_shouldReturnFalse() {
        // Arrange
        SPV300Value spv300Value = new SPV300Value(22);

        // Act & Assert
        assertNotEquals(null, spv300Value);
    }

    /**
     * Test case to verify that the equals method returns false when the other object is not an instance of SPV300Value.
     */
    @Test
    void equals_differentClass_shouldReturnFalse() {
        // Arrange
        SPV300Value spv300Value = new SPV300Value(22);
        String notASPV300Value = "notASPV300Value";

        // Act & Assert
        assertNotEquals(spv300Value, notASPV300Value);
    }

    /**
     * Test case to verify that the equals method returns false when the other SPV300Value instance has a different _nValue.
     */
    @Test
    void equals_different_nValue_shouldReturnFalse() {
        // Arrange
        SPV300Value spv300Value1 = new SPV300Value(22);
        SPV300Value spv300Value2 = new SPV300Value(23);

        // Act & Assert
        assertNotEquals(spv300Value1, spv300Value2);
    }

    /**
     * Test to verify that the hashCode method returns the correct hash code for a valid SPV300Value object.
     */
    @Test
    void hashCode_withValidSPV300ValueObject_shouldReturnCorrectHashCode() {
        // Arrange
        double expectedValue = 22.123;
        SPV300Value spv300Value = new SPV300Value(expectedValue);

        // Act
        int hashCode = spv300Value.hashCode();

        // Assert
        assertEquals(Double.hashCode(expectedValue), hashCode);
    }

}
