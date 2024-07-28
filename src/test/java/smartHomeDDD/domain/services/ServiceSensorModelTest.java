package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.ImplFactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.services.ServiceSensorModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the ServiceSensorModel class.
 * It contains test cases for the following scenarios:
 * - instantiating a ServiceSensorModel object with null repository
 * - instantiating a ServiceSensorModel object with null factory
 * - creating a sensor model
 * - getting a list of sensor models by sensor type
 * - getting a sensor model by ID
 * - getting a non-existent sensor model
 */
class ServiceSensorModelTest {

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the repository is null.
     */
    @Test
    void nullRepository_shouldThrowException() {
        //Arrange
        FactorySensorModel factory = mock(FactorySensorModel.class);
        String expected = "Repository cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ServiceSensorModel(null, factory));
        String actual = exception.getMessage();

        //Assert
        assertTrue(actual.contains(expected));
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the factory is null.
     */
    @Test
    void nullFactory_shouldThrowException() {
        //Arrange
        IRepositorySensorModel repository = mock(IRepositorySensorModel.class);
        String expected = "Factory cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ServiceSensorModel(repository, null));
        String actual = exception.getMessage();

        //Assert
        assertTrue(actual.contains(expected));
    }

    /**
     * Test to instantiate a Sensor Model object
     */
    @Test
    void createSensorModel_shouldCreateSensorModel() {
        //Arrange
        IRepositorySensorModel repository = mock(IRepositorySensorModel.class);
        FactorySensorModel factory = new ImplFactorySensorModel();
        ServiceSensorModel service = new ServiceSensorModel(repository, factory);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorTypeID sensorTypeID = mock(SensorTypeID.class);

        SensorModel sensorModel = factory.createSensorModel(sensorModelID, sensorTypeID);
        when(repository.save(sensorModel)).thenReturn(sensorModel);

        //Act
        SensorModel model = service.addSensorModel(sensorModelID, sensorTypeID);

        //Assert
        assertEquals(sensorModel, model);
    }

    /**
     * Test to get a list of sensor models by sensor type
     */
    @Test
    void getSensorModelBySensorType_shouldReturnListOfSensorModels() {
        //Arrange
        IRepositorySensorModel repository = mock(IRepositorySensorModel.class);
        FactorySensorModel factory = mock(FactorySensorModel.class);
        ServiceSensorModel service = new ServiceSensorModel(repository, factory);
        SensorModel sensorModel1 = mock(SensorModel.class);
        List <SensorModel> sensorModels = List.of(sensorModel1);
        SensorTypeID sensorTypeID = mock(SensorTypeID.class);
        when(repository.getModelsBySensorType(sensorTypeID)).thenReturn(sensorModels);

        //Act
        List<SensorModel> models = service.getModelsBySensorType(sensorTypeID);

        //Assert
        assertEquals(models, sensorModels);
    }

    /**
     * Test to retrieve a sensor model by its ID
     */
    @Test
     void getSensorModelByID_shouldReturnSensorModel() {
        //Arrange
        IRepositorySensorModel repository = mock(IRepositorySensorModel.class);
        FactorySensorModel factory = mock(FactorySensorModel.class);
        ServiceSensorModel service = new ServiceSensorModel(repository, factory);
        SensorModel sensorModel = mock(SensorModel.class);
        when(repository.ofIdentity(sensorModel.identity())).thenReturn(Optional.of(sensorModel));

        //Act
        SensorModel model = service.getSensorModelByID(sensorModel.identity());

        //Assert
        assertEquals(sensorModel, model);
    }

    /**
     * Test to verify that an EntityNotFoundException is thrown when a non-existent sensor model is retrieved
     */
    @Test
    void getNonExistentSensorModel_shouldThrowException() {
        //Arrange
        IRepositorySensorModel repository = mock(IRepositorySensorModel.class);
        FactorySensorModel factory = mock(FactorySensorModel.class);
        ServiceSensorModel service = new ServiceSensorModel(repository, factory);
        SensorModel sensorModel = mock(SensorModel.class);
        when(repository.ofIdentity(sensorModel.identity())).thenReturn(Optional.empty());
        String expected = "Sensor Model doesn't exist";

        //Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getSensorModelByID(sensorModel.identity()));
        String actual = exception.getMessage();

        //Assert
        assertTrue(actual.contains(expected));
    }

}
