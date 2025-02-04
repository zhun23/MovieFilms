package com.example.dev.security.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.dev.exceptions.CustomAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String ADMIN_ROLE = "Admin";

	private final AuthenticationProvider authenticationProvider;

	public SecurityConfig(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(AbstractHttpConfigurer::disable)
	        .cors(AbstractHttpConfigurer::disable)
	        .httpBasic(AbstractHttpConfigurer::disable)
	        .formLogin(form -> form
	            .loginPage("/login")
	            .defaultSuccessUrl("/index", true)
	            .failureHandler(new CustomAuthenticationFailureHandler())
	            .permitAll()
	        )
	        .sessionManagement(configurer -> configurer
	            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
	            .sessionFixation(t -> t.changeSessionId())
	            .maximumSessions(1)
	            .maxSessionsPreventsLogin(true)
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .deleteCookies("jsessionid")
	            .invalidateHttpSession(true).clearAuthentication(true)
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/index")
	            .permitAll()
	        )
	        .authenticationProvider(this.authenticationProvider)
	        .authorizeHttpRequests(authorizeRequests())
	        .build();
	}






	@Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/api/**")
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(registry -> registry.anyRequest().permitAll())
            .build();
    }

	private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeRequests() {
		return authorizeRequests -> authorizeRequests
		        .requestMatchers(
		        		"/index", "/error",
		        		"/css/**", "/js/**", "/images/**",
		        		"/register", "/fullNewRelease",
		        		"/newRelease/true", "/sidebartest",
		        		"/addCart", "/getCarts/**",
		        		"/list", "/id/**",
		        		"/genre/**", "/title/**",
		        		"/titleResults", "adventures",
		        		"/scienceFiction", "fantasy",
		        		"/crime", "/comedy",
		        		"/romance", "/horror",
		        		"/drama", "/musical",
		        		"/thriller", "/animation",
		        		"/kids", "/action",
		        		"/cart", "/movie",
		        		"/directorResults", "/yearResults",
		        		"/newReleaseInc", "/modifyAddress",
		        		"adminNewRelease"
		        ).permitAll()
		        .requestMatchers(HttpMethod.POST,
		        		"/login", "/user/regUser",
		        		"/"
		        		).permitAll()
		    	.requestMatchers("/admin/**").hasRole(ADMIN_ROLE)
//		    	.anyRequest().permitAll();
		        .anyRequest().authenticated();
	}
}
