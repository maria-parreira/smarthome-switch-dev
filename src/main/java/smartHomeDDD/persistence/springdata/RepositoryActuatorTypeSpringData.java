

package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.repository.IRepositoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorTypeDataModel;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the IRepositoryActuatorType interface and provides the functionality to interact with the ActuatorType entities in the database using Spring Data.
 * It uses the FactoryActuatorType to create ActuatorType objects and IRepositoryActuatorTypeSpringData to perform CRUD operations on the ActuatorType entities.
 *
 * @see IRepositoryActuatorType
 * @see FactoryActuatorType
 * @see IRepositoryActuatorTypeSpringData
 */
@Repository
public class RepositoryActuatorTypeSpringData implements IRepositoryActuatorType {

    /**
     * The Spring Data repository for ActuatorType entities.
     */
    final IRepositoryActuatorTypeSpringData repositoryActuatorTypeSpringData;


    /**
     * The factory for creating ActuatorType objects.
     */
    final FactoryActuatorType factoryActuatorType;


    /**
     * Constructor to initialize RepositoryActuatorTypeSpringData.
     *
     * @param factoryActuatorType              The factory for creating ActuatorType objects.
     * @param repositoryActuatorTypeSpringData The Spring Data repository for ActuatorType entities.
     */
    public RepositoryActuatorTypeSpringData(FactoryActuatorType factoryActuatorType, IRepositoryActuatorTypeSpringData repositoryActuatorTypeSpringData) {
        this.factoryActuatorType = factoryActuatorType;
        this.repositoryActuatorTypeSpringData = repositoryActuatorTypeSpringData;
    }

    /**
     * Saves a ActuatorType entity.
     *
     * @param actuatorType The ActuatorType entity to save
     * @return The saved ActuatorType entity
     * @throws IllegalArgumentException if the provided ActuatorType object is null
     */
    @Override
    public ActuatorType save(ActuatorType actuatorType) {
        if (actuatorType == null) {
            throw new IllegalArgumentException("ActuatorType cannot be null");
        }
        if (containsOfIdentity(actuatorType.identity())) {
            throw new DataIntegrityViolationException("ActuatorType already exists");
        }

        ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);

        this.repositoryActuatorTypeSpringData.save(actuatorTypeDataModel);

        return actuatorType;
    }


    /**
     * Retrieves all ActuatorType entities.
     *
     * @return An Iterable containing all ActuatorType entities.
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        List<ActuatorTypeDataModel> listActuatorDataModelSaved = this.repositoryActuatorTypeSpringData.findAll();

        return ActuatorTypeDataModel.toDomain(factoryActuatorType, listActuatorDataModelSaved);
    }


    /**
     * Retrieves a ActuatorType entity by its identity.
     *
     * @param id The identity of the ActuatorType entity.
     * @return An Optional containing the ActuatorType entity, or empty if not found.
     */
    @Override
    public Optional<ActuatorType> ofIdentity(ActuatorTypeID id) {
        Optional<ActuatorTypeDataModel> optActuatorTypeDataModelSaved = this.repositoryActuatorTypeSpringData.findById(id.toString());

        if (optActuatorTypeDataModelSaved.isPresent()) {
            ActuatorType actuatorTypeDomain = ActuatorTypeDataModel.toDomain(factoryActuatorType, optActuatorTypeDataModelSaved.get());
            return Optional.of(actuatorTypeDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a ActuatorType entity with the given identity exists.
     *
     * @param id The identity of the ActuatorType entity to check.
     * @return true if the entity exists, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(ActuatorTypeID id) {
        return repositoryActuatorTypeSpringData.existsById(id.toString());
    }

}
