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
public class GeneradorFacturaCreditoPDF extends sql_consulta {
    
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
     * Obtiene los datos de la factura a crédito desde las vistas de la base de datos
     */
    public FacturaCreditoData obtenerDatosFacturaCredito(int idVenta) throws SQLException {
        FacturaCreditoData factura = new FacturaCreditoData();
        factura.setProductos(new ArrayList<>());
        
        try {
            // Consulta para obtener datos de la venta a crédito desde la vista factura_venta
            // Filtrando solo ventas a crédito
            String queryVenta = "SELECT * FROM factura_venta WHERE `Número de Factura` = ? AND `Tipo de Venta` LIKE '%crédito%'";
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
                    factura.setPorcentajeCredito(rs.getDouble("Porcentaje"));
                    factura.setFechaCobro(rs.getDate("Fecha_Cobro"));
                    primeraVez = false;
                }
                
                // Agregar producto
                ProductoFacturaCredito producto = new ProductoFacturaCredito();
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
            
            // Calcular total del crédito: total de venta + (total de venta * porcentaje / 100)
            double totalCredito = sumaSubtotales + (sumaSubtotales * factura.getPorcentajeCredito() / 100);
            
            factura.setTotalAPagar(totalCredito);
            factura.setSubtotalSinInteres(sumaSubtotales);
            factura.setMontoCredito(totalCredito - sumaSubtotales);
            
            // Para facturas a crédito, no hay pagos iniciales
            factura.setTotalPagado(0.0);
            
            // Calcular saldo pendiente (total completo ya que es crédito)
            factura.setSaldoPendiente(factura.getTotalAPagar());
            
        } catch (Exception e) {
            System.out.println("Error obteniendo datos de factura crédito: " + e);
            mostrarMensajeError("Error obteniendo datos de factura crédito: " + e.getMessage());
        }
        
