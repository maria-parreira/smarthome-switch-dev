package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.GPSCoordinates;
import smartHomeDDD.domain.valueobject.Latitude;
import smartHomeDDD.domain.valueobject.Longitude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * This test class validates the behavior of the GPSCoordinates class, which represents a geographical location
 * identified by a latitude and a longitude. Key aspects tested include:
 * - Creation of a valid GPSCoordinates with a non-null latitude and longitude.
 * - Prevention of GPSCoordinates creation with a null latitude or longitude, ensuring proper error handling.
 * - Equality comparison between GPSCoordinates, checking if two instances with the same properties are considered equal.
 * - Handling of null objects and ensuring that the equals method returns false when comparing with null.
 * - Consistency of equals method when comparing different instances with the same properties.
 * - Accuracy of the toString method, verifying that it returns the expected string representation of the GPSCoordinates.
 */

class GPSCoordinatesTest {

    /**
     * Tests if a valid GPSCoordinates can be created with valid Latitude and Longitude.
     */
    @Test
    void validCoordinates_shouldCreateGPSCoordinates() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);

        // Act & Assert
        assertDoesNotThrow(() -> {
            new GPSCoordinates(latitudeMock, longitudeMock);
        });
    }


    /**
     * Tests if the equals method returns true when comparing the same GPSCoordinates instance.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitudeMock, longitudeMock);

        // Act
        boolean result = gpsCoordinates.equals(gpsCoordinates);

        // Assert
        assertTrue(result);
    }

    /**
     * Tests if the equals method returns false when comparing with null.
     */
    @Test
    void nullObject_shouldReturnFalse() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitudeMock, longitudeMock);

        // Act & Assert
        assertNotEquals(null, gpsCoordinates);
    }

    /**
     * Tests if the equals method returns false when comparing with a different object type.
     */

    @Test
    void differentObjectDifferentCoordinates_shouldReturnFalse() {
        // Arrange
        Latitude latitudeMock1 = mock(Latitude.class);
        Longitude longitudeMock1 = mock(Longitude.class);
        GPSCoordinates gpsCoordinates1 = new GPSCoordinates(latitudeMock1, longitudeMock1);

        Latitude latitudeMock2 = mock(Latitude.class);
        Longitude longitudeMock2 = mock(Longitude.class);
        GPSCoordinates gpsCoordinates2 = new GPSCoordinates(latitudeMock2, longitudeMock2);

        // Act & Assert
        assertNotEquals(gpsCoordinates1, gpsCoordinates2);
    }

    /**
     * Tests if the equals method returns true when comparing two GPSCoordinates with the same properties.
     */
    @Test
    void getLatitude_shouldReturnLatitudeValue() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);
        when(latitudeMock.getValue()).thenReturn(45.0);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitudeMock, longitudeMock);

        // Act
        double result = gpsCoordinates.getLatitude();

        // Assert
        assertEquals(45.0, result);
    }

    /**
     * Tests if the equals method returns true when comparing two GPSCoordinates with the same properties.
     */
    @Test
    void getLongitude_shouldReturnLongitudeValue() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);
        when(longitudeMock.getValue()).thenReturn(90.0);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitudeMock, longitudeMock);

        // Act
        double result = gpsCoordinates.getLongitude();

        // Assert
        assertEquals(90.0, result);
    }

    /**
     * Tests if the equals method returns true when comparing two GPSCoordinates with the same properties.
     */
    @Test
    void areGPSCoordinatesValid_shouldReturnTrueWhenLatitudeAndLongitudeAreNotNull() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitudeMock, longitudeMock);

        // Act
        boolean result = gpsCoordinates.areGPSCoordinatesValid();

        // Assert
        assertTrue(result);
    }

    @Test
    void toString_shouldReturnCorrectFormat() {
        // Arrange
        Latitude latitudeMock = mock(Latitude.class);
        Longitude longitudeMock = mock(Longitude.class);
        when(latitudeMock.getValue()).thenReturn(45.0);
        when(longitudeMock.getValue()).thenReturn(90.0);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitudeMock, longitudeMock);

        // Act
        String result = gpsCoordinates.toString();

        // Assert
        assertEquals("GPSCoordinates{latitude=45.0, longitude=90.0}", result);
    }

}