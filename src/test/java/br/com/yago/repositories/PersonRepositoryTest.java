package br.com.yago.repositories;

import br.com.yago.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    private Person person0;

    @BeforeEach
    public void setup() {

        // Given / Arrange
        person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
    }

    @DisplayName("Given Person Object When Save Then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_ThenReturnSavedPerson() {

        //Given / Arrange

        // When / Act
        Person savedPerson = repository.save(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("Given Person List When Find All Then Return Person List")
    @Test
    void testGivenPersonList_whenFindAll_ThenReturnPersonList() {

        // Given / Arrange

        Person person1 = new Person(
                "Costa",
                "Leonardo@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leonardo"
        );

        repository.save(person0);
        repository.save(person1);

        // When / Act
        List<Person> personList = repository.findAll();

        // Then / Assert
        assertNotNull(repository);
        assertEquals(2, personList.size());
    }

    @DisplayName("Given Person Object When Find By Id Then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindById_ThenReturnPersonObject() {

        // Given / Arrange

        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object When Find By Email Then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByEmail_ThenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findByEmail(person0.getEmail()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object When Update Person Then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_ThenReturnUpdatedPersonObject() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();
        savedPerson.setFirstName("Leonardo");
        savedPerson.setEmail("leonardo@gmail.com");

        Person updatedPerson = repository.save(savedPerson);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@gmail.com", updatedPerson.getEmail());
    }


    @DisplayName("Given Person Object When Delete Then Remove Person")
    @Test
    void testGivenPersonObject_whenDelete_ThenRemovePerson() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        repository.deleteById(person0.getId());
        Optional<Person> personOptional = repository.findById(person0.getId());

        // Then / Assert
        assertTrue(personOptional.isEmpty());
    }

    @DisplayName("Given First Name And Last Name When Find By JQPL Then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQL_ThenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByJPQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given First Name And Last Name When Find By JQPL Named Parameters Then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQLNamedParameters_ThenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByJPQLNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given First Name And Last Name When Find By Native SQL Then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQLNativeSQL_ThenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByNativeSQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given First Name And Last Name When Find By Native SQL With Named Parameters Then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQLCNativeSQLWithNamedParameters_ThenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByNativeSQLWithNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }
}
