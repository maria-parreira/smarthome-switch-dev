package smartHomeDDD.controllersWeb;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.services.ServiceWeather;
import java.util.Map;

/**
 * This controller provides RESTful API endpoints for interacting with weather service data.
 * It offers functionalities to retrieve the current temperature, sunrise, and sunset times
 * based on geographical coordinates (latitude and longitude).
 * This class uses a {@link ServiceWeather} instance to fetch the weather data.
 * Annotated with {@link RestController} and {@link RequestMapping} to define the base path for all methods.
 */
@RestController
@RequestMapping(path = "/api/v1/weather")
public class WeatherServiceControllerWeb {

    /**
     * A service for interacting with weather-related data.
     */
    private final ServiceWeather _serviceWeather;

    /**
     * Constructs a new {@code WeatherServiceControllerWeb} with the specified {@code ServiceWeather}.
     * This constructor initializes the {@link ServiceWeather} instance used by this controller
     * to handle weather-related operations. The {@code serviceWeather} parameter is injected
     * to provide the necessary service for interacting with weather data.
     * @param serviceWeather the {@link ServiceWeather} instance to be used by this controller
     */
    public WeatherServiceControllerWeb(ServiceWeather serviceWeather) {
        _serviceWeather = serviceWeather;
    }

    /**
     * Get the current temperature
     *
     * @return the current temperature.
     */
    @GetMapping("/currentTemperature")
    public Object getCurrentTemperature(@RequestParam Map<String, String> Params) {
        String lat = Params.get("latitude");
        String lon = Params.get("longitude");
        return _serviceWeather.getCurrentTemperature(lat, lon);
    }

    /**
     * Get the sunset time
     *
     * @return the sunset time
     */
    @GetMapping("/sunrise")
    public Object getSunrise(@RequestParam Map<String, String> Params) {
        String lat = Params.get("latitude");
        String lon = Params.get("longitude");
        return _serviceWeather.getSunrise(lat, lon);
    }

    /**
     * Get the sunset time
     *
     * @return the sunset time
     */
    @GetMapping("/sunset")
    public Object getSunset(@RequestParam Map<String, String> Params) {
        String lat = Params.get("latitude");
        String lon = Params.get("longitude");
        return _serviceWeather.getSunset(lat, lon);
    }
}
