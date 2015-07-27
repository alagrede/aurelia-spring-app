package fr.bl.template.ui.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.bl.template.ui.auth.domain.OneTimeToken;

public interface OneTimeTokenDao extends JpaRepository<OneTimeToken, Long> {

    @Query("from OneTimeToken o where o.token=:token")
    OneTimeToken findOneByKey(@Param("token") String token);

    @Query("from OneTimeToken o where o.userId=:userId")
    OneTimeToken findOneByUserId(@Param("userId") Long userId);

    
}
