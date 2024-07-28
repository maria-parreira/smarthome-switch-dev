package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.valueobject.*;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceHouse class provides methods to manage house-related operations,
 * such as configuring house locations and verifying house identities.
 */
@Service
public class ServiceHouse {

    /**
     * The repository for houses.
     */
    private final IRepositoryHouse _repoHouse;

    /**
     * Factory for creating house instances.
     */
    private final FactoryHouse _factoryHouse;


    /**
     * Constructs a new ServiceHouse instance.
     *
     * @param repoHouse The repository for houses. Must not be null.
     * @throws IllegalArgumentException if repoHouse or factoryHouse is null.
     */
    public ServiceHouse(IRepositoryHouse repoHouse, FactoryHouse factoryHouse) {
        if (repoHouse == null)
            throw new IllegalArgumentException("House Repository cannot be null");
        if (factoryHouse == null)
            throw new IllegalArgumentException("House Factory cannot be null");
        _repoHouse = repoHouse;
        _factoryHouse = factoryHouse;
    }

    /**
     * Configures the location of a house identified by the provided ID.
     *
     * @param newHouseLocation The new location for the house.
     * @param id               The ID of the house to configure.
     * @return The updated house object.
     * @throws IllegalArgumentException if no house with the provided ID is found.
     */
    public House configureLocation(Location newHouseLocation, HouseId id) {
        Optional<House> house = this._repoHouse.ofIdentity(id); // Retrieve house from repo
        if (house.isPresent()) { // Check if house with id exists in repo
            House myHouse = house.get(); // Obtain the house
            myHouse.configureLocation(newHouseLocation); // Change the location of the house
            return this._repoHouse.update(myHouse); // if perform successful
        }
        throw new EntityNotFoundException("Can't find House"); // if unable to perform any action for some reason
    }

    /**
     * Retrieves a list of all houses from the repository.
     *
     * @return A list of House domain objects representing all the houses in the repository.
     */
    public List<House> listOfHouses() {
        return _repoHouse.findAll();
    }


    /**
     * Retrieves a house by its ID.
     *
     * @param houseId The ID of the house to retrieve.
     * @return The house with the provided ID if exists, else throws an EntityNotFoundException.
     */
    public House getHouseById(HouseId houseId) {
        Optional<House> house = _repoHouse.ofIdentity(houseId);
        if (house.isPresent()) {
            return house.get();
        }
        throw new EntityNotFoundException("House not found");
    }

    /**
     * This method is used to add a new house to the repository.
     *
     * @param id The unique identifier for the house to be added.
     * @param location The location details of the house to be added.
     * @return The house object that was created and added to the repository.
     */
    public House addHouse(HouseId id, Location location) {
        House house = this._factoryHouse.createHouse(id, location);
        return _repoHouse.save(house);
    }
}