package um.tds.nappmusic.dao;

import java.util.List;

public interface Dao<T extends Identifiable> {
    T get(int id);

    List<T> getAll();

    void register(T obj);

    void update(T obj);

    boolean delete(T obj);
}

