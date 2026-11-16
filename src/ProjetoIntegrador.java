import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProjetoIntegrador {

    public static void main(String[] args) {
        String[][] matrizClientes = new String[0][8];
        String[][] matrizContatos = new String[0][5];

        System.out.println("================================================");
        System.out.println(" SISTEMA DE CADASTRO DE CLIENTES e CONTATOS ");
        System.out.println("================================================");

        menuPrincipal(matrizClientes, matrizContatos);

        System.out.println("\nSistema encerrado. Até logo!");
    }

    private static void menuPrincipal(String[][] matrizClientes, String[][] matrizContatos) {
        int opcao = -1;
        Scanner scanner = new Scanner(System.in);

        while (opcao != 0) {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1 - Gerenciar clientes");
            System.out.println("2 - Gerenciar contatos");
            System.out.println("3 - Relatórios");
            System.out.println("4 - Carregar dados de teste (CSV)");
            System.out.println("0 - Sair");
            System.out.println("====================================");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        String[][][] resultado = menuClientes(matrizClientes, matrizContatos, scanner);
                        matrizClientes = resultado[0];
                        matrizContatos = resultado[1];
                        break;
                    case 2:
                        matrizContatos = menuContatos(matrizClientes, matrizContatos, scanner);
                        break;
                    case 3:
                        relatorios(matrizClientes, matrizContatos);
                        break;
                    case 4:
                        matrizClientes = carregarClientesCSV();
                        matrizContatos = carregarContatosCSV();
                        System.out.println("\n============================================");
                        System.out.println("Clientes carregados: " + matrizClientes.length);
                        System.out.println("Contatos carregados: " + matrizContatos.length);
                        System.out.println("============================================");
                        System.out.println("✓ Dados carregados com sucesso!");
                        break;
                    case 0:
                        System.out.println("\nEncerrando sistema...");
                        break;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite apenas números.");
                opcao = -1;
            }
        }
        scanner.close();
    }

    private static String[][][] menuClientes(String[][] clientes, String[][] contatos, Scanner scanner) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR CLIENTES ---");
            System.out.println("1 - Incluir cliente");
            System.out.println("2 - Listar clientes (todos os clientes)");
            System.out.println("3 - Consultar cliente por código");
            System.out.println("4 - Alterar cliente");
            System.out.println("5 - Apagar cliente");
            System.out.println("6 - Ordenar por nome");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

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
                        String[][][] resExclusao = apagarCliente(clientes, contatos, scanner);
                        clientes = resExclusao[0];
                        contatos = resExclusao[1];
                        break;
                    case 6:
                        ordenarClientesPorNome(clientes);
                        System.out.println("✓ Clientes ordenados por nome com sucesso!");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
            }
        }
        return new String[][][]{clientes, contatos};
    }

    private static String[][] menuContatos(String[][] clientes, String[][] contatos, Scanner scanner) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR CONTATOS ---");
            System.out.println("1 - Incluir contato");
            System.out.println("2 - Listar contatos (Todos os clientes)");
            System.out.println("3 - Listar contatos de um cliente");
            System.out.println("4 - Alterar contato");
            System.out.println("5 - Apagar contato");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

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
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
            }
        }
        return contatos;
    }

    // ========== FUNÇÕES DE CARREGAMENTO DE DADOS (CSV) ==========

    private static String[][] carregarClientesCSV() {
        String arquivo = "clientes.csv";
        String[][] matrizClientes = new String[0][8];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");

                if (dados.length >= 8) {
                    matrizClientes = aumentarMatrizClientes(matrizClientes);
                    int indice = matrizClientes.length - 1;

                    matrizClientes[indice][0] = dados[0].trim(); // codigo
                    matrizClientes[indice][1] = dados[1].trim(); // nome
                    matrizClientes[indice][2] = dados[2].trim(); // cpf_cnpj
                    matrizClientes[indice][3] = dados[3].trim(); // data_nascimento
                    matrizClientes[indice][4] = dados[4].trim(); // sexo
                    matrizClientes[indice][5] = dados[5].trim(); // cidade
                    matrizClientes[indice][6] = dados[6].trim(); // estado
                    matrizClientes[indice][7] = dados[7].trim().toUpperCase(); // status

                    contador++;
                }
            }
            System.out.println("✓ " + contador + " clientes foram carregados do arquivo.");
        } catch (IOException e) {
            System.out.println("\n✗ Erro ao ler arquivo " + arquivo + ": " + e.getMessage());
            System.out.println("Certifique-se de que o arquivo existe na raiz do projeto.");
        }
        return matrizClientes;
    }

    private static String[][] carregarContatosCSV() {
        String arquivo = "contatos.csv";
        String[][] matrizContatos = new String[0][5];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");

                if (dados.length >= 5) {
                    matrizContatos = aumentarMatrizContatos(matrizContatos);
                    int indice = matrizContatos.length - 1;

                    matrizContatos[indice][0] = dados[0].trim(); // codigo_contato
                    matrizContatos[indice][1] = dados[1].trim(); // codigo_cliente
                    matrizContatos[indice][2] = dados[2].trim(); // tipo
                    matrizContatos[indice][3] = dados[3].trim(); // valor
                    matrizContatos[indice][4] = dados[4].trim().toUpperCase(); // status

                    contador++;
                }
            }
            System.out.println("✓ " + contador + " contatos foram carregados do arquivo.");
        } catch (IOException e) {
            System.out.println("\n✗ Erro ao ler arquivo " + arquivo + ": " + e.getMessage());
            System.out.println("Certifique-se de que o arquivo existe na raiz do projeto.");
        }
        return matrizContatos;
    }

    // ========== CRUD DE CLIENTES ==========

    private static String[][] incluirCliente(String[][] clientes, Scanner scanner) {
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
        System.out.print("Status (ATIVO/INATIVO): ");
        clientes[novoIndice][7] = scanner.nextLine().toUpperCase();

        System.out.println("✓ Cliente incluído com sucesso! Código gerado: " + clientes[novoIndice][0]);
        return clientes;
    }

    private static void listarClientesTabela(String[][] clientes) {
        if (clientes.length == 0) {
            System.out.println("\nNenhum cliente cadastrado.");
            return;
        }
        System.out.println("\nCódigo | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
        System.out.println("--------------------------------------------------------------------------------");
        for (int i = 0; i < clientes.length; i++) {
            System.out.println(clientes[i][0] + " | " + clientes[i][1] + " | " + clientes[i][2] + " | " +
                    clientes[i][3] + " | " + clientes[i][4] + " | " + clientes[i][5] + " | " +
                    clientes[i][6] + " | " + clientes[i][7]);
        }
    }

    private static int buscarClientePorCodigo(String[][] clientes, String codigo) {
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i][0].equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    private static void consultarClienteMenu(String[][] clientes, Scanner scanner) {
        System.out.print("Digite o código do cliente: ");
        String codigo = scanner.nextLine();
        int pos = buscarClientePorCodigo(clientes, codigo);
        if (pos != -1) {
            System.out.println("\nCódigo | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
            System.out.println(clientes[pos][0] + " | " + clientes[pos][1] + " | " + clientes[pos][2] + " | " +
                    clientes[pos][3] + " | " + clientes[pos][4] + " | " + clientes[pos][5] + " | " +
                    clientes[pos][6] + " | " + clientes[pos][7]);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void alterarCliente(String[][] clientes, Scanner scanner) {
        listarClientesTabela(clientes);
        if (clientes.length == 0) return;

        System.out.print("\nQual cliente deseja alterar (Código) => ");
        String codigo = scanner.nextLine();
        int pos = buscarClientePorCodigo(clientes, codigo);

        if (pos != -1) {
            System.out.println("Cliente encontrado: " + clientes[pos][1]);
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
                clientes[pos][7] = scanner.nextLine().toUpperCase();
                System.out.println("✓ Cliente alterado com sucesso!");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static String[][][] apagarCliente(String[][] clientes, String[][] contatos, Scanner scanner) {
        listarClientesTabela(clientes);
        if (clientes.length == 0) return new String[][][]{clientes, contatos};

        System.out.print("\nQual cliente deseja apagar (Código) => ");
        String codigo = scanner.nextLine();
        int pos = buscarClientePorCodigo(clientes, codigo);

        if (pos != -1) {
            System.out.println("Cliente encontrado: " + clientes[pos][1]);
            System.out.print("Confirma exclusão do cliente e de TODOS os seus contatos? (S/N): ");
            String confirma = scanner.nextLine().toUpperCase();

            if (confirma.equals("S")) {
                String[][] novaMatrizClientes = new String[clientes.length - 1][8];
                int j = 0;
                for (int i = 0; i < clientes.length; i++) {
                    if (i != pos) {
                        copiarLinha(clientes, novaMatrizClientes, i, j, 8);
                        j++;
                    }
                }

                int contatosRestantes = 0;
                for (int i = 0; i < contatos.length; i++) {
                    if (!contatos[i][1].equals(codigo)) {
                        contatosRestantes++;
                    }
                }

                String[][] novaMatrizContatos = new String[contatosRestantes][5];
                int k = 0;
                for (int i = 0; i < contatos.length; i++) {
                    if (!contatos[i][1].equals(codigo)) {
                        copiarLinha(contatos, novaMatrizContatos, i, k, 5);
                        k++;
                    }
                }

                System.out.println("✓ Cliente e seus respectivos contatos foram removidos com sucesso.");
                return new String[][][]{novaMatrizClientes, novaMatrizContatos};
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
        return new String[][][]{clientes, contatos};
    }

    // ========== CRUD DE CONTATOS ==========

    private static String[][] incluirContato(String[][] clientes, String[][] contatos, Scanner scanner) {
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
            System.out.print("Digite o tipo do contato (Telefone, WhatsApp, Email, Instagram, Site, etc): ");
            contatos[novoIndice][2] = scanner.nextLine();
            System.out.print("Digite o valor do contato: ");
            contatos[novoIndice][3] = scanner.nextLine();
            contatos[novoIndice][4] = "ATIVO";

            System.out.println("✓ Contato incluído com sucesso!");
        } else {
            System.out.println("Cliente não encontrado. Operação cancelada.");
        }
        return contatos;
    }

    private static void listarContatosTabela(String[][] contatos, String[][] clientes) {
        if (contatos.length == 0) {
            System.out.println("\nNenhum contato cadastrado.");
            return;
        }
        System.out.println("\nCodCont | CodCli | Nome Cliente | Tipo | Valor | Status");
        System.out.println("----------------------------------------------------------------------");
        for (int i = 0; i < contatos.length; i++) {
            int posCli = buscarClientePorCodigo(clientes, contatos[i][1]);
            String nomeCli = (posCli != -1) ? clientes[posCli][1] : "Desconhecido";
            System.out.println(contatos[i][0] + " | " + contatos[i][1] + " | " + nomeCli + " | " +
                    contatos[i][2] + " | " + contatos[i][3] + " | " + contatos[i][4]);
        }
    }

    private static void listarContatosPorCliente(String[][] contatos, String[][] clientes, String codCli) {
        int posCli = buscarClientePorCodigo(clientes, codCli);
        if (posCli == -1) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        String nomeCli = clientes[posCli][1];
        System.out.println("\nContatos de " + nomeCli + ":");
        System.out.println("CodCont | CodCli | Nome Cliente | Tipo | Valor | Status");
        System.out.println("----------------------------------------------------------------------");

        boolean achou = false;
        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i][1].equals(codCli)) {
                System.out.println(contatos[i][0] + " | " + contatos[i][1] + " | " + nomeCli + " | " +
                        contatos[i][2] + " | " + contatos[i][3] + " | " + contatos[i][4]);
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhum contato cadastrado para este cliente.");
        }
    }

    private static void alterarContato(String[][] contatos, String[][] clientes, Scanner scanner) {
        listarContatosTabela(contatos, clientes);
        if (contatos.length == 0) return;

        System.out.print("\nQual contato deseja alterar (CodCont) => ");
        String codCont = scanner.nextLine();

        int pos = -1;
        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i][0].equals(codCont)) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            System.out.println("Contato encontrado: " + contatos[pos][2] + " - " + contatos[pos][3]);
            System.out.print("Deseja alterar este contato? (S/N): ");
            String confirma = scanner.nextLine().toUpperCase();
            if (confirma.equals("S")) {
                System.out.print("Novo Tipo: ");
                contatos[pos][2] = scanner.nextLine();
                System.out.print("Novo Valor: ");
                contatos[pos][3] = scanner.nextLine();
                System.out.println("✓ Contato alterado com sucesso!");
            }
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    private static String[][] apagarContato(String[][] contatos, String[][] clientes, Scanner scanner) {
        listarContatosTabela(contatos, clientes);
        if (contatos.length == 0) return contatos;

        System.out.print("\nQual contato deseja apagar (CodCont) => ");
        String codCont = scanner.nextLine();

        int pos = -1;
        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i][0].equals(codCont)) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            System.out.println("Contato encontrado: " + contatos[pos][2] + " - " + contatos[pos][3]);
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
                System.out.println("✓ Contato removido com sucesso.");
                return novaMatriz;
            }
        } else {
            System.out.println("Contato não encontrado.");
        }
        return contatos;
    }

    // ========== RELATÓRIOS ==========

    private static void relatorios(String[][] clientes, String[][] contatos) {
        System.out.println("\n================ RELATÓRIOS ================");
        System.out.println("Listagem de Clientes e Total de Contatos:");
        System.out.println("Código | Nome | Qtd. Contatos");
        System.out.println("--------------------------------------------");

        int clientesSemContato = 0;

        for (int i = 0; i < clientes.length; i++) {
            int qtdContatosCliente = 0;
            for (int j = 0; j < contatos.length; j++) {
                if (contatos[j][1].equals(clientes[i][0])) {
                    qtdContatosCliente++;
                }
            }
            if (qtdContatosCliente == 0) {
                clientesSemContato++;
            }
            System.out.println(clientes[i][0] + " | " + clientes[i][1] + " | " + qtdContatosCliente);
        }

        System.out.println("\n--- SUMARIZAÇÃO DE DADOS ---");
        System.out.println("Total de clientes: " + clientes.length);
        System.out.println("Total de contatos: " + contatos.length);
        if (clientes.length > 0) {
            double media = (double) contatos.length / clientes.length;
            System.out.printf("Contatos por cliente (média): %.2f\n", media);
        } else {
            System.out.println("Contatos por cliente (média): 0.00");
        }
        System.out.println("Clientes sem contato: " + clientesSemContato);
        System.out.println("============================================");
    }

    // ========== FUNÇÕES DE MANIPULAÇÃO DE MATRIZES E ORDENAÇÃO ==========

    private static String[][] aumentarMatrizClientes(String[][] matrizClientes) {
        String[][] novaMatriz = new String[matrizClientes.length + 1][8];
        for (int i = 0; i < matrizClientes.length; i++) {
            copiarLinha(matrizClientes, novaMatriz, i, i, 8);
        }
        return novaMatriz;
    }

    private static String[][] aumentarMatrizContatos(String[][] matrizContatos) {
        String[][] novaMatriz = new String[matrizContatos.length + 1][5];
        for (int i = 0; i < matrizContatos.length; i++) {
            copiarLinha(matrizContatos, novaMatriz, i, i, 5);
        }
        return novaMatriz;
    }

    private static void copiarLinha(String[][] origem, String[][] destino, int linhaOrigem, int linhaDestino, int colunas) {
        for (int c = 0; c < colunas; c++) {
            destino[linhaDestino][c] = origem[linhaOrigem][c];
        }
    }

    private static void limparLinha(String[][] matriz, int linha, int colunas) {
        for (int c = 0; c < colunas; c++) {
            matriz[linha][c] = null;
        }
    }

    private static void trocarLinhas(String[][] matriz, int linha1, int linha2, int colunas) {
        String[] temp = new String[colunas];
        for (int c = 0; c < colunas; c++) {
            temp[c] = matriz[linha1][c];
            matriz[linha1][c] = matriz[linha2][c];
            matriz[linha2][c] = temp[c];
        }
    }

    private static void ordenarClientesPorNome(String[][] clientes) {
        for (int i = 0; i < clientes.length - 1; i++) {
            for (int j = i + 1; j < clientes.length; j++) {
                if (compararNomeCharPorChar(clientes[i][1], clientes[j][1]) > 0) {
                    trocarLinhas(clientes, i, j, 8);
                }
            }
        }
    }

    private static int compararNomeCharPorChar(String s1, String s2) {
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