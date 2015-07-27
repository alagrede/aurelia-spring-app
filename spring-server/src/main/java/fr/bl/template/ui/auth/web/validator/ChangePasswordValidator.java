package fr.bl.template.ui.auth.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fr.bl.template.ui.auth.web.command.ChangePasswordCommand;
import fr.bl.template.ui.util.SpringContext;

public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordCommand.class.equals(clazz);
	}
	
	@Override
	public void validate(Object command, Errors errors) {
		
		SpringContext.getBean(Validator.class).validate(command, errors);

		
		ChangePasswordCommand change = (ChangePasswordCommand) command;
		if (change.getPassword() != null &&  change.getConfirmPassword() != null) {
			if (!change.getPassword().equals(change.getConfirmPassword())) {
				errors.rejectValue("password", "auth.changePasswordCommand.form.validator.password.mismatch", "Les mots de passe ne correspondent pas.");
			}
		}

	}



	
}
