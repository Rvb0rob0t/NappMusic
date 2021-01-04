package um.tds.nappmusic.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserCatalog {
  private static UserCatalog singleton = null;
  private List<User> users;
  private HashMap<String, User> usersByUsername;

  public UserCatalog(List<User> users) {
    this.users = new LinkedList<>(users);
    this.usersByUsername =
        new HashMap<>(
            users.stream().collect(Collectors.toMap(User::getUsername, Function.identity())));
  }

  public List<User> getUsers() {
    return users;
  }

  public User getUser(String username) {
    return usersByUsername.get(username);
  }

  public void addUser(User user) {
    users.add(user);
    usersByUsername.put(user.getUsername(), user);
  }

  public User removeUser(String username) {
    User user = usersByUsername.remove(username);
    if (user != null) {
      users.remove(user);
    }
    return user;
  }
}
