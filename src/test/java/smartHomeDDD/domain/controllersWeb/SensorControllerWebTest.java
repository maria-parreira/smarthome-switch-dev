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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.SensorEntryWebDTO;
import smartHomeDDD.dto.SensorWebDTO;
import smartHomeDDD.services.GenerateRandomId;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the controller layer.
 * This class performs integration tests for the controller layer using Spring's MockMvc framework.
 * It verifies the behavior of the controllers by sending HTTP requests and validating the responses.
 * Each test method corresponds to a specific scenario or endpoint, ensuring proper functioning of the controllers.
 * It contains the following test scenarios:
 * - Create and add a sensor to a device successfully.
 * - Add a duplicated sensor to a device.
 * - Add a sensor to a non-existing device.
 * - Obtain a sensor by a valid ID.
 * - Obtain a sensor by an invalid ID.
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class SensorControllerWebTest {

    /**
     * MockMvc instance for simulating HTTP requests and generating responses.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * ObjectMapper instance for converting Java objects to JSON and vice versa.
     */
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Mock of IRepositorySensor for simulating sensor repository operations.
     */
    @MockBean
    private IRepositorySensor repositorySensor;
    /**
     * Mock of IRepositoryDevice for simulating device repository operations.
     */
    @MockBean
    private IRepositoryDevice repositoryDevice;
    /**
     * Mock of GenerateRandomId for generating random IDs.
     */
    @MockBean
    private GenerateRandomId generateRandomId;

    /**
     * FactorySensor instance for creating Sensor objects.
     */
    @Autowired
    private FactorySensor factorySensor;
    /**
     * Method that is executed before each test. It opens the mocks.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Sets up a {@link Sensor} object based on the provided {@link SensorWebDTO}.
     *
     * @param sensorWebDTO The DTO object containing sensor information.
     * @return A {@link Sensor} object created from the provided DTO.
     */
    private Sensor setupSensor(SensorEntryWebDTO sensorWebDTO) {
        SensorID sensorID = new SensorID(generateRandomId.generateID());
        DeviceId deviceID = new DeviceId(sensorWebDTO.getDeviceID());
        SensorModelID sensorModelID = new SensorModelID(sensorWebDTO.getSensorModelID());
        return factorySensor.createSensor(deviceID, sensorModelID, sensorID);
    }

    /**
     * Tests the scenario where a sensor is created and added to a device successfully.
     * This test method performs the following steps:
     * Sends a POST request to create and add a new sensor to a device, expecting a 201 (Created) status code
     * Verifies the response content of the POST request to ensure the sensor is created successfully.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void shouldCreateAndAddSensorToDevice() throws Exception {

        SensorEntryWebDTO sensorWebDTO = new SensorEntryWebDTO("2", "GA100K");
        when(generateRandomId.generateID()).thenReturn("10");
        Sensor sensor = setupSensor(sensorWebDTO);
        when(repositoryDevice.containsOfIdentity(sensor.getDeviceID())).thenReturn(true);
        when(repositorySensor.save(sensor)).thenReturn(sensor);

        MvcResult result2 = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/sensors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sensorWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultContent = result2.getResponse().getContentAsString();

        assertNotNull(resultContent);
        String expectedContent = """
                {"sensorID":"10",
                 "deviceID":"2",
                 "sensorModelID":"GA100K",
                   "_links":{
                        "self":{
                            "href":"http://localhost/api/v1/sensors/10"}
                        }
                    }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the scenario where attempting to add the same sensor to a device results in a conflict status.
     * This test method performs the following steps:
     * Adds a sensor to a device.
     * Attempts to add the same sensor to the same device again.
     * Expects a 409 (Conflict) status code indicating that the sensor already exists.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void addDuplicatedSensorToDevice_shouldReturnConflictStatus() throws Exception {

        //Arrange
        SensorEntryWebDTO sensorWebDTO = new SensorEntryWebDTO("10", "GA100K");
        when(generateRandomId.generateID()).thenReturn("5" );
        Sensor sensor = setupSensor(sensorWebDTO);
        when(repositoryDevice.containsOfIdentity(sensor.getDeviceID())).thenReturn(true);
        doThrow(new DataIntegrityViolationException("Sensor already exists")).when(repositorySensor).save(sensor);

        // Act
        MvcResult result1 =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sensors")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(sensorWebDTO))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict())
                        .andReturn();
        String resultContent = result1.getResponse().getContentAsString();

        // Assert : Second Call
        String expectedContent = "Sensor already exists";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario where attempting to add a sensor to a non-existing device results in a bad request status.
     * This test method performs the following steps:
     * Attempts to add a sensor to a device that does not exist.
     * Expects a 400 (Bad Request) status code indicating that the device does not exist.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void addSensorWithNonExistingDeviceID_shouldReturnBadRequestStatus() throws Exception {

        //Arrange
        SensorEntryWebDTO sensorWebDTO = new SensorEntryWebDTO( "10", "GA100K");
        when(generateRandomId.generateID()).thenReturn("5");
        Sensor sensor = setupSensor(sensorWebDTO);
        when(repositoryDevice.containsOfIdentity(sensor.getDeviceID())).thenReturn(false);

        // Act
        MvcResult result1 =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sensors")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(sensorWebDTO))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andReturn();
        String resultContent = result1.getResponse().getContentAsString();

        // Assert : Second Call
        String expectedContent = "Device not found";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Test case for obtaining a sensor by a valid ID.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void obtainSensorByValidID_shouldReturnSensorID() throws Exception {
        //Arrange
        SensorEntryWebDTO sensorWebDTO = new SensorEntryWebDTO( "2", "GA100K");
        when(generateRandomId.generateID()).thenReturn("testSensorID" );
        Sensor sensor = setupSensor(sensorWebDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(Optional.of(sensor));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensors/testSensorID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                 {
                     "sensorID": "testSensorID",
                     "deviceID": "2",
                     "sensorModelID": "GA100K",
                     "_links": {
                         "self": {
                             "href": "http://localhost/api/v1/sensors/testSensorID"
                         }
                     }
                 }
               """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
    /**
     * Test case for obtaining a sensor by an invalid ID.
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void obtainSensorByInvalidID_shouldReturnEntityNotFoundException() throws Exception {
        // Arrange
        SensorID sensorID = new SensorID("sensorID");
        when(repositorySensor.ofIdentity(sensorID)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensors/sensorID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Sensor not found.", resultContent);
    }

}
