public class Main {
    public static void main(String[] args) {
        Thread hiloCliente = new Thread(new Cliente(), "Cliente");

        System.out.println("Estado del cliente antes de iniciar: " + hiloCliente.getState());
        hiloCliente.start();

        try {
            hiloCliente.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main interrumpido mientras esperaba al cliente.");
        }

        System.out.println("Estado del cliente tras finalizar: " + hiloCliente.getState());
    }
}