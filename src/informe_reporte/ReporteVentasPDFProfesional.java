package informe_reporte;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import consultas_bd.sql_consulta;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.Font;
import com.toedter.calendar.JDateChooser;
import java.net.URL;
import java.awt.Desktop;
import validaciones.modulo_venta.validar_venta;

public class ReporteVentasPDFProfesional extends sql_consulta {

    private static final String NOMBRE_EMPRESA = "Inversiones\nLa Frontera del Sabor\nCG";
    private validar_venta vl = new validar_venta();

    // Variable para almacenar la ruta de la imagen
    private String rutaImagenFondo = "imagen/AdminPyme.jpg";
    private String rutaLogoEmpresa = "imagen/AdminPyme.jpg";

    private com.itextpdf.text.Image obtenerLogoEmpresa() {

        try {
            // Primero intentar con la ruta específica del logo
            if (rutaLogoEmpresa != null && new File(rutaLogoEmpresa).exists()) {
                com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(rutaLogoEmpresa);
                logo.scaleToFit(80, 80); // Tamaño adecuado para el logo
                return logo;
            }

            // Si no se encuentra, buscar en rutas alternativas
            String[] rutasComunes = {
                "imagen/AdminPyme.jpg",
                "imagen/AdminPyme.jpg",
                "src/imagen/AdminPyme.jpg",
                "resources/imagen/AdminPyme.jpg",
                "recursos/imagen/AdminPyme.jpg",
                System.getProperty("user.dir") + "/imagen/AdminPyme.jpg",
                System.getProperty("user.dir") + "/imagen/AdminPyme.jpg",
                "AdminPyme.jpg",
                "v3_1.png" // Fallback a la imagen anterior
            };

            for (String ruta : rutasComunes) {
                File archivo = new File(ruta);
                if (archivo.exists()) {
                    com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(archivo.getAbsolutePath());
                    logo.scaleToFit(320, 320);
                    return logo;
                }
            }

        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo de la empresa: " + e.getMessage());
        }
        return null;
    }

