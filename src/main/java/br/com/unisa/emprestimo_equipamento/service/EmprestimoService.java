package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Emprestimo;
import br.com.unisa.emprestimo_equipamento.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository){
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> listarTodos(){
        return emprestimoRepository.listarTodos();
    }
    public Emprestimo registrar(Emprestimo emprestimo){
        return emprestimoRepository.registrar(
                emprestimo.getUsuarioId(),
                emprestimo.getEquipamentoId()
        );
    }

    public Emprestimo devolver(Long id) {
        return emprestimoRepository.devolver(id);
    }


}
