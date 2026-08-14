package com.uade.e_commerce.service;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
public class PasswordService {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACIONES = 210_000;
    private static final int TAMANO_SALT_BYTES = 16;
    private static final int TAMANO_HASH_BITS = 256;

    private final SecureRandom secureRandom = new SecureRandom();

    public String hashear(String password) {
        byte[] salt = new byte[TAMANO_SALT_BYTES];
        secureRandom.nextBytes(salt);
        byte[] hash = derivarClave(password.toCharArray(), salt, ITERACIONES);

        return "pbkdf2_sha256$" + ITERACIONES + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    public boolean coincide(String password, String passwordGuardada) {
        try {
            String[] partes = passwordGuardada.split("\\$");
            if (partes.length != 4 || !"pbkdf2_sha256".equals(partes[0])) {
                return false;
            }

            int iteraciones = Integer.parseInt(partes[1]);
            byte[] salt = Base64.getDecoder().decode(partes[2]);
            byte[] hashGuardado = Base64.getDecoder().decode(partes[3]);
            byte[] hashCalculado = derivarClave(password.toCharArray(), salt, iteraciones);
            return MessageDigest.isEqual(hashGuardado, hashCalculado);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private byte[] derivarClave(char[] password, byte[] salt, int iteraciones) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iteraciones, TAMANO_HASH_BITS);
        try {
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new IllegalStateException("No se pudo proteger la password", exception);
        } finally {
            spec.clearPassword();
        }
    }
}
