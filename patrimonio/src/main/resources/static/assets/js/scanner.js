let scanner;

function startScan() {
  scanner = new Html5Qrcode("reader");

  scanner.start(
    { facingMode: "environment" }, // Usa a câmera traseira
    { fps: 10, qrbox: 250 },
    (decodedText, decodedResult) => {
      scanner.stop(); // Para o scanner após leitura
      window.location.href = `detalhesProduto.html?id=${encodeURIComponent(decodedText)}`;
    },
    (errorMessage) => {
      console.warn("Erro ao escanear:", errorMessage);
    }
  ).catch(err => {
    console.error("Erro ao iniciar scanner:", err);
  });
}

// Inicia o scanner automaticamente quando a página carregar
window.addEventListener("DOMContentLoaded", startScan);
