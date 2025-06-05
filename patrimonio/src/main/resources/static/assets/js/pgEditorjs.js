document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie");
  const origem = params.get("origem");

  const voltarButton = document.getElementById('voltar');
  const editarButton = document.getElementById('editar');
  const nomeInput = document.getElementById('nomeProduto');
  const areaInput = document.getElementById('areaProduto');
  const dataInput = document.getElementById('dataEntrada');

 // Botão voltar
voltarButton.addEventListener('click', () => {
  const novaURL = new URL("detalhesProduto.html", window.location.origin);
  novaURL.searchParams.set("numSerie", numSerie);
  if (params.get("area")) novaURL.searchParams.set("area", params.get("area"));
  if (params.get("origem")) novaURL.searchParams.set("origem", params.get("origem"));
  window.location.href = novaURL.toString();
});

  // Buscar dados do produto atual para preencher o formulário
  fetch(`http://localhost:8080/v1/controle/detalhes-produto/${numSerie}`)
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar produto.");
      return response.json();
    })
    .then(produto => {
      nomeInput.value = produto.name;
      areaInput.value = produto.area;
      dataInput.value = produto.inputDate; // yyyy-MM-dd já é aceito pelo input[type="date"]
    })
    .catch(error => {
      console.error("Erro ao carregar produto:", error);
      alert("Erro ao carregar dados do produto.");
    });

  // Botão editar (salvar alterações)
  editarButton.addEventListener("click", () => {
    const nome = nomeInput.value.trim();
    const area = areaInput.value.trim();
    const data = dataInput.value;

    if (!nome || !area || !data) {
      alert("Preencha todos os campos.");
      return;
    }

    const produtoAtualizado = {
      name: nome,
      area: area,
      inputDate: data
    };

    fetch(`http://localhost:8080/v1/controle/alterar-produto/${numSerie}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(produtoAtualizado)
    })
      .then(response => {
        if (!response.ok) throw new Error("Erro ao atualizar o produto.");
        return response.json();
      })
      .then(data => {
        alert(data.message || "Produto atualizado com sucesso!");
        window.location.href = `detalhesProduto.html?numSerie=${encodeURIComponent(numSerie)}`;
      })
      .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao atualizar o produto.");
      });
  });
});
