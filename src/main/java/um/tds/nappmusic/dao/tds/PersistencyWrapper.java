package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.Identifiable;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.User;

class PersistencyWrapper {
  private static final String COLLECTIONS_DEL = "\t";

  private ServicioPersistencia servPersistencia;
  private Dao<User> userDao;
  private Dao<Song> songDao;
  private Dao<Playlist> playlistDao;

  public PersistencyWrapper() {
    this.servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
    this.userDao = new TdsDao<User>(this, new UserEncoder(this));
    this.songDao = new TdsPoolDao<Song>(this, new SongEncoder(this));
    this.playlistDao = new TdsDao<Playlist>(this, new PlaylistEncoder(this));
  }

  public Dao<User> getUserDao() {
    return userDao;
  }

  public Dao<Song> getSongDao() {
    return songDao;
  }

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

  public void updateEntity(Entidad entity) {
    servPersistencia.modificarEntidad(entity);
  }

  public void updateProperty(Entidad entity, String field, String value) {
    for (Propiedad p : entity.getPropiedades()) {
      if (p.getNombre().equals(field)) {
        p.setValor(value);
        servPersistencia.modificarPropiedad(p);
        return;
      }
    }
    servPersistencia.anadirPropiedadEntidad(entity, field, value);
  }

  // Encoding typed values in Propiedad

  public String encodeInt(int value) {
    return Integer.toString(value);
  }

  public String encodeBoolean(boolean value) {
    return value ? "true" : "false";
  }

  public String encodeLocalDate(LocalDate value) {
    return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  String encodeObject(Identifiable obj) {
    return Integer.toString(obj.getId());
  }

  String encodeObjectList(List<? extends Identifiable> objs) {
    return objs.stream()
        .map(obj -> String.valueOf(obj.getId()))
        .collect(Collectors.joining(COLLECTIONS_DEL));
  }

  public String encodeUser(User user) {
    userDao.register(user);
    return Integer.toString(user.getId());
  }

  public String encodeSong(Song song) {
    songDao.register(song);
    return Integer.toString(song.getId());
  }

  public String encodePlaylist(Playlist playlist) {
    playlistDao.register(playlist);
    return Integer.toString(playlist.getId());
  }

  public String encodeStringList(List<String> strings) {
    return strings.stream().collect(Collectors.joining(COLLECTIONS_DEL));
  }

  public String encodeSongList(List<Song> songs) {
    return songs.stream()
        .peek(song -> songDao.register(song))
        .map(song -> String.valueOf(song.getId()))
        .collect(Collectors.joining(COLLECTIONS_DEL));
  }

  public String encodePlaylistList(List<Playlist> playlists) {
    return playlists.stream()
        .peek(playlist -> playlistDao.register(playlist))
        .map(playlist -> String.valueOf(playlist.getId()))
        .collect(Collectors.joining(COLLECTIONS_DEL));
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

  public LocalDate retrieveLocalDate(Entidad entity, String field) throws ParseException {
    return LocalDate.parse(
        servPersistencia.recuperarPropiedadEntidad(entity, field),
        DateTimeFormatter.ISO_LOCAL_DATE);
  }

  public int retrieveId(Entidad entity, String field) {
    return Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entity, field));
  }

  public List<Integer> retrieveIdList(Entidad entity, String field) {
    return splitValueList(servPersistencia.recuperarPropiedadEntidad(entity, field)).stream()
        .map(Integer::valueOf)
        .collect(Collectors.toList());
  }

  public User retrieveUser(Entidad entity, String field) throws DaoException {
    return userDao.get(retrieveId(entity, field));
  }

  public Song retrieveSong(Entidad entity, String field) throws DaoException {
    return songDao.get(retrieveId(entity, field));
  }

  public Playlist retrievePlaylist(Entidad entity, String field) throws DaoException {
    return playlistDao.get(retrieveId(entity, field));
  }

  public List<String> retrieveStringList(Entidad entity, String field) {
    return Arrays.stream(
            servPersistencia.recuperarPropiedadEntidad(entity, field).split(COLLECTIONS_DEL))
        .collect(Collectors.toList());
  }

  public List<Song> retrieveSongList(Entidad entity, String field) throws DaoException {
    // Streams are a hassle to use when the map method throws
    List<Song> list = new ArrayList<>();
    for (int id : retrieveIdList(entity, field)) {
      list.add(songDao.get(id));
    }
    return list;
  }

  public List<Playlist> retrievePlaylistList(Entidad entity, String field) throws DaoException {
    List<Playlist> list = new ArrayList<>();
    for (int id : retrieveIdList(entity, field)) {
      list.add(playlistDao.get(id));
    }
    return list;
  }

  private List<String> splitValueList(String string) {
    if (string.equals("")) {
      return new ArrayList<String>();
    }
    return Arrays.asList(string.split(COLLECTIONS_DEL));
  }
}
