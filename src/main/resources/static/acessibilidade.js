document.addEventListener("DOMContentLoaded", function () {
    const botao = document.getElementById("botao-contraste");

    if (!botao) {
        return;
    }

    const textoBotao = botao.querySelector("span");

    function atualizarContraste(ativado) {
        document.body.classList.toggle("alto-contraste", ativado);
        botao.setAttribute("aria-pressed", String(ativado));

        const texto = ativado
            ? "Desativar alto contraste"
            : "Ativar alto contraste";

        if (textoBotao) {
            textoBotao.textContent = texto;
        } else {
            botao.textContent = texto;
        }
    }

    let preferencia = false;
    try {
        preferencia = localStorage.getItem("altoContraste") === "true";
    } catch (erro) {
        // Se o armazenamento estiver indisponível, o botão continua funcional.
    }

    atualizarContraste(preferencia);

    botao.addEventListener("click", function () {
        const ativado = !document.body.classList.contains("alto-contraste");

        try {
            localStorage.setItem("altoContraste", String(ativado));
        } catch (erro) {
            // A preferência não será persistida, mas o contraste será aplicado.
        }

        atualizarContraste(ativado);
    });
});
