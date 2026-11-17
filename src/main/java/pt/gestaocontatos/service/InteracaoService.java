
package pt.gestaocontatos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.gestaocontatos.model.Interacao;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.repository.InteracaoRepository;
import pt.gestaocontatos.repository.OportunidadeRepository;

@Service
public class InteracaoService {

    private final InteracaoRepository interacaoRepository;
    private final OportunidadeRepository oportunidadeRepository;

    public InteracaoService(
            InteracaoRepository interacaoRepository,
            OportunidadeRepository oportunidadeRepository) {

        this.interacaoRepository = interacaoRepository;
        this.oportunidadeRepository = oportunidadeRepository;
    }

    @Transactional
    public Interacao registrar(Interacao interacao) {

        if (interacao.getOportunidade() == null
                || interacao.getOportunidade().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário selecionar uma oportunidade válida.");
        }

        Long oportunidadeId = interacao.getOportunidade().getId();

        Oportunidade oportunidade = oportunidadeRepository
                .findById(oportunidadeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Oportunidade não encontrada."));

        interacao.setOportunidade(oportunidade);

        return interacaoRepository.save(interacao);
    }

    @Transactional(readOnly = true)
    public List<Interacao> listarPorOportunidade(Long oportunidadeId) {

        if (!oportunidadeRepository.existsById(oportunidadeId)) {
            throw new IllegalArgumentException(
                    "Oportunidade não encontrada.");
        }

        return interacaoRepository
                .findByOportunidadeIdOrderByDataInteracaoDesc(
                        oportunidadeId);
    }

    @Transactional
    public void excluir(Long id, Long oportunidadeId) {

        Interacao interacao = interacaoRepository
                .findByIdAndOportunidadeId(id, oportunidadeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Interação não encontrada nesta oportunidade."));

        interacaoRepository.delete(interacao);
    }

}
