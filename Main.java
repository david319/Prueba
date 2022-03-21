package Semana_10.Prueba;

import java.io.IOException;
import java.util.Scanner;

import static Semana_10.Prueba.ITunes.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        int opcion = -1;

        do {
            try {
                System.out.println("""
                        Menu de Opciones:
                        0-Iniciar
                        1-addSong()
                        2-reviewsSong()
                        3-downloadSong()
                        4-songs()
                        5-infoSong()
                        6-exit()
                        Seleccione una opción:""");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 0:
                        System.out.println("Inicio");
                        new ITunes();
                        break;
                    case 1:
                        System.out.println("addSong");
                        System.out.println("Ingrese el nombre de la cancion:");
                        String nombre = leer.next();
                        System.out.println("Ingrese el nombre del artista:");
                        String artista = leer.next();
                        System.out.println("Ingrese el precio de la canción:");
                        double precio = leer.nextDouble();
                        addSong(nombre, artista, precio);
                        break;
                    case 2:
                        System.out.println("reviewsSong");
                        System.out.println("Ingrese el codigo de la cancion:");
                        int codigo = leer.nextInt();
                        System.out.println("Ingrese las estrellas a calificar:");
                        int estrellas = leer.nextInt();
                        reviewSong(codigo, estrellas);
                        break;
                    case 3:
                        System.out.println("downloadSong");
                        System.out.println("Ingrese el codigo de la cancion:");
                        int codigo1 = leer.nextInt();
                        System.out.println("Ingrese el nombre del cliente:");
                        String cliente = leer.next();
                        downloadSong(codigo1, cliente);
                        break;
                    case 4:
                        System.out.println("songs");
                        songs();
                        break;
                    case 5:
                        System.out.println("infoSong");
                        System.out.println("Ingrese el codigo de la cancion:");
                        int codigo2 = leer.nextInt();
                        infoSong(codigo2);
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;

                }
            } catch (Exception e) {
                System.out.println("Opción no valida");
            }
        } while (opcion != 6);
    }
}


