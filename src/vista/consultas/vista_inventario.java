/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.consultas;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Eddy
 */
public class vista_inventario extends javax.swing.JFrame {

    /**
     * Creates new form vista_inventario
     */
    public vista_inventario() {
        initComponents();
        aplicarEstilosModernos();
    }
    
    private void aplicarEstilosModernos() {
        // Configuración general de la ventana
        getContentPane().setBackground(new Color(245, 247, 250));
        
        // Estilo para el JTabbedPane
        jTabbedPane1.setBackground(new Color(52, 73, 94));
        jTabbedPane1.setForeground(Color.WHITE);
        jTabbedPane1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        // Aplicar estilos a todos los paneles
        aplicarEstiloPanel(jPanel1);
        aplicarEstiloPanel(jPanel2);
        aplicarEstiloPanel(jPanel3);
        aplicarEstiloPanel(jPanel4);
        
        // Aplicar estilos a las tablas
        aplicarEstiloTablas();
        
        // Aplicar estilos a los botones
        aplicarEstiloBotones();
        
        // Aplicar estilos a los campos de texto
        aplicarEstiloCamposTexto();
        
        // Aplicar estilos a los combobox
        aplicarEstiloCombobox();
        
        // Aplicar estilos a las etiquetas
        aplicarEstiloEtiquetas();
    }
    
    private void aplicarEstiloPanel(javax.swing.JPanel panel) {
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
    }
    
    private void aplicarEstiloTablas() {
        // Estilo para jTable1
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new Color(230, 230, 230));
        jTable1.setRowHeight(30);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable1.getTableHeader().setBackground(new Color(52, 73, 94));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        
        // Estilo para jTable2
        jTable2.setShowGrid(true);
        jTable2.setGridColor(new Color(230, 230, 230));
        jTable2.setRowHeight(30);
        jTable2.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable2.getTableHeader().setBackground(new Color(52, 73, 94));
        jTable2.getTableHeader().setForeground(Color.WHITE);
        
        // Estilo para jTable3
        jTable3.setShowGrid(true);
        jTable3.setGridColor(new Color(230, 230, 230));
        jTable3.setRowHeight(30);
        jTable3.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable3.getTableHeader().setBackground(new Color(52, 73, 94));
        jTable3.getTableHeader().setForeground(Color.WHITE);
        
