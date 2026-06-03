package vista.formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class FormularioVender extends JInternalFrame{

    // --- Colores del sistema (inspirado en SistemaContable_080842.java) ---
    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);

    // --- Componentes de la Vista (Públicos) ---

    // Popups para tablas
    public JPopupMenu jPopupMenu_busqueda;
    public JMenuItem jMenuItem_agregar;
    public JPopupMenu jPopupMenu_compra;
    public JMenuItem jMenuItem_eliminar;
    public JMenuItem jMenuItem_modificar_cantidad;

    // Panel principal y de búsqueda
    public JPanel mainPanel;
    public JTextField jTextField_busqueda;
    public JTable jTable_lista_productos;
    public JTable jTable_lista_de_compra;

    // Paneles de montos (Venta, Pago, Cambio)
    public JPanel jPanel_monto_venta;
    public JLabel jLabel_monto_en_bolivares_mon_tot_ven;
    public JLabel Jlabel_monto_dolares_total_de_ven;
    public JPanel jPanel_pago;
    public JLabel jLabel_monto_en_bolivares_mon_tot_pago;
    public JLabel Jlabel_monto_dolares_total_de_pago;
    public JPanel jPanel_cambio;
    public JLabel jLabel_monto_en_bolivares_mon_tot_cambio;
    public JLabel Jlabel_monto_dolares_total_de_cambio;

    // Panel lateral de acciones
    public JTextField jLabel_valor_dolar;
    public JTextField jTextField_monto_pago;
    public JComboBox<String> jComboBox_forma_de_pago;
    public JButton jButton_registrar_venta;
    public JButton jButton_eliminar;
    public JButton jButton_cancelar;
        
    // Labels ocultos
    public JLabel jLabel_id_usuario;
    public JLabel jLabel_cedula1;

    public FormularioVender() {
        initComponents();
    }


    private void initComponents() {
        setTitle("Registrar Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        setSize(1366, 768);
//        setLocationRelativeTo(null); // Centrar en pantalla
    //    setAlwaysOnTop(true);

        // --- Popups ---
        createPopups();

        // --- Panel Principal ---
        mainPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, 0, getHeight(), SECONDARY.brighter());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false); // El panel principal no debe ser opaco para que los hijos lo sean
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- Header ---
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // --- Panel Central (Búsqueda y Lista de Compra) ---
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);

        // --- Panel Inferior (Totales) ---
        mainPanel.add(createTotalsPanel(), BorderLayout.SOUTH);

        // --- Panel Lateral Derecho (Acciones) ---
        mainPanel.add(createSidePanel(), BorderLayout.EAST);

        // --- Labels Ocultos ---
        createHiddenLabels();
    }

    private void createPopups() {
        jPopupMenu_busqueda = new JPopupMenu();
        jMenuItem_agregar = new JMenuItem("Agregar Producto");
        jMenuItem_agregar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPopupMenu_busqueda.add(jMenuItem_agregar);

        jPopupMenu_compra = new JPopupMenu();
        jMenuItem_eliminar = new JMenuItem("Eliminar producto de la lista");
        jMenuItem_modificar_cantidad = new JMenuItem("Modificar cantidad");
        jPopupMenu_compra.add(jMenuItem_eliminar);
        jPopupMenu_compra.add(jMenuItem_modificar_cantidad);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Módulo de Ventas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));

        headerPanel.add(titleLabel, BorderLayout.NORTH);

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Panel de Búsqueda ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4; // 40% de la altura
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 15, 0);
        centerPanel.add(createSearchPanel(), gbc);

        // --- Panel de Lista de Compra ---
        gbc.gridy = 1;
        gbc.weighty = 0.6; // 60% de la altura
        gbc.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(createPurchaseListPanel(), gbc);

        return centerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(0, 10));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createTitledBorder(null,
            "Búsqueda de Productos",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 20), Color.WHITE
        ));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("Código/Nombre del Producto:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchLabel.setForeground(Color.WHITE);
        jTextField_busqueda = new JTextField();
        jTextField_busqueda.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jTextField_busqueda.setToolTipText("Ingrese el nombre o código del producto para buscar");
        inputPanel.add(searchLabel, BorderLayout.WEST);
        inputPanel.add(jTextField_busqueda, BorderLayout.CENTER);

        jTable_lista_productos = new JTable();
        styleTable(jTable_lista_productos, new String[]{"Código", "Nombre", "Descripción", "Precio", "Disponibilidad"});
        jTable_lista_productos.setComponentPopupMenu(jPopupMenu_busqueda);

        jScrollPane_tabla_busqueda = new JScrollPane(jTable_lista_productos);
        jScrollPane_tabla_busqueda.getViewport().setBackground(Color.WHITE);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(jScrollPane_tabla_busqueda, BorderLayout.CENTER);

        return searchPanel;
    }
