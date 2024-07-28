package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.AddNewActuatorToDeviceController;
import smartHomeDDD.domain.actuator.FactoryActuator;
import smartHomeDDD.domain.actuator.ImplFactoryActuator;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.ActuatorDTO;
import smartHomeDDD.persistence.mem.RepositoryActuatorMem;

import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceActuator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the AddNewActuatorToDeviceController class. It lists the following scenarios:
 * - Instantiation of the controller with a valid Actuator parameters
 * - Instantiation of the controller with a duplicated Actuator parameters
 * All repository implementations are tested: Memory, JPA and Spring Data.
 */
class AddNewActuatorToDeviceControllerTest {

    public static Device getDevice() {
        DeviceId deviceId = new DeviceId("deviceID");
        DeviceName deviceName = new DeviceName("deviceName");
        DeviceModel deviceModel = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomId = new RoomID("roomID");
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        return factoryDevice.createDevice(deviceId, deviceName, deviceModel, status, roomId);
    }

    /**
     * Test that the controller can add a new actuator to a device, using a JPA Persistence Repository
     */
    @Test
    void validActuatorIDAndValidActuatorValue_ShouldReturnValidActuator() {
        // Arrange
        // Creation of Factory Actuator, Factory Actuator and Service Actuator
        IRepositoryActuator repositoryActuatorMem = new RepositoryActuatorMem();
        FactoryActuator myFactoryActuator = new ImplFactoryActuator();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceActuator serviceActuator = new ServiceActuator(repositoryActuatorMem, myFactoryActuator, repositoryDevice, mockId);

        // Creating and saving a device
        Device device = getDevice();
        repositoryDevice.save(device);

        // Creation of Actuator ID, Device ID and Actuator Model ID
        String deviceID = "deviceID";
        String actuatorModelID = "OPNCL0100";
        String actuatorID = "validActuatorID";
        ActuatorID actuatorID1 = new ActuatorID(actuatorID);

        ActuatorDTO myActuatorDTO = new ActuatorDTO(actuatorID, deviceID, actuatorModelID);

        AddNewActuatorToDeviceController myController = new AddNewActuatorToDeviceController(serviceActuator);

        when(mockId.generateID()).thenReturn(actuatorID);

        // Act
        ActuatorDTO result = myController.addNewActuatorToDevice(myActuatorDTO);

        //Assert
        assertNotNull(result);
        assertEquals(actuatorID,result.getActuatorID());

    }

    /**
     * Test that the controller can add a duplicated actuator to a device, using a Memory Repository
     */
    @Test
    void duplicatedActuatorIDAndValidActuatorValue_ShouldReturnException() {
        // Arrange
        // Creation of Repository Actuator, Factory Actuator and Service Actuator
        IRepositoryActuator repositoryActuatorMem = new RepositoryActuatorMem();
        FactoryActuator myFactoryActuator = new ImplFactoryActuator();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        GenerateRandomId mockId = mock(GenerateRandomId.class);

        ServiceActuator serviceActuator = new ServiceActuator(repositoryActuatorMem, myFactoryActuator, repositoryDevice, mockId);

        // Creating and saving a device
        Device device = getDevice();
        repositoryDevice.save(device);

        // Creation of Actuator ID, Device ID and Actuator Model ID
        String deviceID = "deviceID";
        String actuatorModelID = "OPNCL0100";
        String actuatorID = "actuatorID";
        when(mockId.generateID()).thenReturn(actuatorID);

        ActuatorDTO myActuatorDTO = new ActuatorDTO(actuatorID, deviceID, actuatorModelID);

        AddNewActuatorToDeviceController myController = new AddNewActuatorToDeviceController(serviceActuator);

        // Act
        // Create actuator with actuatorID.
        myController.addNewActuatorToDevice(myActuatorDTO);
        // Attempt to create another actuator with same actuatorID.
        Exception exception = assertThrows(Exception.class, () ->
                myController.addNewActuatorToDevice(myActuatorDTO));

        //Assert
        assertEquals("actuator already exists", exception.getMessage());
    }

    @Test
    void constructor_NullServiceActuator_ShouldThrowIllegalArgumentException() {
        // Arrange
        ServiceActuator serviceActuator = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new AddNewActuatorToDeviceController(serviceActuator));
    }
}
