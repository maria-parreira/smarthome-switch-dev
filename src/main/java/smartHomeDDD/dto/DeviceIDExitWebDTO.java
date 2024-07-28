package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The DeviceWebDTO class represents a Data Transfer Object (DTO) of type Web for a device.
 */

public class DeviceIDExitWebDTO extends RepresentationModel<DeviceIDExitWebDTO> {

    /**
     * The unique identifier of the device.
     */
    private final String id;

    /**
     * Constructs a new Device Web DTO with the specified device information.
     * @param id the unique identifier of the device.
     */
    public DeviceIDExitWebDTO(String id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier of the device.
     * @return the unique identifier of the device.
     */
    public String getId() {
        return id;
    }


}