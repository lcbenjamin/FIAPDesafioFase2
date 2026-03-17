# Tech Challenge Fase 1 - Backend de Usuários

Este projeto implementa um backend em Spring Boot para gestão de usuários de restaurantes.

## Endpoints principais (v1)
- POST /api/v1/usuarios — cadastra usuário
- GET /api/v1/usuarios/{id} — busca por id
- PUT /api/v1/usuarios/{id} — atualiza dados (exceto senha)
- PATCH /api/v1/usuarios/{id}/senha — troca senha
- DELETE /api/v1/usuarios/{id} — exclui usuário
- GET /api/v1/usuarios/buscar?nome=ABC — busca por nome
- POST /api/v1/usuarios/login?login=abc&senha=123 — valida login

Erros seguem padrão RFC 7807 (ProblemDetail).

## Swagger
A documentação está disponível em `/swagger-ui.html` após iniciar a aplicação.

## Execução local
1. Suba o banco com Docker Compose e a aplicação:

```
docker-compose up --build
```

2. Acesse `http://localhost:8080/swagger-ui.html`.

## Variáveis de ambiente
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD

## Postman
A coleção está no arquivo `postman_collection.json`.

