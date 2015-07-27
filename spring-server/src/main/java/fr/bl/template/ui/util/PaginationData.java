package fr.bl.template.ui.util;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Objet utilisé par la macro de pagination "/macro_pagination.ftl"
 * Voir utilisation dans {@link UserController}
 * @author anthony.lagrede
 *
 */
@ToString
public class PaginationData implements Serializable {

	@Getter @Setter private Integer pageNumber = 0;
	@Getter @Setter private Integer pageSize = 5;
	@Getter @Setter private Integer pagesAvailable = 1;
	@Getter @Setter private String sortDirection = "";
	@Getter @Setter private String sortField = "";
	
}
