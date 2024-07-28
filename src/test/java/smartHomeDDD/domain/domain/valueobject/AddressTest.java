package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Address class.
 * Test Descriptions:
 * - Testing creation of a valid Address object with a valid address string.
 * - Ensuring that exceptions are thrown when invalid or null addresses are provided.
 * - Verifying that the equals method behaves correctly:
 * - Returns false when comparing with null.
 * - Returns true when comparing with the same object.
 * - Returns true when comparing with another Address object with the same address string.
 * - Returns false when comparing with another Address object with different address strings.
 * - Testing the toString method to ensure it returns the correct address string representation.
 * - Testing the hashCode method returns the same hashCode for equal objects
 */
class AddressTest {

    /**
     * Tests the creation of a valid Address object.
     */
    @Test
    void shouldCreateAValidAddress() {
        // Arrange
        String sAddress = "street";
        //Act
        Address address = new Address(sAddress);
        // Act+Assert
        assertNotNull(address);
    }


    /**
     * Tests that an exception is thrown when an invalid address is provided.
     * @param address The invalid address to be tested.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})

    void shouldThrowException_AddressInvalid(String address) {
        //Arrange
        String expectedMessage = "Address can't be empty";
        //Act
        Exception exception = assertThrows(Exception.class, () -> {
            new Address(address);
        });
        String actualMessage = exception.getMessage();
        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Tests that an exception is thrown when a null address is provided.
     */
    @Test
    void shouldThrowException_NullAddress() {
        //Arrange
        String expectedMessage = "Address can't be null";
        //Act
        Exception exception = assertThrows(Exception.class, () -> {
            new Address(null);
        });
        String actualMessage = exception.getMessage();
        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests that equals method returns false when comparing with null.
     */
    @Test
    void shouldReturnFalseEquals_WithNullAddress()  {
        // Arrange
        Address address = new Address("street");
        // Act
        boolean isEquals = address.equals(null);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests that equals method returns true when comparing with the same object.
     */
    @Test
    void shouldReturnTrueEquals_WithSameObject() {
        // Arrange
        Address address = new Address("street");
        // Act
        boolean isEquals = address.equals(address);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests that equals method returns true when comparing with another Address object with the same address string.
     */
    @Test
    void shouldReturnTrueEquals_WithSameIds() {
        // Arrange
        Address address1 = new Address("street1");
        Address address2 = new Address("street1");
        // Act
        boolean isEquals = address1.equals(address2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests that equals method returns false when comparing with another Address object with different address strings.
     */
    @Test
    void shouldReturnFalseEquals_WithDifferentIds() {
        // Arrange
        Address address1 = new Address("street1");
        Address address2 = new Address("street2");
        // Act
        boolean isEquals = address1.equals(address2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests the toString method to ensure it returns the correct address string representation.
     */
    @Test
    void toStringShouldReturnTheAddressString() {
        // Arrange
        Address address = new Address("street");
        // Act
        String actual = address.toString();
        // Assert
        assertEquals("street", actual);
    }

    /**
     * Tests the hashCode is the same in equal objects
     */

    @Test
    void equalObjectShouldHaveTheSameHashCode(){
        //Arrange
        Address address1 = new Address("address");
        Address address2 = new Address("address");

        //Act
        int hashCode1 = address1.hashCode();
        int hashCode2 = address2.hashCode();

        //Assert
        assertEquals(hashCode1, hashCode2);
    }

}
