package dal.dao;

import bll.Activity;
import dal.DatabaseManager;

import java.util.List;

public class activitiesDbDao implements dao<Activity>{
    @Override
    public List<Activity> getAll() {
        return DatabaseManager.getInstance().getAllActivities();
    }

    @Override
    public boolean update(Activity item) {
        return false;
    }
}
