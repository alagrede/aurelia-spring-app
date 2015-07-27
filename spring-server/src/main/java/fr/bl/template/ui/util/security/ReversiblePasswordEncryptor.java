package fr.bl.template.ui.util.security;


/**
 * {@link PasswordEncryptor} réversible
 * 
 * @author anthony.lagrede
 *
 */
public interface ReversiblePasswordEncryptor extends PasswordEncryptor {

    /**
     * Méthode de décryption
     * @param le password à décrypter
     * 
     * @return le password encrypté
     */
    String decrypt(String encryptedPassword);
}
