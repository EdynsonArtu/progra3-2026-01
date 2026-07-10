package ejercicio1;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        int n = 10;
        List<HiloDemostrativo> hilos = new ArrayList<>();
        // crear lista de hilos
        for(int i = 1; i <= n; i++) {
            HiloDemostrativo hilo = new HiloDemostrativo();
            hilo.setName("Hilo " + i);
            hilos.add(hilo);
        }
        // ahora lanzamos los hilos
        for(HiloDemostrativo hilo: hilos) {
            hilo.start();
        }

        System.out.println("Hilo Principal");
    }
}
