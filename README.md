# GymTracker API

API REST para registro e análise de treinos de musculação. Transforma séries e repetições em dados de performance: volume de carga, recordes pessoais, estimativa de 1RM e distribuição de volume por grupo muscular.

## Stack

| Tecnologia | Versão |
|---|---|
| Spring Boot | 4.0.2 |
| Java | 21 |
| MySQL | 8+ |
| Flyway | (migrations automáticas) |
| Spring Security + JWT | Auth0 java-jwt 4.4.0 |
| Lombok | - |
| Maven | - |

## Pré-requisitos

- Java 21+
- MySQL rodando em `localhost:3306` com banco `gymTracker` criado
- Variável de ambiente `DB_PASSWORD` configurada

## Executando

```bash
mvn spring-boot:run
```

As migrations Flyway rodam automaticamente ao iniciar.

---

## Endpoints

### Auth — `/auth`

Rotas públicas, sem autenticação.

#### `POST /auth/register`

```json
// Request
{
  "name": "Seu Nome",
  "email": "email@exemplo.com",
  "password": "sua_senha",
  "bodyWeight": 75,
  "gender": "M"
}

// Response 201
{
  "id": 1,
  "name": "Seu Nome",
  "email": "email@exemplo.com"
}
```

#### `POST /auth/login`

```json
// Request
{
  "email": "email@exemplo.com",
  "password": "sua_senha"
}

// Response 200
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

> Todas as rotas abaixo requerem o header:
> `Authorization: Bearer <token>`

---

### Exercícios — `/exercise`

#### `GET /exercise/list`
Retorna todos os exercícios cadastrados.

```json
// Response 200
[
  { "id": 1, "name": "Supino Reto com Barra", "trainingGroupId": 1 },
  { "id": 17, "name": "Puxada Pulley Frente", "trainingGroupId": 2 }
]
```

#### `GET /exercise/group/{id}`
Exercícios filtrados por grupo muscular (ID).

```json
// Response 200
[
  { "id": 1, "name": "Supino Reto com Barra", "trainingGroupId": 1 },
  { "id": 2, "name": "Supino Reto com Halteres", "trainingGroupId": 1 }
]
```

#### `GET /exercise/search?q={termo}`
Busca exercícios por nome.

```
GET /exercise/search?q=supino
```

#### `GET /exercise/muscle-group/name/{name}`
Exercícios filtrados pelo nome do grupo muscular.

```
GET /exercise/muscle-group/name/peito
```

#### `POST /exercise`
Cria um exercício personalizado.

```json
// Request
{
  "name": "Exercício Customizado",
  "trainingGroupId": 2
}

// Response 201
{
  "id": 118,
  "name": "Exercício Customizado",
  "trainingGroupId": 2
}
```

#### `PUT /exercise/{id}`
Atualiza um exercício existente.

```json
// Request
{
  "name": "Novo Nome",
  "trainingGroupId": 2
}

// Response 200
{
  "id": 118,
  "name": "Novo Nome",
  "trainingGroupId": 2
}
```

#### `DELETE /exercise/{id}`
Remove um exercício. Response `204 No Content`.

---

### Sessões de Treino — `/ts`

#### `POST /ts/createTS`
Cria uma nova sessão de treino.

```json
// Request
{
  "name": "Treino de Costas",
  "tgId": 2,
  "date": "2026-04-20",
  "notes": "Foco em força!"
}

// Response 200
{
  "TsId": 4,
  "name": "Treino de Costas",
  "date": "2026-04-20",
  "tgId": 2,
  "userId": 1,
  "exerciseSets": null,
  "notes": "Foco em força!"
}
```

#### `GET /ts/{id}`
Retorna detalhes e todas as séries de uma sessão.

```json
// Response 200
{
  "tsId": 4,
  "name": "Treino de Costas",
  "date": "2026-04-20",
  "tgId": 2,
  "notes": "Foco em força!",
  "exerciseSets": [
    { "exerciseId": 17, "setNumber": 1, "reps": 8, "weight": 100.0 },
    { "exerciseId": 17, "setNumber": 2, "reps": 6, "weight": 110.0 }
  ]
}
```

#### `GET /ts/history?page=0&size=10`
Histórico paginado de sessões do usuário autenticado.

```json
// Response 200
{
  "content": [ { "tsId": 4, "name": "Treino de Costas", "date": "2026-04-20", "..." } ],
  "totalElements": 20,
  "totalPages": 2,
  "number": 0
}
```

---

### Séries — `/api/sets`

#### `POST /api/sets`
Adiciona uma série a uma sessão existente.

```json
// Request
{
  "ts_id": 4,
  "exerciseId": 17,
  "weight": 100,
  "reps": 8
}

// Response 200
{
  "exerciseId": 17,
  "reps": 8,
  "ts_id": 4,
  "weight": 100
}
```

---

### Performance — `/performance`

#### `GET /performance/volume-load/{sessionId}`
Calcula o volume de carga total de uma sessão (peso × reps), detalhado por exercício.

```json
// Response 200
{
  "sessionId": 4,
  "sessionName": "Treino de Costas",
  "totalVolume": 2400.0,
  "volumeByExercise": {
    "Puxada Pulley Frente": 1600.0,
    "Remada Curvada": 800.0
  }
}
```

#### `GET /performance/prs`
Lista os recordes pessoais (maior peso por exercício) do usuário autenticado.

```json
// Response 200
[
  {
    "exerciseName": "Supino Reto com Barra",
    "weight": 120.0,
    "repetitions": 5,
    "date": "2026-04-10",
    "estimatedOneRepMax": 135.0
  }
]
```

#### `GET /performance/volume-distribution?days=7`
Distribuição do volume de treino por grupo muscular nos últimos N dias (padrão: 7).

```json
// Response 200
{
  "Peito": 3200,
  "Costas": 4800,
  "Ombros": 1600
}
```

#### `GET /performance/1rm-calc?weight={peso}&reps={reps}`
Estima o 1RM com base em peso e repetições (fórmula de Epley).

```
GET /performance/1rm-calc?weight=100&reps=8
```

```json
// Response 200
133.33
```

---

### Templates de Treino — `/template`

#### `POST /template`
Cria um template de treino com lista de exercícios.

```json
// Request
{
  "name": "Push Day",
  "exerciseIds": [1, 3, 5, 34]
}

// Response 201
{
  "templateId": 1,
  "name": "Push Day",
  "exerciseIds": [1, 3, 5, 34]
}
```

#### `GET /template/my`
Lista os templates do usuário autenticado.

```json
// Response 200
[
  { "templateId": 1, "name": "Push Day", "exerciseIds": [1, 3, 5, 34] },
  { "templateId": 2, "name": "Pull Day", "exerciseIds": [17, 18, 19] }
]
```

#### `DELETE /template/{id}`
Remove um template. Response `204 No Content`.
