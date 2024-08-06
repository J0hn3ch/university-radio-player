package eu.universome.Playlist;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository
	// extends CrudRepository<Playlist, Long>
	extends JpaRepository<Playlist, Long> {
	
	Optional<Playlist> findById(Long id);
	List<Playlist> findByTitle(String title);
	Collection<Playlist> findByUserId(Long userId);
}
