/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.gestion;

import javax.swing.JInternalFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.toedter.calendar.JDateChooser;


public class VistaGestionCliente extends JInternalFrame {
    // Colores del sistema
    private final Color PRIMARY = new Color(44, 62, 80);
    private final Color SECONDARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color WARNING = new Color(243, 156, 18);
    private final Color LIGHT = new Color(236, 240, 241);
    private final Color DARK = new Color(52, 73, 94);
    
    // Componentes de la interfaz
    public JButton jButton_activos;
    public JButton jButton_agregar;
    public JButton jButton_inactivos;
    public JButton jButton_limpiar;
    public JButton jButton_modificar;
    public JComboBox<String> jComboBox_numeros_tlf;
    public JComboBox<String> jComboBox_ubicacion;
    public JMenuItem jMenuItem_activar;
    public JMenuItem jMenuItem_consulta_cliente;
    public JMenuItem jMenuItem_editar;
    public JMenuItem jMenuItem_inhabilitar;
    public JPopupMenu jPopupMenu1;
    public JTable jTable_lista_cliente;
    public JTextField jTextField_apellido;
    public JTextField jTextField_busqueda;
    public JTextField jTextField_cedula;
    public JTextField jTextField_nombre;
    public JTextField jTextField_telefono;
    public JLabel jLabel_cedula;
    public JLabel jLabel_id_proveedor;

    public VistaGestionCliente() {
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
        setTitle("Gestión de Clientes");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        // Inicializar todos los componentes
        jButton_agregar = new JButton("➕ Agregar");
        jButton_limpiar = new JButton("🗑️ Limpiar");
        jButton_modificar = new JButton("✏️ Modificar");
        jButton_activos = new JButton("👁️ Activos");
        jButton_inactivos = new JButton("👁️ Inactivos");
        
        jComboBox_numeros_tlf = new JComboBox<>();
        jComboBox_ubicacion = new JComboBox<>();
        
        jTextField_nombre = new JTextField(20);
        jTextField_apellido = new JTextField(20);
        jTextField_cedula = new JTextField(15);
        jTextField_telefono = new JTextField(10);
        jTextField_busqueda = new JTextField(25);
        
        jLabel_cedula = new JLabel();
        jLabel_id_proveedor = new JLabel();
        
        // Configurar combobox
        configurarCombobox();
        
        // Configurar eventos de hover para botones
        configurarEventosBotones();
    }

    private void configurarCombobox() {
        jComboBox_numeros_tlf.setModel(new DefaultComboBoxModel<>(new String[]{
            "0412", "0414", "0416", "0424", "0426"
        }));
        
        jComboBox_ubicacion.setModel(new DefaultComboBoxModel<>(new String[]{
            "Seleccionar ubicación", "Caracas", "Valencia", "Maracaibo", "Barquisimeto", 
            "Maracay", "Ciudad Guayana", "Barcelona", "Maturín", "Puerto La Cruz"
        }));
    }

    private void configurarEventosBotones() {
        // Configurar efectos hover para botones
        configurarBotonHover(jButton_agregar);
        configurarBotonHover(jButton_limpiar);
        configurarBotonHover(jButton_modificar);
    }

