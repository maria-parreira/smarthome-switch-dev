package smartHomeDDD.persistence.springdata;

import org.springframework.stereotype.Repository;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing ActuatorDataModel entities using Spring Data JPA.
 * This interface extends JpaRepository.
 */
@Repository
public interface IRepositoryActuatorSpringData extends JpaRepository<ActuatorDataModel, String> {

    /**
     * Finds all ActuatorDataModel entities by deviceId.
     *
     * @param deviceID The deviceID to search for
     * @return A list of ActuatorDataModel entities with the specified deviceID
     */
    List<ActuatorDataModel> findByDeviceId(String deviceID);
}