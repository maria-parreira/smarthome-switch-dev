package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.actuatorModel.ImplFactoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.persistence.mem.RepositoryActuatorModelMem;
import smartHomeDDD.services.ServiceActuatorModel;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Test class for ServiceActuatorModel.
 * Test cases:
 * -nullRepository_shouldThrowException
 * -nullFactory_shouldThrowException
 * -getModelsByActuatorType_shouldReturnListOfActuatorModels
 * -getModelsByActuatorType_shouldReturnEmptyList
 * -getActuatorModelByID_shouldReturnActuatorModel
 * -getActuatorModelByID_shouldThrow
 * -createActuatorModel_shouldReturnActuatorModel
 */
public class ServiceActuatorModelTest {

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the actuator model repository is null.
     */
    @Test
    void nullRepository_shouldThrowException() {
        //Arrange
        FactoryActuatorModel factory = mock(ImplFactoryActuatorModel.class);

        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceActuatorModel(null, factory)).getMessage();

        //Assert
        String expected = "Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the actuator model factory is null.
     */
    @Test
    void nullFactory_shouldThrowException() {
        //Arrange
        IRepositoryActuatorModel repository = mock(RepositoryActuatorModelMem.class);

        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceActuatorModel(repository, null)).getMessage();

        //Assert
        String expected = "Factory cannot be null";
        assertEquals(expected, actual);
    }


    /**
     * Test case to verify that the list of actuator models is returned.
     */
    @Test
    void getModelsByActuatorType_shouldReturnListOfActuatorModels(){
        //Arrange
        IRepositoryActuatorModel repository = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repository, factory);

        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        ActuatorModel actuatorModel1 = mock(ActuatorModel.class);
        ActuatorModel actuatorModel2 = mock(ActuatorModel.class);
        ActuatorModel actuatorModel3 = mock(ActuatorModel.class);
        when(repository.getModelsByActuatorType(any())).
                thenReturn(List.of(actuatorModel1, actuatorModel2, actuatorModel3));

        //Act
        List<ActuatorModel> actuatorModels = service.getModelsByActuatorType(actuatorTypeID);

        //Assert
        assertFalse(actuatorModels.isEmpty());
    }

    /**
     * Test case to verify that the list of actuator models is empty.
     */
    @Test
    void getModelsByActuatorType_shouldReturnEmptyList(){
        //Arrange
        IRepositoryActuatorModel repository = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repository, factory);

        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        when(repository.getModelsByActuatorType(any())).
                thenReturn(List.of());

        //Act
        List<ActuatorModel> actuatorModels = service.getModelsByActuatorType(actuatorTypeID);

        //Assert
        assertTrue(actuatorModels.isEmpty());
    }


    /**
     * Test case to get an actuator model by its ID.
     */
    @Test
    void getActuatorModelByID_shouldReturnActuatorModel(){
        //Arrange
        IRepositoryActuatorModel repository = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repository, factory);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(repository.ofIdentity(any())).thenReturn(Optional.of(actuatorModel));
        //Act
        ActuatorModel foundActuatorModel = service.getActuatorModelByID(actuatorModelID);

        //Assert
        assertEquals(actuatorModel, foundActuatorModel);
    }

    /**
     * Test case to verify that an EntityNotFoundException is thrown when the actuator model is not found.
     */
    @Test
    void getActuatorModelByID_shouldThrowException(){
        //Arrange
        IRepositoryActuatorModel repository = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repository, factory);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        when(repository.ofIdentity(any())).thenReturn(Optional.empty());
        //Act
        String actual = assertThrows(EntityNotFoundException.class, () -> service.getActuatorModelByID(actuatorModelID)).getMessage();

        //Assert
        String expected = "Actuator Model not found.";
        assertEquals(expected, actual);
    }

    /**
     * Test case to verify that an actuator model is created and saved in the repository.
     */
    @Test
    void createActuatorModel_shouldReturnActuatorModel(){
        //Arrange
        IRepositoryActuatorModel repository = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repository, factory);
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T1");
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(repository.save(any())).thenReturn(actuatorModel);

        //Act
        ActuatorModel createdActuatorModel = service.createActuatorModel(actuatorModelID, actuatorTypeID);

        //Assert
        assertEquals(actuatorModel, createdActuatorModel);
    }





}
