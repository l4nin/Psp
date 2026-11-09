import java.util.ArrayList;
import java.util.List;

public class Cliente implements Runnable {

    @Override
    public void run() {
        ThreadGroup grupo = new ThreadGroup("Vendedores");
        List<Vendedor> vendedores = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Vendedor v = new Vendedor("Vendedor " + i);
            vendedores.add(v);
            hilos.add(new Thread(grupo, v, "Hilo-" + v.getNombre()));
        }

        // Se inician todos antes de esperar: consultas en paralelo
        for (Thread h : hilos) {
            h.start();
        }

        // Se espera a que terminen todos antes de comparar
        try {
            for (Thread h : hilos) {
                h.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Cliente interrumpido mientras esperaba.");
            return;
        }

        // Selección: menor plazo; en empate, el primero de la lista
        Vendedor mejor = vendedores.get(0);
        for (Vendedor v : vendedores) {
            if (v.getDiasEntrega() < mejor.getDiasEntrega()) {
                mejor = v;
            }
        }

        System.out.println("\nResumen de ofertas:");
        for (Vendedor v : vendedores) {
            System.out.println(" - " + v.getNombre() + ": " + v.getDiasEntrega() + " días");
        }
        System.out.println("Vendedor elegido: " + mejor.getNombre()
                + " (" + mejor.getDiasEntrega() + " días)");
    }
}