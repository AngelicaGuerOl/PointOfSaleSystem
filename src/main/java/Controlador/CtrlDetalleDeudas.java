/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author olver
 */
public class CtrlDetalleDeudas {
    public boolean registrarOActualizarDeuda(int idCliente, double nuevoMontoDeuda) {
    Connection cn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        cn = Conexion.Conectar();
        cn.setAutoCommit(false);

        // 1. Verificar si ya existe deuda activa
        String queryExistencia = "SELECT * FROM detalle_deudas WHERE id_cliente = ? AND deuda_pendiente > 0";
        st = cn.prepareStatement(queryExistencia);
        st.setInt(1, idCliente);
        rs = st.executeQuery();

        if (rs.next()) {
            // Ya existe deuda activa, actualiza
            double totalAnterior = rs.getDouble("total_deuda");
            double abonadoAnterior = rs.getDouble("total_abonado");

            double nuevoTotal = totalAnterior + nuevoMontoDeuda;
            double nuevoSaldoPendiente = nuevoTotal - abonadoAnterior;

            String update = "UPDATE detalle_deudas SET total_deuda = ?, deuda_pendiente = ? WHERE id_cliente = ?";
            st = cn.prepareStatement(update);
            st.setDouble(1, nuevoTotal);
            st.setDouble(2, nuevoSaldoPendiente);
            st.setInt(3, idCliente);
            st.executeUpdate();

        } else {
            // No existe deuda activa, inserta nuevo registro
            String insert = "INSERT INTO detalle_deudas (id_cliente, total_deuda, total_abonado, deuda_pendiente) VALUES (?, ?, ?, ?)";
            st = cn.prepareStatement(insert);
            st.setInt(1, idCliente);
            st.setDouble(2, nuevoMontoDeuda);
            st.setDouble(3, 0.0);
            st.setDouble(4, nuevoMontoDeuda);
            st.executeUpdate();
        }

        cn.commit();
        return true;

    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error en rollback: " + ex);
        }
        System.out.println("Error al registrar o actualizar deuda: " + e);
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e);
        }
    }
}

}
