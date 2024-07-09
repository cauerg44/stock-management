# Sistema de gestão de estoque
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/cauerg44/product-stock/blob/main/LICENSE) 

# Sobre o projeto

Esse projeto é uma aplicação com objetivo de ser um sistema de controle de estoque de produtos com base no seus fornecedores e suas categorias.

## Modelo de domínio UML
![Modelo Conceitual](https://github.com/cauerg44/pictures/blob/main/images/product-stock-model-uml.jpg)

## Cobertura de teste na camada dos services - 96%
![Cobertura de testes](https://github.com/cauerg44/pictures/blob/main/images/Captura%20de%20tela%202024-07-03%20113308.png)

## Homologado com o banco de dados PostgreSQL
![Banco de dados PostgreSQL](https://github.com/cauerg44/pictures/blob/main/images/Captura%20de%20tela%202024-07-03%20114618.png)

## API documentada com o Swagger UI
- Para acessar a documentação quando o back-end estiver em execução : http://localhost:8080/swagger-ui/index.html#/
  
![Swagger UI](https://github.com/cauerg44/pictures/blob/main/images/Captura%20de%20tela%202024-06-29%20153158.png)

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot / Spring Security
- H2 database
- JPA / Hibernate
- JUnit5
- Mockito / MockMvc
- Swagger
## Banco de dados em produção
- PostgreSQL

# Como executar o projeto

## Back end
Pré-requisitos: Java 17

```bash
# clonar repositório
git clone git@github.com:cauerg44/product-stock.git

# entrar na pasta do projeto back end
cd back-end

# executar o projeto
./mvnw spring-boot:run
```

# Desenvolvedor

Back-end - Cauê da Rocha Garcia : https://www.linkedin.com/in/cauegarcia8112004
