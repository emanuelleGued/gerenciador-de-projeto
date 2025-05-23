# Requisitos do Sistema - Aplicação de Gerenciamento de Projetos

## 1. Requisitos Funcionais (RF)

### 1.1 Gerenciamento de Usuários (RF-01 a RF-05)
- **RF-01**: Cadastrar usuário (nome, e-mail, organização vinculada)
- **RF-02**: Editar informações do usuário (atualizar nome, e-mail ou organização)
- **RF-03**: Excluir usuário
- **RF-04**: Listar todos os usuários cadastrados
- **RF-05**: Visualizar detalhes de um usuário específico

### 1.2 Gerenciamento de Organizações (RF-06 a RF-10)
- **RF-06**: Cadastrar organização (nome, endereço)
- **RF-07**: Editar organização (alterar nome ou endereço)
- **RF-08**: Excluir organização
- **RF-09**: Listar todas as organizações
- **RF-10**: Visualizar detalhes de uma organização (incluindo endereço)

### 1.3 Gerenciamento de Endereços (RF-11 a RF-15)
- **RF-11**: Cadastrar endereço (rua, cidade, estado, CEP, país)
- **RF-12**: Editar endereço
- **RF-13**: Excluir endereço
- **RF-14**: Listar todos os endereços cadastrados
- **RF-15**: Associar endereço a uma organização

### 1.4 Gerenciamento de Projetos (RF-16 a RF-20)
- **RF-16**: Cadastrar projeto (nome, descrição, datas de início e término)
- **RF-17**: Editar projeto (alterar nome, descrição ou datas)
- **RF-18**: Excluir projeto
- **RF-19**: Listar todos os projetos
- **RF-20**: Visualizar detalhes de um projeto

### 1.5 Gerenciamento de Funções (Roles) (RF-21 a RF-25)
- **RF-21**: Cadastrar função (nome, descrição)
- **RF-22**: Editar função
- **RF-23**: Excluir função
- **RF-24**: Listar todas as funções disponíveis
- **RF-25**: Associar função a um usuário em um projeto

### 1.6 Associação de Usuários a Projetos (RF-26 a RF-28)
- **RF-26**: Adicionar usuário a um projeto com uma função específica
- **RF-27**: Remover usuário de um projeto
- **RF-28**: Listar todos os usuários associados a um projeto, com suas respectivas funções

## 2. Requisitos Não-Funcionais (RNF)

### 2.1 Desempenho (RNF-01 a RNF-03)
- **RNF-01**: O sistema deve responder em até 2 segundos para operações básicas (cadastro, edição, exclusão)
- **RNF-02**: Consultas de listagem devem carregar em até 3 segundos mesmo com grande volume de dados
- **RNF-03**: O banco de dados SQLite deve ser otimizado para evitar lentidão em operações CRUD

### 2.2 Usabilidade (RNF-04 a RNF-06)
- **RNF-04**: Interface gráfica intuitiva, seguindo padrões de design básicos do Java Swing
- **RNF-05**: Mensagens de erro claras para o usuário em caso de falhas
- **RNF-06**: Validação de campos obrigatórios antes de enviar formulários

### 2.3 Segurança (RNF-07 a RNF-09)
- **RNF-07**: Dados sensíveis (como e-mails) devem ser validados antes do cadastro
- **RNF-08**: Confirmação antes de exclusões irreversíveis (usuários, projetos, etc.)
- **RNF-09**: O banco de dados SQLite deve ser armazenado localmente, sem exposição a redes públicas

### 2.4 Compatibilidade (RNF-10 a RNF-11)
- **RNF-10**: O sistema deve funcionar em Windows, Linux e macOS (desde que tenha JVM instalada)
- **RNF-11**: Utilizar Java 8+ para garantir compatibilidade com a maioria dos ambientes

### 2.5 Manutenibilidade (RNF-12 a RNF-14)
- **RNF-12**: Código modularizado em DAO (Data Access Object) e UI (User Interface)
- **RNF-13**: Documentação básica no código (comentários explicativos)
- **RNF-14**: Facilidade para adicionar novas funcionalidades no futuro
