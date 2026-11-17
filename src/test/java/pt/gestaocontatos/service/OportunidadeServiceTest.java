
package pt.gestaocontatos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.Prioridade;
import pt.gestaocontatos.repository.ContatoRepository;
import pt.gestaocontatos.repository.OportunidadeRepository;
import pt.gestaocontatos.repository.InteracaoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OportunidadeServiceTest {

    @Mock
    private OportunidadeRepository oportunidadeRepository;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private InteracaoRepository interacaoRepository;

    @InjectMocks
    private OportunidadeService oportunidadeService;

    private Contato criarContato() {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("Joao Teste");
        contato.setTelefone("910000001");
        contato.setTipoContacto(
                pt.gestaocontatos.model.TipoContacto.CLIENTE
        );
        return contato;
    }

    private Oportunidade criarOportunidade() {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setDescricao("Reparacao de muro exterior");
        oportunidade.setEstado(EstadoOportunidade.RECEBIDO);
        oportunidade.setPrioridade(Prioridade.MEDIA);
        oportunidade.setContato(criarContato());
        oportunidade.setValorAReceber(new BigDecimal("450.00"));
        return oportunidade;
    }

    @Test
    void deveCadastrarOportunidadeComSucesso() {
        Oportunidade oportunidade = criarOportunidade();
        Contato contato = criarContato();

        when(contatoRepository.findById(1L))
                .thenReturn(Optional.of(contato));
        when(oportunidadeRepository.save(oportunidade))
                .thenReturn(oportunidade);

        Oportunidade resultado =
                oportunidadeService.cadastrar(oportunidade);

        assertNotNull(resultado);
        assertEquals("Reparacao de muro exterior", resultado.getDescricao());
        assertEquals(contato, resultado.getContato());

        verify(oportunidadeRepository).save(oportunidade);
    }

    @Test
    void naoDeveCadastrarOportunidadeSemContato() {
        Oportunidade oportunidade = criarOportunidade();
        oportunidade.setContato(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> oportunidadeService.cadastrar(oportunidade)
        );

        verify(oportunidadeRepository, never())
                .save(any(Oportunidade.class));
    }

    @Test
    void naoDeveCadastrarOportunidadeComContatoInexistente() {
        Oportunidade oportunidade = criarOportunidade();

        when(contatoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> oportunidadeService.cadastrar(oportunidade)
        );

        verify(oportunidadeRepository, never())
                .save(any(Oportunidade.class));
    }

    @Test
    void deveListarTodasAsOportunidades() {
        List<Oportunidade> oportunidades =
                List.of(criarOportunidade());

        when(oportunidadeRepository.findAllByOrderByDataRegistoDesc())
                .thenReturn(oportunidades);

        List<Oportunidade> resultado =
                oportunidadeService.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals(
                "Reparacao de muro exterior",
                resultado.get(0).getDescricao()
        );
    }

    @Test
    void deveBuscarOportunidadePorId() {
        Oportunidade oportunidade = criarOportunidade();

        when(oportunidadeRepository.findById(1L))
                .thenReturn(Optional.of(oportunidade));

        Oportunidade resultado =
                oportunidadeService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(oportunidade, resultado);
    }

    @Test
    void naoDeveBuscarOportunidadeInexistente() {
        when(oportunidadeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> oportunidadeService.buscarPorId(99L)
        );
    }

    @Test
    void deveBuscarOportunidadesPorEstado() {
        List<Oportunidade> oportunidades =
                List.of(criarOportunidade());

        when(oportunidadeRepository.findByEstado(
                EstadoOportunidade.RECEBIDO
        )).thenReturn(oportunidades);

        List<Oportunidade> resultado =
                oportunidadeService.buscarPorEstado(
                        EstadoOportunidade.RECEBIDO
                );

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarOportunidadesPorPrioridade() {
        List<Oportunidade> oportunidades =
                List.of(criarOportunidade());

        when(oportunidadeRepository.findByPrioridade(
                Prioridade.MEDIA
        )).thenReturn(oportunidades);

        List<Oportunidade> resultado =
                oportunidadeService.buscarPorPrioridade(
                        Prioridade.MEDIA
                );

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarOportunidadesPorContato() {
        List<Oportunidade> oportunidades =
                List.of(criarOportunidade());

        when(oportunidadeRepository.findByContatoId(1L))
                .thenReturn(oportunidades);

        List<Oportunidade> resultado =
                oportunidadeService.buscarPorContato(1L);

        assertEquals(1, resultado.size());
    }

    @Test
    void deveAtualizarOportunidadeComSucesso() {
        Oportunidade existente = criarOportunidade();
        Oportunidade dadosAtualizados = criarOportunidade();
        Contato contato = criarContato();

        dadosAtualizados.setDescricao("Reparacao de telhado");
        dadosAtualizados.setEstado(EstadoOportunidade.EM_ANALISE);
        dadosAtualizados.setPrioridade(Prioridade.ALTA);
        dadosAtualizados.setValorAReceber(
                new BigDecimal("600.00")
        );

        when(oportunidadeRepository.findById(1L))
                .thenReturn(Optional.of(existente));
        when(contatoRepository.findById(1L))
                .thenReturn(Optional.of(contato));
        when(oportunidadeRepository.save(existente))
                .thenReturn(existente);

        Oportunidade resultado =
                oportunidadeService.atualizar(1L, dadosAtualizados);

        assertEquals("Reparacao de telhado", resultado.getDescricao());
        assertEquals(
                EstadoOportunidade.EM_ANALISE,
                resultado.getEstado()
        );
        assertEquals(Prioridade.ALTA, resultado.getPrioridade());
        assertEquals(
                new BigDecimal("600.00"),
                resultado.getValorAReceber()
        );

        verify(oportunidadeRepository).save(existente);
    }

    @Test
    void naoDeveAtualizarOportunidadeInexistente() {
        when(oportunidadeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> oportunidadeService.atualizar(
                        99L,
                        criarOportunidade()
                )
        );

        verify(oportunidadeRepository, never())
                .save(any(Oportunidade.class));
    }

    @Test
    void deveExcluirOportunidadeExistente() {
        when(oportunidadeRepository.existsById(1L))
                .thenReturn(true);
        when(interacaoRepository.existsByOportunidadeId(1L))
                .thenReturn(false);

        assertDoesNotThrow(
                () -> oportunidadeService.excluir(1L)
        );

        verify(oportunidadeRepository).deleteById(1L);
    }

    @Test
    void naoDeveExcluirOportunidadeInexistente() {
        when(oportunidadeRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> oportunidadeService.excluir(99L)
        );

        verify(oportunidadeRepository, never()).deleteById(99L);
    }
    @Test
    void naoDeveExcluirOportunidadeComInteracoesRegistadas() {
        when(oportunidadeRepository.existsById(1L)).thenReturn(true);
        when(interacaoRepository.existsByOportunidadeId(1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> oportunidadeService.excluir(1L));

        verify(oportunidadeRepository, never()).deleteById(1L);
    }

}
