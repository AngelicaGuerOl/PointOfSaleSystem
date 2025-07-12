/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.Abono;
import Modelo.DetallesDeudas;

import Modelo.DetallesVentas;
import Modelo.Deudas;
import Modelo.Ventas;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Angelica Guerrero
 */
public class CtrlVentas {
    public static int idDatosRegistrados;
    public static int idDetalleDeudaRegistrado;
    java.math.BigDecimal IdColValor;
public boolean agregarDetalleVenta(DetallesVentas detalles, List<Ventas> listaVentas) {
    Connection cn = null;
    PreparedStatement stDetalles = null;
    PreparedStatement stVentas = null;
    ResultSet rs = null;

    String queryDetalles = "INSERT INTO detalle_ventas(id_usuario, fecha_venta, importe_total, tipo_venta) VALUES (?,?,?,?)";
    String queryVentas = "INSERT INTO ventas(id_detalleVenta, id_producto, cantidad, precio_unitario, importe) VALUES (?,?,?,?,?)";

    try {
        cn = Conexion.Conectar();
        cn.setAutoCommit(false);

        // 1. Insertar detalle_ventas
        stDetalles = cn.prepareStatement(queryDetalles, Statement.RETURN_GENERATED_KEYS);
        stDetalles.setInt(1, detalles.getId_usuario());
        stDetalles.setString(2, detalles.getFechaVenta());
        stDetalles.setDouble(3, detalles.getImporteTotal());
        stDetalles.setString(4, detalles.getTipoVenta());
        stDetalles.execute();

        rs = stDetalles.getGeneratedKeys();
        if (rs.next()) {
            int idDetalleVenta = rs.getInt(1);
            idDatosRegistrados = idDetalleVenta;

            // 2. Insertar ventas
            stVentas = cn.prepareStatement(queryVentas);
            for (Ventas venta : listaVentas) {
                stVentas.setInt(1, idDetalleVenta);
                stVentas.setInt(2, venta.getId_producto());
                stVentas.setDouble(3, venta.getCantidad());
                stVentas.setDouble(4, venta.getPrecio_unitario());
                stVentas.setDouble(5, venta.getImporte());
                stVentas.addBatch();
            }
            stVentas.executeBatch();

            cn.commit();
            return true;
        } else {
            cn.rollback();
            return false;
        }

    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error en rollback: " + ex);
        }
        System.out.println("Error en registrarVentaCompleta: " + e);
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
            if (stVentas != null) stVentas.close();
            if (stDetalles != null) stDetalles.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e);
        }
    }
}

