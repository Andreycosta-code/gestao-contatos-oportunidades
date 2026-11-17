package pt.gestaocontatos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Oportunidade;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private ContatoService contatoService;

    @Mock
    private OportunidadeService oportunidadeService;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void deveCalcularIndicadoresComDadosExistentes() {
        Contato contato = new Contato();
        contato.setNome("Cliente de teste");

        Oportunidade recebida = criarOportunidade(
                EstadoOportunidade.RECEBIDO, "250.00", contato);
        Oportunidade emAnalise = criarOportunidade(
                EstadoOportunidade.EM_ANALISE, "500.00", contato);
        Oportunidade concluida = criarOportunidade(
                EstadoOportunidade.CONCLUIDO, null, contato);

        when(contatoService.listarTodos()).thenReturn(List.of(contato));
        when(oportunidadeService.listarTodas())
                .thenReturn(List.of(recebida, emAnalise, concluida));

        DashboardResumo resumo = dashboardService.obterResumo();

        assertEquals(1, resumo.totalContatos());
        assertEquals(3, resumo.totalOportunidades());
        assertEquals(1, resumo.oportunidadesRecebidas());
        assertEquals(1, resumo.oportunidadesEmAnalise());
        assertEquals(1, resumo.oportunidadesConcluidas());
        assertEquals(new BigDecimal("750.00"), resumo.valorPrevistoTotal());
        assertEquals(3, resumo.oportunidadesRecentes().size());
    }

    @Test
    void deveDevolverIndicadoresAZeroQuandoNaoExistemRegistos() {
        when(contatoService.listarTodos()).thenReturn(List.of());
        when(oportunidadeService.listarTodas()).thenReturn(List.of());

        DashboardResumo resumo = dashboardService.obterResumo();

        assertEquals(0, resumo.totalContatos());
        assertEquals(0, resumo.totalOportunidades());
        assertEquals(0, resumo.oportunidadesRecebidas());
        assertEquals(0, resumo.oportunidadesEmAnalise());
        assertEquals(0, resumo.oportunidadesConcluidas());
        assertEquals(BigDecimal.ZERO, resumo.valorPrevistoTotal());
        assertEquals(0, resumo.oportunidadesRecentes().size());
    }

    private Oportunidade criarOportunidade(
            EstadoOportunidade estado, String valor, Contato contato) {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setDescricao("Trabalho de teste");
        oportunidade.setEstado(estado);
        oportunidade.setContato(contato);
        if (valor != null) {
            oportunidade.setValorAReceber(new BigDecimal(valor));
        }
        return oportunidade;
    }
}
