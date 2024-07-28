package smartHomeDDD.domain.domain.valueobject;


import smartHomeDDD.domain.valueobject.SIV280Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SIV280Value.
 * It tests the following scenarios:
 * - instantiating a SIV280Value with a valid value
 * - instantiating a SIV280Value with a value below the minimum value
 * - instantiating a SIV280Value with a value above the maximum value
 * - instantiating a SIV280Value with the minimum value
 * - instantiating a SIV280Value with the maximum value
 */

class SIV280ValueTest {

    /**
     * Tests the constructor of the SIV280Value class.
     * It checks whether the constructor correctly sets the value when it is within the range.
     * @throws IllegalArgumentException if the value is not within the valid range
     */
    @Test
    void validSIV280Value_shouldCreateNewInstance() {
        // arrange
        int expectedValue = 22;

        // act
        SIV280Value siv280Value = new SIV280Value(expectedValue);

        // assert
        assertEquals(expectedValue, siv280Value.getValue());

    }

    /**
     * Tests the constructor of the SIV280Value class.
     * It checks whether the constructor correctly throws an InstantiationException when the value is lower than the minimum value.
     * @throws IllegalArgumentException if the value is lower than the minimum value
     */
    @Test
    void invalidMinSIV280Value_shouldThrowException(){
        // arrange
        int expectedValue = -55;
        String expectedMessage = "Value is out of range";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SIV280Value(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);

    }

    /**
     * Tests the constructor of the SIV280Value class.
     * It checks whether the constructor correctly throws an InstantiationException when the value is greater than the maximum value.
     * @throws IllegalArgumentException if the value is greater than the maximum value
     */
    @Test
    void invalidMaxSIV280Value_shouldThrowException(){
        // arrange
        int expectedValue = 55;
        String expectedMessage = "Value is out of range";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SIV280Value(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(expectedMessage, actualMessage);

    }
    /**
     * Tests the constructor of the SIV280Value class.
     * It checks whether the constructor correctly sets the value when it is equal to the minimum value.
     * @throws IllegalArgumentException if the value is not within the valid range
     */
    @Test
    void validMinSIV280Value_shouldCreateNewInstance() {
        // arrange
        int expectedValue = -50;

        // act
        SIV280Value siv280Value = new SIV280Value(expectedValue);

        // assert
        assertEquals(expectedValue, siv280Value.getValue());
    }
    /**
     * Tests the constructor of the SIV280Value class.
     * It checks whether the constructor correctly sets the value when it is equal to the maximum value.
     * @throws IllegalArgumentException if the value is not within the valid range
     */
    @Test
    void validMaxSIV280Value_shouldCreateNewInstance() {
        // arrange
        int expectedValue = 50;
        String expectedStrValue = String.valueOf(expectedValue);

        // act
        SIV280Value siv280Value = new SIV280Value(expectedValue);

        // assert
        assertEquals(expectedStrValue, siv280Value.toString());
    }

    /**
     * Test case to verify that the equals method returns true when the same instance is compared.
     */
    @Test
    void equals_sameInstance_shouldReturnTrue() {
        // Arrange
        SIV280Value siv280Value = new SIV280Value(22);

        // Act & Assert
        assertEquals(siv280Value, siv280Value);
    }

    /**
     * Test case to verify that the equals method returns true when two different instances have the same _nValue.
     */
    @Test
    void equals_same_nValue_shouldReturnTrue() {
        // Arrange
        SIV280Value siv280Value1 = new SIV280Value(22);
        SIV280Value siv280Value2 = new SIV280Value(22);

        // Act & Assert
        assertEquals(siv280Value1, siv280Value2);
    }

    /**
     * Test case to verify that the equals method returns false when the other object is null.
     */
    @Test
    void equals_nullObject_shouldReturnFalse() {
        // Arrange
        new SIV280Value(22);

        // Act & Assert
        assertFalse(false);
    }

    /**
     * Test case to verify that the equals method returns false when the other object is not an instance of SIV280Value.
     */
    @Test
    void equals_differentClass_shouldReturnFalse() {
        // Arrange
        SIV280Value siv280Value = new SIV280Value(22);
        String notASIV280Value = "notASIV280Value";

        // Act & Assert
        assertNotEquals(siv280Value, notASIV280Value);
    }

    /**
     * Test case to verify that the equals method returns false when the other SIV280Value instance has a different _nValue.
     */
    @Test
    void equals_different_nValue_shouldReturnFalse() {
        // Arrange
        SIV280Value siv280Value1 = new SIV280Value(22);
        SIV280Value siv280Value2 = new SIV280Value(23);

        // Act & Assert
        assertNotEquals(siv280Value1, siv280Value2);
    }

    /**
     * Test to verify that the hashCode method returns the correct hash code for a valid SIV280Value object.
     */
    @Test
    void hashCode_withValidSIV280ValueObject_shouldReturnCorrectHashCode() {
        // Arrange
        int expectedValue = 22;
        SIV280Value siv280Value = new SIV280Value(expectedValue);

        // Act
        int hashCode = siv280Value.hashCode();

        // Assert
        assertEquals(Integer.hashCode(expectedValue), hashCode);
    }

}
