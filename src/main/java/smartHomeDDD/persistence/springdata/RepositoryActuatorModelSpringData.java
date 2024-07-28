package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorModelDataModel;

import java.util.List;
import java.util.Optional;
@Repository
public class RepositoryActuatorModelSpringData implements IRepositoryActuatorModel {
    /**
     * The Spring Data repository for ActuatorModelDataModel entities.
     */
    final IRepositoryActuatorModelSpringData _repositoryActuatorModelSpringData;

    /**
     * The factory for creating actuatorModel domain objects.
     */
    final FactoryActuatorModel _factoryActuatorModel;


    /**
     * Constructor to initialize RepositoryActuatorModelSpringData.
     *
     * @param factoryActuatorModel              The factory for creating actuatorModel objects
     * @param repositoryActuatorModelSpringData The Spring Data repository for actuatorModel entities
     */
    public RepositoryActuatorModelSpringData(FactoryActuatorModel factoryActuatorModel, IRepositoryActuatorModelSpringData repositoryActuatorModelSpringData) {
        this._factoryActuatorModel = factoryActuatorModel;
        this._repositoryActuatorModelSpringData = repositoryActuatorModelSpringData;
    }

    /**
     * Saves a actuatorModel entity.
     *
     * @param actuatorModel The actuatorModel entity to save
     * @return The saved actuatorModel entity
     * @throws IllegalArgumentException if the provided actuatorModel object is null
     */
    @Override
    public ActuatorModel save(ActuatorModel actuatorModel) {
        if (actuatorModel == null) {
            throw new IllegalArgumentException("actuatorModel cannot be null");
        }
        if (containsOfIdentity(actuatorModel.identity())) {
            throw new DataIntegrityViolationException("actuatorModel already exists");
        }

        ActuatorModelDataModel ActuatorModelDataModel = new ActuatorModelDataModel(actuatorModel);

        this._repositoryActuatorModelSpringData.save(ActuatorModelDataModel);

        return actuatorModel;
    }

    /**
     * Retrieves all actuatorModel entities.
     *
     * @return An Iterable containing all actuatorModel entities
     */
    @Override
    public Iterable<ActuatorModel> findAll() {
        List<ActuatorModelDataModel> listActuatorModelDataModelSaved = this._repositoryActuatorModelSpringData.findAll();

        return ActuatorModelDataModel.toDomain(_factoryActuatorModel, listActuatorModelDataModelSaved);
    }

    /**
     * Retrieves a actuatorModel entity by its identity.
     *
     * @param id The identity of the actuatorModel entity
     * @return An Optional containing the actuatorModel entity, or empty if not found
     */
    @Override
    public Optional<ActuatorModel> ofIdentity(ActuatorModelID id) {
        Optional<ActuatorModelDataModel> optActuatorModelDataModelSaved = this._repositoryActuatorModelSpringData.findById(id.toString());

        if (optActuatorModelDataModelSaved.isPresent()) {
            ActuatorModel ActuatorModelDomain = ActuatorModelDataModel.toDomain(_factoryActuatorModel, optActuatorModelDataModelSaved.get());
            return Optional.of(ActuatorModelDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a actuatorModel entity with the given identity exists.
     *
     * @param id The identity of the actuatorModel entity to check
     * @return true if the entity exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(ActuatorModelID id) {
        return _repositoryActuatorModelSpringData.existsById(id.toString());
    }

    /**
     * Retrieves all actuatorModel entities of a specific ActuatorType as a list.
     *
     * @param actuatorTypeID The ActuatorTypeID of the ActuatorType
     * @return A list of actuatorModel entities
     */
    @Override
    public List<ActuatorModel> getModelsByActuatorType(ActuatorTypeID actuatorTypeID) {
        List<ActuatorModelDataModel> actuatorModelDataModels = this._repositoryActuatorModelSpringData.findByActuatorTypeID(actuatorTypeID.toString());
        return ActuatorModelDataModel.toDomain(_factoryActuatorModel, actuatorModelDataModels);
    }

}
