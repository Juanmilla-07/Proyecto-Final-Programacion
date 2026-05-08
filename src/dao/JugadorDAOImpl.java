package dao;

import db.ConexionDB;
import model.Jugador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAOImpl implements JugadorDAO {

    @Override
    public boolean registrar(Jugador j) throws SQLException {
        String sqlUsuario = "INSERT INTO usuarios (username, password, email, nombre, apellidos, dni, rol) VALUES (?, ?, ?, ?, ?, ?, 'jugador')";
        String sqlJugador = "INSERT INTO jugadores (usuario_id, posicion, dorsal, fecha_nac) VALUES (LAST_INSERT_ID(), ?, ?, ?)";

        Connection con = ConexionDB.conectar();
        con.setAutoCommit(false);

        try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario)) {
            ps1.setString(1, j.getUsername());
            ps1.setString(2, j.getPassword());
            ps1.setString(3, j.getEmail());
            ps1.setString(4, j.getNombre());
            ps1.setString(5, j.getApellidos());
            ps1.setString(6, j.getDni());
            ps1.executeUpdate();
        }

        try (PreparedStatement ps2 = con.prepareStatement(sqlJugador)) {
            ps2.setString(1, j.getPosicion());
            ps2.setInt(2, j.getDorsal());
            ps2.setDate(3, j.getFechaNac() != null ? Date.valueOf(j.getFechaNac()) : null);
            ps2.executeUpdate();
        }

        con.commit();
        con.setAutoCommit(true);
        con.close();
        return true;
    }

    @Override
    public List<Jugador> listarTodos() throws SQLException {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.password, u.email, u.nombre, u.apellidos, u.dni, " +
                     "j.posicion, j.dorsal, j.fecha_nac " +
                     "FROM usuarios u JOIN jugadores j ON u.id = j.usuario_id " +
                     "ORDER BY u.apellidos, u.nombre";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Jugador j = new Jugador();
                j.setId(rs.getInt("id"));
                j.setUsername(rs.getString("username"));
                j.setPassword(rs.getString("password"));
                j.setEmail(rs.getString("email"));
                j.setNombre(rs.getString("nombre"));
                j.setApellidos(rs.getString("apellidos"));
                j.setDni(rs.getString("dni"));
                j.setPosicion(rs.getString("posicion"));
                j.setDorsal(rs.getInt("dorsal"));
                Date fecha = rs.getDate("fecha_nac");
                if (fecha != null) {
                    j.setFechaNac(fecha.toLocalDate());
                }
                lista.add(j);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Jugador j) throws SQLException {
        String sqlUsuario = "UPDATE usuarios SET username=?, password=?, email=?, nombre=?, apellidos=?, dni=? WHERE id=?";
        String sqlJugador = "UPDATE jugadores SET posicion=?, dorsal=?, fecha_nac=? WHERE usuario_id=?";

        Connection con = ConexionDB.conectar();
        con.setAutoCommit(false);

        try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario)) {
            ps1.setString(1, j.getUsername());
            ps1.setString(2, j.getPassword());
            ps1.setString(3, j.getEmail());
            ps1.setString(4, j.getNombre());
            ps1.setString(5, j.getApellidos());
            ps1.setString(6, j.getDni());
            ps1.setInt(7, j.getId());
            ps1.executeUpdate();
        }

        try (PreparedStatement ps2 = con.prepareStatement(sqlJugador)) {
            ps2.setString(1, j.getPosicion());
            ps2.setInt(2, j.getDorsal());
            ps2.setDate(3, j.getFechaNac() != null ? Date.valueOf(j.getFechaNac()) : null);
            ps2.setInt(4, j.getId());
            ps2.executeUpdate();
        }

        con.commit();
        con.setAutoCommit(true);
        con.close();
        return true;
    }

    @Override
    public boolean eliminar(int usuarioId) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            return ps.executeUpdate() > 0;
        }
    }
}
