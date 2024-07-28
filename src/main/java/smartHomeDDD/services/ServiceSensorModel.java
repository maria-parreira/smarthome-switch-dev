package smartHomeDDD.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceSensorModel class provides services related to sensorModel objects.
 * It uses a FactorySensorModel to create sensorModel objects and IRepositorySensorModel to store and retrieve them.
 */
@Service
public class ServiceSensorModel {

    /**
     * Repository of sensor models.
     */
    final IRepositorySensorModel _repoSensorModel;

    /**
     * Factory of sensor models.
     */
    final FactorySensorModel _factorySensorModel;

    /**
     * Constructor for the ServiceSensorModel class.
     * @param repoSensorModel    Repository of sensor models. Must not be null.
     * @param factorySensorModel Factory of sensor models. Must not be null.
     * @throws IllegalArgumentException If the repository or factory is null.
     */
    public ServiceSensorModel(IRepositorySensorModel repoSensorModel, FactorySensorModel factorySensorModel) throws IllegalArgumentException {
        if (repoSensorModel == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        if (factorySensorModel == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        this._repoSensorModel = repoSensorModel;
        this._factorySensorModel = factorySensorModel;
    }

    /**
     * Creates a new sensorModel object and saves it in the repository.
     * @param sensorModelID The ID of the sensor model.
     * @param sensorTypeID  The ID of the sensor type.
     * @return The sensorModel object that was created and saved.
     */
    public SensorModel addSensorModel(SensorModelID sensorModelID, SensorTypeID sensorTypeID) {
        SensorModel sensorModel = _factorySensorModel.createSensorModel(sensorModelID, sensorTypeID);
        return _repoSensorModel.save(sensorModel);
    }

    /**
     * Get all sensor models for a given sensor type.
     * @param sensorTypeID Sensor type ID.
     * @return List of sensor models.
     */
    public List<SensorModel> getModelsBySensorType(SensorTypeID sensorTypeID) {
        return _repoSensorModel.getModelsBySensorType(sensorTypeID);
    }

    /**
     * Retrieve a sensor model by its unique identifier.
     * @param id The unique identifier of the sensor model.
     * @return The sensor model associated with the unique identifier.
     */
    public SensorModel getSensorModelByID(SensorModelID id) {
        Optional<SensorModel> sensorModel = _repoSensorModel.ofIdentity(id);
        if (sensorModel.isPresent()) {
            return sensorModel.get();
        } else
            throw new EntityNotFoundException("Sensor Model doesn't exist");
    }


}
