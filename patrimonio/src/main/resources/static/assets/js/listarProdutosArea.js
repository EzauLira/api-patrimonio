document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const area = params.get("area");

  if (area) {
    document.getElementById("inputArea").value = area;
    filtrarProduto(area);
  }
});

const voltarButton = document.getElementById('voltar');

voltarButton.addEventListener('click', () => {
  window.location.href = 'menu.html'; // Redireciona para o menu principal
});

function filtrarProduto(areaParam) {
  const area = areaParam || document.getElementById("inputArea").value.trim();

  if (!area) {
    alert("Por favor, digite uma área.");
    return;
  }

  const container = document.getElementById("lista-produtos-area");
  container.innerHTML = ""; // limpa resultados anteriores

  fetch(`http://localhost:8080/v1/controle/listar-produtos-area/${area}`)
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar os produtos.");
      return response.json();
    })
    .then(data => mostrarProdutos(data))
    .catch(error => {
      console.error("Erro:", error);
      container.innerHTML = "<p>Erro ao carregar os produtos.</p>";
    });
}

function mostrarProdutos(produtos) {
  const container = document.getElementById("lista-produtos-area");
  container.innerHTML = ""; // limpa tabela anterior

  if (!produtos || produtos.length === 0) {
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
          <td><button onclick="verDetalhes('${p.numSerie}', '${p.area}')"><i class="material-icons">content_paste_search</i></button></td>
        </tr>
      `).join('')}
    </tbody>
  `;

  container.appendChild(tabela);
}

function verDetalhes(numSerie, area) {
  window.location.href = `detalhesProduto.html?numSerie=${numSerie}&area=${area}&origem=listaArea`;
}



