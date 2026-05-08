package dao;

import model.Entrenador;
import java.sql.SQLException;
import java.util.List;

public interface EntrenadorDAO {

    boolean registrar(Entrenador entrenador) throws SQLException;

    List<Entrenador> listarTodos() throws SQLException;

    boolean actualizar(Entrenador entrenador) throws SQLException;

    boolean eliminar(int usuarioId) throws SQLException;
}