public JScrollPane jScrollPane_tabla_busqueda;
    private JPanel createPurchaseListPanel() {
        JPanel purchasePanel = new JPanel(new BorderLayout());
        purchasePanel.setOpaque(false);
        purchasePanel.setBorder(BorderFactory.createTitledBorder(null,
            "Productos en la Venta Actual",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 20), Color.WHITE
        ));

        jTable_lista_de_compra = new JTable();
        styleTable(jTable_lista_de_compra, new String[]{"Código", "Nombre", "Descripción", "Precio", "Cantidad", "Sub total BS"});
        jTable_lista_de_compra.setComponentPopupMenu(jPopupMenu_compra);

        JScrollPane scrollPane = new JScrollPane(jTable_lista_de_compra);
        scrollPane.getViewport().setBackground(Color.WHITE);

        purchasePanel.add(scrollPane, BorderLayout.CENTER);

        return purchasePanel;
    }

    private JPanel createTotalsPanel() {
        JPanel totalsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        totalsPanel.setOpaque(false);

        // --- Panel Monto Total Venta ---
        jPanel_monto_venta = createInfoCard("Monto Total Venta", SUCCESS.darker());
        jLabel_monto_en_bolivares_mon_tot_ven = new JLabel("0.00 Bs");
        Jlabel_monto_dolares_total_de_ven = new JLabel("0.00 $");
        addAmountToCard(jPanel_monto_venta, jLabel_monto_en_bolivares_mon_tot_ven, Jlabel_monto_dolares_total_de_ven);

        // --- Panel Monto Total Pago ---
        jPanel_pago = createInfoCard("Monto del Pago", WARNING.darker());
        jLabel_monto_en_bolivares_mon_tot_pago = new JLabel("0.00 Bs");
        Jlabel_monto_dolares_total_de_pago = new JLabel("0.00 $");
        addAmountToCard(jPanel_pago, jLabel_monto_en_bolivares_mon_tot_pago, Jlabel_monto_dolares_total_de_pago);

        // --- Panel Cambio ---
        jPanel_cambio = createInfoCard("Saldo Restante", SECONDARY);
        jLabel_monto_en_bolivares_mon_tot_cambio = new JLabel("0.00 Bs");
        Jlabel_monto_dolares_total_de_cambio = new JLabel("0.00 $");
        addAmountToCard(jPanel_cambio, jLabel_monto_en_bolivares_mon_tot_cambio, Jlabel_monto_dolares_total_de_cambio);

        totalsPanel.add(jPanel_monto_venta);
        totalsPanel.add(jPanel_pago);
        totalsPanel.add(jPanel_cambio);

        return totalsPanel;
    }

    private JPanel createInfoCard(String title, Color borderColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(borderColor, 2),
            title,
            TitledBorder.CENTER, TitledBorder.BELOW_TOP,
            new Font("Segoe UI", Font.BOLD, 20),
            PRIMARY
        ));
        return card;
    }

    private void addAmountToCard(JPanel card, JLabel bsLabel, JLabel usdLabel) {
        JPanel content = new JPanel(new GridLayout(2, 1, 0, 5));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        bsLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        bsLabel.setForeground(DARK);
        bsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        usdLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        usdLabel.setForeground(DARK);
        usdLabel.setHorizontalAlignment(SwingConstants.CENTER);

        content.add(bsLabel);
        content.add(usdLabel);
        card.add(content, BorderLayout.CENTER);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setOpaque(false);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();

        // Contenedor para los paneles superiores que se mantendrán juntos
        JPanel topSidePanel = new JPanel(new GridBagLayout());
        topSidePanel.setOpaque(false);
        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.fill = GridBagConstraints.HORIZONTAL;
        topGbc.weightx = 1.0;
        topGbc.gridx = 0;

        // --- Panel de Pago ---
        topGbc.gridy = 0;
        topGbc.insets = new Insets(0, 0, 10, 0); // Margen inferior
        topSidePanel.add(createPaymentPanel(), topGbc);

        // --- Panel de Tasa del Dólar ---
        topGbc.gridy = 1;
        topGbc.insets = new Insets(0, 0, 10, 0); // Margen inferior
        topSidePanel.add(createDollarRatePanel(), topGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // Anclar el grupo superior arriba
        sidePanel.add(topSidePanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTH; // Anclar los botones abajo
        gbc.weighty = 1.0; // Permitir que el espacio crezca entre los grupos
        sidePanel.add(createActionButtonsPanel(), gbc);

        return sidePanel;
    }

    private JPanel createPaymentPanel() {
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(createTitledPanelBorder("Detalles del Pago"));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Monto a Pagar
        gbc.gridy = 0;
        paymentPanel.add(createLabel("Ingresar Monto:"), gbc);
        gbc.gridy = 1;
        jTextField_monto_pago = new JTextField();
        jTextField_monto_pago.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        paymentPanel.add(jTextField_monto_pago, gbc);

        // Forma de Pago
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        paymentPanel.add(createLabel("Forma de Pago:"), gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        jComboBox_forma_de_pago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta de Débito", "Pago Móvil", "Zelle"});
        jComboBox_forma_de_pago.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        paymentPanel.add(jComboBox_forma_de_pago, gbc);

        return paymentPanel;
    }

    private JPanel createDollarRatePanel() {
        JPanel dollarPanel = new JPanel(new BorderLayout(10, 0));
        dollarPanel.setOpaque(false);
        dollarPanel.setBorder(createTitledPanelBorder("Tasa del Día"));

        JLabel dollarIcon = new JLabel();
        try {
            // Asegúrate de que la ruta del ícono sea correcta
            dollarIcon.setIcon(new ImageIcon(getClass().getResource("/imagen/simbolo-de-dolar.png")));
        } catch (Exception e) {
            dollarIcon.setText("$");
            dollarIcon.setFont(new Font("Segoe UI", Font.BOLD, 24));
        }

        jLabel_valor_dolar = new JTextField("0.00");
        jLabel_valor_dolar.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        jLabel_valor_dolar.setHorizontalAlignment(JTextField.CENTER);
        jLabel_valor_dolar.setBackground(LIGHT);
        jLabel_valor_dolar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        dollarPanel.add(dollarIcon, BorderLayout.WEST);
        dollarPanel.add(jLabel_valor_dolar, BorderLayout.CENTER);

        return dollarPanel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        buttonsPanel.setOpaque(false);

        jButton_registrar_venta = new JButton("Registrar");
        styleButton(jButton_registrar_venta, SUCCESS, "/imagen/caja-registradora (3).png");

        jButton_eliminar = new JButton("Eliminar");
        styleButton(jButton_eliminar, WARNING, "/imagen/borrar (1).png");

        jButton_cancelar = new JButton("Cancelar");
        styleButton(jButton_cancelar, DANGER, "/imagen/eliminarx30.png");

        buttonsPanel.add(jButton_registrar_venta);
        buttonsPanel.add(jButton_eliminar);
        buttonsPanel.add(jButton_cancelar);

        return buttonsPanel;
    }

    private void createHiddenLabels() {
        jLabel_id_usuario = new JLabel();
        jLabel_cedula1 = new JLabel();
        // No es necesario agregarlos a un panel si son solo para almacenar datos
    }

    // --- Métodos de Utilidad para Estilos ---

    private void styleTable(JTable table, String[] columns) {
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };
        table.setModel(model);

        table.setBackground(Color.WHITE);
        table.setForeground(DARK);
        table.setGridColor(LIGHT);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setSelectionBackground(SECONDARY.brighter());
        table.setSelectionForeground(DARK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(100, 40));
        header.setBorder(BorderFactory.createLineBorder(PRIMARY));
    }

    private TitledBorder createTitledPanelBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(LIGHT),
            title,
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 16),
            Color.WHITE
        );
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void styleButton(JButton button, Color color, String iconPath) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);

        try {
            // Asegúrate de que la ruta del ícono sea correcta
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el ícono: " + iconPath);
        }
    }

    // --- Main para pruebas ---
    public static void main(String[] args) {
        // Para un look and feel más moderno (Nimbus)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FormularioVender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FormularioVender().setVisible(true);
        });
    }
}