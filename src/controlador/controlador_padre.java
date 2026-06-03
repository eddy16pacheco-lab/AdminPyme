package controlador;

import com.formdev.flatlaf.FlatLightLaf;
import consultas_bd.conexion_bd;
import consultas_bd.sql_consulta;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelo.cliente;
import modelo.persona;
import modelo.producto;
import validaciones.validaciones.otros.BCVConnector;
import validaciones.validaciones.otros.validar_componente;

public abstract class controlador_padre extends validar_componente{
   
/*   protected insersiones in=new insersiones();
   protected actualizaciones ac=new actualizaciones();
   protected consultas co=new consultas();
   protected eliminaciones el=new eliminaciones();*/
   protected void mostrar(){
   }

    protected void iniciar(JInternalFrame vista, String title, String titulo, String mensaje) {
        vista.setTitle(title);
        mostrarBarraProgreso(titulo, mensaje, vista);
        mostrar();

    }
   private void tema(){
       FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 50);
        UIManager.put("Button.arc", 150);
   }
    protected void iniciar(JFrame vista,String title){
   tema();
       vista.setTitle(title);
   vista.setVisible(true);
   vista.setLocationRelativeTo(null);
   mostrar();
   }
   protected void agregar(){
   }
   protected void actualizar(){
   }
   protected void eliminar(){
     }
   protected void limpiar_todo(){
   }
   protected void iniciar(){
   tema();
   }

    protected void ajustarImagenALabel(JLabel label, String imagePath) {
        // Cargar la imagen original
        ImageIcon iconoOriginal = new ImageIcon(imagePath);
        
        // Obtener las dimensiones del JLabel
        int anchoLabel = label.getWidth();
        int altoLabel = label.getHeight();
        
        // Si el label aún no tiene dimensiones, usar dimensiones por defecto
        if (anchoLabel == 0 || altoLabel == 0) {
            anchoLabel = label.getPreferredSize().width;
            altoLabel = label.getPreferredSize().height;
        }
        
        // Redimensionar la imagen
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(anchoLabel, altoLabel, Image.SCALE_SMOOTH);
        
        // Establecer la imagen en el JLabel
        label.setIcon(new ImageIcon(imagenEscalada));
    }
    protected void cerrar(){
    }
    // ... otros métodos existentes ...
    
    /**
     * Método reutilizable para mostrar barra de progreso
     * @param titulo Título de la ventana de progreso
     * @param mensaje Mensaje que se muestra en la barra
     * @param vista La vista que se mostrará después de la carga
     */
    protected void mostrarBarraProgreso(String titulo, String mensaje, JInternalFrame vista) {
        JDialog dialog = new JDialog();
        dialog.setTitle(titulo);
        dialog.setModal(false);
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString(mensaje);
        progressBar.setStringPainted(true);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(progressBar, BorderLayout.CENTER);
        
        dialog.add(panel);
        dialog.setVisible(true);
        
        new Thread(() -> {
            try {
                // Tiempo de carga simulado
                Thread.sleep(1000);
                SwingUtilities.invokeLater(() -> {
                    dialog.dispose();
                    if (vista != null) {
                        vista.setVisible(true);
                    }
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    dialog.dispose();
                    if (vista != null) {
                        vista.setVisible(true);
                    }
                });
            }
        }).start();
    }
    

public void limitarCaracteresConContador(JTextField textField, int maxLength, javax.swing.JLabel contadorLabel, String tipoValidacion) {
    if (contadorLabel != null) {
    }
    if(textField.getText().length() == maxLength){
          contadorLabel.setText("Limite alcanzado");
  
    }
    
    // Aplicar el DocumentFilter con validación del primer carácter
    if (textField.getDocument() instanceof AbstractDocument) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                int newLength = currentText.length() - length + text.length();
                
                // Validar según el tipo
                if (esEntradaValida(text, offset, currentText, length, tipoValidacion) && newLength <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
            
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) 
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                int currentLength = fb.getDocument().getLength();
                
                // Validar según el tipo
                if (esEntradaValida(text, offset, currentText, 0, tipoValidacion) && 
                    currentLength + text.length() <= maxLength) {
                    super.insertString(fb, offset, text, attrs);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    
    // Agregar listener para actualizar el contador
    textField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            actualizarContador();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            actualizarContador();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
            actualizarContador();
        }
        
        private void actualizarContador() {
            if (contadorLabel != null) {
                int caracteresRestantes = maxLength - textField.getText().length();
                contadorLabel.setText("");
                
                // Cambiar color cuando queden pocos caracteres
                if (caracteresRestantes <= 5) {
                    contadorLabel.setForeground(java.awt.Color.RED);
                } else if (caracteresRestantes <= 10) {
                    contadorLabel.setForeground(java.awt.Color.ORANGE);
                } else {
                    contadorLabel.setForeground(java.awt.Color.BLACK);
                }
            }
        } 
    });
}

/**
 * Método auxiliar para validar la entrada según el tipo
 */
private boolean esEntradaValida(String texto, int offset, String textoActual, int longitudReemplazo, String tipo) {
    if (texto.isEmpty()) return true; // Permitir borrado
    
    // Si es el primer carácter (offset == 0 y el campo está vacío o se está reemplazando desde el inicio)
    boolean esPrimerCaracter = (offset == 0 && textoActual.isEmpty()) || 
                              (offset == 0 && longitudReemplazo > 0);
    
    switch (tipo.toLowerCase()) {
        case "numero":
            // Validar que solo sean números
            if (!texto.matches("\\d*")) return false;
            
            // Para el primer carácter, no permitir cero
            if (esPrimerCaracter && texto.equals("0")) {
                return false;
            }
            return true;
            
        case "letra":
            // Validar que solo sean letras (incluye espacios y acentos)
            if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")) return false;
            
            // Para el primer carácter, no permitir espacio
            if (esPrimerCaracter && texto.equals(" ")) {
                return false;
            }
            return true;
            
        case "general":
        default:
            // Para tipo general, no hay restricciones especiales en el primer carácter
            return true;
    }
}

}


