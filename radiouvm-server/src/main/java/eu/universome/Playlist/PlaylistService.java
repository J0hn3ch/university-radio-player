package eu.universome.Playlist;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// REST Response


// JSON import

@Service
public class PlaylistService {
	
	private final PlaylistRepository playlistRepository;
	
	// Constructor
	@Autowired
	public PlaylistService(PlaylistRepository playlistRepository) {
		this.playlistRepository = playlistRepository;
	}
	
	
	public Collection<Playlist> getPlaylist(){
		return this.playlistRepository.findAll();
	}
	
	/*
	public void addNewPlaylist(Playlist playlist) {
		Optional<Playlist> playlistOptional = this.playlistRepository.findByEmail(playlist.getEmail());
		
		if (playlistOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		
		playlistRepository.save(playlist);
		
	}*/
	
	// Delete
	public void deletePlaylist(Long playlistId) {
		boolean exists = playlistRepository.existsById(playlistId);
		
		if (!exists) throw new IllegalStateException("Playlist with id " + playlistId + "does not exists");
		
		playlistRepository.deleteById(playlistId);		
	}
	
	// Update Playlist
	@Transactional
	public void updatePlaylist(Long playlistId,
							String title,
							String lastName,
							String email) {
		
		Playlist playlist = playlistRepository
						.findById(playlistId)
						.orElseThrow(() -> new IllegalStateException("Playlist with id " + playlistId + "does not exist"));
		
		if (   title != null 
			&& title.length() > 0 
			&& !Objects.equals(playlist.getTitle(), title) ) { // check if new name is not equal to the previous
				
				playlist.setTitle(title);
		}
	}
}

