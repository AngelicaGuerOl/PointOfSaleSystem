/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Angelica Guerrero
 */
public class EfectivoIn {
    private int id;
    private double efectivo_inicial;
    private String fecha;

    public EfectivoIn() {
    }

    public EfectivoIn(int id, double efectivo_inicial, String fecha) {
        this.id = id;
        this.efectivo_inicial = efectivo_inicial;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEfectivo_inicial() {
        return efectivo_inicial;
    }

    public void setEfectivo_inicial(double efectivo_inicial) {
        this.efectivo_inicial = efectivo_inicial;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
