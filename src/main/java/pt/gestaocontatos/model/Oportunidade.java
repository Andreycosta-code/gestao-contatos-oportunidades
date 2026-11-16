
package pt.gestaocontatos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "oportunidades")
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descricao e obrigatoria.")
    @Size(max = 500, message = "A descricao deve ter no maximo 500 caracteres.")
    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "data_registo", nullable = false, updatable = false)
    private LocalDateTime dataRegisto;

    @NotNull(message = "O estado e obrigatorio.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoOportunidade estado;

    @NotNull(message = "A prioridade e obrigatoria.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Prioridade prioridade;

    @Column(name = "prazo_resposta")
    private LocalDate prazoResposta;

    @Column(name = "tempo_estimado_dias")
    private Integer tempoEstimadoDias;

    @DecimalMin(value = "0.0", message = "O valor nao pode ser negativo.")
    @Column(name = "valor_a_receber", precision = 12, scale = 2)
    private BigDecimal valorAReceber;

    @NotNull(message = "O contacto e obrigatorio.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_contacto", nullable = false)
    private Contato contato;

    public Oportunidade() {
    }

    @PrePersist
    protected void aoCriar() {
        if (dataRegisto == null) {
            dataRegisto = LocalDateTime.now();
        }

        if (estado == null) {
            estado = EstadoOportunidade.RECEBIDO;
        }

        if (prioridade == null) {
            prioridade = Prioridade.MEDIA;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public EstadoOportunidade getEstado() {
        return estado;
    }

    public void setEstado(EstadoOportunidade estado) {
        this.estado = estado;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getPrazoResposta() {
        return prazoResposta;
    }

    public void setPrazoResposta(LocalDate prazoResposta) {
        this.prazoResposta = prazoResposta;
    }

    public Integer getTempoEstimadoDias() {
        return tempoEstimadoDias;
    }

    public void setTempoEstimadoDias(Integer tempoEstimadoDias) {
        this.tempoEstimadoDias = tempoEstimadoDias;
    }

    public BigDecimal getValorAReceber() {
        return valorAReceber;
    }

    public void setValorAReceber(BigDecimal valorAReceber) {
        this.valorAReceber = valorAReceber;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}
