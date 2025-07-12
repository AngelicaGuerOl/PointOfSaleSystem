/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Angelica Guerrero
 */
public class CtrlProductos {

    public boolean agregar(Productos obj) {
        Connection cn = null;
        PreparedStatement st = null;
        String query = "INSERT INTO productos(id_producto,id_categoria,codigo_producto,descripcion_producto,unidad_producto,precio_costo,precio_venta,existencias_producto) VALUES (?,?,?,?,?,?,?,?)";

        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setInt(1, 0);
            st.setInt(2, obj.getIdCategoria());
            st.setString(3, obj.getCodigo());
            st.setString(4, obj.getDescripcion());
            st.setString(5, obj.getUnidad());
            st.setDouble(6, obj.getPrecio_costo());
            st.setDouble(7, obj.getPrecio_venta());
            st.setDouble(8, obj.getExistencias());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Falló la inserción en articulos" + e);
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

    public boolean actualizar(Productos obj, int id_producto) {
        boolean respuesta = false;
        String errorMessage = "";
        Connection cn = null;
        PreparedStatement st = null;
        String query = "UPDATE productos SET id_categoria=?,codigo_producto=?,descripcion_producto=?,unidad_producto=?,precio_costo=?,precio_venta=?,existencias_producto=? WHERE id_producto=?";

        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setInt(1, obj.getIdCategoria());
            st.setString(2, obj.getCodigo());
            st.setString(3, obj.getDescripcion());
            st.setString(4, obj.getUnidad());
            st.setDouble(5, obj.getPrecio_costo());
            st.setDouble(6, obj.getPrecio_venta());
            st.setDouble(7, obj.getExistencias());
            st.setInt(8, id_producto);
            if (st.executeUpdate() > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            errorMessage = "Falló actualización de productos: " + e.getMessage();
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
        return respuesta;
    }

    public boolean eliminar(int idProducto) {
        Connection cn = null;
        PreparedStatement st = null;
        String query = "DELETE FROM productos WHERE id_producto = ?";

        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setInt(1, idProducto);
            int filasAfectadas = st.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e);
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

    public boolean codigoYaExiste(String codigoBarras) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT COUNT(*) FROM productos WHERE codigo_producto = ?";

        try {
            cn = Conexion.Conectar(); // Establece la conexión
            st = cn.prepareStatement(query); // Prepara la consulta
            st.setString(1, codigoBarras);
            rs = st.executeQuery(); // Ejecuta la consulta

            if (rs.next()) {
                return rs.getInt(1) > 0; // Si existe, devuelve true
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar código de barras: " + e);
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

        return false;  // Si no encuentra ningún producto con el mismo código, retorna false
    }

    public boolean actualizarExistencias(int idProducto, double nuevasExistencias) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Conexion.Conectar();
            String sql = "UPDATE productos SET existencias_producto = ? WHERE id_producto = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, nuevasExistencias);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int obtenerIdPorCodigo(String codigoBarras) {
        int id = -1;
        Connection cn = Conexion.Conectar();
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = cn.prepareStatement("SELECT id_producto FROM productos WHERE codigo_producto = ?");
            ps.setString(1, codigoBarras);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_producto");
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener ID del producto: " + e);
        }

        return id;
    }

}
