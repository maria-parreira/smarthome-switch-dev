package smartHomeDDD.domain.domain.sensorModel;

import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
 * Equality between an sensorModel object and an object of a different type;
 * Hashcode equality between two identical sensorModel objects.
 */
class SensorModelTest {

    /**
     * Tests if a valid instantiation of sensorModel results in a non-null object with expected attributes.
     */
    @Test
    void validInstantiation_ShouldReturnANonNullObjectWithValidAttributes() {
        // Arrange

        SensorModelID expectedModelID = mock(SensorModelID.class);
        SensorTypeID expectedTypeID = mock(SensorTypeID.class);
        // Act
        SensorModel mySensorModel = new SensorModel(expectedModelID, expectedTypeID);
        // Assert
        assertNotNull(mySensorModel);
        assertEquals(expectedModelID, mySensorModel.identity());
    }

    /**
     * Tests if passing a null SensorModelID to the constructor throws an IllegalArgumentException.
     */
    @Test
    void invalidModelID_ShouldReturnTrueDueToException() {
        SensorTypeID typeID = mock(SensorTypeID.class);
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorModel(null, typeID);
        });
        String expectedMessage = "Parameter 'sensorModelID' cannot be null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests if passing a null SensorTypeID to the constructor throws an IllegalArgumentException.
     */
    @Test
    void invalidTypeID_ShouldReturnTrueDueToException() {
        SensorModelID modelID = mock(SensorModelID.class);
        Exception exception = assertThrows(Exception.class, () -> {
            new SensorModel(modelID, null);
        });
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
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID = mock(SensorTypeID.class);
        SensorModel mySensorModel = new SensorModel(modelID, typeID);
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
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID = mock(SensorTypeID.class);
        SensorModel mySensorModel = new SensorModel(modelID, typeID);
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
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID = mock(SensorTypeID.class);
        SensorModel mySensorModel1 = new SensorModel(modelID, typeID);
        SensorModel mySensorModel2 = new SensorModel(modelID, typeID);
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
        SensorModelID modelID1 = mock(SensorModelID.class);
        SensorModelID modelID2 = mock(SensorModelID.class);
        SensorTypeID typeID = mock(SensorTypeID.class);
        SensorModel mySensorModel1 = new SensorModel(modelID1, typeID);
        SensorModel mySensorModel2 = new SensorModel(modelID2, typeID);
        // Act
        boolean isEquals = mySensorModel1.sameAs(mySensorModel2);
        assertFalse(isEquals);
    }

    /**
     * Tests if an sensorModel is considered equal to another sensorModel with the same name and a different ID.
     */
    @Test
    void sensorTypeNotEqualToOtherWithDifferentTypeID_ShouldReturnFalse() {
        // Arrange
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID1 = mock(SensorTypeID.class);
        SensorTypeID typeID2 = mock(SensorTypeID.class);
        SensorModel mySensorModel1 = new SensorModel(modelID, typeID1);
        SensorModel mySensorModel2 = new SensorModel(modelID, typeID2);
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
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID = mock(SensorTypeID.class);
        SensorModel sensorModel = new SensorModel(modelID, typeID);
        // Act and Assert
        assertEquals(modelID, sensorModel.identity());
    }

    /**
     * Tests if an sensorModel is considered equal to an object of a different type.
     */
    @Test
    void sensorModelNotEqualToOtherWithDifferentType_ShouldReturnFalse() {
        // Arrange
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID1 = mock(SensorTypeID.class);
        SensorModel sensorModel1 = new SensorModel(modelID, typeID1);

        Sensor sensorDouble = mock(Sensor.class);
        // Act
        boolean isEquals = sensorModel1.equals(sensorDouble);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if an sensorModel is considered the same as an object of a different type.
     */
    @Test
    void sensorModelNotTheSameAsOtherWithDifferentType_ShouldReturnFalse() {
        // Arrange
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID1 = mock(SensorTypeID.class);
        SensorModel sensorModel1 = new SensorModel(modelID, typeID1);

        Sensor sensorDouble = mock(Sensor.class);
        // Act
        boolean isEquals = sensorModel1.sameAs(sensorDouble);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if hashcode is the same for two equal sensorModels.
     */
    @Test
    void sameHashCodeInEqualSensorModels(){
        // Arrange
        SensorModelID modelID = mock(SensorModelID.class);
        SensorTypeID typeID1 = mock(SensorTypeID.class);
        SensorModel sensorModel1 = new SensorModel(modelID, typeID1);
        SensorModel sensorModel2 = new SensorModel(modelID, typeID1);

        // Act
        int hash1 = sensorModel1.hashCode();
        int hash2 = sensorModel2.hashCode();

        // Assert
        assertEquals(hash1, hash2);
    }

}

