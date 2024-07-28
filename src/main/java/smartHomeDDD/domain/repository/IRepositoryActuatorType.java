package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;

/**
 * The IRepositoryActuatorType interface provides a contract for a repository that manages ActuatorType objects.
 * It extends the generic Repository interface, specifying ActuatorTypeID as the ID type and ActuatorType as the entity type.
 */

public interface IRepositoryActuatorType extends Repository<ActuatorTypeID, ActuatorType> {

}