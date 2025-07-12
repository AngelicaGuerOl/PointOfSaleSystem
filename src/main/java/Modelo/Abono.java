/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author olver
 */
public class Abono {
    private int id_abono;
    private int id_detalleDeuda;
    private String fecha_abono;
    private double monto_abono;

    public Abono() {
    }

    public Abono(int id_abono, int id_detalleDeuda, String fecha_abono, double monto_abono) {
        this.id_abono = id_abono;
        this.id_detalleDeuda = id_detalleDeuda;
        this.fecha_abono = fecha_abono;
        this.monto_abono = monto_abono;
    }

    public int getId_abono() {
        return id_abono;
    }

    public void setId_abono(int id_abono) {
        this.id_abono = id_abono;
    }

    public int getId_detalleDeuda() {
        return id_detalleDeuda;
    }

    public void setId_detalleDeuda(int id_detalleDeuda) {
        this.id_detalleDeuda = id_detalleDeuda;
    }

    public String getFecha_abono() {
        return fecha_abono;
    }

    public void setFecha_abono(String fecha_abono) {
        this.fecha_abono = fecha_abono;
    }

    public double getMonto_abono() {
        return monto_abono;
    }

    public void setMonto_abono(double monto_abono) {
        this.monto_abono = monto_abono;
    }
    
    
}
