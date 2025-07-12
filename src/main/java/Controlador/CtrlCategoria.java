/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.Categoria;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Angelica Guerrero
 */
public class CtrlCategoria {

    public boolean agregar(Categoria obj) {
        Connection cn = null; // Inicializa la conexión fuera del try
        PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
        String query = "INSERT INTO categorias (id_categoria,nombre_categoria) VALUES(?,?)";
        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setInt(1, 0);
            st.setString(2, obj.getCategoria());
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Falló la inserción en categorias" + e);
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

    public boolean actualizar(Categoria obj) {
        Connection cn = null; // Inicializa la conexión fuera del try
        PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
        String query = "UPDATE categorias SET nombre_categoria=? WHERE id_categoria=?";
        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setString(1, obj.getCategoria());
            st.setInt(2, obj.getId_categoria());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria " + e);
            return false;
        }finally {
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

    public boolean eliminar(int idCategoria) {
    Connection cn = Conexion.Conectar();
    String query = "DELETE FROM categorias WHERE id_categoria=?";
    PreparedStatement st = null;

    try {
        st = cn.prepareStatement(query);
        st.setInt(1, idCategoria);
        int n_filasafectadas = st.executeUpdate(); // Sirve para insertar y actualizar
        return n_filasafectadas > 0; // Si se eliminaron filas, retorna true
    } catch (SQLException e) {
        System.out.println("Falló la eliminación de la categoría: " + e.getMessage());
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
