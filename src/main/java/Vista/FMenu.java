/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Conexion.Conexion;
import Controlador.CtrlCategoria;
import Controlador.CtrlCliente;
import Controlador.CtrlProductos;
import Controlador.CtrlUsuario;
import Controlador.CtrlVentas;
import Controlador.ctrl_movimientos;
import Modelo.Categoria;
import Modelo.Cliente;
import Modelo.DetallesVentas;
import Modelo.Productos;
import Modelo.Usuario;
import Modelo.Ventas;
import Modelo.movimientosInventario;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Angelica Guerrero
 */
public class FMenu extends javax.swing.JFrame {

    Color colorFondo = new Color(222, 222, 222);
    public static JDesktopPane jmenu;
    DefaultTableModel modeloClientes = new DefaultTableModel();
    DefaultTableModel modeloCategorias = new DefaultTableModel();
    DefaultTableModel modeloProductos = new DefaultTableModel();
    DefaultTableModel modeloProductosBajoInv = new DefaultTableModel();
    DefaultTableModel modeloVentas = new DefaultTableModel();
    DefaultTableModel modeloReporteInventario = new DefaultTableModel();
    DefaultTableModel modeloReporteMovimientos = new DefaultTableModel();
    DefaultTableModel modeloEstadoCuenta = new DefaultTableModel();
    DefaultTableModel modeloEstadoCuentaCte = new DefaultTableModel();
    DefaultTableModel ModeloHistorialDeudas = new DefaultTableModel();
    DefaultTableModel ModeloVentasPeriodo = new DefaultTableModel();
    DefaultTableModel modeloEntSal = new DefaultTableModel();
    DefaultTableModel modeloDevoluciones = new DefaultTableModel();
    DefaultTableModel modeloAbonos = new DefaultTableModel();
    DefaultTableModel modeloUsuarios = new DefaultTableModel();

    private int id_cliente;
    private int id_clienteEstado;
    private int id_categoria;
    private int id_producto;
    private int id_venta;
    private int id_usuario;
    private boolean is_selected = false;
    int obtenerIdCategoria = 0;
        Color colorSeleccionado = new Color(234, 234, 230); // más oscuro
Color colorNormal = new Color(204, 204, 204);       // color original

    private Timer timer;

    public static ArrayList<Ventas> listaProducto = new ArrayList<>();
    public static ArrayList<Productos> listaProductosDesc = new ArrayList<>();
    private Ventas productos;
    private int idProducto = 0;
    private String descripcion = "";

    private int cantidadP = 0;
    private double precio_unitario = 0.0;
    //private int cantidad = 0;
    private double importe = 0.0;
    private double importe_total = 0.0;

    private int auxdet = 1;

    //variables para calculos globales
    private double importeGeneral = 0.0;
    private double importeTotalGeneral = 0.0;
    private boolean cargandoCategorias = false;
    private static FMenu instance;

