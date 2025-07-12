/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Angelica Guerrero
 */
public class Deudas {
    private int id_deuda;
    private int id_cliente;
    private int id_detalleVenta;
    private int id_detalleDeuda;
    private String fecha;
    private double monto;


    public Deudas() {
    }

    public Deudas(int id_deuda, int id_cliente, int id_detalleVenta, int id_detalleDeuda, String fecha, double monto) {
        this.id_deuda = id_deuda;
        this.id_cliente = id_cliente;
        this.id_detalleVenta = id_detalleVenta;
        this.id_detalleDeuda = id_detalleDeuda;
        this.fecha = fecha;
        this.monto = monto;
    }

    public int getId_deuda() {
        return id_deuda;
    }

    public void setId_deuda(int id_deuda) {
        this.id_deuda = id_deuda;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_detalleVenta() {
        return id_detalleVenta;
    }

    public void setId_detalleVenta(int id_detalleVenta) {
        this.id_detalleVenta = id_detalleVenta;
    }

    public int getId_detalleDeuda() {
        return id_detalleDeuda;
    }

    public void setId_detalleDeuda(int id_detalleDeuda) {
        this.id_detalleDeuda = id_detalleDeuda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    

    

}
