# QuadroKaban - Kanban API

API REST para gerenciamento de quadros Kanban, cards, colunas, bloqueios, movimentações, relatórios e histórico de tarefas.

---

## 🚀 Tecnologias

- Java 17+
- Spring Boot 3
- Spring Data JPA
- MapStruct (para mapeamento entre entidades e DTOs)
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

Por padrão, o projeto está configurado para MariaDB no perfil `dev`.
Você pode usar H2 para testes rápidos ou configurar para PostgreSQL.

Os arquivos de configuração principais são:
- `src/main/resources/application.yml` (configurações comuns e perfil ativo)
- `src/main/resources/application-dev.yml` (configurações para desenvolvimento)
- `src/main/resources/application-prod.yml` (configurações para produção)

#### Exemplo para H2 (desenvolvimento):

Crie um arquivo `src/main/resources/application-h2.yml`:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: none # Liquibase cuidará do schema
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.yaml
```
Para ativar o perfil H2, altere em `application.yml` ou use variável de ambiente.

#### Configuração para MariaDB (perfil `dev`):

Veja `src/main/resources/application-dev.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mariadb://127.0.0.1:3306/quadrokaban
    username: devuser
    password: devpass
    driver-class-name: org.mariadb.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    database-platform: org.hibernate.dialect.MariaDBDialect
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.yaml
```

### 3. Execute a aplicação

Você pode rodar a aplicação usando o Gradle:

```bash
./gradlew bootRun
```

Por padrão, o perfil `dev` será ativado (conforme `application.yml`).
Para usar outro perfil (ex: `prod` ou `h2`):

```bash
./gradlew bootRun -Dspring.profiles.active=prod
```

A API estará disponível em `http://localhost:8080` (ou a porta configurada).

---

## 🖥️ CLI de Exemplo

O projeto inclui um CLI simples para facilitar testes e demonstrações.  
Com ele, você pode criar boards, adicionar colunas, criar e mover cards, bloquear/desbloquear/cancelar cards, entre outras operações básicas.

> ⚠️ Funcionalidades de relatório e histórico detalhado estão disponíveis apenas via endpoints REST.

### Como rodar o CLI

Execute normalmente:
```bash
./gradlew bootRun
```
Siga as instruções no terminal para navegar pelo menu e testar as funcionalidades.

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
- Relatórios de tempo, bloqueios e resumo geral (apenas via API)
- Histórico detalhado de cards (apenas via API)
- Impedimento de remoção de colunas obrigatórias

---

## 🆕 Principais Alterações Recentes

- **Implementação de DTOs** para todas as entidades expostas na API, aumentando a segurança e flexibilidade.
- **Uso de MapStruct** para conversão automática entre entidades JPA e DTOs, reduzindo código repetitivo.
- **Controllers agora expõem apenas DTOs**, seguindo boas práticas REST.
- **Roadmap atualizado** para refletir as próximas melhorias planejadas.

---

## 👨‍💻 Desenvolvimento

- O projeto segue arquitetura em camadas: controllers, services, repositories e models.
- Para contribuir, crie uma branch, faça suas alterações e abra um Pull Request.
- Para rodar testes, utilize:
  ```bash
  ./gradlew test
  ```

---

## 🚧 Roadmap

- [x] Implementar DTOs para exposição segura e flexível dos dados na API
- [x] Implementar mapeamento automático com MapStruct
- [ ] Implementar DAOs para consultas customizadas e otimização de queries
- [ ] Evoluir o CLI para suportar relatórios e histórico
- [ ] Melhorar interface do CLI
- [ ] Adicionar autenticação e autorização
- [ ] Implementar testes automatizados 
- [ ] Configurar CI/CD básico (ex: GitHub Actions)
- [ ] Implementar logging estruturado
- [ ] Documentação OpenAPI/Swagger

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

**Dúvidas ou sugestões?**  
Abra uma issue ou entre em contato!