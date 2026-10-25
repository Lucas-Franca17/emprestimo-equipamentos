package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Equipamento;
import br.com.unisa.emprestimo_equipamento.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository){
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Equipamento> listarTodos(){
        return equipamentoRepository.listaTodos();
    }

    public Equipamento buscarPorId(Long id){
        return equipamentoRepository.buscarPorId(id);
    }

    public Equipamento salvar(Equipamento equipamento){
        return equipamentoRepository.salvar(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento equipamento) {
        return equipamentoRepository.atualizar(id, equipamento);
    }

    public boolean excluir(Long id){
        return equipamentoRepository.excluir(id);
    }

}
