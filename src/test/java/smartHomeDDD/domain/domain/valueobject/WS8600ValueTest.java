package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.WS8600Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the WS8600Value class.
 * It provides tests for the following scenarios:
 * - Creation of a WS8600Value with valid speed and direction.
 * - Creation of a WS8600Value with an invalid negative speed.
 * - Creation of a WS8600Value with an invalid negative direction.
 * - Creation of a WS8600Value with an invalid direction greater than 2*PI.
 * - Creation of a WS8600Value with a speed and direction of 0 (edge case).
 * - Creation of a WS8600Value with a speed of 0 and a direction of 2*PI (edge case).
 * - The method radiansToCardinal returns the correct cardinal direction - East.
 * - The method radiansToCardinal returns the correct cardinal direction - North East.
 * - The method radiansToCardinal returns the correct cardinal direction - North.
 * - The method radiansToCardinal returns the correct cardinal direction - North West.
 * - The method radiansToCardinal returns the correct cardinal direction - West.
 * - The method radiansToCardinal returns the correct cardinal direction - South West.
 * - The method radiansToCardinal returns the correct cardinal direction - South.
 * - The method radiansToCardinal returns the correct cardinal direction - South East.
 * - The method radiansToCardinal returns the correct cardinal direction - East (boundary value).
 * - The method radiansToCardinal returns the correct cardinal direction - North (boundary value).
 * - The method radiansToCardinal returns the correct cardinal direction - North West (boundary value).
 * - The method radiansToCardinal returns the correct cardinal direction - West (boundary value).
 * - The method radiansToCardinal returns the correct cardinal direction - South West (boundary value).
 * - The method radiansToCardinal returns the correct cardinal direction - South (boundary value).
 * - The method radiansToCardinal returns the correct cardinal direction - South East (boundary value).
 */
class WS8600ValueTest {

    /**
     * Tests the creation of a WS8600Value with valid speed and direction.
     */
    @Test
    void constructor_ValidSpeedAndDirection_ShouldCreateWS8600Value() {

        // arrange
        double direction = 0;
        double speed = 10;
        String expected = "Wind Speed: 10.0 Km/h; Wind Direction: 0.0 rad - East";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.toString();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Tests the creation of a valid WS8600Value with an invalid negative speed.
     */
    @Test
    void constructor_NegativeSpeed_ShouldThrowException() {

        // arrange
        double direction = 0;
        double speed = -10;

        // act & assert
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new WS8600Value(speed, direction);
        });

        String expectedMessage = "Invalid direction or speed";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Tests the creation of a WS8600Value with an invalid negative direction.
     */
    @Test
    void constructor_NegativeDirection_ShouldThrowException() {
        // arrange
        double direction = -1;
        double speed = 10;

        // act & assert
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new WS8600Value(speed, direction);
        });

        String expectedMessage = "Invalid direction or speed";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Tests the creation of a valid WS8600Value with an invalid direction greater than 2*PI.
     * */
    @Test
    void constructor_DirectionGreaterThan2PI_ShouldThrowException() {
        // arrange
        double direction = 3 * Math.PI;
        double speed = 10.2;

        // act & assert
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new WS8600Value(speed, direction);
        });

        String expectedMessage = "Invalid direction or speed";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Boundary test to verify that the creation of a WS8600Value with a speed and direction of 0
     * does not throw an exception.
     */
    @Test
    void constructor_SpeedAndDirectionZeroBoundary_ShouldNotThrowException() {
        // arrange
        double direction = 0;
        double speed = 0;

        // act & assert
        assertDoesNotThrow(() -> new WS8600Value(speed, direction));
    }

    /**
     * Boundary test to verify that the creation of a WS8600Value with a speed of 0 and a direction of 2*PI
     * does not throw an exception.
     */
    @Test
    void constructor_Direction2PIBoundary_ShouldNotThrowException() {
        // arrange
        double direction = 2 * Math.PI;
        double speed = 10;

        // act & assert
        assertDoesNotThrow(() -> new WS8600Value(speed, direction));
    }

    /**
     * Test to verify that the method radiansToCardinal returns "East" for a direction of 0.
     */
    @Test
    void radiansToCardinal_ShouldReturnEast() {
        // arrange
        double direction = 0;

        // act
        WS8600Value ws8600Value = new WS8600Value(10, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals("East", result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "North East" for a direction of 0.25 * PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnNorthEast() {
        // arrange
        double direction = 0.25 * Math.PI;
        double speed = 10.2;
        String expected = "North East";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "North" for a direction of 0.5 * PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnNorth() {
        // arrange
        double direction = 0.5 * Math.PI;
        double speed = 10.2;
        String expected = "North";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "North West" for a direction of 0.75 * PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnNorthWest() {
        // arrange
        double direction = 0.75 * Math.PI;
        double speed = 10.2;
        String expected = "North West";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "West" for a direction of PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnWest() {
        // arrange
        double direction = Math.PI;
        double speed = 10.2;
        String expected = "West";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "South West" for a direction of 1.25 * PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnSouthWest() {
        // arrange
        double direction = 1.25 * Math.PI;
        double speed = 10.2;
        String expected = "South West";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "South" for a direction of 1.5 * PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnSouth() {
        // arrange
        double direction = 1.5 * Math.PI;
        double speed = 10.2;
        String expected = "South";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the method radiansToCardinal returns "South East" for a direction of 1.75 * PI.
     */
    @Test
    void radiansToCardinal_ShouldReturnSouthEast() {
        // arrange
        double direction = 1.75 * Math.PI;
        double speed = 10.2;
        String expected = "South East";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "East" for its lower boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueNorthEast_ShouldReturnSouthNorthEast() {
        // arrange
        double direction = 0.125 * Math.PI; // boundary for North East
        double speed = 10;
        String expected = "North East";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "North" for its boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueNorth_ShouldReturnNorth() {
        // arrange
        double direction = 0.375 * Math.PI; // boundary for North
        double speed = 10;
        String expected = "North";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "North West" for its boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueNorthWest_ShouldReturnNorthWest() {
        // arrange
        double direction = 0.625 * Math.PI; // boundary for North West
        double speed = 10;
        String expected = "North West";

        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "West" for its boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueWest_ShouldReturnWest() {
        // arrange
        double direction = 0.875 * Math.PI; // boundary for West
        double speed = 10;
        String expected = "West";


        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "South West" for its boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueSouthWest_ShouldReturnSouthWest() {
        // arrange
        double direction = 1.125 * Math.PI; // boundary for South West
        double speed = 10;
        String expected = "South West";


        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "South" for its boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueSouth_ShouldReturnSouth() {
        // arrange
        double direction = 1.375 * Math.PI; // boundary for South
        double speed = 10;
        String expected = "South";


        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }

    /**
     * Boundary test to verify that the method radiansToCardinal returns "South East" for its boundary value.
     */
    @Test
    void radiansToCardinal_BoundaryValueSouthEast_ShouldReturnSouthEast() {
        // arrange
        double direction = 1.625 * Math.PI; // boundary for South East
        double speed = 10;
        String expected = "South East";


        // act
        WS8600Value ws8600Value = new WS8600Value(speed, direction);
        String result = ws8600Value.radiansToCardinal();

        // assert
        assertEquals(expected, result);
    }
}
