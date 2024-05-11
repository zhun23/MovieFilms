package com.example.dev.security.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static final String ADMIN_ROLE = "Admin";
	
	private final AuthenticationProvider authenticationProvider;

	public SecurityConfig(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.authenticationProvider(this.authenticationProvider)
            .authorizeHttpRequests(authorizeRequests())
            .logout(logout ->
                logout
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            )
            .build();
    }

	private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeRequests() {
		return authorizeRequests -> authorizeRequests
		        .requestMatchers(
		        		"/index", "/error",
		        		"/css/**", "/js/**",
		        		"/login", "/register"
		        ).permitAll()
		        .requestMatchers(HttpMethod.POST, "/process-login").permitAll()
		    	.requestMatchers("/admin/**").hasRole(ADMIN_ROLE)
		        .anyRequest().authenticated();
	}

	/*
	//este es el bueno
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests((authorizeHttpRequests) ->
	        authorizeHttpRequests
	        	.requestMatchers("/login").permitAll()
	        	//.requestMatchers("/admin").hasRole("ADMIN")
	            .anyRequest().permitAll()
	                
	        );
	    return http.build();
	}
	*/
}
