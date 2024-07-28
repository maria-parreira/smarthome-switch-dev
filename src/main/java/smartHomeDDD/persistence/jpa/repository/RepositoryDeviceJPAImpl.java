package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.RoomID;
import smartHomeDDD.persistence.jpa.datamodel.DeviceDataModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides a JPA implementation of the IRepositoryDevice interface.
 * It provides methods for performing CRUD operations on Device entities in a database.
 */
public class RepositoryDeviceJPAImpl implements IRepositoryDevice {

    /**
     * The factory used for creating Device instances.
     */
    final FactoryDevice factoryDevice;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructor for a Device Repository that uses JPA
     * @param factoryDevice the factory to instantiate Device domain object
     */
    public RepositoryDeviceJPAImpl (FactoryDevice factoryDevice, EntityManager manager) {
        this.factoryDevice = factoryDevice;
        this._manager = manager;
    }

    /**
     * Retrieves the EntityManager used for database operations.
     * @return The EntityManager instance.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Saves a Device entity to the database.
     * @param device The Device object to be saved.
     * @return The saved Device object.
     * @throws IllegalArgumentException if the Device object is null or already exists in the database.
     */
    @Override
    public Device save(Device device) {
        if(device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        if(containsOfIdentity(device.identity())) {
            throw new IllegalArgumentException("Device already exists");
        }

        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(deviceDataModel);
        tx.commit();
        em.close();

        return device;
    }

    /**
     * Retrieves all Device entities in a specific room from the database.
     * @param roomID The RoomID of the room.
     * @return A List of Device entities in the room.
     */
    @Override
    public List<Device> getDevicesInRoom(RoomID roomID) {
        List<Device> devicesInRoom = new ArrayList<>();

        Query query = getEntityManager().createQuery(
                "SELECT e FROM DeviceDataModel e WHERE e.roomId = :roomId");
        query.setParameter("roomId", roomID.toString());

        List<DeviceDataModel> listDataModel = query.getResultList();

        listDataModel.forEach(deviceDataModel -> {
            Device deviceDomain = DeviceDataModel.toDomain(factoryDevice, deviceDataModel);
            devicesInRoom.add(deviceDomain);
        });

        return devicesInRoom;
    }

    /**
     * Retrieves a list of active Device entities from the database.
     * An active device is defined as a device with a status of true.
     * @return A List of active Device entities.
     */
    @Override
    public List<Device> getActiveDevices() {
        List<Device> activeDevices = new ArrayList<>();

        Query query = getEntityManager().createQuery(
                "SELECT e FROM DeviceDataModel e WHERE e.status = :status");
        query.setParameter("status", true);

        List<DeviceDataModel> listDataModel = query.getResultList();

        listDataModel.forEach(deviceDataModel -> {
            Device deviceDomain = DeviceDataModel.toDomain(factoryDevice, deviceDataModel);
            activeDevices.add(deviceDomain);
        });

        return activeDevices;
    }

    /**
     * Update a device in the database
     * @param device the device object to be updated
     * @return The updated device object
     * * @throws IllegalArgumentException if the Device object is null.
     */
    @Override
    public Device update(Device device) {
        if (device == null) {
            throw new IllegalArgumentException();
        }

        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(deviceDataModel);
        tx.commit();
        em.close();

        return device;
    }

    /**
     * Retrieves all devices entities stored in the database
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findAll() {

        Query query = getEntityManager().createQuery(
                "SELECT e FROM DeviceDataModel e");

        List<DeviceDataModel> listDataModel = query.getResultList();

        return DeviceDataModel.toDomain(factoryDevice, listDataModel);
    }

    /**
     * Retrieves a Device entity with the specified DeviceId from the database.
     * @param id The DeviceId of the Device entity to be retrieved
     * @return An Optional containing the Device entity if it exists, or an empty Optional if it does not.
     */
    @Override
    public Optional<Device> ofIdentity(DeviceId id) {

        DeviceDataModel deviceDataModel = getEntityManager().find(DeviceDataModel.class, id.toString());

        if (deviceDataModel != null) {
            Device deviceDomain = DeviceDataModel.toDomain(factoryDevice, deviceDataModel);
            return Optional.of(deviceDomain);
        }
        else
            return Optional.empty();
    }

    /**
     * Check if a device exists in the database
     * @param id the device id of the device entity to be checked.
     * @return true if a Device entity with the specified DeviceId exists, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(DeviceId id) {
        Optional<Device> device = ofIdentity(id);
        return device.isPresent();
    }
}

