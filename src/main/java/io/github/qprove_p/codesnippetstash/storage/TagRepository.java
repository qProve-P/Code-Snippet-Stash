package io.github.qprove_p.codesnippetstash.storage;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.data.Tag;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class TagRepository {

    public List<Tag> findAll() {

        try(EntityManager em = JPAConnector.getEntityManager()) {
            log.info("Get all tags");
            return em.createQuery("select t from Tag t", Tag.class).getResultList();
        }
    }

    public List<Tag> getAllDistinct() {

        try(EntityManager em = JPAConnector.getEntityManager()) {
            log.info("Get all distinct tags");
            return em.createQuery("select distinct t from Tag t", Tag.class).getResultList();
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

            List<Snippet> snippets = new SnippetRepository().findByTag(tag.getName());
            for(Snippet snippet : snippets) {
                snippet.getTags().remove(tag);
                em.merge(snippet);
            }

            Tag managedTag = em.find(Tag.class, tag.getId());
            if(managedTag != null) {
                em.remove(managedTag);
            }

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
