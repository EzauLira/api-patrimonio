document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie");

  if (!numSerie) {
    alert("Produto não encontrado.");
    return;
  }

  fetch(`http://localhost:8080/v1/controle/detalhe-produto-removido/${numSerie}`)
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar detalhes do produto.");
      return response.json();
    })
    .then(produto => preencherDetalhes(produto))
    .catch(error => {
      console.error("Erro:", error);
      alert("Erro ao carregar detalhes do produto.");
    });

  document.getElementById("voltar").addEventListener("click", () => {
    window.location.href = "listarProdutosRemovidos.html";
  });

  document.getElementById("restaurar").addEventListener("click", () => {
    if (!confirm("Deseja restaurar este produto para os ativos?")) return;

    fetch(`http://localhost:8080/v1/controle/restaurar-produto/${numSerie}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => {
        if (!response.ok) throw new Error("Erro ao restaurar produto.");
        return response.json();
      })
      .then(data => {
        alert(data.message || "Produto restaurado com sucesso!");
        window.location.href = "listarProdutosRemovidos.html";
      })
      .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao restaurar o produto.");
      });
  });
});

function preencherDetalhes(produto) {
  document.getElementById("idProduto").innerText = produto.numSerie;
  document.getElementById("nomeProduto").innerText = produto.name;
  document.getElementById("areaProduto").innerText = produto.area;
  document.getElementById("dataEntrada").innerText = produto.inputDate;
}

document.getElementById("editar").addEventListener("click", () => {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie");
  const area = params.get("area");
  const origem = params.get("origem");

  const novaURL = new URL("pgEditor.html", window.location.origin);
  novaURL.searchParams.set("numSerie", numSerie);
  if (area) novaURL.searchParams.set("area", area);
  if (origem) novaURL.searchParams.set("origem", origem);

  window.location.href = novaURL.toString();
});
