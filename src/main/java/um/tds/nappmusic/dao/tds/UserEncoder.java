package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import um.tds.nappmusic.domain.User;

public final class UserEncoder implements BiEncoder<User> {
  private static final String ENTITY_FIELD = "User";
  private static final String NAME_FIELD = "name";
  private static final String SURNAME_FIELD = "surname";
  private static final String BIRTHDATE_FIELD = "date of birth";
  private static final String EMAIL_FIELD = "e-mail";
  private static final String USERNAME_FIELD = "username";
  private static final String PASSWORD_FIELD = "password";
  private static final String PREMIUM_FIELD = "premium";
  private static final String PLAYLISTS_FIELD = "lists";
  private static final String RECENT_FIELD = "recent";

  private DaoFactory factory;

  public UserEncoder(DaoFactory factory) {
    this.factory = factory;
  }

  @Override
  public String getEntityName() {
    return ENTITY_FIELD;
  }

  @Override
  public User newEmptyObj() {
    return new User();
  }

  @Override
  public void initObjFromEntity(User user, Entidad entity) {
    user.setName(factory.retrieveString(entity, NAME_FIELD));
    user.setSurname(factory.retrieveString(entity, SURNAME_FIELD));
    try {
      user.setBirthDate(factory.retrieveLocalDate(entity, BIRTHDATE_FIELD));
    } catch (ParseException e) {
      System.err.println("Date format inconsistency in persistent server");
      e.printStackTrace();
      user.setBirthDate(LocalDate.now()); // TODO is this the best or exit?
    }
    user.setEmail(factory.retrieveString(entity, EMAIL_FIELD));
    user.setUsername(factory.retrieveString(entity, USERNAME_FIELD));
    user.setPassword(factory.retrieveString(entity, PASSWORD_FIELD));
    user.setPremium(factory.retrieveBoolean(entity, PREMIUM_FIELD));
    user.setPlaylists(factory.retrievePlaylistList(entity, PLAYLISTS_FIELD));
    user.setRecent(factory.retrievePlaylist(entity, RECENT_FIELD));
  }

  @Override
  public Entidad encodeEntity(User user) {
    Entidad entity = new Entidad();
    entity.setNombre(ENTITY_FIELD);
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                factory.stringProperty(NAME_FIELD, user.getName()),
                factory.stringProperty(SURNAME_FIELD, user.getSurname()),
                factory.localDateProperty(BIRTHDATE_FIELD, user.getBirthDate()),
                factory.stringProperty(EMAIL_FIELD, user.getEmail()),
                factory.stringProperty(USERNAME_FIELD, user.getUsername()),
                factory.stringProperty(PASSWORD_FIELD, user.getPassword()),
                factory.booleanProperty(PREMIUM_FIELD, user.isPremium()),
                factory.playlistCollectionProperty(PLAYLISTS_FIELD, user.getPlaylists()),
                factory.playlistProperty(RECENT_FIELD, user.getRecent()))));
    return entity;
  }

  @Override
  public void updateEntity(Entidad entity, User user) {
    // TODO
  }
}
