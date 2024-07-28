package smartHomeDDD.domain.domain.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.persistence.mem.RepositoryHouseMem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * These tests verify the functionality of the RepositoryHouse class, which manages houses.
 * Essential points to be tested:
 * - Saving a null house should throw an IllegalArgumentException.
 * - Saving a house that already exists in the repository should throw an IllegalArgumentException.
 * - Saving a house into an empty repository should result in the repository containing the house.
 * - An empty repository should not contain any houses.
 * - The findAll() method should return an empty iterable when the repository is empty.
 * - The findAll() method should return a non-empty iterable when the repository contains houses.
 * - The ofIdentity() method should return an empty Optional when the house does not exist in the repository.
 * - The ofIdentity() method should return a non-empty Optional when the house exists in the repository.
 * - The update() method should update the house in the repository.
 */
class RepositoryHouseMemTest {


    /**
     * Verifies that attempting to save a null House object should result in throwing an IllegalArgumentException.
     */
    @Test
    void shouldNotSaveHouse_NullHouse_shouldThrowException() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.save(null));

        // Assert
        assertTrue(exception.getMessage().contains("House cannot be null"));
    }

    /**
     * Verifies that attempting to save a House object that already exists in the repository should result in throwing an DataDataIntegrityViolationException.
     */
    @Test
    void shouldNotSaveHouse_HouseAlreadyExists_shouldThrowException() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();
        HouseId houseIdDouble = mock(HouseId.class);
        House houseDouble = mock(House.class);
        when(houseDouble.identity()).thenReturn(houseIdDouble);
        repository.save(houseDouble);

        // Act
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(houseDouble));

        // Assert
        assertTrue(exception.getMessage().contains("House already exists"));
    }
    /**
     * Tests the save method of the repository when the repository is empty.
     * The test verifies that saving a house into an empty repository results in the repository containing the house.
     */
    @Test
    void saveHouse_EmptyRepository_ShouldContainHouse() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();
        HouseId houseIdDouble = mock(HouseId.class);
        House houseDouble = mock(House.class);
        when(houseDouble.identity()).thenReturn(houseIdDouble);
        // Act
        House saveHouse = repository.save(houseDouble);
        houseIdDouble = saveHouse.identity();
        Optional<House> houseOptional = repository.ofIdentity(houseIdDouble);
        // Assert
        assertTrue(repository.containsOfIdentity(houseIdDouble));
        assertTrue(houseOptional.isPresent());
    }


    /**
     * Tests the repository when it is empty.
     * Verifies that an empty repository doesn't contain any houses.
     */
    @Test
    void emptyRepository_ShouldNotContainHouse() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();
        HouseId houseIdDouble = mock(HouseId.class);
        House houseDouble = mock(House.class);
        when(houseDouble.identity()).thenReturn(houseIdDouble);
        // Act
        Optional<House> houseOptional = repository.ofIdentity(houseIdDouble);
        // Assert
        assertFalse(repository.containsOfIdentity(houseIdDouble));
        assertFalse(houseOptional.isPresent());
    }


    /**
     * Tests the findAll method of the repository when it is empty.
     * Ensures that when the repository is empty, the findAll() method returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();
        // Act
        Iterable<House> allHouses = repository.findAll();
        // Assert
        assertFalse(allHouses.iterator().hasNext());
    }


    /**
     * Tests the findAll method of the repository when it contains houses.
     * Verifies if, when the repository contains houses, the findAll() method returns a non-empty iterable.
     */
    @Test
    void findAll_NonEmptyRepository_ShouldReturnNonEmptyIterable() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();
        House house = mock(House.class);
        repository.save(house);
        // Act
        Iterable<House> allHouses = repository.findAll();
        // Assert
        assertNotNull(allHouses);
        assertTrue(allHouses.iterator().hasNext());
    }

    /**
     * Verifies if the update method updates the house in the repository.
     */
    @Test
    void updateHouseInRepository_ShouldReturnUpdatedHouse() {

        // Arrange
        RepositoryHouseMem repository = new RepositoryHouseMem();
        House house = mock(House.class);
        HouseId houseId = mock(HouseId.class);
        when(house.identity()).thenReturn(houseId);
        repository.save(house);
        // Act
        House updatedHouse = repository.update(house);
        // Assert
        assertEquals(house, updatedHouse);
    }


    /////////////////////////////////////////// JPA ////////////////////////////////////////////////////

}
