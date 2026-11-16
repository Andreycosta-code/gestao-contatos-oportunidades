
package pt.gestaocontatos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Prioridade;

@Repository
public interface OportunidadeRepository
        extends JpaRepository<Oportunidade, Long> {

    List<Oportunidade> findByEstado(EstadoOportunidade estado);

    List<Oportunidade> findByPrioridade(Prioridade prioridade);

    List<Oportunidade> findByContatoId(Long contatoId);

    List<Oportunidade> findByDescricaoContainingIgnoreCase(
            String descricao);

    @EntityGraph(attributePaths = "contato")
    List<Oportunidade> findAllByOrderByDataRegistoDesc();

    @Override
    @EntityGraph(attributePaths = "contato")
    Optional<Oportunidade> findById(Long id);
}
