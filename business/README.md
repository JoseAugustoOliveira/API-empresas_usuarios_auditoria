# Documentação de uma API de cadastro de empresas, pagamentos e dados para receber, além de download de PDF com dados específicos em Java 17, Spring Boot e banco de dados relacional PostgreSQL.

A API esta documentada via Swagger:

http://localhost:8091/swagger-ui/index.html#/

```
POST - /business/saveCompany
POST - /business/financing
POST - /business/payment
GET  - /business/listCompanies
GET  - /eligibility
GET  - /generate-file//generate-pdf/{contracteeDocumentNumber}
```

# As funcionalidades estão descritas no Swagger para mais detalhes!

Cadastro da empresa, Cadastro de pagamento, Dados para pagamento e Listagem das empresas.

* OBS: Os principais endpoints exigem que o usuário adicione o CPF e detalhes da requisição para auditoria, onde é gravado em banco explicado posteriormente.


* ENUM de Status de Pagamento:

```
ERROR("Erro no processamento")
PARTNER_SENT("Enviado para parceiro")
PAYMENT_REJECTED("Pagamento rejeitado")
PAYMENT_AUTHORIZED("Pagamento autorizado")
WAITING_PROCESSING("Aguardando processamento")
```
Cadastro de empresas:

* Adicionar empresas passando CNPJ, razão social e número do contrato, além dos dados do usuaŕio;
* Ao adicionar a empresa passa a ter um campo como 'ativa' a data de registro da empresa e um Enum com status de "Aguardando processamento";

Registro de financiamento:

* Adicionar financiamento passando CNPJ, número do contrato e valor, além dos dados do usuaŕio;
* Ao adicionar o valor o Enum passa para status de "Enviado para parceiro" ou "Pagamento rejeitado";

Registro de pagamento:

* Adicionar dados para fazer o pagamento requisitado pela empresa passando as informações do contrato do CNPJ e dados bancarios, além dos dados do usuaŕio;
* Ao adicionar os dados bancários como nome do banco, e número da conta se confirmado, uma variável de pagamento feito, passa a ter o valor de verdadeiro e o status de "Pagamento autorizado" ou "Pagamento rejeitado";

Listagem de empresas

* Listar as empresas cadastradas com status do pagamento, CNPJ e número do contrato quando a empresa estiver ativa no banco de dados,além dos dados do usuaŕio;

Verificação de elegibilidade:

* Passar o CNPJ e número do contrato retornando verdadeiro ou falso quando os dados estiverem no banco de dados, além dos dados do usuario;

Geração de PDF:

* Gera um arquivo em formato PDF, passando os dados da empresa solicitante, retornando o CNPJ, razão social, número do contrato, valor, status do pagamento/requisição, se a empresa esta ativa e se o pagamento já foi executado;
* O nome do PDF é "Dados_Empresariais.pdf"

Ferramentas e Dependências:

* Java 17
* Spring Boot 3.1.5
* PostgreSQL
* Swagger
* Spring Security
* Maven 4.0.0
* Lombok
* JUnit / Mockito para testes unitários