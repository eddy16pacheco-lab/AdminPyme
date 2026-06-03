package controlador;

import controlador.modulo_compra.controlador_proveedor;
import controlador.modulo_compra.ControladorGestionCompra;
import vista.formularios.FormularioVender;
import consultas_bd.consulta_orden_compra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import vista.gestion.*;

import controlador.modulo_cliente.*;
import controlador.modulo_gestion_productos.*;
import controlador.modulo_usuario.*;
import controlador.modulo_venta.*;
import informe_reporte.*;
import javax.swing.JOptionPane;
import modelo.*;
import vista.VistaInventarioModerno;
import vista.gestion.GestionCategorias;
import vista.consultas.VistaContable;
import vista.formularios.FormularioGestionCompras;
import vista.formularios.GestionCompras;
import vista.formularios.vista_producto;
import vista.gestion.GestionarVentasContable;
import vista.gestion.VistaGestionCliente;
import vista.gestion.VistaGestionUsuario;
import vista.gestion.GestionarNomina;
import vista.gestion.VistaGestionVentas;
import vista.login1;

import vista.menu;
public  class  controlador_menu extends controlador_padre implements ActionListener {
    
    public static menu vista = new menu();
    private controlador_login ctrl_login;
    private controlador_venta ctrl_venta;
    private usuario usuario=new usuario();
    private login1 login1=new login1();
    private ventas venta=new ventas();
    
    // Mapa para rastrear las vistas abiertas
    private static Map<Class<?>, javax.swing.JInternalFrame> vistasAbiertas = new HashMap<>();
   
    public controlador_menu() {
        this.vista.jMenuItem_gestion_proveedores.addActionListener(this);
        this.vista.jMenuItem_nuevo_registrar.addActionListener(this);
        this.vista.jMenuItem_gestion_cliente.addActionListener(this);
        this.vista.jMenuItem_orden_de_compra.addActionListener(this);
        this.vista.jMenuItem_almacen.addActionListener(this);
        this.vista.jMenuItem_categoria.addActionListener(this);
        this.vista.jMenuItem_vender.addActionListener(this);
        this.vista.jMenuItem_gestion_usuario.addActionListener(this);
        this.vista.jMenuItem_gestion_pago_nomina.addActionListener(this);
        this.vista.jMenuItem_salir.addActionListener(this);
        this.vista.jMenuItem_gestion_ventas.addActionListener(this);
        this.vista.jMenuItem_generar_movimiento_cuenta.addActionListener(this);
        this.vista.jMenuItem_generar_reporte_ventas.addActionListener(this);
        this.vista.jMenuItem_generar_reporte_de_compras.addActionListener(this);
        this.vista.jMenuItem_generar_reporte_de_ganacias.addActionListener(this);
        this.vista.jMenuItem_generar_reporte_pagos.addActionListener(this);
        this.vista.jMenuItem_generar_reporte_de_perdidas.addActionListener(this);
        this.vista.jMenu_movimiento.setVisible(false);
    }

