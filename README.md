# QuadroKaban - Kanban API

## 📋 Sobre o Projeto

API REST para gerenciamento de quadros Kanban, cards, colunas, bloqueios, movimentações, relatórios e histórico de tarefas.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MariaDB** (produção)
- **H2** (testes)
- **Liquibase** (migrations)
- **MapStruct** (mapeamento de objetos)
- **JUnit 5** (testes)
- **Mockito** (mocks para testes unitários)
- **Gradle** (build)


## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controllers**: Endpoints REST
- **Services**: Regras de negócio
- **Repositories**: Acesso a dados
- **DTOs**: Objetos de transferência de dados
- **Mappers**: Conversão entre entidades e DTOs
- **Models**: Entidades do domínio
---
## 📊 Modelo de Dados

O sistema trabalha com as seguintes entidades principais:

- **Board**: Quadro Kanban
- **Coluna**: Colunas do quadro (INICIAL, PENDENTE, FINAL, CANCELAMENTO, PERSONALIZADA)
- **Card**: Cartões/tarefas
- **Movimentacao**: Histórico de movimentações dos cards
- **Bloqueio**: Controle de bloqueios dos cards

## ⚙️ Como rodar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seuusuario/quadrokaban.git
cd quadrokaban
```

### 2. Configure o banco de dados

Por padrão, o projeto está configurado para **MariaDB** no perfil `dev` e produção.

Os arquivos de configuração principais são:
- `src/main/resources/application.yml` (configurações comuns e perfil ativo)
- `src/main/resources/application-dev.yml` (configurações para desenvolvimento)
- `src/main/resources/application-prod.yml` (configurações para produção)

#### Exemplo para MariaDB (desenvolvimento):

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

> **Atenção:** Substitua `<host>`, `<porta>`, `<usuario>`, `<senha>` e `<nome-do-banco>` pelos dados do seu ambiente (ex: Railway, Docker, etc).

#### Exemplo de Docker Compose para MariaDB:

```yaml
version: '3.8'
services:
  mariadb:
    image: mariadb:11
    environment:
      MYSQL_DATABASE: quadrokaban
      MYSQL_USER: devuser
      MYSQL_PASSWORD: devpass
      MYSQL_ROOT_PASSWORD: rootpass
    ports:
      - "3306:3306"
    volumes:
      - mariadb_data:/var/lib/mysql
volumes:
  mariadb_data:
```

#### Exemplo para H2 (opcional, para testes rápidos):

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

---

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

## ⚡ CI/CD

O projeto utiliza GitHub Actions para integração contínua:

- **Build automático** em push/PR para main
- **Execução de testes** sem dependências externas
- **Cache do Gradle** para builds mais rápidos
- **Relatórios de teste** automatizados

O arquivo de configuração está em `.github/workflows/ci-cd.yml`.

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
| GET    | `/api/boards/cards/{cardId}/historico`   | Histórico do card                         |
| GET    | `/api/boards/{boardId}/bloqueios`        | Relatório de bloqueios do board           |
| GET    | `/api/boards/{boardId}/relatorio-tarefas`| Relatório de tarefas do board             |
| GET    | `/api/boards/cards/{cardId}/historico-detalhado` | Histórico detalhado do card        |
| DELETE | `/api/boards/{boardId}`                  | Exclui board                              |
| POST   | `/api/boards/{boardId}/arquivar`         | Arquiva board                             |

> Use ferramentas como Postman, Insomnia ou o navegador para testar os endpoints.
---
## 🧪 Testes

O projeto utiliza duas estratégias de teste:

### Testes Unitários (Mockito)
- Testam a lógica de negócio isoladamente
- Não dependem de banco de dados
- Executam rapidamente
- Localização: `src/test/java/com/reury/kabanquadro/service/`

### Testes de Integração
- Testam a aplicação completa
- Utilizam banco H2 em memória
- Validam o comportamento end-to-end

```bash
# Executar todos os testes
./gradlew test

