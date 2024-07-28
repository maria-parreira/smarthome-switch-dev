package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.valueobject.*;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceDevice class provides services related to Device objects.
 * It uses a FactoryDevice to create Device objects and IRepositoryDevice and IRepositoryRoom to store and retrieve them.
 */
@Service
public class ServiceDevice {
    /**
     * The repository for storing and retrieving Device objects.
     */
    final IRepositoryDevice repositoryDevice;

    /**
     * The repository for storing and retrieving Room objects.
     */
    final IRepositoryRoom repositoryRoom;

    /**
     * The factory for creating Device objects.
     */
    final FactoryDevice factoryDevice;

    /**
     * The service for generating random IDs.
     */
    final GenerateRandomId generateRandomId;

    /**
     * Constructor for the ServiceDevice class.
     *
     * @param repositoryDevice The repository for devices.
     * @param repositoryRoom   The repository for rooms.
     * @param factoryDevice    The factory for devices.
     * @throws IllegalArgumentException If the device repository, room repository, device factory or room factory is null.
     */
    public ServiceDevice(IRepositoryDevice repositoryDevice, IRepositoryRoom repositoryRoom, FactoryDevice factoryDevice, GenerateRandomId generateRandomId) {
        if (repositoryDevice == null)
            throw new IllegalArgumentException("Device Repository cannot be null");
        if (repositoryRoom == null)
            throw new IllegalArgumentException("Room Repository cannot be null");
        if (factoryDevice == null)
            throw new IllegalArgumentException("Factory Device cannot be null");
        if (generateRandomId == null)
            throw new IllegalArgumentException("Generate Random ID cannot be null");

        this.repositoryDevice = repositoryDevice;
        this.repositoryRoom = repositoryRoom;
        this.factoryDevice = factoryDevice;
        this.generateRandomId = generateRandomId;
    }

    /**
     * Creates a new device and saves it to the repository.
     *
     * @param deviceName       The name of the device.
     * @param deviceModel      The model of the device.
     * @param activationStatus The activation status of the device.
     * @param roomID           The unique identifier for the room in which the device is located.
     * @return The saved device object.
     */
    public Device addNewDevice(DeviceName deviceName, DeviceModel deviceModel, ActivationStatus activationStatus, RoomID roomID) {
        if (!repositoryRoom.containsOfIdentity(roomID))
            throw new EntityNotFoundException("Room not found");
        DeviceId deviceId = new DeviceId(generateRandomId.generateID());
        Device device = factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomID);
        return repositoryDevice.save(device);
    }

    /**
     * Lists all devices in a room.
     *
     * @param roomID The id of the room to list devices from
     * @return A list of DTOs of the devices in the room.
     * @throws IllegalArgumentException If the room is not found.
     */
    public List<Device> listDevicesInRoom(RoomID roomID) {

        if (!repositoryRoom.containsOfIdentity(roomID)) {
            throw new EntityNotFoundException("Room not found");
        }

        return repositoryDevice.getDevicesInRoom(roomID);
    }

    /**
     * Lists all active devices.
     *
     * @return A list of DTOs of the active devices.
     * @throws IllegalArgumentException If no active devices are found.
     */
    public List<Device> listOfActiveDevices() {
        return repositoryDevice.getActiveDevices();
    }

    /**
     * Deactivates a device identified by the provided DeviceId.
     *
     * @param deviceId The unique identifier of the device to be deactivated.
     * @return The deactivated Device object.
     * @throws IllegalArgumentException If the device does not exist or cannot be deactivated.
     */
    public Device deactivateDevice(DeviceId deviceId) throws EntityNotFoundException {
        // Retrieve the device from the repository
        Optional<Device> device = repositoryDevice.ofIdentity(deviceId);

        // Deactivate the device if it exists
        if (device.isPresent()) {
            Device myDevice = device.get();
            boolean isDeactivated = myDevice.deactivateDevice();

            // Save the device and return the DTO if it was successfully deactivated
            if (isDeactivated) {
                return repositoryDevice.update(myDevice);
            }
        }
        throw new EntityNotFoundException("Device not found");
    }

    public Device getDeviceByID(DeviceId deviceId) {
        Optional<Device> device = repositoryDevice.ofIdentity(deviceId);
        if (device.isPresent()) {
            return device.get();
        }
        throw new EntityNotFoundException("Device not found");
    }

    public Iterable<Device> getAllDevices() {
        return repositoryDevice.findAll();
    }

    /**
     * Retrieves the PowerGridMeter device.
     * If it's not found, it returns an exception.
     * @return The PowerGridMeter device.
     */
    public Device getPowerGridMeter() {
        List<Device> devices = (List<Device>) repositoryDevice.findAll();
        for (Device device : devices) {
            if (device.getDeviceName().toString().equals("Power Grid Meter")) {
                return device;
            }
        }
        throw new EntityNotFoundException("PowerGridMeter not found");
    }

}

