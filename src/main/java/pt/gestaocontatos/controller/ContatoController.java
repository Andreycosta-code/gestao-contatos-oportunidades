
package pt.gestaocontatos.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        @org.springframework.web.bind.annotation.RequestParam(
                required = false) String nome,
        Model model) {

    if (nome != null && !nome.isBlank()) {
        model.addAttribute("contatos",
                contatoService.buscarPorNome(nome));
    } else {
        model.addAttribute("contatos",
                contatoService.listarTodos());
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
}
