package eu.universome.Music;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository
	// extends CrudRepository<User, Long>
	extends JpaRepository<Music, Long> {
	
	Optional<Music> findById(Long id);
	Optional<Music> findByTitle(String title);
	Collection<Music> findByGenre(String genre);
}
