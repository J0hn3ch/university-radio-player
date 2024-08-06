package eu.universome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.universome.Program.Episode;
import eu.universome.Program.Program;
import reactor.core.publisher.Mono;

/**
 * 
 * @author gianluca
 * Tutorial Web Client on Spring WebFlux
 * - https://howtodoinjava.com/spring-webflux/webclient-get-post-example/#post
 * - https://www.baeldung.com/webflux-webclient-parameters
 * - ResponseEntity class
 * - 
 */
@Service
public class ClientWeb {
	
	private WebClient webClient;

	public ClientWeb() {
		this.webClient = WebClient.builder()
    			.baseUrl("https://api.spreaker.com/v2")
    			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    			.build();
	}
	
	// Retrieving a User’s Shows - API Shows
	public List<Program> getPrograms() throws JsonProcessingException {
		
		List<Program> programs = new ArrayList<>();
		// Docs di JsonParser : https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/json/JsonParser.html
		JsonParser jsonParser = JsonParserFactory.getJsonParser();
		ObjectMapper objectMapper = new ObjectMapper();
		
		// Making request and get the response
 		WebClient.ResponseSpec response = 
				this.webClient
					.get()
					.uri("/users/8497168/shows") // change id with dynamic content user_id
					.accept(MediaType.APPLICATION_JSON)
					.retrieve();
 		
 		// Take the body and convert it to String
 		Mono<String> response_mono = response.bodyToMono(String.class);

 		// Manipulate String to map content
 		Map<String,Object> response_map = jsonParser.parseMap(response_mono.block().toString()); // Response JSON
 		Map<String,Object> items_map = 
 				jsonParser.parseMap(
 						objectMapper.writeValueAsString(		// https://devqa.io/how-to-convert-java-map-to-json/
 								response_map.get("response") 
 						)
		);
 		//System.out.println(items_map.toString());
 		
 		// Finally, from convert a string list of Items into a List of Object
 		List<Object> program_list = jsonParser.parseList(
 				objectMapper.writeValueAsString(
 					items_map.get("items")
 				)
 		);
 		
 		// Get all the Program in the list and return a Program List
 		for (Object item : program_list) {
 			Program program = objectMapper.readValue(objectMapper.writeValueAsString(item), Program.class);
 			programs.add(program);
 		}
 		
 		return programs;
	}
	
	public List<Episode> getEpisodes(Long programId) throws JsonProcessingException {
		
		List<Episode> episodes = new ArrayList<>();
		JsonParser jsonParser = JsonParserFactory.getJsonParser();
		ObjectMapper objectMapper = new ObjectMapper();
		
		WebClient.ResponseSpec response = 
				this.webClient
					.get()
					.uri("/shows/" + programId + "/episodes") // change id with dynamic content program_id
					.accept(MediaType.APPLICATION_JSON)
					.retrieve();
		
		Mono<String> response_mono = response.bodyToMono(String.class);
		Map<String,Object> response_map = jsonParser.parseMap(response_mono.block().toString()); // Response JSON
 		Map<String,Object> items_map = 
 				jsonParser.parseMap(
 						objectMapper.writeValueAsString(		// https://devqa.io/how-to-convert-java-map-to-json/
 								response_map.get("response") 
 						)
		);
 		
 		List<Object> episode_list = jsonParser.parseList(
 				objectMapper.writeValueAsString(
 					items_map.get("items")
 				)
 		);
 		
 		for (Object item : episode_list) {
 			Episode episode = objectMapper.readValue(objectMapper.writeValueAsString(item), Episode.class);
 			System.out.println(episode);
 			episodes.add(episode);
 		}
 		
		return episodes;
	}
	
	public Episode getLive() throws JsonParseException, JsonProcessingException {
		// https://api.spreaker.com/v2/users/8579251/episodes
		Episode episode = new Episode();
		JsonParser jsonParser = JsonParserFactory.getJsonParser();
		ObjectMapper objectMapper = new ObjectMapper();
		
		WebClient.ResponseSpec response = 
				this.webClient
					.get()
					.uri("/users/8579251/episodes") // change id with dynamic content program_id
					.accept(MediaType.APPLICATION_JSON)
					.retrieve();
	
		Mono<String> response_mono = response.bodyToMono(String.class);
		Map<String,Object> response_map = jsonParser.parseMap(response_mono.block().toString()); // Response JSON
 		Map<String,Object> items_map = 
 				jsonParser.parseMap(
 						objectMapper.writeValueAsString(
 								response_map.get("response") 
 						)
		);
 		
 		List<Object> episode_list = jsonParser.parseList(
 				objectMapper.writeValueAsString(
 					items_map.get("items")
 				)
 		);
 		
 		for (Object item : episode_list) {
 			episode = objectMapper.readValue(objectMapper.writeValueAsString(item), Episode.class);
 			System.out.println(episode);
 			if (episode.getType().equals("LIVE"))
 				break;
 		}
	
 		return episode;
	}
	
}
