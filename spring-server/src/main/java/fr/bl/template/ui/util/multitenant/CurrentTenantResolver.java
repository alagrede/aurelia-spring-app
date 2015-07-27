package fr.bl.template.ui.util.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.bl.template.ui.multitenant.dto.DomainUser;


public class CurrentTenantResolver implements CurrentTenantIdentifierResolver {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static ThreadLocal<String> _tenantIdentifier = new ThreadLocal<String>();
	public static String DEFAULT_TENANT_ID = "default_db";

	
	@Override public String resolveCurrentTenantIdentifier() {
		String tenantId = _tenantIdentifier.get();
		if(tenantId == null)
			tenantId = DEFAULT_TENANT_ID;

		if (SecurityContextHolder.getContext() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
//			if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof DomainUser) {
//				tenantId = ((DomainUser)auth.getPrincipal()).getTenant();
//			}
			// UPDATE: modification suite à mise en place authentification stateless par token
			if (auth != null && auth.getDetails() != null && auth.getDetails() instanceof DomainUser) {
				if (((DomainUser)auth.getDetails()).getTenant() != null) {
					tenantId = ((DomainUser)auth.getDetails()).getTenant();
				}
			}
		}
		
		logger.debug("threadlocal tenant id = " + tenantId);
		return tenantId;
	}

	@Override public boolean validateExistingCurrentSessions() {
		return true;
	}

}
