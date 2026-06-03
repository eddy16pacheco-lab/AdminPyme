package vista.gestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.beans.PropertyVetoException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GestionarProveedores extends JInternalFrame {

    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);
    public final Color WHITE = new Color(255, 255, 255);

    public JPanel contentPane;
    public JPanel headerPanel;
    public JPanel centerPanel;
    public JPanel buttonPanel;
    public JPopupMenu JPopuMenu1=new JPopupMenu();
    public JMenuItem JMenuItemSeleccionar=new JMenuItem("Seleccionar");
    public JLabel jLabel_id_provedoor;
    public JTextField jTextField_buscar_proveedor;
    public JScrollPane jScrollPane1;
    public JTable jTable_table_proveedores;
    public JTabbedPane jTabbedPane_paneles;
    public JPanel jPanel2;
    public JTextField jTextField_nombre;
    public JTextField jTextField_telefono;
    public JTextField jTextField_correo;
    public JTextField jTextField_ubicacion;
    public JComboBox<String> jComboBox_numeros_tlf;
    public JButton jButton_registrar;
    public JButton jButton_modificar;
    public JButton jButton_limpiar;
    public JButton jButton_activo=new JButton();
    public JButton jButton_inhabilitado;
    public JLabel jLabel6;

    public GestionarProveedores() {
        setTitle("Gestión de Proveedores");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1366, 768);
        setPreferredSize(new Dimension(1366, 768));

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
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        crearHeader();
        crearCentro();
        crearBotonera();

        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        configurarResponsive();

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                try {
                    setMaximum(true);
                } catch (PropertyVetoException ignored) {
                    if (getDesktopPane() != null) {
                        setBounds(0, 0, getDesktopPane().getWidth(), getDesktopPane().getHeight());
                    }
                }
            }
        });
        JPopuMenu1.add(JMenuItemSeleccionar);
    }

    private void crearHeader() {
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("\uD83D\uDCDE Gestión de Proveedores");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        JLabel subtitleLabel = new JLabel("Registro visual de proveedores con estilo contable");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
    }

    private void crearCentro() {
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        jLabel_id_provedoor = new JLabel();
        jLabel_id_provedoor.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        jLabel_id_provedoor.setForeground(DARK);

        jTabbedPane_paneles = new JTabbedPane();
        jTabbedPane_paneles.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jTabbedPane_paneles.setBackground(WHITE);
        jTabbedPane_paneles.setBorder(BorderFactory.createEmptyBorder());

        jPanel2 = new JPanel(new GridBagLayout());
        jPanel2.setBackground(WHITE);
        jPanel2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Registro de proveedores",
                        TitledBorder.LEFT,
                        TitledBorder.BELOW_TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(14, 16, 14, 16);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;

        JPanel nombreGroup = new JPanel(new GridBagLayout());
        nombreGroup.setOpaque(false);
        GridBagConstraints ng = new GridBagConstraints();
        ng.insets = new Insets(0, 0, 0, 8);
        ng.fill = GridBagConstraints.HORIZONTAL;
        ng.weightx = 0;
        JLabel nombreLabel = new JLabel("Nombre");
        nombreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ng.gridx = 0; ng.gridy = 0; nombreGroup.add(nombreLabel, ng);
        jTextField_nombre = new JTextField();
        jTextField_nombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_nombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        jTextField_nombre.setPreferredSize(new Dimension(200, 34));
        ng.gridx = 1; ng.gridy = 0; ng.weightx = 1; nombreGroup.add(jTextField_nombre, ng);

        JPanel telefonoGroup = new JPanel(new GridBagLayout());
        telefonoGroup.setOpaque(false);
        GridBagConstraints tg = new GridBagConstraints();
        tg.insets = new Insets(0, 0, 0, 8);
        tg.fill = GridBagConstraints.HORIZONTAL;
        tg.weightx = 0;
        JLabel telefonoLabel = new JLabel("Teléfono");
        telefonoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tg.gridx = 0; tg.gridy = 0; telefonoGroup.add(telefonoLabel, tg);
        jComboBox_numeros_tlf = new JComboBox<>(new String[]{"0412","0414","0416","0424","0426"});
        jComboBox_numeros_tlf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_numeros_tlf.setPreferredSize(new Dimension(90, 34));
        tg.gridx = 1; tg.gridy = 0; telefonoGroup.add(jComboBox_numeros_tlf, tg);
        jTextField_telefono = new JTextField();
        jTextField_telefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_telefono.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        jTextField_telefono.setPreferredSize(new Dimension(200, 34));
        tg.gridx = 2; tg.gridy = 0; tg.weightx = 1; telefonoGroup.add(jTextField_telefono, tg);

        JPanel correoGroup = new JPanel(new GridBagLayout());
        correoGroup.setOpaque(false);
        GridBagConstraints cg = new GridBagConstraints();
        cg.insets = new Insets(0, 0, 0, 8);
        cg.fill = GridBagConstraints.HORIZONTAL;
        cg.weightx = 0;
        JLabel correoLabel = new JLabel("Correo");
        correoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cg.gridx = 0; cg.gridy = 0; correoGroup.add(correoLabel, cg);
        jTextField_correo = new JTextField();
        jTextField_correo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_correo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        jTextField_correo.setPreferredSize(new Dimension(200, 34));
        cg.gridx = 1; cg.gridy = 0; cg.weightx = 1; correoGroup.add(jTextField_correo, cg);

        JPanel ubicacionGroup = new JPanel(new GridBagLayout());
        ubicacionGroup.setOpaque(false);
        GridBagConstraints ug = new GridBagConstraints();
        ug.insets = new Insets(0, 0, 0, 8);
        ug.fill = GridBagConstraints.HORIZONTAL;
        ug.weightx = 0;
        JLabel ubicacionLabel = new JLabel("Ubicación");
        ubicacionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ug.gridx = 0; ug.gridy = 0; ubicacionGroup.add(ubicacionLabel, ug);
        jTextField_ubicacion = new JTextField();
        jTextField_ubicacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_ubicacion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        jTextField_ubicacion.setPreferredSize(new Dimension(200, 34));
        ug.gridx = 1; ug.gridy = 0; ug.weightx = 1; ubicacionGroup.add(jTextField_ubicacion, ug);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 16, 10, 25);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.gridx = 0; gc.gridy = 0; jPanel2.add(nombreGroup, gc);
        gc.gridx = 1; gc.gridy = 0; jPanel2.add(telefonoGroup, gc);
        gc.gridx = 2; gc.gridy = 0; jPanel2.add(correoGroup, gc);
        gc.insets = new Insets(10, 16, 10, 16);
        gc.gridx = 3; gc.gridy = 0; jPanel2.add(ubicacionGroup, gc);

        jTabbedPane_paneles.addTab("Registro de proveedores", jPanel2);

        JPanel buscarPanel = new JPanel(new GridBagLayout());
        buscarPanel.setBackground(WHITE);
        GridBagConstraints bc = new GridBagConstraints();
        bc.insets = new Insets(6, 8, 6, 8);
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.weightx = 1;
        jLabel6 = new JLabel("Buscar proveedor");
        jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bc.gridx = 0; bc.gridy = 0; bc.weightx = 0; buscarPanel.add(jLabel6, bc);
        jTextField_buscar_proveedor = new JTextField();
        jTextField_buscar_proveedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_buscar_proveedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        bc.gridx = 1; bc.gridy = 0; bc.weightx = 1; buscarPanel.add(jTextField_buscar_proveedor, bc);

        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBackground(WHITE);
        tablaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Listado de Proveedores",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        jTable_table_proveedores = new JTable(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Código de proveedor","Nombre","Teléfono","Correo","Ubicación"}
        ));
        jTable_table_proveedores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable_table_proveedores.setRowHeight(28);
        jTable_table_proveedores.setGridColor(LIGHT);
        jTable_table_proveedores.setShowVerticalLines(false);
        JTableHeader header = jTable_table_proveedores.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jScrollPane1 = new JScrollPane(jTable_table_proveedores);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        tablaPanel.add(jScrollPane1, BorderLayout.CENTER);
        tablaPanel.add(buscarPanel, BorderLayout.NORTH);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.2; centerPanel.add(jTabbedPane_paneles, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 0.8; centerPanel.add(tablaPanel, gbc);
    }

    private void crearBotonera() {
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        jButton_registrar = new JButton("Registrar");
        jButton_registrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_registrar.setBackground(SUCCESS);
        jButton_registrar.setForeground(Color.WHITE);

        jButton_modificar = new JButton("Modificar");
        jButton_modificar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_modificar.setBackground(SECONDARY);
        jButton_modificar.setForeground(Color.WHITE);

        jButton_limpiar = new JButton("Limpiar");
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_limpiar.setBackground(WARNING);
        jButton_limpiar.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(jButton_registrar, gbc);
        gbc.gridx = 1; gbc.gridy = 0; buttonPanel.add(jButton_modificar, gbc);
        gbc.gridx = 2; gbc.gridy = 0; buttonPanel.add(jButton_limpiar, gbc);
    }

    private void configurarResponsive() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = getWidth();
                if (w >= 1100) {
                    jPanel2.setLayout(new GridBagLayout());
                } else {
                    jPanel2.setLayout(new GridBagLayout());
                }
                jPanel2.revalidate();
                jPanel2.repaint();
            }
        });
    }
}