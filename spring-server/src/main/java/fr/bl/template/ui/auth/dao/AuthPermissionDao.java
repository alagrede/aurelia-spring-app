package fr.bl.template.ui.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bl.template.ui.auth.domain.AuthPermission;

public interface AuthPermissionDao extends JpaRepository<AuthPermission, Long> {

	
	
}
