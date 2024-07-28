package smartHomeDDD.persistence.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class WeatherServiceGateway implements ImplWeatherServiceGateway {

    private final WebClient webClient;

    /**
     * Constructor
     *
     * @param webClient the web client
     */
    @Autowired
    public WeatherServiceGateway(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Get the current temperature from the weather service
     *
     * @return the current temperature
     */
    public Mono<Object> getcurrentTemperature(String lat, String lon) {
        int hour = LocalDateTime.now().getHour();
        return webClient.get()
                .uri("/InstantaneousTemperature?groupNumber=2&latitude=" + lat + "&longitude=" + lon + "&hour=" + hour)
                .retrieve()
                .bodyToMono(Object.class);
    }

    /**
     * Get the sunrise time
     *
     * @return the sunrise time
     */
    public Mono<Object> getSunrise(String lat, String lon) {
        return webClient.get()
                .uri("/SunriseOrSunsetTime?groupNumber=2&latitude=" + lat + "&longitude=" + lon + "&option=sunrise")
                .retrieve()
                .bodyToMono(Object.class);
    }

    /**
     * Get the sunset time from the weather service
     *
     * @return the sunset time
     */
    public Mono<Object> getSunset(String lat, String lon) {
        return webClient.get()
                .uri("/SunriseOrSunsetTime?groupNumber=2&latitude=" + lat + "&longitude=" + lon + "&option=sunset")
                .retrieve()
                .bodyToMono(Object.class);
    }
}


