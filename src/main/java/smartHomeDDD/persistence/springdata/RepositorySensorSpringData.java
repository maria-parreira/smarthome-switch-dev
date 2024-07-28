package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.persistence.jpa.datamodel.SensorDataModel;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for managing Sensor entities using Spring Data.
 * This class provides methods to interact with the persistence layer for Sensor entities.
 */
@Repository
public class RepositorySensorSpringData implements IRepositorySensor {

    
    /**
     * The Spring Data repository for SensorDataModel entities.
     */
    final IRepositorySensorSpringData _repositorySensorSpringData;

    /**
     * The factory for creating Sensor domain objects.
     */
    final FactorySensor _factorySensor;

    /**
     * Constructor to initialize RepositorySensorSpringData.
     *
     * @param factorySensor              The factory for creating Sensor objects
     * @param repositorySensorSpringData The Spring Data repository for Sensor entities
     */
    public RepositorySensorSpringData(FactorySensor factorySensor, IRepositorySensorSpringData repositorySensorSpringData) {
        _factorySensor = factorySensor;
        _repositorySensorSpringData = repositorySensorSpringData;
    }

    /**
     * Saves a Sensor entity.
     *
     * @param sensor The Sensor entity to save
     * @return The saved Sensor entity
     * @throws IllegalArgumentException if the provided Sensor object is null or if a Sensor with the same identity already exists
     */
    @Override
    public Sensor save(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }
        if (containsOfIdentity(sensor.identity())) {
            throw new DataIntegrityViolationException("Sensor already exists");
        }

        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        this._repositorySensorSpringData.save(sensorDataModel);

        return sensor;
    }

    /**
     * Retrieves all Sensor entities.
     *
     * @return An Iterable containing all Sensor entities
     */
    @Override
    public Iterable<Sensor> findAll() {
        List<SensorDataModel> lstSensorDataModelSaved = this._repositorySensorSpringData.findAll();

        return SensorDataModel.toDomain(_factorySensor, lstSensorDataModelSaved);
    }

    /**
     * Retrieves a Sensor entity by its identity.
     *
     * @param id The identity of the Sensor entity
     * @return An Optional containing the Sensor entity, or empty if not found
     */
    @Override
    public Optional<Sensor> ofIdentity(SensorID id) {
        Optional<SensorDataModel> optSensorDataModelSaved = this._repositorySensorSpringData.findById(id.toString());

        if (optSensorDataModelSaved.isPresent()) {
            Sensor sensorDomain = SensorDataModel.toDomain(_factorySensor, optSensorDataModelSaved.get());
            return Optional.of(sensorDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a Sensor entity with the given identity exists.
     *
     * @param id The identity of the Sensor entity to check
     * @return true if the entity exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(SensorID id) {
        return _repositorySensorSpringData.existsById(id.toString());
    }

    /**
     * Retrieves all Sensor entities associated with a Device.
     *
     * @param id The identity of the Device
     * @return A list of Sensor entities associated with the Device
     */
    @Override
    public List<Sensor> getSensorsByDeviceID(DeviceId id) {
        List<SensorDataModel> sensorDataModels = this._repositorySensorSpringData.findByDeviceId(id.toString());
        return SensorDataModel.toDomain(_factorySensor, sensorDataModels);
    }

    @Override
    public SensorID containsOfIdentitySI(SensorID id) {
        return null;
    }

}
