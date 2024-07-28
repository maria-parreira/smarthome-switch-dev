package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;
import smartHomeDDD.domain.valueobject.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * The UnitTest class provides unit tests for the Unit class.
 * It encompasses the following scenarios:
 * - Creating a valid Unit
 * - Creating a Unit with invalid input
 * - Comparing a Unit to null
 * - Comparing a Unit to itself
 * - Comparing different instances of Unit with the same value
 * - Getting the string representation of a Unit
 */
class UnitTest {

    /**
     * Test case to ensure that a valid Unit can be created.
     * The test verifies that the Unit constructor returns a non-null object when provided with a valid unit value.
     */
    @Test
    void validUnit_shouldCreateAValidUnit()  {
        // Arrange
        String unitValue = "m";

        // Act + Assert
        new Unit(unitValue);
    }

    /**
     * Test case for creating a Unit with invalid input.
     * The test verifies that the Unit constructor throws an IllegalArgumentException when provided with an empty or whitespace-only unit value.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidUnit_shouldThrowException(String value)  {
        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Unit(value));

        String expectedMessage = "Unit cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to ensure that the equals method returns true when comparing different instances of Unit with the same value.
     */
    @Test
    void differentObjectSameUnit_shouldReturnTrue()  {
        // Arrange
        Unit unit1 = new Unit("meters");
        Unit unit2 = new Unit("meters");

        // Act
        boolean isEquals = unit1.equals(unit2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test case to ensure that the toString method returns the correct unit value string.
     */
    @Test
    void validUnitToString_ShouldReturnTheUnitValueString() {
        // Arrange
        String expected = "meters";
        Unit unit = new Unit(expected);

        // Act
        String actual = unit.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test case to ensure that the isUnitValid method returns true for valid unit values.
     */
    @ParameterizedTest
    @ValueSource(strings = {"m", "kg", "cm"})
    void isUnitValid_validUnits_shouldReturnTrue(String unit) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        Unit unitObj = new Unit("m");

        // Act
        Method method = Unit.class.getDeclaredMethod("isUnitValid", String.class);
        method.setAccessible(true);
        boolean isValid = (boolean) method.invoke(unitObj, unit);

        // Assert
        assertTrue(isValid);
    }

    /**
     * Test case to ensure that the isUnitValid method returns true for valid unit values.
     */
    @ParameterizedTest
    @ValueSource(strings = {"m", "kg", "cm"})
    void isUnitValid_validUnits_shouldReturnTrue_(String unit) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        Unit unitObj = new Unit("m");

        // Act
        Method method = Unit.class.getDeclaredMethod("isUnitValid", String.class);
        method.setAccessible(true);
        boolean isValid = (boolean) method.invoke(unitObj, unit);

        // Assert
        assertTrue(isValid);
    }

    /**
     * Test case to ensure that the isUnitValid method returns false for null unit values.
     */
    @Test
    void isUnitValid_nullUnit_shouldReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        Unit unitObj = new Unit("m");

        // Act
        Method method = Unit.class.getDeclaredMethod("isUnitValid", String.class);
        method.setAccessible(true);
        boolean isValid = (boolean) method.invoke(unitObj, (Object) null);

        // Assert
        assertFalse(isValid);
    }

    /**
     * Test case to ensure that the equals method returns false when the object being compared is not an instance of Unit.
     */
    @Test
    void equals_notInstanceOfUnit_shouldReturnFalse() {
        // Arrange
        Unit unit = new Unit("meters");
        ValueObject notAUnit = mock(ValueObject.class);

        // Act
        boolean isEquals = unit.equals(notAUnit);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case to ensure that the equals method returns false when the object being compared is an instance of Unit but with a different value.
     */
    @Test
    void equals_differentUnitValue_shouldReturnFalse() {
        // Arrange
        Unit unit1 = new Unit("meters");
        Unit unit2 = new Unit("kilometers");

        // Act
        boolean isEquals = unit1.equals(unit2);

        // Assert
        assertFalse(isEquals);
    }


    /**
     * Test case to ensure that the hashCode method returns the correct hash code for a given unit value.
     */
    @Test
    void hashCode_validUnit_shouldReturnCorrectHashCode() {
        // Arrange
        String unitValue = "m";
        Unit unit = new Unit(unitValue);

        // Act
        int actualHashCode = unit.hashCode();
        int expectedHashCode = unitValue.hashCode();

        // Assert
        assertEquals(expectedHashCode, actualHashCode);
    }
}
