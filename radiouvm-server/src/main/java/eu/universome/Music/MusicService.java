package eu.universome.Music;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicService {
	
	private final MusicRepository musicRepository;
	
	// Constructor
	@Autowired
	public MusicService(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}
	
	// CRUD Implementation
	public String addMusic(Music music) {
		Optional<Music> musicOptional = this.musicRepository.findByTitle(music.getTitle());
		
		if (musicOptional.isPresent()) {
			throw new IllegalStateException("Title present");
		} else {
			musicRepository.save(music);
			return "Music saved";
		}
	}
	
	public Collection<Music> getMusic(){
		return this.musicRepository.findAll();
	}
	
	// Update User
	@Transactional
	public void updateMusic(Long musicId,
							String title,
							String description,
							String genre) {
		
		Music music = musicRepository
						.findById(musicId)
						.orElseThrow(() -> new IllegalStateException("music with id " + musicId + "does not exist"));
		
		if (   title != null 
			&& title.length() > 0 
			&& !Objects.equals(music.getTitle(), title) ) { // check if new name is not equal to the previous
				music.setTitle(title);
		}
		
		if (   description != null 
				&& description.length() > 0 
				&& !Objects.equals(music.getDescription(), description) ) { 
					music.setDescription(description);
		}
		
		if (   genre != null 
				&& genre.length() > 0 
				&& !Objects.equals(music.getGenre(), genre) ) { 
					music.setGenre(genre);
		}
	}
	
	// Delete
	public void deleteMusic(Long musicId) {
		
		boolean exists = musicRepository.existsById(musicId);
		
		if (!exists) 
			throw new IllegalStateException("music with id " + musicId + "does not exists");
		
		musicRepository.deleteById(musicId);		
	}
}

