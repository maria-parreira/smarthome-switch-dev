package smartHomeDDD.domain.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.controllers.DefineSensorTypeController;
import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.dto.SensorTypeDTO;
import smartHomeDDD.persistence.mem.RepositorySensorTypeMem;
import static org.junit.jupiter.api.Assertions.*;

import smartHomeDDD.services.ServiceSensorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


/**
 * The DefineSensorTypeControllerTest class contains test cases for the
 * DefineSensorTypeController class.
 * It compasses the following scenarios:
 * - Valid sensor type: Should add sensor type to repository.
 * - Repeated sensor type: Should throw exception.
 * - Empty description: Should throw exception.
 * - Empty unit: Should throw exception.
 * - Empty sensor type ID: Should throw exception.
 */


class DefineSensorTypeControllerTest {

    /**
     * Test case to verify that a valid sensor type is added to the repository.
     */
    @Test
    void validSensorType_shouldAddSensorTypeToRepository() {
        //Arrange
        String description = "description1";
        String unit = "unit1";
        String sensorTypeID = "sensorTypeID1";

        ImplFactorySensorType factorySensorType = new ImplFactorySensorType();
        RepositorySensorTypeMem repositorySensorType = new RepositorySensorTypeMem();
        ServiceSensorType serviceSensorType = new ServiceSensorType(repositorySensorType, factorySensorType);
        DefineSensorTypeController myController = new DefineSensorTypeController(serviceSensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID, description, unit);

        //Act
        SensorTypeDTO foundSensorTypeDTO = myController.defineSensorType(sensorTypeDTO);

        //Assert
        assertEquals(description, foundSensorTypeDTO.getDescription());
        assertEquals(unit, foundSensorTypeDTO.getUnit());
        assertEquals(sensorTypeID, foundSensorTypeDTO.getSensorTypeID());

    }

    /**
     * Tests if defining a sensor type with an already existing identity throws an exception.
     * The test verifies that the defineSensorType method of the DefineSensorTypeController class throws an
     * IllegalArgumentException when it is called with a SensorTypeDTO that has the same identity as a SensorTypeDTO already defined.
     */

    @Test
    void repeatedSensorType_shouldThrowException() {
        //Arrange
        String description = "description1";
        String unit = "unit1";
        String sensorTypeID = "sensorTypeID1";

        ImplFactorySensorType factorySensorType = new ImplFactorySensorType();
        RepositorySensorTypeMem repositorySensorType = new RepositorySensorTypeMem();
        ServiceSensorType serviceSensorType = new ServiceSensorType(repositorySensorType, factorySensorType);
        DefineSensorTypeController myController = new DefineSensorTypeController(serviceSensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID, description, unit);
        SensorTypeDTO otherSensorTypeDTO = new SensorTypeDTO(sensorTypeID, description, unit);

        //Act
        myController.defineSensorType(sensorTypeDTO);

        String expectedMessage = "Sensor Type already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> myController.defineSensorType(otherSensorTypeDTO));

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to verify that an exception is thrown when the description is empty.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_descriptionEmpty(String description) {
        //Arrange
        String unit = "unit1";
        String sensorTypeID = "sensorTypeID1";

        ImplFactorySensorType factorySensorType = new ImplFactorySensorType();
        RepositorySensorTypeMem repositorySensorType = new RepositorySensorTypeMem();
        ServiceSensorType serviceSensorType = new ServiceSensorType(repositorySensorType, factorySensorType);
        DefineSensorTypeController myController = new DefineSensorTypeController(serviceSensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID, description, unit);

        String expectedMessage = "Description cannot be null or empty";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.defineSensorType(sensorTypeDTO));
        String actualMessage = exception.getMessage();
        //Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify that an exception is thrown when the unit is empty.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_unitEmpty(String unit) {
        //Arrange
        String description = "description1";
        String sensorTypeID = "sensorTypeID1";

        ImplFactorySensorType factorySensorType = new ImplFactorySensorType();
        RepositorySensorTypeMem repositorySensorType = new RepositorySensorTypeMem();
        ServiceSensorType serviceSensorType = new ServiceSensorType(repositorySensorType, factorySensorType);
        DefineSensorTypeController myController = new DefineSensorTypeController(serviceSensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID, description, unit);

        String expectedMessage = "Unit cannot be null or empty";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.defineSensorType(sensorTypeDTO));
        String actualMessage = exception.getMessage();
        //Assert
        assertEquals(expectedMessage, actualMessage);
    }


    /**
     * Test case to verify that an exception is thrown when the sensor type ID is empty.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_sensorTypeIDEmpty(String sensorTypeID) {
        //Arrange
        String description = "description1";
        String unit = "unit1";

        ImplFactorySensorType factorySensorType = new ImplFactorySensorType();
        RepositorySensorTypeMem repositorySensorType = new RepositorySensorTypeMem();
        ServiceSensorType serviceSensorType = new ServiceSensorType(repositorySensorType, factorySensorType);
        DefineSensorTypeController myController = new DefineSensorTypeController(serviceSensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID, description, unit);

        String expectedMessage = "sensorType ID cannot be null or empty";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> myController.defineSensorType(sensorTypeDTO));
        String actualMessage = exception.getMessage();
        //Assert
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void constructor_NullServiceSensorType_ShouldThrowIllegalArgumentException() {
        // Arrange
        ServiceSensorType serviceSensorType = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DefineSensorTypeController(serviceSensorType));
    }

}
