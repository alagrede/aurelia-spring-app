package fr.bl.template.ui.auth.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.web.command.CriteriaCommand;

public interface AuthUserDaoCustom {

    Page<AuthUser> findAllAuthUsersWithCriteria(Pageable pageRequest, CriteriaCommand criteria);
    
}
