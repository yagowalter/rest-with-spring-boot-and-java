package br.com.yago.controllers;

import br.com.yago.exceptions.ResourceNotFoundException;
import br.com.yago.model.Person;
import br.com.yago.services.PersonServices;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonServices service;

    private Person person;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
    }

    @DisplayName("Given Person Object When Create Person Should Return Saved Person")
    @Test
    void testGivenPersonObject_WhenCreatePerson_ShouldReturnSavedPerson() throws Exception {

        // Given / Arrange
        given(service.create(any(Person.class)))
                .willAnswer((invocation -> invocation.getArguments()[0]));

        // When / Act
        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)));

        // Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @DisplayName("Given List of Persons Object When Find all Persons then Return Persons List")
    @Test
    void testGivenListOfPersonsObject_WhenFindAllPersons_ThenReturnPersonsList() throws Exception {

        // Given / Arrange
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(new Person(
                "Costa",
                "leonardo@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leonardo"
        ));
        given(service.findAll()).willReturn(persons);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person"));

        // Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(persons.size())));
    }

    @DisplayName("Given Person Id When Find By Id Then Return Person Object")
    @Test
    void testGivenPersonId_WhenFindById_ThenReturnPersonObject() throws Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willReturn(person);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @DisplayName("Given Invalid Person Id When Find By Id Then Return Not Found")
    @Test
    void testGivenInvalidPersonId_WhenFindById_ThenReturnNotFound() throws Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then / Assert
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("Given Update Person When Update Then Return Updated Person Object")
    @Test
    void testGivenUpdatePerson_WhenUpdate_ThenReturnUpdatedPersonObject() throws Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Person.class)))
                .willAnswer((invocation -> invocation.getArguments()[0]));

        // When / Act
        Person updatedPerson = new Person(
                "Costa",
                "leonardo@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leonardo"
        );
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        // Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }

    @DisplayName("Given Unexistent Person When Update Then Return Not Found")
    @Test
    void testGivenUnexistentPerson_WhenUpdate_ThenReturnNotFound() throws Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Person.class)))
                .willAnswer((invocation -> invocation.getArguments()[1]));

        // When / Act
        Person updatedPerson = new Person(
                "Costa",
                "leonardo@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leonardo"
        );
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        // Then / Assert
        response.andExpect(status().isNotFound()).andDo(print());

    }
}