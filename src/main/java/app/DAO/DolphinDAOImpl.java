package app.DAO;

import app.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DolphinDAOImpl implements DolphinDAO {

    private final EntityManagerFactory emf;

    public DolphinDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Person create(Person person) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(person);            // CascadeType.ALL vil persiste detail/fees/notes
            em.getTransaction().commit();
            return person;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Person findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p", Person.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person p = em.find(Person.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
