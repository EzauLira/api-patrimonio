const voltarButton = document.getElementById('voltar');
let scanner;

function startScan() {
  if (scanner) {
    // Se scanner já está rodando, evita múltiplos starts
    return;
  }

  scanner = new Html5Qrcode("reader");

  scanner.start(
    { facingMode: "environment" }, // câmera traseira
    { fps: 10, qrbox: 250 },
    (decodedText, decodedResult) => {
      scanner.stop().then(() => {
        // Redireciona com id decodificado (pode ajustar o parâmetro se usar outro nome)
        window.location.href = `detalhesProdutoScanner.html?id=${encodeURIComponent(decodedText)}`;
      }).catch(err => {
        console.error("Erro ao parar o scanner:", err);
      });
    },
    (errorMessage) => {
      // Pode logar no console, é normal que tenha erros temporários enquanto procura QR
      console.warn("Erro ao escanear:", errorMessage);
    }
  ).catch(err => {
    console.error("Erro ao iniciar scanner:", err);
    alert("Não foi possível iniciar a câmera. Verifique permissões e tente novamente.");
  });
}

voltarButton.addEventListener('click', () => {
    window.location.href = 'menu.html';
});

// Inicia scanner automaticamente ao carregar a página
window.addEventListener("DOMContentLoaded", startScan);
