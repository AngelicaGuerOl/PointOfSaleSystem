/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.EntradaSalidas;
import Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
/**
 *
 * @author olver
 */
public class CtrlEntradaSalidas {
    public boolean agregar(EntradaSalidas obj) {
        Connection cn = null; // Inicializa la conexión fuera del try
        PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
        String query = "INSERT INTO entradas_salidas (id_entrada_salida,fecha,tipo,monto,descripcion,id_usuario) VALUES(?,?,?,?,?,?)";
        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setInt(1, 0);
            // Obtener la fecha con el formato MM-dd-yyyy HH:mm:ss
            String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            st.setString(2, fechaActual);
            st.setString(3, obj.getTipo());
            st.setDouble(4, obj.getCantidad());
            st.setString(5, obj.getDescripcion());
            st.setInt(6, obj.getId_usuario());
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Falló la inserción en entradas y salidas" + e);
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close(); // Cierra el PreparedStatement si no es nulo
                }
                if (cn != null) {
                    cn.close(); // Cierra la conexión si no es nula
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e);
            }
        }
    }
    public boolean eliminar(int idEntradaSalida) {
    Connection cn = Conexion.Conectar();
    String query = "DELETE FROM entradas_salidas WHERE id_entrada_salida=?";
    PreparedStatement st = null;

    try {
        st = cn.prepareStatement(query);
        st.setInt(1, idEntradaSalida);
        int n_filasafectadas = st.executeUpdate(); // Sirve para insertar y actualizar
        return n_filasafectadas > 0; // Si se eliminaron filas, retorna true
    } catch (SQLException e) {
        System.out.println("Falló la eliminación: " + e.getMessage());
        return false;
    } finally {
        try {
            if (st != null) {
                st.close(); // Cierra el PreparedStatement
            }
            if (cn != null) {
                cn.close(); // Cierra la conexión
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar los recursos: " + e.getMessage());
        }
    }
}
}
