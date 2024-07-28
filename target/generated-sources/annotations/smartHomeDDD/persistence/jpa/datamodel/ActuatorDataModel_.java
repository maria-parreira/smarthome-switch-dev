package smartHomeDDD.persistence.jpa.datamodel;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-05-23T13:49:13", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(ActuatorDataModel.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class ActuatorDataModel_ { 

    public static volatile SingularAttribute<ActuatorDataModel, String> modelID;
    public static volatile SingularAttribute<ActuatorDataModel, String> actuatorID;
    public static volatile SingularAttribute<ActuatorDataModel, String> deviceId;
    public static volatile SingularAttribute<ActuatorDataModel, String> actuatorValue;

}