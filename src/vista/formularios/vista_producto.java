package vista.formularios;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class vista_producto extends javax.swing.JInternalFrame {
    // Colores del sistema (consistentes con GestionCategorias)
    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);

    public vista_producto() {
        initComponents();
        aplicarNuevoEstilo();
    }

    private void aplicarNuevoEstilo() {
        // Configurar el fondo principal con gradiente como GestionCategorias
        jPanel_producto.setLayout(new BorderLayout());
        jPanel_producto.setBackground(PRIMARY);
        
        // Crear header similar a GestionCategorias
        crearHeader();
        
        // Crear panel de contenido principal
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Panel superior con formulario y búsqueda
        JPanel topPanel = crearTopPanel();
        mainContentPanel.add(topPanel, BorderLayout.NORTH);
        
        // Panel central con la tabla
        JPanel centerPanel = crearCenterPanel();
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);
        
        jPanel_producto.add(mainContentPanel, BorderLayout.CENTER);
        
        aplicarEstiloComponentes();
    }

private void crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        
        JLabel titleLabel = new JLabel("📦 Gestión de Productos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(20, 20, 5, 20));
        
        JLabel subtitleLabel = new JLabel("Administración completa del inventario de productos");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        
        jPanel_producto.add(headerPanel, BorderLayout.NORTH);
    }
 private JPanel crearTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Panel de formulario a la izquierda
        JPanel formPanel = crearFormPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        topPanel.add(formPanel, gbc);
        
        // Panel de búsqueda a la derecha
        JPanel searchPanel = crearSearchPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        topPanel.add(searchPanel, gbc);
        
        return topPanel;
    }
  private JPanel crearFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        
        // Título del formulario
        JLabel titulo = new JLabel("Formulario de Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titulo, gbc);
        
        // Reorganizar componentes existentes en el formulario
        gbc.gridwidth = 1;
        
        // Código del producto
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(jLabel_tit_id, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(jTextField_id_producto, gbc);
        formPanel.add(jLabel_men_id, gbc);
        
        // Categoría
        gbc.gridy = 3;
        formPanel.add(jComboBox_categorias, gbc);
        
        // Nombre
        gbc.gridy = 4;
        formPanel.add(jLabel_titu_nom, gbc);
        
        gbc.gridy = 5;
        formPanel.add(jTextField_nombre_producto, gbc);
        formPanel.add(jLabel_nom_pro, gbc);
        
        // Descripción
        gbc.gridy = 6;
        formPanel.add(jLabel_titu_des, gbc);
        
        gbc.gridy = 7;
        formPanel.add(jTextField_descripcion, gbc);
        formPanel.add(jLabel_des_pro, gbc);
        
        // Precios
        JPanel pricePanel = new JPanel(new GridLayout(2, 2, 10, 5));
        pricePanel.setOpaque(false);
        
        pricePanel.add(jLabel_titu_pre_ven);
        pricePanel.add(jLabel_titu_pre_ven1);
        pricePanel.add(jTextField_precio_venta);
        pricePanel.add(jTextField_precio_compra);
        
        gbc.gridy = 8;
        formPanel.add(pricePanel, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        
        buttonPanel.add(jButton_añadir);
        buttonPanel.add(jButton_modificar);
        buttonPanel.add(jButton_limpiar);
        
        gbc.gridy = 9;
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }

    private JPanel crearSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setOpaque(false);
        
        // Panel de búsqueda superior
        JPanel searchTopPanel = new JPanel(new GridBagLayout());
        searchTopPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        
        // Label para "Buscar producto"
JLabel buscarLabel = new JLabel("Buscar producto:");
buscarLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
buscarLabel.setForeground(LIGHT);
gbc.gridx = 0;
gbc.gridy = 0;
gbc.gridwidth = 1;
searchTopPanel.add(buscarLabel, gbc);

// Campo de búsqueda
gbc.gridx = 0;
gbc.gridy = 1;
gbc.gridwidth = 1;
searchTopPanel.add(jTextField_busqueda, gbc);

// Label para "Filtrar búsqueda"
JLabel filtrarLabel = new JLabel("Filtrar por:");
filtrarLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
filtrarLabel.setForeground(LIGHT);
gbc.gridx = 1;
gbc.gridy = 0;
searchTopPanel.add(filtrarLabel, gbc);

// Combobox de filtro
gbc.gridx = 1;
gbc.gridy = 1;
searchTopPanel.add(jComboBox_filtrar_busqueda, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchTopPanel.add(jLabel15, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchTopPanel.add(jLabel18, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        searchTopPanel.add(jTextField_busqueda, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        searchTopPanel.add(jComboBox_filtrar_busqueda, gbc);
        
        searchPanel.add(searchTopPanel, BorderLayout.NORTH);
        
        // Panel de tabla
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Título de la tabla
        JLabel tableTitle = new JLabel("Lista de Productos");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(PRIMARY);
        tableTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        tableContainer.add(tableTitle, BorderLayout.NORTH);
        
        // Controles de vista (ojos)
        JPanel eyePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        eyePanel.setOpaque(false);
        eyePanel.add(jLabel_vista);
        eyePanel.add(jLabel_ojo_oculto);
        eyePanel.add(jLabel_mensaje);
        tableContainer.add(eyePanel, BorderLayout.SOUTH);
        
        // Tabla con scroll
        tableContainer.add(jScrollPane1, BorderLayout.CENTER);
        
        searchPanel.add(tableContainer, BorderLayout.CENTER);
        
        return searchPanel;
    }

    private JPanel crearCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        return centerPanel;
    }
    
    private void aplicarEstiloComponentes() {
        // Configurar fuentes y colores
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        
        // Configurar etiquetas
        Component[] labels = {jLabel_tit_id, jLabel_titu_nom, jLabel_titu_des, 
                             jLabel_titu_pre_ven, jLabel_titu_pre_ven1, jLabel15, jLabel18};
        for (Component label : labels) {
            if (label instanceof JLabel) {
                ((JLabel) label).setFont(labelFont);
                ((JLabel) label).setForeground(PRIMARY);
            }
        }
        
        // Configurar campos de texto
        Component[] textFields = {jTextField_id_producto, jTextField_nombre_producto, 
                                 jTextField_descripcion, jTextField_precio_venta, 
                                 jTextField_precio_compra, jTextField_busqueda};
        for (Component field : textFields) {
            if (field instanceof JTextField) {
                ((JTextField) field).setFont(textFont);
                ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY, 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
            }
        }
        
        // Configurar combobox
        jComboBox_categorias.setFont(textFont);
        jComboBox_filtrar_busqueda.setFont(textFont);
        
        // Configurar botones
        estiloBoton(jButton_añadir, SUCCESS);
        estiloBoton(jButton_modificar, SECONDARY);
        estiloBoton(jButton_limpiar, WARNING);
        
        // Configurar tabla
        jTable_lista_productos.getTableHeader().setBackground(PRIMARY);
        jTable_lista_productos.getTableHeader().setForeground(Color.WHITE);
        jTable_lista_productos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable_lista_productos.setFont(textFont);
        jTable_lista_productos.setRowHeight(25);
        
        // Configurar mensajes de error
        jLabel_mensaje.setForeground(SECONDARY);
        jLabel_mensaje.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void estiloBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void hacerResponsive() {
        // Hacer que los componentes sean responsivos
        jPanel_producto.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustarTamanosResponsivos();
            }
        });
    }
 
    private void ajustarTamanosResponsivos() {
        // Ajustar tamaños según el tamaño actual del frame
        int width = jPanel_producto.getWidth();
        
        if (width < 800) {
            // Vista móvil/tablet pequeña
            ajustarParaMovil();
        } else if (width < 1200) {
            // Vista tablet
            ajustarParaTablet();
        } else {
            // Vista escritorio
            ajustarParaEscritorio();
        }
    }

    private void ajustarParaMovil() {
        // Configuración para dispositivos móviles
        Font smallFont = new Font("Segoe UI", Font.PLAIN, 12);
        jTable_lista_productos.setFont(smallFont);
    }

    private void ajustarParaTablet() {
        // Configuración para tablets
        Font mediumFont = new Font("Segoe UI", Font.PLAIN, 13);
        jTable_lista_productos.setFont(mediumFont);
    }

    private void ajustarParaEscritorio() {
        // Configuración para escritorio
        Font largeFont = new Font("Segoe UI", Font.PLAIN, 14);
        jTable_lista_productos.setFont(largeFont);
    }

    private void estiloCampoTexto(JTextField campo) {
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem_seleccionar = new javax.swing.JMenuItem();
        jMenuItem_Inabilitar = new javax.swing.JMenuItem();
        jMenuItem_hab = new javax.swing.JMenuItem();
        jPanel_producto = new javax.swing.JPanel();
        jLabel_tit_id = new javax.swing.JLabel();
        jTextField_id_producto = new javax.swing.JTextField();
        jLabel_men_id = new javax.swing.JLabel();
        jComboBox_categorias = new javax.swing.JComboBox<>();
        jLabel_titu_nom = new javax.swing.JLabel();
        jTextField_nombre_producto = new javax.swing.JTextField();
        jLabel_nom_pro = new javax.swing.JLabel();
        jLabel_titu_des = new javax.swing.JLabel();
        jTextField_descripcion = new javax.swing.JTextField();
        jLabel_titu_pre_ven = new javax.swing.JLabel();
        jTextField_precio_venta = new javax.swing.JTextField();
        jLabel_pre_vent = new javax.swing.JLabel();
        jButton_añadir = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTextField_busqueda = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_lista_productos = new javax.swing.JTable();
        jLabel_ojo_oculto = new javax.swing.JLabel();
        jLabel_vista = new javax.swing.JLabel();
        jLabel_mensaje = new javax.swing.JLabel();
        jComboBox_filtrar_busqueda = new javax.swing.JComboBox<>();
        jLabel_nombre_usuario = new javax.swing.JLabel();
        jButton_limpiar = new javax.swing.JButton();
        jTextField_precio_compra = new javax.swing.JTextField();
        jLabel_titu_pre_ven1 = new javax.swing.JLabel();
        jLabel_pre_com = new javax.swing.JLabel();
        jButton_modificar = new javax.swing.JButton();
        jLabel_des_pro = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Jlabel_erro = new javax.swing.JLabel();

        jMenuItem_seleccionar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem_seleccionar.setText("Seleccionar");
        jPopupMenu1.add(jMenuItem_seleccionar);

        jMenuItem_Inabilitar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem_Inabilitar.setText("Inhabilitar");
        jPopupMenu1.add(jMenuItem_Inabilitar);

        jMenuItem_hab.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem_hab.setText("Habilitar");
        jPopupMenu1.add(jMenuItem_hab);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel_producto.setBackground(new java.awt.Color(206, 222, 244));
        jPanel_producto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_tit_id.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_tit_id.setText("Ingresar el código del producto");
        jPanel_producto.add(jLabel_tit_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 290, 35));

        jTextField_id_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_id_productoActionPerformed(evt);
            }
        });
        jPanel_producto.add(jTextField_id_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 290, 30));
        jPanel_producto.add(jLabel_men_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 290, 20));

        jComboBox_categorias.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox_categorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel_producto.add(jComboBox_categorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 290, 30));

        jLabel_titu_nom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_titu_nom.setText("Ingresar el nombre del producto");
        jPanel_producto.add(jLabel_titu_nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 290, 35));
        jPanel_producto.add(jTextField_nombre_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 290, 30));
        jPanel_producto.add(jLabel_nom_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 290, 20));

        jLabel_titu_des.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_titu_des.setText("Ingrese la descripción del producto");
        jPanel_producto.add(jLabel_titu_des, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 290, 40));
        jPanel_producto.add(jTextField_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 290, 30));

        jLabel_titu_pre_ven.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_titu_pre_ven.setText("Ingrese el precio de venta");
        jPanel_producto.add(jLabel_titu_pre_ven, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 290, 40));
        jPanel_producto.add(jTextField_precio_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 290, 30));
        jPanel_producto.add(jLabel_pre_vent, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 290, 20));

        jButton_añadir.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_añadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/registro (1).png"))); // NOI18N
        jButton_añadir.setText("<html>Añadir<html>"
        );
        jButton_añadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_añadirActionPerformed(evt);
            }
        });
        jPanel_producto.add(jButton_añadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 130, 80));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Buscar producto");
        jPanel_producto.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 27, 550, 35));
        jPanel_producto.add(jTextField_busqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 62, 680, 30));

        jPanel7.setBackground(new java.awt.Color(206, 222, 244));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de producto ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Segoe UI", 1, 24), new java.awt.Color(38, 117, 191))); // NOI18N
        jPanel7.setLayout(null);

        jTable_lista_productos.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTable_lista_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "C°", "Categoría", "Nombre del producto", "Descripción", "Pre.Venta", "Pre.Compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_lista_productos.setRowHeight(30);
        jTable_lista_productos.getTableHeader().setResizingAllowed(false);
        jTable_lista_productos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable_lista_productos);
        if (jTable_lista_productos.getColumnModel().getColumnCount() > 0) {
            jTable_lista_productos.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable_lista_productos.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable_lista_productos.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTable_lista_productos.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTable_lista_productos.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable_lista_productos.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jPanel7.add(jScrollPane1);
        jScrollPane1.setBounds(50, 80, 1000, 480);

        jLabel_ojo_oculto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Ojo inactivo.jpg"))); // NOI18N
        jLabel_ojo_oculto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.add(jLabel_ojo_oculto);
        jLabel_ojo_oculto.setBounds(1030, 10, 50, 40);

        jLabel_vista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Ojo.jpg"))); // NOI18N
        jLabel_vista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.add(jLabel_vista);
        jLabel_vista.setBounds(1030, 10, 50, 40);

        jLabel_mensaje.setBackground(new java.awt.Color(38, 117, 191));
        jLabel_mensaje.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel_mensaje.setForeground(new java.awt.Color(38, 117, 191));
        jLabel_mensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_mensaje.setText("Habilitados");
        jPanel7.add(jLabel_mensaje);
        jLabel_mensaje.setBounds(390, 40, 330, 35);

        jPanel_producto.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 1100, 578));

        jComboBox_filtrar_busqueda.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox_filtrar_busqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel_producto.add(jComboBox_filtrar_busqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 60, 390, -1));
        jPanel_producto.add(jLabel_nombre_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 70, 70, -1));

        jButton_limpiar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton_limpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/registro (1).png"))); // NOI18N
        jButton_limpiar.setText("Limpiar");
        jButton_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_limpiarActionPerformed(evt);
            }
        });
        jPanel_producto.add(jButton_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 660, -1, 70));
        jPanel_producto.add(jTextField_precio_compra, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, 290, 30));

        jLabel_titu_pre_ven1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_titu_pre_ven1.setText("Ingrese el precio de compra");
        jPanel_producto.add(jLabel_titu_pre_ven1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 290, 40));
        jPanel_producto.add(jLabel_pre_com, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 290, 20));

        jButton_modificar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/actualizar.png"))); // NOI18N
        jButton_modificar.setText("Modificar");
        jButton_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificarActionPerformed(evt);
            }
        });
        jPanel_producto.add(jButton_modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 560, 130, 80));
        jPanel_producto.add(jLabel_des_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 290, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Filtrar búsqueda");
        jPanel_producto.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 330, 35));

        Jlabel_erro.setText("jLabel1");
        jPanel_producto.add(Jlabel_erro, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 116, 170, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_añadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_añadirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_añadirActionPerformed

    private void jTextField_id_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_id_productoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_id_productoActionPerformed

    private void jButton_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_limpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_limpiarActionPerformed

    private void jButton_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_modificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Jlabel_erro;
    public javax.swing.JButton jButton_añadir;
    public javax.swing.JButton jButton_limpiar;
    public javax.swing.JButton jButton_modificar;
    public javax.swing.JComboBox<String> jComboBox_categorias;
    public javax.swing.JComboBox<String> jComboBox_filtrar_busqueda;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel_des_pro;
    public static javax.swing.JLabel jLabel_men_id;
    public javax.swing.JLabel jLabel_mensaje;
    public javax.swing.JLabel jLabel_nom_pro;
    public static javax.swing.JLabel jLabel_nombre_usuario;
    public javax.swing.JLabel jLabel_ojo_oculto;
    public javax.swing.JLabel jLabel_pre_com;
    public javax.swing.JLabel jLabel_pre_vent;
    public javax.swing.JLabel jLabel_tit_id;
    public javax.swing.JLabel jLabel_titu_des;
    public javax.swing.JLabel jLabel_titu_nom;
    public javax.swing.JLabel jLabel_titu_pre_ven;
    public javax.swing.JLabel jLabel_titu_pre_ven1;
    public javax.swing.JLabel jLabel_vista;
    public javax.swing.JMenuItem jMenuItem_Inabilitar;
    public javax.swing.JMenuItem jMenuItem_hab;
    public javax.swing.JMenuItem jMenuItem_seleccionar;
    private javax.swing.JPanel jPanel7;
    public static javax.swing.JPanel jPanel_producto;
    public javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable_lista_productos;
    public javax.swing.JTextField jTextField_busqueda;
    public javax.swing.JTextField jTextField_descripcion;
    public javax.swing.JTextField jTextField_id_producto;
    public javax.swing.JTextField jTextField_nombre_producto;
    public javax.swing.JTextField jTextField_precio_compra;
    public javax.swing.JTextField jTextField_precio_venta;
    // End of variables declaration//GEN-END:variables
}