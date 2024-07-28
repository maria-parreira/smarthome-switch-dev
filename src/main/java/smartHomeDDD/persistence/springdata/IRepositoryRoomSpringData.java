package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.RoomDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing RoomDataModel entities using Spring Data JPA.
 * This interface extends JpaRepository, providing CRUD functionality for RoomDataModel entities.
 */

public interface IRepositoryRoomSpringData extends JpaRepository<RoomDataModel, String> {
    /**
     * Finds all RoomDataModel entities by houseId.
     *
     * @param houseId The houseId to search for
     * @return A list of RoomDataModel entities with the specified houseId
     */
    List<RoomDataModel> findByHouseId(String houseId);

    /**
     * Finds all RoomDataModel entities by inside attribute set to false.
     *
     * @return A list of RoomDataModel entities with inside attribute set to false
     */
    List<RoomDataModel> findByisInsideFalse();
}
