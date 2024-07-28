package smartHomeDDD.persistence.springdata;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.RoomID;
import smartHomeDDD.persistence.jpa.datamodel.DeviceDataModel;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repository implementation for managing Device entities using Spring Data.
 * This class provides methods to interact with the persistence layer for Device entities.
 */
@Repository
public class RepositoryDeviceSpringData implements IRepositoryDevice {

    /**
     * The Spring Data repository for DeviceDataModel entities.
     */
    final IRepositoryDeviceSpringData _repositoryDeviceSpringData;

    /**
     * The factory for creating Device domain objects.
     */
    final FactoryDevice _factoryDevice;

    /**
     * Constructor to initialize RepositoryDeviceSpringData.
     *
     * @param factoryDevice              The factory for creating Device objects
     * @param repositoryDeviceSpringData The Spring Data repository for Device entities
     */
    public RepositoryDeviceSpringData(FactoryDevice factoryDevice, IRepositoryDeviceSpringData repositoryDeviceSpringData) {
        this._factoryDevice = factoryDevice;
        this._repositoryDeviceSpringData = repositoryDeviceSpringData;
    }

    /**
     * Save a device to the database
     * If its name is "PowerGridMeter" it returns an exception as only one PowerGridMeter can exist in the system.
     * @param device the device to save in the database
     * @return the saved device
     */
    @Override
    public Device save(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        if (containsOfIdentity(device.identity())) {
            throw new DataIntegrityViolationException("Device already exists");
        }
        if (Objects.equals(device.getDeviceName().toString(), "PowerGridMeter")) {
            throw new IllegalArgumentException("PowerGridMeter cannot be added as one already exists in the system.");
        }
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        this._repositoryDeviceSpringData.save(deviceDataModel);

        return device;
    }

    /**
     * Retrieve all devices from the database
     *
     * @return a list containing all device objects
     */
    @Override
    public Iterable<Device> findAll() {
        List<DeviceDataModel> deviceDataModels = this._repositoryDeviceSpringData.findAll();
        return DeviceDataModel.toDomain(_factoryDevice, deviceDataModels);
    }

    /**
     * Retrieve a device by its identity
     *
     * @param id the device identity
     * @return the device object
     */
    @Override
    public Optional<Device> ofIdentity(DeviceId id) {
        Optional<DeviceDataModel> deviceDataModelSaved = this._repositoryDeviceSpringData.findById(id.toString());

        if (deviceDataModelSaved.isPresent()) {
            Device deviceDomain = DeviceDataModel.toDomain(_factoryDevice, deviceDataModelSaved.get());
            return Optional.of(deviceDomain);
        } else
            return Optional.empty();
    }

    /**
     * Check if a device with a given identity exists
     *
     * @param id the device identity
     * @return true if the device exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(DeviceId id) {
        return _repositoryDeviceSpringData.existsById(id.toString());
    }

    /**
     * Retrieve all devices in a room
     *
     * @param roomID the room identity from which to retrieve the devices
     * @return a list of devices in the room
     */
    @Override
    public List<Device> getDevicesInRoom(RoomID roomID) {

        List<DeviceDataModel> deviceDataModels = this._repositoryDeviceSpringData.findByRoomId(roomID.toString());
        return DeviceDataModel.toDomain(_factoryDevice, deviceDataModels);
    }


    /**
     * Update a device in the database
     *
     * @param device the device to update
     * @return the updated device
     */
    @Override
    public Device update(Device device) {

        String deviceID = device.identity().toString();

        Optional<DeviceDataModel> deviceDataModel = this._repositoryDeviceSpringData.findById(deviceID);

        if (deviceDataModel.isPresent()) {
            boolean isUpdated = deviceDataModel.get().updateFromDomain(device);

            if (isUpdated) {
                DeviceDataModel deviceDataModelSaved = this._repositoryDeviceSpringData.save(deviceDataModel.get());

                return DeviceDataModel.toDomain(_factoryDevice, deviceDataModelSaved);

            } else
                throw new IllegalArgumentException("Device cannot be updated");
        } else
            throw new EntityNotFoundException("Device cannot be found in the database");
    }

    /**
     * Retrieves all active devices from the repository.
     * An active device is defined as a device whose activation status is "true".
     *
     * @return A list of active Device objects. If no active devices are found, an empty list is returned.
     */
    @Override
    public List<Device> getActiveDevices() {

        List<DeviceDataModel> deviceDataModels = this._repositoryDeviceSpringData.findDevicesByStatus(true);
        return DeviceDataModel.toDomain(_factoryDevice, deviceDataModels);
    }


}

