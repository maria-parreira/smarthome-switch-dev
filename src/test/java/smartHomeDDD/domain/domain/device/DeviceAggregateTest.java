package smartHomeDDD.domain.domain.device;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for the Device aggregate.
 * This class tests the conditions for the following scenarios:
 * - create a valid instance of Device
 * - return the DeviceId
 * - return true when the sameAs method is given the same object as parameter
 * - return false when the sameAs method is given two equal devices with different ids
 * - return false when the sameAs method is given two devices with different names
 * - return false when the sameAs method is given two devices with different models
 * - return false when the sameAs method is given two devices with different status
 * - return false when the sameAs method is given two devices with different room ids
 * - verify if the deactivateDevice method correctly deactivates a device
 */

class DeviceAggregateTest {

    /**
     * Test to ensure that the Device constructor creates a Device object when given valid arguments.
     */
    @Test
    void validArguments_shouldCreateADevice(){

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);

        // Act
        Device device = factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomIDDouble);

        // Assert
        assertNotNull(device);
        assertEquals(device.getRoomId(), roomIDDouble);
        assertEquals(device.identity(), deviceId);
        assertEquals(device.getDeviceName(), deviceName);
        assertEquals(device.getDeviceModel(), deviceModel);
        assertEquals(device.getActivationStatus(), activationStatus);
    }

    /**
     * Test to ensure that the identity method returns the device id.
     */
    @Test
    void shouldReturnDeviceId() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device = factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomIDDouble);

        // Act
        DeviceId actualDeviceId = device.identity();

        // Assert
        assertEquals(actualDeviceId, deviceId);
    }

    /**
     * Test to ensure that the sameAs method returns true when given the same object.
     */
    @Test
    void comparingTheSameDevice_shouldReturnTrue(){

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device = factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomIDDouble);

        // Act
        boolean sameObject = device.sameAs(device);

        // Assert
        assertTrue(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns true when given two equal devices, with different ids.
     */
    @Test
    void comparingTwoIdenticalDevicesWithDifferentIds_shouldReturnFalse() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceId deviceId2 = new DeviceId("2");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);

        Device device1 = factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomIDDouble);
        Device device2 = factoryDevice.createDevice(deviceId2, deviceName, deviceModel, activationStatus, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different names.
     */
    @Test
    void comparingTwoDevicesWithDifferentNames_shouldReturnFalse() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName1 = new DeviceName("Device 1");
        DeviceName deviceName2 = new DeviceName("Device 2");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);

        Device device1 = factoryDevice.createDevice(deviceId, deviceName1, deviceModel, status, roomIDDouble);
        Device device2 = factoryDevice.createDevice(deviceId, deviceName2, deviceModel, status, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different models.
     */
    @Test
    void comparingTwoDevicesWithDifferentModels_shouldReturnFalse() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel1 = new DeviceModel("model 1");
        DeviceModel deviceModel2 = new DeviceModel("model 2");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);

        Device device1 = factoryDevice.createDevice(deviceId, deviceName, deviceModel1, status, roomIDDouble);
        Device device2 = factoryDevice.createDevice(deviceId, deviceName, deviceModel2, status, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different status.
     */
    @Test
    void comparingTwoDevicesWithDifferentStatus_shouldReturnFalse() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus status1 = new ActivationStatus(true);
        ActivationStatus status2 = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);

        Device device1 = factoryDevice.createDevice(deviceId, deviceName, deviceModel, status1, roomIDDouble);
        Device device2 = factoryDevice.createDevice(deviceId, deviceName, deviceModel, status2, roomIDDouble);

        device2.deactivateDevice();

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different room ids.
     */
    @Test
    void comparingTwoDevicesWithDifferentRoomIds_shouldReturnFalse() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceId = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);

        Device device1 = factoryDevice.createDevice(deviceId, deviceName, deviceModel, status, roomIDDouble1);
        Device device2 = factoryDevice.createDevice(deviceId, deviceName, deviceModel, status, roomIDDouble2);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to verify if the deactivateDevice method correctly deactivates a device.
     */
    @Test
    void deactivateDevice_shouldDeactivateDevice() {

        // Arrange
        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        DeviceId deviceIdDouble = new DeviceId("1");
        DeviceName deviceName = new DeviceName("Device");
        DeviceModel deviceModel = new DeviceModel("model");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomIDDouble = mock(RoomID.class);

        Device device = factoryDevice.createDevice(deviceIdDouble, deviceName, deviceModel, status, roomIDDouble);

        // Act
        boolean result = device.deactivateDevice();

        // Assert
        assertTrue(result);
    }
}
