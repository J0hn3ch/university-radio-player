package eu.universome.Playlist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"playlist\"")
public class Playlist {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "playlist_sequence"
	)
	@SequenceGenerator(
			name = "playlist_sequence",
			sequenceName = "playlist_sequence",
			allocationSize = 1
	)
	private Long id;
	private String title;
	
	// Non utilizzare i nomi degli attributi con gli 'underscore'. Simbolo '_' e' un carattere riservato per gli algoritmi di Spring,
	// usato nella creazione di un pattern per la ricerca delle proprieta' delle entita'
	// Read more: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions
	@Column(name="user_id") private Long userId;
	@Column(name="episode_id") private Long episodeId;
	@Column(name="uads_id") private Long uAdsId;
	@Column(name="music_id") private Long musicId;
	
	protected Playlist() { }
	
	public Playlist(
			Long id, 
			String title, 
			Long userId, 
			Long episodeId, 
			Long uAdsId, 
			Long musicId) {
		super();
		this.id = id;
		this.title = title;
		this.userId = userId;
		this.episodeId = episodeId;
		this.uAdsId = uAdsId;
		this.musicId = musicId;
	}
	
	public Playlist(
			String title, 
			Long userId, 
			Long episodeId, 
			Long uAdsId, 
			Long musicId) {
		super();
		this.title = title;
		this.userId = userId;
		this.episodeId = episodeId;
		this.uAdsId = uAdsId;
		this.musicId = musicId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(Long episodeId) {
		this.episodeId = episodeId;
	}

	public Long getuAdsId() {
		return uAdsId;
	}

	public void setuAdsId(Long uAdsId) {
		this.uAdsId = uAdsId;
	}

	public Long getMusicId() {
		return musicId;
	}

	public void setMusicId(Long musicId) {
		this.musicId = musicId;
	}

	@Override
	public String toString() {
		return "Playlist {"+
				((id != null)? (" \"id\": " + id + ","): "") +  
				" \"title\":" + "\"" + title + "\"" + 
				", \"userId\":" + "\"" + userId + "\"" + 
				", \"episodeId\":" + "\"" + episodeId + "\"" +
				", \"uAdsId\":" + "\"" + uAdsId + "\"" + 
				", \"musicId\":" + "\"" + musicId + "\"" + 
				" }";
	}
}
