package eu.universome.UAds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UAdsConfig {

	private static final Logger log = LoggerFactory.getLogger(UAdsConfig.class);
	
	@Bean
	CommandLineRunner preloadUAds(UAdsRepository repository) {
		return args -> {
			UniversityAds uads1 = new UniversityAds(
					//1L,
					"Bando Informatica",
					"Progetto SpaceY",
					"MIFT",
					"Prof. 1");
			
			UniversityAds uads2 = new UniversityAds(
					//1L,
					"Bando Informatica",
					"Progetto eMotor",
					"MIFT",
					"Prof. 2");
			
			UniversityAds uads3 = new UniversityAds(
					//1L,
					"Bando Medicina",
					"Progetto DistrMed",
					"PATOLOGIAUMANADETEV",
					"Prof. 3");
			
			UniversityAds uads4 = new UniversityAds(
					//1L,
					"Bando Informatica",
					"Progetto DistrMed",
					"DICAM",
					"Prof. 3");
			
			
			//log.info("Preloading " + repository.save(uads1));
		    
			log.info("Preloading " + repository.saveAll(List.of( uads1, 
										uads2, 
										uads3,
										uads4)) );
		};
	}
}
