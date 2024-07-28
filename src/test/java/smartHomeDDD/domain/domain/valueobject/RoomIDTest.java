package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.RoomID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RoomIDTest {

    @Test
    void shouldCreateAValidRoomId() {
        // Arrange
        String sId = "bedroom";

        // Act+Assert
        assertNotNull(new RoomID(sId));
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_RoomId(String id) {
        Exception exception = assertThrows(Exception.class, () -> {
            new RoomID(id);
        });

        String expectedMessage = "RoomId cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_RoomIdWithNullId() {
        Exception exception = assertThrows(Exception.class, () -> {
            new RoomID(null);
        });

        String expectedMessage = "RoomId cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldReturnFalseEquals_WithNull() {
        // Arrange
        RoomID roomId = new RoomID("bedroom");

        // Act
        boolean isEquals = roomId.equals(null);

        // Assert
        assertFalse(isEquals);
    }
    @Test
    void shouldReturnTrueEquals_WithSameObject() {
        // Arrange
        RoomID roomId = new RoomID("bedroom");

        // Act
        boolean isEquals = roomId.equals(roomId);

        // Assert
        assertTrue(isEquals);
    }
    @Test
    void shouldReturnTrueEquals_WithSameIds() {
        // Arrange
        RoomID roomID1 = new RoomID("bedroom");
        RoomID roomID2 = new RoomID("bedroom");

        // Act
        boolean isEquals = roomID1.equals(roomID2);

        // Assert
        assertTrue(isEquals);
    }
    @Test
    void shouldReturnFalseEquals_WithDifferentIds() {
        // Arrange
        RoomID roomID1 = new RoomID("bedroom");
        RoomID roomID2 = new RoomID("kitchen");

        // Act
        boolean isEquals = roomID1.equals(roomID2);

        // Assert
        assertFalse(isEquals);
    }
    @Test
    void toStringShouldReturnTheIdString() {
        // Arrange
        RoomID roomId = new RoomID("bedroom");

        // Act
        String actual = roomId.toString();

        // Assert
        assertEquals("bedroom", actual);
    }
}

