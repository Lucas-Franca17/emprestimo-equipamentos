package br.com.unisa.emprestimo_equipamento.model;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;

import java.time.LocalDateTime;

public class Emprestimo {

    private Long id;
    private Long usuarioId;
    private Long equipamentoId;
    private LocalDateTime dataRegistro;
    private LocalDateTime dataDevolucao;
    private String situacao;

    public Emprestimo(){}

    public Emprestimo(Long id, Long usuarioId, Long equipamentoId,
                      LocalDateTime dataRegistro, LocalDateTime dataDevolucao, String situacao){

        this.id = id;
        this.usuarioId = usuarioId;
        this.equipamentoId = equipamentoId;
        this.dataRegistro = dataRegistro;
        this.dataDevolucao = dataDevolucao;
        this.situacao = situacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(Long equipamentoId) {
        this.equipamentoId = equipamentoId;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
