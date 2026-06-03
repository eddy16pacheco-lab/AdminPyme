package informe_reporte;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import consultas_bd.sql_consulta;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.awt.Desktop;
import javax.swing.JOptionPane;

/**
 *
 * @author Eddy
 */
public class GeneradorFacturaPDF extends sql_consulta {
    
    private String empresaNombre = "Inversiones Frontera del Sabor C.G";
    private String sistemaLogo = "src\\imagen\\AdminPyme.jpg";
    private String telefonoEmpresa = "";
    private String emailEmpresa = "";
    private String direccionEmpresa = "Cauacugua, Venezuela";
    private double dolar;

    public double getDolar() {
        return dolar;
    }

    public void setDolar(double dolar) {
        this.dolar = dolar;
    }
    
    /**
     * Obtiene la ruta del escritorio del usuario
     */
    private String obtenerRutaEscritorio() {
        String escritorio = System.getProperty("user.home") + File.separator + "Desktop";
        // Para sistemas Linux/Unix
        if (!new File(escritorio).exists()) {
            escritorio = System.getProperty("user.home") + File.separator + "Escritorio";
        }
        return escritorio;
    }
    
    /**
     * Pregunta al usuario si desea abrir la factura usando JOptionPane
     */
    private boolean preguntarAbrirFactura() {
        int respuesta = JOptionPane.showConfirmDialog(
            null, 
            "¿Desea abrir la factura generada?", 
            "Abrir Factura", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return respuesta == JOptionPane.YES_OPTION;
    }
    
    /**
     * Muestra un mensaje de éxito con JOptionPane
     */
    private void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(
            null, 
            mensaje, 
            "Factura Generada", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Muestra un mensaje de error con JOptionPane
     */
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(
            null, 
            mensaje, 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Abre el archivo PDF con el programa predeterminado
     */
    private void abrirArchivoPDF(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    mostrarMensajeError("No se puede abrir el archivo automáticamente en este sistema.");
                }
            } else {
                mostrarMensajeError("El archivo no existe: " + filePath);
            }
        } catch (Exception e) {
            mostrarMensajeError("Error al abrir el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene los datos de la factura desde las vistas de la base de datos
     */
    public FacturaData obtenerDatosFactura(int idVenta) throws SQLException {
        FacturaData factura = new FacturaData();
        factura.setProductos(new ArrayList<>());
        factura.setPagos(new ArrayList<>());
        
        try {
            // Consulta para obtener datos de la venta desde la vista factura_venta
            String queryVenta = "SELECT * FROM factura_venta WHERE `Número de Factura` = ?";
            ps = con.EstablecerConexion().prepareStatement(queryVenta);
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            
            boolean primeraVez = true;
            double sumaSubtotales = 0.0;
            
            while (rs.next()) {
                if (primeraVez) {
                    // Configurar datos básicos de la factura
                    factura.setNumeroFactura(rs.getInt("Número de Factura"));
                    factura.setFecha(rs.getTimestamp("Fecha"));
                    factura.setCliente(rs.getString("Cliente"));
                    factura.setCedula(rs.getInt("Cédula"));
                    factura.setTelefono(rs.getString("Teléfono"));
                    factura.setVendedor(rs.getString("Vendedor"));
                    primeraVez = false;
                }
                
                // Agregar producto
                ProductoFactura producto = new ProductoFactura();
                producto.setNombre(rs.getString("Producto"));
                producto.setDescripcion(rs.getString("Descripción"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setPrecioUnitario(rs.getDouble("Precio Unitario"));
                producto.setSubtotal(rs.getDouble("Subtotal"));
                producto.setTipoVenta(rs.getString("Tipo de Venta"));
                producto.setEstado(rs.getString("Estado"));
                producto.setTotalProducto(rs.getDouble("Total a Pagar"));
                
                // Sumar al total de la venta
                sumaSubtotales += producto.getSubtotal();
                
                factura.getProductos().add(producto);
            }
            
            // Establecer el total de la venta como la suma de todos los subtotales
            factura.setTotalAPagar(sumaSubtotales);
            
            // Consulta para obtener datos de pagos desde la vista factura_pago
            String queryPagos = "SELECT * FROM factura_pago WHERE id_venta = ?";
            ps = con.EstablecerConexion().prepareStatement(queryPagos);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            
            double totalPagado = 0.0;
            while (rs.next()) {
                PagoFactura pago = new PagoFactura();
                pago.setTipoPago(rs.getString("tipo_de_pago"));
                pago.setReferencia(rs.getString("ref_pago"));
                pago.setMonto(rs.getDouble("monto_cancelado"));
                factura.getPagos().add(pago);
                
                // Sumar al total pagado
                totalPagado += pago.getMonto();
            }
            
            factura.setTotalPagado(totalPagado);
            
            // Calcular saldo pendiente
            factura.setSaldoPendiente(factura.getTotalAPagar() - factura.getTotalPagado());
            
        } catch (Exception e) {
            System.out.println("Error obteniendo datos de factura: " + e);
            mostrarMensajeError("Error obteniendo datos de factura: " + e.getMessage());
        }
        
        return factura;
    }
    
    /**
     * Convierte fecha a formato simplificado
     */
    private String formatearFecha(Date fecha) {
        if (fecha == null) return "N/A";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formato.format(fecha);
    }
    
    /**
     * Genera el PDF de la factura optimizado para impresoras microtérmicas
     */
    public void generarFacturaPDF(int idVenta) throws Exception {
        FacturaData factura = obtenerDatosFactura(idVenta);
        
        // Obtener ruta del escritorio
        String escritorio = obtenerRutaEscritorio();
        String outputPath = escritorio + File.separator + "factura_" + idVenta + ".pdf";
        
        // Tamaño reducido para impresoras microtérmicas (80mm de ancho)
        Document document = new Document(new Rectangle(226, 800), 3, 3, 5, 5);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        
        document.open();
        
        // Configuración de fuentes pequeñas
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 7);
        Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD);
        Font fontPequeño = new Font(Font.FontFamily.HELVETICA, 6);
        
        try {
            // Logo de la empresa (pequeño)
            Image logo = Image.getInstance(sistemaLogo);
            logo.scaleToFit(30, 30);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }
        
        // Encabezado compacto
        Paragraph tituloEmpresa = new Paragraph(empresaNombre, fontTitulo);
        tituloEmpresa.setAlignment(Element.ALIGN_CENTER);
        tituloEmpresa.setSpacingAfter(1f);
        document.add(tituloEmpresa);
        
        Paragraph infoEmpresa = new Paragraph(direccionEmpresa, fontPequeño);
        infoEmpresa.setAlignment(Element.ALIGN_CENTER);
        infoEmpresa.setSpacingAfter(3f);
        document.add(infoEmpresa);
        
        // Línea separadora
        document.add(new Chunk("\n"));
        
        // Información de la factura compacta
        PdfPTable infoFacturaTable = new PdfPTable(2);
        infoFacturaTable.setWidthPercentage(100);
        
        PdfPCell infoFacturaCell = new PdfPCell();
        infoFacturaCell.setBorder(Rectangle.NO_BORDER);
        infoFacturaCell.addElement(new Paragraph("FACTURA: " + factura.getNumeroFactura(), fontNegrita));
        infoFacturaCell.addElement(new Paragraph("FECHA: " + formatearFecha(factura.getFecha()), fontNormal));
        infoFacturaCell.addElement(new Paragraph("VENDEDOR: " + factura.getVendedor(), fontNormal));
        
        PdfPCell infoClienteCell = new PdfPCell();
        infoClienteCell.setBorder(Rectangle.NO_BORDER);
        infoClienteCell.addElement(new Paragraph("CLIENTE:", fontNegrita));
        infoClienteCell.addElement(new Paragraph(factura.getCliente(), fontNormal));
        infoClienteCell.addElement(new Paragraph("CI: " + factura.getCedula(), fontNormal));
        
        infoFacturaTable.addCell(infoFacturaCell);
        infoFacturaTable.addCell(infoClienteCell);
        document.add(infoFacturaTable);
        
        document.add(new Paragraph(" "));
        
        // Tabla de productos simplificada
        PdfPTable productosTable = new PdfPTable(4);
        productosTable.setWidthPercentage(100);
        productosTable.setWidths(new float[]{3, 1, 2, 2});
        
        // Encabezados simplificados
        agregarCeldaProducto(productosTable, "PRODUCTO", fontNegrita, true);
        agregarCeldaProducto(productosTable, "CANT", fontNegrita, true);
        agregarCeldaProducto(productosTable, "P.UNIT", fontNegrita, true);
        agregarCeldaProducto(productosTable, "TOTAL", fontNegrita, true);
        
        // Datos de productos
        for (ProductoFactura producto : factura.getProductos()) {
            // Limitar longitud del nombre
            String nombreProducto = producto.getNombre();
            if (nombreProducto.length() > 20) {
                nombreProducto = nombreProducto.substring(0, 17) + "...";
            }
            
            agregarCeldaProducto(productosTable, nombreProducto, fontNormal, false);
            agregarCeldaProducto(productosTable, String.valueOf(producto.getCantidad()), fontNormal, false);
            agregarCeldaProducto(productosTable, String.format("Bs%.0f", producto.getPrecioUnitario()), fontNormal, false);
            agregarCeldaProducto(productosTable, String.format("Bs%.0f", producto.getSubtotal()), fontNormal, false);
        }
        
        document.add(productosTable);
        document.add(new Paragraph(" "));
        
        // Tabla de pagos simplificada si existen
        if (!factura.getPagos().isEmpty()) {
            Paragraph tituloPagos = new Paragraph("PAGOS:", fontNegrita);
            tituloPagos.setSpacingAfter(2f);
            document.add(tituloPagos);
            
            PdfPTable pagosTable = new PdfPTable(3);
            pagosTable.setWidthPercentage(100);
            
            agregarCeldaProducto(pagosTable, "METODO", fontNegrita, true);
            agregarCeldaProducto(pagosTable, "REF", fontNegrita, true);
            agregarCeldaProducto(pagosTable, "MONTO", fontNegrita, true);
            
            for (PagoFactura pago : factura.getPagos()) {
                agregarCeldaProducto(pagosTable, pago.getTipoPago(), fontNormal, false);
                agregarCeldaProducto(pagosTable, 
                    pago.getReferencia() != null && pago.getReferencia().length() > 8 ? 
                    pago.getReferencia().substring(0, 6) + "..." : 
                    (pago.getReferencia() != null ? pago.getReferencia() : "N/A"), 
                    fontNormal, false);
                agregarCeldaProducto(pagosTable, String.format("Bs%.0f", pago.getMonto()), fontNormal, false);
            }
            
            document.add(pagosTable);
            document.add(new Paragraph(" "));
        }
        
        // Resumen compacto
        PdfPTable resumenTable = new PdfPTable(2);
        resumenTable.setWidthPercentage(100);
        
        // Tipo de cambio
        Paragraph tipoCambio = new Paragraph("Dólar: Bs." + String.format("%.2f", dolar), fontPequeño);
        tipoCambio.setAlignment(Element.ALIGN_CENTER);
        document.add(tipoCambio);

        // Calcular cambio
        double cambio = factura.getTotalPagado() - factura.getTotalAPagar();
        boolean hayCambio = cambio > 0;
            
        // Totales
        agregarCeldaResumen(resumenTable, "TOTAL VENTA:", fontNormal);
        agregarCeldaResumen(resumenTable, String.format("Bs%.0f", factura.getTotalAPagar()), fontNormal);
        
        agregarCeldaResumen(resumenTable, "TOTAL PAGADO:", fontNormal);
        agregarCeldaResumen(resumenTable, String.format("Bs%.0f", factura.getTotalPagado()), fontNormal);
        
        // Saldo pendiente o cambio
        if (factura.getSaldoPendiente() > 0) {
            agregarCeldaResumen(resumenTable, "SALDO PENDIENTE:", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.RED));
            agregarCeldaResumen(resumenTable, String.format("Bs%.0f", factura.getSaldoPendiente()), new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.RED));
        } else if (hayCambio) {
            agregarCeldaResumen(resumenTable, "CAMBIO:", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.GREEN));
            agregarCeldaResumen(resumenTable, String.format("Bs%.0f", cambio), new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.GREEN));
        } else {
            agregarCeldaResumen(resumenTable, "SALDO:", fontNegrita);
            agregarCeldaResumen(resumenTable, "CANCELADO", fontNegrita);
        }
        
