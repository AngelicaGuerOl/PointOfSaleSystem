/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Angelica Guerrero
 */
public class Productos {
    private int idProducto;
    private int idCategoria;
    private String codigo;
    private String descripcion;
    private String unidad;
    private double precio_costo;
    private double precio_venta;
    private double existencias;

    public Productos() {
    }

    public Productos(int idProducto, int idCategoria, String codigo, String descripcion, String unidad, double precio_costo, double precio_venta, int existencias) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.precio_costo = precio_costo;
        this.precio_venta = precio_venta;
        this.existencias = existencias;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getPrecio_costo() {
        return precio_costo;
    }

    public void setPrecio_costo(double precio_costo) {
        this.precio_costo = precio_costo;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public double getExistencias() {
        return existencias;
    }

    public void setExistencias(double existencias) {
        this.existencias = existencias;
    }
    
}
