package dao;

import dto.EquipoEntrenadorDTO;
import model.Equipo;
import java.sql.SQLException;
import java.util.List;

public interface EquipoDAO {

    boolean insertar(Equipo equipo) throws SQLException;

    List<Equipo> listarTodos() throws SQLException;

    boolean actualizar(Equipo equipo) throws SQLException;

    boolean eliminar(int id) throws SQLException;

    /** Lista equipos resolviendo el nombre del entrenador via JOIN. */
    List<EquipoEntrenadorDTO> listarConNombreEntrenador() throws SQLException;
}