package informe_reporte;

import consultas_bd.consulta_contabilidad;
import controlador.controlador_padre;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import validaciones.validaciones.otros.BCVConnector;
import vista.consultas.VistaContable;
import vista.consultas.VistaMovimientoCuenta;

public class controlador_contable_deespeek extends controlador_padre {

    private VistaContable vista;
    private consulta_contabilidad sql = new consulta_contabilidad();
    private double dolar$;
    public controlador_contable_deespeek(VistaContable vista) {
        this.vista = vista;
        dolar$=dolar.getDollarRate();
        iniciar(vista, "Modulo contable", "Cargando modoulo contable", "Modulo contable...");
        this.vista.jButton_filtrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           if(vista.jDateChooser_fecha_inicio.getDate()!=null && vista.jDateChooser_fecha_final.getDate()!=null){
          sql.consulta_movimiento_cuenta(vista.jTable_transacciones,vista.jDateChooser_fecha_inicio.getDate(), vista.jDateChooser_fecha_final.getDate(), false);
           }else
                {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha");
                }
            }
        });
 
        this.vista.filtrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           sql.mostrar_libro_diario(vista.journalTable, false, vista.fechaDesdeField.getDate(), vista.fechaHastaField.getDate());       
            }
        });
        this.vista.exportarExcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           sql.exportarLibroDiarioCSV(vista.journalTable, true, vista.fechaDesdeField.getDate(),vista.fechaHastaField.getDate());
                   }
        });
        this.vista.imprimirBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           sql.cargarEstadoResultadosPorFecha(vista.balanceTextArea, vista.balanceDateField.getDate());
            }
        });
 
    }

    @Override
    protected void mostrar() {
        sql.mostrar_libro_diario(vista.journalTable, true, null, null);
        sql.cargarEstadoResultadosEnTextArea(vista.balanceTextArea);
        sql.consulta_movimiento_cuenta(vista.jTable_transacciones, null, null, true);
        vista.jLabel_info_transacciones.setText(""+sql.total_transacciones_mensuales());
   vista.jButton_buscar.setVisible(false);
   vista.imprimirBalanceButton.setVisible(false);
   vista.generarBalanceButton.setText("Buscar Balance");
//   vista.libroMayorButton.setText("Reporte de Ganacias");
   }


    
}