public boolean agregarDeudaYActualizarDetalle(Deudas obj, int idDetalleVenta) {
    Connection cn = null;
    PreparedStatement stBuscar = null;
    PreparedStatement stInsertarDetalle = null;
    PreparedStatement stActualizarDetalle = null;
    PreparedStatement stInsertarDeuda = null;
    ResultSet rsBuscar = null;
    ResultSet rsDetalle = null;

    String queryBuscar = "SELECT id_detalleDeuda, total_deuda, total_abonado, deuda_pendiente FROM detalle_deudas WHERE id_cliente = ? AND deuda_pendiente > 0";
    String queryInsertarDetalle = "INSERT INTO detalle_deudas(id_cliente, total_deuda, total_abonado, deuda_pendiente) VALUES (?, ?, ?, ?)";
    String queryActualizarDetalle = "UPDATE detalle_deudas SET total_deuda = ?, deuda_pendiente = ? WHERE id_detalleDeuda = ?";
    String queryInsertarDeuda = "INSERT INTO deudas(id_cliente, id_detalleVenta, id_detalleDeuda, fecha_deuda, monto_deuda) VALUES (?, ?, ?, ?, ?)";

    try {
        cn = Conexion.Conectar();
        cn.setAutoCommit(false); // Inicia transacción

        int idDetalleDeuda = -1;

        // Paso 1: Buscar si ya hay una deuda pendiente para este cliente
        stBuscar = cn.prepareStatement(queryBuscar);
        stBuscar.setInt(1, obj.getId_cliente());
        rsBuscar = stBuscar.executeQuery();

        if (rsBuscar.next()) {
            // Ya existe un detalle con deuda pendiente
            idDetalleDeuda = rsBuscar.getInt("id_detalleDeuda");
            double deudaActual = rsBuscar.getDouble("total_deuda");
            double pendienteActual = rsBuscar.getDouble("deuda_pendiente");

            double nuevaDeuda = deudaActual + obj.getMonto();
            double nuevoPendiente = pendienteActual + obj.getMonto();

            stActualizarDetalle = cn.prepareStatement(queryActualizarDetalle);
            stActualizarDetalle.setDouble(1, nuevaDeuda);
            stActualizarDetalle.setDouble(2, nuevoPendiente);
            stActualizarDetalle.setInt(3, idDetalleDeuda);
            stActualizarDetalle.executeUpdate();
        } else {
            // No existe, insertamos nuevo detalle
            stInsertarDetalle = cn.prepareStatement(queryInsertarDetalle, Statement.RETURN_GENERATED_KEYS);
            stInsertarDetalle.setInt(1, obj.getId_cliente());
            stInsertarDetalle.setDouble(2, obj.getMonto());
            stInsertarDetalle.setDouble(3, 0.00);
            stInsertarDetalle.setDouble(4, obj.getMonto());
            stInsertarDetalle.executeUpdate();

            rsDetalle = stInsertarDetalle.getGeneratedKeys();
            if (rsDetalle.next()) {
                idDetalleDeuda = rsDetalle.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del nuevo detalle de deuda.");
            }
        }

        // Paso 2: Insertar en la tabla deudas
        stInsertarDeuda = cn.prepareStatement(queryInsertarDeuda);
        stInsertarDeuda.setInt(1, obj.getId_cliente());
        stInsertarDeuda.setInt(2, idDetalleVenta);
        stInsertarDeuda.setInt(3, idDetalleDeuda);
        stInsertarDeuda.setString(4, obj.getFecha());
        stInsertarDeuda.setDouble(5, obj.getMonto());
        stInsertarDeuda.executeUpdate();

        cn.commit();
        return true;

    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error durante rollback: " + ex);
        }
        System.out.println("Error en agregarDeudaYActualizarDetalle: " + e);
        return false;

    } finally {
        try {
            if (rsBuscar != null) rsBuscar.close();
            if (rsDetalle != null) rsDetalle.close();
            if (stBuscar != null) stBuscar.close();
            if (stInsertarDetalle != null) stInsertarDetalle.close();
            if (stActualizarDetalle != null) stActualizarDetalle.close();
            if (stInsertarDeuda != null) stInsertarDeuda.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e);
        }
    }
}


