package informe_reporte;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import consultas_bd.sql_consulta;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.awt.Desktop;
import javax.swing.JOptionPane;

/**
 *
 * @author Eddy
 */
public class GeneradorReciboNominaPDF extends sql_consulta {
    
    private String empresaNombre = "Inversiones Frontera del Sabor C.G";
    private String sistemaLogo = "src\\imagen\\AdminPyme.jpg";
    private String direccionEmpresa = "Cauacugua, Venezuela";
    private String telefonoEmpresa = "";
    private String rifEmpresa = "J-12345678-9";

    /**
     * Obtiene la ruta del escritorio del usuario
     */
    private String obtenerRutaEscritorio() {
        String escritorio = System.getProperty("user.home") + File.separator + "Desktop";
        if (!new File(escritorio).exists()) {
            escritorio = System.getProperty("user.home") + File.separator + "Escritorio";
        }
        return escritorio;
    }
    
    /**
     * Obtiene los datos del recibo de nómina
     */
    public ReciboNominaData obtenerDatosReciboNomina(int idNomina) throws SQLException {
        ReciboNominaData recibo = new ReciboNominaData();

        try {
            query = "SELECT  n.id_nomina, n.fecha_pago, n.periodo_pago, n.salario_bruto,"
                    + "pa.tipo_de_pago, n.ref_pago, n.estado_pago,u.id_usuario, p.nombre, "
                    + "p.apellido, p.cedula, r.tipo_rol " +
                    "FROM nomina n "
                    + "INNER JOIN usuario u ON n.id_usuario = u.id_usuario "
                    + "INNER JOIN persona p ON u.id_persona=p.id_persona "
                    + "INNER JOIN roles r ON u.id_rol=r.id_rol "
                    + "INNER JOIN pago pa ON n.id_pago=pa.id_pago "
                    + "WHERE n.id_nomina = ?;";

            ps = con.EstablecerConexion().prepareStatement(query);
            ps.setInt(1, idNomina);
            rs = ps.executeQuery();

            if (rs.next()) {
                recibo.setIdNomina(rs.getInt(1));
                recibo.setFechaPago(rs.getDate(2));
                recibo.setPeriodoPago(rs.getString(3));
                recibo.setSalarioBruto(rs.getDouble(4));
                recibo.setMetodoPago(rs.getString(5));
                recibo.setReferenciaPago(rs.getString(6));
                recibo.setEstadoPago(rs.getString(7));
                // Datos del empleado
                recibo.setIdEmpleado(rs.getInt(8));
                recibo.setNombreEmpleado(rs.getString(9) + " " + rs.getString(10));
                recibo.setCedulaEmpleado(rs.getString(11));
                recibo.setCargo(rs.getString(12));
            }
            
        } catch (Exception e) {
            System.out.println("Error obteniendo datos de nómina: " + e);
            mostrarMensajeError("Error obteniendo datos de nómina: " + e.getMessage());
        }
        
        return recibo;
    }
    
    /**
     * Formatea fecha
     */
    private String formatearFecha(Date fecha) {
        if (fecha == null) return "N/A";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }
    
    /**
     * Formatea moneda
     */
    private String formatearMoneda(double monto) {
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        return "Bs. " + df.format(monto);
    }
    
