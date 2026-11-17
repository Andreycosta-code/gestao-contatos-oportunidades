
package pt.gestaocontatos.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.TipoContacto;
import pt.gestaocontatos.service.ContatoService;

@Controller
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            Model model) {

        if (nome != null && !nome.isBlank()) {
            model.addAttribute(
                    "contatos",
                    contatoService.buscarPorNome(nome)
            );
        } else {
            model.addAttribute(
                    "contatos",
                    contatoService.listarTodos()
            );
        }

        model.addAttribute("nomePesquisa", nome);

        return "contatos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("contato", new Contato());
        model.addAttribute("tiposContacto", TipoContacto.values());

        return "contatos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("contato") Contato contato,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            model.addAttribute("tiposContacto", TipoContacto.values());
            return "contatos/formulario";
        }

        try {
            contatoService.cadastrar(contato);
            return "redirect:/contatos?sucesso";
        } catch (IllegalArgumentException erro) {
            model.addAttribute("tiposContacto", TipoContacto.values());
            model.addAttribute("erro", erro.getMessage());

            return "contatos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Contato contato = contatoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Contacto nao encontrado."));
        model.addAttribute("contato", contato);
        model.addAttribute("tiposContacto", TipoContacto.values());
        model.addAttribute("edicao", true);
        return "contatos/formulario";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("contato") Contato contato,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            contato.setId(id);
            model.addAttribute("tiposContacto", TipoContacto.values());
            model.addAttribute("edicao", true);
            return "contatos/formulario";
        }

        try {
            contatoService.atualizar(id, contato);
            return "redirect:/contatos?atualizado";
        } catch (IllegalArgumentException erro) {
            contato.setId(id);
            model.addAttribute("tiposContacto", TipoContacto.values());
            model.addAttribute("edicao", true);
            model.addAttribute("erro", erro.getMessage());
            return "contatos/formulario";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        try {
            contatoService.excluir(id);
            return "redirect:/contatos?excluido";
        } catch (IllegalArgumentException erro) {
            return "redirect:/contatos?erro=" +
                    java.net.URLEncoder.encode(erro.getMessage(), java.nio.charset.StandardCharsets.UTF_8);
        }
    }

}