        return factura;
    }
    
    /**
     * Convierte fecha a formato español
     */
    private String formatearFecha(Date fecha) {
        if (fecha == null) return "N/A";
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formato.format(fecha);
    }
    
    /**
     * Convierte fecha a formato corto (solo fecha)
     */
    private String formatearFechaCorta(Date fecha) {
        if (fecha == null) return "N/A";
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }
    
    /**
     * Genera el PDF de la factura a crédito optimizado para impresoras microtérmicas
     */
    public void generarFacturaCreditoPDF(int idVenta) throws Exception {
        FacturaCreditoData factura = obtenerDatosFacturaCredito(idVenta);
        
        // Verificar si se encontró la factura
        if (factura.getProductos().isEmpty()) {
            mostrarMensajeError("No se encontró la factura a crédito con ID: " + idVenta + 
                              "\nO la factura no es de tipo crédito.");
            return;
        }
        
        // Obtener ruta del escritorio
        String escritorio = obtenerRutaEscritorio();
        String outputPath = escritorio + File.separator + "factura_credito_" + idVenta + ".pdf";
        
        // Tamaño reducido para impresoras microtérmicas (80mm de ancho)
        Document document = new Document(new Rectangle(226, 800), 5, 5, 10, 10); // 80mm ≈ 226 puntos
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        
        document.open();
        
        // Configuración de fuentes simples y pequeñas
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 7);
        Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD);
        Font fontPequeño = new Font(Font.FontFamily.HELVETICA, 6);
        
        try {
            // Logo de la empresa (más pequeño)
            Image logo = Image.getInstance(sistemaLogo);
            logo.scaleToFit(40, 40);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }
        
        // Encabezado de la factura
        Paragraph tituloEmpresa = new Paragraph(empresaNombre, fontTitulo);
        tituloEmpresa.setAlignment(Element.ALIGN_CENTER);
        tituloEmpresa.setSpacingAfter(2f);
        document.add(tituloEmpresa);
        
        // Etiqueta de FACTURA A CRÉDITO
        Paragraph etiquetaCredito = new Paragraph("FACTURA A CRÉDITO", fontSubtitulo);
        etiquetaCredito.setAlignment(Element.ALIGN_CENTER);
        etiquetaCredito.setSpacingAfter(2f);
        document.add(etiquetaCredito);
        
        Paragraph infoEmpresa = new Paragraph(direccionEmpresa, fontPequeño);
        infoEmpresa.setAlignment(Element.ALIGN_CENTER);
        infoEmpresa.setSpacingAfter(5f);
        document.add(infoEmpresa);
        
        // Línea separadora
        document.add(new Chunk("\n"));
        
        // Información de la factura en formato compacto
        PdfPTable infoFacturaTable = new PdfPTable(2);
        infoFacturaTable.setWidthPercentage(100);
        infoFacturaTable.setWidths(new float[]{1, 1});
        
        // Información básica
        PdfPCell infoFacturaCell = new PdfPCell();
        infoFacturaCell.setBorder(Rectangle.NO_BORDER);
        infoFacturaCell.addElement(new Paragraph("FACTURA: " + factura.getNumeroFactura(), fontNegrita));
        infoFacturaCell.addElement(new Paragraph("FECHA: " + formatearFecha(factura.getFecha()), fontNormal));
        infoFacturaCell.addElement(new Paragraph("VENDEDOR: " + factura.getVendedor(), fontNormal));
        
        // Información del cliente
        PdfPCell infoClienteCell = new PdfPCell();
        infoClienteCell.setBorder(Rectangle.NO_BORDER);
        infoClienteCell.addElement(new Paragraph("CLIENTE:", fontNegrita));
        infoClienteCell.addElement(new Paragraph(factura.getCliente(), fontNormal));
        infoClienteCell.addElement(new Paragraph("CI: " + factura.getCedula(), fontNormal));
        infoClienteCell.addElement(new Paragraph("TELF: " + factura.getTelefono(), fontNormal));
        
        infoFacturaTable.addCell(infoFacturaCell);
        infoFacturaTable.addCell(infoClienteCell);
        document.add(infoFacturaTable);
        
        document.add(new Paragraph(" "));
        
        // Información del crédito compacta
        PdfPTable creditoTable = new PdfPTable(2);
        creditoTable.setWidthPercentage(100);
        
        PdfPCell creditoCell = new PdfPCell(new Paragraph("INFORMACIÓN CRÉDITO", fontNegrita));
        creditoCell.setColspan(2);
        creditoCell.setBorder(Rectangle.BOX);
        creditoTable.addCell(creditoCell);
        
        creditoTable.addCell(new Phrase("INTERÉS:", fontNormal));
        creditoTable.addCell(new Phrase(String.format("%.2f", factura.getPorcentajeCredito()) + "%", fontNormal));
        
        creditoTable.addCell(new Phrase("FECHA COBRO:", fontNormal));
        creditoTable.addCell(new Phrase(formatearFechaCorta(factura.getFechaCobro()), fontNormal));
        
        creditoTable.addCell(new Phrase("ESTADO:", fontNegrita));
        creditoTable.addCell(new Phrase("PENDIENTE", fontNegrita));
        
        document.add(creditoTable);
        document.add(new Paragraph(" "));
        
        // Tabla de productos simplificada
        PdfPTable productosTable = new PdfPTable(4);
        productosTable.setWidthPercentage(100);
        productosTable.setWidths(new float[]{3, 1, 2, 2});
        
        // Encabezados de productos simplificados
        agregarCeldaProducto(productosTable, "PRODUCTO", fontNegrita, true);
        agregarCeldaProducto(productosTable, "CANT", fontNegrita, true);
        agregarCeldaProducto(productosTable, "P.UNIT", fontNegrita, true);
        agregarCeldaProducto(productosTable, "SUBTOTAL", fontNegrita, true);
        
        // Datos de productos
        for (ProductoFacturaCredito producto : factura.getProductos()) {
            // Limitar longitud del nombre para ahorrar espacio
            String nombreProducto = producto.getNombre();
            if (nombreProducto.length() > 20) {
                nombreProducto = nombreProducto.substring(0, 17) + "...";
            }
            
            agregarCeldaProducto(productosTable, nombreProducto, fontNormal, false);
            agregarCeldaProducto(productosTable, String.valueOf(producto.getCantidad()), fontNormal, false);
            agregarCeldaProducto(productosTable, String.format("Bs.%.0f", producto.getPrecioUnitario()), fontNormal, false);
            agregarCeldaProducto(productosTable, String.format("Bs.%.0f", producto.getSubtotal()), fontNormal, false);
        }
        
        document.add(productosTable);
        document.add(new Paragraph(" "));
        
        // Resumen de la factura compacto
        PdfPTable resumenTable = new PdfPTable(2);
        resumenTable.setWidthPercentage(100);
        
        // Mostrar tipo de cambio
        Paragraph tipoCambio = new Paragraph("Dólar: Bs. " + String.format("%.2f", dolar), fontPequeño);
        tipoCambio.setAlignment(Element.ALIGN_CENTER);
        document.add(tipoCambio);

        // Totales
        agregarCeldaResumen(resumenTable, "SUBTOTAL:", fontNormal);
        agregarCeldaResumen(resumenTable, String.format("Bs.%.0f", factura.getSubtotalSinInteres()), fontNormal);
        
        agregarCeldaResumen(resumenTable, "INTERÉS:", fontNormal);
        agregarCeldaResumen(resumenTable, String.format("Bs.%.0f", factura.getMontoCredito()), fontNormal);
        
        agregarCeldaResumen(resumenTable, "TOTAL:", fontNegrita);
        agregarCeldaResumen(resumenTable, String.format("Bs.%.0f", factura.getTotalAPagar()), fontNegrita);
        
        agregarCeldaResumen(resumenTable, "SALDO:", fontNegrita);
        agregarCeldaResumen(resumenTable, String.format("Bs.%.0f", factura.getSaldoPendiente()), fontNegrita);
        
        document.add(resumenTable);
        
        // Información de pago
        Paragraph pagoInfo = new Paragraph("\nFECHA LÍMITE: " + formatearFechaCorta(factura.getFechaCobro()), fontNegrita);
        pagoInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(pagoInfo);
        
        // Mensaje simple
        Paragraph agradecimiento = new Paragraph("\n¡Gracias por su compra!", fontPequeño);
        agradecimiento.setAlignment(Element.ALIGN_CENTER);
        document.add(agradecimiento);
        
        document.close();
        
        // Preguntar si desea abrir la factura
        if (preguntarAbrirFactura()) {
            abrirArchivoPDF(outputPath);
        }
    }
    
    /**
     * Método auxiliar para agregar celdas a la tabla de productos (versión simplificada)
     */
    private void agregarCeldaProducto(PdfPTable table, String texto, Font font, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(3);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidth(0.5f);
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        table.addCell(cell);
    }
    
    /**
     * Método auxiliar para agregar celdas al resumen (versión simplificada)
     */
    private void agregarCeldaResumen(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(2);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
    
    /**
     * Clases internas para estructura de datos de crédito (se mantienen igual)
     */
    public class FacturaCreditoData {
        private int numeroFactura;
        private Date fecha;
        private String cliente;
        private int cedula;
        private String telefono;
        private String vendedor;
        private double porcentajeCredito;
        private Date fechaCobro;
        private double subtotalSinInteres;
        private double montoCredito;
        private double totalAPagar;
        private double totalPagado;
        private double saldoPendiente;
        private List<ProductoFacturaCredito> productos;
        
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
        
        public double getPorcentajeCredito() { return porcentajeCredito; }
        public void setPorcentajeCredito(double porcentajeCredito) { this.porcentajeCredito = porcentajeCredito; }
        
        public Date getFechaCobro() { return fechaCobro; }
        public void setFechaCobro(Date fechaCobro) { this.fechaCobro = fechaCobro; }
        
        public double getSubtotalSinInteres() { return subtotalSinInteres; }
        public void setSubtotalSinInteres(double subtotalSinInteres) { this.subtotalSinInteres = subtotalSinInteres; }
        
        public double getMontoCredito() { return montoCredito; }
        public void setMontoCredito(double montoCredito) { this.montoCredito = montoCredito; }
        
        public double getTotalAPagar() { return totalAPagar; }
        public void setTotalAPagar(double totalAPagar) { this.totalAPagar = totalAPagar; }
        
        public double getTotalPagado() { return totalPagado; }
        public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }
        
        public double getSaldoPendiente() { return saldoPendiente; }
        public void setSaldoPendiente(double saldoPendiente) { this.saldoPendiente = saldoPendiente; }
        
        public List<ProductoFacturaCredito> getProductos() { return productos; }
        public void setProductos(List<ProductoFacturaCredito> productos) { this.productos = productos; }
    }
    
    public class ProductoFacturaCredito {
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
    
}