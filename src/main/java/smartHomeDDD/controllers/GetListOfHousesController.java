package smartHomeDDD.controllers;

import smartHomeDDD.domain.house.House;
import smartHomeDDD.dto.HouseDTO;
import smartHomeDDD.dto.HouseMapper;
import smartHomeDDD.services.ServiceHouse;

import java.util.List;

/**
 * Controller class that gives the list of houses.
 */
public class GetListOfHousesController {
    /**
     * Service of houses.
     */
    private final ServiceHouse _serviceHouse;

    /**
     * Constructor for the GetHousesController class.
     *
     * @param serviceHouse Service of houses.
     * @throws IllegalArgumentException If the service is null.
     */

    public GetListOfHousesController(ServiceHouse serviceHouse) throws IllegalArgumentException {
        if (serviceHouse == null) {
            throw new IllegalArgumentException("Service cannot be null.");
        }
        this._serviceHouse = serviceHouse;
    }

    /**
     * List all houses.
     * @return List of houses.
     */
    public List<HouseDTO> getHouses() {
        List<House> houses = _serviceHouse.listOfHouses();
        return HouseMapper.housesListToDTO(houses);
    }
}
