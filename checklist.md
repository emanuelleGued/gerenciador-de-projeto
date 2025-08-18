# 📋 Checklist de Avaliação – Projeto de Gerenciamento

### **1. Visão Geral**

* **Cobertura:** Diagramas contemplam cadastro de usuários, organizações, projetos e tarefas.
* **Forças:**

  * Modelo de classes bem estruturado, atributos e métodos consistentes com o código.
  * Casos de uso coerentes com funcionalidades implementadas.
  * Diagramas de atividades e de sequência cobrem fluxos principais.



### **2. Avaliação do Diagrama de Classes**

| Critério                 | Status | Comentário                                                          | Ação Recomendada                    |
| ------------------------ | ------ | ------------------------------------------------------------------- | ----------------------------------- |
| **Diagrama vazio**       | ✅      | Contém várias classes do sistema (`User`, `Project`, `Task`, etc.). | -                                   |
| **Classe vazia**         | ✅      | Todas possuem atributos e/ou métodos.                               | -                                   |
| **Duplicidade de nomes** | ✅      | Não há classes duplicadas.                                          | -                                   |
| **Classe abstrata**      | N/A    | Nenhuma classe abstrata definida.                                   | -                                   |
| **Atributo duplicado**   | ✅      | Não foram encontrados.                                              | -                                   |
| **Falta de tipo**        | ✅      | Atributos, parâmetros e retornos bem tipados.                       | -                                   |
| **Ciclo de herança**     | ✅      | Não existe hierarquia.                                              | -                                   |
| **Interfaces**           | N/A    | Não foram utilizadas.                                               | -                                   |



### **3. Avaliação dos Casos de Uso**

| Critério                           | Status | Comentário                                       | Ação Recomendada          |
| ---------------------------------- | ------ | ------------------------------------------------ | ------------------------- |
| **Correspondência com requisitos** | ✅      | Cada caso de uso reflete um requisito funcional. | -                         |
| **Caso de uso solto**              | ✅      | Todos ligados a pelo menos um ator.              | -                         |
| **Ator solto**                     | ✅      | `Admin` e `Membro da equipe` estão conectados.   | -                         |
| **Ciclo de inclusão/extensão**     | ✅      | Não há ciclos inválidos.                         | -                         |
| **Ciclo de generalização**         | ✅      | Não aplicável.                                   | -                         |


### **4. Avaliação dos Diagramas de Sequência**

| Critério                      | Status | Comentário                                                          | Ação Recomendada                                                 |
| ----------------------------- | ------ | ------------------------------------------------------------------- | ---------------------------------------------------------------- |
| **Mensagens correspondentes** | ✅      | Mensagens principais (`addUser`, `addTask`, etc.) existem nos DAOs. | -                                                                |
| **Usuário**                   | ✅      | Fluxo consistente com `UserDAO` e `UserOrganization`.               | -                                                                |
| **Fragmentos combinados**     | N/A    | Não há `alt/opt/loop`.                                              | Usar se quiser enriquecer lógicas condicionais.                  |


### **5. Avaliação dos Diagramas de Atividades**

| Critério                             | Status | Comentário                                                                     | Ação Recomendada                 |
| ------------------------------------ | ------ | ------------------------------------------------------------------------------ | -------------------------------- |
| **Correspondência com casos de uso** | ✅      | Fluxos de cadastro de usuário, criação de projeto e tarefas bem representados. | -                                |
| **Ações correspondem a métodos**     | ✅   | “Definir Roles do Usuário” tem persistência implementada.                  | - |
| **Gerenciamento de Tarefas**         | ✅      | Compatível com `TaskDAO`.                                                      | -                                |
| **Gerenciamento de Usuário**         | ✅     | Parte de roles com suporte no código atual.                                    | - |

