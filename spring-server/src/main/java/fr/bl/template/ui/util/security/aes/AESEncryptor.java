package fr.bl.template.ui.util.security.aes;

import fr.bl.template.ui.util.security.ReversiblePasswordEncryptor;


/**
 * Password encoder bas� sur la méthode de cryptage AES <a href="http://fr.wikipedia.org/wiki/Advanced_Encryption_Standard">Advanced Encryption Standard</a>
 * 
 * @author anthony.lagrede
 *
 */
public class AESEncryptor implements ReversiblePasswordEncryptor {

    @Override
    public String encrypt(String plaintextPassword) {
        return Encryptor.encryptAES(plaintextPassword);
    }

    @Override
    public String decrypt(String encryptedPassword) {
        return Encryptor.decryptAES(encryptedPassword);
    }

    
    
}
