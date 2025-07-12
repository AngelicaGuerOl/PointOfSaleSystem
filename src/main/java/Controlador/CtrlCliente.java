package Controlador;

import Conexion.Conexion;
import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Angelica Guerrero
 */
public class CtrlCliente {

    public boolean agregar(Cliente obj) {
        Connection cn = null; // Inicializa la conexión fuera del try
        PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
        String query = "INSERT INTO clientes(id_cliente,nombre_cliente,apellidos_cliente,telefono_cliente) VALUES (?,?,?,?)";
        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setInt(1, 0);
            st.setString(2, obj.getNombre_cliente());
            st.setString(3, obj.getApellidos_cliente());
            st.setString(4, obj.getTelefono_cliente());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Falló la inserción en cliente: " + e);
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

    public boolean actualizar(Cliente obj) {
        Connection cn = null; // Inicializa la conexión fuera del try
        PreparedStatement st = null; // Inicializa el PreparedStatement fuera del try
        String query = "UPDATE clientes SET nombre_cliente=?,apellidos_cliente=?,telefono_cliente=? WHERE id_cliente=?";
        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setString(1, obj.getNombre_cliente());
            st.setString(2, obj.getApellidos_cliente());
            st.setString(3, obj.getTelefono_cliente());
            st.setInt(4, obj.getId_cliente());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Falló actualización de cliente: " + e);
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

   public boolean eliminar(int idCliente) {
    Connection cn = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    String verificarSQL = "SELECT id_detalleDeuda, deuda_pendiente FROM detalle_deudas WHERE id_cliente = ?";
    String borrarAbonosSQL = "DELETE FROM abonos WHERE id_detalleDeuda = ?";
    String borrarDeudasSQL = "DELETE FROM deudas WHERE id_cliente = ?";
    String borrarDetallesSQL = "DELETE FROM detalle_deudas WHERE id_cliente = ?";
    String eliminarClienteSQL = "DELETE FROM clientes WHERE id_cliente = ?";

    try {
        cn = Conexion.Conectar();

        // Verificar si existe deuda pendiente
        st = cn.prepareStatement(verificarSQL);
        st.setInt(1, idCliente);
        rs = st.executeQuery();

        int idDetalleDeuda = -1;
        double deudaPendiente = 0;

        if (rs.next()) {
            idDetalleDeuda = rs.getInt("id_detalleDeuda");
            deudaPendiente = rs.getDouble("deuda_pendiente");
            if (deudaPendiente > 0) {
                return false; // No eliminar si hay deuda pendiente
            }
        }

        rs.close();
        st.close();

        // Eliminar registros de abonos
        st = cn.prepareStatement(borrarAbonosSQL);
        st.setInt(1, idDetalleDeuda);
        st.executeUpdate();
        st.close();

        // Eliminar registros de deudas
        st = cn.prepareStatement(borrarDeudasSQL);
        st.setInt(1, idCliente);
        st.executeUpdate();
        st.close();

        // Eliminar registros de detalles_deudas
        st = cn.prepareStatement(borrarDetallesSQL);
        st.setInt(1, idCliente);
        st.executeUpdate();
        st.close();

        // Eliminar cliente
        st = cn.prepareStatement(eliminarClienteSQL);
        st.setInt(1, idCliente);
        int filasAfectadas = st.executeUpdate();

        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.out.println("Falló la eliminación de cliente: " + e);
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e);
        }
    }
}

    public boolean clienteExiste(String nombre, String apellido) {
    boolean existe = false;
    Connection con = Conexion.Conectar(); // Usa tu clase de conexión
    String sql = "SELECT COUNT(*) FROM clientes WHERE nombre_cliente = ? AND apellidos_cliente = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
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
    public boolean clienteTieneDeudasPendientes(int idCliente) {
    String sql = "SELECT COUNT(*) FROM detalle_deudas WHERE id_cliente = ? AND deuda_pendiente > 0";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
        pst.setInt(1, idCliente);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

}
