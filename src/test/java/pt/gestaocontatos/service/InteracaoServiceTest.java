
package pt.gestaocontatos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.gestaocontatos.model.Interacao;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.TipoInteracao;
import pt.gestaocontatos.repository.InteracaoRepository;
import pt.gestaocontatos.repository.OportunidadeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteracaoServiceTest {

    @Mock
    private InteracaoRepository interacaoRepository;

    @Mock
    private OportunidadeRepository oportunidadeRepository;

    @InjectMocks
    private InteracaoService interacaoService;

    private Oportunidade criarOportunidade() {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setId(1L);
        oportunidade.setDescricao("Reparacao de muro exterior");
        return oportunidade;
    }

    private Interacao criarInteracao() {
        Interacao interacao = new Interacao();
        interacao.setDescricao("Cliente contactado por telefone");
        interacao.setTipoInteracao(TipoInteracao.CHAMADA);
        interacao.setOportunidade(criarOportunidade());
        return interacao;
    }

    @Test
    void deveRegistarInteracaoComSucesso() {
        Interacao interacao = criarInteracao();
        Oportunidade oportunidade = criarOportunidade();

        when(oportunidadeRepository.findById(1L))
                .thenReturn(Optional.of(oportunidade));
        when(interacaoRepository.save(interacao))
                .thenReturn(interacao);

        Interacao resultado = interacaoService.registrar(interacao);

        assertNotNull(resultado);
        assertEquals("Cliente contactado por telefone", resultado.getDescricao());
        assertEquals(TipoInteracao.CHAMADA, resultado.getTipoInteracao());
        assertEquals(oportunidade, resultado.getOportunidade());

        verify(interacaoRepository).save(interacao);
    }

    @Test
    void naoDeveRegistarInteracaoSemOportunidade() {
        Interacao interacao = criarInteracao();
        interacao.setOportunidade(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> interacaoService.registrar(interacao)
        );

        verify(interacaoRepository, never()).save(any(Interacao.class));
    }

    @Test
    void naoDeveRegistarInteracaoComOportunidadeInexistente() {
        Interacao interacao = criarInteracao();

        when(oportunidadeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> interacaoService.registrar(interacao)
        );

        verify(interacaoRepository, never()).save(any(Interacao.class));
    }

    @Test
    void deveListarInteracoesPorOportunidade() {
        List<Interacao> interacoes = List.of(criarInteracao());

        when(oportunidadeRepository.existsById(1L))
                .thenReturn(true);
        when(interacaoRepository
                .findByOportunidadeIdOrderByDataInteracaoDesc(1L))
                .thenReturn(interacoes);

        List<Interacao> resultado =
                interacaoService.listarPorOportunidade(1L);

        assertEquals(1, resultado.size());
        assertEquals(
                "Cliente contactado por telefone",
                resultado.get(0).getDescricao()
        );
    }

    @Test
    void naoDeveListarInteracoesDeOportunidadeInexistente() {
        when(oportunidadeRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> interacaoService.listarPorOportunidade(99L)
        );

        verify(interacaoRepository, never())
                .findByOportunidadeIdOrderByDataInteracaoDesc(99L);
    }

    @Test
    void deveExcluirInteracaoAssociadaAUmaOportunidade() {
        Interacao interacao = criarInteracao();
        when(interacaoRepository.findByIdAndOportunidadeId(1L, 1L))
                .thenReturn(Optional.of(interacao));

        assertDoesNotThrow(() -> interacaoService.excluir(1L, 1L));
        verify(interacaoRepository).delete(interacao);
    }

    @Test
    void naoDeveExcluirInteracaoDeOutraOportunidade() {
        when(interacaoRepository.findByIdAndOportunidadeId(1L, 2L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> interacaoService.excluir(1L, 2L));
        verify(interacaoRepository, never()).delete(any(Interacao.class));
    }

}
