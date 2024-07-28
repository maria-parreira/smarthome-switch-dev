package smartHomeDDD.domain.controllers;

import jakarta.persistence.EntityNotFoundException;
import smartHomeDDD.controllers.ConfigureHouseLocationController;
import smartHomeDDD.domain.house.*;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.HouseDTO;
import smartHomeDDD.dto.LocationDTO;
import smartHomeDDD.persistence.mem.RepositoryHouseMem;

import smartHomeDDD.services.ServiceHouse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains a series of tests for the functionality of configuring location.
 * It encompasses the following scenarios:
 * - Valid house configuration with a new location in Portugal.
 * - Valid house configuration with a new location in the USA.
 * - Not configuring location for a valid house.
 * - Not configuring location when the house ID is null.
 * - Not configuring location when the house ID is empty.
 * - Not configuring location when the house ID is not valid.
 * - Not configuring location when the address is null.
 * - Not configuring location when the country is invalid.
 * - Not configuring location when the zip code is invalid.
 * - Not configuring location when the latitude is invalid.
 * - Not configuring location when the longitude is invalid.
 * - Not configuring location when the repository is null.
 * - Not configuring location when the service is null.
 * - Valid house configuration with a new location in Portugal using JPA repository.
 */
class ConfigureHouseLocationControllerTest {

    /**
     *  Test case for validating the configuration of a house location.
    */

    @Test
    void validHouse_shouldConfigureLocationPortugal_ShouldReturnHouseWithNewLocation() {

        //Arrange

        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal", "1234-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //Act
        LocationDTO locationDTO = new LocationDTO("Avenida Aliados", "Portugal", "4564-789", 90, 180, "1");

        HouseDTO result = us01Controller.configureHouseLocation(locationDTO);

        //get house location
        String houseLocation = myHouseSaved.getHouseLocation().toString();
        String expected = "Avenida Aliados 4564-789 GPSCoordinates{latitude=90.0, longitude=180.0}";

        //Assert
        assertEquals(expected, houseLocation); //same house saved with new location
        assertEquals("1", result.getId());//house id to confirm new location
    }

