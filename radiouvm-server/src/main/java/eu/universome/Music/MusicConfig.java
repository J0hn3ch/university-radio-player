package eu.universome.Music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusicConfig {

	private static final Logger log = LoggerFactory.getLogger(MusicConfig.class);
	
	@Bean
	CommandLineRunner preloadMusic(MusicRepository repository) {
		return args -> {
			Music music1 = new Music(
					//1L,
					"Title Music 1",
					"Description Music 1",
					"Genre Music 1");
			
			Music music2 = new Music(
					//1L,
					"Title Music 2",
					"Description Music 2",
					"Genre Music 2");
			
			Music music3 = new Music(
					//1L,
					"Title Music 3",
					"Description Music 3",
					"Genre Music 3");
			
			log.info("Preloading " + repository.save(music1));
			log.info("Preloading " + repository.save(music2));
			log.info("Preloading " + repository.save(music3));
		};
	}
}
