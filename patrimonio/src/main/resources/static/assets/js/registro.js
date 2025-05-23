const voltarButton = document.getElementById('voltar');
const dataEntrada = document.getElementById("dataEntrada").value;
const dataformatada = formatarData(dataEntrada);

voltarButton.addEventListener('click', () => {
    window.location.href = 'menu.html'; // Redireciona para a página de Menu
});

function formatarData(dataIso) {
    const [ano, mes, dia] = dataIso.split("-");
    return `${dia}/${mes}/${ano}`;
}

const payload = {
  name: nome,
  area: area,
  inputDate: dataFormatada // <- Agora você está enviando dd/mm/yyyy
};

function registrarProduto() {
    const nome = document.getElementById('nomeProduto').value;
    const area = document.getElementById('areaProduto').value;
    const data = document.getElementById('dataEntrada').value;

    const produto = {
        name: nome,
        area: area,
        inputDate: data
    };

    fetch("http://localhost:8080/v1/controle/registrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(produto)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro na requisição");
            }
            return response.json();
        })
        .then(data => {
            alert(data.message);
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Erro ao registrar o produto.");
        });
}
