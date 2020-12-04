package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.Identifiable;

public class Pool<T extends Identifiable> implements Dao<T> {
  private BiEncoder<T> encoder;
  private HashMap<Integer, T> pool;
  private DaoFactory factory;

  public Pool(DaoFactory factory, BiEncoder encoder) {
    this.encoder = encoder;
    this.pool = new HashMap<Integer, T>();
    this.factory = factory;
  }

  public void clear() {
    pool.clear();
  }

  @Override
  public T get(int id) {
    T t = pool.get(id);
    if (t != null) return t;
    T obj = encoder.newEmptyObj();
    obj.setId(id);
    pool.put(id, obj);
    encoder.initObjFromEntity(obj, factory.retrieveEntity(id));
    return obj;
  }

  @Override
  public List<T> getAll() {
    return factory.retrieveEntities(encoder.getEntityName()).stream()
        .map(entity -> get(entity.getId()))
        .collect(Collectors.toList());
  }

  @Override
  public void register(T obj) {
    // This step is very important to avoid cycles when registering
    // an entity that triggers more registers
    if (pool.get(obj.getId()) != null) return;

    Entidad entity = encoder.encodeEntity(obj);
    // Registering the entity in the server gives it a unique id
    factory.registerEntity(entity);
    obj.setId(entity.getId());
    pool.put(obj.getId(), obj);
  }

  @Override
  public void update(T obj) {
    Entidad entity = factory.retrieveEntity(obj.getId());
    encoder.updateEntity(entity, obj);
    // We update the pool in case the object is not the same as the stored
    pool.put(obj.getId(), obj);
  }

  @Override
  public void delete(T obj) {
    Entidad entity = factory.retrieveEntity(obj.getId());
    factory.eraseEntity(entity);
    pool.remove(obj.getId());
  }
}
