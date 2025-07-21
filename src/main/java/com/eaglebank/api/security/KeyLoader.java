package com.eaglebank.api.security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;
import java.util.Base64;

/**
 * Utility class to load RSA keys from PEM-encoded files.
 */
public class KeyLoader {

    /**
     * Load RSA private key from a PEM file.
     *
     * @param path Path to PEM file containing PKCS#8 private key
     * @return Parsed PrivateKey object
     * @throws IOException              on file read failure
     * @throws GeneralSecurityException on decoding failure
     */
    public static PrivateKey loadPrivateKey(String path) throws Exception {
        String key = Files.readString(Path.of(path))
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    /**
     * Load RSA public key from a PEM file.
     *
     * @param path Path to PEM file containing X.509 public key
     * @return Parsed PublicKey object
     * @throws IOException              on file read failure
     * @throws GeneralSecurityException on decoding failure
     */
    public static PublicKey loadPublicKey(String path) throws Exception {
        String key = Files.readString(Path.of(path))
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
