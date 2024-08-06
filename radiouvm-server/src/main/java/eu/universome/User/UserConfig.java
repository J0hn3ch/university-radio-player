package eu.universome.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

	private static final Logger log = LoggerFactory.getLogger(UserConfig.class);
	
	// Insert users records in the database
	@Bean
	CommandLineRunner preloadUser(UserRepository repository) {
		return args -> {
			
			User listener1 = new User(
					//1L,
					"Listener",
					"1",
					"listener1@test.com",
					"LISTENER",
					"listener1");
			
			User listener2 = new User(
					//4L,
					"Listener",
					"2",
					"listener2@test.com",
					"LISTENER",
					"listener2");
			
			User speaker1 = new User(
					//2L,
					"Speaker",
					"1",
					"speaker1@test.com",
					"SPEAKER",
					"speaker1");
			
			User speaker2 = new User(
					//2L,
					"Speaker",
					"2",
					"speaker2@test.com",
					"SPEAKER",
					"speaker2");
			
			log.info("Preloading " + repository.save(listener1));
			log.info("Preloading " + repository.save(listener2));
			log.info("Preloading " + repository.save(speaker1));
			log.info("Preloading " + repository.save(speaker2));
		    
			//repository.saveAll(List.of( administrator1, 
			//							speaker1, 
			//							listener1,
			//							listener2));
		};
	}
}
