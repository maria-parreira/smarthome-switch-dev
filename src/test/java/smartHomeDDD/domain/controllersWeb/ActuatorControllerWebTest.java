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
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.FactoryActuator;
import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.GenerateRandomId;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains tests that evaluate the behavior and performance of the ActuatorControllerWeb class.
 * It tests the following scenarios:
 * - Creating and adding a new actuator to a device.
 * - Attempting to add the same actuator to the same device.
 * - Attempting to add an actuator with a non-existing device ID.
 *  - Updating the actuator value to close a roller blind.
 *  - Updating the actuator value with the highest number possible.
 *  - Updating the actuator value with a value higher than the maximum.
 *  - Updating the actuator value with a value lower than the minimum.
 *  - Attempting to update the actuator value when the actuator is not in the device.
 *  - Attempting to update the actuator value when the sensor is not in the device.
 *  - Attempting to update the actuator value when the sensor doesn't exist.
 *  - Attempting to update the actuator value when the actuator doesn't exist.
 *  - Attempting to update the actuator value when the sensor doesn't have a valid model.
 *  - Attempting to update the actuator value when the actuator doesn't have a valid model.
 *  - Retrieving an actuator successfully, based on its id.
 *  - Attempting to retrieve an actuator that doesn't exist.
 */

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class ActuatorControllerWebTest {
    /**
     * MockMvc instance for simulating HTTP requests.
     */
    @Autowired
    private MockMvc mockMvc;
    /**
     * ObjectMapper instance for converting Java objects to JSON and vice versa.
     */
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Mock of IRepositoryActuator for simulating actuator repository operations.
     */
    @MockBean
    private IRepositoryActuator repositoryActuator;
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
     * FactorySensor instance for creating Sensor objects.
     */
    @Autowired
    private FactorySensor factorySensor;
    /**
     * FactoryActuator instance for creating Actuator objects.
     */
    @Autowired
    private FactoryActuator factoryActuator;

    /**
     * GenerateRandomId instance for generating IDs.
     */
    @MockBean
    private GenerateRandomId generateRandomId;



    /**
     * Sets up a {@link Actuator} object based on the provided {@link ActuatorEntryWebDTO}.
     *
     * @param actuatorEntryWebDTO The DTO object containing sensor information.
     * @return A {@link Actuator} object created from the provided DTO.
     */
    private Actuator setupActuator(ActuatorEntryWebDTO actuatorEntryWebDTO) {
        return factoryActuator.createActuator(
                new ActuatorID(generateRandomId.generateID()),
                new DeviceId(actuatorEntryWebDTO.getDeviceID()),
                new ActuatorModelID(actuatorEntryWebDTO.getActuatorModelID())

        );
    }

    /**
     * Sets up a {@link Sensor} object based on the provided {@link SensorDTO}.
     *
     * @param sensorDTO The DTO object containing sensor information.
     * @return A {@link Sensor} object created from the provided DTO.
     */
    private Sensor setupSensor(SensorDTO sensorDTO) {
        return factorySensor.createSensor(
                new DeviceId(sensorDTO.getDeviceID()),
                new SensorModelID(sensorDTO.getSensorModelID()),
                new SensorID(sensorDTO.getSensorID())
        );
    }


    /**
     * Sets up the test environment before each test method execution.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the scenario where an actuator is created and added to a device successfully.
     * This test method performs the following steps:
     * Sends a GET request to retrieve actuators by device ID, expecting a 404 (Not Found) status code.
     * Sends a POST request to create and add a new actuator to a device, expecting a 201 (Created) status code
     * Verifies the response content of the POST request to ensure the actuator is created successfully.
     * Sends another GET request to retrieve actuators by device ID, expecting a 200 (OK) status code.
     * Verifies the response content of the second GET request to ensure the actuator is retrieved successfully.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void shouldCreateAndAddActuatorToDevice() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("10", "ONF01A");
        when(generateRandomId.generateID()).thenReturn("2");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryDevice.containsOfIdentity(actuator.getDeviceID())).thenReturn(true);
        when(repositoryActuator.save(any())).thenReturn(actuator);

        // Act : Second call -  POST to create and add new actuator to device
        MvcResult result2 = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/actuators")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(actuatorEntryWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // 201
                .andReturn();

        String resultContent = result2.getResponse().getContentAsString();

        //Assert : Second call
        assertNotNull(resultContent);
        String expectedContent = """
                {
                 "actuatorID":"2",
                 "deviceID":"10",
                 "actuatorModelID":"ONF01A",
                   "_links":{
                        "self":{
                            "href":"http://localhost/api/v1/actuators/2"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }


    /**
     * Tests the scenario where attempting to add the same actuator to a device results in a conflict status.
     * This test method performs the following steps:
     * Adds an actuator to a device.
     * Attempts to add the same actuator to the same device again.
     * Expects a 409 (Conflict) status code indicating that the actuator already exists.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void addSameActuatorToDevice_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("10", "ONF01A");
        when(generateRandomId.generateID()).thenReturn("2");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);
        when(repositoryDevice.containsOfIdentity(actuator.getDeviceID())).thenReturn(true);
        when(repositoryActuator.save(any(Actuator.class))).thenReturn(actuator);

        // Add a actuator.
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/actuators")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(actuatorEntryWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // 201
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        //Assert : Check if actuator is added successfully.
        assertNotNull(resultContent);
        String expectedContent = """
                {
                    "actuatorID":"2",
                    "deviceID":"10",
                    "actuatorModelID":"ONF01A",
                   "_links":{
                        "self":{
                            "href":"http://localhost/api/v1/actuators/2"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);

        // Try to add same actuator.
        doThrow(new DataIntegrityViolationException("Actuator already exists")).when(repositoryActuator).save(any(Actuator.class));
        MvcResult result2 =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/actuators")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(actuatorEntryWebDTO))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict()) // 409
                        .andReturn();

        // Assert
        String resultContent2 = result2.getResponse().getContentAsString();
        String expectedContent2 = "Actuator already exists";
        assertEquals(expectedContent2, resultContent2);
    }

    /**
     * Tests the scenario where attempting to add an actuator with a non-existing device ID results in a not found status.
     * This test method performs the following steps:
     * Attempts to add an actuator with a non-existing device ID.
     * Expects a 404 (Not Found) status code indicating that the device was not found.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void addActuatorWithNonExistingDeviceID_shouldReturnBadRequestStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("10", "ONF01A");
        when(generateRandomId.generateID()).thenReturn("actuator1000");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);
        when(repositoryDevice.containsOfIdentity(actuator.getDeviceID())).thenReturn(false);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/actuators")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(actuatorEntryWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        //Assert
        String expectedContent = "Device not found.";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario where attempting to update the actuator value,to close a roller blind.
     * This test method performs the following steps:
     * Attempts to update the actuator value to a lower number than the latest sensor reading.
     * Expects a 200 (Ok) status code indicating that the actuator was updated successfully.
     */

    @Test
    void patchActuatorWithMinimumValue_shouldUpdateActuatorValue() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);
        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(java.util.Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);
        when(repositorySensor.containsOfIdentity(any())).thenReturn(true);
        when(repositoryDevice.containsOfIdentity(any())).thenReturn(true);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "0");

        // PATCH to update actuator value.
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        //Assert : Check if actuator value is updated.
        assertNotNull(resultContent);
        String expectedContent = """
                {
                "actuatorID":"a1",
                "deviceID":"d1",
                "actuatorModelID":"OPNCL0100",
                   "_links":{
                        "self":{
                            "href":"http://localhost/api/v1/actuators/a1"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the scenario where attempting to update the actuator value with the highest number possible.
     * Expects a 200 (Ok) status code indicating that the actuator was updated successfully.
     *
     * @throws Exception if an error occurs during the test.
     */

    @Test
    void patchActuatorWithMaximumValue_shouldUpdateActuatorValue() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(java.util.Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);
        when(repositorySensor.containsOfIdentity(any())).thenReturn(true);
        when(repositoryDevice.containsOfIdentity(any())).thenReturn(true);


        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "100");

        // PATCH to update actuator value.
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        //Assert : Check if actuator value is updated.
        assertNotNull(resultContent);
        String expectedContent = """
                {
                "actuatorID":"a1",
                "deviceID":"d1",
                "actuatorModelID":"OPNCL0100",
                   "_links":{
                        "self":{
                            "href":"http://localhost/api/v1/actuators/a1"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the scenario where is attempted to update the actuator value with a value higher than the maximum.
     * Expects a BadRequest status code indicating that the actuator could not be updated.
     */
    @Test
    void patchActuatorWithValueHigherThanMaximum_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "110");

        // PATCH to update actuator value.
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        String expectedContent = "Input value not valid."; //The actuator value must be between 0 and 100
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario where is attempted to update the actuator value with a value lower than the minimum.
     * Expects a BadRequest status code indicating that the actuator could not be updated.
     */
    @Test
    void patchActuatorWithValueLowerThanMinimum_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "-10");

        // PATCH to update actuator value.
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        String expectedContent = "Input value not valid."; //The actuator value must be between 0 and 100
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario when the actuator is not in the device.
     *
     * @throws Exception if an error occurs during the test.
     * A BadRequest status code is expected
     */
    @Test
    void noActuatorInDevice_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d2", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "10");

        // PATCH to update actuator value.
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        String expectedContent = "The sensor and/or actuator are not from the same device.";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario when the sensor is not in the device.
     *
     * @throws Exception if an error occurs during the test.
     * A BadRequest status code is expected
     */
    @Test
    void noSensorInDevice_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        //Actuator actuator = factoryActuator.createActuator(new ActuatorID("a1"), new DeviceId("d1"), new ActuatorModelID("OPNCL0100"));
        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d2", "CAP200"); // Different DeviceID
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "10");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "The sensor and/or actuator are not from the same device.";
        assertEquals(expectedContent, resultContent);
    }


    /**
     * Tests the scenario when the sensor doesn't exist.
     *
     * @throws Exception if an error occurs during the test.
     * A not found status code is expected (404)
     */
    @Test
    void noSensorFound_shouldReturnNotFoundStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.empty());

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "DoesntExist", "10");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        //Assert : Should return Conflict Status with error message from method canRollerBlindBeClosed.
        String expectedContent = "Sensor not found.";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario when the actuator doesn't exist.
     *
     * @throws Exception if an error occurs during the test.
     *  A not found status code is expected (404)
     */
    @Test
    void noActuatorFound_shouldReturnNotFoundStatus() throws Exception {
        //Arrange
        new SensorID("s1");
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.empty());
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "10");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        //Assert : Should return Conflict Status with error message from method canRollerBlindBeClosed.
        String expectedContent = "Actuator not found.";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario when the sensor doesn't have a valid model.
     *
     * @throws Exception if an error occurs during the test.
     * A BadRequest status code is expected
     */
    @Test
    void incorrectSensorModel_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "OPNCL0100");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "TSY01");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "10");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        //Assert : Should return Conflict Status with error message from method canRollerBlindBeClosed.
        String expectedContent = "The actuator and/or sensor doesn't have the correct model.";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the scenario when the actuator doesn't have a valid model.
     *
     * @throws Exception if an error occurs during the test.
     * A BadRequest status code is expected
     */
    @Test
    void incorrectActuatorModel_shouldReturnConflictStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "ONF01A");
        when(generateRandomId.generateID()).thenReturn("a1");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.of(actuator));
        when(repositoryActuator.update(actuator)).thenReturn(actuator);

        SensorDTO sensorDTO = new SensorDTO("s1", "d1", "CAP200");
        Sensor sensor = setupSensor(sensorDTO);
        when(repositorySensor.ofIdentity(sensor.identity())).thenReturn(java.util.Optional.of(sensor));

        OPNCLEntryDTO entryDTO = new OPNCLEntryDTO("a1", "s1", "10");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/actuators/a1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entryDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        //Assert : Should return Conflict Status with error message from method canRollerBlindBeClosed.
        String expectedContent = "The actuator and/or sensor doesn't have the correct model.";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests if an actuator is retrieved successfully, based on its id.
     * A 200 (OK) status code is expected, along with the retrieved actuator.
     */
    @Test
    void getValidActuator_shouldReturnAnActuator() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "ONF01A");
        when(generateRandomId.generateID()).thenReturn("actuator2");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(java.util.Optional.of(actuator));

        // Act && Assert
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuators/actuator2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                "actuatorID":"actuator2",
                "deviceID":"d1",
                "actuatorModelID":"ONF01A",
                   "_links":{
                        "self":{
                            "href":"http://localhost/api/v1/actuators/actuator2"}}}
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests if exception is thrown when trying to retrieve an actuator that doesn't exist.
     * A 404 (Not Found) status code is expected, along with an error message.
     */
    @Test
    void getInvalidActuator_shouldReturnNotFoundStatus() throws Exception {
        //Arrange
        ActuatorEntryWebDTO actuatorEntryWebDTO = new ActuatorEntryWebDTO("d1", "ONF01A");
        when(generateRandomId.generateID()).thenReturn("2");
        Actuator actuator = setupActuator(actuatorEntryWebDTO);

        when(repositoryActuator.ofIdentity(actuator.identity())).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/actuators/wrongActuatorID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Assert : Second call
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "Actuator not found.";
        assertEquals(expectedContent, resultContent);
    }

}



