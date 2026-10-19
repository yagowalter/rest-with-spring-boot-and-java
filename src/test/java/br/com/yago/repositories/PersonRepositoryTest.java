package br.com.yago.repositories;

import br.com.yago.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;


    @DisplayName("Given Person Object When Save Then Return Saved Person")
    @Test
    void testGivenPersonObject_WhenSave_ThenReturnSavedPerson() {

        // Given / Arrange
        Person person0 = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
        // When / Act
        Person savedPerson = personRepository.save(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }
}
