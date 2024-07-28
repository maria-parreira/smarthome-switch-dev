package smartHomeDDD.domain.domain.valueobject;


import smartHomeDDD.domain.valueobject.OPNCL0100Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OPNCL0100ValueTest {
    /**
     * Tests the creation of an OPNCL0100Value instance with a valid parameter.
     * The test asserts that the instance is created successfully and the value is set correctly.
     */
    @Test
    void validOPNCL0100Value_ShouldCreateNewInstance() {
        // arrange
        int expectedValue = 50;

        // act
        OPNCL0100Value opncl0100Value = new OPNCL0100Value(expectedValue);
        // assert
        assertEquals(expectedValue, opncl0100Value.getValue());
    }

    /**
     * Tests the creation of an OPNCL0100Value instance with an invalid minimum parameter.
     * The test asserts that an InstantiationException is thrown.
     * @throws IllegalArgumentException if the value is lower than the minimum value
     */
    @Test
    void invalidMinOPNCL0100Value_ShouldThrowException() throws IllegalArgumentException {
        // arrange
        int expectedValue = -1;
        String expectedMessage = "Value out of range";
        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new OPNCL0100Value(expectedValue)
        );
        String actualMessage = exception.getMessage();
        // assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Tests the creation of an OPNCL0100Value instance with an invalid maximum parameter.
     * The test asserts that an InstantiationException is thrown.
     * @throws IllegalArgumentException if the value is greater than the maximum value
     */
    @Test
    void invalidMaxOPNCL0100Value_ShouldThrowException() throws IllegalArgumentException {
        // arrange
        int expectedValue = 101;
        String expectedMessage = "Value out of range";
        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new OPNCL0100Value(expectedValue)
        );
        String actualMessage = exception.getMessage();
        // assert
        assertEquals(expectedMessage, actualMessage);
    }
    /**
     * Tests the creation of an OPNCL0100Value instance with the minimum valid parameter.
     * The test asserts that the instance is created successfully and the value is set correctly.
     */
    @Test
    void minValueOPNCL0100Value_ShouldCreateNewInstance() {
        // arrange
        int expectedValue = 0;

        // act
        OPNCL0100Value opncl0100Value = new OPNCL0100Value(expectedValue);

        // assert
        assertEquals(expectedValue, opncl0100Value.getValue());
    }
    /**
     * Tests the creation of an OPNCL0100Value instance with the maximum valid parameter.
     * The test asserts that the instance is created successfully and the value is set correctly.
     * @throws IllegalArgumentException if the value is not within the valid range
     */
    @Test
    void maxValueOPNCL0100Value_ShouldCreateNewInstance() throws IllegalArgumentException {
        // arrange
        int expectedValue = 100;

        // act
        OPNCL0100Value opncl0100Value = new OPNCL0100Value(expectedValue);

        // assert
        assertEquals(expectedValue, opncl0100Value.getValue());
    }

    /**
     * Tests the toString method of the OPNCL0100Value class.
     * The test asserts that the method returns the correct string representation of the value.
     */
    @Test
    void toString_ShouldReturnStringValue() throws IllegalArgumentException {
        // arrange
        int expectedValue = 50;
        String expectedString = String.valueOf(expectedValue);

        // act
        OPNCL0100Value opncl0100Value = new OPNCL0100Value(expectedValue);
        String actualString = opncl0100Value.toString();

        // assert
        assertEquals(expectedString, actualString);
    }

    /**
     * Test to verify that the hashCode method returns a non-zero value for a valid OPNCL0100Value object.
     */
    @Test
    void hashCode_nonZeroForValidOPNCL0100Value() {
        // Arrange
        int validValue = 50;
        OPNCL0100Value opncl0100Value = new OPNCL0100Value(validValue);

        // Act
        int hashCode = opncl0100Value.hashCode();

        // Assert
        assertNotEquals(0, hashCode);
    }
}
