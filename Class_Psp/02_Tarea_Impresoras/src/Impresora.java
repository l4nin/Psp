public class Impresora implements Runnable {
    private final String nombre;
    private final int numeroPaginas;

    public Impresora(String nombre, int numeroPaginas) {
        this.nombre = nombre;
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public void run() {
        for (int paginaActual = 1; paginaActual <= numeroPaginas; paginaActual++) {
            System.out.println(nombre + " imprime la página " + paginaActual
                    + " de " + numeroPaginas);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                // Restablece el estado de interrupción y finaliza esta tarea.
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(nombre + " ha terminado de imprimir.");
    }
}