# Executar apenas testes unitários
./gradlew test --tests "*UnitTest"
```

---

## 📝 Regras de Negócio Implementadas

### 🎯 Gerenciamento de Boards
- **Criação automática de colunas obrigatórias**: Todo board novo recebe automaticamente as colunas INICIAL, PENDENTE, FINAL e CANCELAMENTO
- **Validação de unicidade**: Nomes de boards devem ser únicos no sistema
- **Controle de estado**: Boards podem ser ativados/desativados e arquivados mantendo integridade referencial

### 📋 Gerenciamento de Colunas
- **Tipos de coluna**: Sistema suporta colunas padrão (INICIAL, PENDENTE, FINAL, CANCELAMENTO) e PERSONALIZADAS
- **Controle de ordem**: Colunas são ordenadas automaticamente respeitando o fluxo do Kanban
- **Proteção de colunas obrigatórias**: Colunas essenciais (INICIAL, FINAL, CANCELAMENTO) não podem ser removidas
- **Validação de nomes**: Nomes de colunas devem ser únicos dentro do mesmo board

### 🎴 Gerenciamento de Cards
- **Ciclo de vida completo**: Cards podem ser criados, movimentados, bloqueados, desbloqueados, cancelados e arquivados
- **Rastreamento de tempo**: Sistema registra automaticamente tempo de permanência em cada coluna
- **Controle de bloqueios**: Cards bloqueados requerem motivo obrigatório para bloqueio e desbloqueio
- **Histórico de movimentações**: Toda mudança de estado do card é registrada com timestamp

### 📊 Relatórios e Análises
- **Relatório de tempo**: Calcula tempo total e por coluna de cada card
- **Relatório de bloqueios**: Identifica gargalos e cards frequentemente bloqueados
- **Resumo de board**: Visão geral com métricas de produtividade
- **Histórico detalhado**: Timeline completa de eventos de cada card

### 🔒 Integridade e Segurança
- **Exclusão em cascata**: Remoção de boards remove todos os dados relacionados
- **Arquivamento seguro**: Dados arquivados são preservados mas não aparecem em consultas ativas
- **Validação de fluxo**: Cards só podem ser movidos para colunas válidas respeitando o fluxo Kanban

---

## 🆕 Principais Alterações Recentes

### 🏗️ Arquitetura e Design
- **Implementação completa de DTOs**: Todas as entidades agora possuem DTOs correspondentes, garantindo exposição controlada de dados na API
- **Integração com MapStruct**: Mapeamento automático e type-safe entre entidades JPA e DTOs, eliminando código boilerplate
- **Separação de responsabilidades**: Controllers exclusivamente responsáveis por DTOs, nunca expondo entidades JPA diretamente

### 🚀 Performance e Consultas
- **DAOs customizados implementados**: Consultas otimizadas para relatórios complexos e operações de agregação
- **Queries nativas para relatórios**: Consultas SQL otimizadas para melhor performance em relatórios de tempo e bloqueios
- **Lazy loading configurado**: Relacionamentos JPA otimizados para evitar N+1 queries

### 🧪 Qualidade e Testes
- **Refatoração completa dos testes**: Migração para Mockito em testes unitários, eliminando dependência de banco real
- **Testes de integração com H2**: Testes end-to-end usando banco em memória para maior velocidade
- **CI/CD simplificado**: Pipeline otimizado sem dependências externas, executando apenas com JVM

### 🔧 DevOps e Infraestrutura
- **GitHub Actions configurado**: Build automático, execução de testes e cache de dependências
- **Profiles de ambiente**: Configurações separadas para desenvolvimento, produção e testes
- **Docker-ready**: Configurações preparadas para containerização futura

### 📚 Documentação
- **README expandido**: Documentação completa com exemplos, configuração e guias de desenvolvimento
- **Endpoints documentados**: Tabela completa de endpoints REST com descrições detalhadas
- **Roadmap atualizado**: Visão clara das próximas funcionalidades e melhorias planejadas

---

## 👨‍💻 Arquitetura

- O projeto segue arquitetura em camadas: **controllers**, **services**, **DAOs** (para consultas customizadas), **repositories** (CRUD), **models** (entidades) e **DTOs**.
- DAOs são usados para consultas complexas e relatórios, retornando DTOs prontos para a API.
- Controllers nunca expõem entidades JPA diretamente, apenas DTOs.

---

## 🚧 Roadmap

- [x] Implementar DTOs para exposição segura e flexível dos dados na API
- [x] Implementar mapeamento automático com MapStruct
- [x] Implementar DAOs para consultas customizadas e otimização de queries
- [ ] Evoluir o CLI para suportar relatórios e histórico
- [ ] Melhorar interface do CLI
- [x] Implementar testes automatizados
- [x] Configurar CI/CD básico (ex: GitHub Actions) para rodar testes a cada commit na main
- [ ] Adicionar autenticação e autorização
- [ ] Implementar logging estruturado
- [ ] Documentação OpenAPI/Swagger

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

**Dúvidas ou sugestões?**  
Abra uma issue ou entre em contato!