package controlador.modulo_venta;

import consultas_bd.consulta_detalle_venta;
import controlador.controlador_padre;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import modelo.ventas;
import vista.formularios.FormularioPagoNuevo;
import vista.gestion.GestionarVentasContable;

public class controlador_gestion_ventas extends controlador_padre implements ActionListener, MouseListener, KeyListener {

    private LocalDate fechaActual = LocalDate.now();
    String fecha = fechaActual.toString();
    
    // CONSTANTES PARA VALIDACIONES
    private static final int MAX_LONGITUD_BUSQUEDA = 50;
    private static final int MAX_DIAS_RANGO_FECHAS = 365; // Máximo 1 año de rango
    private static final int MIN_ANIO_PERMITIDO = 2020; // Año mínimo permitido para fechas
    
    private detalle_de_ventas modelo;
    private GestionarVentasContable vista;
    private consulta_detalle_venta sql = new consulta_detalle_venta();
 
    public controlador_gestion_ventas(detalle_de_ventas modelo, GestionarVentasContable vista) {
        this.modelo = modelo;
        this.vista = vista;
        iniciar(vista, "Cargando Gestión de Ventas", "Cargando gestión de ventas...", "Modulo de ventas");
        this.vista.ventasTable.addMouseListener(this);
        this.vista.buscarField.addKeyListener(this);
        this.vista.jMenuItem_pagar.addActionListener(this);
        this.vista.jMenuItem_ver_detalle.addActionListener(this);
        this.vista.fechaHastaSpinner.addMouseListener(this);
        this.vista.filtrarButton.addActionListener(this);
        
        // Configurar limitaciones de campos
        configurarValidaciones();
    }
    
