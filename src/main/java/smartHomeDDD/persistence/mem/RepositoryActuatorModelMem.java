package smartHomeDDD.persistence.mem;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.*;

/**
 * Repository class for managing ActuatorModels.
 */

public class RepositoryActuatorModelMem implements IRepositoryActuatorModel {
    /**
     * The map to store actuatorModel objects mapped by their ActuatorModelID.
     */
    private final Map<ActuatorModelID, ActuatorModel> DATA = new HashMap<>();

    /**
     * Reads actuator model names from a configuration file and returns them as an array.
     *
     * @param filePathname The file path to the configuration.
     * @return The array composed of actuator model names.
     * @throws ConfigurationException Thrown in case of an error with the configuration.
     */
    public String[] generateModelList(String filePathname) throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File(filePathname));
        return config.getStringArray("actuator");
    }

    /**
     * Saves the provided actuatorModel entity in the repository.
     *
     * @param entity the actuatorModel entity to save.
     * @return the saved actuatorModel entity.
     */
    @Override
    public ActuatorModel save(ActuatorModel entity) {
        if (entity == null) {
            throw new IllegalArgumentException("actuatorModel cannot be null");
        }
        if( containsOfIdentity(entity.identity()) ){
            throw new IllegalArgumentException("actuator already exists");
        }
        DATA.put(entity.identity(), entity);
        return entity;
    }


    /**
     * Retrieves all actuatorModel entities from the repository.
     *
     * @return an iterable containing all actuatorModel entities.
     */
    @Override
    public Iterable<ActuatorModel> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves the actuatorModel entity with the specified ActuatorModelID from the repository, if present.
     *
     * @param id the ActuatorModelID of the actuatorModel entity to retrieve.
     * @return an optional containing the actuatorModel entity, or an empty optional if not found.
     */
    @Override
    public Optional<ActuatorModel> ofIdentity(ActuatorModelID id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Checks if the repository contains an actuatorModel entity with the specified ActuatorModelID.
     *
     * @param id the ActuatorModelID to check.
     * @return true if the repository contains the actuatorModel entity, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(ActuatorModelID id) {
        for (ActuatorModelID actuatorModelID : DATA.keySet()) {
            if (actuatorModelID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of actuatorModel objects based on the provided ActuatorTypeID.
     *
     * @param actuatorTypeID The ActuatorTypeID to filter the ActuatorModels by.
     * @return A List containing actuatorModel objects matching the given ActuatorTypeID.
     */
    @Override
    public List<ActuatorModel> getModelsByActuatorType(ActuatorTypeID actuatorTypeID) {
        List<ActuatorModel> actuatorModels = new ArrayList<>();
        Iterable<ActuatorModel> allActuatorModels = findAll();
        for (ActuatorModel actuatorModel : allActuatorModels) {
            if (actuatorModel.getActuatorTypeID().equals(actuatorTypeID)) {
                actuatorModels.add(actuatorModel);
            }
        }
        return actuatorModels;
    }

}
