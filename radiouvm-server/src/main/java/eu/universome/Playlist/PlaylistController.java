package eu.universome.Playlist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/playlist")
public class PlaylistController {
	
	private final PlaylistService playlistService;
	/*
	@Autowired PlaylistRepository playlistRepository;
	
	Collection<Playlist> playlist(){
		return this.playlistRepository.findAll();
	}*/
	
	
	// Autowired serve a legare la creazione della variabile playlistService al parametro del costruttore.
	// Sostituisce la parola new per creare un'istanza della classe, senza la quale non potremmo chiamare il metodo getPlaylists()
	@Autowired
	public PlaylistController(PlaylistService playlistService) {
		this.playlistService = playlistService;
	}
	
	// CRUD Operations
	
	@GetMapping
	public Collection<Playlist> getPlaylist() {
		return this.playlistService.getPlaylist();
	}
	
	@PostMapping
	public void registerNewPlaylist(@RequestBody Playlist playlist) {
		//this.playlistService.addNewPlaylist(playlist);
	}
	
	@DeleteMapping(path = "{playlistId}")
	public void deletePlaylist(@PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(playlistId);
	}
	
	@PutMapping(path = "{playlistId}")
	public void updatePlaylist(
							@PathVariable("playlistId") Long playlistId,
							@RequestParam(required = false) String firstName,
							@RequestParam(required = false) String lastName,
							@RequestParam(required = false) String email) {
		playlistService.updatePlaylist(playlistId, firstName, lastName, email);
	}
	
}
