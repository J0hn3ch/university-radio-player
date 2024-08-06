package eu.universome.Program;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository
	// extends CrudRepository<Episode, Long>
	extends JpaRepository<Episode, Long> {
	
	Optional<Episode> findById(Long id);
	Optional<Episode> findByTitle(String title);
	//https://stackoverflow.com/questions/29983047/spring-data-jpa-repository-methods-dont-recognize-property-names-with-underscor
	Collection<Episode> findByProgramId(Long programId);
}
