package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.RoomID;

import java.util.List;

/**
 * The IRepositoryDevice interface provides a contract for a repository that manages Device objects.
 * It extends the generic Repository interface, specifying DeviceId as the ID type and Device as the entity type.
 */
public interface IRepositoryDevice extends Repository<DeviceId, Device> {

    /**
     * Retrieves a list of Device objects that are located in a specific room.
     *
     * @param roomID The ID of the room for which to retrieve the devices.
     * @return A list of Device objects located in the specified room.
     */
    List<Device> getDevicesInRoom(RoomID roomID);

    /**
     * Updates the information of a Device object in the repository.
     *
     * @param entity The Device object with updated information.
     * @return The updated Device object.
     */
    Device update(Device entity);

    /**
     * Retrieves a list of Device objects that are currently active.
     *
     * @return A list of active Device objects.
     */
    List<Device> getActiveDevices();
}
