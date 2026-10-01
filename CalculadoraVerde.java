public class CalculadoraVerde {

    /**
     * Calcula el consumo estimado en Watts-hora (Wh)
     * @param tiempoMs Tiempo de ejecución en milisegundos
     * @param tdpWatts Potencia del procesador ingresada por el usuario
     * @return Consumo en Wh
     */
    public static double calcularConsumoWh(double tiempoMs, double tdpWatts) {
        double tiempoSegundos = tiempoMs / 1000.0;
        double energiaJoules = tdpWatts * tiempoSegundos;
        return energiaJoules / 3600.0; // 1 Wh = 3600 Joules
    }

    /**
     * Calcula la reducción térmica (calentamiento) basada en la mejora de tiempo
     */
    public static double calcularReduccionTermica(double tiempoOriginal, double tiempoOptimizado) {
        if (tiempoOriginal <= 0) return 0;
        return ((tiempoOriginal - tiempoOptimizado) / tiempoOriginal) * 100.0;
    }
}