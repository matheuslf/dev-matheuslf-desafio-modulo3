# Desafio Técnico — Java Junior + Pleno + Sênior
## Replicador de Banco de Dados (Posto de Combustível)

---

## Contexto

Você irá desenvolver um **software replicador de banco de dados** responsável por copiar informações de um **Banco Origem** (posto de combustível) para **um ou mais Bancos Destino**, com base em configurações armazenadas em um **Banco de Controle (Gerencial)**.

Este desafio simula um cenário real de integração entre sistemas, com foco em:

- Bancos de dados relacionais  
- Consistência e integridade dos dados  
- Arquitetura de software  
- JDBC puro  
- Execução multiplataforma

<img width="613" height="440" alt="Captura de Tela 2026-01-05 às 11 03 10" src="https://github.com/user-attachments/assets/c2f9a284-642b-40fe-972f-3e31f1eaa2b5" />


---

## Objetivo

Criar um **replicador de dados em Java**, capaz de executar:

- Replicação completa (**full load**)
- Replicação parcial / incremental
- Execução manual e automática
- Execução em **Windows (com interface visual)** e **Linux (como serviço)**

---

## Estrutura de Bancos

### Banco Origem (Posto de Combustível)

Representa o sistema operacional do posto.

Tabelas mínimas obrigatórias:

- `funcionarios`
- `clientes`
- `bombas`
- `abastecimentos`

> A tabela `abastecimentos` deve possuir relacionamento com as demais.

A modelagem detalhada (campos, tipos, chaves) fica **a critério do candidato**, desde que coerente e relacional.

---

### 🔹 Bancos Destino (Clones)

- Um ou mais bancos que receberão os dados replicados
- Devem possuir estrutura compatível com o Banco Origem
- Uma mesma origem pode replicar para **N destinos**

---

### 🔹 Banco de Controle (Gerencial)

Banco responsável por **controlar o funcionamento do replicador**.

Deve armazenar, por exemplo:

- Configurações de conexão (origem e destinos)
- Parâmetros de execução
- Tipo de replicação
- Estado da replicação
- Logs e histórico de execução

> ⚠️ **A modelagem do Banco de Controle é livre**  
> Avaliaremos suas decisões arquiteturais e de modelagem.

---

## 🔁 Requisitos Funcionais

### ✅ RF1 — Configuração via Banco de Controle

O replicador deve buscar no Banco de Controle:

- Conexões JDBC
- Definição de banco origem e destinos
- Parâmetros de execução (batch size, intervalo, etc.)

---

### ✅ RF2 — Replicação Completa (Full Load)

- Copiar todos os dados do Banco Origem para o(s) Destino(s)
- Garantir integridade referencial
- Registrar a execução no Banco de Controle

---

### ✅ RF3 — Replicação Parcial / Incremental

- Replicar apenas dados novos ou alterados
- **Logs e controle de alterações devem ficar no Banco de Controle**
- A estratégia de incremental fica a critério do candidato, por exemplo:
  - Timestamp
  - Versionamento
  - Hash de registros
  - Checkpoints

---

### ✅ RF4 — Execução Manual

Permitir execução manual via tela

---

### ✅ RF5 — Execução Automática

Execução periódica. Pode utilizar:

- Scheduler, ou...
- Threads, ou...
- Loop controlado

Configuração obtida a partir do Banco de Controle

---

### ✅ RF6 — Múltiplos Destinos

- Um mesmo job pode replicar para vários destinos
- Falha em um destino não deve comprometer os demais

---

### Execução Multiplataforma

Windows e Linux:

- Deve existir uma forma visual de execução
- Pode ser implementado com: JavaFX ou Swing

Funcionalidades mínimas:

- Iniciar/parar replicação
- Visualizar status

---

### Requisitos Técnicos

Linguagem: 

- Java
- Acesso a dados com JDBC puro
- PreparedStatement
- Batch
- Controle explícito de transações
- Não utilizar ORM
- Arquitetura livre

A arquitetura escolhida será avaliada.

---

### Observabilidade

Logs claros contendo:

- Início e fim da execução
- Erros
- Quantidade de registros replicados
- Histórico das execuções armazenado no Banco de Controle

---

### Entregáveis

- Código-fonte em repositório GitHub
- Scripts SQL:
  - Banco Origem
  - Banco(s) Destino
  - Banco de Controle
- README explicando:
  - Como configurar o ambiente
  - Como executar no Windows
  - Como executar no Linux

---

### O que será avaliado

- Qualidade da arquitetura
- Organização do código
- Uso correto de JDBC
- Consistência e integridade dos dados
- Tratamento de falhas
- Clareza da documentação
- Capacidade de tomada de decisão técnica
