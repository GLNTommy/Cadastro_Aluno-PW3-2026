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
                    System.out.println("1 - Cadastrar aluno");

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
                    System.out.println("2 - Excluir aluno");

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
                        System.out.println("Faça uma alteracao em um dos nomes: " + nomeExcluirAluno + "\nPara prosseguir com a exclusao\n\n");
                        em.getTransaction().rollback();
                    }


                    break;

                case 3:
                    System.out.println("3 - Alterar aluno");
                    em.getTransaction().commit();

                    break;

                case 4:
                    System.out.println("4 - Buscar aluno pelo nome");
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
                        System.out.println("Faça uma alteracao em um dos nomes: " + alunoPorNome + "\nPara prosseguir com a pesquisa\n\n");
                        em.getTransaction().rollback();
                    }

                    break;
                case 5:
                    List<Aluno> alunos = alunoDAO.listarAlunos();
                    System.out.println("\n\nExibindo todos os alunos:\n");
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
}
