using System;
using System.Threading;

namespace Ejercicio3;

public static class Identificador
{
    private static readonly object lockObject = new object();
    private static bool disponible = false;

    public static int Id { get; set; }

    public static void Incrementar()
    {
        lock (lockObject)
        {
            while (disponible)
            {
                Monitor.Wait(lockObject);
            }
            Id++;
            Console.WriteLine($"{Id}: producido por -> {Thread.CurrentThread.Name}");
            disponible = true;
            Monitor.PulseAll(lockObject);
        }
    }

    public static int ObtenerId()
    {
        lock (lockObject)
        {
            return Id;
        }
    }

    public static void ImprimirIdActualConsumido(string nombreConsumidor)
    {
        lock (lockObject)
        {
            while (!disponible)
            {
                Monitor.Wait(lockObject);
            }
            Console.WriteLine($"{nombreConsumidor} leyo identificador actual: {Id}");
            disponible = false;
            Monitor.PulseAll(lockObject);
        }
    }
}
