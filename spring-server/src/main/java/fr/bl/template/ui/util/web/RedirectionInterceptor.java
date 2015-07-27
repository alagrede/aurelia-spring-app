package fr.bl.template.ui.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Cet intercepteur permet de gérer correctement le cas des redirections 302 avec l'Ajax en rajoutant l'url cible 
 * de la page redirigée. 
 * (Lors d'un appel ajax, la redirection 302 est suivie automatiquement par l'implémentation interne 
 * de l'objet XMLHTTPRequest. En Ajax le navigateur ne remplace jamais l'url contenue 
 * dans le header de la redirection (le paramètre 'Location') )
 * 
 * Lors d'une redirection on remplace le 302 par un autre code (278) et on rajoute dans le header de la réponse finale, l'url de la page. 
 * Côté Javascript, il faut surveiller notre custom statusCode (278) et remplacer l'url de la page par celui rajouté ici dans le header.
 * 
 * success: function(html, status, xhr) { // Je récupère la réponse          	
 *     if (xhr.status == 278){
 *         console.log("redirection detected: " + xhr.getResponseHeader("Location"));	
 *         link = xhr.getResponseHeader("Location");
 *     } 
 * 
 * @author anthony.lagrede
 *
 */
public class RedirectionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null && modelAndView.getViewName() != null && modelAndView.getViewName().contains("redirect:")) {
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request); 
			String redirectionUrl = request.getContextPath() + modelAndView.getViewName().split(":")[1];
			flashMap.put("redirection", redirectionUrl);
		} else {
			if (modelAndView != null && modelAndView.getModel().containsKey("redirection")) {
				response.setStatus(278);
				response.setHeader("Location", (String)modelAndView.getModel().get("redirection"));
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
