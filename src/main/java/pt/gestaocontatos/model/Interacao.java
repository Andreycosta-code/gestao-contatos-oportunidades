
package pt.gestaocontatos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "interacoes")
public class Interacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_interacao", nullable = false)
    private LocalDateTime dataInteracao;

    @NotBlank(message = "A descricao e obrigatoria.")
    @Size(max = 1000, message = "A descricao deve ter no maximo 1000 caracteres.")
    @Column(nullable = false, length = 1000)
    private String descricao;

    @NotNull(message = "O tipo de interacao e obrigatorio.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_interacao", nullable = false, length = 20)
    private TipoInteracao tipoInteracao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "id_oportunidade", nullable = false)
private Oportunidade oportunidade;

    public Interacao() {
    }

    @PrePersist
    protected void aoCriar() {
        if (dataInteracao == null) {
            dataInteracao = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataInteracao() {
        return dataInteracao;
    }

    public void setDataInteracao(LocalDateTime dataInteracao) {
        this.dataInteracao = dataInteracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoInteracao getTipoInteracao() {
        return tipoInteracao;
    }

    public void setTipoInteracao(TipoInteracao tipoInteracao) {
        this.tipoInteracao = tipoInteracao;
    }

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public void setOportunidade(Oportunidade oportunidade) {
        this.oportunidade = oportunidade;
    }
}
