package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import java.util.ArrayList;
import java.util.List;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.Identifiable;

public class TdsDao<T extends Identifiable> implements Dao<T> {
  private BiEncoder<T> encoder;
  private PersistencyWrapper wrapper;

  public TdsDao(PersistencyWrapper wrapper, BiEncoder encoder) {
    this.encoder = encoder;
    this.wrapper = wrapper;
  }

  @Override
  public T get(int id) throws DaoException {
    T obj = encoder.newEmptyObj();
    obj.setId(id);
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
    Entidad entity = encoder.encodeEntity(obj);
    // Registering the entity in the server gives it a unique id
    wrapper.registerEntity(entity);
    obj.setId(entity.getId());
  }

  @Override
  public void update(T obj) {
    Entidad entity = wrapper.retrieveEntity(obj.getId());
    encoder.updateEntity(entity, obj);
  }

  @Override
  public void delete(T obj) {
    Entidad entity = wrapper.retrieveEntity(obj.getId());
    wrapper.eraseEntity(entity);
  }
}
