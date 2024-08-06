package eu.universome.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {
	
	@JsonProperty(value = "episode_id")
	private Long id;
	@JsonProperty(value = "title")
	private String title;
	@JsonProperty(value = "type")
	private String type;
	private String description;
	private String guest;
	@JsonProperty(value = "show_id")
	private Long programId;
	@JsonProperty(value = "playback_url")
	private String playback_url;

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

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}
	public String getUrl() {
		return playback_url;
	}

	public void setUrl(String url) {
		this.playback_url = url;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Episode {id=" + id + ", title=" + title + ", description=" + description + ", guest=" + guest
				+ ", programId=" + programId + "}";
	}
}
