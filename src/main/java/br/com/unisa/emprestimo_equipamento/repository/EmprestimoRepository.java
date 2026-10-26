package br.com.unisa.emprestimo_equipamento.repository;

import br.com.unisa.emprestimo_equipamento.model.Emprestimo;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmprestimoRepository {

    private final DataSource dataSource;

    public EmprestimoRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public  List<Emprestimo> listarTodos(){
        String sql = "SELECT id, usuario_id, equipamento_id, " +
                "data_registro, data_devolucao, situacao " +
                "FROM emprestimos ORDER BY id";

        List<Emprestimo> emprestimos = new ArrayList<>();

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
                ){
            while(resultSet.next()){
                Timestamp dataDevolucao = resultSet.getTimestamp("data_devolucao");

                Emprestimo emprestimo = new Emprestimo(
                        resultSet.getLong("id"),
                        resultSet.getLong("usuario_id"),
                        resultSet.getLong("equipamento_id"),
                        resultSet.getTimestamp("data_registro").toLocalDateTime(),
                        dataDevolucao != null
                        ? dataDevolucao.toLocalDateTime() : null, resultSet.getString("situacao")
                );
                emprestimos.add(emprestimo);
            }
        }catch (SQLException e){
            throw new RuntimeException("Erro ao buscar emprestimos", e);
        }
        return emprestimos;
    }

    public Emprestimo registrar(Long usuarioId, Long equipamentoId){
        String sqlUsuario = "SELECt id FROM usuarios WHERE id = ?";
        String sqlEquipamento = "SELECT disponivel FROM equipamentos WHERE id = ? FOR UPDATE";
        String sqlEmprestimo = "INSERT INTO emprestimos(usuario_id, equipamento_id, data_registro, situacao)" +
                "VALUES (?, ?, CURRENT_TIMESTAMP, 'ATIVO')" +
                "RETURNING id, data_registro";
        String sqlAtualizarEquipamento = "UPDATE equipamentos SET disponivel = false WHERE id = ?";

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);

            try{

                try(PreparedStatement statement = connection.prepareStatement(sqlUsuario)){
                    statement.setLong(1, usuarioId);
                    try (ResultSet resultSet = statement.executeQuery()){

                        if(!resultSet.next()){
                            throw new IllegalArgumentException(
                                    "Usuário não encontrado."
                            );
                        }
                    }
                }
                try(PreparedStatement statement = connection.prepareStatement(sqlEquipamento)){
                    statement.setLong(1, equipamentoId);
                    try (ResultSet resultSet = statement.executeQuery()){

                        if(!resultSet.next()){
                            throw new IllegalArgumentException(
                                 "Equipamento não encontrado."
                            );
                        }
                        if(!resultSet.getBoolean("disponivel")){
                            throw new IllegalArgumentException(
                                    "Equipamento indisponivel."
                            );
                        }
                    }
                }
                Emprestimo emprestimo = new Emprestimo();
                try (PreparedStatement statement = connection.prepareStatement(sqlEmprestimo)){

                    statement.setLong(1, usuarioId);
                    statement.setLong(2, equipamentoId);
                    try (ResultSet resultSet = statement.executeQuery()){

                        if(resultSet.next()){
                            emprestimo.setId(resultSet.getLong("id"));
                            emprestimo.setUsuarioId(usuarioId);
                            emprestimo.setEquipamentoId(equipamentoId);

                            emprestimo.setDataRegistro(
                                    resultSet.getTimestamp("data_registro").toLocalDateTime()
                            );
                            emprestimo.setSituacao(("ATIVO"));
                        }
                    }
                }
                try(PreparedStatement statement = connection.prepareStatement(sqlAtualizarEquipamento)){
                    statement.setLong(1, equipamentoId);
                    statement.executeUpdate();
                }
                connection.commit();
                return emprestimo;
            }
            catch(Exception e){
                connection.rollback();
                throw e;
            }
        }catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao registrar emprestimo.", e
            );
        }
    }

    public Emprestimo devolver(Long id) {

        String sqlBuscarEmprestimo = """
            SELECT id, usuario_id, equipamento_id,
                   data_registro, data_devolucao, situacao
            FROM emprestimos
            WHERE id = ?
            FOR UPDATE
            """;

        String sqlDevolverEmprestimo = """
            UPDATE emprestimos
            SET situacao = 'DEVOLVIDO',
                data_devolucao = CURRENT_TIMESTAMP
            WHERE id = ?
            RETURNING id, usuario_id, equipamento_id,
                      data_registro, data_devolucao, situacao
            """;

        String sqlLiberarEquipamento =
                "UPDATE equipamentos SET disponivel = true WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(false);

            try {

                Long equipamentoId;

                try (PreparedStatement statement =
                             connection.prepareStatement(sqlBuscarEmprestimo)) {

                    statement.setLong(1, id);

                    try (ResultSet resultSet = statement.executeQuery()) {

                        if (!resultSet.next()) {
                            throw new IllegalArgumentException(
                                    "Empréstimo não encontrado."
                            );
                        }

                        if ("DEVOLVIDO".equals(resultSet.getString("situacao"))) {
                            throw new IllegalArgumentException(
                                    "Empréstimo já foi devolvido."
                            );
                        }

                        equipamentoId =
                                resultSet.getLong("equipamento_id");
                    }
                }

                Emprestimo emprestimo;

                try (PreparedStatement statement =
                             connection.prepareStatement(sqlDevolverEmprestimo)) {

                    statement.setLong(1, id);

                    try (ResultSet resultSet = statement.executeQuery()) {

                        resultSet.next();

                        emprestimo = new Emprestimo(
                                resultSet.getLong("id"),
                                resultSet.getLong("usuario_id"),
                                resultSet.getLong("equipamento_id"),
                                resultSet.getTimestamp("data_registro")
                                        .toLocalDateTime(),
                                resultSet.getTimestamp("data_devolucao")
                                        .toLocalDateTime(),
                                resultSet.getString("situacao")
                        );
                    }
                }

                try (PreparedStatement statement =
                             connection.prepareStatement(sqlLiberarEquipamento)) {

                    statement.setLong(1, equipamentoId);
                    statement.executeUpdate();
                }

                connection.commit();

                return emprestimo;

            } catch (Exception e) {

                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao registrar devolução.",
                    e
            );
        }
    }
}
