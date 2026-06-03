package vista.gestion;

import static vista.formularios.FormularioRegistroGastoNuevo.jLabel1;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import vista.consultas.DetalleClienteNuevo;
import static vista.consultas.DetalleClienteNuevo.jPanel1;

public class VistaGestionVentas extends javax.swing.JInternalFrame {
    // Colores del sistema GestionCategorias
    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);

    public VistaGestionVentas() {
        initComponents();
        aplicarEstiloGestionCategorias();
        configurarResponsividad();
    }

    private void aplicarEstiloGestionCategorias() {
        // Configurar la ventana principal
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setTitle("Módulo de Ventas");
        
        // Aplicar estilo al panel principal con gradiente
        jPanel1.setLayout(new BorderLayout());
        jPanel1.setBackground(LIGHT);
        jPanel1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Crear header similar a GestionCategorias
        crearHeader();
        
        // Reorganizar componentes con layout mejorado
        reorganizarComponentes();
        
        // Aplicar estilos a componentes individuales
        aplicarEstilosComponentes();
    }

    private void crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("💰 Módulo de Ventas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Sistema de registro y gestión de ventas");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        jPanel1.add(headerPanel, BorderLayout.NORTH);
    }

    private void reorganizarComponentes() {
        // Panel principal que contendrá todo el contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBackground(LIGHT);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel superior: Búsqueda y tabla de productos
        JPanel topPanel = crearPanelSuperior();
        
        // Panel central: Tabla de productos solicitados
        JPanel centerPanel = crearPanelCentral();
        
        // Panel inferior: Montos, pago y botones
        JPanel bottomPanel = crearPanelInferior();

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        jPanel1.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel1.setForeground(PRIMARY);
        jTextField_busqueda=new JTextField();
        jTextField_busqueda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_busqueda.setPreferredSize(new Dimension(200, 30));
        jTextField_busqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        searchPanel.add(jLabel1, BorderLayout.WEST);
        searchPanel.add(jTextField_busqueda, BorderLayout.CENTER);

        // Configurar tabla de productos
        configurarTabla(jTable_lista_productos, "Lista de Productos Disponibles");
        
        jScrollPane_tabla_busqueda.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            "Productos Disponibles",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            PRIMARY
        ));

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(jScrollPane_tabla_busqueda, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        configurarTabla(jTable_lista_de_compra, "Productos en la Venta");
        jScrollPane2.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            "Productos Solicitados",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            PRIMARY
        ));

        panel.add(jScrollPane2, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);

        // Panel izquierdo: Montos
        JPanel montosPanel = crearPanelMontos();
        
        // Panel derecho: Pago y botones
        JPanel pagoPanel = crearPanelPago();

        panel.add(montosPanel, BorderLayout.CENTER);
        panel.add(pagoPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelMontos() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBackground(LIGHT);

        // Configurar paneles de montos
        configurarPanelMonto(jPanel_monto_venta, "Monto Total", SUCCESS);
        configurarPanelMonto(jPanel_pago, "Monto del Pago", WARNING);
        configurarPanelMonto(jPanel_cambio, "Cambio", SECONDARY);

        panel.add(jPanel_monto_venta);
        panel.add(jPanel_pago);
        panel.add(jPanel_cambio);

        return panel;
    }

    private JPanel crearPanelPago() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(300, 400));

        // Valor del dólar
        JPanel dolarPanel = new JPanel(new BorderLayout(5, 5));
        dolarPanel.setBackground(Color.WHITE);
        
        JLabel dolarLabel = new JLabel("Valor del Dólar:");
        dolarLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        dolarLabel.setForeground(PRIMARY);
        
        jLabel_valor_dolar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel_valor_dolar.setPreferredSize(new Dimension(100, 25));
        jLabel_valor_dolar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));

        dolarPanel.add(dolarLabel, BorderLayout.WEST);
        dolarPanel.add(jLabel_valor_dolar, BorderLayout.CENTER);
        dolarPanel.add(jLabel9, BorderLayout.EAST);

        // Monto de pago
        JPanel montoPagoPanel = new JPanel(new BorderLayout(5, 5));
        montoPagoPanel.setBackground(Color.WHITE);
        
        jLabel10.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jLabel10.setForeground(PRIMARY);
        
        jTextField_monto_pago.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_monto_pago.setPreferredSize(new Dimension(150, 25));
        jTextField_monto_pago.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));

        montoPagoPanel.add(jLabel10, BorderLayout.NORTH);
        montoPagoPanel.add(jTextField_monto_pago, BorderLayout.CENTER);

        // Forma de pago
        JPanel formaPagoPanel = new JPanel(new BorderLayout(5, 5));
        formaPagoPanel.setBackground(Color.WHITE);
        
        jLabel11.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jLabel11.setForeground(PRIMARY);
        
        jComboBox_forma_de_pago.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_forma_de_pago.setPreferredSize(new Dimension(150, 25));
        jComboBox_forma_de_pago.setBackground(Color.WHITE);

        formaPagoPanel.add(jLabel11, BorderLayout.NORTH);
        formaPagoPanel.add(jComboBox_forma_de_pago, BorderLayout.CENTER);

        // Botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        configurarBoton(jButton_registrar_venta, SUCCESS, "Registrar Venta");
        configurarBoton(jButton_eliminar, DANGER, "Eliminar");
        configurarBoton(jButton_cancelar, WARNING, "Cancelar");

        botonesPanel.add(jButton_registrar_venta);
        botonesPanel.add(Box.createVerticalStrut(8));
        botonesPanel.add(jButton_eliminar);
        botonesPanel.add(Box.createVerticalStrut(8));
        botonesPanel.add(jButton_cancelar);

        // Agregar componentes al panel
        panel.add(dolarPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(montoPagoPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(formaPagoPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(botonesPanel);

        return panel;
    }

    private void configurarPanelMonto(JPanel panel, String titulo, Color color) {
        panel=new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(200, 120));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(tituloLabel, BorderLayout.NORTH);

        // Configurar labels de montos internos...
    }

    private void configurarTabla(JTable tabla, String titulo) {
        tabla=new JTable();
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tabla.setRowHeight(22);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabla.getTableHeader().setBackground(PRIMARY);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(SECONDARY);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(LIGHT);
    }

    private void configurarBoton(JButton boton, Color color, String texto) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(200, 35));
        
        // Actualizar texto si es necesario
        if (!boton.getText().contains(texto)) {
            boton.setText(texto);
        }
    }

    private void aplicarEstilosComponentes() {
        // Aplicar estilos consistentes a todos los labels
        Component[] components = jPanel1.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (!label.getText().contains("💰")) { // Excluir título principal
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    label.setForeground(DARK);
                }
            }
        }
    }

    private void configurarResponsividad() {
        // Hacer componentes redimensionables
        jScrollPane_tabla_busqueda.setPreferredSize(new Dimension(600, 150));
        jScrollPane2.setPreferredSize(new Dimension(600, 200));
        
        // Configurar tamaños mínimos
        jPanel_monto_venta.setMinimumSize(new Dimension(180, 100));
        jPanel_pago.setMinimumSize(new Dimension(180, 100));
        jPanel_cambio.setMinimumSize(new Dimension(180, 100));
    }

    // El resto del código generado por NetBeans permanece igual...
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // ... (las variables existentes)
    // End of variables declaration//GEN-END:variables

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // ... (el código generado por NetBeans permanece igual)
        // Solo se modificarán los layouts y estilos mediante los métodos arriba
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Jlabel_monto_dolares_total_de_cambio;
    public javax.swing.JLabel Jlabel_monto_dolares_total_de_pago;
    public javax.swing.JLabel Jlabel_monto_dolares_total_de_ven1;
    public javax.swing.JButton jButton_cancelar;
    public javax.swing.JButton jButton_eliminar;
    public javax.swing.JButton jButton_registrar_venta;
    public javax.swing.JComboBox<String> jComboBox_forma_de_pago;
    private javax.swing.JLabel jLabel1=new JLabel();
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JLabel jLabel_cedula1;
    public javax.swing.JLabel jLabel_id_usuario;
    public javax.swing.JLabel jLabel_monto_en_bolivares_mon_tot_cambio;
    public javax.swing.JLabel jLabel_monto_en_bolivares_mon_tot_pago;
    public javax.swing.JLabel jLabel_monto_en_bolivares_mon_tot_ven;
    public javax.swing.JTextField jLabel_valor_dolar;
    public javax.swing.JMenuItem jMenuItem_agregar;
    public javax.swing.JMenuItem jMenuItem_eliminar;
    public javax.swing.JMenuItem jMenuItem_modificar_cantidad;
    private javax.swing.JPanel jPanel1=new JPanel();
    public javax.swing.JPanel jPanel_cambio=new JPanel();
    public javax.swing.JPanel jPanel_monto_venta=new JPanel();
    public javax.swing.JPanel jPanel_pago=new JPanel();
    public javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JPopupMenu jPopupMenu2;
    public javax.swing.JScrollPane jScrollPane2=new JScrollPane();
    public javax.swing.JScrollPane jScrollPane_tabla_busqueda=new JScrollPane();
    public javax.swing.JTable jTable_lista_de_compra;
    public javax.swing.JTable jTable_lista_productos;
    public javax.swing.JTextField jTextField_busqueda;
    public javax.swing.JTextField jTextField_monto_pago;
}