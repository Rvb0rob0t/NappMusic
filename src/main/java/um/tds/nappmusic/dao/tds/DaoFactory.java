package um.tds.nappmusic.dao.tds;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.Identifiable;
import um.tds.nappmusic.domain.User;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.Playlist;
import beans.Entidad;
import beans.Propiedad;

public final class DaoFactory extends um.tds.nappmusic.dao.DaoFactory {
    private static final String COLLECTIONS_DEL = " ";

    private ServicioPersistencia servPersistencia;
    private Dao<User> userDao;
    private Dao<Song> songDao;
    private Dao<Playlist> playlistDao;

    public DaoFactory() {
        servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
        userDao = new Pool<User>(this, new UserEncoder(this));
        // songDao = new Pool<Song>(this, new SongEncoder(this));
        // playlistDao = new Pool<Playlist>(this, new PlaylistEncoder(this));
    }

    @Override
    public Dao<User> getUserDao() {
        return userDao;
    }

    @Override
    public Dao<Song> getSongDao() {
        return songDao;
    }

    @Override
    public Dao<Playlist> getPlaylistDao() {
        return playlistDao;
    }

    public String retrieveString(Entidad entity, String field) {
        return servPersistencia.recuperarPropiedadEntidad(entity, field);
    }

    public boolean retrieveBoolean(Entidad entity, String field) {
        return servPersistencia.recuperarPropiedadEntidad(entity, field) == "true";
    }

    public int retrieveId(Entidad entity, String field) {
        return Integer.parseInt(
            servPersistencia.recuperarPropiedadEntidad(entity, field)
        );
    }

    public List<Integer> retrieveIdList(Entidad entity, String field) {
        return Arrays.stream(
                servPersistencia.recuperarPropiedadEntidad(entity, field)
                .split(COLLECTIONS_DEL)
            )
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }

    public User retrieveUser(Entidad entity, String field) {
        return getUserDao().get(retrieveId(entity, field));
    }

    public Song retrieveSong(Entidad entity, String field) {
        return getSongDao().get(retrieveId(entity, field));
    }

    public Playlist retrievePlaylist(Entidad entity, String field) {
        return getPlaylistDao().get(retrieveId(entity, field));
    }

    public List<Song> retrieveSongList(Entidad entity, String field) {
        return retrieveIdList(entity, field)
            .stream()
            .map(id -> songDao.get(id))
            .collect(Collectors.toList());
    }

    public List<Playlist> retrievePlaylistList(Entidad entity, String field) {
        return retrieveIdList(entity, field)
            .stream()
            .map(id -> playlistDao.get(id))
            .collect(Collectors.toList());
    }

    public Propiedad stringProperty(String field, String value) {
        return new Propiedad(field, value);
    }

    public Propiedad booleanProperty(String field, boolean value) {
        return new Propiedad(field, value? "true" : "false");
    }

    public Propiedad objectProperty(String field, Identifiable obj) {
        return new Propiedad(field, Integer.toString(obj.getId()));
    }

    public Propiedad objectCollectionProperty(
        String field,
        Collection<? extends Identifiable> objs
    ) {
        return new Propiedad(field, objs.stream()
            .map(obj -> String.valueOf(obj.getId()))
            .collect(Collectors.joining(COLLECTIONS_DEL)));
    }
}

