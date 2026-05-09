package view;

import dao.*;
import dto.PartidoDTO;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Principal extends JFrame implements ActionListener {

    private Usuario usuarioActual;

    // DAOs
    private JugadorDAO    jugadorDAO    = new JugadorDAOImpl();
    private EntrenadorDAO entrenadorDAO = new EntrenadorDAOImpl();
    private EquipoDAO     equipoDAO     = new EquipoDAOImpl();
    private PartidoDAO    partidoDAO    = new PartidoDAOImpl();
    private UsuarioDAO    usuarioDAO    = new UsuarioDAOImpl();

    // Botones de módulo
    private JButton btnJugadores, btnEntrenadores, btnEquipos, btnPartidos;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Formulario
    private JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8;
    private JTextField txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8;
    private JButton btnNuevo, btnGuardar, btnEliminar;

    // Menú
    private JMenuBar menuBar;
    private JMenuItem itemCerrar, itemCambiarPass, itemClaro, itemOscuro;

    // Estado
    private String moduloActivo = "";
    private int idSeleccionado  = -1;

    public Principal(Usuario usuario) {
        this.usuarioActual = usuario;
        setTitle("Club Deportivo - " + usuario.getNombre() + " (" + usuario.getRol() + ")");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // ── Menú ──────────────────────────────────────────────────────────────
        menuBar = new JMenuBar();

        JMenu menuSesion = new JMenu("Sesión");
        itemCambiarPass = new JMenuItem("Cambiar contraseña");
        itemCerrar      = new JMenuItem("Cerrar sesión");
        menuSesion.add(itemCambiarPass);
        menuSesion.add(itemCerrar);
        itemCambiarPass.addActionListener(this);
        itemCerrar.addActionListener(this);

        JMenu menuTema = new JMenu("Preferencias");
        itemClaro  = new JMenuItem("Tema claro");
        itemOscuro = new JMenuItem("Tema oscuro");
        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);
        itemClaro.addActionListener(this);
        itemOscuro.addActionListener(this);

        menuBar.add(menuSesion);
        menuBar.add(menuTema);
        setJMenuBar(menuBar);

        // ── Botones de módulo (panel lateral izquierdo) ───────────────────────
        btnJugadores = new JButton("Jugadores");
        btnJugadores.setBounds(10, 10, 130, 35);
        add(btnJugadores);
        btnJugadores.addActionListener(this);

        btnEntrenadores = new JButton("Entrenadores");
        btnEntrenadores.setBounds(10, 55, 130, 35);
        add(btnEntrenadores);
        btnEntrenadores.addActionListener(this);

        btnEquipos = new JButton("Equipos");
        btnEquipos.setBounds(10, 100, 130, 35);
        add(btnEquipos);
        btnEquipos.addActionListener(this);

        btnPartidos = new JButton("Partidos");
        btnPartidos.setBounds(10, 145, 130, 35);
        add(btnPartidos);
        btnPartidos.addActionListener(this);

        // ── Tabla central ─────────────────────────────────────────────────────
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(155, 10, 560, 530);
        add(scroll);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                rellenarFormulario(tabla.getSelectedRow());
            }
        });

        // ── Formulario (panel derecho) ────────────────────────────────────────
        lbl1 = new JLabel("Campo1:"); lbl1.setBounds(730, 10, 100, 25);  add(lbl1);
        txt1 = new JTextField();      txt1.setBounds(730, 35, 170, 25);  add(txt1);

        lbl2 = new JLabel("Campo2:"); lbl2.setBounds(730, 70, 100, 25);  add(lbl2);
        txt2 = new JTextField();      txt2.setBounds(730, 95, 170, 25);  add(txt2);

        lbl3 = new JLabel("Campo3:"); lbl3.setBounds(730, 130, 100, 25); add(lbl3);
        txt3 = new JTextField();      txt3.setBounds(730, 155, 170, 25); add(txt3);

        lbl4 = new JLabel("Campo4:"); lbl4.setBounds(730, 190, 100, 25); add(lbl4);
        txt4 = new JTextField();      txt4.setBounds(730, 215, 170, 25); add(txt4);

        lbl5 = new JLabel("Campo5:"); lbl5.setBounds(730, 250, 100, 25); add(lbl5);
        txt5 = new JTextField();      txt5.setBounds(730, 275, 170, 25); add(txt5);

        lbl6 = new JLabel("Campo6:"); lbl6.setBounds(730, 310, 100, 25); add(lbl6);
        txt6 = new JTextField();      txt6.setBounds(730, 335, 170, 25); add(txt6);

        lbl7 = new JLabel("Campo7:"); lbl7.setBounds(730, 370, 100, 25); add(lbl7);
        txt7 = new JTextField();      txt7.setBounds(730, 395, 170, 25); add(txt7);

        lbl8 = new JLabel("Campo8:"); lbl8.setBounds(730, 425, 100, 25); add(lbl8);
        txt8 = new JTextField();      txt8.setBounds(730, 450, 170, 25); add(txt8);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(730, 485, 170, 25);
        add(btnNuevo);
        btnNuevo.addActionListener(this);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(730, 515, 170, 25);
        add(btnGuardar);
        btnGuardar.addActionListener(this);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(730, 545, 170, 25);
        add(btnEliminar);
        btnEliminar.addActionListener(this);

        // Cargar módulo inicial
        cargarJugadores();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJugadores)    cargarJugadores();
        if (e.getSource() == btnEntrenadores) cargarEntrenadores();
        if (e.getSource() == btnEquipos)      cargarEquipos();
        if (e.getSource() == btnPartidos)     cargarPartidos();
        if (e.getSource() == btnNuevo)        limpiarFormulario();
        if (e.getSource() == btnGuardar)      guardar();
        if (e.getSource() == btnEliminar)     eliminar();
        if (e.getSource() == itemCerrar)      cerrarSesion();
        if (e.getSource() == itemCambiarPass) cambiarContrasena();
        if (e.getSource() == itemClaro)       getContentPane().setBackground(java.awt.Color.WHITE);
        if (e.getSource() == itemOscuro)      getContentPane().setBackground(new java.awt.Color(50, 50, 50));
    }

    // ── Cargar módulos ────────────────────────────────────────────────────────
    private void cargarJugadores() {
        moduloActivo = "jugadores";
        limpiarFormulario();
        lbl1.setText("Nombre:"); lbl2.setText("Apellidos:");
        lbl3.setText("Username:"); lbl4.setText("Password:");
        lbl5.setText("Posición:"); lbl6.setText("Dorsal:");
        lbl7.setText("Email:"); lbl8.setText("DNI:");
        txt7.setVisible(true); lbl7.setVisible(true);
        txt8.setVisible(true); lbl8.setVisible(true);

        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Apellidos", "Username", "Posición", "Dorsal", "Email", "DNI"});
        modeloTabla.setRowCount(0);
        try {
            List<Jugador> lista = jugadorDAO.listarTodos();
            for (Jugador j : lista) {
                modeloTabla.addRow(new Object[]{j.getId(), j.getNombre(), j.getApellidos(), j.getUsername(), j.getPosicion(), j.getDorsal(), j.getEmail(), j.getDni()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEntrenadores() {
        moduloActivo = "entrenadores";
        limpiarFormulario();
        lbl1.setText("Nombre:"); lbl2.setText("Apellidos:");
        lbl3.setText("Username:"); lbl4.setText("Password:");
        lbl5.setText("Especialidad:"); lbl6.setText("Licencia:");
        lbl7.setText("Email:"); lbl8.setText("DNI:");
        txt7.setVisible(true); lbl7.setVisible(true);
        txt8.setVisible(true); lbl8.setVisible(true);

        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Apellidos", "Username", "Especialidad", "Licencia", "Email", "DNI"});
        modeloTabla.setRowCount(0);
        try {
            List<Entrenador> lista = entrenadorDAO.listarTodos();
            for (Entrenador en : lista) {
                modeloTabla.addRow(new Object[]{en.getId(), en.getNombre(), en.getApellidos(), en.getUsername(), en.getEspecialidad(), en.getLicencia(), en.getEmail(), en.getDni()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEquipos() {
        moduloActivo = "equipos";
        limpiarFormulario();
        lbl1.setText("Nombre:"); lbl2.setText("Categoría:");
        lbl3.setText("ID Entrenador:"); lbl4.setText("Puntos:");
        lbl5.setText(""); lbl6.setText("");
        txt5.setVisible(false); txt6.setVisible(false);
        txt7.setVisible(false); lbl7.setVisible(false);
        txt8.setVisible(false); lbl8.setVisible(false);

        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Categoría", "ID Entrenador", "Puntos"});
        modeloTabla.setRowCount(0);
        try {
            List<Equipo> lista = equipoDAO.listarTodos();
            for (Equipo eq : lista) {
                modeloTabla.addRow(new Object[]{eq.getId(), eq.getNombre(), eq.getCategoria(), eq.getEntrenadorId(), eq.getPuntos()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPartidos() {
        moduloActivo = "partidos";
        limpiarFormulario();
        lbl1.setText("ID Equipo Local:"); lbl2.setText("ID Equipo Visit.:");
        lbl3.setText("Goles Local:"); lbl4.setText("Goles Visitante:");
        lbl5.setText("Fecha (yyyy-MM-dd):"); lbl6.setText("Jornada:");
        txt5.setVisible(true); txt6.setVisible(true);
        txt7.setVisible(false); lbl7.setVisible(false);
        txt8.setVisible(false); lbl8.setVisible(false);

        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Jornada", "Local", "Visitante", "Resultado", "Fecha"});
        modeloTabla.setRowCount(0);
        try {
            List<PartidoDTO> lista = partidoDAO.listarTodos();
            for (PartidoDTO p : lista) {
                modeloTabla.addRow(new Object[]{p.getId(), p.getJornada(), p.getEquipoLocalNombre(), p.getEquipoVisitanteNombre(), p.getResultado(), p.getFecha()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Rellenar formulario al seleccionar fila ───────────────────────────────
    private void rellenarFormulario(int fila) {
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);

        if (moduloActivo.equals("jugadores")) {
            txt1.setText((String) modeloTabla.getValueAt(fila, 1));
            txt2.setText((String) modeloTabla.getValueAt(fila, 2));
            txt3.setText((String) modeloTabla.getValueAt(fila, 3));
            txt4.setText("");
            txt5.setText((String) modeloTabla.getValueAt(fila, 4));
            txt6.setText(String.valueOf(modeloTabla.getValueAt(fila, 5)));
            txt7.setText((String) modeloTabla.getValueAt(fila, 6));
            txt8.setText((String) modeloTabla.getValueAt(fila, 7));
        }

        if (moduloActivo.equals("entrenadores")) {
            txt1.setText((String) modeloTabla.getValueAt(fila, 1));
            txt2.setText((String) modeloTabla.getValueAt(fila, 2));
            txt3.setText((String) modeloTabla.getValueAt(fila, 3));
            txt4.setText("");
            txt5.setText((String) modeloTabla.getValueAt(fila, 4));
            txt6.setText((String) modeloTabla.getValueAt(fila, 5));
            txt7.setText((String) modeloTabla.getValueAt(fila, 6));
            txt8.setText((String) modeloTabla.getValueAt(fila, 7));
        }

        if (moduloActivo.equals("equipos")) {
            txt1.setText((String) modeloTabla.getValueAt(fila, 1));
            txt2.setText((String) modeloTabla.getValueAt(fila, 2));
            txt3.setText(String.valueOf(modeloTabla.getValueAt(fila, 3)));
            txt4.setText(String.valueOf(modeloTabla.getValueAt(fila, 4)));
        }

        if (moduloActivo.equals("partidos")) {
            try {
                List<PartidoDTO> lista = partidoDAO.listarTodos();
                for (PartidoDTO p : lista) {
                    if (p.getId() == idSeleccionado) {
                        txt1.setText(String.valueOf(p.getEquipoLocalId()));
                        txt2.setText(String.valueOf(p.getEquipoVisitanteId()));
                        txt3.setText(String.valueOf(p.getGolesLocal()));
                        txt4.setText(String.valueOf(p.getGolesVisitante()));
                        txt5.setText(String.valueOf(p.getFecha()));
                        txt6.setText(String.valueOf(p.getJornada()));
                        break;
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Guardar ───────────────────────────────────────────────────────────────
    private void guardar() {
        try {
            if (moduloActivo.equals("jugadores")) {
                Jugador j = new Jugador();
                j.setNombre(txt1.getText().trim());
                j.setApellidos(txt2.getText().trim());
                j.setUsername(txt3.getText().trim());
                j.setPassword(txt4.getText().trim());
                j.setPosicion(txt5.getText().trim());
                j.setDorsal(Integer.parseInt(txt6.getText().trim()));
                j.setEmail(txt7.getText().trim());
                j.setDni(txt8.getText().trim());
                if (idSeleccionado == -1) {
                    jugadorDAO.registrar(j);
                } else {
                    j.setId(idSeleccionado);
                    jugadorDAO.actualizar(j);
                }
                JOptionPane.showMessageDialog(this, "Jugador guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarJugadores();
            }

            if (moduloActivo.equals("entrenadores")) {
                Entrenador en = new Entrenador();
                en.setNombre(txt1.getText().trim());
                en.setApellidos(txt2.getText().trim());
                en.setUsername(txt3.getText().trim());
                en.setPassword(txt4.getText().trim());
                en.setEspecialidad(txt5.getText().trim());
                en.setLicencia(txt6.getText().trim());
                en.setEmail(txt7.getText().trim());
                en.setDni(txt8.getText().trim());
                if (idSeleccionado == -1) {
                    entrenadorDAO.registrar(en);
                } else {
                    en.setId(idSeleccionado);
                    entrenadorDAO.actualizar(en);
                }
                JOptionPane.showMessageDialog(this, "Entrenador guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarEntrenadores();
            }

            if (moduloActivo.equals("equipos")) {
                Equipo eq = new Equipo();
                eq.setNombre(txt1.getText().trim());
                eq.setCategoria(txt2.getText().trim());
                eq.setEntrenadorId(Integer.parseInt(txt3.getText().trim()));
                eq.setPuntos(Integer.parseInt(txt4.getText().trim()));
                if (idSeleccionado == -1) {
                    equipoDAO.insertar(eq);
                } else {
                    eq.setId(idSeleccionado);
                    equipoDAO.actualizar(eq);
                }
                JOptionPane.showMessageDialog(this, "Equipo guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarEquipos();
            }

            if (moduloActivo.equals("partidos")) {
                Partido p = new Partido();
                p.setEquipoLocalId(Integer.parseInt(txt1.getText().trim()));
                p.setEquipoVisitanteId(Integer.parseInt(txt2.getText().trim()));
                p.setGolesLocal(Integer.parseInt(txt3.getText().trim()));
                p.setGolesVisitante(Integer.parseInt(txt4.getText().trim()));
                p.setFecha(LocalDate.parse(txt5.getText().trim()));
                p.setJornada(Integer.parseInt(txt6.getText().trim()));
                if (idSeleccionado == -1) {
                    partidoDAO.insertar(p);
                } else {
                    p.setId(idSeleccionado);
                    partidoDAO.actualizar(p);
                }
                JOptionPane.showMessageDialog(this, "Partido guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPartidos();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Algún campo numérico es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos incorrectos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Eliminar ──────────────────────────────────────────────────────────────
    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try {
            if (moduloActivo.equals("jugadores"))    jugadorDAO.eliminar(idSeleccionado);
            if (moduloActivo.equals("entrenadores")) entrenadorDAO.eliminar(idSeleccionado);
            if (moduloActivo.equals("equipos"))      equipoDAO.eliminar(idSeleccionado);
            if (moduloActivo.equals("partidos"))     partidoDAO.eliminar(idSeleccionado);

            JOptionPane.showMessageDialog(this, "Registro eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            if (moduloActivo.equals("jugadores"))    cargarJugadores();
            if (moduloActivo.equals("entrenadores")) cargarEntrenadores();
            if (moduloActivo.equals("equipos"))      cargarEquipos();
            if (moduloActivo.equals("partidos"))     cargarPartidos();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void limpiarFormulario() {
        idSeleccionado = -1;
        txt1.setText(""); txt2.setText(""); txt3.setText("");
        txt4.setText(""); txt5.setText(""); txt6.setText("");
        txt7.setText(""); txt8.setText("");
        txt5.setVisible(true); txt6.setVisible(true);
        txt7.setVisible(true); lbl7.setVisible(true);
        txt8.setVisible(true); lbl8.setVisible(true);
        tabla.clearSelection();
    }

    private void cerrarSesion() {
        new Login().setVisible(true);
        dispose();
    }

    private void cambiarContrasena() {
        JPasswordField campo = new JPasswordField();
        int opcion = JOptionPane.showConfirmDialog(this, campo, "Nueva contraseña:", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nueva = new String(campo.getPassword()).trim();
            if (nueva.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            usuarioActual.setPassword(nueva);
            try {
                usuarioDAO.actualizar(usuarioActual);
                JOptionPane.showMessageDialog(this, "Contraseña actualizada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}