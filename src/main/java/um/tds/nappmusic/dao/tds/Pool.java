package um.tds.nappmusic.dao.tds;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.Identifiable;

public class Pool<T extends Identifiable> implements Dao<T> {
    private BiEncoder<T> encoder;
    private HashMap<Integer, T> pool;
    private ServicioPersistencia servPersistencia;

    public Pool(DaoFactory factory, BiEncoder encoder) {
        this.encoder = encoder;
        this.pool = new HashMap<Integer, T>();
        this.servPersistencia = FactoriaServicioPersistencia.getInstance()
            .getServicioPersistencia();
    }

    public T get(int id) {
        T t = pool.get(id);
        if (t != null) return t;
        T obj = encoder.newEmptyObj();
        obj.setId(id);
        pool.put(id, obj);
        encoder.initObjFromEntity(obj, servPersistencia.recuperarEntidad(id));
        return obj;
    }

    public List<T> getAll() {
        return servPersistencia.recuperarEntidades(encoder.getEntityName())
            .stream()
            .map(entity -> get(entity.getId()))
            .collect(Collectors.toList());
    }

    public void register(T obj) {
        Entidad entity = encoder.encodeEntity(obj);
        // Registering the entity in the server gives it a unique id
        entity = servPersistencia.registrarEntidad(entity);
        obj.setId(entity.getId());
        pool.put(obj.getId(), obj);
    }

    public void update(T obj) {
        Entidad entity = servPersistencia.recuperarEntidad(obj.getId());
        encoder.updateEntity(entity, obj);
        // We update the pool in case the object is not the same as the stored
        pool.put(obj.getId(), obj);
    }

    public boolean delete(T obj) {
        Entidad entity = servPersistencia.recuperarEntidad(obj.getId());
        if (servPersistencia.borrarEntidad(entity)) {
            pool.remove(obj.getId());
            return true;
        }
        return false;
    }
}

