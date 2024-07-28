package smartHomeDDD.domain.domain.actuatorType;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.ImplFactoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ActuatorTypeAggregateTest class contains test cases for the ActuatorType class.
 * Test cases:
 * - Verifies successful creation of an ActuatorType object.
 * - Verifies that the sameAs method returns true when comparing two equal ActuatorType objects.
 * - Verifies that the sameAs method returns false when comparing two different ActuatorType objects.
 */
class ActuatorTypeAggregateTest {

    /**
     * Test to verify that an ActuatorType object can be created with the correct unit.
     */
    @Test
    void whenCreatingActuatorType_thenUnitShouldBeCorrect() {
        // Arrange
        ImplFactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("ActuatorOnOff");
        ActuatorTypeID actuatorTypeIDDouble = new ActuatorTypeID("actuatorTypeID1");

        // Act
        ActuatorType actuatorType = factoryActuatorType.createActuatorType(unit, description, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType.getUnit(), unit);
    }

    /**
     * Test to verify that an ActuatorType object can be created with the correct description.
     */
    @Test
    void whenCreatingActuatorType_thenDescriptionShouldBeCorrect() {
        // Arrange
        ImplFactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("ActuatorOnOff");
        ActuatorTypeID actuatorTypeIDDouble = new ActuatorTypeID("actuatorTypeID1");

        // Act
        ActuatorType actuatorType = factoryActuatorType.createActuatorType(unit, description, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType.getDescription(), description);
    }

    /**
     * Test to verify that an ActuatorType object can be created with the correct identity.
     */
    @Test
    void whenCreatingActuatorType_thenIdentityShouldBeCorrect() {
        // Arrange
        ImplFactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("ActuatorOnOff");
        ActuatorTypeID actuatorTypeIDDouble = new ActuatorTypeID("actuatorTypeID1");

        // Act
        ActuatorType actuatorType = factoryActuatorType.createActuatorType(unit, description, actuatorTypeIDDouble);

        // Assert
        assertEquals(actuatorType.identity(), actuatorTypeIDDouble);
    }


    /**
     * Test to verify that the sameAs method returns true when comparing two equal ActuatorType objects.
     */
    @Test
    void whenComparingTwoEqualActuatorTypes_thenShouldReturnTrue() {
        // Arrange
        ImplFactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("ActuatorOnOff");
        ActuatorTypeID actuatorTypeIDDouble = new ActuatorTypeID("actuatorTypeID1");

        ActuatorType actuatorType = factoryActuatorType.createActuatorType(unit, description, actuatorTypeIDDouble);
        ActuatorType actuatorType2 = factoryActuatorType.createActuatorType(unit, description, actuatorTypeIDDouble);

        // Act
        boolean isEquals = actuatorType.sameAs(actuatorType2);

        // Assert
        assertTrue(isEquals);
    }


    /**
     * Test to verify that the sameAs method returns false when comparing two different ActuatorType objects.
     */
    @Test
    void whenComparingTwoDifferentActuatorTypes_thenShouldReturnFalse()  {
        // Arrange
        ImplFactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        Unit unit = new Unit("Celsius");
        Description description = new Description("ActuatorOnOff");
        ActuatorTypeID actuatorTypeID1 = new ActuatorTypeID("actuatorTypeID1");
        ActuatorTypeID actuatorTypeID2 = new ActuatorTypeID("actuatorTypeID2");

        ActuatorType actuatorType = factoryActuatorType.createActuatorType(unit, description, actuatorTypeID1);
        ActuatorType actuatorType2 = factoryActuatorType.createActuatorType(unit, description, actuatorTypeID2);

        // Act
        boolean isEquals = actuatorType.sameAs(actuatorType2);

        // Assert
        assertFalse(isEquals);
    }

}
