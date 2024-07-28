package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.HouseId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HouseId class.
 * Test Descriptions:
 * - Testing creation of a valid HouseId object with a valid identifier.
 * - Ensuring that exceptions are thrown when invalid or null identifiers are provided.
 * - Verifying the behavior of the equals method:
 *      - Returns false when comparing with null.
 *      - Returns true when comparing with the same object.
 *      - Returns true when comparing with another HouseId object with the same identifier.
 *      - Returns false when comparing with another HouseId object with different identifiers.
 * - Testing the toString method to ensure it returns the correct identifier string representation.
 */
class HouseIdTest {

    /**
     * Tests the creation of a valid HouseId object with a valid identifier.
     */
    @Test
    void shouldCreateAValidHouseId() {
        // Arrange
        String sId = "house1";
        // Act+Assert
        assertNotNull(new HouseId(sId));
    }

    /**
     * Tests that an exception is thrown when an invalid identifier is provided.
     * @param id The invalid identifier to be tested.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_HouseId(String id) {
        //Arrange
        String expectedMessage = "HouseId can't be empty";
        //Act
        Exception exception = assertThrows(Exception.class, () -> {
            new HouseId(id);
        });
        String actualMessage = exception.getMessage();
        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Tests that an exception is thrown when a null identifier is provided.
     */
    @Test
    void shouldThrowException_HouseIdWithNullId() {
        //Arrange
        String expectedMessage = "HouseId can't be null";
        //Act
        Exception exception = assertThrows(Exception.class, () -> {
            new HouseId(null);
        });
        String actualMessage = exception.getMessage();
        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests the equals method to ensure it returns false when comparing with null.
     */
    @Test
    void shouldReturnFalseEquals_WithNull() {
        // Arrange
        HouseId houseId = new HouseId("house1");
        // Act
        boolean isEquals = houseId.equals(null);
        // Assert
        assertFalse(isEquals);
    }


    /**
     * Tests the equals method to ensure it returns true when comparing with the same object.
     */
    @Test
    void shouldReturnTrueEquals_WithSameObject() {
        // Arrange
        HouseId houseId = new HouseId("house1");
        // Act
        boolean isEquals = houseId.equals(houseId);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals method to ensure it returns true when comparing with another HouseId object with the same identifier.
     */
    @Test
    void shouldReturnTrueEquals_WithSameIds() {
        // Arrange
        HouseId houseId1 = new HouseId("house1");
        HouseId houseId2 = new HouseId("house1");
        // Act
        boolean isEquals = houseId1.equals(houseId2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals method to ensure it returns false when comparing with another HouseId object with different identifiers.
     */
    @Test
    void shouldReturnFalseEquals_WithDifferentIds() {
        // Arrange
        HouseId houseId1 = new HouseId("house1");
        HouseId houseId2 = new HouseId("house2");
        // Act
        boolean isEquals = houseId1.equals(houseId2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests the toString method to ensure it returns the correct identifier string.
     */
    @Test
    void toStringShouldReturnTheIdString() {
        // Arrange
        HouseId houseId1 = new HouseId("house1");
        // Act
        String actual = houseId1.toString();
        // Assert
        assertEquals("house1", actual);
    }
}