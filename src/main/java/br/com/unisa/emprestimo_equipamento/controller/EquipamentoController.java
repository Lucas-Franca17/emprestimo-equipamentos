package br.com.unisa.emprestimo_equipamento.controller;

import br.com.unisa.emprestimo_equipamento.model.Equipamento;
import br.com.unisa.emprestimo_equipamento.service.EquipamentoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public List<Equipamento> listarTodos(){
        return equipamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Equipamento buscarPorId(@PathVariable Long id){
        return equipamentoService.buscarPorId(id);
    }

    @PostMapping
    public Equipamento salvar(@RequestBody Equipamento equipamento){
        return equipamentoService.salvar(equipamento);
    }

    @PutMapping("/{id}")
    public Equipamento atualizar(
            @PathVariable Long id,
            @RequestBody Equipamento equipamento
    ){
        return equipamentoService.atualizar(id, equipamento);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        equipamentoService.excluir(id);
    }
    @GetMapping("/patrimonio/{patrimonio}")
    public Equipamento buscarPorPatrimonio(
            @PathVariable String patrimonio
    ) {
        return equipamentoService.buscarPorPatrimonioLinear(patrimonio);
    }

    @GetMapping("/ordenados")
    public List<Equipamento> listarOrdenados() {
        return equipamentoService.ordenarPorNome();
    }

    @GetMapping("/nome/{nome}")
    public Equipamento buscarPorNome(@PathVariable String nome
    ) {
        return equipamentoService.buscarPorNomeBinaria(nome);
    }

}
