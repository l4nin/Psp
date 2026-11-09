import java.util.Random;

public class Vendedor implements Runnable {
    private final String nombre;
    private int diasEntrega;

    public Vendedor(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            // Simula el tiempo necesario para consultar el plazo
            Thread.sleep(500);
            // Número aleatorio entre 1 y 7 (ambos incluidos)
            diasEntrega = new Random().nextInt(7) + 1;
            System.out.println(nombre + " responde: " + diasEntrega + " días");
        } catch (InterruptedException e) {
            // Se restaura la señal de interrupción
            Thread.currentThread().interrupt();
            System.out.println(nombre + " ha sido interrumpido.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getDiasEntrega() {
        return diasEntrega;
    }
}