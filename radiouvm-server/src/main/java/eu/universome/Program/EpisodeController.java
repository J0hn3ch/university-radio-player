package eu.universome.Program;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.universome.User.UserService.UnauthorizedException;

@RestController
@RequestMapping(path = "api/v1/episode")
public class EpisodeController {
	
	private final EpisodeService episodeService;
	
	
	// Autowired serve a legare la creazione della variabile episodeService al parametro del costruttore.
	// Sostituisce la parola new per creare un'istanza della classe, senza la quale non potremmo chiamare il metodo getEpisodes()
	@Autowired
	public EpisodeController(EpisodeService episodeService) {
		this.episodeService = episodeService;
	}
	
	// CRUD Operations
	
	@PostMapping
	public ResponseEntity<String> registerNewEpisode(@RequestBody Episode episode) throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			String result = this.episodeService.addEpisode(episode);
			if ( result != null) {
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{ }", HttpStatus.BAD_REQUEST);
			}
		} catch (UnauthorizedException e) {
			return new ResponseEntity<String>(objectMapper.writeValueAsString("Wrong episode"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping
	public Collection<Episode> getEpisode(@RequestParam Long program_id) {
		return this.episodeService.getEpisode(program_id);
	}
	
	@PutMapping(path = "{episodeId}")
	public void updateEpisode(
							@PathVariable("episodeId") Long episodeId,
							@RequestParam(required = false) String title,
							@RequestParam(required = false) String description,
							@RequestParam(required = false) String guest,
							@RequestParam(required = false) Long program_id) {
		episodeService.updateEpisode(episodeId, title, description, guest, program_id);
	}
	
	@DeleteMapping(path = "{episodeId}")
	public void deleteEpisode(@PathVariable("episodeId") Long episodeId) {
		episodeService.deleteEpisode(episodeId);
	}
}
