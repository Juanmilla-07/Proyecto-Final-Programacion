package dao;

import dto.PartidoDTO;
import model.Partido;
import java.sql.SQLException;
import java.util.List;

public interface PartidoDAO {

    boolean insertar(Partido partido) throws SQLException;

    List<PartidoDTO> listarTodos() throws SQLException;

    boolean actualizar(Partido partido) throws SQLException;

    boolean eliminar(int id) throws SQLException;
}
