# Documento de Visão do Sistema

## 1. Objetivo

O propósito deste documento é coletar, analisar e definir as necessidades e características do sistema de **Gerenciamento de Projetos e Organizações**, focando nas funcionalidades requeridas pelos desenvolvedores e usuários-alvo. Este documento fornece uma visão geral do sistema, suas macro-funcionalidades e como os requisitos serão abordados.

## 2. Descrição do Produto

O sistema de **Gerenciamento de Projetos e Organizações** é uma solução desenvolvida para auxiliar no gerenciamento de organizações, projetos, tarefas, usuários e suas relações. O sistema permite:

- **Cadastro e gerenciamento de organizações**, incluindo informações de endereço.
- **Controle de projetos**, com detalhamento de descrição, datas de início e término.
- **Gerenciamento de tarefas**, associadas a projetos, com prazos e status de conclusão.
- **Cadastro de usuários**, vinculados a organizações, com funções específicas em projetos.
- **Controle de papéis (roles)**, definindo permissões e responsabilidades dos usuários nos projetos.

O sistema visa facilitar a organização, colaboração e acompanhamento de atividades em um ambiente empresarial ou de equipe.

## 3. Envolvimento

### 3.1. Abrangência

O sistema foi concebido para uso em ambientes corporativos ou por equipes que necessitam de um controle centralizado de projetos e tarefas. Pode ser implementado em um único terminal ou expandido para múltiplos terminais em rede, dependendo das necessidades do cliente.

### 3.2. Papel dos Atores

#### 3.2.1. Usuário



| **Campo**          | **Descrição**                                                                                                      |
|--------------------|-------------------------------------------------------------------------------------------------------------------|
| **Descrição**      | O usuário é o ator que interage diariamente com o sistema, realizando operações como cadastro de projetos, tarefas e usuários. |
| **Papel**          | Fornecer e atualizar dados no sistema, garantindo que as informações estejam sempre atualizadas para um funcionamento eficiente. |
| **Insumos ao sistema** | • Dados de usuários<br>• Dados de projetos<br>• Dados de tarefas<br>• Feedback sobre usabilidade<br>• Sugestões de melhorias |
| **Representante**  | [Nome do representante do usuário]                                                                                |


#### 3.2.2. Administrador

| **Campo**          | **Descrição**                                                                                                      |
|--------------------|-------------------------------------------------------------------------------------------------------------------|
| **Descrição**      | Responsável pela configuração inicial do sistema, definição de papéis e permissões, e gerenciamento de organizações. |
| **Papel**          | Garantir que o sistema atenda às necessidades da organização e que os usuários tenham as permissões adequadas.      |
| **Insumos ao sistema** | • Configurações do sistema<br>• Definição de papéis<br>• Aprovação de mudanças significativas                     |
| **Representante**  | [Nome do representante do administrador]                                                                          |

#### 3.2.3. Sistema

| **Campo**          | **Descrição**                                                                 |
|--------------------|-------------------------------------------------------------------------------|
| **Descrição**      | O próprio sistema e suas funcionalidades.                                     |
| **Papel**          | Processar dados, realizar cálculos e gerar informações para os usuários.      |
| **Insumos ao sistema** | • Dados fornecidos pelos usuários<br>• Regras de negócio implementadas      |
| **Representante**  | [Nome do desenvolvedor responsável]                                          |


## 4. Necessidades e Funcionalidades  