    /**
     * Creates new form FMenu
     */
    public FMenu() {
        initComponents();
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamPantalla = pantalla.getScreenSize();
        int ancho = tamPantalla.width;
        int alto = tamPantalla.height;
        setSize(ancho, alto);
        // Configurar el layout
        // Configurar tamaño del contenedor principal
        contenedorPrincipal.setPreferredSize(new Dimension(ancho, alto));
        this.setTitle("Sistema de tienda de abarrotes");
        this.setLayout(new BorderLayout()); // Usar BorderLayout para la disposición
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        pnl_clientes.setVisible(false);
        pnl_productos.setVisible(false);
        pnl_ventas.setVisible(true);
        pnl_inventario.setVisible(false);
        instance = this;
        // Crear el JDesktopPane
        jmenu = new JDesktopPane();
        this.add(jmenu, BorderLayout.CENTER); // Añadir el JDesktopPane al centro
        txt_codigo.requestFocus();
        // Crear un objeto Font con la fuente "Segoe UI", estilo Font.BOLD y tamaño 12
        this.cargarCategoriasActualizarProducto();
        this.cargarCategoriasGuardarProducto();
        this.cargarCategoriasReporteInventario();
        btn_vtas.setBackground(colorSeleccionado);
        tablaClientes();
        tablaCategorias();
        tablaProductos();
        tablaEntradasSalidas();
        tablaDevoluciones();
        tablaVentas();
        tablaAbonos();
        AccionestbProductos();
        tbCategorias();
        cargarTablaClientes();
        cargar_tablaCategorias();
        cargar_tablaProductos();
        tablaProductosBajoInventario();
        cargar_tablaProductosBajoInv();
        tablaReporteInventario();
        tablaReporteMovimientos();
        tablaUsuarios();
        tbUsuarios();
        cargarTablaUsuarios();
        tablaEstadoCuenta();
        tablaHistorialDeudas();
        cargarHistorialDeudas();
        cargarTablaEstadoCuenta();
        tablaEstadoCuentaCliente();
        cargarTablaUsuarios();
        tbClientes();
        tabEstadCuenta();
        cargar_tablaProductosInventario();
        totalExistencias();
        totalSaldoInventario();
        cargar_tablaMovimientos();
        tablaVentasPeriodo();
        
        this.setFocusable(true); // Asegurarse de que el JFrame pueda recibir eventos de teclado

        tb_ventas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    int selectedRow = tb_ventas.getSelectedRow();
                    if (selectedRow != -1) {
                        eliminarProducto(selectedRow); // pass the selected row index to the method
                        txt_codigo.requestFocus();
                    }
                }
            }
        });
       

        // Agregar listener de teclado al campo de texto del código del producto
        txt_codigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    // Transferir el foco a la tabla
                    tb_ventas.requestFocus();
                    // Seleccionar la primera fila de la tabla
                    tb_ventas.getSelectionModel().setSelectionInterval(0, 0);

                }
                
            }

        });
        txt_buscarClienteEst.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    // Transferir el foco a la tabla
                    tb_estCta.requestFocus();
                    // Seleccionar la primera fila de la tabla
                    tb_estCta.getSelectionModel().setSelectionInterval(0, 0);

                }
            }
        });

        text_codigoBarrasInv.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                text_codigoBarrasInvKeyReleased(evt);
            }
        });
        cmb_catRep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!cargandoCategorias) {
                    filtrarProductosPorCategoria();
                }
            }
        });

        modeloVentas.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 0) {
                        TableModelListener self = this;
                        modeloVentas.removeTableModelListener(self); // Evita recursividad
                        try {
                            int idProducto = listaProducto.get(row).getId_producto();
                            Productos producto = buscarProductoPorID(idProducto);

                            if (producto == null) {
                                JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            String cantidadStr = modeloVentas.getValueAt(row, column).toString().trim();
                            // Si la celda está vacía, no haces nada
                            if (cantidadStr.isEmpty()) {
                                return; // Sale del método sin hacer nada
                            }
                            if (!cantidadStr.matches("^\\d+(\\.\\d+)?$")) {
                                JOptionPane.showMessageDialog(null, "Ingrese números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                                modeloVentas.setValueAt(listaProducto.get(row).getCantidad(), row, column);
                                return;
                            }

                            boolean cantidadAceptada = false;

                            if (producto.getUnidad().equalsIgnoreCase("Pieza") || producto.getUnidad().equalsIgnoreCase("Pza")) {
                                double valor = Double.parseDouble(cantidadStr);

                                if (valor % 1 != 0) {
                                    JOptionPane.showMessageDialog(null, "Solo se permiten cantidades enteras para productos por pieza.", "Cantidad inválida", JOptionPane.WARNING_MESSAGE);
                                    modeloVentas.setValueAt((int) listaProducto.get(row).getCantidad(), row, column);
                                } else {
                                    int nuevaCantidad = (int) valor;
                                    double cantidadAnterior = listaProducto.get(row).getCantidad();

                                    // Validación de stock antes de modificar
                                    if (nuevaCantidad > producto.getExistencias()) {
                                        JOptionPane.showMessageDialog(null, "La cantidad supera el stock disponible", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                        modeloVentas.setValueAt((int) cantidadAnterior, row, column);
                                    } else {
                                        modificarCantidadProducto(idProducto, nuevaCantidad, row);
                                        listaProducto.get(row).setCantidad(nuevaCantidad); // ✅ Solo si fue válida
                                        cantidadAceptada = true;
                                    }
                                }

                            } else if (producto.getUnidad().equalsIgnoreCase("Granel")) {
                                double nuevaCantidad = Double.parseDouble(cantidadStr);
                                double redondeado = Math.round(nuevaCantidad * 100.0) / 100.0;

                                if (!cantidadStr.matches("^\\d+(\\.\\d{1,2})?$")) {
                                    modeloVentas.setValueAt(String.format("%.2f", redondeado), row, column);
                                } else {
                                    modeloVentas.setValueAt(cantidadStr, row, column);
                                }

                                double cantidadAnterior = listaProducto.get(row).getCantidad();

                                // Validación de stock antes de modificar
                                if (redondeado > producto.getExistencias()) {
                                    JOptionPane.showMessageDialog(null, "La cantidad supera el stock disponible", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                    modeloVentas.setValueAt(String.format("%.2f", cantidadAnterior), row, column);
                                } else {
                                    modificarCantidadProducto(idProducto, redondeado, row);
                                    listaProducto.get(row).setCantidad(redondeado); // ✅ Solo si fue válida
                                    cantidadAceptada = true;
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Unidad de producto no reconocida.", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                            modeloVentas.setValueAt(listaProducto.get(row).getCantidad(), row, column);
                        } finally {
                            modeloVentas.addTableModelListener(self); // Restaurar listener
                        }
                    }
                }
            }
        });


    }

    private void text_codigoBarrasInvKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  // Detectar si presionó Enter
            String buscarProducto = text_codigoBarrasInv.getText().trim();
            if (!buscarProducto.isEmpty()) {
                cargarInformacionProductoInventario(buscarProducto);
            }
        }
    }

    public static FMenu getInstance() {
        if (instance == null) {
            instance = new FMenu();
        }
        return instance;
    }

    public void pnlVentas() {
        pnl_ventas.setVisible(true);
        txt_codigo.setText("");
    }

    public void limpiarTablaVentas() {
        modeloVentas.setRowCount(0);
    }

    public DefaultTableModel buscarCliente(String buscarCliente) {
        String clienteBuscar = txt_buscarCliente.getText().trim();
        int contador = 1;
        // Array de strings que contiene los nombres de las columnas de la tabla
        String nombreColumnas[] = {"N°", "Nombre", "Apellidos", "Telefono"};
        // Array de strings que se utilizará para almacenar los valores de cada fila de la tabla
        String registros[] = new String[4];
        // Crear un modelo de tabla vacío con las columnas especificadas
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumnas);
        // Consulta SQL que busca en la tabla "clientes" los registros que contienen el texto "buscarCliente"
        // en alguno de los campos "id_cliente", "nombre_cliente", "apellidos_cliente" o "telefono_cliente"
        String sql = "SELECT * FROM clientes WHERE id_cliente LIKE '%" + buscarCliente + "%' OR nombre_cliente LIKE '%" + buscarCliente + "%' OR apellidos_cliente LIKE '%" + buscarCliente + "%' OR telefono_cliente LIKE '%" + buscarCliente + "%'";
        try (Connection cn = Conexion.Conectar(); // Establecer conexión a la base de datos
                 Statement st = cn.createStatement(); // Crear un objeto Statement para ejecutar consultas SQL
                 ResultSet rs = st.executeQuery(sql)) { // Ejecutar la consulta y almacenar los resultados en un objeto ResultSet
            while (rs.next()) { // Iterar sobre los resultados de la consulta
                // Extraer los valores de los campos "id_cliente", "nombre_cliente", "apellidos_cliente" y "telefono_cliente"
                registros[0] = rs.getString("id_cliente");
                registros[1] = rs.getString("nombre_cliente");
                registros[2] = rs.getString("apellidos_cliente");
                registros[3] = rs.getString("telefono_cliente");
                // Agregar una nueva fila al modelo de tabla con los valores extraídos
                modelo.addRow(registros);
            }
        } catch (SQLException e) { // Capturar excepciones SQL
            e.printStackTrace(); // Imprimir el stack trace de la excepción
        }
        // Devolver el modelo de tabla con los datos encontrados
        return modelo;
    }

    public DefaultTableModel buscarProductos(String buscarProducto) {
        String productoBuscar = txt_buscarProducto.getText().trim();
        int contador = 1;
        // Array de strings que contiene los nombres de las columnas de la tabla
        String nombreColumnas[] = {"N°", "Categoria", "Codigo del producto", "Descripcion ", "Unidad", "Precio costo", "Precio venta", "Existencias"};
        // Array de strings que se utilizará para almacenar los valores de cada fila de la tabla
        String registros[] = new String[8];
        // Crear un modelo de tabla vacío con las columnas especificadas
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumnas);
        JTable tb_adProducto = new JTable(modelo);
        // Consulta SQL que busca en la tabla "clientes" los registros que contienen el texto "buscarCliente"
        // en alguno de los campos "id_cliente", "nombre_cliente", "apellidos_cliente" o "telefono_cliente"
        String sql = "SELECT id_producto,categorias.nombre_categoria AS categoria,codigo_producto,descripcion_producto,unidad_producto,precio_costo,precio_venta,existencias_producto FROM productos,categorias WHERE productos.id_categoria=categorias.id_categoria AND (codigo_producto LIKE '%" + buscarProducto + "%' OR descripcion_producto LIKE '%" + buscarProducto + "%')";
        try (Connection cn = Conexion.Conectar(); // Establecer conexión a la base de datos
                 Statement st = cn.createStatement(); // Crear un objeto Statement para ejecutar consultas SQL
                 ResultSet rs = st.executeQuery(sql)) { // Ejecutar la consulta y almacenar los resultados en un objeto ResultSet
            while (rs.next()) { // Iterar sobre los resultados de la consulta
                // Extraer los valores de los campos 
                registros[0] = rs.getString("id_producto");
                registros[1] = rs.getString("categoria");
                registros[2] = rs.getString("codigo_producto");
                registros[3] = rs.getString("descripcion_producto");
                registros[4] = rs.getString("unidad_producto");
                registros[5] = rs.getString("precio_costo");
                registros[6] = rs.getString("precio_venta");
                registros[7] = rs.getString("existencias_producto");
                // Agregar una nueva fila al modelo de tabla con los valores extraídos
                modelo.addRow(registros);
            }

        } catch (SQLException e) { // Capturar excepciones SQL
            e.printStackTrace(); // Imprimir el stack trace de la excepción
        }
        // Devolver el modelo de tabla con los datos encontrados
        return modelo;
    }

    public void tbClientes() {
        tb_adcliente.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila_seleccionada = tb_adcliente.getSelectedRow();
                int columna_seleccionada = 0;
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    id_cliente = (int) modeloClientes.getValueAt(fila_seleccionada, columna_seleccionada);
                    btn_modificarCte.setEnabled(true);
                    btn_eliminarCte.setEnabled(true);
                    cargarInformacionCliente(id_cliente);
                }
            }
        });
    }

    private void tablaClientes() {
        modeloClientes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloClientes.addColumn("N°");//ID
        modeloClientes.addColumn("Nombre");
        modeloClientes.addColumn("Apellidos");
        modeloClientes.addColumn("Telefono");
        tb_adcliente = new JTable(modeloClientes);
        scp_adcliente.setViewportView(tb_adcliente);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tb_adcliente.getColumnModel().getColumn(0).setMinWidth(0);
        tb_adcliente.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_adcliente.getColumnModel().getColumn(0).setWidth(0);
        for (int i = 0; i < tb_adcliente.getColumnCount(); i++) {
            tb_adcliente.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }
    
    public void tbUsuarios() {
        tb_adUsuario.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila_seleccionada = tb_adUsuario.getSelectedRow();
                int columna_seleccionada = 0;
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    id_usuario = (int) modeloUsuarios.getValueAt(fila_seleccionada, columna_seleccionada);
                    
                    cargarInformacionUsuarios(id_usuario);
                }
            }
        });
    }

    private void tablaUsuarios() {
        modeloUsuarios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloUsuarios.addColumn("N°");//ID
        modeloUsuarios.addColumn("Usuario");
        modeloUsuarios.addColumn("Contraseña");
        tb_adUsuario = new JTable(modeloUsuarios);
        scp_adUsuario.setViewportView(tb_adUsuario);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tb_adUsuario.getColumnModel().getColumn(0).setMinWidth(0);
        tb_adUsuario.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_adUsuario.getColumnModel().getColumn(0).setWidth(0);
        for (int i = 0; i < tb_adUsuario.getColumnCount(); i++) {
            tb_adUsuario.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    private void tablaEstadoCuenta() {
        modeloEstadoCuenta = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloEstadoCuenta.addColumn("N°");//ID
        modeloEstadoCuenta.addColumn("Nombre");
        modeloEstadoCuenta.addColumn("Apellidos");
        modeloEstadoCuenta.addColumn("Telefono");
        tb_estCta = new JTable(modeloEstadoCuenta);
        scp_estCta.setViewportView(tb_estCta);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tb_estCta.getColumnModel().getColumn(0).setMinWidth(0);
        tb_estCta.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_estCta.getColumnModel().getColumn(0).setWidth(0);
        for (int i = 0; i < tb_estCta.getColumnCount(); i++) {
            tb_estCta.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    private void tablaEstadoCuentaCliente() {
        modeloEstadoCuentaCte = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloEstadoCuentaCte.addColumn("Fecha°");//ID
        modeloEstadoCuentaCte.addColumn("Descripción del producto");
        modeloEstadoCuentaCte.addColumn("Cantidad");
        modeloEstadoCuentaCte.addColumn("Precio unitario");
        modeloEstadoCuentaCte.addColumn("Importe");
        tb_estCtaCte = new JTable(modeloEstadoCuentaCte);
        scp_estCtaCte.setViewportView(tb_estCtaCte);
        tb_estCtaCte.getColumnModel().getColumn(0).setPreferredWidth(50); // Establece el ancho de la columna "Artículos"
        tb_estCtaCte.getColumnModel().getColumn(1).setPreferredWidth(230); // Establece el ancho de la columna "Artículos"
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_estCtaCte.getColumnCount(); i++) {
            tb_estCtaCte.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    private void tablaEntradasSalidas() {
        modeloEntSal = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloEntSal.addColumn("ID");
        modeloEntSal.addColumn("Fecha");
        modeloEntSal.addColumn("Tipo");
        modeloEntSal.addColumn("Cantidad");
        modeloEntSal.addColumn("Descripción");
        modeloEntSal.addColumn("Usuario");
        // Crear una nueva instancia de JTable usando el modelo de datos 'modeloClientes'
        tb_ent = new JTable(modeloEntSal);
        scp_ent.setViewportView(tb_ent);
        tb_ent.getColumnModel().getColumn(0).setMinWidth(0);
        tb_ent.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_ent.getColumnModel().getColumn(0).setWidth(0);
        tb_ent.getColumnModel().getColumn(1).setPreferredWidth(110);
        tb_ent.getColumnModel().getColumn(2).setPreferredWidth(28);
        tb_ent.getColumnModel().getColumn(4).setPreferredWidth(230);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_ent.getColumnCount(); i++) {
            tb_ent.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

    }

    private void tablaDevoluciones() {
        modeloDevoluciones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloDevoluciones.addColumn("ID");
        modeloDevoluciones.addColumn("Fecha");
        modeloDevoluciones.addColumn("Descripcion del producto");
        modeloDevoluciones.addColumn("Cantidad");
        modeloDevoluciones.addColumn("Importe");
        // Crear una nueva instancia de JTable usando el modelo de datos 'modeloClientes'
        tb_devoluciones = new JTable(modeloDevoluciones);
        scp_devoluciones.setViewportView(tb_devoluciones);
        tb_devoluciones.getColumnModel().getColumn(0).setMinWidth(0);
        tb_devoluciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_devoluciones.getColumnModel().getColumn(0).setWidth(0);
        tb_devoluciones.getColumnModel().getColumn(1).setPreferredWidth(110);
        tb_devoluciones.getColumnModel().getColumn(2).setPreferredWidth(200);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_devoluciones.getColumnCount(); i++) {
            tb_devoluciones.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

    }

    private void tablaAbonos() {
        modeloAbonos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloAbonos.addColumn("ID");
        modeloAbonos.addColumn("Fecha");
        modeloAbonos.addColumn("Cliente");
        modeloAbonos.addColumn("Cantidad");
        // Crear una nueva instancia de JTable usando el modelo de datos 'modeloClientes'
        tb_abonos = new JTable(modeloAbonos);
        scp_abonos.setViewportView(tb_abonos);
        tb_abonos.getColumnModel().getColumn(0).setMinWidth(0);
        tb_abonos.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_abonos.getColumnModel().getColumn(0).setWidth(0);
        tb_abonos.getColumnModel().getColumn(2).setPreferredWidth(110);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_abonos.getColumnCount(); i++) {
            tb_abonos.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

    }

    private void tablaHistorialDeudas() {
        ModeloHistorialDeudas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ModeloHistorialDeudas.addColumn("Nombre");//ID
        ModeloHistorialDeudas.addColumn("Telefono");
        ModeloHistorialDeudas.addColumn("Total deuda");
        ModeloHistorialDeudas.addColumn("Total abonado");
        ModeloHistorialDeudas.addColumn("Deuda pendiente");
        tb_histDe = new JTable(ModeloHistorialDeudas);
        scp_histDe.setViewportView(tb_histDe);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_histDe.getColumnCount(); i++) {
            tb_histDe.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void tabEstadCuenta() {
        tb_estCta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila_seleccionada = tb_estCta.getSelectedRow();
                int columna_seleccionada = 0;
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    id_clienteEstado = (int) modeloEstadoCuenta.getValueAt(fila_seleccionada, columna_seleccionada);
                    // Obtenemos el nombre y apellidos
                    String nombre = modeloEstadoCuenta.getValueAt(fila_seleccionada, 1).toString();
                    String apellidos = modeloEstadoCuenta.getValueAt(fila_seleccionada, 2).toString();
                    // Mostramos en el JLabel
                    lbl_nombre_cta.setText(nombre + " " + apellidos);

                    // Obtenemos la deuda pendiente del cliente
                    CtrlVentas.idDetalleDeudaRegistrado = new CtrlVentas().obtenerIdDetalleDeudaConPendiente(id_clienteEstado);

                    mostrarResumenDeudaCliente(id_clienteEstado);

                }
            }
        });
    }

    public int getIdClienteEstado() {
        return id_clienteEstado;
    }

    private void tablaVentas() {
        modeloVentas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        modeloVentas.addColumn("Cantidad");//ID
        modeloVentas.addColumn("Descripción");
        modeloVentas.addColumn("Precio unitario");
        modeloVentas.addColumn("Importe");
        tb_ventas = new JTable(modeloVentas);
        scp_ventas.setViewportView(tb_ventas);
        Font fontCeldas = new Font("Arial", Font.PLAIN, 16);
        tb_ventas.setFont(fontCeldas);
        agregarAccionBorrarCeldaBackspace();
    }
    private void agregarAccionBorrarCeldaBackspace() {
    // Solo tecla Backspace (←)
    InputMap inputMap = tb_ventas.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    ActionMap actionMap = tb_ventas.getActionMap();

    KeyStroke backspaceKey = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
    inputMap.put(backspaceKey, "borrarSoloCantidad");

    actionMap.put("borrarSoloCantidad", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tb_ventas.getSelectedRow();
            int col = tb_ventas.getSelectedColumn();

            if (row != -1 && col == 0) {
                modeloVentas.setValueAt("", row, col); // Borra contenido
                tb_ventas.editCellAt(row, col); // Reactiva edición
                Component editor = tb_ventas.getEditorComponent();
                if (editor != null) {
                    editor.requestFocusInWindow(); // Deja el cursor ahí
                }
            }
        }
    });
}

    private void tablaCategorias() {
        modeloCategorias = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloCategorias.addColumn("N°");//ID
        modeloCategorias.addColumn("Categoria");
        tb_categoria = new JTable(modeloCategorias);
        scp_categoria.setViewportView(tb_categoria);
        tb_categoria.getColumnModel().getColumn(0).setMinWidth(0);
        tb_categoria.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_categoria.getColumnModel().getColumn(0).setWidth(0);
        tb_categoria.getColumnModel().getColumn(1).setPreferredWidth(500); // Establece el ancho de la columna "Artículos"
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_categoria.getColumnCount(); i++) {
            tb_categoria.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void tbCategorias() {
        tb_categoria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_seleccionada = tb_categoria.rowAtPoint(e.getPoint());
                int columna_seleccionada = 0;
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    id_categoria = (int) modeloCategorias.getValueAt(fila_seleccionada, columna_seleccionada);
                    btn_modificarCat.setEnabled(true);
                    btn_eliminarCat.setEnabled(true);
                    text_categoria.setText((String) modeloCategorias.getValueAt(fila_seleccionada, columna_seleccionada + 1));
                }
            }
        });
    }

    public void tablaProductos() {
        modeloProductos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloProductos.addColumn("N°");//ID
        modeloProductos.addColumn("Categoria");
        modeloProductos.addColumn("Codigo del producto");
        modeloProductos.addColumn("Descripcion");
        modeloProductos.addColumn("Unidad");
        modeloProductos.addColumn("Precio costo");
        modeloProductos.addColumn("Precio venta");
        modeloProductos.addColumn("Existencias");
        tb_adProducto = new JTable(modeloProductos);
        scp_adProducto.setViewportView(tb_adProducto);
        tb_adProducto.getColumnModel().getColumn(0).setMinWidth(0);
        tb_adProducto.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_adProducto.getColumnModel().getColumn(0).setWidth(0);
        tb_adProducto.getColumnModel().getColumn(1).setPreferredWidth(100); // Establece el ancho de la columna "Artículos"
        tb_adProducto.getColumnModel().getColumn(2).setPreferredWidth(130); // Establece el ancho de la columna "Artículos"
        tb_adProducto.getColumnModel().getColumn(3).setPreferredWidth(300); // Establece el ancho de la columna "Artículos"
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_adProducto.getColumnCount(); i++) {
            tb_adProducto.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void tablaProductosBajoInventario() {
        modeloProductosBajoInv = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloProductosBajoInv.addColumn("Codigo");
        modeloProductosBajoInv.addColumn("Descripción del producto");
        modeloProductosBajoInv.addColumn("Precio venta");
        modeloProductosBajoInv.addColumn("Existencias");
        modeloProductosBajoInv.addColumn("Categoria");
        tb_prodBaj = new JTable(modeloProductosBajoInv);
        scp_prodBaj.setViewportView(tb_prodBaj);
        tb_prodBaj.getColumnModel().getColumn(0).setPreferredWidth(130); // Establece el ancho de la columna "Artículos"
        tb_prodBaj.getColumnModel().getColumn(1).setPreferredWidth(300); // Establece el ancho de la columna "Artículos"
        tb_prodBaj.getColumnModel().getColumn(4).setPreferredWidth(80); // Establece el ancho de la columna "Artículos"
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_prodBaj.getColumnCount(); i++) {
            tb_prodBaj.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void tablaReporteInventario() {
        modeloReporteInventario = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloReporteInventario.addColumn("Codigo");
        modeloReporteInventario.addColumn("Descripción del producto");
        modeloReporteInventario.addColumn("Precio costo");
        modeloReporteInventario.addColumn("Precio venta");
        modeloReporteInventario.addColumn("Existencias");
        tb_repInv = new JTable(modeloReporteInventario);
        scp_repInv.setViewportView(tb_repInv);
        tb_repInv.getColumnModel().getColumn(0).setPreferredWidth(130); // Establece el ancho de la columna "Artículos"
        tb_repInv.getColumnModel().getColumn(1).setPreferredWidth(300); // Establece el ancho de la columna "Artículos"
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tb_repInv.getColumnCount(); i++) {
            tb_repInv.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void tablaReporteMovimientos() {
        modeloReporteMovimientos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloReporteMovimientos.addColumn("Fecha");
        modeloReporteMovimientos.addColumn("Descripción del producto");
        modeloReporteMovimientos.addColumn("Tipo");
        modeloReporteMovimientos.addColumn("Cantidad");
        modeloReporteMovimientos.addColumn("Cantidad anterior");
        modeloReporteMovimientos.addColumn("Cantidad actual");
        modeloReporteMovimientos.addColumn("Usuario");
        tb_repMov = new JTable(modeloReporteMovimientos);
        scp_repMov.setViewportView(tb_repMov);

        tb_repMov.getColumnModel().getColumn(0).setPreferredWidth(80); // Establece el ancho de la columna "Artículos"
        tb_repMov.getColumnModel().getColumn(1).setPreferredWidth(300); // Establece el ancho de la columna "Artículos"
        tb_repMov.getColumnModel().getColumn(2).setPreferredWidth(30); // Establece el ancho de la columna "Artículos"
        // Centramos todas las celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        // Aplicamos a todas las columnas
        for (int i = 0; i < tb_repMov.getColumnCount(); i++) {
            tb_repMov.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void tablaVentasPeriodo() {
        ModeloVentasPeriodo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ModeloVentasPeriodo.addColumn("Fecha");
        ModeloVentasPeriodo.addColumn("Descripción del producto");
        ModeloVentasPeriodo.addColumn("Cantidad");
        ModeloVentasPeriodo.addColumn("Precio unitario");
        ModeloVentasPeriodo.addColumn("Importe");
        ModeloVentasPeriodo.addColumn("Usuario");
        tb_ventasPeriodo = new JTable(ModeloVentasPeriodo);
        scp_ventasPeriodo.setViewportView(tb_ventasPeriodo);

        tb_ventasPeriodo.getColumnModel().getColumn(0).setPreferredWidth(80); // Establece el ancho de la columna "Artículos"
        tb_ventasPeriodo.getColumnModel().getColumn(1).setPreferredWidth(300); // Establece el ancho de la columna "Artículos"
        tb_ventasPeriodo.getColumnModel().getColumn(2).setPreferredWidth(30); // Establece el ancho de la columna "Artículos"
        // Centramos todas las celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        // Aplicamos a todas las columnas
        for (int i = 0; i < tb_ventasPeriodo.getColumnCount(); i++) {
            tb_ventasPeriodo.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    public void pnlBuscar() {
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Busqueda de productos", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        PBuscarProducto buscarProductos = new PBuscarProducto();

        dialog.setContentPane(buscarProductos);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal

        // Mostrar el JDialog
        dialog.setVisible(true);
        txt_codigo.requestFocus();
    }

    public void AccionestbProductos() {
        tb_adProducto.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila_seleccionada = tb_adProducto.getSelectedRow();
                int columna_seleccionada = 0;
                if (fila_seleccionada > -1) {
                    is_selected = true;
                    id_producto = (int) modeloProductos.getValueAt(fila_seleccionada, columna_seleccionada);
                    btn_modificarPto.setEnabled(true);
                    btn_eliminarPto.setEnabled(true);
                    cargarInformacionProducto(id_producto);
                }
            }
        });
    }

    private void mostrarPanel(String nombrePanel) {
        CardLayout cl = (CardLayout) (contenedorPrincipal.getLayout());
        cl.show(contenedorPrincipal, nombrePanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txt_buscarCliente1 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btn_vtas = new javax.swing.JButton();
        btn_ctes = new javax.swing.JButton();
        btn_producto = new javax.swing.JButton();
        btn_inv = new javax.swing.JButton();
        btn_cte = new javax.swing.JButton();
        btn_usuarios = new javax.swing.JButton();
        btn_cerrar1 = new javax.swing.JButton();
        contenedorPrincipal = new javax.swing.JPanel();
        pnl_productos = new javax.swing.JPanel();
        pnl_barraProductos = new javax.swing.JPanel();
        btn_nvoProd = new javax.swing.JButton();
        btn_adProd = new javax.swing.JButton();
        btn_Cat = new javax.swing.JButton();
        btn_ventasPeriodo = new javax.swing.JButton();
        cont_prod = new javax.swing.JPanel();
        pnl_agProducto = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        text_descProd = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        text_codigoBarras = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cmb_catProducto = new javax.swing.JComboBox<>();
        btn_guardarProducto = new javax.swing.JButton();
        rbtn_pqte = new javax.swing.JRadioButton();
        rbtn_pza = new javax.swing.JRadioButton();
        rbtn_granel = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        sp_exc = new javax.swing.JSpinner();
        sp_cost = new javax.swing.JSpinner();
        sp_vent = new javax.swing.JSpinner();
        pnl_adProducto = new javax.swing.JPanel();
        txt_buscarProducto = new javax.swing.JTextField();
        jSeparatorPro = new javax.swing.JSeparator();
        scp_adProducto = new javax.swing.JScrollPane();
        tb_adProducto = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        cmb_catProdAct = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        text_codigoBarrasAct = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        text_descProdAct = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        rbtn_pzaAct = new javax.swing.JRadioButton();
        rbtn_gnelAct = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        rbtn_paqteAct = new javax.swing.JRadioButton();
        btn_modificarPto = new javax.swing.JButton();
        btn_eliminarPto = new javax.swing.JButton();
        sp_exc1 = new javax.swing.JSpinner();
        sp_cost1 = new javax.swing.JSpinner();
        sp_vent1 = new javax.swing.JSpinner();
        pnl_categorias = new javax.swing.JPanel();
        scp_categoria = new javax.swing.JScrollPane();
        tb_categoria = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        btn_agregarCategoria = new javax.swing.JButton();
        btn_modificarCat = new javax.swing.JButton();
        btn_eliminarCat = new javax.swing.JButton();
        text_categoria = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btn_agregarCategoria1 = new javax.swing.JButton();
        pnl_vtaPeriodo = new javax.swing.JPanel();
        scp_ventasPeriodo = new javax.swing.JScrollPane();
        tb_ventasPeriodo = new javax.swing.JTable();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jDateChooserInicio = new com.toedter.calendar.JDateChooser();
        jLabel48 = new javax.swing.JLabel();
        jDateChooserFinal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        lbl_totalVendido = new javax.swing.JLabel();
        pnl_ventas = new javax.swing.JPanel();
        pnl_barraVentas = new javax.swing.JPanel();
        btn_buscar = new javax.swing.JButton();
        btn_entradas = new javax.swing.JButton();
        cont_ventas = new javax.swing.JPanel();
        pnl_vta = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        scp_ventas = new javax.swing.JScrollPane();
        tb_ventas = new javax.swing.JTable();
        lbl_ctp = new javax.swing.JLabel();
        lbl_totalVenta = new javax.swing.JLabel();
        lbl_cantidad = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btn_cobrar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_devol = new javax.swing.JButton();
        pnl_clientes = new javax.swing.JPanel();
        pnl_barraClientes = new javax.swing.JPanel();
        btn_edCuenta = new javax.swing.JButton();
        btn_nvoCte = new javax.swing.JButton();
        btn_adCte = new javax.swing.JButton();
        btn_histDe = new javax.swing.JButton();
        cont_ctes = new javax.swing.JPanel();
        pnl_estCuenta = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        btn_acEst = new javax.swing.JButton();
        txt_buscarClienteEst = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        scp_estCta = new javax.swing.JScrollPane();
        tb_estCta = new javax.swing.JTable();
        pnl_agCliente = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        text_nombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        text_apellidos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        text_telefono = new javax.swing.JTextField();
        btn_guardarCte = new javax.swing.JButton();
        pnl_adCliente = new javax.swing.JPanel();
        scp_adcliente = new javax.swing.JScrollPane();
        tb_adcliente = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_apellidos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_telefono = new javax.swing.JTextField();
        btn_eliminarCte = new javax.swing.JButton();
        btn_modificarCte = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_buscarCliente = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        pnl_contEstCta = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        scp_estCtaCte = new javax.swing.JScrollPane();
        tb_estCtaCte = new javax.swing.JTable();
        lbl_nombre_cta = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        lbl_totDeu = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lbl_totAb = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        lbl_deudaPend = new javax.swing.JLabel();
        btn_abonar = new javax.swing.JButton();
        btn_detalleAbono = new javax.swing.JButton();
        pnl_HistDe = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        scp_histDe = new javax.swing.JScrollPane();
        tb_histDe = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        lbl_totDeudas = new javax.swing.JLabel();
        pnl_inventario = new javax.swing.JPanel();
        pnl_barraInventario = new javax.swing.JPanel();
        ag_invent = new javax.swing.JButton();
        btn_prodBaj = new javax.swing.JButton();
        btn_repMov = new javax.swing.JButton();
        btn_repInv = new javax.swing.JButton();
        cont_inv = new javax.swing.JPanel();
        pnl_agInv = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        btn_guardarProducto1 = new javax.swing.JButton();
        sp_cantInv = new javax.swing.JSpinner();
        text_codigoBarrasInv = new javax.swing.JTextField();
        lbl_exI = new javax.swing.JLabel();
        lbl_descIn = new javax.swing.JLabel();
        pnl_prBaj = new javax.swing.JPanel();
        scp_prodBaj = new javax.swing.JScrollPane();
        tb_prodBaj = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        pnl_repInv = new javax.swing.JPanel();
        scp_repInv = new javax.swing.JScrollPane();
        tb_repInv = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmb_catRep = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        lbl_costInv = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lbl_cantProd = new javax.swing.JLabel();
        pnl_repMov = new javax.swing.JPanel();
        scp_repMov = new javax.swing.JScrollPane();
        tb_repMov = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jDateChooserInicio1 = new com.toedter.calendar.JDateChooser();
        jLabel51 = new javax.swing.JLabel();
        jDateChooserFinal1 = new com.toedter.calendar.JDateChooser();
        btn_mostrarMovimientosIn = new javax.swing.JButton();
        pnl_corte = new javax.swing.JPanel();
        cont_corte = new javax.swing.JPanel();
        pnl_infCorte = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        fecha_inicioCorte = new com.toedter.calendar.JDateChooser();
        jLabel69 = new javax.swing.JLabel();
        fecha_finCorte = new com.toedter.calendar.JDateChooser();
        btn_corte = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        lbl_vtaTotal = new javax.swing.JLabel();
        lbl_ventaContado = new javax.swing.JLabel();
        lbl_abonos = new javax.swing.JLabel();
        lbl_entradaEfectivo = new javax.swing.JLabel();
        lbl_ventaContado2 = new javax.swing.JLabel();
        lbl_ventaFiado = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        lbl_devoluciones = new javax.swing.JLabel();
        lbl_salidaEfectivo = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lbl_totalDineroCaja = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        lbl_devoluciones2 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        lbl_toVentas = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        scp_ent = new javax.swing.JScrollPane();
        tb_ent = new javax.swing.JTable();
        jLabel85 = new javax.swing.JLabel();
        scp_devoluciones = new javax.swing.JScrollPane();
        tb_devoluciones = new javax.swing.JTable();
        scp_abonos = new javax.swing.JScrollPane();
        tb_abonos = new javax.swing.JTable();
        jLabel86 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        lbl_efectivoInicial = new javax.swing.JLabel();
        pnl_usuarios = new javax.swing.JPanel();
        pnl_barraUsuarios = new javax.swing.JPanel();
        btn_nvoUs = new javax.swing.JButton();
        btn_adUs = new javax.swing.JButton();
        cont_us = new javax.swing.JPanel();
        pnl_agUsuario = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        btn_guardarUs = new javax.swing.JButton();
        text_password = new javax.swing.JPasswordField();
        jLabel52 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        text_usuario = new javax.swing.JTextField();
        pnl_adUsuario = new javax.swing.JPanel();
        scp_adUsuario = new javax.swing.JScrollPane();
        tb_adUsuario = new javax.swing.JTable();
        btn_eliminarUsuario = new javax.swing.JButton();
        btn_modificarUsuario = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        text_passwordAd = new javax.swing.JPasswordField();
        jLabel54 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        text_usuarioAd = new javax.swing.JTextField();

        txt_buscarCliente1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_buscarCliente1.setBorder(null);
        txt_buscarCliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarCliente1KeyReleased(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        menu.setBackground(new java.awt.Color(255, 255, 255));

        btn_vtas.setBackground(new java.awt.Color(204, 204, 204));
        btn_vtas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_vtas.setText(" Ventas");
        btn_vtas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_vtas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vtasActionPerformed(evt);
            }
        });
        menu.add(btn_vtas);

        btn_ctes.setBackground(new java.awt.Color(204, 204, 204));
        btn_ctes.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_ctes.setText(" Clientes");
        btn_ctes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_ctes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ctesActionPerformed(evt);
            }
        });
        menu.add(btn_ctes);

        btn_producto.setBackground(new java.awt.Color(204, 204, 204));
        btn_producto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_producto.setText(" Productos");
        btn_producto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productoActionPerformed(evt);
            }
        });
        menu.add(btn_producto);

        btn_inv.setBackground(new java.awt.Color(204, 204, 204));
        btn_inv.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_inv.setText(" Inventario");
        btn_inv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_inv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invActionPerformed(evt);
            }
        });
        menu.add(btn_inv);

        btn_cte.setBackground(new java.awt.Color(204, 204, 204));
        btn_cte.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_cte.setText(" Corte");
        btn_cte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_cte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cteActionPerformed(evt);
            }
        });
        menu.add(btn_cte);

        btn_usuarios.setBackground(new java.awt.Color(204, 204, 204));
        btn_usuarios.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_usuarios.setText("Usuarios");
        btn_usuarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosActionPerformed(evt);
            }
        });
        menu.add(btn_usuarios);

        btn_cerrar1.setBackground(new java.awt.Color(204, 204, 204));
        btn_cerrar1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_cerrar1.setText("Cerrar sesión");
        btn_cerrar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_cerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrar1ActionPerformed(evt);
            }
        });
        menu.add(btn_cerrar1);

        contenedorPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        contenedorPrincipal.setLayout(new java.awt.CardLayout());

        pnl_productos.setBackground(new java.awt.Color(255, 255, 255));
        pnl_productos.setLayout(new java.awt.BorderLayout());

        pnl_barraProductos.setBackground(new java.awt.Color(255, 255, 255));
        pnl_barraProductos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btn_nvoProd.setBackground(new java.awt.Color(0, 153, 0));
        btn_nvoProd.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_nvoProd.setForeground(new java.awt.Color(255, 255, 255));
        btn_nvoProd.setText("Nuevo producto");
        btn_nvoProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_nvoProd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nvoProd.setPreferredSize(new java.awt.Dimension(144, 38));
        btn_nvoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nvoProdActionPerformed(evt);
            }
        });
        pnl_barraProductos.add(btn_nvoProd);

        btn_adProd.setBackground(new java.awt.Color(0, 153, 0));
        btn_adProd.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_adProd.setForeground(new java.awt.Color(255, 255, 255));
        btn_adProd.setText("Administrar producto");
        btn_adProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_adProd.setPreferredSize(new java.awt.Dimension(200, 38));
        btn_adProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adProdActionPerformed(evt);
            }
        });
        pnl_barraProductos.add(btn_adProd);

        btn_Cat.setBackground(new java.awt.Color(0, 153, 0));
        btn_Cat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_Cat.setForeground(new java.awt.Color(255, 255, 255));
        btn_Cat.setText("Categorias");
        btn_Cat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_Cat.setPreferredSize(new java.awt.Dimension(144, 38));
        btn_Cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CatActionPerformed(evt);
            }
        });
        pnl_barraProductos.add(btn_Cat);

        btn_ventasPeriodo.setBackground(new java.awt.Color(0, 153, 0));
        btn_ventasPeriodo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_ventasPeriodo.setForeground(new java.awt.Color(255, 255, 255));
        btn_ventasPeriodo.setText("Ventas por periodo");
        btn_ventasPeriodo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_ventasPeriodo.setPreferredSize(new java.awt.Dimension(164, 38));
        btn_ventasPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ventasPeriodoActionPerformed(evt);
            }
        });
        pnl_barraProductos.add(btn_ventasPeriodo);

        pnl_productos.add(pnl_barraProductos, java.awt.BorderLayout.NORTH);

        cont_prod.setBackground(new java.awt.Color(255, 255, 255));
        cont_prod.setLayout(new java.awt.CardLayout());

        pnl_agProducto.setBackground(new java.awt.Color(255, 255, 255));
        pnl_agProducto.setPreferredSize(new java.awt.Dimension(510, 390));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 51));
        jLabel12.setText("Nuevo producto");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("Categoria:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Unidad:");

        text_descProd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Existencias:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("Descripción:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setText("Precio costo:");

        text_codigoBarras.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel18.setText("Codigo de barras:");

        cmb_catProducto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmb_catProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_guardarProducto.setBackground(new java.awt.Color(0, 153, 0));
        btn_guardarProducto.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_guardarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardarProducto.setText("Guardar producto");
        btn_guardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarProductoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtn_pqte);
        rbtn_pqte.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtn_pqte.setText("Paquete");

        buttonGroup1.add(rbtn_pza);
        rbtn_pza.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtn_pza.setText("Pza");

        buttonGroup1.add(rbtn_granel);
        rbtn_granel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtn_granel.setText("A granel");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel19.setText("Precio venta:");

        sp_exc.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_exc.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_exc.setEditor(new javax.swing.JSpinner.NumberEditor(sp_exc, "0.00"));

        sp_cost.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_cost.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_cost.setEditor(new javax.swing.JSpinner.NumberEditor(sp_cost, "0.00"));

        sp_vent.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_vent.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_vent.setEditor(new javax.swing.JSpinner.NumberEditor(sp_vent, "0.00"));

        javax.swing.GroupLayout pnl_agProductoLayout = new javax.swing.GroupLayout(pnl_agProducto);
        pnl_agProducto.setLayout(pnl_agProductoLayout);
        pnl_agProductoLayout.setHorizontalGroup(
            pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agProductoLayout.createSequentialGroup()
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel12))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel13)
                        .addGap(9, 9, 9)
                        .addComponent(cmb_catProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel18)
                        .addGap(5, 5, 5)
                        .addComponent(text_codigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel16)
                        .addGap(4, 4, 4)
                        .addComponent(text_descProd, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel14)
                        .addGap(6, 6, 6)
                        .addComponent(rbtn_pza)
                        .addGap(5, 5, 5)
                        .addComponent(rbtn_granel)
                        .addGap(5, 5, 5)
                        .addComponent(rbtn_pqte))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel17)
                        .addGap(11, 11, 11)
                        .addComponent(sp_cost, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel19)
                        .addGap(9, 9, 9)
                        .addComponent(sp_vent, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel15)
                        .addGap(11, 11, 11)
                        .addComponent(sp_exc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agProductoLayout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(btn_guardarProducto)))
                .addContainerGap(787, Short.MAX_VALUE))
        );
        pnl_agProductoLayout.setVerticalGroup(
            pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agProductoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(cmb_catProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(text_codigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(text_descProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(rbtn_pza)
                    .addComponent(rbtn_granel)
                    .addComponent(rbtn_pqte))
                .addGap(15, 15, 15)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(sp_cost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(sp_vent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_agProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(sp_exc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(btn_guardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cont_prod.add(pnl_agProducto, "agProducto");

        pnl_adProducto.setBackground(new java.awt.Color(255, 255, 255));

        txt_buscarProducto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_buscarProducto.setBorder(null);
        txt_buscarProducto.setPreferredSize(new java.awt.Dimension(100, 20));
        txt_buscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarProductoKeyReleased(evt);
            }
        });

        jSeparatorPro.setPreferredSize(new java.awt.Dimension(50, 3));

        scp_adProducto.setForeground(new java.awt.Color(255, 255, 255));

        tb_adProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_adProducto.setFocusable(false);
        tb_adProducto.setGridColor(new java.awt.Color(153, 153, 153));
        tb_adProducto.setRowHeight(25);
        tb_adProducto.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_adProducto.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_adProducto.setShowHorizontalLines(true);
        tb_adProducto.getTableHeader().setReorderingAllowed(false);
        scp_adProducto.setViewportView(tb_adProducto);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Categoria:");

        cmb_catProdAct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmb_catProdAct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel22.setText("Codigo de barras:");

        text_codigoBarrasAct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel23.setText("Descripción:");

        text_descProdAct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel24.setText("Unidad:");

        buttonGroup1.add(rbtn_pzaAct);
        rbtn_pzaAct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtn_pzaAct.setText("Pza");

        buttonGroup1.add(rbtn_gnelAct);
        rbtn_gnelAct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtn_gnelAct.setText("A granel");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel25.setText("Precio costo:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel26.setText("Precio venta:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel27.setText("Existencias:");

        buttonGroup1.add(rbtn_paqteAct);
        rbtn_paqteAct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtn_paqteAct.setText("Paquete");

        btn_modificarPto.setBackground(new java.awt.Color(51, 51, 255));
        btn_modificarPto.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_modificarPto.setForeground(new java.awt.Color(255, 255, 255));
        btn_modificarPto.setText("Modificar");
        btn_modificarPto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarPtoActionPerformed(evt);
            }
        });

        btn_eliminarPto.setBackground(new java.awt.Color(245, 38, 38));
        btn_eliminarPto.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_eliminarPto.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminarPto.setText("Eliminar");
        btn_eliminarPto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarPtoActionPerformed(evt);
            }
        });

        sp_exc1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_exc1.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_exc1.setEditor(new javax.swing.JSpinner.NumberEditor(sp_exc1, "0.00"));

        sp_cost1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_cost1.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_cost1.setEditor(new javax.swing.JSpinner.NumberEditor(sp_cost1, "0.00"));

        sp_vent1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_vent1.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_vent1.setEditor(new javax.swing.JSpinner.NumberEditor(sp_vent1, "0.00"));

        javax.swing.GroupLayout pnl_adProductoLayout = new javax.swing.GroupLayout(pnl_adProducto);
        pnl_adProducto.setLayout(pnl_adProductoLayout);
        pnl_adProductoLayout.setHorizontalGroup(
            pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_adProductoLayout.createSequentialGroup()
                .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addGap(570, 570, 570)
                        .addComponent(txt_buscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addGap(570, 570, 570)
                        .addComponent(jSeparatorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_adProductoLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel21))
                            .addComponent(jLabel22)
                            .addGroup(pnl_adProductoLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel23)))
                        .addGap(4, 4, 4)
                        .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_catProdAct, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_codigoBarrasAct, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_descProdAct, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addGroup(pnl_adProductoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel27)))
                        .addGap(9, 9, 9)
                        .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sp_cost1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sp_vent1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sp_exc1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(200, 200, 200)
                        .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_modificarPto)
                            .addComponent(btn_eliminarPto, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(jLabel24)
                        .addGap(6, 6, 6)
                        .addComponent(rbtn_pzaAct)
                        .addGap(25, 25, 25)
                        .addComponent(rbtn_gnelAct)
                        .addGap(5, 5, 5)
                        .addComponent(rbtn_paqteAct))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(scp_adProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
        );
        pnl_adProductoLayout.setVerticalGroup(
            pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_adProductoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txt_buscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparatorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(scp_adProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addComponent(cmb_catProdAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(text_codigoBarrasAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(text_descProdAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addComponent(sp_cost1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(sp_vent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(sp_exc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adProductoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_modificarPto)
                        .addGap(11, 11, 11)
                        .addComponent(btn_eliminarPto)))
                .addGap(12, 12, 12)
                .addGroup(pnl_adProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(rbtn_pzaAct)
                    .addComponent(rbtn_gnelAct)
                    .addComponent(rbtn_paqteAct))
                .addContainerGap(185, Short.MAX_VALUE))
        );

        cont_prod.add(pnl_adProducto, "adProducto");

        pnl_categorias.setBackground(new java.awt.Color(255, 255, 255));
        pnl_categorias.setPreferredSize(new java.awt.Dimension(800, 600));

        scp_categoria.setForeground(new java.awt.Color(255, 255, 255));

        tb_categoria.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_categoria.setFocusable(false);
        tb_categoria.setGridColor(new java.awt.Color(153, 153, 153));
        tb_categoria.setRowHeight(25);
        tb_categoria.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_categoria.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_categoria.setShowHorizontalLines(true);
        tb_categoria.getTableHeader().setReorderingAllowed(false);
        scp_categoria.setViewportView(tb_categoria);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 153, 51));
        jLabel20.setText("Nueva categoria");

        btn_agregarCategoria.setBackground(new java.awt.Color(0, 153, 0));
        btn_agregarCategoria.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_agregarCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregarCategoria.setText("Agregar");
        btn_agregarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarCategoriaActionPerformed(evt);
            }
        });

        btn_modificarCat.setBackground(new java.awt.Color(51, 51, 255));
        btn_modificarCat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_modificarCat.setForeground(new java.awt.Color(255, 255, 255));
        btn_modificarCat.setText("Modificar");
        btn_modificarCat.setEnabled(false);
        btn_modificarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarCatActionPerformed(evt);
            }
        });

        btn_eliminarCat.setBackground(new java.awt.Color(245, 38, 38));
        btn_eliminarCat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_eliminarCat.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminarCat.setText("Eliminar");
        btn_eliminarCat.setEnabled(false);
        btn_eliminarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarCatActionPerformed(evt);
            }
        });

        text_categoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Nombre:");

        btn_agregarCategoria1.setBackground(new java.awt.Color(153, 153, 153));
        btn_agregarCategoria1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_agregarCategoria1.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregarCategoria1.setText("Limpiar");
        btn_agregarCategoria1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarCategoria1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_categoriasLayout = new javax.swing.GroupLayout(pnl_categorias);
        pnl_categorias.setLayout(pnl_categoriasLayout);
        pnl_categoriasLayout.setHorizontalGroup(
            pnl_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_categoriasLayout.createSequentialGroup()
                .addGap(695, 695, 695)
                .addComponent(jLabel1))
            .addGroup(pnl_categoriasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(scp_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(pnl_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_categoriasLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(pnl_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_agregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_modificarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_agregarCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(pnl_categoriasLayout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel20))
        );
        pnl_categoriasLayout.setVerticalGroup(
            pnl_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_categoriasLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel20)
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(pnl_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scp_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_categoriasLayout.createSequentialGroup()
                        .addComponent(text_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btn_agregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_modificarCat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminarCat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_agregarCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(260, 260, 260))
        );

        cont_prod.add(pnl_categorias, "categorias");

        pnl_vtaPeriodo.setBackground(new java.awt.Color(255, 255, 255));

        scp_ventasPeriodo.setForeground(new java.awt.Color(255, 255, 255));

        tb_ventasPeriodo.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_ventasPeriodo.setFocusable(false);
        tb_ventasPeriodo.setGridColor(new java.awt.Color(153, 153, 153));
        tb_ventasPeriodo.setRowHeight(25);
        tb_ventasPeriodo.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_ventasPeriodo.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_ventasPeriodo.setShowHorizontalLines(true);
        tb_ventasPeriodo.getTableHeader().setReorderingAllowed(false);
        scp_ventasPeriodo.setViewportView(tb_ventasPeriodo);

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 153, 51));
        jLabel46.setText("Reporte de productos vendidos");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel47.setText("Desde el:");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel48.setText("Hasta el:");

        jButton1.setText("Mostrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 102));
        jLabel49.setText("Total vendido:");

        lbl_totalVendido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout pnl_vtaPeriodoLayout = new javax.swing.GroupLayout(pnl_vtaPeriodo);
        pnl_vtaPeriodo.setLayout(pnl_vtaPeriodoLayout);
        pnl_vtaPeriodoLayout.setHorizontalGroup(
            pnl_vtaPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_vtaPeriodoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addGap(430, 430, 430))
            .addGroup(pnl_vtaPeriodoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnl_vtaPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scp_ventasPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_vtaPeriodoLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(90, 90, 90)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_totalVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
        );
        pnl_vtaPeriodoLayout.setVerticalGroup(
            pnl_vtaPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_vtaPeriodoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel46)
                .addGap(30, 30, 30)
                .addGroup(pnl_vtaPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_vtaPeriodoLayout.createSequentialGroup()
                        .addGroup(pnl_vtaPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel47)
                            .addComponent(jDateChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooserFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_vtaPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel49)
                                .addComponent(lbl_totalVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addComponent(scp_ventasPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        cont_prod.add(pnl_vtaPeriodo, "ventasPeriodo");

        pnl_productos.add(cont_prod, java.awt.BorderLayout.CENTER);

        contenedorPrincipal.add(pnl_productos, "productos");

        pnl_ventas.setBackground(new java.awt.Color(255, 255, 255));
        pnl_ventas.setPreferredSize(new java.awt.Dimension(1288, 680));
        pnl_ventas.setLayout(new java.awt.BorderLayout());

        pnl_barraVentas.setBackground(new java.awt.Color(255, 255, 255));
        pnl_barraVentas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        btn_buscar.setBackground(new java.awt.Color(0, 153, 0));
        btn_buscar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar.setText("Buscar");
        btn_buscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_buscar.setPreferredSize(new java.awt.Dimension(100, 38));
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        pnl_barraVentas.add(btn_buscar);

        btn_entradas.setBackground(new java.awt.Color(0, 153, 0));
        btn_entradas.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_entradas.setForeground(new java.awt.Color(255, 255, 255));
        btn_entradas.setText(" Entradas o salidas");
        btn_entradas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_entradas.setPreferredSize(new java.awt.Dimension(170, 38));
        btn_entradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entradasActionPerformed(evt);
            }
        });
        pnl_barraVentas.add(btn_entradas);

        pnl_ventas.add(pnl_barraVentas, java.awt.BorderLayout.NORTH);

        cont_ventas.setBackground(new java.awt.Color(255, 255, 255));
        cont_ventas.setLayout(new java.awt.CardLayout());

        pnl_vta.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel4.setText("Código del producto:");

        txt_codigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_codigoKeyReleased(evt);
            }
        });

        scp_ventas.setForeground(new java.awt.Color(255, 255, 255));

        tb_ventas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tb_ventas.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_ventas.setFocusable(false);
        tb_ventas.setGridColor(new java.awt.Color(153, 153, 153));
        tb_ventas.setRowHeight(25);
        tb_ventas.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_ventas.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_ventas.setShowHorizontalLines(true);
        tb_ventas.getTableHeader().setReorderingAllowed(false);
        scp_ventas.setViewportView(tb_ventas);

        lbl_ctp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_ctp.setForeground(new java.awt.Color(0, 0, 153));
        lbl_ctp.setText("Productos");

        lbl_totalVenta.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbl_totalVenta.setText("$0.00");

        lbl_cantidad.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_cantidad.setForeground(new java.awt.Color(0, 0, 153));
        lbl_cantidad.setText("0");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        jLabel28.setText("Total:");

        btn_cobrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_cobrar.setText("Cobrar");
        btn_cobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cobrarActionPerformed(evt);
            }
        });

        btn_eliminar.setBackground(new java.awt.Color(0, 153, 0));
        btn_eliminar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar.setText(" Eliminar");
        btn_eliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_devol.setBackground(new java.awt.Color(0, 153, 0));
        btn_devol.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_devol.setForeground(new java.awt.Color(255, 255, 255));
        btn_devol.setText("Ventas del dia y devoluciones");
        btn_devol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_devol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_devolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_vtaLayout = new javax.swing.GroupLayout(pnl_vta);
        pnl_vta.setLayout(pnl_vtaLayout);
        pnl_vtaLayout.setHorizontalGroup(
            pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_vtaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_vtaLayout.createSequentialGroup()
                        .addComponent(lbl_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_ctp, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                        .addGap(250, 250, 250))
                    .addGroup(pnl_vtaLayout.createSequentialGroup()
                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_devol, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(btn_cobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_totalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_vtaLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(714, 714, 714))
                .addGroup(pnl_vtaLayout.createSequentialGroup()
                    .addComponent(scp_ventas)
                    .addContainerGap()))
        );
        pnl_vtaLayout.setVerticalGroup(
            pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_vtaLayout.createSequentialGroup()
                .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_vtaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ctp)
                            .addComponent(lbl_cantidad))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_devol, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_vtaLayout.createSequentialGroup()
                        .addGap(451, 451, 451)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_cobrar, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(lbl_totalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addGap(43, 43, 43))
            .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_vtaLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(pnl_vtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_vtaLayout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(2, 2, 2))
                        .addComponent(txt_codigo))
                    .addGap(14, 14, 14)
                    .addComponent(scp_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(123, 123, 123)))
        );

        cont_ventas.add(pnl_vta, "pnl_vta");

        pnl_ventas.add(cont_ventas, java.awt.BorderLayout.CENTER);

        contenedorPrincipal.add(pnl_ventas, "ventas");

        pnl_clientes.setBackground(new java.awt.Color(255, 255, 255));
        pnl_clientes.setLayout(new java.awt.BorderLayout());

        pnl_barraClientes.setBackground(new java.awt.Color(255, 255, 255));
        pnl_barraClientes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btn_edCuenta.setBackground(new java.awt.Color(0, 153, 0));
        btn_edCuenta.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_edCuenta.setForeground(new java.awt.Color(255, 255, 255));
        btn_edCuenta.setText("Estado de cuenta");
        btn_edCuenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_edCuenta.setPreferredSize(new java.awt.Dimension(178, 38));
        btn_edCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edCuentaActionPerformed(evt);
            }
        });
        pnl_barraClientes.add(btn_edCuenta);

        btn_nvoCte.setBackground(new java.awt.Color(0, 153, 0));
        btn_nvoCte.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_nvoCte.setForeground(new java.awt.Color(255, 255, 255));
        btn_nvoCte.setText("Nuevo cliente");
        btn_nvoCte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_nvoCte.setPreferredSize(new java.awt.Dimension(137, 38));
        btn_nvoCte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nvoCteActionPerformed(evt);
            }
        });
        pnl_barraClientes.add(btn_nvoCte);

        btn_adCte.setBackground(new java.awt.Color(0, 153, 0));
        btn_adCte.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_adCte.setForeground(new java.awt.Color(255, 255, 255));
        btn_adCte.setText("Administrar cliente");
        btn_adCte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_adCte.setPreferredSize(new java.awt.Dimension(178, 38));
        btn_adCte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adCteActionPerformed(evt);
            }
        });
        pnl_barraClientes.add(btn_adCte);

        btn_histDe.setBackground(new java.awt.Color(0, 153, 0));
        btn_histDe.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_histDe.setForeground(new java.awt.Color(255, 255, 255));
        btn_histDe.setText("Historial de deudas");
        btn_histDe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_histDe.setPreferredSize(new java.awt.Dimension(178, 38));
        btn_histDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_histDeActionPerformed(evt);
            }
        });
        pnl_barraClientes.add(btn_histDe);

        pnl_clientes.add(pnl_barraClientes, java.awt.BorderLayout.NORTH);

        cont_ctes.setBackground(new java.awt.Color(255, 255, 255));
        cont_ctes.setLayout(new java.awt.CardLayout());

        pnl_estCuenta.setBackground(new java.awt.Color(255, 255, 255));
        pnl_estCuenta.setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 153, 51));
        jLabel39.setText("Estado de cuenta");

        btn_acEst.setBackground(new java.awt.Color(0, 153, 0));
        btn_acEst.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_acEst.setForeground(new java.awt.Color(255, 255, 255));
        btn_acEst.setText("Aceptar");
        btn_acEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acEstActionPerformed(evt);
            }
        });

        txt_buscarClienteEst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_buscarClienteEst.setBorder(null);
        txt_buscarClienteEst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarClienteEstKeyReleased(evt);
            }
        });

        scp_estCta.setForeground(new java.awt.Color(255, 255, 255));

        tb_estCta.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_estCta.setFocusable(false);
        tb_estCta.setGridColor(new java.awt.Color(153, 153, 153));
        tb_estCta.setRowHeight(25);
        tb_estCta.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_estCta.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_estCta.setShowHorizontalLines(true);
        tb_estCta.getTableHeader().setReorderingAllowed(false);
        scp_estCta.setViewportView(tb_estCta);

        javax.swing.GroupLayout pnl_estCuentaLayout = new javax.swing.GroupLayout(pnl_estCuenta);
        pnl_estCuenta.setLayout(pnl_estCuentaLayout);
        pnl_estCuentaLayout.setHorizontalGroup(
            pnl_estCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_estCuentaLayout.createSequentialGroup()
                .addGroup(pnl_estCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_estCuentaLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addGroup(pnl_estCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_estCuentaLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel39))
                            .addComponent(txt_buscarClienteEst, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_estCuentaLayout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(btn_acEst))
                    .addGroup(pnl_estCuentaLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(scp_estCta, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(660, Short.MAX_VALUE))
        );
        pnl_estCuentaLayout.setVerticalGroup(
            pnl_estCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_estCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(txt_buscarClienteEst, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(scp_estCta, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btn_acEst, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cont_ctes.add(pnl_estCuenta, "estadoCuenta");

        pnl_agCliente.setBackground(new java.awt.Color(255, 255, 255));
        pnl_agCliente.setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 51));
        jLabel5.setText("Nuevo cliente");

        text_nombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Nombre:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Apellidos:");

        text_apellidos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("Telefono:");

        text_telefono.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btn_guardarCte.setBackground(new java.awt.Color(0, 153, 0));
        btn_guardarCte.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_guardarCte.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardarCte.setText("Guardar cliente");
        btn_guardarCte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarCteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_agClienteLayout = new javax.swing.GroupLayout(pnl_agCliente);
        pnl_agCliente.setLayout(pnl_agClienteLayout);
        pnl_agClienteLayout.setHorizontalGroup(
            pnl_agClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agClienteLayout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel5))
            .addGroup(pnl_agClienteLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel6)
                .addGap(28, 28, 28)
                .addComponent(text_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agClienteLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel7)
                .addGap(21, 21, 21)
                .addComponent(text_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agClienteLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel8)
                .addGap(23, 23, 23)
                .addComponent(text_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agClienteLayout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(btn_guardarCte))
        );
        pnl_agClienteLayout.setVerticalGroup(
            pnl_agClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(26, 26, 26)
                .addGroup(pnl_agClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agClienteLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(text_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_agClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agClienteLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(text_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(pnl_agClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agClienteLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(text_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(btn_guardarCte, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        cont_ctes.add(pnl_agCliente, "ag_cliente");
        pnl_agCliente.getAccessibleContext().setAccessibleName("");

        pnl_adCliente.setBackground(new java.awt.Color(255, 255, 255));
        pnl_adCliente.setPreferredSize(new java.awt.Dimension(880, 660));

        scp_adcliente.setForeground(new java.awt.Color(255, 255, 255));

        tb_adcliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_adcliente.setFocusable(false);
        tb_adcliente.setGridColor(new java.awt.Color(153, 153, 153));
        tb_adcliente.setRowHeight(25);
        tb_adcliente.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_adcliente.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_adcliente.setShowHorizontalLines(true);
        tb_adcliente.getTableHeader().setReorderingAllowed(false);
        scp_adcliente.setViewportView(tb_adcliente);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Nombre:");

        txt_nombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Apellidos:");

        txt_apellidos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Telefono:");

        txt_telefono.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btn_eliminarCte.setBackground(new java.awt.Color(245, 38, 38));
        btn_eliminarCte.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_eliminarCte.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminarCte.setText("Eliminar");
        btn_eliminarCte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarCteActionPerformed(evt);
            }
        });

        btn_modificarCte.setBackground(new java.awt.Color(51, 51, 255));
        btn_modificarCte.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_modificarCte.setForeground(new java.awt.Color(255, 255, 255));
        btn_modificarCte.setText("Modificar");
        btn_modificarCte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarCteActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setText("Administrar cliente");

        txt_buscarCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_buscarCliente.setBorder(null);
        txt_buscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarClienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnl_adClienteLayout = new javax.swing.GroupLayout(pnl_adCliente);
        pnl_adCliente.setLayout(pnl_adClienteLayout);
        pnl_adClienteLayout.setHorizontalGroup(
            pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_adClienteLayout.createSequentialGroup()
                .addGroup(pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(jLabel11))
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(txt_buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel3)
                        .addGap(8, 8, 8)
                        .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btn_modificarCte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel9)
                        .addGap(11, 11, 11)
                        .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel10)
                        .addGap(13, 13, 13)
                        .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btn_eliminarCte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(scp_adcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(521, 521, 521))
        );
        pnl_adClienteLayout.setVerticalGroup(
            pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_adClienteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(scp_adcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_modificarCte)))
                .addGap(1, 1, 1)
                .addGroup(pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_eliminarCte)
                    .addGroup(pnl_adClienteLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnl_adClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        cont_ctes.add(pnl_adCliente, "ad_cliente");

        pnl_contEstCta.setBackground(new java.awt.Color(255, 255, 255));
        pnl_contEstCta.setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 51));
        jLabel40.setText("Estado de cuenta");

        scp_estCtaCte.setForeground(new java.awt.Color(255, 255, 255));

        tb_estCtaCte.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_estCtaCte.setFocusable(false);
        tb_estCtaCte.setGridColor(new java.awt.Color(153, 153, 153));
        tb_estCtaCte.setRowHeight(25);
        tb_estCtaCte.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_estCtaCte.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_estCtaCte.setShowHorizontalLines(true);
        tb_estCtaCte.getTableHeader().setReorderingAllowed(false);
        scp_estCtaCte.setViewportView(tb_estCtaCte);

        lbl_nombre_cta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_nombre_cta.setForeground(new java.awt.Color(153, 153, 153));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 153, 51));
        jLabel41.setText("Total deuda:");

        lbl_totDeu.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_totDeu.setPreferredSize(new java.awt.Dimension(87, 32));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 153, 51));
        jLabel43.setText("Total abonado:");

        lbl_totAb.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_totAb.setPreferredSize(new java.awt.Dimension(87, 32));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 153, 51));
        jLabel45.setText("Deuda pendiente:");

        lbl_deudaPend.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_deudaPend.setPreferredSize(new java.awt.Dimension(87, 32));

        btn_abonar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_abonar.setText("Abonar");
        btn_abonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abonarActionPerformed(evt);
            }
        });

        btn_detalleAbono.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_detalleAbono.setText("Detalle de abonos");
        btn_detalleAbono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detalleAbonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_contEstCtaLayout = new javax.swing.GroupLayout(pnl_contEstCta);
        pnl_contEstCta.setLayout(pnl_contEstCtaLayout);
        pnl_contEstCtaLayout.setHorizontalGroup(
            pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                        .addComponent(btn_abonar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_detalleAbono))
                    .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_totDeu, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_totAb, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_deudaPend, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel40)
                    .addComponent(lbl_nombre_cta, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scp_estCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 1233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnl_contEstCtaLayout.setVerticalGroup(
            pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                .addGroup(pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45)
                            .addComponent(lbl_deudaPend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel43)
                            .addGroup(pnl_contEstCtaLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_nombre_cta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_totAb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_totDeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addGap(18, 18, 18)
                .addGroup(pnl_contEstCtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_abonar)
                    .addComponent(btn_detalleAbono))
                .addGap(61, 61, 61)
                .addComponent(scp_estCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cont_ctes.add(pnl_contEstCta, "contEstCuenta");

        pnl_HistDe.setBackground(new java.awt.Color(255, 255, 255));
        pnl_HistDe.setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 153, 51));
        jLabel42.setText("Historial de deudas");

        scp_histDe.setForeground(new java.awt.Color(255, 255, 255));

        tb_histDe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tb_histDe.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_histDe.setFocusable(false);
        tb_histDe.setGridColor(new java.awt.Color(153, 153, 153));
        tb_histDe.setRowHeight(25);
        tb_histDe.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_histDe.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_histDe.setShowHorizontalLines(true);
        tb_histDe.getTableHeader().setReorderingAllowed(false);
        scp_histDe.setViewportView(tb_histDe);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 153, 51));
        jLabel44.setText("Total de deudas pendientes:");

        lbl_totDeudas.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_totDeudas.setPreferredSize(new java.awt.Dimension(87, 32));

        javax.swing.GroupLayout pnl_HistDeLayout = new javax.swing.GroupLayout(pnl_HistDe);
        pnl_HistDe.setLayout(pnl_HistDeLayout);
        pnl_HistDeLayout.setHorizontalGroup(
            pnl_HistDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_HistDeLayout.createSequentialGroup()
                .addGroup(pnl_HistDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_HistDeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scp_histDe, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_HistDeLayout.createSequentialGroup()
                        .addGap(353, 353, 353)
                        .addComponent(jLabel42))
                    .addGroup(pnl_HistDeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_totDeudas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_HistDeLayout.setVerticalGroup(
            pnl_HistDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_HistDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addGap(21, 21, 21)
                .addGroup(pnl_HistDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel44)
                    .addComponent(lbl_totDeudas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scp_histDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        cont_ctes.add(pnl_HistDe, "historialDeudas");

        pnl_clientes.add(cont_ctes, java.awt.BorderLayout.CENTER);

        contenedorPrincipal.add(pnl_clientes, "clientes");

        pnl_inventario.setBackground(new java.awt.Color(255, 255, 255));
        pnl_inventario.setLayout(new java.awt.BorderLayout());

        pnl_barraInventario.setBackground(new java.awt.Color(255, 255, 255));
        pnl_barraInventario.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        ag_invent.setBackground(new java.awt.Color(0, 153, 0));
        ag_invent.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        ag_invent.setForeground(new java.awt.Color(255, 255, 255));
        ag_invent.setText("Agregar inventario");
        ag_invent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        ag_invent.setPreferredSize(new java.awt.Dimension(157, 38));
        ag_invent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ag_inventActionPerformed(evt);
            }
        });
        pnl_barraInventario.add(ag_invent);

        btn_prodBaj.setBackground(new java.awt.Color(0, 153, 0));
        btn_prodBaj.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_prodBaj.setForeground(new java.awt.Color(255, 255, 255));
        btn_prodBaj.setText("Productos bajos en inventario");
        btn_prodBaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_prodBaj.setPreferredSize(new java.awt.Dimension(250, 38));
        btn_prodBaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prodBajActionPerformed(evt);
            }
        });
        pnl_barraInventario.add(btn_prodBaj);

        btn_repMov.setBackground(new java.awt.Color(0, 153, 0));
        btn_repMov.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_repMov.setForeground(new java.awt.Color(255, 255, 255));
        btn_repMov.setText("Reporte de movimientos");
        btn_repMov.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_repMov.setPreferredSize(new java.awt.Dimension(210, 38));
        btn_repMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_repMovActionPerformed(evt);
            }
        });
        pnl_barraInventario.add(btn_repMov);

        btn_repInv.setBackground(new java.awt.Color(0, 153, 0));
        btn_repInv.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_repInv.setForeground(new java.awt.Color(255, 255, 255));
        btn_repInv.setText("Reporte de inventario");
        btn_repInv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_repInv.setPreferredSize(new java.awt.Dimension(179, 38));
        btn_repInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_repInvActionPerformed(evt);
            }
        });
        pnl_barraInventario.add(btn_repInv);

        pnl_inventario.add(pnl_barraInventario, java.awt.BorderLayout.NORTH);

        cont_inv.setBackground(new java.awt.Color(255, 255, 255));
        cont_inv.setLayout(new java.awt.CardLayout());

        pnl_agInv.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 153, 51));
        jLabel29.setText("Agregar a inventario");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel32.setText("Existencias:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel33.setText("Descripción:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel34.setText("Cantidad:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel35.setText("Codigo de barras:");

        btn_guardarProducto1.setBackground(new java.awt.Color(0, 153, 0));
        btn_guardarProducto1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_guardarProducto1.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardarProducto1.setText("Agregar cantidad a inventario");
        btn_guardarProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarProducto1ActionPerformed(evt);
            }
        });

        sp_cantInv.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sp_cantInv.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 10000.0d, 1.0d));
        sp_cantInv.setEditor(new javax.swing.JSpinner.NumberEditor(sp_cantInv, "0.00"));

        text_codigoBarrasInv.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbl_exI.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbl_descIn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        javax.swing.GroupLayout pnl_agInvLayout = new javax.swing.GroupLayout(pnl_agInv);
        pnl_agInv.setLayout(pnl_agInvLayout);
        pnl_agInvLayout.setHorizontalGroup(
            pnl_agInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel29))
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel35)
                .addGap(5, 5, 5)
                .addComponent(text_codigoBarrasInv, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel33)
                .addGap(5, 5, 5)
                .addComponent(lbl_descIn, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lbl_exI, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel34)
                .addGap(7, 7, 7)
                .addComponent(sp_cantInv, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btn_guardarProducto1))
        );
        pnl_agInvLayout.setVerticalGroup(
            pnl_agInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agInvLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel29)
                .addGap(38, 38, 38)
                .addGroup(pnl_agInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(text_codigoBarrasInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_agInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_descIn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnl_agInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_exI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnl_agInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sp_cantInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btn_guardarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cont_inv.add(pnl_agInv, "ag_inv");

        pnl_prBaj.setBackground(new java.awt.Color(255, 255, 255));

        scp_prodBaj.setForeground(new java.awt.Color(255, 255, 255));

        tb_prodBaj.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_prodBaj.setFocusable(false);
        tb_prodBaj.setGridColor(new java.awt.Color(153, 153, 153));
        tb_prodBaj.setRowHeight(25);
        tb_prodBaj.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_prodBaj.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_prodBaj.setShowHorizontalLines(true);
        tb_prodBaj.getTableHeader().setReorderingAllowed(false);
        scp_prodBaj.setViewportView(tb_prodBaj);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 153, 51));
        jLabel30.setText("Productos bajos en inventario");

        javax.swing.GroupLayout pnl_prBajLayout = new javax.swing.GroupLayout(pnl_prBaj);
        pnl_prBaj.setLayout(pnl_prBajLayout);
        pnl_prBajLayout.setHorizontalGroup(
            pnl_prBajLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_prBajLayout.createSequentialGroup()
                .addGroup(pnl_prBajLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_prBajLayout.createSequentialGroup()
                        .addGap(460, 460, 460)
                        .addComponent(jLabel30))
                    .addGroup(pnl_prBajLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(scp_prodBaj, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
        );
        pnl_prBajLayout.setVerticalGroup(
            pnl_prBajLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_prBajLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel30)
                .addGap(28, 28, 28)
                .addComponent(scp_prodBaj, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cont_inv.add(pnl_prBaj, "pr_baj");

        pnl_repInv.setBackground(new java.awt.Color(255, 255, 255));

        scp_repInv.setForeground(new java.awt.Color(255, 255, 255));

        tb_repInv.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_repInv.setFocusable(false);
        tb_repInv.setGridColor(new java.awt.Color(153, 153, 153));
        tb_repInv.setRowHeight(25);
        tb_repInv.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_repInv.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_repInv.setShowHorizontalLines(true);
        tb_repInv.getTableHeader().setReorderingAllowed(false);
        scp_repInv.setViewportView(tb_repInv);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 153, 51));
        jLabel31.setText("Reporte de inventario");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Categoria:");

        cmb_catRep.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmb_catRep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 102));
        jLabel36.setText("Costo del inventario:");

        lbl_costInv.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setText("Cantidad de productos en inventario:");

        lbl_cantProd.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        javax.swing.GroupLayout pnl_repInvLayout = new javax.swing.GroupLayout(pnl_repInv);
        pnl_repInv.setLayout(pnl_repInvLayout);
        pnl_repInvLayout.setHorizontalGroup(
            pnl_repInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repInvLayout.createSequentialGroup()
                .addGroup(pnl_repInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_repInvLayout.createSequentialGroup()
                        .addGap(460, 460, 460)
                        .addComponent(jLabel31))
                    .addGroup(pnl_repInvLayout.createSequentialGroup()
                        .addGap(370, 370, 370)
                        .addComponent(jLabel36)
                        .addGap(91, 91, 91)
                        .addComponent(jLabel38))
                    .addGroup(pnl_repInvLayout.createSequentialGroup()
                        .addGap(410, 410, 410)
                        .addComponent(lbl_costInv, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(220, 220, 220)
                        .addComponent(lbl_cantProd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_repInvLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_catRep, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_repInvLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(scp_repInv, javax.swing.GroupLayout.PREFERRED_SIZE, 1254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
        );
        pnl_repInvLayout.setVerticalGroup(
            pnl_repInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repInvLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel31)
                .addGap(8, 8, 8)
                .addGroup(pnl_repInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_repInvLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnl_repInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_costInv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cantProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnl_repInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmb_catRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(scp_repInv, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cont_inv.add(pnl_repInv, "rep_inv");

        pnl_repMov.setBackground(new java.awt.Color(255, 255, 255));

        scp_repMov.setForeground(new java.awt.Color(255, 255, 255));

        tb_repMov.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tb_repMov.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_repMov.setFocusable(false);
        tb_repMov.setGridColor(new java.awt.Color(153, 153, 153));
        tb_repMov.setRowHeight(25);
        tb_repMov.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_repMov.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_repMov.setShowHorizontalLines(true);
        tb_repMov.getTableHeader().setReorderingAllowed(false);
        scp_repMov.setViewportView(tb_repMov);

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 153, 51));
        jLabel37.setText("Historial de movimientos de inventario");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel50.setText("Desde el:");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel51.setText("Hasta el:");

        btn_mostrarMovimientosIn.setText("Mostrar");
        btn_mostrarMovimientosIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mostrarMovimientosInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_repMovLayout = new javax.swing.GroupLayout(pnl_repMov);
        pnl_repMov.setLayout(pnl_repMovLayout);
        pnl_repMovLayout.setHorizontalGroup(
            pnl_repMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repMovLayout.createSequentialGroup()
                .addGroup(pnl_repMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_repMovLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pnl_repMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scp_repMov, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_repMovLayout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserFinal1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_mostrarMovimientosIn))))
                    .addGroup(pnl_repMovLayout.createSequentialGroup()
                        .addGap(460, 460, 460)
                        .addComponent(jLabel37)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        pnl_repMovLayout.setVerticalGroup(
            pnl_repMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repMovLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel37)
                .addGap(27, 27, 27)
                .addGroup(pnl_repMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_repMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel51)
                        .addComponent(jLabel50)
                        .addComponent(jDateChooserInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooserFinal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_mostrarMovimientosIn))
                .addGap(18, 18, 18)
                .addComponent(scp_repMov, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cont_inv.add(pnl_repMov, "rep_mov");

        pnl_inventario.add(cont_inv, java.awt.BorderLayout.CENTER);

        contenedorPrincipal.add(pnl_inventario, "inventario");

        pnl_corte.setBackground(new java.awt.Color(255, 255, 255));
        pnl_corte.setLayout(new java.awt.BorderLayout());

        cont_corte.setBackground(new java.awt.Color(255, 255, 255));
        cont_corte.setLayout(new java.awt.CardLayout());

        pnl_infCorte.setBackground(new java.awt.Color(255, 255, 255));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 153, 51));
        jLabel67.setText("Corte");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel68.setText("Desde el:");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel69.setText("Hasta el:");

        btn_corte.setText("Mostrar");
        btn_corte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_corteActionPerformed(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel71.setText("Ventas a contado:");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel72.setText("Ventas totales:");

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel73.setText("Ventas fiadas:");

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel75.setText("Ventas");

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel76.setText("Dinero en caja");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel77.setText("Abonos:");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel78.setText("Ventas a contado:");

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel79.setText("Entradas efectivo:");

        lbl_vtaTotal.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_vtaTotal.setForeground(new java.awt.Color(102, 102, 102));
        lbl_vtaTotal.setPreferredSize(new java.awt.Dimension(10, 10));

        lbl_ventaContado.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_ventaContado.setForeground(new java.awt.Color(29, 198, 29));
        lbl_ventaContado.setText("$0.00");
        lbl_ventaContado.setPreferredSize(new java.awt.Dimension(10, 10));

        lbl_abonos.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_abonos.setForeground(new java.awt.Color(29, 198, 29));
        lbl_abonos.setText("$0.00");
        lbl_abonos.setPreferredSize(new java.awt.Dimension(10, 10));

        lbl_entradaEfectivo.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_entradaEfectivo.setForeground(new java.awt.Color(29, 198, 29));
        lbl_entradaEfectivo.setText("$0.00");
        lbl_entradaEfectivo.setPreferredSize(new java.awt.Dimension(10, 10));

        lbl_ventaContado2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_ventaContado2.setForeground(new java.awt.Color(29, 198, 29));
        lbl_ventaContado2.setText("$0.00");
        lbl_ventaContado2.setPreferredSize(new java.awt.Dimension(10, 10));

        lbl_ventaFiado.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_ventaFiado.setForeground(new java.awt.Color(29, 198, 29));
        lbl_ventaFiado.setText("$0.00");
        lbl_ventaFiado.setPreferredSize(new java.awt.Dimension(10, 10));

        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel81.setText("Devoluciones:");

        lbl_devoluciones.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_devoluciones.setForeground(new java.awt.Color(217, 30, 30));
        lbl_devoluciones.setText("$0.00");
        lbl_devoluciones.setPreferredSize(new java.awt.Dimension(10, 10));

        lbl_salidaEfectivo.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_salidaEfectivo.setForeground(new java.awt.Color(217, 30, 30));
        lbl_salidaEfectivo.setText("$0.00");
        lbl_salidaEfectivo.setPreferredSize(new java.awt.Dimension(10, 10));

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel82.setText("Salidas efectivo:");

        jSeparator4.setForeground(new java.awt.Color(102, 102, 102));
        jSeparator4.setPreferredSize(new java.awt.Dimension(70, 10));

        lbl_totalDineroCaja.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_totalDineroCaja.setForeground(new java.awt.Color(102, 102, 102));
        lbl_totalDineroCaja.setText("$0.00");
        lbl_totalDineroCaja.setPreferredSize(new java.awt.Dimension(10, 10));

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel83.setText("Devoluciones:");

        lbl_devoluciones2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_devoluciones2.setForeground(new java.awt.Color(217, 30, 30));
        lbl_devoluciones2.setText("$0.00");
        lbl_devoluciones2.setPreferredSize(new java.awt.Dimension(10, 10));

        jSeparator5.setForeground(new java.awt.Color(102, 102, 102));

        lbl_toVentas.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_toVentas.setForeground(new java.awt.Color(102, 102, 102));
        lbl_toVentas.setText("$0.00");
        lbl_toVentas.setPreferredSize(new java.awt.Dimension(10, 10));

        jLabel84.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel84.setText("Devoluciones");

        scp_ent.setForeground(new java.awt.Color(255, 255, 255));

        tb_ent.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_ent.setFocusable(false);
        tb_ent.setGridColor(new java.awt.Color(153, 153, 153));
        tb_ent.setRowHeight(25);
        tb_ent.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_ent.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_ent.setShowHorizontalLines(true);
        tb_ent.getTableHeader().setReorderingAllowed(false);
        scp_ent.setViewportView(tb_ent);

        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel85.setText("Entradas y salidas de efectivo");

        scp_devoluciones.setForeground(new java.awt.Color(255, 255, 255));

        tb_devoluciones.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_devoluciones.setFocusable(false);
        tb_devoluciones.setGridColor(new java.awt.Color(153, 153, 153));
        tb_devoluciones.setRowHeight(25);
        tb_devoluciones.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_devoluciones.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_devoluciones.setShowHorizontalLines(true);
        tb_devoluciones.getTableHeader().setReorderingAllowed(false);
        scp_devoluciones.setViewportView(tb_devoluciones);

        scp_abonos.setForeground(new java.awt.Color(255, 255, 255));

        tb_abonos.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_abonos.setFocusable(false);
        tb_abonos.setGridColor(new java.awt.Color(153, 153, 153));
        tb_abonos.setRowHeight(25);
        tb_abonos.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_abonos.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_abonos.setShowHorizontalLines(true);
        tb_abonos.getTableHeader().setReorderingAllowed(false);
        scp_abonos.setViewportView(tb_abonos);

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel86.setText("Abonos");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel74.setText("Efectivo inicial:");

        lbl_efectivoInicial.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_efectivoInicial.setForeground(new java.awt.Color(102, 102, 102));
        lbl_efectivoInicial.setText("$0.00");
        lbl_efectivoInicial.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout pnl_infCorteLayout = new javax.swing.GroupLayout(pnl_infCorte);
        pnl_infCorte.setLayout(pnl_infCorteLayout);
        pnl_infCorteLayout.setHorizontalGroup(
            pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(jLabel67))
                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbl_devoluciones2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel75)
                                                .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lbl_ventaFiado, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lbl_ventaContado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(77, 77, 77))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_infCorteLayout.createSequentialGroup()
                                        .addGap(159, 159, 159)
                                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_toVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(82, 82, 82)))
                                .addComponent(scp_devoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scp_abonos, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fecha_inicioCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel72)
                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(lbl_devoluciones, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                                    .addComponent(lbl_salidaEfectivo, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                                    .addComponent(lbl_totalDineroCaja, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_infCorteLayout.createSequentialGroup()
                                                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lbl_entradaEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(270, 270, 270)
                                        .addComponent(jLabel84)
                                        .addGap(358, 358, 358)
                                        .addComponent(jLabel86))
                                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel74, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12)
                                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_ventaContado, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_abonos, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_efectivoInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel69)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_infCorteLayout.createSequentialGroup()
                                        .addGap(170, 170, 170)
                                        .addComponent(lbl_vtaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel76, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                        .addGap(262, 262, 262)
                                        .addComponent(jLabel85))
                                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fecha_finCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_corte)))))))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_infCorteLayout.createSequentialGroup()
                    .addGap(345, 345, 345)
                    .addComponent(scp_ent, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(121, Short.MAX_VALUE)))
        );
        pnl_infCorteLayout.setVerticalGroup(
            pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_corte)
                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addGap(26, 26, 26)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel69)
                            .addComponent(fecha_inicioCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha_finCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68))))
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel76))
                    .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnl_infCorteLayout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(jLabel85))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_infCorteLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_vtaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(lbl_efectivoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(lbl_ventaContado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(lbl_abonos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_infCorteLayout.createSequentialGroup()
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(lbl_totalDineroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel84)
                                .addComponent(jLabel86)))
                        .addGap(20, 20, 20)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scp_devoluciones, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(scp_abonos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(pnl_infCorteLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_entradaEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel79))
                        .addGap(6, 6, 6)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel82)
                            .addComponent(lbl_salidaEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_devoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel81))
                        .addGap(49, 49, 49)
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel78)
                            .addComponent(lbl_ventaContado2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_ventaFiado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_devoluciones2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_toVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(pnl_infCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_infCorteLayout.createSequentialGroup()
                    .addGap(172, 172, 172)
                    .addComponent(scp_ent, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(357, Short.MAX_VALUE)))
        );

        cont_corte.add(pnl_infCorte, "pnlCorte");

        pnl_corte.add(cont_corte, java.awt.BorderLayout.CENTER);

        contenedorPrincipal.add(pnl_corte, "corte");

        pnl_usuarios.setBackground(new java.awt.Color(255, 255, 255));
        pnl_usuarios.setLayout(new java.awt.BorderLayout());

        pnl_barraUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        pnl_barraUsuarios.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btn_nvoUs.setBackground(new java.awt.Color(0, 153, 0));
        btn_nvoUs.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_nvoUs.setForeground(new java.awt.Color(255, 255, 255));
        btn_nvoUs.setText("Nuevo usuario");
        btn_nvoUs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_nvoUs.setPreferredSize(new java.awt.Dimension(137, 38));
        btn_nvoUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nvoUsActionPerformed(evt);
            }
        });
        pnl_barraUsuarios.add(btn_nvoUs);

        btn_adUs.setBackground(new java.awt.Color(0, 153, 0));
        btn_adUs.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_adUs.setForeground(new java.awt.Color(255, 255, 255));
        btn_adUs.setText("Administrar usuario");
        btn_adUs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btn_adUs.setPreferredSize(new java.awt.Dimension(178, 38));
        btn_adUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adUsActionPerformed(evt);
            }
        });
        pnl_barraUsuarios.add(btn_adUs);

        pnl_usuarios.add(pnl_barraUsuarios, java.awt.BorderLayout.NORTH);

        cont_us.setBackground(new java.awt.Color(255, 255, 255));
        cont_us.setLayout(new java.awt.CardLayout());

        pnl_agUsuario.setBackground(new java.awt.Color(255, 255, 255));
        pnl_agUsuario.setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 153, 51));
        jLabel53.setText("Nuevo usuario");

        btn_guardarUs.setBackground(new java.awt.Color(0, 153, 0));
        btn_guardarUs.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_guardarUs.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardarUs.setText("Guardar usuario");
        btn_guardarUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarUsActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel52.setText("Contraseña:");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel61.setText("Usuario:");

        text_usuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        javax.swing.GroupLayout pnl_agUsuarioLayout = new javax.swing.GroupLayout(pnl_agUsuario);
        pnl_agUsuario.setLayout(pnl_agUsuarioLayout);
        pnl_agUsuarioLayout.setHorizontalGroup(
            pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                .addGroup(pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(jLabel53))
                    .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel61))
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(text_usuario)
                            .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(btn_guardarUs)))
                .addGap(806, 806, 806))
        );
        pnl_agUsuarioLayout.setVerticalGroup(
            pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53)
                .addGap(18, 18, 18)
                .addGroup(pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_agUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52)
                    .addGroup(pnl_agUsuarioLayout.createSequentialGroup()
                        .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(24, 24, 24)
                .addComponent(btn_guardarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        cont_us.add(pnl_agUsuario, "ag_usuario");

        pnl_adUsuario.setBackground(new java.awt.Color(255, 255, 255));
        pnl_adUsuario.setPreferredSize(new java.awt.Dimension(880, 660));

        scp_adUsuario.setForeground(new java.awt.Color(255, 255, 255));

        tb_adUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_adUsuario.setFocusable(false);
        tb_adUsuario.setGridColor(new java.awt.Color(153, 153, 153));
        tb_adUsuario.setRowHeight(25);
        tb_adUsuario.setSelectionBackground(new java.awt.Color(222, 218, 218));
        tb_adUsuario.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_adUsuario.setShowHorizontalLines(true);
        tb_adUsuario.getTableHeader().setReorderingAllowed(false);
        scp_adUsuario.setViewportView(tb_adUsuario);

        btn_eliminarUsuario.setBackground(new java.awt.Color(245, 38, 38));
        btn_eliminarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_eliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminarUsuario.setText("Eliminar");
        btn_eliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarUsuarioActionPerformed(evt);
            }
        });

        btn_modificarUsuario.setBackground(new java.awt.Color(51, 51, 255));
        btn_modificarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_modificarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btn_modificarUsuario.setText("Modificar");
        btn_modificarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarUsuarioActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel60.setText("Administrar usuario");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel54.setText("Contraseña:");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel62.setText("Usuario:");

        text_usuarioAd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        javax.swing.GroupLayout pnl_adUsuarioLayout = new javax.swing.GroupLayout(pnl_adUsuario);
        pnl_adUsuario.setLayout(pnl_adUsuarioLayout);
        pnl_adUsuarioLayout.setHorizontalGroup(
            pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(jLabel60))
                    .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(scp_adUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel62))
                            .addComponent(jLabel54))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(text_usuarioAd)
                            .addComponent(text_passwordAd, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_modificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(521, 521, 521))
        );
        pnl_adUsuarioLayout.setVerticalGroup(
            pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel60)
                .addGap(44, 44, 44)
                .addComponent(scp_adUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_usuarioAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62)
                    .addComponent(btn_modificarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel54)
                    .addGroup(pnl_adUsuarioLayout.createSequentialGroup()
                        .addGroup(pnl_adUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_passwordAd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminarUsuario))
                        .addGap(2, 2, 2)))
                .addGap(169, 169, 169))
        );

        cont_us.add(pnl_adUsuario, "ad_usuario");

        pnl_usuarios.add(cont_us, java.awt.BorderLayout.CENTER);

        contenedorPrincipal.add(pnl_usuarios, "usuarios");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contenedorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(contenedorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_guardarCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarCteActionPerformed
        // TODO add your handling code here:
        Cliente cliente = new Cliente();
        CtrlCliente ctCliente = new CtrlCliente();
        String nombre = text_nombre.getText().trim();
        String apellidos = text_apellidos.getText().trim();
        String telefono = text_telefono.getText().trim();
        if (ctCliente.clienteExiste(nombre, apellidos)) {
            JOptionPane.showMessageDialog(null, "El cliente ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nombre.equals("") || apellidos.equals("") || telefono.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!soloLetras(nombre) || !soloLetras(apellidos)) {
            JOptionPane.showMessageDialog(null, "Nombre y apellidos solo deben contener letras", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        cliente.setNombre_cliente(nombre);
        cliente.setApellidos_cliente(apellidos);
        cliente.setTelefono_cliente(telefono);
        if (ctCliente.agregar(cliente)) {
            JOptionPane.showMessageDialog(null, "Registro guardado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            text_nombre.setText("");
            text_apellidos.setText("");
            text_telefono.setText("");
            this.cargarTablaClientes();
            cargarTablaEstadoCuenta();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_guardarCteActionPerformed

    private void btn_modificarCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarCteActionPerformed
        // TODO add your handling code here:
        String nombre = txt_nombre.getText().trim();
        String apellidos = txt_apellidos.getText().trim();
        String telefono = txt_telefono.getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!soloLetras(nombre) || !soloLetras(apellidos)) {
            JOptionPane.showMessageDialog(null, "Nombre y apellidos solo deben contener letras", "Validación", JOptionPane.WARNING_MESSAGE);
            cargarInformacionCliente(id_cliente);
            return;
        }

        if (!soloNumeros(telefono)) {
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números", "Validación", JOptionPane.WARNING_MESSAGE);
            cargarInformacionCliente(id_cliente);
            return;
        }

        Cliente cliente = new Cliente();
        CtrlCliente ctCliente = new CtrlCliente();
        cliente.setId_cliente(id_cliente);
        cliente.setNombre_cliente(nombre);
        cliente.setApellidos_cliente(apellidos);
        cliente.setTelefono_cliente(telefono);

        if (ctCliente.actualizar(cliente)) {
            JOptionPane.showMessageDialog(null, "Cliente actualizado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.cargarTablaClientes();
            this.limpiarClientes();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarCteActionPerformed

    private void btn_eliminarCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarCteActionPerformed
        // TODO add your handling code here:
        CtrlCliente ctCliente = new CtrlCliente();
    
    if (is_selected) {
        // Verifica si el cliente tiene deudas pendientes
        if (ctCliente.clienteTieneDeudasPendientes(this.id_cliente)) {
            JOptionPane.showMessageDialog(null, "El cliente no se puede eliminar porque tiene deudas pendientes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este cliente seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            if (ctCliente.eliminar(this.id_cliente)) {
                JOptionPane.showMessageDialog(null, "El cliente seleccionado se eliminó correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.cargarTablaClientes();
                this.limpiarClientes();
                this.cargarTablaEstadoCuenta();
            } else {
                JOptionPane.showMessageDialog(null, "Falló la eliminación del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione el cliente que desea eliminar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btn_eliminarCteActionPerformed


    //presiona una tecla y se muestra abajo
    private void txt_buscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarClienteKeyReleased
        // TODO add your handling code here:
        // Obtener el texto del campo de texto "txt_buscarCliente" después de quitar los espacios en blanco al inicio y al final
        String buscarCliente = txt_buscarCliente.getText().trim();
        // Llamar al método "buscarCliente" para buscar los clientes que coincidan con el texto "buscarCliente"
        DefaultTableModel model = buscarCliente(buscarCliente);
        // Actualizar la tabla "tb_adcliente" con el nuevo modelo de datos
        tb_adcliente.setModel(model);
        // Llamar al método "diseñoTbClientes" para configurar el diseño de la tabla "tb_adcliente"
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TableColumnModel columnModel = tb_adcliente.getColumnModel();
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centrado);
        }
    }//GEN-LAST:event_txt_buscarClienteKeyReleased

    private void btn_guardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarProductoActionPerformed
        // TODO add your handling code here:
        Productos producto = new Productos();
        CtrlProductos ctProducto = new CtrlProductos();
        String descripcionV = text_descProd.getText().trim();
        String codigoBarrasV = text_codigoBarras.getText().trim();
        String categorias = cmb_catProducto.getSelectedItem().toString();

        // Validar campos vacíos o inválidos
        if (descripcionV.equals("")
                || (double) sp_cost.getValue() <= 0
                || (double) sp_vent.getValue() <= 0
                || (double) sp_exc.getValue() <= 0) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
         if ((double) sp_vent.getValue() < (double) sp_cost.getValue()) {
            JOptionPane.showMessageDialog(null, "El precio de venta no puede ser menor que el de costo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
         }

        // Validar que descripción y código solo contengan letras y números
        if (!letrasYNumeros(descripcionV) || !letrasYNumeros(codigoBarrasV)) {
            JOptionPane.showMessageDialog(null, "La descripción y el código de barras solo deben contener letras y números", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (categorias.equalsIgnoreCase("Seleccione la categoria")) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoria", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Verificamos si el código de barras ya existe
        if (ctProducto.codigoYaExiste(codigoBarrasV)) {
            JOptionPane.showMessageDialog(null, "El código de barras ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Resto del proceso para guardar el producto
        this.idCategoria();
        producto.setIdCategoria(idCategoria());
        producto.setCodigo(codigoBarrasV);
        producto.setDescripcion(descripcionV);

        try {
            double costo = (double) sp_cost.getValue();
            double venta = (double) sp_vent.getValue();
            double existencias = (double) sp_exc.getValue();
            producto.setPrecio_costo(costo);
            producto.setPrecio_venta(venta);
            producto.setExistencias(existencias);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar y asignar unidad
        String unidad = "";
        if (rbtn_pza.isSelected()) {
            unidad = "Pieza";
        } else if (rbtn_granel.isSelected()) {
            unidad = "Granel";
        } else if (rbtn_pqte.isSelected()) {
            unidad = "Paquete";
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una unidad", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        producto.setUnidad(unidad);

        // Guardar producto
        if (ctProducto.agregar(producto)) {
            int idNuevoProducto = ctProducto.obtenerIdPorCodigo(codigoBarrasV);
            producto.setIdProducto(idNuevoProducto);

            String fechaActual = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());
            movimientosInventario movimiento = new movimientosInventario();
            movimiento.setFechaMovimiento(fechaActual);
            movimiento.setTipoMovimiento("ENTRADA");
            movimiento.setCantidad(producto.getExistencias());
            movimiento.setCantidadAnt(0.0);
            movimiento.setCantidadActual(producto.getExistencias());
            movimiento.setIdProducto(idNuevoProducto);
            movimiento.setIdUsuario(Login.id_usuario);

            ctrl_movimientos ctrlMov = new ctrl_movimientos();
            ctrlMov.agregarMovimientoInventario(movimiento);

            JOptionPane.showMessageDialog(null, "Registro guardado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.cargarCategoriasGuardarProducto();
            this.cargar_tablaProductos();
            this.cargar_tablaMovimientos();
            this.totalExistencias();
            this.totalSaldoInventario();
            this.cargar_tablaProductosInventario();
            this.cargar_tablaProductosBajoInv();
            this.cmb_catProducto.setSelectedItem("Seleccione la categoria del producto");
            text_codigoBarras.setText("");
            sp_cost.setValue(0.00);
            sp_vent.setValue(0.00);
            text_descProd.setText("");
            sp_exc.setValue(0.00);
            buttonGroup1.clearSelection();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar producto", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_guardarProductoActionPerformed

    private void btn_agregarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarCategoriaActionPerformed
        // TODO add your handling code here:
        CtrlCategoria ctrlcategoria = new CtrlCategoria();
        String ncategoria = JOptionPane.showInputDialog("Ingrese la Nueva Categoria");
        if (ncategoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese una categoria", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Categoria categoria = new Categoria(0, ncategoria);
            if (ctrlcategoria.agregar(categoria)) {
                JOptionPane.showMessageDialog(null, "Categoria registrada", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                this.cargar_tablaCategorias();
                this.cargarCategoriasGuardarProducto();
                this.cargarCategoriasActualizarProducto();
                this.cargarCategoriasReporteInventario();
                text_categoria.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar categoria", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_agregarCategoriaActionPerformed

    private void btn_modificarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarCatActionPerformed
        // TODO add your handling code here:
        if (text_categoria.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete el campo", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Categoria categoria = new Categoria();
            CtrlCategoria ctCategoria = new CtrlCategoria();
            categoria.setId_categoria(id_categoria);
            categoria.setCategoria(text_categoria.getText().trim());
            if (ctCategoria.actualizar(categoria)) {
                JOptionPane.showMessageDialog(null, "Categoria actualizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.cargar_tablaCategorias();
                text_categoria.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar categoria", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }//GEN-LAST:event_btn_modificarCatActionPerformed

    private void btn_eliminarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarCatActionPerformed
        // TODO add your handling code here:
        CtrlCategoria ctrlcategoria = new CtrlCategoria();
        if (is_selected) {

            int opcion = JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar la Categoria seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (opcion == JOptionPane.YES_OPTION) {
                if (ctrlcategoria.eliminar(this.id_categoria)) {
                    JOptionPane.showMessageDialog(null, "La Categoria seleccionada se eliminó Correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    this.cargar_tablaCategorias();

                    text_categoria.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "La Categoria seleccionada no se eliminó correctamente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione la categoria que desea eliminar", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_eliminarCatActionPerformed

    private void txt_buscarCliente1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarCliente1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarCliente1KeyReleased

    private void txt_buscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarProductoKeyReleased
        // TODO add your handling code here:
        // Obtener el texto del campo de texto "txt_buscarCliente" después de quitar los espacios en blanco al inicio y al final
        String buscarProducto = txt_buscarProducto.getText().trim();
        // Llamar al método "buscarCliente" para buscar los clientes que coincidan con el texto "buscarCliente"
        DefaultTableModel model = buscarProductos(buscarProducto);
        tb_adProducto.setModel(model);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TableColumnModel columnModel = tb_adProducto.getColumnModel();
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centrado);
        }
        columnModel.getColumn(1).setPreferredWidth(80); // Establece el ancho de la columna "Categoria"
        columnModel.getColumn(2).setPreferredWidth(130); // Establece el ancho de la columna "Codigo del producto"
        columnModel.getColumn(3).setPreferredWidth(300); // Establece el ancho de la columna "Descripcion"
        // Llamar al método "diseñoTbClientes" para configurar el diseño de la tabla "tb_adcliente"
        

    }//GEN-LAST:event_txt_buscarProductoKeyReleased

    private void btn_modificarPtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarPtoActionPerformed
        // TODO add your handling code here:
        Productos producto = new Productos();
        CtrlProductos ctProducto = new CtrlProductos();
        String descripcionV = text_descProdAct.getText().trim();
        String codigoBarrasV = text_codigoBarrasAct.getText().trim();
        String categorias = cmb_catProdAct.getSelectedItem().toString();

        // Validar campos vacíos o inválidos
        if (descripcionV.isEmpty()
                || codigoBarrasV.isEmpty()
                || (double) sp_cost1.getValue() <= 0
                || (double) sp_vent1.getValue() <= 0
                || (double) sp_exc1.getValue() <= 0) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Validar que la descripción y el código de barras solo contengan letras y números
        if (!letrasYNumeros(descripcionV) || !letrasYNumeros(codigoBarrasV)) {
            JOptionPane.showMessageDialog(null, "La descripción y el código de barras solo deben contener letras y números", "Validación", JOptionPane.WARNING_MESSAGE);
            cargarInformacionProducto(id_producto);
            return;
        }

        if (categorias.equalsIgnoreCase("Seleccione la categoria")) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoría", "Información", JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        // Resto del proceso para actualizar el producto
        this.idCategoria();
        producto.setIdCategoria(obtenerIdCategoria);
        producto.setCodigo(codigoBarrasV);
        producto.setDescripcion(descripcionV);

        try {
            double costo = (double) sp_cost1.getValue();
            double venta = (double) sp_vent1.getValue();
            double existencias = (double) sp_exc1.getValue();
            producto.setPrecio_costo(costo);
            producto.setPrecio_venta(venta);
            producto.setExistencias(existencias);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos en los campos de costo, venta y existencias.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar y asignar unidad
        String unidad = "";
        if (rbtn_pzaAct.isSelected()) {
            unidad = "Pieza";
        } else if (rbtn_gnelAct.isSelected()) {
            unidad = "Granel";
        } else if (rbtn_paqteAct.isSelected()) {
            unidad = "Paquete";
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una unidad", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        producto.setUnidad(unidad);

        // Actualizar producto
        if (ctProducto.actualizar(producto, id_producto)) {
            JOptionPane.showMessageDialog(null, "Registro actualizado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.cargarCategoriasActualizarProducto();
            this.cargar_tablaProductos();
            this.cargar_tablaProductosInventario();
            this.cargar_tablaProductosBajoInv();
            this.cargar_tablaMovimientos();
            this.totalExistencias();
            this.totalSaldoInventario();
            // Limpiar campos
            sp_cost1.setValue(0.00);
            text_descProdAct.setText("");
            text_codigoBarrasAct.setText("");
            sp_vent1.setValue(0.00);
            sp_exc1.setValue(0.00);
            cmb_catProdAct.setSelectedItem("Seleccione la categoria");
            buttonGroup1.clearSelection();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarPtoActionPerformed

    private void btn_eliminarPtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarPtoActionPerformed
        // TODO add your handling code here:
        CtrlProductos ctProductos = new CtrlProductos();
        if (is_selected) {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este articulo seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (opcion == JOptionPane.YES_OPTION) {
                if (ctProductos.eliminar(this.id_producto)) {
                    JOptionPane.showMessageDialog(null, "El articulo seleccionado se eliminó correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    this.cargar_tablaProductos();
                    sp_cost1.setValue(0.00);
                    sp_vent1.setValue(0.00);
                    text_descProdAct.setText("");
                    text_codigoBarrasAct.setText("");
                    sp_exc.setValue(0.00);
                    this.cmb_catProdAct.setSelectedItem("Seleccione la categoria");
                    buttonGroup1.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar producto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el producto que desea eliminar", "Informacion", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_btn_eliminarPtoActionPerformed

    private void txt_codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoKeyReleased
        // TODO add your handling code here:
        // Verifica si ya hay un temporizador en ejecución
        if (timer != null) {
            // Si hay un temporizador en ejecución, se detiene
            timer.stop();
        }
        // Se crea un nuevo temporizador con un retraso de 500 milisegundos (0.5 segundos)
        timer = new Timer(500, new ActionListener() {
            // Definimos la acción que se ejecutará cuando el temporizador expire
            @Override
            public void actionPerformed(ActionEvent e) {
                // Detenemos el temporizador para asegurarnos de que no se repita
                timer.stop();
                // Llamamos al método que realiza la búsqueda del producto
                codigoProducto();
            }
        });
        // Configuramos el temporizador para que no se repita
        timer.setRepeats(false);
        // Iniciamos el temporizador, comenzando la cuenta regresiva de 500 milisegundos
        timer.start();
    }//GEN-LAST:event_txt_codigoKeyReleased

    private void btn_cobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cobrarActionPerformed
        // TODO add your handling code here:
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Cobrar", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        PCobrar cobrarPanel = new PCobrar();

        dialog.setContentPane(cobrarPanel);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal

        // Mostrar el JDialog
        dialog.setVisible(true);
        txt_codigo.requestFocus();

    }//GEN-LAST:event_btn_cobrarActionPerformed
    

    private void btn_vtasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vtasActionPerformed
        mostrarPanel("ventas");
         txt_codigo.requestFocus();
          // Cambiar color del botón seleccionado
    btn_vtas.setBackground(colorSeleccionado);

    // Restaurar color a los demás
    btn_ctes.setBackground(colorNormal);
    btn_producto.setBackground(colorNormal);
    btn_inv.setBackground(colorNormal);
    btn_cte.setBackground(colorNormal);
    btn_usuarios.setBackground(colorNormal);
    }//GEN-LAST:event_btn_vtasActionPerformed

    private void btn_ctesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ctesActionPerformed
        // TODO add your handling code here:
        mostrarPanel("clientes");
        btn_vtas.setBackground(colorNormal);

    // Restaurar color a los demás
    btn_ctes.setBackground(colorSeleccionado);
    btn_producto.setBackground(colorNormal);
    btn_inv.setBackground(colorNormal);
    btn_corte.setBackground(colorNormal);
    btn_usuarios.setBackground(colorNormal);
    }//GEN-LAST:event_btn_ctesActionPerformed

    private void btn_nvoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nvoProdActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_prod.getLayout();
        cl.show(cont_prod, "agProducto");
    }//GEN-LAST:event_btn_nvoProdActionPerformed

    private void btn_adProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adProdActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_prod.getLayout();
        cl.show(cont_prod, "adProducto");
    }//GEN-LAST:event_btn_adProdActionPerformed

    private void btn_CatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CatActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_prod.getLayout();
        cl.show(cont_prod, "categorias");
    }//GEN-LAST:event_btn_CatActionPerformed

    private void btn_nvoCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nvoCteActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_ctes.getLayout();
        cl.show(cont_ctes, "ag_cliente");
    }//GEN-LAST:event_btn_nvoCteActionPerformed

    private void btn_adCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adCteActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_ctes.getLayout();
        cl.show(cont_ctes, "ad_cliente");
    }//GEN-LAST:event_btn_adCteActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        // TODO add your handling code here:
        pnlBuscar();
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_entradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entradasActionPerformed
        // TODO add your handling code here:
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Registro de entradas y salidas de efectivo", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        EntradasSalidas entradasYSalidas = new EntradasSalidas();

        dialog.setContentPane(entradasYSalidas);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal

        // Mostrar el JDialog
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_entradasActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        // TODO add your handling code here:
        int selectedRow = tb_ventas.getSelectedRow();
                    if (selectedRow != -1) {
                        eliminarProducto(selectedRow); // pass the selected row index to the method
                        txt_codigo.requestFocus();
        }

    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void ag_inventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ag_inventActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_inv.getLayout();
        cl.show(cont_inv, "ag_inv");
    }//GEN-LAST:event_ag_inventActionPerformed

    private void btn_prodBajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prodBajActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_inv.getLayout();
        cl.show(cont_inv, "pr_baj");
    }//GEN-LAST:event_btn_prodBajActionPerformed

    private void btn_guardarProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarProducto1ActionPerformed
        // TODO add your handling code here:
        // Crear instancia del controlador
        CtrlProductos ctProducto = new CtrlProductos();
        String codigoBarras = text_codigoBarrasInv.getText().trim();

        // Validar campos
        if (codigoBarras.equals("") || (double) sp_cantInv.getValue() <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el código de barras y una cantidad válida", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Buscar el producto en la base de datos por su código de barras
        Productos producto = buscarProductoPorCodigo(codigoBarras);

        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener la cantidad a agregar
        double cantidadAgregar = (double) sp_cantInv.getValue();
        double nuevasExistencias = producto.getExistencias() + cantidadAgregar;

        // Actualizar en la base de datos
        if (ctProducto.actualizarExistencias(producto.getIdProducto(), nuevasExistencias)) {
            JOptionPane.showMessageDialog(null, "Inventario actualizado. Nuevas existencias: " + nuevasExistencias, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar campos
            text_codigoBarrasInv.setText("");
            lbl_descIn.setText("");
            lbl_exI.setText("");
            sp_cantInv.setValue(0.00);
            this.cargar_tablaProductos();
            this.cargar_tablaMovimientos();
            this.totalExistencias();
            this.totalSaldoInventario();
            this.cargar_tablaProductosInventario();
            this.cargar_tablaProductosBajoInv();
            
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_guardarProducto1ActionPerformed

    private void btn_invActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invActionPerformed
        // TODO add your handling code here:
        mostrarPanel("inventario");
        btn_vtas.setBackground(colorNormal);

    // Restaurar color a los demás
    btn_ctes.setBackground(colorNormal);
    btn_producto.setBackground(colorNormal);
    btn_inv.setBackground(colorSeleccionado);
    btn_cte.setBackground(colorNormal);
    btn_usuarios.setBackground(colorNormal);
    }//GEN-LAST:event_btn_invActionPerformed

    private void btn_repMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_repMovActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_inv.getLayout();
        cl.show(cont_inv, "rep_mov");
    }//GEN-LAST:event_btn_repMovActionPerformed

    private void btn_repInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_repInvActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_inv.getLayout();
        cl.show(cont_inv, "rep_inv");
    }//GEN-LAST:event_btn_repInvActionPerformed

    private void btn_cteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cteActionPerformed
        // TODO add your handling code here:
        mostrarPanel("corte");
        btn_vtas.setBackground(colorNormal);

    // Restaurar color a los demás
    btn_ctes.setBackground(colorNormal);
    btn_producto.setBackground(colorNormal);
    btn_inv.setBackground(colorNormal);
    btn_cte.setBackground(colorSeleccionado);
    btn_usuarios.setBackground(colorNormal);
    }//GEN-LAST:event_btn_cteActionPerformed

    private void btn_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosActionPerformed
        // TODO add your handling code here:
        mostrarPanel("usuarios");
        btn_vtas.setBackground(colorNormal);

    // Restaurar color a los demás
    btn_ctes.setBackground(colorNormal);
    btn_producto.setBackground(colorNormal);
    btn_inv.setBackground(colorNormal);
    btn_cte.setBackground(colorNormal);
    btn_usuarios.setBackground(colorSeleccionado);
    }//GEN-LAST:event_btn_usuariosActionPerformed

    private void btn_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productoActionPerformed
        // TODO add your handling code here:
        mostrarPanel("productos");
        btn_vtas.setBackground(colorNormal);

    // Restaurar color a los demás
    btn_ctes.setBackground(colorNormal);
    btn_producto.setBackground(colorSeleccionado);
    btn_inv.setBackground(colorNormal);
    btn_corte.setBackground(colorNormal);
    btn_usuarios.setBackground(colorNormal);
        
    }//GEN-LAST:event_btn_productoActionPerformed

    private void btn_acEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acEstActionPerformed
        // TODO add your handling code here:
        if (is_selected) {
            // Cambiar al panel de estado de cuenta del cliente
            CardLayout cl = (CardLayout) cont_ctes.getLayout();
            cl.show(cont_ctes, "contEstCuenta");
            cargarTablaEstadoCuentaFiado(id_clienteEstado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un cliente primero.");
        }
    }//GEN-LAST:event_btn_acEstActionPerformed

    private void txt_buscarClienteEstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarClienteEstKeyReleased
        // TODO add your handling code here:
        // Obtener el texto del campo de texto "txt_buscarCliente" después de quitar los espacios en blanco al inicio y al final
        String buscarCliente = txt_buscarClienteEst.getText().trim();
        // Llamar al método "buscarCliente" para buscar los clientes que coincidan con el texto "buscarCliente"
        DefaultTableModel model = buscarCliente(buscarCliente);
        // Actualizar la tabla "tb_adcliente" con el nuevo modelo de datos
        tb_estCta.setModel(model);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TableColumnModel columnModel = tb_estCta.getColumnModel();
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centrado);
        }

    }//GEN-LAST:event_txt_buscarClienteEstKeyReleased

    private void btn_abonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abonarActionPerformed
        // TODO add your handling code here:
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Abonar", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        PAbono cobrarAbono = new PAbono();

        dialog.setContentPane(cobrarAbono);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal

        // Mostrar el JDialog
        dialog.setVisible(true);

    }//GEN-LAST:event_btn_abonarActionPerformed

    private void btn_detalleAbonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detalleAbonoActionPerformed
        // TODO add your handling code here:
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Detalle de abonos", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        DetAbono detAbono = new DetAbono();

        dialog.setContentPane(detAbono);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal

        // Mostrar el JDialog
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_detalleAbonoActionPerformed

    private void btn_histDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_histDeActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_ctes.getLayout();
        cl.show(cont_ctes, "historialDeudas");
    }//GEN-LAST:event_btn_histDeActionPerformed

    private void btn_devolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_devolActionPerformed
        // TODO add your handling code here:
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Historial de ventas", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        VentasDevoluciones ventasDev = new VentasDevoluciones();

        dialog.setContentPane(ventasDev);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal
        // Mostrar el JDialog
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_devolActionPerformed

    private void btn_ventasPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ventasPeriodoActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_prod.getLayout();
        cl.show(cont_prod, "ventasPeriodo");
    }//GEN-LAST:event_btn_ventasPeriodoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Date fechaInicio = jDateChooserInicio.getDate();
        Date fechaFin = jDateChooserFinal.getDate();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(null, "Seleccione ambas fechas", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (fechaFin.before(fechaInicio)) {
            JOptionPane.showMessageDialog(null, "La fecha final no puede ser anterior a la inicial", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Ajustar fechaFin para incluir todo el día
        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fechaFin);
        calendarFin.set(Calendar.HOUR_OF_DAY, 23);
        calendarFin.set(Calendar.MINUTE, 59);
        calendarFin.set(Calendar.SECOND, 59);
        calendarFin.set(Calendar.MILLISECOND, 999);
        fechaFin = calendarFin.getTime();

        // Ajustar fechaInicio para incluir el inicio del día
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.setTime(fechaInicio);
        calendarInicio.set(Calendar.HOUR_OF_DAY, 0);
        calendarInicio.set(Calendar.MINUTE, 0);
        calendarInicio.set(Calendar.SECOND, 0);
        calendarInicio.set(Calendar.MILLISECOND, 0);
        fechaInicio = calendarInicio.getTime();
        cargar_tablaVentasPorPeriodo(fechaInicio, fechaFin);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_corteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_corteActionPerformed
        // TODO add your handling code here:
        Date fechaInicio = fecha_inicioCorte.getDate();
        Date fechaFin = fecha_finCorte.getDate();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(null, "Seleccione ambas fechas", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (fechaFin.before(fechaInicio)) {
            JOptionPane.showMessageDialog(null, "La fecha final no puede ser anterior a la inicial", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Ajustar fechaFin para incluir todo el día
        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fechaFin);
        calendarFin.set(Calendar.HOUR_OF_DAY, 23);
        calendarFin.set(Calendar.MINUTE, 59);
        calendarFin.set(Calendar.SECOND, 59);
        calendarFin.set(Calendar.MILLISECOND, 999);
        fechaFin = calendarFin.getTime();

        // Ajustar fechaInicio para incluir el inicio del día
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.setTime(fechaInicio);
        calendarInicio.set(Calendar.HOUR_OF_DAY, 0);
        calendarInicio.set(Calendar.MINUTE, 0);
        calendarInicio.set(Calendar.SECOND, 0);
        calendarInicio.set(Calendar.MILLISECOND, 0);
        fechaInicio = calendarInicio.getTime();
        cargarResumenCorte(fechaInicio, fechaFin);
        cargarTablaAbonosPorPeriodo(fechaInicio, fechaFin);
        cargar_tablaDevolucionesPorPeriodo(fechaInicio, fechaFin);
        cargar_tablaEntradasSalidasPorFechas(fechaInicio, fechaFin);
    }//GEN-LAST:event_btn_corteActionPerformed

    private void btn_mostrarMovimientosInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mostrarMovimientosInActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_mostrarMovimientosInActionPerformed

    private void btn_agregarCategoria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarCategoria1ActionPerformed
        // TODO add your handling code here:
        text_categoria.setText("");
        btn_modificarCat.setEnabled(false);
        btn_eliminarCat.setEnabled(false);
    }//GEN-LAST:event_btn_agregarCategoria1ActionPerformed

    private void btn_cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrar1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_cerrar1ActionPerformed

    private void btn_nvoUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nvoUsActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_us.getLayout();
        cl.show(cont_us, "ag_usuario");
    }//GEN-LAST:event_btn_nvoUsActionPerformed

    private void btn_adUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adUsActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_us.getLayout();
        cl.show(cont_us, "ad_usuario");
    }//GEN-LAST:event_btn_adUsActionPerformed

    private void btn_guardarUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarUsActionPerformed
        // TODO add your handling code here:
         Usuario usuario = new Usuario();
        CtrlUsuario ctUsuario = new CtrlUsuario();

        String nombre = text_usuario.getText().trim();
        String password= text_password.getText().trim();
        if (nombre.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Validar si el usuario ya existe
    if (ctUsuario.usuarioExiste(nombre)) {
        JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
        usuario.setNombre_usuario(nombre);
        usuario.setPassword_usuario(password);

        if (ctUsuario.agregar(usuario)) {
            JOptionPane.showMessageDialog(null, "Registro guardado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            text_usuario.setText("");
            text_password.setText("");
            // Cierra el JDialog que contiene este panel
            ((JDialog) SwingUtilities.getWindowAncestor(this)).dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
            
    }//GEN-LAST:event_btn_guardarUsActionPerformed

    private void btn_modificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarUsuarioActionPerformed
        // TODO add your handling code here:
        if(text_usuarioAd.getText().isEmpty()||text_passwordAd.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
        }else{
             
            Usuario usuario=new Usuario();
            CtrlUsuario ctUsuario=new CtrlUsuario();
            usuario.setId_usuario(id_usuario);
            usuario.setNombre_usuario(text_usuarioAd.getText().trim());
            usuario.setPassword_usuario(text_passwordAd.getText().trim());
            
            if(ctUsuario.actualizar(usuario)){
                JOptionPane.showMessageDialog(null, "Usuario actualizado","Éxito",JOptionPane.INFORMATION_MESSAGE);
                cargarTablaUsuarios();
                text_usuarioAd.setText("");
                text_passwordAd.setText("");
            }else{
                 JOptionPane.showMessageDialog(null, "Fallo la actualizacion de usuario","Error",JOptionPane.ERROR_MESSAGE);
            }
        
        }
    }//GEN-LAST:event_btn_modificarUsuarioActionPerformed

    private void btn_edCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edCuentaActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) cont_ctes.getLayout();
        cl.show(cont_ctes, "estadoCuenta");
    }//GEN-LAST:event_btn_edCuentaActionPerformed

    private void btn_eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarUsuarioActionPerformed
        // TODO add your handling code here:
        CtrlUsuario ctUsuario=new CtrlUsuario();
        if(is_selected){
            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este usuario seleccionado?",   "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if(opcion==JOptionPane.YES_OPTION){
                if(ctUsuario.eliminar(this.id_usuario)){
                    JOptionPane.showMessageDialog(null, "El usuario seleccionado se eliminó correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
                    cargarTablaUsuarios();
                    text_usuarioAd.setText("");
                    text_passwordAd.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, "No es posible eliminar un usuario que ya realizó transacciones, ya que se perderian.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione el usuario que desea eliminar","Advertencia",JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_btn_eliminarUsuarioActionPerformed
    public void codigoProducto() {
        String codigoProducto = txt_codigo.getText().trim();

        if (codigoProducto.isEmpty()) {
            return; // No hacer nada si el código está vacío
        }

        Productos producto = buscarProductoPorCodigo(codigoProducto);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Información", JOptionPane.INFORMATION_MESSAGE);
            txt_codigo.setText("");
            return; // No hacer nada si el producto no existe
        }

        double cantidad = 1.00;
        double precioUnitario = producto.getPrecio_venta();

        // Buscar si el producto ya existe en la lista de ventas
        Ventas ventaExistente = null;
        for (Ventas venta : listaProducto) {
            if (venta.getId_producto() == producto.getIdProducto()) {
                ventaExistente = venta;
                break;
            }
        }

        if (ventaExistente != null) {
            // Si el producto ya existe, aumentar la cantidad y actualizar el importe
            double nuevaCantidad = ventaExistente.getCantidad() + cantidad;
            if (nuevaCantidad > producto.getExistencias()) {
                JOptionPane.showMessageDialog(null, "La cantidad supera el stock", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txt_codigo.setText("");
                return; // No agregar el producto si la cantidad supera el stock
            }
            double nuevoImporte = nuevaCantidad * precioUnitario;
            ventaExistente.setCantidad(nuevaCantidad);
            ventaExistente.setImporte(nuevoImporte); // Corregido: se añadió el paréntesis de cierre
        } else {
            // Si el producto no existe, verificar si hay suficiente stock antes de agregar
            if (cantidad > producto.getExistencias()) {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock para agregar este producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txt_codigo.setText("");
                return; // No agregar el producto si no hay suficiente stock
            }

            // Agregar el nuevo producto a la lista de ventas
            double importe1 = precioUnitario * cantidad;
            double importeTotal = importe1;
            Ventas venta = new Ventas(auxdet, 1, producto.getIdProducto(), producto.getDescripcion(), cantidad, precioUnitario, importe1, importeTotal);
            listaProducto.add(venta);
            auxdet++;
        }

        txt_codigo.setText("");
        listaTablaProductos(); // Actualizar la tabla de ventas
        actualizarCantidadProductos();
        totalVenta();
    }

    public void agregarProductoAVenta(Productos producto) {
        double cantidad = 1.00;
        double precioUnitario = producto.getPrecio_venta();

        // Buscar si el producto ya existe en la lista de ventas
        Ventas ventaExistente = null;
        for (Ventas venta : listaProducto) {
            if (venta.getId_producto() == producto.getIdProducto()) {
                ventaExistente = venta;
                break;
            }
        }

        if (ventaExistente != null) {
            // Si el producto ya existe, aumentar la cantidad y actualizar el importe
            double nuevaCantidad = ventaExistente.getCantidad() + cantidad;
            if (nuevaCantidad > producto.getExistencias()) {
                JOptionPane.showMessageDialog(null, "La cantidad supera el stock", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // No agregar el producto si la cantidad supera el stock
            }
            double nuevoImporte = nuevaCantidad * precioUnitario;
            ventaExistente.setCantidad(nuevaCantidad);
            ventaExistente.setImporte(nuevoImporte);
        } else {
            // Si el producto no existe, agregarlo a la lista de ventas
            double importe1 = precioUnitario * cantidad;
            double importeTotal = importe1;
            Ventas venta = new Ventas(auxdet, 1, producto.getIdProducto(), producto.getDescripcion(), cantidad, precioUnitario, importe1, importeTotal);
            listaProducto.add(venta);
            auxdet++;
        }

        listaTablaProductos(); // Actualizar la tabla de ventas
        actualizarCantidadProductos();
        totalVenta();
    }

    public void modificarCantidadProducto(int idProducto, double nuevaCantidad, int row) {
        Productos producto = buscarProductoPorID(idProducto);

        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar en la lista de ventas
        Ventas ventaExistente = null;
        for (Ventas venta : listaProducto) {
            if (venta.getId_producto() == idProducto) {
                ventaExistente = venta;
                break;
            }
        }

        if (ventaExistente != null) {
            double stockDisponible = producto.getExistencias();
            double cantidadAnterior = ventaExistente.getCantidad(); // GUARDAMOS PRIMERO

            // Validar contra el stock
            if (nuevaCantidad > stockDisponible) {
                JOptionPane.showMessageDialog(null, "La cantidad supera el stock disponible", "Advertencia", JOptionPane.WARNING_MESSAGE);
                modeloVentas.setValueAt(cantidadAnterior, row, 0); // Restaurar valor real
                return;
            }

            // ✅ Solo si es válida se guarda
            ventaExistente.setCantidad(nuevaCantidad);
            double nuevoImporte = nuevaCantidad * ventaExistente.getPrecio_unitario();
            ventaExistente.setImporte(nuevoImporte);
            String importeFormateado = String.format("%.2f", nuevoImporte);
            modeloVentas.setValueAt(importeFormateado, row, 3);

            actualizarCantidadProductos();
            totalVenta();
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado en la lista de ventas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatIntelliJLaf.setup();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ag_invent;
    private javax.swing.JButton btn_Cat;
    private javax.swing.JButton btn_abonar;
    private javax.swing.JButton btn_acEst;
    private javax.swing.JButton btn_adCte;
    private javax.swing.JButton btn_adProd;
    private javax.swing.JButton btn_adUs;
    private javax.swing.JButton btn_agregarCategoria;
    private javax.swing.JButton btn_agregarCategoria1;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_cerrar1;
    private javax.swing.JButton btn_cobrar;
    private javax.swing.JButton btn_corte;
    private javax.swing.JButton btn_cte;
    private javax.swing.JButton btn_ctes;
    private javax.swing.JButton btn_detalleAbono;
    private javax.swing.JButton btn_devol;
    private javax.swing.JButton btn_edCuenta;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_eliminarCat;
    private javax.swing.JButton btn_eliminarCte;
    private javax.swing.JButton btn_eliminarPto;
    private javax.swing.JButton btn_eliminarUsuario;
    private javax.swing.JButton btn_entradas;
    private javax.swing.JButton btn_guardarCte;
    private javax.swing.JButton btn_guardarProducto;
    private javax.swing.JButton btn_guardarProducto1;
    private javax.swing.JButton btn_guardarUs;
    private javax.swing.JButton btn_histDe;
    private javax.swing.JButton btn_inv;
    private javax.swing.JButton btn_modificarCat;
    private javax.swing.JButton btn_modificarCte;
    private javax.swing.JButton btn_modificarPto;
    private javax.swing.JButton btn_modificarUsuario;
    private javax.swing.JButton btn_mostrarMovimientosIn;
    private javax.swing.JButton btn_nvoCte;
    private javax.swing.JButton btn_nvoProd;
    private javax.swing.JButton btn_nvoUs;
    private javax.swing.JButton btn_prodBaj;
    private javax.swing.JButton btn_producto;
    private javax.swing.JButton btn_repInv;
    private javax.swing.JButton btn_repMov;
    private javax.swing.JButton btn_usuarios;
    private javax.swing.JButton btn_ventasPeriodo;
    private javax.swing.JButton btn_vtas;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cmb_catProdAct;
    private javax.swing.JComboBox<String> cmb_catProducto;
    private javax.swing.JComboBox<String> cmb_catRep;
    private javax.swing.JPanel cont_corte;
    private javax.swing.JPanel cont_ctes;
    private javax.swing.JPanel cont_inv;
    private javax.swing.JPanel cont_prod;
    private javax.swing.JPanel cont_us;
    private javax.swing.JPanel cont_ventas;
    private javax.swing.JPanel contenedorPrincipal;
    private com.toedter.calendar.JDateChooser fecha_finCorte;
    private com.toedter.calendar.JDateChooser fecha_inicioCorte;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooserFinal;
    private com.toedter.calendar.JDateChooser jDateChooserFinal1;
    private com.toedter.calendar.JDateChooser jDateChooserInicio;
    private com.toedter.calendar.JDateChooser jDateChooserInicio1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparatorPro;
    private javax.swing.JLabel lbl_abonos;
    private javax.swing.JLabel lbl_cantProd;
    public static javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_costInv;
    private javax.swing.JLabel lbl_ctp;
    private javax.swing.JLabel lbl_descIn;
    private javax.swing.JLabel lbl_deudaPend;
    private javax.swing.JLabel lbl_devoluciones;
    private javax.swing.JLabel lbl_devoluciones2;
    private javax.swing.JLabel lbl_efectivoInicial;
    private javax.swing.JLabel lbl_entradaEfectivo;
    private javax.swing.JLabel lbl_exI;
    private javax.swing.JLabel lbl_nombre_cta;
    private javax.swing.JLabel lbl_salidaEfectivo;
    private javax.swing.JLabel lbl_toVentas;
    private javax.swing.JLabel lbl_totAb;
    private javax.swing.JLabel lbl_totDeu;
    private javax.swing.JLabel lbl_totDeudas;
    private javax.swing.JLabel lbl_totalDineroCaja;
    private javax.swing.JLabel lbl_totalVendido;
    public static javax.swing.JLabel lbl_totalVenta;
    private javax.swing.JLabel lbl_ventaContado;
    private javax.swing.JLabel lbl_ventaContado2;
    private javax.swing.JLabel lbl_ventaFiado;
    private javax.swing.JLabel lbl_vtaTotal;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel pnl_HistDe;
    private javax.swing.JPanel pnl_adCliente;
    private javax.swing.JPanel pnl_adProducto;
    private javax.swing.JPanel pnl_adUsuario;
    private javax.swing.JPanel pnl_agCliente;
    private javax.swing.JPanel pnl_agInv;
    private javax.swing.JPanel pnl_agProducto;
    private javax.swing.JPanel pnl_agUsuario;
    private javax.swing.JPanel pnl_barraClientes;
    private javax.swing.JPanel pnl_barraInventario;
    private javax.swing.JPanel pnl_barraProductos;
    private javax.swing.JPanel pnl_barraUsuarios;
    private javax.swing.JPanel pnl_barraVentas;
    private javax.swing.JPanel pnl_categorias;
    private javax.swing.JPanel pnl_clientes;
    private javax.swing.JPanel pnl_contEstCta;
    private javax.swing.JPanel pnl_corte;
    private javax.swing.JPanel pnl_estCuenta;
    private javax.swing.JPanel pnl_infCorte;
    private javax.swing.JPanel pnl_inventario;
    private javax.swing.JPanel pnl_prBaj;
    private javax.swing.JPanel pnl_productos;
    private javax.swing.JPanel pnl_repInv;
    private javax.swing.JPanel pnl_repMov;
    private javax.swing.JPanel pnl_usuarios;
    public static javax.swing.JPanel pnl_ventas;
    private javax.swing.JPanel pnl_vta;
    private javax.swing.JPanel pnl_vtaPeriodo;
    private javax.swing.JRadioButton rbtn_gnelAct;
    private javax.swing.JRadioButton rbtn_granel;
    private javax.swing.JRadioButton rbtn_paqteAct;
    private javax.swing.JRadioButton rbtn_pqte;
    private javax.swing.JRadioButton rbtn_pza;
    private javax.swing.JRadioButton rbtn_pzaAct;
    public static javax.swing.JScrollPane scp_abonos;
    private javax.swing.JScrollPane scp_adProducto;
    private javax.swing.JScrollPane scp_adUsuario;
    private javax.swing.JScrollPane scp_adcliente;
    private javax.swing.JScrollPane scp_categoria;
    public static javax.swing.JScrollPane scp_devoluciones;
    public static javax.swing.JScrollPane scp_ent;
    private javax.swing.JScrollPane scp_estCta;
    private javax.swing.JScrollPane scp_estCtaCte;
    private javax.swing.JScrollPane scp_histDe;
    private javax.swing.JScrollPane scp_prodBaj;
    private javax.swing.JScrollPane scp_repInv;
    private javax.swing.JScrollPane scp_repMov;
    private javax.swing.JScrollPane scp_ventas;
    private javax.swing.JScrollPane scp_ventasPeriodo;
    private javax.swing.JSpinner sp_cantInv;
    private javax.swing.JSpinner sp_cost;
    private javax.swing.JSpinner sp_cost1;
    private javax.swing.JSpinner sp_exc;
    private javax.swing.JSpinner sp_exc1;
    private javax.swing.JSpinner sp_vent;
    private javax.swing.JSpinner sp_vent1;
    public static javax.swing.JTable tb_abonos;
    private javax.swing.JTable tb_adProducto;
    private javax.swing.JTable tb_adUsuario;
    private javax.swing.JTable tb_adcliente;
    private javax.swing.JTable tb_categoria;
    public static javax.swing.JTable tb_devoluciones;
    public static javax.swing.JTable tb_ent;
    private javax.swing.JTable tb_estCta;
    private javax.swing.JTable tb_estCtaCte;
    private javax.swing.JTable tb_histDe;
    private javax.swing.JTable tb_prodBaj;
    private javax.swing.JTable tb_repInv;
    private javax.swing.JTable tb_repMov;
    public static javax.swing.JTable tb_ventas;
    private javax.swing.JTable tb_ventasPeriodo;
    private javax.swing.JTextField text_apellidos;
    private javax.swing.JTextField text_categoria;
    private javax.swing.JTextField text_codigoBarras;
    private javax.swing.JTextField text_codigoBarrasAct;
    private javax.swing.JTextField text_codigoBarrasInv;
    private javax.swing.JTextField text_descProd;
    private javax.swing.JTextField text_descProdAct;
    private javax.swing.JTextField text_nombre;
    private javax.swing.JPasswordField text_password;
    private javax.swing.JPasswordField text_passwordAd;
    private javax.swing.JTextField text_telefono;
    private javax.swing.JTextField text_usuario;
    private javax.swing.JTextField text_usuarioAd;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_buscarCliente;
    private javax.swing.JTextField txt_buscarCliente1;
    private javax.swing.JTextField txt_buscarClienteEst;
    private javax.swing.JTextField txt_buscarProducto;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
    private void cargarInformacionCliente(int idCliente) {
        try {
            Connection cn = Conexion.Conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM clientes WHERE id_cliente='" + idCliente + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txt_nombre.setText(rs.getString("nombre_cliente"));
                txt_apellidos.setText(rs.getString("apellidos_cliente"));
                txt_telefono.setText(rs.getString("telefono_cliente"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar cliente");
        }
    }

    private void limpiarClientes() {
        txt_nombre.setText("");
        txt_apellidos.setText("");
        txt_telefono.setText("");

    }

    public void cargarTablaClientes() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT * FROM clientes";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            while (modeloClientes.getRowCount() > 0) {
                modeloClientes.removeRow(0);
            }
            scp_adcliente.setViewportView(tb_adcliente);
            while (rs.next()) {
                Object fila[] = new Object[4];
                for (byte i = 0; i < 4; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloClientes.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Fallo la consulta de clientes: " + e);
        }
        is_selected = false;
    }
   public void cargarTablaUsuarios() {
    Connection cn = Conexion.Conectar();
    String query = "SELECT * FROM usuarios";
    try (PreparedStatement st = cn.prepareStatement(query)) {
        ResultSet rs = st.executeQuery();

        // Limpiar la tabla antes de cargar nuevos datos
        while (modeloUsuarios.getRowCount() > 0) {
            modeloUsuarios.removeRow(0);
        }

        scp_adUsuario.setViewportView(tb_adUsuario);

        while (rs.next()) {
            Object fila[] = new Object[3];
            fila[0] = rs.getInt("id_usuario");
            fila[1] = rs.getString("nombre_usuario");

            // Ocultar la contraseña real con asteriscos
            String password = rs.getString("password_usuario");
            fila[2] = "*".repeat(password.length()); // O usa "*****" si prefieres algo fijo

            modeloUsuarios.addRow(fila);
        }

        cn.close();
    } catch (SQLException e) {
        System.out.println("Fallo la consulta de usuarios: " + e);
    }

    is_selected = false;
}

    private void cargarInformacionUsuarios(int idUsuario)  {
        try {
            Connection cn = Conexion.Conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM usuarios WHERE id_usuario='" + idUsuario + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                text_usuarioAd.setText(rs.getString("nombre_usuario"));
                text_passwordAd.setText(rs.getString("password_usuario"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar usuario");
        }
    }

    public void cargarTablaEstadoCuenta() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT * FROM clientes";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            while (modeloEstadoCuenta.getRowCount() > 0) {
                modeloEstadoCuenta.removeRow(0);
            }
            scp_estCta.setViewportView(tb_estCta);
            while (rs.next()) {
                Object fila[] = new Object[4];
                for (byte i = 0; i < 4; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloEstadoCuenta.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Fallo la consulta de clientes: " + e);
        }
        is_selected = false;
    }

    public void cargarTablaEstadoCuentaFiado(int idClienteEstado) {
        Connection cn = Conexion.Conectar();
        String query = """
    SELECT 
        dv.fecha_venta,
        p.descripcion_producto,
        v.cantidad,
        v.precio_unitario,
        v.importe
    FROM deudas d
    JOIN detalle_deudas dd ON d.id_detalleDeuda = dd.id_detalleDeuda
    JOIN detalle_ventas dv ON d.id_detalleVenta = dv.id_detalleVenta
    JOIN ventas v ON v.id_detalleVenta = dv.id_detalleVenta
    JOIN productos p ON p.id_producto = v.id_producto
    WHERE d.id_cliente = ? 
      AND dv.tipo_venta = 'Fiado' 
      AND dd.deuda_pendiente > 0
    ORDER BY dv.fecha_venta DESC
""";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, idClienteEstado);
            ResultSet rs = st.executeQuery();

            // Limpiar el modelo antes de cargar nuevos datos
            modeloEstadoCuentaCte.setRowCount(0);

            // Eliminar filas existentes (por si acaso)
            while (modeloEstadoCuentaCte.getRowCount() > 0) {
                modeloEstadoCuentaCte.removeRow(0);
            }

            // Cargar los datos al modelo
            scp_estCtaCte.setViewportView(tb_estCtaCte); // Asegúrate de tener estos componentes declarados
            while (rs.next()) {
                Object fila[] = new Object[5]; // 5 columnas: fecha, descripción, precio, cantidad, importe
                Timestamp timestamp = rs.getTimestamp("fecha_venta");

                TimeZone timeZone = TimeZone.getDefault();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(timeZone);

                String fechaFormateada = sdf.format(timestamp);
                fila[0] = fechaFormateada;
                fila[1] = rs.getString("descripcion_producto");
                fila[2] = rs.getDouble("cantidad");
                fila[3] = rs.getDouble("precio_unitario");
                fila[4] = rs.getDouble("importe");
                modeloEstadoCuentaCte.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Falló la consulta del estado de cuenta fiado: " + e);
        }

        is_selected = false;
    }

    public void mostrarResumenDeudaCliente(int idCliente) {
        Connection cn = Conexion.Conectar();
        String query = """
        SELECT 
            dd.total_deuda,
            dd.total_abonado,
            dd.deuda_pendiente
        FROM detalle_deudas dd
        JOIN deudas d ON dd.id_detalleDeuda = d.id_detalleDeuda
        WHERE d.id_cliente = ?
          AND dd.deuda_pendiente > 0
        ORDER BY dd.deuda_pendiente DESC
        LIMIT 1
    """;

        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double totalDeuda = rs.getDouble("total_deuda");
                double totalAbonado = rs.getDouble("total_abonado");
                double deudaPendiente = rs.getDouble("deuda_pendiente");

                lbl_totDeu.setText(String.format("$ %.2f", totalDeuda));
                lbl_totAb.setText(String.format("$ %.2f", totalAbonado));
                lbl_deudaPend.setText(String.format("$ %.2f", deudaPendiente));
            } else {
                lbl_totDeu.setText("$ 0.00");
                lbl_totAb.setText("$ 0.00");
                lbl_deudaPend.setText("$ 0.00");
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("❌ Error al mostrar resumen de deuda: " + e);
        }
    }

    public void cargarHistorialDeudas() {
        Connection cn = Conexion.Conectar();
        String query = """
        SELECT c.nombre_cliente, c.apellidos_cliente, c.telefono_cliente, d.total_deuda, d.total_abonado, d.deuda_pendiente
        FROM clientes c
        JOIN detalle_deudas d ON c.id_cliente = d.id_cliente
        WHERE d.deuda_pendiente > 0
        ORDER BY c.nombre_cliente, c.apellidos_cliente, d.id_detalleDeuda ASC;
    """;

        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();

            // Limpiar el modelo antes de cargar nuevos datos
            ModeloHistorialDeudas.setRowCount(0);
            double totalPendiente = 0;
            // Recorrer los resultados y agregar a la tabla
            while (rs.next()) {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("nombre_cliente") + " " + rs.getString("apellidos_cliente");
                fila[1] = rs.getString("telefono_cliente"); // Teléfono del cliente
                fila[2] = String.format("%.2f", rs.getDouble("total_deuda"));
                fila[3] = String.format("%.2f", rs.getDouble("total_abonado"));
                double deudaPendiente = rs.getDouble("deuda_pendiente");
                fila[4] = String.format("%.2f", deudaPendiente);
                ModeloHistorialDeudas.addRow(fila); // Agregar la fila a la tabla
                totalPendiente += deudaPendiente;
            }
            // Mostrar el total general en el JLabel
            lbl_totDeudas.setText(String.format("%.2f", totalPendiente));

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar historial de deudas: " + e);
        }
    }

    public void cargar_tablaCategorias() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT id_categoria, nombre_categoria FROM categorias";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            //Elimina filas existentes
            while (modeloCategorias.getRowCount() > 0) {
                modeloCategorias.removeRow(0);
            }
            scp_categoria.setViewportView(tb_categoria);
            while (rs.next()) {
                Object fila[] = new Object[2];
                for (byte i = 0; i < 2; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloCategorias.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Falló la consulta de Categorias" + e);
        }
        is_selected = false;
    }

    private void cargarCategoriasActualizarProducto() {
        String sql = "SELECT nombre_categoria FROM categorias"; // Solo seleccionamos el nombre de la categoría
        try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            cmb_catProdAct.removeAllItems();
            cmb_catProdAct.addItem("Seleccione la categoria");

            // Llenar los JComboBox con las categorías
            while (rs.next()) {
                String nombreCategoria = rs.getString("nombre_categoria");
                cmb_catProdAct.addItem(nombreCategoria);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar categorias de actualizar producto: " + e.getMessage());
        }
    }

    private void cargarCategoriasGuardarProducto() {
        String sql = "SELECT nombre_categoria FROM categorias"; // Solo seleccionamos el nombre de la categoría
        try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            // Limpiar los JComboBox antes de llenarlos
            cmb_catProducto.removeAllItems();

            // Agregar elementos por defecto
            cmb_catProducto.addItem("Seleccione la categoria");

            // Llenar los JComboBox con las categorías
            while (rs.next()) {
                String nombreCategoria = rs.getString("nombre_categoria");
                cmb_catProducto.addItem(nombreCategoria);

            }
        } catch (SQLException e) {
            System.out.println("Error al cargar categorias de guardar producto: " + e.getMessage());
        }
    }

    private void cargarCategoriasReporteInventario() {
        String sql = "SELECT nombre_categoria FROM categorias";
        try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            cargandoCategorias = true; // Inicia bandera
            cmb_catRep.removeAllItems();
            cmb_catRep.addItem("- Todos -");
            while (rs.next()) {
                cmb_catRep.addItem(rs.getString("nombre_categoria"));
            }
            cargandoCategorias = false; // Fin de carga

        } catch (SQLException e) {
            cargandoCategorias = false; // Asegura que se apague si hay error
            System.out.println("Error al cargar categorias de reporte inventario: " + e.getMessage());
        }
    }

    private int idCategoria() {
        String sql = "SELECT * FROM categorias WHERE nombre_categoria = '" + this.cmb_catProducto.getSelectedItem() + "' OR nombre_categoria = '" + this.cmb_catProdAct.getSelectedItem() + "'";
        Statement st;
        try {
            Connection cn = Conexion.Conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                obtenerIdCategoria = rs.getInt("id_categoria");
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener id categoria");
        }
        return obtenerIdCategoria;
    }

    private void cargarInformacionProducto(int id_producto) {
        try {
            Connection cn = Conexion.Conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM productos WHERE id_producto='" + id_producto + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                int idCat = rs.getInt("id_categoria");
                cmb_catProdAct.setSelectedItem(relacionarCategorias(idCat));
                text_codigoBarrasAct.setText(rs.getString("codigo_producto"));
                text_descProdAct.setText(rs.getString("descripcion_producto"));
                // Obtener el valor de la unidad y seleccionar el botón de radio correspondiente
                String unidad = rs.getString("unidad_producto");
                if (unidad.equals("Pieza")) {
                    rbtn_pzaAct.setSelected(true);
                } else if (unidad.equals("Granel")) {
                    rbtn_gnelAct.setSelected(true);
                } else if (unidad.equals("Paquete")) {
                    rbtn_paqteAct.setSelected(true);
                }
                // Asignar valores a los spinners después de convertirlos
                sp_cost1.setValue(Double.parseDouble(rs.getString("precio_costo")));
                sp_vent1.setValue(Double.parseDouble(rs.getString("precio_venta")));
                sp_exc1.setValue(Double.parseDouble(rs.getString("existencias_producto")));
            }
            cn.close();
        } catch (SQLException ex) {
            System.out.println("Error al seleccionar producto");
        }

    }
    String categoria = "";

    private void cargarInformacionProductoInventario(String codigoBarras) {
        try {
            Connection cn = Conexion.Conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT descripcion_producto, existencias_producto FROM productos WHERE codigo_producto=?");
            pst.setString(1, codigoBarras);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lbl_descIn.setText(rs.getString("descripcion_producto"));
                lbl_exI.setText(String.format("%.2f", rs.getDouble("existencias_producto")));

            } else {
                text_codigoBarrasInv.setText("");
                lbl_descIn.setText("");
                lbl_exI.setText("");
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }

            cn.close();
        } catch (SQLException ex) {
            System.out.println("Error al seleccionar producto: " + ex);
        }
    }

    private String relacionarCategorias(int idCategoria) {
        String sql = "SELECT nombre_categoria FROM categorias WHERE id_categoria='" + idCategoria + "'";
        Statement st;
        try {
            Connection cn = Conexion.Conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                categoria = rs.getString("nombre_categoria");
            }
            st.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener id categorias");
        }
        return categoria;
    }

    public void cargar_tablaProductos() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT id_producto,categorias.nombre_categoria,codigo_producto,descripcion_producto,unidad_producto,precio_costo,precio_venta,existencias_producto FROM productos,categorias WHERE productos.id_categoria=categorias.id_categoria;";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            // Limpiar el modelo antes de cargar nuevos datos
            modeloProductos.setRowCount(0); // Esto elimina todas las filas del modelo

            //Elimina filas existentes
            while (modeloProductos.getRowCount() > 0) {
                modeloProductos.removeRow(0);
            }
            scp_adProducto.setViewportView(tb_adProducto);
            while (rs.next()) {
                Object fila[] = new Object[8];
                for (byte i = 0; i < 8; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloProductos.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Falló la consulta de productos" + e);
        }
        is_selected = false;
    }

    public void cargar_tablaMovimientos() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT m.fecha, p.descripcion_producto, m.tipo_movimiento, m.cantidad, "
                + "m.cantidad_anterior, m.cantidad_actual, u.nombre_usuario "
                + "FROM movimientos_inventario m "
                + "JOIN productos p ON m.id_producto = p.id_producto "
                + "JOIN usuarios u ON m.id_usuario = u.id_usuario";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();

            // Limpiar tabla si ya tiene datos
            while (modeloReporteMovimientos.getRowCount() > 0) {
                modeloReporteMovimientos.removeRow(0);
            }

            scp_repMov.setViewportView(tb_repMov);

            while (rs.next()) {
                Object fila[] = new Object[7];

                // Formatear fecha
                Timestamp timestamp = rs.getTimestamp("fecha");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(TimeZone.getDefault());
                String fechaFormateada = sdf.format(timestamp);

                fila[0] = fechaFormateada;
                fila[1] = rs.getString("descripcion_producto");
                fila[2] = rs.getString("tipo_movimiento");
                fila[3] = rs.getInt("cantidad");
                fila[4] = rs.getInt("cantidad_anterior");
                fila[5] = rs.getInt("cantidad_actual");
                fila[6] = rs.getString("nombre_usuario");

                modeloReporteMovimientos.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar movimientos de inventario: " + e);
        }

        is_selected = false;
    }

    public void cargar_tablaProductosBajoInv() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT codigo_producto, descripcion_producto, precio_venta, existencias_producto, categorias.nombre_categoria "
                + "FROM productos, categorias "
                + "WHERE productos.id_categoria = categorias.id_categoria "
                + "AND existencias_producto <= 3;";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            // Limpiar el modelo antes de cargar nuevos datos
            modeloProductosBajoInv.setRowCount(0); // Esto elimina todas las filas del modelo

            //Elimina filas existentes
            while (modeloProductosBajoInv.getRowCount() > 0) {
                modeloProductosBajoInv.removeRow(0);
            }
            scp_prodBaj.setViewportView(tb_prodBaj);
            while (rs.next()) {
                Object fila[] = new Object[5];
                for (byte i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloProductosBajoInv.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Falló la consulta de productos" + e);
        }
        is_selected = false;
    }

    public void cargar_tablaProductosInventario() {
        Connection cn = Conexion.Conectar();
        String query = "SELECT p.codigo_producto, p.descripcion_producto, p.precio_costo, p.precio_venta, p.existencias_producto FROM productos p";
        try (PreparedStatement st = cn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            // Limpiar el modelo antes de cargar nuevos datos
            modeloReporteInventario.setRowCount(0); // Esto elimina todas las filas del modelo

            //Elimina filas existentes
            while (modeloReporteInventario.getRowCount() > 0) {
                modeloReporteInventario.removeRow(0);
            }
            scp_repInv.setViewportView(tb_repInv);
            while (rs.next()) {
                Object fila[] = new Object[5];
                for (byte i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloReporteInventario.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Falló la consulta de productos" + e);
        }
        is_selected = false;
    }

    private void filtrarProductosPorCategoria() {
        Object selectedItem = cmb_catRep.getSelectedItem();

        if (selectedItem == null) {
            return; // Evita el error si aún no hay selección
        }

        String categoriaSeleccionada = selectedItem.toString();

        DefaultTableModel modelo = (DefaultTableModel) tb_repInv.getModel();
        modelo.setRowCount(0);

        String sql;
        if (categoriaSeleccionada.equals("- Todos -")) {
            sql = "SELECT p.codigo_producto, p.descripcion_producto, p.precio_costo, p.precio_venta, p.existencias_producto, c.nombre_categoria "
                    + "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria";
        } else {
            sql = "SELECT p.codigo_producto, p.descripcion_producto, p.precio_costo, p.precio_venta, p.existencias_producto, c.nombre_categoria "
                    + "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                    + "WHERE c.nombre_categoria = ?";
        }

        try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {

            if (!categoriaSeleccionada.equals("- Todos -")) {
                pst.setString(1, categoriaSeleccionada);
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("codigo_producto"),
                    rs.getString("descripcion_producto"),
                    rs.getDouble("precio_costo"),
                    rs.getDouble("precio_venta"),
                    rs.getDouble("existencias_producto")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar productos.");
        }
    }

    public void listaTablaProductos() {
        // Limpiar el modelo de la tabla
        modeloVentas.setRowCount(0);

        // Iterar sobre la lista de productos y agregarlos al modelo de la tabla
        for (Ventas producto : listaProducto) {
            Object[] fila = new Object[4];
            fila[0] = producto.getCantidad();
            fila[1] = producto.getDescripcion();
            fila[2] = producto.getPrecio_unitario();
            fila[3] = producto.getImporte();

            modeloVentas.addRow(fila);
        }
    }

    public Productos buscarProductoPorCodigo(String codigoProducto) {

        Productos producto = null;
        String sql = "SELECT id_producto, categorias.nombre_categoria, codigo_producto, descripcion_producto, unidad_producto, precio_costo, precio_venta, existencias_producto "
                + "FROM productos, categorias "
                + "WHERE productos.id_categoria = categorias.id_categoria "
                + "AND codigo_producto = ?";

        try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, codigoProducto);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    producto = new Productos();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setDescripcion(rs.getString("descripcion_producto"));
                    producto.setPrecio_venta(rs.getDouble("precio_venta"));
                    producto.setExistencias(rs.getDouble("existencias_producto"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    private void actualizarCantidadProductos() {
        int totalCantidad = 0;

        for (Ventas venta : listaProducto) {
            // Obtener el producto correspondiente a la venta
            Productos producto = buscarProductoPorID(venta.getId_producto());

            if (producto != null) {
                // Si el producto es de tipo "Granel", contar solo 1 sin importar la cantidad
                if ("Granel".equalsIgnoreCase(producto.getUnidad())) {
                    totalCantidad += 1;  // Siempre contar como 1 producto sin importar la cantidad vendida
                } else {
                    totalCantidad += (int) venta.getCantidad();  // Para productos por pieza, sumar normalmente
                }
            }
        }

        lbl_cantidad.setText("" + totalCantidad);  // Actualizar la cantidad total en la interfaz
    }

    private void totalVenta() {
        double total = 0.00;
        for (Ventas venta : listaProducto) {
            total += venta.getImporte();
        }

        // FORMATEAR A DOS DECIMALES
        String totalFormateado = String.format("%.2f", total);
        lbl_totalVenta.setText("$" + totalFormateado);
    }

    public void totalExistencias() {
        Connection cn = Conexion.Conectar(); // Asegúrate de que esta función esté bien definida
        String query = "SELECT SUM(existencias_producto) AS total_existencias FROM productos;";

        try (PreparedStatement st = cn.prepareStatement(query); ResultSet rs = st.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total_existencias"); // Obtener el total de existencias
                lbl_cantProd.setText(" " + total); // Actualiza la etiqueta
            } else {
                lbl_cantProd.setText("0"); // En caso de que no haya productos
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el total de existencias ");
        } finally {
            try {
                if (cn != null) {
                    cn.close(); // Cerrar la conexión
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e);
            }
        }
    }

    public void totalSaldoInventario() {
    Connection cn = Conexion.Conectar();
    String query = "SELECT SUM(precio_costo * existencias_producto) AS total_precio_costo FROM productos;";

    try (PreparedStatement st = cn.prepareStatement(query); ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
            double total = rs.getDouble("total_precio_costo");
            lbl_costInv.setText("$" + String.format("%.2f", total));
        } else {
            lbl_costInv.setText("$0.00");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener el total de inventario");
    } finally {
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e);
        }
    }
}


    public void eliminarProducto(int fila_seleccionada) {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este producto seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            // Eliminar el artículo de la lista según la fila seleccionada
            listaProducto.remove(fila_seleccionada);
            // Mostrar un mensaje de éxito si la eliminación fue exitosa.
            JOptionPane.showMessageDialog(null, "El producto seleccionado se eliminó correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
            // Recargar la tabla con la información actualizada.
            this.listaTablaProductos();
            actualizarCantidadProductos();
            totalVenta();
        }
    }

    public Productos buscarProductoPorID(int idProducto) {
        Productos producto = null;
        String sql = "SELECT id_producto, categorias.nombre_categoria, codigo_producto, descripcion_producto, unidad_producto, precio_costo, precio_venta, existencias_producto "
                + "FROM productos, categorias "
                + "WHERE productos.id_categoria = categorias.id_categoria "
                + "AND id_producto = ?"; // Usamos el idProducto en lugar de codigo_producto

        try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idProducto); // Establecer el idProducto como parámetro en la consulta
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    producto = new Productos();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setDescripcion(rs.getString("descripcion_producto"));
                    producto.setUnidad(rs.getString("unidad_producto"));
                    producto.setPrecio_venta(rs.getDouble("precio_venta"));
                    producto.setExistencias(rs.getDouble("existencias_producto"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    public void limpiarVenta() {
        // 1. Borrar todas las filas de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tb_ventas.getModel();
        modelo.setRowCount(0); // Esto borra todas las filas
        lbl_totalVenta.setText("$0.00"); // Reinicia el total (ajusta el nombre del label si es diferente)
        lbl_cantidad.setText("0 "); // Si tienes un contador de productos
        listaProducto.clear();
    }

    public void rqFocus() {
        txt_codigo.setText("");
    }

    public void cargar_tablaVentasPorPeriodo(Date fechaInicio, Date fechaFin) {
        Connection cn = Conexion.Conectar();
        String query = "SELECT dv.fecha_venta, p.descripcion_producto, v.cantidad, v.precio_unitario, "
                + "v.importe, u.nombre_usuario "
                + "FROM ventas v "
                + "JOIN productos p ON v.id_producto = p.id_producto "
                + "JOIN detalle_ventas dv ON v.id_detalleVenta = dv.id_detalleVenta "
                + "JOIN usuarios u ON dv.id_usuario = u.id_usuario "
                + "WHERE dv.fecha_venta BETWEEN ? AND ?";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            Timestamp inicioTimestamp = new Timestamp(fechaInicio.getTime());
            Timestamp finTimestamp = new Timestamp(fechaFin.getTime());

            st.setTimestamp(1, inicioTimestamp);
            st.setTimestamp(2, finTimestamp);

            ResultSet rs = st.executeQuery();

            // Limpiar tabla
            while (ModeloVentasPeriodo.getRowCount() > 0) {
                ModeloVentasPeriodo.removeRow(0);
            }

            scp_ventasPeriodo.setViewportView(tb_ventasPeriodo);

            double totalImporte = 0;

            while (rs.next()) {
                Object fila[] = new Object[6];

                Timestamp timestamp = rs.getTimestamp("fecha_venta");
                TimeZone timeZone = TimeZone.getDefault();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(timeZone);

                String fechaFormateada = sdf.format(timestamp);

                fila[0] = fechaFormateada;
                fila[1] = rs.getString("descripcion_producto");
                fila[2] = rs.getDouble("cantidad");
                fila[3] = String.format("%.2f", rs.getDouble("precio_unitario"));

                double importeT = rs.getDouble("importe");
                fila[4] = String.format("%.2f", importeT);
                fila[5] = rs.getString("nombre_usuario");

                ModeloVentasPeriodo.addRow(fila);
                totalImporte += importeT;
            }

            // Mostrar total en JLabel
            lbl_totalVendido.setText("$" + String.format("%.2f", totalImporte));
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar ventas por periodo: " + e);
        }

        is_selected = false;
    }

    public void cargarResumenCorte(Date fechaInicio, Date fechaFin) {
    try (Connection cn = Conexion.Conectar()) {
        Timestamp inicio = new Timestamp(fechaInicio.getTime());
        Timestamp fin = new Timestamp(fechaFin.getTime());

        // 1. Efectivo inicial
        java.sql.Date fechaSolo = new java.sql.Date(fechaFin.getTime());
        double efectivoInicial = 0.00;
        String sqlEfectivo = "SELECT monto FROM efectivo_inicial WHERE fecha = ? ORDER BY id DESC LIMIT 1;";
        try (PreparedStatement stmt = cn.prepareStatement(sqlEfectivo)) {
            stmt.setDate(1, fechaSolo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    efectivoInicial = rs.getDouble("monto");
                    lbl_efectivoInicial.setText("$" + String.format("%.2f", efectivoInicial));
                }
            }
        }

        // 2. Ventas contado
        double ventasContado = obtenerSuma(cn,
            "SELECT SUM(importe_total) FROM detalle_ventas WHERE tipo_venta = 'Contado' AND fecha_venta BETWEEN ? AND ?",
            inicio, fin);

        // 3. Ventas fiadas
        double ventasFiadas = obtenerSuma(cn,
            "SELECT SUM(importe_total) FROM detalle_ventas WHERE tipo_venta = 'Fiado' AND fecha_venta BETWEEN ? AND ?",
            inicio, fin);

        // 4. Abonos
        double abonos = obtenerSuma(cn,
            "SELECT SUM(monto_abono) FROM abonos WHERE fecha_abono BETWEEN ? AND ?",
            inicio, fin);

        // 5. Entradas
        double entradas = obtenerSuma(cn,
            "SELECT SUM(monto) FROM entradas_salidas WHERE tipo = 'ENTRADA' AND fecha BETWEEN ? AND ?",
            inicio, fin);

        // 6. Salidas
        double salidas = obtenerSuma(cn,
            "SELECT SUM(monto) FROM entradas_salidas WHERE tipo = 'SALIDA' AND fecha BETWEEN ? AND ?",
            inicio, fin);

        // 7. Devoluciones
        double devoluciones = obtenerSuma(cn,
            "SELECT SUM(mi.cantidad * p.precio_venta) FROM movimientos_inventario mi JOIN productos p ON mi.id_producto = p.id_producto WHERE mi.tipo_movimiento = 'DEVOLUCIÓN' AND mi.fecha BETWEEN ? AND ?",
            inicio, fin);

        // Totales
        double totalVendido = ventasContado + ventasFiadas;
        double totalCorte = efectivoInicial + ventasContado + abonos + entradas;
        double totalCajaReal = totalCorte - salidas - devoluciones;
        // Mostrar resultados
        lbl_vtaTotal.setText("$" + String.format("%.2f", totalVendido));
        lbl_toVentas.setText("$" + String.format("%.2f", totalVendido));
        lbl_totalDineroCaja.setText("$" + String.format("%.2f", totalCajaReal));
        lbl_ventaContado.setText(formatearMonto(ventasContado));
        lbl_ventaContado2.setText(formatearMonto(ventasContado));
        lbl_ventaFiado.setText(formatearMonto(ventasFiadas));
        lbl_abonos.setText(formatearMonto(abonos));
        lbl_entradaEfectivo.setText(formatearMonto(entradas));
        lbl_devoluciones.setText("-$" + String.format("%.2f", devoluciones));
        lbl_devoluciones2.setText("-$" + String.format("%.2f", devoluciones));
        lbl_salidaEfectivo.setText("-$" + String.format("%.2f", salidas));

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar resumen financiero.");
    }
}





    private double obtenerSuma(Connection cn, String sql, Timestamp inicio, Timestamp fin) throws SQLException {
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setTimestamp(1, inicio);
            pst.setTimestamp(2, fin);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    private String formatearMonto(double monto) {
        return "$" + String.format("%.2f", monto);
    }

    public void cargar_tablaEntradasSalidasPorFechas(Date fechaInicio, Date fechaFin) {
        Connection cn = Conexion.Conectar();
        String query = "SELECT id_entrada_salida, fecha, tipo, monto, descripcion, usuarios.nombre_usuario "
                + "FROM entradas_salidas, usuarios "
                + "WHERE entradas_salidas.id_usuario = usuarios.id_usuario "
                + "AND fecha BETWEEN ? AND ?";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            // Convertir fechas de java.util.Date a java.sql.Timestamp
            Timestamp inicioTimestamp = new Timestamp(fechaInicio.getTime());
            Timestamp finTimestamp = new Timestamp(fechaFin.getTime());

            st.setTimestamp(1, inicioTimestamp);
            st.setTimestamp(2, finTimestamp);

            ResultSet rs = st.executeQuery();

            // Elimina filas existentes
            while (modeloEntSal.getRowCount() > 0) {
                modeloEntSal.removeRow(0);
            }

            scp_ent.setViewportView(tb_ent);
            while (rs.next()) {
                Object fila[] = new Object[6];

                Timestamp timestamp = rs.getTimestamp("fecha");

                TimeZone timeZone = TimeZone.getDefault();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(timeZone);

                String fechaFormateada = sdf.format(timestamp);

                fila[0] = rs.getInt("id_entrada_salida");
                fila[1] = fechaFormateada;
                fila[2] = rs.getString("tipo");
                fila[3] = String.format("%.2f", rs.getDouble("monto"));
                fila[4] = rs.getString("descripcion");
                fila[5] = rs.getString("nombre_usuario");

                modeloEntSal.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar Entradas y Salidas por fechas: " + e);
        }
        is_selected = false;
    }

    public void cargar_tablaDevolucionesPorPeriodo(Date fechaInicio, Date fechaFin) {
        Connection cn = Conexion.Conectar();
        String query = "SELECT mi.id_movimiento, mi.fecha, p.descripcion_producto, mi.cantidad, "
                + "(mi.cantidad * p.precio_venta) AS importe "
                + "FROM movimientos_inventario mi "
                + "JOIN productos p ON mi.id_producto = p.id_producto "
                + "WHERE mi.tipo_movimiento = 'DEVOLUCIÓN' "
                + "AND mi.fecha BETWEEN ? AND ? "
                + "ORDER BY mi.fecha";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            Timestamp inicioTimestamp = new Timestamp(fechaInicio.getTime());
            Timestamp finTimestamp = new Timestamp(fechaFin.getTime());

            st.setTimestamp(1, inicioTimestamp);
            st.setTimestamp(2, finTimestamp);

            ResultSet rs = st.executeQuery();

            // Limpiar tabla
            while (modeloDevoluciones.getRowCount() > 0) {
                modeloDevoluciones.removeRow(0);
            }

            scp_devoluciones.setViewportView(tb_devoluciones);

            while (rs.next()) {
                Object fila[] = new Object[5];

                Timestamp timestamp = rs.getTimestamp("fecha");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(TimeZone.getDefault());
                String fechaFormateada = sdf.format(timestamp);

                fila[0] = rs.getInt("id_movimiento");
                fila[1] = fechaFormateada;
                fila[2] = rs.getString("descripcion_producto");
                fila[3] = rs.getInt("cantidad");
                fila[4] = String.format("%.2f", rs.getDouble("importe"));

                modeloDevoluciones.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar devoluciones por periodo: " + e);
        }

        is_selected = false;
    }

    public void cargarTablaAbonosPorPeriodo(Date fechaInicio, Date fechaFin) {
        Connection cn = Conexion.Conectar();
        String query = "SELECT a.id_abono, a.fecha_abono, c.nombre_cliente, a.monto_abono "
                + "FROM abonos a "
                + "JOIN detalle_deudas dd ON a.id_detalleDeuda = dd.id_detalleDeuda "
                + "JOIN clientes c ON dd.id_cliente = c.id_cliente "
                + "WHERE a.fecha_abono BETWEEN ? AND ? "
                + "ORDER BY a.fecha_abono";

        try (PreparedStatement st = cn.prepareStatement(query)) {
            Timestamp inicioTimestamp = new Timestamp(fechaInicio.getTime());
            Timestamp finTimestamp = new Timestamp(fechaFin.getTime());

            st.setTimestamp(1, inicioTimestamp);
            st.setTimestamp(2, finTimestamp);

            ResultSet rs = st.executeQuery();

            // Limpiar tabla
            while (modeloAbonos.getRowCount() > 0) {
                modeloAbonos.removeRow(0);
            }

            scp_abonos.setViewportView(tb_abonos);

            while (rs.next()) {
                Object fila[] = new Object[4];

                // Formatear la fecha
                Timestamp timestamp = rs.getTimestamp("fecha_abono");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(TimeZone.getDefault());
                String fechaFormateada = sdf.format(timestamp);

                fila[0] = rs.getInt("id_abono");
                fila[1] = fechaFormateada;
                fila[2] = rs.getString("nombre_cliente");
                fila[3] = String.format("%.2f", rs.getDouble("monto_abono"));

                modeloAbonos.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar abonos por periodo: " + e);
        }

        is_selected = false;
    }

    public boolean soloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    public boolean soloNumeros(String texto) {
        return texto.matches("\\d+");
    }

    public boolean letrasYNumeros(String texto) {
        return texto.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+");
    }

}
