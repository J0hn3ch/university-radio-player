package eu.universome.UAds;

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
@RequestMapping(path = "api/v1/uads")
public class UAdsController {
	
	private final UAdsService uAdsService;
	
	@Autowired
	public UAdsController(UAdsService uAdsService) {
		this.uAdsService = uAdsService;
	}
	
	// CRUD Operations: Create, Read, Update, Delete
	// Test Done - Ok http://localhost:8080/api/v1/uads Body: {"title":"FLOP", "description":"Album di Salmo", "genre":"Rap"}
	@PostMapping
	public ResponseEntity<String> registerNewUAds(@RequestBody UniversityAds uAds) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("Request Arrived" + uAds);
		try {
			String result = this.uAdsService.addUAds(uAds);
			if ( result != null) {
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{ }", HttpStatus.BAD_REQUEST);
			}
		} catch (UnauthorizedException e) {
			return new ResponseEntity<String>(objectMapper.writeValueAsString("Wrong user or password"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping
	public Collection<UniversityAds> getUAds() {
		return this.uAdsService.getUAds();
	}
	
	@PutMapping(path = "{uAdsId}")
	public void updateUAds(
							@PathVariable("uAdsId") Long uAdsId,
							@RequestParam(required = false) String title,
							@RequestParam(required = false) String description,
							@RequestParam(required = false) String department,
							@RequestParam(required = false) String speaker) {
		uAdsService.updateUAds(uAdsId, title, description, department, speaker);
	}
	
	@DeleteMapping(path = "{uAdsId}")
	public void deleteUAds(@PathVariable("uAdsId") Long uAdsId) {
		uAdsService.deleteUAds(uAdsId);
	}
	
}
