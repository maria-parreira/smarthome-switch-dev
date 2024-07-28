package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceDevice;
import smartHomeDDD.services.ServiceRoom;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The RoomControllerWeb class provides web services related to Room objects.
 */
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomControllerWeb {

    /**
     * The service for rooms.
     */
    private final ServiceRoom serviceRoom;

    /**
     * The service for devices.
     */
    private final ServiceDevice serviceDevice;

    /**
     * Constructor for the RoomControllerWeb class.
     * @param serviceRoom   The service for rooms.
     * @param serviceDevice The service for devices.
     */
    public RoomControllerWeb(ServiceRoom serviceRoom, ServiceDevice serviceDevice) {
        this.serviceRoom = serviceRoom;
        this.serviceDevice = serviceDevice;
    }

    /**
     * This method is a POST request that adds a new room to a house.
     * @param roomWebDTO A RoomDTO object that contains the details of the room to be added. This is obtained from the request body.
     * @return A ResponseEntity containing a RoomWebDTO object representing the added room,
     * and an HTTP status code. If the room is successfully added, the status code is HttpStatus.CREATED (201).
     * If the room already exists, the status code is HttpStatus.CONFLICT (409).
     * If the house does not exist, the status code is HttpStatus.BAD_REQUEST (400).
     * @throws DataIntegrityViolationException if the room already exists.
     * @throws EntityNotFoundException         if the house does not exist.
     */
    @PostMapping("")
    public ResponseEntity<?> addRoom(@RequestBody RoomEntryWebDTO roomWebDTO) {
        try {
            HouseId houseId = HouseMapper.DTOToHouseId(roomWebDTO.getHouseId());
            FloorNumber floorNumber = RoomMapper.DTOToFloorNumber(roomWebDTO.getFloorNumber());
            Length length = RoomMapper.DTOToLength(roomWebDTO.getLength());
            Width width = RoomMapper.DTOToWidth(roomWebDTO.getWidth());
            Height height = RoomMapper.DTOToHeight(roomWebDTO.getHeight());
            Dimensions dimensions = RoomMapper.DTOToDimensions(length, width, height);
            boolean isInside = roomWebDTO.isInside();
            RoomName roomName = RoomMapper.DTOToRoomName(roomWebDTO.getRoomName());

            Room addedRoom = serviceRoom.addRoomToHouse(houseId, floorNumber, dimensions, isInside, roomName);

            RoomExitWebDTO addedRoomDTO = RoomMapper.domainToExitWebDTO(addedRoom);

            Link selfLink = linkTo(RoomControllerWeb.class).slash(addedRoomDTO.getRoomId()).withSelfRel();
            addedRoomDTO.add(selfLink);

            return new ResponseEntity<>(addedRoomDTO, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * This method is a GET request that retrieves a room by its ID.
     * It maps to the "/{roomID}" endpoint, where {roomID} is a path variable representing the ID of the room.
     * @param id The ID of the room to retrieve. This is a path variable.
     * @return A ResponseEntity containing a RoomWebDTO object representing the room,
     * and an HTTP status code. If the room exists, the status code is HttpStatus.OK (200).
     * If the room does not exist, the status code is HttpStatus.NOT_FOUND (404).
     * @throws EntityNotFoundException if the room with the provided ID does not exist.
     */
    @GetMapping("/{roomID}")
    public ResponseEntity<?> getRoom(@PathVariable(value = "roomID") String id) {
        try {
            RoomID roomID = RoomMapper.DTOToRoomId(id);
            Room room = serviceRoom.getRoomById(roomID);

            RoomExitWebDTO roomWebDTO = RoomMapper.domainToExitWebDTO(room);

            Link devices = linkTo(methodOn(RoomControllerWeb.class).getDevicesByRoom(id)).withRel("devices");
            roomWebDTO.add(devices);

            return new ResponseEntity<>(roomWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is a GET request that retrieves a list of devices in a specific room.
     * It maps to the "/{roomID}/devices" endpoint, where {roomID} is a path variable representing the ID of the room.
     * @param id The ID of the room for which to retrieve the devices. This is a path variable.
     * @return A ResponseEntity containing a list of DeviceWebDTO objects representing the devices in the room,
     * and an HTTP status code. If the room exists and has devices, the status code is HttpStatus.OK (200).
     * If the room does not exist, the status code is HttpStatus.NOT_FOUND (404).
     * @throws EntityNotFoundException if the room with the provided ID does not exist.
     */
    @GetMapping("/{roomID}/devices")
    public ResponseEntity<?> getDevicesByRoom(@PathVariable(value = "roomID") String id) {
        try {
            RoomID roomID = RoomMapper.DTOToRoomId(id);
            List<Device> rooms = serviceDevice.listDevicesInRoom(roomID);

            List<DeviceIDExitWebDTO> listDeviceWebDTOs = new ArrayList<>();

            for (Device device : rooms) {
                DeviceIDExitWebDTO deviceWebDTO = DeviceMapper.domainToIDExitWebDTO(device);

                Link selfLink = linkTo(DeviceControllerWeb.class).slash(deviceWebDTO.getId()).withSelfRel();
                deviceWebDTO.add(selfLink);

                listDeviceWebDTOs.add(deviceWebDTO);
            }
            return new ResponseEntity<>(listDeviceWebDTOs, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}