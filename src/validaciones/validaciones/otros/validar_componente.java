package validaciones.validaciones.otros;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class validar_componente {
public static BCVConnector dolar= new BCVConnector();
    
public static String obtenerPrimerGrupoNumerico(String cadena) {
    if (cadena == null || cadena.trim().isEmpty()) {
        return null;
    }
    
    // Dividir por espacios y buscar el primer grupo que sean solo números
    String[] partes = cadena.split("\\s+");
    
    for (String parte : partes) {
        if (parte.matches("\\d+")) { // Verifica si la parte son solo dígitos
            return parte;
        }
    }
    return null;
}
public static Double limpiar_valores(String valorTexto) {
        String valorLimpio = valorTexto.replace("Bs.", "").replace(",", ".").trim();
        return Double.parseDouble(valorLimpio);
    }

    public double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(decimales, RoundingMode.HALF_UP); // Redondear hacia arriba
        return bd.doubleValue();
    }

    public static String limitar_decimales(double limitar) {
        DecimalFormat fm = new DecimalFormat("0.00");
        fm.format(limitar);
        return fm.format(limitar);
    }

    // Método para obtener el valor del dólar desde una página web
    public String obtenerValorDolar() throws IOException {
        // URL de la página web que contiene el valor del dólar
        String url = "https://www.bcv.org.ve/"; // Reemplaza con la URL correcta

        // Conectar a la página web y obtener el documento HTML
        Document doc = Jsoup.connect(url).get();

        // Extraer el valor del dólar usando un selector CSS
        // (Debes inspeccionar la página web para encontrar el selector correcto)
        Element elemento = doc.selectFirst("#dolar .centrado strong"); // Reemplaza con el selector correcto
        if (elemento != null) {
            return elemento.text(); // Retorna el texto del elemento
        } else {
            throw new IOException("No se encontró el valor del dólar en la página.");
        }

    }

    public boolean validar_tabla(JTable tabla, String nombre,int num_columuna) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String n = tabla.getValueAt(i, num_columuna).toString();
            if (n.equalsIgnoreCase(nombre)) {
                return true;
            }
    }
        return false;

    }
    
    public boolean validar_campo(String campo) {
       if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No dejar espacios vacios");
       return false;
       }
       return true;
    }
    
    public boolean validar_campo(String campo,String campo2) {
       if (campo.isEmpty() && campo2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No dejar espacios vacios");
         return true;
       }
        return false;
    }
    
    public boolean validar_campo(String campo,String campo2,String campo3){
    if (campo.isEmpty() || campo2.isEmpty() || campo3.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No dejar espacios vacios");
            return false;
       }
    return true;
    }
    
    public boolean validar_campo(String campo,String campo2,String campo3,String campo4){
    if(campo.isEmpty() || campo2.isEmpty() || campo3.isEmpty() || campo4.isEmpty()){
         JOptionPane.showMessageDialog(null, "No dejar espacios vacios");
        return false;
    }
    return true;
    }
   
       public Date validar_fecha() {
        Date fechaSeleccionada=null;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        JPanel panel = new JPanel();
        panel.add(new JLabel("Seleccione una fecha:"));
        panel.add(dateChooser);
        int option = JOptionPane.showConfirmDialog(null, panel, "Seleccionar Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        // Verificar si el usuario seleccionó "OK"
        if (option == JOptionPane.OK_OPTION) {
            // Obtener la fecha seleccionada
            fechaSeleccionada = dateChooser.getDate();
            long fecha_sql = fechaSeleccionada.getTime();

            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna fecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        return fechaSeleccionada;
    }

    public boolean validar_campo(String campo,String campo2,String campo3,String campo4,String campo5){
    if(campo.isEmpty() || campo2.isEmpty() || campo3.isEmpty() || campo4.isEmpty() || campo5.isEmpty()){
         JOptionPane.showMessageDialog(null, "No dejar espacios vacios");
        return false;
    }
    return true;
    }
   public boolean validar_campo(String campo,String campo2,String campo3,String campo4,String campo5,String campo6){
    if(campo.isEmpty() || campo2.isEmpty() || campo3.isEmpty() || campo4.isEmpty() || campo5.isEmpty() || campo6.isEmpty()){
         JOptionPane.showMessageDialog(null, "No dejar espacios vacios");
        return false;
    }
    return true;
    }
   
    public boolean validar_campo(int longitudMi,int longitudMa,String campo,JLabel lbl_men_u){
    
        if (campo.length() < longitudMi || campo.length() > longitudMa) {
          lbl_men_u.setText("El nombre de usuario debe tener entre " + longitudMi + " y " + longitudMa + " caracteres.");
        return false;     
        }else{
       lbl_men_u.setText("");
        return true;
        }
    }
    
    protected boolean validar_tabla(String campo_tabla,JTable tabla,int columna) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String n = tabla.getValueAt(i, columna).toString();
            if (n.equalsIgnoreCase(campo_tabla)) {
                return true;
            }
        }
        return false;
    }
    
    public void validar_tabla(int columna,JTable tabla,int canMin){
    int fila=tabla.getSelectedRow();
    int campo=0;    
    if(fila>=0){
        for (int i = 0; i < tabla.getRowCount(); i++) {
        campo=Integer.parseInt(tabla.getValueAt(i, columna).toString());
        }
    if(campo < canMin){
    for (int i = 0; i < tabla.getRowCount(); i++) {
        tabla.setBackground(Color.red);
        }
    }
        }
        
    }
    
    public Integer validar_numero(String mensaje){
           while (true) { // Bucle infinito hasta que se ingrese un número válido o se cancele
            // Mostrar el JOptionPane para solicitar la entrada
            String input = JOptionPane.showInputDialog(null, mensaje);

            // Verificar si el usuario hizo clic en Cancelar
            if (input == null) {
                System.out.println("operacion cancelada");         
return null;
            }

            // Verificar si la entrada está vacía
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La entrada no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Volver a solicitar la entrada
            }

            // Validar si la entrada es un número
            try {
                int numero = Integer.parseInt(input);
                return numero; // Retornar el número si la conversión es exitosa
            } catch (NumberFormatException e) {
                // Mostrar un mensaje de error si la entrada no es un número
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public Integer validar_numero(String mensaje,String texto){
           while (true) { // Bucle infinito hasta que se ingrese un número válido o se cancele
            // Mostrar el JOptionPane para solicitar la entrada
            String input = JOptionPane.showInputDialog(null, mensaje,"MENSAJE DE AVISO",JOptionPane.QUESTION_MESSAGE,null,null,texto).toString();
            // Verificar si el usuario hizo clic en Cancelar
            if (input == null) {
          return null;
            }

            // Verificar si la entrada está vacía
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La entrada no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Volver a solicitar la entrada
            }

            // Validar si la entrada es un número
            try {
                int numero = Integer.parseInt(input);
                return numero; // Retornar el número si la conversión es exitosa
            } catch (NumberFormatException e) {
                // Mostrar un mensaje de error si la entrada no es un número
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
 
public Double validarNumeroDecimal(String mensaje) {
    while (true) {
        String input = JOptionPane.showInputDialog(null, mensaje);

        // Verificar si se hizo clic en Cancelar
        if (input == null) {
            return null;
        }

        // Verificar entrada vacía
        if (input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "La entrada no puede estar vacía.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            continue;
        }

        // Validar y convertir a decimal
        try {
            double numero = Double.parseDouble(input);
            return numero;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Entrada inválida. Ingrese un número decimal.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

    public  String cortarSegundosConSplit(String horaCompleta) {
    if (horaCompleta == null || !horaCompleta.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
        return "Formato inválido. Use HH:mm:ss";
    }
    
    String[] partes = horaCompleta.split(":");
    if (partes.length == 3) {
        return partes[0] + ":" + partes[1];
    }
    
    return horaCompleta;
}

    public String convertirHoraInglesaAEspanolaSimple(String horaInglesa) {
    try {
        DateTimeFormatter formatoIngles = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hora = LocalTime.parse(horaInglesa, formatoIngles);
        
        String amPm = hora.getHour() < 12 ? "am" : "pm";
        int hora12 = hora.getHour() > 12 ? hora.getHour() - 12 : hora.getHour();
        hora12 = hora12 == 0 ? 12 : hora12;
        
        return String.format("%d:%02d %s", hora12, hora.getMinute(), amPm);
        
    } catch (DateTimeParseException e) {
        System.err.println("Formato de hora inválido. Use HH:mm");
        return null;
    }
    }
        public static boolean esNumeroValido(JTextField textField) {
        if (textField == null) {
            return false;
        }
        
        String texto = textField.getText().trim();
        
        // Si está vacío, no es válido
        if (texto.isEmpty()) {
            return false;
        }
        
        return esNumeroValido(texto);
    }
    
    /**
     * Valida si una cadena es un número válido (entero o decimal)
     * @param texto Cadena a validar
     * @return true si es un número válido, false si no lo es
     */
    public static boolean esNumeroValido(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        
        String textoLimpio = texto.trim();
        
        try {
            // Intentar parsear como double (acepta enteros y decimales)
            Double.parseDouble(textoLimpio);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}

    

