package io.github.qprove_p.codesnippetstash.storage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

@Getter
public class JPAConnector {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("codesnippetstash");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void stop() {
        if(emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
