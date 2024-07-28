package smartHomeDDD.domain.domain.sensorType;

import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The ImplFactorySensorTypeTest class provides unit tests for the ImplFactorySensorType class.
 * It encompasses the following scenario:
 * Successful instantiation of a sensorType object.
 */
class ImplFactorySensorTypeTest {

    /**
     * Tests the successful instantiation of a sensorType object.
     * The test verifies that the createSensorType method of the ImplFactorySensorType class returns a sensorType
     * object with the correct unit, description, and sensor type ID.
     */
    @Test
    void validSensorType_ReturnsInstantiatedSensorType() {
        // Arrange
        Unit unitDouble = mock(Unit.class);
        Description descriptionDouble = mock(Description.class);
        SensorTypeID sensorTypeIDDouble = mock(SensorTypeID.class);
        ImplFactorySensorType factory = new ImplFactorySensorType();

        // Act
        try (MockedConstruction<SensorType> sensorTypeDouble = mockConstruction(SensorType.class, (mock, context) -> {
            Unit unit = (Unit) context.arguments().get(0);
            Description description = (Description) context.arguments().get(1);
            SensorTypeID sensorTypeID = (SensorTypeID) context.arguments().get(2);
            when(mock.getUnit()).thenReturn(unit);
            when(mock.getDescription()).thenReturn(description);
            when(mock.identity()).thenReturn(sensorTypeID);
        })) {
            SensorType sensorType = factory.createSensorType(sensorTypeIDDouble, descriptionDouble, unitDouble);

            // Assert
            List<SensorType> sensorTypes = sensorTypeDouble.constructed();
            SensorType mySensorType = sensorTypeDouble.constructed().get(0);
            assertEquals(1, sensorTypes.size());
            assertEquals(sensorType, mySensorType);
            assertEquals(unitDouble, mySensorType.getUnit());
            assertEquals(descriptionDouble, mySensorType.getDescription());
            assertEquals(sensorTypeIDDouble, mySensorType.identity());
        }
    }
}