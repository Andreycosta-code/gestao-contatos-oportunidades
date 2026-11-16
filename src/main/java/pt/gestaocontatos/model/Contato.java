
package pt.gestaocontatos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome e obrigatorio.")
    @Size(max = 100, message = "O nome deve ter no maximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O telefone e obrigatorio.")
    @Size(max = 30, message = "O telefone deve ter no maximo 30 caracteres.")
    @Column(nullable = false, length = 30)
    private String telefone;

    @Email(message = "Informe um endereco de email valido.")
    @Size(max = 150, message = "O email deve ter no maximo 150 caracteres.")
    @Column(length = 150)
    private String email;

    @NotNull(message = "O tipo de contato e obrigatorio.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contacto", nullable = false, length = 20)
    private TipoContacto tipoContacto;

    public Contato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoContacto getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
    }
}
