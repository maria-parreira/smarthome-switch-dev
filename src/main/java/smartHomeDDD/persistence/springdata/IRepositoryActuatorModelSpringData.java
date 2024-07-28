package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.ActuatorModelDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing ActuatorModelDataModel entities using Spring Data JPA.
 * This interface extends JpaRepository, providing CRUD functionality for ActuatorModelDataModel entities.
 */


public interface IRepositoryActuatorModelSpringData extends JpaRepository<ActuatorModelDataModel, String> {

    /**
     * Finds all ActuatorModelDataModel entities with the specified ActuatorTypeID and returns them as a list, and it is a query method.
     *
     * @param actuatorTypeID The ActuatorTypeID to search for.
     * @return A list of ActuatorModelDataModel entities with the specified ActuatorTypeID, otherwise an empty list.
     */
    List<ActuatorModelDataModel> findByActuatorTypeID(String actuatorTypeID);
}


