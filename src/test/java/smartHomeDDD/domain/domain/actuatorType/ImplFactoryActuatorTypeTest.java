package smartHomeDDD.domain.domain.actuatorType;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.ImplFactoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * This is a test class for the ImplFactoryActuatorType class. It tests the following scenarios:
 * - Successful instantiation of an ActuatorType object.
 */
class ImplFactoryActuatorTypeTest {

    /**
     * Test to verify that an ActuatorType object can be created with the correct unit.
     */
    @Test
    void whenCreatingActuatorType_thenUnitShouldBeCorrect() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();

        // Act
        ActuatorType actuatorType = factory.createActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(unitDouble, actuatorType.getUnit());
    }

    /**
     * Test to verify that an ActuatorType object can be created with the correct description.
     */
    @Test
    void whenCreatingActuatorType_thenDescriptionShouldBeCorrect() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();

        // Act
        ActuatorType actuatorType = factory.createActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(descriptionDouble, actuatorType.getDescription());
    }

    /**
     * Test to verify that an ActuatorType object can be created with the correct identity.
     */
    @Test
    void whenCreatingActuatorType_thenIdentityShouldBeCorrect() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        ActuatorTypeID actuatorTypeIDDouble = mock(ActuatorTypeID.class);
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();

        // Act
        ActuatorType actuatorType = factory.createActuatorType(unitDouble, descriptionDouble, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorTypeIDDouble, actuatorType.identity());
    }


}
