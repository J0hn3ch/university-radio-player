package eu.universome.Program;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpisodeService {
	
	private final EpisodeRepository episodeRepository;
	
	// Constructor
	@Autowired
	public EpisodeService(EpisodeRepository episodeRepository) {
		this.episodeRepository = episodeRepository;
	}
	
	// CRUD Operations
	// POST - Create
	public String addEpisode(Episode episode) {
		Optional<Episode> episodeOptional = this.episodeRepository.findByTitle(episode.getTitle());
		
		if (episodeOptional.isPresent()) {
			throw new IllegalStateException("Title present");
		} else {
			episodeRepository.save(episode);
			return "Episode saved";
		}
		
	}
	// GET - Retrieve 
	public Collection<Episode> getEpisode(Long program_id){
		return this.episodeRepository.findByProgramId(program_id);
	}
	
	// PUT - Update
	@Transactional
	public void updateEpisode(Long episodeId,
							String title,
							String description,
							String guest,
							Long program_id) {
		
		Episode episode = episodeRepository
						.findById(episodeId)
						.orElseThrow(() -> new IllegalStateException("Episode with id " + episodeId + "does not exist"));
		
		if (   title != null 
			&& title.length() > 0 
			&& !Objects.equals(episode.getTitle(), title) ) { // check if new name is not equal to the previous
				
				episode.setTitle(title);
		}
		
		if (   description != null 
				&& description.length() > 0 
				&& !Objects.equals(episode.getDescription(), description) ) { 
					
					episode.setDescription(description);
		}
		
		if (   guest != null 
				&& guest.length() > 0 
				&& !Objects.equals(episode.getGuest(), guest) ) { 
					
					episode.setGuest(guest);
		}
	}
	
	// DELETE - Delete
	public void deleteEpisode(Long episodeId) {
		boolean exists = episodeRepository.existsById(episodeId);
		
		if (!exists) 
			throw new IllegalStateException("Episode with id " + episodeId + "does not exists");
		
		episodeRepository.deleteById(episodeId);		
	}
}

