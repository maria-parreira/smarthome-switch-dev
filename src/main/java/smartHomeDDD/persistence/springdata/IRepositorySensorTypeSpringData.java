package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.SensorTypeDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface extends JpaRepository and provides methods for managing SensorTypeDataModel entities using Spring Data JPA.
 * It provides CRUD functionality for SensorTypeDataModel entities.
 */
public interface IRepositorySensorTypeSpringData extends JpaRepository<SensorTypeDataModel, String>
{

}
