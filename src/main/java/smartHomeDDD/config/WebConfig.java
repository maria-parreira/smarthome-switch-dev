package smartHomeDDD.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;

@Configuration
public class WebConfig {
    /**
     * Create a web client
     * Base URL points to Group7 Weather Service
     */
    @Bean
    public WebClient webClient() {

        String IPAdress="";
        String PATHNAME = "config.properties";
        try {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            IPAdress = config.getString("WeatherService");

        } catch (Exception e) {
            System.out.println("Error in reading config file");
        }

        return WebClient.builder().baseUrl(IPAdress)
                .defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}