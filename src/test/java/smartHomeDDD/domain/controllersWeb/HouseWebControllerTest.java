package smartHomeDDD.domain.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
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
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.repository.*;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;

import java.sql.Timestamp;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test class for the HouseWebController.
 * It uses the Spring Boot Test framework to mock the MVC layer,
 * and Mockito to mock the interaction with the repository.
 * It encompasses the following scenarios:
 * - A valid house is configured and the location is updated.
 * - An invalid house is configured and the location is not updated.
 * - The list of all houses is retrieved.
 * - No houses were retrieved.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HouseWebControllerTest {

    /**
     * The MockMvc instance is used to perform HTTP requests to the application.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The repositoryRoom object is a mock used to simulate the repository of rooms.
     */
    @MockBean
    private IRepositoryRoom repositoryRoom;

    /**
     * The repositoryHouse object is a mock used to simulate the repository of houses.
     */
    @MockBean
    IRepositoryHouse repositoryHouse;

    /**
     * The repositoryDevice object is a mock used to simulate the repository of devices.
     */
    @MockBean
    private IRepositoryDevice repositoryDevice;

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
     * The factorySensor object is used to create sensors.
     */
    @Autowired
    private FactorySensor factorySensor;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Test case for updating the location of a valid house.
     *
     * @throws Exception if any exception occurs during the request
     */
    @Test
    void validHouse_shouldConfigureAndUpdateLocation() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("House01");
        House house = new House(houseId, new Location(
                new Address("Rua dos Croissants"),
                new ZipCode("Portugal", "1234-123"),
                new GPSCoordinates(new Latitude(55.0), new Longitude(90.0))));

        when(repositoryHouse.save(house)).thenReturn(house);
        when(repositoryHouse.ofIdentity(houseId)).thenReturn(Optional.of(house));
        when(repositoryHouse.update(any(House.class))).thenReturn(house);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/api/v1/houses/House01")
                        .param("houseId", houseId.toString())
                        .param("address", "Rua das Flores")
                        .param("country", "Portugal")
                        .param("zipCode", "1234-123")
                        .param("latitude", "55.0")
                        .param("longitude", "90.0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                 {
                     "id": "House01",
                     "address": "Rua das Flores",
                     "country": "Portugal",
                     "zipCode": "1234-123",
                     "latitude": 55.0,
                     "longitude": 90.0,
                     "_links": {
                         "self": {
                             "href": "http://localhost/api/v1/houses/House01"
                         }
                     }
                 }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Test case for attempting to update the location of an invalid house.
     * The test simulates a scenario where the houseId does not exist in the repository.
     * When the `repositoryHouse.ofIdentity(houseId)` method is called, it returns an empty Optional,
     * indicating that no house with the given houseId exists.
     * As a result, when we try to update the location of the house, the server should return a 404 Not Found status code.
     * In the Assert phase, we're checking that the response body is empty, as it should be for a 404 response.
     *
     * @throws Exception if any exception occurs during the request
     */
    @Test
    void invalidHouse_shouldNotUpdateAndThrowException() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("InvalidHouseId");

        when(repositoryHouse.ofIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/houses/InvalidHouseId")
                        .param("houseId", houseId.toString())
                        .param("address", "Rua das Flores")
                        .param("country", "Portugal")
                        .param("zipCode", "1234-123")
                        .param("latitude", "55.0")
                        .param("longitude", "90.0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Can't find House", resultContent);
    }

    /**
     * Test case for retrieving a list of all houses.
     *
     * @throws Exception if any exception occurs during the request
     */
    @Test
    void getHouses_shouldReturnListOfHouses() throws Exception {
        // Arrange
        List<House> houses = List.of(
                new House(new HouseId("House01"), new Location(new Address("Rua das Flores"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55.0), new Longitude(90.0)))),
                new House(new HouseId("House02"), new Location(new Address("Rua das Gramíneas"), new ZipCode("Portugal", "1234-123"), new GPSCoordinates(new Latitude(55.0), new Longitude(90.0))))
        );
        when(repositoryHouse.findAll()).thenReturn(houses);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        String expectedContent = """
                [
                    {
                        "id": "House01",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/houses/House01"
                            }
                        ]
                    },
                    {
                        "id": "House02",
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/houses/House02"
                            }
                        ]
                    }
                
                ]
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Test case for retrieving an empty list when there are no houses.
     *
     * @throws Exception if any error occurs during the test
     */
    @Test
    void noHouses_shouldReturnEmptyList() throws Exception {
        // Arrange
        List<House> houses = new ArrayList<>();

        when(repositoryHouse.findAll()).thenReturn(houses);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                []
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Test case for obtaining a house by a valid ID.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void obtainHouseByValidID_shouldReturnHouseID() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("House01");
        House house = new House(houseId, new Location(
                new Address("Rua dos Croissants"),
                new ZipCode("Portugal", "1234-123"),
                new GPSCoordinates(new Latitude(55.0), new Longitude(90.0))));

        when(repositoryHouse.ofIdentity(houseId)).thenReturn(Optional.of(house));


        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                 {
                     "id": "House01",
                     "address": "Rua dos Croissants",
                     "country": "Portugal",
                     "zipCode": "1234-123",
                     "latitude": 55.0,
                     "longitude": 90.0,
                      "_links": {
                             "rooms": {
                                 "href": "http://localhost/api/v1/houses/House01/rooms"
                             }
                         }
                 }
               """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Test case for obtaining a house by an invalid ID.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void obtainHouseByInvalidID_shouldReturnEntityNotFoundException() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("House01");

        when(repositoryHouse.ofIdentity(houseId)).thenReturn(Optional.empty());


        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("House not found", resultContent);
    }

    /**
     * Test case for obtaining a list of rooms by a valid house ID.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void getRoomsByHouseID_shouldReturnRooms() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("h1");
        Room room = new Room(houseId, new RoomID("r1"), new FloorNumber(0), new Dimensions(new Length(10), new Width(10.0), new Height(10.0)), true, new RoomName("Living Room"));
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(repositoryHouse.containsOfIdentity(room.getHouseId())).thenReturn(true);
        when(repositoryRoom.getRoomsByHouseID(houseId)).thenReturn(rooms);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/h1/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        System.out.println("Actual response: " + resultContent);

        String expectedContent = """
            [
                {
                    "roomId": "r1"
                }
            ]
            """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }


    /**
     * Test case for obtaining a list of rooms by a valid house ID.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void getRoomsByHouseID_shouldReturnEmptyList() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("h1");
        when(repositoryHouse.containsOfIdentity(houseId)).thenReturn(true);
        when(repositoryRoom.getRoomsByHouseID(houseId)).thenReturn(List.of());


        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/h1/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = "[]";
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
    /**
     * Test case for obtaining a list of rooms by a non-existing house ID.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void getRoomsByHouseWithNonExistingHouse_shouldReturnNotFoundStatus() throws Exception {
        // Arrange
        HouseId houseId = new HouseId("h1");
        when(repositoryHouse.containsOfIdentity(houseId)).thenReturn(false);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/h1/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("House not found", resultContent);
    }


    /**
     * Tests the endpoint for retrieving peak power consumption with an invalid house ID.
     * This test simulates the scenario where a request is made to the peak power consumption
     * API endpoint with a house ID that does not exist in the repository. The expected outcome
     * is that the endpoint responds with a 404 Not Found status.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    void peakPowerConsumption_InvalidHouseID_shouldReturnException() throws Exception {
        HouseId houseID = new HouseId("House01");
        when(repositoryHouse.ofIdentity(houseID)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/peak-power-consumption")
                        .param("houseId", "h1")
                        .param("startTimeString", "2021-01-01T11:00:00")
                        .param("endTimeString", "2021-01-01T15:00:00")
                        .param("intervalInMinutes", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("", resultContent); // should return a list of 1 element only
    }


    /**
     * Tests the peak power consumption calculation when devices are consuming energy.
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void peakPowerConsumption_devicesOnlyConsumeEnergy_shouldReturnException() throws Exception {

        //Arrange
        HouseId houseID = new HouseId("House01");
        // get house id from repo
        when(repositoryHouse.containsOfIdentity(new HouseId("House01"))).thenReturn(true);

        // create and save PGM
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // House devices with energy sensors to determine energy consumption ("PC500W")
        SensorModelID sensorModelID = new SensorModelID("PC500W"); // model applied for all devices

        // air conditioner
        DeviceId id1 = new DeviceId("air conditioner");
        SensorID sId1 = new SensorID("1");
        Sensor sensor1 = factorySensor.createSensor(id1, sensorModelID, sId1);

        // fridge
        DeviceId id2 = new DeviceId("fridge");
        SensorID sId2 = new SensorID("2");
        Sensor sensor2 = factorySensor.createSensor(id2,sensorModelID,sId2);

        // microwave
        DeviceId id3 = new DeviceId("microwave");
        SensorID sId3 = new SensorID("3");
        Sensor sensor3 = factorySensor.createSensor(id3,sensorModelID,sId3);

        //save sensors
        when(repositorySensor.save(sensor1)).thenReturn(sensor1);
        when(repositorySensor.save(sensor2)).thenReturn(sensor2);
        when(repositorySensor.save(sensor3)).thenReturn(sensor3);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor1);
        sensors.add(sensor2);
        sensors.add(sensor3);
        when(repositorySensor.findAll()).thenReturn(sensors);

        // readings for air conditioner
        SensorReading sensorReading = new SensorReading(new SensorReadingID("sensorReading1"), new Reading("10"), id1, sId1, Timestamp.valueOf("2021-01-01 11:00:00"));
        SensorReading sensorReading1 = new SensorReading(new SensorReadingID("sensorReading2"), new Reading("3"), id1, sId1, Timestamp.valueOf("2021-01-01 11:30:00"));
        // readings for fridge
        SensorReading sensorReading2 = new SensorReading(new SensorReadingID("sensorReading3"), new Reading("10"), id2, sId2, Timestamp.valueOf("2021-01-01 12:00:38"));
        SensorReading sensorReading3 = new SensorReading(new SensorReadingID("sensorReading4"), new Reading("30"), id2, sId2, Timestamp.valueOf("2021-01-01 12:45:00"));
        // readings for microwave
        SensorReading sensorReading4 = new SensorReading(new SensorReadingID("sensorReading5"), new Reading("7"), id3, sId3, Timestamp.valueOf("2021-01-01 14:46:00"));
        SensorReading sensorReading5 = new SensorReading(new SensorReadingID("sensorReading6"), new Reading("30"), id3, sId3, Timestamp.valueOf("2021-01-01 15:55:20"));

        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id1), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading,sensorReading1));
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id2), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading2,sensorReading3));
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id2), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading4,sensorReading5));


        // Act
        MvcResult result = mockMvc
                // get peak consumption from 10:30 to 16 h , 15 min interval
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:30:00&end=2021-01-01 16:00:00&interval=15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        String expectedContent = """
                {
                "peakPowerConsumption":"25.0"
                }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }


    /**
     * Tests the scenario in which the peak power consumption is negative due the presence of power generation devices.
     * If the solar panels are producing more energy than the house is using, the excess is sent to the electric grid,
     * and the meter records this amount as exported energy (negative result)
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void peakPowerConsumption_deviceGenerateEnergy_shouldReturnNegativePeakPowerConsumption() throws Exception {
        //Arrange
        HouseId houseID = new HouseId("House01");
        // get house id from repo
        when(repositoryHouse.containsOfIdentity(new HouseId("House01"))).thenReturn(true);

        // create and save PGM
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // House devices with energy sensors for determine energy consumption ("PC500W")
        SensorModelID sensorModelID = new SensorModelID("PC500W"); // model applied for all devices

        // Solar Panel 1 (DEVICE THAT GENERATE ENERGY)
        DeviceId id2 = new DeviceId("SolarPanelLeftRoom");
        SensorID sId2 = new SensorID("2");
        Sensor sensor2 = factorySensor.createSensor(id2,sensorModelID,sId2);

        // Solar Panel 2 (DEVICE THAT GENERATE ENERGY)
        DeviceId id1 = new DeviceId("SolarPanelRightRoom");
        SensorID sId1 = new SensorID("3");
        Sensor sensor1 = factorySensor.createSensor(id1,sensorModelID,sId1);

        // readings for Solar Panel 1
        SensorReading sensorReading2 = new SensorReading(new SensorReadingID("sensorReading3"), new Reading("-100"), id2, sId2, Timestamp.valueOf("2021-01-01 12:00:38"));
        SensorReading sensorReading3 = new SensorReading(new SensorReadingID("sensorReading4"), new Reading("-30"), id2, sId2, Timestamp.valueOf("2021-01-01 12:45:00"));

        // readings for Solar Panel 2
        SensorReading sensorReading4 = new SensorReading(new SensorReadingID("sensorReading3"), new Reading("-150"), id1, sId1, Timestamp.valueOf("2021-01-01 12:00:38"));
        SensorReading sensorReading5 = new SensorReading(new SensorReadingID("sensorReading4"), new Reading("-35"), id1, sId1, Timestamp.valueOf("2021-01-01 12:45:00"));

        // air conditioner
        DeviceId id3 = new DeviceId("air conditioner");
        SensorID sId3 = new SensorID("1");
        Sensor sensor3 = factorySensor.createSensor(id3, sensorModelID, sId3);

        // readings from air conditioner
        SensorReading sensorReading6 = new SensorReading(new SensorReadingID("sensorReading1"), new Reading("10"), id3, sId3, Timestamp.valueOf("2021-01-01 11:00:00"));
        SensorReading sensorReading7 = new SensorReading(new SensorReadingID("sensorReading2"), new Reading("25"), id3, sId3, Timestamp.valueOf("2021-01-01 13:30:00"));

        //save sensors from Solar Panel
        when(repositorySensor.save(sensor2)).thenReturn(sensor2);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor2);
        sensors.add(sensor1);
        sensors.add(sensor3);
        when(repositorySensor.findAll()).thenReturn(sensors);

        // get energy readings from Solar Panels
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id2), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading2,sensorReading3));
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id1), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading4,sensorReading5));
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id3), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading6,sensorReading7));

        // Act
        MvcResult result = mockMvc
                // get peak consumption from 10:30 to 15 h , 60 min interval
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:00:00&end=2021-01-01 15:00:00&interval=60")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        String expectedContent = """
            
            {
            "peakPowerConsumption":"-140.0"}
            """;
        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }


    /**
     * Tests the scenario in which the inserted start time is after the end time.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void peakPowerConsumption_InvalidStartAndEnd_shouldReturnException() throws Exception {
        // Arrange
        // get house id from repo
        HouseId houseID = new HouseId("House01");
        when(repositoryHouse.containsOfIdentity(houseID)).thenReturn(true);
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:00:00&end=2020-01-01 15:00:00&interval=60")
                        .param("houseId", "h1")
                        .param("startTimeString", "2021-01-01T11:00:00")
                        .param("endTimeString", "2020-01-01T15:00:00")
                        .param("intervalInMinutes", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("end time can't be before start time", resultContent);
    }

    /**
     * Tests the scenario in which the peak Power consumption is calculated with intervals of more than 2 hours.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void peakPowerConsumption_2hoursIntervals_shouldReturnPeakPowerConsumption() throws Exception {
        //Arrange
        HouseId houseID = new HouseId("House01");
        // get house id from repo
        when(repositoryHouse.containsOfIdentity(houseID)).thenReturn(true);


        // Create and save Power Grid Meter
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // House devices with energy sensors for determine energy consumption ("PC500W")
        SensorModelID sensorModelID = new SensorModelID("PC500W"); // model applied for all devices

        // air conditioner
        DeviceId id1 = new DeviceId("air conditioner");
        SensorID sId1 = new SensorID("1");
        Sensor sensor1 = factorySensor.createSensor(id1, sensorModelID, sId1);

        // Solar Panel (DEVICE THAT GENERATE ENERGY)
        DeviceId id2 = new DeviceId("SolarPanelLeftRoom");
        SensorID sId2 = new SensorID("2");
        Sensor sensor2 = factorySensor.createSensor(id2,sensorModelID,sId2);

        //save sensors
        when(repositorySensor.save(sensor1)).thenReturn(sensor1);
        when(repositorySensor.save(sensor2)).thenReturn(sensor2);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor1);
        sensors.add(sensor2);
        when(repositorySensor.findAll()).thenReturn(sensors);

        // readings for air conditioner
        SensorReading sensorReading = new SensorReading(new SensorReadingID("sensorReading1"), new Reading("10"), id1, sId1, Timestamp.valueOf("2021-01-01 11:00:00"));
        SensorReading sensorReading1 = new SensorReading(new SensorReadingID("sensorReading2"), new Reading("3"), id1, sId1, Timestamp.valueOf("2021-01-01 13:30:00"));
        // readings for Solar Panel
        SensorReading sensorReading2 = new SensorReading(new SensorReadingID("sensorReading3"), new Reading("-100"), id2, sId2, Timestamp.valueOf("2021-01-01 14:00:38"));
        SensorReading sensorReading3 = new SensorReading(new SensorReadingID("sensorReading4"), new Reading("-30"), id2, sId2, Timestamp.valueOf("2021-01-01 18:45:00"));


        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id1), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading,sensorReading1));
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id2), any(Timestamp.class),any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading2,sensorReading3));

        // Act
        MvcResult result = mockMvc
                // get peak consumption from 10:30 to 15 h , 60 min interval
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:00:00&end=2021-01-01 15:00:00&interval=60")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        String expectedContent = """
            {
            "peakPowerConsumption":"-58.5"}
            """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }

    /**
     * Tests the scenario in which the inserted interval is negative.
     * @throws Exception if any exception occurs during the test execution.
     */
    @Test
    void peakPowerConsumption_NegativeInterval_shouldReturnException() throws Exception {
        // Arrange
        HouseId houseID = new HouseId("House01");
        when(repositoryHouse.containsOfIdentity(houseID)).thenReturn(true);
        // create and save PGM
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:00:00&end=2021-01-01 15:00:00&interval=-50")
                        .param("houseId", "h1")
                        .param("startTimeString", "2021-01-01T11:00:00")
                        .param("endTimeString", "2020-01-01T15:00:00")
                        .param("intervalInMinutes", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("end time can't be negative", resultContent);
    }


    /**
     * Test case to verify the behavior of peakPowerConsumption method when devices without energy consumption sensors are present.
     * @throws Exception when an error occurs during test execution
     */
    @Test
    void peakPowerConsumption_DevicesWithoutEnergyConsumptionSensors_shouldReturnException() throws Exception {

        // Arrange
        HouseId houseID = new HouseId("House01");
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,activationStatus,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));


        ActivationStatus s = new ActivationStatus(true);
        // Create a list of devices without power consumption sensors
        List<Device> devicesWithoutPowerConsumptionSensors = new ArrayList<>();
        devicesWithoutPowerConsumptionSensors.add(new Device(new DeviceId("Device1"), new DeviceName("Device1"), new DeviceModel("Model1"), new ActivationStatus(true), new RoomID("Room1")));
        devicesWithoutPowerConsumptionSensors.add(new Device(new DeviceId("Device2"), new DeviceName("Device2"), new DeviceModel("Model2"), new ActivationStatus(true), new RoomID("Room2")));

        // Mock the repositoryDevice to return the list of devices without power consumption sensors
        when(repositoryDevice.findAll()).thenReturn(devicesWithoutPowerConsumptionSensors);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:30:00&end=2021-01-01 16:00:00&interval=15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        int status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected 404 Not Found status");
    }

    /**
     * Test method for the scenario where the peak power consumption is requested for a house
     * that has devices but no energy consumption during the specified period. This test simulates
     * the situation where devices generate energy readings of zero and verifies that the peak power
     * consumption returned is zero.
     * @throws Exception if an error occurs during the request.
     */

    @Test
    void peakPowerConsumption_NoConsumption_shouldReturnZeroConsumption() throws Exception {
        // Arrange
        HouseId houseID = new HouseId("House01");
        when(repositoryHouse.containsOfIdentity(houseID)).thenReturn(true);

        // Create and save Power Grid Meter
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // Create devices that generate energy
        DeviceId id1 = new DeviceId("SolarPanelLeftRoom");
        SensorID sId1 = new SensorID("1");
        Sensor sensor1 = factorySensor.createSensor(id1, new SensorModelID("PC500W"), sId1);

        // Save sensors
        when(repositorySensor.save(sensor1)).thenReturn(sensor1);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor1);
        when(repositorySensor.findAll()).thenReturn(sensors);

        // Create sensor readings that indicate energy generation
        SensorReading sensorReading1 = new SensorReading(new SensorReadingID("sensorReading1"), new Reading("0"), id1, sId1, Timestamp.valueOf("2021-01-01 11:00:00"));

        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id1), any(Timestamp.class), any(Timestamp.class))).thenReturn(List.of(sensorReading1));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:30:00&end=2021-01-01 16:00:00&interval=15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                "peakPowerConsumption":"0.0"}
                """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }


    /**
     * Test method for the scenario where the peak power consumption is requested for a house
     * with devices that have equal power consumption readings in specified intervals.
     * This test simulates the situation where all devices have the same power consumption,
     * and verifies that the peak power consumption returned is the value of that equal consumption.
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void peakPowerConsumption_EqualConsumptionInIntervals_shouldReturnPeak() throws Exception {
        // Arrange
        HouseId houseID = new HouseId("House01");
        when(repositoryHouse.containsOfIdentity(houseID)).thenReturn(true);

        // Create and save Power Grid Meter
        DeviceId id = new DeviceId("d6");
        DeviceName name = new DeviceName("Power Grid Meter");
        DeviceModel model = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomID = new RoomID("r4");
        Room entranceRoom = new Room(houseID,roomID,new FloorNumber(0), new Dimensions(new Length(10), new Width(10), new Height(10)),true, new RoomName("Entrance"));
        Device PowerGridMeter = new Device(id,name,model,status,roomID);
        when(repositoryDevice.findAll()).thenReturn(List.of(PowerGridMeter));
        when(repositoryRoom.ofIdentity(roomID)).thenReturn(Optional.of(entranceRoom));

        // Create devices with equal power consumption
        DeviceId id1 = new DeviceId("Device1");
        SensorID sId1 = new SensorID("1");
        Sensor sensor1 = factorySensor.createSensor(id1, new SensorModelID("PC500W"), sId1);

        // Save sensors
        when(repositorySensor.save(sensor1)).thenReturn(sensor1);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor1);
        when(repositorySensor.findAll()).thenReturn(sensors);

        // Create sensor readings with equal power consumption
        SensorReading sensorReading1 = new SensorReading(new SensorReadingID("sensorReading1"), new Reading("10"), id1, sId1, Timestamp.valueOf("2021-01-01 11:00:00"));
        SensorReading sensorReading2 = new SensorReading(new SensorReadingID("sensorReading2"), new Reading("10"), id1, sId1, Timestamp.valueOf("2021-01-01 11:30:00"));

        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(eq(id1), any(Timestamp.class), any(Timestamp.class))).thenReturn(Arrays.asList(sensorReading1, sensorReading2));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/houses/House01/peak-power-consumption?start=2021-01-01 10:30:00&end=2021-01-01 16:00:00&interval=15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
            {
            "peakPowerConsumption":"10.0"}
            """;

        JSONAssert.assertEquals(expectedContent, resultContent, false);
    }
}

