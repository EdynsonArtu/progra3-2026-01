package ejercicio3;


public class ConsumidorID implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Identificador.consumir();   // espera un valor y lo retira del buffer
        }
    }
}
