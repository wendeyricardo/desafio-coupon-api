
# Coupon API

API para cadastro, consulta e remoção (soft delete) de cupons de desconto, utilizando Java 21, Spring Boot, H2, Swagger/OpenAPI e Docker.

O projeto segue princípios SOLID, DDD e Clean Architecture, garantindo regras de negócio no domínio, alta cobertura de testes e documentação profissional.

----------

## Funcionalidades

-   **Criar cupom**  (`POST /coupon`)
    
    -   Código alfanumérico ajustado para 6 caracteres (caracteres especiais removidos)
    -   Desconto mínimo 0.5
    -   Data de expiração não pode ser no passado
    -   Permite criar cupom já publicado
    -   Não permite código duplicado
-   **Buscar cupom por ID**  (`GET /coupon/{id}`)
    
    -   Retorna só cupons ativos (não deletados)
    -   Retorna erro se cupom não existir ou estiver deletado
-   **Deletar cupom (soft delete)**  (`DELETE /coupon/{id}`)
    
    -   Marca o cupom como DELETED (soft delete)
    -   Não permite deletar duas vezes
    -   Retorna erro se cupom não existir

----------

## Tecnologias e Ferramentas

-   Java 21
-   Spring Boot 3.2.6
-   Spring Data JPA
-   H2 Database
-   Swagger / OpenAPI
-   Docker
-   Jacoco
-   Lombok

----------

## Como rodar localmente


```
mvn clean package
mvn spring-boot:run
```

Acesse:

-   **Swagger UI:**  [http://localhost:8080/swagger-ui.html![Opens in a new window; external.](data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7)](http://localhost:8080/swagger-ui.html)
-   **H2 Console:**  [http://localhost:8080/h2-console![Opens in a new window; external.](data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7)](http://localhost:8080/h2-console)
    -   JDBC URL:  `jdbc:h2:mem:testdb`
    -   User:  `sa`
    -   Password: `123`

----------

## Como rodar com Docker

Para iniciar a aplicação:
```
docker compose up --build
```
Para parar a aplicação:
```
docker compose down
```
Observação:
Se sua instalação do Docker for antiga e não suportar o comando acima, use:
```
docker-compose up --build
docker-compose down
```

### (Opcional) Rodando manualmente com Docker
Se preferir, você também pode rodar sem Compose:

```
docker build -t coupon-api .
docker run -p 8080:8080 coupon-api
```


Arquivo  `docker-compose.yaml`:


```
version: '3.8'
services:
  coupon-api:
    build: .
    ports:
      - "8080:8080"
    environment:
      - JAVA_OPTS=-Dh2.console.settings.webAllowOthers=true
```



----------

## Testes e cobertura



```
mvn clean test
```

O relatório Jacoco estará em:  
`target/site/jacoco/index.html`

----------

## Exemplos de payloads

### Criar cupom (`POST /coupon`)


```
{
  "code": "PROMO@1",
  "description": "Cupom de desconto para teste",
  "discountValue": 1.0,
  "expirationDate": "2027-12-31",
  "published": true
}
```

**Resposta:**

```
{
  "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
  "code": "PROMO1",
  "description": "Cupom de desconto para teste",
  "discountValue": 1.0,
  "expirationDate": "2027-12-31",
  "published": true,
  "status": "ACTIVE"
}
```

### Buscar cupom (`GET /coupon/{id}`)

```
{
  "id": "...",
  "code": "PROMO1",
  "description": "...",
  "discountValue": 1.0,
  "expirationDate": "2027-12-31",
  "published": true,
  "status": "ACTIVE"
}
```

### Deletar cupom (`DELETE /coupon/{id}`)

-   Resposta:  `204 No Content`

### Exemplo de erro de validação

```
{
  "error": "Erro de validação nos campos",
  "fields": {
    "discountValue": "O valor do desconto deve ser maior ou igual a 0.5"
  },
  "status": 400,
  "timestamp": "2026-02-18T12:34:56.789Z"
}
```

----------

## Regras de negócio implementadas

-   Código do cupom sempre 6 caracteres alfanuméricos (caracteres especiais removidos, preenchido com 'X' se faltar)
-   Valor de desconto mínimo de 0.5
-   Não permite data de expiração no passado
-   Código não pode ser duplicado
-   Não permite deletar duas vezes
-   Soft delete (não remove, apenas status DELETED)
-   Apenas cupons ativos podem ser buscados

----------

## Arquitetura do projeto

-   **Domínio rico:**  regras encapsuladas na entidade  `Coupon`
-   **Services/Casos de uso:**  cada ação em uma classe separada
-   **DTOs:**  para request/response, validados com Bean Validation
-   **Mapper:**  conversão DTO <-> domínio
-   **Controller:**  apenas orquestração
-   **Tratamento global de erros:**  JSON padronizado para qualquer exceção
-   **Testes:**  unitários e integração, cobertura >80%