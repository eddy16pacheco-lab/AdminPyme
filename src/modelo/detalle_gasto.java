package modelo;

public class detalle_gasto {
private int id_detalle_gasto;
private nomina nomina;
private detalle_orden_de_compra detalle_orden_de_compra;
private gasto gasto;

    public int getId_detalle_gasto() {
        return id_detalle_gasto;
    }

    public void setId_detalle_gasto(int id_detalle_gasto) {
        this.id_detalle_gasto = id_detalle_gasto;
    }

    public nomina getNomina() {
        return nomina;
    }

    public void setNomina(nomina nomina) {
        this.nomina = nomina;
    }

    public detalle_orden_de_compra getDetalle_orden_de_compra() {
        return detalle_orden_de_compra;
    }

    public void setDetalle_orden_de_compra(detalle_orden_de_compra detalle_orden_de_compra) {
        this.detalle_orden_de_compra = detalle_orden_de_compra;
    }

    public gasto getGasto() {
        return gasto;
    }

    public void setGasto(gasto gasto) {
        this.gasto = gasto;
    }


}
