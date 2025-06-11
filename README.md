
# 📦 Sistema de Controle Patrimonial

Este é um sistema de controle patrimonial completo, com **backend em Java (Spring Boot)**, **frontend web (HTML, CSS, JavaScript)** e arquitetura **Hexagonal (Ports & Adapters)**. Foi desenvolvido para funcionar como uma aplicação web tradicional e também como um **PWA** (Progressive Web App), podendo ser instalado no celular — mesmo em dispositivos iOS como iPhone.

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Maven**
- **Arquitetura Hexagonal**
- **HTML5, CSS3, JavaScript**
- **PWA (Manifest + Service Worker)**
- **Leitor de QR Code (html5-qrcode.js)**
- **Captura de Imagem via Câmera**
- **Armazenamento Temporário (sessionStorage)**

---

## 🗂 Estrutura de Pastas (src/main)

```
src/
└── main/
    ├── java/
    │   └── ... suas classes (usecase, domain, controller, etc.)
    ├── resources/
    │   ├── application.properties
    │   └── static/
    │       ├── menu.html
    │       ├── registrar.html
    │       ├── scanner.html
    │       ├── detalhesProduto.html
    │       ├── sw.js
    │       ├── manifest.json
    │       └── assets/
    │           ├── css/style.css
    │           ├── js/registro.js
    │           ├── js/scanner.js
    │           └── icons/
    │               ├── icon-192.png
    │               └── icon-512.png
```

---

## 🚀 Como Executar

### Pré-requisitos

- JDK 17+
- IntelliJ IDEA (ou outro IDE)
- Maven
- Um navegador moderno
- iPhone (para testar como PWA)

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/sua-repo.git
cd sua-repo
```

2. No arquivo `application.properties`, defina:

```properties
server.port=8080
server.address=0.0.0.0
```

3. Compile e execute o projeto:

```bash
./mvnw spring-boot:run
```

4. Acesse no navegador do PC:

```
http://localhost:8080/menu.html
```

5. Acesse pelo iPhone (na mesma rede Wi-Fi):

```
http://<IP-da-sua-máquina>:8080/menu.html
```

---

## 📱 Funcionalidades

- 📋 **Menu principal** com navegação para todas as funcionalidades
- 🧾 **Registro de produto** com nome, área, data e foto
- 📷 **Captura de imagem da câmera**
- 📦 **Visualização de produtos registrados**
- 🗃️ **Listagem por área, ativos ou removidos**
- 📱 **Leitura de QR Code para abrir detalhes**
- 📲 **Instalável como PWA**

---

## ✅ Testando no iPhone

- Certifique-se de estar na **mesma rede Wi-Fi**
- Adicione o endereço `http://<IP>:8080/menu.html` aos **sites confiáveis**
- No Safari, toque em **"Compartilhar" → "Adicionar à Tela de Início"**
- O app será instalado como um **aplicativo nativo** (PWA)

---

## 🛡 Problemas comuns

### ❌ `java.io.IOException: conexão anulada`

> 🔒 Solução: permitir o Java ou IntelliJ no **Firewall do Windows** e criar regras para a **porta 8080** (entrada e saída).

### ❌ Não abre no celular

> Verifique se:
> - O firewall não está bloqueando
> - O servidor está escutando em `0.0.0.0`
> - Você digitou corretamente o IP (ex: `http://192.168.0.10:8080/menu.html`)

---

## 🧠 Arquitetura Hexagonal

- **domain**: lógica de negócio (entidades, regras)
- **usecase**: casos de uso da aplicação
- **adapters/inbound**: controladores HTTP
- **adapters/outbound**: persistência de dados, integração externa
- **configuration**: beans, injeções e configs do Spring

---

## 🧾 Licença

Projeto de uso pessoal / acadêmico. Sem licença definida.
