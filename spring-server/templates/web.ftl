package ${package}.${app}.web;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


<#if !isDto>
import ${package}.${app}.dao.${domain}Dao;
  <#list foreigns?keys as key>
import ${foreigns[key]?replace(".domain.",".dao.")}Dao;
import ${foreigns[key]};
  </#list>
</#if>
import ${package}.${app}.${targetPackage}.${domain};
import fr.bl.template.ui.util.PaginationData;

@Controller
@RequestMapping("/${domain?uncap_first}/")
public class ${domain}Controller {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	<#if !isDto>
	@Autowired
	private ${domain}Dao ${domain?uncap_first}Dao;

   <#list foreigns?keys as key>
	@Autowired
	private ${key?cap_first}Dao ${key?uncap_first}Dao;
   </#list>
	
	
	</#if>

	@InitBinder // Pour tout le Controller
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

	<#if !isDto>
	private List<${domain}> get${domain}s(PaginationData p) {
		if (p == null || p.getSortField() == "") {
			p.setSortField("${criteria?cap_first}");
			p.setSortDirection("Desc");
			logger.debug(p.toString());
		}

		Sort.Direction direction = "Asc".equals(p.getSortDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = new PageRequest(p.getPageNumber(), p.getPageSize(), direction, p.getSortField());
		Page<${domain}> page = ${domain?uncap_first}Dao.findAll(pageRequest);
		p.setPagesAvailable(page.getTotalPages());
		p.setPageNumber(page.getNumber());
		logger.debug(p.toString());

		return page.getContent();
	}
	</#if>
	
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView show${domain}List(PaginationData p) {
    	ModelAndView mav = new ModelAndView("${app}/${domain?uncap_first}List");
    	<#if isDto>
    	//TODO mav.addObject("${domain?uncap_first}s", ==>get${domain}s(p));
    	<#else>
    	mav.addObject("${domain?uncap_first}s", get${domain}s(p));
    	</#if>
		mav.addObject("paginationData", p);
        return mav;
    }


    @RequestMapping(value = "initadd", method = RequestMethod.GET)
    public ModelAndView init2add(@ModelAttribute("${domain?uncap_first}") ${domain} ${domain?uncap_first}) {
    	ModelAndView mav = new ModelAndView("${app}/${domain?uncap_first}Add");
    
    	<#list foreigns?keys as key>
        Map<String, String> ${key?uncap_first}sOptions = get${key?cap_first}Options();
    	mav.addObject("${key?uncap_first}List", ${key?uncap_first}sOptions);
    	mav.addObject("${key?uncap_first}Id", "*");
   		</#list>
    
    	return mav;
    }

	<#list foreigns?keys as key>
	
	private Map<String, String> get${key?cap_first}Options() {
		Map<String, String> ${key?uncap_first}sOptions = new HashMap<String, String>();
		${key?uncap_first}sOptions.put("*", "--");
		
		List<Object> list = ${key?uncap_first}Dao.findAllMap();
		for (Object row : list) {
		   Object[] obj= (Object[]) row;
		    String id = String.valueOf(obj[0]);
		    String name = (String)obj[1];
		    ${key?uncap_first}sOptions.put(id, name);
		  }
		
		return ${key?uncap_first}sOptions;
	}
   </#list>


    @RequestMapping(value = "modify", method = RequestMethod.GET)
    public ModelAndView modify(Long id) {
    	ModelAndView mav = new ModelAndView("${app}/${domain?uncap_first}Add");
    	<#if isDto>
    	//TODO mav.addObject("${domain?uncap_first}", ==>${domain?uncap_first}Dao.findOne(id));
    	<#else>

    	${domain} ${domain?uncap_first} = ${domain?uncap_first}Dao.findOne(id);
    	mav.addObject("${domain?uncap_first}", ${domain?uncap_first});

		<#list foreigns?keys as key>
    	Map<String, String> ${key?uncap_first}sOptions = get${key?cap_first}Options();
		mav.addObject("${key?uncap_first}List", ${key?uncap_first}sOptions);
		mav.addObject("${key?uncap_first}Id", String.valueOf(${domain?uncap_first}.get${key?cap_first}().getId()));
		</#list>


    	</#if>
    	return mav;
    }
    
    @Transactional
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ModelAndView delete(Long id, RedirectAttributes redirectAttributes) {
    	<#if isDto>
    	//TODO ${domain?uncap_first}Dao.delete(id);
    	<#else>
    	${domain?uncap_first}Dao.delete(id);
    	</#if>
    	redirectAttributes.addFlashAttribute("successNotify", true);
    	return new ModelAndView("redirect:/${domain?uncap_first}/list");
    }

    
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid ${domain} ${domain?uncap_first}, BindingResult result,<#if isDto><#else><#list foreigns?keys as key>@RequestParam("${key?uncap_first}Id") String ${key?uncap_first}Id,</#list></#if>RedirectAttributes redirectAttributes) {
    	if (result.hasErrors() <#if isDto><#else><#list foreigns?keys as key>|| ${key?uncap_first}Id == null || ${key?uncap_first}Id.equals("*")</#list></#if>) {
    		ModelAndView mav = new ModelAndView("${app}/${domain?uncap_first}Add");
    		mav.addObject("errorTag", true);

    		List<FieldError> errors = new ArrayList<FieldError>();

			<#if isDto><#else>
			<#list foreigns?keys as key>
    	    Map<String, String> ${key?uncap_first}sOptions = get${key?cap_first}Options();
    		if (${key?uncap_first}Id == null || ${key?uncap_first}Id.equals("*")) {
    			FieldError f = new FieldError("", "${key?uncap_first}Id", "${key?cap_first} ne peut être vide");
    			errors.add(f);
    		}
    		
    		mav.addObject("${key?uncap_first}List", ${key?uncap_first}sOptions);
    		mav.addObject("${key?uncap_first}Id", ${key?uncap_first}Id);
			</#list>
			</#if>    		
    		
    		mav.addObject("errorList", errors);
    		
    		return mav;
    	} else {
    		if (${domain?uncap_first}.getId() != null)
    			redirectAttributes.addFlashAttribute("successNotify", true);
			<#if isDto>
			//TODO ${domain?uncap_first}Dao.save(${domain?uncap_first});
			<#else>
			
			<#list foreigns?keys as key>
			${key?cap_first} ${key?uncap_first} = ${key?uncap_first}Dao.getOne(Long.valueOf(${key?uncap_first}Id));
    		${domain?uncap_first}.set${key?cap_first}(${key?uncap_first});
			</#list>
			
			${domain?uncap_first}Dao.save(${domain?uncap_first});
			</#if>
    		return new ModelAndView("redirect:/${domain?uncap_first}/list");
    	}
    }
    
    // Pour ne pas bloquer l'utilisateur
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView redirect() {
    	return new ModelAndView("redirect:/${domain?uncap_first}/initadd");
    }
   
}