# Javer Bank - Backend Service (App 2)

## Visão Geral
O serviço backend (`app2`) do javer Bank gerencia a lógica de negócios e a persistência de dados, fornecendo API RESTful para operações CRUD com clientes e cálculo de score de crédito.

## Funcionalidades
- CRUD completo para gerenciamento de clientes;
- Cálculo do score de crédito baseado no saldo;
- Integração com MYSQL para persistência de dados;
- Documentação de API com Swagger.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **Maven**
- **Swagger**
- **AWS Elastic Beanstalk**

## Documentação da API
A documentação da API está disponível e interativa via Swagger em modo local ou na nuvem.

## Acessando a documentação:
Em modo local, acesse http://localhost:8080/swagger-ui/index.html
Na AWS, após o deploy, acesse: http://novo-deploy-env.eba-tni9kurn.us-east-1.elasticbeanstalk.com/swagger-ui/index.html
OBS.: O link pode estar depreciado caso o serviço Elestic Beanstalk para esta aplicação não esteja disponível no momento da consulta.