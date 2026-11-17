
package pt.gestaocontatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.gestaocontatos.model.Interacao;

import java.util.List;

@Repository
public interface InteracaoRepository extends JpaRepository<Interacao, Long> {

    List<Interacao> findByOportunidadeIdOrderByDataInteracaoDesc(Long oportunidadeId);

    boolean existsByOportunidadeId(Long oportunidadeId);

    java.util.Optional<Interacao> findByIdAndOportunidadeId(Long id, Long oportunidadeId);
}
