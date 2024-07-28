package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the data model for the Room entity in the database.
 * It provides methods to convert between the Room domain object and the RoomDataModel.
 */
@Entity
@Table(name = "ROOM")
public class RoomDataModel {

    /**
     * The unique identifier for the room.
     */
    @Id
    private String roomId;
    /**
     * The identifier of the house that the room belongs to.
     */
    private String houseId;
    /**
     * The floor number where the room is located.
     */
    private Integer floorNumber;
    /**
     * The width of the room.
     */
    private double width;
    /**
     * The length of the room.
     */
    private double length;
    /**
     * The height of the room.
     */
    private double height;
    /**
     * Indicates whether the room is inside or not.
     */
    private boolean isInside;
    /**
     * The name of the room.
     */
    private String roomName;

    /**
     * Default constructor with no parameters.
     */
    public RoomDataModel() {}

     /**
     * Constructs a new RoomDataModel object with the specified Room.
     * @param room The Room object to be converted to a data model.
     */
    public RoomDataModel(Room room) {
        roomId = room.identity().toString();
        houseId = room.getHouseId().toString();
        floorNumber = room.getFloorNumber().getFloorNumber();
        width = room.getDimensions().getWidth().toDouble();
        length = room.getDimensions().getLength().toDouble();
        height = room.getDimensions().getHeight().toDouble();
        isInside = room.isInside();
        roomName = room.getRoomName().toString();
    }

    /**
     * Converts a RoomDataModel to a Room object.
     * @param factory The factory to create the Room object.
     * @param roomDataModel The RoomDataModel to be converted.
     * @return The Room object.
     */
    static public Room toDomain (FactoryRoom factory, RoomDataModel roomDataModel) {
        RoomID roomId = new RoomID(roomDataModel.roomId);
        HouseId houseId = new HouseId(roomDataModel.houseId);
        FloorNumber floorNumber = new FloorNumber(roomDataModel.floorNumber);
        Length length = new Length(roomDataModel.length);
        Width width = new Width(roomDataModel.width);
        Height height = new Height(roomDataModel.height);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = roomDataModel.isInside;
        RoomName roomName = new RoomName(roomDataModel.roomName);

        return factory.createRoom(houseId, roomId, floorNumber, dimensions, isInside, roomName);
    }

    /**
     * Converts a list of RoomDataModel to a list of Room objects.
     * @param factory The factory to create the Room object.
     * @param listDataModel The list of RoomDataModel to be converted.
     * @return The list of Room objects.
     */
    static public List<Room> toDomain (FactoryRoom factory, Iterable<RoomDataModel> listDataModel) {
        List<Room> listDomain = new ArrayList<>();

        for (RoomDataModel roomDataModel : listDataModel) {
            Room roomDomain = toDomain(factory, roomDataModel);
            listDomain.add(roomDomain);
        }
        return listDomain;
    }
}
