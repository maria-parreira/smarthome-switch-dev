package smartHomeDDD.dto;

import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping Room domain objects to RoomDTO objects.
 */
public class RoomMapper {

    /**
     * Converts a RoomDTO object to a Room domain object.
     */
    public static RoomID DTOToRoomId(String roomId) {
        return new RoomID(roomId);
    }

    /**
     * Converts a RoomDTO object to a HouseId domain object.
     */
    public static FloorNumber DTOToFloorNumber(Integer floorNumber) {
        return new FloorNumber(floorNumber);
    }

    /**
     * Converts a RoomDTO object to a HouseId domain object.
     */
    public static Length DTOToLength(double length) {
        return new Length(length);
    }

    /**
     * Converts a RoomDTO object to a HouseId domain object.
     */
    public static Width DTOToWidth(double width) {
        return new Width(width);
    }

    /**
     * Converts a RoomDTO object to a HouseId domain object.
     */
    public static Height DTOToHeight(double height) {
        return new Height(height);
    }

    /**
     * Converts a RoomDTO object to a RoomName domain object.
     */
    public static RoomName DTOToRoomName(String roomName) {return new RoomName(roomName); }

    /**
     * Converts a RoomDTO object to a Dimensions domain object.
     */
    public static Dimensions DTOToDimensions(Length length, Width width, Height height) { return new Dimensions (length,width,height); }

    /**
     * Converts a RoomDTO object to a HouseId domain object.
     */
    public static RoomDTO roomToDTO(Room room) {
        return new RoomDTO(
                room.identity().toString(),
                Double.parseDouble(room.getDimensions().getLength().toString()),
                Double.parseDouble(room.getDimensions().getWidth().toString()),
                Double.parseDouble(room.getDimensions().getHeight().toString()),
                Integer.valueOf(room.getFloorNumber().toString()),
                room.getHouseId().toString(),
                room.isInside(),
                room.getRoomName().toString()
        );
    }

    /**
     * Converts a list of Room domain objects to a list of RoomDTO objects.
     */
    public static List<RoomDTO> roomsListToDTO(List<Room> rooms){
        List <RoomDTO> roomsDTO = new ArrayList<>();
        for (Room room : rooms){
            roomsDTO.add(roomToDTO(room));
        }
        return roomsDTO;
    }

    /**
     * This method is used to convert a Room domain object into a RoomWebDTO object.
     * It first retrieves the roomId and converts it into String.
     * Then, it creates a new RoomWebDTO object using this String value
     * @param room The Room domain object to be converted.
     * @return A RoomWebDTO object that represents the provided Room object.
     */
    public static RoomIDExitWebDTO domainToIDExitWebDTO(Room room) {
        String roomId = room.identity().toString();
        return new RoomIDExitWebDTO(roomId);
    }

    /**
     * This method is used to convert a Room domain object into a RoomExitWebDTO object.
     * It first retrieves the roomId, length, width, height, floorNumber, houseId, and isInside values from the Room object.
     * Then, it creates a new RoomExitWebDTO object using these values.
     * @param room The Room domain object to be converted.
     * @return A RoomExitWebDTO object that represents the provided Room object.
     */
    public static RoomExitWebDTO domainToExitWebDTO(Room room) {
        String roomId = room.identity().toString();
        double length = Double.parseDouble(room.getDimensions().getLength().toString());
        double width = Double.parseDouble(room.getDimensions().getWidth().toString());
        double height = Double.parseDouble(room.getDimensions().getHeight().toString());
        Integer floorNumber = Integer.valueOf(room.getFloorNumber().toString());
        String houseId = room.getHouseId().toString();
        boolean isInside = room.isInside();
        String roomName = room.getRoomName().toString();
        return new RoomExitWebDTO(roomId, length, width, height, floorNumber, houseId, isInside, roomName);
    }

}
