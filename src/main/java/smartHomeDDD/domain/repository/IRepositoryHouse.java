package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.valueobject.HouseId;

import java.util.List;

/**
 * The IRepositoryHouse interface extends the generic Repository interface with HouseId as the ID type and House as the entity type.
 * It provides an additional method for updating a House entity in the repository.
 */
public interface IRepositoryHouse extends Repository<HouseId, House> {

  /**
   * Updates the provided House entity in the repository.
   * @param entity The House entity to be updated.
   * @return The updated House entity.
   */
  House update(House entity);

  /**
   * Retrieves all houses stored in the repository.
   * @return A List containing all the House domain objects stored in the repository.
   */
  List <House> findAll();
}
