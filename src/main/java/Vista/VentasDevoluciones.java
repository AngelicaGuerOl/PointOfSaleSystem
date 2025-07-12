/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Conexion.Conexion;
import Controlador.CtrlVentas;
import Controlador.ctrl_movimientos;
import Modelo.movimientosInventario;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Angelica Guerrero
 */
public class VentasDevoluciones extends javax.swing.JPanel {

    DefaultTableModel modeloDetalle = new DefaultTableModel();
    DefaultTableModel modeloVenta = new DefaultTableModel();

    private boolean is_selected = false;
    private int id_detalle;
    private int idVentaSeleccionado = -1;
    private int id_venta;
    private double cantidadSeleccionada = 0;
    private static VentasDevoluciones instance;

    /**
     * Creates new form PCobrar
     */
    public VentasDevoluciones() {
        initComponents();
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamPantalla = pantalla.getScreenSize();
        int alto = tamPantalla.height;
        int ancho = tamPantalla.width;
        setSize(ancho / 2, alto / 2);
        this.setLayout(new BorderLayout());
        pnl_entradas.setVisible(true);
        this.add(pnl_entradas, BorderLayout.CENTER); // Agregar pnl_cobrar al centro del panel
        tablaDetalleVentas();
        cargar_tablaDetalleVentasHoy();
        tablaVenta();
        detalleVentas();
        tventas();
    }

