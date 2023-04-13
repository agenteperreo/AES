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

    /**
     * Constante con la longitud de la clave para la Key
     */
    static final int LONGITUD_BLOQUE = 16;

    /**
     * Constante con el algoritmo a usar en el cifrado y descifrado
     */
    static final String ALGORITMO = "AES/ECB/PKCS5Padding";

    /**
     * Metodo para obtener la key a partir de la contraseña del usuario
     *
     * @param contraseña Constraseña del usuario
     * @return Key de la contraseña
     */
    public static Key obtenerClave(String contraseña) {

        Key clave = new SecretKeySpec(contraseña.getBytes(), 0, LONGITUD_BLOQUE, "AES");

        return clave;
    }

    /**
     * Metodo para cifrar el mensaje con la contraseña proporcionada
     *
     * @param mensaje    Mensaje del usuario
     * @param contraseña Contraseña con la que se sacará la key y se cifrará el mensajer
     * @return String del mensaje encriptado
     */
    public static String cifrado(String mensaje, String contraseña) {

        //Creamos un array de bytes que almacenará el mensaje cifrado
        byte[] cypherText = null;

        try {
            //Instanciamos el Cipher con la constante algoritmo
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            //Iniciamos el cipher con el modo encrypt y la key de la contraseña que obtenemos con el otro metodo
            cipher.init(Cipher.ENCRYPT_MODE, obtenerClave(contraseña));

            //Hacemos el doFinalde los bytes del mensaje y lo guardamo en el array de bytes creado anteriormente
            cypherText = cipher.doFinal(mensaje.getBytes());

            //Control de excepciones
        } catch (NoSuchAlgorithmException e) {
            System.err.println("ERROR: El algoritmo no existe");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("ERROR: Padding no disponible");
            e.printStackTrace();
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

        //Devolvemos un String con el mensaje cifrado
        return (Base64.getEncoder().encodeToString(cypherText));
    }

    /**
     * Metodo para descifrar el mensaje con la contraseña proporcionada
     *
     * @param mensaje    Mensaje del usuario
     * @param contraseña Contraseña para descifrar el mensaje
     * @return El mensaje descifrado
     */
    public static String descifrado(String mensaje, String contraseña) {

        //Creamos un array de bytes que almacenará el mensaje y lo inicializamos a 0
        byte[] plainText = null;

        //Creamos un variable String vacia que contendrá el mensaje ya descifrado
        String mensajeDescifrado = "";

        try {
            //Instanciamos el cipher con la constante ALGORITMO
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            //Iniciamos el cipher en modo DECRYPT y la key de la contraseña
            cipher.init(Cipher.DECRYPT_MODE, obtenerClave(contraseña));

            //Guardamos el doFinal.decode del mensaje en el array de bytes creado anteriormente
            plainText = cipher.doFinal(Base64.getDecoder().decode(mensaje));

            //Guardamos el String del array de bytes con el mensaje descifrado en la variable creada anteriormente
            mensajeDescifrado = new String(plainText);

            //Control de excepciones
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

        //Devolvemos el mensaje descifrado
        return mensajeDescifrado;
    }

    /**
     * Metodo que guardará el return del cifrado en un fichero creado anteriormente
     *
     * @param texto Mensaje a encriptar
     * @param clave Contraseña del usuario
     */
    public static void guardarTextoCifrado(String texto, String clave) {
        try {
            //Creamos el BufferedWriter y el FileWriter con el path del fichero creado
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/ejercicio2/mensajeEncryptado"));

            //Escribimos el return del metodo cifrado con el texto y la clave
            bw.write(cifrado(texto, clave));

            //Nueva linea
            bw.newLine();

            //Cerramos el buffered para terminar de escribir
            bw.close();

            //Control de excepciones
        } catch (IOException e) {
            System.err.println("No se ha podido guardar el texto");
            e.printStackTrace();
        }
    }

    /**
     * Metodo para leer el mensaje cifrado del fichero y asi llamar al descifrado
     *
     * @param clave Contraseña del usuario
     * @return Return del metodo descifrado con el mensajeCifrado y la clave
     */
    public static String leerTextCifrado(String clave) {

        //Creamos la variable que va a contener el texto cifrado y la inicializamos a null
        String textoCifrado = null;

        try {
            //Generamos los buffereds para leer el mensaje encriptado
            BufferedReader br = new BufferedReader(new FileReader("src/ejercicio2/mensajeEncryptado"));

            //Leemos la linea del mensaje
            textoCifrado = br.readLine();

            //Cerramos el reader
            br.close();

            //Control de excepciones
        } catch (IOException e) {
            System.err.println("No se ha podido recuperar el texto");
            e.printStackTrace();
        }

        //Devolvemos el codigo descifrado con el metodo descifrado
        return descifrado(textoCifrado, clave);
    }
}
