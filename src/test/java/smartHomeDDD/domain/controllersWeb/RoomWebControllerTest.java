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
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.RoomEntryWebDTO;
import smartHomeDDD.services.GenerateRandomId;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The RoomWebControllerTest class is responsible for testing the RoomWebController class.
 * It provides test cases for the following scenarios:
 * - Adding a new room to a house.
 * - Adding a room that already exists.
 * - Adding a room with a non-existing house ID.
 * - Obtaining a room by a valid ID.
 * - Obtaining a room by an invalid ID.
 * - Obtaining devices by room ID.
 * - Obtaining devices with an invalid room ID.
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RoomWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FactoryRoom factoryRoom;

    @MockBean
    private IRepositoryRoom repositoryRoom;

    @MockBean
    private IRepositoryHouse repositoryHouse;

    @MockBean
    private IRepositoryDevice repositoryDevice;

    @MockBean
    private GenerateRandomId generateRandomId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Private helper method to set up a room for testing.
     * @param roomWebDTO The RoomEntryWebDTO object containing the room details.
     * @return The Room object.
     */
    private Room setupRoom(RoomEntryWebDTO roomWebDTO) {
        RoomID roomID = new RoomID(generateRandomId.generateID());
        Length length = new Length(roomWebDTO.getLength());
        Width width = new Width(roomWebDTO.getWidth());
        Height height = new Height(roomWebDTO.getHeight());
        FloorNumber floorNumber = new FloorNumber(roomWebDTO.getFloorNumber());
        HouseId houseId = new HouseId(roomWebDTO.getHouseId());
        boolean isInside = roomWebDTO.isInside();
        Dimensions dimensions = new Dimensions(length, width, height);
        RoomName roomName = new RoomName(roomWebDTO.getRoomName());
        return factoryRoom.createRoom(houseId, roomID, floorNumber, dimensions, isInside, roomName);
    }

    /**
     * Test to successfully add a new room to a house.
     */

    @Test
    void addRoom_shouldReturnNewRoom() throws Exception {
        // Arrange
        RoomEntryWebDTO roomWebDTO = new RoomEntryWebDTO( 10, 10, 10, 0, "h1", true, "Living Room");
        when(generateRandomId.generateID()).thenReturn("r1");
        Room room = setupRoom(roomWebDTO);

        when(repositoryHouse.containsOfIdentity(room.getHouseId())).thenReturn(true);
        when(repositoryRoom.save(room)).thenReturn(room);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/rooms")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(roomWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                {
                  "roomId": "r1",
                  "length": 10.0,
                  "width": 10.0,
                  "height": 10.0,
                  "floorNumber": 0,
                  "houseId": "h1",
                  "inside": true,
                  "roomName": "Living Room",
                    "_links": {
                        "self": {
                        "href": "http://localhost/api/v1/rooms/r1"
                        }
                    }
                }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * Test to add a room that already exists.
     * @throws Exception if the room ID already exists.
     */

    @Test
    void existingRoom_shouldReturnConflict() throws Exception {
        // Arrange
        RoomEntryWebDTO roomWebDTO = new RoomEntryWebDTO( 10, 10, 10, 0, "h1", true, "Living Room");
        when(generateRandomId.generateID()).thenReturn("r1");
        Room room = setupRoom(roomWebDTO);

        when(repositoryHouse.containsOfIdentity(room.getHouseId())).thenReturn(true);
        doThrow(new DataIntegrityViolationException("Room already exists")).when(repositoryRoom).save(room);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rooms")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(roomWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        //Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Room already exists", resultContent);
    }

    /**
     * Test to add a room with a non-existing house ID.
     * @throws Exception If house ID does not exist.
     */

    @Test
    void addRoomWithNonExistingHouseID_shouldReturnBadRequest() throws Exception {
        // Arrange
        RoomEntryWebDTO roomWebDTO = new RoomEntryWebDTO( 10, 10, 10, 0, "h1", true, "Living Room");
        when(generateRandomId.generateID()).thenReturn("r1");
        Room room = setupRoom(roomWebDTO);

        when(repositoryHouse.containsOfIdentity(room.getHouseId())).thenReturn(false);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rooms")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(roomWebDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("House not found", resultContent);
    }

    /**
     * Test to successfully obtain a room with a valid ID.
     */
    @Test
    void obtainRoomByValidID_shouldReturnRoomID() throws Exception {
        // Arrange
        RoomEntryWebDTO roomWebDTO = new RoomEntryWebDTO( 10, 10, 10, 0, "h1", true, "Living Room");
        when(generateRandomId.generateID()).thenReturn("r1");
        Room room = setupRoom(roomWebDTO);

        when(repositoryRoom.ofIdentity(room.identity())).thenReturn(Optional.of(room));

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/rooms/r1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        String expectedContent = """
                  {
                   "roomId": "r1",
                   "length": 10.0,
                   "width": 10.0,
                   "height": 10.0,
                   "floorNumber": 0,
                   "houseId": "h1",
                   "inside": true,
                   "roomName": "Living Room",
                        "_links": {
                               "devices": {
                                   "href": "http://localhost/api/v1/rooms/r1/devices"
                               }
                           }
                   }
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * Test to obtain a room with an invalid ID.
     * @throws Exception If the room ID is not valid or non-existent.
     */
    @Test
    void obtainRoomByInvalidID_shouldReturnRoomID() throws Exception {
        // Arrange
        RoomEntryWebDTO roomWebDTO = new RoomEntryWebDTO(10, 10, 10, 0, "h1", true, "Living Room");
        when(generateRandomId.generateID()).thenReturn("r1");
        Room room = setupRoom(roomWebDTO);

        when(repositoryRoom.ofIdentity(room.identity())).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/rooms/r1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Room not found", resultContent);
    }

    /**
     * Test to successfully obtain devices by room ID.
     */
    @Test
    void getDevicesByRoomID_shouldReturnDevices() throws Exception {
        // Arrange
        RoomID roomID = new RoomID("r1");

        Device device1 = new Device(new DeviceId("d1"), new DeviceName("name1"), new DeviceModel("model1"), new ActivationStatus(true), roomID);
        Device device2 = new Device(new DeviceId("d2"), new DeviceName("name2"), new DeviceModel("model2"), new ActivationStatus(true), roomID);

        when(repositoryRoom.containsOfIdentity(roomID)).thenReturn(true);
        when(repositoryDevice.getDevicesInRoom(roomID)).thenReturn(java.util.List.of(device1, device2));


        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/rooms/r1/devices")
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
                     },
                    {
                        "id": "d2",
                        "links": [
                            {
                             "rel": "self",
                             "href": "http://localhost/api/v1/devices/d2"
                            }
                        ]
                    }
                ]
                """;
        JSONAssert.assertEquals(expectedContent, resultContent, true);
    }

    /**
     * Test to obtain devices with an invalid room ID.
     * @throws Exception If the room ID is not valid or non-existent.
     */
    @Test
    void getDevices_shouldReturnNotFound() throws Exception {
        // Arrange
        RoomID roomID = new RoomID("r1");

        when(repositoryRoom.containsOfIdentity(roomID)).thenReturn(false);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/rooms/r1/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("Room not found", resultContent);
    }
}