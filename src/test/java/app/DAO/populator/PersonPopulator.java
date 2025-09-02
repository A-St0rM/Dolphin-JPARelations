package app.DAO.populator;

import app.DAO.DolphinDAOImpl;
import app.entities.Person;

public class PersonPopulator {

    public static Person[] getPopulator(DolphinDAOImpl dolphinDAO){
        Person person1 = new Person("Alissa");
        Person person2 = new Person("Morten");
        Person person3 = new Person("Philip");

        dolphinDAO.create(person1);
        dolphinDAO.create(person2);
        dolphinDAO.create(person3);


        return new Person[]{person1, person2, person3};
    }
}
