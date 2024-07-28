package smartHomeDDD.persistence.springdata;

import org.springframework.stereotype.Repository;
import smartHomeDDD.persistence.jpa.datamodel.SensorDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing SensorDataModel entities using Spring Data JPA.
 * This interface extends JpaRepository, providing CRUD functionality for SensorDataModel entities.
 */
@Repository
public interface IRepositorySensorSpringData extends JpaRepository<SensorDataModel, String>
{
    /**
     * Finds all SensorDataModel entities by deviceId.
     *
     * @param deviceID The deviceID to search for
     * @return A list of SensorDataModel entities with the specified deviceID
     */
    List<SensorDataModel> findByDeviceId(String deviceID);
}
