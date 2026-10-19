package br.com.yago.services;

import br.com.yago.exceptions.ResourceNotFoundException;
import br.com.yago.model.Person;
import br.com.yago.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void testGiverPersonObject_WhenSavePerson_ThenReturnPersonObject() {

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
    void testGiverExistingEmail_WhenSavePerson_ThenThrowsException() {

        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });

        // Then / Assert
        verify(repository, never()).save(any(Person.class));
    }
}