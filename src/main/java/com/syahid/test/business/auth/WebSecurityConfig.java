package com.syahid.test.business.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableJpaAuditing
public class WebSecurityConfig {
	
	@Autowired
	AuthenticationEntryPointJwt authenticationEntryPointJwt;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Bean
	public AuthorizationFilter authorizationFilter() {
	    return new AuthorizationFilter();
	}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors((cors) -> cors.disable())
		.csrf((csrf) -> csrf.disable())
		.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
		.exceptionHandling(
				(exceptionHandling) -> 
				exceptionHandling
				.authenticationEntryPoint(authenticationEntryPointJwt)
				)
		.sessionManagement(
				(sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
		.authorizeHttpRequests(
				(authorizeHttpRequests) ->
				authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
				.anyRequest().authenticated()
				);
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
