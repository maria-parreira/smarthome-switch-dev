package smartHomeDDD.domain.domain.actuatorModel;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.ImplFactoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * This test class encompasses the tests for the actuatorModel Factory Class. It lists the following scenarios:
 * Successful instantiation of an actuatorModel.
 */
class ImplFactoryActuatorModelTest {
    /**
     * Tests the successful instantiation of an actuatorModel object.
     */
    @Test
    void validActuatorModel_ReturnsInstantiatedActuatorModel() {
        // Arrange
        ActuatorModelID modelIDDouble = mock(ActuatorModelID.class);
        ActuatorTypeID typeIDDouble = mock(ActuatorTypeID.class);
        ImplFactoryActuatorModel implFactoryActuatorModel = new ImplFactoryActuatorModel();
        // Act
        try (MockedConstruction<ActuatorModel> actuatorModelDouble = mockConstruction(ActuatorModel.class, (mock, context) -> {
            ActuatorModelID modelID = (ActuatorModelID) context.arguments().get(0);
            ActuatorTypeID actuatorTypeID = (ActuatorTypeID) context.arguments().get(1);
            when(mock.identity()).thenReturn(modelID);
            when(mock.getActuatorTypeID()).thenReturn(actuatorTypeID);
        })) {
            ActuatorModel actuatorModel = implFactoryActuatorModel.createActuatorModel(modelIDDouble, typeIDDouble);
        // Assert
            List<ActuatorModel> actuatorModels = actuatorModelDouble.constructed();
            ActuatorModel myActuatorModel = actuatorModelDouble.constructed().get(0);
            assertEquals(1, actuatorModels.size());
            assertEquals(actuatorModel, myActuatorModel);
            assertEquals(modelIDDouble, myActuatorModel.identity());
            assertEquals(typeIDDouble, myActuatorModel.getActuatorTypeID());
        }
    }
}