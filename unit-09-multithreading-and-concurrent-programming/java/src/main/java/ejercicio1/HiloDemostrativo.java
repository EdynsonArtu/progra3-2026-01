package ejercicio1;


import java.util.Random;

public class HiloDemostrativo extends Thread {
    private static final long MILLIS = 1000L;
    private static final int MAX_SECONDS = 5;

    @Override
    public void run() {
        System.out.println("Iniciando Hilo " + getName());
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(MAX_SECONDS) * MILLIS  + MILLIS);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return;
        }
        
        System.out.println("Hilo '" + getName() + "'");
    }
}