    /**
     * Test case for validating the configuration of a house location to a new location in the USA.
     */
    @Test
    void validHouse_shouldConfigureLocation_ChangeToUSA_ShouldReturnHouseWithNewLocation() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1234-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);


        //Act
        LocationDTO locationDTO = new LocationDTO("5th Avenue","USA","45653-1234",90, 180, "1");

        HouseDTO result = us01Controller.configureHouseLocation(locationDTO);

        //get house location
        String houseLocation = myHouseSaved.getHouseLocation().toString();
        String expected = "5th Avenue 45653-1234 GPSCoordinates{latitude=90.0, longitude=180.0}";

        //Assert
        assertEquals(expected, houseLocation); //same house saved with new location
        assertEquals("1", result.getId()); //house id to confirm new location
    }

    /**
     * Tests the behavior of not configuring location when the house ID not exists in Repository.
     */
    @Test
    void shouldNotConfigureLocation_notValidHouseID_ShouldReturnException() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1236-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.update(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String expected = "Rua Augusta 1236-456 GPSCoordinates{latitude=55.0, longitude=178.0}";
        String houseLocation = myHouseSaved.getHouseLocation().toString();

        //Act
        LocationDTO locationDTO = new LocationDTO("avenue des alliés", "France","12345", 90, 180, "2");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(expected, houseLocation); // location continues the same
        assertEquals("1", myHouse.identity().toString()); // house id continues the same
        assertTrue(exception.getMessage().contains("Can't find House"));
    }

    /**
     * Tests the behavior of not configuring location when the house ID is null.
     */
    @Test
    void shouldNotConfigureLocation_NullHouseID_ShouldReturnException() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1235-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String actualLocation = "Rua Augusta 1235-456 GPSCoordinates{latitude=55.0, longitude=178.0}";
        String houseLocation = myHouseSaved.getHouseLocation().toString();

        //Act
        LocationDTO locationDTO = new LocationDTO("avenida aliados","Portugal", "456-789", 90, 180, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("HouseId can't be null"));
    }

    /**
     * Tests the behavior of not configuring location when the houseId is empty.
     */
    @Test
    void shouldNotConfigureLocation_EmptyHouseID_ShouldReturnException() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1238-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create  controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String actualLocation = "Rua Augusta 1238-456 GPSCoordinates{latitude=55.0, longitude=178.0}";
        String houseLocation = myHouseSaved.getHouseLocation().toString();

        //Act
        LocationDTO locationDTO = new LocationDTO("avenida aliados", "Portugal","4569-789", 90, 180, " ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("HouseId can't be empty"));

    }

        /**
         * Tests the behavior of not configuring location when the address is null.
         */
        @Test
        void shouldNotConfigureLocation_NullAddress_ShouldReturnException() {

            //Arrange
            //create Value objects
            Address address = new Address("Rua Augusta");
            ZipCode zipCode = new ZipCode("Portugal","1237-456");
            Latitude latitude = new Latitude(55);
            Longitude longitude = new Longitude(178);
            GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

            //create location and houseId
            HouseId houseId = new HouseId("1");
            Location location = new Location(address, zipCode, gpsCoordinates);

            // create house entity
            FactoryHouse factoryHouse = new ImplFactoryHouse();
            House myHouse = factoryHouse.createHouse(houseId, location);

            // save house at repo
            RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
            House myHouseSaved = repositoryHouse.save(myHouse);

            //create controller and service
            ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
            ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

            //get house location
            String actualLocation = "Rua Augusta 1237-456 GPSCoordinates{latitude=55.0, longitude=178.0}";
            String houseLocation = myHouseSaved.getHouseLocation().toString();

            //Act
            LocationDTO locationDTO = new LocationDTO(null,"Portugal", "4568-789", 90, 180, "1");

            Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

            //Assert
            assertEquals(actualLocation, houseLocation); // location continues the same
            assertTrue(exception.getMessage().contains("Address can't be null"));
        }

    /**
     * Tests the behavior of not configuring location when the country is invalid.
     */
    @Test
    void shouldNotConfigureLocation_InvalidCountry_ShouldReturnException() {

        //Arrange

        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1239-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String actualLocation = "Rua Augusta 1239-456 GPSCoordinates{latitude=55.0, longitude=178.0}";
        String houseLocation = myHouseSaved.getHouseLocation().toString();

        //Act
        LocationDTO locationDTO = new LocationDTO("avenida aliados", "ABC","123", 90, 180, "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("Invalid country"));
    }

    /**
     * Tests the behavior of not configuring location when the zip code is invalid.
     */
    @Test
    void shouldNotConfigureLocation_ValidCountry_InvalidZipCode_ShouldReturnException() {

        //Arrange

        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1239-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String actualLocation = "Rua Augusta 1239-456 GPSCoordinates{latitude=55.0, longitude=178.0}";
        String houseLocation = myHouseSaved.getHouseLocation().toString();

        //Act
        LocationDTO locationDTO = new LocationDTO("avenida aliados", "USA","123", 90, 180, "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("Invalid arguments provided."));
    }

    /**
     * Tests the behavior of not configuring location when the latitude is invalid.
     */
    @Test
    void shouldNotConfigureLocation_notValidLatitude_ShouldReturnException() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1239-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String houseLocation = myHouseSaved.getHouseLocation().toString();
        String actualLocation = "Rua Augusta 1239-456 GPSCoordinates{latitude=55.0, longitude=178.0}";

        //Act
        LocationDTO locationDTO = new LocationDTO("Avenida Aliados", "Portugal","4569-789", -300, 180, "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("Invalid latitude. Must be between -90.0 and 90.0."));
    }

    /**
     * Tests the behavior of not configuring location when the longitude is invalid.
     */
    @Test
    void shouldNotConfigureLocation_notValidLongitude_ShouldReturnException() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1239-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //create controller and service
        ServiceHouse serviceHouse = new ServiceHouse(repositoryHouse,new ImplFactoryHouse());
        ConfigureHouseLocationController us01Controller = new ConfigureHouseLocationController(serviceHouse);

        //get house location
        String houseLocation = myHouseSaved.getHouseLocation().toString();
        String actualLocation = "Rua Augusta 1239-456 GPSCoordinates{latitude=55.0, longitude=178.0}";

        //Act
        LocationDTO locationDTO = new LocationDTO("Avenida Aliados","Portugal", "4568-789", 60.0, -181.0, "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> us01Controller.configureHouseLocation(locationDTO));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("Invalid longitude. Must be between -180.0 and 180.0."));
    }

    /**
     * Tests the scenario where configuring a location with a null repository for a house entity should return an exception.
     */
    @Test
    void shouldNotConfigureLocation_nullRepoHouse_ShouldReturnException() {

        //Arrange
        //create Value objects
        Address address = new Address("Rua Augusta");
        ZipCode zipCode = new ZipCode("Portugal","1239-456");
        Latitude latitude = new Latitude(55);
        Longitude longitude = new Longitude(178);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(latitude, longitude);

        //create location and houseId
        HouseId houseId = new HouseId("1");
        Location location = new Location(address, zipCode, gpsCoordinates);

        // create house entity with location and houseId
        FactoryHouse factoryHouse = new ImplFactoryHouse();
        House myHouse = factoryHouse.createHouse(houseId, location);

        // save house at repo
        RepositoryHouseMem repositoryHouse = new RepositoryHouseMem();
        House myHouseSaved = repositoryHouse.save(myHouse);

        //get house location
        String houseLocation = myHouseSaved.getHouseLocation().toString();
        String actualLocation = "Rua Augusta 1239-456 GPSCoordinates{latitude=55.0, longitude=178.0}";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ServiceHouse(null,null));

        //Assert
        assertEquals(actualLocation, houseLocation); // location continues the same
        assertTrue(exception.getMessage().contains("House Repository cannot be null"));
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void nullHouseService_ShouldThrowIllegalArgumentException() {
        // Arrange
        String expectedMessage = "Service cannot be null";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ConfigureHouseLocationController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}

