package io.github.qprove_p.codesnippetstash.storage;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class SnippetRepository {

    public List<Snippet> findAll() {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            log.info("Get all snippets");
            return em.createQuery("select s from Snippet s", Snippet.class).getResultList();
        }finally {
            em.close();
        }
    }

    public List<Snippet> findByTag(String tag) {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            log.info("Get all snippets by tag");
            return em.createQuery("select s from Snippet s join s.tags t where t.name = :tag", Snippet.class)
                    .setParameter("tag", tag)
                    .getResultList();
        }finally {
            em.close();
        }
    }

    public void save(Snippet snippet) {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(snippet);
            em.getTransaction().commit();
            log.info("Create new snippet");
        }catch(Exception e) {
            em.getTransaction().rollback();
            log.error("Can't create snippet: ", e);
            throw e;
        }finally {
            em.close();
        }
    }

    public void delete(Snippet snippet) {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.merge(snippet));
            em.getTransaction().commit();
            log.info("Delete snippet");
        }catch(Exception e) {
            em.getTransaction().rollback();
            log.error("Can't delete snippet: ", e);
            throw e;
        }finally {
            em.close();
        }
    }
}
