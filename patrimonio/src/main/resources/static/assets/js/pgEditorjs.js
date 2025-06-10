document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const numSerie = params.get("numSerie");

    if (!numSerie) {
        alert("Produto não encontrado.");
        window.location.href = "listarProdutos.html";
        return;
    }

    // === Elementos do DOM ===
    const elementos = {
        btnEditar: document.getElementById("editar"),
        btnVoltar: document.getElementById("voltar"),
        btnNovaFoto: document.getElementById("btnNovaFoto"),
        previewFoto: document.getElementById("previewFoto"),
        nomeProduto: document.getElementById("nomeProduto"),
        areaProduto: document.getElementById("areaProduto"),
        dataEntrada: document.getElementById("dataEntrada")
    };

    let fotoBase64 = null;

    // === Funções auxiliares ===

    /**
     * Formata data do padrão ISO (YYYY-MM-DD) para dd/MM/yyyy
     * @param {string} dataIso - Data no formato YYYY-MM-DD
     * @returns {string}
     */
    function formatarDataParaBr(dataIso) {
        if (!dataIso) return "";
        const [ano, mes, dia] = dataIso.split("-");
        return `${dia}/${mes}/${ano}`;
    }

    /**
     * Retorna a URL correta para voltar com base na origem
     * @returns {string}
     */
    function getUrlParaVoltar() {
        const origem = params.get("origem");
        const area = params.get("area");

        if (origem === "listaArea" && area) {
            return `listarPorArea.html?area=${encodeURIComponent(area)}`;
        } else if (origem === "scanner") {
            return "escanear.html";
        } else {
            return "listarProdutos.html";
        }
    }

    /**
     * Navega de volta com segurança
     */
    function voltarParaDetalhes() {
        const novaURL = new URL("detalhesProduto.html", window.location.origin);
        novaURL.searchParams.set("numSerie", numSerie);

        const area = params.get("area");
        const origem = params.get("origem");

        if (area) novaURL.searchParams.set("area", area);
        if (origem) novaURL.searchParams.set("origem", origem);

        window.location.href = novaURL.toString();
    }

    // === Evento: Voltar ===
    if (elementos.btnVoltar) {
        elementos.btnVoltar.addEventListener("click", () => {
            const url = getUrlParaVoltar();
            window.location.href = url;
        });
    }

    // === Evento: Nova Foto ===
    if (elementos.btnNovaFoto) {
        elementos.btnNovaFoto.addEventListener("click", (event) => {
            event.preventDefault();
            sessionStorage.setItem("produtoId", numSerie);
            const novaURL = new URL("cameraPgEdicao.html", window.location.origin);
            novaURL.searchParams.set("origem", "editor");
            window.location.href = novaURL.toString();
        });
    }

    // === Carregar dados do produto ===
    fetch(`http://localhost:8080/v1/controle/detalhes-produto/${numSerie}`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao carregar produto.");
            return response.json();
        })
        .then(produto => {
            elementos.nomeProduto.value = produto.name || "";
            elementos.areaProduto.value = produto.area || "";
            elementos.dataEntrada.value = produto.inputDate || produto.dataEntrada || "";

            // Mostra foto do banco, se não tiver nova foto
            if (!fotoBase64 && produto.foto && produto.foto.length > 0) {
                const imgSrc = `data:image/png;base64,${produto.foto}`;
                elementos.previewFoto.innerHTML = `
                    <img src="${imgSrc}" alt="Foto atual" style="max-width: 300px; border-radius: 8px; margin-top: 10px;">
                `;
            }
        })
        .catch(error => {
            console.error("Erro ao carregar produto:", error);
            alert("Erro ao carregar dados do produto.");
        });

    // === Verifica imagem salva após câmera ===
    const imagemProduto = sessionStorage.getItem("imagemProduto");
    if (imagemProduto) {
        fotoBase64 = imagemProduto.split(',')[1]; // Extrai só o base64 puro
        elementos.previewFoto.innerHTML = `
            <img src="${imagemProduto}" alt="Nova foto" style="max-width: 300px; border-radius: 8px; margin-top: 10px;">
        `;
        sessionStorage.removeItem("imagemProduto");
    }

    // === Evento: Salvar alterações ===
    if (elementos.btnEditar) {
        elementos.btnEditar.addEventListener("click", (event) => {
            event.preventDefault();

            const nome = elementos.nomeProduto.value.trim();
            const area = elementos.areaProduto.value.trim();
            const data = elementos.dataEntrada.value.trim();

            if (!nome || !area || !data) {
                alert("Preencha todos os campos.");
                return;
            }

            const dataFormatada = formatarDataParaBr(data); // Agora vai como "dd/MM/yyyy"

            const produtoAtualizado = {
                name: nome,
                area: area,
                inputDate: dataFormatada
            };

            if (fotoBase64) {
                produtoAtualizado.foto = fotoBase64;
            }

            fetch(`http://localhost:8080/v1/controle/alterar-produto/${numSerie}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(produtoAtualizado)
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao atualizar produto.");
                    return response.json();
                })
                .then(data => {
                    alert(data.message || "Produto atualizado com sucesso!");
                    voltarParaDetalhes();
                })
                .catch(error => {
                    console.error("Erro ao atualizar:", error);
                    alert("Erro ao salvar as alterações.");
                });
        });
    }
});