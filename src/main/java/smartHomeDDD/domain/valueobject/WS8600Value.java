package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * Implementation of the Value interface representing the value of an WS8600 sensor.
 */
public class WS8600Value implements Value, ValueObject {

    /**
     * The wind speed.
     */
    private final double _speed;

    /**
     * The wind direction.
     */
    private final double _direction;

    /**
     * Constructs a new WS8600Value with the specified speed and direction.
     *
     * @param speed The speed of the wind.
     * @param direction The direction of the wind.
     * @throws IllegalArgumentException If the direction or speed is invalid.
     */
    public WS8600Value(double speed, double direction) {
        if(!isDirectionValid(direction) || !isSpeedValid(speed)) {
            throw new IllegalArgumentException ("Invalid direction or speed");
        }
        this._direction = direction;
        this._speed = speed;
    }

    /**
     * Checks if the provided speed is valid (non-negative).
     *
     * @param speed The speed value to be validated.
     * @return True if the speed is valid, false otherwise.
     */
    private boolean isSpeedValid(double speed){
        if(speed < 0.00){
            return false;
        }
        return true;
    }

    /**
     * Check if the direction falls within a valid range for a radian value.
     * @param direction The direction value to be validated.
     * @return True if the direction is valid, false otherwise.
     */
    private boolean isDirectionValid(double direction){
        if (direction < 0.00 || direction > 2 * Math.PI) {
            return false;
        }
        return true;
    }

    /**
     * Converts radians to cardinal direction.
     *
     * @return The cardinal direction corresponding to the provided radians.
     */
    public String radiansToCardinal() {

        if (_direction < 0.125 * Math.PI) {
            return "East";
        }
        if (_direction < 0.375 * Math.PI) {
            return "North East";
        }
        if (_direction < 0.625 * Math.PI) {
            return "North";
        }
        if (_direction < 0.875 * Math.PI) {
            return "North West";
        }
        if (_direction < 1.125 * Math.PI) {
            return "West";
        }
        if (_direction < 1.375 * Math.PI) {
            return "South West";
        }
        if (_direction < 1.625 * Math.PI) {
            return "South";
        }
        return "South East";
    }

    /**
     * Returns a string representation of the WS8600Value.
     *
     * @return A string with the wind speed, wind direction, and cardinal direction.
     */
    public String toString() {
        return "Wind Speed: " + this._speed + " Km/h; Wind Direction: " + this._direction + " rad - " + radiansToCardinal();
    }
}
