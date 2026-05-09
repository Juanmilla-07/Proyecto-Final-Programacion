package view;

import dao.EntrenadorDAOImpl;
import dao.EntrenadorDAO;
import dao.JugadorDAO;
import dao.JugadorDAOImpl;
import model.Entrenador;
import model.Jugador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

public class Registro extends JFrame implements ActionListener {

    // Campos comunes
    private JLabel lblUsername, lblPassword, lblEmail;
    private JLabel lblNombre, lblApellidos, lblDni, lblRol;
    private JTextField txtUsername, txtEmail, txtNombre, txtApellidos, txtDni;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;

    // Campos jugador
    private JLabel lblPosicion, lblDorsal, lblFecha;
    private JTextField txtPosicion, txtDorsal, txtFecha;

    // Campos entrenador
    private JLabel lblEspecialidad, lblLicencia;
    private JTextField txtEspecialidad, txtLicencia;

    private JButton btnGuardar, btnVolver;

    private JugadorDAO jugadorDAO       = new JugadorDAOImpl();
    private EntrenadorDAO entrenadorDAO = new EntrenadorDAOImpl();

    public Registro() {
        setTitle("Club Deportivo - Registro");
        setSize(380, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // ── Campos comunes ────────────────────────────────────────────────────
        lblUsername = new JLabel("Usuario:");
        lblUsername.setBounds(20, 20, 100, 25);
        add(lblUsername);
        txtUsername = new JTextField();
        txtUsername.setBounds(130, 20, 150, 25);
        add(txtUsername);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(20, 55, 100, 25);
        add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(130, 55, 150, 25);
        add(txtPassword);

        lblEmail = new JLabel("Email (obligatorio):");
        lblEmail.setBounds(20, 90, 130, 25);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(160, 90, 120, 25);
        add(txtEmail);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 125, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(130, 125, 150, 25);
        add(txtNombre);

        lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(20, 160, 100, 25);
        add(lblApellidos);
        txtApellidos = new JTextField();
        txtApellidos.setBounds(130, 160, 150, 25);
        add(txtApellidos);

        lblDni = new JLabel("DNI:");
        lblDni.setBounds(20, 195, 100, 25);
        add(lblDni);
        txtDni = new JTextField();
        txtDni.setBounds(130, 195, 150, 25);
        add(txtDni);

        lblRol = new JLabel("Rol:");
        lblRol.setBounds(20, 230, 100, 25);
        add(lblRol);
        cmbRol = new JComboBox<>(new String[]{"jugador", "entrenador"});
        cmbRol.setBounds(130, 230, 150, 25);
        add(cmbRol);
        cmbRol.addActionListener(this);

        // ── Campos jugador (visibles por defecto) ─────────────────────────────
        lblPosicion = new JLabel("Posición:");
        lblPosicion.setBounds(20, 270, 100, 25);
        add(lblPosicion);
        txtPosicion = new JTextField();
        txtPosicion.setBounds(130, 270, 150, 25);
        add(txtPosicion);

        lblDorsal = new JLabel("Dorsal:");
        lblDorsal.setBounds(20, 305, 100, 25);
        add(lblDorsal);
        txtDorsal = new JTextField();
        txtDorsal.setBounds(130, 305, 150, 25);
        add(txtDorsal);

        lblFecha = new JLabel("Fecha (yyyy-MM-dd):");
        lblFecha.setBounds(20, 340, 140, 25);
        add(lblFecha);
        txtFecha = new JTextField();
        txtFecha.setBounds(170, 340, 110, 25);
        add(txtFecha);

        // ── Campos entrenador (ocultos por defecto) ───────────────────────────
        lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(20, 270, 100, 25);
        add(lblEspecialidad);
        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(130, 270, 150, 25);
        add(txtEspecialidad);

        lblLicencia = new JLabel("Licencia:");
        lblLicencia.setBounds(20, 305, 100, 25);
        add(lblLicencia);
        txtLicencia = new JTextField();
        txtLicencia.setBounds(130, 305, 150, 25);
        add(txtLicencia);

        // Ocultar campos entrenador al inicio
        lblEspecialidad.setVisible(false);
        txtEspecialidad.setVisible(false);
        lblLicencia.setVisible(false);
        txtLicencia.setVisible(false);

        // ── Botones ───────────────────────────────────────────────────────────
        btnGuardar = new JButton("Registrarse");
        btnGuardar.setBounds(20, 400, 130, 30);
        add(btnGuardar);
        btnGuardar.addActionListener(this);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(170, 400, 100, 30);
        add(btnVolver);
        btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Cambio de rol → mostrar/ocultar campos
        if (e.getSource() == cmbRol) {
            String rol = (String) cmbRol.getSelectedItem();
            boolean esJugador = rol.equals("jugador");

            lblPosicion.setVisible(esJugador);
            txtPosicion.setVisible(esJugador);
            lblDorsal.setVisible(esJugador);
            txtDorsal.setVisible(esJugador);
            lblFecha.setVisible(esJugador);
            txtFecha.setVisible(esJugador);

            lblEspecialidad.setVisible(!esJugador);
            txtEspecialidad.setVisible(!esJugador);
            lblLicencia.setVisible(!esJugador);
            txtLicencia.setVisible(!esJugador);
        }

        if (e.getSource() == btnVolver) {
            new Login().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnGuardar) {
            registrar();
        }
    }

    private void registrar() {
        String username  = txtUsername.getText().trim();
        String password  = new String(txtPassword.getPassword()).trim();
        String email     = txtEmail.getText().trim();
        String nombre    = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String dni       = txtDni.getText().trim();
        String rol       = (String) cmbRol.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()
                || nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellena todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (rol.equals("jugador")) {
                Jugador j = new Jugador();
                j.setUsername(username);
                j.setPassword(password);
                j.setEmail(email);
                j.setNombre(nombre);
                j.setApellidos(apellidos);
                j.setDni(dni);
                j.setPosicion(txtPosicion.getText().trim());
                j.setDorsal(Integer.parseInt(txtDorsal.getText().trim()));
                if (!txtFecha.getText().trim().isEmpty()) {
                    j.setFechaNac(LocalDate.parse(txtFecha.getText().trim()));
                }
                jugadorDAO.registrar(j);

            } else {
                Entrenador en = new Entrenador();
                en.setUsername(username);
                en.setPassword(password);
                en.setEmail(email);
                en.setNombre(nombre);
                en.setApellidos(apellidos);
                en.setDni(dni);
                en.setEspecialidad(txtEspecialidad.getText().trim());
                en.setLicencia(txtLicencia.getText().trim());
                entrenadorDAO.registrar(en);
            }

            JOptionPane.showMessageDialog(this, "Registro completado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            new Login().setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El dorsal debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos incorrectos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}