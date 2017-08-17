/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.stptv.formularioManuelas.controlador.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author christian
 */
public class ClaveEncriptada implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5838986135965991425L;

	public static synchronized String claveEncriptada(String password) {
        return Crypt.encrypt(password);
    }

    public static String claveDesEncriptada(String password) {
        return Crypt.decrypt(password);
    }

    public static String claveEncriptadaMd5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