        // Estilo para jTable4
        jTable4.setShowGrid(true);
        jTable4.setGridColor(new Color(230, 230, 230));
        jTable4.setRowHeight(30);
        jTable4.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable4.getTableHeader().setBackground(new Color(52, 73, 94));
        jTable4.getTableHeader().setForeground(Color.WHITE);
    }
    
    private void aplicarEstiloBotones() {
        Font fontBoton = new Font("Segoe UI", Font.BOLD, 16);
        Color colorPrimario = new Color(41, 128, 185);
        Color colorSecundario = new Color(39, 174, 96);
        Color colorPeligro = new Color(231, 76, 60);
        
        jButton1.setFont(fontBoton);
        jButton1.setBackground(colorSecundario);
        jButton1.setForeground(Color.WHITE);
        jButton1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(35, 155, 86), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jButton2.setFont(fontBoton);
        jButton2.setBackground(colorPrimario);
        jButton2.setForeground(Color.WHITE);
        jButton2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(35, 110, 165), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jButton3.setFont(fontBoton);
        jButton3.setBackground(colorPeligro);
        jButton3.setForeground(Color.WHITE);
        jButton3.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 60, 45), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jButton4.setFont(fontBoton);
        jButton4.setBackground(colorSecundario);
        jButton4.setForeground(Color.WHITE);
        jButton4.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(35, 155, 86), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jButton5.setFont(fontBoton);
        jButton5.setBackground(colorPrimario);
        jButton5.setForeground(Color.WHITE);
        jButton5.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(35, 110, 165), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    private void aplicarEstiloCamposTexto() {
        Font fontCampo = new Font("Segoe UI", Font.PLAIN, 16);
        Color bordeCampo = new Color(200, 200, 200);
        
        // Campos de texto del panel 1
        jTextField1.setFont(fontCampo);
        jTextField1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField2.setFont(fontCampo);
        jTextField2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField3.setFont(fontCampo);
        jTextField3.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField4.setFont(fontCampo);
        jTextField4.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField5.setFont(fontCampo);
        jTextField5.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField6.setFont(fontCampo);
        jTextField6.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Campos de texto del panel 3
        jTextField7.setFont(fontCampo);
        jTextField7.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField8.setFont(fontCampo);
        jTextField8.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField9.setFont(fontCampo);
        jTextField9.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField10.setFont(fontCampo);
        jTextField10.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jTextField11.setFont(fontCampo);
        jTextField11.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCampo, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }
    
    private void aplicarEstiloCombobox() {
        Font fontCombobox = new Font("Segoe UI", Font.PLAIN, 16);
        Color bordeCombobox = new Color(200, 200, 200);
        
        jComboBox1.setFont(fontCombobox);
        jComboBox1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox2.setFont(fontCombobox);
        jComboBox2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox3.setFont(fontCombobox);
        jComboBox3.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox4.setFont(fontCombobox);
        jComboBox4.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox5.setFont(fontCombobox);
        jComboBox5.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox6.setFont(fontCombobox);
        jComboBox6.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox7.setFont(fontCombobox);
        jComboBox7.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox8.setFont(fontCombobox);
        jComboBox8.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox9.setFont(fontCombobox);
        jComboBox9.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        jComboBox10.setFont(fontCombobox);
        jComboBox10.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordeCombobox, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }
    
    private void aplicarEstiloEtiquetas() {
        Font fontEtiqueta = new Font("Segoe UI", Font.BOLD, 16);
        Color colorEtiqueta = new Color(52, 73, 94);
        
        // Aplicar a todas las etiquetas
        for (java.awt.Component comp : getContentPane().getComponents()) {
            if (comp instanceof javax.swing.JLabel) {
                ((javax.swing.JLabel) comp).setFont(fontEtiqueta);
                ((javax.swing.JLabel) comp).setForeground(colorEtiqueta);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem_seleccionar = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();

        jMenuItem_seleccionar.setText("Seleccionar");
        jPopupMenu1.add(jMenuItem_seleccionar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión de Inventario");
        setMinimumSize(new java.awt.Dimension(1200, 800));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1.setText("Código de barras");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 400, 35));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel2.setText("Nombre");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 400, 35));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel4.setText("Descripción");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 400, 35));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel3.setText("Categoría");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar categoría", "Alimentos", "Bebidas", "Limpieza", "Electrónica" }));
        jPanel5.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 400, 35));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel6.setText("Precio unitario");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, -1));

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 70, 270, 35));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Unidad de medida");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 30, -1, -1));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidad", "Kg", "Litro", "Paquete", "Caja" }));
        jPanel5.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 70, 180, 35));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel7.setText("Fecha de vencimiento");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 130, -1, -1));

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel5.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 170, 270, 35));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setText("Precio de venta");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, -1, -1));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 400, 35));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel9.setText("Precio de compra");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 400, 35));

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton1.setText("Registrar Producto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 260, 220, 45));

        jPanel1.add(jPanel5, java.awt.BorderLayout.NORTH);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Categoría", "Precio Compra", "Precio Venta", "Stock", "Unidad", "Vencimiento"
            }
        ));
        jTable1.setRowHeight(30);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Gestión de Productos", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Filtrar por Producto");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los productos", "Bajo stock", "Por vencer", "Sin stock" }));
        jPanel7.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 400, 35));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel11.setText("Filtrar por Categoría");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        jComboBox4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas las categorías", "Alimentos", "Bebidas", "Limpieza", "Electrónica" }));
        jPanel7.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 400, 35));

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton6.setText("Limpiar Filtros");
        jPanel7.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 65, 200, 40));

        jPanel2.add(jPanel7, java.awt.BorderLayout.NORTH);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Producto", "Categoría", "Stock Actual", "Stock Mínimo", "Última Actualización", "Ubicación", "Estado"
            }
        ));
        jTable3.setRowHeight(30);
        jScrollPane4.setViewportView(jTable3);

        jPanel2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Inventario", jPanel2);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel23.setText("Tipo de movimiento");
        jPanel8.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los movimientos", "Entrada", "Salida", "Ajuste" }));
        jPanel8.add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 300, 35));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel24.setText("Producto");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, -1));

        jComboBox10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los productos" }));
        jPanel8.add(jComboBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 300, 35));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel25.setText("Fecha inicial");
        jPanel8.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jDateChooser3.setDateFormatString("dd/MM/yyyy");
        jDateChooser3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel8.add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel26.setText("Fecha final");
        jPanel8.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, -1));

        jDateChooser4.setDateFormatString("dd/MM/yyyy");
        jDateChooser4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel8.add(jDateChooser4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 300, 35));

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton5.setText("Filtrar");
        jPanel8.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 165, 150, 40));

        jPanel4.add(jPanel8, java.awt.BorderLayout.NORTH);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Tipo", "Producto", "Cantidad", "Usuario", "Referencia", "Observaciones"
            }
        ));
        jTable4.setRowHeight(30);
        jScrollPane5.setViewportView(jTable4);

        jPanel4.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Movimientos", jPanel4);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel12.setText("Código de orden");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel9.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 400, 35));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel13.setText("Proveedor");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar proveedor" }));
        jPanel9.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 400, 35));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel16.setText("Almacén destino");
        jPanel9.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jComboBox6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar almacén", "Almacén Principal", "Almacén Secundario" }));
        jPanel9.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 400, 35));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel14.setText("Fecha esperada");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        jDateChooser2.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel9.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 400, 35));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel15.setText("Detalle de Productos");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel17.setText("Producto");
        jPanel9.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jComboBox7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar producto" }));
        jPanel9.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 350, 35));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel20.setText("Cantidad entregada");
        jPanel9.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, -1, -1));

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        jPanel9.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 200, 35));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel21.setText("Precio unitario");
        jPanel9.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, -1, -1));

        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        jPanel9.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 200, 35));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel18.setText("Forma de entrega");
        jPanel9.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        jComboBox8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidad", "Caja", "Paquete", "Palet" }));
        jPanel9.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 350, 35));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel22.setText("Cantidad por unidad");
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, -1, -1));

        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jPanel9.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, 200, 35));

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton2.setText("Añadir Producto");
        jPanel9.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 415, 200, 40));

        jPanel3.add(jPanel9, java.awt.BorderLayout.NORTH);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Producto", "Forma Entrega", "Cant. x Unidad", "Cant. Entregada", "Total Unidades", "Precio Unitario", "Subtotal", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(30);
        jScrollPane2.setViewportView(jTable2);

        jPanel6.setLayout(new java.awt.BorderLayout());
        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jScrollPane3.setViewportView(jPanel6);

        jPanel3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton4.setText("Registrar Compra");
        jPanel10.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 220, 45));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton3.setText("Limpiar Compra");
        jPanel10.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 220, 45));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("Total Items: 0");
        jPanel10.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 150, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Subtotal: $0.00");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 150, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(41, 128, 185));
        jLabel29.setText("Total: $0.00");
        jPanel10.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 25, 150, -1));

        jPanel3.add(jPanel10, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("Órdenes de Compra", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // Hacer la interfaz responsiva
        ajustarComponentesResponsivos();
    }//GEN-LAST:event_formComponentResized

    private void ajustarComponentesResponsivos() {
        // Ajustar componentes según el tamaño de la ventana
        int ancho = getWidth();
        int alto = getHeight();
        
        // Puedes agregar lógica para ajustar componentes según el tamaño
        if (ancho < 1200) {
            jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 16));
        } else {
            jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vista_inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vista_inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vista_inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vista_inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FlatLightLaf.setup();
                new vista_inventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem_seleccionar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
private javax.swing.JTextField jTextField8=new JTextField();
    // End of variables declaration//GEN-END:variables

}