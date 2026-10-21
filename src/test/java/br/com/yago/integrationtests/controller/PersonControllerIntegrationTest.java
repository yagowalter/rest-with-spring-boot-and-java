package br.com.yago.integrationtests.controller;

import br.com.yago.config.TestConfig;
import br.com.yago.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.yago.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Person person;

    @BeforeAll
    public static void setup() {

        // Given / Arrange
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        person = new Person(
                "Costa",
                "leandro@gmail.com",
                "Male",
                "Uberlândia - Minas Gerais - Brasil",
                "Leandro"
        );
    }

    @Test
    @Order(1)
    @DisplayName("integrationTest_GivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject")
    void integrationTest_GivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject() throws IOException {

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Person createdPerson = objectMapper.readValue(content, Person.class);

        person = createdPerson;

        assertNotNull(createdPerson);

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertNotNull(createdPerson.getEmail());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Leandro", createdPerson.getFirstName());
        assertEquals("Costa", createdPerson.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("leandro@gmail.com", createdPerson.getEmail());
    }

    @Test
    @Order(2)
    @DisplayName("integrationTest_GivenPersonObject_when_UpdateOnePerson_ShouldReturnAUpdatedPersonObject")
    void integrationTest_GivenPersonObject_when_UpdateOnePerson_ShouldReturnAUpdatedPersonObject() throws IOException {

        person.setFirstName("Leonardo");
        person.setEmail("leonardo@gmail.com");

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person updatedPerson = objectMapper.readValue(content, Person.class);

        person = updatedPerson;

        assertNotNull(updatedPerson);

        assertNotNull(updatedPerson.getId());
        assertNotNull(updatedPerson.getFirstName());
        assertNotNull(updatedPerson.getLastName());
        assertNotNull(updatedPerson.getAddress());
        assertNotNull(updatedPerson.getGender());
        assertNotNull(updatedPerson.getEmail());

        assertTrue(updatedPerson.getId() > 0);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("Costa", updatedPerson.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", updatedPerson.getAddress());
        assertEquals("Male", updatedPerson.getGender());
        assertEquals("leonardo@gmail.com", updatedPerson.getEmail());
    }

    @Test
    @Order(3)
    @DisplayName("integrationTest_GivenPersonObject_when_FindById_ShouldReturnAPersonObject")
    void integrationTest_GivenPersonObject_when_FindById_ShouldReturnAPersonObject() throws IOException {

        var content = given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person foundPerson = objectMapper.readValue(content, Person.class);

        assertNotNull(foundPerson);

        assertNotNull(foundPerson.getId());
        assertNotNull(foundPerson.getFirstName());
        assertNotNull(foundPerson.getLastName());
        assertNotNull(foundPerson.getAddress());
        assertNotNull(foundPerson.getGender());
        assertNotNull(foundPerson.getEmail());

        assertTrue(foundPerson.getId() > 0);
        assertEquals("Leonardo", foundPerson.getFirstName());
        assertEquals("Costa", foundPerson.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", foundPerson.getAddress());
        assertEquals("Male", foundPerson.getGender());
        assertEquals("leonardo@gmail.com", foundPerson.getEmail());
    }
}
