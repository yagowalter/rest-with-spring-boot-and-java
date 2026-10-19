package br.com.yago.repositories;

import br.com.yago.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    @Query("select p from Person p where p.firstName =?1 and p.lastName =?2")
    Person findByJPQL(String fistName, String lastName);

    @Query("select p from Person p where p.firstName =:fistName and p.lastName =:lastName")
    Person findByJPQLNamedParameters(
            @Param("fistName")String fistName,
            @Param("lastName") String lastName);

}
