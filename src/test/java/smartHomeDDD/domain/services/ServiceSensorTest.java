package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceSensor;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for ServiceSensor.
 * Test cases:
 * -validArguments_ShouldConstructService
 * -nullFactory_shouldThrowException
 * -nullSensorRepository_shouldThrowException
 * -nullDeviceRepository_shouldThrowException
 * -nullGenerateRandomId_shouldThrowException
 * -validArguments_ShouldCreateSensor
 * -deviceIdNotFoundInRepo_ShouldThrowExceptionWhenCreatingSensor
 * -validArgument_ShouldReturnListOfSensors
 * -deviceIdNotFoundInRepo_ShouldThrowExceptionWhenGettingSensors
 * -validSensorID_ShouldReturnSensor
 * -sensorIDNotFoundInRepo_ShouldThrowExceptionWhenGettingSensor
 * -validTemperatureSensor_ShouldReturnTrue
 * -invalidSensorModelID_ShouldReturnFalse
 */
public class ServiceSensorTest {

    @Test
    void validArguments_ShouldConstructService() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        //Act
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        //Assert
        assertNotNull(serviceSensor);
    }

    @Test
    void nullFactory_shouldThrowException() {
        //Arrange
        FactorySensor factorySensor = null;
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId)).getMessage();
        //Assert
        String expected = "Factory cannot be null.";
        assert (actual.contains(expected));
    }

    @Test
    void nullSensorRepository_shouldThrowException() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = null;
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId)).getMessage();
        //Assert
        String expected = "Repository cannot be null.";
        assert (actual.contains(expected));
    }

    @Test
    void nullDeviceRepository_shouldThrowException() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = null;
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId)).getMessage();
        //Assert
        String expected = "Repository cannot be null.";
        assert (actual.contains(expected));
    }

    @Test
    void nullGenerateRandomId_shouldThrowException() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = null;
        //Act
        String actual = assertThrows(IllegalArgumentException.class, () -> new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId)).getMessage();
        //Assert
        String expected = "GenerateRandomId cannot be null.";
        assert (actual.contains(expected));
    }

    @Test
    void validArguments_ShouldCreateSensor() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID= new SensorID("ValidSensorID");
        Sensor expectedSensor = mock(Sensor.class);
        //Act
        when(repositoryDevice.containsOfIdentity(deviceId)).thenReturn(true);
        when(generateRandomId.generateID()).thenReturn("ValidSensorID");
        when(factorySensor.createSensor(deviceId, sensorModelID,sensorID)).thenReturn(expectedSensor);
        when(repositorySensor.save(expectedSensor)).thenReturn(expectedSensor);
        Sensor resultSensor = serviceSensor.createNewSensor(deviceId, sensorModelID);
        //Assert
        assertNotNull(resultSensor);
    }

    @Test
    void deviceIdNotFoundInRepo_ShouldThrowExceptionWhenCreatingSensor() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        //Act
        when(repositoryDevice.containsOfIdentity(deviceId)).thenReturn(false);
        //Assert
        assertThrows(EntityNotFoundException.class, () -> serviceSensor.createNewSensor(deviceId, sensorModelID));
    }

    @Test
    void validArgument_ShouldReturnListOfSensors() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        DeviceId deviceId = mock(DeviceId.class);
        //Act
        when(repositoryDevice.containsOfIdentity(deviceId)).thenReturn(true);
        List<Sensor> sensorList = serviceSensor.getSensorsByDeviceID(deviceId);
        //Assert
        assertNotNull(sensorList);
    }

    @Test
    void deviceIdNotFoundInRepo_ShouldThrowExceptionWhenGettingSensors() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        DeviceId deviceId = mock(DeviceId.class);
        //Act
        when(repositoryDevice.containsOfIdentity(deviceId)).thenReturn(false);
        //Assert
        assertThrows(EntityNotFoundException.class, () -> serviceSensor.getSensorsByDeviceID(deviceId));
    }

    @Test
    void validSensorID_ShouldReturnSensor() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        SensorID sensorID = mock(SensorID.class);
        Sensor expectedSensor = mock(Sensor.class);
        Optional<Sensor> sensorOpt = Optional.of(expectedSensor);
        //Act
        when(repositorySensor.ofIdentity(sensorID)).thenReturn(sensorOpt);
        Sensor retrievedSensor = serviceSensor.getSensor(sensorID);
        //Assert
        assertEquals(expectedSensor, retrievedSensor);
    }

    @Test
    void sensorIDNotFoundInRepo_ShouldThrowExceptionWhenGettingSensor() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        SensorID sensorID = mock(SensorID.class);
        //Act
        when(repositorySensor.ofIdentity(sensorID)).thenReturn(Optional.empty());
        String result = assertThrows(EntityNotFoundException.class, () -> serviceSensor.getSensor(sensorID)).getMessage();
        //Assert
        String expected = "Sensor not found.";
        assertEquals(expected, result);
    }

    @Test
    void validTemperatureSensor_ShouldReturnTrue() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        Sensor sensor = mock(Sensor.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        when(sensorModelID.toString()).thenReturn("GA100K");
        when(sensor.getSensorModelID()).thenReturn(sensorModelID);
        //Act & Assert
        assertTrue(serviceSensor.isSensorOfTemperature(sensor));
    }

    @Test
    void invalidSensorModelID_ShouldReturnFalse() {
        //Arrange
        FactorySensor factorySensor = mock(FactorySensor.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repositorySensor, repositoryDevice, generateRandomId);
        Sensor sensor = mock(Sensor.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        when(sensorModelID.toString()).thenReturn("NotTemperatureSensor");
        when(sensor.getSensorModelID()).thenReturn(sensorModelID);
        //Act & Assert
        assertFalse(serviceSensor.isSensorOfTemperature(sensor));
    }

}
