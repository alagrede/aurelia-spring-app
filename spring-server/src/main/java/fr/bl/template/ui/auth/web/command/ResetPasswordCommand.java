package fr.bl.template.ui.auth.web.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class ResetPasswordCommand {

	@NotNull
	@Getter @Setter private String mail;
	
	@NotNull
	@Getter @Setter private String redirecturl;

}
