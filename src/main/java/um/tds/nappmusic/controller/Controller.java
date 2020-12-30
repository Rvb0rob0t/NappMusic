package um.tds.nappmusic.controller;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
  private XmlLoader xmlLoader;
  private PdfGenerator pdfGenerator;

  private User currentUser;

  private Controller() throws DaoException {
    DaoFactory factory = DaoFactory.getSingleton();
    userDao = factory.getUserDao();
    songDao = factory.getSongDao();
    playlistDao = factory.getPlaylistDao();

    userCatalog = UserCatalog.getSingleton();
    songCatalog = SongCatalog.getSingleton();
    xmlLoader = new XmlLoader();
    pdfGenerator = new PdfGenerator();

    currentUser = null;
  }

  /**
   * Get the only instance of Controller.
   *
   * @return The instance of Controller class
   * @throws DaoException if the DAO system couldn't be initializated
   */
  public static Controller getSingleton() throws DaoException {
    if (singleton == null) {
      singleton = new Controller();
    }
    return singleton;
  }

  /**
   * Register a user in the system.
   *
   * @param name The name of the user
   * @param surname The surname of the user
   * @param birthDate The date of birth of the user
   * @param email The email of the user
   * @param username The nickname of the user
   * @param password The password of the user
   * @return true if the user has been registered or false if there is a user with the same username
   */
  public boolean registerUser(
      String name,
      String surname,
      LocalDate birthDate,
      String email,
      String username,
      String password) {
    if (isUserRegistered(username)) {
      return false;
    }

    User user = new User(name, surname, birthDate, email, username, password, false);
    userCatalog.addUser(user);
    userDao.register(user);
    return true;
  }

  /**
   * Logs the user who is going to use the app.
   *
   * @param username The username of the user
   * @param password The password of the user
   * @return true if exists a user with username username and password password and false otherwise
   */
  public boolean logIn(String username, String password) {
    User user = UserCatalog.getSingleton().getUser(username);
    if (user != null && user.getPassword().equals(password)) {
      this.currentUser = user;

      // The best discount among possible is calculated in each session
      currentUser.setBestDiscount();

      return true;
    }
    return false;
  }

  public User getCurrentUser() {
    if (currentUser == null) {
      // TODO Throw exception
    }

    return currentUser;
  }

  public boolean isUserRegistered(String username) {
    return userCatalog.getUser(username) != null;
  }

  public boolean isSongRegistered(String title, String author) {
    return songCatalog.getSong(title, author) != null;
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
    if (!isUserRegistered(user.getUsername())) {
      return false;
    }

    userCatalog.removeUser(user.getUsername());
    userDao.delete(user);
    return true;
  }

  // Functionality
  public List<String> getAllStyles() {
    return songCatalog.getAllStyles();
  }

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

  public void updatePlaysCounter(Song song) {
    song.incrementNumPlays();
    currentUser.updateRecent(song);
    songDao.update(song);
    playlistDao.update(currentUser.getRecent());
  }

  public void loadXml(String xmlPath) {
    xmlLoader.loadSongs(xmlPath);
  }

  public void makeUserPremium(User user) {
    user.setPremium(true);
  }

  public void generatePlaylistsPdf(String filePath)
      throws FileNotFoundException, DocumentException {
    pdfGenerator.userPlaylistsToPdf(currentUser, filePath);
  }

  public UserCatalog getUserCatalog() {
    return userCatalog;
  }

  public SongCatalog getSongCatalog() {
    return songCatalog;
  }
}
