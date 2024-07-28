package smartHomeDDD.domain.domain.sensorModel;

import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.ImplFactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This Test class encompasses the tests to the Aggregate sensorModel. It lists the following scenarios:
 * Valid instantiation;
 * Unsuccessful instantiation due to invalid name;
 * Unsuccessful instantiation due to invalid ID;
 * Equality between an sensorModel object and itself;
 * Equality between two identical sensorModel objects;
 * Equality between two sensorModel objects with the same ID and different names;
 * Equality between two sensorModel objects with the same name and different IDs;
 * Equality between an sensorModel object's ID and its identity;
 */
class SensorModelAggregateTest {

    /**
     * Tests if a valid instantiation of sensorModel results in a non-null object with expected attributes.
     */
    @Test
    void validInstantiation_ShouldReturnANonNullObjectWithValidAttributes() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorModelID expectedModelID = new SensorModelID("PC500W");
        SensorTypeID expectedTypeID = new SensorTypeID("SensorTypeID");
        // Act
        SensorModel mySensorModel = modelFactory.createSensorModel(expectedModelID, expectedTypeID);
        // Assert
        assertNotNull(mySensorModel);
        assertEquals(expectedModelID, mySensorModel.identity());
    }

    /**
     * Tests if passing a null SensorModelID to the constructor throws an IllegalArgumentException.
     */
    @Test
    void invalidModelID_ShouldReturnTrueDueToException() {
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorTypeID sensorTypeID = new SensorTypeID("SensorTypeID");
        Exception exception = assertThrows(Exception.class, () ->
                modelFactory.createSensorModel(null, sensorTypeID));
        String expectedMessage = "Parameter 'sensorModelID' cannot be null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests if passing a null SensorTypeID to the constructor throws an IllegalArgumentException.
     */
    @Test
    void invalidTypeID_ShouldReturnTrueDueToException() {
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorModelID modelID = new SensorModelID("PC500W");
        Exception exception = assertThrows(Exception.class, () ->
                modelFactory.createSensorModel(modelID, null));
        String expectedMessage = "Parameter 'sensorTypeID' cannot be null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests if an sensorModel equals itself.
     */
    @Test
    void sensorModelEqualToItself_ShouldReturnEqualObject() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorModelID modelID = new SensorModelID("PC500W");
        SensorTypeID typeIDDouble = new SensorTypeID("SensorTypeID");
        SensorModel mySensorModel = modelFactory.createSensorModel(modelID, typeIDDouble);
        // Act
        boolean isEquals = mySensorModel.equals(mySensorModel);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if an sensorModel is not equal to null.
     */
    @Test
    void sensorModelNotEqualToANullOne_ShouldReturnFalse() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorModelID modelIDDouble = new SensorModelID("PC500W");
        SensorTypeID typeIDDouble = new SensorTypeID("SensorTypeID");
        SensorModel mySensorModel = modelFactory.createSensorModel(modelIDDouble, typeIDDouble);
        // Act
        boolean isEquals = mySensorModel.equals(null);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if an sensorModel is considered equal to another sensorModel with the same name and ID.
     */
    @Test
    void sensorModelEqualToAnotherEqualSensorModel_ShouldReturnTrue() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorTypeID typeIDDouble = new SensorTypeID("SensorTypeID");
        SensorModel mySensorModel1 = modelFactory.createSensorModel(new SensorModelID("PC500W"), typeIDDouble);
        SensorModel mySensorModel2 = modelFactory.createSensorModel(new SensorModelID("PC500W"), typeIDDouble);
        // Act
        boolean isEquals = mySensorModel1.sameAs(mySensorModel2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if an sensorModel is considered equal to another sensorModel with the same name and a different ID.
     */
    @Test
    void sensorModelNotEqualToOtherWithDifferentModelID_ShouldReturnFalse() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorTypeID typeIDDouble = new SensorTypeID("SensorTypeID");
        SensorModel mySensorModel1 = modelFactory.createSensorModel(new SensorModelID("PC500W"), typeIDDouble);
        SensorModel mySensorModel2 = modelFactory.createSensorModel(new SensorModelID("GA100K"), typeIDDouble);
        // Act
        boolean isEquals = mySensorModel1.sameAs(mySensorModel2);
        assertFalse(isEquals);
    }

    /**
     * Tests if an sensorModel is considered equal to another sensorModel with the same name and a different ID.
     */
    @Test
    void sensorTpeNotEqualToOtherWithDifferentTypeID_ShouldReturnFalse() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorTypeID typeIDDouble1 = new SensorTypeID("SensorTypeID");
        SensorTypeID typeIDDouble2 = new SensorTypeID("SensorTypeID2");
        SensorModel mySensorModel1 = modelFactory.createSensorModel(new SensorModelID("PC500W"), typeIDDouble1);
        SensorModel mySensorModel2 = modelFactory.createSensorModel(new SensorModelID("PC500W"), typeIDDouble2);
        // Act
        boolean isEquals = mySensorModel1.sameAs(mySensorModel2);
        assertFalse(isEquals);
    }

    /**
     * Tests if the identity of a Sensor is the same as its ID.
     */
    @Test
    void identity_ShouldReturnModelID() {
        // Arrange
        FactorySensorModel modelFactory = new ImplFactorySensorModel();
        SensorTypeID typeIDDouble = new SensorTypeID("SensorTypeID");
        SensorModelID modelID = new SensorModelID("PC500W");
        SensorModel sensorModel = modelFactory.createSensorModel(modelID, typeIDDouble);
        // Act and Assert
        assertEquals(modelID, sensorModel.identity());
    }

}

