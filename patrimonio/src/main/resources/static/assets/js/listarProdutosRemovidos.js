document.addEventListener("DOMContentLoaded", () => {
  const voltarButton = document.getElementById("voltar");
  const filtrarButton = document.getElementById("filtrar");
  const filtroForm = document.getElementById("filtro-form");
  const ocultarFiltroButton = document.getElementById("ocultarFiltroBtn");

  if (filtroForm) filtroForm.style.display = "none";

  if (voltarButton) {
    voltarButton.addEventListener("click", () => {
      window.location.href = "menu.html";
    });
  }

  if (filtrarButton && filtroForm) {
    filtrarButton.addEventListener("click", () => {
      filtroForm.style.display = "block";
      filtrarButton.style.display = "none";
    });
  }

  if (ocultarFiltroButton && filtroForm && filtrarButton) {
    ocultarFiltroButton.addEventListener("click", () => {
      filtroForm.style.display = "none";
      filtrarButton.style.display = "inline-block";
    });
  }

  // Carrega os produtos ao abrir a página
  fetch("http://localhost:8080/v1/controle/listar-produtos-removidos")
    .then((response) => {
      if (!response.ok) throw new Error("Erro ao buscar os produtos.");
      return response.json();
    })
    .then((data) => mostrarProdutos(data))
    .catch((error) => {
      console.error("Erro:", error);
      const container = document.getElementById("listar-produtos-removidos");
      if (container) {
        container.innerHTML = "<p>Erro ao carregar os produtos.</p>";
      }
    });
});

function mostrarProdutos(produtos) {
  const container = document.getElementById("listar-produtos-removidos");
  if (!container) {
    console.error("Elemento 'listar-produtos-removidos' não encontrado no HTML.");
    return;
  }

  container.innerHTML = "";

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
        <th>Data de Entrada</th>
        <th>Ações</th>
      </tr>
    </thead>
    <tbody>
      ${produtos
        .map(
          (p) => `
        <tr>
          <td>${p.numSerie}</td>
          <td>${p.name}</td>
          <td>${p.area}</td>
          <td>${p.inputDate}</td>
          <td><button onclick="verDetalhes('${p.numSerie}', '${p.area}', 'removidos')"><i class="material-icons">content_paste_search</i></button></td>
        </tr>
      `
        )
        .join("")}
    </tbody>
  `;
  container.appendChild(tabela);
}

function verDetalhes(numSerie, area, origem) {
  window.location.href = `detalhesProduto.html?numSerie=${numSerie}&area=${area}&origem=${origem}`;
}

function aplicarFiltro() {
  const numSerie = document.getElementById("filtroNumSerie").value.toLowerCase();
  const nome = document.getElementById("filtroNome").value.toLowerCase();
  const area = document.getElementById("filtroArea").value.toLowerCase();
  const data = document.getElementById("filtroData").value;

  fetch("http://localhost:8080/v1/controle/listar-produtos-removidos")
    .then((response) => {
      if (!response.ok) throw new Error("Erro ao buscar os produtos.");
      return response.json();
    })
    .then((produtos) => {
      const filtrados = produtos.filter((p) => {
        const matchSerie = !numSerie || p.numSerie.toLowerCase().includes(numSerie);
        const matchNome = !nome || p.name.toLowerCase().includes(nome);
        const matchArea = !area || p.area.toLowerCase().includes(area);
        const matchData = !data || p.inputDate === data;
        return matchSerie && matchNome && matchArea && matchData;
      });

      mostrarProdutos(filtrados);
    })
    .catch((error) => {
      console.error("Erro:", error);
      document.getElementById("listar-produtos-removidos").innerHTML =
        "<p>Erro ao carregar os produtos.</p>";
    });
}
