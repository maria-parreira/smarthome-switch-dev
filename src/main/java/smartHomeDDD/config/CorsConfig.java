package smartHomeDDD.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to define global Cross-Origin Resource Sharing (CORS) settings.
 * This configuration ensures that the application only allows requests from specific trusted origins.
 */
@Component
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Allow requests from the following methods
                        .allowedMethods("GET", "POST", "PUT", "PATCH");
            }
        };
    }
}

