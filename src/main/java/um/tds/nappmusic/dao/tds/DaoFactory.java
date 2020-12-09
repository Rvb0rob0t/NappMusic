package um.tds.nappmusic.dao.tds;

import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.User;

public final class DaoFactory extends um.tds.nappmusic.dao.DaoFactory {
  private PersistencyWrapper persistencyWrapper;

  public DaoFactory() {
    this.persistencyWrapper = new PersistencyWrapper();
  }

  @Override
  public Dao<User> getUserDao() {
    return persistencyWrapper.getUserDao();
  }

  @Override
  public Dao<Song> getSongDao() {
    return persistencyWrapper.getSongDao();
  }

  @Override
  public Dao<Playlist> getPlaylistDao() {
    return persistencyWrapper.getPlaylistDao();
  }
}
