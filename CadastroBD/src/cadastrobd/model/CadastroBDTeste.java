package cadastrobd.model;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.dao.PessoaFisicaDAO;
import cadastrobd.model.dao.PessoaJuridicaDAO;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CadastroBDTeste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaDAO daoFisica = new PessoaFisicaDAO();
        PessoaJuridicaDAO daoJuridica = new PessoaJuridicaDAO();
        int opcao;

        do {
            System.out.println("\n=== Sistema de Cadastro ===");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Buscar pelo Id");
            System.out.println("5 - Exibir Todos");
            System.out.println("0 - Finalizar Programa");
            System.out.print("Digite a opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Erro: Opção inválida. Digite um número inteiro.");
                scanner.next(); // Limpa o buffer do scanner
                opcao = -1; // Garante que o loop continue
            }
           

            switch (opcao) {
                case 1:
                    incluirPessoa(scanner, daoFisica, daoJuridica);
                    break;
                case 2:
                    alterarPessoa(scanner, daoFisica, daoJuridica);
                    break;
                case 3:
                    excluirPessoa(scanner, daoFisica, daoJuridica);
                    break;
                case 4:
                    buscarPessoaPorId(scanner, daoFisica, daoJuridica);
                    break;
                case 5:
                    exibirTodos(daoFisica, daoJuridica);
                    break;
                case 0:
                    System.out.println("Finalizando o programa...");
                    break;
                default:
                    if(opcao != -1){
                       System.out.println("Opção inválida. Tente novamente."); 
                    }
                    
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void incluirPessoa(Scanner scanner, PessoaFisicaDAO daoFisica, PessoaJuridicaDAO daoJuridica) {
        scanner.nextLine(); // Consumir a quebra de linha deixada pelo nextInt()
        System.out.print("F - Pessoa Física | J - Pessoa Jurídica: ");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            PessoaFisica pf = new PessoaFisica();
            System.out.print("Nome: ");
            pf.setNome(scanner.nextLine());
            System.out.print("Logradouro: ");
            pf.setLogradouro(scanner.nextLine());
            System.out.print("Cidade: ");
            pf.setCidade(scanner.nextLine());
            System.out.print("Estado: ");
            pf.setEstado(scanner.nextLine());
            System.out.print("Telefone: ");
            pf.setTelefone(scanner.nextLine());
            System.out.print("Email: ");
            pf.setEmail(scanner.nextLine());
            System.out.print("CPF: ");
            pf.setCpf(scanner.nextLine());

            try {
                daoFisica.incluir(pf);
                System.out.println("Pessoa Física inserida com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao inserir Pessoa Física: " + e.getMessage());
            }

        } else if (tipo.equals("J")) {
            PessoaJuridica pj = new PessoaJuridica();
            System.out.print("Nome: ");
            pj.setNome(scanner.nextLine());
            System.out.print("Logradouro: ");
            pj.setLogradouro(scanner.nextLine());
            System.out.print("Cidade: ");
            pj.setCidade(scanner.nextLine());
            System.out.print("Estado: ");
            pj.setEstado(scanner.nextLine());
            System.out.print("Telefone: ");
            pj.setTelefone(scanner.nextLine());
            System.out.print("Email: ");
            pj.setEmail(scanner.nextLine());
            System.out.print("CNPJ: ");
            pj.setCnpj(scanner.nextLine());

            try {
                daoJuridica.incluir(pj);
                System.out.println("Pessoa Jurídica inserida com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao inserir Pessoa Jurídica: " + e.getMessage());
            }
        } else {
            System.out.println("Tipo de pessoa inválido.");
        }
    }

    private static void alterarPessoa(Scanner scanner, PessoaFisicaDAO daoFisica, PessoaJuridicaDAO daoJuridica) {
        scanner.nextLine(); 
        System.out.print("F - Pessoa Física | J - Pessoa Jurídica: ");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            System.out.print("Digite o ID da Pessoa Física a ser alterada: ");
            try {
                int id = scanner.nextInt();
                scanner.nextLine(); 
                PessoaFisica pf = daoFisica.getPessoa(id);
                if (pf != null) {
                    System.out.println("Dados atuais da Pessoa Física:");
                    pf.exibir();

                    System.out.println("\nDigite os novos dados (ou deixe em branco para manter o atual):");
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    if (!nome.isEmpty()) pf.setNome(nome);

                    System.out.print("Logradouro: ");
                    String logradouro = scanner.nextLine();
                    if (!logradouro.isEmpty()) pf.setLogradouro(logradouro);

                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    if (!cidade.isEmpty()) pf.setCidade(cidade);

                    System.out.print("Estado: ");
                    String estado = scanner.nextLine();
                    if (!estado.isEmpty()) pf.setEstado(estado);

                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    if (!telefone.isEmpty()) pf.setTelefone(telefone);

                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    if (!email.isEmpty()) pf.setEmail(email);

                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    if (!cpf.isEmpty()) pf.setCpf(cpf);

                    try {
                        daoFisica.alterar(pf);
                        System.out.println("Pessoa Física alterada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao alterar Pessoa Física: " + e.getMessage());
                    }
                } else {
                    System.out.println("Pessoa Física não encontrada.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: ID inválido. Digite um número inteiro.");
                scanner.next(); 
            }
        } else if (tipo.equals("J")) {
            System.out.print("Digite o ID da Pessoa Jurídica a ser alterada: ");
            try{
                int id = scanner.nextInt();
                scanner.nextLine();
                PessoaJuridica pj = daoJuridica.getPessoa(id);
                if (pj != null) {
                    System.out.println("Dados atuais da Pessoa Jurídica:");
                    pj.exibir();

                    System.out.println("\nDigite os novos dados (ou deixe em branco para manter o atual):");
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    if (!nome.isEmpty()) pj.setNome(nome);

                    System.out.print("Logradouro: ");
                    String logradouro = scanner.nextLine();
                    if (!logradouro.isEmpty()) pj.setLogradouro(logradouro);

                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    if (!cidade.isEmpty()) pj.setCidade(cidade);

                    System.out.print("Estado: ");
                    String estado = scanner.nextLine();
                    if (!estado.isEmpty()) pj.setEstado(estado);

                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    if (!telefone.isEmpty()) pj.setTelefone(telefone);

                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    if (!email.isEmpty()) pj.setEmail(email);

                    System.out.print("CNPJ: ");
                    String cnpj = scanner.nextLine();
                    if (!cnpj.isEmpty()) pj.setCnpj(cnpj);

                    try {
                        daoJuridica.alterar(pj);
                        System.out.println("Pessoa Jurídica alterada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao alterar Pessoa Jurídica: " + e.getMessage());
                    }
                } else {
                    System.out.println("Pessoa Jurídica não encontrada.");
                }
             } catch (InputMismatchException e) {
                System.out.println("Erro: ID inválido. Digite um número inteiro.");
                scanner.next(); 
            }
        } else {
            System.out.println("Tipo de pessoa inválido.");
        }
    }

    private static void excluirPessoa(Scanner scanner, PessoaFisicaDAO daoFisica, PessoaJuridicaDAO daoJuridica) {
        scanner.nextLine(); 
        System.out.print("F - Pessoa Física | J - Pessoa Jurídica: ");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            System.out.print("Digite o ID da Pessoa Física a ser excluída: ");
            try{
                int id = scanner.nextInt();
                scanner.nextLine(); 
                if (daoFisica.getPessoa(id) != null) {
                    try {
                        daoFisica.excluir(id);
                        System.out.println("Pessoa Física excluída com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao excluir Pessoa Física: " + e.getMessage());
                    }
                } else {
                    System.out.println("Pessoa Física não encontrada.");
                }
             } catch (InputMismatchException e) {
                System.out.println("Erro: ID inválido. Digite um número inteiro.");
                scanner.next(); 
            }
        } else if (tipo.equals("J")) {
            System.out.print("Digite o ID da Pessoa Jurídica a ser excluída: ");
            try{
                int id = scanner.nextInt();
                scanner.nextLine(); 
                if (daoJuridica.getPessoa(id) != null) {
                    try {
                        daoJuridica.excluir(id);
                        System.out.println("Pessoa Jurídica excluída com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao excluir Pessoa Jurídica: " + e.getMessage());
                    }
                } else {
                    System.out.println("Pessoa Jurídica não encontrada.");
                }
             } catch (InputMismatchException e) {
                System.out.println("Erro: ID inválido. Digite um número inteiro.");
                scanner.next(); 
            }
        } else {
            System.out.println("Tipo de pessoa inválido.");
        }
    }

    private static void buscarPessoaPorId(Scanner scanner, PessoaFisicaDAO daoFisica, PessoaJuridicaDAO daoJuridica) {
        scanner.nextLine(); 
        System.out.print("F - Pessoa Física | J - Pessoa Jurídica: ");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            System.out.print("Digite o ID da Pessoa Física a ser buscada: ");
            try{
                int id = scanner.nextInt();
                scanner.nextLine(); 
                PessoaFisica pf = daoFisica.getPessoa(id);
                if (pf != null) {
                    System.out.println("Dados da Pessoa Física:");
                    pf.exibir();
                } else {
                    System.out.println("Pessoa Física não encontrada.");
                }
             } catch (InputMismatchException e) {
                System.out.println("Erro: ID inválido. Digite um número inteiro.");
                scanner.next(); 
            }
        } else if (tipo.equals("J")) {
            System.out.print("Digite o ID da Pessoa Jurídica a ser buscada: ");
            try{
                int id = scanner.nextInt();
                scanner.nextLine(); 
                PessoaJuridica pj = daoJuridica.getPessoa(id);
                if (pj != null) {
                    System.out.println("Dados da Pessoa Jurídica:");
                    pj.exibir();
                } else {
                    System.out.println("Pessoa Jurídica não encontrada.");
                }
             } catch (InputMismatchException e) {
                System.out.println("Erro: ID inválido. Digite um número inteiro.");
                scanner.next(); 
            }
        } else {
            System.out.println("Tipo de pessoa inválido.");
        }
    }

    private static void exibirTodos(PessoaFisicaDAO daoFisica, PessoaJuridicaDAO daoJuridica) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("F - Pessoa Física | J - Pessoa Jurídica: ");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            List<PessoaFisica> listaPF = daoFisica.getPessoas();
            if (listaPF.isEmpty()) {
                System.out.println("Não há Pessoas Físicas cadastradas.");
            } else {
                System.out.println("=== Pessoas Físicas ===");
                for (PessoaFisica pf : listaPF) {
                    pf.exibir();
                    System.out.println("----------------------");
                }
            }
        } else if (tipo.equals("J")) {
            List<PessoaJuridica> listaPJ = daoJuridica.getPessoas();
            if (listaPJ.isEmpty()) {
                System.out.println("Não há Pessoas Jurídicas cadastradas.");
            } else {
                System.out.println("=== Pessoas Jurídicas ===");
                for (PessoaJuridica pj : listaPJ) {
                    pj.exibir();
                    System.out.println("----------------------");
                }
            }
        } else {
            System.out.println("Tipo de pessoa inválido.");
        }
        scanner.close();
    }
}

