package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.SensorModelDataModel;

import java.util.List;
import java.util.Optional;

@Repository
public class RepositorySensorModelSpringData implements IRepositorySensorModel {

    /**
     * The Spring Data repository for SensorModelDataModel entities.
     */
    final IRepositorySensorModelSpringData _repositorySensorModelSpringData;

    /**
     * The factory for creating sensorModel domain objects.
     */
    final FactorySensorModel _factorySensorModel;

    /**
     * Constructor to initialize RepositorySensorModelSpringData.
     *
     * @param factorySensorModel              The factory for creating sensorModel objects
     * @param repositorySensorModelSpringData The Spring Data repository for sensorModel entities
     */
    public RepositorySensorModelSpringData(FactorySensorModel factorySensorModel, IRepositorySensorModelSpringData repositorySensorModelSpringData) {
        this._factorySensorModel = factorySensorModel;
        this._repositorySensorModelSpringData = repositorySensorModelSpringData;
    }

    /**
     * Saves a sensorModel entity.
     *
     * @param sensorModel The sensorModel entity to save
     * @return The saved sensorModel entity
     * @throws IllegalArgumentException if the provided sensorModel object is null
     */
    @Override
    public SensorModel save(SensorModel sensorModel) {
        if (sensorModel == null) {
            throw new IllegalArgumentException("sensorModel cannot be null");
        }

        if( containsOfIdentity(sensorModel.identity()) ){
            throw new DataIntegrityViolationException("sensorModel already exists");
        }
        SensorModelDataModel sensorModelDataModel = new SensorModelDataModel(sensorModel);

        this._repositorySensorModelSpringData.save(sensorModelDataModel);

        return sensorModel;
    }

    /**
     * Retrieves all sensorModel entities.
     *
     * @return An Iterable containing all sensorModel entities
     */
    @Override
    public Iterable<SensorModel> findAll() {
        List<SensorModelDataModel> listSensorModelDataModelSaved = this._repositorySensorModelSpringData.findAll();

        return SensorModelDataModel.toDomain(_factorySensorModel, listSensorModelDataModelSaved);
    }

    /**
     * Retrieves a sensorModel entity by its identity.
     *
     * @param id The identity of the sensorModel entity
     * @return An Optional containing the sensorModel entity, or empty if not found
     */
    @Override
    public Optional<SensorModel> ofIdentity(SensorModelID id) {
        Optional<SensorModelDataModel> optSensorModelDataModelSaved = this._repositorySensorModelSpringData.findById(id.toString());

        if (optSensorModelDataModelSaved.isPresent()) {
            SensorModel SensorModelDomain = SensorModelDataModel.toDomain(_factorySensorModel, optSensorModelDataModelSaved.get());
            return Optional.of(SensorModelDomain);
        } else
            return Optional.empty();
    }


    /**
     * Checks if a sensorModel entity with the given identity exists.
     *
     * @param id The identity of the sensorModel entity to check
     * @return true if the entity exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(SensorModelID id) {
        return _repositorySensorModelSpringData.existsById(id.toString());
    }

    /**
     * Retrieves all sensorModel entities of a specific ActuatorType.
     * @param sensorTypeID The SensorTypeID of the sensorType.
     * @return A List containing all sensorModel entities of the specified sensorType.
     */
    @Override
    public List<SensorModel> getModelsBySensorType(SensorTypeID sensorTypeID) {
        List<SensorModelDataModel> sensorModelDataModels = this._repositorySensorModelSpringData.findBySensorTypeID(sensorTypeID.toString());
        return SensorModelDataModel.toDomain(_factorySensorModel, sensorModelDataModels);
    }

}
