package smartHomeDDD;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.services.*;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {

    /**
     * Static HashMap to store room identities*/
    private static final HashMap<String, String> roomIdentitiesMap = new HashMap<>();

    private static final HashMap<String, String> deviceIdentitiesMap = new HashMap<>();

    private static final HashMap<String, String> sensorIdentitiesMap = new HashMap<>();

    private static final HashMap<String, String> actuatorIdentitiesMap = new HashMap<>();


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    static final String PATHNAME = "config.properties";

    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started
     * It reads a house from a properties file and saves it into a house repository.
     */
    @Bean
    @Order(1)
    @Profile("dev")
    public CommandLineRunner houseCommandLineRunner(ServiceHouse serviceHouse) {
        //if(args[0]="testing")
        return args -> {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] house = config.getStringArray("house");
            String[] houseParts = house[0].split("\\.");

            serviceHouse.addHouse(
                    new HouseId(houseParts[0]),
                    new Location(
                            new Address(houseParts[1]),
                            new ZipCode(houseParts[2], houseParts[3]),
                            new GPSCoordinates(
                                    new Latitude(Double.parseDouble(houseParts[4])),
                                    new Longitude(Double.parseDouble(houseParts[5]))))
            );
        };
    }


    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started
     * It reads a room from a properties file and saves it into a room repository.
     */
    @Bean
    @Order(2)
    @Profile("dev")
    public CommandLineRunner roomCommandLineRunner(ServiceRoom serviceRoom) {
        return args -> {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] rooms = config.getStringArray("room");

            for (String room : rooms) {
                String[] roomParts = room.split("\\.");

                Room savedRoom = serviceRoom.addRoomToHouse(
                        new HouseId(roomParts[0]),
                        new FloorNumber(Integer.parseInt(roomParts[2])),
                        new Dimensions(
                                new Length(Integer.parseInt(roomParts[3])),
                                new Width(Integer.parseInt(roomParts[4])),
                                new Height(Integer.parseInt(roomParts[5]))),
                        Boolean.parseBoolean(roomParts[6]),
                        new RoomName(roomParts[7])
                );

                roomIdentitiesMap.put(roomParts[1], savedRoom.identity().toString());
            }


        };
    }


    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started
     * It reads a device from a properties file and saves it into a device repository.
     */
    @Bean
    @Order(3)
    @Profile("dev")
    public CommandLineRunner deviceCommandLineRunner(ServiceDevice serviceDevice) {
        return args -> {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] devices = config.getStringArray("device");

            for (String device : devices) {
                String[] deviceParts = device.split("\\.");

                // Get the room identity from the map
                String roomId = roomIdentitiesMap.get(deviceParts[4]);

                Device savedDevice = serviceDevice.addNewDevice(
                        new DeviceName(deviceParts[1]),
                        new DeviceModel(deviceParts[2]),
                        new ActivationStatus(Boolean.parseBoolean(deviceParts[3])),
                        new RoomID(roomId)
                );

                deviceIdentitiesMap.put(deviceParts[0], savedDevice.identity().toString());
            }
        };
    }


    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads sensor types from a properties file and saves them into a sensor type repository.
     */
    @Bean
    @Order(4)
    @Profile("dev")
    public CommandLineRunner sensorTypeCommandLineRunner(ServiceSensorType serviceSensorType) {
        return args -> {

            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] sensorTypes = config.getStringArray("sensorType");

            for (String sensorType : sensorTypes) {

                String[] sensorTypeParts = sensorType.split("\\.");
                serviceSensorType.createSensorType(
                        new SensorTypeID(sensorTypeParts[0]),
                        new Description(sensorTypeParts[1]),
                        new Unit(sensorTypeParts[2])
                );
            }
        };
    }

    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads actuator types from a properties file and saves them into an actuator type repository.
     */
    @Bean
    @Order(5)
    @Profile("dev")
    public CommandLineRunner actuatorTypeCommandLineRunner(ServiceActuatorType serviceActuatorType) {
        return args -> {

            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] actuatorTypes = config.getStringArray("actuatorType");

            for (String actuatorType : actuatorTypes) {

                String[] actuatorTypeParts = actuatorType.split("\\.");
                serviceActuatorType.createActuatorType(
                        new ActuatorTypeID(actuatorTypeParts[0]),
                        new Description(actuatorTypeParts[1]),
                        new Unit(actuatorTypeParts[2])
                );
            }
        };
    }

    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads sensor models from a properties file and saves them into a sensor model repository.
     */
    @Bean
    @Order(6)
    @Profile("dev")
    public CommandLineRunner sensorModelsLineRunner(ServiceSensorModel serviceSensorModel) {
        return args -> {

            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] sensorModels = config.getStringArray("sensorModel");

            for (String sensorModel : sensorModels) {

                String[] sensorModelParts = sensorModel.split("\\.");
                serviceSensorModel.addSensorModel(
                        new SensorModelID(sensorModelParts[0]),
                        new SensorTypeID(sensorModelParts[1])
                );
            }
        };
    }

    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads actuator models from a properties file and saves them into an actuator model repository.
     */
    @Bean
    @Order(7)
    @Profile("dev")
    public CommandLineRunner actuatorModelsLineRunner(ServiceActuatorModel serviceActuatorModel) {
        return args -> {

            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] actuatorModels = config.getStringArray("actuatorModel");

            for (String actuatorModel : actuatorModels) {

                String[] actuatorModelParts = actuatorModel.split("\\.");
                serviceActuatorModel.createActuatorModel(
                        new ActuatorModelID(actuatorModelParts[0]),
                        new ActuatorTypeID(actuatorModelParts[1])
                );
            }
        };
    }


    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads sensor from a properties file and saves them into a sensor repository.
     */
    @Bean
    @Order(8)
    @Profile("dev")
    public CommandLineRunner sensorCommandLineRunner(ServiceSensor serviceSensor) {
        return args -> {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] sensors = config.getStringArray("sensor1");

            for (String sensor : sensors) {
                String[] sensorParts = sensor.split("\\.");

                // Get the device identity from the map
                String deviceId = deviceIdentitiesMap.get(sensorParts[1]);

                Sensor savedSensor = serviceSensor.createNewSensor(
                        new DeviceId(deviceId),
                        new SensorModelID(sensorParts[2])
                );

                sensorIdentitiesMap.put(sensorParts[0], savedSensor.identity().toString());
            }
        };
    }


    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads actuator from a properties file and saves them into a actuator repository.
     */
    @Bean
    @Order(9)
    @Profile("dev")
    public CommandLineRunner actuatorCommandLineRunner(ServiceActuator serviceActuator) {
        return args -> {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] actuators = config.getStringArray("actuator1");

            for (String actuator : actuators) {
                String[] actuatorParts = actuator.split("\\.");

                // Get the device identity from the map
                String deviceId = deviceIdentitiesMap.get(actuatorParts[1]);

                Actuator savedActuator = serviceActuator.addNewActuator(
                        new DeviceId(deviceId),
                        new ActuatorModelID(actuatorParts[2])
                );

                actuatorIdentitiesMap.put(actuatorParts[0], savedActuator.identity().toString());
            }
        };
    }


    /**
     * This method initializes a CommandLineRunner bean that is executed after the application context is fully started.
     * It reads sensor readings from a properties file and saves them into a sensor reading repository.
     */
    @Bean
    @Order(10)
    @Profile("dev")
    public CommandLineRunner sensorReadingsLineRunner(ServiceSensorReading serviceSensorReading) {
        return args -> {

            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(PATHNAME));
            String[] sensorReadings = config.getStringArray("sensorReading");

            for (String sensorReading : sensorReadings) {

                String[] sensorReadingParts = sensorReading.split("\\.");

                // Get the device identity from the map
                String deviceId = deviceIdentitiesMap.get(sensorReadingParts[2]);

                // Get the sensor identity from the map
                String sensorId = sensorIdentitiesMap.get(sensorReadingParts[3]);

                serviceSensorReading.addSensorReading(
                        new Reading(sensorReadingParts[1]),
                        new DeviceId(deviceId),
                        new SensorID(sensorId),
                        Timestamp.valueOf(sensorReadingParts[4])
                );
            }
        };
    }


}