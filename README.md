# GymTracker API

API REST para registro e análise de treinos de musculação.

---

## Requisitos Funcionais

### RF-01 — Usuários
- Cadastro com nome, e-mail, senha (mín. 8 caracteres), peso corporal e gênero
- Autenticação via e-mail e senha com retorno de token JWT (~16h de validade)
- Senhas armazenadas com hash BCrypt

### RF-02 — Exercícios
- Listar todos os exercícios disponíveis
- Filtrar por grupo muscular (via ID ou nome)
- Buscar por nome (parcial, case-insensitive)
- Criar, atualizar e remover exercícios personalizados
- Cada exercício é vinculado a um grupo muscular

### RF-03 — Sessões de Treino
- Criar sessões vinculadas ao usuário autenticado e a um grupo muscular
- Consultar sessão completa com todas as séries registradas
- Histórico paginado de sessões ordenado por data decrescente

### RF-04 — Séries
- Adicionar séries a uma sessão existente (exercício, peso, repetições)
- Número da série calculado automaticamente pelo sistema

### RF-05 — Performance
- Volume de carga por sessão: `Σ (peso × repetições)` por exercício e total
- Recordes pessoais: maior peso registrado por exercício, com 1RM estimado
- 1RM estimado pela fórmula de Brzycki: `peso / (1.0278 − 0.0278 × reps)`
- Distribuição de volume por grupo muscular nos últimos N dias (padrão: 7)

### RF-06 — Templates
- Criar templates com lista de exercícios predefinidos
- Listar templates do usuário autenticado
- Apenas o dono pode remover o próprio template

---

## Endpoints

Rotas públicas: `/auth/register` e `/auth/login`.
Todas as demais exigem header: `Authorization: Bearer <token>`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/register` | Cadastrar usuário |
| POST | `/auth/login` | Autenticar e obter JWT |
| GET | `/exercises` | Listar todos os exercícios |
| GET | `/exercises/group/{id}` | Exercícios por grupo muscular (ID) |
| GET | `/exercises/search?q=` | Buscar exercícios por nome |
| GET | `/exercises/muscle-group/name/{name}` | Exercícios por nome do grupo muscular |
| POST | `/exercises` | Criar exercício personalizado |
| PUT | `/exercises/{id}` | Atualizar exercício |
| DELETE | `/exercises/{id}` | Remover exercício |
| POST | `/training-sessions` | Criar sessão de treino |
| GET | `/training-sessions/{id}` | Detalhes completos de uma sessão |
| GET | `/training-sessions/history` | Histórico paginado de sessões |
| POST | `/exercise-sets` | Adicionar série a uma sessão |
| GET | `/performance/volume-load/{sessionId}` | Volume de carga por sessão |
| GET | `/performance/prs` | Recordes pessoais |
| GET | `/performance/volume-distribution?days=` | Distribuição de volume por grupo muscular |
| GET | `/performance/1rm-calc?weight=&reps=` | Calculadora de 1RM estimado |
| POST | `/templates` | Criar template de treino |
| GET | `/templates/my` | Listar meus templates |
| DELETE | `/templates/{id}` | Remover template |

Para exemplos de JSON de entrada e saída de cada endpoint, consulte `Documentacao.txt`.
