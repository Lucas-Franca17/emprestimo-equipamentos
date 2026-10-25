package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Usuario;
import br.com.unisa.emprestimo_equipamento.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.listarTodos();
    }


    public Usuario buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.buscarPorId(id);

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado."
            );
        }
        return usuario;

    }
}
