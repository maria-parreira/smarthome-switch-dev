package smartHomeDDD.domain.domain.actuatorModel;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.actuatorModel.ImplFactoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This Test class encompasses the tests to the Aggregate actuatorModel. It lists the following scenarios:
 * Valid instantiation;
 * Equality between an actuatorModel object and itself;
 * Non-Equality between an actuatorModel object and null one;
 * Equality between two actuatorModel objects with different IDs;
 * Equality between an actuatorModel object's ID and its identity;
 */
class ActuatorModelAggregateTest {

    /**
     * Tests if a valid instantiation of actuatorModel results in a non-null object with expected attributes.
     */
    @Test
    void validInstantiation_ShouldReturnANonNullObjectWithValidAttributes() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorModelID expectedModelID = new ActuatorModelID("OPNCL0100");
        ActuatorTypeID expectedTypeID = new ActuatorTypeID("ActuatorTypeID");
        // Act
        ActuatorModel myActuatorModel = modelFactory.createActuatorModel(expectedModelID, expectedTypeID);
        // Assert
        assertNotNull(myActuatorModel);
        assertEquals(expectedModelID, myActuatorModel.identity());
    }


    /**
     * Tests if an actuatorModel equals itself.
     */
    @Test
    void actuatorModelEqualToItself_ShouldReturnEqualObject() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorModelID modelID = new ActuatorModelID("OPNCL0100");
        ActuatorTypeID typeIDDouble = new ActuatorTypeID("ActuatorTypeID");
        ActuatorModel myActuatorModel = modelFactory.createActuatorModel(modelID, typeIDDouble);
        // Act
        boolean isEquals = myActuatorModel.equals(myActuatorModel);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if an actuatorModel is not equal to null.
     */
    @Test
    void actuatorModelNotEqualToANullOne_ShouldReturnFalse() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorModelID modelIDDouble = new ActuatorModelID("OPNCL0100");
        ActuatorTypeID typeIDDouble = new ActuatorTypeID("ActuatorTypeID");
        ActuatorModel myActuatorModel = modelFactory.createActuatorModel(modelIDDouble, typeIDDouble);
        // Act
        boolean isEquals = myActuatorModel.equals(null);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if an actuatorModel is considered equal to another actuatorModel with the same name and ID.
     */
    @Test
    void actuatorModelEqualToAnotherEqualActuatorModel_ShouldReturnTrue() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorTypeID typeIDDouble = new ActuatorTypeID("ActuatorTypeID");
        ActuatorModel myActuatorModel1 = modelFactory.createActuatorModel(new ActuatorModelID("OPNCL0100"), typeIDDouble);
        ActuatorModel myActuatorModel2 = modelFactory.createActuatorModel(new ActuatorModelID("OPNCL0100"), typeIDDouble);
        // Act
        boolean isEquals = myActuatorModel1.sameAs(myActuatorModel2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if an actuatorModel is considered equal to another actuatorModel with the same name and a different ID.
     */
    @Test
    void actuatorModelNotEqualToOtherWithDifferentModelID_ShouldReturnFalse() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorTypeID typeIDDouble = new ActuatorTypeID("ActuatorTypeID");
        ActuatorModel myActuatorModel1 = modelFactory.createActuatorModel(new ActuatorModelID("OPNCL0100"), typeIDDouble);
        ActuatorModel myActuatorModel2 = modelFactory.createActuatorModel(new ActuatorModelID("ONF01A"), typeIDDouble);
        // Act
        boolean isEquals = myActuatorModel1.sameAs(myActuatorModel2);
        assertFalse(isEquals);
    }



    /**
     * Tests if an actuatorModel is considered equal to another actuatorModel with the same name and a different ID.
     */
    @Test
    void actuatorTpeNotEqualToOtherWithDifferentTypeID_ShouldReturnFalse() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorTypeID typeIDDouble1 = new ActuatorTypeID("ActuatorTypeID");
        ActuatorTypeID typeIDDouble2 = new ActuatorTypeID("ActuatorTypeID2");
        ActuatorModel myActuatorModel1 = modelFactory.createActuatorModel(new ActuatorModelID("OPNCL0100"), typeIDDouble1);
        ActuatorModel myActuatorModel2 = modelFactory.createActuatorModel(new ActuatorModelID("OPNCL0100"), typeIDDouble2);
        // Act
        boolean isEquals = myActuatorModel1.sameAs(myActuatorModel2);
        assertFalse(isEquals);
    }



    /**
     * Tests if the identity of an actuatorModel is the same as its ID.
     */
    @Test
    void identity_ShouldReturnModelID() {
        // Arrange
        FactoryActuatorModel modelFactory = new ImplFactoryActuatorModel();
        ActuatorTypeID typeIDDouble = new ActuatorTypeID("ActuatorTypeID");
        ActuatorModelID modelID = new ActuatorModelID("OPNCL0100");
        ActuatorModel actuatorModel = modelFactory.createActuatorModel(modelID, typeIDDouble);
        // Act and Assert
        assertEquals(modelID, actuatorModel.identity());
    }
}


