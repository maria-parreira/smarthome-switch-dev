package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.HouseDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface extends JpaRepository and provides methods for managing HouseDataModel entities using Spring Data JPA.
 * It provides CRUD functionality for HouseDataModel entities.
 */
public interface IRepositoryHouseSpringData extends JpaRepository<HouseDataModel, String>{


}
