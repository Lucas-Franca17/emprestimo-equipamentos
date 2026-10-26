package br.com.unisa.emprestimo_equipamento.service;

import br.com.unisa.emprestimo_equipamento.model.Equipamento;
import br.com.unisa.emprestimo_equipamento.repository.EquipamentoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EquipamentoServiceTest {

    @Test
    void deveBuscarEquipamentoPorPatrimonio() {

        EquipamentoRepository repository =
                Mockito.mock(EquipamentoRepository.class);

        EquipamentoService service =
                new EquipamentoService(repository);

        List<Equipamento> equipamentos = List.of(
                new Equipamento(
                        1L,
                        "Notebook Dell",
                        "PAT001",
                        true
                ),
                new Equipamento(
                        2L,
                        "Projetor Epson",
                        "PAT002",
                        true
                )
        );

        Mockito.when(repository.listarTodos())
                .thenReturn(equipamentos);

        Equipamento resultado =
                service.buscarPorPatrimonioLinear("PAT001");

        assertNotNull(resultado);
        assertEquals("Notebook Dell", resultado.getNome());
        assertEquals("PAT001", resultado.getPatrimonio());
    }

    @Test
    void deveOrdenarEquipamentosPorNome() {

        EquipamentoRepository repository =
                Mockito.mock(EquipamentoRepository.class);

        EquipamentoService service =
                new EquipamentoService(repository);

        List<Equipamento> equipamentos = List.of(
                new Equipamento(
                        1L,
                        "Tablet Samsung",
                        "PAT003",
                        true
                ),
                new Equipamento(
                        2L,
                        "Notebook Dell",
                        "PAT001",
                        true
                ),
                new Equipamento(
                        3L,
                        "Projetor Epson",
                        "PAT002",
                        true
                )
        );

        Mockito.when(repository.listarTodos())
                .thenReturn(new java.util.ArrayList<>(equipamentos));

        List<Equipamento> resultado =
                service.ordenarPorNome();

        assertEquals("Notebook Dell", resultado.get(0).getNome());
        assertEquals("Projetor Epson", resultado.get(1).getNome());
        assertEquals("Tablet Samsung", resultado.get(2).getNome());
    }

    @Test
    void deveBuscarEquipamentoPorNomeComBuscaBinaria() {

        EquipamentoRepository repository =
                Mockito.mock(EquipamentoRepository.class);

        EquipamentoService service =
                new EquipamentoService(repository);

        List<Equipamento> equipamentos = new java.util.ArrayList<>(
                List.of(
                        new Equipamento(
                                1L,
                                "Tablet Samsung",
                                "PAT003",
                                true
                        ),
                        new Equipamento(
                                2L,
                                "Notebook Dell",
                                "PAT001",
                                true
                        ),
                        new Equipamento(
                                3L,
                                "Projetor Epson",
                                "PAT002",
                                true
                        )
                )
        );

        Mockito.when(repository.listarTodos())
                .thenReturn(equipamentos);

        Equipamento resultado =
                service.buscarPorNomeBinaria("Projetor Epson");

        assertNotNull(resultado);
        assertEquals("Projetor Epson", resultado.getNome());
        assertEquals("PAT002", resultado.getPatrimonio());
    }

}