package um.tds.nappmusic.dao;

import java.util.List;

public interface Dao<T extends Identifiable> {
  /**
   * Obtains the persistent object given its id.
   *
   * @throws DaoException If an error occurs accessing the persistent data.
   */
  T get(int id) throws DaoException;

  /**
   * Obtains a list with all the persistent objects.
   *
   * @throws DaoException If an error occurs accessing the persistent data.
   */
  List<T> getAll() throws DaoException;

  /**
   * Persists the object if it is not persisted. Does not update.
   *
   * When the object contains references to other persistent objects,
   * a register is also invoked for them.
   * This assures that
   * the references stored in the persistent service are valid.
   * The implementation avoids infinite calls
   * when there is a cycle of references.
   */
  void register(T obj);

  /**
   * Updated a registered object.
   *
   * When the object contains references to other persistent objects,
   * a register is also invoked for them.
   * This assures that
   * the references stored in the persistent service are valid,
   * but it does not assure that the referenced objects are updated.
   * The implementation avoids infinite calls
   * when there is a cycle of references.
   */
  void update(T obj);

  /**
   * Removes an object from the persistent service.
   *
   * When the object contains unique references to other persistent objects
   * they are not removed from the persistent service.
   * When other persistent objects contain a reference to this one,
   * the user is responsible of updating their references.
   */
  void delete(T obj);
}
