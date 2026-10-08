/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tienda.util;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author manue
 */
public class PasswordUtils {

    public static String hashPassword(String password) throws Exception {
// Generar salt aleatorio
        byte[] salt = new byte[16]; // 16bytes=128bits
        new SecureRandom().nextBytes(salt);
// Generar hash
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                100000,
                256
        );
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
// Convertir salt y hash a Base64
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);
// Devolver salt:hash
        return saltBase64 + ":" + hashBase64;
    }

    public static boolean checkPassword(String password, String passwordBD)
            throws Exception {
        String[] partes = passwordBD.split(":");
        String saltBase64 = partes[0];
        String hashBase64 = partes[1];
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] hashGuardado = Base64.getDecoder().decode(hashBase64);
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                100000,
                256
        );
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashCalculado = factory.generateSecret(spec).getEncoded();
        return java.security.MessageDigest.isEqual(
                hashCalculado,
                hashGuardado
        );
    }
}
