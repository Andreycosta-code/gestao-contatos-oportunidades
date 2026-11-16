
package pt.gestaocontatos.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.gestaocontatos.model.Interacao;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.TipoInteracao;
import pt.gestaocontatos.service.InteracaoService;
import pt.gestaocontatos.service.OportunidadeService;

@Controller
@RequestMapping("/interacoes")
public class InteracaoController {

    private final InteracaoService interacaoService;
    private final OportunidadeService oportunidadeService;

    public InteracaoController(
            InteracaoService interacaoService,
            OportunidadeService oportunidadeService) {

        this.interacaoService = interacaoService;
        this.oportunidadeService = oportunidadeService;
    }

    @GetMapping("/oportunidade/{oportunidadeId}")
    public String listar(
            @PathVariable Long oportunidadeId,
            Model model) {

        try {
            carregarDadosPagina(oportunidadeId, model);
            return "interacoes/lista";

        } catch (IllegalArgumentException erro) {
            return "redirect:/oportunidades?erro";
        }
    }

    @PostMapping("/salvar/{oportunidadeId}")
    public String salvar(
            @PathVariable Long oportunidadeId,
            @Valid @ModelAttribute("interacao") Interacao interacao,
            BindingResult resultado,
            Model model) {

        Oportunidade oportunidade;

        try {
            oportunidade = oportunidadeService.buscarPorId(oportunidadeId);

        } catch (IllegalArgumentException erro) {
            return "redirect:/oportunidades?erro";
        }

        if (resultado.hasErrors()) {
            carregarDadosPagina(oportunidade, oportunidadeId, model);
            return "interacoes/lista";
        }

        try {
            interacao.setOportunidade(oportunidade);
            interacaoService.registrar(interacao);

            return "redirect:/interacoes/oportunidade/"
                    + oportunidadeId + "?sucesso";

        } catch (IllegalArgumentException erro) {
            carregarDadosPagina(oportunidade, oportunidadeId, model);
            model.addAttribute("erro", erro.getMessage());

            return "interacoes/lista";
        }
    }

    private void carregarDadosPagina(
            Long oportunidadeId,
            Model model) {

        Oportunidade oportunidade =
                oportunidadeService.buscarPorId(oportunidadeId);

        carregarDadosPagina(oportunidade, oportunidadeId, model);
    }

    private void carregarDadosPagina(
            Oportunidade oportunidade,
            Long oportunidadeId,
            Model model) {

        model.addAttribute("oportunidade", oportunidade);

        model.addAttribute(
                "interacoes",
                interacaoService.listarPorOportunidade(oportunidadeId)
        );

        model.addAttribute("interacao", new Interacao());

        model.addAttribute(
                "tiposInteracao",
                TipoInteracao.values()
        );
    }
}
