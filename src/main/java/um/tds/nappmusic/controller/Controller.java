package um.tds.nappmusic.controller;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import um.tds.nappmusic.dao.Dao;
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

  private Controller(UserCatalog userCatalog, SongCatalog songCatalog, DaoFactory factory) {
    userDao = factory.getUserDao();
    songDao = factory.getSongDao();
    playlistDao = factory.getPlaylistDao();

    this.userCatalog = userCatalog;
    this.songCatalog = songCatalog;
    xmlLoader = new XmlLoader(songCatalog);
    pdfGenerator = new PdfGenerator();

    currentUser = null;
  }

  /**
   * Get the only instance of Controller and initialize it if it's not.
   *
   * @param userCatalog the userCatalog of the app
   * @param songCatalog the songCatalog of the app
   * @param factory the factory
   * @return
   */
  public static Controller getSingleton(
      UserCatalog userCatalog, SongCatalog songCatalog, DaoFactory factory) {
    if (singleton == null) {
      singleton = new Controller(userCatalog, songCatalog, factory);
    }
    return singleton;
  }

  /**
   * Get the only instance of Controller.
   *
   * @return The instance of Controller class
   */
  public static Controller getSingleton() {
    if (singleton == null) {
      throw new NullPointerException("There's no singleton built.");
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
    User user = userCatalog.getUser(username);
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
      throw new NullPointerException("There's no user registered");
    }
    return currentUser;
  }

  public boolean isUserRegistered(String username) {
    return userCatalog.getUser(username) != null;
  }

  public boolean isSongRegistered(String title, String author) {
    return songCatalog.getSong(title, author) != null;
  }

  public boolean registerSong(String title, String author, List<String> styles, String filePath) {
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

  public Playlist searchSongsByTitleAndAuthor(String titleSubstring, String authorSubstring) {
    return songCatalog.searchSongsByTitleAndAuthor(titleSubstring, authorSubstring);
  }

  public Playlist searchSongsBy(String titleSubstring, String authorSubstring, String style) {
    return songCatalog.searchSongsBy(titleSubstring, authorSubstring, style);
  }

  public Playlist createPlaylist(String name) {
    Playlist playlist = new Playlist(name);
    playlistDao.register(playlist);
    getCurrentUser().addPlaylist(playlist);
    userDao.update(getCurrentUser());
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

  public List<Playlist> getUserPlaylists() {
    return getCurrentUser().getPlaylists();
  }

  public Playlist getUserRecentlyPlayedSongs() {
    return getCurrentUser().getRecent();
  }

  public void updatePlaysCounter(Song song) {
    song.incrementNumPlays();
    getCurrentUser().updateRecent(song);
    songDao.update(song);
    playlistDao.update(getCurrentUser().getRecent());
  }

  public void loadXml(String xmlPath) {
    xmlLoader.loadSongs(xmlPath);
  }

  public void makeUserPremium(User user) {
    user.setPremium(true);
  }

  public void generatePlaylistsPdf(String filePath)
      throws FileNotFoundException, DocumentException {
    pdfGenerator.userPlaylistsToPdf(getCurrentUser(), filePath);
  }

  public UserCatalog getUserCatalog() {
    return userCatalog;
  }

  public SongCatalog getSongCatalog() {
    return songCatalog;
  }
}
