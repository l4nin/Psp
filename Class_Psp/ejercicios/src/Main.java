import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        // 1. Evaluamos el sistema operativo
        String so = System.getProperty("os.name");
        System.out.println("Sistema operativo detectado: " + so);

        String comando;
        String directorio;

        if (so.toLowerCase().contains("windows")) {
            // 1.1 Windows
            comando = "cmd /c dir";
            // 3.1 Directorio de trabajo en Windows
            directorio = "c:/temp";
        } else {
            // 1.2 Linux
            comando = "sh -c ls";
            // 3.2 Directorio de trabajo en Linux
            directorio = "/tmp";
        }

        // 2. split("\\s") para generar el array de argumentos del proceso
        String[] argumentos = comando.split("\\s");

        ProcessBuilder pb = new ProcessBuilder(argumentos);

        // 3. Cambiamos el directorio de trabajo del proceso
        pb.directory(new File(directorio));

        // 4. Ejecutamos el proceso
        try {
            Process proceso = pb.start();

            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));

            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
            lector.close();

            int codigoSalida = proceso.waitFor();
            System.out.println("Proceso finalizado con código de salida: " + codigoSalida);

        } catch (IOException e) {
            System.out.println("Error al ejecutar el proceso (¿existe el directorio " + directorio + "?): " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}