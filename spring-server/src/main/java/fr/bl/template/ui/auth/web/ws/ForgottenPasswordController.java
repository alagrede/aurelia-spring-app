package fr.bl.template.ui.auth.web.ws;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.bl.template.ui.auth.dao.AuthUserDao;
import fr.bl.template.ui.auth.dao.OneTimeTokenDao;
import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.domain.OneTimeToken;
import fr.bl.template.ui.auth.service.ForgottenPasswordService;
import fr.bl.template.ui.auth.web.command.ResetPasswordCommand;

/**
 * Controller pour le changement des mots de passe (non authentifié)
 * 
 * @author anthony.lagrede
 *
 */
@RestController
@RequestMapping("/ws/")
public class ForgottenPasswordController {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private AuthUserDao authUserDao;
	@Autowired private OneTimeTokenDao oneTimeTokenDao;

	@Autowired private ForgottenPasswordService forgottenPasswordService;
	
    
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordCommand resetPasswordCommand, BindingResult result) {

    	if (result.hasErrors() ) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	} else {
    		//send mail and create OneTimeToken
    		forgottenPasswordService.startResetPassword(resetPasswordCommand.getMail(), resetPasswordCommand.getRedirecturl());
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
			
    }


    @RequestMapping(value = "/reinitPassword", method = RequestMethod.GET)
    public ModelAndView reinitPassword(@RequestParam("token") String token, @RequestParam("redirecturl") String redirecturl) {

    	if (token != null && !"".equals(token)) {

    		try {
    			OneTimeToken oneTimeToken = oneTimeTokenDao.findOneByKey(token);
    			AuthUser authUser = authUserDao.findOne(oneTimeToken.getUserId());

    			forgottenPasswordService.endResetPassword(authUser, oneTimeToken);
    			
    		}catch (NoResultException e) {
    			return new ModelAndView("redirect:" + redirecturl + "?error=badtoken");

    		}
    		
    	}
    	
		return new ModelAndView("redirect:" + redirecturl);
    }

}
