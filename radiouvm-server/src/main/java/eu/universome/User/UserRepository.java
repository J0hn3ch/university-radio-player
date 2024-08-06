package eu.universome.User;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Difference between Repository and DAO: 
// https://stackoverflow.com/questions/8550124/what-is-the-difference-between-dao-and-repository-patterns

@Repository
public interface UserRepository
	// extends CrudRepository<User, Long>
	extends JpaRepository<User, Long> {
	
	// https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
	Optional<User> findById(Long id);
	List<User> findByLastName(String lastName);
	Optional<User> findByEmail(String email);
}
