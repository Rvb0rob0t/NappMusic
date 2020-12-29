package um.tds.nappmusic.dao;

import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.User;

public abstract class DaoFactory {
  public static final String TDS_DAO = "um.tds.nappmusic.dao.tds.DaoFactory";

  private static DaoFactory singleton = null;

  public static DaoFactory getSingleton(String type) throws DaoException {
    if (singleton == null) {
      try {
        singleton = (DaoFactory) Class.forName(type).getDeclaredConstructor().newInstance();
      } catch (Exception e) {
        throw new DaoException(e.getMessage());
      }
    }
    return singleton;
  }

  public static DaoFactory getSingleton() throws DaoException {
    return getSingleton(DaoFactory.TDS_DAO);
  }

  protected DaoFactory() {}

  // Factory methods to obtain adaptors
  public abstract Dao<User> getUserDao();

  public abstract Dao<Song> getSongDao();

  public abstract Dao<Playlist> getPlaylistDao();
}
