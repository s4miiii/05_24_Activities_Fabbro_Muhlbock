package dal.dao;

import bll.Person;
import dal.DatabaseManager;

import java.util.List;

public class personDbDao implements dao<Person>{
    @Override
    public List<Person> getAll() {
        return DatabaseManager.getInstance().getAllPerson();
    }

    @Override
    public boolean update(Person item) {
        return DatabaseManager.getInstance().updatePerson(item);
    }
}
