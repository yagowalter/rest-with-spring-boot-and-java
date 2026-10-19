package br.com.yago.repositories;

import br.com.yago.model.Person;
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

    @DisplayName("Given Person Object When Save Then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_ThenReturnSavedPerson() {

        // Given / Arrange
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
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
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
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
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
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
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
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
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
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
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
        repository.save(person0);

        // When / Act
        repository.deleteById(person0.getId());
        Optional<Person> personOptional = repository.findById(person0.getId());

        // Then / Assert
        assertTrue(personOptional.isEmpty());
    }
}
