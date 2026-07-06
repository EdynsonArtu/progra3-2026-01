using System.Threading;

namespace Ejercicio3;

public class ConsumidorID
{
    public void Run()
    {
        string nombreConsumidor = Thread.CurrentThread.Name ?? "Consumidor";
        for (int i = 0; i < 10; i++)
        {
            Identificador.ImprimirIdActualConsumido(nombreConsumidor);
        }
    }
}
