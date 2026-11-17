
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

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.Oportunidade;
import pt.gestaocontatos.model.EstadoOportunidade;
import pt.gestaocontatos.model.Prioridade;
import pt.gestaocontatos.service.OportunidadeService;
import pt.gestaocontatos.service.ContatoService;

@Controller
@RequestMapping("/oportunidades")
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;
    private final ContatoService contatoService;

    public OportunidadeController(
            OportunidadeService oportunidadeService,
            ContatoService contatoService) {

        this.oportunidadeService = oportunidadeService;
        this.contatoService = contatoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute(
                "oportunidades",
                oportunidadeService.listarTodas()
        );

        return "oportunidades/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {

        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setContato(new Contato());

        model.addAttribute("oportunidade", oportunidade);

        carregarDadosFormulario(model);

        return "oportunidades/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("oportunidade")
            Oportunidade oportunidade,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            carregarDadosFormulario(model);
            return "oportunidades/formulario";
        }

        try {
            oportunidadeService.cadastrar(oportunidade);

            return "redirect:/oportunidades?sucesso";

        } catch (IllegalArgumentException erro) {

            carregarDadosFormulario(model);
            model.addAttribute("erro", erro.getMessage());

            return "oportunidades/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        try {
            model.addAttribute(
                    "oportunidade",
                    oportunidadeService.buscarPorId(id)
            );

            carregarDadosFormulario(model);

            return "oportunidades/formulario";

        } catch (IllegalArgumentException erro) {

            return "redirect:/oportunidades?erro";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("oportunidade")
            Oportunidade oportunidade,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            carregarDadosFormulario(model);
            return "oportunidades/formulario";
        }

        try {
            oportunidadeService.atualizar(id, oportunidade);

            return "redirect:/oportunidades?atualizada";

        } catch (IllegalArgumentException erro) {

            carregarDadosFormulario(model);
            model.addAttribute("erro", erro.getMessage());

            return "oportunidades/formulario";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        try {
            oportunidadeService.excluir(id);
            return "redirect:/oportunidades?eliminada";
        } catch (IllegalArgumentException erro) {
            return "redirect:/oportunidades?erroExclusao";
        }
    }

    private void carregarDadosFormulario(Model model) {

        model.addAttribute(
                "contatos",
                contatoService.listarTodos()
        );

        model.addAttribute(
                "estados",
                EstadoOportunidade.values()
        );

        model.addAttribute(
                "prioridades",
                Prioridade.values()
        );
    }
}
