package smartHomeDDD.domain.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import smartHomeDDD.domain.valueobject.RoomName;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This class tests the functionality of the RoomName class.
 * This class tests the conditions for the following scenarios:
 * - create a valid instance of RoomName
 * - throw an exception when a null name is provided
 * - throw an exception when an empty or blank name is provided
 * - return true when the equals method is given the same object as parameter
 * - return true when the equals method is given the same object as parameter
 * - return false when the equals method is given a null object as parameter
 * - return true when the equals method is given a RoomName object with the same name as parameter
 * - return false when the equals method is given a RoomName object with different name as parameter
 * - return false when the equals method is given a different object instance as parameter
 * - return the name string
 * - return the same hash code for two RoomName objects with the same name
 */

class RoomNameTest {

    /**
     * Test to ensure that the RoomName constructor creates a RoomName object when given a valid name.
     */
    @Test
    void validRoomName_shouldCreateDeviceName() {
        // arrange
        String name = "RoomName";

        // act
        RoomName roomName = new RoomName(name);

        // assert
        assertNotNull(roomName);


    }

    /**
     * Test to ensure that the RoomName constructor throws an exception when given a null name.
     */
    @Test
    void nullRoomName_shouldThrowException() {
        // arrange
        String expectedMessage = "Room Name cannot be null or empty.";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RoomName(null);
        });
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the RoomName constructor throws an exception when given an empty or blank name.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r", "\n"})
    void invalidRoomName_shouldThrowException(String name) {
        // arrange
        String expectedMessage = "Room Name cannot be null or empty.";

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RoomName(name);
        });
        String actualMessage = exception.getMessage();

        // assert
        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Test to ensure that the equals method returns true when given the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // arrange
        RoomName roomName = new RoomName("RoomName");

        // act
        boolean result = roomName.equals(roomName);

        // assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the equals method returns true when given the same object.
     */
    @Test
    void differentObjectSameName_shouldReturnTrue() {
        // arrange
        String name = "RoomName";
        RoomName roomName = new RoomName(name);
        RoomName roomName2 = new RoomName(name);

        // act
        boolean result = roomName.equals(roomName2);

        // assert
        assertTrue(result);

    }

    /**
     * Test to ensure that the equals method returns false when given a different object with a different name.
     */
    @Test
    void differentObjectDifferentName_shouldReturnFalse() {
        // arrange
        RoomName roomName = new RoomName("RoomName");
        RoomName roomName2 = new RoomName("RoomName2");

        // act
        boolean result = roomName.equals(roomName2);

        // assert
        assertFalse(result);
    }
    /**
     * Test to ensure that the equals method returns false when given a different object instance.
     */
    @Test
    void differentObjectInstance_shouldReturnFalse() {
        // arrange
        RoomName roomName = new RoomName("RoomName");
        Object object = new Object();

        // act
        boolean result = roomName.equals(object);

        // assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the toString method returns the name of the RoomName object.
     */
    @Test
    void toString_shouldReturnName() {
        // arrange
        String name = "RoomName";
        RoomName roomName = new RoomName(name);

        // act
        String result = roomName.toString();

        // assert
        assertEquals(name, result);
    }

    /**
     * Test to ensure that the hashCode method returns the same hash code for two RoomName objects with the same name.
     */
    @Test
    void sameName_shouldReturnSameHashCode() {
        // arrange
        String name = "RoomName";
        RoomName roomName = new RoomName(name);
        RoomName roomName2 = new RoomName(name);

        // act
        int result = roomName.hashCode();
        int result2 = roomName2.hashCode();

        // assert
        assertEquals(result, result2);
    }
}