        document.add(resumenTable);
        
        // Mensaje final compacto
        Paragraph agradecimiento = new Paragraph("\n¡Gracias por su compra!", fontPequeño);
        agradecimiento.setAlignment(Element.ALIGN_CENTER);
        document.add(agradecimiento);
        
        // Firma
        Paragraph firma = new Paragraph("\n\n_________________________", fontPequeño);
        firma.setAlignment(Element.ALIGN_CENTER);
        document.add(firma);
        
        Paragraph nombreFirma = new Paragraph("Firma y Sello", fontPequeño);
        nombreFirma.setAlignment(Element.ALIGN_CENTER);
        document.add(nombreFirma);
        
        document.close();
        
        // Mostrar mensaje de éxito
        mostrarMensajeExito("Factura generada exitosamente en:\n" + outputPath);
        
        // Preguntar si desea abrir la factura
        if (preguntarAbrirFactura()) {
            abrirArchivoPDF(outputPath);
        }
    }
    
    /**
     * Método auxiliar para agregar celdas a la tabla de productos (simplificado)
     */
    private void agregarCeldaProducto(PdfPTable table, String texto, Font font, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(2);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidth(0.3f);
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        table.addCell(cell);
    }
    
    /**
     * Método auxiliar para agregar celdas al resumen (simplificado)
     */
    private void agregarCeldaResumen(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
    
    /**
     * Clases internas para estructura de datos (se mantienen igual)
     */
    public class FacturaData {
        private int numeroFactura;
        private Date fecha;
        private String cliente;
        private int cedula;
        private String telefono;
        private String vendedor;
        private double totalAPagar;
        private double totalPagado;
        private double saldoPendiente;
        private List<ProductoFactura> productos;
        private List<PagoFactura> pagos;
        
        // Getters y Setters
        public int getNumeroFactura() { return numeroFactura; }
        public void setNumeroFactura(int numeroFactura) { this.numeroFactura = numeroFactura; }
        
        public Date getFecha() { return fecha; }
        public void setFecha(Date fecha) { this.fecha = fecha; }
        
        public String getCliente() { return cliente; }
        public void setCliente(String cliente) { this.cliente = cliente; }
        
        public int getCedula() { return cedula; }
        public void setCedula(int cedula) { this.cedula = cedula; }
        
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        
        public String getVendedor() { return vendedor; }
        public void setVendedor(String vendedor) { this.vendedor = vendedor; }
        
        public double getTotalAPagar() { return totalAPagar; }
        public void setTotalAPagar(double totalAPagar) { this.totalAPagar = totalAPagar; }
        
        public double getTotalPagado() { return totalPagado; }
        public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }
        
        public double getSaldoPendiente() { return saldoPendiente; }
        public void setSaldoPendiente(double saldoPendiente) { this.saldoPendiente = saldoPendiente; }
        
        public List<ProductoFactura> getProductos() { return productos; }
        public void setProductos(List<ProductoFactura> productos) { this.productos = productos; }
        
        public List<PagoFactura> getPagos() { return pagos; }
        public void setPagos(List<PagoFactura> pagos) { this.pagos = pagos; }
    }
    
    public class ProductoFactura {
        private String nombre;
        private String descripcion;
        private int cantidad;
        private double precioUnitario;
        private double subtotal;
        private String tipoVenta;
        private String estado;
        private double totalProducto;
        
        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        
        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
        
        public double getSubtotal() { return subtotal; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
        
        public String getTipoVenta() { return tipoVenta; }
        public void setTipoVenta(String tipoVenta) { this.tipoVenta = tipoVenta; }
        
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
        
        public double getTotalProducto() { return totalProducto; }
        public void setTotalProducto(double totalProducto) { this.totalProducto = totalProducto; }
    }
    
    public class PagoFactura {
        private String tipoPago;
        private String referencia;
        private double monto;
        
        // Getters y Setters
        public String getTipoPago() { return tipoPago; }
        public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }
        
        public String getReferencia() { return referencia; }
        public void setReferencia(String referencia) { this.referencia = referencia; }
        
        public double getMonto() { return monto; }
        public void setMonto(double monto) { this.monto = monto; }
    }
}