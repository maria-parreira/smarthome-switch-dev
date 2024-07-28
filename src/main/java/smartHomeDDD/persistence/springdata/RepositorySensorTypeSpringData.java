package smartHomeDDD.persistence.springdata;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.SensorTypeDataModel;
import java.util.List;
import java.util.Optional;

/**
 * RepositorySensorTypeSpringData is a Spring Data repository for SensorTypeDataModel entities.
 */
@Repository
public class RepositorySensorTypeSpringData implements IRepositorySensorType
{
    /**
     * The Spring Data repository for SensorTypeDataModel entities.
     */
    final IRepositorySensorTypeSpringData repositorySensorTypeSpringData;

    /**
     * The factory for creating sensorType domain objects.
     */
    final FactorySensorType factorySensorType;


    /**
     * Constructor to initialize RepositorySensorTypeSpringData.
     * @param _factorySensorType The factory for creating sensorType objects
     * @param _repositorySensorTypeSpringData The Spring Data repository for sensorType entities
     */
    public RepositorySensorTypeSpringData(FactorySensorType _factorySensorType, IRepositorySensorTypeSpringData _repositorySensorTypeSpringData)
    {
        factorySensorType = _factorySensorType;
        repositorySensorTypeSpringData = _repositorySensorTypeSpringData;
    }

    /**
     * Saves a sensorType entity.
     * @param sensorType The sensorType entity to save
     * @return The saved sensorType entity
     * @throws IllegalArgumentException if the provided sensorType object is null
     */
    @Override
    public SensorType save(SensorType sensorType)
    {
        if (sensorType == null) {
            throw new IllegalArgumentException("sensorType cannot be null");
        }
        if( containsOfIdentity(sensorType.identity()) ){
            throw new DataIntegrityViolationException("Sensor Type already exists");}

        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);

        this.repositorySensorTypeSpringData.save(sensorTypeDataModel);

        return sensorType;
    }

    /**
     * Retrieves all sensorType entities.
     * @return An Iterable containing all sensorType entities
     */
    @Override
    public Iterable<SensorType> findAll()
    {
        List<SensorTypeDataModel> lstSensorTypeDataModelSaved = this.repositorySensorTypeSpringData.findAll();

        return SensorTypeDataModel.toDomain(factorySensorType, lstSensorTypeDataModelSaved);
    }

    /**
     * Retrieves a sensorType entity by its identity.
     * @param id The identity of the sensorType entity
     * @return An Optional containing the sensorType entity, or empty if not found
     */
    @Override
    public Optional<SensorType> ofIdentity(SensorTypeID id)
    {
        Optional<SensorTypeDataModel> optSensorTypeDataModelSaved = this.repositorySensorTypeSpringData.findById(id.toString());

        if(optSensorTypeDataModelSaved.isPresent())
        {
            SensorType sensorTypeDomain = SensorTypeDataModel.toDomain(factorySensorType, optSensorTypeDataModelSaved.get());
            return Optional.of(sensorTypeDomain);
        }
        else
            return Optional.empty();
    }


    /**
     * Checks if a sensorType entity with the given identity exists.
     * @param id The identity of the sensorType entity to check
     * @return true if the entity exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(SensorTypeID id)
    {
        return repositorySensorTypeSpringData.existsById(id.toString());
    }



}
