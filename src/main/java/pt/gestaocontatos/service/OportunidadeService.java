
package pt.gestaocontatos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Prioridade;
import pt.gestaocontatos.repository.OportunidadeRepository;
import pt.gestaocontatos.repository.ContatoRepository;

import java.util.List;

@Service
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final ContatoRepository contatoRepository;

    public OportunidadeService(
            OportunidadeRepository oportunidadeRepository,
            ContatoRepository contatoRepository) {
        this.oportunidadeRepository = oportunidadeRepository;
        this.contatoRepository = contatoRepository;
    }

    @Transactional
    public Oportunidade cadastrar(Oportunidade oportunidade) {
        Long contatoId = oportunidade.getContato().getId();

        Contato contato = contatoRepository.findById(contatoId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Contacto nao encontrado."));

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
                        new IllegalArgumentException("Oportunidade nao encontrada."));
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> buscarPorEstado(EstadoOportunidade estado) {
        return oportunidadeRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> buscarPorPrioridade(Prioridade prioridade) {
        return oportunidadeRepository.findByPrioridade(prioridade);
    }

    @Transactional(readOnly = true)
    public List<Oportunidade> buscarPorContato(Long contatoId) {
        return oportunidadeRepository.findByContatoId(contatoId);
    }

    @Transactional
    public Oportunidade atualizar(Long id, Oportunidade dados) {
        Oportunidade oportunidade = buscarPorId(id);

        oportunidade.setDescricao(dados.getDescricao());
        oportunidade.setEstado(dados.getEstado());
        oportunidade.setPrioridade(dados.getPrioridade());
        oportunidade.setPrazoResposta(dados.getPrazoResposta());
        oportunidade.setTempoEstimadoDias(dados.getTempoEstimadoDias());
        oportunidade.setValorAReceber(dados.getValorAReceber());

        return oportunidadeRepository.save(oportunidade);
    }

    @Transactional
    public void excluir(Long id) {
        if (!oportunidadeRepository.existsById(id)) {
            throw new IllegalArgumentException("Oportunidade nao encontrada.");
        }

        oportunidadeRepository.deleteById(id);
    }
}
