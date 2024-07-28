/**
 * The ImplFactorySensor class is a concrete implementation of the
 * FactorySensor interface. It is responsible for creating Sensor objects
 * using the provided parameters.
 */
package smartHomeDDD.domain.sensor;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import java.lang.reflect.InvocationTargetException;
@Component
public class ImplFactorySensor implements FactorySensor {

    /**
     * Constructs a Sensor object using the provided parameters.
     * If an exception occurs during the creation of the Sensor object (due to
     * reflection), this method returns null.
     *
     * @param deviceId        The unique identifier for the device to which the
     *                        sensor will be associated.
     * @param sensorModelID   The unique identifier for the sensor model.
     * @param sensorID        The unique identifier for the sensor.
     * @return A Sensor object of the specified type, or null if an exception occurs.
     */
    @Override
    public Sensor createSensor(DeviceId deviceId, SensorModelID sensorModelID, SensorID sensorID) {

        Object[] arguments = {deviceId, sensorModelID, sensorID};

        Class[] classTypes = {DeviceId.class, SensorModelID.class, SensorID.class};

        try {
            String sensorModelName = sensorModelID.toString();
            String strModelPath = "smartHomeDDD.domain.sensor." + sensorModelName;
            return (Sensor) Class.forName(strModelPath).getConstructor(classTypes).newInstance(arguments);
        } catch (ClassNotFoundException |
                 NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            return null;
        }
    }
}
