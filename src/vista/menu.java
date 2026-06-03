package vista;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

public class menu extends javax.swing.JFrame {

    public menu() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("MENÚ");
        this.setSize(new Dimension(1366, 768));
        this.setExtendedState(6);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_usu = new javax.swing.JLabel();
        jDesktopPane = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_usuario = new javax.swing.JMenu();
        jMenuItem_gestion_usuario = new javax.swing.JMenuItem();
        jMenu_cliente = new javax.swing.JMenu();
        jMenuItem_gestion_cliente = new javax.swing.JMenuItem();
        jMenu_orden_compra = new javax.swing.JMenu();
        jMenuItem_gestion_proveedores = new javax.swing.JMenuItem();
        jMenuItem_orden_de_compra = new javax.swing.JMenuItem();
        jMenu_producto = new javax.swing.JMenu();
        jMenuItem_almacen = new javax.swing.JMenuItem();
        jMenuItem_nuevo_registrar = new javax.swing.JMenuItem();
        jMenuItem_categoria = new javax.swing.JMenuItem();
        jMenu_venta = new javax.swing.JMenu();
        jMenuItem_vender = new javax.swing.JMenuItem();
        jMenuItem_gestion_ventas = new javax.swing.JMenuItem();
        jMenu_reporte = new javax.swing.JMenu();
        jMenuItem_generar_reporte_de_compras = new javax.swing.JMenuItem();
        jMenuItem_generar_reporte_pagos = new javax.swing.JMenuItem();
        jMenuItem_generar_reporte_ventas = new javax.swing.JMenuItem();
        jMenuItem_generar_reporte_de_perdidas = new javax.swing.JMenuItem();
        jMenuItem_generar_reporte_de_ganacias = new javax.swing.JMenuItem();
        jMenu_movimiento = new javax.swing.JMenu();
        jMenuItem_generar_movimiento_cuenta = new javax.swing.JMenuItem();
        jMenuItem_gestion_pago_nomina = new javax.swing.JMenuItem();
        jMenu_cerrar = new javax.swing.JMenu();
        jMenuItem_salir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jLabel_usu.setBackground(new java.awt.Color(230, 235, 240));
        jLabel_usu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_usu.setForeground(new java.awt.Color(230, 235, 240));
        jLabel_usu.setText("2");

        jDesktopPane.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane.setPreferredSize(new java.awt.Dimension(1366, 768));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/v3_1.png"))); // NOI18N
        jDesktopPane.add(jLabel1);
        jLabel1.setBounds(299, -30, 768, 768);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("?");
        jLabel2.setToolTipText("Manual de Usuario");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jDesktopPane.add(jLabel2);
        jLabel2.setBounds(1310, 0, 60, 50);

