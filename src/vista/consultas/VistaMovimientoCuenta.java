package vista.consultas;

import com.toedter.calendar.JDateChooser;
import consultas_bd.consulta_contabilidad;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import validaciones.validaciones.otros.validar_componente;

public class VistaMovimientoCuenta extends JPanel {
    validar_componente val=new validar_componente();
    consulta_contabilidad sql=new consulta_contabilidad();
    // Colores del sistema
    private final Color PRIMARY = new Color(44, 62, 80);
    private final Color SECONDARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color WARNING = new Color(243, 156, 18);
    private final Color LIGHT = new Color(236, 240, 241);
    private final Color DARK = new Color(52, 73, 94);

    // Componentes públicos para acceso externo
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
    private double dolar = val.dolar.getDollarRate();
    private double fondos = sql.saldo_fondos();
    private double ingresos = sql.ingresos_mensuales();
    private double egreso = sql.egresos_mensuales();
    private String ingresos_title="BS "+ingresos +" | $ "+(validar_componente.limitar_decimales(ingresos/ dolar));
    private String egresos_title="BS "+egreso +" | $ "+(validar_componente.limitar_decimales(egreso / dolar));
    private String saldo= "BS "+ fondos +" | $ "+(validar_componente.limitar_decimales(fondos / dolar));
   
    public VistaMovimientoCuenta () {
        initComponents();
        aplicarEstilos();
       sql.consulta_movimiento_cuenta(jTable_transacciones, null,null, true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(LIGHT);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel superior con estadísticas
        JPanel panelEstadisticas = crearPanelEstadisticas();
        add(panelEstadisticas, BorderLayout.NORTH);

        // Tabla de transacciones en el centro
        JScrollPane scrollPaneTabla = crearTablaTransacciones();
        add(scrollPaneTabla, BorderLayout.CENTER);

        // Panel de filtros en la parte inferior
        JPanel panelFiltros = crearPanelFiltros();
        add(panelFiltros, BorderLayout.SOUTH);
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panelContenedor = new JPanel(new GridLayout(1, 4, 15, 0));
        panelContenedor.setBackground(LIGHT);
        panelContenedor.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Panel de Ingresos
        JPanel panelIngresos = crearPanelEstadistica(
            SUCCESS, 
            "Total Ingresos", 
            "Ingresos Mensuales", 
            ""+ingresos_title+"", 
            "45 transacciones"
        );
        jLabel_infor_ingresos = (JLabel) ((BorderLayout) panelIngresos.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        jLabel_numero_ingreso = (JLabel) ((BorderLayout) panelIngresos.getLayout()).getLayoutComponent(BorderLayout.SOUTH);

        // Panel de Transacciones
        JPanel panelTransacciones = crearPanelEstadistica(
            SECONDARY, 
            "Total Transacciones", 
            "Transacciones Mensuales", 
            "128", 
            null
        );
        jLabel_info_transacciones = (JLabel) ((BorderLayout) panelTransacciones.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Panel de Gastos
        JPanel panelGastos = crearPanelEstadistica(
            DANGER, 
            "Total Egresos", 
            "Egresos+", 
            ""+egresos_title+"", 
            null
        );
        jLabel_info_gastos = (JLabel) ((BorderLayout) panelGastos.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Panel de Saldo
        JPanel panelSaldo = crearPanelEstadistica(
            WARNING, 
            "Saldo Total", 
            "Saldo Actual", 
            ""+saldo+"", 
            null
        );
        jLabel_info_monto = (JLabel) ((BorderLayout) panelSaldo.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        panelContenedor.add(panelIngresos);
        panelContenedor.add(panelTransacciones);
        panelContenedor.add(panelGastos);
        panelContenedor.add(panelSaldo);

        return panelContenedor;
    }

    private JPanel crearPanelEstadistica(Color color, String titulo, String subtitulo, String valor, String pie) {
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

    private JScrollPane crearTablaTransacciones() {
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
            boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        jTable_transacciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable_transacciones.setRowHeight(35);
        jTable_transacciones.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(jTable_transacciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        return scrollPane;
    }

    private JPanel crearPanelFiltros() {
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
        panelControles.add(jButton_filtrar);

        jButton_buscar = new JButton("Buscar");
        panelControles.add(jButton_buscar);

        panelFiltros.add(panelControles, BorderLayout.CENTER);

        return panelFiltros;
    }

    private void aplicarEstilos() {
        // Aplicar estilos a la tabla
        jTable_transacciones.setSelectionBackground(SECONDARY);
        jTable_transacciones.setSelectionForeground(Color.WHITE);
        jTable_transacciones.setGridColor(LIGHT);
        jTable_transacciones.getTableHeader().setBackground(PRIMARY);
        jTable_transacciones.getTableHeader().setForeground(Color.WHITE);
        jTable_transacciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Configurar botones
        configurarBoton(jButton_filtrar, SECONDARY);
        configurarBoton(jButton_buscar, SUCCESS);
    }

    private void configurarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 2),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
    }

    // Métodos para actualizar los datos
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
}