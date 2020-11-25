package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.stream.Stream;

import um.tds.nappmusic.dao.DaoFactory;
import um.tds.nappmusic.dao.DaoException;

public class UserCatalog {
    private static UserCatalog singleton = null;
    private DaoFactory factory;
    private HashMap<String, User> usersByName;

    public static UserCatalog getSingleton() {
        if (singleton == null) singleton = new UserCatalog();
        return singleton;
    }

    private UserCatalog() {
        try {
            factory = DaoFactory.getSingleton();
            List<User> usuarios = factory.getUserDao().getAll();
            usersByName = new HashMap(usuarios.stream().collect(Collectors.toMap(
                User::getName, Function.identity()
            )));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public Collection<User> getUsuarios() throws DaoException {
        return usersByName.values();
    }

    public User getUser(String name) {
        return usersByName.get(name);
    }

    public void addUser(User user) {
        usersByName.put(user.getName(), user);
    }

    public User removeUser(String userName) {
        return usersByName.remove(userName);
    }
}

