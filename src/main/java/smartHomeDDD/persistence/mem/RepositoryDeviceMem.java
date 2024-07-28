package smartHomeDDD.persistence.mem;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.RoomID;

import java.util.*;

/**
 * In-memory implementation of the Device repository.
 */

public class RepositoryDeviceMem implements IRepositoryDevice {

    private final Map<DeviceId, Device> DATA = new HashMap<>();

    /**
     * Saves the provided Device entity in the repository.
     *
     * @param entity the Device entity to save.
     * @return the saved Device entity.
     */
    @Override
    public Device save(Device entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        if (containsOfIdentity(entity.identity())) {
            throw new DataIntegrityViolationException("Device already exists");
        }
        DATA.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Stores a Device object in the repository.
     *
     * @param entity The Device object to be stored.
     * @return The Device object that was stored.
     */
    @Override
    public Device update(Device entity) {
        DATA.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Retrieves all devices stored in the repository.
     *
     * @return An Iterable containing all devices stored in the repository.
     */
    @Override
    public Iterable<Device> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves a device from the repository based on its identity.
     *
     * @param id The identity of the device to retrieve.
     * @return An Optional containing the device if found, or empty if not found.
     */
    @Override
    public Optional<Device> ofIdentity(DeviceId id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Checks if a device with the specified identity exists in the repository.
     *
     * @param id The identity of the device to check.
     * @return true if the device exists in the repository, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(DeviceId id) {
        for (Device device : DATA.values()) {
            if (device.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all devices in a room.
     *
     * @param roomID The identity of the room to retrieve devices from.
     * @return A list of devices in the room.
     */
    @Override
    public List<Device> getDevicesInRoom(RoomID roomID) {
        List<Device> devicesInRoom = new ArrayList<>();
        Iterable<Device> allDevices = findAll();

        for (Device device : allDevices) {
            if (device.getRoomId().equals(roomID)) {
                devicesInRoom.add(device);
            }
        }
        return devicesInRoom;

    }

    /**
     * Retrieves all active devices from the repository.
     *
     * @return A list of active Device objects. If no active devices are found, an empty list is returned.
     */
    @Override
    public List<Device> getActiveDevices() {
        List<Device> activeDevices = new ArrayList<>();
        Iterable<Device> allDevices = findAll();

        for (Device device : allDevices) {
            if (device.getActivationStatus().toString().equals("true")) {
                activeDevices.add(device);
            }
        }
        return activeDevices;
    }
}
