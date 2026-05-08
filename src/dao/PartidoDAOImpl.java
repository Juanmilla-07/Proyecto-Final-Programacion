package dao;

import db.ConexionDB;
import dto.PartidoDTO;
import model.Partido;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAOImpl implements PartidoDAO {

    @Override
    public boolean insertar(Partido p) throws SQLException {
        String sql = "INSERT INTO partidos (equipo_local_id, equipo_visit_id, fecha, goles_local, goles_visitante, jornada) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getEquipoLocalId());
            ps.setInt(2, p.getEquipoVisitanteId());
            ps.setDate(3, Date.valueOf(p.getFecha()));
            ps.setInt(4, p.getGolesLocal());
            ps.setInt(5, p.getGolesVisitante());
            ps.setInt(6, p.getJornada());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<PartidoDTO> listarTodos() throws SQLException {
        List<PartidoDTO> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.fecha, p.goles_local, p.goles_visitante, p.jornada, " +
                     "el.nombre AS local_nombre, ev.nombre AS visit_nombre " +
                     "FROM partidos p " +
                     "JOIN equipos el ON p.equipo_local_id = el.id " +
                     "JOIN equipos ev ON p.equipo_visit_id = ev.id " +
                     "ORDER BY p.jornada, p.fecha";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date fechaSql = rs.getDate("fecha");
                lista.add(new PartidoDTO(
                        rs.getInt("id"),
                        rs.getString("local_nombre"),
                        rs.getString("visit_nombre"),
                        fechaSql != null ? fechaSql.toLocalDate() : null,
                        rs.getInt("goles_local"),
                        rs.getInt("goles_visitante"),
                        rs.getInt("jornada")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Partido p) throws SQLException {
        String sql = "UPDATE partidos SET equipo_local_id=?, equipo_visit_id=?, fecha=?, goles_local=?, goles_visitante=?, jornada=? WHERE id=?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getEquipoLocalId());
            ps.setInt(2, p.getEquipoVisitanteId());
            ps.setDate(3, Date.valueOf(p.getFecha()));
            ps.setInt(4, p.getGolesLocal());
            ps.setInt(5, p.getGolesVisitante());
            ps.setInt(6, p.getJornada());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM partidos WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}