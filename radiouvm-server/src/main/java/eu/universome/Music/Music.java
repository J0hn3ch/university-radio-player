package eu.universome.Music;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"music\"")
public class Music {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "music_sequence"
	)
	@SequenceGenerator(
			name = "music_sequence",
			sequenceName = "music_sequence",
			allocationSize = 1
	)
	private Long id;
	private String title;
	private String description;
	private String genre;
	
	protected Music() { }
	
	public Music(Long id, String title, String description, String genre) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.genre = genre;
	}
	
	public Music(String title, String description, String genre) {
		super();
		this.id = null;
		this.title = title;
		this.description = description;
		this.genre = genre;
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		//return "Music [id=" + id + ", title=" + title + ", description=" + description + ", genre=" + genre + "]";
		return "Music {"+
					((id != null)? (" \"id\": " + id + ","): "") +  
					" \"title\":" + "\"" + title + "\"" + 
					", \"description\":" + "\"" + description + "\"" + 
					", \"genre\":" + "\"" + genre + "\"" + 
					" }";
	}
	
	
	
}
