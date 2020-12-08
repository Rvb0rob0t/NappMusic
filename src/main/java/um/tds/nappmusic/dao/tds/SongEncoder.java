package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.util.ArrayList;
import java.util.Arrays;
import um.tds.nappmusic.domain.Song;

public final class SongEncoder implements BiEncoder<Song> {
  private static final String ENTITY_FIELD = "Song";
  private static final String TITLE_FIELD = "title";
  private static final String AUTHOR_FIELD = "author";
  private static final String STYLES_FIELD = "styles";
  private static final String FILEPATH_FIELD = "filePath";
  private static final String NUMPLAYS_FIELD = "numPlays";

  private PersistencyWrapper wrapper;

  public SongEncoder(PersistencyWrapper wrapper) {
    this.wrapper = wrapper;
  }

  @Override
  public String getEntityName() {
    return ENTITY_FIELD;
  }

  @Override
  public Song newEmptyObj() {
    return new Song();
  }

  @Override
  public void initObjFromEntity(Song song, Entidad entity) {
    song.setTitle(wrapper.retrieveString(entity, TITLE_FIELD));
    song.setAuthor(wrapper.retrieveString(entity, AUTHOR_FIELD));
    song.setStyles(new ArrayList(wrapper.retrieveStringList(entity, STYLES_FIELD)));
    song.setFilePath(wrapper.retrieveString(entity, FILEPATH_FIELD));
    song.setNumPlays(wrapper.retrieveInt(entity, NUMPLAYS_FIELD));
  }

  @Override
  public Entidad encodeEntity(Song song) {
    Entidad entity = new Entidad();
    entity.setNombre(ENTITY_FIELD);
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                new Propiedad(TITLE_FIELD, song.getTitle()),
                new Propiedad(AUTHOR_FIELD, song.getAuthor()),
                new Propiedad(STYLES_FIELD, wrapper.encodeStringList(song.getStyles())),
                new Propiedad(FILEPATH_FIELD, song.getFilePath()),
                new Propiedad(NUMPLAYS_FIELD, wrapper.encodeInt(song.getNumPlays())))));
    return entity;
  }

  @Override
  public void updateEntity(Entidad entity, Song song) {
    wrapper.updateProperty(entity, TITLE_FIELD, song.getTitle());
    wrapper.updateProperty(entity, AUTHOR_FIELD, song.getAuthor());
    wrapper.updateProperty(entity, STYLES_FIELD, wrapper.encodeStringList(song.getStyles()));
    wrapper.updateProperty(entity, FILEPATH_FIELD, song.getFilePath());
    wrapper.updateProperty(entity, NUMPLAYS_FIELD, wrapper.encodeInt(song.getNumPlays()));
  }
}
