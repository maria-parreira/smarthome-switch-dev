package smartHomeDDD.domain.domain.actuatorModel;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This Test class encompasses the tests to the actuatorModel Class. It lists the following scenarios:
 * Valid instantiation;
 * Equality between an actuatorModel object and itself;
 * Non-Equality between an actuatorModel object and null one;
 * Equality between two identical actuatorModel objects;
 * Non-Equality between two actuatorModel objects with different ActuatorModelIDs;
 */
class ActuatorModelTest {
    /**
     * Tests if a valid instantiation of actuatorModel results in a non-null object with expected attributes.
     */
    @Test
    void validInstantiation_shouldReturnANonNullObjectWithValidAttributes() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        // Act
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        // Assert
        assertNotNull(myActuatorModel);
        assertEquals(modelIDDouble, myActuatorModel.identity());
        assertEquals(typeIDDouble, myActuatorModel.getActuatorTypeID());
    }

    /**
     * Tests if an actuatorModel equals itself.
     */
    @Test
    void actuatorModelEqualToItself_shouldReturnEqualObject() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        // Act
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);

        // Act & Assert
        assertEquals(myActuatorModel, myActuatorModel);
    }

    /**
     * Tests if an actuatorModel is not equal to null.
     */
    @Test
    void actuatorModelNotEqualToANullOne_shouldReturnFalse() {
        // Arrange

        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        // Act
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        // Act
        boolean isEquals = false;
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if an actuatorModel is considered equal to another equal actuatorModel.
     */
    @Test
    void actuatorModelEqualToAnotherEqualActuatorModel_shouldReturnTrue() {
        // Arrange
        // First actuator
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        // Act
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        ActuatorModel myActuatorModel2 = new ActuatorModel(modelIDDouble, typeIDDouble);
        boolean isEquals = myActuatorModel.sameAs(myActuatorModel2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if an actuatorModel is considered equal to another actuatorModel with a different ID.
     */
    @Test
    void actuatorModelNotEqualToOtherWithDifferentModelID_shouldReturnFalse() {
        // Arrange
        // First actuator
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        // Second actuator
        ActuatorModelID ModelID2 = new ActuatorModelID("OPNCL0100");
        // Act
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        ActuatorModel myActuatorModel2 = new ActuatorModel(ModelID2, typeIDDouble);
        boolean isEquals = myActuatorModel.sameAs(myActuatorModel2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if the sameAs method returns false when the object being compared is not an instance of actuatorModel.
     */
    @Test
    void sameAs_withNonActuatorModelObject_shouldReturnFalse() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        String nonActuatorModelObject = "I am not an actuatorModel";

        // Act
        boolean isSame = myActuatorModel.sameAs(nonActuatorModelObject);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Tests if the equals method returns true when the object being compared is the same instance.
     */
    @Test
    void equals_withSameInstance_shouldReturnTrue() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);

        // Act
        boolean isEquals = myActuatorModel.equals(myActuatorModel);

        // Assert
        assertTrue(true);
    }

    /**
     * Tests if the equals method returns true when the object being compared is an instance of actuatorModel.
     */
    @Test
    void equals_withSameActuatorModelID_shouldReturnTrue() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        ActuatorModel anotherActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);

        // Act
        boolean isEquals = myActuatorModel.equals(anotherActuatorModel);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if the hashCode method returns the same hash code for two actuatorModel objects with the same ActuatorModelID and ActuatorTypeID.
     */
    @Test
    void hashCode_withSameActuatorModelIDAndTypeID_shouldReturnSameHashCode() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        ActuatorModel myActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);
        ActuatorModel anotherActuatorModel = new ActuatorModel(modelIDDouble, typeIDDouble);

        // Act
        int myHashCode = myActuatorModel.hashCode();
        int anotherHashCode = anotherActuatorModel.hashCode();

        // Assert
        assertEquals(myHashCode, anotherHashCode);
    }

    /**
     * Tests if the equals method returns false when two actuatorModel objects with different ActuatorModelID are compared.
     */
    @Test
    void equals_withDifferentActuatorModelID_shouldReturnFalse() {
        // Arrange
        ActuatorModelID modelID1 = mock(ActuatorModelID.class);
        ActuatorModelID modelID2 = mock(ActuatorModelID.class);
        ActuatorTypeID typeID = new ActuatorTypeID("Type1");

        ActuatorModel actuatorModel1 = new ActuatorModel(modelID1, typeID);
        ActuatorModel actuatorModel2 = new ActuatorModel(modelID2, typeID);

        // Act
        boolean isEqual = actuatorModel1.equals(actuatorModel2);

        // Assert
        assertFalse(isEqual);
    }

    /**
     * Tests if the hashCode method returns different hash codes for two actuatorModel objects with different ActuatorModelID.
     */
    @Test
    void hashCode_withDifferentActuatorModelID_shouldReturnDifferentHashCodes() {
        // Arrange
        ActuatorModelID modelID1 = mock(ActuatorModelID.class);
        ActuatorModelID modelID2 = mock(ActuatorModelID.class);
        ActuatorTypeID typeID = new ActuatorTypeID("Type1");

        ActuatorModel actuatorModel1 = new ActuatorModel(modelID1, typeID);
        ActuatorModel actuatorModel2 = new ActuatorModel(modelID2, typeID);

        // Act
        int hashCode1 = actuatorModel1.hashCode();
        int hashCode2 = actuatorModel2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

}

