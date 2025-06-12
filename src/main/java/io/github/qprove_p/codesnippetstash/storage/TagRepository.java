package io.github.qprove_p.codesnippetstash.storage;

import io.github.qprove_p.codesnippetstash.data.Tag;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class TagRepository {

    public List<Tag> findAll() {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            log.info("Get all tags");
            return em.createQuery("select t from Tag t", Tag.class).getResultList();
        }finally {
            em.close();
        }
    }

    public List<Tag> getAllDistinct() {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            log.info("Get all distinct tags");
            return em.createQuery("select distinct t from Tag t", Tag.class).getResultList();
        }finally {
            em.close();
        }
    }

    public void save(Tag tag) {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(tag);
            em.getTransaction().commit();
            log.info("Create new tag");
        }catch(Exception e) {
            em.getTransaction().rollback();
            log.error("Can't create tag: ", e);
            throw e;
        }finally {
            em.close();
        }
    }

    public void delete(Tag tag) {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.merge(tag));
            em.getTransaction().commit();
            log.info("Delete tag");
        }catch(Exception e) {
            em.getTransaction().rollback();
            log.error("Can't delete tag: ", e);
            throw e;
        }finally {
            em.close();
        }
    }
}