    private void configurarBotonHover(JButton boton) {
        Color colorOriginal = boton.getBackground();
        Color colorTextoOriginal = boton.getForeground();
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(90, 142, 179));
                boton.setForeground(Color.WHITE);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorOriginal);
                boton.setForeground(colorTextoOriginal);
            }
        });
    }

    private void setupLayout() {
        // Panel principal con BoxLayout para mejor control
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Agregar las secciones en orden
        mainPanel.add(crearHeader());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        mainPanel.add(crearPanelFormulario());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        mainPanel.add(crearPanelBusqueda());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        mainPanel.add(crearPanelTabla());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        mainPanel.add(crearPanelBotones());
        
        // Agregar scroll por si el contenido es muy grande
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        setContentPane(scrollPane);
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        headerPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
        
        // Título
        JLabel titleLabel = new JLabel("👥 Gestión de Clientes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Administración completa del registro de clientes");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 250));
        
        // Título del formulario
        JLabel titulo = new JLabel("📝 Formulario de Registro de Clientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Panel para los campos del formulario
        JPanel camposPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        camposPanel.setBackground(Color.WHITE);
        camposPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
        
        // Fila 1 - Campos principales
        camposPanel.add(crearCampoTexto("Nombre *", jTextField_nombre, "Ingrese el nombre del cliente"));
        camposPanel.add(crearCampoTexto("Apellido *", jTextField_apellido, "Ingrese el apellido del cliente"));
        camposPanel.add(crearCampoTexto("Cédula *", jTextField_cedula, "Ingrese la cédula de identidad"));
        camposPanel.add(crearCampoTelefono("Teléfono *", jComboBox_numeros_tlf, jTextField_telefono));
        
        // Fila 2 - Campos adicionales
        camposPanel.add(crearPanelVacio());
        camposPanel.add(crearPanelVacio());
        camposPanel.add(crearCampoCombo("Ubicación", jComboBox_ubicacion));
        camposPanel.add(crearPanelInfoAdicional());
        
        // Agregar componentes al panel principal
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(camposPanel);
        
        return panel;
    }

    private JPanel crearCampoTexto(String titulo, JTextField textField, String tooltip) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(300, 60));
        
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setToolTipText(tooltip);
        textField.setPreferredSize(new Dimension(200, 35));
        textField.setMaximumSize(new Dimension(200, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearCampoCombo(String titulo, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(300, 60));
        
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBackground(LIGHT);
        comboBox.setPreferredSize(new Dimension(200, 35));
        comboBox.setMaximumSize(new Dimension(200, 35));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearCampoTelefono(String titulo, JComboBox<String> comboPrefijo, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(300, 60));
        
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JPanel telefonoPanel = new JPanel(new BorderLayout(5, 0));
        telefonoPanel.setBackground(Color.WHITE);
        
        comboPrefijo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        comboPrefijo.setPreferredSize(new Dimension(80, 35));
        comboPrefijo.setMaximumSize(new Dimension(80, 35));
        
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setPreferredSize(new Dimension(120, 35));
        textField.setMaximumSize(new Dimension(120, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        telefonoPanel.add(comboPrefijo, BorderLayout.WEST);
        telefonoPanel.add(textField, BorderLayout.CENTER);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(telefonoPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelVacio() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(200, 60));
        return panel;
    }

    private JPanel crearPanelInfoAdicional() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(LIGHT), "Información"
        ));
        panel.setPreferredSize(new Dimension(200, 60));
        
        JLabel infoLabel = new JLabel("<html><div style='text-align: center; color: #666; font-size: 10px;'>"
            + "Los campos marcados con *<br>son obligatorios"
            + "</div></html>");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(infoLabel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel buscarLabel = new JLabel("🔍 Buscar Cliente:");
        buscarLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        jTextField_busqueda.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_busqueda.setPreferredSize(new Dimension(400, 40));
        jTextField_busqueda.setToolTipText("Buscar por nombre, apellido o cédula");
        jTextField_busqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Botones de filtro
        jButton_activos.setBackground(SUCCESS);
        jButton_activos.setForeground(Color.WHITE);
        jButton_activos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_activos.setPreferredSize(new Dimension(120, 40));
        
        jButton_inactivos.setBackground(DANGER);
        jButton_inactivos.setForeground(Color.WHITE);
        jButton_inactivos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_inactivos.setPreferredSize(new Dimension(120, 40));
        
        searchPanel.add(buscarLabel);
        searchPanel.add(jTextField_busqueda);
        searchPanel.add(jButton_activos);
        searchPanel.add(jButton_inactivos);
        
        panel.add(searchPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 400));
        
        JLabel titulo = new JLabel("📋 Lista de Clientes Registrados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        // Configurar tabla con datos de ejemplo
        String[] columnNames = {
            "N° Cliente", "Nombre", "Apellido", "Cédula", "Teléfono", "Ubicación", "Estado"
        };
        
        Object[][] data = {
            {"001", "Juan", "Pérez", "V-12345678", "0412-5551234", "Caracas", "Activo"},
            {"002", "María", "García", "V-87654321", "0414-5555678", "Valencia", "Activo"},
            {"003", "Carlos", "López", "V-11223344", "0416-5559012", "Maracaibo", "Inactivo"},
            {"004", "Ana", "Rodríguez", "V-44332211", "0424-5553456", "Barquisimeto", "Activo"},
            {"005", "Pedro", "Martínez", "V-55667788", "0426-5557890", "Maracay", "Activo"}
        };
        
        jTable_lista_cliente = new JTable(data, columnNames);
        jTable_lista_cliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable_lista_cliente.setRowHeight(30);
        
        // Personalizar header de la tabla
        jTable_lista_cliente.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable_lista_cliente.getTableHeader().setBackground(PRIMARY);
        jTable_lista_cliente.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(jTable_lista_cliente);
        scrollPane.setPreferredSize(new Dimension(1200, 300));
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
        
        // Configurar botones
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_limpiar.setBackground(WARNING);
        jButton_limpiar.setForeground(Color.WHITE);
        jButton_limpiar.setPreferredSize(new Dimension(120, 45));
        
        jButton_agregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_agregar.setBackground(SUCCESS);
        jButton_agregar.setForeground(Color.WHITE);
        jButton_agregar.setPreferredSize(new Dimension(120, 45));
        
        jButton_modificar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_modificar.setBackground(SECONDARY);
        jButton_modificar.setForeground(Color.WHITE);
        jButton_modificar.setPreferredSize(new Dimension(120, 45));
        
        panel.add(jButton_limpiar);
        panel.add(jButton_agregar);
        panel.add(jButton_modificar);
        
        return panel;
    }

    private void initPopupMenu() {
        jPopupMenu1 = new JPopupMenu();
        
        jMenuItem_editar = new JMenuItem("✏️ Seleccionar Cliente");
        jMenuItem_editar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        jMenuItem_inhabilitar = new JMenuItem("🚫 Inhabilitar");
        jMenuItem_inhabilitar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        jMenuItem_consulta_cliente = new JMenuItem("📊 Consultar Información");
        jMenuItem_consulta_cliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        jMenuItem_activar = new JMenuItem("✅ Activar");
        jMenuItem_activar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        jPopupMenu1.add(jMenuItem_editar);
        jPopupMenu1.add(jMenuItem_inhabilitar);
        jPopupMenu1.add(jMenuItem_consulta_cliente);
        jPopupMenu1.add(jMenuItem_activar);
        
        // Asignar menú contextual a la tabla
        if (jTable_lista_cliente != null) {
            jTable_lista_cliente.setComponentPopupMenu(jPopupMenu1);
        }
    }

    // Métodos públicos para acceder a los componentes
    public JTable getTablaClientes() {
        return jTable_lista_cliente;
    }
    
    public JButton getBotonAgregar() {
        return jButton_agregar;
    }
    
    public JButton getBotonModificar() {
        return jButton_modificar;
    }
    
    public JTextField getCampoBusqueda() {
        return jTextField_busqueda;
    }
    
    // Métodos para obtener datos del formulario
    public String getNombre() {
        return jTextField_nombre.getText().trim();
    }
    
    public String getApellido() {
        return jTextField_apellido.getText().trim();
    }
    
    public String getCedula() {
        return jTextField_cedula.getText().trim();
    }
    
    public String getTelefono() {
        return jComboBox_numeros_tlf.getSelectedItem() + "-" + jTextField_telefono.getText().trim();
    }
    
    public String getUbicacion() {
        return jComboBox_ubicacion.getSelectedItem().toString();
    }
    
    // Método para limpiar el formulario
    public void limpiarFormulario() {
        jTextField_nombre.setText("");
        jTextField_apellido.setText("");
        jTextField_cedula.setText("");
        jTextField_telefono.setText("");
        jComboBox_ubicacion.setSelectedIndex(0);
        jComboBox_numeros_tlf.setSelectedIndex(0);
    }
    
}

