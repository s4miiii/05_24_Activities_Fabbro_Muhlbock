package bll;

import dal.dao.dao;

import java.util.List;

public class Season {
    private int id;
    private String identifier;

    public Season(int id, String identifier) {
        this.id = id;
        this.identifier = identifier;
    }

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static List<Season> getAll(dao<Season> dao){
        return dao.getAll();
    }
}
