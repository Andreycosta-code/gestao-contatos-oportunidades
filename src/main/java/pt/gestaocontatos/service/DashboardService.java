package pt.gestaocontatos.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Oportunidade;

/**
 * Reúne os indicadores do dashboard a partir dos serviços existentes.
 * Esta implementação é somente de leitura e não altera a base de dados.
 */
@Service
public class DashboardService {

    private final ContatoService contatoService;
    private final OportunidadeService oportunidadeService;

    public DashboardService(
            ContatoService contatoService,
            OportunidadeService oportunidadeService) {
        this.contatoService = contatoService;
        this.oportunidadeService = oportunidadeService;
    }

    @Transactional(readOnly = true)
    public DashboardResumo obterResumo() {
        var contatos = contatoService.listarTodos();
        List<Oportunidade> oportunidades = oportunidadeService.listarTodas();

        long recebidas = oportunidades.stream()
                .filter(o -> o.getEstado() == EstadoOportunidade.RECEBIDO)
                .count();

        long emAnalise = oportunidades.stream()
                .filter(o -> o.getEstado() == EstadoOportunidade.EM_ANALISE)
                .count();

        long concluidas = oportunidades.stream()
                .filter(o -> o.getEstado() == EstadoOportunidade.CONCLUIDO)
                .count();

        BigDecimal valorPrevisto = oportunidades.stream()
                .map(Oportunidade::getValorAReceber)
                .filter(valor -> valor != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Oportunidade> recentes = oportunidades.stream()
                .limit(5)
                .toList();

        return new DashboardResumo(
                contatos.size(),
                oportunidades.size(),
                recebidas,
                emAnalise,
                concluidas,
                valorPrevisto,
                recentes);
    }
}