    /**
     * Configura las validaciones y limitaciones de los campos
     */
    private void configurarValidaciones() {
        // Limitar longitud del campo de búsqueda
        vista.buscarField.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) 
                    throws javax.swing.text.BadLocationException {
                if (str == null) return;
                
                if ((getLength() + str.length()) <= MAX_LONGITUD_BUSQUEDA) {
                    super.insertString(offs, str, a);
                } else {
                    // Solo permitir insertar hasta el máximo
                    int espacioDisponible = MAX_LONGITUD_BUSQUEDA - getLength();
                    if (espacioDisponible > 0) {
                        super.insertString(offs, str.substring(0, espacioDisponible), a);
                    }
                    // Mostrar advertencia si se excede
                    if (getLength() >= MAX_LONGITUD_BUSQUEDA) {
                        mostrarAdvertenciaCampoLleno("búsqueda", MAX_LONGITUD_BUSQUEDA);
                    }
                }
            }
        });
    }

    @Override
    protected void mostrar() {
        sql.consultar_listado_ventas(vista.ventasTable, "", 1, null, null);
        String[] datos = sql.obtenerVentasSemana();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jMenuItem_pagar) {
            procesarPago();
        }
        if (e.getSource() == vista.filtrarButton) {
            filtrarVentas();
        }
        if (e.getSource() == vista.jMenuItem_ver_detalle) {
            mostrarDetalleVenta();
        }
    }
    
    /**
     * Procesa el pago de una venta con validaciones
     */
    private void procesarPago() {
        if (validarSeleccionVenta()) {
            descripcion_pago modelo = new descripcion_pago();
            modelo.setVenta(this.modelo.getN_venta());
            FormularioPagoNuevo vista = new FormularioPagoNuevo();
            controlador_registro_pago ctrl = new controlador_registro_pago(modelo, vista);
            ctrl.iniciar();
            this.vista.dispose();
        }
    }
    
    /**
     * Filtra las ventas con validaciones de fechas
     */
    private void filtrarVentas() {
        if (validarFechasFiltro()) {
            String fechaIni = "" + new java.sql.Date(vista.fechaDesdeSpinner.getDate().getTime());
            String fechaFin = "" + new java.sql.Date(vista.fechaHastaSpinner.getDate().getTime());
            sql.consultar_listado_ventas(vista.ventasTable, "", 3, fechaIni, fechaFin);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == vista.ventasTable) {
            int fila = vista.ventasTable.getSelectedRow();
            
            // Validar que se haya seleccionado una fila válida
            if (fila < 0 || fila >= vista.ventasTable.getRowCount()) {
                return;
            }
            
            // Validar que la celda no sea nula
            if (vista.ventasTable.getValueAt(fila, 0) == null) {
                JOptionPane.showMessageDialog(vista, 
                    "La venta seleccionada no es válida", 
                    "Error de selección", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                ventas ve = new ventas();
                ve.setId_venta(Integer.parseInt(vista.ventasTable.getValueAt(fila, 0).toString()));
                
                if (e.getButton() == MouseEvent.BUTTON1) {
                    modelo.setN_venta(ve);
                }
                modelo.setN_venta(ve);
                
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (fila >= 0) {
                        vista.JPopuMenu1.show(vista.ventasTable, e.getX(), e.getY());
                        ventas venta = new ventas();
                        venta.setId_venta(Integer.parseInt(vista.ventasTable.getValueAt(fila, 0).toString()));
                        modelo.setN_venta(venta);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, 
                    "Error: El ID de venta no tiene un formato válido", 
                    "Error de formato", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, 
                    "Error inesperado al procesar la venta: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.buscarField) {
            String busqueda = vista.buscarField.getText().trim();
            
            // Validar longitud máxima
            if (busqueda.length() > MAX_LONGITUD_BUSQUEDA) {
                busqueda = busqueda.substring(0, MAX_LONGITUD_BUSQUEDA);
                vista.buscarField.setText(busqueda);
                mostrarAdvertenciaCampoLleno("búsqueda", MAX_LONGITUD_BUSQUEDA);
            }
            
            // Validar caracteres no permitidos (opcional)
            if (!busqueda.isEmpty() && contieneCaracteresInvalidos(busqueda)) {
                JOptionPane.showMessageDialog(vista, 
                    "La búsqueda contiene caracteres no permitidos", 
                    "Búsqueda inválida", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!busqueda.isEmpty() && vista.fechaDesdeSpinner.getDate() != null && vista.fechaHastaSpinner.getDate() != null) {
                String fechaIni = "" + new java.sql.Date(vista.fechaDesdeSpinner.getDate().getTime());
                String fechaFin = "" + new java.sql.Date(vista.fechaHastaSpinner.getDate().getTime());
                sql.consultar_listado_ventas(vista.ventasTable, busqueda, 4, fechaIni, fechaFin);
            } else if (!busqueda.isEmpty()) {
                sql.consultar_listado_ventas(vista.ventasTable, busqueda, 5, null, null);
            } else if (busqueda.isEmpty()) {
                sql.consultar_listado_ventas(vista.ventasTable, "", 1, null, null);
            }
        }
    }
    
    /**
     * Valida la selección de una venta en la tabla
     */
    private boolean validarSeleccionVenta() {
        int fila = vista.ventasTable.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, 
                "Por favor, seleccione una venta de la tabla", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar que la venta tenga un ID válido
        if (vista.ventasTable.getValueAt(fila, 0) == null) {
            JOptionPane.showMessageDialog(vista, 
                "La venta seleccionada no tiene un ID válido", 
                "Error de datos", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Valida las fechas del filtro
     */
    private boolean validarFechasFiltro() {
        if (vista.fechaDesdeSpinner.getDate() == null) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una fecha de inicio", 
                "Fecha requerida", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.fechaHastaSpinner.getDate() == null) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una fecha final", 
                "Fecha requerida", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        Date fechaDesde = vista.fechaDesdeSpinner.getDate();
        Date fechaHasta = vista.fechaHastaSpinner.getDate();
        
        // Validar que la fecha desde no sea mayor que la fecha hasta
        if (fechaDesde.after(fechaHasta)) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha de inicio no puede ser posterior a la fecha final", 
                "Rango de fechas inválido", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que el rango de fechas no sea demasiado amplio
        long diferenciaMs = fechaHasta.getTime() - fechaDesde.getTime();
        long diferenciaDias = diferenciaMs / (1000 * 60 * 60 * 24);
        
        if (diferenciaDias > MAX_DIAS_RANGO_FECHAS) {
            JOptionPane.showMessageDialog(vista, 
                "El rango de fechas no puede ser mayor a " + MAX_DIAS_RANGO_FECHAS + " días", 
                "Rango de fechas excesivo", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar que las fechas no sean demasiado antiguas
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, MIN_ANIO_PERMITIDO);
        Date fechaMinima = cal.getTime();
        
        if (fechaDesde.before(fechaMinima)) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha no puede ser anterior a " + MIN_ANIO_PERMITIDO, 
                "Fecha no válida", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Muestra advertencia cuando un campo alcanza su límite
     */
    private void mostrarAdvertenciaCampoLleno(String nombreCampo, int maximo) {
        JOptionPane.showMessageDialog(vista, 
            "El campo de " + nombreCampo + " ha alcanzado el límite máximo de " + maximo + " caracteres", 
            "Límite alcanzado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Valida si una cadena contiene caracteres inválidos para búsqueda
     */
    private boolean contieneCaracteresInvalidos(String texto) {
        // Definir caracteres potencialmente peligrosos o no deseados
        String caracteresInvalidos = "[]{}()<>|&^%$#@!*+=/\\";
        for (char c : caracteresInvalidos.toCharArray()) {
            if (texto.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para mostrar el detalle completo de una venta seleccionada
     */
    private void mostrarDetalleVenta() {
        if (!validarSeleccionVenta()) {
            return;
        }
        
        int fila = vista.ventasTable.getSelectedRow();
        try {
            // Obtener el número de venta seleccionado
            String numeroVenta = vista.ventasTable.getValueAt(fila, 0).toString();
            
            // Validar formato del número de venta
            if (!esNumeroValido(numeroVenta)) {
                JOptionPane.showMessageDialog(vista, 
                    "El número de venta no tiene un formato válido", 
                    "Error de formato", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Consultar los detalles de la venta usando la vista listado_detalle_de_ventas
            String detalles = sql.obtenerDetalleVentaPorNumero(numeroVenta);
            
            // Validar que se obtuvieron detalles
            if (detalles == null || detalles.trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, 
                    "No se encontraron detalles para la venta seleccionada", 
                    "Sin detalles", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Mostrar en un JOptionPane
            JTextArea textArea = new JTextArea(15, 50);
            textArea.setText(detalles);
            textArea.setEditable(false);
            textArea.setCaretPosition(0);
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            
            JOptionPane.showMessageDialog(vista, scrollPane, 
                "Detalle de Venta #" + numeroVenta, 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, 
                "Error al cargar el detalle de la venta: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Valida si una cadena representa un número válido
     */
    private boolean esNumeroValidos(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Resto de métodos de la interfaz (sin cambios)
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}