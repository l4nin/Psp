import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Lanza un proceso externo "ping localhost" y muestra por pantalla:
 *  - Si el proceso está vivo (al principio y al final)
 *  - PID, comando, argumentos y usuario
 *  - La salida del proceso
 *  - El código de salida tras esperar con waitFor()
 */
public class Main {

    public static void main(String[] args) {

        // En Linux, "ping" se ejecuta indefinidamente salvo que limitemos
        // el número de paquetes con -c (aquí 4 paquetes).
        ProcessBuilder constructorProceso = new ProcessBuilder("ping", "-c", "4", "localhost");

        // Mezclamos la salida de error con la salida estándar para leer todo junto
        constructorProceso.redirectErrorStream(true);

        Process procesoPing = null;

        try {
            // 1. Lanzamos el proceso
            procesoPing = constructorProceso.start();

            // 2. Comprobamos si está vivo justo después de lanzarlo
            boolean estaVivoInicio = procesoPing.isAlive();
            System.out.println("¿Proceso vivo al inicio? " + estaVivoInicio);

            // 3. Información del proceso (PID, comando, argumentos y usuario)
            mostrarInformacionProceso(procesoPing);

            // 4. Leemos y mostramos la salida del proceso línea a línea
            System.out.println("\n--- Salida del proceso ---");
            try (BufferedReader lectorSalida = new BufferedReader(
                    new InputStreamReader(procesoPing.getInputStream()))) {

                String lineaSalida;
                while ((lineaSalida = lectorSalida.readLine()) != null) {
                    System.out.println(lineaSalida);
                }
            }

            // 5. Esperamos a que el proceso termine y recogemos su código de salida
            int codigoSalida = procesoPing.waitFor();

            // 6. Resultado final y comprobación de si sigue vivo
            System.out.println("\n--- Resultado final ---");
            System.out.println("Código de salida: " + codigoSalida
                    + (codigoSalida == 0 ? " (finalizó correctamente)" : " (finalizó con error)"));
            System.out.println("¿Proceso vivo al finalizar? " + procesoPing.isAlive());

        } catch (IOException excepcionES) {
            // Se produce si "ping" no existe en el sistema o falla la lectura de la salida
            System.err.println("Error de entrada/salida al ejecutar o leer el proceso: "
                    + excepcionES.getMessage());

        } catch (InterruptedException excepcionInterrupcion) {
            // Se produce si el hilo es interrumpido mientras espera en waitFor()
            System.err.println("La espera del proceso fue interrumpida: "
                    + excepcionInterrupcion.getMessage());
            // Buena práctica: restaurar el estado de interrupción del hilo
            Thread.currentThread().interrupt();
            // Evitamos dejar el proceso huérfano
            if (procesoPing != null) {
                procesoPing.destroy();
            }

        } catch (SecurityException excepcionSeguridad) {
            // Se produce si el gestor de seguridad no permite crear procesos
            System.err.println("Permisos insuficientes para lanzar el proceso: "
                    + excepcionSeguridad.getMessage());
        }
    }

    /**
     * Muestra PID, comando, argumentos y usuario del proceso indicado.
     * Algunos datos pueden no estar disponibles según el sistema operativo,
     * por eso se usan Optional con valor por defecto.
     *
     * @param proceso proceso del que se quiere mostrar la información
     */
    private static void mostrarInformacionProceso(Process proceso) {
        ProcessHandle.Info informacionProceso = proceso.info();

        Optional<String> comando = informacionProceso.command();
        Optional<String[]> argumentos = informacionProceso.arguments();
        Optional<String> usuario = informacionProceso.user();

        System.out.println("\n--- Información del proceso ---");
        System.out.println("PID: " + proceso.pid());
        System.out.println("Comando: " + comando.orElse("no disponible"));
        System.out.println("Argumentos: "
                + (argumentos.isPresent() ? String.join(" ", argumentos.get()) : "no disponibles"));
        System.out.println("Usuario: " + usuario.orElse("no disponible"));
    }
}