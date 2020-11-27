package um.tds.nappmusic.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import java.util.ArrayList;
import java.util.Arrays;
import um.tds.nappmusic.domain.User;

public final class UserEncoder implements BiEncoder<User> {
  private static final String ENTITY_FIELD = "User";
  private static final String NAME_FIELD = "name";
  private static final String PREMIUM_FIELD = "premium";
  private static final String DISCOUNT_FIELD = "discount";
  private static final String PLAYLISTS_FIELD = "lists";
  private static final String RECENT_FIELD = "recent";

  private DaoFactory factory;

  public UserEncoder(DaoFactory factory) {
    this.factory = factory;
  }

  public String getEntityName() {
    return ENTITY_FIELD;
  }

  public User newEmptyObj() {
    return new User();
  }

  public void initObjFromEntity(User user, Entidad entity) {
    user.setName(factory.retrieveString(entity, NAME_FIELD));
    user.setPremium(factory.retrieveBoolean(entity, PREMIUM_FIELD));
    // int discountId = factory.retrieveDiscount(entity, DISCOUNT_FIELD);
    user.setPlaylists(factory.retrievePlaylistList(entity, PLAYLISTS_FIELD));
    user.setRecent(factory.retrievePlaylist(entity, RECENT_FIELD));
  }

  public Entidad encodeEntity(User user) {
    Entidad entity = new Entidad();
    entity.setNombre(ENTITY_FIELD);
    entity.setPropiedades(
        new ArrayList<Propiedad>(
            Arrays.asList(
                factory.stringProperty(NAME_FIELD, user.getName()),
                factory.booleanProperty(PREMIUM_FIELD, user.isPremium()),
                factory.objectProperty(DISCOUNT_FIELD, user.getDiscount()),
                factory.objectCollectionProperty(PLAYLISTS_FIELD, user.getPlaylists()),
                factory.objectProperty(RECENT_FIELD, user.getRecent()))));
    return entity;
  }

  public void updateEntity(Entidad entity, User user) {}
}
