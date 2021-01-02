package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import um.tds.nappmusic.dao.DaoException;
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

  private PersistencyWrapper wrapper;

  public UserEncoder(PersistencyWrapper wrapper) {
    this.wrapper = wrapper;
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
  public void initObjFromEntity(User user, Entidad entity) throws DaoException {
    user.setName(wrapper.retrieveString(entity, NAME_FIELD));
    user.setSurname(wrapper.retrieveString(entity, SURNAME_FIELD));
    try {
      user.setBirthDate(wrapper.retrieveLocalDate(entity, BIRTHDATE_FIELD));
    } catch (ParseException e) {
      e.printStackTrace();
      throw new DaoException("Date format inconsistency in persistent server");
    }
    user.setEmail(wrapper.retrieveString(entity, EMAIL_FIELD));
    user.setUsername(wrapper.retrieveString(entity, USERNAME_FIELD));
    user.setPassword(wrapper.retrieveString(entity, PASSWORD_FIELD));
    user.setPremium(wrapper.retrieveBoolean(entity, PREMIUM_FIELD));
    user.setPlaylists(wrapper.retrievePlaylistList(entity, PLAYLISTS_FIELD));
    user.setRecent(wrapper.retrievePlaylist(entity, RECENT_FIELD));
  }

  @Override
  public Entidad encodeIntoEntity(User user, Entidad entity) {
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                new Propiedad(NAME_FIELD, user.getName()),
                new Propiedad(SURNAME_FIELD, user.getSurname()),
                new Propiedad(BIRTHDATE_FIELD, wrapper.encodeLocalDate(user.getBirthDate())),
                new Propiedad(EMAIL_FIELD, user.getEmail()),
                new Propiedad(USERNAME_FIELD, user.getUsername()),
                new Propiedad(PASSWORD_FIELD, user.getPassword()),
                new Propiedad(PREMIUM_FIELD, wrapper.encodeBoolean(user.isPremium())),
                new Propiedad(PLAYLISTS_FIELD, wrapper.encodePlaylistList(user.getPlaylists())),
                new Propiedad(RECENT_FIELD, wrapper.encodePlaylist(user.getRecent())))));
    return entity;
  }

  @Override
  public void updateEntity(Entidad entity, User user) {
    wrapper.updateProperty(entity, NAME_FIELD, user.getName());
    wrapper.updateProperty(entity, SURNAME_FIELD, user.getSurname());
    wrapper.updateProperty(entity, BIRTHDATE_FIELD, wrapper.encodeLocalDate(user.getBirthDate()));
    wrapper.updateProperty(entity, EMAIL_FIELD, user.getEmail());
    wrapper.updateProperty(entity, USERNAME_FIELD, user.getUsername());
    wrapper.updateProperty(entity, PASSWORD_FIELD, user.getPassword());
    wrapper.updateProperty(entity, PREMIUM_FIELD, wrapper.encodeBoolean(user.isPremium()));
    wrapper.updateProperty(
        entity, PLAYLISTS_FIELD, wrapper.encodePlaylistList(user.getPlaylists()));
    wrapper.updateProperty(entity, RECENT_FIELD, wrapper.encodePlaylist(user.getRecent()));
  }
}
