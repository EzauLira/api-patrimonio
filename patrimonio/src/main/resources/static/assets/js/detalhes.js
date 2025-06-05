document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie") || params.get("id"); // Suporte tanto para botões como QR Code
  const area = params.get("area");
  const origem = params.get("origem");

  if (!numSerie) {
    alert("Produto não encontrado.");
    return;
  }

  // Define a URL correta com base na origem (removido ou não)
  let url;
  if (origem === "removidos") {
    url = `http://localhost:8080/v1/controle/detalhe-produto-removido/${numSerie}`;
  } else {
    url = `http://localhost:8080/v1/controle/detalhes-produto/${numSerie}`;
  }

  // Busca os dados no backend
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

  // Botão voltar
  const voltarButton = document.getElementById("voltar");
  voltarButton.addEventListener("click", () => {
    if (origem === "listaArea") {
      window.location.href = `listarPorArea.html?area=${encodeURIComponent(area)}`;
    } else {
      window.location.href = "listarProdutos.html";
    }
  });

  // Botão editar
  const editarButton = document.getElementById("editar");
  editarButton.addEventListener("click", () => {
    const novaURL = new URL("pgEditor.html", window.location.origin);
    novaURL.searchParams.set("numSerie", numSerie);
    if (area) novaURL.searchParams.set("area", area);
    if (origem) novaURL.searchParams.set("origem", origem);
    window.location.href = novaURL.toString();
  });
});

function preencherDetalhes(produto) {
  document.getElementById("idProduto").innerText = produto.numSerie || produto.numeroSerie;
  document.getElementById("nomeProduto").innerText = produto.name || produto.nome;
  document.getElementById("areaProduto").innerText = produto.area;
  document.getElementById("dataEntrada").innerText = produto.inputDate || produto.dataEntrada;
}

function remover() {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie") || params.get("id");

  if (!confirm("Tem certeza que deseja remover este produto?")) return;

  fetch(`http://localhost:8080/v1/controle/remover-produto/${numSerie}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao remover o produto.");
      return response.json();
    })
    .then(data => {
      alert(data.message || "Produto removido com sucesso!");
      window.location.href = "listarProdutos.html";
    })
    .catch(error => {
      console.error("Erro ao remover:", error);
      alert("Erro ao remover o produto.");
    });
}
