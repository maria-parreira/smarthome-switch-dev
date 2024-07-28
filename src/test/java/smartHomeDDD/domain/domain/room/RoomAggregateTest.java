package smartHomeDDD.domain.domain.room;

import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test Class for the  Room Aggregate to evaluate the functionality, behaviour and cohesion of the Room Aggregate.
 * This class tests the conditions for the following scenarios;
 * Test to ensure that the Room constructor creates a room when valid arguments are provided.
 * Test to ensure the identity method returns the room ID.
 * Test to ensure that the sameAs method returns true when being compared to itself.
 * Test to ensure that the sameAs method returns true when comparing two equal rooms.
 * Test to ensure that the equals method returns false when comparing two different rooms.
 * Test to ensure that the equals method returns false when comparing a room with a different object.
 * Test to ensure that the sameAs method returns true when comparing two rooms that are equal.
 * Test to ensure that the sameAs method returns true when comparing two equal rooms with different ids.
 * Test to ensure that the equals method returns true when comparing two equal rooms with different house IDs.
 * Test to ensure that the equals method returns true when comparing two equal rooms with different floor numbers.
 * Test to ensure that the equals method returns true when comparing two equal rooms with different dimensions.
 * Test to ensure that the equals method returns false when comparing two rooms with different ids.
 * Test to ensure that the sameAs method returns false when comparing two rooms with different house IDs.
 * Test to ensure that the sameAs method returns false when comparing two rooms with different floor numbers.
 * Test to ensure that the sameAs method returns false when comparing two rooms with different dimensions.
 */

class RoomAggregateTest {
    /**
     * Test to ensure that the Room constructor creates a room when valid arguments are provided.
     */
    @Test
    void validArguments_shouldCreateARoom() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");

        // Act
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);

        // Assert
        assertNotNull(room);
        assertEquals(room.identity(), roomID);
        assertEquals(room.getHouseId(), houseIdDouble);
        assertEquals(room.getFloorNumber(), floorNumber);
        assertEquals(room.getDimensions(), dimensions);
    }

    /**
     * Test to ensure the identity method returns the room ID.
     */
    @Test
    void shouldReturnRoomId_WhenGettingIdentity() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);

        // Act
        RoomID roomId = room.identity();

        // Assert
        assertEquals(roomID, roomId);
    }

    /**
     * Test to ensure that the sameAs method returns true when being compared to itself.
     */
    @Test
    void shouldReturnTrue_WhenComparingItself() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);

        // Act
        boolean isEquals = room.sameAs(room);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the sameAs method returns true when comparing two equal rooms.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualRooms() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);

        // Act
        boolean isEquals = room.sameAs(room2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns false when comparing two different rooms.
     */
    @Test
    void shouldReturnFalse_WhenComparingTwoDifferentRooms() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID1 = new RoomID("1");
        RoomID roomID2 = new RoomID("2");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID1, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID2, floorNumber, dimensions, isInside, roomName);

        // Act
        boolean isEquals = room.equals(room2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing a room with a different object.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomWithDifferentObject() {
        // Arrange
        RoomID roomID = new RoomID("1");
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);
        Object obj = new Object();

        // Act
        boolean isEquals = room.sameAs(obj);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the equals method returns true when comparing two equal rooms with different house IDs.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualRoomsWithDifferentHouseIds() {
        // Arrange
        HouseId houseId1Double = mock(HouseId.class);
        HouseId houseId2Double = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseId1Double, roomID, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseId2Double, roomID, floorNumber, dimensions, isInside, roomName);

        // Act
        boolean isEquals = room.equals(room2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns true when comparing two equal rooms with different floor numbers.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualRoomsWithDifferentFloorNumbers() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber1 = new FloorNumber(3);
        FloorNumber floorNumber2 = new FloorNumber(4);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber1, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber2, dimensions, isInside, roomName);

        // Act
        boolean isEquals = room.equals(room2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns true when comparing two equal rooms with different dimensions.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualRoomsWithDifferentDimensions() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length1 = new Length(10.5);
        Width width1 = new Width(10.5);
        Height height1 = new Height(10.5);
        Length length2 = new Length(20.5);
        Width width2 = new Width(10.5);
        Height height2 = new Height(10.5);
        Dimensions dimensions1 = new Dimensions(length1, width1, height1);
        Dimensions dimensions2 = new Dimensions(length2, width2, height2);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions1, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber, dimensions2, isInside, roomName);

        // Act
        boolean isEquals = room.equals(room2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns false when comparing two rooms with different ids.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsWithDifferentIds() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID1 = new RoomID("1");
        RoomID roomID2 = new RoomID("2");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID1, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID2, floorNumber, dimensions, isInside, roomName);

        // Act
        boolean isEquals = room.equals(room2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different house IDs.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsFromDifferentHouses() {
        // Arrange
        HouseId houseId1Double = mock(HouseId.class);
        HouseId houseId2Double = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseId1Double, roomID, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseId2Double, roomID, floorNumber, dimensions, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different floor numbers.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsFromDifferentFloors() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber1 = new FloorNumber(3);
        FloorNumber floorNumber2 = new FloorNumber(4);
        Length length = new Length(10.5);
        Width width = new Width(10.5);
        Height height = new Height(10.5);
        Dimensions dimensions = new Dimensions(length, width, height);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber1, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber2, dimensions, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different dimensions.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsWithDifferentDimensions() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length1 = new Length(10.5);
        Width width1 = new Width(10.5);
        Height height1 = new Height(10.5);
        Length length2 = new Length(20.5);
        Width width2 = new Width(10.5);
        Height height2 = new Height(10.5);
        Dimensions dimensions1 = new Dimensions(length1, width1, height1);
        Dimensions dimensions2 = new Dimensions(length2, width2, height2);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions1, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber, dimensions2, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different indoors state.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsWithDifferentIndoorsState()
    {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length1 = new Length(10.5);
        Width width1 = new Width(10.5);
        Height height1 = new Height(10.5);
        Dimensions dimensions = new Dimensions(length1, width1, height1);
        boolean isInside = true;
        boolean isInside2 = false;
        RoomName roomName = new RoomName("Living Room");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside2, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }
    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different names.
     */

    @Test
    void shouldReturnFalse_WhenComparingRoomsWithDifferentNames()
    {

        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID = new RoomID("1");
        FloorNumber floorNumber = new FloorNumber(3);
        Length length1 = new Length(10.5);
        Width width1 = new Width(10.5);
        Height height1 = new Height(10.5);
        Dimensions dimensions = new Dimensions(length1, width1, height1);
        boolean isInside = true;
        RoomName roomName = new RoomName("Living Room");
        RoomName roomName2 = new RoomName("Bedroom");
        Room room = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID, floorNumber, dimensions, isInside, roomName2);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }


}
