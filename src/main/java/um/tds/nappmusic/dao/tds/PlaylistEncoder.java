package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.util.ArrayList;
import java.util.Arrays;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.domain.Playlist;

public final class PlaylistEncoder implements BiEncoder<Playlist> {
  private static final String ENTITY_FIELD = "Playlist";
  private static final String NAME_FIELD = "name";
  private static final String SONGS_FIELD = "songs";

  private PersistencyWrapper factory;

  public PlaylistEncoder(PersistencyWrapper factory) {
    this.factory = factory;
  }

  @Override
  public String getEntityName() {
    return ENTITY_FIELD;
  }

  @Override
  public Playlist newEmptyObj() {
    return new Playlist();
  }

  @Override
  public void initObjFromEntity(Playlist playlist, Entidad entity) throws DaoException {
    playlist.setName(factory.retrieveString(entity, NAME_FIELD));
    playlist.setSongs(factory.retrieveSongList(entity, SONGS_FIELD));
  }

  @Override
  public Entidad encodeEntity(Playlist playlist) {
    Entidad entity = new Entidad();
    entity.setNombre(ENTITY_FIELD);
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                new Propiedad(NAME_FIELD, playlist.getName()),
                new Propiedad(SONGS_FIELD, factory.encodeSongList(playlist.getSongs())))));
    return entity;
  }

  @Override
  public void updateEntity(Entidad entity, Playlist playlist) {
    // TODO
  }
}
