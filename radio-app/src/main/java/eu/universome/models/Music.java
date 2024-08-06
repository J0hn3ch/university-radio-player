package eu.universome.models;

public class Music {
	
	private Long id;
	private String title;
	private String description;
	private String genre;
	
	public Music() {}
	
	public Music(Long id, String title, String description, String genre) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.genre = genre;
	}
	
	public Music(String title, String description, String genre) {
		super();
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
		return "{"+
				((id != null)? (" \"id\": " + id + ",") : "") +  
				" \"title\":" + "\"" + title + "\"" + 
				", \"description\":" + "\"" + description + "\"" + 
				", \"genre\":" + "\"" + genre + "\"" + 
				" }";
	}
	
	
}
