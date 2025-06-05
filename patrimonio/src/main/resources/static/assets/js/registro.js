const homeButton = document.getElementById('home');
const voltarButton = document.getElementById('voltar');

homeButton.addEventListener('click', () => {
    window.location.href = 'menu.html'; // Redireciona para a página de Menu
});
voltarButton.addEventListener('click', () => {
    window.location.href = 'camera.html'; // Redireciona para a página de câmera
});

function formatarData(dataIso) {
    if (!dataIso) return ""; // Evita erro se data vazia
    const [ano, mes, dia] = dataIso.split("-");
    return `${dia}/${mes}/${ano}`;
}

function registrarProduto() {
    const nome = document.getElementById('nomeProduto').value.trim();
    const area = document.getElementById('areaProduto').value.trim();
    const dataEntrada = document.getElementById('dataEntrada').value;

    // Validação simples (pode expandir)
    if (!nome || !area || !dataEntrada) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const dataFormatada = formatarData(dataEntrada);

    const produto = {
        name: nome,
        area: area,
        inputDate: dataFormatada // Data no formato dd/mm/yyyy
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
            sessionStorage.removeItem('imagemProduto'); // Opcional: limpa a imagem após o registro
            // Limpa campos após registro (opcional)
            document.getElementById('nomeProduto').value = "";
            document.getElementById('areaProduto').value = "";
            document.getElementById('dataEntrada').value = "";
            const imgPreview = document.getElementById('previewImagem');
            if (imgPreview) {
                imgPreview.style.display = 'none';
                imgPreview.src = "";
            }
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Erro ao registrar o produto.");
        });
}

window.addEventListener("DOMContentLoaded", () => {
    const imagem = sessionStorage.getItem('imagemProduto');
    const imgPreview = document.getElementById('previewImagem');

    if (imagem && imgPreview) {
        imgPreview.src = imagem;
        imgPreview.style.display = 'block';
    }
});
