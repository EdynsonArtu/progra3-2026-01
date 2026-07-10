package ejercicio3;

/**
 * Buffer compartido entre productores y consumidores.
 *
 * - El productor llama a producir() para depositar un nuevo id.
 *   Si ya hay un valor sin consumir, espera (wait) hasta que el
 *   consumidor lo retire.
 *
 * - El consumidor llama a consumir() para retirar el id.
 *   Si no hay ningún valor disponible, espera (wait) hasta que el
 *   productor deposite uno.
 *
 * Este mecanismo garantiza que cada valor producido es leído
 * exactamente una vez y que nunca se lee el mismo valor dos veces.
 */
public class Identificador {

    private static int id = 0;

    /** true  → hay un valor producido esperando ser consumido  */
    private static boolean disponible = false;

    // ------------------------------------------------------------------ //
    //  PRODUCTOR: genera un nuevo id y lo deposita en el buffer          //
    // ------------------------------------------------------------------ //
    public synchronized static void producir() {
        // Mientras haya un valor sin consumir, el productor espera
        while (disponible) {
            try {
                Identificador.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        id++;
        disponible = true;
        System.out.println(id + ": producido por -> " + Thread.currentThread().getName());

        // Avisa a todos los hilos que esperan (consumidores)
        Identificador.class.notifyAll();
    }

    // ------------------------------------------------------------------ //
    //  CONSUMIDOR: retira el id disponible del buffer                    //
    // ------------------------------------------------------------------ //
    public synchronized static int consumir() {
        // Mientras NO haya un valor disponible, el consumidor espera
        while (!disponible) {
            try {
                Identificador.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            }
        }

        disponible = false;
        int valorConsumido = id;
        System.out.println(Thread.currentThread().getName()
                + " consumio identificador: " + valorConsumido);

        // Avisa a todos los hilos que esperan (productores)
        Identificador.class.notifyAll();
        return valorConsumido;
    }

    // ------------------------------------------------------------------ //
    //  Lectura segura del id actual (solo para el resumen final)         //
    // ------------------------------------------------------------------ //
    public synchronized static int obtenerId() {
        return id;
    }
}