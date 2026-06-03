/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.gestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class GestionarNomina extends JInternalFrame {
    // Colores del sistema
    private final Color PRIMARY = new Color(44, 62, 80);
    private final Color SECONDARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color WARNING = new Color(243, 156, 18);
    private final Color LIGHT = new Color(236, 240, 241);
    private final Color DARK = new Color(52, 73, 94);
    
    // Componentes de la interfaz
    public JButton jButton_crear_nomina;
    public JComboBox<String> jComboBox_empleado;
    public JDateChooser jDateChooser_fecha_inicio;
    public JDateChooser jDateChooser_fecha_fin;
    public JMenuItem jMenuItem_consultar;
    public JMenuItem jMenuItem_editar;
    public JPopupMenu jPopupMenu1;
    public JTable jTable_lista_nomina;
    public JButton jButton_buscar;
    public JButton jButton_limpiar;

    public GestionarNomina() {
        initFrame();
        initComponents();
        setupLayout();
        initPopupMenu();
        setVisible(true);
    }

    private void initFrame() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestión de Nómina - Pagos de Empleados");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        // Inicializar todos los componentes
        jButton_crear_nomina = new JButton("➕ Crear Nuevo Pago");
        
        // Componentes para filtros
        jComboBox_empleado = new JComboBox<>();
        jDateChooser_fecha_inicio = new JDateChooser();
        jDateChooser_fecha_fin = new JDateChooser();
        jButton_buscar = new JButton("🔍 Buscar");
        jButton_limpiar = new JButton("Limpiar Filtros");
        
        // Configurar combobox de empleados
        configurarCombobox();
        
        // Configurar botones de búsqueda
        configurarBotonesBusqueda();
    }

    private void configurarBotonesBusqueda() {
        jButton_buscar.setBackground(SECONDARY);
        jButton_buscar.setForeground(Color.WHITE);
        jButton_buscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_buscar.setFocusPainted(false);
        
        jButton_limpiar.setBackground(LIGHT);
        jButton_limpiar.setForeground(DARK);
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_limpiar.setFocusPainted(false);
    }

    private void configurarCombobox() {
        jComboBox_empleado.setModel(new DefaultComboBoxModel<>(new String[]{
            "Todos los empleados", "Juan Pérez", "María García", "Carlos López", "Ana Rodríguez"
        }));
    }

    private void setupLayout() {
        // Usar un panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Agregar las secciones en orden
        mainPanel.add(crearHeader(), BorderLayout.NORTH);
        mainPanel.add(crearPanelFiltros(), BorderLayout.CENTER);
        mainPanel.add(crearPanelTabla(), BorderLayout.SOUTH);
        
        // Configurar el contenido de la ventana
        setContentPane(mainPanel);
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        headerPanel.setPreferredSize(new Dimension(0, 100));
        
        // Título
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY);
        
        JLabel titleLabel = new JLabel("💰 Gestión de Nómina");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Administración completa de pagos a empleados");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        // Botón de acción
        jButton_crear_nomina.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_crear_nomina.setBackground(SUCCESS);
        jButton_crear_nomina.setForeground(Color.WHITE);
        jButton_crear_nomina.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jButton_crear_nomina.setFocusPainted(false);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(jButton_crear_nomina, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JPanel crearPanelFiltros() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));
        mainPanel.setPreferredSize(new Dimension(0, 120));
        
        // Panel principal para los filtros
        JPanel filtrosPanel = new JPanel(new GridBagLayout());
        filtrosPanel.setBackground(Color.WHITE);
        filtrosPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        // Filtro por empleado
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblEmpleado = new JLabel("Buscar por Empleado:");
        lblEmpleado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        filtrosPanel.add(lblEmpleado, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        jComboBox_empleado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_empleado.setPreferredSize(new Dimension(200, 30));
        filtrosPanel.add(jComboBox_empleado, gbc);
        
        // Filtro por fecha desde
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblFechaDesde = new JLabel("Fecha Desde:");
        lblFechaDesde.setFont(new Font("Segoe UI", Font.BOLD, 12));
        filtrosPanel.add(lblFechaDesde, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        jDateChooser_fecha_inicio.setPreferredSize(new Dimension(150, 30));
        jDateChooser_fecha_inicio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filtrosPanel.add(jDateChooser_fecha_inicio, gbc);
        
        // Filtro por fecha hasta
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblFechaHasta = new JLabel("Fecha Hasta:");
        lblFechaHasta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        filtrosPanel.add(lblFechaHasta, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        jDateChooser_fecha_fin.setPreferredSize(new Dimension(150, 30));
        jDateChooser_fecha_fin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filtrosPanel.add(jDateChooser_fecha_fin, gbc);
        
        // Botones de acción
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        botonesPanel.setBackground(Color.WHITE);
        
//        jButton_limpiar.addActionListener(e -> limpiarFiltros());
        botonesPanel.add(jButton_limpiar);
        
        botonesPanel.add(jButton_buscar);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        filtrosPanel.add(botonesPanel, gbc);
        
        mainPanel.add(filtrosPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Pagos de Nómina"));
        
        // Configurar tabla con datos de ejemplo
        String[] columnNames = {
            "N° Pago", "Empleado", "Periodo", "Salario Bruto", 
            "Forma Pago", "Estado", "Referencia", "Fecha Pago"
        };
        
        Object[][] data = {
            {"001", "Juan Pérez", "Quincenal", "$1,500.00", "Transferencia", "Completado", "REF001", "15/03/2024"},
            {"002", "María García", "Mensual", "$2,800.00", "Efectivo", "Pendiente", "REF002", "30/03/2024"},
            {"003", "Carlos López", "Semanal", "$700.00", "Depósito", "Completado", "REF003", "22/03/2024"},
            {"004", "Ana Rodríguez", "Quincenal", "$1,600.00", "Cheque", "Anulado", "REF004", "10/03/2024"}
        };
        
        jTable_lista_nomina = new JTable(data, columnNames);
        jTable_lista_nomina.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable_lista_nomina.setRowHeight(25);
        
        // Personalizar header de la tabla
        jTable_lista_nomina.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable_lista_nomina.getTableHeader().setBackground(PRIMARY);
        jTable_lista_nomina.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(jTable_lista_nomina);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void initPopupMenu() {
        jPopupMenu1 = new JPopupMenu();
        
        jMenuItem_editar = new JMenuItem("✏️ Editar Pago");
        jMenuItem_editar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        jMenuItem_consultar = new JMenuItem("📊 Consultar Historial");
        jMenuItem_consultar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        jPopupMenu1.add(jMenuItem_editar);
        jPopupMenu1.add(jMenuItem_consultar);
        
        // Asignar menú contextual a la tabla
        if (jTable_lista_nomina != null) {
            jTable_lista_nomina.setComponentPopupMenu(jPopupMenu1);
        }
    }

    // Método para limpiar todos los filtros
    

    // Método para buscar nóminas con los filtros aplicados

}