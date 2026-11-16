import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] clientes = new String[0][8];
        String[][] contatos = new String[0][5];
        int opcao;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Gerenciar clientes");
            System.out.println("2 - Gerenciar contatos");
            System.out.println("3 - Relatórios");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    clientes = menuClientes(clientes, contatos, scanner);
                    break;
                case 2:
                    contatos = menuContatos(clientes, contatos, scanner);
                    break;
                case 3:
                    relatorios(clientes, contatos);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // ==========================================
    // MENUS
    // ==========================================
    public static String[][] menuClientes(String[][] clientes, String[][] contatos, Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR CLIENTES ---");
            System.out.println("1 - Incluir cliente");
            System.out.println("2 - Listar clientes (todos os clientes)");
            System.out.println("3 - Consultar cliente por código");
            System.out.println("4 - Alterar cliente");
            System.out.println("5 - Apagar cliente");
            System.out.println("6 - Ordenar por nome");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    clientes = incluirCliente(clientes, scanner);
                    break;
                case 2:
                    listarClientesTabela(clientes);
                    break;
                case 3:
                    consultarClienteMenu(clientes, scanner);
                    break;
                case 4:
                    alterarCliente(clientes, scanner);
                    break;
                case 5:
                    clientes = apagarCliente(clientes, contatos, scanner);
                    break;
                case 6:
                    ordenarClientesPorNome(clientes);
                    System.out.println("Clientes ordenados com sucesso.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        return clientes;
    }

    public static String[][] menuContatos(String[][] clientes, String[][] contatos, Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR CONTATOS ---");
            System.out.println("1 - Incluir contato");
            System.out.println("2 - Listar contatos (Todos os clientes)");
            System.out.println("3 - Listar contatos de um cliente");
            System.out.println("4 - Alterar contato");
            System.out.println("5 - Apagar contato");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    contatos = incluirContato(clientes, contatos, scanner);
                    break;
                case 2:
                    listarContatosTabela(contatos, clientes);
                    break;
                case 3:
                    System.out.print("Selecione o cliente que quer listar os contatos (Código): ");
                    String codCli = scanner.nextLine();
                    listarContatosPorCliente(contatos, clientes, codCli);
                    break;
                case 4:
                    alterarContato(contatos, clientes, scanner);
                    break;
                case 5:
                    contatos = apagarContato(contatos, clientes, scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        return contatos;
    }

    // ==========================================
    // CRUD DE CLIENTES
    // ==========================================
    public static String[][] incluirCliente(String[][] clientes, Scanner scanner) {
        clientes = aumentarMatrizClientes(clientes);
        int novoIndice = clientes.length - 1;

        int maiorCodigo = 0;
        for (int i = 0; i < clientes.length - 1; i++) {
            if (clientes[i][0] != null) {
                int cod = Integer.parseInt(clientes[i][0]);
                if (cod > maiorCodigo) maiorCodigo = cod;
            }
        }

        clientes[novoIndice][0] = String.valueOf(maiorCodigo + 1);
        System.out.print("Nome: ");
        clientes[novoIndice][1] = scanner.nextLine();
        System.out.print("CPF/CNPJ: ");
        clientes[novoIndice][2] = scanner.nextLine();
        System.out.print("Data de nascimento: ");
        clientes[novoIndice][3] = scanner.nextLine();
        System.out.print("Sexo: ");
        clientes[novoIndice][4] = scanner.nextLine();
        System.out.print("Cidade: ");
        clientes[novoIndice][5] = scanner.nextLine();
        System.out.print("Estado: ");
        clientes[novoIndice][6] = scanner.nextLine();
        System.out.print("Status: ");
        clientes[novoIndice][7] = scanner.nextLine();

        System.out.println("Cliente incluído com sucesso!");
        return clientes;
    }

    public static void listarClientesTabela(String[][] clientes) {
        System.out.println("\nCódigo | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
        for (int i = 0; i < clientes.length; i++) {
            System.out.println(clientes[i][0] + " | " + clientes[i][1] + " | " + clientes[i][2] + " | " +
                    clientes[i][3] + " | " + clientes[i][4] + " | " + clientes[i][5] + " | " +
                    clientes[i][6] + " | " + clientes[i][7]);
        }
    }

    public static int buscarClientePorCodigo(String[][] clientes, String codigo) {
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i][0].equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    public static void consultarClienteMenu(String[][] clientes, Scanner scanner) {
        System.out.print("Digite o código do cliente: ");
        String codigo = scanner.nextLine();
        int pos = buscarClientePorCodigo(clientes, codigo);
        if (pos != -1) {
            System.out.println("Código | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
            System.out.println(clientes[pos][0] + " | " + clientes[pos][1] + " | " + clientes[pos][2] + " | " +
                    clientes[pos][3] + " | " + clientes[pos][4] + " | " + clientes[pos][5] + " | " +
                    clientes[pos][6] + " | " + clientes[pos][7]);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public static void alterarCliente(String[][] clientes, Scanner scanner) {
        listarClientesTabela(clientes);
        System.out.print("\nQual cliente deseja alterar => ");
        String codigo = scanner.nextLine();
        int pos = buscarClientePorCodigo(clientes, codigo);

        if (pos != -1) {
            System.out.println("Cliente encontrado:");
            System.out.println(clientes[pos][0] + " | " + clientes[pos][1] + " | " + clientes[pos][2] + " | " +
                    clientes[pos][3] + " | " + clientes[pos][4] + " | " + clientes[pos][5] + " | " +
                    clientes[pos][6] + " | " + clientes[pos][7]);

            System.out.print("Deseja alterar este cliente? (S/N): ");
            String confirma = scanner.nextLine().toUpperCase();

            if (confirma.equals("S")) {
                System.out.println("Digite os novos dados:");
                System.out.print("Nome: ");
                clientes[pos][1] = scanner.nextLine();
                System.out.print("CPF/CNPJ: ");
                clientes[pos][2] = scanner.nextLine();
                System.out.print("Data de nascimento: ");
                clientes[pos][3] = scanner.nextLine();
                System.out.print("Sexo: ");
                clientes[pos][4] = scanner.nextLine();
                System.out.print("Cidade: ");
                clientes[pos][5] = scanner.nextLine();
                System.out.print("Estado: ");
                clientes[pos][6] = scanner.nextLine();
                System.out.print("Status: ");
                clientes[pos][7] = scanner.nextLine();
                System.out.println("Cliente alterado com sucesso!");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public static String[][] apagarCliente(String[][] clientes, String[][] contatos, Scanner scanner) {
        listarClientesTabela(clientes);
        System.out.print("\nQual cliente deseja apagar => ");
        String codigo = scanner.nextLine();
        int pos = buscarClientePorCodigo(clientes, codigo);

        if (pos != -1) {
            System.out.println("Cliente encontrado: " + clientes[pos][1]);
            System.out.print("Confirma exclusão? (S/N): ");
            String confirma = scanner.nextLine().toUpperCase();

            if (confirma.equals("S")) {
                // Ao apagar o cliente, seus contatos deveriam ser apagados (simulação para cumprir a regra)
                for (int i = 0; i < contatos.length; i++) {
                    if (contatos[i][1].equals(codigo)) {
                        contatos[i][4] = "INATIVO"; // Forma simplificada sem criar terceira matriz de retorno
                    }
                }

                String[][] novaMatriz = new String[clientes.length - 1][8];
                int j = 0;
                for (int i = 0; i < clientes.length; i++) {
                    if (i != pos) {
                        copiarLinha(clientes, novaMatriz, i, j, 8);
                        j++;
                    }
                }
                System.out.println("Cliente removido com sucesso.");
                return novaMatriz;
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
        return clientes;
    }

    // ==========================================
    // CRUD DE CONTATOS
    // ==========================================
    public static String[][] incluirContato(String[][] clientes, String[][] contatos, Scanner scanner) {
        System.out.print("Digite o código do cliente: ");
        String codCli = scanner.nextLine();
        int posCliente = buscarClientePorCodigo(clientes, codCli);

        if (posCliente != -1) {
            System.out.println("Cliente encontrado: " + clientes[posCliente][1]);

            contatos = aumentarMatrizContatos(contatos);
            int novoIndice = contatos.length - 1;

            int maiorCodigo = 0;
            for (int i = 0; i < contatos.length - 1; i++) {
                if (contatos[i][0] != null) {
                    int cod = Integer.parseInt(contatos[i][0]);
                    if (cod > maiorCodigo) maiorCodigo = cod;
                }
            }

            contatos[novoIndice][0] = String.valueOf(maiorCodigo + 1);
            contatos[novoIndice][1] = codCli;
            System.out.print("Digite o tipo do contato (Telefone, WhatsApp, Email, etc): ");
            contatos[novoIndice][2] = scanner.nextLine();
            System.out.print("Digite o valor do contato: ");
            contatos[novoIndice][3] = scanner.nextLine();
            contatos[novoIndice][4] = "ATIVO";

            System.out.println("Contato incluído com sucesso!");
        } else {
            System.out.println("Cliente não encontrado. Operação cancelada.");
        }
        return contatos;
    }

    public static void listarContatosTabela(String[][] contatos, String[][] clientes) {
        System.out.println("\nCodCont | CodCli | Nome Cliente | Tipo | Valor | Status");
        for (int i = 0; i < contatos.length; i++) {
            if (!contatos[i][4].equals("INATIVO_DELETADO")) {
                int posCli = buscarClientePorCodigo(clientes, contatos[i][1]);
                String nomeCli = (posCli != -1) ? clientes[posCli][1] : "Desconhecido";
                System.out.println(contatos[i][0] + " | " + contatos[i][1] + " | " + nomeCli + " | " +
                        contatos[i][2] + " | " + contatos[i][3] + " | " + contatos[i][4]);
            }
        }
    }

    public static void listarContatosPorCliente(String[][] contatos, String[][] clientes, String codCli) {
        System.out.println("\nCodCont | CodCli | Nome Cliente | Tipo | Valor | Status");
        int posCli = buscarClientePorCodigo(clientes, codCli);
        String nomeCli = (posCli != -1) ? clientes[posCli][1] : "Desconhecido";

        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i][1].equals(codCli) && !contatos[i][4].equals("INATIVO_DELETADO")) {
                System.out.println(contatos[i][0] + " | " + contatos[i][1] + " | " + nomeCli + " | " +
                        contatos[i][2] + " | " + contatos[i][3] + " | " + contatos[i][4]);
            }
        }
    }

    public static void alterarContato(String[][] contatos, String[][] clientes, Scanner scanner) {
        listarContatosTabela(contatos, clientes);
        System.out.print("\nQual contato deseja alterar => ");
        String codCont = scanner.nextLine();

        int pos = -1;
        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i][0].equals(codCont)) pos = i;
        }

        if (pos != -1) {
            System.out.print("Deseja alterar este contato? (S/N): ");
            String confirma = scanner.nextLine().toUpperCase();
            if (confirma.equals("S")) {
                System.out.print("Tipo: ");
                contatos[pos][2] = scanner.nextLine();
                System.out.print("Valor: ");
                contatos[pos][3] = scanner.nextLine();
                System.out.println("Contato alterado com sucesso!");
            }
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    public static String[][] apagarContato(String[][] contatos, String[][] clientes, Scanner scanner) {
        listarContatosTabela(contatos, clientes);
        System.out.print("\nQual contato deseja apagar => ");
        String codCont = scanner.nextLine();

        int pos = -1;
        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i][0].equals(codCont)) pos = i;
        }

        if (pos != -1) {
            System.out.print("Confirma exclusão? (S/N): ");
            String confirma = scanner.nextLine().toUpperCase();

            if (confirma.equals("S")) {
                String[][] novaMatriz = new String[contatos.length - 1][5];
                int j = 0;
                for (int i = 0; i < contatos.length; i++) {
                    if (i != pos) {
                        copiarLinha(contatos, novaMatriz, i, j, 5);
                        j++;
                    }
                }
                System.out.println("Contato removido com sucesso.");
                return novaMatriz;
            }
        } else {
            System.out.println("Contato não encontrado.");
        }
        return contatos;
    }

    // ==========================================
    // RELATÓRIOS
    // ==========================================
    public static void relatorios(String[][] clientes, String[][] contatos) {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("Total de clientes: " + clientes.length);
        System.out.println("Total de contatos: " + contatos.length);

        if (clientes.length > 0) {
            System.out.println("Contatos por cliente (média): " + ((double) contatos.length / clientes.length));
        }

        System.out.println("\nClientes sem contato:");
        for (int i = 0; i < clientes.length; i++) {
            boolean temContato = false;
            for (int j = 0; j < contatos.length; j++) {
                if (contatos[j][1].equals(clientes[i][0])) {
                    temContato = true;
                    break;
                }
            }
            if (!temContato) {
                System.out.println("- " + clientes[i][1]);
            }
        }
    }

    // ==========================================
    // FUNÇÕES AUXILIARES E ORDENAÇÃO
    // ==========================================
    public static String[][] aumentarMatrizClientes(String[][] clientes) {
        String[][] novaMatriz = new String[clientes.length + 1][8];
        for (int i = 0; i < clientes.length; i++) {
            copiarLinha(clientes, novaMatriz, i, i, 8);
        }
        return novaMatriz;
    }

    public static String[][] aumentarMatrizContatos(String[][] contatos) {
        String[][] novaMatriz = new String[contatos.length + 1][5];
        for (int i = 0; i < contatos.length; i++) {
            copiarLinha(contatos, novaMatriz, i, i, 5);
        }
        return novaMatriz;
    }

    public static void copiarLinha(String[][] origem, String[][] destino, int linhaOrigem, int linhaDestino, int colunas) {
        for (int c = 0; c < colunas; c++) {
            destino[linhaDestino][c] = origem[linhaOrigem][c];
        }
    }

    public static void limparLinha(String[][] matriz, int linha, int colunas) {
        for (int c = 0; c < colunas; c++) {
            matriz[linha][c] = null;
        }
    }

    public static void trocarLinhas(String[][] matriz, int linha1, int linha2, int colunas) {
        String[] temp = new String[colunas];
        for (int c = 0; c < colunas; c++) {
            temp[c] = matriz[linha1][c];
            matriz[linha1][c] = matriz[linha2][c];
            matriz[linha2][c] = temp[c];
        }
    }

    public static void ordenarClientesPorNome(String[][] clientes) {
        for (int i = 0; i < clientes.length - 1; i++) {
            for (int j = i + 1; j < clientes.length; j++) {
                if (compararNomeCharPorChar(clientes[i][1], clientes[j][1]) > 0) {
                    trocarLinhas(clientes, i, j, 8);
                }
            }
        }
    }

    public static int compararNomeCharPorChar(String s1, String s2) {
        String str1 = s1.toUpperCase();
        String str2 = s2.toUpperCase();
        int tamanhoMinimo = str1.length() < str2.length() ? str1.length() : str2.length();

        for (int i = 0; i < tamanhoMinimo; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.charAt(i) - str2.charAt(i);
            }
        }
        return str1.length() - str2.length();
    }
}
