package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;

import java.util.List;

/**
 * The IRepositoryActuatorModel interface provides a contract for a repository that manages actuatorModel objects.
 * It extends the generic Repository interface, specifying ActuatorModelID as the ID type and actuatorModel as the entity type.
 */
public interface IRepositoryActuatorModel extends Repository<ActuatorModelID, ActuatorModel> {

    /**
     * Retrieves a list of actuatorModel objects that are associated with a specific ActuatorType.
     *
     * @param actuatorTypeID The ID of the ActuatorType for which to retrieve the associated ActuatorModels.
     * @return A list of actuatorModel objects associated with the specified ActuatorType.
     */
    List<ActuatorModel> getModelsByActuatorType(ActuatorTypeID actuatorTypeID);

}
