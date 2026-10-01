public class LogicaOptimizacion {

    public static class ResultadoPrueba {
        public long tiempoOriginalMs;
        public long tiempoOptimizadoMs;
        public long sumaOriginal;
        public long sumaOptimizada;
        public String log;

        public ResultadoPrueba(long tOrig, long tOpt, long sOrig, long sOpt, String log) {
            this.tiempoOriginalMs = tOrig;
            this.tiempoOptimizadoMs = tOpt;
            this.sumaOriginal = sOrig;
            this.sumaOptimizada = sOpt;
            this.log = log;
        }
    }

    /**
     * Ejecuta las pruebas con los parámetros ingresados por el usuario
     */
    public static ResultadoPrueba ejecutarPruebas(int iteraciones, int a, int b) {
        StringBuilder log = new StringBuilder();
        long tiempoTotalOriginal = 0;
        long tiempoTotalOptimizado = 0;

        // === CASO 1: Eliminación de Subexpresiones Comunes (CSE) ===
        log.append(">> CASO 1: Eliminación de Subexpresiones Comunes (CSE)\n");
        log.append(String.format("   Parámetros: iteraciones=%d, a=%d, b=%d\n", iteraciones, a, b));

        long start1 = System.nanoTime();
        long sumaOrig1 = codigoOriginalCSE(iteraciones, a, b);
        long end1 = System.nanoTime();
        long tOrig1 = (end1 - start1) / 1_000_000;
        tiempoTotalOriginal += tOrig1;

        long start2 = System.nanoTime();
        long sumaOpt1 = codigoOptimizadoCSE(iteraciones, a, b);
        long end2 = System.nanoTime();
        long tOpt1 = (end2 - start2) / 1_000_000;
        tiempoTotalOptimizado += tOpt1;

        log.append(String.format("   Original: %d ms | Optimizado: %d ms\n", tOrig1, tOpt1));
        log.append(String.format("   Verificación de resultados (deben coincidir): %d == %d\n\n", sumaOrig1, sumaOpt1));

        // === CASO 2: Movimiento de Código Invariante en Bucles ===
        log.append(">> CASO 2: Invariancia de Bucles (Loop Invariant Code Motion)\n");
        log.append(String.format("   Parámetros: iteraciones=%d, a=%d, b=%d\n", iteraciones, a, b));

        long start3 = System.nanoTime();
        long sumaOrig2 = codigoOriginalBucle(iteraciones, a, b);
        long end3 = System.nanoTime();
        long tOrig2 = (end3 - start3) / 1_000_000;
        tiempoTotalOriginal += tOrig2;

        long start4 = System.nanoTime();
        long sumaOpt2 = codigoOptimizadoBucle(iteraciones, a, b);
        long end4 = System.nanoTime();
        long tOpt2 = (end4 - start4) / 1_000_000;
        tiempoTotalOptimizado += tOpt2;

        log.append(String.format("   Original: %d ms | Optimizado: %d ms\n", tOrig2, tOpt2));
        log.append(String.format("   Verificación de resultados (deben coincidir): %d == %d\n\n", sumaOrig2, sumaOpt2));

        log.append("Pruebas finalizadas exitosamente.\n");

        return new ResultadoPrueba(
            tiempoTotalOriginal, tiempoTotalOptimizado,
            sumaOrig1 + sumaOrig2, sumaOpt1 + sumaOpt2,
            log.toString()
        );
    }

    // ==========================================================
    // CASO 1: ELIMINACIÓN DE SUBEXPRESIONES COMUNES (CSE)
    // ==========================================================

    // ORIGINAL: Recalcula "a + b" dos veces en cada iteración
    private static long codigoOriginalCSE(int n, int a, int b) {
        long suma = 0;
        for (int i = 0; i < n; i++) {
            int t1 = a + b;      // Calculado
            int t2 = t1 * 2;
            int t3 = a + b;      // ¡RECALCULADO! Mismo valor que t1
            int t4 = t3 + 1;
            suma += t2 + t4;
        }
        return suma;
    }

    // OPTIMIZADO: Reutiliza el valor de "a + b" ya calculado
    private static long codigoOptimizadoCSE(int n, int a, int b) {
        long suma = 0;
        for (int i = 0; i < n; i++) {
            int t1 = a + b;      // Calculado una sola vez
            int t2 = t1 * 2;
            int t4 = t1 + 1;     // Reutiliza t1 (equivale a t3 + 1)
            suma += t2 + t4;
        }
        return suma;
    }

    // ==========================================================
    // CASO 2: MOVIMIENTO DE CÓDIGO INVARIANTE EN BUCLES
    // ==========================================================

    // ORIGINAL: Calcula "a + b" y sus derivados en CADA iteración
    private static long codigoOriginalBucle(int n, int a, int b) {
        long suma = 0;
        for (int i = 0; i < n; i++) {
            int t1 = a + b;      // Invariante (siempre da el mismo resultado)
            int t2 = t1 * 2;     // Invariante
            suma += t2 + i;
        }
        return suma;
    }

    // OPTIMIZADO: Los cálculos invariantes se mueven FUERA del bucle
    private static long codigoOptimizadoBucle(int n, int a, int b) {
        long suma = 0;
        int t1 = a + b;          // Calculado una sola vez
        int t2 = t1 * 2;         // Calculado una sola vez
        for (int i = 0; i < n; i++) {
            suma += t2 + i;
        }
        return suma;
    }
}