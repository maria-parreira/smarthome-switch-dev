package smartHomeDDD.domain.domain.sensorType;

import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
 */

class SensorTypeAggregateTest {

    /**
     * Test to verify that an sensorType object can be created successfully.
     */
    @Test
    void validSensor_shouldCreateAnSensorType() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeIDDouble = new SensorTypeID("SensorTypeID");


        // Act
        SensorType sensorType = implFactorySensorType.createSensorType(sensorTypeIDDouble, description, unit);

        // Assert
        assertNotNull(sensorType);
        assertEquals(sensorType.identity(), sensorTypeIDDouble);
        assertEquals(sensorType.getDescription(), description);
        assertEquals(sensorType.getUnit(), unit);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when attempting to create an sensorType with a null unit.
     */
    @Test
    void nullUnit_ShouldThrowException() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = null;
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeIDDouble = new SensorTypeID("SensorTypeID");

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            implFactorySensorType.createSensorType(sensorTypeIDDouble, description, unit);
        });

        String expectedMessage = "Parameter 'unit' cannot be null.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when attempting to create an sensorType with a null description.
     */
    @Test
    void nullDescription_ShouldThrowException() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = null;
        SensorTypeID sensorTypeIDDouble = new SensorTypeID("SensorTypeID");

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            implFactorySensorType.createSensorType(sensorTypeIDDouble, description, unit);
        });

        String expectedMessage = "Parameter 'description' cannot be null.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when attempting to create an sensorType with a null sensorTypeID.
     */
    @Test
    void nullId_ShouldThrowException() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeIDDouble = null;

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            implFactorySensorType.createSensorType(sensorTypeIDDouble, description, unit);
        });

        String expectedMessage = "Parameter 'id' cannot be null.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that the sameAs method returns true when comparing two equal sensorType objects.
     */
    @Test
    void twoEqualSensorTypes_ShouldReturnTrueWhenComparingSensorTypes() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeIDDouble = new SensorTypeID("SensorTypeID");

        SensorType sensorType = implFactorySensorType.createSensorType(sensorTypeIDDouble, description, unit);

        // Act
        boolean isEquals = sensorType.sameAs(sensorType);

        // Assert
        assertTrue(isEquals);
    }


    /**
     * Test to verify that the sameAs method returns false when comparing two different sensorType objects.
     */
    @Test
    void twoDifferentSensorTypes_ShouldReturnFalseWhenComparingSensorTypes() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeIDDouble = new SensorTypeID("SensorTypeID");

        SensorType sensorType1 = implFactorySensorType.createSensorType(sensorTypeIDDouble, description, unit);
        Object obj = new Object();

        // Act
        boolean isEquals = sensorType1.sameAs(obj);

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
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeID1 = new SensorTypeID("SensorTypeID1");
        SensorTypeID sensorTypeID2 = new SensorTypeID("SensorTypeID2");

        SensorType sensorType1 = implFactorySensorType.createSensorType(sensorTypeID1, description, unit);
        SensorType sensorType2 = implFactorySensorType.createSensorType(sensorTypeID2, description, unit);

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
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit1 = new Unit("Celsius");
        Unit unit2 = new Unit("Kelvin");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeID = new SensorTypeID("SensorTypeID");

        SensorType sensorType1 = implFactorySensorType.createSensorType(sensorTypeID, description, unit1);
        SensorType sensorType2 = implFactorySensorType.createSensorType(sensorTypeID, description, unit2);

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
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description1 = new Description("Temperature");
        Description description2 = new Description("Humidity");
        SensorTypeID sensorTypeID = new SensorTypeID("SensorTypeID");

        SensorType sensorType1 = implFactorySensorType.createSensorType(sensorTypeID, description1, unit);
        SensorType sensorType2 = implFactorySensorType.createSensorType(sensorTypeID, description2, unit);

        // Act
        boolean result = sensorType1.sameAs(sensorType2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing a sensorType object with itself.
     * The test verifies that the equals method of the sensorType class returns true when comparing the same object.
     */
    @Test
    void whenComparingItself_shouldReturnTrue() {

        // Arrange
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeID = new SensorTypeID("SensorTypeID");

        SensorType sensorType = implFactorySensorType.createSensorType(sensorTypeID, description, unit);

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
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeID = new SensorTypeID("SensorTypeID");

        SensorType sensorType1 = implFactorySensorType.createSensorType(sensorTypeID, description, unit);
        SensorType sensorType2 = implFactorySensorType.createSensorType(sensorTypeID, description, unit);

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
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit1 = new Unit("Celsius");
        Unit unit2 = new Unit("Kelvin");
        Description description1 = new Description("Temperature");
        Description description2 = new Description("Humidity");
        SensorTypeID sensorTypeID1 = new SensorTypeID("SensorTypeID1");
        SensorTypeID sensorTypeID2 = new SensorTypeID("SensorTypeID2");

        SensorType sensorType1 = implFactorySensorType.createSensorType(sensorTypeID1, description1, unit1);
        SensorType sensorType2 = implFactorySensorType.createSensorType(sensorTypeID2, description2, unit2);

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
        ImplFactorySensorType implFactorySensorType = new ImplFactorySensorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeID = new SensorTypeID("SensorTypeID");

        SensorType sensorType = implFactorySensorType.createSensorType(sensorTypeID, description, unit);
        Object obj = new Object();

        // Act
        boolean result = sensorType.equals(obj);

        // Assert
        assertFalse(result);
    }

}
