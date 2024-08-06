package eu.universome.models;

public class User {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;

	
	public User() { }
	
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
			String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}
	
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
	
	public String loginInfo() {
		return "User {" +
				"email:" + email + 
				", password:" + password + 
				"}";
	}
	
	@Override
	public String toString() {
		return "User {"+
				"id:" + id + 
				", firstName:" + firstName + 
				", lastName:" + lastName + 
				", email:" + email + 
				", role:" + role + 
				"}";
	}
}