    public void generarReporteVentas() {
        String filePath = null; // Variable para almacenar la ruta del archivo

        try {

            // Diálogo para seleccionar rango de fechas
            JPanel fechaPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            fechaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JDateChooser dateChooserInicio = new JDateChooser();
            JDateChooser dateChooserFin = new JDateChooser();

            dateChooserInicio.setDate(new Date());
            dateChooserFin.setDate(new Date());
            dateChooserInicio.setDateFormatString("yyyy-MM-dd");
            dateChooserFin.setDateFormatString("yyyy-MM-dd");

            fechaPanel.add(new JLabel("Fecha Inicio:"));
            fechaPanel.add(dateChooserInicio);
            fechaPanel.add(new JLabel("Fecha Fin:"));
            fechaPanel.add(dateChooserFin);

            int result = JOptionPane.showConfirmDialog(null, fechaPanel,
                    "Seleccionar Rango de Fechas", JOptionPane.OK_CANCEL_OPTION);

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            if (dateChooserInicio.getDate() == null || dateChooserFin.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaInicio = sdf.format(dateChooserInicio.getDate());
            String fechaFin = sdf.format(dateChooserFin.getDate());

            // Obtener datos
            List<String[]> ventas = obtenerVentasPorFecha(fechaInicio, fechaFin);

            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No se encontraron ventas en el rango de fechas especificado");
                return;
            }

            // Diálogo para guardar PDF
            String tituloReporte = "Reporte de Ventas " + fechaInicio + " al " + fechaFin;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte PDF");
            fileChooser.setSelectedFile(new File(tituloReporte.replace(" ", "_") + ".pdf"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                filePath = fileToSave.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                crearPDFProfesional(filePath, tituloReporte, ventas, fechaInicio, fechaFin);

                // Preguntar si desea abrir la factura
                abrirFacturaGenerada(filePath, ventas.size());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Método para preguntar y abrir la factura PDF generada
     *
     * @param rutaPDF La ruta del archivo PDF generado
     * @param totalVentas El número total de ventas en el reporte
     */
    private void abrirFacturaGenerada(String rutaPDF, int totalVentas) {
        // Mostrar mensaje de éxito
        int opcion = JOptionPane.showConfirmDialog(null,
                "Reporte PDF generado exitosamente:\n" + rutaPDF
                + "\n\nTotal de ventas: " + totalVentas
                + "\n\n¿Desea abrir la factura ahora?",
                "Reporte Generado - Abrir Factura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            abrirArchivoPDF(rutaPDF);
        }
    }

    /**
     * Método para abrir el archivo PDF con el programa predeterminado del
     * sistema
     *
     * @param rutaArchivo La ruta completa del archivo PDF a abrir
     */
    private void abrirArchivoPDF(String rutaArchivo) {
        try {
            File archivoPDF = new File(rutaArchivo);

            // Verificar si el archivo existe
            if (!archivoPDF.exists()) {
                JOptionPane.showMessageDialog(null,
                        "El archivo PDF no se encuentra en la ruta especificada:\n" + rutaArchivo,
                        "Archivo No Encontrado",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Intentar abrir con Desktop
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Verificar si el sistema soporta abrir archivos
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(archivoPDF);
                    JOptionPane.showMessageDialog(null,
                            "Factura abierta correctamente en el visor de PDF predeterminado.",
                            "Factura Abierta",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    throw new Exception("La acción OPEN no está soportada en este sistema.");
                }
            } else {
                throw new Exception("Desktop no está soportado en este sistema.");
            }

        } catch (Exception e) {
            // Si falla Desktop, intentar métodos alternativos
            try {
                // Método alternativo para Windows
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + rutaArchivo);
                } // Método alternativo para Mac
                else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec("open " + rutaArchivo);
                } // Método alternativo para Linux/Unix
                else {
                    Runtime.getRuntime().exec("xdg-open " + rutaArchivo);
                }

                JOptionPane.showMessageDialog(null,
                        "Factura abierta correctamente.",
                        "Factura Abierta",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                // Si todo falla, mostrar opción manual
                JOptionPane.showMessageDialog(null,
                        "No se pudo abrir la factura automáticamente.\n\n"
                        + "Puede abrirla manualmente en:\n" + rutaArchivo
                        + "\n\nError: " + e.getMessage(),
                        "Error al Abrir Factura",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Método público para abrir un PDF específico (puede ser usado desde otras
     * partes del sistema)
     *
     * @param rutaPDF La ruta del archivo PDF a abrir
     */
    public void abrirFacturaExistente(String rutaPDF) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Desea abrir la factura ahora?",
                "Abrir Factura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            abrirArchivoPDF(rutaPDF);
        }
    }

    private List<String[]> obtenerVentasPorFecha(String fechaInicio, String fechaFin) {
        List<String[]> ventas = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }

        query = "SELECT * FROM listado_de_ventas_2 WHERE `Venta realizada` BETWEEN ? AND ? ORDER BY `Venta realizada` DESC";

        try {
            ps = con.EstablecerConexion().prepareStatement(query);

            ps.setString(1, fechaInicio + " 00:00:00");
            ps.setString(2, fechaFin + " 23:59:59");

            rs = ps.executeQuery();

            while (rs.next()) {
                String numeroVenta = String.valueOf(rs.getInt("Numero de venta"));
                String codigoProducto = String.valueOf(rs.getInt("Codigo de producto"));
                String producto = rs.getString("Producto");
                // Formato con 2 decimales fijos
                String precioVenta = String.format("%,.2f", rs.getDouble("precio_venta"));
                String cantidad = String.valueOf(rs.getInt("Cantidad vendida"));
                // Formato con 2 decimales fijos
                String subTotal = String.format("%,.2f", rs.getDouble("sub total"));
                String porcentaje = rs.getString("Porcentaje");
                // Formato con 2 decimales fijos
                String totalVenta = String.format("%,.2f", rs.getDouble("Total de la venta"));

                String fechaVenta = "N/A";
                if (rs.getTimestamp("Venta realizada") != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    fechaVenta = dateFormat.format(rs.getTimestamp("Venta realizada"));
                }

                String fechaCobro = "N/A";
                if (rs.getDate("Fecha de cobro") != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    fechaCobro = dateFormat.format(rs.getDate("Fecha de cobro"));
                }

                String tipoVenta = rs.getString("tipo_venta");
                String estadoVenta = rs.getString("estado_venta");

                String[] fila = {
                    numeroVenta, codigoProducto, producto, precioVenta, cantidad,
                    subTotal, porcentaje, totalVenta, fechaVenta, fechaCobro,
                    tipoVenta, estadoVenta
                };
                ventas.add(fila);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener ventas: " + e.getMessage(), e);
        }

        return ventas;
    }

    private void crearPDFProfesional(String filePath, String tituloReporte,
            List<String[]> datosVentas, String fechaInicio, String fechaFin) {
        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(40, 40, 80, 40);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            writer.setPageEvent(new BackgroundEventHandler(this));

            document.open();

            // Fuentes
            Font fontTituloPrincipal = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.GRAY);
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font fontPequeño = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);

            // Crear tabla para encabezado con logo
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setSpacingAfter(10f);

            // Celda para el logo
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER);
            celdaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

            com.itextpdf.text.Image logo = obtenerLogoEmpresa();
            if (logo != null) {
                celdaLogo.addElement(logo);
            } else {
                // Si no hay logo, agregar espacio en blanco
                celdaLogo.addElement(new Paragraph(" "));
            }

            // Celda para la información de la empresa
            PdfPCell celdaEmpresa = new PdfPCell();
            celdaEmpresa.setBorder(PdfPCell.NO_BORDER);
            celdaEmpresa.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaEmpresa.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Información de la empresa
            Paragraph empresa = new Paragraph(NOMBRE_EMPRESA, fontTituloPrincipal);
            empresa.setAlignment(Element.ALIGN_CENTER);

            Paragraph linea = new Paragraph("_________________________________________________________");
            linea.setAlignment(Element.ALIGN_CENTER);

            Paragraph titulo = new Paragraph(tituloReporte.toUpperCase(), fontSubtitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5);

            Paragraph periodo = new Paragraph("PERÍODO: " + fechaInicio + " AL " + fechaFin, fontNegrita);
            periodo.setAlignment(Element.ALIGN_CENTER);
            periodo.setSpacingAfter(3);

            Paragraph estadisticas = new Paragraph("TOTAL DE VENTAS: " + datosVentas.size(), fontNormal);
            estadisticas.setAlignment(Element.ALIGN_CENTER);
            estadisticas.setSpacingAfter(10);

            celdaEmpresa.addElement(empresa);
            celdaEmpresa.addElement(linea);
            celdaEmpresa.addElement(titulo);
            celdaEmpresa.addElement(periodo);
            celdaEmpresa.addElement(estadisticas);

            // Agregar celdas a la tabla
            tablaEncabezado.addCell(celdaLogo);
            tablaEncabezado.addCell(celdaEmpresa);

            document.add(tablaEncabezado);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm:ss");
            String fechaHora = sdf.format(new Date());

            Paragraph fechaGeneracion = new Paragraph("Generado el " + fechaHora, fontPequeño);
            fechaGeneracion.setAlignment(Element.ALIGN_RIGHT);
            fechaGeneracion.setSpacingAfter(15);
            document.add(fechaGeneracion);

            // Tabla
            PdfPTable table = new PdfPTable(12);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(20f);

            float[] anchosColumnas = {4f, 4f, 18f, 6f, 5f, 7f, 4f, 7f, 9f, 7f, 6f, 7f};
            table.setWidths(anchosColumnas);

            String[] columnas = {
                "N° VENTA", "CÓDIGO", "PRODUCTO", "PRECIO", "CANT.",
                "SUB TOTAL", "%", "TOTAL", "FECHA VENTA", "FECHA COBRO", "TIPO", "ESTADO"
            };

            BaseColor colorHeader = new BaseColor(70, 130, 180);
            for (String columna : columnas) {
                PdfPCell header = new PdfPCell(new Phrase(columna, fontHeader));
                header.setBackgroundColor(colorHeader);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setPadding(8);
                header.setBorderWidth(1);
                header.setBorderColor(BaseColor.WHITE);
                table.addCell(header);
            }

            double totalGeneral = 0;
            boolean filaPar = false;
            BaseColor colorFilaPar = new BaseColor(240, 240, 240);
            BaseColor colorFilaImpar = new BaseColor(255, 255, 255);

            for (String[] fila : datosVentas) {
                BaseColor colorFondo = filaPar ? colorFilaPar : colorFilaImpar;

                for (int i = 0; i < fila.length; i++) {
                    String texto = fila[i] != null ? fila[i] : "";
                    PdfPCell cell = new PdfPCell(new Phrase(texto, fontPequeño));

                    if (esNumero(i)) {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }

                    cell.setBackgroundColor(colorFondo);
                    cell.setPadding(5);
                    cell.setBorderWidth(0.5f);
                    cell.setBorderColor(new BaseColor(200, 200, 200));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                }

                try {
                    String totalStr = fila[7].replace(",", ".");
                    totalGeneral += Double.parseDouble(totalStr);
                } catch (NumberFormatException e) {
                    // Ignorar
                }
                filaPar = !filaPar;
            }

            document.add(table);

            // Resumen - Formato con 2 decimales fijos
            PdfPTable tablaResumen = new PdfPTable(2);
            tablaResumen.setWidthPercentage(50);
            tablaResumen.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaResumen.setSpacingBefore(10f);

            Font fontResumenTitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontResumenValor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);

            PdfPCell cellTitulo = new PdfPCell(new Phrase("TOTAL GENERAL:", fontResumenTitulo));
            cellTitulo.setBorder(PdfPCell.NO_BORDER);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTitulo.setPadding(5);

            // Formatear total general con 2 decimales fijos
            String totalGeneralFormateado = String.format("%,.2f", totalGeneral);
            PdfPCell cellValor = new PdfPCell(new Phrase("Bs. " + totalGeneralFormateado, fontResumenValor));
            cellValor.setBorder(PdfPCell.NO_BORDER);
            cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellValor.setPadding(5);
            cellValor.setBackgroundColor(new BaseColor(220, 220, 220));

            tablaResumen.addCell(cellTitulo);
            tablaResumen.addCell(cellValor);

            document.add(tablaResumen);

            // Pie de página
            Paragraph piePagina = new Paragraph("\n\n* Reporte de compras generado automáticamente por el Sistema Automatizado AdminPyme\nPara gestionar ventas*",
                    new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY));
            piePagina.setAlignment(Element.ALIGN_CENTER);
            document.add(piePagina);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear PDF: " + e.getMessage(), e);
        } finally {
            document.close();
        }
    }

    class BackgroundEventHandler extends PdfPageEventHelper {

        private ReporteVentasPDFProfesional reporte;

        public BackgroundEventHandler(ReporteVentasPDFProfesional reporte) {
            this.reporte = reporte;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                com.itextpdf.text.Image imagenFondo = null;

                if (imagenFondo != null) {
                    // Configurar imagen como marca de agua
                    imagenFondo.scaleToFit(200, 100); // Tamaño más pequeño
                    imagenFondo.setAbsolutePosition(
                            document.getPageSize().getWidth() - imagenFondo.getScaledWidth() - 20,
                            20
                    );

                    PdfGState gState = new PdfGState();
                    gState.setFillOpacity(0.3f); // 30% de opacidad
                    canvas.setGState(gState);

                    canvas.addImage(imagenFondo);
                }

            } catch (Exception e) {
                System.out.println("Error al agregar fondo: " + e.getMessage());
            }
        }
    }

    private boolean esNumero(int indiceColumna) {
        return indiceColumna == 3 || indiceColumna == 4 || indiceColumna == 5 || indiceColumna == 7;
    }
}
