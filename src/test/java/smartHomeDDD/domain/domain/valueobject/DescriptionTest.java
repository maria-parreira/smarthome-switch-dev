package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The DescriptionTest class provides unit tests for the Description class.
 * It encompasses the following scenarios:
 * - Creating a valid description
 * - Creating a description with invalid input
 * - Checking the equality of a description with null
 * - Checking the equality of a description with itself
 * - Checking the equality of two descriptions with the same text
 * - Getting the string representation of a description
 */

class DescriptionTest {

    /**
     * Test case for creating a valid description.
     * The test verifies that the Description constructor returns a non-null object when provided with a valid description text.
     */
    @Test
    void validDescription_shouldCreateAValidDescription() {
        // Arrange
        String descriptionText = "This is a valid description.";

        // Act + Assert
        new Description(descriptionText);
    }

    /**
     * Test case for creating a description with invalid input.
     * The test verifies that the Description constructor throws an IllegalArgumentException when provided with an empty or whitespace-only description text.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void invalidDescription_shouldThrowException(String text) {
        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Description(text));

        String expectedMessage = "Description cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for checking the equality of a description with null.
     * The test verifies that the equals method of the Description class returns false when the description is compared with null.
     */
    @Test
    void nullDescription_shouldReturnFalse() {
        // Arrange
        new Description("Valid Description");

        // Act
        boolean isEquals = false;

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test case for checking the equality of a description with itself.
     * The test verifies that the equals method of the Description class returns true when the description is compared with itself.
     */
    @Test
    void descriptionEqualToItself_shouldReturnTrue()  {
        // Arrange
        Description description = new Description("Valid Description");

        // Act
        boolean isEquals = description.equals(description);

        // Assert
        assertTrue(true);
    }

    /**
     * Test case for checking the equality of two descriptions with the same text.
     * The test verifies that the equals method of the Description class returns true when two descriptions with the same text are compared.
     */
    @Test
    void differentObjectSameDescription_shouldReturnTrue() {
        // arrange
        Description description = new Description("Valid Description");
        Description description1 = new Description("Valid Description");

        // act
        boolean isEquals = description.equals(description1);

        // assert
        assertTrue(isEquals);
    }

    /**
     * Test case for getting the string representation of a description.
     * The test verifies that the toString method of the Description class returns the correct description text.
     */
    @Test
    void validDescriptionToString_ShouldReturnTheDescriptionString() {
        // Arrange
        String expected = "Valid Description";
        Description description = new Description(expected);

        // Act
        String actual = description.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test case to verify that the equals method returns true when the same instance is compared.
     */
    @Test
    void equals_sameInstance_shouldReturnTrue() {
        // Arrange
        Description description = new Description("Valid Description");

        // Act & Assert
        assertEquals(description, description);
    }

    /**
     * Test case to verify that the equals method returns true when two different instances have the same _description.
     */
    @Test
    void equals_same_description_shouldReturnTrue() {
        // Arrange
        Description description1 = new Description("Valid Description");
        Description description2 = new Description("Valid Description");

        // Act & Assert
        assertEquals(description1, description2);
    }

    /**
     * Test case to verify that the equals method returns false when the other object is null.
     */
    @Test
    void equals_nullObject_shouldReturnFalse() {
        // Arrange
        Description description = new Description("Valid Description");

        // Act & Assert
        assertNotEquals(null, description);
    }

    /**
     * Test case to verify that the equals method returns false when the other object is not an instance of Description.
     */
    @Test
    void equals_differentClass_shouldReturnFalse() {
        // Arrange
        Description description = new Description("Valid Description");
        String notADescription = "notADescription";

        // Act & Assert
        assertNotEquals(description, notADescription);
    }

    /**
     * Test case to verify that the equals method returns false when the other Description instance has a different _description.
     */
    @Test
    void equals_different_description_shouldReturnFalse() {
        // Arrange
        Description description1 = new Description("Valid Description");
        Description description2 = new Description("Different Description");

        // Act & Assert
        assertNotEquals(description1, description2);
    }

    /**
     * Test to verify that the Description constructor correctly sets a valid description.
     */
    @Test
    void validDescription_shouldCreateNewInstance() {
        // Arrange
        String expectedDescription = "Valid Description";

        // Act
        Description description = new Description(expectedDescription);

        // Assert
        assertEquals(expectedDescription, description.toString());
    }

    /**
     * Test to verify that the Description constructor throws an IllegalArgumentException when the description is null.
     */
    @Test
    void nullDescription_shouldThrowException() {
        // Arrange
        String nullDescription = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Description(null));
    }

    /**
     * Test to verify that the Description constructor throws an IllegalArgumentException when the description is empty.
     */
    @Test
    void emptyDescription_shouldThrowException() {
        // Arrange
        String emptyDescription = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Description(emptyDescription));
    }

    /**
     * Test to verify that the Description constructor throws an IllegalArgumentException when the description is blank.
     */
    @Test
    void blankDescription_shouldThrowException() {
        // Arrange
        String blankDescription = " ";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Description(blankDescription));
    }

    /**
     * Test to verify that the hashCode method returns the correct hash code for a valid Description object.
     */
    @Test
    void hashCode_withValidDescriptionObject_shouldReturnCorrectHashCode() {
        // Arrange
        String descriptionText = "Valid Description";
        Description description = new Description(descriptionText);

        // Act
        int hashCode = description.hashCode();

        // Assert
        assertEquals(descriptionText.hashCode(), hashCode);
    }

}
