package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.ZipCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the Value Object ZipCode class.
 * It verifies that the constructor behaves as expected when provided with valid input, allowing the creation of ZipCode objects representing valid zip codes.
 * It verifies that the ZipCode constructor correctly throws an exception with the appropriate error message when provided with invalid zip code strings.
 * It verifies that the equality comparison behaves as expected, confirming that ZipCode objects with identical values are considered equal.
 */
class ZipCodeTest {


    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_ZipCodeInvalid() {
        //arrange
        String expectedMessage = "Invalid arguments provided.";
        //act
        Exception exception = assertThrows(Exception.class, () -> {
            new ZipCode("Portugal","");
        });
        String actualMessage = exception.getMessage();
        //assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_ZipCodeInvalidCountry() {
        //arrange
        String expectedMessage = "Invalid country";
        //act
        Exception exception = assertThrows(Exception.class, () -> {
            new ZipCode("Port","12345");
        });

        //Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldCreateAValidZipCode_Portugal(){
        // Arrange
        String zipCode = "1234-456";
        String country = "Portugal";

        // Act+Assert
        assertNotNull(new ZipCode(country,zipCode));
    }
    @Test
    void shouldCreateAValidZipCode_USA(){
        // Arrange
        String zipCode = "12348-4564";
        String country = "USA";

        // Act+Assert
        assertNotNull(new ZipCode(country,zipCode));
    }

    @Test
    void shouldCreateAValidZipCode_Canada(){
        // Arrange
        String zipCode = "B2B 3C4";
        String country = "Canada";

        // Act+Assert
        assertNotNull(new ZipCode(country,zipCode));
    }

    @Test
    void shouldCreateAValidZipCode_France(){
        // Arrange
        String zipCode = "12345";
        String country = "France";

        // Act+Assert
        assertNotNull(new ZipCode(country,zipCode));
    }

    /**
     * Tests the equals method with a null parameter.
     * It should return false.
     */
    @Test
    void shouldReturnFalseEquals_WithNullZipCode(){
        // Arrange
        ZipCode zipCode = new ZipCode("Portugal","1237-456");
        // Act
        boolean isEquals = zipCode.equals(null);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests the equals method with the same object.
     * It should return true.
     */
    @Test
    void shouldReturnTrueEquals_WithSameObject() {
        // Arrange
        ZipCode zipCode = new ZipCode("France","12345");
        // Act
        boolean isEquals = zipCode.equals(zipCode);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals method with two ZipCode objects having the same zip code.
     * It should return true.
     */
    @Test
    void shouldReturnTrueEquals_WithSameZipCode() {
        // Arrange
        ZipCode zipCode1 = new ZipCode("Portugal","1239-456");
        ZipCode zipCode2 = new ZipCode("Portugal","1239-456");
        // Act
        boolean isEquals = zipCode1.equals(zipCode2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests the equals method with two ZipCode objects having different zip codes.
     * It should return false.
     */
    @Test
    void shouldReturnFalseEquals_WithDifferentZipCode()  {
        // Arrange
        ZipCode zipCode1 = new ZipCode("USA","78945-1235");
        ZipCode zipCode2 = new ZipCode("USA","12378-4569");
        // Act
        boolean isEquals = zipCode1.equals(zipCode2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests the toString method to ensure it returns the correct zip code string representation.
     */
    @Test
    void toStringShouldReturnTheZipCodeString() {
        // Arrange
        ZipCode zipCode = new ZipCode("Canada","A2A 1S1");
        // Act
        String actual = zipCode.toString();
        // Assert
        assertEquals("A2A 1S1", actual);
    }

    /**
     * Validates if the country is correctly returned.
     */
     @Test
        void validCountry_shouldReturnCountry() {
            // Arrange
            ZipCode zipCode = new ZipCode("Canada","A2A 1S1");
            // Act
            String actual = zipCode.toString();
            // Assert
            assertEquals("A2A 1S1", actual);
            assertEquals("Canada",zipCode.getCountry());
        }
    @Test
    void shouldThrowException_WhenCountryOrZipCodeIsEmpty() {
        // Arrange
        String country = "";
        String zipCode = "";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(country, zipCode));
    }

    @Test
    void hashCode_sameZipCode_ShouldReturnSameHashCode() {
        // Arrange
        String country = "USA";
        String zipCode = "12345-6789";

        ZipCode zipCode1 = new ZipCode(country, zipCode);
        ZipCode zipCode2 = new ZipCode(country, zipCode);

        // Act
        int hashCode1 = zipCode1.hashCode();
        int hashCode2 = zipCode2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

}
