package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Equipamento;
import br.com.unisa.emprestimo_equipamento.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository){
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Equipamento> listarTodos(){
        return equipamentoRepository.listarTodos();
    }

    public Equipamento buscarPorId(Long id) {

        Equipamento equipamento = equipamentoRepository.buscarPorId(id);

        if (equipamento == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Equipamento não encontrado"
            );
        }

        return equipamento;
    }

    public Equipamento salvar(Equipamento equipamento){
        return equipamentoRepository.salvar(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento equipamento) {

        Equipamento atualizado =
                equipamentoRepository.atualizar(id, equipamento);

        if (atualizado == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Equipamento não encontrado"
            );
        }

        return atualizado;
    }

    public void excluir(Long id) {

        boolean excluido = equipamentoRepository.excluir(id);

        if (!excluido) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Equipamento não encontrado"
            );
        }
    }

    public Equipamento buscarPorPatrimonioLinear(String patrimonio) {

        List<Equipamento> equipamentos = equipamentoRepository.listarTodos();

        for (Equipamento equipamento : equipamentos) {

            if (equipamento.getPatrimonio().equalsIgnoreCase(patrimonio)) {
                return equipamento;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Equipamento não encontrado"
        );
    }

    public List<Equipamento> ordenarPorNome() {

        List<Equipamento> equipamentos =
                equipamentoRepository.listarTodos();

        for (int i = 0; i < equipamentos.size() - 1; i++) {

            for (int j = 0; j < equipamentos.size() - 1 - i; j++) {

                Equipamento atual = equipamentos.get(j);
                Equipamento proximo = equipamentos.get(j + 1);

                if (atual.getNome().compareToIgnoreCase(proximo.getNome()) > 0) {

                    equipamentos.set(j, proximo);
                    equipamentos.set(j + 1, atual);
                }
            }
        }

        return equipamentos;
    }

    public Equipamento buscarPorNomeBinaria(String nome) {

        List<Equipamento> equipamentos = ordenarPorNome();

        int inicio = 0;
        int fim = equipamentos.size() - 1;

        while (inicio <= fim) {

            int meio = (inicio + fim) / 2;

            Equipamento equipamento = equipamentos.get(meio);

            int comparacao =
                    equipamento.getNome().compareToIgnoreCase(nome);

            if (comparacao == 0) {
                return equipamento;
            }

            if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Equipamento não encontrado"
        );
    }

}
