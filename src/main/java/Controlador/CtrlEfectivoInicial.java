/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.EfectivoIn;
import Conexion.Conexion;
import Modelo.Cliente;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Angelica Guerrero
 */
public class CtrlEfectivoInicial {
   public boolean registrarEfectivo(EfectivoIn obj) {
    Connection cn = null; // Inicializa la conexión fuera del try
    PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
    String query = "INSERT INTO efectivo_inicial(id,monto,fecha)VALUES(?,?,?)";

    try {
        cn = Conexion.Conectar(); // Establece la conexión
        st = cn.prepareStatement(query); // Prepara la consulta
        st.setInt(1, 0);
        st.setDouble(2, obj.getEfectivo_inicial());
        st.setString(3, obj.getFecha());
        st.executeUpdate();
        return true;
    } catch (SQLException ex) {
        System.out.println("Fallo al agregar el efectivo inicial: " + ex);
        return false;
    } finally {
        try {
            if (st != null) {
                st.close(); // Cierra el PreparedStatement si no es nulo
            }
            if (cn != null) {
                cn.close(); // Cierra la conexión si no es nula
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar recursos: " + ex);
        }
    }
}

}
