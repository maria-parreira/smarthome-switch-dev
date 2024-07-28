package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorTypeDataModel;
import jakarta.persistence.*;

import smartHomeDDD.domain.repository.IRepositoryActuatorType;

import java.util.List;
import java.util.Optional;

/**
 * This class provides a JPA implementation of the IRepositoryActuatorType interface.
 * It provides methods for performing CRUD operations on ActuatorType entities in a database.
 */
public class RepositoryActuatorTypeJPAImpl implements IRepositoryActuatorType {

    /**
     * The factory used for creating ActuatorType instances.
     */
    final FactoryActuatorType factoryActuatorType;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructs a new RepositoryActuatorTypeJPAImpl object with the specified FactoryActuatorType.
     * @param factoryActuatorType The factory used for creating ActuatorType instances.
     */
    public RepositoryActuatorTypeJPAImpl(FactoryActuatorType factoryActuatorType, EntityManager manager) {
        this.factoryActuatorType = factoryActuatorType;
        this._manager = manager;
    }

    /**
     * Creates and returns an EntityManager for interacting with the database.
     * @return an EntityManager for interacting with the database
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Saves the provided ActuatorType in the database.
     * @param actuatorType the ActuatorType to save
     * @return the saved ActuatorType
     * @throws IllegalArgumentException if the actuatorType is null or an ActuatorType with the same identity already exists
     */
    @Override
    public ActuatorType save(ActuatorType actuatorType) {
        if (actuatorType == null) {
            throw new IllegalArgumentException("ActuatorType cannot be null");
        }
        if( containsOfIdentity(actuatorType.identity()) ){
            throw new IllegalArgumentException("actuator Type already exists");
        }

        ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(actuatorTypeDataModel);
        tx.commit();
        em.close();

        return actuatorType;
    }

    /**
     * Retrieves all ActuatorType objects from the database.
     * @return an Iterable containing all ActuatorType objects from the database
     */
    @Override
//    @SuppressWarnings("unchecked")
    public Iterable<ActuatorType> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM ActuatorTypeDataModel e");

        List <ActuatorTypeDataModel> listDataModel = query.getResultList();

        return ActuatorTypeDataModel.toDomain(this.factoryActuatorType, listDataModel);
    }

    /**
     * Retrieves an ActuatorType with the specified ActuatorTypeID from the database.
     * @param id the ActuatorTypeID of the ActuatorType to retrieve
     * @return an Optional containing the ActuatorType if it exists, or an empty Optional if it does not
     */
    @Override
    public Optional<ActuatorType> ofIdentity(ActuatorTypeID id) {
        ActuatorTypeDataModel actuatorTypeDataModel = getEntityManager().find(ActuatorTypeDataModel.class, id.toString());

        if (actuatorTypeDataModel != null) {
            ActuatorType actuatorTypeDomain = ActuatorTypeDataModel.toDomain(this.factoryActuatorType, actuatorTypeDataModel);
            return Optional.of(actuatorTypeDomain);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if an ActuatorType with the specified ActuatorTypeID exists in the database.
     * @param id the ActuatorTypeID of the ActuatorType to check
     * @return true if an ActuatorType with the specified ActuatorTypeID exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(ActuatorTypeID id) {
        Optional<ActuatorType> actuatorType = ofIdentity(id);
        return actuatorType.isPresent();
    }

}