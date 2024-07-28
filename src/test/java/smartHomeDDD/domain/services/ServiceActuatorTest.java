package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.FactoryActuator;
import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceActuator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceActuatorTest {

    @Test
    void validArguments_ShouldCreateServiceActuator() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        // Act
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        // Assert
        assertNotNull(serviceActuator);
    }

    @Test
    void nullActuatorRepo_ShouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryActuator repoActuator = null;
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        // Act & Assert
        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        }).getMessage();
        // Assert
        String expected = "Repository cannot be null";
        assertEquals(expected, result);
    }

    @Test
    void nullActuatorFactory_ShouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = null;
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        // Act & Assert
        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        }).getMessage();
        // Assert
        String expected = "Factory cannot be null";
        assertEquals(expected, result);
    }

    @Test
    void nullDeviceRepo_ShouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = null;
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        // Act & Assert
        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        }).getMessage();
        // Assert
        String expected = "Repository cannot be null";
        assertEquals(expected, result);
    }

    @Test
    void nullGenerateId_ShouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = null;
        // Act & Assert
        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        }).getMessage();
        // Assert
        String expected = "GenerateRandomId cannot be null";
        assertEquals(expected, result);
    }


    @Test
    void validArguments_ShouldReturnNewActuator() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        DeviceId deviceID = mock(DeviceId.class);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        ActuatorID actuatorID = new ActuatorID("ValidActuatorID");
        Actuator expected = mock(Actuator.class);
        // Act
        when(repoDevice.containsOfIdentity(deviceID)).thenReturn(true);
        when(generateRandomId.generateID()).thenReturn("ValidActuatorID");
        when(factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID)).thenReturn(expected);
        when(repoActuator.save(expected)).thenReturn(expected);
        Actuator result = serviceActuator.addNewActuator(deviceID, actuatorModelID);
        // Assert
        assertNotNull(result);
    }

    @Test
    void invalidDeviceID_ShouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        DeviceId deviceID = mock(DeviceId.class);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        // Act
        when(repoDevice.containsOfIdentity(deviceID)).thenReturn(false);
        // Act & Assert
        String result = assertThrows(EntityNotFoundException.class, () -> {
            serviceActuator.addNewActuator(deviceID, actuatorModelID);
        }).getMessage();
        // Assert
        String expected = "Device not found.";
        assertEquals(expected, result);
    }

    @Test
    void validDeviceID_ShouldReturnListOfActuator() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        DeviceId deviceID = mock(DeviceId.class);
        //Act
        when(repoDevice.containsOfIdentity(deviceID)).thenReturn(true);
        List<Actuator> result = serviceActuator.getActuatorsByDeviceID(deviceID);
        //Assert
        assertNotNull(result);

    }

    @Test
    void deviceIdNotFoundInRepo_ShouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        DeviceId deviceID = mock(DeviceId.class);
        // Act
        when(repoDevice.containsOfIdentity(deviceID)).thenReturn(false);
        // Act & Assert
        String result = assertThrows(EntityNotFoundException.class, () -> {
            serviceActuator.getActuatorsByDeviceID(deviceID);
        }).getMessage();
        // Assert
        String expected = "Device not found";
        assertEquals(expected, result);
    }

    @Test
    void validActuatorId_ShouldReturnActuator() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        ActuatorID actuatorID = mock(ActuatorID.class);
        Actuator expected = mock(Actuator.class);
        Optional<Actuator> actuatorOpt = Optional.of(expected);
        //Act
        when(repoActuator.ofIdentity(actuatorID)).thenReturn(actuatorOpt);
        Actuator result = serviceActuator.getActuator(actuatorID);
        //Assert
        assertNotNull(result);
    }

    @Test
    void invalidActuatorId_ShouldReturnEntityNotFoundException() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        ActuatorID actuatorID = mock(ActuatorID.class);
        //Act
        when(repoActuator.ofIdentity(actuatorID)).thenReturn(Optional.empty());
        //Act & Assert
        String result = assertThrows(EntityNotFoundException.class, () -> {
            serviceActuator.getActuator(actuatorID);
        }).getMessage();
        //Assert
        String expected = "Actuator not found.";
        assertEquals(expected, result);
    }

    @Test
    void validArguments_ShouldReturnActuator() {
        // Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        OPNCL0100Value value = mock(OPNCL0100Value.class);
        Actuator expected = mock(Actuator.class);
        //Act
        Actuator result = serviceActuator.updateRollerBlind(expected, value);
        //Assert
        assertEquals(expected, result);
    }

    @Test
    void differentDeviceIds_ShouldReturnIllegalArgumentException(){
        //Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        Actuator actuator = mock(Actuator.class);
        Sensor sensor = mock(Sensor.class);
        DeviceId actuatorDeviceId = mock(DeviceId.class);
        DeviceId sensorDeviceId = mock(DeviceId.class);
        when(actuator.getDeviceID()).thenReturn(actuatorDeviceId);
        when(sensor.getDeviceID()).thenReturn(sensorDeviceId);
        //Act
        String result = assertThrows(IllegalArgumentException.class, () -> {
            serviceActuator.areActuadorAndSensorInSameDevice(actuator, sensor);
        }).getMessage();
        //Assert
        String expected = "The sensor and/or actuator are not from the same device.";
        assertEquals(expected, result);
    }

    @Test
    void incorrectActuatorModelID_ShouldReturnIllegalArgumentException(){
        //Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        Actuator actuator = mock(Actuator.class);
        Sensor sensor = mock(Sensor.class);
        DeviceId deviceId = mock(DeviceId.class);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        when(actuator.getActuatorModelID()).thenReturn(actuatorModelID);
        when(sensor.getSensorModelID()).thenReturn(sensorModelID);
        when(sensorModelID.toString()).thenReturn("CAP200");
        when(actuatorModelID.toString()).thenReturn("IncorrectModelID");
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(sensor.getDeviceID()).thenReturn(deviceId);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(sensor.getDeviceID()).thenReturn(deviceId);
        //Act
        String result = assertThrows(IllegalArgumentException.class, () -> {
            serviceActuator.areActuadorAndSensorInSameDevice(actuator,sensor);
        }).getMessage();
        //Assert
        String expected = "The actuator and/or sensor doesn't have the correct model.";
        assertEquals(expected, result);
    }

    @Test
    void incorrectSensorModelID_ShouldReturnIllegalArgumentException(){
        //Arrange
        IRepositoryActuator repoActuator = mock(IRepositoryActuator.class);
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repoActuator, factoryActuator, repoDevice, generateRandomId);
        Actuator actuator = mock(Actuator.class);
        Sensor sensor = mock(Sensor.class);
        DeviceId deviceId = mock(DeviceId.class);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        when(actuator.getActuatorModelID()).thenReturn(actuatorModelID);
        when(sensor.getSensorModelID()).thenReturn(sensorModelID);
        when(sensorModelID.toString()).thenReturn("IncorrectModelID");
        when(actuatorModelID.toString()).thenReturn("OPNCL0100");
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(sensor.getDeviceID()).thenReturn(deviceId);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(sensor.getDeviceID()).thenReturn(deviceId);
        //Act
        String result = assertThrows(IllegalArgumentException.class, () -> {
            serviceActuator.areActuadorAndSensorInSameDevice(actuator,sensor);
        }).getMessage();
        //Assert
        String expected = "The actuator and/or sensor doesn't have the correct model.";
        assertEquals(expected, result);
    }

}
