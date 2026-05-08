package dao;

import dto.JugadorEquipoDTO;
import model.Jugador;
import java.sql.SQLException;
import java.util.List;

public interface JugadorDAO {

    boolean registrar(Jugador jugador) throws SQLException;

    List<Jugador> listarTodos() throws SQLException;

    boolean actualizar(Jugador jugador) throws SQLException;

    boolean eliminar(int usuarioId) throws SQLException;

    /** Lista jugadores resolviendo el nombre de su equipo via JOIN. */
    List<JugadorEquipoDTO> listarConEquipo() throws SQLException;
}