package fr.bl.template.ui.util;

import java.util.Currency;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Utilitaire permettant de récupérer les paramètres en session d'un utilisateur
 * 
 * @author anthony.lagrede
 *
 */
public class SessionUtils {

	final static Logger logger = LoggerFactory.getLogger(SessionUtils.class);
	
	public static Locale DEFAULT_LOCALE = Locale.FRANCE;
	
	/**
	 * Return l'objet demandé ou null dans tous les autres cas.
	 * 
	 * @param parameter
	 * @return object in session
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getParameterInSession(String parameter) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		if (request != null && request.getSession(false) != null) {
			return (T) request.getSession().getAttribute(parameter);
		}

		return null;
	}

	
	/**
	 * Chargement de la {@link Currency}.<br/>
	 * Utilise la Locale par défaut <i>(OUT : si la locale donnée est invalide)</i>
	 * 
	 * @param locale {@link Locale}
	 * @return {@link Currency}
	 */
	private Currency loadCurrency(Locale locale) {

		Currency currency = Currency.getInstance(DEFAULT_LOCALE);
		try {
			// Attention: Dans le cas ou l'on souhaite utiliser la devise définie par l'utilisateur décommenter cette ligne
			// currency = Currency.getInstance(locale);
		} catch (IllegalArgumentException ex) {
			logger.warn("invalide currency given", ex);
		}
		return currency;
	}
	
	/**
	 * Chargement de la {@link Locale}.<br/>
	 * Défini un pays par défaut s'il n'a pas été spécifié<br/>
	 * Utilise la langue par défaut si le code langue est invalide
	 * 
	 * @param String sLocale
	 * @return {@link Locale} 
	 */
	private Locale loadLocale(String sLocale) {

		Locale locale = DEFAULT_LOCALE;
		try {
			locale = LocaleUtils.toLocale(sLocale);
		} catch (IllegalArgumentException ex) {
			logger.warn("invalide locale given", ex);
		}
		
		// Vérification de la présence d'un country par défaut
		if ("".equals(locale.getCountry())) {
			locale = LocaleUtils.toLocale(sLocale + "_" + DEFAULT_LOCALE.getCountry());
			logger.info("load default country :" + DEFAULT_LOCALE.getCountry());
		}
		return locale;
	}
	
}
