package br.edu.ifsp.ads;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CadastroDeAluno {
    public static void main(String[] args) {

        Scanner leitorTeclado = new Scanner(System.in);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("cadastroAluno");

        EntityManager em = factory.createEntityManager();
        AlunoDAO alunoDAO = new AlunoDAO(em);



        String menu = """ 
        
        
        ** CADASTRO DE ALUNOS **

        1 - Cadastrar aluno
        2 - Excluir aluno
        3 - Alterar aluno
        4 - Buscar aluno pelo nome
        5 - Listar alunos (Status aprovação)
        6 - FIM
        
        Escolha uma opcao: 
        """;

        Boolean sairMenu = true;


        while (sairMenu){


            em.getTransaction().begin();
            System.out.println(menu);
            int opcao = leitorTeclado.nextInt();
            leitorTeclado.nextLine();

            switch(opcao){
                case 1:
                    System.out.println("\n\n1 - Cadastrar aluno\n");

                    System.out.println("Digite o nome: ");
                    String nome = leitorTeclado.nextLine();

                    System.out.println("Digite o RA: ");
                    String ra = leitorTeclado.nextLine();

                    System.out.println("Digite o Email: ");
                    String email = leitorTeclado.nextLine();

                    System.out.println("\nATENCAO\n" +
                            "UTILIZE VIRGULA PARA COLOCAR NOTAS QUEBRADAS, EXEMPLO: 5,2  8,7 .....\n");

                    System.out.println("Digete a nota 1: ");
                    BigDecimal nota1 = leitorTeclado.nextBigDecimal();

                    System.out.println("Digete a nota 2: ");
                    BigDecimal nota2 = leitorTeclado.nextBigDecimal();

                    System.out.println("Digete a nota 3: ");
                    BigDecimal nota3 = leitorTeclado.nextBigDecimal();

                    Aluno aluno = new Aluno(nome, ra, email, nota1, nota2, nota3);
                    alunoDAO.cadastrar(aluno);

                    em.getTransaction().commit();
                    System.out.println("\nAluno cadastrado!\n");
                    break;

                case 2:
                    System.out.println("\n\n2 - Excluir aluno\n");

                    System.out.println("Digite o nome: ");
                    String nomeExcluirAluno = leitorTeclado.nextLine();

                    try {
                        Aluno alunoExcluir = alunoDAO.buscarUnicoPorNome(nomeExcluirAluno);
                        alunoDAO.excluirAluno(alunoExcluir);
                        em.getTransaction().commit();
                        System.out.println("\nAluno excluido!\n");

                    } catch (NoResultException e) {
                        System.out.println("\n\nAluno nao encontrado pelo nome!\n\n");
                        em.getTransaction().rollback();

                    } catch (NonUniqueResultException e){
                        System.out.println("\n\nMais de um aluno encontrado pelo mesmo nome!");
                        em.getTransaction().rollback();
                    }


                    break;

                case 3:
                    System.out.println("\n\n3 - Alterar aluno\n");
                    System.out.println("\nATENCAO\n" +
                            "SE NAO QUISER ALTERAR UM CAMPO APENAS PRESSIONE ENTER PARA PASSAR PARA O PROXIMO CAMPO!\n");
                    System.out.println("Digite o nome: ");
                    String nomeAlunoAlterar = leitorTeclado.nextLine();

                    try {
                        Aluno alunoAlterar = alunoDAO.buscarUnicoPorNome(nomeAlunoAlterar);

                        System.out.println("\n\nDigite o novo nome: ");
                        String novoNome = leitorTeclado.nextLine();
                        if (novoNome.isBlank()){
                            novoNome = alunoAlterar.getNome();
                        }
                        alunoAlterar.setNome(novoNome);

                        System.out.println("Digite o novo RA: ");
                        String raAlterar = leitorTeclado.nextLine();
                        if (raAlterar.isBlank()){
                            raAlterar = alunoAlterar.getRa();
                        }
                        alunoAlterar.setRa(raAlterar);

                        System.out.println("Digite o novo Email: ");
                        String emailAlterar = leitorTeclado.nextLine();
                        if (emailAlterar.isBlank()){
                            emailAlterar = alunoAlterar.getEmail();
                        }
                        alunoAlterar.setEmail(emailAlterar);


                        BigDecimal nota1Alterar = lerBigdecimal(leitorTeclado, "Digite a nova nota 1: ", alunoAlterar.getNota1());
                        alunoAlterar.setNota1(nota1Alterar);


                        BigDecimal nota2Alterar = lerBigdecimal(leitorTeclado, "Digite a nova nota 2: ", alunoAlterar.getNota2());
                        alunoAlterar.setNota2(nota2Alterar);


                        BigDecimal nota3Alterar = lerBigdecimal(leitorTeclado, "Digite a nova nota 3: ", alunoAlterar.getNota3());
                        alunoAlterar.setNota3(nota3Alterar);


                        em.getTransaction().commit();
                        System.out.println("\nAluno alterado!\n");
                        System.out.println("------------------------------------------------------");
                        System.out.println("Novo nome: " + alunoAlterar.getNome());
                        System.out.println("Novo RA: " + alunoAlterar.getRa());
                        System.out.println("Novo email: " + alunoAlterar.getEmail());
                        System.out.println("Nova nota 1: " + alunoAlterar.getNota1());
                        System.out.println("Nova nota 2: " + alunoAlterar.getNota2());
                        System.out.println("Nova nota 3: " + alunoAlterar.getNota3());
                        System.out.println("------------------------------------------------------");


                    } catch (NoResultException e) {
                        System.out.println("\n\nAluno nao encontrado pelo nome!\n\n");
                        em.getTransaction().rollback();

                    } catch (NonUniqueResultException e){
                        System.out.println("\n\nMais de um aluno encontrado pelo mesmo nome!");
                        em.getTransaction().rollback();
                    }



                    break;

                case 4:
                    System.out.println("\n\n4 - Buscar aluno pelo nome\n");
                    System.out.println("Digite o nome: ");
                    String alunoPorNome = leitorTeclado.nextLine();

                    try {
                        Aluno alunoExibir = alunoDAO.buscarUnicoPorNome(alunoPorNome);
                        System.out.println("\n\nNome: " + alunoExibir.getNome());
                        System.out.println("Email: " + alunoExibir.getEmail());
                        System.out.println("RA: " + alunoExibir.getRa());
                        System.out.println("Notas: \n" +
                                "Nota 1: " + alunoExibir.getNota1() +
                                "\nNota 2: " + alunoExibir.getNota2() +
                                "\nNota 3: " + alunoExibir.getNota3());

                        em.getTransaction().commit();

                    } catch (NoResultException e) {
                        System.out.println("\n\nAluno nao encontrado pelo nome!\n\n");
                        em.getTransaction().rollback();

                    } catch (NonUniqueResultException e){
                        System.out.println("\n\nMais de um aluno encontrado pelo mesmo nome!");
                        em.getTransaction().rollback();
                    }

                    break;
                case 5:
                    List<Aluno> alunos = alunoDAO.listarAlunos();
                    System.out.println("\n\n5 - Exibindo todos os alunos:\n");
                    for ( Aluno alunoListado : alunos ){
                        System.out.println("Nome: " + alunoListado.getNome());
                        System.out.println("Email: " + alunoListado.getEmail());
                        System.out.println("Ra: " + alunoListado.getRa());
                        System.out.println("Notas: \n" +
                                "Nota 1: " + alunoListado.getNota1() +
                                "\nNota 2: " + alunoListado.getNota2() +
                                "\nNota 3: " + alunoListado.getNota3());

                        BigDecimal media = alunoListado.getNota1().add(alunoListado.getNota2().add(alunoListado.getNota3())).divide(new BigDecimal("3"), 2, 2);
                        System.out.println("Media: " + media);

                        if(media.compareTo(new BigDecimal("4")) < 0){
                            System.out.println("Situação: Reprovado\n");
                        }else if(media.compareTo(new BigDecimal("6")) >= 0){
                            System.out.println("Situação: Aprovado\n");
                        }else{
                            System.out.println("Situação: Recuperação\n");
                        }
                    }

                    em.getTransaction().commit();

                    break;
                default:
                    em.close();
                    sairMenu = false;
                    break;
            }
        }
    }

    public static BigDecimal lerBigdecimal(Scanner scanner, String mensagem, BigDecimal nota){
        System.out.println(mensagem);
        String novaNota = scanner.nextLine().trim();
        if (!novaNota.isBlank()){
            try {
                return new BigDecimal(novaNota.replace(',', '.'));
            }catch (NumberFormatException e){
                System.out.println("Numero invalido");
            }
        }
        return nota;
    }
}
