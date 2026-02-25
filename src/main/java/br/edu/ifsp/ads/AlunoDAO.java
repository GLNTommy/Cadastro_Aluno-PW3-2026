package br.edu.ifsp.ads;
import jakarta.persistence.EntityManager;
public class AlunoDAO {
    private EntityManager em;

    public AlunoDAO(EntityManager em) {
        this.em = em;
    }

}
