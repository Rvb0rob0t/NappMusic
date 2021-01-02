package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.Identifiable;

public class TdsPoolDao<T extends Identifiable> implements Dao<T> {
  private BiEncoder<T> encoder;
  private HashMap<Integer, T> pool;
  private PersistencyWrapper wrapper;

  public TdsPoolDao(PersistencyWrapper wrapper, BiEncoder encoder) {
    this.encoder = encoder;
    this.pool = new HashMap<Integer, T>();
    this.wrapper = wrapper;
  }

  public void clear() {
    pool.clear();
  }

  @Override
  public T get(int id) throws DaoException {
    T t = pool.get(id);
    if (t != null) return t;
    T obj = encoder.newEmptyObj();
    obj.setId(id);
    pool.put(id, obj);
    encoder.initObjFromEntity(obj, wrapper.retrieveEntity(id));
    return obj;
  }

  @Override
  public List<T> getAll() throws DaoException {
    // Streams are a hassle to use when the map method throws
    List<T> all = new ArrayList();
    for (Entidad entity : wrapper.retrieveEntities(encoder.getEntityName())) {
      all.add(get(entity.getId()));
    }
    return all;
  }

  @Override
  public void register(T obj) {
    // This step is very important to avoid cycles when registering
    // an entity that triggers more registers
    if (pool.get(obj.getId()) != null) return;

    // We include our object into the pool to avoid cycles
    // We first need to register the entity without fields
    Entidad emptyEntity = new Entidad();
    emptyEntity.setNombre(encoder.getEntityName());
    // Registering the entity in the server gives it a unique id
    wrapper.registerEntity(emptyEntity);
    obj.setId(emptyEntity.getId());
    pool.put(obj.getId(), obj);

    encoder.encodeIntoEntity(obj, emptyEntity);
    // Now the entity is not empty
    wrapper.updateEntity(emptyEntity);
  }

  @Override
  public void update(T obj) {
    Entidad entity = wrapper.retrieveEntity(obj.getId());
    // We update the pool in case the object is not the same as the stored
    pool.put(obj.getId(), obj);
    encoder.updateEntity(entity, obj);
  }

  @Override
  public void delete(T obj) {
    Entidad entity = wrapper.retrieveEntity(obj.getId());
    wrapper.eraseEntity(entity);
    pool.remove(obj.getId());
  }
}
