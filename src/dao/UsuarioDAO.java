package dao;

import model.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {

    Usuario validar(String username, String password) throws SQLException;

    boolean insertar(Usuario usuario) throws SQLException;

    List<Usuario> listarTodos() throws SQLException;

    boolean actualizar(Usuario usuario) throws SQLException;

    boolean eliminar(int id) throws SQLException;
}
