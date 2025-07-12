/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Angelica Guerrero
 */
public class Ventas {
    private int id_venta;
    private int id_detalleVenta;
    private int id_producto;
    private double cantidad;
    private double precio_unitario;
    private double importe;
    private double importeTotal;
    private String descripcion;

    public Ventas() {
    }

    public Ventas(int id_venta, int id_detalleVenta, int id_producto,String descripcion, double cantidad, double precio_unitario, double importe,double importe_total) {
        this.id_venta = id_venta;
        this.id_detalleVenta = id_detalleVenta;
        this.id_producto = id_producto;
        this.descripcion=descripcion;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.importe = importe;
        this.importeTotal=importe_total;
        
    }
    

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_detalleVenta() {
        return id_detalleVenta;
    }

    public void setId_detalleVenta(int id_detalleVenta) {
        this.id_detalleVenta = id_detalleVenta;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
    
}
