package smartHomeDDD.persistence.mem;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.valueobject.HouseId;

import java.util.*;

/**
 * Implementation of a repository interface for managing houses.
 */
//@Repository ("houseRepoMem")
public class RepositoryHouseMem implements IRepositoryHouse {

    /**
     * A map representing the data storage for houses in the repository.
     * This map associates each house identifier (key of type {@link HouseId}) with its corresponding house entity
     * (value of type {@link House}). It serves as the internal storage mechanism for houses within the repository.
     */
    private final Map<HouseId, House> DATA = new HashMap<>();

    /**
     * Saves the provided house entity.
     * @param entity The house entity to save.
     * @return The saved house entity.
     * @throws IllegalArgumentException if house is null.
     * @throws  DataIntegrityViolationException if house already exists.
     */
    @Override
    public House save(House entity) {
        if (entity == null) {
            throw new IllegalArgumentException("House cannot be null");
        }
        if (containsOfIdentity(entity.identity())) {
            throw new DataIntegrityViolationException("House already exists");
        }
        DATA.put(entity.identity(), entity );
        return entity;
    }

    /**
     * Update the provided house entity.
     * @param entity The house entity to save.
     * @return The saved house entity.
     */
    @Override
    public House update(House entity) {
        DATA.put(entity.identity(), entity );
        return entity;
    }

    /**
     * Retrieves all houses stored in the repository and returns them as a List.
     * @return A List of House objects representing all the houses stored in the repository.
     */
    @Override
    public List<House> findAll() {
        return new ArrayList<>(DATA.values());
    }
    /**
     * Retrieves the house associated with the provided identifier, if present.
     * @param id The identifier of the house to retrieve.
     * @return An Optional containing the house associated with the provided identifier, or empty if not found.
     */
    @Override
    public Optional<House> ofIdentity(HouseId id) {
        if( !containsOfIdentity(id) )
            return Optional.empty();
        else
            return Optional.of( DATA.get(id) );
    }

    /**
     * Checks if the repository contains a house associated with the provided identifier.
     * @param id The identifier to check.
     * @return true if the repository contains a house associated with the provided identifier, otherwise false.
     */
    @Override
    public boolean containsOfIdentity(HouseId id) {
        for (HouseId houseId : DATA.keySet()) {
            if (houseId.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
