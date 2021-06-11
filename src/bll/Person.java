package bll;

import dal.dao.dao;

import java.util.List;

public class Person {
    private int id;
    private int activityID;
    private String firstname;
    private String lastname;

    public Person(int id, int activityID, String firstname, String lastname) {
        this.id = id;
        this.activityID = activityID;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static List<Person> getAllPerson(dao<Person> dao){
        return dao.getAll();
    }

    public int getId() {
        return id;
    }

    public int getActivityID() {
        return activityID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean update(dao<Person> dao){
        return dao.update(this);
    }
}
