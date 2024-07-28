package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.SensorTypeDataModel;

import jakarta.persistence.*;

import smartHomeDDD.domain.repository.IRepositorySensorType;

import java.util.List;
import java.util.Optional;

/**
 * RepositorySensorTypeJPAImpl is a repository class that interacts
 * with the database using JPA for sensorType objects.
 */
public class RepositorySensorTypeJPAImpl implements IRepositorySensorType {

    /**
     * The factory used for creating sensorType instances.
     */
   final FactorySensorType _factorySensorType;

    /**
     * The EntityManager used for database operations.
     */
   final EntityManager _manager;

    /**
     * Constructs a RepositorySensorTypeJPAImpl object using the provided parameters.
     * @param factorySensorType The factory used for creating sensorType instances.
     * @param manager The EntityManager used for database operations.
     */
    public RepositorySensorTypeJPAImpl(FactorySensorType factorySensorType, EntityManager manager) {
        this._factorySensorType = factorySensorType;
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
     * Saves a sensorType entity to the database.
     * @param sensorType The sensorType object to be saved.
     * @return The saved sensorType object.
     * @throws IllegalArgumentException if the sensorType object is null or already exists in the database.
     */
    @Override
    public SensorType save(SensorType sensorType) {
        if (sensorType == null) {
            throw new IllegalArgumentException("sensorType cannot be null");
        }
        SensorTypeDataModel existingSensorType = _manager.find(SensorTypeDataModel.class, sensorType.identity().toString());
        if (existingSensorType != null) {
            throw new IllegalArgumentException("sensorType already exists");
        }

        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(sensorTypeDataModel);
        tx.commit();
        em.close();

        return sensorType;
    }

    /**
     * Retrieves all sensorType entities stored in the database.
     * @return An Iterable containing all sensorType entities.
     */
    @Override
    public Iterable<SensorType> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorTypeDataModel e");

        List<SensorTypeDataModel> listDataModel = query.getResultList();

        return SensorTypeDataModel.toDomain(this._factorySensorType, listDataModel);
    }

    /**
     * Retrieves a sensorType entity with the specified SensorTypeID from the database.
     * @param id The SensorTypeID of the sensorType entity to be retrieved.
     * @return An Optional containing the sensorType entity if it exists, or an empty Optional if it does not.
     */
    @Override
    public Optional<SensorType> ofIdentity(SensorTypeID id) {
        SensorTypeDataModel sensorTypeDataModel = getEntityManager().find(SensorTypeDataModel.class, id.toString());

        if (sensorTypeDataModel != null) {
            SensorType sensorTypeDomain = SensorTypeDataModel.toDomain(this._factorySensorType, sensorTypeDataModel);
            return Optional.of(sensorTypeDomain);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if a sensorType entity with the specified SensorTypeID exists in the database.
     * @param id The SensorTypeID of the sensorType entity to be checked.
     * @return true if a sensorType entity with the specified SensorTypeID exists, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(SensorTypeID id) {
        Optional<SensorType> sensorType = ofIdentity(id);
        return sensorType.isPresent();
    }
}
