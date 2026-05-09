package view;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JButton btnRegistro;

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public Login() {
        setTitle("Club Deportivo - Login");
        setSize(320, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        lblUsername = new JLabel("Usuario:");
        lblUsername.setBounds(30, 30, 80, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(120, 30, 150, 25);
        add(txtUsername);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30, 70, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 150, 25);
        add(txtPassword);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(30, 120, 100, 30);
        add(btnEntrar);
        btnEntrar.addActionListener(this);

        btnRegistro = new JButton("Registrarse");
        btnRegistro.setBounds(150, 120, 120, 30);
        add(btnRegistro);
        btnRegistro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEntrar) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Rellena todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Usuario usuario = usuarioDAO.validar(username, password);
                if (usuario != null) {
                    new Principal(usuario).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnRegistro) {
            new Registro().setVisible(true);
            dispose();
        }
    }
}
