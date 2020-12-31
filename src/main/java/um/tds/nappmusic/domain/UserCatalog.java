package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;

public class UserCatalog {
  private static UserCatalog singleton = null;
  private HashMap<String, User> usersByUsername;

  public static UserCatalog getSingleton() throws DaoException {
    if (singleton == null) {
      singleton = new UserCatalog();
    }
    return singleton;
  }

  private UserCatalog() throws DaoException {
    List<User> usuarios = DaoFactory.getSingleton().getUserDao().getAll();
    usersByUsername =
        new HashMap<String, User>(
            usuarios.stream().collect(Collectors.toMap(User::getUsername, Function.identity())));
  }

  public List<User> getUsers() throws DaoException {
    return new ArrayList<>(usersByUsername.values());
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
