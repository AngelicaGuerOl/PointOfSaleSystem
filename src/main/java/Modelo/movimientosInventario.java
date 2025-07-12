/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Angelica Guerrero
 */
public class movimientosInventario {
    private int idMovimiento;
    private String fechaMovimiento;
    private int idProducto;
    private double cantidad;
    private double cantidadAnt;
    private String tipoMovimiento;
    private int idUsuario;
    private double cantidadActual;
    
    public movimientosInventario() {
    }

    public movimientosInventario(int idMovimiento, String fechaMovimiento, int idProducto, double cantidad, double cantidadAnt, String tipoMovimiento,double cantidadActual, int idUsuario) {
        this.idMovimiento = idMovimiento;
        this.fechaMovimiento = fechaMovimiento;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.cantidadAnt = cantidadAnt;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidadActual = cantidadActual;
        this.idUsuario = idUsuario;
        
        
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getCantidadAnt() {
        return cantidadAnt;
    }

    public void setCantidadAnt(double cantidadAnt) {
        this.cantidadAnt = cantidadAnt;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

   
    
    
}
