package eu.universome.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="\"user\"")
public class User {
	
	@Id
	@SequenceGenerator(
			name = "user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_sequence"
	)
	//@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	//@Column(name = "Role_Name", length = 30, nullable = false)
	private String role;

	// Constructors
	protected User() { }
	
	public User(Long id,
				String firstName,
				String lastName,
				String email,
				String password,
				String role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
	}
	
	public User(String firstName,
			String lastName,
			String email,
			String role,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	// Getter and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// Textual representation of the object
	@Override
	public String toString() {
		return "{ \"id\":" + id + 
				", \"firstName\":\"" + firstName + "\"" +
				", \"lastName\":\"" + lastName + "\"" +
				", \"email\":\"" + email + "\"" +
				", \"role\":\"" + role + "\"" +
				" }";
	}
}
