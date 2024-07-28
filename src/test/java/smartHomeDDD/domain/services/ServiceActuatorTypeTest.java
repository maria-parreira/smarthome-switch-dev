package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.actuatorType.ImplFactoryActuatorType;
import smartHomeDDD.domain.repository.IRepositoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.persistence.mem.RepositoryActuatorTypeMem;
import smartHomeDDD.services.ServiceActuatorType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Test class for ServiceActuatorType.
 * Test cases:
 * -nullRepository_shouldThrowException
 * -nullFactory_shouldThrowException
 * -getActuatorTypes_ShouldGiveListWithActuatorTypes
 * -getActuatorTypeByID_shouldReturnActuatorType
 * -getActuatorTypeByID_shouldThrowException
 * -createActuatorType_shouldReturnActuatorType
 */

class ServiceActuatorTypeTest {
    /**
     * Test case to verify that an IllegalArgumentException is thrown when the repository is null.
     */
    @Test
    void nullRepository_shouldThrowException() {
        //Arrange
        FactoryActuatorType factory = mock(ImplFactoryActuatorType.class);

        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceActuatorType(null, factory)).getMessage();

        //Assert
        String expected = "Repository cannot be null";
        assertTrue(actual.contains(expected));
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the factory is null.
     */
    @Test
    void nullFactory_shouldThrowException() {
        //Arrange
        IRepositoryActuatorType repository = mock(RepositoryActuatorTypeMem.class);

        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceActuatorType(repository, null)).getMessage();

        //Assert
        String expected = "Factory cannot be null";
        assertTrue(actual.contains(expected));
    }


    /**
     * Test case to verify that the list of actuator types is returned.
     */
    @Test
    void getActuatorTypes_ShouldGiveListWithActuatorTypes() {
        // Arrange
        IRepositoryActuatorType repository = mock(RepositoryActuatorTypeMem.class);
        FactoryActuatorType factory = new ImplFactoryActuatorType();
        ServiceActuatorType service = new ServiceActuatorType(repository, factory);

        ActuatorType actuatorType1 = mock(ActuatorType.class);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        ActuatorType actuatorType3 = mock(ActuatorType.class);
        List<ActuatorType> actuatorTypes = List.of(actuatorType1, actuatorType2, actuatorType3);
        when(repository.findAll()).
                thenReturn(actuatorTypes);

        // Act
        Iterable<ActuatorType> foundActuatorTypes = service.getActuatorTypes();

        // Assert
        assertTrue(foundActuatorTypes.iterator().hasNext());
    }


    /**
     * Test case to get an actuator type by its ID.
     */
    @Test
    void getActuatorTypeByID_shouldReturnActuatorType(){
        //Arrange
        IRepositoryActuatorType repository = mock(RepositoryActuatorTypeMem.class);
        FactoryActuatorType factory = new ImplFactoryActuatorType();
        ServiceActuatorType service = new ServiceActuatorType(repository, factory);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        ActuatorType actuatorType = mock(ActuatorType.class);
        when(repository.ofIdentity(any())).thenReturn(Optional.of(actuatorType));
        //Act
        ActuatorType foundActuatorType = service.getActuatorTypeById(actuatorTypeID);

        //Assert
        assertEquals(actuatorType, foundActuatorType);
    }

    /**
     * Test case to verify that an EntityNotFoundException is thrown when the actuator type is not found.
     */
    @Test
    void getActuatorTypeByID_shouldThrowException(){
        //Arrange
        IRepositoryActuatorType repository = mock(RepositoryActuatorTypeMem.class);
        FactoryActuatorType factory = new ImplFactoryActuatorType();
        ServiceActuatorType service = new ServiceActuatorType(repository, factory);
        ActuatorTypeID actuatorTypeID = mock(ActuatorTypeID.class);
        when(repository.ofIdentity(any())).thenReturn(Optional.empty());
        //Act
        String actual = assertThrows(EntityNotFoundException.class, () -> service.getActuatorTypeById(actuatorTypeID)).getMessage();

        //Assert
        String expected = "Actuator Type not found.";
        assertTrue(actual.contains(expected));
    }

    /**
     * Test case to verify that an actuator type is created and saved in the repository.
     */
    @Test
    void createActuatorType_shouldReturnActuatorType(){
        //Arrange
        IRepositoryActuatorType repository = mock(RepositoryActuatorTypeMem.class);
        FactoryActuatorType factory = new ImplFactoryActuatorType();
        ServiceActuatorType service = new ServiceActuatorType(repository, factory);

        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T1");
        Description description = new Description("Status");
        Unit unit = new Unit("Celsius");
        ActuatorType actuatorType = mock(ActuatorType.class);
        when(repository.save(any())).thenReturn(actuatorType);
        //Act
        ActuatorType createdActuatorType = service.createActuatorType(actuatorTypeID, description, unit);

        //Assert
        assertEquals(actuatorType, createdActuatorType);
    }





}
