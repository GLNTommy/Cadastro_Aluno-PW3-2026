package br.edu.ifsp.ads;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.Scanner;

public class CadastroDeAluno {
    public static void main(String[] args) {

        Scanner leitorTeclado = new Scanner(System.in);

        String menu = """
        ** CADASTRO DE ALUNOS **

        1 - Cadastrar aluno
        2 - Excluir aluno
        3 - Alterar aluno
        4 - Buscar aluno pelo nome
        5 - Listar alunos (Status aprovação)
        6 - FIM
        """;

        Boolean sairMenu = true;

        while (sairMenu){
            System.out.println(menu);
            int opcao = leitorTeclado.nextInt();

            switch(opcao){
                case 1:
                    System.out.println("1 - Cadastrar aluno");

                    System.out.println("\n Digite o nome: ");
                    String nome = leitorTeclado.nextLine();

                    System.out.println("\n Digite o RA: ");
                    String ra = leitorTeclado.nextLine();

                    System.out.println("\n Digite o Email: ");
                    String email = leitorTeclado.nextLine();

                    System.out.println("\n Digete a nota 1: ");
                    BigDecimal nota1 = leitorTeclado.nextBigDecimal();

                    System.out.println("\n Digete a nota 2: ");
                    BigDecimal nota2 = leitorTeclado.nextBigDecimal();

                    System.out.println("\n Digete a nota 3: ");
                    BigDecimal nota3 = leitorTeclado.nextBigDecimal();

                    Aluno aluno = new Aluno(nome, ra, email, nota1, nota2, nota3);
                    break;
                case 2:
                    System.out.println("2 - Excluir aluno");
                    break;
                case 3:
                    System.out.println("3 - Alterar aluno");
                    break;
                case 4:
                    System.out.println("4 - Buscar aluno pelo nome");
                    break;
                case 5:
                    System.out.println("5 - Listar alunos (Status aprovação)");
                    break;
                default:
                    sairMenu = false;
                    break;
            }
        }

    }
}