    /**
     * Genera el PDF del recibo de nómina
     */
    public void generarReciboNominaPDF(int idNomina) throws Exception {
        ReciboNominaData recibo = obtenerDatosReciboNomina(idNomina);
        
        if (recibo.getIdNomina() == 0) {
            mostrarMensajeError("No se encontró el registro de nómina con ID: " + idNomina);
            return;
        }
        
        String escritorio = obtenerRutaEscritorio();
        String outputPath = escritorio + File.separator + "recibo_nomina_" + idNomina + ".pdf";
        
        Document document = new Document(PageSize.A4, 40, 40, 60, 40);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        
        writer.setPageEvent(new HeaderFooterRecibo());
        
        document.open();
        
        // Configuración de fuentes
        BaseColor colorAzulOscuro = new BaseColor(0, 51, 102);
        BaseColor colorVerde = new BaseColor(0, 102, 51);
        
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, colorAzulOscuro);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, colorAzulOscuro);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10);
        Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font fontPequeño = new Font(Font.FontFamily.HELVETICA, 9);
        
        try {
            // Logo
            Image logo = Image.getInstance(sistemaLogo);
            logo.scaleToFit(80, 80);
            logo.setAbsolutePosition(40, 750);
            document.add(logo);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }
        
        // Encabezado
        Paragraph tituloEmpresa = new Paragraph(empresaNombre, fontTitulo);
        tituloEmpresa.setAlignment(Element.ALIGN_CENTER);
        tituloEmpresa.setSpacingAfter(5f);
        document.add(tituloEmpresa);
        
        Paragraph tituloRecibo = new Paragraph("RECIBO DE PAGO DE NÓMINA", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, colorVerde));
        tituloRecibo.setAlignment(Element.ALIGN_CENTER);
        tituloRecibo.setSpacingAfter(5f);
        document.add(tituloRecibo);
        
        Paragraph infoEmpresa = new Paragraph(direccionEmpresa + " | " + telefonoEmpresa + " | RIF: " + rifEmpresa, fontPequeño);
        infoEmpresa.setAlignment(Element.ALIGN_CENTER);
        infoEmpresa.setSpacingAfter(20f);
        document.add(infoEmpresa);
        
        // Información del recibo
        PdfPTable infoReciboTable = new PdfPTable(2);
        infoReciboTable.setWidthPercentage(100);
        infoReciboTable.setWidths(new float[]{1, 1});
        
        // Columna izquierda - Información del recibo
        PdfPCell infoReciboCell = new PdfPCell();
        infoReciboCell.setBorder(Rectangle.NO_BORDER);
        infoReciboCell.addElement(new Paragraph("RECIBO N°: " + recibo.getIdNomina(), fontSubtitulo));
        infoReciboCell.addElement(new Paragraph("Fecha de Pago: " + formatearFecha(recibo.getFechaPago()), fontNormal));
        infoReciboCell.addElement(new Paragraph("Período: " + recibo.getPeriodoPago(), fontNormal));
        infoReciboCell.addElement(new Paragraph("Estado: " + recibo.getEstadoPago(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLUE)));
        
        // Columna derecha - Información del empleado
        PdfPCell infoEmpleadoCell = new PdfPCell();
        infoEmpleadoCell.setBorder(Rectangle.NO_BORDER);
        infoEmpleadoCell.addElement(new Paragraph("EMPLEADO", fontSubtitulo));
        infoEmpleadoCell.addElement(new Paragraph("Nombre: " + recibo.getNombreEmpleado(), fontNormal));
        infoEmpleadoCell.addElement(new Paragraph("Cédula: " + recibo.getCedulaEmpleado(), fontNormal));
        infoEmpleadoCell.addElement(new Paragraph("Cargo: " + recibo.getCargo(), fontNormal));
        //infoEmpleadoCell.addElement(new Paragraph("Departamento: " + recibo.getDepartamento(), fontNormal));
        
        infoReciboTable.addCell(infoReciboCell);
        infoReciboTable.addCell(infoEmpleadoCell);
        document.add(infoReciboTable);
        
        document.add(new Paragraph(" "));
        
        // Desglose de pago
        PdfPTable desgloseTable = new PdfPTable(2);
        desgloseTable.setWidthPercentage(100);
        desgloseTable.setWidths(new float[]{3, 2});
        
        // Encabezado
        PdfPCell encabezadoDesglose = new PdfPCell(new Paragraph("DESGLOSE DE PAGO", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, colorAzulOscuro)));
        encabezadoDesglose.setColspan(2);
        encabezadoDesglose.setBackgroundColor(new BaseColor(240, 240, 240));
        encabezadoDesglose.setPadding(10);
        encabezadoDesglose.setBorder(Rectangle.BOX);
        desgloseTable.addCell(encabezadoDesglose);
        
        // Ingresos
        agregarCeldaDesglose(desgloseTable, "SALARIO BRUTO:", fontNegrita);
        agregarCeldaDesglose(desgloseTable, formatearMoneda(recibo.getSalarioBruto()), fontNormal);
        
        // Deducciones
       /* if (recibo.getDeducciones() > 0) {
            agregarCeldaDesglose(desgloseTable, "DEDUCCIONES:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.RED));
            agregarCeldaDesglose(desgloseTable, formatearMoneda(recibo.getDeducciones()), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.RED));
        }*/
        
        // Línea separadora
        PdfPCell lineaSeparadora = new PdfPCell();
        lineaSeparadora.setColspan(2);
        lineaSeparadora.setBorder(Rectangle.TOP);
        lineaSeparadora.setFixedHeight(1f);
        lineaSeparadora.setBackgroundColor(BaseColor.LIGHT_GRAY);
        desgloseTable.addCell(lineaSeparadora);
        
        // Total neto
        agregarCeldaDesglose(desgloseTable, "TOTAL A PAGAR:", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, colorVerde));
        agregarCeldaDesglose(desgloseTable, formatearMoneda(recibo.getSalarioBruto()), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, colorVerde));
        
        document.add(desgloseTable);
        document.add(new Paragraph(" "));
        
        // Información de pago
        PdfPTable infoPagoTable = new PdfPTable(2);
        infoPagoTable.setWidthPercentage(100);
        
        PdfPCell encabezadoPago = new PdfPCell(new Paragraph("INFORMACIÓN DE PAGO", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, colorAzulOscuro)));
        encabezadoPago.setColspan(2);
        encabezadoPago.setBackgroundColor(new BaseColor(240, 240, 240));
        encabezadoPago.setPadding(10);
        encabezadoPago.setBorder(Rectangle.BOX);
        infoPagoTable.addCell(encabezadoPago);
        
        agregarCeldaDesglose(infoPagoTable, "MÉTODO DE PAGO:", fontNegrita);
        agregarCeldaDesglose(infoPagoTable, recibo.getMetodoPago(), fontNormal);
        
        if (recibo.getReferenciaPago() != null && !recibo.getReferenciaPago().isEmpty()) {
            agregarCeldaDesglose(infoPagoTable, "REFERENCIA:", fontNegrita);
            agregarCeldaDesglose(infoPagoTable, recibo.getReferenciaPago(), fontNormal);
        }
        
       /* if (recibo.getNumeroCuenta() != null && !recibo.getNumeroCuenta().isEmpty()) {
            agregarCeldaDesglose(infoPagoTable, "N° DE CUENTA:", fontNegrita);
            agregarCeldaDesglose(infoPagoTable, recibo.getNumeroCuenta(), fontNormal);
        }*/
        
        document.add(infoPagoTable);
        
        // Observaciones si existen
        if (recibo.getObservaciones() != null && !recibo.getObservaciones().isEmpty()) {
            document.add(new Paragraph(" "));
            Paragraph observaciones = new Paragraph("Observaciones: " + recibo.getObservaciones(), 
                new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.DARK_GRAY));
            document.add(observaciones);
        }
        
        document.add(new Paragraph("\n\n"));
        
        // Firmas
        PdfPTable firmasTable = new PdfPTable(2);
        firmasTable.setWidthPercentage(100);
        
        PdfPCell firmaEmpleado = new PdfPCell();
        firmaEmpleado.setBorder(Rectangle.NO_BORDER);
        firmaEmpleado.setHorizontalAlignment(Element.ALIGN_CENTER);
        firmaEmpleado.addElement(new Paragraph("_________________________", fontNormal));
        firmaEmpleado.addElement(new Paragraph("Firma del Empleado", fontPequeño));
        firmaEmpleado.addElement(new Paragraph(recibo.getNombreEmpleado(), fontPequeño));
        firmaEmpleado.addElement(new Paragraph("C.I. " + recibo.getCedulaEmpleado(), fontPequeño));
        
        PdfPCell firmaEmpresa = new PdfPCell();
        firmaEmpresa.setBorder(Rectangle.NO_BORDER);
        firmaEmpresa.setHorizontalAlignment(Element.ALIGN_CENTER);
        firmaEmpresa.addElement(new Paragraph("_________________________", fontNormal));
        firmaEmpresa.addElement(new Paragraph("Firma y Sello de la Empresa", fontPequeño));
        firmaEmpresa.addElement(new Paragraph(empresaNombre, fontPequeño));
        firmaEmpresa.addElement(new Paragraph("RIF: " + rifEmpresa, fontPequeño));
        
        firmasTable.addCell(firmaEmpleado);
        firmasTable.addCell(firmaEmpresa);
        document.add(firmasTable);
        
        // Mensaje final
        Paragraph agradecimiento = new Paragraph("\n\nEste documento constituye un recibo oficial de pago de nómina.", 
            new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.DARK_GRAY));
        agradecimiento.setAlignment(Element.ALIGN_CENTER);
        document.add(agradecimiento);
        
        document.close();
        
        mostrarMensajeExito("Recibo de nómina generado exitosamente en:\n" + outputPath);
        
        if (preguntarAbrirRecibo()) {
            abrirArchivoPDF(outputPath);
        }
    }
    
    /**
     * Método auxiliar para agregar celdas al desglose
     */
    private void agregarCeldaDesglose(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(8);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }
    
    private boolean preguntarAbrirRecibo() {
        int respuesta = JOptionPane.showConfirmDialog(
            null, 
            "¿Desea abrir el recibo generado?", 
            "Abrir Recibo", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return respuesta == JOptionPane.YES_OPTION;
    }
    
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Recibo Generado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirArchivoPDF(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            mostrarMensajeError("Error al abrir el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Clase para header y footer del recibo
     */
    class HeaderFooterRecibo extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable footer = new PdfPTable(1);
            footer.setTotalWidth(500);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            
            Font fontFooter = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            footer.addCell(new Phrase("Documento generado por " + empresaNombre + " - " + new Date(), fontFooter));
            
            footer.writeSelectedRows(0, -1, 50, 30, writer.getDirectContent());
        }
    }
    
    /**
     * Clase para datos del recibo de nómina
     */
    public class ReciboNominaData {
        private int idNomina;
        private Date fechaPago;
        private String periodoPago;
        private double salarioBruto;
        private double deducciones;
        private double salarioNeto;
        private String metodoPago;
        private String referenciaPago;
        private String estadoPago;
        private String observaciones;
        
        private int idEmpleado;
        private String nombreEmpleado;
        private String cedulaEmpleado;
        private String cargo;
        private String departamento;
        private String numeroCuenta;
        
        // Getters y Setters
        public int getIdNomina() { return idNomina; }
        public void setIdNomina(int idNomina) { this.idNomina = idNomina; }
        
        public Date getFechaPago() { return fechaPago; }
        public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }
        
        public String getPeriodoPago() { return periodoPago; }
        public void setPeriodoPago(String periodoPago) { this.periodoPago = periodoPago; }
        
        public double getSalarioBruto() { return salarioBruto; }
        public void setSalarioBruto(double salarioBruto) { this.salarioBruto = salarioBruto; }
        
        public double getDeducciones() { return deducciones; }
        public void setDeducciones(double deducciones) { this.deducciones = deducciones; }
        
        public double getSalarioNeto() { return salarioNeto; }
        public void setSalarioNeto(double salarioNeto) { this.salarioNeto = salarioNeto; }
        
        public String getMetodoPago() { return metodoPago; }
        public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
        
        public String getReferenciaPago() { return referenciaPago; }
        public void setReferenciaPago(String referenciaPago) { this.referenciaPago = referenciaPago; }
        
        public String getEstadoPago() { return estadoPago; }
        public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
        
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
        
        public int getIdEmpleado() { return idEmpleado; }
        public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
        
        public String getNombreEmpleado() { return nombreEmpleado; }
        public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }
        
        public String getCedulaEmpleado() { return cedulaEmpleado; }
        public void setCedulaEmpleado(String cedulaEmpleado) { this.cedulaEmpleado = cedulaEmpleado; }
        
        public String getCargo() { return cargo; }
        public void setCargo(String cargo) { this.cargo = cargo; }
        
        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }
        
        public String getNumeroCuenta() { return numeroCuenta; }
        public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    }
}