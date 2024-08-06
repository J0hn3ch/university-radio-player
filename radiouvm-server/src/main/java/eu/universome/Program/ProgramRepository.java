package eu.universome.Program;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository
	// extends CrudRepository<Program, Long>
	extends JpaRepository<Program, Long> {
	
	Optional<Program> findById(Long id);
	Optional<Program> findByTitle(String title);
}
