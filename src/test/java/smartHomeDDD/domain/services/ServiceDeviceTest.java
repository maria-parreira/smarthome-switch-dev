package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceDevice;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceDeviceTest {

    /**
     * This method tests the successful creation of a ServiceDevice object.
     */
    @Test
    void testServiceDeviceConstructor_shouldConstructServiceObject() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);

        // Act
        ServiceDevice result = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);

        // Assert
        assertNotNull(result);
    }

    /**
     * This method tests the handling of a null FactoryDevice in the ServiceDevice constructor. It should throw an
     * IllegalArgumentException.
     */
    @Test
    void testServiceDeviceConstructorWithNullFactoryDevice_shouldThrowIllegalArgumentException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceDevice(repoDevice, repoRoom, null, generateRandomId));
    }

    /**
     * This method tests the handling of a null IRepositoryDevice in the ServiceDevice constructor. It should throw
     * an IllegalArgumentException.
     */
    @Test
    void testServiceDeviceConstructorWithNullRepoDevice_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceDevice(null, repoRoom, factoryDevice, generateRandomId));
    }

    /**
     * This method tests the handling of a null IRepositoryRoom in the ServiceDevice constructor. It should throw an
     * IllegalArgumentException.
     */
    @Test
    void testServiceDeviceConstructorWithNullRepoRoom_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceDevice(repoDevice, null, factoryDevice, generateRandomId));
    }

    /**
     * This method tests the handling of a null GenerateRandomId in the ServiceDevice constructor. It should throw an
     * IllegalArgumentException.
     */
    @Test
    void testServiceDeviceConstructorWithNullGenerateRandomId_shouldThrowIllegalArgumentException() {
        // Arrange
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ServiceDevice(repoDevice, repoRoom, factoryDevice, null));
    }

    /**
     * This method tests the addition of a device to a room. It should return the newly added device.
     */
    @Test
    void testAddNewDevice() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        RoomID roomID = new RoomID("1");
        DeviceName deviceName = new DeviceName("Thermostat");
        DeviceModel deviceModel = new DeviceModel("ModelX");
        ActivationStatus activationStatus = new ActivationStatus(true);
        DeviceId deviceId = new DeviceId("d1");
        Device device = mock(Device.class);

        when(repoRoom.containsOfIdentity(roomID)).thenReturn(true);
        when(generateRandomId.generateID()).thenReturn("d1");
        when(factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomID)).thenReturn(device);
        when(repoDevice.save(device)).thenReturn(device);

        // Act
        Device result = serviceDevice.addNewDevice(deviceName, deviceModel, activationStatus, roomID);

        // Assert
        assertEquals(device, result);
    }

    /**
     * This method tests the handling of a scenario where adding a device to a non-existent room is attempted. It should
     * throw an EntityNotFoundException.
     */
    @Test
    void testAddNewDeviceWithNoRoom_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        RoomID roomID = new RoomID("1");
        DeviceName deviceName = new DeviceName("Thermostat");
        DeviceModel deviceModel = new DeviceModel("ModelX");
        ActivationStatus activationStatus = new ActivationStatus(true);

        when(repoRoom.containsOfIdentity(roomID)).thenReturn(false);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.addNewDevice(deviceName, deviceModel, activationStatus, roomID));
    }

    /**
     * This method tests the listing of devices in a room. It should return a list of devices.
     */
    @Test
    void testListDevicesInRoom() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        RoomID roomID = new RoomID("1");
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);

        when(repoRoom.containsOfIdentity(roomID)).thenReturn(true);
        when(repoDevice.getDevicesInRoom(roomID)).thenReturn(List.of(device1, device2));

        // Act
        List<Device> result = serviceDevice.listDevicesInRoom(roomID);

        // Assert
        assertEquals(List.of(device1, device2), result);
    }

    /**
     * This method tests the handling of a scenario where listing devices in a non-existent room is attempted.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testListDevicesInRoomWithNoRoom_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        RoomID roomID = new RoomID("1");

        when(repoRoom.containsOfIdentity(roomID)).thenReturn(false);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.listDevicesInRoom(roomID));
    }

    /**
     * This method tests the listing of all active devices. It should return a list of active devices.
     */
    @Test
    void testListOfActiveDevices() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);

        when(repoDevice.getActiveDevices()).thenReturn(List.of(device1, device2));

        // Act
        List<Device> result = serviceDevice.listOfActiveDevices();

        // Assert
        assertEquals(List.of(device1, device2), result);
    }

    /**
     * This method tests the deactivation of a device. It should return the deactivated device.
     */
    @Test
    void testDeactivateDevice() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        DeviceId deviceId = new DeviceId("d1");
        Device device = mock(Device.class);

        when(repoDevice.ofIdentity(deviceId)).thenReturn(Optional.of(device));
        when(device.deactivateDevice()).thenReturn(true);
        when(repoDevice.update(device)).thenReturn(device);

        // Act
        Device result = serviceDevice.deactivateDevice(deviceId);

        // Assert
        assertEquals(device, result);
        verify(device).deactivateDevice();
    }

    /**
     * This method tests the handling of a scenario where deactivating a non-existent device is attempted.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testDeactivateDeviceWithNoDevice_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        DeviceId deviceId = new DeviceId("d1");

        when(repoDevice.ofIdentity(deviceId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.deactivateDevice(deviceId));
    }

    /**
     * This method tests the retrieval of a device by its ID. It should return the device.
     */
    @Test
    void testGetDeviceByID() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        DeviceId deviceId = new DeviceId("d1");
        Device device = mock(Device.class);

        when(repoDevice.ofIdentity(deviceId)).thenReturn(Optional.of(device));

        // Act
        Device result = serviceDevice.getDeviceByID(deviceId);

        // Assert
        assertEquals(device, result);
    }

    /**
     * This method tests the handling of a scenario where retrieving a device by a non-existent ID is attempted.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testGetDeviceByIDWithNoDevice_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        DeviceId deviceId = new DeviceId("d1");

        when(repoDevice.ofIdentity(deviceId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.getDeviceByID(deviceId));
    }

    /**
     * This method tests the handling of a scenario where the Power Grid Meter device is not found in the room.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testGetPowerGridMeterWithNoDevice_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        DeviceId powerGridMeterId = new DeviceId("d6");

        when(repoDevice.ofIdentity(powerGridMeterId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.getPowerGridMeter());
    }

    /**
     * This method tests the retrieval of the Power Grid Meter device in a room with an invalid room ID.
     * It should throw an EntityNotFoundException.
     */
    @Test
    void testGetPowerGridMeterWithInvalidRoom_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        RoomID roomID = new RoomID("1");
        DeviceId powerGridMeterId = new DeviceId("d6");
        Device powerGridMeter = mock(Device.class);

        when(repoDevice.ofIdentity(powerGridMeterId)).thenReturn(Optional.of(powerGridMeter));
        when(powerGridMeter.getRoomId()).thenReturn(new RoomID("2"));

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.getPowerGridMeter());
    }

    @Test
    void testDeactiveDevice_shouldThrowEntityNotFoundException() {
        // Arrange
        IRepositoryDevice repoDevice = mock(IRepositoryDevice.class);
        IRepositoryRoom repoRoom = mock(IRepositoryRoom.class);
        GenerateRandomId generateRandomId = mock(GenerateRandomId.class);
        FactoryDevice factoryDevice = mock(FactoryDevice.class);
        ServiceDevice serviceDevice = new ServiceDevice(repoDevice, repoRoom, factoryDevice, generateRandomId);
        DeviceId deviceId = new DeviceId("d1");
        Device device = mock(Device.class);

        when(repoDevice.ofIdentity(deviceId)).thenReturn(Optional.of(device));
        when(device.deactivateDevice()).thenReturn(false);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> serviceDevice.deactivateDevice(deviceId));
    }
}
