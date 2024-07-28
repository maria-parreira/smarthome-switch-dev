package smartHomeDDD.domain.domain.room;

import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This class contains the tests for the Room class which evaluates the behavior of the Room class.
 * The following scenarios were tested:
 * Test to ensure that the Room constructor creates a room when valid arguments are provided.
 * Test to ensure that the identity method returns the room ID.
 * Test to ensure that the sameAs method returns true when comparing the same room.
 * Test to ensure that the sameAs method returns true when comparing two equal rooms.
 * Test to ensure that the equals method returns false when comparing two different rooms.
 * Test to ensure that the equals method returns false when comparing a room with a different object.
 * Test to ensure that the sameAs method returns true when comparing two equal rooms.
 * Test to ensure that the sameAs method returns true when comparing two equal rooms with different ids.
 * Test to ensure that the sameAs method returns false when comparing two rooms from different houses.
 * Test to ensure that the sameAs method returns false when comparing two rooms from different floors.
 * Test to ensure that the sameAs method returns false when comparing two rooms with different dimensions.
 * Test to ensure that the equals method returns false when comparing two object types
 * Test to ensure that the equals method returns true when comparing the same object
 * Test to ensure that the hashCode method returns the same value for two equal rooms.
 *
 */
class RoomTest {

    /**
     * Test to ensure that the Room constructor creates a room when valid arguments are provided.
     */
    @Test
    void validArguments_shouldCreateARoom(){
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        // Act
        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Assert
        assertNotNull(room);
        assertEquals(room.identity(), roomIDDouble);
        assertEquals(room.getHouseId(), houseIdDouble);
        assertEquals(room.getFloorNumber(), floorNumberDouble);
        assertEquals(room.getDimensions(), dimensionsDouble);
        assertEquals(room.isInside(), isInside);
        assertEquals(room.getRoomName(), roomName);
    }

    /**
     * Test to ensure that the identity method returns the room ID.
     */
    @Test
    void shouldReturnRoomId_WhenGettingIdentity(){
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);
        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Act
        RoomID roomId = room.identity();

        // Assert
        assertEquals(roomIDDouble, roomId);
    }

    /**
     * Test to ensure that the sameAs method returns true when comparing the same room.
     */
    @Test
    void shouldReturnTrue_WhenComparingItself() {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);
        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
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
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);
        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Act
        boolean isEquals = room.sameAs(room2);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the equals method returns false when comparing two equal rooms but with different ids.
     */
    @Test
    void shouldReturnFalse_WhenComparingTwoEqualRoomsWithDifferentID() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        RoomID roomID1Double = mock(RoomID.class);
        RoomID roomID2Double = mock(RoomID.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);
        Room room = new Room(houseIdDouble, roomID1Double, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomID2Double, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Act
        boolean isEquals = room.equals(room2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing a room with a different object.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomWithDifferentObject()  {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);
        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Object obj = new Object();

        // Act
        boolean isEquals = room.sameAs(obj);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test to ensure that the sameAs method returns true when comparing two equal rooms.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualRoomsSameAs()  {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);
        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertTrue(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms from different houses.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsFromDifferentHouses()  {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseId1Double = mock(HouseId.class);
        HouseId houseId2Double = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        Room room = new Room(houseId1Double, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseId2Double, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms from different floors.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsFromDifferentFloors()  {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumber1Double = mock(FloorNumber.class);
        FloorNumber floorNumber2Double = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        Room room = new Room(houseIdDouble, roomIDDouble, floorNumber1Double, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumber2Double, dimensionsDouble, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different dimensions.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsWithDifferentDimensions()  {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensions1Double = mock(Dimensions.class);
        Dimensions dimensions2Double = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensions1Double, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensions2Double, isInside, roomName);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two rooms with different inside state.
     */
    @Test
    void shouldReturnFalse_WhenComparingRoomsWithDifferentInsideState()
    {
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        boolean isInside2 = false;
        RoomName roomName = mock(RoomName.class);

        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside2, roomName);

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
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName1 = mock(RoomName.class);
        RoomName roomName2 = mock(RoomName.class);

        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName1);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName2);

        // Act
        boolean isSame = room.sameAs(room2);

        // Assert
        assertFalse(isSame);
    }

    /**
     * This test verifies that the hashCode method returns the same value for two equal rooms.
     */
    @Test
    void shouldReturnSameHashCodeInSameObject(){
        // Arrange
        RoomID roomIDDouble = mock(RoomID.class);
        HouseId houseIdDouble = mock(HouseId.class);
        FloorNumber floorNumberDouble = mock(FloorNumber.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        Room room = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);
        Room room2 = new Room(houseIdDouble, roomIDDouble, floorNumberDouble, dimensionsDouble, isInside, roomName);

        // Act
        int hashCode1 = room.hashCode();
        int hashCode2 = room2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testEquals() {
        // Arrange
        RoomID roomID1 = mock(RoomID.class);
        RoomID roomID2 = mock(RoomID.class);
        HouseId houseId = mock(HouseId.class);
        FloorNumber floorNumber = mock(FloorNumber.class);
        Dimensions dimensions = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        Room room1 = new Room(houseId, roomID1, floorNumber, dimensions, isInside, roomName );
        Room room2 = new Room(houseId, roomID1, floorNumber, dimensions, isInside, roomName);
        Room room3 = new Room(houseId, roomID2, floorNumber, dimensions, isInside, roomName);

        // Act & Assert
        // Scenario 1: Comparing the same instance
        assertEquals(room1, room1);

        // Scenario 2: Comparing different instances with the same roomID
        assertEquals(room1, room2);

        // Scenario 3: Comparing instances with different roomID
        assertNotEquals(room1, room3);

        // Scenario 4: Comparing with a non-Room object
        assertNotEquals(room1, new Object());
    }

    @Test
    void testIsInside() {
        // Arrange
        RoomID roomID = mock(RoomID.class);
        HouseId houseId = mock(HouseId.class);
        FloorNumber floorNumber = mock(FloorNumber.class);
        Dimensions dimensions = mock(Dimensions.class);
        RoomName roomName = mock(RoomName.class);

        Room roomInside = new Room(houseId, roomID, floorNumber, dimensions, true, roomName);
        Room roomOutside = new Room(houseId, roomID, floorNumber, dimensions, false, roomName);

        // Act & Assert
        // Scenario 1: Room is inside
        assertTrue(roomInside.isInside());

        // Scenario 2: Room is outside
        assertFalse(roomOutside.isInside());
    }

    @Test
    void testHashCode() {
        // Arrange
        RoomID roomID = mock(RoomID.class);
        HouseId houseId = mock(HouseId.class);
        FloorNumber floorNumber = mock(FloorNumber.class);
        Dimensions dimensions = mock(Dimensions.class);
        boolean isInside = true;
        RoomName roomName = mock(RoomName.class);

        Room room = new Room(houseId, roomID, floorNumber, dimensions, isInside, roomName);

        // Act
        int hashCode = room.hashCode();

        // Assert
        assertNotEquals(0, hashCode);
    }

}