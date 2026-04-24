# GymTracker API

API REST para registro e análise de treinos de musculação. Transforma séries e repetições em dados de performance: volume de carga, recordes pessoais, estimativa de 1RM e distribuição de volume por grupo muscular.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Requisitos Funcionais](#requisitos-funcionais)
- [Stack Tecnológica](#stack-tecnológica)
- [Arquitetura](#arquitetura)
- [Configuração e Execução](#configuração-e-execução)
- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
  - [Auth](#auth--auth)
  - [Exercícios](#exercícios--exercises)
  - [Sessões de Treino](#sessões-de-treino--training-sessions)
  - [Séries](#séries--exercise-sets)
  - [Performance](#performance--performance)
  - [Templates](#templates--templates)
- [Modelos de Dados](#modelos-de-dados)

---

## Visão Geral

O GymTracker API é um backend completo para aplicações de acompanhamento de musculação. O sistema permite que usuários registrem suas sessões de treino, acompanhem a evolução de cargas ao longo do tempo e obtenham métricas de performance calculadas automaticamente.

O fluxo de uso típico é:

1. Usuário cria uma conta e faz login (recebe JWT)
2. Cria uma sessão de treino vinculada a um grupo muscular
3. Adiciona séries de exercícios à sessão (peso, repetições)
4. Consulta métricas de performance: volume de carga, recordes pessoais, estimativa de 1RM
5. Opcionalmente cria templates para reutilizar combinações de exercícios favoritas

---

## Requisitos Funcionais

### RF-01 — Gerenciamento de Usuários
- O sistema deve permitir o cadastro de novos usuários com nome, e-mail, senha, peso corporal e gênero
- O sistema deve autenticar usuários via e-mail e senha, retornando um token JWT
- Senhas devem ter no mínimo 8 caracteres e são armazenadas com hash BCrypt
- O token JWT tem validade de aproximadamente 16 horas

### RF-02 — Gerenciamento de Exercícios
- O sistema deve listar todos os exercícios disponíveis
- O sistema deve permitir filtrar exercícios por grupo muscular (via ID ou nome)
- O sistema deve permitir buscar exercícios por nome (busca parcial, case-insensitive)
- O sistema deve permitir criar, atualizar e remover exercícios personalizados
- Cada exercício é associado a um grupo muscular (ex: Peito, Costas, Pernas)

### RF-03 — Sessões de Treino
- O sistema deve permitir criar sessões de treino vinculadas ao usuário autenticado e a um grupo muscular
- O sistema deve retornar o detalhamento completo de uma sessão, incluindo todas as séries registradas
- O sistema deve fornecer um histórico paginado de sessões do usuário, ordenado por data decrescente
- Cada sessão pode ter nome, data, notas e grupo muscular associado

### RF-04 — Registro de Séries
- O sistema deve permitir adicionar séries (sets) a uma sessão de treino existente
- Cada série é vinculada a um exercício específico e registra peso e repetições
- O número da série dentro da sessão é calculado automaticamente pelo sistema

### RF-05 — Análise de Performance
- O sistema deve calcular o **volume de carga** de uma sessão (peso × repetições por exercício, com total geral)
- O sistema deve identificar os **recordes pessoais** do usuário (maior peso por exercício)
- O sistema deve calcular o **1RM estimado** usando a fórmula de Brzycki: `Peso / (1.0278 − 0.0278 × Reps)`
- O sistema deve retornar a **distribuição de volume** por grupo muscular nos últimos N dias (padrão: 7)

### RF-06 — Templates de Treino
- O sistema deve permitir criar templates de treino com uma lista de exercícios predefinidos
- O sistema deve listar os templates do usuário autenticado
- O sistema deve garantir que apenas o dono do template possa removê-lo

---

## Stack Tecnológica

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.2 | Framework web |
| Spring Security | — | Autenticação e autorização |
| Auth0 java-jwt | 4.4.0 | Geração e validação de tokens JWT |
| Spring Data JPA / Hibernate | — | ORM e acesso a dados |
| MySQL | 8+ | Banco de dados relacional |
| Flyway | — | Migrations automáticas de banco |
| Lombok | — | Redução de boilerplate |
| Maven | — | Gerenciador de dependências e build |

---

## Arquitetura

```
src/main/java/com/example/gymTracker/
├── config/           # Segurança: JWT filter, SecurityConfig, TokenService, AuthService
├── controller/       # Camada HTTP: recebe requisições e devolve respostas
├── service/          # Regras de negócio
├── repository/       # Acesso ao banco via Spring Data JPA
├── model/            # Entidades JPA
└── dto/              # Objetos de transferência (request, response, data)
    ├── request/
    └── response/
```

**Fluxo de uma requisição autenticada:**

```
Cliente → JwtAuthenticationFilter → Controller → Service → Repository → MySQL
                  ↓
         Valida token JWT
         Carrega usuário no SecurityContext
```

---

## Configuração e Execução

### Pré-requisitos

- Java 21+
- MySQL 8+ rodando em `localhost:3306`
- Banco de dados `gymTracker` criado

```sql
CREATE DATABASE gymTracker;
```

### Variáveis de Ambiente

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_PASSWORD` | Senha do MySQL | `minhasenha123` |
| `TOKEN_KEY` | Chave secreta para assinar o JWT | `chave-secreta-longa-e-aleatoria` |

### Executando

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd gymtracker-api

# Configure as variáveis de ambiente
export DB_PASSWORD=sua_senha
export TOKEN_KEY=sua_chave_secreta

# Execute
mvn spring-boot:run
```

As migrations Flyway são executadas automaticamente ao iniciar a aplicação. O servidor sobe em `http://localhost:8080`.

---

## Autenticação

A API usa **JWT (JSON Web Token)** para autenticação stateless. As únicas rotas públicas são `/auth/login` e `/auth/register`. Todas as demais exigem o token no header:

```
Authorization: Bearer <seu_token_jwt>
```

**Fluxo completo:**

```
1. POST /auth/register  →  cria conta
2. POST /auth/login     →  recebe { "token": "eyJ..." }
3. Todas as requisições →  header: Authorization: Bearer eyJ...
```

---

## Endpoints

### Auth — `/auth`

> Rotas públicas. Não requerem autenticação.

---

#### `POST /auth/register` — Cadastrar usuário

**Request Body:**

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123",
  "bodyWeight": 80.5,
  "gender": "M"
}
```

| Campo | Tipo | Obrigatório | Regras |
|---|---|---|---|
| `name` | String | Sim | — |
| `email` | String | Sim | Formato de e-mail válido |
| `password` | String | Sim | Mínimo 8 caracteres |
| `bodyWeight` | BigDecimal | Sim | — |
| `gender` | String | Sim | `"M"` ou `"F"` |

**Response `201 Created`:**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com"
}
```

---

#### `POST /auth/login` — Autenticar usuário

**Request Body:**

```json
{
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Response `200 OK`:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOjEsInN1YiI6ImpvYW9AZW1haWwuY29tIiwiaXNzIjoiZ3ltVHJhY2tlciIsImV4cCI6MTcxNzE2MDAwMH0.xxxx"
}
```

Use o valor de `token` no header `Authorization: Bearer <token>` para todas as requisições seguintes.

---

### Exercícios — `/exercises`

> Todas as rotas requerem autenticação.

---

#### `GET /exercises` — Listar todos os exercícios

**Response `200 OK`:**

```json
[
  { "id": 1,  "name": "Supino Reto com Barra",     "trainingGroupId": 1 },
  { "id": 2,  "name": "Supino Reto com Halteres",  "trainingGroupId": 1 },
  { "id": 17, "name": "Puxada Pulley Frente",       "trainingGroupId": 2 },
  { "id": 18, "name": "Remada Curvada com Barra",   "trainingGroupId": 2 }
]
```

---

#### `GET /exercises/group/{id}` — Listar exercícios por grupo muscular (ID)

**Exemplo:** `GET /exercises/group/1`

**Response `200 OK`:**

```json
[
  { "id": 1, "name": "Supino Reto com Barra",    "trainingGroupId": 1 },
  { "id": 2, "name": "Supino Reto com Halteres", "trainingGroupId": 1 },
  { "id": 3, "name": "Crucifixo com Halteres",   "trainingGroupId": 1 }
]
```

---

#### `GET /exercises/search?q={termo}` — Buscar exercícios por nome

A busca é parcial e case-insensitive.

**Exemplo:** `GET /exercises/search?q=supino`

**Response `200 OK`:**

```json
[
  { "id": 1, "name": "Supino Reto com Barra",    "trainingGroupId": 1 },
  { "id": 2, "name": "Supino Reto com Halteres", "trainingGroupId": 1 },
  { "id": 4, "name": "Supino Inclinado",          "trainingGroupId": 1 }
]
```

---

#### `GET /exercises/muscle-group/name/{name}` — Buscar exercícios pelo nome do grupo muscular

**Exemplo:** `GET /exercises/muscle-group/name/costas`

**Response `200 OK`:**

```json
[
  { "id": 17, "name": "Puxada Pulley Frente",     "trainingGroupId": 2 },
  { "id": 18, "name": "Remada Curvada com Barra", "trainingGroupId": 2 }
]
```

---

#### `POST /exercises` — Criar exercício personalizado

**Request Body:**

```json
{
  "name": "Rosca Spider",
  "trainingGroupId": 5
}
```

**Response `201 Created`:**

```json
{
  "id": 118,
  "name": "Rosca Spider",
  "trainingGroupId": 5
}
```

---

#### `PUT /exercises/{id}` — Atualizar exercício

**Exemplo:** `PUT /exercises/118`

**Request Body:**

```json
{
  "name": "Rosca Spider com Barra",
  "trainingGroupId": 5
}
```

**Response `200 OK`:**

```json
{
  "id": 118,
  "name": "Rosca Spider com Barra",
  "trainingGroupId": 5
}
```

---

#### `DELETE /exercises/{id}` — Remover exercício

**Exemplo:** `DELETE /exercises/118`

**Response `204 No Content`**

---

### Sessões de Treino — `/training-sessions`

> Todas as rotas requerem autenticação. O usuário é vinculado automaticamente via token.

---

#### `POST /training-sessions` — Criar sessão de treino

**Request Body:**

```json
{
  "name": "Treino de Costas",
  "tgId": 2,
  "date": "2026-04-24",
  "notes": "Foco em força. Aumentar carga no deadlift."
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `name` | String | Sim | Nome da sessão |
| `tgId` | Long | Sim | ID do grupo muscular principal |
| `date` | LocalDate | Sim | Data do treino (`YYYY-MM-DD`) |
| `notes` | String | Não | Anotações livres |

**Response `201 Created`:**

```json
{
  "TsId": 4,
  "name": "Treino de Costas",
  "date": "2026-04-24",
  "tgId": 2,
  "userId": 1,
  "exerciseSets": null,
  "notes": "Foco em força. Aumentar carga no deadlift."
}
```

---

#### `GET /training-sessions/{id}` — Detalhes de uma sessão

Retorna a sessão completa com todas as séries registradas.

**Exemplo:** `GET /training-sessions/4`

**Response `200 OK`:**

```json
{
  "TsId": 4,
  "name": "Treino de Costas",
  "date": "2026-04-24",
  "tgId": 2,
  "userId": 1,
  "notes": "Foco em força. Aumentar carga no deadlift.",
  "exerciseSets": [
    { "ts_id": 4, "exerciseId": 17, "setNumber": 1, "reps": 8,  "weight": 80.0  },
    { "ts_id": 4, "exerciseId": 17, "setNumber": 2, "reps": 6,  "weight": 90.0  },
    { "ts_id": 4, "exerciseId": 17, "setNumber": 3, "reps": 5,  "weight": 100.0 },
    { "ts_id": 4, "exerciseId": 18, "setNumber": 1, "reps": 10, "weight": 60.0  }
  ]
}
```

---

#### `GET /training-sessions/history` — Histórico paginado de sessões

Retorna as sessões do usuário autenticado, ordenadas por data decrescente.

**Query Params:**

| Parâmetro | Tipo | Padrão | Descrição |
|---|---|---|---|
| `page` | int | `0` | Número da página (base 0) |
| `size` | int | `10` | Itens por página |

**Exemplo:** `GET /training-sessions/history?page=0&size=5`

**Response `200 OK`:**

```json
{
  "content": [
    {
      "TsId": 4,
      "name": "Treino de Costas",
      "date": "2026-04-24",
      "tgId": 2,
      "userId": 1,
      "notes": "Foco em força.",
      "exerciseSets": null
    },
    {
      "TsId": 3,
      "name": "Treino de Peito",
      "date": "2026-04-22",
      "tgId": 1,
      "userId": 1,
      "notes": null,
      "exerciseSets": null
    }
  ],
  "totalElements": 12,
  "totalPages": 3,
  "number": 0,
  "size": 5,
  "first": true,
  "last": false
}
```

---

### Séries — `/exercise-sets`

> Requer autenticação.

---

#### `POST /exercise-sets` — Adicionar série a uma sessão

O campo `setNumber` é calculado automaticamente (não precisa ser enviado).

**Request Body:**

```json
{
  "ts_id": 4,
  "exerciseId": 17,
  "weight": 100.0,
  "reps": 6
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `ts_id` | Long | Sim | ID da sessão de treino |
| `exerciseId` | Long | Sim | ID do exercício |
| `weight` | BigDecimal | Sim | Peso utilizado (kg) |
| `reps` | Integer | Sim | Repetições realizadas |

**Response `200 OK`:**

```json
{
  "ts_id": 4,
  "exerciseId": 17,
  "setNumber": 4,
  "weight": 100.0,
  "reps": 6
}
```

> `setNumber` é atribuído automaticamente com base nas séries já existentes para aquele exercício naquela sessão.

---

### Performance — `/performance`

> Todas as rotas requerem autenticação. Os dados são calculados para o usuário autenticado.

---

#### `GET /performance/volume-load/{sessionId}` — Volume de carga de uma sessão

Calcula o volume total (peso × repetições) de cada exercício na sessão e o total geral.

**Exemplo:** `GET /performance/volume-load/4`

**Response `200 OK`:**

```json
{
  "sessionId": 4,
  "sessionName": "Treino de Costas",
  "totalVolume": 2640.0,
  "volumeByExercise": {
    "Puxada Pulley Frente": 1840.0,
    "Remada Curvada com Barra": 800.0
  }
}
```

> **Fórmula:** `Volume = Σ (peso × repetições)` para cada série

---

#### `GET /performance/prs` — Recordes pessoais

Lista o maior peso registrado por exercício para o usuário autenticado, com estimativa de 1RM.

**Response `200 OK`:**

```json
[
  {
    "exerciseName": "Supino Reto com Barra",
    "weight": 120.0,
    "repetitions": 5,
    "date": "2026-04-10",
    "estimatedOneRepMax": 135.0
  },
  {
    "exerciseName": "Agachamento Livre",
    "weight": 160.0,
    "repetitions": 3,
    "date": "2026-04-18",
    "estimatedOneRepMax": 171.56
  }
]
```

---

#### `GET /performance/volume-distribution?days={n}` — Distribuição de volume por grupo muscular

Retorna a quantidade de séries realizadas por grupo muscular nos últimos N dias.

**Query Params:**

| Parâmetro | Tipo | Padrão | Descrição |
|---|---|---|---|
| `days` | int | `7` | Janela de dias a analisar |

**Exemplo:** `GET /performance/volume-distribution?days=14`

**Response `200 OK`:**

```json
{
  "Peito": 18,
  "Costas": 24,
  "Pernas": 30,
  "Ombros": 12,
  "Bíceps": 15,
  "Tríceps": 15
}
```

---

#### `GET /performance/1rm-calc?weight={peso}&reps={reps}` — Calculadora de 1RM

Estima o 1RM (carga máxima para uma repetição) com base em peso e repetições.

**Query Params:**

| Parâmetro | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `weight` | BigDecimal | Sim | Peso utilizado (kg) |
| `reps` | Integer | Sim | Repetições realizadas |

**Exemplo:** `GET /performance/1rm-calc?weight=100&reps=8`

**Response `200 OK`:**

```json
128.57
```

> **Fórmula de Brzycki:** `1RM = Peso / (1.0278 − 0.0278 × Reps)`
> Para `reps = 1`, retorna o próprio peso.

---

### Templates — `/templates`

> Todas as rotas requerem autenticação. Templates são pessoais (por usuário).

---

#### `POST /templates` — Criar template de treino

**Request Body:**

```json
{
  "name": "Push Day",
  "exerciseIds": [1, 3, 5, 34]
}
```

**Response `201 Created`:**

```json
{
  "templateId": 1,
  "name": "Push Day",
  "exerciseIds": [1, 3, 5, 34]
}
```

---

#### `GET /templates/my` — Listar meus templates

**Response `200 OK`:**

```json
[
  {
    "templateId": 1,
    "name": "Push Day",
    "exerciseIds": [1, 3, 5, 34]
  },
  {
    "templateId": 2,
    "name": "Pull Day",
    "exerciseIds": [17, 18, 19, 22]
  },
  {
    "templateId": 3,
    "name": "Leg Day",
    "exerciseIds": [50, 51, 52, 53, 54]
  }
]
```

---

#### `DELETE /templates/{id}` — Remover template

Apenas o dono do template pode removê-lo. Retorna `403` caso contrário.

**Exemplo:** `DELETE /templates/1`

**Response `204 No Content`**

---

## Modelos de Dados

### Tabelas principais

```
users
├── user_id       BIGINT PK AUTO_INCREMENT
├── name          VARCHAR
├── email         VARCHAR UNIQUE
├── password      VARCHAR (BCrypt hash)
├── body_weight   DECIMAL
└── gender        ENUM('M', 'F')

training_groups
├── tg_id         BIGINT PK AUTO_INCREMENT
└── name          VARCHAR  (ex: "Peito", "Costas", "Pernas")

exercises
├── exercise_id        BIGINT PK AUTO_INCREMENT
├── name               VARCHAR
└── training_group_id  BIGINT FK → training_groups

training_sessions
├── session_id  BIGINT PK AUTO_INCREMENT
├── ts_name     VARCHAR
├── date        DATE
├── notes       VARCHAR
├── user_id     BIGINT FK → users
└── tg_id       BIGINT FK → training_groups

exercise_sets
├── set_id      BIGINT PK AUTO_INCREMENT
├── set_number  INT
├── repetitions INT
├── weight      DECIMAL
├── session_id  BIGINT FK → training_sessions
└── exercise_id BIGINT FK → exercises

training_templates
├── template_id  BIGINT PK AUTO_INCREMENT
├── name         VARCHAR
└── user_id      BIGINT FK → users

template_exercises  (tabela de junção N:N)
├── template_id  BIGINT FK → training_templates
└── exercise_id  BIGINT FK → exercises
```

### IDs de grupos musculares (referência)

Os IDs de `training_groups` são populados pelas migrations Flyway. Verifique os arquivos em `src/main/resources/db/migration` para os dados de seed, ou consulte `GET /exercises` para identificar os `trainingGroupId` disponíveis.

---

## Resumo dos Endpoints

| Método | Rota | Autenticação | Descrição |
|---|---|---|---|
| POST | `/auth/register` | ❌ | Cadastrar novo usuário |
| POST | `/auth/login` | ❌ | Autenticar e obter JWT |
| GET | `/exercises` | ✅ | Listar todos os exercícios |
| GET | `/exercises/group/{id}` | ✅ | Exercícios por grupo muscular (ID) |
| GET | `/exercises/search?q=` | ✅ | Buscar exercícios por nome |
| GET | `/exercises/muscle-group/name/{name}` | ✅ | Exercícios por nome do grupo muscular |
| POST | `/exercises` | ✅ | Criar exercício personalizado |
| PUT | `/exercises/{id}` | ✅ | Atualizar exercício |
| DELETE | `/exercises/{id}` | ✅ | Remover exercício |
| POST | `/training-sessions` | ✅ | Criar sessão de treino |
| GET | `/training-sessions/{id}` | ✅ | Detalhes de uma sessão |
| GET | `/training-sessions/history` | ✅ | Histórico paginado de sessões |
| POST | `/exercise-sets` | ✅ | Adicionar série a uma sessão |
| GET | `/performance/volume-load/{sessionId}` | ✅ | Volume de carga por sessão |
| GET | `/performance/prs` | ✅ | Recordes pessoais |
| GET | `/performance/volume-distribution?days=` | ✅ | Distribuição de volume por grupo muscular |
| GET | `/performance/1rm-calc?weight=&reps=` | ✅ | Calculadora de 1RM estimado |
| POST | `/templates` | ✅ | Criar template de treino |
| GET | `/templates/my` | ✅ | Listar meus templates |
| DELETE | `/templates/{id}` | ✅ | Remover template |
