package eu.universome.UAds;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"university_ads\"")
public class UniversityAds {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "uads_sequence"
	)
	@SequenceGenerator(
			name = "uads_sequence",
			sequenceName = "uads_sequence",
			allocationSize = 1
	)
	private Long id;
	private String title;
	private String description;
	private String department;
	private String speaker;
	
	protected UniversityAds() { }
	
	public UniversityAds(Long id, String title, String description, String department, String speaker) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.department = department;
		this.speaker = speaker;
	}
	
	public UniversityAds(String title, String description, String department, String speaker) {
		super();
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


	@Override
	public String toString() {
		return "UAds {"+
				((id != null)? " \"id\": ," + id : "") +  
				" \"title\":" + "\"" + title + "\"" + 
				", \"description\":" + "\"" + description + "\"" + 
				", \"department\":" + "\"" + department + "\"" +
				", \"speaker\":" + "\"" + speaker + "\"" + 
				" }";
	}
	
	
	
}
