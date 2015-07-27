package fr.bl.template.ui.util.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class HibernateAwareObjectMapper extends ObjectMapper {

	public HibernateAwareObjectMapper() {
		Hibernate4Module module = new Hibernate4Module();
		module.configure(Feature.USE_TRANSIENT_ANNOTATION, false);
	    registerModule(module);
    }
	
}
