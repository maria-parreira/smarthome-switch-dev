package smartHomeDDD.persistence.springdata;


import smartHomeDDD.persistence.jpa.datamodel.ActuatorTypeDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface extends JpaRepository and provides methods for managing ActuatorTypeDataModel entities using Spring Data JPA.
 * It provides CRUD functionality for ActuatorTypeDataModel entities.
 */
public interface IRepositoryActuatorTypeSpringData extends JpaRepository<ActuatorTypeDataModel, String> {
}
