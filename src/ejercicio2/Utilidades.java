package ejercicio2;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utilidades {

    static final int LONGITUD_BLOQUE = 16;

    static final String ALGORITMO ="AES/ECB/PKCS5Padding";

    public static Key obtenerClave(String contraseña) {

        Key clave = new SecretKeySpec(contraseña.getBytes(),0, LONGITUD_BLOQUE, "AES");

        return clave;
    }

    public static String cifrado(String mensaje, String contraseña) {

        byte[] cypherText = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);

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

    public static String descifrado(String mensaje, String contraseña) {
        byte[] plainText = null;

        String mensajeDescifrado = "";

        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            cipher.init(Cipher.DECRYPT_MODE, obtenerClave(contraseña));

            plainText = cipher.doFinal(Base64.getDecoder().decode(mensaje));

            mensajeDescifrado = new String(plainText);

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

        return mensajeDescifrado;
    }

    public static void guardarTextoCifrado(String texto, String clave){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/ejercicio2/mensajeEncryptado"));
            bw.write(cifrado(texto, clave));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.err.println("No se ha podido guardar el texto");
            e.printStackTrace();
        }
    }

    public static String leerTextCifrado(String clave) {

        //Creamos la variable que va a contener el texto cifrado y la inicializamos a null
        String textoCifrado = null;

        try {
            //Leemos las lineas del texto cifrado del fichero que almacena el texto cifrado y lo guardamos en la variable creada anteriormente
            BufferedReader br = new BufferedReader(new FileReader("src/ejercicio2/mensajeEncryptado"));
            textoCifrado = br.readLine();
            br.close();

            //Control de excepciones
        } catch (IOException e) {
            System.err.println("No se ha podido recuperar el texto");
            e.printStackTrace();
        }

        //Devolvemos el codigo descifradocon el mtodo descifrar de la clase maquina enigma y como parametros el tesxto cifrado
        // y la clave que obtengo de otro metodo en la clase maquina enigma
        return descifrado(textoCifrado, clave);
    }
}