public int obtenerIdDetalleDeudaConPendiente(int idCliente) {
    Connection cn = Conexion.Conectar();
    int idDetalleDeuda = -1;
    String sql = """
        SELECT id_detalleDeuda 
        FROM detalle_deudas 
        WHERE id_cliente = ? AND deuda_pendiente > 0 
        LIMIT 1
    """;

    try (PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            idDetalleDeuda = rs.getInt("id_detalleDeuda");
        }
        rs.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener detalle deuda: " + e);
    }

    return idDetalleDeuda;
}
public boolean registrarAbono(Abono obj) {
    Connection cn = Conexion.Conectar();
    PreparedStatement stActualizar = null;
    PreparedStatement stInsertar = null;

    String queryActualizar = """
        UPDATE detalle_deudas
        SET total_abonado = total_abonado + ?, deuda_pendiente = deuda_pendiente - ?
        WHERE id_detalleDeuda = ? AND deuda_pendiente > 0
    """;

    String queryInsertar = """
        INSERT INTO abonos (id_detalleDeuda, fecha_abono, monto_abono)
        VALUES (?, ?, ?)
    """;

    try {
        cn.setAutoCommit(false); // Iniciar transacción

        // 1. Actualizar los campos en detalle_deudas
        stActualizar = cn.prepareStatement(queryActualizar);
        stActualizar.setDouble(1, obj.getMonto_abono());
        stActualizar.setDouble(2, obj.getMonto_abono());
        stActualizar.setInt(3, obj.getId_detalleDeuda());
        int actualizados = stActualizar.executeUpdate();

        if (actualizados > 0) {
            // 2. Insertar el abono en la tabla abonos
            stInsertar = cn.prepareStatement(queryInsertar);
            stInsertar.setInt(1, obj.getId_detalleDeuda());
            stInsertar.setString(2, obj.getFecha_abono());
            stInsertar.setDouble(3, obj.getMonto_abono());
            stInsertar.executeUpdate();

            cn.commit();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el abono. Verifica si hay deuda pendiente.");
            cn.rollback();
            return false;
        }
    } catch (SQLException ex) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException rollbackEx) {
            System.out.println("Error al hacer rollback: " + rollbackEx);
        }
        System.out.println("Error al registrar abono: " + ex);
        return false;
    } finally {
        try {
            if (stActualizar != null) stActualizar.close();
            if (stInsertar != null) stInsertar.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e);
        }
    }
}

