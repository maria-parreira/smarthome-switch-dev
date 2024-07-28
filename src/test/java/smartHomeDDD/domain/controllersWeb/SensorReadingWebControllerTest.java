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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.persistence.springdata.RepositoryHouseSpringData;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * This class tests the SensorReadingWebController class. It contains the following tests scenarios:
 * - getSensorReadingsFromDevice_shouldReturnListOfSensorReadings
 * - noReadingsWithDeviceID_shouldReturnEmptyList
 * - noReadingsInTimePeriod_shouldReturnEmptyList
 * - getValidReadingsFromDeviceWithinPeriod_shouldReturnMaximumTemperatureDifference
 * - noReadingsFromDeviceWithinPeriod_shouldThrowException
 * - getSensorReadingByID_shouldReturnSensorReading
 * - getSensorReadingByIDNonExistent_shouldReturnEntityNotFound
 * - noDifferenceBetweenReadings_shouldReturnZero
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class SensorReadingWebControllerTest {

    /**
     * The MockMvc object is used to simulate HTTP requests to the API.
     */
    @Autowired
    private MockMvc mockMvc;
    /**
     * The repositorySensorReading object is a mock used to simulate the repository of sensor readings.
     */
    @MockBean
    private IRepositorySensorReading repositorySensorReading;
    /**
     * The repositorySensor object is a mock used to simulate the repository of sensors.
     */
    @MockBean
    private IRepositorySensor repositorySensor;
    /**
     * The factorySensorReading object is used to create sensor readings.
     */
    @Autowired
    private FactorySensorReading factorySensorReading;

    /**
     * The factorySensor object is used to create sensors.
     */
    @Autowired
    private FactorySensor factorySensor;

    /**
     * The repositoryHouseSpringData object is a mock used to simulate the repository of houses.
     */
    @MockBean
    RepositoryHouseSpringData repositoryHouseSpringData;

    /**
     * The repositoryDevice object is a mock used to simulate the repository of devices.
     */
    @MockBean
    private IRepositoryDevice repositoryDevice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This method creates a sensor reading with the given parameters.
     * @param id the sensor reading ID.
     * @param value the sensor reading value.
     * @param deviceID the device ID.
     * @param sensorID the sensor ID.
     * @param timestamp the timestamp of the sensor reading.
     * @return the sensor reading object.
     */
    private SensorReading setUpSensorReading(String id, String value, String deviceID, String sensorID, String timestamp) {
        SensorReadingID sensorReadingID = new SensorReadingID(id);
        Reading reading = new Reading(value);
        DeviceId device = new DeviceId(deviceID);
        SensorID sensor = new SensorID(sensorID);
        Timestamp time = Timestamp.valueOf(timestamp);
        return factorySensorReading.createSensorReading(sensorReadingID, reading, device, sensor, time);
    }

    /**
     * Tests the endpoint for obtaining sensor readings from a device within a given time period.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void getSensorReadingsFromDevice_shouldReturnListOfSensorReadings() throws Exception {
        //Arrange
        String deviceID = "d1";
        LocalDateTime startTimeString = LocalDateTime.parse("2021-01-01T11:00:00");
        LocalDateTime endTimeString = LocalDateTime.parse("2021-01-01T15:00:00");

        List<SensorReading> sensorReadings = List.of(
                setUpSensorReading("sr1","10","d1", "s1", "2021-01-01 12:00:00"),
                setUpSensorReading("sr2", "15", "d1", "s1", "2021-01-01 14:00:00"));

        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(
                new DeviceId(deviceID), Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadings);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings")
                        .param("deviceID", deviceID)
                        .param("startTimeString", startTimeString.toString())
                        .param("endTimeString", endTimeString.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        String expectedContent = """
                [
                {"sensorReadingID":"sr1",
                "links":[{"rel":"self","href":
                "http://localhost/api/v1/sensor-readings/sr1"}]},
                
                {"sensorReadingID":"sr2",

                "links":[{"rel":"self","href":"http://localhost/api/v1/sensor-readings/sr2"}]}]
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the endpoint for obtaining sensor readings from a device within a given time period.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void noReadingsWithDeviceID_shouldReturnEmptyList() throws Exception {
        //Arrange
        String deviceID = "d5";
        LocalDateTime startTimeString = LocalDateTime.parse("2021-01-01T11:00:00");
        LocalDateTime endTimeString = LocalDateTime.parse("2021-01-01T15:00:00");

        List<SensorReading> sensorReadings = new ArrayList<>();
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(
                new DeviceId(deviceID), Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadings);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings")
                        .param("deviceID", deviceID)
                        .param("startTimeString", startTimeString.toString())
                        .param("endTimeString", endTimeString.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "[]";

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the endpoint for obtaining sensor readings from a device within a given time period.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void noReadingsInTimePeriod_shouldReturnEmptyList() throws Exception {
        //Arrange
        String deviceID = "d1";
        LocalDateTime startTimeString = LocalDateTime.parse("2022-01-01T11:00:00");
        LocalDateTime endTimeString = LocalDateTime.parse("2022-01-01T15:00:00");

        List<SensorReading> sensorReadings = new ArrayList<>();
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(
                new DeviceId(deviceID), Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadings);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings")
                        .param("deviceID", deviceID)
                        .param("startTimeString", startTimeString.toString())
                        .param("endTimeString", endTimeString.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "[]";

        JSONAssert.assertEquals(expectedContent, resultContent, false);

    }

    /**
     * Tests the endpoint for obtaining the maximum temperature difference between two devices within a given time period.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void getValidReadingsFromDeviceWithinPeriod_shouldReturnMaximumTemperatureDifference() throws Exception {
        //Arrange
        LocalDateTime startTimeString = LocalDateTime.parse("2021-01-01T11:00:00");
        LocalDateTime endTimeString = LocalDateTime.parse("2021-01-01T15:00:00");

        List<SensorReading> sensorReadingsDevice1 = List.of(
                setUpSensorReading("sr1","10","d1", "s1", "2021-01-01 12:00:00"));
        List<SensorReading> sensorReadingsDevice2 = List.of(
                setUpSensorReading("sr2", "15", "d2", "s2", "2021-01-01 12:10:00"));

        List<Sensor> sensors = List.of(
                factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s1")),
                factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s2")));

        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(
                new DeviceId("d1"),new SensorID("s1"),  Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadingsDevice1);

        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(
                new DeviceId("d2"),new SensorID("s2"),  Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadingsDevice2);

        when(repositoryDevice.containsOfIdentity(new DeviceId("d1"))).thenReturn(true);
        when(repositoryDevice.containsOfIdentity(new DeviceId("d2"))).thenReturn(true);
        when(repositorySensor.getSensorsByDeviceID(any(DeviceId.class))).
                thenReturn(sensors);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings?deviceID=d1&startTimeString=2021-01-01T11:00:00&endTimeString=2021-01-01T15:00:00")
                        .param("deviceIDIndoor", "d1")
                        .param("deviceIDOutdoor", "d2")
                        .param("deltaTime", String.valueOf(20))
                        .param("startTimeString", startTimeString.toString())
                        .param("endTimeString", endTimeString.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {"maxDifference":5}
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the endpoint for obtaining the maximum temperature difference between two devices within a given time period.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void noReadingsFromDeviceWithinPeriod_shouldThrowException() throws Exception {
        //Arrange
        LocalDateTime startTimeString = LocalDateTime.parse("2021-01-01T11:00:00");
        LocalDateTime endTimeString = LocalDateTime.parse("2021-01-01T15:00:00");

        List<SensorReading> sensorReadingsDevice1 = new ArrayList<>();
        List<SensorReading> sensorReadingsDevice2 = new ArrayList<>();

        List<Sensor> sensors = List.of(
                factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s1")),
                factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s2")));

        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(
                new DeviceId("d1"),new SensorID("s1"),  Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadingsDevice1);

        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(
                new DeviceId("d2"),new SensorID("s2"),  Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadingsDevice2);
        when(repositoryDevice.containsOfIdentity(new DeviceId("d1"))).thenReturn(true);
        when(repositoryDevice.containsOfIdentity(new DeviceId("d2"))).thenReturn(true);

        when(repositorySensor.getSensorsByDeviceID(any(DeviceId.class))).
                thenReturn(sensors);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings?deviceID=d1&startTimeString=2021-01-01T11:00:00&endTimeString=2021-01-01T15:00:00")
                        .param("deviceIDIndoor", "d1")
                        .param("deviceIDOutdoor", "d2")
                        .param("deltaTime", String.valueOf(20))
                        .param("startTimeString", startTimeString.toString())
                        .param("endTimeString", endTimeString.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "No temperature readings found for the given time period";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the endpoint for obtaining a sensor reading by its ID.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void getSensorReadingByID_shouldReturnSensorReading() throws Exception {
        //Arrange
        SensorReading sensorReading = setUpSensorReading("sr1","10","d1", "s1", "2021-01-01 12:00:00");

        when( repositorySensorReading.ofIdentity(new SensorReadingID("sr1"))).thenReturn(Optional.of(sensorReading));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings/sr1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                "sensorReadingID":"sr1",
                "reading":"10",
                "deviceID":"d1",
                "sensorID":"s1",
                "timeStamp":"2021-01-01 12:00:00.0",
                "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/sensor-readings/sr1"
                        }
                    }
                }""";

        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * Tests the endpoint for obtaining a sensor reading by its ID when the sensor reading does not exist.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void getSensorReadingByIDNonExistent_shouldReturnEntityNotFound() throws Exception {
        //Arrange
        when( repositorySensorReading.ofIdentity(new SensorReadingID("sr1"))).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings/sr1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "Sensor Reading not found";
        assertEquals(expectedContent, resultContent);
    }

    /**
     * Tests the endpoint for obtaining the maximum temperature difference between two devices within a given time period
     * when there is no difference between the readings.
     */
    @Test
    void noDifferenceBetweenReadings_shouldReturnZero() throws Exception {
        //Arrange
        LocalDateTime startTimeString = LocalDateTime.parse("2021-01-01T11:00:00");
        LocalDateTime endTimeString = LocalDateTime.parse("2021-01-01T15:00:00");

        List<SensorReading> sensorReadingsDevice1 = List.of(
                setUpSensorReading("sr1","10","d1", "s1", "2021-01-01 12:00:00"));
        List<SensorReading> sensorReadingsDevice2 = List.of(
                setUpSensorReading("sr2", "10", "d2", "s2", "2021-01-01 12:10:00"));

        List<Sensor> sensors = List.of(
                factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s1")),
                factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s2")));

        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(
                new DeviceId("d1"),new SensorID("s1"),  Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadingsDevice1);

        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(
                new DeviceId("d2"),new SensorID("s2"),  Timestamp.valueOf(startTimeString), Timestamp.valueOf(endTimeString)))
                .thenReturn(sensorReadingsDevice2);

        when(repositoryDevice.containsOfIdentity(new DeviceId("d1"))).thenReturn(true);
        when(repositoryDevice.containsOfIdentity(new DeviceId("d2"))).thenReturn(true);
        when(repositorySensor.getSensorsByDeviceID(any(DeviceId.class))).
                thenReturn(sensors);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sensor-readings?deviceID=d1&startTimeString=2021-01-01T11:00:00&endTimeString=2021-01-01T15:00:00")
                        .param("deviceIDIndoor", "d1")
                        .param("deviceIDOutdoor", "d2")
                        .param("deltaTime", String.valueOf(20))
                        .param("startTimeString", startTimeString.toString())
                        .param("endTimeString", endTimeString.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {"maxDifference":0}
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

}
