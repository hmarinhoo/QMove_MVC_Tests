# QMove – Sistema de Gestão de Pátio da Mottu

O **QMove** é uma solução tecnológica desenvolvida para a Mottu com o objetivo de otimizar a gestão do pátio, facilitando a organização, identificação e localização das motocicletas de forma ágil, precisa e digital.

Através da integração entre um backend robusto, aplicativo móvel e tecnologia baseada em QR Code, o QMove transforma um processo tradicionalmente manual em uma operação automatizada, segura e escalável.

---

## 👥 Integrantes

- 👩‍💻 **Hellen Marinho Cordeiro** – RM: 558841
- 👩‍💻 **Heloisa Alves de Mesquita** – RM: 559145

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security** (autenticação e controle de acesso)
- **Thymeleaf** (renderização de páginas HTML com fragmentos para cabeçalho, rodapé e menu)
- **Flyway** (versionamento do banco de dados)
- **PostgreSQL**
- **Docker / Docker Compose**

---

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter instalado:

- **Java 21 ou superior**
- **Docker e Docker Compose**
- **Git** (para clonar o repositório)

---

## ⚙️ Configuração

1. **Clonar o repositório:**

   ```bash
   git clone https://github.com/hmarinhoo/QMove_MVC
   cd QMove_MVC
   
2. Configuração do Banco de Dados

A aplicação utiliza o **Flyway** para controle de versões do banco de dados. O **PostgreSQL** é utilizado como banco de dados principal. Para configurá-lo, execute o Docker Compose para criar o contêiner do banco.

```bash
docker-compose up

```
3. Configuração da Aplicação e Autenticação

A autenticação no sistema é realizada via **Spring Security**. Dois perfis de usuário estão disponíveis:

- **Admin**:
  - **Email**: `admin@mottu.com`
  - **Senha**: `admin123`
  - **Papel**: Admin (acesso total a todas as funcionalidades)

- **Funcionário**:
  - **Email**: `funcionario@mottu.com`
  - **Senha**: `func123`
  - **Papel**: User (acesso a motos e setores)

Esses usuários já estão preconfigurados no banco de dados para facilitar o início dos testes.

4. Configuração do `application.properties`

No arquivo `src/main/resources/application.properties`, o **Spring Security** está configurado para usar autenticação baseada em usuários e senhas no banco de dados. Caso queira ajustar ou adicionar mais usuários, basta modificar esse arquivo.

## Acesso e Funcionalidades

- **Admin** tem acesso completo, podendo gerenciar todas as funcionalidades do sistema.
- **Funcionário** tem acesso limitado, podendo visualizar e gerenciar motos e setores dentro do pátio.


