Sistema de Empréstimo de Equipamentos

API REST desenvolvida em Java com Spring Boot para gerenciamento de usuários, equipamentos, empréstimos e devoluções.

Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- JDBC
- PostgreSQL
- Supabase
- Maven
- JUnit
- Mockito
- Postman
- Git e GitHub

Arquitetura

O projeto utiliza separação em camadas:

Controller
↓
Service
↓
Repository
↓
PostgreSQL

Controller

Responsável por receber as requisições HTTP.

Service

Responsável pelas regras de negócio.

Repository

Responsável pelo acesso ao banco de dados utilizando JDBC e "PreparedStatement".

Entidades

O sistema possui três entidades principais:

- Usuario
- Equipamento
- Emprestimo

Endpoints

Usuários

GET /usuarios
GET /usuarios/{id}

Equipamentos

GET    /equipamentos
GET    /equipamentos/{id}
POST   /equipamentos
PUT    /equipamentos/{id}
DELETE /equipamentos/{id}

Algoritmos

Busca linear por patrimônio:

GET /equipamentos/patrimonio/{patrimonio}

Ordenação dos equipamentos por nome:

GET /equipamentos/ordenados

Busca binária por nome:

GET /equipamentos/nome/{nome}

Empréstimos

GET   /emprestimos
POST  /emprestimos
PATCH /emprestimos/{id}/devolucao

Registro de empréstimo

Exemplo:

{
"usuarioId": 1,
"equipamentoId": 1
}

Ao registrar um empréstimo:

1. O usuário é verificado.
2. O equipamento é verificado.
3. A disponibilidade do equipamento é validada.
4. O empréstimo é registrado como "ATIVO".
5. O equipamento passa para "disponivel = false".

A operação é executada dentro de uma transação.

Caso ocorra algum erro, é realizado "rollback".

Devolução

Ao realizar uma devolução:

1. O empréstimo é localizado.
2. É verificado se ele ainda está ativo.
3. A situação é alterada para "DEVOLVIDO".
4. A data de devolução é registrada.
5. O equipamento volta para "disponivel = true".

A operação também utiliza transação com "commit" e "rollback".

Algoritmos implementados

O projeto possui implementação manual de:

- Busca linear
- Bubble Sort
- Busca binária

A busca binária é executada sobre uma lista previamente ordenada.

Tratamento de erros

Exemplos de respostas utilizadas pela API:

404 Not Found

Para registros inexistentes.

409 Conflict

Para conflitos de regra de negócio, como tentativa de empréstimo de equipamento indisponível ou nova devolução de um empréstimo já devolvido.

Testes

Foram implementados testes unitários utilizando JUnit e Mockito.

Entre os cenários testados estão:

- Busca linear de equipamento
- Ordenação de equipamentos
- Busca binária
- Equipamento indisponível para empréstimo
- Usuário inexistente
- Empréstimo inexistente
- Tentativa de devolver empréstimo já devolvido

Configuração do banco de dados

As credenciais não são armazenadas diretamente no código.

O "application.properties" utiliza variáveis de ambiente:

spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

Também deve ser configurada a URL JDBC do PostgreSQL/Supabase.

Executando o projeto

1. Configure "DB_USERNAME" e "DB_PASSWORD".
2. Certifique-se de que o PostgreSQL/Supabase esteja disponível.
3. Execute a aplicação Spring Boot.
4. A API ficará disponível em:

http://localhost:8080

5. Os endpoints podem ser testados utilizando Postman.

Versionamento

O projeto utiliza Git e GitHub para controle de versão.