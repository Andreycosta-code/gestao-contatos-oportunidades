package pt.gestaocontatos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pt.gestaocontatos.service.DashboardService;

@Controller
public class HomeController {

    private final DashboardService dashboardService;

    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("resumo", dashboardService.obterResumo());
        return "dashboard";
    }
}
