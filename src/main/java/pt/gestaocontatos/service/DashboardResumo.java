package pt.gestaocontatos.service;

import java.math.BigDecimal;
import java.util.List;

import pt.gestaocontatos.model.Oportunidade;

/**
 * Dados agregados apresentados no painel inicial.
 */
public record DashboardResumo(
        long totalContatos,
        long totalOportunidades,
        long oportunidadesRecebidas,
        long oportunidadesEmAnalise,
        long oportunidadesConcluidas,
        BigDecimal valorPrevistoTotal,
        List<Oportunidade> oportunidadesRecentes) {
}
