# QuadroKaban - Kanban API

API REST para gerenciamento de quadros Kanban, cards, colunas, bloqueios, movimentações, relatórios e histórico de tarefas.

---

## 🚀 Tecnologias

- Java 17+
- Spring Boot 3
- Spring Data JPA
- MariaDB, PostgreSQL ou H2 (para desenvolvimento)
- Gradle

---

## ⚙️ Como rodar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seuusuario/quadrokaban.git
cd quadrokaban
```

### 2. Configure o banco de dados

Por padrão, o projeto está configurado para MariaDB.  
Você pode usar H2 para testes rápidos.  
Edite o arquivo `src/main/resources/application.properties` conforme seu banco:

#### Exemplo para H2 (desenvolvimento):

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

#### Exemplo para MariaDB:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/quadrokaban
spring.datasource.username=seuusuario
spring.datasource.password=suasenha
spring.jpa.hibernate.ddl-auto=update
```

### 3. Rode o projeto

No terminal, execute:

```bash
gradlew bootRun
```
ou, se preferir Maven:
```bash
mvn spring-boot:run
```

Acesse: [http://localhost:8080](http://localhost:8080)

---

## 📚 Principais Endpoints da API REST

| Método | Endpoint                                 | Descrição                                 |
|--------|------------------------------------------|-------------------------------------------|
| GET    | `/api/boards`                            | Lista todos os boards ativos              |
| POST   | `/api/boards`                            | Cria um novo board                        |
| POST   | `/api/boards/{boardId}/colunas`          | Adiciona coluna ao board                  |
| POST   | `/api/boards/{boardId}/cards`            | Cria card na coluna inicial               |
| POST   | `/api/boards/cards/{cardId}/mover`       | Move card para próxima coluna             |
| POST   | `/api/boards/cards/{cardId}/cancelar`    | Cancela card                              |
| POST   | `/api/boards/cards/{cardId}/bloquear`    | Bloqueia card (motivo obrigatório)        |
| POST   | `/api/boards/cards/{cardId}/desbloquear` | Desbloqueia card (motivo obrigatório)     |
| GET    | `/api/boards/cards/{cardId}/tempo-total` | Tempo total do card                       |
| GET    | `/api/boards/{boardId}/resumo`           | Resumo geral do board                     |
| GET    | `/api/boards/cards/{cardId}/historico`   | Histórico detalhado do card               |
| DELETE | `/api/boards/{boardId}`                  | Exclui board                              |
| POST   | `/api/boards/{boardId}/arquivar`         | Arquiva board                             |

> Use ferramentas como Postman, Insomnia ou o navegador para testar os endpoints.

---

## 📝 Regras de Negócio Implementadas

- Criação de boards com colunas obrigatórias (inicial, final, cancelamento)
- Validação de nomes duplicados para boards e colunas
- Criação, movimentação, bloqueio/desbloqueio, cancelamento e arquivamento de cards
- Exclusão/arquivamento de boards, cards e colunas (com integridade)
- Relatórios de tempo, bloqueios e resumo geral
- Histórico detalhado de cards
- Impedimento de remoção de colunas obrigatórias

---

## 👨‍💻 Desenvolvimento

- O projeto segue arquitetura em camadas: controllers, services, repositories e models.
- Para contribuir, crie uma branch, faça suas alterações e abra um Pull Request.
- Para rodar testes, utilize:
  ```bash
  gradlew test
  ```

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

**Dúvidas ou sugestões?**  
Abra uma issue ou entre em contato!