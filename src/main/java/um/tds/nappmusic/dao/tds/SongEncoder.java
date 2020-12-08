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

  private DaoFactory factory;

  public SongEncoder(DaoFactory factory) {
    this.factory = factory;
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
    song.setTitle(factory.retrieveString(entity, TITLE_FIELD));
    song.setAuthor(factory.retrieveString(entity, AUTHOR_FIELD));
    song.setStyles(new ArrayList(factory.retrieveStringList(entity, STYLES_FIELD)));
    song.setFilePath(factory.retrieveString(entity, FILEPATH_FIELD));
    song.setNumPlays(factory.retrieveInt(entity, NUMPLAYS_FIELD));
  }

  @Override
  public Entidad encodeEntity(Song song) {
    Entidad entity = new Entidad();
    entity.setNombre(ENTITY_FIELD);
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                factory.stringProperty(TITLE_FIELD, song.getTitle()),
                factory.stringProperty(AUTHOR_FIELD, song.getAuthor()),
                factory.stringCollectionProperty(STYLES_FIELD, song.getStyles()),
                factory.stringProperty(FILEPATH_FIELD, song.getFilePath()),
                factory.intProperty(NUMPLAYS_FIELD, song.getNumPlays()))));
    return entity;
  }

  @Override
  public void updateEntity(Entidad entity, Song song) {
    // TODO
  }
}
