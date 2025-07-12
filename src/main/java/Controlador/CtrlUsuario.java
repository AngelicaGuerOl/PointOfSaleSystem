/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Angelica Guerrero
 */
public class CtrlUsuario {
    public boolean agregar(Usuario obj){
        Connection cn=Conexion.Conectar();
        String query="INSERT INTO usuarios(id_usuario,nombre_usuario,password_usuario) VALUES (?,?,?)";
        try(PreparedStatement st=cn.prepareStatement(query)){
            st.setInt(1, 0);
            st.setString(2, obj.getNombre_usuario());
            st.setString(3, obj.getPassword_usuario());
            st.executeUpdate();
            st.close();
            return true;
        }catch(SQLException ex){
            System.out.println("Falló al momento de agregar un nuevo usuario en Usuarios: " + ex); 
            return false;
        }
    }
    public boolean actualizar(Usuario obj){
        Connection cn=Conexion.Conectar();
        String query="UPDATE usuarios SET nombre_usuario=?,password_usuario=? WHERE id_usuario=?";
        try(PreparedStatement st=cn.prepareStatement(query)){
            st.setString(1, obj.getNombre_usuario());
            st.setString(2, obj.getPassword_usuario());
            st.setInt(3, obj.getId_usuario());
            st.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Falló actualización de usuario: " + e);
            return false;
        }
    }
    public boolean eliminar(int idUsuario){
        Connection cn=Conexion.Conectar();
        String query="DELETE FROM usuarios WHERE id_usuario =?";
        try(PreparedStatement st = cn.prepareStatement(query)){
             st.setInt(1, idUsuario);
             // Se ejecuta la consulta de eliminación y se obtiene el número de filas afectadas.
             byte n_filasafectadas = (byte) st.executeUpdate();
             // Se cierra la conexión a la base de datos.
             cn.close();
             // Se retorna true si se eliminó al menos una fila, de lo contrario false.
             return n_filasafectadas > 0;
        }catch(SQLException e){
            System.out.println("Fallo la eliminación de usuario: "+e);
            return false;
        }
    }
    public boolean usuarioExiste(String nombreUsuario) {
    boolean existe = false;
    Connection con = Conexion.Conectar(); // Usa tu clase de conexión
    String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreUsuario);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            existe = rs.getInt(1) > 0;
        }
        rs.close();
        ps.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return existe;
}

}
