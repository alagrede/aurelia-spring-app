package fr.bl.template.ui.multitenant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.bl.template.ui.multitenant.domain.DomainGroup;

public interface DomainGroupDao extends JpaRepository<DomainGroup, Long> {

	@Query("select d.tenantName from DomainGroup d where d.username=:username")
	public String findOneByUsername(@Param("username") String username);
	
}
