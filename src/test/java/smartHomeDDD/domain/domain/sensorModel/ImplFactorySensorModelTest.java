package smartHomeDDD.domain.domain.sensorModel;


import smartHomeDDD.domain.sensorModel.ImplFactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockConstruction;

/**
 * This test class encompasses the tests for the sensorModel Factory Class.
 * It encompasses the following scenario:
 * Successful instantiation of an sensorModel.
 */
class ImplFactorySensorModelTest {

    /**
     * Tests the successful instantiation of a sensorModel object.
     * The test verifies that the createSensorModel method of the ImplFactorySensorModel class returns a sensorModel
     * object with the correct sensor model and type ID values.
     */
    @Test
    void validSensorModel_ReturnsInstantiatedSensorModel() {
        // Arrange
        SensorModelID modelIDDouble = mock(SensorModelID.class);
        SensorTypeID typeIDDouble = mock(SensorTypeID.class);
        ImplFactorySensorModel implFactorySensorModel = new ImplFactorySensorModel();
        // Act
        try (MockedConstruction<SensorModel> sensorModelDouble = mockConstruction(SensorModel.class, (mock, context) -> {
            SensorModelID modelID = (SensorModelID) context.arguments().get(0);
            SensorTypeID sensorTypeID = (SensorTypeID) context.arguments().get(1);
            when(mock.identity()).thenReturn(modelID);
            when(mock.getSensorTypeID()).thenReturn(sensorTypeID);
        })) {
            SensorModel sensorModel = implFactorySensorModel.createSensorModel( modelIDDouble, typeIDDouble);
            // Assert
            List<SensorModel> sensorModels = sensorModelDouble.constructed();
            SensorModel mySensorModel = sensorModelDouble.constructed().get(0);
            assertEquals(1, sensorModels.size());
            assertEquals(sensorModel, mySensorModel);
            assertEquals(modelIDDouble, mySensorModel.identity());
            assertEquals(typeIDDouble, mySensorModel.getSensorTypeID());
        }
    }
}