package eu.universome.Program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.universome.ClientWeb;

@Configuration
public class EpisodeConfig {

	private ClientWeb clientWeb;
	private static final Logger log = LoggerFactory.getLogger(EpisodeConfig.class);
	
	@Autowired
	public EpisodeConfig(ClientWeb clientWeb) {
		this.clientWeb = clientWeb;
	}
	
	@Bean
	CommandLineRunner preloadEpisode(EpisodeRepository repository) {
		return args -> {
			
			Episode episode1 = new Episode(
					//1L,
					"Titolo Ep 1",
					"Descrizione Ep 1",
					"Speaker Ep 1",
					1L);

			
			log.info("Preloading " + repository.save(episode1));
		    
			//repository.saveAll(List.of( administrator1, 
			//							speaker1, 
			//							listener1,
			//							listener2));
		};
	}
}
