package fr.bl.template.ui.auth.dao.impl;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.PathBuilder;

import fr.bl.template.ui.auth.dao.AuthUserDaoCustom;
import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.domain.QAuthGroup;
import fr.bl.template.ui.auth.domain.QAuthPermission;
import fr.bl.template.ui.auth.domain.QAuthUser;
import fr.bl.template.ui.auth.service.AuthService;
import fr.bl.template.ui.auth.web.command.CriteriaCommand;

/**
 * 
 * @author anthony.lagrede
 * Exemple de manipulation de DAO spécifique avec QueryDSL Voir {@link AuthService} pour une utilisation générique
 *
 * Voir documentation pour utiliser des DTO
 * http://www.querydsl.com/static/querydsl/3.6.2/reference/html/ch03s02.html#d0e2105
 * 
 *
 */
public class AuthUserDaoImpl implements AuthUserDaoCustom {
	
	final static Logger logger = LoggerFactory.getLogger(AuthUserDaoImpl.class.getSimpleName());
	
	@PersistenceContext
    EntityManager entityManager;
	 		
	@Override public Page<AuthUser> findAllAuthUsersWithCriteria(Pageable pageRequest, CriteriaCommand criteria) {
		
		QAuthUser authUser = QAuthUser.authUser;
		QAuthGroup authGroup = QAuthGroup.authGroup;
		QAuthPermission authPermission = QAuthPermission.authPermission;
		
		BooleanBuilder predicate = new BooleanBuilder();

		if (criteria.getSearch() != null && !"".equals(criteria.getSearch())) {
			predicate.and(authUser.username.like("%" + criteria.getSearch() + "%"));
		}
		if (criteria.hasStaffFilter()) {
			predicate.and(authUser.staff.eq(criteria.isStaff()));
		}
		if (criteria.hasSuperUserFilter()) {
			predicate.and(authUser.superUser.eq(criteria.isSuperUser()));
		}
		if (criteria.hasActiveFilter()) {
			predicate.and(authUser.active.eq(criteria.isActive()));
		}
		
		// Requete avec jointures et fetchs
		JPAQuery query = new JPAQuery(entityManager);
		query = query.distinct().from(authUser).leftJoin(authUser.groups, authGroup).fetch().leftJoin(authUser.permissions, authPermission).fetch().where(predicate);    

		// pagination
		query.offset(pageRequest.getOffset());
		query.limit(pageRequest.getPageSize());

		// tri
		PathBuilder<AuthUser> pbAuthUser = new PathBuilder<AuthUser>(AuthUser.class, "authUser");
		//QAuthUser.authUser.active.getMetadata().getClass()
		if (pageRequest.getSort() != null) {
			Iterator<Sort.Order> it = pageRequest.getSort().iterator();
			while(it.hasNext()) {
				Sort.Order order = it.next();
				if (order.getDirection() == Direction.DESC) {
					query.orderBy(pbAuthUser.getString(order.getProperty()).desc());
				} else {
					query.orderBy(pbAuthUser.getString(order.getProperty()).asc());
				}
			}
		}

		// Requête de count
		JPAQuery countQuery = new JPAQuery(entityManager);
		Long total = countQuery.from(authUser).where(predicate).count();

		
		return new PageImpl<AuthUser>(query.list(authUser), pageRequest, total);

	}
		
//		public static final <T> List<T> emptyList() {
		
		
		
//		String query = "from AuthUser a ";
//
//		List<PropUtils> properties = new ArrayList<PropUtils>();
//		
//		if (criteria.getSearch() != null && !"".equals(criteria.getSearch())) {
//			query = addFilter(query, "a.username like :keyword");
//			properties.add(new PropUtils("keyword", "%"+criteria.getSearch()+"%"));
//		}
//  		if (criteria.hasStaffFilter()) {
//			query = addFilter(query, "a.staff=:staff");
//			properties.add(new PropUtils("staff", criteria.isStaff()));
//  		}
//  		if (criteria.hasSuperUserFilter()) {
//			query = addFilter(query, "a.superUser=:superUser");
//			properties.add(new PropUtils("superUser", criteria.isSuperUser()));
//  		}
//  		if (criteria.hasActiveFilter()) {
//			query = addFilter(query, "a.active=:active");
//			properties.add(new PropUtils("active", criteria.isActive()));
//  		}
//
//
//  		return PaginationUtils.makeQuery(entityManager, query, pageRequest, properties);
//	}
//
//	private String addFilter(String query, String criteria) {
//		if (query.toLowerCase().contains("where")) {
//			query += " and ";
//		} else {
//			query += " where ";
//		}
//		query += criteria;
//		
//		return query;
//	}

}
