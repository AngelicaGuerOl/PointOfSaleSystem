
package Vista;

import Conexion.Conexion;
import Controlador.CtrlLogin;
import Modelo.Usuario;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Angelica Guerrero
 */
public class Login extends javax.swing.JFrame {
    public static int id_usuario;
    int obtenerIdUsuario=0;
public static JDesktopPane jmenu;
    public Login() {
        initComponents();
        
        this.setSize(480,610); //minimas dimensiones cuando se oprime el botón maximizar
        int ancho=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocationRelativeTo(null); 
        this.setResizable(false); // Evitar que la ventana se pueda redimensionar
        this.setTitle("Sistema de tienda");
          }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_iniciar = new javax.swing.JButton();
        txt_usuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_registrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Usuario");

        txt_password.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_password.setBorder(null);

        btn_iniciar.setBackground(new java.awt.Color(23, 29, 221));
        btn_iniciar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_iniciar.setForeground(new java.awt.Color(255, 255, 255));
        btn_iniciar.setText("Iniciar sesión");
        btn_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciarActionPerformed(evt);
            }
        });

        txt_usuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_usuario.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Contraseña");

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel1.setText("Iniciar sesión");

        btn_registrar.setBackground(new java.awt.Color(102, 204, 0));
        btn_registrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_registrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_registrar.setText("Registrarse");
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1)
                                    .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                    .addComponent(txt_usuario)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_iniciar)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btn_registrar)))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(191, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btn_iniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_registrar)
                .addGap(73, 73, 73))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciarActionPerformed
        String usuario = txt_usuario.getText().trim();
        String password = txt_password.getText().trim();
        CtrlLogin ctrlLogin = new CtrlLogin();
        if (usuario.isEmpty()||password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else{
            Usuario usuarioObj = new Usuario();
            usuarioObj.setNombre_usuario(usuario);
            usuarioObj.setPassword_usuario(password);
            if (ctrlLogin.comprobacion(usuarioObj)){
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso","Éxito",JOptionPane.INFORMATION_MESSAGE);
                EfectivoInicial efectIn=new EfectivoInicial();
                efectIn.setVisible(true);
                id_usuario=idUsuario(usuario);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Error al iniciar sesión, usuario y/o contraseña incorrecta","Error", JOptionPane.ERROR_MESSAGE);

            }

        }
    }//GEN-LAST:event_btn_iniciarActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        // TODO add your handling code here:
        // Crear un JDialog para mostrar la ventana de cobrar
        JDialog dialog = new JDialog((Frame) null, "Registro de usuarios", true); // true indica que es modal

        // Crear el panel de cobrar y agregarlo al JDialog
        PRegistro panelRegistro = new PRegistro();

        dialog.setContentPane(panelRegistro);
        // Configurar el JDialog
        dialog.pack(); // Ajustar el tamaño del JDialog al contenido
        dialog.setLocationRelativeTo(this); // Mostrar el JDialog en el centro de la ventana principal

        // Mostrar el JDialog
        dialog.setVisible(true);
        

    }//GEN-LAST:event_btn_registrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatIntelliJLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_iniciar;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField txt_password;
    public static javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
    public int idUsuario(String usuario) {
    String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
    try (Connection cn = Conexion.Conectar();
         PreparedStatement pst = cn.prepareStatement(sql)) {
        pst.setString(1, usuario); // Reemplazar el ? con el parámetro
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                obtenerIdUsuario = rs.getInt("id_usuario");
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener id categoria");
    }
    return obtenerIdUsuario;
}
}
