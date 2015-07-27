package fr.bl.template.ui.auth.web.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class ChangePasswordCommand {

	@NotNull
	@Getter @Setter private Long userId;
	
	@NotNull
	@Size(min = 5)
	@Getter @Setter private String password;
	
	@NotNull
	@Size(min = 5)
	@Getter @Setter private String confirmPassword;
	
}
