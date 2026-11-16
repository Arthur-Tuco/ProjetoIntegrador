# Sistema de Cadastro de Clientes e Contatos

Projeto Integrador desenvolvido em **Java Console** com foco em manipulação de estruturas de dados estáticas (matrizes bidimensionais), redimensionamento dinâmico manual, algoritmos manuais de ordenação e relacionamento 1:N (um cliente para vários contatos).

---

## Funcionalidades

### Gerenciamento de Clientes (CRUD)
* **Incluir cliente:** Cadastro completo com geração automática e sequencial de código.
* **Listar clientes:** Exibição de todos os registros em formato de tabela no console.
* **Consultar cliente:** Busca individual pelo código identificador.
* **Alterar cliente:** Edição dos dados cadastrais mediante confirmação.
* **Apagar cliente:** Exclusão do cliente e remoção em cascata de todos os seus contatos vinculados.
* **Ordenar por nome:** Ordenação alfabética manual (caractere por caractere, sem uso de `compareTo` ou `Arrays.sort`).

### Gerenciamento de Contatos (CRUD)
* **Incluir contato:** Vinculação de múltiplos contatos (Telefone, WhatsApp, E-mail, Instagram, Site, etc.) a um cliente existente.
* **Listar todos os contatos:** Exibição tabular realizando *JOIN* manual para mostrar o nome do cliente associado.
* **Listar contatos por cliente:** Filtro de contatos específicos de um determinado código de cliente.
* **Alterar contato:** Edição de tipo e valor do contato.
* **Apagar contato:** Remoção física do registro e redimensionamento da matriz.

### Relatórios e Carga de Dados
* **Relatórios Gerenciais:** Listagem da quantidade de contatos por cliente, total geral de clientes e contatos, média de contatos por cliente e contagem de clientes sem contato.
* **Importação de Dados de Teste (CSV):** Leitura automatizada dos arquivos `clientes.csv` e `contatos.csv` para popular as matrizes inicialmente.

---

## Restrições Técnicas Atendidas

O sistema foi construído seguindo rigorosamente as diretrizes acadêmicas do projeto:
* **Armazenamento:** Exclusivamente matrizes primitivas (`String[][]`) para Clientes (8 colunas) e Contatos (5 colunas).
* **Escopo:** Ausência total de variáveis globais na classe; todas as matrizes são passadas por parâmetro e retornadas conforme necessário.
* **Crescimento Dinâmico Manual:** Implementação própria para expandir e retrair o tamanho das matrizes a cada inserção ou exclusão, sem uso de `ArrayList` ou `List`.
* **Ordenação e Comparação Manual:** Implementação de comparação lexicográfica utilizando `charAt()`, `length()` e `toUpperCase()`.

---

## Estrutura de Arquivos

```text
├── src/
│   └── ProjetoIntegrador.java   # Código-fonte principal do sistema
├── clientes.csv                 # Base de dados de teste para clientes
├── contatos.csv                 # Base de dados de teste para contatos
└── README.md                    # Documentação do projeto