package smartHomeDDD.controllers;

import smartHomeDDD.dto.HouseDTO;
import smartHomeDDD.dto.HouseMapper;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.Location;
import smartHomeDDD.dto.LocationDTO;
import smartHomeDDD.services.ServiceHouse;


/**
 * The controller class for configuring location information for house.
 */
public class ConfigureHouseLocationController {

    /**
     * The service for managing house-related operations.
     */
    private final ServiceHouse _service;

    /**
     * Constructs a new ConfigureHouseLocationController with the provided house service.
     *
     * @param houseService The service for managing house-related operations. It cannot be null.
     * @throws IllegalArgumentException if the provided houseService is null.
     */
    public ConfigureHouseLocationController(ServiceHouse houseService){
        if (houseService == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._service = houseService;
    }

    /**
     * Configures the location for a house based on the provided location information.
     * If the house exists, its location will be updated with the new information.
     *
     * @param locationDto The location information to configure.
     * @return A HouseDTO representing the houseId with new location.
     */

    public HouseDTO configureHouseLocation(LocationDTO locationDto) {

        //gets house Id object
        String houseId = locationDto.getHouseId();
        HouseId id = HouseMapper.DTOToHouseId(houseId);

        //get a location object
        Location newHouseLocation = HouseMapper.DTOToLocation(locationDto);

        // change house location and update in repo return houseDTO
        return HouseMapper.houseToDTO(this._service.configureLocation(newHouseLocation,id));
    }

}