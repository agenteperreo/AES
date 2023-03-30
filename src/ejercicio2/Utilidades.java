package ejercicio2;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utilidades {

    static final int LONGITUD_BLOQUE = 16;
    public Key obtenerClave(String contraseña) {

        Key clave = new SecretKeySpec(contraseña.getBytes(),0, LONGITUD_BLOQUE, "AES");

        return clave;
    }

    public String cifrado(String mensaje, String algoritmo, String contraseña) {

        byte[] cypherText = null;
        try {
            Cipher cipher = Cipher.getInstance(algoritmo);

            cipher.init(Cipher.ENCRYPT_MODE, obtenerClave(contraseña));

            cypherText = cipher.doFinal(mensaje.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("ERROR: El algoritmo no existe");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("ERROR: Padding no disponible");
        } catch (InvalidKeyException e) {
            System.err.println("ERROR: Clave no valida");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("ERROR: Blocke demasiado grande");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("ERROR: Padding incorrecto");
            e.printStackTrace();
        }

        return (Base64.getEncoder().encodeToString(cypherText));
    }

    public String descifrado(String mensaje, String algoritmo, String contraseña) {
        byte[] plainText = null;
        try {
            Cipher cipher = Cipher.getInstance(algoritmo);

            cipher.init(Cipher.DECRYPT_MODE, obtenerClave(contraseña));

            plainText = cipher.doFinal(Base64.getDecoder().decode(mensaje));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("ERROR: El algoritmo no existe");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("ERROR: Padding no disponible");
        } catch (InvalidKeyException e) {
            System.err.println("ERROR: Clave no valida");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("ERROR: Blocke demasiado grande");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("ERROR: Padding incorrecto");
            e.printStackTrace();
        }

        return "ksjhois";
    }
}
