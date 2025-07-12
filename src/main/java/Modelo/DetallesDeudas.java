/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author olver
 */
public class DetallesDeudas {
    private int id_deuda;
    private int id_cliente;
    private double total_deuda;
    private double total_abonado;
    private double deuda_pendiente;

    public DetallesDeudas(int id_deuda, int id_cliente, double total_deuda, double total_abonado, double deuda_pendiente) {
        this.id_deuda = id_deuda;
        this.id_cliente = id_cliente;
        this.total_deuda = total_deuda;
        this.total_abonado = total_abonado;
        this.deuda_pendiente = deuda_pendiente;
    }

    public DetallesDeudas() {
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

    public double getTotal_deuda() {
        return total_deuda;
    }

    public void setTotal_deuda(double total_deuda) {
        this.total_deuda = total_deuda;
    }

    public double getTotal_abonado() {
        return total_abonado;
    }

    public void setTotal_abonado(double total_abonado) {
        this.total_abonado = total_abonado;
    }

    public double getDeuda_pendiente() {
        return deuda_pendiente;
    }

    public void setDeuda_pendiente(double deuda_pendiente) {
        this.deuda_pendiente = deuda_pendiente;
    }
    
    
}
