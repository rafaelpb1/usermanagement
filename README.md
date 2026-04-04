# User Management & Vehicle Sales API

Sistema de gestão para operação de concessionária, com backend em Spring Boot (quase completo) e frontend React em fase inicial.

## Status do Projeto

Este projeto está em desenvolvimento ativo.

- Backend: majoritariamente implementado (autenticação, autorização, CRUDs e registro de vendas).
- Frontend: iniciado, com integração de login em andamento.

## Objetivo do Produto

Centralizar a operação de cadastro e consulta de:

- Usuários e autenticação (JWT)
- Clientes
- Funcionários
- Veículos
- Vendas

Com controle de acesso por perfil (`ADMIN` e `USER`) e documentação interativa via Swagger/OpenAPI.

## Principais Funcionalidades

- Login e registro de usuários com token JWT.
- Controle de autorização por perfil:
  - `ADMIN`: cria/atualiza/exclui entidades e registra vendas.
  - `USER`: consulta listagens.
- Gestão de clientes (cadastro, listagem, atualização e remoção).
- Gestão de funcionários (cadastro, listagem, atualização e remoção).
- Gestão de veículos (cadastro, listagem, atualização e remoção).
- Registro de vendas com regra de negócio:
  - Apenas veículo `AVAILABLE` pode ser vendido.
  - Após venda, status do veículo é atualizado para `SOLD`.
- Tratamento centralizado de erros e validações.
- Interface Swagger para exploração da API.
- Banco H2 em memória para desenvolvimento local.

## Arquitetura e Stack

### Backend

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Security + JWT (`java-jwt`)
- Spring Data JPA
- H2 Database
- Bean Validation
- MapStruct
- SpringDoc OpenAPI (Swagger UI)
- Lombok

### Frontend

- React 19 + TypeScript
- Vite
- Axios
- React Router (base já instalada)

## Estrutura do Repositório

```text
.
├── src/main/java/com/example/usermanagement
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── dto
│   ├── security
│   ├── mappers
│   └── exception
├── src/main/resources
│   ├── application.yml
│   └── data.sql
├── frontend
│   └── src
└── pom.xml
```

## Como Executar Localmente

## Pré-requisitos

- Java 17+
- Maven (ou usar `./mvnw`)
- Node.js 18+ e npm

## 1. Subir Backend

```bash
./mvnw spring-boot:run
```

Backend disponível em: `http://localhost:8090`

## 2. Subir Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend disponível em: `http://localhost:5173`

## Configurações Importantes

Arquivo: `src/main/resources/application.yml`

- Porta da API: `8090`
- Banco: H2 em memória (`jdbc:h2:mem:usermanagement`)
- JWT secret: `api.security.token.secret`
  - default local: `my-secret-key`
  - recomendado em produção: definir `JWT_SECRET`

Exemplo:

```bash
export JWT_SECRET="um-segredo-forte-aqui"
```

## Acessos de Desenvolvimento

- Swagger UI: `http://localhost:8090/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8090/v3/api-docs`
- H2 Console: `http://localhost:8090/h2-console`

Credenciais H2 (conforme `application.yml`):

- JDBC URL: `jdbc:h2:mem:usermanagement`
- User: `sa`
- Password: `password`

## Fluxo de Autenticação

1. Registrar usuário em `POST /auth/register`.
2. Fazer login em `POST /auth/login`.
3. Usar token JWT no header:

```http
Authorization: Bearer <seu_token>
```

Validade do token: aproximadamente 2 horas.

## Matriz de Permissões

| Recurso | Ação | ADMIN | USER |
| --- | --- | --- | --- |
| Auth | Login/Register | Sim | Sim |
| Customers | GET | Sim | Sim |
| Customers | POST/PUT/DELETE | Sim | Não |
| Employees | GET | Sim | Sim |
| Employees | POST/PUT/DELETE | Sim | Não |
| Vehicles | GET | Sim | Sim |
| Vehicles | POST/PUT/DELETE | Sim | Não |
| Sales | GET | Sim | Sim |
| Sales | POST/DELETE | Sim | Não |

## Endpoints da API

## Autenticação

- `POST /auth/register`
- `POST /auth/login`

## Clientes (`/customers`)

- `POST /customers`
- `GET /customers`
- `PUT /customers/{document}`
- `DELETE /customers/{document}`

## Funcionários (`/employees`)

- `POST /employees`
- `GET /employees`
- `PUT /employees/{id}`
- `DELETE /employees/{id}`

## Veículos (`/vehicles`)

- `POST /vehicles`
- `GET /vehicles`
- `PUT /vehicles/{vin}`
- `DELETE /vehicles/{vin}`

## Vendas (`/sales`)

- `POST /sales`
- `GET /sales`
- `DELETE /sales/{id}`

## Exemplos de Payload

## Register

```json
{
  "login": "admin",
  "password": "123456",
  "role": "ADMIN"
}
```

## Login

```json
{
  "login": "admin",
  "password": "123456"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI...",
  "expiresIn": 7200
}
```

## Cadastro de Veículo

```json
{
  "vin": "1FAFP34341W207183",
  "licensePlate": "AAA-1A11",
  "brand": "Toyota",
  "model": "Corolla",
  "color": "Prata",
  "year": 2025,
  "price": 125000.00,
  "status": "AVAILABLE"
}
```

## Registro de Venda

```json
{
  "vehicleVin": "1FAFP34341W207183",
  "customerDocument": "28378947025",
  "employeeId": 1,
  "saleValue": 120000.00,
  "paymentMethod": "PIX"
}
```

## Padrão de Erros

A API retorna estrutura padronizada:

```json
{
  "status": 400,
  "message": "Validation Error",
  "errors": [
    {
      "field": "login",
      "message": "Login is required"
    }
  ]
}
```

## Frontend (Estado Atual)

Implementações iniciadas:

- Serviço de autenticação e sessão (`token` em `localStorage`).
- Integração com endpoint de login.
- Estrutura base para páginas (`LoginPage` e `DashboardPage`).

Em evolução:

- Rotas protegidas.
- Telas de CRUD para entidades.
- Dashboard operacional com indicadores.
- Tratamento de erros e feedback visual de autenticação.

## Roadmap

- Finalizar fluxo completo do frontend.
- Adicionar testes unitários e de integração (backend e frontend).
- Adicionar seed de dados para ambiente de demonstração.
- Melhorar observabilidade e logs.
- Preparar deploy (API + Web) com pipeline CI/CD.

## Qualidade e Boas Práticas

- Documentação OpenAPI com Swagger.
- Separação por camadas (`controller`, `service`, `repository`).
- DTOs para contrato de entrada/saída.
- Mapeamento com MapStruct.
- Segurança stateless com JWT.
- Tratamento centralizado de exceções.

## Contribuição

Como o projeto está em evolução, contribuições e sugestões são bem-vindas.

Fluxo recomendado:

1. Criar branch de feature/fix.
2. Implementar alteração com commits pequenos e claros.
3. Abrir PR com descrição funcional e técnica.

## Licença

Defina aqui a licença do projeto (ex.: MIT) antes de publicar em produção.
