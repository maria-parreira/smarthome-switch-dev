package smartHomeDDD.services;
import org.springframework.stereotype.Service;
import smartHomeDDD.persistence.gateway.ImplWeatherServiceGateway;


/**
 * Service class for handling weather-related operations.
 * This service provides methods to interact with weather data, such as fetching current temperature,
 * sunrise time, and sunset time. It utilizes an implementation of the {@link ImplWeatherServiceGateway}
 * to communicate with a weather service.
 * This class is annotated with {@link Service}, indicating that it is a Spring service component,
 * and thus eligible for Spring's component scanning and dependency injection mechanisms.
 */
@Service
public class ServiceWeather {

    /**
     * Gateway for accessing weather service data.
     * This field is used to interact with a weather service to fetch weather-related data.
     * It is initialized through dependency injection, ensuring that an appropriate implementation
     * of {@link ImplWeatherServiceGateway} is provided at runtime.
     * This field is marked as final, indicating that it cannot be reassigned once it has been initialized.
     */
    final ImplWeatherServiceGateway _weatherServiceGateway;

    /**
     * Constructor for ServiceWeather.
     * @param weatherServiceGateway Value of weatherServiceGateway.
     * @throws IllegalArgumentException if weatherServiceGateway is null.
     */
    public ServiceWeather(ImplWeatherServiceGateway weatherServiceGateway) {
        if (weatherServiceGateway == null) {
            throw new IllegalArgumentException("WeatherServiceGateway cannot be null");
        }
        _weatherServiceGateway = weatherServiceGateway;
    }

    /**
     * Get the current temperature from weather service.
     * @param lat Value of latitude.
     * @param lon Value of longitude.
     * @return the response from the weather service.
     */
    public Object getCurrentTemperature(String lat, String lon) {
        return _weatherServiceGateway.getcurrentTemperature(lat,lon).block();
    }

    /**
     * Get the sunrise time from weather service.
     * @param lat Value of latitude.
     * @param lon Value of longitude.
     * @return the response from the weather service.
     */
    public Object getSunrise(String lat, String lon) {
        return _weatherServiceGateway.getSunrise(lat,lon).block();
    }

    /**
     * Get the sunset time from weather service.
     * @param lat Value of latitude.
     * @param lon Value of longitude.
     * @return the response from the weather service.
     */
    public Object getSunset(String lat, String lon) {
        return _weatherServiceGateway.getSunset(lat,lon).block();
    }
}
