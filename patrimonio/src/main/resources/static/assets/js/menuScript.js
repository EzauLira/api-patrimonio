const registroButton = document.getElementById('registrar');
const listarButton = document.getElementById('listarProdutos');
const listarButtonArea = document.getElementById('listarProdutosArea');
const listarRemovidosButton = document.getElementById('listarProdutosRemovidos');
const scannerButton = document.getElementById('scanner');

registroButton.addEventListener('click', () => {
    window.location.href = 'camera.html'; // Redireciona para a página de Registro
});

listarButton.addEventListener('click', () => {
    window.location.href = 'listarProdutos.html'; // Redireciona para a página para listar todos os produtos
});

listarButtonArea.addEventListener('click', () => {
    window.location.href = 'listarPorArea.html'; // Redireciona para a página para listar todos os produtos
});

listarRemovidosButton.addEventListener('click', () => {
    window.location.href = 'listarProdutosRemovidos.html'; // Redireciona para a página para listar de produtos removidos
});

scannerButton.addEventListener('click', () => {
    window.location.href = 'scanner.html'; // Redireciona para a página de scanner;
});