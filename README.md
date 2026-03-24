# Tech Challenge - Backend de Gestao de Restaurantes (Fase 2)

Este projeto implementa uma API REST em Spring Boot para gestao de:

- Usuarios
- Tipos de usuario
- Restaurantes
- Itens de cardapio

O README foi organizado para servir como:
- guia de uso do app
- guia de testes (Postman/Insomnia)
- guia para usuario que vai consumir a API

## 1) Escopo implementado

A aplicacao entrega:
- CRUD de tipos de usuario
- CRUD de usuarios
- endpoint separado para troca de senha
- endpoint separado para atualizacao de dados do usuario
- busca de usuarios por nome
- validacao simples de login (login + senha)
- garantia de unicidade de e-mail no cadastro de usuario
- registro de data da ultima alteracao do usuario
- CRUD de restaurantes com dono associado a usuario existente
- CRUD de itens de cardapio associados a restaurante existente
- execucao com Docker Compose (app + MySQL)

## 2) Tecnologias

- Java 17
- Spring Boot 3.3.4
- Spring Web, Validation, Spring Data JPA
- MySQL 8
- Swagger/OpenAPI (`springdoc-openapi`)
- Maven
- Docker / Docker Compose

## 3) Como executar

### Opcao A (recomendada): Docker Compose

```powershell
docker-compose up --build
```

A aplicacao sobe em `http://localhost:8080`.

### Opcao B: rodar app localmente

O `application.yml` indica, por padrao:
- URL: `jdbc:mysql://localhost:3306/desafio_fase1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
- usuario: `desafio`
- senha: `desafio`

Voce pode sobrescrever com:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Para iniciar local:

```powershell
mvn spring-boot:run
```

## 4) Documentacao da API

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Arquivo para Postman: `postman_collection.json`

## 5) Ordem recomendada para testes no Postman

Siga esta ordem para evitar erros de relacionamento entre entidades:

1. Criar tipo de usuario `CLIENTE`
2. Criar tipo de usuario `DONO_RESTAURANTE`
3. Criar usuario dono (usando `tipoId` de dono)
4. Criar usuario cliente (usando `tipoId` de cliente)
5. Criar restaurante (usando `donoId` do usuario dono)
6. Criar item de cardapio (usando `restauranteId` do restaurante)
7. Testar listagens, buscas por id e atualizacoes
8. Testar troca de senha e validacao de login
9. Testar exclusoes

## 6) Endpoints

Base URL: `http://localhost:8080`

### 6.1 Tipos de usuario

- `POST /api/v1/tipos-usuario` - Cadastrar tipo
- `GET /api/v1/tipos-usuario` - Listar tipos
- `GET /api/v1/tipos-usuario/{id}` - Buscar por id
- `PUT /api/v1/tipos-usuario/{id}` - Atualizar tipo
- `DELETE /api/v1/tipos-usuario/{id}` - Excluir tipo

Exemplo de body (POST/PUT):

```json
{
  "nomeTipo": "CLIENTE"
}
```

### 6.2 Usuarios

- `POST /api/v1/usuarios` - Cadastrar usuario
- `GET /api/v1/usuarios/{id}` - Buscar usuario por id
- `PUT /api/v1/usuarios/{id}` - Atualizar dados (exceto senha)
- `PATCH /api/v1/usuarios/{id}/senha` - Trocar senha
- `PATCH /api/v1/usuarios/{id}/tipo` - Alterar tipo do usuario
- `DELETE /api/v1/usuarios/{id}` - Excluir usuario
- `GET /api/v1/usuarios/buscar?nome=abc` - Buscar usuarios por nome
- `POST /api/v1/usuarios/login?login=abc&senha=123` - Validar login

Exemplo de body (POST `/api/v1/usuarios`):

```json
{
  "nome": "Lucas Costa",
  "email": "lucasc@email.com",
  "login": "lucas",
  "senha": "123456",
  "endereco": {
    "rua": "Rua Manoel Borba",
    "numero": "1000",
    "cidade": "Recife",
    "cep": "090890-001"
  },
  "tipoId": 1
}
```

Exemplo de body (PUT `/api/v1/usuarios/{id}`):

```json
{
  "nome": "Lucas Costa",
  "endereco": {
    "rua": "Avenida Manoel Borba",
    "numero": "999",
    "cidade": "recife",
    "cep": "070000-000"
  },
  "tipoId": 2
}
```

Exemplo de body (PATCH senha):

```json
{
  "novaSenha": "novaSenha123"
}
```

Exemplo de body (PATCH tipo):

