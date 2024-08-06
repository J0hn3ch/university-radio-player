package eu.universome.Playlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaylistConfig {

	private static final Logger log = LoggerFactory.getLogger(PlaylistConfig.class);
	
	@Bean
	CommandLineRunner preloadPlaylist(PlaylistRepository repository) {
		return args -> {
			Playlist playlist1 = new Playlist(
					"Title Playlist 1",
					1L,
					2L,
					0L, // uAds
					0L); // Music
			
			Playlist playlist2 = new Playlist(
					"Title Playlist 1",
					1L,
					2L,
					0L, // uAds
					0L); // Music
			
			Playlist playlist3 = new Playlist(
					"Title Playlist 1",
					1L,
					2L,
					0L, // uAds
					0L); // Music
			
			
			log.info("Preloading " + repository.save(playlist1));
			log.info("Preloading " + repository.save(playlist2));
			log.info("Preloading " + repository.save(playlist3));
		    
			//repository.saveAll(List.of( administrator1, 
			//							speaker1, 
			//							listener1,
			//							listener2));
		};
	}
}
