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
        String sqlUsuario    = "INSERT INTO usuarios (usuario, contraseña, email, nombre, apellidos, dni, rol) VALUES (?, ?, ?, ?, ?, ?, 'entrenador')";
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
        String sql = "SELECT u.id, u.usuario, u.contraseña, u.email, u.nombre, u.apellidos, u.dni, " +
                     "e.especialidad, e.licencia " +
                     "FROM usuarios u JOIN entrenadores e ON u.id = e.usuario_id " +
                     "ORDER BY u.apellidos, u.nombre";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Entrenador en = new Entrenador();
                en.setId(rs.getInt("id"));
                en.setUsername(rs.getString("usuario"));
                en.setPassword(rs.getString("contraseña"));
                en.setEmail(rs.getString("email"));
                en.setNombre(rs.getString("nombre"));
                en.setApellidos(rs.getString("apellidos"));
                en.setDni(rs.getString("dni"));
                en.setEspecialidad(rs.getString("especialidad"));
                en.setLicencia(rs.getString("licencia"));
                lista.add(en);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Entrenador e) throws SQLException {
        String sqlUsuario    = "UPDATE usuarios SET usuario=?, contraseña=?, email=?, nombre=?, apellidos=?, dni=? WHERE id=?";
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