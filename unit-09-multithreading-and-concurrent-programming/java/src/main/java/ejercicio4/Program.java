package ejercicio4;


public class Program {
    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria();

        // Total retiros : 100+70+70+30+60+100+100+100+100+100+100+100+100 = 1,230
        Runnable retirar100 = new Retiro(cuenta, 100.00);
        Runnable retirar70  = new Retiro(cuenta, 70.00);
        Runnable retirar60  = new Retiro(cuenta, 60.00);
        Runnable retirar30  = new Retiro(cuenta, 30.00);

        // Saldo inicial 500 + total depositos (50+100+300+100+50+50+50+50) = 750
        // Total disponible: 500 + 750 = 1,250 >= 1,230  ✓
        Runnable depositar100 = new Deposito(cuenta, 100);
        Runnable depositar50  = new Deposito(cuenta, 50);
        Runnable depositar300 = new Deposito(cuenta, 300);

        Thread t1  = new Thread(retirar100, "Gerente General");
        Thread t2  = new Thread(retirar70,  "Director RRHH");
        Thread t3  = new Thread(retirar70,  "Director Ventas");
        Thread t4  = new Thread(retirar30,  "Asistente Administrativo 1");
        Thread t5  = new Thread(retirar60,  "Director Almacen");
        Thread t6  = new Thread(retirar100, "Asistente Administrativo 2");
        Thread t7  = new Thread(retirar100, "Director Sistemas");
        Thread t8  = new Thread(retirar100, "Asistente Administrativo 3");
        Thread t9  = new Thread(retirar100, "Asistente Administrativo 4");
        Thread t10 = new Thread(retirar100, "Asistente Administrativo 5");
        Thread t11 = new Thread(retirar100, "Asistente Administrativo 6");
        Thread t12 = new Thread(retirar100, "Asistente Administrativo 7");
        Thread t13 = new Thread(retirar100, "Asistente Administrativo 8");

        Thread t14 = new Thread(depositar50,  "Cliente 1");
        Thread t15 = new Thread(depositar100, "Cliente 2");
        Thread t16 = new Thread(depositar300, "Cliente 3");
        Thread t17 = new Thread(depositar100, "Cliente 4");
        Thread t18 = new Thread(depositar50,  "Cliente 5");
        Thread t19 = new Thread(depositar50,  "Cliente 6");
        Thread t20 = new Thread(depositar50,  "Cliente 7");
        Thread t21 = new Thread(depositar50,  "Cliente 8");  // extra para cubrir deficit

        // Primero arrancan los retiros; quedarán bloqueados si no hay saldo
        t1.start(); t2.start();  t3.start();  t4.start();  t5.start();
        t6.start(); t7.start();  t8.start();  t9.start();  t10.start();
        t11.start(); t12.start(); t13.start();

        // Esperar un momento para que los retiros se bloqueen antes de depositar
        try { Thread.sleep(2000); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }

        // Ahora arrancan los depósitos, que irán desbloqueando los retiros
        t14.start(); t15.start(); t16.start(); t17.start();
        t18.start(); t19.start(); t20.start(); t21.start();

        // Esperar a que TODOS los hilos terminen antes de salir del main
        Thread[] todos = { t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,
                           t14,t15,t16,t17,t18,t19,t20,t21 };
        for (Thread hilo : todos) {
            try {
                hilo.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Hilo principal interrumpido.");
                return;
            }
        }

        System.out.println("\nTodos los hilos finalizaron correctamente.");
        System.out.printf("Saldo final de la cuenta: %.2f%n", cuenta.getSaldo());
    }
}
