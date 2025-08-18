# Sistema de Gerenciamento de Projetos e Tarefas

## Visão Geral
O Sistema de Gerenciamento de Projetos e Tarefas é uma aplicação Java que permite o gerenciamento completo de organizações, usuários, projetos e tarefas. O sistema oferece funcionalidades para:

- Criar e gerenciar organizações e seus endereços
- Cadastrar e administrar usuários
- Criar projetos e associar membros com funções específicas
- Gerenciar tarefas com controle de prazos e status
- Visualizar todas as relações entre as entidades do sistema

## Documentação do Projeto

### Documentos Principais
1. **[Documento de visao](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Documento%20de%20vis%C3%A3o.md)**  
   Descreve os objetivos, escopo, stakeholders e visão geral do sistema.

2. **[Requisitos](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/requisitos.md)**  
   Detalhamento dos requisitos funcionais e não-funcionais do sistema.

3. **[CheckList](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/checklist.md)**  
   Lista de verificação com todos os itens necessários para o desenvolvimento completo.


### Diagramas
- [Diagrama de Classes](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Diagrama%20de%20classe.pdf) - Estrutura das classes e relacionamentos
- [Diagrama de Caso de Uso](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Diagrama%20de%20caso%20de%20uso.pdf) - Funcionalidades e atores
- [Diagrama de Sequência - Usuário](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Diagrama%20de%20sequ%C3%AAncia%20-%20Usu%C3%A1rio.pdf) - Fluxo para atribuição de usuários
- [Diagrama de Sequência - Tarefas](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Diagrama%20de%20sequ%C3%AAncia%20-%20Atribui%C3%A7%C3%A3o%20de%20Task%20a%20Usu%C3%A1rio%20em%20Projeto.pdf) - Fluxo para atribuição de tarefas
- [Diagrama de Atividades - Tarefas](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Diagrama%20de%20atividades%20-%20Gerenciamento%20de%20Tarefas%20em%20um%20Projeto.pdf) - Fluxo de gerenciamento de tarefas
- [Diagrama de Atividades - Usuário](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Diagrama%20de%20atividades%20-%20Gerenciamento%20de%20Usu%C3%A1rio.pdf) - Processo de gestão de usuários
- [Diagrama de Componentes](https://github.com/emanuelleGued/gerenciador-de-projeto/blob/main/Component%20Diagram.png) - Arquitetura do sistema 

## Execução do Projeto

1. Clone o repositório:
```bash
git clone https://github.com/emanuelleGued/gerenciador-de-projeto.git
```

2. Execute o projeto:
- Abra o projeto em sua IDE favorita
- Execute a classe `Main` localizada em `src/main/java/Main.java`

## Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   ├── dao/       # Camada de acesso a dados
│   │   ├── model/     # Entidades do sistema
│   │   ├── ui/        # Interface do usuário
│   │   ├── util/      # Utilitários
│   │   ├── DatabaseConnection.java
│   │   └── Main.java  # Classe principal para execução
│   └── resources/     # Recursos do projeto
```

## Funcionalidades Principais
- **Organizações**:
  - Cadastro completo com endereço
  - Edição e remoção

- **Usuários**:
  - CRUD completo
  - Associação a organizações
  - Atribuição de funções em projetos

- **Projetos**:
  - Criação e gerenciamento
  - Associação de membros
  - Controle de status

- **Tarefas**:
  - Criação e atribuição
  - Controle de prazos
  - Atualização de status

## Autores
* **Emanuelle Garcia Guedes** - Desenvolvimento e documentação
* **Thiago dos Santos Gomes** - Desenvolvimento e arquitetura
