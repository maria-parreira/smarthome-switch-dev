package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceDevice;
import smartHomeDDD.services.ServiceHouse;
import smartHomeDDD.services.ServiceRoom;
import smartHomeDDD.services.ServiceSensorReading;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST Controller for managing houses.
 */

@RestController
@RequestMapping("/api/v1/houses")
public class HouseWebController {

    /**
     * Service for managing houses.
     */
    private final ServiceHouse _serviceHouse;

    /**
     * Service for managing rooms.
     */
    private final ServiceRoom _serviceRoom;

    /**
     * Service for handling device-related operations.
     */
    private final ServiceDevice _serviceDevice;

    /**
     * Service for handling sensor reading-related operations.
     */
    private final ServiceSensorReading _serviceSensorReading;

    /**
     * Constructs a new HouseControllerWeb instance.
     * @param serviceHouse The service for managing houses.
     */
    public HouseWebController(ServiceHouse serviceHouse, ServiceRoom _serviceRoom, ServiceDevice serviceDevice, ServiceSensorReading serviceSensorReading) {
        this._serviceHouse = serviceHouse;
        this._serviceRoom = _serviceRoom;
        this._serviceDevice = serviceDevice;
        this._serviceSensorReading = serviceSensorReading;
    }

    /**
     * Updates the location of a house.
     * @param houseId The ID of the house to update.
     * @param address The new address of the house.
     * @param country The country where the house is located.
     * @param zipCode The zip code of the house's location.
     * @param latitude The latitude of the house's location.
     * @param longitude The longitude of the house's location.
     * @return A ResponseEntity containing the updated HouseWebDTO and HTTP status.
     * @throws EntityNotFoundException if the house with the given ID is not found.
     */
    @PatchMapping("/{houseID}")
    public ResponseEntity<?> updateHouseLocation(
            @PathVariable ("houseID") String houseId,
            @RequestParam ("address") String address,
            @RequestParam ("country") String country,
            @RequestParam ("zipCode") String zipCode,
            @RequestParam ("latitude") double latitude,
            @RequestParam ("longitude") double longitude) {

        try {
            HouseId id = HouseMapper.DTOToHouseId(houseId);
            LocationDTO locationDTO = new LocationDTO(address, country, zipCode, latitude, longitude, houseId);
            Location newHouseLocation = HouseMapper.DTOToLocation(locationDTO);

            House updatedHouse = this._serviceHouse.configureLocation(newHouseLocation, id);

            // Convert the updated house to a HouseWebDTO
            HouseExitWebDTO updatedHouseDTO = HouseMapper.houseToExitWebDTO(updatedHouse);

            // Add a self link to the DTO
            Link selfLink = linkTo(HouseWebController.class).slash(updatedHouseDTO.getId()).withSelfRel();
            updatedHouseDTO.add(selfLink);

            // Return the updated house
            return new ResponseEntity<>(updatedHouseDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a list of all houses.
     * @return A ResponseEntity containing the list of HouseWebDTOs and HTTP status.
     */
    @GetMapping("")
    public ResponseEntity<List<HouseIDExitWebDTO>> getHouses() {

        Iterable<House> housesList = _serviceHouse.listOfHouses();

        List<HouseIDExitWebDTO> listHouseWebDTOs = new ArrayList<>();
        for (House house : housesList) {
            HouseIDExitWebDTO houseWebDTO = HouseMapper.houseToIDExitWebDTO(house);

            Link selfLink = linkTo(HouseWebController.class)
                    .slash(houseWebDTO.getId())
                    .withSelfRel();
            houseWebDTO.add(selfLink);

            listHouseWebDTOs.add(houseWebDTO);
        }
        return new ResponseEntity<>(listHouseWebDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves a house by ID.
     * @param id The ID of the house to retrieve.
     * @return A ResponseEntity containing the HouseWebDTO and HTTP status.
     */
    @GetMapping("/{houseID}")
    public ResponseEntity<?> getHouseByID(@PathVariable(value = "houseID") String id) {
        try {
            HouseId houseId = HouseMapper.DTOToHouseId(id);
            House house = _serviceHouse.getHouseById(houseId);
            HouseExitWebDTO houseWebDTO = HouseMapper.houseToExitWebDTO(house);

            Link rooms = linkTo(methodOn(HouseWebController.class).getRoomsByHouseID(id)).withRel("rooms");
            houseWebDTO.add(rooms);

            return new ResponseEntity<>(houseWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a list of rooms by house ID.
     * @param houseID The ID of the house to retrieve rooms from.
     * @return A ResponseEntity containing the list of RoomWebDTOs and HTTP status.
     */
    @GetMapping("/{houseID}/rooms")
    public ResponseEntity<?> getRoomsByHouseID(@PathVariable String houseID) {
        try {
            List<Room> rooms = _serviceRoom.getRoomsByHouseID(HouseMapper.DTOToHouseId(houseID));

            List<RoomIDExitWebDTO> listRoomWebDTOs = new ArrayList<>();
            for (Room room : rooms) {
                RoomIDExitWebDTO roomWebDTO = RoomMapper.domainToIDExitWebDTO(room);

                Link selfLink = linkTo(RoomControllerWeb.class).slash(roomWebDTO.getRoomId()).withSelfRel();
                roomWebDTO.add(selfLink);

                listRoomWebDTOs.add(roomWebDTO);
            }
            return new ResponseEntity<>(listRoomWebDTOs, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Retrieves the peak power consumption for a specific house within a given time period and interval.
     * @param id       the ID of the house whose peak power consumption is to be calculated
     * @param start    the start timestamp of the period in the format "yyyy-mm-dd hh:mm:ss"
     * @param end      the end timestamp of the period in the format "yyyy-mm-dd hh:mm:ss"
     * @param interval the interval in minutes over which the peak power consumption is to be calculated
     * @return a {@link ResponseEntity} containing a {@link PeakPowerConsumptionWebDTO} with the peak power consumption data,
     *         and an HTTP status code:
     *         - {@code 404 NOT FOUND} if any device not found
     *          - {@code 400 BAD REQUEST} if invalid method arguments
     * @throws DataIntegrityViolationException if there is a data integrity issue during the calculation
     * @throws ResponseStatusException         if the house ID is invalid or not found
     */
    @GetMapping("{houseID}/peak-power-consumption")
    public ResponseEntity<?> getHousePeakPowerConsumptionWithinPeriod(@PathVariable(value = "houseID") String id, @RequestParam String start, @RequestParam String end, @RequestParam String interval) {

        try {
            HouseId houseId = HouseMapper.DTOToHouseId(id);
            Timestamp startTime = Timestamp.valueOf(start);
            Timestamp endTime = Timestamp.valueOf(end);
            long intervalInMinutes = Long.parseLong(interval);
            // Retrieves the power grid meter device
            Device powerGridMeter = _serviceDevice.getPowerGridMeter();
            // Confirm that the power grid meter is in the provided house
            _serviceRoom.isPowerGridMeterInHouse(houseId, powerGridMeter.getRoomId());
            // calculate the peak
            double peakPowerConsumption = _serviceSensorReading.calculateHousePeakPowerConsumptionWithinPeriod(powerGridMeter, startTime, endTime, intervalInMinutes);
            // convert the result to dto
            PeakPowerConsumptionWebDTO calculatedPeakPowerConsumptionWebDTO = HouseMapper.housePeakPowerConsumptionDomainToWebDTO(peakPowerConsumption);
            return new ResponseEntity<>(calculatedPeakPowerConsumptionWebDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}