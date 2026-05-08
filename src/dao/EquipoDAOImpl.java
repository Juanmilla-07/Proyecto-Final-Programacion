package dao;

import db.ConexionDB;
import model.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAOImpl implements EquipoDAO {

    @Override
    public boolean insertar(Equipo eq) throws SQLException {
        String sql = "INSERT INTO equipos (nombre, categoria, entrenador_id, puntos) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, eq.getNombre());
            ps.setString(2, eq.getCategoria());
            if (eq.getEntrenadorId() > 0) {
                ps.setInt(3, eq.getEntrenadorId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, eq.getPuntos());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Equipo> listarTodos() throws SQLException {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos ORDER BY puntos DESC, nombre";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("entrenador_id"),
                        rs.getInt("puntos")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Equipo eq) throws SQLException {
        String sql = "UPDATE equipos SET nombre=?, categoria=?, entrenador_id=?, puntos=? WHERE id=?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, eq.getNombre());
            ps.setString(2, eq.getCategoria());
            if (eq.getEntrenadorId() > 0) {
                ps.setInt(3, eq.getEntrenadorId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, eq.getPuntos());
            ps.setInt(5, eq.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM equipos WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
