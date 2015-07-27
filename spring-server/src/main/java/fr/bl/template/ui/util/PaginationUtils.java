package fr.bl.template.ui.util;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author anthony.lagrede
 *
 * Exemple d'utilisation
 * <pre>
 *		@PersistenceContext
 *	    EntityManager entityManager;
 *
 *		...
 *		Sort.Direction direction = "Asc".equals(p.getSortDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;
 *		PageRequest pageRequest = new PageRequest(p.getPageNumber(), p.getPageSize(), direction, p.getSortField());
 *
 * 		List<PropUtils> properties = new ArrayList<PropUtils>();
 *		String query = "select * from ... where u.id = :idUser and cl.id = :clientId"; // construction de la requête
 *
 *		properties.add(new PropUtils("idUser", idUser));
 *		properties.add(new PropUtils("clientId", clientId));
 *	
 *		return PaginationUtils.makeQuery(entityManager, query, pageRequest, properties);

 * </pre>
 *
 *	@deprecated Utiliser Spring data avec QueryDsl pour faire des queries dynamiques 
 *
 */
public class PaginationUtils {

	@NoArgsConstructor
	@AllArgsConstructor
	public static class PropUtils {
		@Getter @Setter String name;
		@Getter @Setter Object value;
	}
	

	final static Logger logger = LoggerFactory.getLogger(PaginationUtils.class.getSimpleName());
	
	public static <T> Page<T> makeQuery(EntityManager em, String query, Pageable pageRequest, List<PropUtils> properties) {
		
		query = addOrderBy(query, pageRequest);
		
		Query q = em.createQuery(query);
		q.setMaxResults(pageRequest.getPageSize());
		q.setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber());
		
		// Ajout des paramètres
		for (PropUtils prop : properties) {
			q.setParameter(prop.name, prop.value);
		}

		List<T> results = q.getResultList();
		
		
		// Requête de count
		int total = executeCountQuery(em, query, properties);
		
		try {
			return new PageImpl<T>(results, pageRequest, total);
		} catch (Exception e) {
			logger.error("SQL error", e);
			throw e;
		}
		
	}

	
	private static int executeCountQuery(EntityManager em, String query, List<PropUtils> properties) {
		if (query.toLowerCase().startsWith("select")) {
			int fromIndex = query.toLowerCase().indexOf("from");
			String selectClause = query.substring(0, fromIndex);
			query = query.substring(fromIndex);
			query = "select " + addDistinct(selectClause) + " count(" + getAlias(query) + ") " + query;
		
		} else if (query.toLowerCase().startsWith("from")) {
			query = "select count(" + getAlias(query) + ") " + query;
		}
		
        query = query.replaceAll("fetch", "");
        query = query.replaceAll("FETCH", "");
        query = query.replaceAll("Fetch", "");
        
        int positionOrderBy = query.toLowerCase().indexOf("order by");
        if (positionOrderBy > 0) {
               query = query.substring(0, positionOrderBy);
        }
		
		Query q = em.createQuery(query);

		// Ajout des paramètres
		for (PropUtils prop : properties) {
			q.setParameter(prop.name, prop.value);
		}

		int total = ((Number)q.getSingleResult()).intValue();
		return total;
	}

	private static String addDistinct(String query) {
		if (query.toLowerCase().startsWith("select distinct")) {
			return "distinct";
		}
		return "";
	}

	private static String getAlias(String query) {
		return "1";
	}


	private static String addOrderBy(String query, Pageable pageRequest) {
		if (pageRequest.getSort() != null) {
			Iterator<Sort.Order> it = pageRequest.getSort().iterator();
			if (it.hasNext()) {
				query += " ORDER BY ";
			}
			while(it.hasNext()) {
				Sort.Order order = it.next();
				query += order.getProperty() + " " + String.valueOf(order.getDirection()) + (it.hasNext() ? ",": "");
			}
		}
		return query;
	}
	
	
}
