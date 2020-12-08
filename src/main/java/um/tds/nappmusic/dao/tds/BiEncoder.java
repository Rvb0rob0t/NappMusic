package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.Identifiable;

public interface BiEncoder<T extends Identifiable> {
  public String getEntityName();

  public T newEmptyObj();

  public void initObjFromEntity(T obj, Entidad entity) throws DaoException;

  public Entidad encodeEntity(T obj);

  public void updateEntity(Entidad entity, T obj);
}
