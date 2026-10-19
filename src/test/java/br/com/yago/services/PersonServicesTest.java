package br.com.yago.services;

import br.com.yago.exceptions.ResourceNotFoundException;
import br.com.yago.model.Person;
import br.com.yago.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices
            services;
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

    @DisplayName("Given Person Object When Save Person Then Return Person Object")
    @Test
    void testGivenPersonObject_WhenSavePerson_ThenReturnPersonObject() {

        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);

        // When / Act
        Person savedPerson = services.create(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());
    }

    @DisplayName("Given Existing Email Then Throws Exception")
    @Test
    void testGivenExistingEmail_WhenSavePerson_ThenThrowsException() {

        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });

        // Then / Assert
        verify(repository, never()).save(any(Person.class));
    }

    @DisplayName("Given Persons List When Find All Persons Then Return Persons List")
    @Test
    void testGivenPersonsList_WhenFindAllPersons_ThenReturnPersonsList() {

        // Given / Arrange
        Person person1 = new Person(
                "Costa",
                "Leonardo@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leonardo"
        );

        given(repository.findAll()).willReturn(List.of(person0, person1));

        // When / Act
        List<Person> personsList = services.findAll();

        // Then / Assert
        assertNotNull(personsList);
        assertEquals(2, personsList.size());
    }

    @DisplayName("Given Empty Persons List When Find All Persons Then Return Empty Persons List")
    @Test
    void testGivenEmptyPersonsList_WhenFindAllPersons_ThenReturnEmptyPersonsList() {

        // Given / Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());

        // When / Act
        List<Person> personsList = services.findAll();

        // Then / Assert
        assertTrue(personsList.isEmpty());
        assertEquals(0, 0);
    }


    @DisplayName("Given Person Id When Find By Id Then Return Person Object")
    @Test
    void testGivenPersonId_WhenFindById_ThenReturnPersonObject() {

        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        // When / Act
        Person savedPerson = services.findById(1L);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());
    }
}