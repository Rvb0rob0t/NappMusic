package um.tds.nappmusic.dao;

import java.util.List;

public interface Dao<T extends Identifiable> {
  T get(int id) throws DaoException;

  List<T> getAll() throws DaoException;

  void register(T obj);

  void update(T obj);

  void delete(T obj);
}
