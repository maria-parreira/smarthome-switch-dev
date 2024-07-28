package smartHomeDDD.persistence.gateway;
import reactor.core.publisher.Mono;
import smartHomeDDD.ddd.Gateway;


/**
 * Interface for a Weather Service Gateway.
 * This interface provides methods to interact with a weather service,
 * retrieving current temperature, sunrise time, and sunset time based on
 * geographical coordinates (latitude and longitude).
 * It extends the {@link Gateway} interface and uses reactive programming with
 * {@link Mono} to handle asynchronous operations.
 */
public interface ImplWeatherServiceGateway extends Gateway {

    /**
     * Retrieves the current temperature for the specified geographical coordinates.
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * @return a {@link Mono} emitting the current temperature at the specified location
     */
    public Mono<Object> getcurrentTemperature(String lat, String lon);

    /**
     * Retrieves the sunrise time for the specified geographical coordinates.
     *
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * @return a {@link Mono} emitting the sunrise time at the specified location
     */
    public Mono<Object> getSunrise(String lat, String lon);

    /**
     * Retrieves the sunset time for the specified geographical coordinates.
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * @return a {@link Mono} emitting the sunset time at the specified location
     */
    public Mono<Object> getSunset(String lat, String lon);
}
