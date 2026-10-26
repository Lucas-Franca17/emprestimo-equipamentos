package br.com.unisa.emprestimo_equipamento.controller;

import br.com.unisa.emprestimo_equipamento.model.Emprestimo;
import br.com.unisa.emprestimo_equipamento.service.EmprestimoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<Emprestimo> listarTodos(){
        return emprestimoService.listarTodos();
    }

    @PostMapping
    public Emprestimo registrar(@RequestBody Emprestimo emprestimo){
        return emprestimoService.registrar(emprestimo);
    }

    @PatchMapping("/{id}/devolucao")
    public Emprestimo devolver(@PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}
