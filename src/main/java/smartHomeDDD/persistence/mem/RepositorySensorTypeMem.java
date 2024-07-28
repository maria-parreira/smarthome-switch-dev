package smartHomeDDD.persistence.mem;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.valueobject.SensorTypeID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * RepositorySensorTypeMem is a class that implements the IRepositorySensorType interface for sensorType objects.
 * It provides methods for storing sensorType objects and retrieving them using their SensorTypeID.
 */
public class RepositorySensorTypeMem implements IRepositorySensorType {

    /**
     * A HashMap used for storing sensorType objects. The SensorTypeID of a sensorType object is used as the key.
     */
    private final Map<SensorTypeID, SensorType> DATA = new HashMap<>();

    /**
     * Saves the provided sensorType entity in the repository.
     * If a sensorType with the same identity already exists in the repository, an IllegalArgumentException is thrown.
     * Otherwise, the sensorType entity is stored in the repository and returned.
     *
     * @param entity the sensorType entity to save.
     * @return the saved sensorType entity.
     * @throws IllegalArgumentException if a sensorType with the same identity already exists in the repository.
     */
    @Override
    public SensorType save(SensorType entity) {
        if (entity == null) {
            throw new IllegalArgumentException("sensorType cannot be null");
        }
        if( containsOfIdentity(entity.identity()) ){
            throw new DataIntegrityViolationException("Sensor Type already exists");
        }
        DATA.put( entity.identity(), entity );
        return entity;
    }

    /**
     * Retrieves all sensorType objects stored in the repository.
     *
     * @return An Iterable containing all sensorType objects in the repository.
     */
    @Override
    public Iterable<SensorType> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves a sensorType object with the specified SensorTypeID from the repository.
     *
     * @param id The SensorTypeID of the sensorType object to be retrieved.
     * @return An Optional containing the sensorType object if it exists in the repository, or an empty Optional if it does not.
     */
    @Override
    public Optional<SensorType> ofIdentity(SensorTypeID id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Checks if a sensorType object with the specified SensorTypeID exists in the repository.
     *
     * @param id The SensorTypeID of the sensorType object to be checked.
     * @return true if a sensorType object with the specified SensorTypeID exists in the repository, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(SensorTypeID id) {
        for (SensorTypeID sensorTypeID : DATA.keySet()) {
            if (sensorTypeID.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
