package smartHomeDDD.domain.services;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import smartHomeDDD.persistence.gateway.WeatherServiceGateway;
import smartHomeDDD.services.ServiceWeather;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for ServiceWeather.
 * it tests the conditions for the following scenarios:
 * instantiating the class with a null gateway should throw an exception
 * retrieving the current temperature for a given latitude and longitude
 * retrieving the sunrise time for a given latitude and longitude
 * retrieving the sunset time for a given latitude and longitude
 */
class ServiceWeatherTest {

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the gateway is null.
     */
    @Test
    void nullGateway_shouldThrowException() {
        //Arrange
        String expected = "WeatherServiceGateway cannot be null";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ServiceWeather(null));
        String actual = exception.getMessage();
        //Assert
        assertTrue(actual.contains(expected));
    }

    /**
     * Test case to verify that the current temperature is retrieved.
     */
    @Test
    void getCurrentTemperature() {
        //Arrange
        String latitude = "55";
        String longitude = "90";
        Object temperature = "20";

        WeatherServiceGateway weatherServiceGateway = mock(WeatherServiceGateway.class);
        when(weatherServiceGateway.getcurrentTemperature(latitude, longitude)).thenReturn(Mono.just(temperature));
        ServiceWeather serviceWeather = new ServiceWeather(weatherServiceGateway);
        //Act
        Object result = serviceWeather.getCurrentTemperature(latitude, longitude);
        //Assert
        assertEquals(temperature, result);
    }

    /**
     * Test case to verify that the sunrise time is retrieved.
     */
    @Test
    void getSunrise() {
        //Arrange
        String latitude = "55";
        String longitude = "90";
        Object sunrise = "06:00";

        WeatherServiceGateway weatherServiceGateway = mock(WeatherServiceGateway.class);
        when(weatherServiceGateway.getSunrise(latitude, longitude)).thenReturn(Mono.just(sunrise));
        ServiceWeather serviceWeather = new ServiceWeather(weatherServiceGateway);
        //Act
        Object result = serviceWeather.getSunrise(latitude, longitude);
        //Assert
        assertEquals(sunrise, result);
    }

    /**
     * Test case to verify that the sunset time is retrieved.
     */
    @Test
    void getSunset() {
        //Arrange
        String latitude = "55";
        String longitude = "90";
        Object sunset = "18:00";

        WeatherServiceGateway weatherServiceGateway = mock(WeatherServiceGateway.class);
        when(weatherServiceGateway.getSunset(latitude, longitude)).thenReturn(Mono.just(sunset));
        ServiceWeather serviceWeather = new ServiceWeather(weatherServiceGateway);
        //Act
        Object result = serviceWeather.getSunset(latitude, longitude);
        //Assert
        assertEquals(sunset, result);
    }
}
