package smartHomeDDD.domain.domain.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * JUnit test class for the RepositoryDevice class. It encompasses the following scenarios:
 * - Saving a device to an initially empty repository and verifying that the repository contains the saved device.
 * - Verifying that an empty repository doesn't contain any devices.
 * - Verifying that, when saving a null device, the save() method throws an IllegalArgumentException.
 * - Ensuring that, when the repository is empty, the findAll() method returns an empty iterable.
 * - Verifying if, when the repository contains devices, the findAll() method returns a non-empty iterable.
 * - Verifying if the getDevicesInRoom() method returns a list of devices in a room.
 * - Verifying if the update method updates the device in the repository.
 * - Verifying that, when saving a device with the same identity as an existing device, the repository doesn't save the device.
 * - Verifying if the getActiveDevices() method returns a list of active devices.
 * - Verifying if the getActiveDevices() method returns an empty list of active devices.
 * - Verifying if, after saving a PowerGridMeter device to an initially empty repository, the repository contains the saved device.
 * - Verifying that, when saving a PowerGridMeter device with the same identity as an existing device, the repository doesn't save the device.
 */

class RepositoryDeviceMemTest {

    /**
     * Verifies if, after saving a device to an initially empty repository, the repository contains the saved device.
     */
    @Test
    void saveDeviceToEmptyRepository_ShouldContainDevice() {

        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        DeviceId deviceId = mock(DeviceId.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceId);

        // Act
        Device savedDevice = repository.save(device);
        deviceId = savedDevice.identity();
        Optional<Device> retrievedDeviceOptional = repository.ofIdentity(deviceId);

        // Assert
        assertTrue(repository.containsOfIdentity(deviceId));
        assertTrue(retrievedDeviceOptional.isPresent());
    }

    /**
     * Verifies that an empty repository doesn't contain any devices.
     */
    @Test
    void emptyRepository_ShouldNotContainDevice() {

        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        DeviceId deviceId = mock(DeviceId.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceId);

        // Act
        Optional<Device> retrievedDeviceOptional = repository.ofIdentity(deviceId);

        // Assert
        assertFalse(repository.containsOfIdentity(deviceId));
        assertFalse(retrievedDeviceOptional.isPresent());
    }

    /**
     * Verifies that, when saving a null device, the save() method throws an IllegalArgumentException.
     */

    @Test
    void saveNullDevice_ShouldThrowException() {
        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();

        String expectedMessage = "Device cannot be null";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.save(null));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Ensures that, when the repository is empty, the findAll() method returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {

        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();

        // Act
        Iterable<Device> allDevices = repository.findAll();

        // Assert
        assertFalse(allDevices.iterator().hasNext());
    }

    /**
     * Verifies if, when the repository contains devices, the findAll() method returns a non-empty iterable.
     */
    @Test
    void findAll_NonEmptyRepository_ShouldReturnNonEmptyIterable() {

        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        Device device1 = mock(Device.class);
        when(device1.identity()).thenReturn(deviceId1);
        Device device2 = mock(Device.class);
        when(device2.identity()).thenReturn(deviceId2);
        repository.save(device1);
        repository.save(device2);

        // Act
        Iterable<Device> allDevices = repository.findAll();

        // Assert
        assertNotNull(allDevices);
        assertTrue(allDevices.iterator().hasNext());
    }

    /**
     * Verifies if the getDevicesInRoom() method returns a list of devices in a room.
     */
    @Test
    void listDevicesInRoom_shouldReturnDeviceList() {
        //Arrange
        int expectedDevices = 3;
        IRepositoryDevice repository = new RepositoryDeviceMem();
        RoomID roomID = mock(RoomID.class);

        Device device1 = mock(Device.class);
        when(device1.getRoomId()).thenReturn(roomID);
        when(device1.identity()).thenReturn(mock(DeviceId.class));

        Device device2 = mock(Device.class);
        when(device2.getRoomId()).thenReturn(roomID);
        when(device2.identity()).thenReturn(mock(DeviceId.class));

        Device device3 = mock(Device.class);
        when(device3.getRoomId()).thenReturn(roomID);
        when(device3.identity()).thenReturn(mock(DeviceId.class));

        repository.save(device1);
        repository.save(device2);
        repository.save(device3);

        //Act
        List<Device> devicesInRoom = repository.getDevicesInRoom(roomID);

        //Assert
        assertEquals(expectedDevices, devicesInRoom.size());
    }

