package eu.universome.Program;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "\"episode\"")
public class Episode {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "episode_sequence"
	)
	@SequenceGenerator(
			name = "episode_sequence",
			sequenceName = "episode_sequence",
			allocationSize = 1
	)
	private Long id;
	private String title;
	private String description;
	private String guest;
	@JsonProperty(value = "type")
	private String type;

	//https://stackoverflow.com/questions/29983047/spring-data-jpa-repository-methods-dont-recognize-property-names-with-underscor
	@Column(name="program_id") private Long programId;
	
	public Episode() { }
	
	public Episode(Long id, String title, String description, String guest, Long programId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.guest = guest;
		this.programId = programId;
	}
	
	public Episode(String title, String description, String guest, Long programId) {
		super();
		this.title = title;
		this.description = description;
		this.guest = guest;
		this.programId = programId;
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

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	@Override
	public String toString() {
		return "Episode {"+
				((id != null)? (" \"id\": " + id + ","): "") +  
				" \"title\":" + "\"" + title + "\"" + 
				", \"description\":" + "\"" + description + "\"" + 
				", \"guest\":" + "\"" + guest + "\"" +
				", \"programId\":" + "\"" + programId + "\"" + 
				" }";
	}
}
