/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.movimientosInventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Angelica Guerrero
 */
public class ctrl_movimientos {
    public boolean agregarMovimientoInventario(movimientosInventario obj) {
    Connection cn = null;
    PreparedStatement st = null;
    String query = "INSERT INTO movimientos_inventario(id_movimiento, fecha, id_producto, tipo_movimiento, cantidad, cantidad_anterior, cantidad_actual, id_usuario) VALUES ( ?, ?, ?, ?, ?, ?, ?,?)";

    try {
        cn = Conexion.Conectar();
        st = cn.prepareStatement(query);
        st.setInt(1, 0); // ID autoincremental
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            
        st.setString(2, fechaActual);
        st.setInt(3, obj.getIdProducto());
        st.setString(4, obj.getTipoMovimiento());
        st.setDouble(5, obj.getCantidad());
        st.setDouble(6, obj.getCantidadAnt());
        st.setDouble(7, obj.getCantidadActual());
        st.setInt(8, obj.getIdUsuario());
        
        st.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println("Falló la inserción en movimientos inventario: " + e);
        return false;
    } finally {
        try {
            if (st != null) {
                st.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar recursos: " + ex);
        }
    }
}

}
