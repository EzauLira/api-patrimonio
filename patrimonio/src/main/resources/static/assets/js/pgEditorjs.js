document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const numSerie = params.get("numSerie");

  // Elementos do DOM
  const btnEditar = document.getElementById("editar");
  const btnVoltar = document.getElementById("voltar");
  const btnNovaFoto = document.getElementById("btnNovaFoto");
  const previewFoto = document.getElementById("previewFoto");

  const nomeInput = document.getElementById("nomeProduto");
  const areaInput = document.getElementById("areaProduto");
  const dataInput = document.getElementById("dataEntrada");

  let fotoBase64 = null;

  // Função para navegar de volta com segurança
  function voltarParaDetalhes() {
    window.location.href = `detalhesProduto.html?numSerie=${encodeURIComponent(numSerie)}`;
  }

  // Botão voltar
  btnVoltar.addEventListener("click", () => {
    const novaURL = new URL("detalhesProduto.html", window.location.origin);
    novaURL.searchParams.set("numSerie", numSerie);

    if (params.get("area")) novaURL.searchParams.set("area", params.get("area"));
    if (params.get("origem")) novaURL.searchParams.set("origem", params.get("origem"));

    window.location.href = novaURL.toString();
  });

  // Botão tirar nova foto (impede submit do form)
  btnNovaFoto.addEventListener("click", (event) => {
    event.preventDefault();
    sessionStorage.setItem("produtoId", numSerie);
    window.location.href = "cameraPgEdicao.html";
  });

  // Carregar dados do produto
  fetch(`http://localhost:8080/v1/controle/detalhes-produto/${numSerie}`)
    .then(response => {
      if (!response.ok) throw new Error("Erro ao carregar produto.");
      return response.json();
    })
    .then(produto => {
      nomeInput.value = produto.name || "";
      areaInput.value = produto.area || "";
      dataInput.value = produto.inputDate || "";

      // Mostra foto do produto (do banco), se não tiver nova foto
      if (!fotoBase64 && produto.foto && produto.foto.length > 0) {
        const imgSrc = `data:image/png;base64,${produto.foto}`;
        previewFoto.innerHTML = `
          <img src="${imgSrc}" alt="Foto atual" style="max-width: 200px; border-radius: 8px; margin-top: 10px;">
        `;
      }
    })
    .catch(error => {
      console.error("Erro ao carregar produto:", error);
      alert("Erro ao carregar dados do produto.");
    });

  // Verifica se tem imagem salva após retornar da câmera
  const imagemProduto = sessionStorage.getItem("imagemProduto");
  if (imagemProduto) {
    fotoBase64 = imagemProduto.split(',')[1]; // Apenas base64 puro, se precisar enviar pro backend
    previewFoto.innerHTML = `
      <img src="${imagemProduto}" alt="Nova foto" style="max-width: 300px; height: auto; border-radius: 8px; margin-top: 10px;">
    `;
    sessionStorage.removeItem("imagemProduto"); // Limpa depois de usar
  }

  // Salvar alterações
  btnEditar.addEventListener("click", (event) => {
    event.preventDefault(); // Evita submit padrão caso esteja dentro de form

    const nome = nomeInput.value.trim();
    const area = areaInput.value.trim();
    const data = dataInput.value.trim();

    if (!nome || !area || !data) {
      alert("Preencha todos os campos.");
      return;
    }

    const produtoAtualizado = {
      name: nome,
      area: area,
      inputDate: data
    };

    if (fotoBase64) {
      produtoAtualizado.foto = fotoBase64;
    }

    fetch(`http://localhost:8080/v1/controle/alterar-produto/${numSerie}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(produtoAtualizado)
    })
      .then(response => {
        if (!response.ok) throw new Error("Erro ao atualizar produto.");
        return response.json();
      })
      .then(data => {
        alert(data.message || "Produto atualizado com sucesso!");
        voltarParaDetalhes();
      })
      .catch(error => {
        console.error("Erro ao atualizar:", error);
        alert("Erro ao salvar as alterações.");
      });
  });
});
