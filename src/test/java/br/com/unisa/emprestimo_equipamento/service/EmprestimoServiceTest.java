package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Emprestimo;
import br.com.unisa.emprestimo_equipamento.repository.EmprestimoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmprestimoServiceTest {

    @Test
    void naoDeveEmprestarEquipamentoIndisponivel() {

        EmprestimoRepository repository =
                Mockito.mock(EmprestimoRepository.class);

        EmprestimoService service =
                new EmprestimoService(repository);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuarioId(1L);
        emprestimo.setEquipamentoId(1L);

        Mockito.when(repository.registrar(1L, 1L))
                .thenThrow(
                        new IllegalArgumentException(
                                "Equipamento indisponível."
                        )
                );

        ResponseStatusException excecao =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.registrar(emprestimo)
                );

        assertEquals(409, excecao.getStatusCode().value());
        assertEquals(
                "Equipamento indisponível.",
                excecao.getReason()
        );
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoExistir() {

        EmprestimoRepository repository =
                Mockito.mock(EmprestimoRepository.class);

        EmprestimoService service =
                new EmprestimoService(repository);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuarioId(999L);
        emprestimo.setEquipamentoId(1L);

        Mockito.when(repository.registrar(999L, 1L))
                .thenThrow(
                        new IllegalArgumentException(
                                "Usuário não encontrado."
                        )
                );

        ResponseStatusException excecao =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.registrar(emprestimo)
                );

        assertEquals(404, excecao.getStatusCode().value());
        assertEquals(
                "Usuário não encontrado.",
                excecao.getReason()
        );
    }

    @Test
    void deveRetornar409QuandoEmprestimoJaEstiverDevolvido() {

        EmprestimoRepository repository =
                Mockito.mock(EmprestimoRepository.class);

        EmprestimoService service =
                new EmprestimoService(repository);

        Mockito.when(repository.devolver(1L))
                .thenThrow(
                        new IllegalArgumentException(
                                "Empréstimo já foi devolvido."
                        )
                );

        ResponseStatusException excecao =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.devolver(1L)
                );

        assertEquals(409, excecao.getStatusCode().value());
        assertEquals(
                "Empréstimo já foi devolvido.",
                excecao.getReason()
        );
    }

    @Test
    void deveRetornar404QuandoEmprestimoNaoExistir() {

        EmprestimoRepository repository =
                Mockito.mock(EmprestimoRepository.class);

        EmprestimoService service =
                new EmprestimoService(repository);

        Mockito.when(repository.devolver(999L))
                .thenThrow(
                        new IllegalArgumentException(
                                "Empréstimo não encontrado."
                        )
                );

        ResponseStatusException excecao =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.devolver(999L)
                );

        assertEquals(404, excecao.getStatusCode().value());
        assertEquals(
                "Empréstimo não encontrado.",
                excecao.getReason()
        );
    }

}