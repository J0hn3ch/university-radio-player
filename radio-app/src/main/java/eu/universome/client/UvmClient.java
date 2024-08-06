package eu.universome.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.reactive.function.BodyInserter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import eu.universome.models.Music;
import eu.universome.models.Program;
import eu.universome.models.User;

public class UvmClient {
	
	private WebClient webClient;

	public UvmClient() {
		this.webClient = WebClient.builder()
    			.baseUrl("http://localhost:8080/api/v1")
    			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    			.build();
	}
	
	public User login(String email, String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		
		System.out.println("Tentativo di accesso con le credenziali: " + user.loginInfo());
		
		WebClient.ResponseSpec response = 
				this.webClient
					.post()
					.uri(uriBuilder -> 
						uriBuilder
						.path("/user/login")
						.queryParam("email", user.getEmail())
						.queryParam("password", user.getPassword())
						.build() )
			        .retrieve();
		
		// Handling Response Code
		// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.ResponseSpec.html
		Mono<User> responseText = 
				response.bodyToMono(User.class);
		
		Mono<String> responseCheck = 
				response.bodyToMono(String.class)
					.onErrorResume(WebClientResponseException.class,
				          resp -> resp.getRawStatusCode() == 401 ? Mono.empty() : Mono.error(resp)); // HTTP 401 - UNAUTHORIZED
		
		//System.out.println(responseText.block());
		
		if (responseCheck.block() == null) {
			return null;
		} else {
			return responseText.block();
		}
	}
	
	public int register(String firstName, String lastName, String email, String password) {
		
		WebClient.ResponseSpec response = 
				this.webClient
					.post()
					.uri(uriBuilder -> 
						uriBuilder
						.path("/user/login")
						.queryParam("firstName", firstName)
						.queryParam("lastName", lastName)
						.queryParam("email", email)
						.queryParam("password", password)
						.build() )
			        .retrieve();
		
		Mono<String> responseText = response.bodyToMono(String.class);
		System.out.println(responseText.block());
		
		return 0;
	}
	
	// CRUD - PROGRAM
	public List<Program> getPrograms() {
		List<Program> programList = new ArrayList<>();
 		Program program;
 		
 		WebClient.ResponseSpec response = 
				this.webClient
					.get()
					.uri(uriBuilder -> 
						uriBuilder
						.path("/program")
						.build() )
					.accept(MediaType.APPLICATION_JSON)
			        .retrieve();
 		// If the JSON is like [ { ... } ], use bodyToFlux, else if is like { ... }
 		Flux<Program> program_flux = response.bodyToFlux(Program.class);
 		System.out.println("[ GET Request ]:" + program_flux.blockFirst());
 		Iterator<Program> program_it = program_flux.toIterable().iterator();
 		
 		while (program_it.hasNext()) {
			program = program_it.next();
			programList.add(program);
		}

		//Mono<Episode> episode_mono = response.bodyToMono(Episode.class);
		return programList;
	}
	
	// CRUD - MUSIC
	
	public List<Music> getMusic() {
		List<Music> musicList = new ArrayList<>();
 		Music music;
 		
 		WebClient.ResponseSpec response = 
				this.webClient
					.get()
					.uri(uriBuilder -> 
						uriBuilder
						.path("/music")
						.build() )
					.accept(MediaType.APPLICATION_JSON)
			        .retrieve();
 		// If the JSON is like [ { ... } ], use bodyToFlux, else if is like { ... }
 		Flux<Music> music_flux = response.bodyToFlux(Music.class);
 		System.out.println("[ GET Request ]:" + music_flux.blockFirst());
 		Iterator<Music> music_it = music_flux.toIterable().iterator();
 		
 		while (music_it.hasNext()) {
			music = music_it.next();
			musicList.add(music);
		}

		//Mono<Episode> episode_mono = response.bodyToMono(Episode.class);
		return musicList;
	}
	
	public String addMusic(String title, String description, String genre) {
		
		// Creo l'oggetto Music
		Music music = new Music(title, description,genre);
		
		System.out.println("[LOG] Aggiunta musica: " + music);
		
		WebClient.ResponseSpec response = 
				this.webClient
					.post()
					.uri(uriBuilder -> 
						uriBuilder
						.path("/music")
						.build() )
					.bodyValue(music.toString())
			        .retrieve();
		
		// Handling Response Code
				// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.ResponseSpec.html
		Mono<String> responseText = 
				response.bodyToMono(String.class);
		
		return responseText.block(); // La richiesta viene effettuata al momento dell'invocazione del metodo "block" di WebClient.ResponseSpec
	}
	
	/*
	public String updateMusic(Long id, String title, String description, String genre) {
		
	}
	
	public String deleteMusic(Long id) {
		
	}
	*/
}
