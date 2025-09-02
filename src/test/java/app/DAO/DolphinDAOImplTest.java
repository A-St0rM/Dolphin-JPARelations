package app.DAO;

import app.DAO.populator.PersonPopulator;
import app.config.HibernateConfig;
import app.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DolphinDAOImplTest {
    private static Person p1;
    private static Person p2;
    private static Person p3;

    private static EntityManagerFactory emf;
    private static DolphinDAOImpl dolphinDAO;


    @BeforeAll
    static void setUp() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactory();
        dolphinDAO = new DolphinDAOImpl(emf);
    }

    @BeforeEach
    void cleanDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Person").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();

            Person[] testPerson = PersonPopulator.getPopulator(dolphinDAO);
            p1 = testPerson[0];
            p2 = testPerson[1];
            p3 = testPerson[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void create() {
        Person person = new Person("Cille");
        Person persistPerson = dolphinDAO.create(person);
        assertNotNull(persistPerson);
        assertEquals(person.getName(), persistPerson.getName());
    }

    @Test
    void findById() {
        Person foundPerson = dolphinDAO.findById(p2.getId());
        assertEquals("Morten", foundPerson.getName());
    }

    @Test
    void findAll() {
        List<Person> personList = dolphinDAO.findAll();
        assertEquals(3, personList.size());
    }

    @Test
    void deleteById() {

        dolphinDAO.deleteById(p1.getId());

        Person found = dolphinDAO.findById(p1.getId());
        assertNull(found, "Person should be deleted from the database");
    }
}