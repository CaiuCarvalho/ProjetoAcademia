package com.caio;
import com.caio.dao.*;
import com.caio.modelos.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App 
{
    static ProfessorDAO pDao = new ProfessorDAO();
    static AlunoDAO aDao = new AlunoDAO();
    static PessoaDAO pessoaDAO = new PessoaDAO();
    static TreinoDAO tDao = new TreinoDAO();
    static UnidadeDAO uDao = new UnidadeDAO();

    static Scanner leitor = new Scanner(System.in);

    //variavel que determina se tem um professor cadastrado para uniciar o sistema
    static boolean prof_logado = false;
    public static void main( String[] args )
    {
        //apagar antes de passar pra frente
        // Professor p1 = new Professor("caio", 20, "caioadmin", "1234");
        // pDao.salvarProfessor(p1);

        validarPrimeiroUsoOuMenu();

    }

    public static void validarPrimeiroUsoOuMenu(){
        
        if (!prof_logado) {
            primeiroUso();
        } else {
            chamarMenu();
        }
    }

    public static void primeiroUso() {
        int opcao;

        do {
            System.out.println("- - - - - - - - Seja Bem-Vindo! - - - - - - - - ");

            if (!verificarSeExistemProfessoresCadastrados()) {
                System.out.println("Nenhum professor cadastrado!");
                System.out.println("Para utilizar o sistema você precisa cadastrar um professor...");
                System.out.println("0 - Encerrar programa");
                System.out.println("1 - Cadastrar professor (Admin)");
            } else {
                System.out.println("0 - Encerrar programa");
                System.out.println("1 - Cadastrar professor (Admin)");
                System.out.println("2 - Fazer login (professor)");
            }
            try {
                opcao = leitor.nextInt();
                leitor.nextLine();

                switch (opcao) {
                    case 0:
                        System.out.println("Programa Finalizado!");
                        return;
                    case 1:
                        adicionarNovoProfessor();
                        break;
                    case 2:
                        logarProfessor();
                            validarPrimeiroUsoOuMenu();
                        break;
                    default:
                        System.out.println("Opção Incorreta");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção Inválida. Tente Novamente.");
                leitor.nextLine();
                opcao = -1;
            }

        } while (!prof_logado);
    }

    public static void chamarMenu() {
        int opcao;

        do {
            System.out.println("Menu");
            System.out.println("0 - Encerrar programa.");
            System.out.println("1 - Cadastrar novo professor.");
            System.out.println("2 - Fazer login (professor).");
            System.out.println("3 - Deslogar professor.");
            System.out.println("4 - Cadastrar Aluno.");
            System.out.println("5 - Indiciar Professor para um Aluno.");
            System.out.println("6 - Cadastrar Treino.");
            System.out.println("7 - Indicar Treino a Aluno.");
            System.out.println("8 - Cadastrar Nova Unidade.");
            System.out.println("9 - Indicar Unidade a Pessoa(Aluno/Professor).");
            System.out.println("10 - Apresentar todas as estatisticas do Banco de Dados.");
            System.out.println("11 - Apresentar estatísticas de um Aluno ou Professor por ID.");
            System.out.println("12 - ....");
            System.out.println("13 - Limpar Banco de Dados.");

            try {
                opcao = leitor.nextInt();
                leitor.nextLine();

                switch (opcao) {
                    case 0:
                        System.out.println("Programa Finalizado!");
                        break;
                    case 1:
                        adicionarNovoProfessor();
                        break;
                    case 2:
                        logarProfessor();
                        break;
                    case 3:
                        deslogarProfessor();
                            validarPrimeiroUsoOuMenu();
                        break;
                    case 4:
                        cadastrarAluno();
                        break;
                    case 5:
                        indicarProfessorAoAluno();
                        break;
                    case 6:
                        cadastrarTreino();
                        break;
                    case 7:
                        indicarTreinoAAluno();
                        break;
                    case 8:
                        cadastrarUnidade();
                        break;
                    case 9:
                        indicarUnidadeAPessoa();
                        break;
                    case 10:
                        mostrarEstatisticas();
                        break;
                    case 11:
                        mostrarEstatisticasPessoa();
                        break;
                    case 13:
                        limparBancoDeDados();
                        if (!prof_logado) {
                            validarPrimeiroUsoOuMenu();
                        }
                        break;
                    default:
                        System.out.println("Opção Incorreta");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção Inválida. Tente Novamente.");
                leitor.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    public static void mostrarEstatisticas() {
    int totalAlunos = contarAlunosCadastrados();
    int totalProfessores = contarProfessoresCadastrados();
    int totalUnidades = contarUnidadesCadastradas();
    int totalTreinos = contarTreinosCadastrados();

    System.out.println("- - - - - - - - Estatísticas do Sistema - - - - - - - -");
    System.out.println("Total de Alunos cadastrados: " + totalAlunos);
    System.out.println("Total de Professores cadastrados: " + totalProfessores);
    System.out.println("Total de Unidades cadastradas: " + totalUnidades);
    System.out.println("Total de Treinos cadastrados: " + totalTreinos);

    System.out.println("Relacionamentos entre Alunos e Professores:");
    listarRelacionamentosAlunosATodasAsEntidades();
    System.out.println("Relacionamentos entre Unidades e Alunos/Professores: ");
    listarRelacionamentoAlunosProfessoresUnidades();
    System.out.println("Relacionamentos entre Treinos e Alunos: ");
    listarRelacionamentoAlunosTreinos();
    }

    public static void mostrarEstatisticasPessoa(){
        
        if (verificarSeExistemAlunosCadastrados()) {

            System.out.println("Apresentar estatísticas de... ");
            System.out.println("1 - Professor | 2 - Aluno: ");
            int opcao = leitor.nextInt();
            leitor.nextLine();

                switch (opcao) {
                    case 1:
                        apresentarEstatisticasProfessorPorID();
                        break;
                    case 2:
                        apresentarEstatisticasAlunoPorID();
                        break; 
                    default:
                        System.out.println("Opção digitada Incorreta!");
                        break;
                } 
        } 
        
    }

    public static void apresentarEstatisticasAlunoOuProfessorPorID(){
        System.out.println("Deseja consultar as estatísticas de...");
        System.out.println("1 - Professor | 2 - Aluno");
        int opcao = leitor.nextInt();
        leitor.nextLine();

        switch (opcao) {
            case 1:
                apresentarEstatisticasProfessorPorID();
                break;
            case 2:
                apresentarEstatisticasAlunoPorID();
                break;
            default:
                System.out.println("Opção Invalida. Tente Novamente!");
                break;
        }
    }

    public static void apresentarEstatisticasAlunoPorID(){
        System.out.println("Informe o ID do Aluno que deseja consultar: ");
        apresentarAlunoComID();
        Long iDAluno = leitor.nextLong();
        leitor.nextLine();
        Aluno aluno = aDao.buscarAlunoPorId(iDAluno);

        if (aluno != null) {
            System.out.println("Aluno: " + aluno.getNome() + " | Professor: " + aluno.getProfessor().getNome() + " | Unidade: " + aluno.getUnidade().getDescricao() + " | Objetivo: " + aluno.getTreino().getObjetivo());
        } else {
            System.out.println("Aluno não encontrado!");
        }

    }

    public static void apresentarEstatisticasProfessorPorID(){
        System.out.println("Informe o ID do Professor que deseja consultar: ");
        percorreEApresentaListaProfessor();
        Long iDProf = leitor.nextLong();
        leitor.nextLine();
        Professor professor = pDao.buscarProfessorPorId(iDProf);

        if (professor != null) {
            List<Aluno> listaAlunos = aDao.buscarTodosAlunos();

            System.out.println("Professor: " + professor.getNome() + " | Unidade: " + professor.getUnidade().getDescricao());
            
            if (!listaAlunos.isEmpty()) {  
                for (Aluno aluno : listaAlunos) {
                    System.out.println(" | Aluno: " + aluno.getNome());
                }
            } else {
                System.out.println(" | Alunos: Nenhum");
            }
        } else {
            System.out.println("Professor não encontrado. Tente Novamente!");
        }

    }

    public static int contarAlunosCadastrados() {
        return aDao.buscarTodosAlunos().size();
    }

    public static int contarProfessoresCadastrados() {
        return pDao.buscarTodosProfessores().size();
    }

    public static int contarUnidadesCadastradas() {
        return uDao.buscarTodosUnidades().size();
    }

    public static int contarTreinosCadastrados() {
        return tDao.buscarTodosTreinos().size();
    }

    public static void apresentarProfessorComID(){
        List<Professor> listaProfessor = pDao.buscarTodosProfessores();

        for (Professor professor : listaProfessor) {
          if (professor != null) {
            System.out.println("\n ID: " + professor.getId() + "| Nome: " + professor.getNome());
          }  
        }
    }

    public static void apresentarAlunoComID(){
        List<Aluno> listaAlunos = aDao.buscarTodosAlunos();

        for (Aluno aluno : listaAlunos) 
            if (aluno != null) {
                System.out.println("\nID: " + aluno.getId() + "| Aluno: " + aluno.getNome());
            }
        }

    public static void listarRelacionamentosAlunosATodasAsEntidades() {
        List<Aluno> listaAlunos = aDao.buscarTodosAlunos();
        List<Unidade> listaUnidades = uDao.buscarTodosUnidades();
        List<Treino> listaTreinos = tDao.buscarTodosTreinos();

        for (Aluno aluno : listaAlunos) {
            Professor professor = aluno.getProfessor();
            Unidade unidade = aluno.getUnidade();
            Treino treino = aluno.getTreino();

            if (professor != null) {
                System.out.println("Aluno: " + aluno.getNome() + " | Professor: " + professor.getNome() + " | Unidade: " + unidade.getDescricao() + " | Treino: " + treino.getObjetivo());
            }
        }
    }

    public static void listarRelacionamentoAlunosTreinos() {
    List<Aluno> listaAlunos = aDao.buscarTodosAlunos();

        for (Aluno aluno : listaAlunos) {
            Treino treino = aluno.getTreino();
            if (treino != null) {
                System.out.println("Aluno: " + aluno.getNome() + " | Treino (Objetivo): " + treino.getObjetivo());
            }
        }
    }

    public static void listarRelacionamentoAlunosProfessoresUnidades() {
        List<Aluno> alunos = aDao.buscarTodosAlunos();
        List<Professor> professores = pDao.buscarTodosProfessores();
        List<Unidade> unidades = uDao.buscarTodosUnidades();

        System.out.println("Relacionamento Alunos e Professores com as Unidades:\n");

        System.out.println("Alunos:");
        for (Aluno aluno : alunos) {
            String nomeAluno = aluno.getNome();
            String nomeUnidade = aluno.getUnidade() != null ? aluno.getUnidade().getDescricao() : "N/A";
            System.out.println("Aluno: " + nomeAluno + ", Unidade: " + nomeUnidade);
        }

        System.out.println("\nProfessores:");
        for (Professor professor : professores) {
            String nomeProfessor = professor.getNome();
            String nomeUnidade = professor.getUnidade() != null ? professor.getUnidade().getDescricao() : "N/A";
            System.out.println("Professor: " + nomeProfessor + ", Unidade: " + nomeUnidade);
        }
    }

    public static boolean verificarSeExistemProfessoresCadastrados() {
        return !pDao.buscarTodosProfessores().isEmpty();
    }

    public static boolean verificarSeExistemAlunosCadastrados() {
        return !aDao.buscarTodosAlunos().isEmpty();
    }

    public static boolean verificarSeExistemTreinosCadastrados(){
        return !tDao.buscarTodosTreinos().isEmpty();
    }

    public static boolean verificarSeExistemUnidadesCadastradas(){
        return !uDao.buscarTodosUnidades().isEmpty();
    }

    public static void indicarUnidadeAPessoa(){ 
        if (verificarSeExistemUnidadesCadastradas()) {
            System.out.println("Voce deseja vincular..\nDigete a opção que deseja:");
            System.out.println("1 - Professor | 2 - Aluno");
            int opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    vincularUnidadeAProfessor();
                    break;
                case 2:
                    vincularUnidadeAAluno();
                    break; 
                default:
                    System.out.println("Opção digitada Incorreta!");
                    break;
            } 
        }        
    }

    public static void vincularUnidadeAProfessor(){
            System.out.println("Informe o nome do Professor que deseja vincular: ");
            String nomeProf = leitor.nextLine();
            Professor professor = pDao.buscarProfessorPorNome(nomeProf);
            if (professor != null) {
                System.out.println("Selecione a UNIDADE que deseja vincula-lo: ");
                percorreEApresentaListaUnidades();
                Long iDUnidade = leitor.nextLong();
                leitor.nextLine();
                Unidade unidade = uDao.buscarUnidadePorId(iDUnidade);

                if (unidade != null) {
                    professor.setUnidade(unidade);
                    pDao.atualizarProfessor(professor);
                    System.out.println("Unidade Vinculada com Sucesso!");
                } else {
                    System.out.println("Unidade não Encontrada. Tente Novamente!");
                }
            } else {
                System.out.println("Professor não encontrado! Tente Novamente.");
            }
    }

    public static void vincularUnidadeAAluno(){
        System.out.println("Informe o nome do Aluno que deseja vincular: ");
        String nomeAluno = leitor.nextLine();

        Aluno aluno = aDao.buscarAlunoPorNome(nomeAluno);
        if(aluno != null) {
            System.out.println("Informe o ID da unidade que deseja vincular: ");
            percorreEApresentaListaUnidades();
            Long iDUnidade = leitor.nextLong();
            leitor.nextLine();
            Unidade unidade = uDao.buscarUnidadePorId(iDUnidade);
            if (unidade != null) {
                aluno.setUnidade(unidade);
                aDao.atualizarAluno(aluno);
                System.out.println("Unidade Vinculada com Sucesso!");
            } else {
                System.out.println("Unidade não encotnrada! Tente Novamente.");
            }
        } else {
            System.out.println("Aluno não encontrado. Twente Novamente!");
        }
    }

    public static void percorreEApresentaListaUnidades() {
        List<Unidade> listaUnidades = uDao.buscarTodosUnidades();
        for (Unidade unidade : listaUnidades) {
            System.out.println("ID: " + unidade.getId_unidade() + ", Descrição: " + unidade.getDescricao() + ".");
        }
    }

    public static void percorreEApresentaListaProfessor(){
        List<Professor> listaProfessores = pDao.buscarTodosProfessores();
        for (Professor professor : listaProfessores) {
            System.out.println("ID: " + professor.getId() + ", Nome: "+ professor.getNome());
        }
    }

    public static void percorreEApresentaListaAlunos(){
        List<Aluno> listaAlunos = aDao.buscarTodosAlunos();
        for (Aluno aluno : listaAlunos) {
            System.out.println("ID: " + aluno.getId() + ", Nome: "+ aluno.getNome());
        }
    }

    public static void indicarTreinoAAluno(){ 
        if (verificarSeExistemTreinosCadastrados()) {
            System.out.println("Informe o Aluno que deseja indicar o Treino: ");
            String nomeAluno = leitor.nextLine();
            Aluno aluno = aDao.buscarAlunoPorNome(nomeAluno);
            if (aluno != null) {
                System.out.println("Selecione qual treino deseja vincular ao aluno: ");
                percorreEApresentaListaTreino();
                long iDTreino = leitor.nextLong();
                leitor.nextLine();
                Treino treino = tDao.buscarTreinoPorId(iDTreino);

                if (treino != null) {
                    aluno.setTreino(treino);
                    aDao.atualizarAluno(aluno);
                    System.out.println("Treino Vinculado com Sucesso!");
                } else {
                    System.out.println("Treino não encontrado. Tente Novamente.");
                }
            } else {
                System.out.println("Aluno não encontrado. Tente Novamente!");
            }
        } else {
            System.out.println("Nenhum Aluno Cadastrado. Cadastre ao menos um para poder acessar a função!");
        }
    }

    public static void percorreEApresentaListaTreino(){
        List<Treino> listaTreino = tDao.buscarTodosTreinos();
        for (Treino treino : listaTreino) {
            System.out.println("ID: " + treino.getId() + ", Objetivo: "+ treino.getObjetivo());
        }
    }

    public static void indicarProfessorAoAluno(){
        if (verificarSeExistemAlunosCadastrados()) {
            System.out.println("Informe o nome do Aluno: ");
            String nomeDoAluno = leitor.nextLine();
            Aluno aluno = aDao.buscarAlunoPorNome(nomeDoAluno);
            if (aluno != null) {
                System.out.println("Selecione o ID do professor que deseja vincular ao aluno: ");
                percorreEApresentaListaProfessor();
                Long iDProfessor = leitor.nextLong();
                leitor.nextLine();
                Professor professor = pDao.buscarProfessorPorId(iDProfessor);
                
                if (professor != null) {
                    aluno.setProfessor(professor);
                    aDao.atualizarAluno(aluno);
                    System.out.println("Professor Vinculado com Sucesso! ");
                } else {
                    System.out.println("Professor não encontrado. Tente novamente.");
                }
            } else {
                System.out.println("Aluno não encontrado. Tente novamente.");
            }                
        } else {
            System.out.println("Nenhum Aluno cadastrado. Cadastre um aluno para poder usar a função!");
        }       
    }

    public static int contarPessoasCadastradas(){
        int totPessoas = pessoaDAO.buscarTodasPessoas().size();
        return totPessoas;
    }

    public static void cadastrarAluno(){
        System.out.println("Informe o nome do Aluno: ");
        String nome = leitor.nextLine();
        if (nome.length() >= 2 && nome.length() <= 20) {
            System.out.println("Informe a idade do Aluno: ");
            int idade = leitor.nextInt();
            leitor.nextLine();
            if (idade >= 4 && idade <= 150) {
                int matricula = contarPessoasCadastradas() + 1;
                System.out.printf("Matricúla número:  "+ matricula + ".");
                Aluno aluno = new Aluno(nome, idade, matricula);
                aDao.salvarAluno(aluno);
            } else {
                System.out.println("Para poder se cadastrar o Aluno deve ter entre 4 e 150 anos!");
            }
        } else {
            System.out.println("O Nome do Aluno deve conter entre 2 e 20 caracteres!");
        }
    }

    public static void cadastrarUnidade(){
        System.out.println("Informe a Descrição (Endereço) que deseja cadastrar: ");
        String descUnidade = leitor.nextLine();
        if (descUnidade.length() >= 2 && descUnidade.length() <= 30) {
            Unidade unidade = new Unidade(descUnidade);
            uDao.salvarUnidade(unidade);
        } else {
            System.out.println("A Descrição deve conter entre 2 a 30 caracteres!");
        }
    }

    public static void cadastrarTreino(){
        System.out.println("Informe o objetivo do Treino: ");
        String objetivo = leitor.nextLine();

        Treino treino = new Treino(objetivo);
        tDao.salvarTreino(treino);

        System.out.println("Treino cadastrado com Sucesso!");
    }

    public static void adicionarNovoProfessor() {
        System.out.println("Informe o nome do professor: ");
        String nome = leitor.nextLine();
        System.out.println("Informe a idade do professor: ");
        int idade = leitor.nextInt();
        leitor.nextLine();

        if (nome.length() >= 2 && nome.length() <= 20) {
            if (idade >= 10 && idade <= 150) {

                System.out.println("Informe o login desejado: ");
                String login = leitor.nextLine();
                if (login.length() >= 4 && login.length() <= 15) {
                    List<Professor> listaProfessores = pDao.buscarTodosProfessores();

                    boolean prof_ja_cadastrado = false;

                    for (Professor prof : listaProfessores) {
                        String logBd = prof.getLogin();
                        if (logBd.equals(login)) {
                            System.out.println("Login já cadastrado!");
                            prof_ja_cadastrado = true;
                            break;
                        }
                    }

                    if (!prof_ja_cadastrado) {
                        System.out.println("Informe a senha: ");
                        String senha = leitor.nextLine();
                        if (senha.length() >= 3 && senha.length() <= 20) {
                            Professor professor = new Professor(nome, idade, login, senha);
                            pDao.salvarProfessor(professor);
                            System.out.println("Professor Cadastrado com Sucesso!");
                        } else {
                            System.out.println("A senha deve conter entre 3 e 20 caracteres!");
                        }  
                    }
                } else {
                    System.out.println("O Login deve conter entre 4 e 15 caracteres!");
                }               
            } else {
                System.out.println("A Idade deve ser entre 10 a 150 anos. Tente novamente");
            }
        } else {
            System.out.println("O Nome deve conter entre 2 e 20 caracteres. Tente Novamente!");
        }
    }

    public static boolean logarProfessor(){

        boolean professor_logado = false;

        System.out.println("Informe o login do professor: ");
        String login = leitor.nextLine();
        System.out.println("Informe a senha: ");
        String senha = leitor.nextLine();

        if (pDao.validandoCredenciaisProfessor(login, senha)) {
            professor_logado = true;
            prof_logado = true;
            System.out.println("Login realizado com Sucesso!");
        } else {
            System.out.println("Login ou senha inválidos. Tente Novamente.");
            return professor_logado = false;
        }
        return professor_logado;
    }

    public static void deslogarProfessor(){
        System.out.println("Informe o login do professor: ");
        String login = leitor.nextLine();
        System.out.println("Informe a senha: ");
        String senha = leitor.nextLine();

        if (pDao.validandoCredenciaisProfessor(login, senha)) {
            prof_logado = false;
            System.out.println("Credenciais corretas! Deslogando..");
        } else {
            System.out.println("Login ou senha inválidos. Tente Novamente.");
            return;
        }
    }

    public static void limparBancoDeDados(){
        
        System.out.println("Informe o login do professor: ");
        String login = leitor.nextLine();
        System.out.println("Informe a senha: ");
        String senha = leitor.nextLine();
        System.out.println("Tem certeza? se deletados os dados não poderão ser recuperados! (s/n)");
        String confirmacao = leitor.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            if (pDao.validandoCredenciaisProfessor(login, senha)) {
                System.out.println("Credenciais corretas! Deletando..");
                pessoaDAO.limparBancoDeDados();
                prof_logado = false;
                validarPrimeiroUsoOuMenu();
            } else {
                System.out.println("Login ou senha inválidos. Tente Novamente.");
            }
        } else {
            validarPrimeiroUsoOuMenu();
        }
    }
}
