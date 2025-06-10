document.addEventListener("DOMContentLoaded", () => {
  const elementos = {
    idProduto: document.getElementById("idProduto"),
    nomeProduto: document.getElementById("nomeProduto"),
    areaProduto: document.getElementById("areaProduto"),
    dataEntrada: document.getElementById("dataEntrada"),
    foto: document.getElementById("foto"),
    btnEditar: document.getElementById("editar"),
    btnRemover: document.getElementById("remover"),
    btnVoltar: document.getElementById("voltar")
  };

  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie") || params.get("id");
  const area = params.get("area");
  const origem = params.get("origem");

  if (!numSerie) {
    alert("Produto não encontrado.");
    return;
  }

  const url = origem === "removidos"
    ? `http://localhost:8080/v1/controle/detalhe-produto-removido/${numSerie}`
    : `http://localhost:8080/v1/controle/detalhes-produto/${numSerie}`;

  fetch(url)
    .then(res => {
      if (!res.ok) throw new Error("Erro ao buscar detalhes do produto.");
      return res.json();
    })
    .then(preencherDetalhes)
    .catch(err => {
      console.error("Erro:", err);
      elementos.foto.innerText = "Erro ao carregar os detalhes.";
    });

  elementos.btnVoltar.addEventListener("click", () => {
      window.location.href = "scanner.html";
  });

  elementos.btnEditar.addEventListener("click", () => {
    const url = new URL("pgEditor.html", window.location.origin);
    url.searchParams.set("numSerie", numSerie);
    if (area) url.searchParams.set("area", area);
    if (origem) url.searchParams.set("origem", origem);
    window.location.href = url.toString();
  });

  elementos.btnRemover.addEventListener("click", removerProduto);

  function preencherDetalhes(produto) {
    elementos.idProduto.innerText = produto.numSerie || produto.numeroSerie || "-";
    elementos.nomeProduto.innerText = produto.name || produto.nome || "-";
    elementos.areaProduto.innerText = produto.area || "-";
    elementos.dataEntrada.innerText = produto.inputDate || produto.dataEntrada || "-";

    elementos.foto.innerHTML = "";

    if (produto.foto?.length > 0) {
      const img = document.createElement("img");
      img.src = `data:image/png;base64,${produto.foto}`;
      img.alt = "Imagem do produto";
      img.style.maxWidth = "100%";
      img.style.borderRadius = "8px";
      img.style.marginTop = "10px";
      elementos.foto.appendChild(img);
    } else {
      elementos.foto.innerText = "Nenhuma imagem disponível.";
    }
  }

  function removerProduto() {
    if (!confirm("Tem certeza que deseja remover este produto?")) return;

    fetch(`http://localhost:8080/v1/controle/remover-produto/${numSerie}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" }
    })
      .then(res => {
        if (!res.ok) throw new Error("Erro ao remover o produto.");
        return res.json();
      })
      .then(data => {
        alert(data.message || "Produto removido com sucesso!");
        window.location.href = "listarProdutos.html";
      })
      .catch(err => {
        console.error("Erro ao remover:", err);
        alert("Erro ao remover o produto.");
      });
  }
});