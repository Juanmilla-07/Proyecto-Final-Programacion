package dao;

import db.ConexionDB;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario validar(String username, String password) throws SQLException {
        Usuario user = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Usuario(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("dni"),
                            rs.getString("rol")
                    );
                }
            }
        }
        return user;
    }

    @Override
    public boolean insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (username, password, email, nombre, apellidos, dni, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getNombre());
            ps.setString(5, u.getApellidos());
            ps.setString(6, u.getDni());
            ps.setString(7, u.getRol());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY apellidos, nombre";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("rol")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET username=?, password=?, email=?, nombre=?, apellidos=?, dni=?, rol=? WHERE id=?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getNombre());
            ps.setString(5, u.getApellidos());
            ps.setString(6, u.getDni());
            ps.setString(7, u.getRol());
            ps.setInt(8, u.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
