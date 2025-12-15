package com.spring.Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain customChain(HttpSecurity security) throws Exception {
		// for disabling csrf so we cannot depend on 1 session Id
		security.csrf(customizer->customizer.disable()); 
		// authorizing because we can authorize any requests
		security.authorizeHttpRequests(request->request 
				.requestMatchers("/signup","/login")
				.permitAll()   // 👈 allow signup & login
	            .anyRequest().authenticated() );
		// for form login access to the browse
		//security.formLogin(Customizer.withDefaults());
		//for to access also in the postman
		security.httpBasic(Customizer.withDefaults());
		//for Multiple SessionIds for each Tab
		security.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
	}
	// for Basic Authentication
	@Bean
    public PasswordEncoder passwordEncoder() {
	//return NoOpPasswordEncoder.getInstance(); 
      return new BCryptPasswordEncoder();
    }

	@Autowired
	private UserDetailsService userDetailsService;
	
	// for basic Auth
	  @Bean
	    public AuthenticationManager authenticationManager(
	            HttpSecurity http,
	            PasswordEncoder passwordEncoder
	    ) throws Exception {

	        AuthenticationManagerBuilder builder =
	                http.getSharedObject(AuthenticationManagerBuilder.class);

	        // THIS avoids deprecated DaoAuthenticationProvider()
	        builder.userDetailsService(userDetailsService)
	               .passwordEncoder(passwordEncoder);

	        return builder.build();
	    }
	
	
	
	// for JWT 
//	@Bean 
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();		
//	}
	
	
	
	
	
	// for spring <6.3
//	 @Bean
//	    public AuthenticationProvider authenticationProvider(
//	            UserDetailsService userDetailsService,
//	            PasswordEncoder passwordEncoder) {
//
//	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//	        provider.setPasswordEncoder(passwordEncoder);
//	        provider.setUserDetailsService(userDetailsService);
//
//	        return provider;
//	    }
	
	// for Basic AUTH like Hard coded
	
//	@Bean
//	public UserDetailsService addUser(PasswordEncoder passwordEncoder) {
//	    
//	    // Get the hashed password for "897" and "8978" using the encoder bean
//	    String p1 = passwordEncoder.encode("897"); 
//	    String p2 = passwordEncoder.encode("8978");
//
//	    UserDetails user1 = User
//	            .builder()
//	            .username("jillu")
//	            // Pass the HASHED password to the UserDetails builder
//	            .password(p1) 
//	            .roles("USER")
//	            .build();
//	    
//	    UserDetails user2 = User
//	            .builder()
//	            .username("jillu1")
//	            .password(p2)
//	            .roles("ADMIN")
//	            .build();
//
//	    // The InMemoryUserDetailsManager will now check the raw password against the stored hash
//	    return new InMemoryUserDetailsManager(user1, user2);
//	}
//	
}

