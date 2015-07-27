package fr.bl.template.ui.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import fr.bl.template.ui.auth.domain.AuthGroup;
import fr.bl.template.ui.auth.domain.AuthPermission;
import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.dto.CustomSimpleAuthority;
import fr.bl.template.ui.auth.dto.CustomUser;
import fr.bl.template.ui.multitenant.dao.DomainGroupDao;
import fr.bl.template.ui.multitenant.dto.DomainUser;

/**
 * DAO custom pour spring security.
 * Chargement de l'utilisateur et de ses permissions
 * 
 * @author anthony.lagrede
 *
 */
@Repository
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@PersistenceContext EntityManager em;
	
	@Autowired DomainGroupDao domainGroupDao;
	
	@Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Query q = em.createQuery("select distinct a from AuthUser a "
				+ "left join fetch a.permissions p "
				+ "left join fetch a.groups g "
				+ "left join fetch g.permissions gp "
				+ "where a.username=:username");
		
		q.setParameter("username", username);
		
		AuthUser authUser = null;
		try {
			authUser = (AuthUser) q.getSingleResult();
		} catch(NoResultException e) {
			throw new UsernameNotFoundException("L'uitilisateur n'existe pas.");
		}
		
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		List<CustomSimpleAuthority> authorities = new ArrayList<CustomSimpleAuthority>();
		
		for (AuthGroup g : authUser.getGroups()) {
			for (AuthPermission p : g.getPermissions()) {
				authorities.add(new CustomSimpleAuthority(p.getCodename()));
			}
		}
		
		for (AuthPermission p : authUser.getPermissions()) {
			authorities.add(new CustomSimpleAuthority(p.getCodename()));
		}
		
		UserDetails user = null;
		
		// Ajout du tenant de l'utilisateur
		String tenantName = domainGroupDao.findOneByUsername(authUser.getUsername());
		user = new DomainUser(authUser.getUsername(), authUser.getPassword(), authUser.getActive(), accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		if (tenantName != null) {
			((DomainUser)user).setTenant(tenantName);
		}
        
		return user;
	}

}
