
package pt.gestaocontatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Prioridade;

import java.util.List;

@Repository
public interface OportunidadeRepository
        extends JpaRepository<Oportunidade, Long> {

    List<Oportunidade> findByEstado(EstadoOportunidade estado);

    List<Oportunidade> findByPrioridade(Prioridade prioridade);

    List<Oportunidade> findByContatoId(Long contatoId);

    List<Oportunidade> findByDescricaoContainingIgnoreCase(String descricao);

    List<Oportunidade> findAllByOrderByDataRegistoDesc();
}
