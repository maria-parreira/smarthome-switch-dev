package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.services.ServiceSensorType;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for ServiceSensorType.
 * It tests the results of the following test cases:
 * instantiating a ServiceSensorType object with a null repository should throw an exception
 * instantiating a ServiceSensorType object with a null factory should throw an exception
 * creating a sensor type
 * getting sensor types should return a list of sensor types
 * getting a sensor type by ID should return the sensor type
 * getting a non-existent sensor type should throw an exception
 */
class ServiceSensorTypeTest {

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the repository is null.
     */
    @Test
    void nullRepository_shouldThrowException() {
        //Arrange
        FactorySensorType factory = mock(FactorySensorType.class);
        String expected = "Repository cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ServiceSensorType(null, factory));
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
        IRepositorySensorType repository = mock(IRepositorySensorType.class);
        String expected = "Factory cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ServiceSensorType(repository, null));
        String actual = exception.getMessage();

        //Assert
        assertTrue(actual.contains(expected));
    }

    /**
     * Test case to verify that a SensorType is created.
     */
    @Test
    void createSensorType_shouldCreateSensorType() {
        //Arrange
        IRepositorySensorType repository = mock(IRepositorySensorType.class);
        FactorySensorType factory = new ImplFactorySensorType();
        ServiceSensorType service = new ServiceSensorType(repository, factory);
        SensorTypeID id = mock(SensorTypeID.class);
        Description description = mock(Description.class);
        Unit unit = mock(Unit.class);

        SensorType sensorType = factory.createSensorType(id, description, unit);
        when(repository.save(sensorType)).thenReturn(sensorType);

        //Act
        SensorType type = factory.createSensorType(id, description, unit);

        //Assert
        assertEquals(type, sensorType);
    }


    /**
     * Test case to verify that the list of sensor types is returned.
     */
    @Test
    void getSensorTypes_shouldReturnListOfSensorTypes() {
        //Arrange
        IRepositorySensorType repository = mock(IRepositorySensorType.class);
        FactorySensorType factory = mock(FactorySensorType.class);
        ServiceSensorType service = new ServiceSensorType(repository, factory);
        SensorType sensorType1 = mock(SensorType.class);
        SensorType sensorType2 = mock(SensorType.class);
        SensorType sensorType3 = mock(SensorType.class);
        List<SensorType> sensorTypes = List.of(sensorType1, sensorType2, sensorType3);
        when(repository.findAll()).thenReturn(sensorTypes);

        //Act
        Iterable<SensorType> actual = service.getSensorTypes();

        //Assert
        assertEquals(sensorTypes, actual);
    }

    /**
     * Test case to verify that a SensorType is retrieved based on its ID.
     */
    @Test
    void getSensorTypeByID_shouldReturnSensorType() {
        //Arrange
        IRepositorySensorType repository = mock(IRepositorySensorType.class);
        FactorySensorType factory = mock(FactorySensorType.class);
        ServiceSensorType service = new ServiceSensorType(repository, factory);
        SensorType sensorType = mock(SensorType.class);
        when(repository.ofIdentity(sensorType.identity())).thenReturn(Optional.of(sensorType));

        //Act
        SensorType actual = service.getSensorTypeById(sensorType.identity());

        //Assert
        assertEquals(sensorType, actual);
    }

    /**
     * Test case to verify that an EntityNotFoundException is thrown when the sensor type is not found.
     */
    @Test
    void getNonExistentSensorType_shouldThrowException() {
        //Arrange
        IRepositorySensorType repository = mock(IRepositorySensorType.class);
        FactorySensorType factory = mock(FactorySensorType.class);
        ServiceSensorType service = new ServiceSensorType(repository, factory);
        SensorType sensorType = mock(SensorType.class);
        when(repository.ofIdentity(sensorType.identity())).thenReturn(Optional.empty());
        String expected = "Sensor Type not found";

        //Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getSensorTypeById(sensorType.identity()));
        String actual = exception.getMessage();

        //Assert
        assertTrue(actual.contains(expected));
    }
}
