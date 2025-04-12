# 📚 API utilizando HATEOAS e Spring Boot

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-blue)](https://swagger.io/)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)

API RESTful de gerenciamento de livros com HATEOAS, documentação Swagger e recursos do Spring Boot.

## ✨ Funcionalidades

- Empréstimo e devolução de livros
- Links HATEOAS dinâmicos nas respostas
- Documentação automática com Swagger UI
- Banco de dados H2 em memória
- Validações de negócio

## 🚀 Como Executar

### Pré-requisitos
- Java 21
- Maven 3.9+
- IDE de sua preferência (IntelliJ, Eclipse, VS Code)

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/anaclaudiaaraujo/biblioteca-api-hateoas.git
   ```
2. Entre no diretório:
   ```bash
   cd biblioteca-api-hateoas
   ```
3. Execute com Maven:
   ```bash
   mvn spring-boot:run
   ```

## 🔍 Endpoints Principais

| Método | Endpoint               | Descrição                     |
|--------|------------------------|-------------------------------|
| GET    | `/livros`              | Lista todos os livros         |
| GET    | `/livros/{id}`         | Busca um livro por ID         |
| POST   | `/livros/{id}/emprestar` | Empresta um livro            |
| POST   | `/livros/{id}/devolver` | Devolve um livro             |

## 📊 Exemplo de Resposta HATEOAS
```json
{
  "id": 1,
  "titulo": "Ruínas de Minaster",
  "autor": "Raianne Viana",
  "emprestado": true,
  "_links": {
    "self": { "href": "/livros/1" },
    "devolver": { "href": "/livros/1/devolver" }
  }
}
```

## 🌐 Acessando a Documentação
Após iniciar a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Banco H2 Console**: http://localhost:8080/h2-console  
  (Credenciais: JDBC URL=`jdbc:h2:mem:testdb`, User=`sa`, Senha=``)

## 🛠 Tecnologias Utilizadas
- Spring Boot 3.x
- Spring HATEOAS
- Spring Data JPA
- H2 Database
- Lombok
- SpringDoc OpenAPI (Swagger)
