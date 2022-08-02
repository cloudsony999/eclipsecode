package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
//				authenticationManagerBean());
//		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.authorizeRequests().anyRequest().permitAll().and().addFilter(customAuthenticationFilter);
//	}

	/*
	 * 
	 * Now let’s see whether we can really use our access token to access the
	 * server’s resources. At this point, it’s almost as if we don’t have any
	 * security at all, because we’re allowing any request without authorization.
	 * Modify the configure(HttpSecurity http) method to allow unauthorized requests
	 * only to the “/login” endpoint and to approve requests to all other endpoints
	 * as follows:
	 */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
//				authenticationManagerBean());
//		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.authorizeRequests().antMatchers("/login").permitAll().and().authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_USER").and().authorizeRequests()
//				.antMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN").and()
//				.authorizeRequests().anyRequest().authenticated().and().addFilter(customAuthenticationFilter);
//	}

	/*
	 * We can now add this filter to our authorization configuration, but we must
	 * ensure that it comes before any other filters since we want to intercept all
	 * requests before they reach any other filters.
	 * 
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
				authenticationManagerBean());
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/login").permitAll().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_USER").and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN").and()
				.authorizeRequests().anyRequest().authenticated().and().addFilter(customAuthenticationFilter)
				.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}