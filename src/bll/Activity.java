package bll;

import dal.dao.dao;

import java.util.List;

public class Activity {
    private int id;
    private int seasonid;
    private String identifier;

    public Activity(int id, int seasonid, String identifier) {
        this.id = id;
        this.seasonid = seasonid;
        this.identifier = identifier;
    }

    public int getId() {
        return id;
    }

    public int getSeasonid() {
        return seasonid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static List<Activity> getAllActivities(dao<Activity> dao){
        return dao.getAll();
    }

    @Override
    public String toString() {
        return this.identifier;
    }
}
