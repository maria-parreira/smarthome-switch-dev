package smartHomeDDD.domain.controllersWeb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This a test class for the SensorModelWebController class. It contains the following test scenarios:
 * - The retrieval of sensor models by sensor type is successful.
 * - The retrieval of sensor models by sensor type is an empty list due to a non-existing models for the given sensor type.
 * - The retrieval of a sensor model by its unique identifier is successful.
 * - The retrieval of a sensor model by its unique identifier is unsuccessful.
 */
@AutoConfigureMockMvc
@SpringBootTest
class SensorModelWebControllerTest {

    /**
     * The MockMvc object is used to simulate HTTP requests to the controller.
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRepositorySensorModel repositorySensorModel;

    /**
     * This method is called before each test execution.
     * It opens the Mockito annotations for the test class.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This test scenario tests the retrieval of sensor models by sensor type.
     */
    @Test
    void getModelsByType_shouldReturnListOfModels() throws Exception {
        // Arrange
        SensorModelID sensorModelID = new SensorModelID("GA100K");
        SensorTypeID sensorTypeID = new SensorTypeID("T1");
        SensorModel sensorModel = new SensorModel(sensorModelID, sensorTypeID);

        when(repositorySensorModel.getModelsBySensorType(sensorTypeID)).thenReturn(List.of(sensorModel));

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sensor-models?sensorTypeID=T1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        String expectedContent = """
                [
                    {
                        "sensorModelID": "GA100K",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/sensor-models/GA100K"
                            }
                        ]
                    }
                ]
                """;
        JSONAssert.assertEquals(expectedContent, content, false);
    }

    /**
     * This test scenario tests the retrieval of sensor models by sensor type when no models are found.
     */
    @Test
    void getModelsByType_shouldReturnEmptyList() throws Exception {
        // Arrange
        SensorTypeID sensorTypeID = new SensorTypeID("T1");

        when(repositorySensorModel.getModelsBySensorType(sensorTypeID)).thenReturn(List.of());

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sensor-models?sensorTypeID=T1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        String expectedContent = "[]";
        JSONAssert.assertEquals(expectedContent, content, false);
    }
    /**
     * This test scenario tests the retrieval of a sensor model by its unique identifier.
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getSensorModelByValidID_shouldReturnSensorModelID() throws Exception {
        //Arrange
        SensorModelID sensorModelID = new SensorModelID("GA100K");

        when(repositorySensorModel.ofIdentity(sensorModelID)).thenReturn(Optional.of(new SensorModel(sensorModelID, new SensorTypeID("T1"))));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-models/GA100K")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {"sensorModelID":"GA100K",
                   "_links":{"self":{"href":"http://localhost/api/v1/sensor-models/GA100K"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
    /**
     * This test scenario tests the retrieval of a sensor model by its unique identifier when the identifier is invalid.
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getSensorModelByInValidID_shouldReturnException() throws Exception {
        //Arrange
        SensorModelID sensorModelID = new SensorModelID("GA100K");

        when(repositorySensorModel.ofIdentity(sensorModelID)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-models/GA100K")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Sensor Model doesn't exist", resultContent);
    }
}
