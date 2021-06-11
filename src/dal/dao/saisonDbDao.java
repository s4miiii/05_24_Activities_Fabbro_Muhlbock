package dal.dao;

import bll.Season;
import dal.DatabaseManager;

import java.util.List;

public class saisonDbDao implements dao<Season> {
    @Override
    public List<Season> getAll() {
        return DatabaseManager.getInstance().getAllSeasons();
    }

    @Override
    public boolean update(Season item) {
        return false;
    }
}