public boolean registrarDevolucionYActualizarVentas(int idDetalleVenta, int idProducto, double cantidadDevuelta) {
    Connection cn = null;
    PreparedStatement stBuscarVenta = null;
    PreparedStatement stActualizarVenta = null;
    PreparedStatement stEliminarVenta = null;
    PreparedStatement stActualizarDetalle = null;
    PreparedStatement stEliminarDetalle = null;
    PreparedStatement stBuscarTipoVenta = null;
    PreparedStatement stBuscarDeuda = null;
    PreparedStatement stActualizarDeuda = null;
    PreparedStatement stActualizarDetalleDeuda = null;
    ResultSet rsVenta = null;
    ResultSet rsTipoVenta = null;
    ResultSet rsDeuda = null;

    String queryBuscarVenta = "SELECT cantidad, importe FROM ventas WHERE id_detalleVenta = ? AND id_producto = ?";
    String queryActualizarVenta = "UPDATE ventas SET cantidad = ?, importe = ? WHERE id_detalleVenta = ? AND id_producto = ?";
    String queryEliminarVenta = "DELETE FROM ventas WHERE id_detalleVenta = ? AND id_producto = ?";
    String querySumarImportes = "SELECT SUM(importe) as total FROM ventas WHERE id_detalleVenta = ?";
    String queryActualizarTotal = "UPDATE detalle_ventas SET importe_total = ? WHERE id_detalleVenta = ?";
    String queryContarVentas = "SELECT COUNT(*) as total FROM ventas WHERE id_detalleVenta = ?";
    String queryEliminarDetalle = "DELETE FROM detalle_ventas WHERE id_detalleVenta = ?";
    String queryBuscarTipoVenta = "SELECT tipo_venta FROM detalle_ventas WHERE id_detalleVenta = ?";
    String queryBuscarDeuda = "SELECT d.id_deuda, d.monto_deuda, dd.id_detalleDeuda, dd.total_deuda, dd.deuda_pendiente FROM deudas d JOIN detalle_deudas dd ON d.id_detalleDeuda = dd.id_detalleDeuda WHERE d.id_detalleVenta = ?";
    String queryActualizarDeuda = "UPDATE deudas SET monto_deuda = ? WHERE id_deuda = ?";
    String queryActualizarDetalleDeuda = "UPDATE detalle_deudas SET total_deuda = ?, deuda_pendiente = ? WHERE id_detalleDeuda = ?";

    try {
        cn = Conexion.Conectar();
        cn.setAutoCommit(false);

        // 1. Buscar datos de la venta
        stBuscarVenta = cn.prepareStatement(queryBuscarVenta);
        stBuscarVenta.setInt(1, idDetalleVenta);
        stBuscarVenta.setInt(2, idProducto);
        rsVenta = stBuscarVenta.executeQuery();

        if (!rsVenta.next()) throw new SQLException("No se encontró la venta.");

        double cantidadActual = rsVenta.getDouble("cantidad");
        double importeActual = rsVenta.getDouble("importe");

        if (cantidadDevuelta > cantidadActual) throw new SQLException("La cantidad devuelta no puede ser mayor a la vendida.");

        double precioUnitario = importeActual / cantidadActual;
        double nuevaCantidad = cantidadActual - cantidadDevuelta;
        double importeDevuelto = cantidadDevuelta * precioUnitario;

        // 2. Actualizar o eliminar venta
        if (nuevaCantidad <= 0) {
            stEliminarVenta = cn.prepareStatement(queryEliminarVenta);
            stEliminarVenta.setInt(1, idDetalleVenta);
            stEliminarVenta.setInt(2, idProducto);
            stEliminarVenta.executeUpdate();
        } else {
            double nuevoImporte = nuevaCantidad * precioUnitario;
            stActualizarVenta = cn.prepareStatement(queryActualizarVenta);
            stActualizarVenta.setDouble(1, nuevaCantidad);
            stActualizarVenta.setDouble(2, nuevoImporte);
            stActualizarVenta.setInt(3, idDetalleVenta);
            stActualizarVenta.setInt(4, idProducto);
            stActualizarVenta.executeUpdate();
        }

        // 3. Contar ventas y actualizar o eliminar detalle_ventas
        PreparedStatement stContar = cn.prepareStatement(queryContarVentas);
        stContar.setInt(1, idDetalleVenta);
        ResultSet rsContar = stContar.executeQuery();
        int totalVentas = rsContar.next() ? rsContar.getInt("total") : 0;
        boolean esFiado = false;
stBuscarTipoVenta = cn.prepareStatement(queryBuscarTipoVenta);
stBuscarTipoVenta.setInt(1, idDetalleVenta);
rsTipoVenta = stBuscarTipoVenta.executeQuery();

if (rsTipoVenta.next()) {
    esFiado = rsTipoVenta.getString("tipo_venta").equalsIgnoreCase("Fiado");
}
        if (totalVentas == 0 && !esFiado) {
            stEliminarDetalle = cn.prepareStatement(queryEliminarDetalle);
            stEliminarDetalle.setInt(1, idDetalleVenta);
            stEliminarDetalle.executeUpdate();
        } else {
            PreparedStatement stSumar = cn.prepareStatement(querySumarImportes);
            stSumar.setInt(1, idDetalleVenta);
            ResultSet rsSuma = stSumar.executeQuery();
            double nuevoTotal = rsSuma.next() ? rsSuma.getDouble("total") : 0;

            stActualizarDetalle = cn.prepareStatement(queryActualizarTotal);
            stActualizarDetalle.setDouble(1, nuevoTotal);
            stActualizarDetalle.setInt(2, idDetalleVenta);
            stActualizarDetalle.executeUpdate();
        }

        // 4. Verificar si es venta fiada
        stBuscarTipoVenta = cn.prepareStatement(queryBuscarTipoVenta);
        stBuscarTipoVenta.setInt(1, idDetalleVenta);
        rsTipoVenta = stBuscarTipoVenta.executeQuery();

        if (rsTipoVenta.next() && rsTipoVenta.getString("tipo_venta").equalsIgnoreCase("Fiado")) {
            // Buscar deuda asociada
            stBuscarDeuda = cn.prepareStatement(queryBuscarDeuda);
            stBuscarDeuda.setInt(1, idDetalleVenta);
            rsDeuda = stBuscarDeuda.executeQuery();

            if (rsDeuda.next()) {
                int idDeuda = rsDeuda.getInt("id_deuda");
                int idDetalleDeuda = rsDeuda.getInt("id_detalleDeuda");
                double montoActual = rsDeuda.getDouble("monto_deuda");
                double totalDeudaActual = rsDeuda.getDouble("total_deuda");
                double pendienteActual = rsDeuda.getDouble("deuda_pendiente");

                double nuevoMonto = montoActual - importeDevuelto;
                double nuevoTotalDeuda = totalDeudaActual - importeDevuelto;
                double nuevoPendiente = pendienteActual - importeDevuelto;

                stActualizarDeuda = cn.prepareStatement(queryActualizarDeuda);
                stActualizarDeuda.setDouble(1, nuevoMonto);
                stActualizarDeuda.setInt(2, idDeuda);
                stActualizarDeuda.executeUpdate();

                stActualizarDetalleDeuda = cn.prepareStatement(queryActualizarDetalleDeuda);
                stActualizarDetalleDeuda.setDouble(1, nuevoTotalDeuda);
                stActualizarDetalleDeuda.setDouble(2, nuevoPendiente);
                stActualizarDetalleDeuda.setInt(3, idDetalleDeuda);
                stActualizarDetalleDeuda.executeUpdate();
            }
        }

        cn.commit();
        return true;

    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error en rollback: " + ex);
        }
        System.out.println("Error al registrar devolución: " + e);
        return false;

    } finally {
        try {
            if (rsVenta != null) rsVenta.close();
            if (rsDeuda != null) rsDeuda.close();
            if (rsTipoVenta != null) rsTipoVenta.close();
            if (stBuscarVenta != null) stBuscarVenta.close();
            if (stActualizarVenta != null) stActualizarVenta.close();
            if (stEliminarVenta != null) stEliminarVenta.close();
            if (stActualizarDetalle != null) stActualizarDetalle.close();
            if (stEliminarDetalle != null) stEliminarDetalle.close();
            if (stBuscarTipoVenta != null) stBuscarTipoVenta.close();
            if (stBuscarDeuda != null) stBuscarDeuda.close();
            if (stActualizarDeuda != null) stActualizarDeuda.close();
            if (stActualizarDetalleDeuda != null) stActualizarDetalleDeuda.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e);
        }
    }
}


