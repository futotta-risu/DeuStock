package es.deusto.deustock.util.crypto;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

/**
 * Class with crypto functionality
 *
 * @author Erik B. Terres
 */
public class Crypto {

    private Crypto(){}

    /**
     * Returns the MD5 encryption of a String.
     *
     * @param text Non null String
     */
    public static String getHash(String text){
        Objects.requireNonNull(text);
        return DigestUtils.sha512Hex(text).toUpperCase();
    }
}
