# Desafio Backend - Sistema de Contas a Pagar

## Descrição do Projeto

Este projeto implementa uma API REST para um sistema simples de contas a pagar. O sistema permite realizar operações CRUD em contas a pagar, alterar a situação das contas quando forem pagas, obter informações sobre as contas cadastradas no banco de dados e importar um lote de contas a partir de um arquivo CSV.

## Funcionalidades

    - CRUD de contas a pagar:
    - Cadastrar conta
    - Atualizar conta
    - Alterar a situação da conta
    - Obter a lista de contas a pagar com filtro de data de vencimento e descrição
    - Obter conta por ID
    - Obter valor total pago por período
    - Importação de contas a pagar via arquivo CSV

## Tecnologias

- Java 17
- Spring Boot
- PostgreSQL
- Docker
- Docker Compose
- Flyway
- JPA/Hibernate
- Rest Assured (para testes de API)


## APIs Implementadas

### Cadastrar Conta

- **Endpoint**: `/v1/payable-accounts`
- **Método**: POST
- **Descrição**: Cadastra uma nova conta a pagar.
- **Request Body**:
  ```json
  {
    "dueDate": "2023-12-31",
    "paymentDate": "2024-01-05",
    "amount": 100.50,
    "description": "Conta de luz",
    "status": "PENDENTE"
  }
  
### Atualizar Conta

- **Endpoint**: `/v1/payable-accounts/{id}`
- **Método**: PUT
- **Descrição:** Atualiza os dados de uma conta existente.
- **Request Body**:
  ```json
  {
    "dueDate": "2023-12-31",
    "paymentDate": "2024-01-05",
    "amount": 100.50,
    "description": "Conta de luz",
    "status": "PAGO"
  }

### Alterar Situação da Conta

- **Endpoint**: `/v1/payable-accounts/{id}/status`
- **Método**: PATCH
- **Descrição**: Altera a situação de uma conta.
  ```json
  {
    "status": "PAGO"
  }

### Obter Conta por ID

- **Endpoint**: `/v1/payable-accounts/{id}`
- **Método**: GET
- **Descrição**: Retorna uma conta específica pelo ID.

### Obter Lista de Contas a Pagar

- **Endpoint**: `/v1/payable-accounts`
- **Método**: GET
- **Descrição**: Retorna uma lista de contas a pagar com filtro de data de vencimento e descrição.
- **Parâmetros**:
- **page (opcional)**: Número da página (default = 0)
- **size (opcional)**: Tamanho da página (default = 10)
- **dueDate (opcional)**: Filtra por data de vencimento
- **description (opcional)**: Filtra por descrição

### Obter Valor Total Pago por Período

- **Endpoint**: `/v1/payable-accounts/total-paid`
- **Método**: GET
- **Descrição**: Retorna o valor total pago em um determinado período.
- **Parâmetros**:
- **startDate (obrigatório)**: Data de início do período
- **endDate (obrigatório)**: Data de término do período

### Importação de Contas via CSV

- **Endpoint**: `/v1/payable-accounts/import`
- **Método**: POST
- **Descrição**: Importa contas a pagar a partir de um arquivo CSV.
- **Parâmetros**:
- **file (obrigatório)**: Arquivo CSV contendo as contas a pagar

### Docker Compose

1. Execute o comando
  ```bash
  docker-compose up --build
  ```

2. Aguarde até que todos os contêineres sejam inicializados.
3. A aplicação estará acessível em [http://localhost:8080](http://localhost:8080).
4. A aplicação vai subir por default com o profile do docker

### Execução via Gradle

1. Suba o banco de dados da aplicação manualmente
2. Execute o comando
  ```bash
  gradle clean build bootRun --args='--spring.profiles.active=dev'
  ```

3. Aguarde até que todos os contêineres sejam inicializados.
4. A aplicação estará acessível em [http://localhost:8080](http://localhost:8080).
