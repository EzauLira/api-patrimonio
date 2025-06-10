document.addEventListener("DOMContentLoaded", () => {
  const inputArea = document.getElementById("inputArea");
  const btnFiltrar = document.getElementById("filtrar");
  const btnVoltar = document.getElementById("voltar");

  const params = new URLSearchParams(window.location.search);
  const areaURL = params.get("area");

  if (areaURL) {
    inputArea.value = areaURL;
    filtrarProduto(areaURL);
  }

  btnFiltrar.addEventListener("click", () => filtrarProduto());
  btnVoltar.addEventListener("click", () => {
    window.location.href = "menu.html";
  });
});

function filtrarProduto(areaParam) {
  const area = areaParam || document.getElementById("inputArea").value.trim();
  const container = document.getElementById("lista-produtos-area");
  container.innerHTML = "";

  if (!area) {
    alert("Por favor, digite uma área.");
    return;
  }

  fetch(`http://localhost:8080/v1/controle/listar-produtos-area/${encodeURIComponent(area)}`)
    .then(res => {
      if (!res.ok) throw new Error("Erro ao buscar os produtos.");
      return res.json();
    })
    .then(produtos => mostrarProdutos(produtos))
    .catch(err => {
      console.error("Erro ao carregar produtos:", err);
      container.innerHTML = "<p>Erro ao carregar os produtos.</p>";
    });
}

function mostrarProdutos(produtos) {
  const container = document.getElementById("lista-produtos-area");
  container.innerHTML = "";

  if (!Array.isArray(produtos) || produtos.length === 0) {
    container.innerHTML = "<p>Nenhum produto encontrado.</p>";
    return;
  }

  const tabela = document.createElement("table");
  tabela.innerHTML = `
    <thead>
      <tr>
        <th>Nº de Série</th>
        <th>Nome</th>
        <th>Área</th>
        <th>Ações</th>
      </tr>
    </thead>
    <tbody>
      ${produtos.map(p => `
        <tr>
          <td>${p.numSerie}</td>
          <td>${p.name}</td>
          <td>${p.area}</td>
          <td>
            <button class="btn-detalhes" data-numserie="${p.numSerie}" data-area="${p.area}" title="Ver Detalhes">
              <i class="material-icons">content_paste_search</i>
            </button>
          </td>
        </tr>
      `).join("")}
    </tbody>
  `;

  container.appendChild(tabela);

  // Adiciona evento aos botões de detalhes após renderizar a tabela
  document.querySelectorAll(".btn-detalhes").forEach(button => {
    button.addEventListener("click", () => {
      const numSerie = button.dataset.numserie;
      const area = button.dataset.area;
      window.location.href = `detalhesProduto.html?numSerie=${numSerie}&area=${area}&origem=listaArea`;
    });
  });
}
