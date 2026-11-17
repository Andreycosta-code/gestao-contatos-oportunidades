
document.addEventListener("DOMContentLoaded", function () {
    const botao = document.getElementById("botao-contraste");

    if (!botao) {
        return;
    }

    function atualizarContraste(ativado) {
        document.body.classList.toggle("alto-contraste", ativado);

        botao.setAttribute("aria-pressed", String(ativado));
        botao.textContent = ativado
            ? "Desativar alto contraste"
            : "Ativar alto contraste";
    }

    const preferencia = localStorage.getItem("altoContraste") === "true";

    atualizarContraste(preferencia);

    botao.addEventListener("click", function () {
        const ativado = !document.body.classList.contains("alto-contraste");

        localStorage.setItem("altoContraste", String(ativado));
        atualizarContraste(ativado);
    });
});
