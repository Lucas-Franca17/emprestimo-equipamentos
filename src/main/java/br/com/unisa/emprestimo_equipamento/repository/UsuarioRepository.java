package br.com.unisa.emprestimo_equipamento.repository;

import br.com.unisa.emprestimo_equipamento.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    private final DataSource dataSource;

    public UsuarioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Usuario> listarTodos() {
        String sql = "SELECT id, nome, matricula FROM usuarios ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                Usuario usuario = new Usuario(resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("matricula"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuarios no banco de dados.", e);
        }
        return usuarios;
    }

    public Usuario buscarPorId(Long id) {
        String sql = "SELECT id, nome, matricula FROM usuarios WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Usuario(resultSet.getLong("id"),
                            resultSet.getString("nome"),
                            resultSet.getString("matricula"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário.", e);
        }
        return null;
    }
}
