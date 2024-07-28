package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.DeviceDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing DeviceDataModel entities using Spring Data JPA.
 * This interface extends JpaRepository, providing CRUD functionality for DeviceDataModel entities.
 */
public interface IRepositoryDeviceSpringData extends JpaRepository<DeviceDataModel, String> {

    /**
     * Find a device by its room id
     * @param roomId the room id
     * @return the device
     */
    List<DeviceDataModel> findByRoomId(String roomId);

    /**
     * Find a device by its status
     * @param status the status
     * @return the device
     */
    List<DeviceDataModel> findDevicesByStatus(boolean status);

}
