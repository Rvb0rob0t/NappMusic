package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.Identifiable;

public interface BiEncoder<T extends Identifiable> {
  public String getEntityName();

  public T newEmptyObj();

  public void initObjFromEntity(T obj, Entidad entity) throws DaoException;

  default public Entidad encodeEntity(T obj) {
    Entidad entity = new Entidad();
    entity.setNombre(getEntityName());
    encodeIntoEntity(obj, entity);
    return entity;
  }

  public Entidad encodeIntoEntity(T obj, Entidad entity);

  public void updateEntity(Entidad entity, T obj);
}
