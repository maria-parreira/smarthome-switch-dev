package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;

import java.util.Optional;

/**
 * The ServiceSensorType class provides services related to sensorType objects.
 * It uses a FactorySensorType to create sensorType objects and a RepositorySensorType to store and retrieve them.
 */
@Service
public class ServiceSensorType {
    /**
     * The RepositorySensorType used to store and retrieve sensorType objects.
     */
    final IRepositorySensorType _repoSensorType;

    /**
     * The FactorySensorType used to create sensorType objects.
     */
    final FactorySensorType _factorySensorType;

    /**
     * Constructs a new ServiceSensorType with the specified FactorySensorType and RepositorySensorType.
     * Throws an IllegalArgumentException if any of the parameters are null.
     * @param repoSensorType    the RepositorySensorType to be used by the service
     * @param factorySensorType the FactorySensorType to be used by the service
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public ServiceSensorType(IRepositorySensorType repoSensorType, FactorySensorType factorySensorType) {
        if (repoSensorType == null)
            throw new IllegalArgumentException("Repository cannot be null");
        if (factorySensorType == null)
            throw new IllegalArgumentException("Factory cannot be null");
        this._repoSensorType = repoSensorType;
        this._factorySensorType = factorySensorType;
    }

    /**
     * Creates a new sensorType object and saves it in the repository.
     * @param sensorTypeID the ID of the sensor type.
     * @param description  the description of the sensor type.
     * @param unit         the unit of the sensor type.
     * @return the sensorType object that was created and saved.
     */
    public SensorType createSensorType(SensorTypeID sensorTypeID, Description description, Unit unit) {
        SensorType sensorType = _factorySensorType.createSensorType(sensorTypeID, description, unit);
        return _repoSensorType.save(sensorType);
    }


    /**
     * List all sensor types.
     * @return List of sensor types.
     */
    public Iterable<SensorType> getSensorTypes() {
        return _repoSensorType.findAll();
    }

    /**
     * Retrieve a sensor type by its identifier.
     * @param sensorTypeID The identifier of the sensor type.
     * @return The sensor type.
     */
    public SensorType getSensorTypeById(SensorTypeID sensorTypeID) {
        Optional<SensorType> sensorType = _repoSensorType.ofIdentity(sensorTypeID);
        if (sensorType.isPresent()) {
            return sensorType.get();
        }
        throw new EntityNotFoundException("Sensor Type not found");
    }

}
