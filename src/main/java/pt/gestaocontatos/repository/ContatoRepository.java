
package pt.gestaocontatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.TipoContacto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    Optional<Contato> findByTelefone(String telefone);

    List<Contato> findByNomeContainingIgnoreCase(String nome);

    List<Contato> findByTipoContacto(TipoContacto tipoContacto);

    boolean existsByTelefone(String telefone);
}
