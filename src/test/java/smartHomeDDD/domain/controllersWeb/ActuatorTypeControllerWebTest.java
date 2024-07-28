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
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.repository.IRepositoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The ActuatorTypeControllerWebTest class is responsible for testing the ActuatorTypeControllerWeb class.
 * In this test class, the ActuatorTypeControllerWeb class is tested by mocking the HTTP requests.
 * It tests the following scenarios:
 * - The retrieval of a list of actuator types is successful.
 * - The retrieval of an actuator type by its unique identifier is successful.
 * - The retrieval of an actuator type by its unique identifier is unsuccessful.
 */

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorTypeControllerWebTest {

    /**
     * The mock MVC object that is used to simulate HTTP requests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The repositoryActuatorType object is used to simulate the IRepositoryActuatorType.
     */
    @MockBean
    private IRepositoryActuatorType repositoryActuatorType;

    /**
     * The factoryActuatorType is used to instantiate the ActuatorType objects.
     */
    @Autowired
    private FactoryActuatorType factoryActuatorType;

    /**
     * This method sets up the test environment.
     */
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This test scenario should return a list of actuator types.
     */
    @Test
    void shouldReturnListOfActuatorTypes() throws Exception {
        //Arrange
        List<ActuatorType> actuatorTypes = Arrays.asList(
                factoryActuatorType.createActuatorType(new Unit("Value"), new Description("SetPrecisionValue"), new ActuatorTypeID("T4")),
                factoryActuatorType.createActuatorType(new Unit("OnOff"), new Description("Status"), new ActuatorTypeID("T1")),
                factoryActuatorType.createActuatorType(new Unit("Percentage"), new Description("BlindRoller"), new ActuatorTypeID("T2")),
                factoryActuatorType.createActuatorType(new Unit("Value"), new Description("SetIntegerValue"), new ActuatorTypeID("T3"))
                );

        when(repositoryActuatorType.findAll()).thenReturn(actuatorTypes);
        //Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuator-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        String content = result.getResponse().getContentAsString();
        String expected = """
            [
                         {
                             "actuatorTypeID": "T1",
                             "links": [
                                 {
                                     "rel": "self",
                                     "href": "http://localhost/api/v1/actuator-types/T1"
                                 }
                             ]
                         },
                         {
                             "actuatorTypeID": "T2",
                             "links": [
                                 {
                                     "rel": "self",
                                     "href": "http://localhost/api/v1/actuator-types/T2"
                                 }
                             ]
                         },
                         {
                             "actuatorTypeID": "T3",
                             "links": [
                                 {
                                     "rel": "self",
                                     "href": "http://localhost/api/v1/actuator-types/T3"
                                 }
                             ]
                         },
                         {
                             "actuatorTypeID": "T4",
                             "links": [
                                 {
                                     "rel": "self",
                                     "href": "http://localhost/api/v1/actuator-types/T4"
                                 }
                             ]
                         }
                     ]
            """;
        JSONAssert.assertEquals(expected, content, false);
    }

    /**
     * This test scenario should return an actuator type by its unique identifier.
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getActuatorTypeByValidID_shouldReturnAnActuatorTypeID() throws Exception {
        //Arrange
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T4");
        ActuatorType actuatorType1 = factoryActuatorType.createActuatorType(new Unit("Value"), new Description("SetPrecisionValue"), actuatorTypeID);
        when(repositoryActuatorType.ofIdentity(actuatorTypeID)).thenReturn(Optional.of(actuatorType1));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuator-types/T4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                "actuatorTypeID":"T4",
                "description":"SetPrecisionValue",
                "unit":"Value",
                   "_links":{"self":{"href":"http://localhost/api/v1/actuator-types/T4"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * This test scenario should return an actuator type by its unique identifier.
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getActuatorTypeByInvalidID_shouldReturnAnActuatorTypeID() throws Exception {
        //Arrange
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T4");
        when(repositoryActuatorType.ofIdentity(actuatorTypeID)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuator-types/T4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "Actuator Type not found.";
        assertEquals(expectedContent, resultContent);
    }


}
