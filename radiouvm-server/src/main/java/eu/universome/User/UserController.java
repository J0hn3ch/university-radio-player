package eu.universome.User;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.universome.User.UserService.UnauthorizedException;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
	
	private final UserService userService;
		
	// Autowired serve a legare la creazione della variabile userService al parametro del costruttore.
	// Sostituisce la parola new per creare un'istanza della classe, senza la quale non potremmo chiamare il metodo getUsers()
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
		
	// CRUD Operations
	@GetMapping
	public Collection<User> getUser() {
		return this.userService.getUsers();
	}
	
	@PostMapping
	public String registerNewUser(@RequestParam(required = true) String firstName,
								@RequestParam(required = true) String lastName,
								@RequestParam(required = true) String email,
								@RequestParam(required = true) String password) {
		User user = new User(firstName,lastName,email,"LISTENER",password);
		this.userService.addNewUser(user);
		return "User registered!";
	}
	
	@DeleteMapping(path = "{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
	}
	
	@PutMapping(path = "{userId}")
	public void updateUser(	@PathVariable("userId") Long userId,
							@RequestParam(required = false) String firstName,
							@RequestParam(required = false) String lastName,
							@RequestParam(required = false) String email) {
		userService.updateUser(userId, firstName, lastName, email);
	}
	
	// AUTH Operations
	@PostMapping(value = "/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity loginUser(@RequestParam("email") String email, 
									@RequestParam(name = "password", required = false) String password) throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			User userAuthenticated = userService.authenticate(email, password);
			if ( userAuthenticated != null) {
				return new ResponseEntity<>(userAuthenticated.toString(), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{ }", HttpStatus.BAD_REQUEST);
			}
		} catch (UnauthorizedException e) {
			return new ResponseEntity<String>(objectMapper.writeValueAsString("Wrong user or password"), HttpStatus.UNAUTHORIZED);
		}
	}
}
