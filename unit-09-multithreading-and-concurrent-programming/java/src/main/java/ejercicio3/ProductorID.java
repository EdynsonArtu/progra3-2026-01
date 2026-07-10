package ejercicio3;


public class ProductorID implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Identificador.producir();   // deposita un nuevo id en el buffer
        }
    }
}
