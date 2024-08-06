package eu.universome.Program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.universome.ClientWeb;

@Configuration
public class ProgramConfig {

	private ClientWeb clientWeb;
	private static final Logger log = LoggerFactory.getLogger(ProgramConfig.class);
	
	
	@Autowired
	public ProgramConfig(ClientWeb clientWeb) {
		this.clientWeb = clientWeb;
	}
	
	@Bean
	CommandLineRunner preloadProgram(ProgramRepository repository) {
		return args -> {
			
			log.info("Preloading " + repository.saveAll(clientWeb.getPrograms()) );
			/*
			Program administrator1 = new Program(
					//1L,
					"Titolo Programma 1",
					"Descrizione Programma 1",
					"Speaker 1");
			*/
			
			//log.info("Preloading " + repository.save(administrator1));

		    
			//repository.saveAll(List.of( administrator1, 
			//							speaker1, 
			//							listener1,
			//							listener2));
		};
	}
}
