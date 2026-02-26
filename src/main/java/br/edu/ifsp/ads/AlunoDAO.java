package br.edu.ifsp.ads;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

public class AlunoDAO {
    private EntityManager em;

    public AlunoDAO(EntityManager em){
        this.em = em;
    }

    public void cadastrar(Aluno aluno){
        this.em.persist(aluno);
    }

    public Aluno buscarUnicoPorNome(String nome) throws NoResultException, NonUniqueResultException{
        String jpql = "SELECT a FROM Aluno a WHERE a.nome = :n";
        return em.createQuery(jpql, Aluno.class)
                .setParameter("n", nome).getSingleResult();
    }

    public void excluirAluno(Aluno aluno){
        em.remove(aluno);
    }

}
