# Controle de Finanças

Aplicação web para controle de finanças pessoais, construída com **Spring Boot** no backend e **Angular** no frontend. O projeto foi pensado para ser desenvolvido **passo a passo**, com foco em entender cada decisão técnica, e inclui desde o início **segurança**, **testes automatizados** e **testes de carga/estresse**.

---

## Sumário

1. [Sobre o projeto](#sobre-o-projeto)
2. [Funcionalidades](#funcionalidades)
3. [Tecnologias](#tecnologias)
4. [Arquitetura](#arquitetura)
5. [Modelo de dados](#modelo-de-dados)
6. [Segurança](#seguran%C3%A7a)
7. [Estratégia de testes](#estrat%C3%A9gia-de-testes)
8. [Convenções do projeto](#conven%C3%A7%C3%B5es-do-projeto)
9. [Estrutura de pastas](#estrutura-de-pastas)
10. [Roadmap (passo a passo)](#roadmap-passo-a-passo)
11. [Como executar](#como-executar)
12. [Licença](#licen%C3%A7a)

---

## Sobre o projeto

O **Controle de Finanças** ajuda cada usuário a registrar suas receitas e despesas, organizar o dinheiro em contas e categorias, acompanhar quanto gastou em relação ao orçamento do mês e visualizar tudo em um dashboard com gráficos.

Objetivos do projeto:

- Praticar o desenvolvimento **full stack** com Java (Spring Boot) e Angular.
- Aplicar **boas práticas de segurança** tanto no backend quanto no frontend.
- Criar uma base sólida de **testes** (unitários, integração, ponta a ponta e estresse).
- Manter uma **documentação clara** de cada etapa, para servir também como material de estudo.

## Funcionalidades

Escopo da primeira versão (MVP):

- **Login e múltiplos usuários**: cadastro, autenticação e isolamento total dos dados de cada usuário.
- **Contas / carteiras**: conta corrente, poupança, carteira, cartão, cada uma com saldo inicial.
- **Categorias**: categorias de receita e de despesa (ex.: Salário, Alimentação, Transporte).
- **Lançamentos (receitas e despesas)**: registro com descrição, valor, data, conta e categoria.
- **Orçamento mensal**: limite de gasto por categoria em cada mês, comparado ao que foi gasto.
- **Metas**: objetivos financeiros com valor alvo, valor acumulado e prazo.
- **Dashboard com gráficos**: gasto por categoria, saldo por conta, receitas vs. despesas e orçamento vs. gasto.

## Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Backend | Java 21, Spring Boot 4.0.8, Spring Web (webmvc), Spring Data JPA, Spring Security |
| Migrations | Flyway |
| Autenticação | JWT (JSON Web Token) |
| Banco de dados | PostgreSQL |
| Frontend | Angular, TypeScript |
| Testes backend | JUnit 5, Mockito, Spring Boot Test, Testcontainers |
| Testes frontend | Jasmine/Karma (unitários), Cypress ou Playwright (ponta a ponta) |
| Testes de estresse | k6, Gatling ou JMeter |
| Infraestrutura | Docker e Docker Compose |

> As ferramentas de teste e de estresse podem ser ajustadas ao longo do projeto; cada escolha será justificada na etapa correspondente.

## Arquitetura

O backend segue uma arquitetura em camadas, com responsabilidades bem separadas:

```
Frontend (Angular)
      │  HTTPS + JWT
      ▼
Controller  →  Service  →  Repository  →  PostgreSQL
(REST/DTO)     (regras)    (acesso a dados)
```

- **Controller**: recebe as requisições HTTP, valida a entrada e devolve DTOs.
- **Service**: concentra as regras de negócio (ex.: saldo, orçamento, metas).
- **Repository**: acesso ao banco via Spring Data JPA.
- **DTOs**: objetos de entrada e saída da API, separados das entidades do banco.

## Modelo de dados

Todas as tabelas de negócio pertencem a um usuário, o que garante o isolamento dos dados.

| Entidade | Campos principais |
| --- | --- |
| **Usuario** | nome, e-mail, senha (criptografada) |
| **Conta** | nome, tipo, saldo inicial, usuário |
| **Categoria** | nome, tipo (receita ou despesa), usuário |
| **Lancamento** | descrição, valor, data, tipo, conta, categoria, usuário |
| **Orcamento** | categoria, mês/ano, valor limite, usuário |
| **Meta** | nome, valor alvo, valor atual, prazo, usuário |

O **dashboard** não tem tabela própria: usa consultas agregadas sobre os lançamentos.

Os valores monetários usam `BigDecimal` para evitar erros de arredondamento.

## Segurança

A segurança faz parte do projeto desde a primeira etapa, e não como algo adicionado no final.

### Backend

- **Autenticação com JWT** e **Spring Security** protegendo todas as rotas privadas.
- **Senhas com BCrypt**: nunca armazenadas em texto puro.
- **Autorização por dono do recurso**: cada usuário só acessa os próprios dados (proteção contra IDOR).
- **Validação de entrada** com Bean Validation em todos os DTOs.
- **Proteção contra SQL Injection** usando JPA e consultas parametrizadas.
- **CORS restrito** apenas à origem do frontend.
- **Tratamento global de erros**, sem expor detalhes internos (stack traces) ao cliente.
- **Limite de tentativas de login** (rate limiting) contra ataques de força bruta.
- **Cabeçalhos de segurança HTTP** e uso de HTTPS.
- **Segredos fora do código** (variáveis de ambiente), nunca versionados.
- **Verificação de dependências** vulneráveis (ex.: OWASP Dependency-Check).

### Frontend

- **Guards de rota** para impedir acesso a telas privadas sem autenticação.
- **HTTP Interceptor** para enviar o token e tratar respostas 401/403.
- **Proteção contra XSS**: uso do sanitizador do Angular e nenhum `innerHTML` sem tratamento.
- **Cuidado com o armazenamento do token**, com a decisão e os riscos explicados na etapa de autenticação do frontend.
- **Validação nos formulários** (a validação real continua no backend).
- **Content Security Policy (CSP)** e outros cabeçalhos configurados no servidor.
- **Auditoria de dependências** com `npm audit`.

Referência de estudo: **OWASP Top 10**.

## Estratégia de testes

| Tipo | Objetivo | Onde |
| --- | --- | --- |
| **Unitários** | Testar regras de negócio isoladas (Services, validações) | Backend e frontend |
| **Integração** | Testar Controller + Service + Repository com banco real (Testcontainers) | Backend |
| **Segurança** | Garantir que rotas privadas e dados de outros usuários estão protegidos | Backend |
| **Ponta a ponta (E2E)** | Simular o usuário usando o sistema completo | Frontend |
| **Estresse/carga** | Medir desempenho e limites com muitos usuários simultâneos | API |

Metas de qualidade: cobertura mínima nas regras de negócio, testes rodando automaticamente a cada alteração (CI) e relatórios de desempenho documentados.

## Convenções do projeto

- Getters e setters escritos manualmente (sem Lombok), para entender o que o código faz.
- **Records** do Java para os DTOs.
- Arquitetura em camadas: Controller → Service → Repository.
- Commits pequenos e descritivos, um por etapa concluída.

## Estrutura de pastas

```
controle-financas/
├── backend/          # API Spring Boot
│   └── src/
│       ├── main/java/.../
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   ├── model/
│       │   ├── dto/
│       │   ├── security/
│       │   └── exception/
│       └── test/
├── frontend/         # Aplicação Angular
├── docs/             # Documentação de cada etapa
├── docker-compose.yml
└── README.md
```

## Roadmap (passo a passo)

Cada etapa é feita, entendida e validada antes de passar para a próxima.

- [x] **1. Definição do escopo** (funcionalidades do MVP)
- [x] **2. Modelagem das entidades**
- [x] **3. Criação do projeto backend** (Spring Boot 4.0.8, Maven, Java 21)
- [x] **4. Banco de dados e entidades** (PostgreSQL, Flyway, JPA — Usuario, Conta, Categoria, Lancamento, Orcamento, Meta)
- [ ] **5. Segurança do backend** (cadastro, login, JWT, Spring Security)
- [ ] **6. CRUD de contas e categorias**
- [ ] **7. Lançamentos** (receitas e despesas)
- [ ] **8. Orçamento mensal e metas**
- [ ] **9. Endpoints do dashboard** (consultas agregadas)
- [ ] **10. Testes do backend** (unitários, integração e segurança)
- [ ] **11. Criação do projeto Angular**
- [ ] **12. Segurança do frontend** (login, guards, interceptor)
- [ ] **13. Telas** (contas, categorias, lançamentos, orçamento, metas)
- [ ] **14. Dashboard com gráficos**
- [ ] **15. Testes do frontend** (unitários e E2E)
- [ ] **16. Testes de estresse**
- [ ] **17. Docker, CI e documentação final**

## Como executar

> Esta seção será preenchida conforme o projeto avança (pré-requisitos, variáveis de ambiente, como subir o banco, o backend e o frontend).

## Licença

A definir.
