package ${package}.${app}.dao;

import ${package}.${app}.domain.${domain};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ${domain}Dao extends JpaRepository<${domain}, Long> {

    //@Query("from ${domain} ${domain?substring(0, 1)?lower_case} where upper(${domain?substring(0, 1)?lower_case}.${criteria}) = upper(:${criteria})")
    //${domain} findByNameIgnoreCase(@Param("${criteria}") String ${criteria});
    
    @Query("select ${domain?substring(0, 1)?lower_case}.${criteria}, ${domain?substring(0, 1)?lower_case}.${name} from ${domain} ${domain?substring(0, 1)?lower_case}")
	List<Object> findAllMap();	
    
    
    
}
