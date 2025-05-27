package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.Observer;
import jakarta.persistence.EntityManager;

public class DatabaseObserver implements Observer {
    @Override
    public void update(DiskRun run) {
        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();
    }
}
