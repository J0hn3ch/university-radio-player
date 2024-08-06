package eu.universome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	//private UserDetailsServiceImpl userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * How to enable api paths
		 * API: https://stackoverflow.com/questions/35890540/when-to-use-spring-securitys-antmatcher
		 * 403 - Forbidden: https://stackoverflow.com/questions/50486314/how-to-solve-403-error-in-spring-boot-post-request
		 */
		// Disabilitiamo il Cross-site request forgery - problema di sicurezza
		// https://it.wikipedia.org/wiki/Cross-site_request_forgery
		http.csrf().disable();
		
		// Configuriamo la richiesta per le pagine di login e logout, in modo che un utente non loggato possa accedervi
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
		
		http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ANONYOMOUS','LISTENER')");
		
		
		http.authorizeRequests().antMatchers("/adminPanel").access("hasRole('SPEAKER')");
		
		// Gestiamo l'accesso ad una pagina per i quali non si hanno i permessi.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		http
			.antMatcher("/api/**")
			.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.anyRequest().anonymous()
				.and()	
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
			
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		/*
		 * Update 2022-02-26:
		 * - The method UserDetails.withDefaultPasswordEncoder is deprecated, so I have removed it with an external encoder define previously
		 * Useful link: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/User.html
		 * Search the method name in the link.
		 */
		UserDetails user =
			 User.withUsername("Melo")
				.password(encoder.encode("password"))
				.roles("Speaker")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}