    @Override
    public void iniciar() {
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
    // Método auxiliar para verificar y abrir vistas únicas
    private boolean abrirVistaUnica(javax.swing.JInternalFrame nuevaVista, Class<?> claseVista) {
        // Verificar si ya existe una instancia de esta vista
        if (vistasAbiertas.containsKey(claseVista)) {
            javax.swing.JInternalFrame vistaExistente = vistasAbiertas.get(claseVista);
            if (vistaExistente != null && !vistaExistente.isClosed()) {
                // La vista ya está abierta, traer al frente
                try {
                    vistaExistente.setSelected(true);
                    vistaExistente.moveToFront();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false; // No se abre nueva instancia
            } else {
                // La vista estaba en el mapa pero está cerrada, removerla
                vistasAbiertas.remove(claseVista);
            }
        }
        
        // Agregar la nueva vista al mapa y al desktop pane
        vistasAbiertas.put(claseVista, nuevaVista);
        this.vista.jDesktopPane.add(nuevaVista);
        return true; // Se abre nueva instancia
    }
    
    public static void vista_ges_de_productos() {
        usuario modelo = new usuario();
        VistaGestionUsuario vista = new VistaGestionUsuario();
        controlador_usuario ctrl = new controlador_usuario(vista, modelo);
        menu.jDesktopPane.add(vista);
        ctrl.iniciar();
        
}
    
    public static void vista_pago_nomina(){
    nomina nomina=new nomina();
    GestionarNomina vista=new GestionarNomina();
    controlador_nomina ctrl=new controlador_nomina(nomina, vista);
    menu.jDesktopPane.add(vista);
    ctrl.iniciar();
    }
    
    public static void abrir_gestion_de_ventas(){
        detalle_de_ventas modelo=new detalle_de_ventas();
        GestionarVentasContable vista=new GestionarVentasContable();
        controlador_gestion_ventas ctrl=new controlador_gestion_ventas(modelo, vista);
        menu.jDesktopPane.add(vista);
        ctrl.iniciar();
    
    }
    
    
    @Override
    public void  actionPerformed(ActionEvent e) {
        //pestaña de ventas
        if (e.getSource() == vista.jMenuItem_vender) {
            FormularioVender vis = new FormularioVender();
            detalle_de_ventas mod=new detalle_de_ventas();
            controlador_venta ctrl;
            // Verificar si ya está abierta
            if (abrirVistaUnica(vis, FormularioVender.class)) {
              ctrl = new controlador_venta(vis, mod);
              ctrl.iniciar();
            }
        }
    
    //pestaña movimiento inventario
 
        if (e.getSource() == this.vista.jMenuItem_nuevo_registrar) {
            producto modelo = new producto();
            vista_producto vistaProd = new vista_producto();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaProd, vista_producto.class)) {
            }
        }
      //pestaña gestion clientes
        if (e.getSource() == this.vista.jMenuItem_gestion_cliente) {
            cliente modelo = new cliente();
            VistaGestionCliente vistaCliente = new VistaGestionCliente();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaCliente, VistaGestionCliente.class)) {
                controlador_gestion_clientes ctrl = new controlador_gestion_clientes(modelo, vistaCliente);
                ctrl.iniciar();
            }
           
        }
        if (e.getSource() == vista.jMenuItem_orden_de_compra) {
            GestionCompras vista=new GestionCompras();
            detalle_orden_de_compra compraModelo = new detalle_orden_de_compra();
        if(abrirVistaUnica(vista, ControladorGestionCompra.class)){
        ControladorGestionCompra ctrl=new ControladorGestionCompra(compraModelo,vista);
        }
        }
        if (e.getSource() == vista.jMenuItem_almacen) {
            inventario m_inventario = new inventario();
            producto m_producto=new producto();
            VistaInventarioModerno vistaAlmacen = new VistaInventarioModerno();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaAlmacen, GestionarInventario.class)) {
                controlador_inventario ctrl = new controlador_inventario(m_producto, vistaAlmacen,m_inventario);
                ctrl.iniciar();
            }
        }
        if (e.getSource() == vista.jMenuItem_categoria) {
            categoria modelo = new categoria();
            GestionCategorias vistaCategoria = new GestionCategorias();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaCategoria, GestionCategorias.class)) {
                GestionCategorias.id_usuario.setText(vista.jLabel_usu.getText());
                controlador_categoria_deepskee ctrl = new controlador_categoria_deepskee(modelo, vistaCategoria);
                ctrl.iniciar();
            }
        }
        if (e.getSource() == vista.jMenuItem_gestion_proveedores) {
            proveedor modelo = new proveedor();
            GestionarProveedores vistaProveedores = new GestionarProveedores();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaProveedores, GestionarProveedores.class)) {
                controlador_proveedor ctrl = new controlador_proveedor(modelo, vistaProveedores);
                ctrl.iniciar();
            }
        }
        if (e.getSource() == vista.jMenuItem_gestion_usuario) {
            usuario modelo = new usuario();
            VistaGestionUsuario vistaUsuario = new VistaGestionUsuario();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaUsuario, VistaGestionUsuario.class)) {
                VistaGestionUsuario.jLabel_id_usuario.setText(vista.jLabel_usu.getText());
                controlador_usuario ctrl = new controlador_usuario(vistaUsuario, modelo);
                ctrl.iniciar();
            }
        }
        if(e.getSource()==vista.jMenuItem_gestion_pago_nomina){
            nomina nomina=new nomina();
            GestionarNomina vistaNomina = new GestionarNomina();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaNomina, GestionarNomina.class)) {
                controlador_nomina ctrl = new controlador_nomina(nomina, vistaNomina);
    
            }
        }
        if(e.getSource()==vista.jMenuItem_nuevo_registrar){
        GestionarNomina ges=new GestionarNomina();
            if(abrirVistaUnica(ges, GestionarNomina.class)){
                
            }
            
}
        
        if(e.getSource()==vista.jMenuItem_generar_movimiento_cuenta){
            VistaContable vistaContable=new VistaContable();
//  this.vista.jDesktopPane.add(vistaMovi);
        if(abrirVistaUnica(vistaContable, VistaContable.class)){
    controlador_contable_deespeek ctrl=new controlador_contable_deespeek(vistaContable);}
        }
        if (e.getSource() == vista.jMenuItem_salir) {  
            if(ctrl_login==null){
                String[] opciones={"Volver al login","Salir del sistema"};
                String op = (String) JOptionPane.showInputDialog(null, "Seleccionar opciones", "MENSAJE DE OPCIONES", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                    if(op.equals("Volver al login")){
                ctrl_login = new controlador_login(usuario, login1);
                    }
                    else{
                        System.exit(0);
                            }
                vista.jDesktopPane.removeAll();
                vistasAbiertas.clear(); // Limpiar el mapa al salir
                vista.dispose();
                ctrl_login.iniciar();
            }
            ctrl_login.cerrar();
            ctrl_login.iniciar();
        }
        if(e.getSource()==vista.jMenuItem_gestion_ventas){
            detalle_de_ventas modelo=new detalle_de_ventas();     
            GestionarVentasContable vistaGestionVentas= new GestionarVentasContable();
            // Verificar si ya está abierta
            if (abrirVistaUnica(vistaGestionVentas, GestionarVentasContable.class)) {
              controlador_gestion_ventas ctrl = new controlador_gestion_ventas(modelo, vistaGestionVentas);
            }
        
        }
    
        if(e.getSource()==vista.jMenuItem_generar_reporte_ventas){
            // Los reportes PDF pueden abrirse múltiples veces si se desea
            ReporteVentasPDFProfesional ctrl=new ReporteVentasPDFProfesional();
            ctrl.generarReporteVentas();
        }
        if(e.getSource()==vista.jMenuItem_generar_reporte_pagos){
            // Los reportes PDF pueden abrirse múltiples veces si se desea
            ReportePagosPDFProfesional ctrl=new ReportePagosPDFProfesional();
            ctrl.generarReportePagos();
        }
        if(e.getSource()==vista.jMenuItem_generar_reporte_de_compras){
            // Los reportes PDF pueden abrirse múltiples veces si se desea
            ReporteComprasPDFProfesional ctrl=new ReporteComprasPDFProfesional();
            ctrl.generarReporteCompras();
        }
        
        if(e.getSource()==vista.jMenuItem_generar_reporte_de_perdidas){
            // Los reportes PDF pueden abrirse múltiples veces si se desea
            ReportePerdidasPDFProfesional ctrl=new ReportePerdidasPDFProfesional();
            ctrl.generarReportePerdidas();
        }
        if(e.getSource()==vista.jMenuItem_generar_reporte_de_ganacias){
        ReporteGananciasPDFProfesional ctrl = new ReporteGananciasPDFProfesional();
            ctrl.generarReporteGanancias();
        }
    }
   
    public static void inaviltar_opciones(boolean respuesta){
        vista.jMenu_usuario.setEnabled(respuesta);
        vista.jMenu_cliente.setEnabled(respuesta);
        vista.jMenu_producto.setEnabled(respuesta);
        vista.jMenu_orden_compra.setEnabled(respuesta);
        vista.jMenu_reporte.setEnabled(respuesta);
    }
        public static void inaviltar_opciones_supervisor(){
        vista.jMenu_usuario.setEnabled(false);
        vista.jMenu_cliente.setEnabled(false);
        vista.jMenu_producto.setEnabled(true);
        vista.jMenu_orden_compra.setEnabled(true);
        vista.jMenu_venta.setEnabled(false);
        vista.jMenu_reporte.setEnabled(true);
    }
    
    public void cerrar(){
        // Limpiar el mapa al cerrar la aplicación
        vistasAbiertas.clear();
    }
    
    // Método para remover una vista del mapa cuando se cierra
    public static void removerVista(Class<?> claseVista) {
        vistasAbiertas.remove(claseVista);
    }
}