package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;

import java.util.List;

/**
 * The IRepositorySensorModel interface provides a contract for a repository that manages sensorModel objects.
 * It extends the generic Repository interface, specifying SensorModelID as the ID type and sensorModel as the entity type.
 */
public interface IRepositorySensorModel extends Repository<SensorModelID, SensorModel> {

    /**
     * Retrieves a list of sensorModel objects that are associated with a specific sensorType.
     * @param sensorTypeID The ID of the sensorType for which to retrieve the associated SensorModels.
     * @return A list of sensorModel objects associated with the specified sensorType.
     */
    List<SensorModel> getModelsBySensorType(SensorTypeID sensorTypeID);
}
