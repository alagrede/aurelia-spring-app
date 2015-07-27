package fr.bl.template.ui.multitenant.dto;

import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fr.bl.template.ui.auth.dto.CustomSimpleAuthority;
import fr.bl.template.ui.auth.dto.CustomUser;

/**
 * Extension du User de spring security pour inclure le tenant auquel appartient l'utilisateur
 * 
 * @author anthony.lagrede
 *
 */
@NoArgsConstructor
public class DomainUser extends CustomUser {

	@Getter @Setter private String tenant = null;
	
	public DomainUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<CustomSimpleAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

}
