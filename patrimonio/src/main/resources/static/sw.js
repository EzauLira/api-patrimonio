const CACHE_NAME = "patrimonio-cache-v1";
const URLS_TO_CACHE = [
  "/",
  "/index.html",
  "/menu.html",
  "/registrar.html",
  "/detalhesProduto.html",
  "/scanner.html",
  "/camera.html",
  "/cameraPgEdicao.html",
  "/editarProduto.html",
  "/assets/css/style.css",
  "/assets/js/registro.js",
  "/assets/js/scanner.js",
  "/assets/js/pgEditorjs.js",
  "https://fonts.googleapis.com/icon?family=Material+Icons",
  "https://unpkg.com/html5-qrcode"
];

// Instala o service worker
self.addEventListener("install", event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => {
      return cache.addAll(URLS_TO_CACHE);
    })
  );
});

// Ativa e limpa caches antigos
self.addEventListener("activate", event => {
  event.waitUntil(
    caches.keys().then(keys =>
      Promise.all(
        keys.map(key => {
          if (key !== CACHE_NAME) {
            return caches.delete(key);
          }
        })
      )
    )
  );
});

// Intercepta requisições
self.addEventListener("fetch", event => {
  event.respondWith(
    caches.match(event.request).then(response =>
      response || fetch(event.request)
    )
  );
});
