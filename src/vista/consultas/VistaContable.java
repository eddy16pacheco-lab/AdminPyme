/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.consultas;

import com.toedter.calendar.JDateChooser;
import consultas_bd.consulta_contabilidad;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import validaciones.validaciones.otros.validar_componente;

public class VistaContable extends javax.swing.JInternalFrame {
    private validar_componente val = new validar_componente();
    // Colores del sistema (públicos)
    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);
    private consulta_contabilidad sql = new consulta_contabilidad();

    // Componentes principales (públicos)
    public JPanel contentPane;
    public JTabbedPane tabbedPane;

    // Paneles para cada pestaña (públicos)
    public JPanel libroDiarioPanel;
    public JPanel balanceGeneralPanel;
    public JPanel estadoResultadosPanel;
    public JPanel reportesPanel;
    public JPanel movimientosCuentaPanel; // Nueva pestaña

    // Componentes del Dashboard (públicos)
    public JLabel ventasMesLabel;
    public JLabel totalAsientosLabel;
    public JLabel saldoActualLabel = new JLabel();
    public JLabel trendVentasLabel;
    public JLabel trendAsientosLabel;
    public JLabel trendSaldoLabel = new JLabel();

    // Componentes del Libro Diario (públicos)
    public JTable journalTable;
    public JDateChooser fechaDesdeField;
    public JDateChooser fechaHastaField;
    public JComboBox<String> tipoAsientoCombo;
    public JButton filtrarButton;
    public JButton exportarExcelButton;

    // Componentes del Balance General (públicos)
    public JTextArea balanceTextArea;
    public JDateChooser balanceDateField;
    public JButton generarBalanceButton;
    public JButton imprimirBalanceButton;

    // Componentes del Estado de Resultados (públicos)
    public JTextArea incomeStatementTextArea;
    public JTextField periodoDesdeField;
    public JTextField periodoHastaField;
    public JButton generarEstadoButton;

    // Componentes de Reportes (públicos)
    public JTextArea reportOutputTextArea;
    public JButton reporteVentasButton;
    public JButton reporteComprasButton;
    public JButton reporteGastosButton;
    public JButton libroMayorButton;
    public JButton reportePagos;

    // Componentes de Movimientos de Cuenta (nuevos - integrados desde VistaMovimientoCuentaMejorada)
    public JLabel jLabel_infor_ingresos;
    public JLabel jLabel_info_transacciones;
    public JLabel jLabel_info_gastos;
    public JLabel jLabel_info_monto;
    public JLabel jLabel_numero_ingreso;
    public JTable jTable_transacciones;
    public JButton jButton_filtrar;
    public JButton jButton_buscar;
    public JDateChooser jDateChooser_fecha_inicio;
    public JDateChooser jDateChooser_fecha_final;

    // Formatos (públicos)
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    // Variables para datos
    private double dolar = val.dolar.getDollarRate();
    private double fondos = sql.saldo_fondos();
    private double ingresos = sql.ingresos_mensuales();
    private double egreso = sql.egresos_mensuales();
    private String ingresos_title = "BS " +validar_componente.limitar_decimales(ingresos)  + " | $ " + (validar_componente.limitar_decimales(ingresos / dolar));
    private String egresos_title = "BS " +validar_componente.limitar_decimales(egreso)  + " | $ " + (validar_componente.limitar_decimales(egreso / dolar));
    private String saldo = "BS " +validar_componente.limitar_decimales(fondos) + " | $ " + (validar_componente.limitar_decimales(fondos / dolar));

    public VistaContable() {
        setTitle("Modulo Contable - Sistema de Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        // Crear panel principal con gradiente
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, 0, getHeight(), SECONDARY);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Crear header
        crearHeader();

        // Crear pestañas
        crearPestanas();

        // Cargar datos iniciales
        cargarDatosIniciales();
    }

    private void crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel titleLabel = new JLabel("📊 Modulo Contable");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(20, 20, 5, 20));

        JLabel subtitleLabel = new JLabel("Gestión integral de la contabilidad del sistema de ventas");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 20, 20, 20));

        JLabel subtitleDolar = new JLabel("<html>Fecha: " + LocalDate.now().toString() + "<br>Tasa Dolar: " + dolar + "<html>");
        subtitleDolar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleDolar.setForeground(Color.WHITE);
        subtitleDolar.setBorder(new EmptyBorder(0, 20, 20, 20));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleDolar, BorderLayout.CENTER);

        contentPane.add(headerPanel, BorderLayout.NORTH);
    }

    private void crearPestanas() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(Color.WHITE);

        // Crear paneles para cada pestaña
        movimientosCuentaPanel = crearPanelMovimientosCuenta(); // Nueva pestaña integrada
        libroDiarioPanel = crearPanelLibroDiario();
        balanceGeneralPanel = crearPanelBalanceGeneral();
        estadoResultadosPanel = crearPanelEstadoResultados();
    //    reportesPanel = crearPanelReportes();

        // Agregar pestañas
        tabbedPane.addTab("Movimientos de Cuenta", movimientosCuentaPanel); // Nueva pestaña
        tabbedPane.addTab("Libro Diario", libroDiarioPanel);
        tabbedPane.addTab("Balance General", balanceGeneralPanel);
        //tabbedPane.addTab("Estado de Resultados", estadoResultadosPanel);
      //  tabbedPane.addTab("Reportes", reportesPanel);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }

    // Método para crear la nueva pestaña de Movimientos de Cuenta (integrada)
    private JPanel crearPanelMovimientosCuenta() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel superior con estadísticas
        JPanel panelEstadisticas = crearPanelEstadisticasMovimientos();
        panel.add(panelEstadisticas, BorderLayout.NORTH);

        // Tabla de transacciones en el centro
        JScrollPane scrollPaneTabla = crearTablaTransaccionesMovimientos();
        panel.add(scrollPaneTabla, BorderLayout.CENTER);

        // Panel de filtros en la parte inferior
        JPanel panelFiltros = crearPanelFiltrosMovimientos();
        panel.add(panelFiltros, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadisticasMovimientos() {
        JPanel panelContenedor = new JPanel(new GridLayout(1, 4, 15, 0));
        panelContenedor.setBackground(LIGHT);
        panelContenedor.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Panel de Ingresos
        JPanel panelIngresos = crearPanelEstadisticaMovimientos(
                SUCCESS,
                "Total Ingresos",
                "Ingresos Mensuales",
                "" + ingresos_title + "",
                ""
        );
        jLabel_infor_ingresos = (JLabel) ((BorderLayout) panelIngresos.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        jLabel_numero_ingreso = (JLabel) ((BorderLayout) panelIngresos.getLayout()).getLayoutComponent(BorderLayout.SOUTH);

        // Panel de Transacciones
        JPanel panelTransacciones = crearPanelEstadisticaMovimientos(
                SECONDARY,
                "Total Transacciones",
                "Transacciones Mensuales",
                "128",
                null
        );
        jLabel_info_transacciones = (JLabel) ((BorderLayout) panelTransacciones.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Panel de Gastos
        JPanel panelGastos = crearPanelEstadisticaMovimientos(
                DANGER,
                "Total Egresos",
                "Egresos+",
                "" + egresos_title + "",
                null
        );
        jLabel_info_gastos = (JLabel) ((BorderLayout) panelGastos.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Panel de Saldo
        JPanel panelSaldo = crearPanelEstadisticaMovimientos(
                WARNING,
                "Saldo Total",
                "Saldo Actual",
                "" + saldo + "",
                null
        );
        jLabel_info_monto = (JLabel) ((BorderLayout) panelSaldo.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        panelContenedor.add(panelIngresos);
        panelContenedor.add(panelTransacciones);
        panelContenedor.add(panelGastos);
        panelContenedor.add(panelSaldo);

        return panelContenedor;
    }

    private JPanel crearPanelEstadisticaMovimientos(Color color, String titulo, String subtitulo, String valor, String pie) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createMatteBorder(3, 3, 3, 3, color),
                titulo,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 16),
                Color.WHITE
        ));

        // Subtítulo
        JLabel labelSubtitulo = new JLabel(subtitulo, SwingConstants.CENTER);
        labelSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelSubtitulo.setForeground(Color.WHITE);
        panel.add(labelSubtitulo, BorderLayout.NORTH);

        // Valor principal
        JLabel labelValor = new JLabel(valor, SwingConstants.CENTER);
        labelValor.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelValor.setForeground(Color.WHITE);
        panel.add(labelValor, BorderLayout.CENTER);

        // Texto pie (opcional)
        if (pie != null) {
            JLabel labelPie = new JLabel(pie, SwingConstants.CENTER);
            labelPie.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            labelPie.setForeground(Color.WHITE);
            panel.add(labelPie, BorderLayout.SOUTH);
        }

        return panel;
    }

    private JScrollPane crearTablaTransaccionesMovimientos() {
        // Modelo de datos de ejemplo
        Object[][] data = {
            {"2024-01-15", "Compra en Supermercado", "Débito", "Alimentos", "$ 150.00", "Completado"},
            {"2024-01-14", "Depósito de Nómina", "Crédito", "Ingresos", "$ 2,500.00", "Completado"},
            {"2024-01-13", "Pago de Servicios", "Débito", "Servicios", "$ 85.00", "Completado"},
            {"2024-01-12", "Transferencia Recibida", "Crédito", "Transferencia", "$ 500.00", "Completado"},
            {"2024-01-11", "Retiro Cajero", "Débito", "Efectivo", "$ 200.00", "Completado"},
            {"2024-01-10", "Pago de Tarjeta", "Débito", "Crédito", "$ 350.00", "Completado"}
        };

        String[] columnNames = {
            "Fecha", "Descripción", "Tipo Transacción", "Categoría", "Monto", "Estado"
        };

        jTable_transacciones = new JTable(data, columnNames) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        jTable_transacciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable_transacciones.setRowHeight(35);
        jTable_transacciones.getTableHeader().setReorderingAllowed(false);

        // Aplicar estilos a la tabla
        jTable_transacciones.setSelectionBackground(SECONDARY);
        jTable_transacciones.setSelectionForeground(Color.WHITE);
        jTable_transacciones.setGridColor(LIGHT);
        jTable_transacciones.getTableHeader().setBackground(PRIMARY);
        jTable_transacciones.getTableHeader().setForeground(Color.WHITE);
        jTable_transacciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(jTable_transacciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        return scrollPane;
    }

    private JPanel crearPanelFiltrosMovimientos() {
        JPanel panelFiltros = new JPanel(new BorderLayout());
        panelFiltros.setBackground(LIGHT);
        panelFiltros.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Título
        JLabel labelTitulo = new JLabel("Filtrar Transacciones");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelTitulo.setForeground(PRIMARY);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelFiltros.add(labelTitulo, BorderLayout.NORTH);

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelControles.setBackground(LIGHT);

        // Fecha inicio
        JLabel labelFechaInicio = new JLabel("Fecha Inicio:");
        labelFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelControles.add(labelFechaInicio);

        jDateChooser_fecha_inicio = new JDateChooser();
        jDateChooser_fecha_inicio.setPreferredSize(new Dimension(150, 35));
        jDateChooser_fecha_inicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelControles.add(jDateChooser_fecha_inicio);

        // Fecha final
        JLabel labelFechaFinal = new JLabel("Fecha Final:");
        labelFechaFinal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelControles.add(labelFechaFinal);

        jDateChooser_fecha_final = new JDateChooser();
        jDateChooser_fecha_final.setPreferredSize(new Dimension(150, 35));
        jDateChooser_fecha_final.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelControles.add(jDateChooser_fecha_final);

        // Botones
        jButton_filtrar = new JButton("Filtrar");
        configurarBotonMovimientos(jButton_filtrar, SECONDARY);
        panelControles.add(jButton_filtrar);

        jButton_buscar = new JButton("Buscar");
        configurarBotonMovimientos(jButton_buscar, SUCCESS);
        panelControles.add(jButton_buscar);

        panelFiltros.add(panelControles, BorderLayout.CENTER);

        return panelFiltros;
    }

    private void configurarBotonMovimientos(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFondo.darker(), 2),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
    }

    // Métodos para actualizar los datos de movimientos de cuenta
    public void actualizarIngresos(String monto, String numeroTransacciones) {
        jLabel_infor_ingresos.setText(monto);
        jLabel_numero_ingreso.setText(numeroTransacciones + " transacciones");
    }

    public void actualizarTransacciones(String numero) {
        jLabel_info_transacciones.setText(numero);
    }

    public void actualizarGastos(String monto) {
        jLabel_info_gastos.setText(monto);
    }

    public void actualizarSaldo(String monto) {
        jLabel_info_monto.setText(monto);
    }

    // Los métodos restantes se mantienen igual...
    private JPanel crearPanelLibroDiario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel de filtros
        JPanel filtersPanel = crearFiltrosLibroDiario();
        panel.add(filtersPanel, BorderLayout.NORTH);

        // Panel de tabla vacía
        JPanel tablePanel = crearTablaLibroDiario();
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearFiltrosLibroDiario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Fecha Desde
        JLabel desdeLabel = new JLabel("Desde:");
        fechaDesdeField = new JDateChooser();

        // Fecha Hasta
        JLabel hastaLabel = new JLabel("Hasta:");
        fechaHastaField = new JDateChooser();

        // Botones
        filtrarButton = new JButton("Filtrar");
        filtrarButton.setBackground(SECONDARY);
        filtrarButton.setForeground(Color.WHITE);

        exportarExcelButton = new JButton("Exportar Excel");
        exportarExcelButton.setBackground(SUCCESS);
        exportarExcelButton.setForeground(Color.WHITE);

        panel.add(desdeLabel);
        panel.add(fechaDesdeField);
        panel.add(hastaLabel);
        panel.add(fechaHastaField);
        panel.add(filtrarButton);
        panel.add(exportarExcelButton);

        return panel;
    }

    private JPanel crearTablaLibroDiario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("Libro Diario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);

        // Tabla vacía
        String[] columnNames = {"Número", "Fecha", "Concepto", "Debe", "Haber"};
        Object[][] data = {}; // Tabla vacía

        journalTable = new JTable(data, columnNames);
        journalTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        journalTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(journalTable);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBalanceGeneral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel de filtros
        JPanel filtersPanel = crearFiltrosBalanceGeneral();
        panel.add(filtersPanel, BorderLayout.NORTH);

        // Panel de contenido vacío
        JPanel contentPanel = crearContenidoBalanceGeneral();
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearFiltrosBalanceGeneral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel fechaLabel = new JLabel("Fecha de Corte:");
        balanceDateField = new JDateChooser();

        generarBalanceButton = new JButton("Generar Reporte");
        generarBalanceButton.setBackground(SECONDARY);
        generarBalanceButton.setForeground(Color.WHITE);

        imprimirBalanceButton = new JButton("Imprimir");
        imprimirBalanceButton.setBackground(SUCCESS);
        imprimirBalanceButton.setForeground(Color.WHITE);

        panel.add(fechaLabel);
        panel.add(balanceDateField);
        panel.add(generarBalanceButton);
        panel.add(imprimirBalanceButton);

        return panel;
    }

    private JPanel crearContenidoBalanceGeneral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("Balance General");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);

        balanceTextArea = new JTextArea();
        balanceTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        balanceTextArea.setText("No hay datos disponibles para mostrar el balance general.\nSeleccione una fecha y haga clic en 'Generar Reporte'.");
        balanceTextArea.setEditable(false);
        balanceTextArea.setBackground(LIGHT);
        balanceTextArea.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(balanceTextArea);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEstadoResultados() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel de filtros
        JPanel filtersPanel = crearFiltrosEstadoResultados();
        panel.add(filtersPanel, BorderLayout.NORTH);

        // Panel de contenido vacío
        JPanel contentPanel = crearContenidoEstadoResultados();
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearFiltrosEstadoResultados() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel desdeLabel = new JLabel("Desde:");
        periodoDesdeField = new JTextField(7);

        JLabel hastaLabel = new JLabel("Hasta:");
        periodoHastaField = new JTextField(7);

        generarEstadoButton = new JButton("Generar Reporte");
        generarEstadoButton.setBackground(SECONDARY);
        generarEstadoButton.setForeground(Color.WHITE);

        panel.add(desdeLabel);
        panel.add(periodoDesdeField);
        panel.add(hastaLabel);
        panel.add(periodoHastaField);
        panel.add(generarEstadoButton);

        return panel;
    }

    private JPanel crearContenidoEstadoResultados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("Estado de Resultados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);

        incomeStatementTextArea = new JTextArea();
        incomeStatementTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        incomeStatementTextArea.setText("No hay datos disponibles para mostrar el estado de resultados.\nSeleccione un período y haga clic en 'Generar Reporte'.");
        incomeStatementTextArea.setEditable(false);
        incomeStatementTextArea.setBackground(LIGHT);
        incomeStatementTextArea.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(incomeStatementTextArea);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel de botones de reportes
        JPanel reportsPanel = crearBotonesReportes();
        panel.add(reportsPanel, BorderLayout.NORTH);

        // Panel de salida de reportes vacío
        JPanel outputPanel = crearSalidaReportes();
        panel.add(outputPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearBotonesReportes() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        reporteVentasButton = new JButton("Reporte de Ventas");
        reporteComprasButton = new JButton("Reporte de Compras");
        reporteGastosButton = new JButton("Reporte de Gastos");
        reportePagos = new JButton("Reporte de Pagos");
        libroMayorButton = new JButton("Libro Mayor");

        // Configurar colores de botones
        reporteVentasButton.setBackground(SECONDARY);
        reporteComprasButton.setBackground(SECONDARY);
        reporteGastosButton.setBackground(SECONDARY);
        reportePagos.setBackground(SECONDARY);
        libroMayorButton.setBackground(SECONDARY);

        reporteVentasButton.setForeground(Color.WHITE);
        reporteComprasButton.setForeground(Color.WHITE);
        reporteGastosButton.setForeground(Color.WHITE);
        reportePagos.setForeground(Color.WHITE);
        libroMayorButton.setForeground(Color.WHITE);

        panel.add(reporteVentasButton);
        panel.add(reporteComprasButton);
        panel.add(reporteGastosButton);
        panel.add(reportePagos);
        panel.add(libroMayorButton);

        return panel;
    }

    private JPanel crearSalidaReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("Reporte Generado");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);

        reportOutputTextArea = new JTextArea("Seleccione un reporte para generar");
        reportOutputTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reportOutputTextArea.setEditable(false);
        reportOutputTextArea.setBackground(LIGHT);
        reportOutputTextArea.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(reportOutputTextArea);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void cargarDatosIniciales() {
        // Establecer fechas por defecto (vacías)
        Date today = new Date();
        fechaDesdeField.setToolTipText(dateFormat.format(today));
        fechaHastaField.setToolTipText(dateFormat.format(today));
        balanceDateField.setToolTipText(dateFormat.format(today));
        periodoDesdeField.setText(monthFormat.format(today));
        periodoHastaField.setText(monthFormat.format(today));
    }
}