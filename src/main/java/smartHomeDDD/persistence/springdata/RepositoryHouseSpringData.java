package smartHomeDDD.persistence.springdata;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.persistence.jpa.datamodel.HouseDataModel;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the IRepositoryHouse interface using Spring Data.
 */
@Repository
public class RepositoryHouseSpringData implements IRepositoryHouse {


    /**
     * The Spring Data repository for House entities.
     */
    final IRepositoryHouseSpringData _repositoryHouseSpringData;
    /**
     * The factory for creating House domain objects.
     */
    final FactoryHouse _factoryHouse;


    /**
     * Constructs a RepositoryHouseSpringData instance.
     * @param factoryHouse FactoryHouse instance for creating houses.
     * @param repositoryHouse IRepositoryHouseSpringData instance for repository operations.
     */
    public RepositoryHouseSpringData(FactoryHouse factoryHouse, IRepositoryHouseSpringData repositoryHouse) {
        _factoryHouse = factoryHouse;
        _repositoryHouseSpringData = repositoryHouse;
    }

    /**
     * Saves a House entity.
     * @param house The House domain object to be saved.
     * @return The saved House domain object.
     * @throws IllegalArgumentException if house is null.
     * @throws  DataIntegrityViolationException if house already exists.
     */
    @Override
    public House save(House house)
    {
        if (house == null) {
            throw new IllegalArgumentException("House cannot be null");
        }
        if (containsOfIdentity(house.identity())) {
            throw new DataIntegrityViolationException("House already exists");
        }

        HouseDataModel houseDataModel = new HouseDataModel(house);
        HouseDataModel houseDataModelSaved = this._repositoryHouseSpringData.save(houseDataModel);

        // Convert the saved data model back to the domain object
        return HouseDataModel.toDomain(_factoryHouse, houseDataModelSaved);
    }

    /**
     * Update a house in the database
     * @param entity the house to update
     * @return the updated house
     */
    @Override
    public House update(House entity) {

        String houseID = entity.identity().toString();

        Optional<HouseDataModel> houseDataModel = this._repositoryHouseSpringData.findById(houseID);

        if (houseDataModel.isPresent()) {
            boolean isUpdated = houseDataModel.get().updateFromDomain(entity);

            if (isUpdated) {
                HouseDataModel houseDataModelSaved = this._repositoryHouseSpringData.save(houseDataModel.get());

                return HouseDataModel.toDomain(_factoryHouse,houseDataModelSaved);

            } else
                throw new IllegalArgumentException("House cannot be updated");
        } else
            throw new IllegalArgumentException("House cannot found");
    }

    /**
     * Retrieves a House entity by its identity.
     * @param id The identity of the House entity.
     * @return An Optional containing the House domain object if found, or an empty Optional if not found.
     */
    @Override
    public Optional<House> ofIdentity(HouseId id) {
        Optional<HouseDataModel> houseDataModelSaved = this._repositoryHouseSpringData.findById(id.toString());

        if(houseDataModelSaved.isPresent())
        {
            House houseDomain = HouseDataModel.toDomain(_factoryHouse, houseDataModelSaved.get());
            return Optional.of(houseDomain);
        }
        else
            return Optional.empty();
    }

    /**
     * Checks if a House entity exists by its identity.
     * @param id The identity of the House entity.
     * @return True if the House entity exists, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(HouseId id) {
        return _repositoryHouseSpringData.existsById(id.toString());
    }

    /**
     * Retrieves all House entities.
     * @return A List containing all House domain objects.
     */
    @Override
    public List<House> findAll() {
        List <HouseDataModel> houseDataModelList = this._repositoryHouseSpringData.findAll();
        return (List<House>) HouseDataModel.toDomain(_factoryHouse, houseDataModelList);
    }

}