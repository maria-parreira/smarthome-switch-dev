package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.DeviceId;

import java.util.List;

/**
 * The IRepositoryActuator interface provides a contract for a repository that manages actuator objects.
 * It extends the generic Repository interface, specifying ActuatorID as the ID type and actuator as the entity type.
 */
public interface IRepositoryActuator extends Repository<ActuatorID, Actuator>
{
    List<Actuator> getActuatorsByDeviceID(DeviceId id);

    Actuator update(Actuator entity);
}
