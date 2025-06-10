// Referências aos botões
const homeButton = document.getElementById('home');
const voltarButton = document.getElementById('voltar');
const registrarButton = document.getElementById('registrar');

// Event Listeners para navegação
homeButton?.addEventListener('click', () => {
    window.location.href = 'menu.html';
});

voltarButton?.addEventListener('click', () => {
    window.location.href = 'camera.html';
});

registrarButton?.addEventListener('click', (event) => {
    event.preventDefault();
    registrarProduto();
});

/**
 * Formata data YYYY-MM-DD para dd/MM/yyyy
 */
function formatarData(dataIso) {
    if (!dataIso) return "";
    const [ano, mes, dia] = dataIso.split("-");
    return `${dia}/${mes}/${ano}`;
}

/**
 * Converte Data URL base64 em Blob
 */
function converterImagemParaBlob(imageData) {
    if (!imageData) return null;

    try {
        const base64 = imageData.split(',')[1];
        const mimeString = imageData.split(',')[0].split(':')[1].split(';')[0];
        const byteString = atob(base64);
        const ab = new ArrayBuffer(byteString.length);
        const ia = new Uint8Array(ab);
        for (let i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], { type: mimeString });
    } catch (e) {
        console.error("Erro ao converter imagem:", e);
        alert("Erro ao processar a imagem.");
        return null;
    }
}

/**
 * Cria FormData para envio
 */
function criarFormData(dadosProduto, blob) {
    const formData = new FormData();
    formData.append("name", dadosProduto.nome);
    formData.append("area", dadosProduto.area);
    formData.append("inputDate", dadosProduto.dataFormatada);

    if (blob) {
        formData.append("file", blob, "produto.png");
    }
    return formData;
}

/**
 * Envia FormData para backend
 */
async function enviarProduto(formData) {
    const response = await fetch("http://localhost:8080/v1/controle/registrar", {
        method: "POST",
        body: formData
    });

    if (!response.ok) {
        throw new Error("Erro na requisição ao servidor");
    }

    return await response.json();
}

/**
 * Limpa formulário e preview da imagem
 */
function limparFormulario() {
    document.getElementById('nomeProduto').value = "";
    document.getElementById('areaProduto').value = "";
    document.getElementById('dataEntrada').value = "";

    const imgPreview = document.getElementById('previewImagem');
    if (imgPreview) {
        imgPreview.style.display = 'none';
        imgPreview.src = "";
    }
}

/**
 * Função principal para registrar produto
 */
function registrarProduto() {
    const nome = document.getElementById('nomeProduto')?.value.trim();
    const area = document.getElementById('areaProduto')?.value.trim();
    const dataEntrada = document.getElementById('dataEntrada')?.value;

    if (!nome || !area || !dataEntrada) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const dataFormatada = formatarData(dataEntrada);

    const imageData = sessionStorage.getItem('imagemProduto');
    const blob = converterImagemParaBlob(imageData);

    const dadosProduto = { nome, area, dataFormatada };
    const formData = criarFormData(dadosProduto, blob);

    enviarProduto(formData)
        .then(data => {
            alert(data.message || "Produto registrado com sucesso!");
            sessionStorage.removeItem('imagemProduto');
            limparFormulario();
            window.location.href = 'menu.html';
        })
        .catch(error => {
            console.error("Erro ao registrar:", error);
            alert("Erro ao registrar o produto.");
        });
}

// Mostrar preview da imagem ao carregar página
window.addEventListener("DOMContentLoaded", () => {
    const imagem = sessionStorage.getItem('imagemProduto');
    const imgPreview = document.getElementById('previewImagem');

    if (imagem && imgPreview) {
        imgPreview.src = imagem;
        imgPreview.style.display = 'block';
    }
});
