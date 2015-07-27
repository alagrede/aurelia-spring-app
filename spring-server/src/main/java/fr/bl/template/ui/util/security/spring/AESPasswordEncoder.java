package fr.bl.template.ui.util.security.spring;

import org.springframework.security.crypto.password.PasswordEncoder;

import fr.bl.template.ui.util.security.PasswordEncryptor;
import fr.bl.template.ui.util.security.aes.AESEncryptor;

/**
 * Password encoder basé sur {@link AESEncryptor}
 * 
 * @author anthony.lagrede
 *
 */
public class AESPasswordEncoder implements PasswordEncoder {

    PasswordEncryptor passwordEncryptor = new AESEncryptor();

	@Override
	public String encode(CharSequence rawPassword) {
		return passwordEncryptor.encrypt(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(passwordEncryptor.encrypt(rawPassword.toString()));
	}
    
}