```json
{
  "tipoId": 2
}
```

### 6.3 Restaurantes

- `POST /api/v1/restaurantes` - Cadastrar restaurante
- `GET /api/v1/restaurantes` - Listar restaurantes
- `GET /api/v1/restaurantes/{id}` - Buscar restaurante por id
- `PUT /api/v1/restaurantes/{id}` - Atualizar restaurante
- `DELETE /api/v1/restaurantes/{id}` - Excluir restaurante

Exemplo de body (POST/PUT):

```json
{
  "nome": "Restaurante da Neide",
  "endereco": {
    "rua": "Rua Neide Silva",
    "numero": "456",
    "cidade": "Olinda",
    "cep": "02000-000"
  },
  "tipoCozinha": "Brasileira",
  "horarioFuncionamento": "11:00-23:00",
  "donoId": 1
}
```

### 6.4 Itens de cardapio

- `POST /api/v1/itens-cardapio` - Cadastrar item
- `GET /api/v1/itens-cardapio` - Listar todos os itens
- `GET /api/v1/itens-cardapio/{id}` - Buscar item por id
- `GET /api/v1/itens-cardapio/restaurante/{restauranteId}` - Listar itens por restaurante
- `PUT /api/v1/itens-cardapio/{id}` - Atualizar item
- `DELETE /api/v1/itens-cardapio/{id}` - Excluir item

Exemplo de body (POST/PUT):

```json
{
  "nome": "Risoto de Cogumelos",
  "descricao": "Arroz arborio com cogumelos frescos",
  "preco": 59.9,
  "apenasLocalizado": true,
  "caminhoFoto": "/imagens/risoto.jpg",
  "restauranteId": 1
}
```

## 7) Estrutura de erro (ProblemDetail - RFC 7807)

Para erros de negocio/validacao, a API retorna objeto padrao `ProblemDetail`.

Exemplo de e-mail duplicado:

```json
{
  "type": "about:blank",
  "title": "Dados invalidos",
  "status": 400,
  "detail": "E-mail ja cadastrado",
  "instance": "/api/v1/usuarios"
}
```

Exemplo de erro de validacao:

```json
{
  "type": "about:blank",
  "title": "Erro de validacao",
  "status": 400,
  "detail": "email: must be a well-formed email address, nome: must not be blank"
}
```

Erros genericos retornam `500` com detalhe padrao.

## 8) Guia de testes (cenarios principais)

Cenarios recomendados para cobrirem os requisitos das fases 1 e 2:

1. Cadastro de tipo de usuario valido
2. Tentativa de tipo de usuario duplicado
3. Cadastro de usuario valido
4. Tentativa de cadastro de usuario com e-mail duplicado
5. Atualizacao de dados de usuario (PUT)
6. Troca de senha de usuario (PATCH senha)
7. Alteracao de tipo de usuario (PATCH tipo)
8. Busca de usuario por nome
9. Validacao de login (sucesso e erro)
10. CRUD completo de restaurante
11. CRUD completo de item de cardapio
12. Exclusao de tipo de usuario com usuarios vinculados (deve falhar)

## 9) Testes automatizados

Existem testes em `src/test/java/br/com/gestaoeventos/desafioFase1`:
- `UserServiceTest.java`
- `RestaurantServiceTest.java`
- `MenuItemServiceTest.java`

Para executar:

```powershell
mvn test
```

## 10) Estrutura do projeto

- `src/main/java/br/com/gestaoeventos/desafioFase1/domain` - Entidades
- `src/main/java/br/com/gestaoeventos/desafioFase1/repository` - Repositorios
- `src/main/java/br/com/gestaoeventos/desafioFase1/service` - Regras de negocio
- `src/main/java/br/com/gestaoeventos/desafioFase1/api/controller` - Endpoints REST
- `src/main/java/br/com/gestaoeventos/desafioFase1/api/dto` - DTOs de entrada/saida
- `src/main/java/br/com/gestaoeventos/desafioFase1/api/mapper` - Conversao entidade/DTO
- `src/main/java/br/com/gestaoeventos/desafioFase1/api/exception` - Tratamento de erros

## 11) Observacoes importantes

- O endpoint de login faz validacao simples (sem JWT nesta fase).
- Usuario precisa de `tipoId` valido para ser criado/atualizado.
- Restaurante precisa de `donoId` valido (usuario existente).
- Item de cardapio precisa de `restauranteId` valido.
- O arquivo `postman_collection.json` pode ser importado em ferramenta similar ao Postman para testes.
