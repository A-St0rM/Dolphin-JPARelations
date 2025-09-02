package app.DAO;

import app.DTO.NoteInfoDTO;
import app.entities.Note;
import app.entities.Person;
import jakarta.persistence.*;

import java.io.NotActiveException;
import java.time.LocalDate;
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

    //US-2: As an administrator I would like to be able to get the total amount paid for a given person.
    public int getTotalPaidUpToToday(int personId) {
        EntityManager em = emf.createEntityManager();
        try {
            LocalDate today = LocalDate.now();
            Long sum = em.createQuery(
                            "SELECT SUM(f.amount) " +
                                    "FROM Fee f " +
                                    "WHERE f.person.id = :pid " +
                                    "  AND f.payDate IS NOT NULL " +
                                    "  AND f.payDate <= :today", Long.class)
                    .setParameter("pid", personId)
                    .setParameter("today", today)
                    .getSingleResult();
            return sum.intValue(); // amount is int → SUM returns Long
        } finally {
            em.close();
        }
    }

    //US-3: As an administrator I would like to be able to get a list of all notes for a given person
    public List<Note> GetAllNotes(int personId){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Note> query = em.createQuery("SELECT n FROM Note n WHERE n.person.id = :personId", Note.class)
                    .setParameter("personId", personId);
            return query.getResultList();
        }
        finally {
            em.close();
        }
    }

    //US-4: As an administrator I would like to get a list of all notes with the name and age of the person it belongs to.
    public List<NoteInfoDTO> AllNotesWithNameAndAge(){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT new app.DTO.NoteInfoDTO(n.text, p.name, pd.age) " +
                    "FROM Note n " +
                    "JOIN n.person p " +
                    "JOIN p.personDetail pd", NoteInfoDTO.class)
                    .getResultList();
        }
        finally {
            em.close();
        }
    }
}
