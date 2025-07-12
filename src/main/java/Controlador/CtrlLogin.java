/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Angelica Guerrero
 */
public class CtrlLogin {
    public boolean comprobacion(Usuario obj) {
    Connection cn = null; // Inicializa la conexión fuera del try
    PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
    ResultSet rs = null; // Inicializa el ResultSet fuera del try
    String query = "SELECT * FROM usuarios WHERE nombre_usuario=? AND password_usuario=?";

    try {
        cn = Conexion.Conectar(); // Establece la conexión
        st = cn.prepareStatement(query); // Prepara la consulta
        st.setString(1, obj.getNombre_usuario());
        st.setString(2, obj.getPassword_usuario());
        rs = st.executeQuery(); // Ejecuta la consulta
        boolean comprobado = rs.next(); // Verifica si el resultado existe
        return comprobado;
    } catch (SQLException e) {
        System.out.println("El usuario no está registrado en la base de datos: " + e);
        return false;
    } finally {
        try {
            if (rs != null) {
                rs.close(); // Cierra el ResultSet si no es nulo
            }
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
