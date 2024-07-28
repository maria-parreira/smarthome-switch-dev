package smartHomeDDD.domain.dto;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.ONF01AValue;
import smartHomeDDD.domain.valueobject.OPNCL0100Value;
import smartHomeDDD.domain.valueobject.SPV300Value;
import smartHomeDDD.dto.ActuatorMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the corresponding Test Class for the ActuatorMapper. It Lists the following scenarios:
 * The successful instantiation of a ActuatorDTO;
 * The unsuccessful instantiation of an ActuatorDTO due to invalid ModelName;
 */
class ActuatorMapperTest {
    /**
     * Tests whether an ActuatorDTO object is successfully instantiated given a valid Value and ModelName.
     */
    @Test
    void validValueStringAndMode_ShouldReturnOPNCL0100ValueInstance() throws InstantiationException {
        // Arrange
        String value = "30"; // Is converted to int.
        String modelName = "OPNCL0100";
        // Act
        Value myValue = ActuatorMapper.convertToValue(value, modelName);
        // Assert
        assertNotNull(myValue);
        assertInstanceOf(OPNCL0100Value.class, myValue);
    }

    /**
     * Tests whether an ActuatorDTO object is successfully instantiated given a valid Value and ModelName.
     */
    @Test
    void validValueStringAndMode_ShouldReturnSPV300ValueInstance() throws InstantiationException {
        // Arrange
        String value = "30"; // Is converted to double.
        String modelName = "SPV300";
        // Act
        Value myValue = ActuatorMapper.convertToValue(value, modelName);
        // Assert
        assertNotNull(myValue);
        assertInstanceOf(SPV300Value.class, myValue);
    }

    /**
     * Tests whether an ActuatorDTO object is successfully instantiated given a valid Value and ModelName.
     */
    @Test
    void validValueStringAndMode_ShouldReturnONF01AValueInstance() throws InstantiationException{
        // Arrange
        String value = "OFF"; // String types are not converted.
        String modelName = "ONF01A";
        // Act
        Value myValue = ActuatorMapper.convertToValue(value, modelName);
        // Assert
        assertNotNull(myValue);
        assertInstanceOf(ONF01AValue.class, myValue);
    }

    /**
     * Tests whether an ActuatorDTO object is not instantiated given an invalid Model Name.
     */
    @Test
    void invalidModelName_ShouldReturnNullPointerException(){
        // Arrange
        String value = "30";
        String modelName = "doesnt";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.convertToValue(value, modelName));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Model is not present in the system.";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Tests whether an ActuatorDTO object is not instantiated given an invalid value for a model present in the system.
     */
    @Test
    void invalidTypeDataForModel_ShouldReturnNull() {
        // Arrange
        String value = "30"; // Int type data.
        String modelName = "ONF01A"; // This model only accepts strings as value.
        // Act
        Exception exception = assertThrows(NumberFormatException.class, () -> ActuatorMapper.convertToValue(value, modelName));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Input value not valid.";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));

    }

}
