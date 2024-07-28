package smartHomeDDD.persistence.jpa.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.persistence.jpa.datamodel.HouseDataModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of a JPA repository for managing House entities.
 */
public class RepositoryHouseJPAImpl implements IRepositoryHouse {

    /**
     * The factory for creating House instances.
     */
    final FactoryHouse _factoryHouse;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructs a RepositoryHouseJPA object with the specified FactoryHouse.
     * @param factoryHouse The factory for creating House instances.
     */
    public RepositoryHouseJPAImpl(FactoryHouse factoryHouse, EntityManager manager) {
        _factoryHouse = factoryHouse;
        _manager = manager;
    }

    /**
     * Retrieves the EntityManager for database operations.
     * @return The EntityManager instance.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Saves a House entity to the database.
     * @param house The House entity to save.
     * @return The saved House entity.
     * @throws IllegalArgumentException if house is null.
     * @throws  DataIntegrityViolationException if house already exists.
     */

    @Override
    public House save(House house) throws IllegalArgumentException {
        if (house == null) {
            throw new IllegalArgumentException("House cannot be null");
        }
        if(containsOfIdentity(house.identity())) {
            throw new DataIntegrityViolationException("House already exists");
        }

        HouseDataModel houseDataModel = new HouseDataModel(house); // classe que representa a entidade House de uma forma específica para persistência

        EntityManager em = getEntityManager(); // entity manager é criada faz gestão da entidade house

        EntityTransaction tx = em.getTransaction(); // a classe para transação é chamada atraves da entity manager

        tx.begin(); //Marca o início de um bloco de operações de persistência que serão tratadas como uma unidade.
        em.persist(houseDataModel); // adiciona ou atualiza a entrada correspondente no banco de dados
        tx.commit(); // confirmar a transação
        em.close();

        return house;
    }

    /**
     * Retrieves a House entity from the database based on its identity.
     * @param id The identity of the House entity to retrieve.
     * @return An Optional containing the retrieved House entity, or empty if not found.
     */
    @Override
    public Optional<House> ofIdentity(HouseId id) {
        HouseDataModel houseDataModel = getEntityManager().find(HouseDataModel.class, id.toString());

        if (houseDataModel != null) {
            House houseDomain = HouseDataModel.toDomain(_factoryHouse, houseDataModel);
            return Optional.of(houseDomain);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if a House entity with the specified identity exists in the database.
     * @param id The identity of the House entity to check.
     * @return True if a matching House entity exists, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(HouseId id) {
        Optional<House> optionalHouse = ofIdentity(id);
        return optionalHouse.isPresent();
    }

    /**
     * Retrieves all House entities from the database.
     * @return A List of House entities.
     */
    @Override
    public List<House> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM HouseDataModel e");

        List<HouseDataModel> listDataModel = query.getResultList();

        return (List<House>) HouseDataModel.toDomain(_factoryHouse, listDataModel);
    }

    /**
     * Updates the provided House entity in the repository.
     * @param entity The House entity to be updated.
     * @return The updated House entity.
     * @throws IllegalArgumentException if the provided house is null.
     */
    @Override
    public House update(House entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("House cannot be null");

        // The API is used to create, read, update and delete operations in a relational database.
        EntityManager em = getEntityManager(); // creates an EntityManager instance.

        EntityTransaction tx = em.getTransaction(); //starts a transaction - it ensures that the update is completed successfully.

        HouseDataModel houseDataModel = new HouseDataModel(entity);

        tx.begin(); //begins the transaction
        em.merge(houseDataModel); //updates the house entity in the database (used to merge the state of the given entity into the current persistent context)
        tx.commit(); //commits the transaction. if changes are successful, the changes are permanently saved in the database.

        em.close(); //closes the EntityManager instance (closing the connection to the database)

        return entity;
    }
}