package app;

import app.config.HibernateConfig;
import app.entities.Fee;
import app.entities.Note;
import app.entities.Person;
import app.entities.PersonDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            Person p1 = new Person("Alissa");
            PersonDetail personDetail = new PersonDetail("Islev have 10", 2610, "Rødovre", 20);
            p1.addPersonDetail(personDetail);

            Fee fee = new Fee(LocalDate.of(2025,5,14), 200);
            Fee fee2 = new Fee(LocalDate.of(2025,6,14), 200);
            p1.addFee(fee);
            p1.addFee(fee2);

            Note note1 = new Note("Remember the kid is allergic to icecream", "Alissa");
            Note note2 = new Note("Has to pay for the last two fees", "Alissa");
            p1.addNote(note1);
            p1.addNote(note2);


            em.getTransaction().begin();
            em.persist(p1);

            em.getTransaction().commit();

        } finally {
            emf.close();
        }
    }
}