    @Test
    void listDevicesInRoom_shouldReturnEmptyList() {
        //Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        RoomID roomID = mock(RoomID.class);
        RoomID roomID2 = mock(RoomID.class);
        RoomID roomID3 = mock(RoomID.class);

        Device device1 = mock(Device.class);
        when(device1.getRoomId()).thenReturn(roomID2);
        when(device1.identity()).thenReturn(mock(DeviceId.class));

        Device device2 = mock(Device.class);
        when(device2.getRoomId()).thenReturn(roomID3);
        when(device2.identity()).thenReturn(mock(DeviceId.class));

        repository.save(device1);
        repository.save(device2);

        //Act
        List<Device> devicesInRoom = repository.getDevicesInRoom(roomID);

        //Assert
        assertTrue(devicesInRoom.isEmpty());
    }
    /**
     * Verifies if the update method updates the device in the repository.
     */
    @Test
    void updateDeviceInRepository_ShouldReturnUpdatedDevice() {
        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();

        ActivationStatus status = mock(ActivationStatus.class);
        when(status.toString()).thenReturn("true");

        DeviceId deviceId = mock(DeviceId.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceId);
        when(device.getActivationStatus()).thenReturn(status);
        repository.save(device);

        ActivationStatus status1 = mock(ActivationStatus.class);
        when(status.toString()).thenReturn("false");

        Device deviceToUpdate = mock(Device.class);
        when(deviceToUpdate.identity()).thenReturn(deviceId);
        when(deviceToUpdate.getActivationStatus()).thenReturn(status1);

        // Act
        Device updatedDevice = repository.update(deviceToUpdate);

        // Assert
        assertNotNull(updatedDevice);
        assertEquals(status1, updatedDevice.getActivationStatus());
    }
    /**
     * Verifies that, when saving a device with the same identity as an existing device, the repository doesn't save the device.
     */
    @Test
    void saveDuplicateDevice_ShouldThrowException() {
        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        Device device = mock(Device.class);
        DeviceId deviceId = mock(DeviceId.class);
        when(device.identity()).thenReturn(deviceId);
        repository.save(device);

        String expectedMessage = "Device already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(device));

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Verifies if the getActiveDevices() method returns a list of active devices.
     */
    @Test
    void listOfActiveDevices_shouldReturnListOfActiveDevices() {
        //Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        ActivationStatus status = mock(ActivationStatus.class);
        when(status.toString()).thenReturn("true");

        DeviceId deviceId = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);

        Device device = mock(Device.class);
        when(device.getActivationStatus()).thenReturn(status);
        when(device.identity()).thenReturn(deviceId);

        Device device2 = mock(Device.class);
        when(device2.getActivationStatus()).thenReturn(status);
        when(device2.identity()).thenReturn(deviceId2);

        repository.save(device);
        repository.save(device2);

        //Act
        List<Device> activeDevices = repository.getActiveDevices();

        //Assert
        assertFalse(activeDevices.isEmpty());
    }

    /**
     * Verifies if the getActiveDevices() method returns an empty list of active devices.
     */
    @Test
    void listOfActiveDevices_shouldReturnEmptyListOfActiveDevices() {
        //Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        ActivationStatus status = mock(ActivationStatus.class);
        when(status.toString()).thenReturn("false");

        DeviceId deviceId = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);

        Device device = mock(Device.class);
        when(device.getActivationStatus()).thenReturn(status);
        when(device.identity()).thenReturn(deviceId);

        Device device2 = mock(Device.class);
        when(device2.getActivationStatus()).thenReturn(status);
        when(device2.identity()).thenReturn(deviceId2);

        repository.save(device);
        repository.save(device2);

        //Act
        List<Device> activeDevices = repository.getActiveDevices();

        //Assert
        assertTrue(activeDevices.isEmpty());
    }
    /**
     * Verifies that, when saving a PowerGridMeter device with the same identity as an existing device, the repository doesn't save the device,
     * as it already exists within the system.
     */
    @Test
    void savePowerGridMeterDevice_ShouldThrowException() {
        // Arrange
        IRepositoryDevice repository = new RepositoryDeviceMem();
        DeviceId deviceId = new DeviceId("PowerGridMeter");
        DeviceName deviceName = new DeviceName("123");
        DeviceModel deviceModel = new DeviceModel("Dyson");
        RoomID roomId = new RoomID("room1");
        ActivationStatus activationStatus = new ActivationStatus(true);
        Device device = new Device(deviceId,deviceName,deviceModel,activationStatus,roomId);
        repository.save(device);

        String expectedMessage = "Device already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(device));

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}


