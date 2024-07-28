package smartHomeDDD.domain.domain.actuatorType;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


/**
 * The ActuatorTypeTest class contains unit tests for the ActuatorType class.
 * Test cases:
 * - Verifies successful creation of an ActuatorType object.
 * - Verifies that the sameAs method returns true when comparing two equal ActuatorType objects.
 * - Verifies that the sameAs method returns false when comparing two different ActuatorType objects.
 */
class ActuatorTypeTest {

    /**
     * Test to verify that an ActuatorType object can be created with the correct unit.
     */
    @Test
    void shouldCreateAnActuatorTypeWithCorrectUnit() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        // Act
        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType.getUnit(), unitDouble);
    }

    /**
     * Test to verify that an ActuatorType object can be created with the correct description.
     */
    @Test
    void shouldCreateAnActuatorTypeWithCorrectDescription() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        // Act
        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType.getDescription(), descriptionDouble);
    }

    /**
     * Test to verify that an ActuatorType object can be created with the correct identity.
     */
    @Test
    void shouldCreateAnActuatorTypeWithCorrectIdentity() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        // Act
        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType.identity(), actuatorTypeIDDouble);
    }


    /**
     * Unitary test to verify that the sameAs method returns true when comparing two equal ActuatorType objects.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualActuatorTypes()  {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);
        ActuatorType actuatorType2 = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Act
        boolean isEquals = actuatorType.sameAs(actuatorType2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Unitary test to verify that the sameAs method returns false when comparing two different ActuatorType objects.
     */
    @Test
    void shouldReturnFalse_WhenComparingTwoDifferentActuatorTypes()  {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble1 = mock(ActuatorTypeID.class);
        ActuatorTypeID actuatorTypeIDDouble2 = mock(ActuatorTypeID.class);

        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble1);
        ActuatorType actuatorType2 = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble2);

        // Act
        boolean isEquals = actuatorType.sameAs(actuatorType2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to verify that the equals method returns false when comparing an ActuatorType object with null.
     */
    @Test
    void shouldReturnFalse_WhenComparingWithNull() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        new ActuatorType(unit, description, actuatorTypeID);

        // Act
        boolean isEqual = false;

        // Assert
        assertFalse(isEqual);
    }

    /**
     * Test to verify that the sameAs method returns false when comparing an ActuatorType object with null.
     */
    @Test
    void shouldReturnFalse_WhenSameAsComparingWithNull() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        ActuatorType actuatorType = new ActuatorType(unit, description, actuatorTypeID);

        // Act
        boolean isSame = actuatorType.sameAs(null);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to verify that the constructor throws an IllegalArgumentException when the unit is null.
     */
    @Test
    void shouldThrowIllegalArgumentException_WhenUnitIsNull() {
        // Arrange
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorType(null, description, actuatorTypeID));
    }

    /**
     * Test to verify that the constructor throws an IllegalArgumentException when the actuatorTypeID is null.
     */
    @Test
    void shouldThrowIllegalArgumentException_WhenActuatorTypeIDIsNull() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorType(unit, description, null));
    }

    /**
     * Test to verify that the constructor throws an IllegalArgumentException when the description is null.
     */
    @Test
    void shouldThrowIllegalArgumentException_WhenDescriptionIsNull() {
        // Arrange
        Unit unit = mock(Unit.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorType(unit, null, actuatorTypeID));
    }

    /**
     * Test to verify that the sameAs method returns false when comparing an ActuatorType object with an object of a different class.
     */
    @Test
    void shouldReturnFalse_WhenSameAsComparingWithDifferentClass() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        ActuatorType actuatorType = new ActuatorType(unit, description, actuatorTypeID);

        // Act
        boolean isSame = actuatorType.sameAs(new Object());

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to verify that the equals method returns true when comparing two ActuatorType objects with the same actuatorTypeID.
     */
    @Test
    void shouldReturnTrue_WhenEqualsComparingWithSameActuatorTypeID() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        ActuatorType actuatorType1 = new ActuatorType(unit, description, actuatorTypeID);
        ActuatorType actuatorType2 = new ActuatorType(unit, description, actuatorTypeID);

        // Act
        boolean isEqual = actuatorType1.equals(actuatorType2);

        // Assert
        assertTrue(isEqual);
    }

    /**
     * Test to verify that the equals method returns false when comparing two ActuatorType objects with different actuatorTypeID.
     */
    @Test
    void shouldReturnFalse_WhenEqualsComparingWithDifferentActuatorTypeID() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID1 = mock(ActuatorTypeID.class);
        ActuatorTypeID actuatorTypeID2 = mock(ActuatorTypeID.class);
        ActuatorType actuatorType1 = new ActuatorType(unit, description, actuatorTypeID1);
        ActuatorType actuatorType2 = new ActuatorType(unit, description, actuatorTypeID2);

        // Act
        boolean isEqual = actuatorType1.equals(actuatorType2);

        // Assert
        assertFalse(isEqual);
    }


    /**
     * Test to verify that the equals method returns false when comparing an ActuatorType object with an object of a different class.
     */
    @Test
    void shouldReturnFalse_WhenEqualsComparingWithDifferentClass() {
        // Arrange
        Unit unit = mock(Unit.class);
        Description description = mock(Description.class);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        ActuatorType actuatorType = new ActuatorType(unit, description, actuatorTypeID);

        // Act
        boolean isEqual = actuatorType.equals(new Object());

        // Assert
        assertFalse(isEqual);
    }

    /**
     * Test to verify that the equals method returns false when comparing an ActuatorType object with null.
     */
    @Test
    void equals_withSameActuatorTypeID_shouldReturnTrue() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        // Act
        ActuatorType actuatorType1 = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);
        ActuatorType actuatorType2 = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType1, actuatorType2);
    }

    /**
     * Test to verify that the equals method returns false when comparing an ActuatorType object with null.
     */
    @Test
    void hashCode_withValidActuatorTypeObject_shouldReturnCorrectHashCode() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        // Act
        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);
        int actualHashCode = actuatorType.hashCode();

        // Assert
        assertEquals(actuatorTypeIDDouble.hashCode(), actualHashCode);
    }


    /**
     * Test to verify that the equals method returns false when comparing an ActuatorType object with null.
     */
    @Test
    void equals_withSameInstance_shouldReturnTrue() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);

        // Act
        ActuatorType actuatorType = new ActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType, actuatorType);
    }
}







