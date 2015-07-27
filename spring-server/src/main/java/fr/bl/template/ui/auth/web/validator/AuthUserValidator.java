package fr.bl.template.ui.auth.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.util.SpringContext;

public class AuthUserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthUser.class.equals(clazz);
	}

	@Override
	public void validate(Object command, Errors errors) {

		SpringContext.getBean(Validator.class).validate(command, errors);
		
		AuthUser user = (AuthUser) command;
		if (user.getPassword() != null && user.getId() != null) {
			errors.rejectValue("password", "auth.authUser.form.validator.password.empty");
		}

	}

}
