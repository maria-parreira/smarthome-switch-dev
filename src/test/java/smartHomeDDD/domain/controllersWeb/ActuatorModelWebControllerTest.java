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
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.*;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is a test class for the ActuatorModelWebController class. It contains the following test scenarios:
 * - The retrieval of actuator models by actuator type is successful.
 * - The retrieval of actuator models is unsuccessful due to a non-existing actuator type as input.
 * - The retrieval of an actuator model by its unique identifier is successful.
 * - The retrieval of an actuator model by its unique identifier is unsuccessful.
 */

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorModelWebControllerTest {
    /**
     * The mock MVC object that is used to simulate HTTP requests.
     */
    @Autowired
    private MockMvc mockMvc;
    /**
     * The mock object of the repository for actuator models.
     */
    @MockBean
    private IRepositoryActuatorModel repositoryActuatorModel;
    /**
     * The factory for actuator models.
     */
    @Autowired
    private FactoryActuatorModel factoryActuatorModel;

    /**
     * This method sets up the testing environment before each test is run. It initializes the mock objects.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This test scenario tests the retrieval of actuator models by actuator type. The test is successful.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getModelsByType_shouldReturnListOfModels() throws Exception {
        // Arrange
        ActuatorModel actuatorModel1 = factoryActuatorModel.createActuatorModel(new ActuatorModelID("OPNCL0100"), new ActuatorTypeID("T2"));
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T2");

        when(repositoryActuatorModel.getModelsByActuatorType(actuatorTypeID)).thenReturn(Arrays.asList(actuatorModel1));

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/actuator-models?actuatorTypeID=T2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
     [
         {
             "actuatorModelID": "OPNCL0100",
             "links": [
                 {
                     "rel": "self",
                     "href": "http://localhost/api/v1/actuator-models/OPNCL0100"
                 }
             ]
         }
     ]
    """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * This test scenario tests the retrieval of actuator models by actuator type. The test is unsuccessful due to a non-existing actuator type as input.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getModelsByNonExistingType_shouldReturnEmptyList() throws Exception {

        //Arrange
        ActuatorModel actuatorModel1 = factoryActuatorModel.createActuatorModel(new ActuatorModelID("OPNCL0100"), new ActuatorTypeID("T2"));
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T2");

        when(repositoryActuatorModel.getModelsByActuatorType(actuatorTypeID)).thenReturn(Arrays.asList(actuatorModel1));
        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/actuator-models?actuatorTypeID=T5", "NonExistingType")
                        .param("actuatorTypeID", "NonExistingType")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "[]";

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests whether the ActuatorModelWebController class can retrieve an actuator model by its unique identifier.
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getValidActuator_shouldReturnAnActuatorID() throws Exception {
        //Arrange
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        ActuatorModel actuatorModel1 = factoryActuatorModel.createActuatorModel(actuatorModelID, new ActuatorTypeID("T2"));

        when(repositoryActuatorModel.ofIdentity(actuatorModelID)).thenReturn(Optional.of(actuatorModel1));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuator-models/OPNCL0100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {"actuatorModelID":"OPNCL0100",
                   "_links":{"self":{"href":"http://localhost/api/v1/actuator-models/OPNCL0100"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests a scenario in which the ActuatorModelWebController class cannot retrieve an actuator model by its unique identifier.
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getValidActuatorWithInvalidID_shouldReturnException() throws Exception {
        //Arrange
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        when(repositoryActuatorModel.ofIdentity(actuatorModelID)).thenReturn(Optional.empty());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuator-models/OPNCL0100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "Actuator Model not found.";
        assertEquals(expectedContent, resultContent);
    }

}

