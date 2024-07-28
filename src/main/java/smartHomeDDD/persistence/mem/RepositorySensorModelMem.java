package smartHomeDDD.persistence.mem;

import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;

import java.util.*;

/**
 * RepositorySensorModel is a class that implements the Repository interface for sensorModel objects.
 * It provides methods for storing sensorModel objects and retrieving them using their SensorModelID.
 * It also provides a method to generate a list of sensor model names from a configuration file.
 */

public class RepositorySensorModelMem implements IRepositorySensorModel {

    /**
     * A HashMap used for storing sensorModel objects. The SensorModelID of a sensorModel object is used as the key.
     */
    private final Map<SensorModelID, SensorModel> DATA = new HashMap<>();

    /**
     * Saves the provided sensorModel entity in the repository.
     * If a sensorModel with the same identity already exists in the repository, an IllegalArgumentException is thrown.
     * Otherwise, the sensorModel entity is stored in the repository and returned.
     *
     * @param entity the sensorModel entity to save.
     * @return the saved sensorModel entity.
     * @throws IllegalArgumentException if a sensorModel with the same identity already exists in the repository.
     */
    @Override
    public SensorModel save(SensorModel entity) {
        if (entity == null) {
            throw new IllegalArgumentException("sensorModel cannot be null");
        }
        if( containsOfIdentity(entity.identity()) ){
            throw new IllegalArgumentException("Sensor Model already exists");}
        DATA.put( entity.identity(), entity );
        return entity;
    }

    /**
     * Retrieves all sensorModel objects stored in the repository.
     *
     * @return An Iterable containing all sensorModel objects in the repository.
     */
    @Override
    public Iterable<SensorModel> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves a sensorModel object with the specified SensorModelID from the repository.
     *
     * @param id The SensorModelID of the sensorModel object to be retrieved.
     * @return An Optional containing the sensorModel object if it exists in the repository, or an empty Optional if it does not.
     */
    @Override
    public Optional<SensorModel> ofIdentity(SensorModelID id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Checks if a sensorModel object with the specified SensorModelID exists in the repository.
     *
     * @param id The SensorModelID of the sensorModel object to be checked.
     * @return true if a sensorModel object with the specified SensorModelID exists in the repository, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(SensorModelID id) {
        for (SensorModelID sensorModelID : DATA.keySet())
            if (sensorModelID.equals(id)) {
                return true;
            }
        return false;
    }
/**
     * Retrieves a list of sensorModel objects with the specified SensorTypeID from the repository.
     *
     * @param sensorTypeID The SensorTypeID of the sensorModel objects to be retrieved.
     * @return A List containing the sensorModel objects with the specified SensorTypeID.
     */
    @Override
    public List<SensorModel> getModelsBySensorType(SensorTypeID sensorTypeID) {
        List<SensorModel> sensorModels = new ArrayList<>();
        Iterable<SensorModel> allSensorModels = findAll();
        for (SensorModel sensorModel : allSensorModels) {
            if (sensorModel.getSensorTypeID().equals(sensorTypeID)) {
                sensorModels.add(sensorModel);
            }
        }
        return sensorModels;
    }
}