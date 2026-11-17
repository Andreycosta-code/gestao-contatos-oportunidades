
package pt.gestaocontatos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.repository.ContatoRepository;
import pt.gestaocontatos.repository.OportunidadeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final OportunidadeRepository oportunidadeRepository;

    public ContatoService(ContatoRepository contatoRepository,
                          OportunidadeRepository oportunidadeRepository) {
        this.contatoRepository = contatoRepository;
        this.oportunidadeRepository = oportunidadeRepository;
    }

    @Transactional
    public Contato cadastrar(Contato contato) {

        if (contatoRepository.existsByTelefone(contato.getTelefone())) {
            throw new IllegalArgumentException(
                "Ja existe um contato cadastrado com este telefone."
            );
        }

        return contatoRepository.save(contato);
    }

    @Transactional(readOnly = true)
    public List<Contato> listarTodos() {
        return contatoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Contato> buscarPorId(Long id) {
        return contatoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Contato> buscarPorNome(String nome) {
        return contatoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Contato atualizar(Long id, Contato dadosAtualizados) {

        Contato contato = contatoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "Contato nao encontrado."
            ));

        if (!contato.getTelefone().equals(dadosAtualizados.getTelefone())
                && contatoRepository.existsByTelefone(dadosAtualizados.getTelefone())) {
            throw new IllegalArgumentException(
                "Ja existe outro contato cadastrado com este telefone."
            );
        }

        contato.setNome(dadosAtualizados.getNome());
        contato.setTelefone(dadosAtualizados.getTelefone());
        contato.setEmail(dadosAtualizados.getEmail());
        contato.setTipoContacto(dadosAtualizados.getTipoContacto());

        return contatoRepository.save(contato);
    }

    @Transactional
    public void excluir(Long id) {

        if (!contatoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "Contato nao encontrado."
            );
        }

        if (oportunidadeRepository.existsByContatoId(id)) {
            throw new IllegalArgumentException(
                "Nao e possivel eliminar este contacto porque existem oportunidades associadas."
            );
        }

        contatoRepository.deleteById(id);
    }
}
