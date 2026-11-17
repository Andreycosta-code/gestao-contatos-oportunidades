
package pt.gestaocontatos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.Prioridade;
import pt.gestaocontatos.repository.ContatoRepository;
import pt.gestaocontatos.repository.OportunidadeRepository;
import pt.gestaocontatos.repository.InteracaoRepository;

@Service
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final ContatoRepository contatoRepository;
    private final InteracaoRepository interacaoRepository;

    public OportunidadeService(
            OportunidadeRepository oportunidadeRepository,
            ContatoRepository contatoRepository,
            InteracaoRepository interacaoRepository) {

        this.oportunidadeRepository = oportunidadeRepository;
        this.contatoRepository = contatoRepository;
        this.interacaoRepository = interacaoRepository;
    }

    @Transactional
    public Oportunidade cadastrar(Oportunidade oportunidade) {

        if (oportunidade.getContato() == null
                || oportunidade.getContato().getId() == null) {
            throw new IllegalArgumentException(
                    "Selecione um contacto válido.");
        }

        Contato contato = obterContatoValido(
                oportunidade.getContato().getId());

        oportunidade.setContato(contato);

        return oportunidadeRepository.save(oportunidade);
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> listarTodas() {
        return oportunidadeRepository.findAllByOrderByDataRegistoDesc();
    }

    @Transactional(readOnly = true)
    public Oportunidade buscarPorId(Long id) {
        return oportunidadeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Oportunidade não encontrada."));
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> buscarPorEstado(
            EstadoOportunidade estado) {
        return oportunidadeRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> buscarPorPrioridade(
            Prioridade prioridade) {
        return oportunidadeRepository.findByPrioridade(prioridade);
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> buscarPorContato(Long contatoId) {
        return oportunidadeRepository.findByContatoId(contatoId);
    }

    @Transactional
    public Oportunidade atualizar(Long id, Oportunidade dados) {

        Oportunidade oportunidade = buscarPorId(id);

        if (dados.getContato() == null
                || dados.getContato().getId() == null) {
            throw new IllegalArgumentException(
                    "Selecione um contacto válido.");
        }

        Contato contato = obterContatoValido(
                dados.getContato().getId());

        oportunidade.setDescricao(dados.getDescricao());
        oportunidade.setContato(contato);
        oportunidade.setEstado(dados.getEstado());
        oportunidade.setPrioridade(dados.getPrioridade());
        oportunidade.setPrazoResposta(dados.getPrazoResposta());
        oportunidade.setTempoEstimadoDias(
                dados.getTempoEstimadoDias());
        oportunidade.setValorAReceber(dados.getValorAReceber());

        return oportunidadeRepository.save(oportunidade);
    }

    @Transactional
    public void excluir(Long id) {

        if (!oportunidadeRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Oportunidade não encontrada.");
        }

        if (interacaoRepository.existsByOportunidadeId(id)) {
            throw new IllegalArgumentException(
                    "Não é possível eliminar esta oportunidade porque tem interações registadas. Elimine primeiro as interações, se necessário.");
        }

        oportunidadeRepository.deleteById(id);
    }

    private Contato obterContatoValido(Long contatoId) {
        return contatoRepository.findById(contatoId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Contacto não encontrado."));
    }
}
