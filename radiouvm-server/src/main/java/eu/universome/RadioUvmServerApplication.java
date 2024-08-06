package eu.universome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RadioUvmServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RadioUvmServerApplication.class, args);
	}
}

/*
 * If your configuration has JPA repository interface definitions located in a package that is not visible, 
 * you can point out alternate packages by using @EnableJpaRepositories and its type-safe 
 * basePackageClasses=MyRepository.class parameter. 
 */