        jMenuBar1.setBorder(null);
        jMenuBar1.setMaximumSize(new java.awt.Dimension(1920, 60));
        jMenuBar1.setMinimumSize(new java.awt.Dimension(1920, 60));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(1366, 63));

        jMenu_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/usuario (4).png"))); // NOI18N
        jMenu_usuario.setText("Usuarios");
        jMenu_usuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_usuario.setIconTextGap(13);
        jMenu_usuario.setPreferredSize(new java.awt.Dimension(150, 22));

        jMenuItem_gestion_usuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_gestion_usuario.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_gestion_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/agregar-usuario.png"))); // NOI18N
        jMenuItem_gestion_usuario.setText("Gestión de usuarios");
        jMenuItem_gestion_usuario.setPreferredSize(new java.awt.Dimension(240, 32));
        jMenuItem_gestion_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestion_usuarioActionPerformed(evt);
            }
        });
        jMenu_usuario.add(jMenuItem_gestion_usuario);

        jMenuBar1.add(jMenu_usuario);

        jMenu_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/usuario-de-computadora-portatil.png"))); // NOI18N
        jMenu_cliente.setText("Clientes");
        jMenu_cliente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_cliente.setIconTextGap(13);
        jMenu_cliente.setPreferredSize(new java.awt.Dimension(140, 22));

        jMenuItem_gestion_cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_gestion_cliente.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_gestion_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/usuario-web.png"))); // NOI18N
        jMenuItem_gestion_cliente.setText("Gestión de clientes");
        jMenuItem_gestion_cliente.setPreferredSize(new java.awt.Dimension(224, 32));
        jMenu_cliente.add(jMenuItem_gestion_cliente);

        jMenuBar1.add(jMenu_cliente);

        jMenu_orden_compra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/revision-de-producto.png"))); // NOI18N
        jMenu_orden_compra.setText("Compra");
        jMenu_orden_compra.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_orden_compra.setPreferredSize(new java.awt.Dimension(150, 32));

        jMenuItem_gestion_proveedores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_gestion_proveedores.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_gestion_proveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/producto (1).png"))); // NOI18N
        jMenuItem_gestion_proveedores.setText("Proveedores");
        jMenuItem_gestion_proveedores.setPreferredSize(new java.awt.Dimension(224, 35));
        jMenu_orden_compra.add(jMenuItem_gestion_proveedores);

        jMenuItem_orden_de_compra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_orden_de_compra.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_orden_de_compra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/caja.png"))); // NOI18N
        jMenuItem_orden_de_compra.setText("Gestion de Compra");
        jMenuItem_orden_de_compra.setPreferredSize(new java.awt.Dimension(224, 35));
        jMenu_orden_compra.add(jMenuItem_orden_de_compra);

        jMenuBar1.add(jMenu_orden_compra);

        jMenu_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/servicio.png"))); // NOI18N
        jMenu_producto.setText("Gestión de productos");
        jMenu_producto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_producto.setPreferredSize(new java.awt.Dimension(240, 22));

        jMenuItem_almacen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_almacen.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_almacen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/producto (3).png"))); // NOI18N
        jMenuItem_almacen.setText("Almacén de productos");
        jMenuItem_almacen.setPreferredSize(new java.awt.Dimension(260, 35));
        jMenu_producto.add(jMenuItem_almacen);

        jMenuItem_nuevo_registrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_nuevo_registrar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_nuevo_registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/agregar-producto (1).png"))); // NOI18N
        jMenuItem_nuevo_registrar.setText("Nuevos productos");
        jMenuItem_nuevo_registrar.setPreferredSize(new java.awt.Dimension(260, 35));
        jMenu_producto.add(jMenuItem_nuevo_registrar);

        jMenuItem_categoria.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem_categoria.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/producto-digital.png"))); // NOI18N
        jMenuItem_categoria.setText("Gestión de categorias");
        jMenuItem_categoria.setPreferredSize(new java.awt.Dimension(260, 35));
        jMenuItem_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_categoriaActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem_categoria);

        jMenuBar1.add(jMenu_producto);

        jMenu_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/bolsa-de-dinero.png"))); // NOI18N
        jMenu_venta.setText("Ventas");
        jMenu_venta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_venta.setIconTextGap(13);
        jMenu_venta.setPreferredSize(new java.awt.Dimension(130, 22));

        jMenuItem_vender.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_vender.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_vender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/terminal-de-punto-de-venta.png"))); // NOI18N
        jMenuItem_vender.setText("Registro de ventas");
        jMenuItem_vender.setPreferredSize(new java.awt.Dimension(235, 35));
        jMenuItem_vender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_venderActionPerformed(evt);
            }
        });
        jMenu_venta.add(jMenuItem_vender);

        jMenuItem_gestion_ventas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_gestion_ventas.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_gestion_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/dinero.png"))); // NOI18N
        jMenuItem_gestion_ventas.setText("Gestion de ventas");
        jMenuItem_gestion_ventas.setPreferredSize(new java.awt.Dimension(235, 35));
        jMenuItem_gestion_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestion_ventasActionPerformed(evt);
            }
        });
        jMenu_venta.add(jMenuItem_gestion_ventas);

        jMenuBar1.add(jMenu_venta);

        jMenu_reporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/informe-de-datos.png"))); // NOI18N
        jMenu_reporte.setText("Repórtes");
        jMenu_reporte.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_reporte.setIconTextGap(13);
        jMenu_reporte.setPreferredSize(new java.awt.Dimension(150, 22));

        jMenuItem_generar_reporte_de_compras.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_generar_reporte_de_compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/pedido.png"))); // NOI18N
        jMenuItem_generar_reporte_de_compras.setText("Generar reporte de compra");
        jMenuItem_generar_reporte_de_compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_generar_reporte_de_comprasActionPerformed(evt);
            }
        });
        jMenu_reporte.add(jMenuItem_generar_reporte_de_compras);

        jMenuItem_generar_reporte_pagos.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_generar_reporte_pagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/informe-de-caja.png"))); // NOI18N
        jMenuItem_generar_reporte_pagos.setText("Generar reporte de pago");
        jMenuItem_generar_reporte_pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_generar_reporte_pagosActionPerformed(evt);
            }
        });
        jMenu_reporte.add(jMenuItem_generar_reporte_pagos);

        jMenuItem_generar_reporte_ventas.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_generar_reporte_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/informe-financiero.png"))); // NOI18N
        jMenuItem_generar_reporte_ventas.setText("Generar reporte de ventas");
        jMenu_reporte.add(jMenuItem_generar_reporte_ventas);

        jMenuItem_generar_reporte_de_perdidas.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_generar_reporte_de_perdidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/perdida-de-dinero.png"))); // NOI18N
        jMenuItem_generar_reporte_de_perdidas.setText("Generar reporte de perdidas");
        jMenu_reporte.add(jMenuItem_generar_reporte_de_perdidas);

        jMenuItem_generar_reporte_de_ganacias.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_generar_reporte_de_ganacias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/ingreso.png"))); // NOI18N
        jMenuItem_generar_reporte_de_ganacias.setText("Generar reporte de ganacias");
        jMenu_reporte.add(jMenuItem_generar_reporte_de_ganacias);

        jMenuBar1.add(jMenu_reporte);

        jMenu_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/libro-de-registro.png"))); // NOI18N
        jMenu_movimiento.setText("Movimiento Contable");
        jMenu_movimiento.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_movimiento.setPreferredSize(new java.awt.Dimension(250, 31));

        jMenuItem_generar_movimiento_cuenta.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_generar_movimiento_cuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/contador.png"))); // NOI18N
        jMenuItem_generar_movimiento_cuenta.setText("Gestión Contable");
        jMenuItem_generar_movimiento_cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_generar_movimiento_cuentaActionPerformed(evt);
            }
        });
        jMenu_movimiento.add(jMenuItem_generar_movimiento_cuenta);

        jMenuItem_gestion_pago_nomina.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem_gestion_pago_nomina.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_gestion_pago_nomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/pago.png"))); // NOI18N
        jMenuItem_gestion_pago_nomina.setText("Pagos de Nomina");
        jMenuItem_gestion_pago_nomina.setPreferredSize(new java.awt.Dimension(240, 32));
        jMenu_movimiento.add(jMenuItem_gestion_pago_nomina);

        jMenuBar1.add(jMenu_movimiento);

        jMenu_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/cerrar-sesion_1.png"))); // NOI18N
        jMenu_cerrar.setText("Cerrar");
        jMenu_cerrar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_cerrar.setIconTextGap(13);
        jMenu_cerrar.setName(""); // NOI18N
        jMenu_cerrar.setPreferredSize(new java.awt.Dimension(135, 22));

        jMenuItem_salir.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/cerrar-sesion(2).png"))); // NOI18N
        jMenuItem_salir.setText("Salir");
        jMenuItem_salir.setPreferredSize(new java.awt.Dimension(130, 32));
        jMenuItem_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_salirActionPerformed(evt);
            }
        });
        jMenu_cerrar.add(jMenuItem_salir);

        jMenuBar1.add(jMenu_cerrar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(847, 847, 847)
                    .addComponent(jLabel_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(294, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(491, 491, 491)
                    .addComponent(jLabel_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(179, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_salirActionPerformed
    }//GEN-LAST:event_jMenuItem_salirActionPerformed

    private void jMenuItem_venderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_venderActionPerformed
    }//GEN-LAST:event_jMenuItem_venderActionPerformed

    private void jMenuItem_gestion_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestion_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_gestion_ventasActionPerformed

    private void jMenuItem_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_categoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_categoriaActionPerformed

    private void jMenuItem_gestion_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestion_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_gestion_usuarioActionPerformed

    private void jMenuItem_generar_reporte_de_comprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_generar_reporte_de_comprasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_generar_reporte_de_comprasActionPerformed

    private void jMenuItem_generar_reporte_pagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_generar_reporte_pagosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_generar_reporte_pagosActionPerformed

    private void jMenuItem_generar_movimiento_cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_generar_movimiento_cuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_generar_movimiento_cuentaActionPerformed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        try {
            File archivo=new File("src\\Manual de usuario.pdf");
       if(archivo.exists()){
           Desktop.getDesktop().open(archivo);
       }
                    } catch (IOException e) {
            System.out.println(""+e);
                    }
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
jLabel2.setForeground(new Color(90,142,179));

    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
jLabel2.setForeground(Color.black);
    }//GEN-LAST:event_jLabel2MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane jDesktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel_usu;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenuItem jMenuItem_almacen;
    public javax.swing.JMenuItem jMenuItem_categoria;
    public javax.swing.JMenuItem jMenuItem_generar_movimiento_cuenta;
    public javax.swing.JMenuItem jMenuItem_generar_reporte_de_compras;
    public javax.swing.JMenuItem jMenuItem_generar_reporte_de_ganacias;
    public javax.swing.JMenuItem jMenuItem_generar_reporte_de_perdidas;
    public javax.swing.JMenuItem jMenuItem_generar_reporte_pagos;
    public javax.swing.JMenuItem jMenuItem_generar_reporte_ventas;
    public javax.swing.JMenuItem jMenuItem_gestion_cliente;
    public javax.swing.JMenuItem jMenuItem_gestion_pago_nomina;
    public javax.swing.JMenuItem jMenuItem_gestion_proveedores;
    public javax.swing.JMenuItem jMenuItem_gestion_usuario;
    public javax.swing.JMenuItem jMenuItem_gestion_ventas;
    public javax.swing.JMenuItem jMenuItem_nuevo_registrar;
    public javax.swing.JMenuItem jMenuItem_orden_de_compra;
    public javax.swing.JMenuItem jMenuItem_salir;
    public javax.swing.JMenuItem jMenuItem_vender;
    public javax.swing.JMenu jMenu_cerrar;
    public javax.swing.JMenu jMenu_cliente;
    public javax.swing.JMenu jMenu_movimiento;
    public javax.swing.JMenu jMenu_orden_compra;
    public javax.swing.JMenu jMenu_producto;
    public javax.swing.JMenu jMenu_reporte;
    public javax.swing.JMenu jMenu_usuario;
    public javax.swing.JMenu jMenu_venta;
    // End of variables declaration//GEN-END:variables
}
