document.addEventListener("DOMContentLoaded", () => {
  const cameraContainer = document.getElementById("camera-container");
  const btnCapturar = document.getElementById("btn-capturar");
  const btnVoltar = document.getElementById("btn-voltar");

  let videoElement = null;
  let stream = null;

  function iniciarCamera() {
    videoElement = document.createElement("video");
    videoElement.id = "camera";
    videoElement.autoplay = true;
    videoElement.playsInline = true;
    videoElement.style.maxWidth = "100%";

    cameraContainer.appendChild(videoElement);

    navigator.mediaDevices.getUserMedia({ video: true })
      .then(mediaStream => {
        stream = mediaStream;
        videoElement.srcObject = mediaStream;
      })
      .catch(err => {
        console.error("Erro ao acessar a câmera:", err);
        alert("Erro ao acessar a câmera. Verifique as permissões.");
      });
  }

  function capturarImagem() {
    if (!videoElement || videoElement.videoWidth === 0 || videoElement.videoHeight === 0) {
      alert("Aguardando a câmera carregar... Tente novamente em alguns segundos.");
      return;
    }

    const canvas = document.createElement("canvas");
    canvas.width = videoElement.videoWidth;
    canvas.height = videoElement.videoHeight;

    const ctx = canvas.getContext("2d");
    ctx.drawImage(videoElement, 0, 0, canvas.width, canvas.height);

    const imagem = canvas.toDataURL("image/png");
    sessionStorage.setItem("imagemProduto", imagem);

    encerrarCamera();
    redirecionarParaEdicao();
  }

  function encerrarCamera() {
    if (stream) {
      stream.getTracks().forEach(track => track.stop());
    }
  }

  function redirecionarParaEdicao() {
    const produtoId = sessionStorage.getItem("produtoId") || "";
    window.location.href = `pgEditor.html?numSerie=${produtoId}`;
  }

  // Eventos
  btnCapturar.addEventListener("click", capturarImagem);
  btnVoltar.addEventListener("click", redirecionarParaEdicao);

  iniciarCamera();
});
