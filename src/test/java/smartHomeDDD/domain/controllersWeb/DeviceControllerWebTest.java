package smartHomeDDD.domain.controllersWeb;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
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
import smartHomeDDD.domain.actuator.OPNCL0100;
import smartHomeDDD.domain.actuator.SIV280;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.repository.*;
import smartHomeDDD.domain.sensor.GA100K;
import smartHomeDDD.domain.sensor.TSY01;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceEntryWebDTO;
import smartHomeDDD.services.GenerateRandomId;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for DeviceControllerWeb. It uses MockMvc to simulate HTTP requests,
 * ObjectMapper for JSON conversion, and Mockito for mocking dependencies.
 *  It contains the following tests scenarios:
 *  - Add a new device to the system.
 *  - Add a new device to the system with a duplicated ID.
 *  - Add a new device to the system with a non-existing room ID.
 *  - Retrieve a device by its ID.
 *  - Retrieve a device by its ID, but the device does not exist.
 *  - Retrieve a list of sensors of a device.
 *  - Retrieve a list of sensors of a device, but the device does not have any sensors.
 *  - Retrieve a list of sensors of a non-existing device.
 *  - Retrieve a list of actuators of a device.
 *  - Retrieve a list of actuators of a device, but the device does not have any actuators.
 *  - Retrieve a list of actuators of a non-existing device.
 *  - Retrieve a list of sensor readings of a device.
 *  - Retrieve a list of sensor readings of a device, but the device does not have any sensor readings.
 *  - Retrieve a list of sensor readings of a device that doesn't exist.
 *  - Retrieve the active devices.
 *  - Retrieve the active devices but there are no active devices.
 *  - Retrieve a list of devices.
 *  - Retrieve a list of devices but there are no devices.
 *  - Retrieve a list of devices by functionality.
 *  - Deactivate a device.
 *  - Deactivate a device that does not exist.
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class DeviceControllerWebTest {
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
     * FactoryDevice instance for creating Device objects.
     */
    @Autowired
    private FactoryDevice factoryDevice;
    /**
     * Mock of IRepositoryDevice for simulating device repository operations.
     */
    @MockBean
    private IRepositoryDevice repositoryDevice;
    /**
     * Mock of IRepositorySensor for simulating sensor repository operations.
     */
    @MockBean
    private IRepositorySensor repositorySensor;
    /**
     * Mock of IRepositoryActuator for simulating actuator repository operations.
     */
    @MockBean
    private IRepositoryActuator repositoryActuator;

    /**
     * Mock of IRepositorySensorModel for simulating sensor model repository operations.
     */
    @MockBean
    private IRepositorySensorModel repositorySensorModel;
    /**
     * Mock of IRepositorySensorType for simulating sensor type repository operations.
     */
    @MockBean
    private IRepositorySensorType repositorySensorType;
    /**
     * Mock of IRepositorySensorReading for simulating sensor reading repository operations.
     */
    @MockBean
    private IRepositorySensorReading repositorySensorReading;
    /**
     * Mock of IRepositoryRoom for simulating room repository operations.
     */
    @MockBean
    private IRepositoryRoom repositoryRoom;

    /**
     * Mock of GenerateRandomId for simulating random ID generation.
     */
    @MockBean
    private GenerateRandomId generateRandomId;
    /**
     * Method that is executed before each test. It opens the mocks.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Helper method to create a Device object from a DeviceEntryWebDTO.
     * @param deviceEntryWebDTO The DTO that contains the information to create a new Device.
     * @return The created Device object.
     */
    private Device setupDevice (DeviceEntryWebDTO deviceEntryWebDTO) {
        DeviceId deviceId = new DeviceId(generateRandomId.generateID());
        DeviceName deviceName = new DeviceName(deviceEntryWebDTO.getDeviceName());
        DeviceModel deviceModel = new DeviceModel(deviceEntryWebDTO.getDeviceModel());
        ActivationStatus activationStatus = new ActivationStatus(deviceEntryWebDTO.getActivationStatus());
        RoomID roomID = new RoomID(deviceEntryWebDTO.getRoomId());
        return factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus, roomID);
    }

    /**
     * This test method verifies the scenario where a new device is added to the system.
     */
    @Test
    void addDevice_shouldReturnNewDevice() throws Exception {

        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryRoom.containsOfIdentity(device.getRoomId())).thenReturn(true);
        when(repositoryDevice.save(device)).thenReturn(device);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceEntryWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
              
                {
                  "deviceId": "d1",
                  "roomId": "r1",
                  "deviceName": "lamp",
                  "deviceModel": "siemens",
                  "activationStatus": true,
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices/d1"
                      }
                  }
              }
              """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);

    }

    /**
     * This test method verifies the scenario where a new device is added to the system with a duplicated ID.
     */
    @Test
    void addDeviceWithDuplicatedID_shouldReturnConflictStatus() throws Exception {

        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryRoom.containsOfIdentity(device.getRoomId())).thenReturn(true);

        doThrow(new DataIntegrityViolationException("Device already exists")).when(repositoryDevice).save(device);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceEntryWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Device already exists",resultContent);
    }
    /**
     * This test method verifies the scenario where a new device is added to the system with a non-existing room ID.
     */

    @Test
    void addDeviceWithNonExistingRoomID_shouldReturnBadRequestStatus() throws Exception {

        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryRoom.containsOfIdentity(device.getRoomId())).thenReturn(false);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceEntryWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Room not found",resultContent);
    }

    /**
     * This test method verifies the scenario where a device is retrieved by its ID.
     */
    @Test
    void getDevicesByID_shouldReturnDevice() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.ofIdentity(device.identity())).thenReturn(java.util.Optional.of(device));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                    "deviceId": "d1",
                    "roomId": "r1",
                    "deviceName": "lamp",
                    "deviceModel": "siemens",
                    "activationStatus": true,
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/devices/d1"
                        },
                        "actuators": {
                            "href": "http://localhost/api/v1/devices/d1/actuators"
                        },
                        "sensors": {
                            "href": "http://localhost/api/v1/devices/d1/sensors"
                        },
                        "sensor-readings": {
                            "href": "http://localhost/api/v1/devices/d1/sensor-readings"
                        }
                    }
                }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where a device is retrieved by its ID, but the device does not exist.
     */
    @Test
    void getDevicesByIDWithNonExistingDevice_shouldReturnNotFoundStatus() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        doThrow(new EntityNotFoundException("Device not found")).when(repositoryDevice).ofIdentity(device.identity());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Device not found",resultContent);
    }

    /**
     * This test method verifies the scenario where a list of sensors of a device is retrieved.
     */
    @Test
    void getSensorsOfDevice_shouldReturnListOfSensors() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        GA100K sensor = new GA100K(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s1"));
        TSY01 sensor2 = new TSY01(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s2"));

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositorySensor.getSensorsByDeviceID(device.identity())).thenReturn(List.of(sensor, sensor2));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/sensors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                [
                    {
                        "sensorId": "s1",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/sensors/s1"
                            }
                        ]
                    },
                    {
                        "sensorId": "s2",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/sensors/s2"
                            }
                        ]
                    }
                ]
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where a list of sensors of a device is retrieved, but the device does not have any sensors.
     */
    @Test
    void getSensorsOfDeviceWithoutSensors_shouldReturnEmptyList() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositorySensor.getSensorsByDeviceID(device.identity())).thenReturn(List.of());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/sensors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                []
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where a list of sensors of a non existing device is retrieved.
     */
    @Test
    void getSensorsOfDeviceWithNonExistingDeviceID_shouldReturnNotFoundStatus() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        doThrow(new EntityNotFoundException("Device not found")).when(repositoryDevice).containsOfIdentity(device.identity());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/sensors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Device not found",resultContent);
    }

    /**
     * This test method verifies the scenario where a list of actuators of a device is retrieved.
     */
    @Test
    void getActuatorsOfDevice_shouldReturnListOfActuators() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "device1", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        OPNCL0100 actuator1 = new OPNCL0100(new ActuatorID("a1"), new DeviceId("d1"), new ActuatorModelID("OPNCL0100"));
        SIV280 actuator2 = new SIV280(new ActuatorID("a2"), new DeviceId("d1"), new ActuatorModelID("SIV280"));

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositoryActuator.getActuatorsByDeviceID(device.identity())).thenReturn(List.of(actuator1, actuator2));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/actuators")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                [
                    {
                        "actuatorId": "a1",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/actuators/a1"
                            }
                        ]
                    },
                    {
                        "actuatorId": "a2",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/actuators/a2"
                            }
                        ]
                    }
                ]
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }
    /**
     * This test method verifies the scenario where a list of actuators of a device is retrieved, but the device does not have any actuators.
     */
    @Test
    void getActuatorsOfDeviceWithoutActuators_shouldReturnEmptyList() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "device1", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositoryActuator.getActuatorsByDeviceID(device.identity())).thenReturn(List.of());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/actuators")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                []
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where a list of actuators of a non existing device is retrieved.
     */
    @Test
    void getActuatorsOfDeviceWithNonExistingDeviceID_shouldReturnNotFoundStatus() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "device1", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        doThrow(new EntityNotFoundException("Device not found")).when(repositoryDevice).containsOfIdentity(device.identity());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/actuators")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Device not found",resultContent);
    }

    /**
     * This test method verifies the scenario where a list of sensor readings of a device is retrieved.
     */
    @Test
    void getSensorReadingsOfDevice_shouldReturnListOfSensors() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);
        Timestamp t1 = Timestamp.valueOf("2021-01-01 11:00:00");
        SensorReading sensorReading = new SensorReading(new SensorReadingID("sr1"), new Reading("10"), new DeviceId("d1"), new SensorID("s1"), t1);

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositorySensorReading.getSensorReadingsByDeviceId(device.identity())).thenReturn(List.of(sensorReading));

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositorySensorReading.getSensorReadingsByDeviceId(device.identity())).thenReturn(List.of(sensorReading));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/sensor-readings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                [
                    {
                        "sensorReadingID": "sr1",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/sensor-readings/sr1"
                            }
                        ]
                    }
                ]
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }


    @Test
    void getSensorReadingsOfDeviceWithoutReadings_shouldReturnLEmptyListOfSensorReadings() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.containsOfIdentity(device.identity())).thenReturn(true);
        when(repositorySensorReading.getSensorReadingsByDeviceId(device.identity())).thenReturn(List.of());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/sensor-readings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                []
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where a list of sensor readings of a device that doesn't exist.
     */
    @Test
    void getSensorReadingsWithNonExistingDeviceID_shouldReturnNotFoundStatus() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        doThrow(new EntityNotFoundException("Device not found")).when(repositorySensorReading).getSensorReadingsByDeviceId(device.identity());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices/d1/sensor-readings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Device not found",resultContent);
    }

    /**
     * This test method verifies the scenario where the active devices are retrieved.
     */
    @Test
    void getActiveDevices_shouldReturnListOfActiveDevices() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.getActiveDevices()).thenReturn(List.of(device));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices?getBy=active")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                [
                    {
                        "id": "d1",
                        "links": [
                        {
                            "rel": "self",
                                "href": "http://localhost/api/v1/devices/d1"
                            }
                
                        ]
                    }
                ]
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where there are no active devices to retrieve.
     */
    @Test
    void getActiveDevicesWithoutDevices_shouldReturnEmptyList() throws Exception {
        // Act
        when(repositoryDevice.getActiveDevices()).thenReturn(List.of());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices?getBy=active")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                []
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where all devices are retrieved.
     */
    @Test
    void getDevices_shouldReturnListOfDevices() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.findAll()).thenReturn(List.of(device));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                [
                    {
                        "id": "d1",
                        "links": [
                        {
                            "rel": "self",
                                "href": "http://localhost/api/v1/devices/d1"
                            }
                
                        ]
                    }
                ]
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where there are no devices to retrieve.
     */

    @Test
    void getDevices_shouldReturnEmptyList() throws Exception {
        // Act
        when(repositoryDevice.findAll()).thenReturn(List.of());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                []
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, true);

    }

    /**
     * This test method verifies the scenario where a list of devices is retrieved by functionality.
     */

    @Test
    void getDevicesByFunction_shouldReturnListOfDevicesGroupedByFunctionality() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        GA100K sensor1 = new GA100K(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s1"));

        //Mock getDevicesByType
        when(repositorySensor.findAll()).thenReturn(List.of(sensor1));
        when(repositoryActuator.findAll()).thenReturn(List.of());

        //Mock assignDeviceToSensorGroupType
        when(repositorySensorModel.ofIdentity(sensor1.getSensorModelID())).thenReturn(java.util.Optional.of(new SensorModel(new SensorModelID("GA100K"), new SensorTypeID("Temperature"))));
        when(repositorySensorType.ofIdentity(new SensorTypeID("Temperature"))).thenReturn(java.util.Optional.of(new SensorType(new Unit("Celsius"), new Description("Temperature"), new SensorTypeID("T1"))));
        when(repositoryDevice.ofIdentity(sensor1.getDeviceID())).thenReturn(java.util.Optional.of(device));


        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/devices?getBy=functionality")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
            
                {
                "Temperature": [
                    {
                        "id": "d1",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/devices/d1"
                            }
                        ]
                    }
                ]
            }
            """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * This test method verifies the scenario where a device is deactivated.
     */
    @Test
    void deactivateDevice_shouldReturnDeactivatedDevice() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO("r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);

        when(repositoryDevice.ofIdentity(device.identity())).thenReturn(java.util.Optional.of(device));
        when(repositoryDevice.update(device)).thenReturn(device);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/devices?deviceID=d1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                  "deviceId": "d1",
                  "roomId": "r1",
                  "deviceName": "lamp",
                  "deviceModel": "siemens",
                  "activationStatus": false,
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices/d1"
                      }
                  }
              }
              """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * This test method verifies the scenario where an attempt is made to deactivate a device that does not exist.
     */
    @Test
    void deactivateNotExistingDevice_shouldReturnNotFoundStatus() throws Exception {
        // Arrange
        DeviceEntryWebDTO deviceEntryWebDTO = new DeviceEntryWebDTO( "r1", "lamp", "siemens", true);
        when(generateRandomId.generateID()).thenReturn("d1");
        Device device = setupDevice(deviceEntryWebDTO);
        doThrow(new DataIntegrityViolationException("Device not found")).when(repositoryDevice).update(device);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/devices?deviceID=d1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Device not found",resultContent);
    }

}