package eu.universome.Program;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "\"program\"")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "program_sequence"
	)
	@SequenceGenerator(
			name = "program_sequence",
			sequenceName = "program_sequence",
			allocationSize = 1
	)
	@JsonProperty(value = "show_id")
	private Long id;
	@JsonProperty(value = "title")
	private String title;
	@JsonProperty(value = "description")
	private String description;
	private String speaker;
	// Image
	private String imgSrc;
	
	protected Program() { }
	
	public Program(Long id, String title, String description, String speaker) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.speaker = speaker;
	}
	
	public Program(String title, String description, String speaker) {
		super();
		this.title = title;
		this.description = description;
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

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	@Override
	public String toString() {		
		return "Program {"+
				((id != null)? (" \"id\": " + id + ","): "") +  
				" \"title\":" + "\"" + title + "\"" + 
				", \"description\":" + "\"" + description + "\"" + 
				", \"speaker\":" + "\"" + speaker + "\"" + 
				" }";
	}
	
	
	
}
