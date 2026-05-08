package dao;

import db.ConexionDB;
import model.Entrenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAOImpl implements EntrenadorDAO {

    @Override
    public boolean registrar(Entrenador e) throws SQLException {
        String sqlUsuario    = "INSERT INTO usuarios (username, password, email, nombre, apellidos, dni, rol) VALUES (?, ?, ?, ?, ?, ?, 'entrenador')";
        String sqlEntrenador = "INSERT INTO entrenadores (usuario_id, especialidad, licencia) VALUES (LAST_INSERT_ID(), ?, ?)";

        Connection con = ConexionDB.conectar();
        con.setAutoCommit(false);

        try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario)) {
            ps1.setString(1, e.getUsername());
            ps1.setString(2, e.getPassword());
            ps1.setString(3, e.getEmail());
            ps1.setString(4, e.getNombre());
            ps1.setString(5, e.getApellidos());
            ps1.setString(6, e.getDni());
            ps1.executeUpdate();
        }

        try (PreparedStatement ps2 = con.prepareStatement(sqlEntrenador)) {
            ps2.setString(1, e.getEspecialidad());
            ps2.setString(2, e.getLicencia());
            ps2.executeUpdate();
        }

        con.commit();
        con.setAutoCommit(true);
        con.close();
        return true;
    }

    @Override
    public List<Entrenador> listarTodos() throws SQLException {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.password, u.email, u.nombre, u.apellidos, u.dni, " +
                     "e.especialidad, e.licencia " +
                     "FROM usuarios u JOIN entrenadores e ON u.id = e.usuario_id " +
                     "ORDER BY u.apellidos, u.nombre";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Entrenador(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("especialidad"),
                        rs.getString("licencia")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Entrenador e) throws SQLException {
        String sqlUsuario    = "UPDATE usuarios SET username=?, password=?, email=?, nombre=?, apellidos=?, dni=? WHERE id=?";
        String sqlEntrenador = "UPDATE entrenadores SET especialidad=?, licencia=? WHERE usuario_id=?";

        Connection con = ConexionDB.conectar();
        con.setAutoCommit(false);

        try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario)) {
            ps1.setString(1, e.getUsername());
            ps1.setString(2, e.getPassword());
            ps1.setString(3, e.getEmail());
            ps1.setString(4, e.getNombre());
            ps1.setString(5, e.getApellidos());
            ps1.setString(6, e.getDni());
            ps1.setInt(7, e.getId());
            ps1.executeUpdate();
        }

        try (PreparedStatement ps2 = con.prepareStatement(sqlEntrenador)) {
            ps2.setString(1, e.getEspecialidad());
            ps2.setString(2, e.getLicencia());
            ps2.setInt(3, e.getId());
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
