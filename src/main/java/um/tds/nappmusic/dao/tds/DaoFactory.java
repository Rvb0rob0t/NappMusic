package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.Identifiable;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.User;

public final class DaoFactory extends um.tds.nappmusic.dao.DaoFactory {
  private static final String COLLECTIONS_DEL = "\t";

  private ServicioPersistencia servPersistencia;
  private Dao<User> userDao;
  private Dao<Song> songDao;
  private Dao<Playlist> playlistDao;

  public DaoFactory() {
    servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
    userDao = new Pool<User>(this, new UserEncoder(this));
    songDao = new Pool<Song>(this, new SongEncoder(this));
    playlistDao = new Pool<Playlist>(this, new PlaylistEncoder(this));
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

  // Basic operations on Entidad

  public void registerEntity(Entidad entity) {
    Entidad retrieved = servPersistencia.registrarEntidad(entity);
    entity.setId(retrieved.getId());
  }

  public Entidad retrieveEntity(int id) {
    return servPersistencia.recuperarEntidad(id);
  }

  public List<Entidad> retrieveEntities(String entityName) {
    return servPersistencia.recuperarEntidades(entityName);
  }

  public void eraseEntity(Entidad entity) {
    servPersistencia.borrarEntidad(entity);
  }

  // Encoding typed values in Propiedad

  public Propiedad stringProperty(String field, String value) {
    return new Propiedad(field, value);
  }

  public Propiedad intProperty(String field, int value) {
    return new Propiedad(field, Integer.toString(value));
  }

  public Propiedad booleanProperty(String field, boolean value) {
    return new Propiedad(field, value ? "true" : "false");
  }

  // FIXME This is to support an old test and is no longer needed
  public Propiedad objectProperty(String field, Identifiable obj) {
    return new Propiedad(field, Integer.toString(obj.getId()));
  }

  // FIXME This is to support an old test and is no longer needed
  public Propiedad objectCollectionProperty(String field, Collection<? extends Identifiable> objs) {
    return new Propiedad(
        field,
        objs.stream()
            .map(obj -> String.valueOf(obj.getId()))
            .collect(Collectors.joining(COLLECTIONS_DEL)));
  }

  public Propiedad userProperty(String field, User user) {
    userDao.register(user);
    return new Propiedad(field, Integer.toString(user.getId()));
  }

  public Propiedad songProperty(String field, Song song) {
    songDao.register(song);
    return new Propiedad(field, Integer.toString(song.getId()));
  }

  public Propiedad playlistProperty(String field, Playlist playlist) {
    playlistDao.register(playlist);
    return new Propiedad(field, Integer.toString(playlist.getId()));
  }

  public Propiedad songCollectionProperty(String field, Collection<Song> songs) {
    return new Propiedad(
        field,
        songs.stream()
            .peek(song -> songDao.register(song))
            .map(song -> String.valueOf(song.getId()))
            .collect(Collectors.joining(COLLECTIONS_DEL)));
  }

  public Propiedad playlistCollectionProperty(String field, Collection<Playlist> playlists) {
    return new Propiedad(
        field,
        playlists.stream()
            .peek(playlist -> playlistDao.register(playlist))
            .map(playlist -> String.valueOf(playlist.getId()))
            .collect(Collectors.joining(COLLECTIONS_DEL)));
  }

  // Retrieving typed Propiedad from Entidad

  public String retrieveString(Entidad entity, String field) {
    return servPersistencia.recuperarPropiedadEntidad(entity, field);
  }

  public int retrieveInt(Entidad entity, String field) {
    return Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entity, field));
  }

  public boolean retrieveBoolean(Entidad entity, String field) {
    return servPersistencia.recuperarPropiedadEntidad(entity, field).equals("true");
  }

  public int retrieveId(Entidad entity, String field) {
    return Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entity, field));
  }

  public List<Integer> retrieveIdList(Entidad entity, String field) {
    return Arrays.stream(
            servPersistencia.recuperarPropiedadEntidad(entity, field).split(COLLECTIONS_DEL))
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
    return retrieveIdList(entity, field).stream()
        .map(id -> songDao.get(id))
        .collect(Collectors.toList());
  }

  public List<Playlist> retrievePlaylistList(Entidad entity, String field) {
    return retrieveIdList(entity, field).stream()
        .map(id -> playlistDao.get(id))
        .collect(Collectors.toList());
  }
}
