package app.DAO;

import app.entities.Person;

import java.util.List;

public interface DolphinDAO {

    Person create(Person person);

    Person findById(int id);

    List<Person> findAll();

    void deleteById(int id);

}
