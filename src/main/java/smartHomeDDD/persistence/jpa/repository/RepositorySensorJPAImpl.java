package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.persistence.jpa.datamodel.SensorDataModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

/**
 * The RepositorySensorJPAImpl class provides a JPA implementation of the IRepositorySensor interface.
 * It manages Sensor objects, providing methods for saving, retrieving, and checking the existence of Sensor objects in the database.
 */
public class RepositorySensorJPAImpl implements IRepositorySensor {

    /**
     * Factory for creating Sensor objects.
     */
    final FactorySensor _factorySensor;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructor for the RepositorySensorJPAImpl class.
     *
     * @param factorySensor The factory used to create Sensor objects.
     */
    public RepositorySensorJPAImpl(FactorySensor factorySensor, EntityManager manager) {
        _factorySensor = factorySensor;
        _manager = manager;
    }

    /**
     * Creates an EntityManager object for interacting with the database.
     *
     * @return An EntityManager object.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Persists a Sensor object in the database.
     *
     * @param sensor The Sensor object to be persisted.
     * @return The persisted Sensor object.
     * @throws IllegalArgumentException If the sensor is null or already exists in the database.
     */
    @Override
    public Sensor save(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }
        if (containsOfIdentity(sensor.identity())) {
            throw new IllegalArgumentException("Sensor already exists");
        }

        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(sensorDataModel);
        tx.commit();
        em.close();

        return sensor;
    }

    /**
     * Retrieves a list with all persisted sensors.
     *
     * @return A list with all persisted sensors.
     */
    @Override
    public Iterable<Sensor> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorDataModel e");

        List<SensorDataModel> listDataModel = query.getResultList();

        return SensorDataModel.toDomain(_factorySensor, listDataModel);
    }

    /**
     * Finds a sensor by identity
     *
     * @param id Sensor identity
     * @return Sensor if found, otherwise empty
     */
    @Override
    public Optional<Sensor> ofIdentity(SensorID id) {
        SensorDataModel sensorDataModel = getEntityManager().find(SensorDataModel.class, id.toString());
        if (sensorDataModel == null) {
            return Optional.empty();
        }
        return Optional.of(SensorDataModel.toDomain(_factorySensor, sensorDataModel));
    }

    /**
     * Checks if a sensor with the given identity exists
     *
     * @param id Sensor identity
     * @return True if the sensor exists, otherwise false
     */
    @Override
    public boolean containsOfIdentity(SensorID id) {
        return getEntityManager().find(SensorDataModel.class, id.toString()) != null;
    }

    /**
     * Retrieves a list of Sensor objects that are associated with a specific Device.
     *
     * @param id The ID of the Device for which to retrieve the associated Sensors.
     * @return A list of Sensor objects associated with the specified Device.
     */

    @Override
    public List<Sensor> getSensorsByDeviceID(DeviceId id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorDataModel e WHERE e.deviceId = :deviceId");
        query.setParameter("deviceId", id.toString());
        List<SensorDataModel> listDataModel = query.getResultList();

        return SensorDataModel.toDomain(_factorySensor, listDataModel);
    }


    @Override
    public SensorID containsOfIdentitySI(SensorID id) {
        return null;
    }



}