    public void detalleVentas() {
        tb_detalle.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila_seleccionada = tb_detalle.getSelectedRow();
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    id_detalle = (int) tb_detalle.getValueAt(fila_seleccionada, 0);
                    cargarTablaVentasPorDetalle(id_detalle);
                }
            }
        });
    }

    public int getIdDetalle() {
        return id_detalle;
    }

    public void tventas() {
        tb_venta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila_seleccionada = tb_venta.getSelectedRow();
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    // Asegúrate de que la columna 0 es idVenta y la columna 1 es cantidad
                    id_venta = (int) tb_venta.getValueAt(fila_seleccionada, 0);
                    this.idVentaSeleccionado = id_venta;
                    cantidadSeleccionada = (Double) tb_venta.getValueAt(fila_seleccionada, 1);
                    
                }
            }
        });
        
    }
     public int getIdVenta() {
    return id_venta;
}

    public static VentasDevoluciones getInstance() {
        if (instance == null) {
            instance = new VentasDevoluciones();
        }
        return instance;
    }

    private void tablaDetalleVentas() {
        modeloDetalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloDetalle.addColumn("Folio");
        modeloDetalle.addColumn("Hora");
        modeloDetalle.addColumn("Total");
        modeloDetalle.addColumn("Tipo venta");
        // Crear una nueva instancia de JTable usando el modelo de datos 'modeloClientes'
        tb_detalle = new JTable(modeloDetalle);
        scp_detalle.setViewportView(tb_detalle);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_detalle.getColumnCount(); i++) {
            tb_detalle.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

    }

    private void tablaVenta() {
        modeloVenta = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloVenta.addColumn("ID");
        modeloVenta.addColumn("Cantidad");
        modeloVenta.addColumn("Descripción");
        modeloVenta.addColumn("Importe");
        modeloVenta.addColumn("id producto");
        // Crear una nueva instancia de JTable usando el modelo de datos 'modeloClientes'
        tb_venta = new JTable(modeloVenta);
        scp_venta.setViewportView(tb_venta);
        tb_venta.getColumnModel().getColumn(0).setMinWidth(0);
        tb_venta.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_venta.getColumnModel().getColumn(0).setWidth(0);
        tb_venta.getColumnModel().getColumn(4).setMinWidth(0);
        tb_venta.getColumnModel().getColumn(4).setMaxWidth(0);
        tb_venta.getColumnModel().getColumn(4).setWidth(0);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_venta.getColumnCount(); i++) {
            tb_venta.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_entradas = new javax.swing.JPanel();
        scp_detalle = new javax.swing.JScrollPane();
        tb_detalle = new javax.swing.JTable();
        scp_venta = new javax.swing.JScrollPane();
        tb_venta = new javax.swing.JTable();
        btn_devolver1 = new javax.swing.JButton();
        btn_devolver = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        btn_devolver2 = new javax.swing.JButton();

        pnl_entradas.setBackground(new java.awt.Color(255, 255, 255));

        scp_detalle.setForeground(new java.awt.Color(255, 255, 255));

        tb_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_detalle.setFocusable(false);
        tb_detalle.setGridColor(new java.awt.Color(153, 153, 153));
        tb_detalle.setRowHeight(25);
        tb_detalle.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_detalle.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_detalle.setShowHorizontalLines(true);
        tb_detalle.getTableHeader().setReorderingAllowed(false);
        scp_detalle.setViewportView(tb_detalle);

        scp_venta.setForeground(new java.awt.Color(255, 255, 255));

        tb_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_venta.setFocusable(false);
        tb_venta.setGridColor(new java.awt.Color(153, 153, 153));
        tb_venta.setRowHeight(25);
        tb_venta.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_venta.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_venta.setShowHorizontalLines(true);
        tb_venta.getTableHeader().setReorderingAllowed(false);
        scp_venta.setViewportView(tb_venta);

        btn_devolver1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_devolver1.setText("Cancelar venta");
        btn_devolver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_devolver1ActionPerformed(evt);
            }
        });

        btn_devolver.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_devolver.setText("Devolver articulo seleccionado");
        btn_devolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_devolverActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 153, 51));
        jLabel31.setText("Ventas del día y devoluciones");

        btn_devolver2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_devolver2.setText("Regresar");
        btn_devolver2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_devolver2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_entradasLayout = new javax.swing.GroupLayout(pnl_entradas);
        pnl_entradas.setLayout(pnl_entradasLayout);
        pnl_entradasLayout.setHorizontalGroup(
            pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_entradasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scp_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_entradasLayout.createSequentialGroup()
                        .addComponent(scp_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_entradasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_entradasLayout.createSequentialGroup()
                                .addComponent(btn_devolver, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_entradasLayout.createSequentialGroup()
                                .addComponent(btn_devolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_entradasLayout.createSequentialGroup()
                                .addComponent(btn_devolver2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(164, 164, 164))))))
            .addGroup(pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_entradasLayout.createSequentialGroup()
                    .addGap(317, 317, 317)
                    .addComponent(jLabel31)
                    .addContainerGap(318, Short.MAX_VALUE)))
        );
        pnl_entradasLayout.setVerticalGroup(
            pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_entradasLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_entradasLayout.createSequentialGroup()
                        .addComponent(scp_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_devolver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_devolver1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_devolver2))
                    .addComponent(scp_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(pnl_entradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_entradasLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel31)
                    .addContainerGap(429, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 966, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnl_entradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnl_entradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_devolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_devolver1ActionPerformed
       // TODO add your handling code here:
       if (is_selected) {
        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas cancelar esta venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CtrlVentas ctrl = new CtrlVentas();

            String tipoVenta = ctrl.obtenerTipoVenta(id_detalle);
            boolean cancelado = false;
            // ✅ Si no hay abonos o es contado, continuar
            if ("contado".equalsIgnoreCase(tipoVenta)) {
                cancelarVentaContado(id_detalle);
                cancelado = ctrl.cancelarVenta(id_detalle);
            } else if ("fiado".equalsIgnoreCase(tipoVenta)) {
                cancelarVentaContado(id_detalle); // para sumar stock y registrar devolución
                cancelarDeudaVentaFiado(id_detalle); // actualiza detalle_deudas y elimina en deudas
                cancelado = ctrl.cancelarVenta(id_detalle); // elimina en ventas y detalle_ventas
            }

            if (cancelado) {
                JOptionPane.showMessageDialog(null, "Venta cancelada correctamente.");
                cargar_tablaDetalleVentasHoy();
                limpiarTablaVentas();
                FMenu fMenu = FMenu.getInstance(); // Asegúrate de que estás obteniendo la instancia correcta de FMenu
                FMenu.getInstance().cargar_tablaProductos();
                FMenu.getInstance().cargar_tablaMovimientos();
                FMenu.getInstance().cargar_tablaProductosBajoInv();
                FMenu.getInstance().cargar_tablaProductosInventario();
                FMenu.getInstance().cargarHistorialDeudas();
                int idClienteEstado = fMenu.getIdClienteEstado();
                FMenu.getInstance().cargarTablaEstadoCuentaFiado(idClienteEstado);
                FMenu.getInstance().totalExistencias();
                FMenu.getInstance().totalSaldoInventario();
                FMenu.getInstance().mostrarResumenDeudaCliente(idClienteEstado);
                is_selected = false;
            } else {
                JOptionPane.showMessageDialog(null, "Error al cancelar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selecciona una venta primero.","Advertencia",JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btn_devolver1ActionPerformed

    private void btn_devolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_devolverActionPerformed
      // Obtener la fila seleccionada en las tablas
       int filaDetalle = tb_detalle.getSelectedRow();
    int filaVenta = tb_venta.getSelectedRow();

    if (filaDetalle == -1 || filaVenta == -1) {
        JOptionPane.showMessageDialog(this, "Debes seleccionar una venta y un producto.");
        return;
    }

    int idDetalleVenta = Integer.parseInt(tb_detalle.getValueAt(filaDetalle, 0).toString());
    int idProducto = Integer.parseInt(tb_venta.getValueAt(filaVenta, 4).toString());

    // Si se puede continuar, abrir ventana de devolución
    Devolver pnlDevolver = new Devolver(idDetalleVenta, idProducto, this);
    JDialog dialog = new JDialog((Frame) null, "Devolución de artículos", true);
    dialog.setContentPane(pnlDevolver);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
        
    }//GEN-LAST:event_btn_devolverActionPerformed

    private void btn_devolver2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_devolver2ActionPerformed
        // TODO add your handling code here:
        ((JDialog) SwingUtilities.getWindowAncestor(this)).dispose();

    }//GEN-LAST:event_btn_devolver2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_devolver;
    private javax.swing.JButton btn_devolver1;
    private javax.swing.JButton btn_devolver2;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel pnl_entradas;
    public static javax.swing.JScrollPane scp_detalle;
    public static javax.swing.JScrollPane scp_venta;
    public static javax.swing.JTable tb_detalle;
    public static javax.swing.JTable tb_venta;
    // End of variables declaration//GEN-END:variables

    public void cargar_tablaDetalleVentasHoy() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT id_detalleVenta, fecha_venta, importe_total, tipo_venta "
                + "FROM detalle_ventas "
                + "WHERE DATE(fecha_venta) = CURDATE() "
                + "ORDER BY fecha_venta DESC";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();

            // Limpiar modelo
            while (modeloDetalle.getRowCount() > 0) {
                modeloDetalle.removeRow(0);
            }

            // Asociar tabla al scroll
            scp_detalle.setViewportView(tb_detalle);

            while (rs.next()) {
                Object fila[] = new Object[4];

                // Obtener timestamp y formatear la hora local
                Timestamp timestamp = rs.getTimestamp("fecha_venta");
                TimeZone timeZone = TimeZone.getDefault();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(timeZone);
                String horaFormateada = sdf.format(timestamp);

                // Asignar valores
                fila[0] = rs.getInt("id_detalleVenta");          // Folio
                fila[1] = horaFormateada;                        // Hora
                fila[2] = String.format("%.2f", rs.getDouble("importe_total")); // Total
                fila[3] = rs.getString("tipo_venta");            // Tipo venta

                modeloDetalle.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Falló la consulta de ventas del día: " + e);
        }

        is_selected = false;
    }

    public void cargarTablaVentasPorDetalle(int idDetalleVenta) {
        Connection cn = Conexion.Conectar();
        String query = "SELECT v.id_venta, v.cantidad, p.descripcion_producto, v.importe, v.id_producto "
                + "FROM ventas v "
                + "JOIN productos p ON v.id_producto = p.id_producto "
                + "WHERE v.id_detalleVenta = ?";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, idDetalleVenta);
            ResultSet rs = st.executeQuery();

            // Limpiar modelo actual
            while (modeloVenta.getRowCount() > 0) {
                modeloVenta.removeRow(0);
            }

            while (rs.next()) {
                Object fila[] = new Object[5];
                fila[0] = rs.getInt("id_venta");
                fila[1] = rs.getDouble("cantidad");
                fila[2] = rs.getString("descripcion_producto"); // descripción del producto
                fila[3] = String.format("%.2f", rs.getDouble("importe"));
                fila[4] = rs.getInt("id_producto");
                modeloVenta.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar ventas de la fila seleccionada: " + e);
        }
    }
    public void limpiarTablaVentas() {
    DefaultTableModel modelo = (DefaultTableModel) tb_venta.getModel();
    modelo.setRowCount(0);
}
    
public void cancelarVentaContado(int idDetalleVenta) {
    Connection cn = Conexion.Conectar();
    String query = "SELECT v.cantidad, v.id_producto "
                 + "FROM ventas v "
                 + "WHERE v.id_detalleVenta = ?";

    try (PreparedStatement st = cn.prepareStatement(query)) {
        st.setInt(1, idDetalleVenta);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            double cantidadDevuelta = rs.getDouble("cantidad");
            int idProducto = rs.getInt("id_producto");

            double stockAnterior = obtenerStockActual(idProducto);
            double stockNuevo = stockAnterior + cantidadDevuelta;

            // Actualizar el stock del producto
            sumarStockProductos(idProducto, cantidadDevuelta);

            // Registrar el movimiento de tipo DEVOLUCION
            movimientosInventario movimiento = new movimientosInventario();
            movimiento.setFechaMovimiento(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            movimiento.setTipoMovimiento("DEVOLUCION");
            movimiento.setCantidad(cantidadDevuelta);
            movimiento.setCantidadAnt(stockAnterior);
            movimiento.setCantidadActual(stockNuevo);
            movimiento.setIdProducto(idProducto);
            movimiento.setIdUsuario(Login.id_usuario);

            ctrl_movimientos ctrlMov = new ctrl_movimientos();
            ctrlMov.agregarMovimientoInventario(movimiento);
        }

        cn.close();
    } catch (SQLException e) {
        System.out.println("Error al cancelar venta y registrar devoluciones: " + e);
    }
}
 public double obtenerStockActual(int idProducto) {
    double stock = 0;
    Connection cn = Conexion.Conectar();
    String query = "SELECT existencias_producto FROM productos WHERE id_producto = ?";

    try (PreparedStatement ps = cn.prepareStatement(query)) {
        ps.setInt(1, idProducto);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            stock = rs.getDouble("existencias_producto");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener stock actual: " + e);
    }

    return stock;
}
 public void sumarStockProductos(int idProducto, double cantidad) {
    Connection cn = Conexion.Conectar();
    String query = "UPDATE productos SET existencias_producto = existencias_producto + ? WHERE id_producto = ?";

    try (PreparedStatement ps = cn.prepareStatement(query)) {
        ps.setDouble(1, cantidad);
        ps.setInt(2, idProducto);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error al sumar stock: " + e);
    }
}
 
public void cancelarDeudaVentaFiado(int idDetalleVenta) {
    Connection cn = null;
    PreparedStatement psSelect = null;
    PreparedStatement psUpdateDetalleDeuda = null;
    PreparedStatement psDeleteDeuda = null;
    ResultSet rs = null;

    String sqlSelect = "SELECT id_deuda, id_detalleDeuda, monto_deuda FROM deudas WHERE id_detalleVenta = ?";
    String sqlUpdateDetalleDeuda = "UPDATE detalle_deudas SET total_deuda = total_deuda - ?, deuda_pendiente = deuda_pendiente - ? WHERE id_detalleDeuda = ?";
    String sqlDeleteDeuda = "DELETE FROM deudas WHERE id_detalleVenta = ?";

    try {
        cn = Conexion.Conectar();
        cn.setAutoCommit(false);

        psSelect = cn.prepareStatement(sqlSelect);
        psSelect.setInt(1, idDetalleVenta);
        rs = psSelect.executeQuery();

        if (rs.next()) {
            int idDetalleDeuda = rs.getInt("id_detalleDeuda");
            double montoDeuda = rs.getDouble("monto_deuda");

            psUpdateDetalleDeuda = cn.prepareStatement(sqlUpdateDetalleDeuda);
            psUpdateDetalleDeuda.setDouble(1, montoDeuda);
            psUpdateDetalleDeuda.setDouble(2, montoDeuda);
            psUpdateDetalleDeuda.setInt(3, idDetalleDeuda);
            psUpdateDetalleDeuda.executeUpdate();

            psDeleteDeuda = cn.prepareStatement(sqlDeleteDeuda);
            psDeleteDeuda.setInt(1, idDetalleVenta);
            psDeleteDeuda.executeUpdate();
        }

        cn.commit();
    } catch (SQLException e) {
        try {
            if (cn != null) cn.rollback();
        } catch (SQLException ex) {
            System.out.println("Error rollback: " + ex);
        }
        System.out.println("Error al actualizar deuda: " + e);
    } finally {
        try {
            if (rs != null) rs.close();
            if (psSelect != null) psSelect.close();
            if (psUpdateDetalleDeuda != null) psUpdateDetalleDeuda.close();
            if (psDeleteDeuda != null) psDeleteDeuda.close();
            if (cn != null) {
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar conexión: " + ex);
        }
    }
}



}
