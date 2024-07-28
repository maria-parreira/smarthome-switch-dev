package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.SensorModelDataModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

/**
 * This class provides a JPA implementation of the IRepositorySensorModel interface.
 * It provides methods for performing CRUD operations on sensorModel entities in a database.
 */
public class RepositorySensorModelJPAImpl implements IRepositorySensorModel {

    /**
     * The factory for creating Sensor Model instances.
     */
    final FactorySensorModel _factorySensorModel;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructs a RepositorySensorModelJPA object with the specified FactorySensorModel.
     * @param factorySensorModel The factory for creating Sensor Model instances.
     */
    public RepositorySensorModelJPAImpl(FactorySensorModel factorySensorModel, EntityManager manager) {
        _factorySensorModel = factorySensorModel;
        _manager = manager;
    }

    /**
     * Retrieves the EntityManager for database operations.
     * @return The EntityManager instance.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

   /**
     * Saves a Sensor Model entity to the database.
     * @param sensorModel the model of the sensor object
     * @return a Sensor Model object if it is successfully saved in the repository
     */
   @Override
    public SensorModel save(SensorModel sensorModel) {
        if (sensorModel == null) {
            throw new IllegalArgumentException("Sensor Model cannot be null");
        }
        if (containsOfIdentity(sensorModel.identity())) {
            throw new IllegalArgumentException("Sensor Model already exists");
        }

        SensorModelDataModel sensorModelDataModel = new SensorModelDataModel(sensorModel);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(sensorModelDataModel);
        tx.commit();
        em.close();

        return sensorModel;
    }

    /**
     * Retrieves all sensorModel objects stored in the repository.
     * @return An Iterable containing all sensorModel objects in the repository.
     */
    @Override
    public Iterable<SensorModel> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorModelDataModel e");

        List<SensorModelDataModel> listDataModel = query.getResultList();

        try {

            return SensorModelDataModel.toDomain(_factorySensorModel, listDataModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a sensorModel object with the specified SensorModelID from the repository.
     * @param id The SensorModelID of the sensorModel object to be retrieved.
     * @return An Optional containing the sensorModel object if it exists in the repository, or an empty Optional if it does not.
     */
    @Override
    public Optional<SensorModel> ofIdentity(SensorModelID id) {
        SensorModelDataModel sensorModelDataModel = getEntityManager().find(SensorModelDataModel.class, id.toString());

        if (sensorModelDataModel != null) {
            SensorModel sensorModelDomain = SensorModelDataModel.toDomain(_factorySensorModel, sensorModelDataModel);
            return Optional.of(sensorModelDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a sensorModel object with the specified SensorModelID exists in the repository.
     * @param id The SensorModelID of the sensorModel object to be checked.
     * @return true if a sensorModel object with the specified SensorModelID exists in the repository, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(SensorModelID id) {
        Optional<SensorModel> sensorModel = ofIdentity(id);
        return sensorModel.isPresent();
    }

    /**
     * Retrieves a list of sensorModel objects from the repository that match the specified SensorTypeID.
     * @param sensorTypeID The SensorTypeID of the sensorModel objects to be retrieved.
     * @return A List of sensorModel objects that match the specified SensorTypeID.
     */
    @Override
  public List<SensorModel> getModelsBySensorType(SensorTypeID sensorTypeID) {
      Query query = getEntityManager().createQuery("SELECT e FROM SensorModelDataModel e WHERE e.sensorTypeID = :sensorTypeID");
      query.setParameter("sensorTypeID", sensorTypeID.toString());

      List<SensorModelDataModel> resultList = query.getResultList();
      return SensorModelDataModel.toDomain(_factorySensorModel, resultList);
  }
}
