package eu.universome.models;

public class UniversityAds {

	private Long id;
	private String title;
	private String description;
	private String department;
	private String speaker;
	
	public UniversityAds() {}
	
	public UniversityAds(Long id, String title, String description, String department, String speaker) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.department = department;
		this.speaker = speaker;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	
	
	
	
}
