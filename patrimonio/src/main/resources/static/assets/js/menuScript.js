document.addEventListener("DOMContentLoaded", () => {
  document.getElementById('registrar').addEventListener('click', () => {
    window.location.href = 'camera.html';
  });

  document.getElementById('listarProdutos').addEventListener('click', () => {
    window.location.href = 'listarProdutos.html';
  });

  document.getElementById('listarProdutosArea').addEventListener('click', () => {
    window.location.href = 'listarPorArea.html';
  });

  document.getElementById('listarProdutosRemovidos').addEventListener('click', () => {
    window.location.href = 'listarProdutosRemovidos.html';
  });

  document.getElementById('scanner').addEventListener('click', () => {
    window.location.href = 'scanner.html';
  });
});
