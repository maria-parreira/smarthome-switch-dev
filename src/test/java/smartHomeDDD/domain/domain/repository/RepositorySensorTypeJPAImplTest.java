package smartHomeDDD.domain.domain.repository;

import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.persistence.jpa.datamodel.SensorTypeDataModel;
import smartHomeDDD.persistence.jpa.repository.RepositorySensorTypeJPAImpl;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * RepositorySensorTypeJPAImplTest is a test class for RepositorySensorTypeJPAImpl. It contains the following test cases:
 * - Valid save of sensorType should save a sensorType in RepositoryJPA.
 * - Repeated save of sensorType should throw IllegalArgumentException.
 * - Tests if when a sensorType does not exist in the repository, an empty optional is returned.
 */
class RepositorySensorTypeJPAImplTest {

    /**
     * Valid save of sensorType should save a sensorType in RepositoryJPA.
     */
    @Test
    void validSaveOfSensorType_shouldSaveASensorTypeInRepositoryJPA() {

        // Arrange
        String id = "id2";
        String description = "description2";
        String unit = "unit3";

        SensorTypeID sensorTypeID = mock(SensorTypeID.class);
        when(sensorTypeID.toString()).thenReturn(id);

        Description descriptionMock = mock(Description.class);
        when(descriptionMock.toString()).thenReturn(description);

        Unit unitMock = mock(Unit.class);
        when(unitMock.toString()).thenReturn(unit);

        SensorType sensorTypeMock = mock(SensorType.class);
        when(sensorTypeMock.identity()).thenReturn(sensorTypeID);
        when(sensorTypeMock.getDescription()).thenReturn(descriptionMock);
        when(sensorTypeMock.getUnit()).thenReturn(unitMock);

        ImplFactorySensorType factorySensorTypeMock = mock(ImplFactorySensorType.class);

        SensorTypeDataModel sensorTypeDataModel = mock(SensorTypeDataModel.class);

        EntityManager entityManagerMock = mock(EntityManager.class);
        EntityTransaction entityTransaction = mock (EntityTransaction.class);
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityTransaction).begin();
        doNothing().when(entityManagerMock).persist(sensorTypeDataModel);
        doNothing().when(entityTransaction).commit();
        doNothing().when(entityManagerMock).close();
        RepositorySensorTypeJPAImpl repositorySensorType = new RepositorySensorTypeJPAImpl(factorySensorTypeMock, entityManagerMock);

        // Act
        SensorType savedSensorType = repositorySensorType.save(sensorTypeMock);

        // Assert
        assertEquals(id, savedSensorType.identity().toString());
    }

    /**
     * Repeated save of sensorType should throw IllegalArgumentException.
     */
    @Test
    void repeatedSaveOfSensorType_shouldThrowIllegalArgumentException() {

        // Arrange
        String id = "id2";
        String description = "description2";
        String unit = "unit3";

        SensorTypeID sensorTypeID = mock(SensorTypeID.class);
        when(sensorTypeID.toString()).thenReturn(id);

        Description descriptionMock = mock(Description.class);
        when(descriptionMock.toString()).thenReturn(description);

        Unit unitMock = mock(Unit.class);
        when(unitMock.toString()).thenReturn(unit);

        SensorType sensorTypeMock = mock(SensorType.class);
        when(sensorTypeMock.identity()).thenReturn(sensorTypeID);
        when(sensorTypeMock.getDescription()).thenReturn(descriptionMock);
        when(sensorTypeMock.getUnit()).thenReturn(unitMock);

        ImplFactorySensorType factorySensorTypeMock = mock(ImplFactorySensorType.class);

        SensorTypeDataModel sensorTypeDataModel = mock(SensorTypeDataModel.class);

        EntityManager entityManagerMock = mock(EntityManager.class);
        EntityTransaction entityTransaction = mock (EntityTransaction.class);
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityTransaction).begin();
        doNothing().when(entityManagerMock).persist(sensorTypeDataModel);
        doNothing().when(entityTransaction).commit();
        doNothing().when(entityManagerMock).close();

        when(entityManagerMock.find(SensorTypeDataModel.class, sensorTypeMock.identity().toString()))
                .thenReturn(null) // First call to find() returns null (sensorType doesn't exist)
                .thenReturn(sensorTypeDataModel); // Second call to find() returns non-null (sensorType exists)

        RepositorySensorTypeJPAImpl repositorySensorType = new RepositorySensorTypeJPAImpl(factorySensorTypeMock, entityManagerMock);

        // Act
        repositorySensorType.save(sensorTypeMock);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> repositorySensorType.save(sensorTypeMock));
    }

    /**
     * Tests if when a sensorType does not exist in the repository, an empty optional is returned.
     */
    @Test
    void testOfIdentity_WhenSensorTypeDoesNotExist_ExpectEmptyOptional() {

        // Arrange
        EntityManager entityManager = mock(EntityManager.class);

        SensorTypeID sensorTypeId = mock(SensorTypeID.class);
        when(sensorTypeId.toString()).thenReturn("example_sensor_type_id");

        // Mock EntityManager behavior to return null when find is called
        when(entityManager.find(SensorTypeDataModel.class, sensorTypeId.toString()))
                .thenReturn(null);

        RepositorySensorTypeJPAImpl repository = new RepositorySensorTypeJPAImpl(
                mock(FactorySensorType.class),
                entityManager);

        // Act
        Optional<SensorType> result = repository.ofIdentity(sensorTypeId);

        // Assert
        assertFalse(result.isPresent());
    }
}