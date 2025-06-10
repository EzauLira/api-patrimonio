document.addEventListener("DOMContentLoaded", () => {
  const elementos = {
    idProduto: document.getElementById("idProduto"),
    nomeProduto: document.getElementById("nomeProduto"),
    areaProduto: document.getElementById("areaProduto"),
    dataEntrada: document.getElementById("dataEntrada"),
    foto: document.getElementById("foto"),
    btnRestaurar: document.getElementById("restaurar"),
    btnVoltar: document.getElementById("voltar")
  };

  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie");

  if (!numSerie) {
    alert("Produto não encontrado.");
    window.location.href = "listarProdutosRemovidos.html";
    return;
  }

  const url = `http://localhost:8080/v1/controle/detalhe-produto-removido/${numSerie}`;

  fetch(url)
    .then(res => {
      if (!res.ok) throw new Error(`HTTP ${res.status}: ${res.statusText}`);
      if (!res.headers.get("content-type")?.includes("application/json"))
        throw new Error("Resposta da API não é JSON.");
      return res.json();
    })
    .then(produto => preencherDetalhes(produto))
    .catch(err => {
      console.error("Erro ao carregar detalhes:", err);
      alert("Não foi possível carregar os detalhes do produto.");
      window.location.href = "listarProdutosRemovidos.html";
    });

  elementos.btnVoltar?.addEventListener("click", () => {
    window.location.href = "listarProdutosRemovidos.html";
  });

  elementos.btnRestaurar?.addEventListener("click", () => {
    if (!confirm("Deseja restaurar este produto para os ativos?")) return;

    fetch(`http://localhost:8080/v1/controle/restaurar-produto/${numSerie}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" }
    })
      .then(res => {
        if (!res.ok) throw new Error("Erro ao restaurar o produto.");
        return res.json();
      })
      .then(data => {
        alert(data.message || "Produto restaurado com sucesso!");
        window.location.href = "listarProdutosRemovidos.html";
      })
      .catch(err => {
        console.error("Erro ao restaurar:", err);
        alert("Erro ao restaurar o produto.");
      });
  });

  function preencherDetalhes(produto) {
    elementos.idProduto.innerText = produto.numSerie || produto.numeroSerie || "-";
    elementos.nomeProduto.innerText = produto.name || produto.nome || "-";
    elementos.areaProduto.innerText = produto.area || "-";
    elementos.dataEntrada.innerText = produto.inputDate || produto.dataEntrada || "-";

    elementos.foto.innerHTML = "";

    const base64 = produto.foto?.startsWith("data:image/")
      ? produto.foto.split(",")[1]
      : produto.foto;

    if (base64) {
      const img = document.createElement("img");
      img.src = `data:image/png;base64,${base64}`;
      img.alt = "Imagem do produto";
      img.style.maxWidth = "100%";
      img.style.borderRadius = "8px";
      img.style.marginTop = "10px";
      elementos.foto.appendChild(img);
    } else {
      elementos.foto.innerText = "Nenhuma imagem disponível.";
    }
  }
});
