package dal.dao;

import java.util.List;

public interface dao<T> {
    List<T> getAll();
    boolean update (T item);
}