public boolean cancelarVenta(int idDetalleVenta) {
    Connection cn = null;
    PreparedStatement stEliminarVentas = null;
    PreparedStatement stEliminarDetalle = null;

    String queryEliminarVentas = "DELETE FROM ventas WHERE id_detalleVenta = ?";
    String queryEliminarDetalle = "DELETE FROM detalle_ventas WHERE id_detalleVenta = ?";

    try {
        
        cn = Conexion.Conectar();
        cn.setAutoCommit(false); // Iniciar transacción

        // Eliminar las ventas asociadas
        stEliminarVentas = cn.prepareStatement(queryEliminarVentas);
        stEliminarVentas.setInt(1, idDetalleVenta);
        stEliminarVentas.executeUpdate();

        // Eliminar el detalle de la venta
        stEliminarDetalle = cn.prepareStatement(queryEliminarDetalle);
        stEliminarDetalle.setInt(1, idDetalleVenta);
        stEliminarDetalle.executeUpdate();

        cn.commit();
        return true;

    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error al hacer rollback: " + ex);
        }
        System.out.println("Error al cancelar venta: " + e);
        return false;
    } finally {
        try {
            if (stEliminarVentas != null) stEliminarVentas.close();
            if (stEliminarDetalle != null) stEliminarDetalle.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e);
        }
    }
    
}
public String obtenerTipoVenta(int idDetalleVenta) {
    Connection cn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    String tipoVenta = "";

    String query = "SELECT tipo_venta FROM detalle_ventas WHERE id_detalleVenta = ?";

    try {
        cn = Conexion.Conectar();
        st = cn.prepareStatement(query);
        st.setInt(1, idDetalleVenta);
        rs = st.executeQuery();

        if (rs.next()) {
            tipoVenta = rs.getString("tipo_venta"); // Asumiendo que el tipo de venta está en la columna 'tipo_venta'
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener tipo de venta: " + e);
    } finally {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e);
        }
    }
    return tipoVenta;
}

