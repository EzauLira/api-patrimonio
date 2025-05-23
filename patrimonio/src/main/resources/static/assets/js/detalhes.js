document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie");
  const area = params.get("area");
  const origem = params.get("origem");

  if (!numSerie) {
    alert("Produto não encontrado.");
    return;
  }

  // Define a URL correta com base na origem
  let url;
  if (origem === "removidos") {
    url = `http://localhost:8080/v1/controle/detalhe-produto-removido/${numSerie}`;
  } else {
    url = `http://localhost:8080/v1/controle/detalhes-produto/${numSerie}`;
  }

  // Faz a requisição
  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar detalhes do produto.");
      return response.json();
    })
    .then(produto => preencherDetalhes(produto))
    .catch(error => {
      console.error("Erro:", error);
      document.getElementById("detalhes-ativos").innerText = "Erro ao carregar os detalhes.";
    });

  // Configura o botão "voltar"
  const voltarButton = document.getElementById("voltar");
  voltarButton.addEventListener("click", () => {
    if (origem === "listaArea") {
      window.location.href = `listarPorArea.html?area=${encodeURIComponent(area)}`;
    } else if (origem === "removidos") {
      window.location.href = "listarProdutosRemovidos.html";
    } else {
      window.location.href = "listarProdutos.html";
    }
  });
});

function preencherDetalhes(produto) {
  document.getElementById("idProduto").innerText = produto.numSerie;
  document.getElementById("nomeProduto").innerText = produto.name;
  document.getElementById("areaProduto").innerText = produto.area;
  document.getElementById("dataEntrada").innerText = produto.inputDate;
}
