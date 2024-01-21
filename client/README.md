# Documentação de uma API de cadastro de funcionário e auditoria em Java 17, Spring Boot e banco de dados relacional PostgreSQL.

A API esta documentada via Swagger:

http://localhost:8090/swagger-ui/index.html#/

```
POST - /client/register-staff
POST - /client/register-audit
```

# As funcionalidades estão descritas no Swagger para mais detalhes!

Cadastro de funcionario/usuário e atualização de atualizações no sistema na tabela de auditoria.

* ENUM do Departamento do Usuário:

```
IT("Tecnologia da Informação")
HR("Recursos Humanos")
SALES("Vendas")
MARKETING("Marketing")
FINANCE("Finanças")
OPERATIONS("Operações")
CUSTOMER_SERVICE("Atendimento ao Cliente")
```
Cadastro dos funcionários:

* Adicionar usuários passando CPF que é gravado no banco criptografado usando o BCryptPasswordEncoder, nome completo, email, a data de inicio, departamento e se esta ativo na função;

Registro de auditoria:

* Adiciona uma atualização toda vez que o usuário cadastrado faz alguma utilização dos recursos, pegando o CPF do usuário, detalhe ou resumo da utilização, a data do acesso e um contador de quantas vezes este usuário fez o acesso;

Ferramentas e Dependências:

* Java 17
* Spring Boot 3.1.5
* PostgreSQL
* Swagger
* Spring Security
* BCryptPasswordEncoder
* Maven 4.0.0
* Lombok
* JUnit / Mockito para testes unitários