public boolean cancelarVentaConDeuda(int idDetalleVenta) {
    Connection cn = null;
    PreparedStatement psDeuda = null;
    PreparedStatement psActualizarDetalles = null;
    PreparedStatement psEliminarDeuda = null;
    PreparedStatement psEliminarVentas = null;
    PreparedStatement psEliminarDetalle = null;
    ResultSet rsDeuda = null;

    String queryDeuda = "SELECT id_deuda, monto_deuda FROM deudas WHERE id_detalleVenta = ?";
    String queryActualizarDetalles = "UPDATE detalle_deudas SET total_deuda = total_deuda - ?, deuda_pendiente = deuda_pendiente - ? WHERE id_detalleDeuda = ?";
    String queryEliminarDeuda = "DELETE FROM deudas WHERE id_detalleVenta = ?";
    String queryEliminarVentas = "DELETE FROM ventas WHERE id_detalleVenta = ?";
    String queryEliminarDetalle = "DELETE FROM detalle_ventas WHERE id_detalleVenta = ?";

    try {
        cn = Conexion.Conectar();
        cn.setAutoCommit(false);

        // 2. Obtener datos de deuda
        psDeuda = cn.prepareStatement(queryDeuda);
        psDeuda.setInt(1, idDetalleVenta);
        rsDeuda = psDeuda.executeQuery();

        int idDeuda = 0;
        double montoDeuda = 0;

        if (rsDeuda.next()) {
            idDeuda = rsDeuda.getInt("id_deuda");
            montoDeuda = rsDeuda.getDouble("monto_deuda");
        }

        // 3. Actualizar detalles_deudas
        psActualizarDetalles = cn.prepareStatement(queryActualizarDetalles);
        psActualizarDetalles.setDouble(1, montoDeuda);
        psActualizarDetalles.setDouble(2, montoDeuda);
        psActualizarDetalles.setInt(3, idDeuda);
        psActualizarDetalles.executeUpdate();

        // 4. Eliminar deuda
        psEliminarDeuda = cn.prepareStatement(queryEliminarDeuda);
        psEliminarDeuda.setInt(1, idDetalleVenta);
        psEliminarDeuda.executeUpdate();

        // 5. Eliminar ventas y detalle_ventas
        psEliminarVentas = cn.prepareStatement(queryEliminarVentas);
        psEliminarVentas.setInt(1, idDetalleVenta);
        psEliminarVentas.executeUpdate();

        psEliminarDetalle = cn.prepareStatement(queryEliminarDetalle);
        psEliminarDetalle.setInt(1, idDetalleVenta);
        psEliminarDetalle.executeUpdate();

        cn.commit();
        return true;

    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error al hacer rollback: " + ex);
        }
        System.out.println("Error al cancelar venta fiado: " + e);
        return false;
    } finally {
        try {
            if (psDeuda != null) psDeuda.close();
            if (psActualizarDetalles != null) psActualizarDetalles.close();
            if (psEliminarDeuda != null) psEliminarDeuda.close();
            if (psEliminarVentas != null) psEliminarVentas.close();
            if (psEliminarDetalle != null) psEliminarDetalle.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e);
        }
    }
}



public double obtenerImporteDeuda(int idDetalleVenta) {
    Connection cn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    double importeDeuda = 0;

    String query = "SELECT total_deuda FROM detalle_deudas WHERE id_detalleVenta = ?";

    try {
        cn = Conexion.Conectar();
        st = cn.prepareStatement(query);
        st.setInt(1, idDetalleVenta);
        rs = st.executeQuery();

        if (rs.next()) {
            importeDeuda = rs.getDouble("total_deuda"); // Suponiendo que la deuda está en la columna 'total_deuda'
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener importe de deuda: " + e);
    } finally {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e);
        }
    }
    return importeDeuda;
}








}

