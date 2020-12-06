package um.tds.nappmusic.domain;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;

public class UserCatalog {
  private static UserCatalog singleton = null;
  private DaoFactory factory;
  private HashMap<String, User> usersByUsername;

  public static UserCatalog getSingleton() {
    if (singleton == null) {
      singleton = new UserCatalog();
    }
    return singleton;
  }

  private UserCatalog() {
    try {
      factory = DaoFactory.getSingleton();
      List<User> usuarios = factory.getUserDao().getAll();
      usersByUsername =
          new HashMap<String, User>(
              usuarios.stream().collect(Collectors.toMap(User::getUsername, Function.identity())));
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

  public List<User> getUsers() throws DaoException {
    return factory.getUserDao().getAll();
  }

  public User getUser(String username) {
    return usersByUsername.get(username);
  }

  public void addUser(User user) {
    usersByUsername.put(user.getUsername(), user);
  }

  public User removeUser(String username) {
    return usersByUsername.remove(username);
  }
}
