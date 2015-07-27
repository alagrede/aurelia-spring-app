package fr.bl.template.ui.util.security.aes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

    
public class Encryptor {



    private static final String AES_TRANSFORMATION_STRING = "AES";
    private static final String AES_KEY_STRING            = "oirzegjiro8978EF-zf-ze-rez-gfre";
    private static final String AES_CHARSET               = "ISO-8859-15";

    // Encryption / decryption avec l'algorithme AES128
    //-------------------------------------------------------------------------

    /**
     * Encrypte une chaine de caractère en utilisant l'algo AES128
     * 
     * @param toEncrypt
     */
    public static String encryptAES(String toEncrypt) {
            
        String encrypted = null;
        
        try {
            
           // Instantiate the cipher
    
           Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION_STRING);
    
           cipher.init(Cipher.ENCRYPT_MODE, getKey());

           // Récupère la clé secrète
            byte[] cipherText = cipher.doFinal(toEncrypt.getBytes(AES_CHARSET));
            encrypted = new String(convertToHex(cipherText)).toUpperCase();
        }
        catch (Exception e) {
            System.out.println("Impossible to encrypt with AES algorithm: string=(" + toEncrypt + ")");
        }
        
        return encrypted;
    }

    private static SecretKeySpec getKey() {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Impossible : MD5 is a good algorithm name
            throw new RuntimeException(e);
        }

        try {
            return  new SecretKeySpec(digest.digest(new String(AES_KEY_STRING.getBytes(),AES_CHARSET).getBytes()), AES_TRANSFORMATION_STRING);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Decrypte une chaine de caractère en utilisant l'algo DES
     * 
     * @param toDecrypt
     */
    public static String decryptAES(String toDecrypt) {
        
        String decrypted = null;
        
        try {
            // Instantiate the cipher

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION_STRING);

            cipher.init(Cipher.DECRYPT_MODE, getKey());
            byte[] original = cipher.doFinal(hexToBytes(toDecrypt));
            return new String(original);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Impossible to decrypt with AES algorithm: string=(" + toDecrypt + ") message=(" + e.getMessage() + ")");
        }
        
        return decrypted;
    }

    private static byte[] hexToBytes(String hex) {
        return hexToBytes(hex.toCharArray());
    }

    private static byte[] hexToBytes(char[] hex) {
      int length = hex.length / 2;
      byte[] raw = new byte[length];
      for (int i = 0; i < length; i++) {
        int high = Character.digit(hex[i * 2], 16);
        int low = Character.digit(hex[i * 2 + 1], 16);
        int value = (high << 4) | low;
        if (value > 127)
          value -= 256;
        raw[i] = (byte) value;
      }
      return raw;
    }

    /**
     * Generic Hex conversion
     * @param data
     * @return
     */
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            }
            while (two_halfs++ < 1);
        }
        return buf.toString();
    }

}