package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.SensorModelDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This interface extends JpaRepository and provides methods for managing SensorModelDataModel entities using Spring Data JPA.
 * It provides CRUD functionality for SensorModelDataModel entities and a method to find all SensorModelDataModel entities with a specific SensorTypeID.
 */
public interface IRepositorySensorModelSpringData extends JpaRepository<SensorModelDataModel, String>
{
    /**
     * Finds all SensorModelDataModel entities with the specified SensorTypeID and returns them as a list.
     * This is a query method provided by Spring Data JPA.
     *
     * @param sensorTypeID The SensorTypeID to search for.
     * @return A list of SensorModelDataModel entities with the specified SensorTypeID, otherwise an empty list.
     */
    List<SensorModelDataModel> findBySensorTypeID(String sensorTypeID);
}
