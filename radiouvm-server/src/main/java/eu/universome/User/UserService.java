package eu.universome.User;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	// Constructor
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//@RequestMapping(path = "/user")
	public Collection<User> getUsers(){
		return this.userRepository.findAll();
	}
	
	// ==== CRUD ====
	public void addNewUser(User user) {
		Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
		
		if (userOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		
		userRepository.save(user);
		
	}
	
	// Delete
	public void deleteUser(Long userId) {
		boolean exists = userRepository.existsById(userId);
		
		if (!exists) throw new IllegalStateException("user with id " + userId + "does not exists");
		
		userRepository.deleteById(userId);		
	}
	
	// Update User
	@Transactional
	public void updateUser(Long userId,
							String firstName,
							String lastName,
							String email) {
		
		User user = userRepository
						.findById(userId)
						.orElseThrow(() -> new IllegalStateException("student with id " + userId + "does not exist"));
		
		if (   firstName != null 
			&& firstName.length() > 0 
			&& !Objects.equals(user.getFirstName(), firstName) ) { // check if new name is not equal to the previous
				
				user.setFirstName(firstName);
		}
		
		if (   lastName != null 
				&& lastName.length() > 0 
				&& !Objects.equals(user.getLastName(), lastName) ) { 
					
					user.setLastName(lastName);
		}
		
		if (   email != null 
				&& email.length() > 0 
				&& !Objects.equals(user.getEmail(), email) ) { 
					
					user.setEmail(email);
		}
	}
	
	// ==== Authentication ====
	public User authenticate(String email, String password) {
		
		User user = (userRepository
				.findByEmail(email))
				.orElseThrow(() -> new IllegalStateException("user with email " + email + "does not exist"));

		if (user.getPassword().equals(password)) { // cannot use user.getPassword == password, the operator == compare the instances not the content
			return user;
		} else {
			throw new UnauthorizedException("Wrong email or password");
		}
	}
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Wrong email or password")
	public class UnauthorizedException extends RuntimeException {
		public UnauthorizedException(String string) {
	        super(string);
	    }
	}
}

