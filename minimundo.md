# Mini Mundo: Sistema de Gestão de Projetos e Organizações

## Descrição Geral
O sistema gerencia organizações, seus membros, projetos associados e tarefas. Cada organização possui um endereço cadastrado e usuários vinculados. Projetos contêm tarefas e podem ter múltiplos participantes com diferentes funções.

## Entidades Principais

### 1. Organização (`Organization`)
- **Atributos**:
  - Nome (obrigatório)
  - Endereço (vinculado à tabela `Address`)
- **Regras**:
  - Uma organização deve ter exatamente um endereço
  - Pode ter zero ou mais usuários associados

### 2. Endereço (`Address`)
- **Atributos**:
  - Rua, Cidade, Estado, CEP, País (todos obrigatórios)
- **Regras**:
  - Um endereço pode ser compartilhado por múltiplas organizações

### 3. Usuário (`User`)
- **Atributos**:
  - Nome (obrigatório)
  - Email (único, obrigatório)
  - Organização vinculada
- **Regras**:
  - Cada usuário pertence a exatamente uma organização
  - Pode participar de zero ou mais projetos

### 4. Projeto (`Project`)
- **Atributos**:
  - Nome (obrigatório)
  - Descrição
  - Data de início/término
- **Regras**:
  - Deve ter pelo menos uma tarefa
  - Pode ter múltiplos participantes

### 5. Tarefa (`Task`)
- **Atributos**:
  - Título (obrigatório)
  - Descrição
  - Data de vencimento
  - Status (concluído/não concluído)
- **Regras**:
  - Pertence a exatamente um projeto
  - Status padrão é "não concluído"

### 6. Função (`Role`)
- **Atributos**:
  - Nome (ex: "Gerente", "Desenvolvedor")
  - Descrição
- **Regras**:
  - Define permissões em projetos

## Relacionamentos Chave

1. **Organização-Usuário**:
   - 1:N (Uma organização tem muitos usuários)
   - Exemplo: _Acme Inc._ possui os usuários _João_ e _Maria_

2. **Projeto-Tarefa**:
   - 1:N (Um projeto contém muitas tarefas)
   - Exemplo: Projeto _Portal Web_ tem tarefas como "Criar homepage" e "Configurar servidor"

3. **Usuário-Projeto-Função** (via `UserProject`):
   - N:M (Um usuário pode estar em muitos projetos com funções diferentes)
   - Exemplo: _Carlos_ é "Scrum Master" no projeto _Mobile_ e "Desenvolvedor" no projeto _API_

## Regras de Negócio
1. Todo usuário deve ter um email único no sistema
2. Tarefas não podem ter data de vencimento anterior à data de início do projeto
3. Quando uma organização é removida, todos seus usuários e projetos são deletados em cascata
4. Um projeto não pode ser excluído se tiver tarefas pendentes

## Exemplo de Cenário
**Organização:** TechSolutions  
**Endereço:** Rua das Inovações, 100 - São Paulo/SP  
**Usuários:** 
- Ana (ana@tech.com) - Diretora
- Pedro (pedro@tech.com) - Desenvolvedor

**Projetos:**
1. _Sistema ERP_ (01/01/2024 - 30/06/2024)
   - Tarefas:
     - Modelagem do BD (concluído)
     - Desenvolvimento do módulo financeiro (em andamento)
   - Participantes:
     - Ana como "Product Owner"
     - Pedro como "Desenvolvedor Backend"
