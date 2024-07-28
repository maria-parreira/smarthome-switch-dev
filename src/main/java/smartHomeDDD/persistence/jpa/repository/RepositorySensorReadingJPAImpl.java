package smartHomeDDD.persistence.jpa.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.persistence.jpa.datamodel.SensorReadingDataModel;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is the implementation of the repository interface for SensorReading
 * It uses JPA to persist the data
 */
public class RepositorySensorReadingJPAImpl implements IRepositorySensorReading {

    /**
     * The factory for SensorReading objects
     */
    final FactorySensorReading _factorySensorReading;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * The RepositorySensorReadingJPAImpl constructor
     * @param factory The factory to create the SensorReading
     */
    public RepositorySensorReadingJPAImpl (FactorySensorReading factory, EntityManager manager){
        this._factorySensorReading = factory;
        this._manager = manager;
    }

    /**
     * Gets the entity manager to access the database
     * @return the entity manager
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Saves a SensorReading object to the database
     * @param entity the domain object to save
     *
     * @return the saved domain object
     */
    @Override
    public SensorReading save(SensorReading entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Sensor reading cannot be null");
        }

        if(containsOfIdentity(entity.identity())) {
            throw new DataIntegrityViolationException("Sensor Reading already exists");
        }

        SensorReadingDataModel sensorReadingDataModel = new SensorReadingDataModel(entity);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(sensorReadingDataModel);
        tx.commit();
        em.close();

        return entity;
    }

    /**
     * Retrieves all SensorReading objects from the database
     * @return a list of all SensorReading objects
     */
    @Override
    public Iterable<SensorReading> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorReadingDataModel e");

        List<SensorReadingDataModel> listDataModel = query.getResultList();

        return SensorReadingDataModel.toDomainList(_factorySensorReading, listDataModel);
    }

    /**
     * Retrieves a SensorReading object from the database by its identity
     * @param id the identity of the SensorReading object
     * @return the SensorReading object, if it exists, otherwise, an empty optional
     */
    @Override
    public Optional<SensorReading> ofIdentity(SensorReadingID id) {

        SensorReadingDataModel sensorReadingDataModel = getEntityManager().find(SensorReadingDataModel.class, id.toString());

        if (sensorReadingDataModel != null) {
            SensorReading sensorReadingDomain = SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModel);
            return Optional.of(sensorReadingDomain);
        }
        else
            return Optional.empty();
    }

    /**
     * Verifies if a SensorReading object exists in the database by its identity
     * @param id the identity of the SensorReading object
     * @return true if the SensorReading object exists, otherwise, false
     */
    @Override
    public boolean containsOfIdentity(SensorReadingID id) {
        Optional<SensorReading> sensorReading = ofIdentity(id);

        return sensorReading.isPresent();
    }

    /**
     * Retrieves all SensorReading objects from the database by the device identity and defined time period
     * @param deviceID the identity of the device
     * @param startTime the timestamp that defines the start of the time period
     * @param endTime the timestamp that defines the end of the time period
     * @return a list of all SensorReading objects
     */

    @Override
    public List<SensorReading> getMeasurementsFromDeviceWithinPeriod(DeviceId deviceID, Timestamp startTime, Timestamp endTime){
        if (startTime.after(endTime)){
            throw new IllegalArgumentException("Invalid time period");
        }
        List <SensorReading> readingsWithinPeriod = new ArrayList<>();
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorReadingDataModel e WHERE e.timeStamp BETWEEN :startTime AND :endTime AND e.deviceID = :deviceId");
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        query.setParameter("deviceId", deviceID.toString());

        List <SensorReadingDataModel> sensorReadingDataModels = query.getResultList();
        sensorReadingDataModels.forEach(sensorReadingDataModel -> {
            SensorReading sensorReadingDomain = SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModel);
            readingsWithinPeriod.add(sensorReadingDomain);
        });

        return readingsWithinPeriod;
    }

    /**
     * Retrieves all SensorReading objects from the database by the device identity, sensor identity and defined time period
     * @param deviceID the identity of the device
     * @param sensorIDIndoors the identity of the sensor
     * @param startTime the timestamp that defines the start of the time period
     * @param endTime the timestamp that defines the end of the time period
     * @return a list of all SensorReading objects
     */
    @Override
    public List<SensorReading> getSensorReadingsBetweenTimestamp(DeviceId deviceID, SensorID sensorIDIndoors, Timestamp startTime, Timestamp endTime) {
        List <SensorReading> readingsBetweenTimeStamp = new ArrayList<>();
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorReadingDataModel e WHERE e.timeStamp BETWEEN :startTime AND :endTime AND e.deviceID = :deviceId AND e.sensorID = :sensorID");
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        query.setParameter("deviceId", deviceID.toString());
        query.setParameter("sensorID", sensorIDIndoors.toString());

        List <SensorReadingDataModel> sensorReadingDataModels = query.getResultList();
        sensorReadingDataModels.forEach(sensorReadingDataModel -> {
            SensorReading sensorReadingDomain = SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModel);
            readingsBetweenTimeStamp.add(sensorReadingDomain);
        });

        return readingsBetweenTimeStamp;
    }

    /**
     * Retrieves the latest SensorReading object from the database by the sensor identity
     *
     * @param sensorID the identity of the sensor
     * @return the SensorReading object
     */
    @Override
    public Optional<SensorReading> getLatestReadingFromSensor(SensorID sensorID) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorReadingDataModel e WHERE e.sensorID = :sensorID ORDER BY e.timeStamp DESC");
        query.setParameter("sensorID", sensorID.toString());

        SensorReadingDataModel readingDataModel = (SensorReadingDataModel) query.getResultList().get(0);
        SensorReading latestReading = SensorReadingDataModel.toDomain(_factorySensorReading, readingDataModel);
        return Optional.of(latestReading);
    }

    @Override
    public List<SensorReading> getSensorReadingsByDeviceId(DeviceId deviceId) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM SensorReadingDataModel e WHERE e.deviceID = :deviceId");
        query.setParameter("deviceId", deviceId.toString());

        List <SensorReadingDataModel> sensorReadingDataModels = query.getResultList();
        List <SensorReading> sensorReadings = new ArrayList<>();
        sensorReadingDataModels.forEach(sensorReadingDataModel -> {
            SensorReading sensorReadingDomain = SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModel);
            sensorReadings.add(sensorReadingDomain);
        });

        return sensorReadings;
    }

}
