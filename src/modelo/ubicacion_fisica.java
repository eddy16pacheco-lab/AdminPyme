package modelo;

public class ubicacion_fisica {

    private int id_ubicacion_fisica;
    private String codigo_ubicacion;
    private almacen almacen;
    private String pasillo;
    private String estante;
    private String tipo_ubicacion;
    private String estado_ubicacion;

    public int getUbicacion_fisica() {
        return id_ubicacion_fisica;
    }

    public void setUbicacion_fisica(int id_ubicacion_fisica) {
        this.id_ubicacion_fisica = id_ubicacion_fisica;
    }

    public String getCodigo_ubicacion() {
        return codigo_ubicacion;
    }

    public void setCodigo_ubicacion(String codigo_ubicacion) {
        this.codigo_ubicacion = codigo_ubicacion;
    }

    public almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(almacen almacen) {
        this.almacen = almacen;
    }

    public String getPasillo() {
        return pasillo;
    }

    public void setPasillo(String pasillo) {
        this.pasillo = pasillo;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getTipo_ubicacion() {
        return tipo_ubicacion;
    }

    public void setTipo_ubicacion(String tipo_ubicacion) {
        this.tipo_ubicacion = tipo_ubicacion;
    }

    public String getEstado_ubicacion() {
        return estado_ubicacion;
    }

    public void setEstado_ubicacion(String estado_ubicacion) {
        this.estado_ubicacion = estado_ubicacion;
    }

}
