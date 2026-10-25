package br.com.unisa.emprestimo_equipamento.controller;

import br.com.unisa.emprestimo_equipamento.model.Equipamento;
import br.com.unisa.emprestimo_equipamento.model.Usuario;
import br.com.unisa.emprestimo_equipamento.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarTodos(){
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario
    buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }


}
