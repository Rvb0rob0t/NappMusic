package um.tds.nappmusic.controller;

import java.util.ArrayList;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.SongCatalog;
import um.tds.nappmusic.domain.User;
import um.tds.nappmusic.domain.UserCatalog;

public final class Controller {

  private static Controller singleton = null;

  private Dao<User> userDao;
  private Dao<Song> songDao;
  private Dao<Playlist> playlistDao;

  private UserCatalog userCatalog;
  private SongCatalog songCatalog;

  private User currentUser;

  private Controller() {
    DaoFactory factory = null;
    try {
      factory = DaoFactory.getSingleton();
    } catch (DaoException e) {
      e.printStackTrace();
    }
    userDao = factory.getUserDao();
    songDao = factory.getSongDao();
    playlistDao = factory.getPlaylistDao();

    userCatalog = UserCatalog.getSingleton();
    songCatalog = SongCatalog.getSingleton();

    currentUser = null;
  }

  /**
   * Get the only instance of Controller.
   *
   * @return The instance of Controller class
   */
  public static Controller getSingleton() {
    if (singleton == null) {
      singleton = new Controller();
    }
    return singleton;
  }

  /**
   * Logs the user who is going to use the app.
   *
   * @param name The name of the user
   * @param password The password of the user
   * @return true if exists a user with username name and password password and false otherwise
   */
  public boolean logIn(String name, String password) {
    User user = UserCatalog.getSingleton().getUser(name);
    if (user != null /* && user.getPassword().equals(password) */) {
      this.currentUser = user;
      return true;
    }
    return false;
  }

  public User getcurrentUser() {
    if (currentUser == null) {
      // TODO Throw exception
    }

    return currentUser;
  }

  public boolean isUserRegistered(String name) {
    return userCatalog.getUser(name) != null;
  }

  public boolean isSongRegistered(String title, String author) {
    return songCatalog.getSong(title, author) != null;
  }

  public boolean registerUser(
      String name,
      String surname,
      String birthDate,
      String email,
      String userName,
      String password) {
    if (isUserRegistered(userName)) {
      return false;
    }

    User user = new User(name, false, null, null, null);
    userCatalog.addUser(user);
    userDao.register(user);
    return true;
  }

  public boolean registerSong(
      String title, String author, ArrayList<String> styles, String filePath) {
    if (isSongRegistered(title, author)) {
      return false;
    }

    Song song = new Song(title, author, styles, filePath, 0);
    songCatalog.addSong(song);
    songDao.register(song);
    return true;
  }

  /**
   * Remove an user.
   *
   * @param user The user to be removed
   * @return true if the user has been deleted and false otherwise
   */
  public boolean removeUser(User user) {
    if (!isUserRegistered(user.getName())) {
      return false;
    }

    userCatalog.removeUser(user.getName());
    userDao.delete(user);
    return true;
  }

  // Functionality
  public Playlist searchSongsBy(String titleSubstring, String authorSubstring, String style) {
    return songCatalog.searchSongsBy(titleSubstring, authorSubstring, style);
  }

  public Playlist createPlaylist(String name) {
    // TODO Should create the playlist elsewhere?
    Playlist playlist = new Playlist(name, new ArrayList<Song>());
    playlistDao.register(playlist);
    currentUser.addPlaylist(playlist);
    userDao.update(currentUser);
    return playlist;
  }

  public boolean addToPlaylist(Playlist playlist, Song song) {
    if (!playlist.add(song)) {
      return false;
    }
    playlistDao.update(playlist);
    return true;
  }

  public boolean removeFromPlaylist(Playlist playlist, Song song) {
    if (!playlist.remove(song)) {
      return false;
    }
    playlistDao.update(playlist);
    return true;
  }

  public ArrayList<Playlist> getUserPlaylists() {
    if (currentUser == null) {
      // TODO Throw exception
    }

    return currentUser.getPlaylists();
  }

  public Playlist getUserRecentlyPlayedSongs() {
    if (currentUser == null) {
      // TODO Throw exception
    }

    return currentUser.getRecent();
  }
}
