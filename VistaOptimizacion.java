import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VistaOptimizacion extends JFrame {

    // Campos de entrada
    private JTextField txtIteraciones, txtA, txtB, txtTDP;

    // Etiquetas de resultados
    private JTextArea areaLog;
    private JLabel lblTiempoOriginal, lblTiempoOptimizado, lblAhorroTiempo;
    private JLabel lblEnergiaOriginal, lblEnergiaOptimizado, lblCalentamiento;

    private JButton btnEjecutar, btnRestaurar;

    public VistaOptimizacion() {
        setTitle("Analizador de Optimización y Cómputo Verde");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // === PANEL SUPERIOR: TÍTULO ===
        JLabel lblTitulo = new JLabel("Optimización de Software y Eficiencia Energética", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // === PANEL DE CONFIGURACIÓN (Entrada de Datos) ===
        JPanel panelConfig = new JPanel(new GridLayout(2, 4, 10, 10));
        panelConfig.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Parámetros de Entrada", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        txtIteraciones = new JTextField("10000000");
        txtA = new JTextField("5");
        txtB = new JTextField("3");
        txtTDP = new JTextField("65");

        panelConfig.add(new JLabel("Iteraciones (número de ciclos):"));
        panelConfig.add(new JLabel("Valor A:"));
        panelConfig.add(new JLabel("Valor B:"));
        panelConfig.add(new JLabel("TDP del CPU (Watts):"));

        panelConfig.add(txtIteraciones);
        panelConfig.add(txtA);
        panelConfig.add(txtB);
        panelConfig.add(txtTDP);

        // === PANEL CENTRAL: MÉTRICAS Y LOG ===
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

        // Panel de Métricas
        JPanel panelMetricas = new JPanel(new GridLayout(7, 1, 5, 5));
        panelMetricas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Métricas de Rendimiento", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        lblTiempoOriginal = new JLabel("Tiempo Original: -");
        lblTiempoOptimizado = new JLabel("Tiempo Optimizado: -");
        lblAhorroTiempo = new JLabel("Ahorro de Tiempo: -");
        lblEnergiaOriginal = new JLabel("Energía Original: -");
        lblEnergiaOptimizado = new JLabel("Energía Optimizada: -");
        lblCalentamiento = new JLabel("Reducción Térmica: -");

        panelMetricas.add(lblTiempoOriginal);
        panelMetricas.add(lblTiempoOptimizado);
        panelMetricas.add(lblAhorroTiempo);
        panelMetricas.add(lblEnergiaOriginal);
        panelMetricas.add(lblEnergiaOptimizado);
        panelMetricas.add(lblCalentamiento);

        // Panel de Log
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Consola de Análisis", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        panelCentro.add(panelMetricas);
        panelCentro.add(scrollLog);

        // === PANEL INFERIOR: BOTONES ===
        btnEjecutar = new JButton("▶ Ejecutar Comparación");
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEjecutar.setBackground(new Color(46, 204, 113));
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setFocusPainted(false);

        btnRestaurar = new JButton("↺ Restaurar Valores por Defecto");
        btnRestaurar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRestaurar.setBackground(new Color(189, 195, 199));
        btnRestaurar.setFocusPainted(false);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRestaurar);
        panelBoton.add(btnEjecutar);

        // === ENSAMBLADO FINAL ===
        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelConfig, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        // === EVENTOS ===
        btnEjecutar.addActionListener(e -> iniciarAnalisis());
        btnRestaurar.addActionListener(e -> restaurarValores());
    }

    private void restaurarValores() {
        txtIteraciones.setText("10000000");
        txtA.setText("5");
        txtB.setText("3");
        txtTDP.setText("65");
        areaLog.setText("");
        limpiarMetricas();
    }

    private void limpiarMetricas() {
        lblTiempoOriginal.setText("Tiempo Original: -");
        lblTiempoOptimizado.setText("Tiempo Optimizado: -");
        lblAhorroTiempo.setText("Ahorro de Tiempo: -");
        lblEnergiaOriginal.setText("Energía Original: -");
        lblEnergiaOptimizado.setText("Energía Optimizada: -");
        lblCalentamiento.setText("Reducción Térmica: -");
    }

    private void iniciarAnalisis() {
        // Validar entradas
        int iteraciones, a, b;
        double tdp;
        try {
            iteraciones = Integer.parseInt(txtIteraciones.getText().trim());
            a = Integer.parseInt(txtA.getText().trim());
            b = Integer.parseInt(txtB.getText().trim());
            tdp = Double.parseDouble(txtTDP.getText().trim());

            if (iteraciones <= 0) throw new NumberFormatException("Las iteraciones deben ser > 0");
            if (tdp <= 0) throw new NumberFormatException("El TDP debe ser > 0");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Error en los datos ingresados:\n" + ex.getMessage() +
                "\n\nVerifique que todos los campos contengan números válidos.",
                "Datos Inválidos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Deshabilitar botón mientras se ejecuta
        btnEjecutar.setEnabled(false);
        btnEjecutar.setText("⏳ Ejecutando...");
        areaLog.setText("Iniciando análisis en segundo plano...\n");
        areaLog.append("Iteraciones: " + iteraciones + "\n");
        areaLog.append("A = " + a + ", B = " + b + ", TDP = " + tdp + " W\n");
        areaLog.append("--------------------------------------------------\n");
        limpiarMetricas();

        // Ejecutar en segundo plano con SwingWorker
        SwingWorker<LogicaOptimizacion.ResultadoPrueba, Void> worker = new SwingWorker<>() {
            @Override
            protected LogicaOptimizacion.ResultadoPrueba doInBackground() {
                return LogicaOptimizacion.ejecutarPruebas(iteraciones, a, b);
            }

            @Override
            protected void done() {
                try {
                    LogicaOptimizacion.ResultadoPrueba res = get();
                    actualizarInterfaz(res, tdp);
                } catch (Exception ex) {
                    areaLog.append("Error durante la ejecución: " + ex.getMessage() + "\n");
                } finally {
                    btnEjecutar.setEnabled(true);
                    btnEjecutar.setText("▶ Ejecutar Comparación");
                }
            }
        };
        worker.execute();
    }

    private void actualizarInterfaz(LogicaOptimizacion.ResultadoPrueba res, double tdp) {
        areaLog.append(res.log);

        double tOrig = res.tiempoOriginalMs;
        double tOpt = res.tiempoOptimizadoMs;
        double ahorroMs = tOrig - tOpt;
        double porcentajeAhorro = (tOrig == 0) ? 0 : (ahorroMs / tOrig) * 100;

        // Cálculos de Cómputo Verde
        double energiaOrigWh = CalculadoraVerde.calcularConsumoWh(tOrig, tdp);
        double energiaOptWh = CalculadoraVerde.calcularConsumoWh(tOpt, tdp);
        double reduccionTermica = CalculadoraVerde.calcularReduccionTermica(tOrig, tOpt);

        lblTiempoOriginal.setText(String.format("Tiempo Original: %.2f ms", tOrig));
        lblTiempoOptimizado.setText(String.format("Tiempo Optimizado: %.2f ms", tOpt));
        lblAhorroTiempo.setText(String.format("Ahorro de Tiempo: %.2f ms (%.2f%%)", ahorroMs, porcentajeAhorro));
        lblEnergiaOriginal.setText(String.format("Energía Original: %.8f Wh", energiaOrigWh));
        lblEnergiaOptimizado.setText(String.format("Energía Optimizada: %.8f Wh", energiaOptWh));
        lblCalentamiento.setText(String.format("Reducción Térmica: %.2f%%", reduccionTermica));

        areaLog.append("\n--- RESUMEN FINAL DE CÓMPUTO VERDE ---\n");
        areaLog.append(String.format("Ahorro total de energía: %.8f Wh\n", (energiaOrigWh - energiaOptWh)));
        areaLog.append(String.format("Reducción de calor: %.2f%% menos disipación térmica\n", reduccionTermica));
        areaLog.append(String.format("Verificación de equivalencia funcional: %s\n", 
            (res.sumaOriginal == res.sumaOptimizada) ? "✔ Correcta" : "✘ ERROR"));
    }
}