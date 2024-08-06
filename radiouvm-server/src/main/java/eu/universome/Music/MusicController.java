package eu.universome.Music;

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

import eu.universome.User.User;
import eu.universome.User.UserService.UnauthorizedException;

@RestController
@RequestMapping(path = "api/v1/music")
public class MusicController {
	
	private final MusicService musicService;
	
	// Autowired serve a legare la creazione della variabile userService al parametro del costruttore.
	// Sostituisce la parola new per creare un'istanza della classe, senza la quale non potremmo chiamare il metodo getMusic()
	@Autowired
	public MusicController(MusicService musicService) {
		this.musicService = musicService;
	}
	
	// CRUD Operations: Create, Read, Update, Delete
	// Test Done - Ok http://localhost:8080/api/v1/music Body: {"title":"FLOP", "description":"Album di Salmo", "genre":"Rap"}
	@PostMapping
	public ResponseEntity<String> registerNewMusic(@RequestBody Music music) throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("Request Arrived" + music);
		try {
			String result = this.musicService.addMusic(music);
			if ( result != null) {
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{ }", HttpStatus.BAD_REQUEST);
			}
		} catch (UnauthorizedException e) {
			return new ResponseEntity<String>(objectMapper.writeValueAsString("Wrong user or password"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	// Test Done - Ok
	@GetMapping
	public Collection<Music> getUser() {
		return this.musicService.getMusic();
	}
	
	// Test Done - Ok http://localhost:8080/api/v1/music/1
	@DeleteMapping(path = "{musicId}")
	public void deleteMusic(@PathVariable("musicId") Long musicId) {
		this.musicService.deleteMusic(musicId);
	}
	
	// Test Done - Ok http://localhost:8080/api/v1/music/2?title=Disumano&description=Album di Fedez&genre=Rap
	@PutMapping(path = "{musicId}")
	public void updateMusic(
							@PathVariable("musicId") Long musicId,
							@RequestParam(required = false) String title,
							@RequestParam(required = false) String description,
							@RequestParam(required = false) String genre) {
		this.musicService.updateMusic(musicId, title, description, genre);
	}
	
	/* I test sono stati effettuati tramite: https://spring.io/guides/gs/testing-web/ 
	 * Controlla il percorso src/test/java
	 * */
}
