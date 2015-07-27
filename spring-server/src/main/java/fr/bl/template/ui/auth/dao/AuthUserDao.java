package fr.bl.template.ui.auth.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import fr.bl.template.ui.auth.domain.AuthUser;

public interface AuthUserDao extends AuthUserDaoCustom, JpaRepository<AuthUser, Long>, QueryDslPredicateExecutor<AuthUser>  {

    //@Query("from AuthUser a where upper(a.id) = upper(:id)")
    //AuthUser findByNameIgnoreCase(@Param("id") String id);
    
    @Query("select a.id, a.username from AuthUser a")
	List<Object> findAllMap();

    @Query("select distinct a from AuthUser a left join fetch a.permissions left join fetch a.groups")
	List<AuthUser> findAllFetchPermissionsAndGroups();
    
    @Query("select distinct a from AuthUser a left join fetch a.permissions p left join fetch a.groups g where a.id=:id")
    AuthUser findOneFetchGroups(@Param("id") Long id);
    
    @Query("select password from AuthUser a where a.id=:id")
    String loadPasswordForUser(@Param("id") Long id);
    
    @Query("from AuthUser a where a.email=:email")
	AuthUser findByMail(@Param("email") String email);
    
}
