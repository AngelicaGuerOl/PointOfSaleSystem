/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Angelica Guerrero
 */
public class DetallesVentas {
    private int  id_detalleVenta;
    private int id_usuario;
    private String fechaVenta;
    private double importeTotal;
    private String tipoVenta;

    public DetallesVentas() {
    }

    public DetallesVentas(int id_detalleVenta, int id_usuario, String fechaVenta, double importeTotal, String tipoVenta) {
        this.id_detalleVenta = id_detalleVenta;
        this.id_usuario = id_usuario;
        this.fechaVenta = fechaVenta;
        this.importeTotal = importeTotal;
        this.tipoVenta = tipoVenta;
    }

    public int getId_detalleVenta() {
        return id_detalleVenta;
    }

    public void setId_detalleVenta(int id_detalleVenta) {
        this.id_detalleVenta = id_detalleVenta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }
    
    
}