| **Necessidade**               | **Benefício** | **ID Func.** | **Descrição das Funcionalidades / Atores Envolvidos**                                                                 |
|-------------------------------|---------------|--------------|-----------------------------------------------------------------------------------------------------------------------|
| **Gerenciamento de Organizações** | Crítico       | F1.1         | Cadastro de novas organizações.<br>Usuário – Fornece os dados da organização, incluindo endereço.                     |
|                               |               | F1.2         | Pesquisa e listagem de organizações.<br>Usuário – Informa critérios de busca e recebe uma lista de organizações.      |
|                               |               | F1.3         | Atualização de dados da organização.<br>Usuário – Edita informações de uma organização existente.                     |
|                               |               | F1.4         | Exclusão de organização (Bloqueio).<br>Usuário – Remove uma organização do sistema (dados são mantidos como inativos).|
| **Gerenciamento de Projetos**    | Crítico       | F2.1         | Cadastro de novos projetos.<br>Usuário – Define nome, descrição, datas de início e término.                           |
|                               |               | F2.2         | Pesquisa e listagem de projetos.<br>Usuário – Busca projetos por nome, descrição ou período.                          |
|                               |               | F2.3         | Atualização de dados do projeto.<br>Usuário – Altera informações do projeto.                                         |
|                               |               | F2.4         | Exclusão de projeto (Bloqueio).<br>Usuário – Remove um projeto (dados são mantidos como inativos).                   |
| **Gerenciamento de Tarefas**     | Crítico       | F3.1         | Cadastro de novas tarefas.<br>Usuário – Define título, descrição, prazo e associa a um projeto.                      |
|                               |               | F3.2         | Listagem de tarefas por projeto.<br>Usuário – Visualiza todas as tarefas de um projeto.                               |
|                               |               | F3.3         | Atualização de status da tarefa.<br>Usuário – Marca tarefas como concluídas ou em andamento.                         |
|                               |               | F3.4         | Exclusão de tarefa.<br>Usuário – Remove uma tarefa do sistema.                                                       |
| **Gerenciamento de Usuários**    | Crítico       | F4.1         | Cadastro de novos usuários.<br>Usuário – Fornece nome, e-mail e organização.                                         |
|                               |               | F4.2         | Pesquisa e listagem de usuários.<br>Usuário – Busca usuários por nome ou e-mail.                                     |
|                               |               | F4.3         | Atualização de dados do usuário.<br>Usuário – Edita informações do usuário.                                          |
|                               |               | F4.4         | Exclusão de usuário (Bloqueio).<br>Usuário – Remove um usuário (dados são mantidos como inativos).                   |
| **Gerenciamento de Papéis (Roles)** | Importante   | F5.1         | Cadastro de novos papéis.<br>Usuário – Define nome e descrição do papel.                                             |
|                               |               | F5.2         | Associação de papéis a usuários em projetos.<br>Usuário – Define qual papel um usuário terá em um projeto.           |
|                               |               | F5.3         | Listagem de papéis.<br>Usuário – Visualiza todos os papéis cadastrados.                                              |
| **Relatórios**                  | Importante   | F6.1         | Geração de relatórios de projetos.<br>Usuário – Seleciona critérios e gera relatórios em tela ou impressos.         |
|                               |               | F6.2         | Geração de relatórios de tarefas.<br>Usuário – Filtra por status, projeto ou período.                                |

---

## 5. Proposta de Solução Tecnológica Escolhida

O sistema será desenvolvido utilizando as seguintes tecnologias:

- **Linguagens de programação**: Java (para a lógica de negócio), SQL (para consultas ao banco de dados).
- **Banco de dados**: MySQL (para armazenamento de dados).
- **Ferramentas de desenvolvimento**: IDE IntelliJ IDEA ou Eclipse, MySQL Workbench para gerenciamento do banco de dados.
- **Backup**: Os dados poderão ser exportados para arquivos locais ou dispositivos externos.

## 6. Cronograma de Execução

| **Atividade**          | **Semana 1** | **Semana 2** | **Semana 3** | **Semana 4** |
|------------------------|--------------|--------------|--------------|--------------|
| Análise de Requisitos  | X            |              |              |              |
| Projeto do Sistema     | X            |              |              |              |
| Codificação            |              | X            | X            |              |
| Testes e Validação     |              |              | X            |              |
| Documentação           |              |              | X            |              |
| Implantação            |              |              |              | X            |
| Treinamento            |              |              |              | X            |

## 7. Histórico de Versões

| **Data**       | **Versão** | **Descrição**            | **Autor**         | **Revisor**     | **Aprovado por** |
|----------------|------------|--------------------------|-------------------|-----------------|------------------|
| [Data atual]   | 1.0        | Versão inicial do documento | [Seu Nome]       | [Nome do Revisor] | [Nome do Aprovador] |
