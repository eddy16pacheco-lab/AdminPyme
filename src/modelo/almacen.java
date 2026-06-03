package modelo;


public class almacen {
    private int id_almacen;
    private String codigo;
    private String nombre;
    private String direccion;
    private String tipo_almacen;

    public int getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo_almacen() {
        return tipo_almacen;
    }

    public void setTipo_almacen(String tipo_almacen) {
        this.tipo_almacen = tipo_almacen;
    }
    
    
}
