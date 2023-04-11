package ejercicio2;

import java.util.Scanner;

public class Recibo {

    public static void main(String[] args) {

        //Abrimos el escaner
        Scanner sc = new Scanner(System.in);

        //Pedimos el codigo al usuario
        System.out.println("Escriba el codigo de descifrado (tiene que tener una longitud de 16)");
        String clave=sc.nextLine();

        //Desciframos el mensaje del fichero y mostramos
        System.out.println("El mensaje es: "+Utilidades.leerTextCifrado(clave));

        //Cerramos el esaner
        sc.close();
    }

}
