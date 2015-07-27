package fr.bl.template.ui.auth.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import fr.bl.template.ui.auth.dao.AuthUserDao;
import fr.bl.template.ui.auth.dao.OneTimeTokenDao;
import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.domain.OneTimeToken;
import fr.bl.template.ui.util.SpringContext;

/**
 * Service de changement des mots de passe (non authentifié)
 * @author anthony.lagrede
 *
 */
@Component
public class ForgottenPasswordService {

	private static final String TEMPLATE_RESETMAIL = "mailstemplates/resetmail.vm";

	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${baseAppUrl}")
	private String baseAppUrl;
	
	@Autowired private AuthUserDao authUserDao;
	@Autowired private OneTimeTokenDao oneTimeTokenDao;
	@Autowired private JavaMailSender mailSender;
	@Autowired  private VelocityEngine velocityEngine;
	
	@Transactional
	public void startResetPassword(String email, String redirectUrl) {
		AuthUser authUser = authUserDao.findByMail(email);

		OneTimeToken ott = createOneTimePassword(authUser);
		sendResetMail(ott.getToken(), authUser.getEmail(), redirectUrl);
	}

	@Transactional
	public void endResetPassword(AuthUser authUser, OneTimeToken oneTimeToken) {
		String newPassword = generatAndSaveNewPassword(authUser);
		oneTimeTokenDao.delete(oneTimeToken);
		sendNewPasswordMail(newPassword, authUser.getEmail());
	}



	private String generatAndSaveNewPassword(AuthUser authUser) {
		String newPassword = "pass" + generateToken().substring(0, 6);
		PasswordEncoder passwordEncoder = SpringContext.getBean(BCryptPasswordEncoder.class);
		String encodedPassword = passwordEncoder.encode(newPassword);
		authUser.setPassword(encodedPassword);
		authUserDao.save(authUser);
		return newPassword;
	}
	
	private OneTimeToken createOneTimePassword(AuthUser authUser) {
		OneTimeToken ott = null;
		ott = oneTimeTokenDao.findOneByUserId(authUser.getId());
		if (ott == null) {
			ott = new OneTimeToken();
		}
		
		ott.setToken(generateToken());
		ott.setUserId(authUser.getId());
		oneTimeTokenDao.save(ott);
		return ott;
	}

	private void sendResetMail(final String token, final String email, final String redirecturl) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom("noreply@berger-levrault.fr");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("resetlink", baseAppUrl + "/ws/reinitPassword?token=" + token + "&redirecturl=" + URLEncoder.encode(redirecturl, "UTF-8"));
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, TEMPLATE_RESETMAIL, "UTF-8", model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
		
	}

	private void sendNewPasswordMail(String password, String email) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("noreply@berger-levrault.fr");
		msg.setSubject("[Administration] Réinitialisation de votre mot de passe.");
		
		msg.setTo(email);
		msg.setText("Votre nouveau mot de passe est: " + password);
		
		try{
			this.mailSender.send(msg);
		} catch (MailException ex) {
			logger.error(ex.getCause() + ":" + ex.getMessage());
		}
	}

	private String generateToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
