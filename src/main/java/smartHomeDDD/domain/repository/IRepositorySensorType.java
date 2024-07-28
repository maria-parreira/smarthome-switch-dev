package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.SensorTypeID;

/**
 * The IRepositorySensorType interface provides a contract for a repository that manages sensorType objects.
 * It extends the generic Repository interface, specifying SensorTypeID as the ID type and sensorType as the entity type.
 */
public interface IRepositorySensorType extends Repository<SensorTypeID, SensorType> {

}
