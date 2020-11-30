package um.tds.nappmusic.dao.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.tds.nappmusic.domain.User;

class PoolTests {
  private DaoFactory factory;

  @BeforeEach
  void initAll() {
    factory = new DaoFactory();
  }

  @Test
  void checkUserPool() {
    User user = new User();
    user.setName("Alberto");
    user.setPremium(false);
    // TODO
    // user.setPlaylists(new ArrayList<Playlist>());
    // user.setRecent(new Playlist());

    Pool userDao = new Pool<User>(factory, new UserEncoder(factory));
    userDao.register(user);
    // The object is now in the pool, clear it
    // to try retrieving it from the database
    userDao.clear();
    User retrieved = (User) userDao.get(user.getId());
    assertEquals(user.getName(), retrieved.getName());
    assertEquals(user.isPremium(), retrieved.isPremium());
  }
}
