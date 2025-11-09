# QMove – Sistema de Gestão de Pátio da Mottu

O QMove é uma solução tecnológica desenvolvida para a Mottu, com o objetivo de otimizar a gestão do pátio, facilitando a organização, identificação e localização das motocicletas de forma ágil, precisa e digital.

A aplicação transforma um processo tradicionalmente manual em uma operação automatizada, segura e escalável, integrando backend em Java com Spring Boot, aplicativo móvel e tecnologia baseada em QR Code.

## 🔗 Links do Projeto

* **Vídeo de Apresentação (YouTube):** https://youtu.be/CG44sZo_gLY
* **Organização do Projeto (Azure Boards):** https://dev.azure.com/RM559145/Sprint%204%20%E2%80%93%20QA

## 👥 Integrantes

* Hellen Marinho Cordeiro – RM: 558841
* Heloisa Alves de Mesquita – RM: 559145

## 💻 Tecnologias Utilizadas

* Java 21
* Spring Boot 3.x
* Spring Security (autenticação e controle de acesso)
* Thymeleaf (renderização de páginas HTML com fragmentos para cabeçalho, rodapé e menu)
* Flyway (versionamento do banco de dados)
* PostgreSQL
* Docker / Docker Compose
* Selenium + JUnit (testes automatizados)

## ⚙️ Pré-requisitos

Antes de executar a aplicação e os testes, certifique-se de ter instalado:

* Java 21 ou superior
* Docker e Docker Compose
* Git (para clonar o repositório)

## 📂 Configuração do Projeto

### Clonar o repositório
```bash
git clone https://github.com/hmarinhoo/QMove_MVC
cd QMove_MVC
```

### Configuração do Banco de Dados

A aplicação utiliza o Flyway para controle de versões do banco de dados. O PostgreSQL é utilizado como banco principal. Para criar e iniciar o contêiner do banco, execute:
```bash
docker-compose up
```

### Configuração da Aplicação e Autenticação

A autenticação é realizada via Spring Security. Dois perfis de usuário estão disponíveis:

| Perfil | Email | Senha | Acesso |
|--------|-------|-------|--------|
| Admin | admin@mottu.com | admin123 | Acesso total a todas as funcionalidades |
| Funcionário | funcionario@mottu.com | func123 | Acesso limitado a motos e setores |

Esses usuários já estão preconfigurados no banco de dados para facilitar os testes.

### Configuração do `application.properties`

No arquivo `src/main/resources/application.properties`, o Spring Security está configurado para autenticação baseada em banco de dados. Caso queira adicionar ou alterar usuários, basta modificar este arquivo.

## 🛠 Execução da Aplicação

1. Inicie o banco de dados com Docker Compose:
```bash
docker-compose up
```

2. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

3. Acesse pelo navegador:
```
http://localhost:8080
```

* **Admin**: pode gerenciar todas as funcionalidades do sistema.
* **Funcionário**: pode visualizar e gerenciar motos e setores.

## ✅ Testes Automatizados

O principal foco deste trabalho é testar a aplicação com Selenium e JUnit, garantindo a qualidade das funcionalidades do sistema.

### Como executar os testes:

1. Inicialize o banco e a aplicação via Docker Compose.
2. Execute os testes JUnit com Maven:
```bash
./mvnw test
```

### Cobertura de Testes:

Os testes automatizados incluem:

* **Login**: sucesso, senha incorreta, campos vazios
* **Cadastro de motos**: sucesso, campos obrigatórios vazios, moto já existente
* **Cadastro de setores**: sucesso, exclusão com motos vinculadas
* **Cadastro de usuários**: sucesso, senha curta, campos obrigatórios vazios

Todos os testes utilizam Selenium WebDriver e JUnit 5, podendo ser executados em contêiner Docker para isolamento e consistência.

## 📌 Considerações Finais

O QMove demonstra a integração de desenvolvimento backend, frontend e testes automatizados em um cenário real de gestão de pátio. A utilização de Docker garante que o ambiente de desenvolvimento e testes seja consistente e facilmente replicável, promovendo qualidade e confiabilidade no sistema.
