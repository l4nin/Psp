public class CuentaAtras implements Runnable {
    // Definicion de variables
    private int cuentaAtras;

    private static int contadorHilos =0;

    private final int idHilo = contadorHilos;

    public CuentaAtras(int cuentaAtras) {
        this.cuentaAtras = cuentaAtras;
        contadorHilos++;
    }

    @Override
    public void run() {
        // contador hacia atras
        while (cuentaAtras > 0){
            System.out.println("Hilo: "+idHilo+" : "+cuentaAtras);
            cuentaAtras--;
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
