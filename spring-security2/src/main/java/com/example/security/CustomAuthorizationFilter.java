package com.example.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Validating The JWT
When a user signs in to the program,
 we can already give them an access token. 
 Now we need to be able to accept this token from the user and 
 then grant them access to the resources once we’ve confirmed that it’s valid.
 To do so, we’ll need to create an authorization filter.
 This filter will intercept all requests coming into the application,
 search for that specific token, process it,
  and then determine whether or not the user has access to specified resources.
Let’s make a new class that implements OncePerRequestFilter named CustomAuthorizationFilter.
 To filter each request that comes into the application,
 we’ll need to implement a method called doFilterInternal().
 We don’t want the “/login” path to be authorized,
therefore that’s the first thing we should check.
 If that’s the case, we know the user is simply attempting to log in, 
 therefore we don’t need to do anything but transmit the request and answer to the next filter in the chain. 
If anything goes wrong throughout the process, we’ll need to catch exceptions.
 * 
 * 
 */
public class CustomAuthorizationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader("AUTHORIZATION");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
				} catch (Exception ex) {
					// log.error("Error loggin in: {}", ex.getMessage());
					response.setStatus(403);
					String errorMessage = ex.getMessage();
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}
}