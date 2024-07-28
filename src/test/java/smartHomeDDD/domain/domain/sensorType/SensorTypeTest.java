package smartHomeDDD.domain.domain.sensorType;

import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unitary test class for the sensorType entity.
 * It encompasses the following scenarios:
 * - Create a sensorType object successfully.
 * - Attempt to create a sensorType object with a null unit.
 * - Attempt to create a sensorType object with a null description.
 * - Attempt to create a sensorType object with a null sensorTypeID.
 * - Compare two equal sensorType objects.
 * - Compare two different sensorType objects.
 * - Compare two sensorType objects with different sensorTypeIds.
 * - Compare two sensorType objects with different units.
 * - Compare two sensorType objects with different descriptions.
 * - Compare a sensorType object with itself.
 * - Compare two equal sensorType objects.
 * - Compare two different sensorType objects.
 * - Compare a sensorType object with a different object.
 * - Compare hash codes in equal sensor types
 *
 */

class SensorTypeTest {

    /**
     * Unitary test to verify that an sensorType object can be created successfully.
     */
    @Test
    void validSensorType_ShouldCreateASensorType() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        // Act
        SensorType sensorType = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);

        // Assert
        assertEquals(sensorType.getUnit(), unitDouble);
        assertEquals(sensorType.getDescription(), descriptionDouble);
        assertEquals(sensorType.identity(), sensorTypeIDDouble);
    }

    /**
     * Unitary test to verify that an IllegalArgumentException is thrown when attempting to create an sensorType with a
     * null unit.
     */
    @Test
    void nullUnit_ShouldThrowException() {
        // Arrange
        Unit unit = null;
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new SensorType(unit, descriptionDouble, sensorTypeIDDouble);

        });

        String expectedMessage = "Parameter 'unit' cannot be null.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Unitary test to verify that an IllegalArgumentException is thrown when attempting to create an sensorType with a
     * null description.
     */
    @Test
    void nullDescription_ShouldThrowException() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description description = null;
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new SensorType(unitDouble, description, sensorTypeIDDouble);

        });

        String expectedMessage = "Parameter 'description' cannot be null.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Unitary test to verify that an IllegalArgumentException is thrown when attempting to create an sensorType with
     * a null sensorTypeID.
     */
    @Test
    void nullId_ShouldThrowException() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = null;

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);

        });

        String expectedMessage = "Parameter 'id' cannot be null.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Unitary test to verify that the sameAs method returns true when comparing two equal sensorType objects.
     */
    @Test
    void twoEqualSensorTypes_ShouldReturnTrueWhenComparingSensorTypes() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);
        SensorType sensorType2 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);

        // Act
        boolean isEquals = sensorType.sameAs(sensorType2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Unitary test to verify that the sameAs method returns false when comparing two different sensorType objects.
     */
    @Test
    void TwoDifferentSensorType_ShouldReturnFalse_WhenComparingSensorTypes() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble1 = mock(SensorTypeID.class);
        SensorTypeID sensorTypeIDDouble2 = mock(SensorTypeID.class);

        SensorType sensorType = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble1);
        SensorType sensorType2 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble2);

        // Act
        boolean isEquals = sensorType.sameAs(sensorType2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for comparing two sensorType objects with different SensorTypeIDs.
     * The test verifies that the sameAs method of the sensorType class returns false when comparing two objects with
     * different SensorTypeIDs.
     */
    @Test
    void differentSensorTypeIds_shouldReturnFalse() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble1 = mock(SensorTypeID.class);
        SensorTypeID sensorTypeIDDouble2 = mock(SensorTypeID.class);

        SensorType sensorType1 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble1);
        SensorType sensorType2 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble2);


        // Act
        boolean result = sensorType1.sameAs(sensorType2);

        // Assert
        assertFalse(result);
    }


    /**
     * Test case for comparing two sensorType objects with different Units.
     * The test verifies that the sameAs method of the sensorType class returns false when comparing two objects with
     * different Units.
     */
    @Test
    void differentUnits_shouldReturnFalse() {
        // Arrange
        Unit unitDouble1 = mock(Unit.class);
        Unit unitDouble2 = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType1 = new SensorType(unitDouble1, descriptionDouble, sensorTypeIDDouble);
        SensorType sensorType2 = new SensorType(unitDouble2, descriptionDouble, sensorTypeIDDouble);


        // Act
        boolean result = sensorType1.sameAs(sensorType2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two sensorType objects with different Descriptions.
     * The test verifies that the sameAs method of the sensorType class returns false when comparing two objects with
     * different Descriptions.
     */
    @Test
    void differentDescriptions_shouldReturnFalse() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble1 = mock(Description.class);
        Description descriptionDouble2 = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType1 = new SensorType(unitDouble, descriptionDouble1, sensorTypeIDDouble);
        SensorType sensorType2 = new SensorType(unitDouble, descriptionDouble2, sensorTypeIDDouble);


        // Act
        boolean result = sensorType1.sameAs(sensorType2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing a sensorType object with itself.
     * The test verifies that the equals method of the sensorType class returns true when comparing the same object.
     */    @Test
    void whenComparingItself_shouldReturnTrue() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);

        // Act
        boolean result = sensorType.equals(sensorType);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two sensorType objects with the same values.
     * The test verifies that the equals method of the sensorType class returns true when comparing two objects with
     * the same Unit, Description, and SensorTypeID.
     */
    @Test
    void twoEqualSensorTypes_shouldReturnTrue() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType1 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);
        SensorType sensorType2 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);


        // Act
        boolean result = sensorType1.equals(sensorType2);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two sensorType objects with different values.
     * The test verifies that the equals method of the sensorType class returns false when comparing two objects with
     * different Units, Descriptions, and SensorTypeIDs.
     */
    @Test
    void twoDifferentSensorTypes_shouldReturnFalse() {
        // Arrange
        Unit unitDouble1 = mock(Unit.class);
        Unit unitDouble2 = mock(Unit.class);
        Description descriptionDouble1 = mock(Description.class);
        Description descriptionDouble2 = mock(Description.class);
        SensorTypeID sensorTypeIDDouble1 = mock(SensorTypeID.class);
        SensorTypeID sensorTypeIDDouble2 = mock(SensorTypeID.class);

        SensorType sensorType1 = new SensorType(unitDouble1, descriptionDouble1, sensorTypeIDDouble1);
        SensorType sensorType2 = new SensorType(unitDouble2, descriptionDouble2, sensorTypeIDDouble2);



        // Act
        boolean result = sensorType1.equals(sensorType2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing a sensorType object with a non-sensorType object.
     * The test verifies that the equals method of the sensorType class returns false when comparing a sensorType object
     * with a non-sensorType object.
     */
    @Test
    void whenComparingSensorTypesWithDifferentObject_shouldReturnFalse() {

        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);
        Object obj = new Object();

        // Act
        boolean result = sensorType.equals(obj);

        // Assert
        assertFalse(result);
    }

    /**
     * Test to verify that the hashcode is the same for two equal sensorType objects.
     */

    @Test
    void sameHashCodeInEqualSensorTypes() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);

        SensorType sensorType1 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);
        SensorType sensorType2 = new SensorType(unitDouble, descriptionDouble, sensorTypeIDDouble);

        // Act
        int hashCode1 = sensorType1.hashCode();
        int hashCode2 = sensorType2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

}
