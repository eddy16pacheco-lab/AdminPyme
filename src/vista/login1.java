package vista;

import java.awt.Color;
import java.awt.Dimension;


public class login1 extends javax.swing.JFrame {

    public login1() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(new Dimension(1920, 1080));
        this.setExtendedState(MAXIMIZED_BOTH);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_usuario = new javax.swing.JTextField();
        jPasswordFie_contraseña = new javax.swing.JPasswordField();
        jLabel_men_usu = new javax.swing.JLabel();
        jLabel_men_con = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton_iniciar_sesión = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_men_olv_con = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel_salir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(206, 222, 244));
        jPanel1.setMaximumSize(new java.awt.Dimension(1366, 768));
        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel2.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(130, 158, 188));
        jPanel3.setPreferredSize(new java.awt.Dimension(800, 200));
        jPanel3.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Iniciar Sesión");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(225, 87, 198, 43);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/acceso.png"))); // NOI18N
        jPanel3.add(jLabel2);
        jLabel2.setBounds(476, 50, 102, 102);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 0, 800, 200);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 26)); // NOI18N
        jLabel3.setText("Usuario:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(241, 293, 110, 38);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 26)); // NOI18N
        jLabel4.setText("Contraseña:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(241, 390, 160, 38);

        jTextField_usuario.setText("eddy_199");
        jPanel2.add(jTextField_usuario);
        jTextField_usuario.setBounds(443, 294, 200, 35);

        jPasswordFie_contraseña.setText("eddy_199");
        jPanel2.add(jPasswordFie_contraseña);
        jPasswordFie_contraseña.setBounds(443, 391, 200, 35);

        jLabel_men_usu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_men_usu.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_men_usu.setText("Ingrese su nombre de usuario");
        jLabel_men_usu.setPreferredSize(new java.awt.Dimension(185, 20));
        jPanel2.add(jLabel_men_usu);
        jLabel_men_usu.setBounds(443, 338, 220, 20);

        jLabel_men_con.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.add(jLabel_men_con);
        jLabel_men_con.setBounds(443, 434, 240, 20);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Editada sin fondo.png"))); // NOI18N
        jPanel2.add(jLabel5);
        jLabel5.setBounds(61, 278, 170, 170);

        jButton_iniciar_sesión.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton_iniciar_sesión.setText("Entrar");
        jButton_iniciar_sesión.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_iniciar_sesión.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton_iniciar_sesiónFocusGained(evt);
            }
        });
        jButton_iniciar_sesión.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_iniciar_sesiónMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_iniciar_sesiónMouseExited(evt);
            }
        });
        jButton_iniciar_sesión.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_iniciar_sesiónActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_iniciar_sesión);
        jButton_iniciar_sesión.setBounds(348, 498, 105, 51);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/usuario.gif"))); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(646, 294, 35, 35);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/verificacion-de-dos-pasos.gif"))); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(646, 390, 35, 35);

        jLabel_men_olv_con.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_men_olv_con.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_men_olv_con.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_men_olv_con.setText("Olvide mi contraseña");
        jLabel_men_olv_con.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_men_olv_con.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_men_olv_conMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_men_olv_conMouseExited(evt);
            }
        });
        jPanel2.add(jLabel_men_olv_con);
        jLabel_men_olv_con.setBounds(580, 540, 190, 30);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator1);
        jSeparator1.setBounds(580, 570, 190, 20);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 84, -1, -1));

        jLabel_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/cerrar-sesion.png"))); // NOI18N
        jLabel_salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_salirMouseClicked(evt);
            }
        });
        jLabel_salir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jLabel_salirKeyTyped(evt);
            }
        });
        jPanel1.add(jLabel_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 14, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1366, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_salirMouseClicked
 
    }//GEN-LAST:event_jLabel_salirMouseClicked

    private void jLabel_salirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel_salirKeyTyped
        //this.dispose();
    }//GEN-LAST:event_jLabel_salirKeyTyped

    private void jLabel_men_olv_conMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_men_olv_conMouseEntered
    }//GEN-LAST:event_jLabel_men_olv_conMouseEntered

    private void jLabel_men_olv_conMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_men_olv_conMouseExited
    }//GEN-LAST:event_jLabel_men_olv_conMouseExited

    private void jButton_iniciar_sesiónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_iniciar_sesiónActionPerformed
    }//GEN-LAST:event_jButton_iniciar_sesiónActionPerformed

    private void jButton_iniciar_sesiónMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_iniciar_sesiónMouseEntered
jButton_iniciar_sesión.setBackground(new Color(90,142,179));
jButton_iniciar_sesión.setForeground(Color.white);
    }//GEN-LAST:event_jButton_iniciar_sesiónMouseEntered

    private void jButton_iniciar_sesiónMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_iniciar_sesiónMouseExited
jButton_iniciar_sesión.setBackground(new Color(255,255,255));
jButton_iniciar_sesión.setForeground(Color.black);
    }//GEN-LAST:event_jButton_iniciar_sesiónMouseExited

    private void jButton_iniciar_sesiónFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton_iniciar_sesiónFocusGained
jButton_iniciar_sesión.setBackground(new Color(90,142,179));
jButton_iniciar_sesión.setForeground(Color.white);
    }//GEN-LAST:event_jButton_iniciar_sesiónFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_iniciar_sesión;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel_men_con;
    public javax.swing.JLabel jLabel_men_olv_con;
    public javax.swing.JLabel jLabel_men_usu;
    public javax.swing.JLabel jLabel_salir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPasswordField jPasswordFie_contraseña;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JTextField jTextField_usuario;
    // End of variables declaration//GEN-END:variables
}
