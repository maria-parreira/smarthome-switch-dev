package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.Reading;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ReadingTest {

    @ParameterizedTest
    @ValueSource(strings = {"oFf", "ON", "Wind Speed: 10.0 Km/h; Wind Direction: 0.0 rad - East", "50%", "300"})
    void offString_shouldCreateAValidReading(String sReading) {

        // Act+Assert
        assertNotNull(new Reading(sReading));
    }

    @Test
    void negativeDecimalReading_shouldCreateAValidReading() {
        // Arrange
        String sReading = "-123.0";

        // Act+Assert
        assertNotNull(new Reading(sReading));
    }




    @Test
    void nullReading_shouldThrowException() {
        // Arrange
        String expectedMessage = "Invalid reading";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new Reading(null);
        });

        // Assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void emptyReading_shouldThrowException() {
        // Arrange
        String expectedMessage = "Invalid reading";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new Reading("");
        });

        // Assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void blankReading_shouldThrowException() {
        // Arrange
        String expectedMessage = "Invalid reading";

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new Reading(" ");
        });

        // Assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void null_shouldReturnFalseEquals() {
        // Arrange
        Reading reading = new Reading("on");

        // Act
        boolean isEquals = reading.equals(null);

        // Assert
        assertFalse(isEquals);
    }
    @Test
    void sameObject_shouldReturnTrueEquals() {
        // Arrange
        Reading reading = new Reading("on");

        // Act
        boolean isEquals = reading.equals(reading);

        // Assert
        assertTrue(isEquals);
    }
    @Test
    void sameReading_shouldReturnTrueEquals() {
        // Arrange
        Reading reading1 = new Reading("on");
        Reading reading2 = new Reading("on");

        // Act
        boolean isEquals = reading1.equals(reading2);

        // Assert
        assertTrue(isEquals);
    }
    @Test
    void differentReadings_shouldReturnFalseEquals() {
        // Arrange
        Reading reading1 = new Reading("on");
        Reading reading2 = new Reading("off");

        // Act
        boolean isEquals = reading1.equals(reading2);

        // Assert
        assertFalse(isEquals);
    }
    @Test
    void toString_ShouldReturnReading() {
        // Arrange
        Reading reading = new Reading("on");

        // Act
        String sReading = reading.toString();

        // Assert
        String expected = "on";
        assertEquals(expected, sReading);
    }

    @Test
    void hashCode_sameReading_ShouldReturnSameHashCode() {
        // Arrange
        String readingValue = "on";

        Reading reading1 = new Reading(readingValue);
        Reading reading2 = new Reading(readingValue);

        // Act
        int hashCode1 = reading1.hashCode();
        int hashCode2 = reading2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}
