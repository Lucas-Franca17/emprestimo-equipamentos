package br.com.unisa.emprestimo_equipamento.repository;

import br.com.unisa.emprestimo_equipamento.model.Equipamento;
import org.springframework.stereotype.Repository;

import javax.management.RuntimeMBeanException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EquipamentoRepository {

    private final DataSource dataSource;

    public EquipamentoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Equipamento> listaTodos() {
        String sql = "SELECT id, nome, patrimonio, disponivel FROM equipamentos ORDER BY id";

        List<Equipamento> equipamentos = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                Equipamento equipamento = new Equipamento(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("patrimonio"),
                        resultSet.getBoolean("disponivel")
                );
                equipamentos.add(equipamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar equipamento no banco de dados.", e
            );
        }
        return equipamentos;
    }

    public Equipamento buscarPorId(Long id) {
        String sql = "SELECT id, nome, patrimonio, disponivel FROM equipamentos WHERE id = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Equipamento(
                            resultSet.getLong("id"),
                            resultSet.getString("nome"),
                            resultSet.getString("patrimonio"),
                            resultSet.getBoolean("disponivel")
                    );
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar equipamento.", e);
        }
    return null;
    }

    public Equipamento salvar(Equipamento equipamento){
        String sql = "INSERT INTO equipamentos (nome, patrimonio, disponivel) VALUES (?,?,?) RETURNING id";

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ){

            statement.setString(1, equipamento.getNome());
            statement.setString(2, equipamento.getPatrimonio());
            statement.setBoolean(3, equipamento.isDisponivel());

            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    equipamento.setId(resultSet.getLong("id"));
                }
            }
            return equipamento;
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastra equipamento.", e);
        }
    }

    public Equipamento atualizar(Long id, Equipamento equipamento){

        String sql = "UPDATE equipamentos SET nome  = ?, patrimonio = ?, disponivel = ? WHERE id = ?";

        try(
                Connection conection = dataSource.getConnection();
                PreparedStatement statement = conection.prepareStatement(sql);
                ){
            statement.setString(1, equipamento.getNome());
            statement.setString(2,equipamento.getPatrimonio());
            statement.setBoolean(3, equipamento.isDisponivel());
            statement.setLong(4, id);

            int linhasAfetadas = statement.executeUpdate();
            if(linhasAfetadas == 0){
                return null;
            }
            equipamento.setId(id);
            return equipamento;
        }
        catch (SQLException e){
            throw new RuntimeException("Erro ao atualizar.", e);
        }
    }

    public boolean excluir(Long id){
        String sql = "DELETE FROM equipamentos WHERE id = ?";

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ){
            statement.setLong(1, id);
            int linhasAfetadas = statement.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e ){
            throw new RuntimeException("Erro ao excluir equipamento.", e);
        }
    }


}
