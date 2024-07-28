package smartHomeDDD.domain.controllersWeb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.dto.SensorTypeEntryWebDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class is responsible for unit testing the SensorTypeWebController class. It verifies the following use cases:
 * - Successful creation of a valid sensor type
 * - Conflict when trying to add a sensor type with a duplicate ID
 * - Successful retrieval of a list of sensor types
 * - Verification of the updated list of sensor types after the addition of a new sensor type
 * - Successful retrieval of a sensor type using its unique ID
 * - Handling of a scenario where retrieval of a sensor type is attempted with a non-existent ID
 */
@SpringBootTest
@AutoConfigureMockMvc
class SensorTypeWebControllerTest {
    /**
     * The MockMvc object is used to simulate HTTP requests to the controller.
     */
    @Autowired
    private MockMvc mockMvc;
    /**
     * The repository of Sensor Types.
     */
    @MockBean
    private IRepositorySensorType repositorySensorType;

    /**
     * The objectMapper object is used to convert Java objects to JSON and vice versa.
     */
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * The factorySensorType object is used to create sensor types.
     */
    @Autowired
    private FactorySensorType factorySensorType;

    /**
     * This method creates a SensorType object from a SensorTypeWebDTO object.
     * @param sensorTypeWebDTO The SensorTypeWebDTO object used as input.
     * @return The created SensorType object.
     */
    private SensorType setupSensorType(SensorTypeEntryWebDTO sensorTypeWebDTO) {
        SensorTypeID sensorTypeID = new SensorTypeID(sensorTypeWebDTO.getSensorTypeID());
        Description description = new Description(sensorTypeWebDTO.getDescription());
        Unit unit = new Unit(sensorTypeWebDTO.getUnit());
        return factorySensorType.createSensorType(sensorTypeID, description, unit);
    }
    /**
     * This method is called before each test. It initializes the mocks.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * This method tests the creation of a valid sensor type. It should return a new sensor type.
     * @throws Exception If an error occurs.
     */
    @Test
    void validSensorTypeCreation_shouldReturnNewSensorType() throws Exception {
        SensorTypeEntryWebDTO sensorTypeWebDTO = new SensorTypeEntryWebDTO("T14", "Temperature", "m1");
        SensorType sensorType = setupSensorType(sensorTypeWebDTO);

        when(repositorySensorType.save(sensorType)).thenReturn(sensorType);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/sensor-types")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sensorTypeWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                    "sensorTypeID": "T14",
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/sensor-types/T14"
                        }
                    }
                }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
    /**
     * This method tests the creation of a sensor type with a duplicated ID. It should return a conflict status.
     * @throws Exception If an error occurs.
     */
    @Test
    void addSensorWithDuplicatedID_shouldReturnConflictStatus() throws Exception {
        SensorTypeEntryWebDTO sensorTypeWebDTO = new SensorTypeEntryWebDTO("T14", "Temperature", "m1");
        SensorType sensorType = setupSensorType(sensorTypeWebDTO);

        doThrow(new DataIntegrityViolationException("Sensor Type already exists")).when(repositorySensorType).save(sensorType);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/sensor-types")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sensorTypeWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Sensor Type already exists", resultContent);
    }
    /**
     * This method tests the retrieval of a list of sensor types. It should return a list of sensor types.
     * @throws Exception If an error occurs.
     */
    @Test
    void getSensorTypes_shouldReturnNonEmptyList() throws Exception {
        // Setup expected sensor types
        List<SensorType> sensorTypes = Arrays.asList(
                factorySensorType.createSensorType(new SensorTypeID("T4"), new Description("Status"), new Unit("OnOff")),
                factorySensorType.createSensorType(new SensorTypeID("T5"), new Description("WindSpeedAndDirection"), new Unit("Km/h")),
                factorySensorType.createSensorType(new SensorTypeID("T6"), new Description("Sunrise"), new Unit("LocalTime")),
                factorySensorType.createSensorType(new SensorTypeID("T10"), new Description("EnergyConsumption"), new Unit("Wh")),
                factorySensorType.createSensorType(new SensorTypeID("T7"), new Description("Sunset"), new Unit("LocalTime")),
                factorySensorType.createSensorType(new SensorTypeID("T8"), new Description("SolarIrradiance"), new Unit("W/m2")),
                factorySensorType.createSensorType(new SensorTypeID("T12"), new Description("DewPoint"), new Unit("Celsius")),
                factorySensorType.createSensorType(new SensorTypeID("T9"), new Description("PowerConsumption"), new Unit("W")),
                factorySensorType.createSensorType(new SensorTypeID("T11"), new Description("AveragePowerConsumption"), new Unit("W")),
                factorySensorType.createSensorType(new SensorTypeID("T1"), new Description("Temperature"), new Unit("Celsius")),
                factorySensorType.createSensorType(new SensorTypeID("T2"), new Description("Humidity"), new Unit("Percentage")),
                factorySensorType.createSensorType(new SensorTypeID("T3"), new Description("Position"), new Unit("Percentage"))
        );

        when(repositorySensorType.findAll()).thenReturn(sensorTypes);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
            [{
                 "sensorTypeID": "T4",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T4"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T5",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T5"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T6",
                "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T6"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T10",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T10"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T7",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T7"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T8",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T8"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T12",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T12"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T9",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T9"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T11",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T11"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T1",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T1"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T2",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T2"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T3",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T3"
                              }
                          ]
             }]
            """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * This method tests the creation of a sensor type. It should return an updated list of sensor types.
     * @throws Exception If an error occurs.
     */
    @Test
    void addSensorType_shouldReturnListOfSensorTypes() throws Exception {
        SensorTypeEntryWebDTO sensorTypeWebDTO = new SensorTypeEntryWebDTO("T14", "Temperature", "m1");
        SensorType sensorType = setupSensorType(sensorTypeWebDTO);

        when(repositorySensorType.save(sensorType)).thenReturn(sensorType);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sensor-types")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sensorTypeWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<SensorType> sensorTypes = Arrays.asList(
                factorySensorType.createSensorType(new SensorTypeID("T14"), new Description("Temperature"), new Unit("m1")),
                factorySensorType.createSensorType(new SensorTypeID("T4"), new Description("Status"), new Unit("OnOff")),
                factorySensorType.createSensorType(new SensorTypeID("T5"), new Description("WindSpeedAndDirection"), new Unit("Km/h")),
                factorySensorType.createSensorType(new SensorTypeID("T6"), new Description("Sunrise"), new Unit("LocalTime")),
                factorySensorType.createSensorType(new SensorTypeID("T10"), new Description("EnergyConsumption"), new Unit("Wh")),
                factorySensorType.createSensorType(new SensorTypeID("T7"), new Description("Sunset"), new Unit("LocalTime")),
                factorySensorType.createSensorType(new SensorTypeID("T8"), new Description("SolarIrradiance"), new Unit("W/m2")),
                factorySensorType.createSensorType(new SensorTypeID("T12"), new Description("DewPoint"), new Unit("Celsius")),
                factorySensorType.createSensorType(new SensorTypeID("T9"), new Description("PowerConsumption"), new Unit("W")),
                factorySensorType.createSensorType(new SensorTypeID("T11"), new Description("AveragePowerConsumption"), new Unit("W")),
                factorySensorType.createSensorType(new SensorTypeID("T1"), new Description("Temperature"), new Unit("Celsius")),
                factorySensorType.createSensorType(new SensorTypeID("T2"), new Description("Humidity"), new Unit("Percentage")),
                factorySensorType.createSensorType(new SensorTypeID("T3"), new Description("Position"), new Unit("Percentage"))
        );

        when(repositorySensorType.findAll()).thenReturn(sensorTypes);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
            [{
                 "sensorTypeID": "T4",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T4"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T5",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T5"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T6",
                "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T6"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T10",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T10"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T7",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T7"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T8",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T8"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T12",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T12"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T9",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T9"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T11",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T11"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T14",
                 "links": [
                     {
                         "rel": "self",
                         "href": "http://localhost/api/v1/sensor-types/T14"
                     }
                 ]
             },
             {
                 "sensorTypeID": "T1",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T1"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T2",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T2"
                              }
                          ]
             },
             {
                 "sensorTypeID": "T3",
                 "links": [
                              {
                                  "rel": "self",
                                  "href": "http://localhost/api/v1/sensor-types/T3"
                              }
                          ]
             }]
            """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
    /**
     * This method tests the retrieval of a sensor type by its ID. It should return the sensor type.
     * @throws Exception If an error occurs.
     */
    @Test
    void getSensorTypeByValidID_shouldReturnASensorTypeID() throws Exception {
        //Arrange
        SensorTypeID sensorTypeID = new SensorTypeID("T4");
        SensorType sensorType1 = factorySensorType.createSensorType(sensorTypeID, new Description("Status"), new Unit("OnOff"));
        when(repositorySensorType.ofIdentity(sensorTypeID)).thenReturn(Optional.of(sensorType1));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-types/T4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                "sensorTypeID":"T4",
                "description":"Status",
                "unit":"OnOff",
                   "_links":{"self":{"href":"http://localhost/api/v1/sensor-types/T4"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
    /**
     * This method tests the retrieval of a sensor type by an invalid ID.
     * @throws Exception If an error occurs.
     */
    @Test
    void getSensorTypeByInvalidID_shouldThrowException() throws Exception {
        //Arrange
        SensorTypeID sensorTypeID = new SensorTypeID("T4");
        when(repositorySensorType.ofIdentity(sensorTypeID)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-types/T4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Sensor Type not found", resultContent);
    }
}

