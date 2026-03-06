🏋️ GymTracker API
O GymTracker é uma API REST desenvolvida para transformar registros brutos
de treino em inteligência de performance. Mais do que um diário digital,
a plataforma atua como uma ferramenta analítica para praticantes de musculação
que buscam precisão no acompanhamento de sua evolução.

🎯 Por que o GymTracker?
A estagnação (platô) nos treinos ocorre por falta de dados claros.
Esta API resolve esse problema ao estruturar cada série e repetição, permitindo:

📈 Identificação de Platôs: Analise estatísticas para entender
onde e por que sua evolução estagnou.

⚖️ Sobrecarga Progressiva: Monitore o aumento de carga e volume
total para garantir estímulos constantes.

🔍 Visão Analítica: Converta logs de peso e repetições em
indicadores de desempenho estratégicos.

🔐 Cheat Sheet -> Controller AUTH
📝 POST: auth/register
Request Body:
```json
{
"name": "Seu Nome",
"email": "seu_nome@email.com",
"password": "sua_senha",
"bodyWeight": 60,
"gender": "M"
}
```

Response (201 Created):
```json
{
"id": 4,
"name": "Seu Nome",
"email": "seu_nome@email.com"
}
```

🔑 POST: auth/login
Request Body:
```json
{
"email": "seu_nome@email.com",
"password": "sua_senha"
}
```

Response (200 OK):
```json
{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

🏋️ Cheat Sheet -> Controller EXERCISE
🔐 Requer Header: Authorization: Bearer <token>

📋 GET: exercise/list
Response (200 OK):
```json
[
{ "id": 1, "name": "Supino Reto com Barra", "trainingGroupId": 1 },
{ "id": 17, "name": "Puxada Pulley Frente", "trainingGroupId": 2 },
{ "id": 33, "name": "Desenvolvimento Halteres", "trainingGroupId": 3 },
"..."
]
```

🔍 GET: exercise/group/{id}
Response (200 OK):
```json
[
{ "id": 1, "name": "Supino Reto com Barra", "trainingGroupId": 1 },
{ "id": 2, "name": "Supino Reto com Halteres", "trainingGroupId": 1 },
"..."
]
```

➕ POST: exercise
Request Body:
```json
{
"name": "Exercicio Teste",
"trainingGroupId": 2
}
```

Response (201 Created):
```json
{
"id": 118,
"name": "Exercicio Teste",
"trainingGroupId": 2
}
```

📊 Cheat Sheet -> Training Session & Sets
🔐 Requer Header: Authorization: Bearer <token>

📅 POST: ts/createTS
Request Body:
```json
{
"name": "Treino de Costas",
"tgId": 2,
"date": "2026-03-06",
"notes": "Foco em força!"
}
```

Response (200 OK):
```json
{
"TsId": 4,
"date": "2026-03-06",
"exerciseSets": null,
"name": "Treino de Costas",
"notes": "Foco em força!",
"tgId": 2,
"userId": 4
}
```

📈 POST: api/sets
Request Body:
```json
{
"ts_id": 4,
"exerciseId" : 17,
"weight" : 100,
"reps" : 8
}
```

Response (200 OK):
```json
{
"exerciseId": 17,
"reps": 8,
"ts_id": 4,
"weight": 100
}
```

📑 GET: ts/{id}
Response (200 OK):
```json
{
"tsId": 4,
"name": "Treino de Costas",
"date": "2026-03-06",
"tgId": 2,
"exerciseSets": [
{ "exerciseId": 17, "setNumber": 1, "reps": 8, "weight": 100.0 },
{ "exerciseId": 17, "setNumber": 2, "reps": 4, "weight": 160.0 },
{ "exerciseId": 18, "setNumber": 1, "reps": 10, "weight": 140.0 },
"..."
],
"notes": "Foco em força!"
}
```
