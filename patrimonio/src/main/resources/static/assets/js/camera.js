const voltarButton = document.getElementById('voltar');
const prosseguirButton = document.getElementById('prosseguir');
const registrarButton = document.getElementById('registro');

let stream;

function startCamera() {
    const video = document.createElement('video');
    video.setAttribute('autoplay', '');
    video.setAttribute('playsinline', '');
    video.setAttribute('id', 'camera');
    video.style.maxWidth = '100%';
    document.getElementById('registrar').appendChild(video);

    navigator.mediaDevices.getUserMedia({ video: true })
        .then((mediaStream) => {
            stream = mediaStream;
            video.srcObject = mediaStream;
        })
        .catch((err) => {
            console.error("Erro ao acessar câmera:", err);
            alert("Erro ao acessar a câmera.");
        });
}

// Captura a imagem da câmera e salva no sessionStorage
function registrar() {
    const video = document.getElementById('camera');

    if (!video || video.videoWidth === 0 || video.videoHeight === 0) {
        alert("Aguardando a câmera carregar... Tente novamente em alguns segundos.");
        return;
    }

    const canvas = document.createElement('canvas');
    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    const ctx = canvas.getContext('2d');
    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

    const imageData = canvas.toDataURL('image/png');
    sessionStorage.setItem('imagemProduto', imageData);
    console.log("Imagem capturada e salva no sessionStorage");

    // Para a câmera
    if (stream) {
        stream.getTracks().forEach(track => track.stop());
    }

    // Redireciona para a tela de registro
    window.location.href = 'registrar.html';
}

registrarButton.addEventListener('click', registrar);

voltarButton.addEventListener('click', () => {
    window.location.href = 'menu.html';
});

prosseguirButton.addEventListener('click', () => {
    sessionStorage.removeItem('imagemProduto'); // remove a imagem quando apertado em prosseguir.
    window.location.href = 'registrar.html';
});

window.addEventListener('DOMContentLoaded', startCamera);
