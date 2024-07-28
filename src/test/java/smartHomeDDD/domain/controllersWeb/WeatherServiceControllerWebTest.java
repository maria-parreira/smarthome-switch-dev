package smartHomeDDD.domain.controllersWeb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import smartHomeDDD.services.ServiceWeather;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

/**
 * Test class for WeatherServiceControllerWeb
 * It uses MockMvc to simulate the HTTP requests
 * It tests the following scenarios:
 * - retrieve the current temperature for a given latitude and longitude
 * - retrieve the sunrise time for a given latitude and longitude
 * - retrieve the sunset time for a given latitude and longitude
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherServiceControllerWebTest {

    /**
     * MockMvc is the main entry point for server-side Spring MVC test support.
     * It allows to test Spring MVC applications without starting a full HTTP server.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * MockBean is used to add mocks to a Spring ApplicationContext.
     * A mock of the ServiceWeather is created and injected into the WeatherServiceControllerWeb.
     */
    @MockBean
    private ServiceWeather serviceWeather;

    /**
     * Test to retrieve the current temperature for a given latitude and longitude
     */
    @Test
    public void testGetCurrentTemperature() throws Exception {
        when(serviceWeather.getCurrentTemperature("40.7128", "74.0060")).thenReturn(20.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather/currentTemperature")
                        .param("latitude", "40.7128")
                        .param("longitude", "74.0060")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test to retrieve the sunrise time for a given latitude and longitude
     */
    @Test
    public void testGetSunrise() throws Exception {
        when(serviceWeather.getSunrise("40.7128", "74.0060")).thenReturn("06:00");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather/sunrise")
                        .param("latitude", "40.7128")
                        .param("longitude", "74.0060")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test to retrieve the sunset time for a given latitude and longitude
     */
    @Test
    public void testGetSunset() throws Exception {
        when(serviceWeather.getSunset("40.7128", "74.0060")).thenReturn("20:00");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather/sunset")
                        .param("latitude", "40.7128")
                        .param("longitude", "74.0060")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

