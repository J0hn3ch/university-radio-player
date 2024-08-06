package eu.universome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {

	@JsonProperty(value = "show_id")
	private Long id;
	@JsonProperty(value = "title")
	private String title;
	@JsonProperty(value = "description")
	private String description;
	private String speaker;
	
	// Image
	private String imgSrc;
	
	public Program() { }
	
	public Program(Long id, String title, String description, String speaker) {
		super();
		this.id = id;
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

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
}
