package smartHomeDDD.domain.controllers;

import jakarta.persistence.EntityNotFoundException;
import smartHomeDDD.controllers.DeactivateDeviceController;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceDevice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The DeactivateDeviceControllerTest class provides test cases for the DeactivateDeviceController class.
 * It contains test cases for the following scenarios:
 * - The service is null.
 * - The device is successfully deactivated.
 * - The device cannot be found or deactivated.
 */
class DeactivateDeviceControllerTest {

    private static Room getRoom() {
        HouseId houseId = new HouseId("houseId");
        RoomID roomId = new RoomID("roomId");
        FloorNumber floorNumber = new FloorNumber(1);
        Length length = new Length(10);
        Width width = new Width(10);
        Height height = new Height(10);
        Dimensions dimensions = new Dimensions(length, width, height);
        RoomName roomName = new RoomName("Living Room");
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        return factoryRoom.createRoom(houseId, roomId, floorNumber, dimensions, true, roomName);
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     * The test arranges a null ServiceDevice, acts by attempting to create a DeactivateDeviceController with the null service,
     * and asserts that an IllegalArgumentException is thrown with the expected message.
     */
    @Test
    void nullService_ShouldThrowIllegalArgumentException() throws IllegalArgumentException {
        // Arrange
        String expectedMessage = "ServiceDevice cannot be null.";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new DeactivateDeviceController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to verify that the deactivateDevice method returns the correct deactivated device.
     * The test arranges a DeactivateDeviceController and adds a device to the repository, acts by deactivating the device,
     * and asserts that the returned DeviceDTO represents a deactivated device.
     */
    @Test
    void deactivateDevice_ShouldReturnDeactivatedDevice() {
        // Arrange
        RepositoryDeviceMem repositoryDevice = new RepositoryDeviceMem();
        RepositoryRoomMem repositoryRoom = new RepositoryRoomMem();
        ImplFactoryDevice ImplFactoryDevice = new ImplFactoryDevice();
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, ImplFactoryDevice, generateRandomId);
        DeactivateDeviceController controller = new DeactivateDeviceController(serviceDevice);

        // Add a room to the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        // Add a device with DeviceId "device1" to the repository
        DeviceName deviceName = new DeviceName("Test Device");
        DeviceModel deviceModel = new DeviceModel("Test Model");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomId = new RoomID("roomId");
        when(generateRandomId.generateID()).thenReturn("device1");
        serviceDevice.addNewDevice(deviceName, deviceModel, activationStatus, roomId);

        // Act
        DeviceDTO deactivatedDevice = controller.deactivateDevice("device1");

        // Assert
        assertNotNull(deactivatedDevice);
        assertFalse(deactivatedDevice.getActivationStatus());
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when trying to deactivate a non-existent device.
     * The test arranges a DeactivateDeviceController and does not add any device to the repository,
     * acts by attempting to deactivate a device with id "device1",
     * and asserts that an IllegalArgumentException is thrown with the expected message "Device deactivation is not possible.".
     *
     * @throws IllegalArgumentException if the device does not exist or cannot be deactivated.
     */
    @Test
    void deactivateDevice_nonExistentDevice_ShouldThrowException() throws IllegalArgumentException {

        // Arrange
        RepositoryDeviceMem repositoryDevice = new RepositoryDeviceMem();
        RepositoryRoomMem repositoryRoom = new RepositoryRoomMem();
        ImplFactoryDevice ImplFactoryDevice = new ImplFactoryDevice();
        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, ImplFactoryDevice, generateRandomId);
        DeactivateDeviceController controller = new DeactivateDeviceController(serviceDevice);

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                controller.deactivateDevice("device1"));

        String expectedMessage = "Device not found";
        String actualMessage = exception.getMessage();

        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}