Sistema de Empréstimo de Equipamentos

API REST desenvolvida para gerenciamento de equipamentos, usuários, empréstimos e devoluções.

Integrantes

- Lucas França
- Hiago Paes

Tecnologias

- Java 21
- Spring Boot
- JDBC
- PostgreSQL
- Supabase
- Maven
- JUnit
- Mockito
- Postman
- Git e GitHub

Execução do projeto

Pré-requisitos

- Java 21
- Maven
- Acesso ao PostgreSQL/Supabase
- IntelliJ IDEA ou outra IDE compatível

Configuração

O projeto utiliza variáveis de ambiente para proteger as credenciais do banco.

Configure:

DB_USERNAME
DB_PASSWORD

O "application.properties" utiliza:

spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

Após configurar as variáveis:

1. Abra o projeto na IDE.
2. Aguarde o Maven carregar as dependências.
3. Execute "EmprestimoEquipamentoApplication".
4. A API ficará disponível em:

http://localhost:8080

Os endpoints podem ser testados pelo Postman.

Principais endpoints

Usuários

GET /usuarios
GET /usuarios/{id}

Equipamentos

GET    /equipamentos
GET    /equipamentos/{id}
POST   /equipamentos
PUT    /equipamentos/{id}
DELETE /equipamentos/{id}

Empréstimos

GET   /emprestimos
POST  /emprestimos
PATCH /emprestimos/{id}/devolucao

Decisões técnicas essenciais

Arquitetura em camadas

O projeto foi organizado em:

Controller
↓
Service
↓
Repository
↓
PostgreSQL

O Controller recebe as requisições HTTP.

O Service concentra regras de negócio.

O Repository realiza o acesso ao banco de dados.

Essa separação facilita manutenção, testes e organização do código.

JDBC e PreparedStatement

O acesso ao banco foi implementado utilizando JDBC e "PreparedStatement".

Exemplo conceitual:

SELECT id, nome, patrimonio
FROM equipamentos
WHERE id = ?

O uso de consultas parametrizadas evita a concatenação direta dos valores recebidos nas instruções SQL.

Transações

As operações de empréstimo e devolução utilizam transações.

No empréstimo:

1. O usuário é validado.
2. O equipamento é validado.
3. A disponibilidade é verificada.
4. O empréstimo é criado como "ATIVO".
5. O equipamento passa para indisponível.

Se tudo funcionar, é executado "commit".

Se ocorrer erro, é executado "rollback".

A devolução segue a mesma lógica transacional, atualizando o empréstimo e liberando o equipamento de forma conjunta.

Concorrência

Na operação de empréstimo foi utilizado "FOR UPDATE" para bloquear temporariamente o registro do equipamento durante a transação.

Isso reduz o risco de duas operações concorrentes tentarem emprestar o mesmo equipamento.

Tratamento de erros

A aplicação possui tratamento centralizado de exceções.

Entre os retornos utilizados:

404 Not Found

para recursos inexistentes.

409 Conflict

para conflitos de regra de negócio, como equipamento indisponível ou empréstimo já devolvido.

Algoritmos

Foram implementados manualmente três algoritmos.

Busca linear

Utilizada para localizar equipamento pelo patrimônio.

Complexidade no pior caso:

O(n)

A lista pode precisar ser percorrida completamente até encontrar o elemento.

Bubble Sort

Utilizado para ordenar equipamentos alfabeticamente pelo nome.

Complexidade no pior caso:

O(n²)

Foi implementado manualmente para demonstrar o funcionamento de um algoritmo de ordenação.

Busca binária

Utilizada para pesquisar equipamentos pelo nome após a ordenação da lista.

Complexidade:

O(log n)

A cada comparação, aproximadamente metade do espaço de busca é descartada.

A busca binária exige que os elementos estejam previamente ordenados.

Testes

Foram realizados testes manuais no Postman e testes automatizados com JUnit e Mockito.

Testes automatizados implementados

"EquipamentoServiceTest":

- busca linear por patrimônio;
- ordenação por nome;
- busca binária por nome.

"EmprestimoServiceTest":

- equipamento indisponível retorna conflito;
- usuário inexistente retorna 404;
- empréstimo inexistente retorna 404;
- tentativa de devolver empréstimo já devolvido retorna conflito.

Os testes unitários utilizam Mockito para simular os repositories sem depender diretamente do banco de dados.

Evidências

Os testes JUnit foram executados pela IDE e concluídos com sucesso, apresentando:

Process finished with exit code 0

Também foram realizados testes dos endpoints no Postman, incluindo:

GET /usuarios
GET /equipamentos
POST /equipamentos
PUT /equipamentos/{id}
DELETE /equipamentos/{id}
POST /emprestimos
PATCH /emprestimos/{id}/devolucao

Foram verificados, entre outros comportamentos:

- cadastro de equipamento;
- atualização;
- exclusão;
- criação de empréstimo;
- equipamento tornando-se indisponível;
- devolução;
- equipamento tornando-se novamente disponível;
- retornos 404 e 409.

Uso de Inteligência Artificial

Ferramentas de inteligência artificial generativa foram utilizadas como apoio durante o desenvolvimento do projeto.

O uso incluiu:

- auxílio na compreensão de conceitos de Java e Spring Boot;
- apoio na estruturação das camadas da aplicação;
- explicação e identificação de erros de código e SQL;
- apoio na elaboração de casos de teste;
- revisão da documentação.

As decisões de implementação, execução, testes, correções e validação do funcionamento do sistema foram realizadas e acompanhadas pelos integrantes do grupo.

Versionamento

O projeto utiliza Git e GitHub para controle de versão.

Foram realizados commits ao longo do desenvolvimento para registrar a evolução da aplicação.