package fr.bl.template.ui.util.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.bl.template.ui.auth.dto.UserAuthentication;
import fr.bl.template.ui.auth.service.TokenAuthenticationService;
import fr.bl.template.ui.multitenant.dto.DomainUser;


public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final TokenAuthenticationService tokenAuthenticationService;
	private final UserDetailsService userDetailsService;

	protected StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, UserDetailsService userDetailsService, AuthenticationManager authManager, AuthenticationFailureHandler authenticationFailureHandler) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationFailureHandler(authenticationFailureHandler);
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (!request.getMethod().equals("OPTIONS")) {
			final Map<String, String> user = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
			final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.get("username"), user.get("password"));
			return getAuthenticationManager().authenticate(loginToken);
		} else {
			return null;
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		// Lookup the complete User object from the database and create an Authentication for it
		final DomainUser authenticatedUser = (DomainUser)userDetailsService.loadUserByUsername(authentication.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		// FIXME on rajoute dans la réponse le token car Aurelia perd les Headers sur la réponse
		response.getWriter().append(tokenAuthenticationService.addAuthentication(response, userAuthentication));
		
		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		
	}
	
}