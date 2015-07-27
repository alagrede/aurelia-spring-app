package fr.bl.template.ui.util.security;

/**
 * Encrypteur de password
 * 
 * @author anthony.lagrede
 *
 */
public interface PasswordEncryptor {

    String encrypt(String plaintextPassword);
}
