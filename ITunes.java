package Semana_10.Prueba;

import java.io.*;
import java.util.Calendar;

public class ITunes {
    static RandomAccessFile code;
    static RandomAccessFile song;
    static RandomAccessFile download;

    public ITunes() throws IOException {
        code = new RandomAccessFile("Codigos.itn", "rw");
        song = new RandomAccessFile("songs.itn", "rw");
        download = new RandomAccessFile("Downloads.itn", "rw");
        if (code.length() == 0) {
            code.writeInt(1);
            code.writeInt(1);
        }
    }

    public static int getCodigo(long offset) throws IOException {
        song.seek(offset);
        int cancion = code.readInt();
        int descarga = code.readInt();
        if (song.length() != 0) {
            if (offset == 0) {
                return ++cancion;
            } else if (offset == 4) {
                return ++descarga;
            }
        } else if (song.length() != 0 && download.length() == 0) {
            if (offset == 0) {
                return ++cancion;
            } else if (offset == 4) {
                return descarga;
            }
        } else {
            if (offset == 0) {
                return cancion;
            } else if (offset == 4) {
                return descarga;
            }
        }
        return -1;
    }

    public static void addSong(String nombre, String cantante, double precio) throws IOException {
        song.seek(0);
        int cancion = code.readInt();
        int descarga = code.readInt();
        if (song.length() != 0) {
            song.seek(song.length());
            song.writeInt(++cancion);
            song.writeUTF(nombre);
            song.writeUTF(cantante);
            song.writeDouble(precio);
            song.writeInt(0);
            song.writeInt(0);
        } else {
            song.seek(song.length());
            song.writeInt(cancion);
            song.writeUTF(nombre);
            song.writeUTF(cantante);
            song.writeDouble(precio);
            song.writeInt(0);
            song.writeInt(0);
        }
        code.seek(0);
        code.writeInt(++cancion);
        code.writeInt(descarga);
    }

    public static void reviewSong(int code, int stars) throws IOException {
        song.seek(0);
        while (song.getFilePointer() < song.length()) {
            int codigo = song.readInt();
            String name = song.readUTF();
            String cantante = song.readUTF();
            double precio = song.readDouble();
            long posicion = song.getFilePointer();
            int star = song.readInt();
            int review = song.readInt();
            if (code == codigo) {
                if (stars >= 0 && stars <= 5) {
                    star += stars;
                    ++review;
                    song.seek(posicion);
                    song.writeInt(star);
                    song.writeInt(review);
                } else {
                    throw new IllegalArgumentException("Ya existe");
                }
            } else {
                System.out.println("Código de canción inválido.");
            }
        }
    }

    public static void downloadSong(int codeSong, String cliente) throws IOException {
        code.seek(0);
        int cancion = code.readInt();
        int descarga = code.readInt();

        song.seek(0);
        while (song.getFilePointer() < song.length()) {
            int codigo = song.readInt();
            String name = song.readUTF();
            String cantante = song.readUTF();
            double precio = song.readDouble();
            int star = song.readInt();
            int review = song.readInt();
            if (codeSong == codigo) {
                download.seek(download.length());
                if (download.length() != 0) {
                    download.writeInt(++descarga);
                    download.writeLong(Calendar.getInstance().getTimeInMillis());
                    download.writeInt(codigo);
                    download.writeUTF(cliente);
                    download.writeDouble(precio);
                } else {
                    download.writeInt(descarga);
                    download.writeLong(Calendar.getInstance().getTimeInMillis());
                    download.writeInt(codigo);
                    download.writeUTF(cliente);
                    download.writeDouble(precio);
                }
                code.seek(0);
                code.writeInt(cancion);
                code.writeInt(++descarga);
                return;
            } else {
                System.out.println("Código de canción inválido.");
            }
        }
    }

    public static void songs() throws IOException {
        songs("impresión");
    }

    private static void songs(String txtFile) throws IOException {
        PrintWriter escritura = new PrintWriter(txtFile);
        escritura.print("");
        escritura.close();

        FileReader canciones = new FileReader(txtFile);
        BufferedReader leer = new BufferedReader(canciones);
        String linea = leer.readLine();
        while (linea != null) {
            System.out.println(linea);
            linea = leer.readLine();
        }
        leer.close();

        while (song.getFilePointer() < song.length()) {
            int code = song.readInt();
            String name = song.readUTF();
            String cantante = song.readUTF();
            double precio = song.readDouble();
            double star = song.readInt();
            int review = song.readInt();

            double ratingfinal = star / review;

            System.out.println("");
            System.out.println("Codigo: " + code);
            System.out.println("Nombre: " + name);
            System.out.println("Cantante: " + cantante);
            System.out.println("Cantidad de reviews hechas: " + review);
            System.out.println("Precio: " + precio);
            System.out.println("Rating: " + ratingfinal);
        }
    }

    public static void infoSong(int codeSong) throws IOException {
        int des = 0;
        song.seek(0);
        download.seek(0);

        while (song.getFilePointer() < song.length()) {
            int code = song.readInt();
            String name = song.readUTF();
            String cantante = song.readUTF();
            double precio = song.readDouble();
            int star = song.readInt();
            int review = song.readInt();
            if (codeSong == code) {
                download.seek(0);
                while (download.getFilePointer() < download.length()) {
                    int code2 = download.readInt();
                    long f = download.readLong();
                    int cS = download.readInt();
                    String cl = download.readUTF();
                    double pr = download.readDouble();
                    if (cS == codeSong) {
                        des++;
                    }
                }
                System.out.println("Código de la canción: " + code + " - Nombre:" + name + " - Cantante: " + cantante
                        + " - Precio: " + precio + " - Estrellas recibidas: " + star + " - Cantidad de reviews recibidos: " + review + " - Rating: " + (star / review));
            }
        }
    }
}
