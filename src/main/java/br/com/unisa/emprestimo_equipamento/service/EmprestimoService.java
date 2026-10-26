package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Emprestimo;
import br.com.unisa.emprestimo_equipamento.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    public Emprestimo registrar(Emprestimo emprestimo) {

        try {
            return emprestimoRepository.registrar(
                    emprestimo.getUsuarioId(),
                    emprestimo.getEquipamentoId()
            );

        } catch (IllegalArgumentException e) {

            if ("Usuário não encontrado.".equals(e.getMessage())
                    || "Equipamento não encontrado.".equals(e.getMessage())) {

                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        e.getMessage()
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    e.getMessage()
            );
        }
    }

    public Emprestimo devolver(Long id) {

        try {
            return emprestimoRepository.devolver(id);

        } catch (IllegalArgumentException e) {

            if ("Empréstimo não encontrado.".equals(e.getMessage())) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        e.getMessage()
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    e.getMessage()
            );
        }
    }


}
