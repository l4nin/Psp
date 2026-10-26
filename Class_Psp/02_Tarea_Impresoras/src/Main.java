public class Main {
    public static void main(String[] args) {
        // Cada tarea tiene su propio hilo para que las impresiones se ejecuten en paralelo.
        Thread impresoraA = new Thread(new Impresora("Impresora A", 3));
        Thread impresoraB = new Thread(new Impresora("Impresora B", 5));
        Thread impresoraC = new Thread(new Impresora("Impresora C", 4));

        impresoraA.start();
        impresoraB.start();
        impresoraC.start();
    }
}
