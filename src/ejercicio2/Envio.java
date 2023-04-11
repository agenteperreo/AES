package ejercicio2;

import java.util.Scanner;

public class Envio {

    public static void main(String[] args) {

        //Abrimos el escaner
        Scanner sc = new Scanner(System.in);

        //Pedimos el mensaje al usuario
        System.out.println("¿Cual es el mensaje?");
        String mensaje = sc.nextLine();

        //Pedimos el codigo de cifrado del mensaje
        System.out.println("Introduzca el codigo de cifrado (debe tener una longitud de 16)");
        String codigo = sc.nextLine();

        //Ciframos el tecto y lo guardamo en el fichero
        Utilidades.guardarTextoCifrado(mensaje, codigo);

        //Mostramos que se ha enviado el mensaje
        System.out.println("Mensaje enviado");
    }
}
