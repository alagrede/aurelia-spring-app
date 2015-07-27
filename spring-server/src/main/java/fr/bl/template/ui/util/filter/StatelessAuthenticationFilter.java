package fr.bl.template.ui.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.GenericFilterBean;

import fr.bl.template.ui.auth.service.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {
	
	private final TokenAuthenticationService tokenAuthenticationService;
	
	protected StatelessAuthenticationFilter(TokenAuthenticationService taService) {
		this.tokenAuthenticationService = taService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		if (!request.getMethod().equals("OPTIONS")) {
			SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationService.getAuthentication((HttpServletRequest) req));
			chain.doFilter(req, res); // always continue
		}

	}

}
