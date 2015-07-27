package fr.bl.template.ui.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;

import fr.bl.template.ui.auth.dao.AuthUserDao;
import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.domain.QAuthUser;
import fr.bl.template.ui.auth.web.command.CriteriaCommand;

@Component
public class AuthService {

	@Autowired private AuthUserDao authUserDao;
	
	public Page<AuthUser> findAllAuthUsersWithCriteria(CriteriaCommand criteria, PageRequest pageRequest) {
		
		QAuthUser authUser = QAuthUser.authUser;
		BooleanBuilder query = new BooleanBuilder();
		
		if (criteria.getSearch() != null && !"".equals(criteria.getSearch())) {
			query.and(authUser.username.like("%" + criteria.getSearch() + "%"));
		}
		if (criteria.hasStaffFilter()) {
			query.and(authUser.staff.eq(criteria.isStaff()));
		}
		if (criteria.hasSuperUserFilter()) {
			query.and(authUser.superUser.eq(criteria.isSuperUser()));
		}
		if (criteria.hasActiveFilter()) {
			query.and(authUser.active.eq(criteria.isActive()));
		}
		
		return authUserDao.findAll(query, pageRequest);
	}

}
