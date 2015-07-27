package fr.bl.template.ui.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.bl.template.ui.auth.domain.AuthGroup;

public interface AuthGroupDao extends JpaRepository<AuthGroup, Long> {

    //@Query("from AuthGroup a where upper(a.id) = upper(:id)")
    //AuthGroup findByNameIgnoreCase(@Param("id") String id);
    
    @Query("select a.id, a.name from AuthGroup a")
	List<Object> findAllMap();
    
    @Query("select distinct g from AuthGroup g left join fetch g.permissions p where g.id=:id")
    AuthGroup findOneFetchPermissions(@Param("id") Long id);

    
}
