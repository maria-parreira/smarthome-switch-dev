package smartHomeDDD.persistence.mem;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.repository.IRepositoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * RepositoryActuatorType is a class that implements the Repository interface for ActuatorType objects.
 * It provides methods for storing ActuatorType objects and retrieving them using their ActuatorTypeID.
 */
public class RepositoryActuatorTypeMem implements IRepositoryActuatorType
{

    /** The map to store ActuatorType objects mapped by their ActuatorTypeID. */
    private final Map<ActuatorTypeID, ActuatorType> DATA = new HashMap<>();

    /**
     * Saves the provided ActuatorType entity in the repository.
     *
     * @param entity the ActuatorType entity to save.
     * @return the saved ActuatorType entity.
     */
    @Override
    public ActuatorType save(ActuatorType entity) {
        if (entity == null) {
            throw new IllegalArgumentException("ActuatorType cannot be null");
        }
        if( containsOfIdentity(entity.identity()) ){
            throw new IllegalArgumentException("actuator already exists");
        }
        DATA.put( entity.identity(), entity );
        return entity;
    }


    /**
     * Retrieves all ActuatorType entities from the repository.
     *
     * @return an iterable containing all ActuatorType entities.
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        return  DATA.values();
    }

    /**
     * Retrieves the ActuatorType entity with the specified ActuatorTypeID from the repository, if present.
     *
     * @param id the ActuatorTypeID of the ActuatorType entity to retrieve.
     * @return an optional containing the ActuatorType entity, or an empty optional if not found.
     */
    @Override
    public Optional<ActuatorType> ofIdentity(ActuatorTypeID id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else {
            ActuatorTypeID repositoryId = findRepositoryId(id);
            return Optional.of(DATA.get(repositoryId));
        }
    }

    /**
     * Retrieves the ActuatorType entity with the specified ActuatorTypeID from the repository, if present.
     * @param id the ActuatorTypeID of the ActuatorType entity to retrieve.
     * @return an ActuatorType entity, or null if not found.
     */
    public ActuatorTypeID findRepositoryId(ActuatorTypeID id) {
        for (ActuatorTypeID repositoryId : DATA.keySet()) {
            if (id.equals(repositoryId)) { // Compare content of IDs
                return repositoryId;
            }
        }
        return null; // Or throw an exception if the ID is not found
    }

    /**
     * Checks if the repository contains an ActuatorType entity with the specified ActuatorTypeID.
     *
     * @param id the ActuatorTypeID to check.
     * @return true if the repository contains the ActuatorType entity, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(ActuatorTypeID id) {
        for (ActuatorTypeID actuatorTypeID : DATA.keySet()) {
            if (actuatorTypeID.equals(id)) {
                return true;
            }
        }
        return false;
    }

}
