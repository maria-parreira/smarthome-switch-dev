package smartHomeDDD.persistence.jpa.datamodel;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-05-21T19:25:05", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(DeviceDataModel.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class DeviceDataModel_ { 

    public static volatile SingularAttribute<DeviceDataModel, String> deviceModel;
    public static volatile SingularAttribute<DeviceDataModel, String> deviceId;
    public static volatile SingularAttribute<DeviceDataModel, String> deviceName;
    public static volatile SingularAttribute<DeviceDataModel, String> roomId;
    public static volatile SingularAttribute<DeviceDataModel, Boolean> status;

}