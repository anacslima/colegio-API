package br.edu.senaisp.colegio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.stereotype.Component;

import br.edu.senaisp.colegio.model.EPerfil;



@Component
@EnableWebSecurity
public class SegurancaFilterChain {
	
	@Autowired
	SegurancaFilter segurancaFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
													throws Exception {
		System.err.println("A");
		return http
				.csrf().disable()
				.sessionManagement(s ->
					s.sessionCreationPolicy(
							SessionCreationPolicy.STATELESS)
															)		
		.authorizeHttpRequests(a ->	a
				.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/usuario").permitAll()
				.requestMatchers("/api/professor").hasRole(EPerfil.ALUNO.toString())
				.requestMatchers(HttpMethod.GET, "/api/aluno")
								.hasAnyRole(EPerfil.ADMIN.toString(), 
										EPerfil.PROFESSOR.toString())
				.requestMatchers("/api/**").denyAll()								
				.anyRequest().authenticated()
				)
				.addFilterBefore(segurancaFilter, 
						   UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(
//			               PasswordEncoder passwordEncoder) {
//		return new InMemoryUserDetailsManager(
//				User.withUsername("Xaxa")
//				.password(passwordEncoder.encode("Bit123456") )
//				.roles("USER")
//				.build()
//				);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authManager(
			AuthenticationConfiguration config
							) throws Exception {
		System.err.println("C");
		return config.getAuthenticationManager();
	}

}
