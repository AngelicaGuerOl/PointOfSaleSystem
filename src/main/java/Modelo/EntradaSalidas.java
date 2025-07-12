/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author olver
 */
public class EntradaSalidas {
    private int id_entrada_salida;
    private String fecha;
    private String tipo;
    private double cantidad;
    private String descripcion;
    private int id_usuario;

    public EntradaSalidas() {
    }
    

    public EntradaSalidas(int id_entrada_salida, String fecha, String tipo, double cantidad, String descripcion, int id_usuario) {
        this.id_entrada_salida = id_entrada_salida;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
    }

    public int getId_entrada_salida() {
        return id_entrada_salida;
    }

    public void setId_entrada_salida(int id_entrada_salida) {
        this.id_entrada_salida = id_entrada_salida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
}
