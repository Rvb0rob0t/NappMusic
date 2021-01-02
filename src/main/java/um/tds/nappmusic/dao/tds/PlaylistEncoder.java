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

  private PersistencyWrapper wrapper;

  public PlaylistEncoder(PersistencyWrapper wrapper) {
    this.wrapper = wrapper;
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
    playlist.setName(wrapper.retrieveString(entity, NAME_FIELD));
    playlist.setSongs(wrapper.retrieveSongList(entity, SONGS_FIELD));
  }

  @Override
  public Entidad encodeIntoEntity(Playlist playlist, Entidad entity) {
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                new Propiedad(NAME_FIELD, playlist.getName()),
                new Propiedad(SONGS_FIELD, wrapper.encodeSongList(playlist.getSongs())))));
    return entity;
  }

  @Override
  public void updateEntity(Entidad entity, Playlist playlist) {
    wrapper.updateProperty(entity, NAME_FIELD, playlist.getName());
    wrapper.updateProperty(entity, SONGS_FIELD, wrapper.encodeSongList(playlist.getSongs()));
  }
}
