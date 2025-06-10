document.addEventListener("DOMContentLoaded", () => {
  const btnVoltar = document.getElementById("voltar");
  const btnFiltrar = document.getElementById("filtrar");
  const filtroForm = document.getElementById("filtro-form");
  const btnOcultarFiltro = document.getElementById("ocultarFiltroBtn");
  const btnAplicarFiltro = document.getElementById("aplicarFiltroBtn");

  filtroForm.style.display = "none"; // Oculta o filtro inicialmente

  btnVoltar.addEventListener("click", () => {
    window.location.href = "menu.html";
  });

  btnFiltrar.addEventListener("click", () => {
    filtroForm.style.display = "block";
    btnFiltrar.style.display = "none";
  });

  btnOcultarFiltro.addEventListener("click", () => {
    filtroForm.style.display = "none";
    btnFiltrar.style.display = "inline-block";
  });

  btnAplicarFiltro.addEventListener("click", aplicarFiltro);

  carregarProdutos();
});

function carregarProdutos() {
  fetch("http://localhost:8080/v1/controle/listar-produtos")
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar os produtos.");
      return response.json();
    })
    .then(produtos => mostrarProdutos(produtos))
    .catch(erro => {
      console.error("Erro ao carregar os produtos:", erro);
      document.getElementById("lista-produtos").innerHTML = "<p>Erro ao carregar os produtos.</p>";
    });
}

function mostrarProdutos(produtos) {
  const container = document.getElementById("lista-produtos");
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
        <th>Data de Entrada</th>
        <th>Ações</th>
      </tr>
    </thead>
    <tbody>
      ${produtos.map(p => `
        <tr>
          <td>${p.numSerie}</td>
          <td>${p.name}</td>
          <td>${p.area}</td>
          <td>${p.inputDate}</td>
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

  // Eventos para os botões de detalhes
  document.querySelectorAll(".btn-detalhes").forEach(btn => {
    btn.addEventListener("click", () => {
      const numSerie = btn.dataset.numserie;
      const area = btn.dataset.area;
      window.location.href = `detalhesProduto.html?numSerie=${numSerie}&area=${area}&origem=listaTodos`;
    });
  });
}

function aplicarFiltro() {
  const numSerie = document.getElementById("filtroNumSerie").value.toLowerCase();
  const nome = document.getElementById("filtroNome").value.toLowerCase();
  const area = document.getElementById("filtroArea").value.toLowerCase();
  const data = document.getElementById("filtroData").value;

  fetch("http://localhost:8080/v1/controle/listar-produtos")
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar os produtos.");
      return response.json();
    })
    .then(produtos => {
      const filtrados = produtos.filter(p => {
        const matchSerie = !numSerie || p.numSerie.toLowerCase().includes(numSerie);
        const matchNome = !nome || p.name.toLowerCase().includes(nome);
        const matchArea = !area || p.area.toLowerCase().includes(area);
        const matchData = !data || p.inputDate === data;
        return matchSerie && matchNome && matchArea && matchData;
      });
      mostrarProdutos(filtrados);
    })
    .catch(error => {
      console.error("Erro ao aplicar filtro:", error);
      document.getElementById("lista-produtos").innerHTML = "<p>Erro ao carregar os produtos.</p>";
    });
}
